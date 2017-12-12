$(document).ready(function () {
    $('#searchDate').datepicker({
        format: "yyyy-mm-dd",
        autoclose: true,
        language: "kr"
    });

    $('#searchDateInput').datepicker({
        format: "yyyy-mm-dd",
        todayHighlight:true,
        autoclose: true,
        language: "kr"
    });
    $("#searchDateInput").datepicker("setDate", new Date());

    /** 검색용 - 담당시작기간 memberAccountInfoList.html **/
    $('#managerSearchStartYearMonth').datepicker({
        format: "yyyy-mm",
        viewMode: "months",
        minViewMode: "months",
        autoclose: true,
        language: "kr"
    });/** 검색용 - 담당시작기간 memberAccountInfoList.html **/

    /** 검색용 - 담당종료기간 memberAccountInfoList.html **/
    $('#managerSearchEndYearMonth').datepicker({
        format: "yyyy-mm",
        viewMode: "months",
        minViewMode: "months",
        autoclose: true,
        language: "kr"
    });/** 검색용 - 담당종료기간 memberAccountInfoList.html **/

    /** 담당시작일 memberAccountInfoList.html **/
    $('#managerStartDate').datepicker({
        format: "yyyy-mm",
        viewMode: "months",
        minViewMode: "months",
        autoclose: true,
        language: "kr"
    }); /** 담당시작일 memberAccountInfoList.html **/

    /** 담당종일 memberAccountInfoList.html **/
    $('#managerEndDate').datepicker({
        format: "yyyy-mm",
        viewMode: "months",
        minViewMode: "months",
        autoclose: true,
        language: "kr"
    }); /** 담당종료일 memberAccountInfoList.html **/

    /** 재고관리 - 해당년월 **/
    $('#mappingDate').datepicker({
        format: "yyyy-mm",
        viewMode: "months",
        minViewMode: "months",
        autoclose: true,
        language: "kr"
    }); /** 재고관리 - 해당년월 **/

    $('#searchYear').datepicker({
        format: "yyyy",
        viewMode: "years",
        minViewMode: "years",
        autoclose: true
    });

    $('#searchYearMonth').datepicker({
        format: "yyyy-mm",
        viewMode: "months",
        minViewMode: "months",
        autoclose: true,
        language: "kr"
    });

    $('#searchYearMonth2').datepicker({
        format: "yyyy-mm",
        viewMode: "months",
        minViewMode: "months",
        todayHighlight:true,
        autoclose: true,
        language: "kr"
    });

    var Now = new Date();
    $("#searchYear").val(Now.getFullYear());

    var monthOfYear = Now.getMonth()
    Now.setMonth(monthOfYear - 1)
    $('#searchYearMonth').val(getDateStrYearMonth(Now));
    $('#mappingDate').val(getDateStrYearMonth(Now));

    $("#btnSubmit").click(function (event) {

        event.preventDefault();

        fire_ajax_submit();

    });

    $("#btnEdit").click(function (event) {

        event.preventDefault();
        save();
    });

    $('#pagination-here').bootpag({
        total: 5,          // total pages
        page: 1,            // default page
        maxVisible: 5,     // visible pagination
        leaps: true,         // next/prev leaps through maxVisible
        firstLastUse: true,
        first: '←',
        last: '→',
        wrapClass: 'pagination',
        activeClass: 'active',
        disabledClass: 'disabled',
        nextClass: 'next',
        prevClass: 'prev',
        lastClass: 'last',
        firstClass: 'first'
    }).on("page", function(event, num){
        eval($("#callName").val()+"("+num+")");
    });

    $('#pagination-here').hide();

    $('#pagination-showCodeModal').bootpag({
        total: 5,          // total pages
        page: 1,            // default page
        maxVisible: 5,     // visible pagination
        leaps: true,         // next/prev leaps through maxVisible
        firstLastUse: true,
        first: '←',
        last: '→',
        wrapClass: 'pagination',
        activeClass: 'active',
        disabledClass: 'disabled',
        nextClass: 'next',
        prevClass: 'prev',
        lastClass: 'last',
        firstClass: 'first'
    }).on("page", function(event, num){
        showCodeList($('#relayGroupCode').val(), num);
    });

    $('#pagination-showCodeModal').hide();

    $('#pagination-popupAccountInfoModal').bootpag({
        total: 5,          // total pages
        page: 1,            // default page
        maxVisible: 5,     // visible pagination
        leaps: true,         // next/prev leaps through maxVisible
        firstLastUse: true,
        first: '←',
        last: '→',
        wrapClass: 'pagination',
        activeClass: 'active',
        disabledClass: 'disabled',
        nextClass: 'next',
        prevClass: 'prev',
        lastClass: 'last',
        firstClass: 'first'
    }).on("page", function(event, num){
        popupAccountInfoList(num);
    });

    $('#pagination-popupAccountInfoModal').hide();

    $('#pagination-popupMemberListModal').bootpag({
        total: 5,          // total pages
        page: 1,            // default page
        maxVisible: 5,     // visible pagination
        leaps: true,         // next/prev leaps through maxVisible
        firstLastUse: true,
        first: '←',
        last: '→',
        wrapClass: 'pagination',
        activeClass: 'active',
        disabledClass: 'disabled',
        nextClass: 'next',
        prevClass: 'prev',
        lastClass: 'last',
        firstClass: 'first'
    }).on("page", function(event, num){
        popupMemberList(num);
    });

    $('#pagination-popupMemberListModal').hide();

    $('#pagination-popupItemModal').bootpag({
        total: 5,          // total pages
        page: 1,            // default page
        maxVisible: 5,     // visible pagination
        leaps: true,         // next/prev leaps through maxVisible
        firstLastUse: true,
        first: '←',
        last: '→',
        wrapClass: 'pagination',
        activeClass: 'active',
        disabledClass: 'disabled',
        nextClass: 'next',
        prevClass: 'prev',
        lastClass: 'last',
        firstClass: 'first'
    }).on("page", function(event, num){
        popupItemList(num);
    });

    $('#pagination-popupItemModal').hide();

    $('#pagination-popupProdModal').bootpag({
        total: 5,          // total pages
        page: 1,            // default page
        maxVisible: 5,     // visible pagination
        leaps: true,         // next/prev leaps through maxVisible
        firstLastUse: true,
        first: '←',
        last: '→',
        wrapClass: 'pagination',
        activeClass: 'active',
        disabledClass: 'disabled',
        nextClass: 'next',
        prevClass: 'prev',
        lastClass: 'last',
        firstClass: 'first'
    }).on("page", function(event, num){
        popupProdList(num);
    });

    $('#pagination-popupProdModal').hide();

});

