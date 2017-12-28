
/**  재고현황 가져오기 **/
function getNeoeList(str) {

    if (str != null && str != '') {
        $("#pageNumber").val(str);
    } else {
        $("#pageNumber").val(1);
    }

    console.log($("#searchDateInput").val());
    if ($("#searchDateInput").val().length > 0) {
        var startDate = "2017-09-01";
        var startDateArr = startDate.split('-');

        var endDate = $("#searchDateInput").val();
        var endDateArr = endDate.split('-');

        var startDateCompare = new Date(startDateArr[0], parseInt(startDateArr[1])-1, startDateArr[2]);
        var endDateCompare = new Date(endDateArr[0], parseInt(endDateArr[1])-1, endDateArr[2]);

        if(startDateCompare.getTime() > endDateCompare.getTime()) {

            alert("2017년9월1일 이후로 검색해주세요.");

            return;
        }
    }

    var form = $('#searchForm')[0];

    var data = new FormData(form);

    var url = "/neoe/neoeList.ajax";

    showModal('검색중입니다. 잠시만 기달려주세요.');
    var func = function (data) {
        hideModal();
        $('#total-count').text("총건수 : " + data.totalCount);
        if (data.totalPageCount > 0) {
            $('#pagination-here').bootpag({total: data.totalPageCount, page:$("#pageNumber").val()});
            $("table tbody").empty();
            $.each(data.list, function(key, value) {
                var trHtml = "";
                // trHtml = "<tr onclick='selectAccountInfo(this)'>";
                trHtml = "<tr>";
                trHtml += "<td class='center'>"
                trHtml += "<a href='javascript://' onClick='getNeoeDetail(this);'>" + value['CD_ITEM'] + "</a>";
                trHtml += "</td>";
                trHtml += "<td>" + value['NM_ITEM'];
                if (value['eQt'] > 0) trHtml += "<button type='button' class='btn btn-danger btn-sm float-right' onclick='test()' >외부몰</button>";
                if (value['dQt'] > 0) trHtml += "<button type='button' class='btn btn-primary btn-sm float-right' onclick='test()' >듀오락</button>";
                if (value['salesQt'] > 0) trHtml += "<button type='button' class='btn btn-success btn-sm float-right' onclick='test()' >영업</button>";
                var dQt = 0; if (value['dQt'] > 0) dQt = value['dQt'];
                var eQt = 0;
                if (value['eQt'] > 0) eQt = value['eQt'];
                trHtml += "</td>";
                trHtml += "<td class='right'>" + comma(value['QT']) + "</td>";
                trHtml += "<td class='right'>" + comma(value['salesQt']) + "</td>";
                trHtml += "<td class='right'>" + comma(value['gi']) + "</td>";
                if (value['qtGoodInv'] == undefined) {

                    trHtml += "<td class='right'></td>";
                } else {

                    trHtml += "<td class='right'>" + comma(value['qtGoodInv']) + "</td>";
                }
                trHtml += "<td class='center'>" + value['dt'].substring(0, 4) + "-" + value['dt'].substring(4, 6) + "</td>";
                trHtml += "<td class='foo right'>" + comma(dQt+eQt) + "</td>";
                trHtml += "<td class='right'>" + comma(dQt) + "</td>";
                if (value['dSum'] > 0) {
                    trHtml += "<td class='right'>" + comma(value['dSum']) + "</td>";

                } else {
                    trHtml += "<td class='right'>0</td>";

                }
                trHtml += "<td class='right'>" + comma(eQt) + "</td>";
                trHtml += "</tr>";
                $("table tbody").append(trHtml);
            });
            $('#pagination-here').show();
        } else {
            $('#pagination-here').hide();
        }
    };

    postAjax(url, data, func);

}
/** 재고현황 가져오기 **/

