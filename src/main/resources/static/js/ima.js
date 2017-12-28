/** 품목 리스트 가져오기 **/
function getItemList(str) {

    if (str != null && str != '') {
        $("#pageNumber").val(str);
    } else {
        $("#pageNumber").val(1);
    }

    var form = $('#searchForm')[0];

    var data = new FormData(form);

    var url = "/ima/itemList.ajax";

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
                trHtml +="<td><input type='checkbox' name='checks[]' value='"+value['itemCode']+"'></td>";
                trHtml +="<td class='center'>"
                trHtml +="<a href='javascript://' onClick='itemDetail(this);'>"+value['itemCode']+"</a>";
                trHtml +="</td>";
                trHtml +="<td>"+value['itemName']+"</td>";
                trHtml +="<td>"+value['standardInfo']+"</td>";
                trHtml +="<td class='center'>"+value['unit']+"</td>";
                if (value['useYn'] == 'Y') {
                    trHtml +="<td class='center'>등록</td>";
                } else {
                    trHtml +="<td class='foo center'>해제</td>";
                }
                // trHtml +="<td>"+value['salesPriceSum']+"</td>";
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
/** 품목 리스트 가져오기 **/

var itemDetail = function(a){
    var itemCode = $(a).html();

    var $tr = $(a).closest('tr');
    var $detail = $('#'+itemCode);
    var year = 1;   // 올해부터 작년까
    // var year = $("#year").val();

    if($detail.length){
        $detail.toggle();
    }else{
        $(a).attr('disabled','disabled');

        $.ajax({
            url:'/ima/itemHistoryListByItemCode.ajax',
            type:'post',
            data:{itemCode:itemCode, year:year},
            dataType:'json',
            success:function(data){
                if(data.list.length > 0){
                    var str = '';
                    str += '<tr style="display:none;" id="'+itemCode+'">';
                    str += '	<td colspan="14">';
                    str += '	<table width="100%" class="table table-bordered">';
                    str += '	<thead>';
                    str += '	<tr>';
                    str += '		<th class="none text-center">해당년월</th>';
                    str += '		<th class="none text-center">기초재고</th>';
                    str += '		<th class="none text-center">입고</th>';
                    str += '		<th class="none text-center">출고</th>';
                    str += '		<th class="none text-center">기말재고</th>';
                    str += '	</tr>';
                    str += '	</thead>';

                    $.each(data.list, function(key, value) {
                        str += '<tr>';
                        str += '	<td class="center">'+value['id']['updateDate'].substr(0,7)+'</td>';
                        str += '	<td class="right">'+comma(value['basicStock'])+'</td>';
                        str += '	<td class="right">'+comma(value['inItem'])+'</td>';
                        str += '	<td class="right">'+comma(value['outItem'])+'</td>';
                        str += '	<td class="right">'+comma(value['closingStock'])+'</td>';
                        str += '</tr>';
                    });
                    str += '	</table>';
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

/** 품목 재고현황 리스트 가져오기 **/
function getItemHistoryList(str) {

    if (str != null && str != '') {
        $("#pageNumber").val(str);
    } else {
        $("#pageNumber").val(1);
    }

    var form = $('#searchForm')[0];

    var data = new FormData(form);

    var url = "/ima/itemHistoryList.ajax";

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
                trHtml +="<td class='center'>"+value['itemCode']+"</td>";
                trHtml +="<td>"+value['itemName']+"</td>";
                trHtml +="<td class='center'>"+value['updateDate']+"</td>";
                trHtml +="<td class='right'>"+comma(value['basicStock'])+"</td>";
                trHtml +="<td class='right'>"+comma(value['inItem'])+"</td>";
                trHtml +="<td class='right'>"+comma(value['outItem'])+"</td>";
                trHtml +="<td class='right'>"+comma(value['closingStock'])+"</td>";
                // trHtml +="<td>"+value['salesPriceSum']+"</td>";
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
/** 품목 재고현황 리스트 가져오기 **/

/** 상품 판매현황 리스트 가져오기 **/
function getProdReleaseList(str) {

    if (str != null && str != '') {
        $("#pageNumber").val(str);
    } else {
        $("#pageNumber").val(1);
    }

    var form = $('#searchForm')[0];

    var data = new FormData(form);

    var url = "/ima/prodReleaseList.ajax";

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
                trHtml +="<td class='center'>"+value['id']['updateDate']+"</td>";
                trHtml +="<td class='center'>"+value['id']['prodCode']+"</td>";
                trHtml +="<td>"+value['prodName']+"</td>";
                trHtml +="<td class='right'>"+comma(value['prodQty'])+"</td>";
                if (value['unitPrice'] == null) {
                    trHtml +="<td class='right'></td>";
                } else {
                    trHtml +="<td class='right'>"+comma(value['unitPrice'])+"</td>";
                }
                trHtml +="<td class='center'>"+value['id']['exmallCode']+"</td>";
                trHtml +="<td class='center'>"+value['mallSite']+"</td>";
                // trHtml +="<td>"+value['salesPriceSum']+"</td>";
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
/** 상품 판매현황 리스트 가져오기 **/

/** 상품 재고현황 리스트 가져오기 **/
function getProdHistoryList(str) {

    if (str != null && str != '') {
        $("#pageNumber").val(str);
    } else {
        $("#pageNumber").val(1);
    }

    var form = $('#searchForm')[0];

    var data = new FormData(form);

    var url = "/ima/prodHistoryList.ajax";

    // showModal('검색중입니다. 잠시만 기달려주세요.');
    $("table tbody").empty();
    var func = function (data) {
        // hideModal();
        $('#total-count').text("총건수 : " + data.totalCount);

        var date = new Date();
        if (data.totalPageCount > 0) {
            $('#pagination-here').bootpag({total: data.totalPageCount, page:$("#pageNumber").val()});
            $("table tbody").empty();
            $.each(data.list, function(key, value) {
                var trHtml = "";
                trHtml = "<tr style='cursor: pointer;'   onclick='setItemHistory(this)'>";
                trHtml +="<td class='center' style='padding: 3px !important;'>"+value['id']['prodCode']+"</td>";
                trHtml +="<td class='center' style='padding: 3px !important;'>"+value['id']['inputSeq']+"</td>";
                trHtml +="<td style='padding: 3px !important;'>"+value['prodName']+"</td>";
                trHtml +="<td class='center' style='padding: 3px !important;'>"+value['id']['inputDate']+"</td>";
                if (value['inputType'] == '1') {
                    trHtml +="<td class='center' style='padding: 3px !important;'>+</td>";
                } else if (value['inputType'] == '2') {
                    trHtml +="<td class='center' style='padding: 3px !important;'>-</td>";
                } else {
                }
                trHtml +="<td class='center' style='padding: 3px !important;'>"+value['outItem']+"</td>";
                trHtml += "<td class='center' style='padding: 3px !important;'><button type='button' class='btn btn-success btn-sm' style='margin-right: 10px; margin-left: 20px;' onclick='setItemHistory(this)'>수정</button>";
                // trHtml +="<td>"+value['salesPriceSum']+"</td>";
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
/** 상품 재고현황 리스트 가져오기 **/

/** 상품 재고현황 리스트 가져오기 **/
function getProdHistoryList2(str) {

    if (str != null && str != '') {
        $("#pageNumber").val(str);
    } else {
        $("#pageNumber").val(1);
    }

    var form = $('#searchForm')[0];

    var data = new FormData(form);

    var url = "/ima/prodHistoryList.ajax";

    var counter = 0;
    showModal('검색중입니다. 잠시만 기달려주세요.');
    var func = function (data) {
        hideModal();
        $('#total-count').text("총건수 : " + data.totalCount);

        var date = new Date();
        if (data.totalPageCount > 0) {
            $('#pagination-here').bootpag({total: data.totalPageCount, page:$("#pageNumber").val()});
            $("table tbody").empty();
            $.each(data.list, function(key, value) {
                counter++;
                var trHtml = "";
                // trHtml = "<tr onclick='selectAccountInfo(this)'>";
                trHtml = "<tr>";
                trHtml +="<td class='center'>"
                trHtml +="<a href='javascript://' onClick='prodDetail(this);'>"+value['prodCode']+"</a>";
                trHtml +="<input type='hidden' name='prodCode"+counter+"'  value='"+value['prodCode']+"' />"
                trHtml +="</td>";
                trHtml +="<td>"+value['prodName']+"</td>";
                trHtml +="<td class='right'>"+counter+"</td>";
                trHtml +="<td class='center'>";
                trHtml +="<input type='text' class='form-control' name='inputDate"+counter+"'  placeholder='yyyy-mm-dd' value='' />"
                trHtml +="</td>";
                trHtml +="<td class='right'>";
                trHtml +="<input type='text' name='outItem"+counter+"' class='form-control'  />";
                trHtml +="</td>";
                // trHtml +="<td class='right'>"+comma(value['basicStock'])+"</td>";
                // trHtml +="<td class='right'>"+comma(value['inItem'])+"</td>";
                // trHtml +="<td class='right'>"+comma(value['outItem'])+"</td>";
                trHtml +="<td><select name='inputType"+counter+"' class='form-control'><option value='1'>반품</option><option value='2'>기획</option><option value='3'>행사</option></select></td>";
                trHtml +="<td class='center'><button type='button' class='btn btn-success btn-xs' name='btnSaveProdHistory"+counter+"' onclick='saveProdHistory2("+counter+")'>저장</button></td>";
                trHtml += "</tr>";
                var row =$("table tbody").append(trHtml);
                // console.log("row ::: "+ row);
                $("[name^=inputDate]",row).datepicker({
                    format: "yyyy-mm-dd",
                    autoclose: true,
                    todayHighlight: true,
                    language: "kr"
                });

            });
            $("[name^=inputDate]").datepicker("setDate", date);
            $('#pagination-here').show();
        } else {
            $('#pagination-here').hide();
        }
    };

    postAjax(url, data, func);

}
/** 상품 재고현황 리스트 가져오기 **/

var useYnChecked = function(useYn){
    var msg1 = '';
    var msg2 = '';
    var msg3 = '';
    if (useYn === 'Y') {
        msg1 = '재고관리 등록하시겠습니까?';
        msg2 = '건의 주문을 정말 재고관리 등록하시겠습니까?';
        msg3 = '등록하였습니다.'
    } else {
        msg1 = '재고관리 해제하시겠습니까?';
        msg2 = '건의 주문을 정말 재고관리 해제하시겠습니까?';
        msg3 = '해제하였습니다.'
    }
    if(!checkboxArrayValidate('checks[]')) return false;
    if(!confirm(msg1)) return false;
    var idx = '';
    var j = 0;
    $('input[name="checks[]"]').each(function(i,v){
        if($(this).prop('checked')){
            idx += (j===0 ? "'"+$(this).val()+"'" : ",'"+$(this).val()+"'");
            j++;
        }
    });

    var l = idx.split(',').length;
    if(!confirm(l+msg2)){
        return false;
    }

    $.ajax({
        url:'/ima/itemUseYn.ajax',
        type:'post',
        data:{itemCodes:idx, useYn:useYn},
        dataType:'json',
        success:function(data){
            if(data.size === l){
                alert(msg3);
                getItemList($("#pageNumber").val());
//                    location.href='/ima/itemList';
            }else{
                alert(msg3);
                return false;
            }
        },
        error:function(error){
            console.log(error);
        }
    });
};

function prodDetail(a) {
    var prodCode = $(a).html();

    var $tr = $(a).closest('tr');
    var $detail = $('#'+prodCode);

    var year = 1;   // 올해부터 작년까
    // var year = $("#year").val();

    if($detail.length){
        $detail.toggle();
    }else{
        $(a).attr('disabled','disabled');

        $.ajax({
            url:'/ima/prodHistoryListByProdCode.ajax',
            type:'post',
            data:{prodCode:prodCode, year:year},
            dataType:'json',
            success:function(data){
                if(data.list.length > 0){
                    var str = '';
                    str += '<tr style="display:none;" id="'+prodCode+'">';
                    str += '	<td colspan="14">';
                    str += '	<table width="100%" class="table table-bordered">';
                    str += '	<thead>';
                    str += '	<tr>';
                    str += '		<th class="none text-center">해당년월</th>';
                    // str += '		<th class="none text-center">기초재고</th>';
                    // str += '		<th class="none text-center">입고</th>';
                    str += '		<th class="none text-center">출고</th>';
                    // str += '		<th class="none text-center">기말재고</th>';
                    str += '		<th class="none text-center">분류</th>';
                    str += '	</tr>';
                    str += '	</thead>';

                    $.each(data.list, function(key, value) {
                        str += '<tr>';
                        str += '	<td class="center">'+value['id']['inputDate']+'</td>';
                        // str += '	<td class="right">'+comma(value['basicStock'])+'</td>';
                        // str += '	<td class="right">'+comma(value['inItem'])+'</td>';
                        str += '	<td class="right">'+comma(value['outItem'])+'</td>';
                        // str += '	<td class="right">'+comma(value['closingStock'])+'</td>';
                        str += '	<td class="right">'+value['inputType']+'</td>';
                        str += '</tr>';
                    });
                    str += '	</table>';
                    str += '	</td>';
                    str += '</tr>';

                    $tr.after(str);
                    $('#'+prodCode).show();
                }else{
                }
            },
            error:function(error){
                console.log(error);
            }
        });
        $(a).removeAttr('disabled');
    }


}

function saveItemHistory() {
    var inputType = $("#inputType").val();
    var inputDate = $("#searchDateInput").val();
    var prodCode = $("#itemCode").val();
    var inputSeq = $("#inputSeq").val();
    var outItem = $("#prodQty").val();
    var prodName = $("#itemName").val();
    //
    // console.log(outItem);

    if ($("#itemCode").val().length > 0) {

    } else {
        alert('품목코드는 필수 입니다.');
        $("#prodQty").val("");
        $("#prodQty").focus();
        return;
    }
    if (isNumeric($("#prodQty").val())) {
    } else {
        alert('숫자만 입력해주세요.');
        $("#prodQty").val("");
        $("#prodQty").focus();
        return;
    }

    var form = $('#createItemHistoryModalForm')[0];

    var data = new FormData(form);

    $.ajax({
        url:'/ima/saveProdHistory.ajax',
        type:'post',
        // data:data,
        data:{itemCode:prodCode, inputSeq:inputSeq, searchDateInput:inputDate, prodQty:outItem, inputType:inputType, itemName:prodName},
        dataType:'json',
        success:function(data){
            closeModalIma('createItemHistoryModal');
            // getProdHistoryList(1);
            // $("button[name='btnSaveProdHistory"+cnt+"']").prop("disabled", false);
            // $("input[name='outItem"+cnt+"']").val('');
        },
        error:function(error){
            closeModalIma('createItemHistoryModal');
            // $("button[name='btnSaveProdHistory"+cnt+"']").prop("disabled", false);
            console.log(error);
        }
    });
}

function saveProdHistory2(cnt) {
    // alert("saveProdHistory");
    var inputType = $("select[name='inputType"+cnt+"']").val();
    var inputDate = $("input[name='inputDate"+cnt+"']").val();
    var prodCode = $("input[name='prodCode"+cnt+"']").val();
    var outItem = $("input[name='outItem"+cnt+"']").val();

    console.log(outItem);

    if (isNumeric(outItem)) {
    } else {
        alert('숫자만 입력해주세요.');
        $("input[name='outItem"+cnt+"']").val("");
        $("input[name='outItem"+cnt+"']").focus();
        return;
    }

    console.log($("select[name='inputType"+cnt+"']").val());
    console.log($("input[name='inputDate"+cnt+"']").val());
    console.log($("input[name='prodCode"+cnt+"']").val());
    console.log($("input[name='outItem"+cnt+"']").val());
    console.log(cnt);

    $("button[name='btnSaveProdHistory"+cnt+"']").prop("disabled", true);

    $.ajax({
        url:'/ima/saveProdHistory.ajax',
        type:'post',
        data:{prodCode:prodCode, inputDate:inputDate, outItem:outItem, inputType:inputType},
        dataType:'json',
        success:function(data){
            // getProdHistoryList(1);
            $("button[name='btnSaveProdHistory"+cnt+"']").prop("disabled", false);
            $("input[name='outItem"+cnt+"']").val('');
        },
        error:function(error){
            $("button[name='btnSaveProdHistory"+cnt+"']").prop("disabled", false);
            console.log(error);
        }
    });
}

/** 상품 리스트 가져오기 **/
function getProdList(str) {

    if (str != null && str != '') {
        $("#pageNumber").val(str);
    } else {
        $("#pageNumber").val(1);
    }

    var form = $('#searchForm')[0];

    var data = new FormData(form);

    var url = "/ima/prodList.ajax";

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
                trHtml +="<td><input type='checkbox' name='checks[]' value='"+value['id']['prodCode']+"-"+value['id']['mallSite']+"'></td>";
                trHtml +="<td class='center'>"+value['id']['prodCode']+"</td>";
                trHtml +="<td>"+value['prodName']+"</td>";
                if (value['prodType'] == 'G') {
                    trHtml +="<td class='center'>일반</td>";
                } else if (value['prodType'] == 'S') {
                    trHtml +="<td class='center'>세트</td>";
                } else {
                    trHtml +="<td class='center'>포인트</td>";
                }
                trHtml +="<td class='center'>"+value['id']['mallSite']+"</td>";
                if (value['useYn'] == 'Y') {
                    trHtml +="<td class='center'>사용</td>";
                } else {
                    trHtml +="<td class='foo center'>미사용</td>";
                }
                // trHtml +="<td>"+value['salesPriceSum']+"</td>";
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
/** 상품 리스트 가져오기 **/

/** 품목-상품 리스트 가져오기 **/
function getItemProdList(str) {

    if (str != null && str != '') {
        $("#pageNumber").val(str);
    } else {
        $("#pageNumber").val(1);
    }

    var form = $('#searchForm')[0];

    var data = new FormData(form);

    var url = "/ima/itemProdList.ajax";

    showModal('검색중입니다. 잠시만 기달려주세요.');
    var func = function (data) {
        hideModal();
        $('#total-count').text("총건수 : " + data.totalCount);
        if (data.totalPageCount > 0) {
            $('#pagination-here').bootpag({total: data.totalPageCount, page:$("#pageNumber").val()});
            $("#itemProdListTbody").empty();
            $.each(data.list, function(key, value) {
                var trHtml = "";
                trHtml = "<tr style='cursor: pointer;'  onclick='setItemProd(this)'>";
                trHtml +="<td class='center'>"+value['itemCode']+"</td>";
                trHtml +="<td>"+value['itemName']+"</td>";
                trHtml +="<td class='center'>"+value['prodCode']+"</td>";
                trHtml +="<td>"+value['prodName']+"</td>";
                trHtml +="<td class='center'>"+value['mappingDate']+"</td>";
                if (value['useYn'] == 'Y') {
                    trHtml +="<td class='center'>등록</td>";
                } else {
                    trHtml +="<td class='foo center'>해제</td>";
                }
                trHtml += "<td><button type='button' class='btn btn-success btn-xs' style='margin-right: 10px; margin-left: 20px;' onclick='setItemProd(this)'>수정</button>";
                trHtml += "</tr>";
                $("#itemProdListTbody").append(trHtml);
            });
            $('#pagination-here').show();
        } else {
            $('#pagination-here').hide();
        }
    };

    postAjax(url, data, func);

}
/** 품목-상품 리스트 가져오기 **/
/** 품목-상품 수정 화면 **/
function setItemProd(obj) {
    $('#itemCode').val($(obj).children('td').eq(0).html());
    $('#itemCode').attr('readonly', true);
    $('#itemName').val($(obj).children('td').eq(1).html());
    $('#prodCode').val($(obj).children('td').eq(2).html());
    $('#prodName').val($(obj).children('td').eq(3).html());
    $('#mappingDate').val($(obj).children('td').eq(4).html());
    $('#createItemProdBtn').text("수정");
    $('#deleteItemProdBtn').show();
    $('#createItemProdModal').modal();
    $('#createItemProdModal').on('shown.bs.modal', function () {
        $('#prodCode').focus()
    });
} /** 품목-상품 수정 화면 **/
/** 품목-상품 삭제 **/
function deleteItemProd() {
    // Get form
    var form = $('#createItemProdModalForm')[0];

    var data = new FormData(form);

    $.ajax({
        type: "POST",
        url: "/ima/deleteItemProd.ajax",
        data: data,
        processData: false, //prevent jQuery from automatically transforming the data into a query string
        contentType: false,
        cache: false,
        timeout: 600000,
        success: function (data) {
            $('#createItemProdModal').modal('hide');
            getItemProdList(1);
            // location.href='/ima/itemProdList';
        },
        error: function (e) {
            console.log("ERROR : ", e);
        }
    });
} /** 품목-상품 삭제 **/

/** 품목-상품 등록 모달 **/
function createItemProd() {
    $('#warnning-alert').hide();
    $('#itemCode').val("");
    $('#itemCode').attr('readonly', false);
    // $('#groupCodeId').attr("readonly", false);
    $('#itemName').val("");
    $('#prodCode').val("");
    $('#prodName').val("");
    // $('#mappingDate').val("");
    var Now = new Date();
    $('#mappingDate').val(getDateStrYearMonth(Now));
    $('#createItemProdBtn').text("등록");
    $('#deleteItemProdBtn').hide();
    $('#createItemProdModal').modal({backdrop: 'static', keyboard: false});
    $('#createItemProdModal').on('shown.bs.modal', function () {
        $('#itemCode').focus()
    });

} /** 품목-상품 등록 모달 **/

/** 상품 입출고 등록 모달 **/
function createItemHistory() {
    // $('#warnning-alert').hide();
    // $('#itemCode').val("");
    // $('#inputSeq').val("");
    // // $('#groupCodeId').attr("readonly", false);
    // $('#itemName').val("");
    // $('#prodCode').val("");
    // $('#prodName').val("");
    // $('#prodQty').val("");
    // $('#inputType').val(1);

    $('#itemCode').attr('readonly', false);
    $('#searchDateInput').attr('disabled', false);
    // $("#searchDateInput").datepicker("option", "ignoreReadonly", false);
    var form = $('#createItemHistoryModalForm')[0];

    form.reset();
    // $('#mappingDate').val("");
    var Now = new Date();
    $('#searchDateInput').val(getDateStrYearMonthDay(Now));
    $('#createItemHistoryBtn').text("등록");
    $('#deleteItemHistoryBtn').hide();
    $('#createItemHistoryModal').modal({backdrop: 'static', keyboard: false});
    $('#createItemHistoryModal').on('shown.bs.modal', function () {
        $('#itemCode').focus()
    });

} /** 품목-상품 등록 모달 **/

/** 상품 입출고 수정 화면 **/
function setItemHistory(obj) {
    $('#itemCode').attr('readonly', true);
    $('#searchDateInput').attr('disabled', true);
    $('#itemCode').val($(obj).children('td').eq(0).html());
    $('#inputSeq').val($(obj).children('td').eq(1).html());
    $('#itemName').val($(obj).children('td').eq(2).html());
    $('#searchDateInput').val($(obj).children('td').eq(3).html());
    $('#prodQty').val($(obj).children('td').eq(5).html());
    if ($(obj).children('td').eq(4).html() == '+') {
        $('#inputType').val(1);
    } else {
        $('#inputType').val(2);
    }
    $('#createItemHistoryBtn').text("수정");
    $('#deleteItemHistoryBtn').show();
    $('#createItemHistoryModal').modal();
    $('#createItemHistoryModal').on('shown.bs.modal', function () {
        $('#prodCode').focus()
    });
} /** 품목-상품 수정 화면 **/

/** 상품 입출고 삭제 **/
function deleteItemHistory() {
    $('#searchDateInput').attr('disabled', false);
    // Get form
    var form = $('#createItemHistoryModalForm')[0];

    var data = new FormData(form);

    var inputDate = $("#searchDateInput").val();
    var prodCode = $("#itemCode").val();
    var inputSeq = $("#inputSeq").val();

    $.ajax({
        type: "POST",
        url: "/ima/deleteItemHistory.ajax",
        data: data,
        processData: false, //prevent jQuery from automatically transforming the data into a query string
        contentType: false,
        cache: false,
        timeout: 600000,
        success: function (data) {
            $('#createItemProdModal').modal('hide');
            closeModalIma('createItemHistoryModal');
            // location.href='/ima/itemProdList';
        },
        error: function (e) {
            console.log("ERROR : ", e);
        }
    });
} /** 품목-상품 삭제 **/

/** 팝업 품목 리스트 가져오기 **/
function popupItemList(str) {
    if (str != null && str != '') {
        $("#pageNumber2").val(str);
    } else {
        $("#pageNumber2").val(1);
    }
    // Get form
    var form = $('#popupItemForm')[0];

    var data = new FormData(form);
    data.append("pageNumber", $("#pageNumber2").val());
    data.append("itemName", $("#popupItemName").val());
    data.append("itemCode", "");
    data.append("useYn", "Y");

    var url = "/ima/itemList.ajax";

    // showModal('검색중입니다. 잠시만 기달려주세요.');
    $("#popupItemBody").empty();
    var func = function (data) {
        // hideModal();
        if (data.totalPageCount > 0) {
            $('#pagination-popupItemModal').bootpag({total: data.totalPageCount});
            $("#popupItemBody").empty();
            var trHtml = "";
            $.each(data.list, function(key, value) {
                trHtml = "<tr onclick='selectItem(this)'>";
                trHtml +="<td>"+value['itemCode']+"</td>";
                trHtml +="<td>"+value['itemName']+"</td>";
                trHtml += "</tr>";
                $("#popupItemBody").append(trHtml);
            });

            $('#pagination-popupItemModal').show();
        } else {
            $('#pagination-popupItemModal').hide();
        }
    };

    postAjax(url, data, func);

} /** 팝업 품목 리스트 가져오기 **/


/** 팝업 품목 선택 **/
function selectItem(obj) {
    $('#popupItemModal').modal('hide');
//    $('#popupAccountInfoModal2').modal('hide');
//    $('#createItemProdModal').modal();
    $('#itemCode').val($(obj).children('td').eq(0).html());
    $('#itemName').val($(obj).children('td').eq(1).html());
} /** 팝업 품목 선택 **/

/** 팝업 상품 리스트 가져오기 **/
function popupProdList(str) {
    if (str != null && str != '') {
        $("#pageNumber3").val(str);
    } else {
        $("#pageNumber3").val(1);
    }
    // Get form
    var form = $('#popupItemForm')[0];

    var data = new FormData(form);
    data.append("pageNumber", $("#pageNumber3").val());
    data.append("prodName",$("#popupProdName").val());
    data.append("prodCode","");
    data.append("prodType","");
    data.append("mallSite","");
    data.append("useYn","Y");

    var url = "/ima/prodList.ajax";

    showModal('검색중입니다. 잠시만 기달려주세요.');;
    var func = function (data) {
        hideModal();
        if (data.totalPageCount > 0) {
            $('#pagination-popupProdModal').bootpag({total: data.totalPageCount});
            $("#popupProdBody").empty();
            var trHtml = "";
            $.each(data.list, function(key, value) {
                trHtml = "<tr onclick='selectProd(this)'>";
                trHtml +="<td>"+value['id']['prodCode']+"</td>";
                trHtml +="<td>"+value['prodName']+"</td>";
                trHtml += "</tr>";
                $("#popupProdBody").append(trHtml);
            });

            $('#pagination-popupProdModal').show();
        } else {
            $('#pagination-popupProdModal').hide();
        }
    };

    postAjax(url, data, func);

} /** 팝업 상품 리스트 가져오기 **/

/** 팝업 상품 선택 **/
function selectProd(obj) {
    $('#popupProdModal').modal('hide');
//    $('#popupAccountInfoModal2').modal('hide');
//    $('#createItemProdModal').modal();
    $('#prodCode').val($(obj).children('td').eq(0).html());
    $('#prodName').val($(obj).children('td').eq(1).html());
} /** 팝업 상품 선택 **/

function closeModalIma(page) {
    $('#'+page).modal('hide');
    // if (page == 'createItemProdModal' || page == 'popupItemModal' || page == 'popupProdModal') {
    if (page == 'createItemProdModal') {
        // location.href = '/ima/itemProdList';
        getItemProdList(1);
    // } else if (page == 'createAccountGradeInfoModal' || page == 'popupAccountInfoModal2') {
    //     getAccountGradeList(1);
    }

    if (page == 'createItemHistoryModal') {
        getProdHistoryList(1);
    }

}
/** 품목-상품 등록 **/
function saveItemProd() {
    if ($('#itemCode').val().length == 0 || $('#prodCode').val().length == 0 || $('#mappingDate').val().length == 0) {
        $('#warnning-alert').show();
        $("#warnning-message").text("품목코드, 상품코드, 해당년월은 필수값입니다.");
        return;
    }

    // Get form
    var form = $('#createItemProdModalForm')[0];

    var data = new FormData(form);

    $.ajax({
        type: "POST",
        url: "/ima/createItemProd.ajax",
        data: data,
        processData: false, //prevent jQuery from automatically transforming the data into a query string
        contentType: false,
        cache: false,
        timeout: 600000,
        success: function (data) {
            getItemProdList(1);
            $('#createItemProdModal').modal('hide');
            // location.href='/ima/itemProdList';
        },
        error: function (e) {
            console.log("ERROR : ", e);
        }
    });
} /** 품목-상품 등록 **/

/**  재고현황 가져오기 **/
function getImaList(str) {

    if (str != null && str != '') {
        $("#pageNumber").val(str);
    } else {
        $("#pageNumber").val(1);
    }

    var form = $('#searchForm')[0];

    var data = new FormData(form);

    var url = "/ima/imaList.ajax";

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
                trHtml +="<td class='center'>"
                trHtml +="<a href='javascript://' onClick='imaDetail(this);'>"+value['itemCode']+"</a>";
                trHtml +="</td>";
                trHtml +="<td>"+value['itemName'];
                if (value['exmallQty'] > 0) trHtml +="<button type='button' class='btn pull-right btn-danger btn-xs' onclick='test()' >외부몰</button>";
                if (value['duolacQty'] > 0) trHtml +="<button type='button' class='btn pull-right btn-primary btn-xs' onclick='test()' >듀오락</button>";
                if (value['orderQty'] > 0) trHtml +="<button type='button' class='btn pull-right btn-success btn-xs' onclick='test()' >영업</button>";
                trHtml +="</td>";
                if (value['updateDate'] == undefined) {
                    trHtml +="<td class='center'></td>";
                } else {
                    trHtml +="<td class='center'>"+value['updateDate']+"</td>";
                }
                if (value['basicStock'] == undefined) {
                    trHtml +="<td class='right'></td>";

                } else {
                    trHtml +="<td class='right'>"+comma(value['basicStock'])+"</td>";

                }
                if (value['inItem'] == undefined) {
                    trHtml +="<td class='right'></td>";

                } else {
                    trHtml +="<td class='right'>"+comma(value['inItem'])+"</td>";

                }
                if (value['outItem'] == undefined) {

                    trHtml +="<td class='right'></td>";
                } else {
                    trHtml +="<td class='right'>"+comma(value['outItem'])+"</td>";

                }
                if (value['closingStock'] == undefined) {

                    trHtml +="<td class='foo right'></td>";
                } else {

                    trHtml +="<td class='foo right'>"+comma(value['closingStock'])+"</td>";
                }
                trHtml +="<td class='foo right'>협의</td>";
                trHtml +="<td class='foo right'>"+comma(value['sumQty'])+"</td>";
                trHtml +="<td class='center'>"+value['orderDate']+"</td>";
                trHtml +="<td class='right'>"+comma(value['orderQty'])+"</td>";
                trHtml +="<td class='right'>"+comma(value['orderPrice'])+"</td>";
                trHtml +="<td class='center'>"+value['duolacDate']+"</td>";
                trHtml +="<td class='right'>"+comma(value['duolacQty'])+"</td>";
                trHtml +="<td class='right'>"+comma(value['duolacPrice'])+"</td>";
                trHtml +="<td class='center'>"+value['exmallDate']+"</td>";
                trHtml +="<td class='right'>"+comma(value['exmallQty'])+"</td>";
                trHtml +="<td class='right'>"+comma(value['exmallPrice'])+"</td>";
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

var test = function() {
    alert('test');
}

var imaDetail = function(a){
    var itemCode = $(a).html();

    var $tr = $(a).closest('tr');
    var $detail = $('#'+itemCode);
    var year = 1;   // 올해부터 작년까
    // var year = $("#year").val();

    if($detail.length){
        $detail.toggle();
    }else{
        $(a).attr('disabled','disabled');

        $.ajax({
            url:'/ima/itemHistoryListByItemCode.ajax',
            type:'post',
            data:{itemCode:itemCode, year:year},
            dataType:'json',
            async: false,
            success:function(data){
                if(data.list.length > 0){
                    var str = '';
                    str += '<tr style="display: none" id="'+itemCode+'">';
                    str += '	<td colspan="18">';
                    str += '<table width="100%" class="table table-bordered" id="datatable'+itemCode+'">';
                    str += '	<thead>';
                    str += '	<tr>';
                    str += '		<th class="text-center">해당년월</th>';
                    str += '		<th class="text-center">기초재고</th>';
                    str += '		<th class="text-center">입고</th>';
                    str += '		<th class="text-center">출고</th>';
                    str += '		<th class="text-center">기말재고</th>';
                    str += '	</tr>';
                    str += '	</thead>';
                    str += '	<tbody>';
                    $.each(data.list, function(key, value) {
                        str += '    <tr>';
                        str += '	    <td class="center">'+value['id']['updateDate'].substr(0,7).replace("-", "년")+'월</td>';
                        str += '	    <td class="right">'+value['basicStock']+'</td>';
                        str += '	    <td class="right">'+value['inItem']+'</td>';
                        str += '	    <td class="right">'+value['outItem']+'</td>';
                        str += '	    <td class="right">'+value['closingStock']+'</td>';
                        str += '    </tr>';
                    });
                    str += '	</tbody>';
                    str += '</table>';
                    str += '<div id="container'+itemCode+'" class="highchart-css"></div>';
                    str += '<div style="margin: 20px;"></div>';
                    str += '<script type="text/javascript">';
                    str += "    Highcharts.chart('container"+itemCode+"', {";

                    str += "data: {";
                    str += "    table: 'datatable"+itemCode+"'";
                    str += "},";
                    str += "chart: {";
                    str += "    type: 'line'";
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
                    str += "});";
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


/** 자사몰 판매 현황 리스트 가져오기 **/
function getDuolacList(str) {

    if (str != null && str != '') {
        $("#pageNumber").val(str);
    } else {
        $("#pageNumber").val(1);
    }

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

    var url = "/ima/duolacList.ajax";

    // showModal('검색중입니다. 잠시만 기달려주세요.');
    $("#duolacListBody").empty();
    $("#pointListBody").empty();
    var func = function (data) {
        // hideModal();
        $('#total-count').text("총건수 : " + data.totalCount);
        if (data.totalPageCount > 0) {
            $('#pagination-here').bootpag({total: data.totalPageCount, page:$("#pageNumber").val()});
            $.each(data.list, function(key, value) {
                var trHtml = "";
                if (value['priority'] == undefined) {
                    trHtml += "<tr class='foo'>";
                    trHtml +="<td class='center'></td>";
                    trHtml +="<td>합계</td>";

                    trHtml +="<td></td>";
                } else {
                    trHtml += "<tr>";

                    trHtml +="<td class='center'>"+value['priority']+"</td>";
                    trHtml +="<td>"+value['itemCode']+"</td>";
                    trHtml +="<td>"+value['itemName']+"</td>";
                }
                trHtml +="<td class='right'>"+commaZero2blank(value['oneTime'])+"</td>";
                trHtml +="<td class='right'>"+commaZero2blank(value['twoTime'])+"</td>";
                trHtml +="<td class='right'>"+commaZero2blank(value['threeTime'])+"</td>";
                trHtml +="<td class='right'>"+commaZero2blank(value['prodQtyReturn'])+"</td>";
                trHtml +="<td class='right'>"+commaZero2blank(value['prodQty'])+"</td>";
                trHtml +="<td class='right'>"+commaZero2blank(value['exmallQty'])+"</td>";
                if (value['priority'] == undefined) {
                    trHtml += "<td class='right'></td>";
                } else {
                    if (value['qtHistory'] == undefined) {
                        trHtml += "<td class='right'></td>";
                    } else {
                        trHtml += "<td class='right'>" + commaZero2blank(value['qtHistory']) + "</td>";
                    }
                }
                if (value['priority'] == undefined) {
                    trHtml += "<td class='right'></td>";
                } else {
                    if (value['qtGoodInv'] == undefined) {
                        trHtml += "<td class='right'></td>";
                    } else {
                        trHtml += "<td class='right'>" + commaZero2blank(value['qtGoodInv']) + "</td>";
                    }
                }
                trHtml += "</tr>";
                $("#duolacListBody").append(trHtml);
            });

            $.each(data.pointList, function(key, value) {
                var trHtml = "";
                if (value['priority'] == undefined) {
                    trHtml += "<tr class='foo'>";

                    trHtml +="<td class='center'></td>";
                    trHtml +="<td>합계</td>";
                } else {
                    trHtml += "<tr>";
                    trHtml +="<td class='center'>"+value['priority']+"</td>";
                    trHtml +="<td>"+value['itemName']+"</td>";
                }
                trHtml +="<td class='right'>"+commaZero2blank(value['oneTime'])+"</td>";
                trHtml +="<td class='right'>"+commaZero2blank(value['twoTime'])+"</td>";
                trHtml +="<td class='right'>"+commaZero2blank(value['threeTime'])+"</td>";
                trHtml +="<td class='right'>"+commaZero2blank(value['prodQtyReturn'])+"</td>";
                trHtml +="<td class='right'>"+commaZero2blank(value['prodQty'])+"</td>";
                trHtml +="<td class='right'></td>";
                if (value['priority'] == undefined) {
                    trHtml += "<td class='center'></td>";
                } else {
                    if (value['qtGoodInv'] == undefined) {
                        trHtml += "<td class='center'></td>";
                    } else {
                        trHtml += "<td class='center'>" + commaZero2blank(value['qtGoodInv']) + "</td>";
                    }
                }
                trHtml += "</tr>";
                $("#pointListBody").append(trHtml);
            });

            $('#pagination-here').show();
        } else {
            $('#pagination-here').hide();
        }
    };

    postAjax(url, data, func);

}


/** 재고 - 외부몰 리스트 가져오기 **/
function getExmallOrderList(str) {

    if (str != null && str != '') {
        $("#pageNumber").val(str);
    } else {
        $("#pageNumber").val(1);
    }

    var form = $('#searchForm')[0];

    var data = new FormData(form);

    var url = "/exmall/orderList.ajax";

    showModal('검색중입니다. 잠시만 기달려주세요.');
    var func = function (data) {
        hideModal();
        if (data.totalPageCount > 0) {
            $('#total-count').text("총건수 : " + data.totalCount);
            $('#pagination-here').show();
            $('#pagination-here').bootpag({total: data.totalPageCount, page:$("#pageNumber").val()});
            $("#exmallOrderListBody").empty();
            var trHtml = "";
            $.each(data.list, function(key, value) {
                trHtml = "<tr>";

                trHtml += "<td>"+value["prodno"]+"</td>";
                trHtml += "<td>"+value["omDesc"]+"</td>";
                trHtml += "<td>"+value["prodnm"]+"</td>";
                trHtml += "<td>"+value["sqty"]+"</td>";
                trHtml += "</tr>";

                $("#exmallOrderListBody").append(trHtml);
            });
        } else {
            $('#total-count').text("총건수 : 0");
            $('#pagination-here').hide();
        }
    };

    postAjax(url, data, func);

}
/** 재고 - 외부몰 리스트 가져오기 **/



/** 기초 재고 리스트 가져오기 **/
function getBasicStockList(str) {

    if (str != null && str != '') {
        $("#pageNumber").val(str);
    } else {
        $("#pageNumber").val(1);
    }

    var form = $('#searchForm')[0];

    var data = new FormData(form);

    var url = "/ima/basicStockList.ajax";

    var counter = 0;
    showModal('검색중입니다. 잠시만 기달려주세요.');
    var func = function (data) {
        hideModal();
        $('#total-count').text("총건수 : " + data.totalCount);

        var date = new Date();
        if (data.totalPageCount > 0) {
            $('#pagination-here').bootpag({total: data.totalPageCount, page:$("#pageNumber").val()});
            $("table tbody").empty();
            $.each(data.list, function(key, value) {
                counter++;
                var trHtml = "";
                // trHtml = "<tr onclick='selectAccountInfo(this)'>";
                trHtml = "<tr>";
                trHtml +="<td class='center'>"
                trHtml +="<a href='javascript://' onClick='prodDetail(this);'>"+value['itemCode']+"</a>";
                trHtml +="<input type='hidden' name='itemCode"+counter+"'  value='"+value['itemCode']+"' />"
                trHtml +="<input type='hidden' name='plant"+counter+"'  value='IMA' />"
                trHtml +="</td>";
                trHtml +="<td>"+value['prodName']+"</td>";
                // trHtml +="<td class='right'>"+counter+"</td>";
                trHtml +="<td class='center'>";
                trHtml +="<input type='text' class='form-control' name='inputDate"+counter+"'  placeholder='yyyy' value='' />"
                trHtml +="</td>";
                trHtml +="<td class='right'>";
                trHtml +="<input type='text' name='qtGoodInv"+counter+"' class='form-control'  />";
                trHtml +="</td>";
                // trHtml +="<td class='right'>"+comma(value['basicStock'])+"</td>";
                // trHtml +="<td class='right'>"+comma(value['inItem'])+"</td>";
                // trHtml +="<td class='right'>"+comma(value['outItem'])+"</td>";
                // trHtml +="<td><select name='inputType"+counter+"' class='form-control'><option value='1'>반품</option><option value='2'>기획</option><option value='3'>행사</option></select></td>";
                trHtml +="<td class='center'><button type='button' class='btn btn-success btn-xs' name='btnSaveBasicStock"+counter+"' onclick='saveBasicStock("+counter+")'>저장</button></td>";
                trHtml += "</tr>";
                var row =$("table tbody").append(trHtml);
                // console.log("row ::: "+ row);
                $("[name^=inputDate]",row).datepicker({
                    format: "yyyy",
                    autoclose: true,
                    todayHighlight: true,
                    language: "kr"
                });

            });
            $("[name^=inputDate]").datepicker("setDate", date);
            $('#pagination-here').show();
        } else {
            $('#pagination-here').hide();
        }
    };

    postAjax(url, data, func);

}
/** 기초 재고 리스트 가져오기 **/

/** 기초 재고 등록 **/
function saveBasicStock(cnt) {
    // alert("saveProdHistory");
    var inputDate = $("input[name='inputDate"+cnt+"']").val()+"00";
    var itemCode = $("input[name='itemCode"+cnt+"']").val();
    var qtGoodInv = $("input[name='qtGoodInv"+cnt+"']").val();
    var plant = $("input[name='plant"+cnt+"']").val();

    if (isNumeric(qtGoodInv)) {
    } else {
        alert('숫자만 입력해주세요.');
        $("input[name='qtGoodInv"+cnt+"']").val("");
        $("input[name='qtGoodInv"+cnt+"']").focus();
        return;
    }

    console.log($("input[name='inputDate"+cnt+"']").val());
    console.log($("input[name='itemCode"+cnt+"']").val());
    console.log($("input[name='qtGoodInv"+cnt+"']").val());
    console.log(cnt);

    $("button[name='btnSaveBasicStock"+cnt+"']").prop("disabled", true);

    $.ajax({
        url:'/ima/saveBasicStock.ajax',
        type:'post',
        data:{itemCode:itemCode, inputDate:inputDate, qtGoodInv:qtGoodInv, plant:plant},
        dataType:'json',
        success:function(data){
            // getProdHistoryList(1);
            $("button[name='btnSaveBasicStock"+cnt+"']").prop("disabled", false);
            $("input[name='qtGoodInv"+cnt+"']").val('');
        },
        error:function(error){
            $("button[name='btnSaveBasicStock"+cnt+"']").prop("disabled", false);
            console.log(error);
        }
    });
} /** 기초 재고 등록 **/