function fire_ajax_submit() {

    if ($("#fileVal").val() == '') { alert("파일을 선택해주세요"); return; }

    var boardIdx = $("#boardIdx").val();
    // Get form
    var form = $('#fileUploadForm')[0];

    var data = new FormData(form);

    $("#btnSubmit").prop("disabled", true);

    $.ajax({
        type: "POST",
        enctype: 'multipart/form-data',
        url: "/api/upload",
        data: data,
        processData: false, //prevent jQuery from automatically transforming the data into a query string
        contentType: false,
        cache: false,
        timeout: 600000,
        success: function (data) {
            if (data.idx > 0) {
                $('#success-alert').show();
                $("#success-alert").fadeTo(1000, 500).slideUp(500, function(){
                    $("#success-alert").hide();
                });
                $("#btnSubmit").prop("disabled", false);
                var input = $("#fileVal");
                input.replaceWith(input.val('').clone(true));

                setCookie("fileUpload","false"); //호출
                checkUploadCheck();
                if (boardIdx == '111') {
                    $.post( "/api/insert/orderList", { idx: data.idx,
                        uploadPath: data.uploadPath,
                        insertTable:'E_ORDER_LIST' });
                } else if (boardIdx == '222') {
                    $.post( "/api/insert/salesDeadlineList", { idx: data.idx,
                        uploadPath: data.uploadPath,
                        insertTable:'E_SALES_DEADLINE_LIST' });
                } else if (boardIdx == '333') {
                    $.post( "/api/insert/collectionList", { idx: data.idx,
                        uploadPath: data.uploadPath,
                        insertTable:'E_COLLECTION_LIST' });
                } else if (boardIdx == '444') {
                    $.post( "/api/insert/deliveryRequestList", { idx: data.idx,
                        uploadPath: data.uploadPath,
                        insertTable:'E_DELIVERY_REQUEST_LIST' });
                } else if (boardIdx == '555') {
                    $.post( "/api/insert/accountInfoList", { idx: data.idx,
                        uploadPath: data.uploadPath,
                        insertTable:'E_ACCOUNT_INFO_LIST' });
                } else if (boardIdx == '666') {
                    $.post( "/api/insert/goalList", { idx: data.idx,
                        uploadPath: data.uploadPath,
                        insertTable:'B_SALES_MANAGER_GOAL' });
                } else if (boardIdx == '777') {
                    $.post( "/api/insert/memberAccountInfoList", { idx: data.idx,
                        uploadPath: data.uploadPath,
                        insertTable:'G_MEMBER_ACCOUNT_INFO_MANAGER_LIST' });
                } else if (boardIdx == '888') {
                    $.post( "/api/insert/adminDistrictList", { idx: data.idx,
                        uploadPath: data.uploadPath,
                        insertTable:'B_ADMINISTRATIVE_DISTRICT_LIST' });
                } else if (boardIdx == '999') {
                    // TODO 디비 인서트 테이블 정보 공통코드로 가져오기로 수정
                    $.post( "/api/insert/accountGradeList", { idx: data.idx,
                        uploadPath: data.uploadPath,
                        insertTable:'G_ACCOUNT_GRADE_LIST' });
                } else if (boardIdx == '001') { // 재고관리 - 품목리스트
                    $.post( "/api/insert/itemList", { idx: data.idx,
                        uploadPath: data.uploadPath,
                        insertTable:'I_ITEM_MASTER_LIST' });
                } else if (boardIdx == '002') { // 재고관리 - 품목재고현황리스트
                    $.post( "/api/insert/itemHistoryList", { idx: data.idx,
                        uploadPath: data.uploadPath,
                        insertTable:'I_ITEM_HISTORY_LIST' });
                } else if (boardIdx == '003') { // 재고관리 - 상품판매현황리스트
                    $.post( "/api/insert/prodList", { idx: data.idx,
                        uploadPath: data.uploadPath,
                        insertTable:'I_PROD_MASTER_LIST' });
                } else if (boardIdx == '004') { // 재고관리 - 품목상품리스트
                    $.post( "/api/insert/itemProdList", { idx: data.idx,
                        uploadPath: data.uploadPath,
                        insertTable:'I_ITEM_PROD_LIST' });
                } else {}
            }

        },
        error: function (e) {

            $("#result").text(e.responseText);
            console.log("ERROR : ", e);
            $("#btnSubmit").prop("disabled", false);
            var input = $("#fileVal");
            input.replaceWith(input.val('').clone(true));

        }
    });

}