var getNeoeDetail = function(a){
    var itemCode = $(a).html();

    var $tr = $(a).closest('tr');
    var $detail = $('#'+itemCode);
    // var year = 1;   // 올해부터 작년까
    // var year = $("#year").val();

    console.log($('#searchDateInput').val());
    console.log($('#multiCdSl').val());
    if($detail.length){
        $detail.toggle();
    }else{
        $(a).attr('disabled','disabled');

        $.ajax({
            url:'/neoe/neoeDetailList.ajax',
            type:'post',
            data:{itemCode:itemCode, searchDateInput:$('#searchDateInput').val(), multiCdSl:$('#multiCdSl').val()},
            dataType:'json',
            async: false,
            success:function(data){
                if(data.list.length > 0){
                    var str = '';
                    str += '<tr style="display: none" id="'+itemCode+'">';
                    str += '	<td colspan="18">';
                    str += '<div id="container'+itemCode+'" style="min-width: 1550px; height: 500px;"></div>';
                    str += '<div style="margin: 5px;"></div>';
                    str += '<table width="100%" class="table table-bordered" id="datatable'+itemCode+'">';
                    str += '	<thead>';
                    str += '	<tr>';
                    str += '		<th class="text-center">해당년월</th>';
                    // str += '		<th class="text-center">기초재고</th>';
                    str += '		<th class="text-center">입고</th>';
                    str += '		<th class="text-center">출고</th>';
                    str += '		<th class="text-center">기말재고</th>';
                    str += '		<th class="text-center">영업 판매량</th>';
                    str += '		<th class="text-center">배송실 입고</th>';
                    str += '		<th class="text-center">듀오락 판매량</th>';
                    str += '		<th class="text-center">외부몰 판매량</th>';
                    // str += '		<th class="text-center">영업판매액</th>';
                    str += '	</tr>';
                    str += '	</thead>';
                    str += '	<tbody>';
                    var kimal = 0;
                    $.each(data.list, function(key, value) {
                        str += '    <tr>';
                        str += '	    <td class="center">' + value['DT'].substring(0, 4) + "년" + value['DT'].substring(4, 6) + '월</td>';
                        // str += '	    <td class="right">'+value['QT_INV']+'</td>';
                        str += '	    <td class="right">'+value['qtIn']+'</td>';
                        str += '	    <td class="right">'+value['qtOut']+'</td>';
                        if (key == 0) {
                            kimal = value['qtIn']-value['qtOut'];
                            str += '	    <td class="right">'+checkFixZero(kimal.toFixed(2))+'</td>';
                        } else {
                            kimal = kimal + (value['qtIn']-value['qtOut']);
                            str += '	    <td class="right">'+checkFixZero(kimal.toFixed(2))+'</td>';
                        }
                        str += '	    <td class="right">'+value['SALES_QT']+'</td>';
                        str += '	    <td class="right">'+(value['qt']>0?value['qt']:0)+'</td>';

                        // str += '	    <td class="right">'+value['GI']+'</td>';
                        str += '	    <td class="right">'+(value['dQt']>0?value['dQt']:0)+'</td>';
                        str += '	    <td class="right">'+(value['eQt']>0?value['eQt']:0)+'</td>';
                        str += '    </tr>';
                    });
                    str += '	</tbody>';
                    str += '</table>';
                    // str += '<div id="container'+itemCode+'" class="highchart-css"></div>';
                    // str += '<div style="margin: 20px;"></div>';
                    str += '<script type="text/javascript">';
                    str += "    itemContainer = Highcharts.chart('container"+itemCode+"', {";

                    str += "data: {";
                    str += "    table: 'datatable"+itemCode+"'";
                    str += "},";
                    str += "chart: {";
                    str += "    type: 'spline'";
                    str += "},";
                    str += "title: {";
                    str += "    text: '재고현황'";
                    str += "},";
                    str += "yAxis: {";
                    str += "    allowDecimals: false,";
                    str += "        title: {";
                    str += "        text: '수량'";
                    str += "    }";
                    str += "},";
                    str += "credits: {";
                    str += "    enabled: false";
                    str += "}";
                    // str += "},";
                    // str += "responsive: {";
                    // str += "    rules: [{";
                    // str += "        condition: {";
                    // str += "            maxWidth: 1920";
                    // str += "        }";
                    // str += "    }]";
                    // str += "}";
                    str += "});";
                    // str += "itemContainer.redraw();";
                    // str += "itemContainer.reflow();";
                    str += " var windowWidth = $( window ).width();";
                    str += "console.log('::'+windowWidth);";
                    str += "itemContainer.setSize(windowWidth-350, 500);;";
                    str += "</script>";
                    str += '	</td>';
                    str += '</tr>';
                    $tr.after(str);
                    $('#'+itemCode).show();
                }else{
                }
            },
            error:function(error){
                console.log(error);
            }
        });
        $(a).removeAttr('disabled');
    }

};


function checkFixZero(fix) {
    var rtn = '';

    var arrFix = fix.split('.');

    if (arrFix.length == 2 && arrFix[1] == '00') {
        rtn = arrFix[0];
    } else {
        rtn = fix;
    }

    return rtn;
}