function downloadExcel() {

    $("#btnExcel").prop("disabled", true);

    var boardIdx = $('#boardIdx').val();

    var data = new FormData();

    var url = '';
    console.log('boardIdx :: '+boardIdx);
    if (boardIdx == '111') {
        url = "/admin/orderListExcelCount.ajax";
    } else if (boardIdx == '222') {
        url = "/admin/salesDeadlineListExcelCount.ajax";
    } else if (boardIdx == '333') {
        url = "/admin/collectionListExcelCount.ajax";
    } else if (boardIdx == '444') {
        url = "/admin/deliveryRequestListExcelCount.ajax";
    } else if (boardIdx == '555') {
        url = "/admin/accountInfoListExcelCount.ajax";
    } else if (boardIdx == '666') {
    } else if (boardIdx == 'accountStatusList') {
        url = "/admin/accountStatusListExcelCount.ajax";
        data.append('searchYearMonth', $('#searchYearMonth').val());
    } else if (boardIdx == 'itemSalesStatusList') {
        url = "/admin/itemSalesStatusListExcelCount.ajax";
        data.append('searchYearMonth', $('#searchYearMonth').val());
    } else if (boardIdx == 'areaStatusList') {
        url = "/admin/areaStatusListExcelCount.ajax";
        data.append('salesManagerName', $('#salesManagerName').val());
        data.append('searchYearMonth', $('#searchYearMonth').val());
    } else if (boardIdx == '001') {
        url = "/ima/itemListExcelCount.ajax";
        data.append('itemCode', $('#itemCode').val());
        data.append('itemName', $('#itemName').val());
        data.append('useYn', $('#useYn').val());
    } else if (boardIdx == '002') {
        url = "/ima/itemHistoryListExcelCount.ajax";
        data.append('itemCode', $('#itemCode').val());
        data.append('itemName', $('#itemName').val());
        data.append('searchYearMonth2', $('#searchYearMonth2').val());
    } else if (boardIdx == '003') {
        url = "/ima/prodListExcelCount.ajax";
        data.append('prodCode', $('#prodCode').val());
        data.append('prodName', $('#prodName').val());
        data.append('prodType', $('#prodType').val());
        data.append('mallSite', $('#mallSite').val());
        data.append('useYn', $('#useYn').val());
    } else if (boardIdx == 'prodReleaseList') {
        url = "/ima/prodReleaseListExcelCount.ajax";
        data.append('searchYearMonth2', $('#searchYearMonth2').val());
        data.append('prodCode', $('#prodCode').val());
        data.append('prodName', $('#prodName').val());
        data.append('mallCode', $('#mallCode').val());
        data.append('mallSite', $('#mallSite').val());
    } else if (boardIdx == 'duolacList') {
        url = "/ima/duolacListExcelCount.ajax";
        data.append('searchDateInput', $('#searchDateInput').val());
        data.append('prodCode', $('#prodCode').val());
        data.append('prodName', $('#prodName').val());
    } else {
        url = "/admin/cpListExcelCount.ajax";
        data.append('searchYearMonth', $('#searchYearMonth').val());
    }

    $.ajax({
        type: "POST",
        url: url,
        data: data,
        processData: false, //prevent jQuery from automatically transforming the data into a query string
        contentType: false,
        cache: false,
        timeout: 600000,
        success: function (data) {
            if (data.cnt  < 1048576) {
                // $("#btnExcel").prop("disabled", false);
                setCookie("fileDownload","false"); //호출

                $('#modalMessage').text('엑셀다운로드중입니다. 잠시만 기달려주세요.')
                $('#myModal').modal({backdrop: 'static', keyboard: false});

                checkDownloadCheck();

                if (boardIdx == '111') {
                    location.href='/admin/orderListExcel?orderNum='+$('#orderNum').val()+'&accountName='+$('#accountName').val()+'&accountCode='+$('#accountCode').val()+'&searchYearMonth2='+$('#searchYearMonth2').val();
                } else if (boardIdx == '222') {
                    location.href='/admin/salesDeadlineListExcel?salesNum='+$("#salesNum").val()+'&accountName='+$('#accountName').val()+'&accountCode='+$('#accountCode').val()+'&searchYearMonth2='+$('#searchYearMonth2').val();
                } else if (boardIdx == '333') {
                    location.href='/admin/collectionListExcel?collectionNum='+$("#collectionNum").val()+'&accountName='+$('#accountName').val()+'&accountCode='+$('#accountCode').val()+'&searchYearMonth2='+$('#searchYearMonth2').val();
                } else if (boardIdx == '444') {
                    location.href='/admin/deliveryRequestListExcel?requestNum='+$("#requestNum").val()+'&accountName='+$('#accountName').val()+'&accountCode='+$('#accountCode').val()+'&searchYearMonth2='+$('#searchYearMonth2').val();
                } else if (boardIdx == '555') {
                    var uri ='/admin/accountInfoListExcel?accountName='+$("#accountName").val()+'&accountCode='+$('#accountCode').val();
                    location.href=encodeURI(uri);
                } else if (boardIdx == '666') {
                } else if (boardIdx == 'accountStatusList') {
                    location.href='/admin/accountStatusListExcel?searchYearMonth='+$('#searchYearMonth').val();
                } else if (boardIdx == 'itemSalesStatusList') {
                    location.href='/admin/itemSalesStatusListExcel?searchYearMonth='+$('#searchYearMonth').val();
                } else if (boardIdx == 'areaStatusList') {
                    location.href='/admin/areaStatusListExcel?searchYearMonth='+$('#searchYearMonth').val()+'&salesManagerName='+$('#salesManagerName').val();
                } else if (boardIdx == '001') {
                    location.href='/ima/itemListExcel?itemCode='+$('#itemCode').val()+'&itemName='+$('#itemName').val()+'&useYn='+$('#useYn').val();
                } else if (boardIdx == '002') {
                    location.href='/ima/itemHistoryListExcel?itemCode='+$('#itemCode').val()+'&itemName='+$('#itemName').val()+'&searchYearMonth2='+$('#searchYearMonth2').val();
                } else if (boardIdx == '003') {
                    location.href='/ima/prodListExcel?prodCode='+$('#prodCode').val()+'&prodName='+$('#prodName').val()+'&prodType='+$('#prodType').val()+'&mallSite='+$('#mallSite').val()+'&useYn='+$('#useYn').val();
                } else if (boardIdx == 'prodReleaseList') {
                    location.href='/ima/prodReleaseListExcel?prodCode='+$('#prodCode').val()+'&prodName='+$('#prodName').val()+'&searchYearMonth2='+$('#searchYearMonth2').val()+'&mallCode='+$('#mallCode').val()+'&mallSite='+$('#mallSite').val();
                } else if (boardIdx == 'duolacList') {
                    location.href='/ima/duolacListExcel?prodCode='+$('#prodCode').val()+'&prodName='+$('#prodName').val()+'&searchDateInput='+$('#searchDateInput').val();
                } else {
                    location.href='/admin/cpListExcel?searchYearMonth='+$('#searchYearMonth').val();
                }
            } else {
                alert("1048576행까지 엑셀 다운로드 가능합니다.")
                $("#btnExcel").prop("disabled", false);
            }

        },
        error: function (e) {
            console.log("ERROR : ", e);
            $("#btnExcel").prop("disabled", false);
        }
    });

}

function isNumeric(num, opt){
    // 좌우 trim(공백제거)을 해준다.
    num = String(num).replace(/^\s+|\s+$/g, "");

    if(typeof opt == "undefined" || opt == "1"){
        // 모든 10진수 (부호 선택, 자릿수구분기호 선택, 소수점 선택)
        var regex = /^[+\-]?(([1-9][0-9]{0,2}(,[0-9]{3})*)|[0-9]+){1}(\.[0-9]+)?$/g;
    }else if(opt == "2"){
        // 부호 미사용, 자릿수구분기호 선택, 소수점 선택
        var regex = /^(([1-9][0-9]{0,2}(,[0-9]{3})*)|[0-9]+){1}(\.[0-9]+)?$/g;
    }else if(opt == "3"){
        // 부호 미사용, 자릿수구분기호 미사용, 소수점 선택
        var regex = /^[0-9]+(\.[0-9]+)?$/g;
    }else{
        // only 숫자만(부호 미사용, 자릿수구분기호 미사용, 소수점 미사용)
        var regex = /^[0-9]$/g;
    }

    if( regex.test(num) ){
        num = num.replace(/,/g, "");
        return isNaN(num) ? false : true;
    }else{ return false;  }
}

/** 공통 **/
function postAjax(url, data, func) {
    showModal('검색중입니다. 잠시만 기달려주세요.');
    $('#error-alert').hide();
    $.ajax({
        beforeSend:function(xhr) {
            xhr.setRequestHeader("AJAX", true);
        },
        type: "POST",
        url: url,
        data: data,
        processData: false,
        contentType: false,
        cache: false,
        timeout: 600000,
        success: func,
        error: function (xhr, ajaxOptions, thrownError) {
            hideModal();
            // $('#pagination-here').hide();
            // $('#error-alert').show();
            // $("#error-message").text("status :: "+xhr.responseText);
            // console.log(xhr.status);
            // console.log(xhr.responseText);
            // console.log(thrownError);
            if (xhr.status == 401) {
                alert("인증에 실패 했습니다. 로그인 페이지로 이동합니다.");
                location.href = "/login";
            } else if (xhr.status == 403) {
                alert("세션이 만료가 되었습니다. 로그인 페이지로 이동합니다.");
                location.href = "/login";
            } else if (xhr.status == 302) {
                alert("세션이 만료가 되었습니다. 로그인 페이지로 이동합니다.");
                location.href = "/login";
            }
        }
    });
}

function getDateStrYearMonth(myDate) {
    var mm = myDate.getMonth()+1;
    if (mm < 10) mm = "0"+mm;
    return (myDate.getFullYear() + "-" + mm);
}

function comma(num){
    var len, point, str;

    num = num + "";
    var numArray = num.split('.');
    point = numArray[0].length % 3 ;
    len = numArray[0].length;

    str = numArray[0].substring(0, point);
    while (point < len) {
        if (str != "" && str!="-") str += ",";
        str += numArray[0].substring(point, point + 3);
        point += 3;
    }

    if (numArray.length > 1) str += '.'+numArray[1];
    return str;

}

function commaZero2blank(num){
    var len, point, str;

    num = num + "";
    var numArray = num.split('.');
    point = numArray[0].length % 3 ;
    len = numArray[0].length;

    str = numArray[0].substring(0, point);
    while (point < len) {
        if (str != "" && str!="-") str += ",";
        str += numArray[0].substring(point, point + 3);
        point += 3;
    }

    if (numArray.length > 1) str += '.'+numArray[1];

    if (str == '0') {
        str = '';
    } else {}
    return str;

}

function commaDot(num){
    var len, point, str;

    num = num + "";
    var numArray = num.split('.');
    point = numArray[0].length % 3 ;
    len = numArray[0].length;

    str = numArray[0].substring(0, point);
    while (point < len) {
        if (str != "" && str!="-") str += ",";
        str += numArray[0].substring(point, point + 3);
        point += 3;
    }

    if (numArray.length > 1) str += '.'+numArray[1];
    return str;

}

function setCookie(c_name,value){
    var exdate=new Date();
    var c_value=escape(value);
    document.cookie=c_name + "=" + c_value + "; path=/";
}

function checkDownloadCheck(){
    if (document.cookie.indexOf("fileDownload=true") != -1) {
        var date = new Date(1000);
        document.cookie = "fileDownload=; expires=" + date.toUTCString() + "; path=/";
        //프로그래스바 OFF
        $('#myModal').modal('hide');
        $("#btnExcel").prop("disabled", false);
        return;
    }
    setTimeout(checkDownloadCheck , 100);
}

function checkUploadCheck(){
    if (document.cookie.indexOf("fileUpload=true") != -1) {
        var date = new Date(1000);
        document.cookie = "fileUpload=; expires=" + date.toUTCString() + "; path=/";
        alert('엑셀 업로드가 실패하였습니다.');
        return;
    }
    setTimeout(checkUploadCheck , 100);
}

function showModal(str) {
    $("table tbody").empty();
    $('#modalMessage').text(str);
    $('#myModal').modal({backdrop: 'static', keyboard: false});
}

function hideModal() {
    $('#myModal').modal('hide');
}

var checkboxArrayValidate = function(name){
    if(!$('input[name="'+name+'"]:checked').val()){
        alert("체크박스를 하나 이상 선택해주세요");
        return false;
    }else{
        return true;
    }
};

var toastMsg = function(msg, func) {
    $('#modalMessage').text(msg);
    $('#myModal').modal({backdrop: 'static', keyboard: false});
    setTimeout(function() {func(1)}, 3000);
}

