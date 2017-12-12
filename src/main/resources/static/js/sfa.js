function getOrderList(str) {

    if (str != null && str != '') {
        $("#pageNumber").val(str);
    } else {
        $("#pageNumber").val(1);
    }

    var form = $('#searchForm')[0];

    var data = new FormData(form);

    var url = "/admin/orderList.ajax";

    showModal('검색중입니다. 잠시만 기달려주세요.');
    var func = function (data) {
        hideModal();
        if (data.totalPageCount > 0) {
            $('#total-count').text("총건수 : " + data.totalCount);
            $('#pagination-here').bootpag({total: data.totalPageCount, page:$("#pageNumber").val()});
            $("table tbody").empty();
            $.each(data.list, function(key, value) {
                var tr = $("<tr />")
                $.each(value, function(k, v) {

                    if (typeof v == 'object') {
                        $.each(v, function(k2, v2) {
                            tr.append(
                                $("<td />", {
                                    html: v2
                                })[0].outerHTML
                            );
                        });
                    } else {
                        if (k=='qty' || k=='unitPrice' || k == 'amount' || k=='superTax' || k=='sumAmount') {
                            v =comma(v);
                            tr.append(
                                $("<td />", {
                                    html: v,
                                    style:'text-align:right'

                                })[0].outerHTML
                            );
                        } else {
                            tr.append(
                                $("<td />", {
                                    html: v
                                })[0].outerHTML
                            );
                        }

                    }
                    $("table tbody").append(tr);
                });
            });
            $('#pagination-here').show();
        } else {
            $('#pagination-here').hide();
        }
    };

    postAjax(url, data, func);

}

/** 매출마감리스트 가져오기 salesDeadlineList.html **/
function getSalesDeadlineList(str) {

    if (str != null && str != '') {
        $("#pageNumber").val(str);
    } else {
        $("#pageNumber").val(1);
    }

    // Get form
    var form = $('#searchForm')[0];

    var data = new FormData(form);

    var url = "/admin/salesDeadlineList.ajax";

    showModal('검색중입니다. 잠시만 기달려주세요.');
    var func = function (data) {
        hideModal();
        if (data.totalPageCount > 0) {
            $('#total-count').text("총건수 : " + data.totalCount);
            $('#pagination-here').bootpag({total: data.totalPageCount, page:$("#pageNumber").val()});
            $("table tbody").empty();
            var trHtml = "";
            $.each(data.list, function(key, value) {
                var idObj = value['id'];
                trHtml = "<tr>";
                trHtml += "<td>"+idObj['salesNum']+"</td>";
                trHtml += "<td>"+idObj['docNum']+"</td>";
                trHtml += "<td>"+value['accountCode']+"</td>";
                trHtml += "<td>"+value['accountName']+"</td>";
                trHtml += "<td>"+value['salesDate']+"</td>";
                trHtml += "<td>"+idObj['itemCode']+"</td>";
                trHtml += "<td>"+value['itemName']+"</td>";
                trHtml += "<td style='text-align: right;'>"+comma(value['qty'])+"</td>";
                trHtml += "<td style='text-align: right;'>"+comma(value['unitPrice'])+"</td>";
                trHtml += "<td style='text-align: right;'>"+comma(value['amount'])+"</td>";
                trHtml += "<td style='text-align: right;'>"+comma(value['superTax'])+"</td>";
                trHtml += "<td style='text-align: right;'>"+comma(value['sumAmount'])+"</td>";
                trHtml += "<td>"+value['returnGb']+"</td>";
                trHtml += "<td>"+idObj['issueNum']+"</td>";
                trHtml += "</tr>";
                $("table tbody").append(trHtml);
            });
            $('#pagination-here').show();
        } else {
            $('#total-count').text("총건수 : 0");
            $('#pagination-here').hide();
        }
    };

    postAjax(url, data, func);

} /** 매출마감리스트 가져오기 salesDeadlineList.html **/

function getCollectionList(str) {

    if (str != null && str != '') {
        $("#pageNumber").val(str);
    } else {
        $("#pageNumber").val(1);
    }

    var form = $('#searchForm')[0];

    var data = new FormData(form);

    var url = "/admin/collectionList.ajax";

    showModal('검색중입니다. 잠시만 기달려주세요.');
    var func = function (data) {
        hideModal();
        if (data.totalPageCount > 0) {
            $('#total-count').text("총건수 : " + data.totalCount);
            $('#pagination-here').bootpag({total: data.totalPageCount, page:$("#pageNumber").val()});
            $("table tbody").empty();
            $.each(data.list, function(key, value) {
                var tr = $("<tr />")
                $.each(value, function(k, v) {
                    if (k=='collectionAmount') {
                        v=comma(v);
                        tr.append(
                            $("<td />", {
                                html: v,
                                style:'text-align:right'

                            })[0].outerHTML
                        );
                    } else {
                        tr.append(
                            $("<td />", {
                                html: v
                            })[0].outerHTML
                        );
                    }
                    $("table tbody").append(tr)
                });
            });
            $('#pagination-here').show();
        } else {
            $('#total-count').text("총건수 : 0");
            $('#pagination-here').hide();
        }
    };

    postAjax(url, data, func);

}

function getDeliveryRequestList(str) {

    if (str != null && str != '') {
        $("#pageNumber").val(str);
    } else {
        $("#pageNumber").val(1);
    }

    var form = $('#searchForm')[0];

    var data = new FormData(form);

    var url = "/admin/deliveryRequestList.ajax";

    showModal('검색중입니다. 잠시만 기달려주세요.');
    var func = function (data) {
        hideModal();
        if (data.totalPageCount > 0) {
            $('#total-count').text("총건수 : " + data.totalCount);
            $('#pagination-here').bootpag({total: data.totalPageCount, page:$("#pageNumber").val()});
            $("table tbody").empty();
            $.each(data.list, function(key, value) {
                var tr = $("<tr />")
                $.each(value, function(k, v) {
                    if (typeof v == 'object') {
                        $.each(v, function(k2, v2) {
                            tr.append(
                                $("<td />", {
                                    html: v2
                                })[0].outerHTML
                            );
                        });
                    } else {

                        if (k=='requestQty' || k=='shippingQty' || k=='notShippingQty' || k=='unitPrice' || k=='price' || k=='amount') {
                            v=comma(v);
                            tr.append(
                                $("<td />", {
                                    html: v,
                                    style:'text-align:right'

                                })[0].outerHTML
                            );
                        } else {
                            tr.append(
                                $("<td />", {
                                    html: v
                                })[0].outerHTML
                            );
                        }
                    }
                    $("table tbody").append(tr);

                });
            });
            $('#pagination-here').show();
        } else {
            $('#total-count').text("총건수 : 0");
            $('#pagination-here').hide();
        }
    };

    postAjax(url, data, func);

}

/** 거래처정보리스트 가져오기 accountInfoList.html **/
function getAccountInfoList(str) {
    if (str != null && str != '') {
        $("#pageNumber").val(str);
    } else {
        $("#pageNumber").val(1);
    }

    var form = $('#searchForm')[0];

    var data = new FormData(form);

    var url = "/admin/accountInfoList.ajax";

    showModal('검색중입니다. 잠시만 기달려주세요.');;
    var func = function (data) {
        hideModal();
        if (data.totalPageCount > 0) {
            $('#total-count').text("총건수 : " + data.totalCount);
            $('#pagination-here').bootpag({total: data.totalPageCount, page:$("#pageNumber").val()});
            $("table tbody").empty();
            $.each(data.list, function(key, value) {
                var tr = $("<tr />")
                $.each(value, function(k, v) {
                    tr.append(
                        $("<td />", {
                            html: v
                        })[0].outerHTML
                    );
                    $("table tbody").append(tr)
                });
            });
            $('#pagination-here').show();
        } else {
            $('#total-count').text("총건수 : 0");
            $('#pagination-here').hide();
        }
    };

    postAjax(url, data, func);

} /** 거래처정보리스트 가져오기 accountInfoList.html **/

function getUserList(str) {
    if (str != null && str != '') {
        $("#pageNumber").val(str);
    } else {
        $("#pageNumber").val(1);
    }

    var form = $('#searchForm')[0];

    var data = new FormData(form);

    var url = "/admin/userList.ajax";

    showModal('검색중입니다. 잠시만 기달려주세요.');

    var func = function (data) {
        hideModal();
        if (data.totalPageCount > 0) {
            $('#pagination-here').bootpag({total: data.totalPageCount, page:$("#pageNumber").val()});
            $("table tbody").empty();
            $.each(data.list, function(key, value) {
                var trHtml = "";
                trHtml = "<tr>";
                trHtml +="<td class='left'>"+value['id']+"</td>";
                trHtml +="<td class='left'>"+value['lastName']+value['name']+"</td>";
                trHtml +="<td class='left'>"+value['role']+"</td>";
                trHtml += "</tr>";
                $("table tbody").append(trHtml);
            })
            $('#pagination-here').show();
        } else {
            $('#pagination-here').hide();
        }

    };

    postAjax(url, data, func);
}


function getMemberList(str) {
    if (str != null && str != '') {
        $("#pageNumber").val(str);
    } else {
        $("#pageNumber").val(1);
    }

    var form = $('#searchForm')[0];

    var data = new FormData(form);

    var url = "/admin/memberList.ajax";

    showModal('검색중입니다. 잠시만 기달려주세요.');

    var func = function (data) {
        hideModal();
        if (data.totalPageCount > 0) {
            $('#total-count').text("총건수 : " + data.totalCount);
            $('#pagination-here').bootpag({total: data.totalPageCount, page:$("#pageNumber").val()});
            $("table tbody").empty();
            $.each(data.list, function(key, value) {
                var tr = $("<tr />")
                $.each(value, function(k, v) {
                    if (typeof v == 'object') {
                        $.each(v, function(k2, v2) {
                            tr.append(
                                $("<td />", {
                                    html: v2
                                })[0].outerHTML
                            );
                        });
                    } else {
                        if (k=='posiOrders' || k=='posiCd' || k=='teamCd' || k=='teamOrders' || k=='areaCd' || k=='areaOrders') {

                        } else {
                            tr.append(
                                $("<td />", {
                                    html: v
                                })[0].outerHTML
                            );
                        }
                    }
                    $("table tbody").append(tr);
                });
            });
            $('#pagination-here').show();
        } else {
            $('#total-count').text("총건수 : 0");
            $('#pagination-here').hide();
        }
    };

    postAjax(url, data, func);

}

/** 그룹코드 리스트 가져오기 groupCodeList.html **/
function getGroupCodeList(str) {

    $("#pageNumber").val(str);
    // Get form
    var form = $('#searchForm')[0];

    var data = new FormData(form);

    var url = "/admin/groupCodeList.ajax";

    showModal('검색중입니다. 잠시만 기달려주세요.');
    var func = function (data) {
        hideModal();
        if (data.totalPageCount > 0) {
            $('#total-count').text("총건수 : " + data.totalCount);
            $('#pagination-here').bootpag({total: data.totalPageCount, page:$("#pageNumber").val()});
            $("#groupCodeBody").empty();
            var trHtml = "";
            $.each(data.list, function(key, value) {
                trHtml = "<tr>";
                var groupCodeId = "";
                var groupCodeName = "";
                var groupCodeExp = "";
                var useYn = "";
                $.each(value, function(k, v) {
                    if (k == "groupCodeId") {
                        groupCodeId = v;
                    }
                    if (k == "groupCodeName") {
                        groupCodeName = v;
                    }
                    if (k == "groupCodeExp") {
                        groupCodeExp = v;
                    }
                    if (k == "useYn") {
                        useYn = v;
                    }

                    if (k == "creaDate" || k== "creaId") {

                    } else {

                        trHtml += "<td>"+v+"</td>";
                    }
                });
                trHtml += "<td><button type='button' class='btn btn-success btn-xs' style='margin-right: 10px; margin-left: 20px;' onclick='setGroupCodeList(\""+groupCodeId+"\", \""+groupCodeName+"\", \""+groupCodeExp+"\", \""+useYn+"\")'>수정</button>"+
                              "<button type='button' class='btn btn-success btn-xs' style='margin-left: 10px;' onclick='showCodeList(\""+eval('groupCodeId')+"\", 1)'>상세</button></td>";
                trHtml += "</tr>";
                $("#groupCodeBody").append(trHtml);
            });

            $('#pagination-here').show();
        } else {
            $('#total-count').text("총건수 : 0");
            $('#pagination-here').hide();
        }
    }

    postAjax(url, data, func);

} /** 그룹코드 리스트 가져오기 groupCodeList.html **/

/** 그룹코드 생성 groupCodeList.html **/
function createGroupCode() {
    $('#warnning-alert').hide();
    $('#groupCodeId').val("");
    $('#groupCodeId').attr("readonly", false);
    $('#groupCodeName').val("");
    $('#groupCodeExp').val("");
    $('#useYn').val("Y");
    $('#createGroupCodeBtn').text("등록");
    $('#createGroupCodeModal').modal();
    $('#createGroupCodeModal').on('shown.bs.modal', function () {
        $('#groupCodeId').focus()
    });

} /** 그룹코드 생성 groupCodeList.html **/

/** 코드 생성 groupCodeList.html **/
function createCode() {
    $('#showCodeModal').modal('hide');
    $('#groupCodeId2').val($('#relayGroupCode').val());
    $('#codeId').val('');
    $('#codeId').attr("readonly", false);
    $('#codeName').val('');
    $('#sort').val('');
    $('#createCodeBtn').text("등록");
    $('#createCodeModal').modal();
    $('#createCodeModal').on('shown.bs.modal', function () {
        $('#codeId').focus()
    });
} /** 코드 생성 groupCodeList.html **/

/** 그룹코드 저장 groupCodeList.html **/
function saveGroupCode() {
    if ($('#groupCodeId').val().length == 0 || $('#groupCodeName').val().length == 0) {
        $('#warnning-alert').show();
        $("#warnning-message").text("그룹코드, 그룹명은 필수값입니다.");
        return;
    }

    // Get form
    var form = $('#createGroupCodeModalForm')[0];

    var data = new FormData(form);

    if ($('#createGroupCodeBtn').text() === '등록') {
        $.ajax({
            type: "POST",
            url: "/admin/groupCodeListSaveCheck.ajax",
            data: data,
            processData: false, //prevent jQuery from automatically transforming the data into a query string
            contentType: false,
            cache: false,
            timeout: 600000,
            success: function (data) {
                if (data.saveCheck == 1) {
                    if (confirm("등록된 데이터가 있습니다. 수정하시겠습니까?") == true){    //확인
                        saveGroupCodeByCheck();
                    }else{   //취소
                        $('#createGroupCodeModal').modal('hide');
                        return;
                    }
                } else {
                    saveGroupCodeByCheck();
                }
            },
            error: function (e) {
                console.log("ERROR : ", e);
            }
        });
    } else {
        saveGroupCodeByCheck();
    }


} /** 그룹코드 저장 groupCodeList.html **/

/** 그룹코드 등록여부 체크 후 저장 groupCodeList.html **/
function saveGroupCodeByCheck() {
    // Get form
    var form = $('#createGroupCodeModalForm')[0];

    var data = new FormData(form);

    $.ajax({
        type: "POST",
        url: "/admin/groupCodeListSave.ajax",
        data: data,
        processData: false, //prevent jQuery from automatically transforming the data into a query string
        contentType: false,
        cache: false,
        timeout: 600000,
        success: function (data) {
            $('#createGroupCodeModal').modal('hide');
            location.href='/admin/groupCodeList';
        },
        error: function (e) {
            console.log("ERROR : ", e);
        }
    });
} /** 그룹코드 등록여부 체크 후 저장 groupCodeList.html **/

/** 코드 저장 groupCodeList.html **/
function saveCode() {

    if ($('#codeId').val().length == 0 || $('#relayGroupCode').val().length == 0) {
        $('#warnning-alert').show();
        $("#warnning-message").text("그룹는 필수값입니다.");
        return;
    }

    // Get form
    var form = $('#createCodeModalForm')[0];

    var data = new FormData(form);

    if ($('#createCodeBtn').text() === '등록') {
        $.ajax({
            type: "POST",
            url: "/admin/codeListSaveCheck.ajax",
            data: data,
            processData: false, //prevent jQuery from automatically transforming the data into a query string
            contentType: false,
            cache: false,
            timeout: 600000,
            success: function (data) {
                if (data.saveCheck == 1) {
                    if (confirm("등록된 데이터가 있습니다. 수정하시겠습니까?") == true) {    //확인
                        saveCodeByCheck();
                    } else {   //취소
                        $('#createCodeModal').modal('hide');
                        return;
                    }
                } else {
                    saveCodeByCheck();
                }
            },
            error: function (e) {
                console.log("ERROR : ", e);
            }
        });
    } else {
        saveCodeByCheck();
    }
} /** 코드 저장 groupCodeList.html **/

/** 코드 등록여부 체크 후 저장 groupCodeList.html **/
function saveCodeByCheck() {
    // Get form
    var form = $('#createCodeModalForm')[0];

    var data = new FormData(form);

    $.ajax({
        type: "POST",
        url: "/admin/codeListSave.ajax",
        data: data,
        processData: false, //prevent jQuery from automatically transforming the data into a query string
        contentType: false,
        cache: false,
        timeout: 600000,
        success: function (data) {
            $('#createCodeModal').modal('hide');
            showCodeList($('#relayGroupCode').val(), $('#pageNumber2').val());
        },
        error: function (e) {
            console.log("ERROR : ", e);
        }
    });
} /** 코드 등록여부 체크 후 저장 groupCodeList.html **/

/** 그룹코드 수정 화면  groupCodeList.html **/
function setGroupCodeList(groupCodeId, groupCodeName, groupCodeExp, useYn) {
    $('#groupCodeId').val(groupCodeId);
    $('#groupCodeId').attr("readonly", true);
    $('#groupCodeName').val(groupCodeName);
    $('#groupCodeExp').val(groupCodeExp);
    $('#useYn').val(useYn);
    $('#createGroupCodeBtn').text("수정");
    $('#createGroupCodeModal').modal();
    $('#createGroupCodeModal').on('shown.bs.modal', function () {
        $('#groupCodeName').focus()
    });
} /** 그룹코드 수정 화면 groupCodeList.html **/

/** 코드 수정 화면 groupCodeList.html **/
function setCodeList(groupCodeId2, codeId, codeName, sort, useYn) {
    $('#groupCodeId2').val(groupCodeId2);
    $('#groupCodeId2').attr("readonly", true);
    $('#codeId').val(codeId);
    $('#codeId').attr("readonly", true);
    $('#codeName').val(codeName);
    $('#sort').val(sort);
    $('#useYn').val(useYn);
    $('#showCodeModal').modal('hide');
    $('#createCodeBtn').text("수정");
    $('#createCodeModal').modal();
    $('#createCodeModal').on('shown.bs.modal', function () {
        $('#codeName').focus()
    });
} /** 코드 수정 화면 groupCodeList.html **/

/** 코드 리스트 가져오기 groupCodeList.html **/
function showCodeList(groupCodeId, str) {

    $("#relayGroupCode").val(groupCodeId);

    if (str != null && str != '') {
        $("#pageNumber2").val(str);
    } else {
        $("#pageNumber2").val(1);
    }

    var data = new FormData();
    data.append("groupCodeId", groupCodeId);
    data.append("pageNumber",  $("#pageNumber2").val());

    var url = "/admin/codeList.ajax";

    $("#codeBody").empty();
    var func = function (data) {
        if (data.totalPageCount > 0) {
            $('#pagination-showCodeModal').bootpag({total: data.totalPageCount});
            var trHtml = "";
            $.each(data.list, function(key, value) {
                var idObj = value['id'];
                trHtml = "<tr>";
                trHtml +="<td>"+idObj['groupCodeId']+"</td>";
                trHtml +="<td>"+idObj['codeId']+"</td>";
                trHtml +="<td>"+value['codeName']+"</td>";
                trHtml +="<td>"+value['sort']+"</td>";
                trHtml +="<td>"+value['useYn']+"</td>";
                trHtml += "<td><button type='button' class='btn btn-success btn-xs' style='margin-right: 10px; margin-left: 20px;' onclick='setCodeList(\""+idObj['groupCodeId']+"\", \""+idObj['codeId']+"\", \""+value['codeName']+"\", \""+value['sort']+"\", \""+value['useYn']+"\")'>수정</button>";
                trHtml += "</tr>";
                $("#codeBody").append(trHtml);
            });

            $('#pagination-showCodeModal').show();
            $('#showCodeModal').modal();
        } else {
            $('#pagination-showCodeModal').hide();
            $('#showCodeModal').modal();
        }
    }

    postAjax(url, data, func);

} /** 코드 리스트 가져오기 groupCodeList.html **/

function save() {
    // Get form
    var form = $('#editForm')[0];

    var data = new FormData(form);

    $("#btnEdit").prop("disabled", true);

    $.ajax({
        type: "POST",
        url: "/admin/codeListEdit/save.ajax",
        data: data,
        processData: false, //prevent jQuery from automatically transforming the data into a query string
        contentType: false,
        cache: false,
        timeout: 600000,
        success: function (data) {
            alert("등록 하였습니다.");
            $("#btnEdit").prop("disabled", false);

        },
        error: function (e) {
            console.log("ERROR : ", e);
            $("#btnEdit").prop("disabled", false);
        }
    });

}

/** 담당자목표 리스트 가져오기 goalList.html **/
function getGoalList(str) {

    if (str != null && str != '') {
        $("#pageNumber").val(str);
    } else {
        $("#pageNumber").val(1);
    }
    // $("#pageNumber").val(str);

    if ($("#searchYear").val() == "") {
        var Now = new Date();
        $("#goalYear").val(Now.getFullYear());
        $("#searchYear").val(Now.getFullYear());
    } else {
        $("#goalYear").val($("#searchYear").val());
    }

    // Get form
    var form = $('#searchForm')[0];

    var data = new FormData(form);

    var url = "/admin/goalList.ajax";

    showModal('검색중입니다. 잠시만 기달려주세요.');

    var func = function (data) {
        hideModal();
        if (data.totalPageCount > 0) {
            $('#pagination-here').bootpag({total: data.totalPageCount, page:$("#pageNumber").val()});
            $("table tbody").empty();
            var trHtml = "";
            var i = 0;
            $.each(data.list, function(key, value) {
                var aa = "test"+i;
                trHtml += "<tr id='"+aa+"'>";
                $.each(value, function(k, v) {
                    if (k.match('teamCd')) {
                        trHtml += "<input type='hidden' class='form-control' style='width:90px;'  name='"+k+"' value='"+v+"'/>";
                    }else if (k.match('areaCd')) {
                        trHtml += "<input type='hidden' class='form-control' style='width:90px;'  name='"+k+"' value='"+v+"'/>";
                    }else if (k.match('goalDate')) {
                        trHtml += "<td class='text-center'><button type='button' class='btn btn-success btn-xs' onclick='saveGoal("+i+")' id='btn"+i+"'>저장</button></td>";
                    } else if (k.match('goal')){
                        trHtml += "<td><input type='text' class='form-control text-right' style='width:120px;'  name='"+k+"' value='"+comma(v)+"'/></td>";
                    } else if (k.match('id')) {
                        trHtml += "<input type='hidden' class='form-control' style='width:90px;'  name='"+k+"' value='"+v+"'/>";
                        trHtml += "<td>"+ v +"</td>";
                    } else {
                        trHtml += "<td>"+ v +"</td>";
                    }
                });
                trHtml += "</tr>";
                i++;

            });
        $("table tbody").append(trHtml);
        $('#pagination-here').show();
        } else {
            $('#pagination-here').hide();
        }
    };

    postAjax(url, data, func);

} /** 담당자목표 리스트 가져오기 goalList.html **/

/** 담당자목표 저장 goalList.html **/
function saveGoal(i) {
    console.log("i :::: "+i);
    console.log("goal3 >>>>>>>>>>>>> "+$("#test"+i).find("[name='goal3']").val());
    var data = new FormData();
    data.append('searchYear', $("#goalYear").val());
    data.append('id', $("#test"+i).find("[name='id']").val());
    data.append('goal1', $("#test"+i).find("[name='goal1']").val().replace(/,/g, ""));
    data.append('goal2', $("#test"+i).find("[name='goal2']").val().replace(/,/g, ""));
    data.append('goal3', $("#test"+i).find("[name='goal3']").val().replace(/,/g, ""));
    data.append('goal4', $("#test"+i).find("[name='goal4']").val().replace(/,/g, ""));
    data.append('goal5', $("#test"+i).find("[name='goal5']").val().replace(/,/g, ""));
    data.append('goal6', $("#test"+i).find("[name='goal6']").val().replace(/,/g, ""));
    data.append('goal7', $("#test"+i).find("[name='goal7']").val().replace(/,/g, ""));
    data.append('goal8', $("#test"+i).find("[name='goal8']").val().replace(/,/g, ""));
    data.append('goal9', $("#test"+i).find("[name='goal9']").val().replace(/,/g, ""));
    data.append('goal10', $("#test"+i).find("[name='goal10']").val().replace(/,/g, ""));
    data.append('goal11', $("#test"+i).find("[name='goal11']").val().replace(/,/g, ""));
    data.append('goal12', $("#test"+i).find("[name='goal12']").val().replace(/,/g, ""));

     $("#btn"+i).prop("disabled", true);

    $.ajax({
        type: "POST",
        url: "/admin/goalList/save.ajax",
        data: data,
        processData: false, //prevent jQuery from automatically transforming the data into a query string
        contentType: false,
        cache: false,
        timeout: 600000,
        success: function (data) {
            alert("등록 하였습니다.");
            $("#btn"+i).prop("disabled", false);

        },
        error: function (e) {
            console.log("ERROR : ", e);
            $("#btn"+i).prop("disabled", false);
        }
    });
} /** 담당자목표 저장 goalList.html **/

/** 영업담당자 거래처정보 등록 모달 memberAccountInfoList.html **/
function createMemberAccountInfo() {
    $('#warnning-alert').hide();
    $('#accountCode').val("");
    $('#accountCode').attr('readonly', false);
    // $('#groupCodeId').attr("readonly", false);
    $('#accountName').val("");
    $('#salesManagerCode').val("");
    $('#salesManagerName').val("");
    $('#managerStartDate').val("");
    $('#managerEndDate').val("");
    $('#createMemberAccountInfoBtn').text("등록");
    $('#deleteMemberAccountInfoBtn').hide();
    $('#createMemberAccountInfoModal').modal({backdrop: 'static', keyboard: false});
    $('#createMemberAccountInfoModal').on('shown.bs.modal', function () {
        $('#accountCode').focus()
    });

} /** 영업담당자 거래처정보 등록 모달 memberAccountInfoList.html **/

function closeModal(page) {
        $('#'+page).modal('hide');
        if (page == 'createMemberAccountInfoModal' || page == 'popupAccountInfoModal' || page == 'popupMemberListModal') {
            // getMemberAccountInfoList(1);
            location.href = '/admin/memberAccountInfoList'
        } else if (page == 'createAccountGradeInfoModal' || page == 'popupAccountInfoModal2') {
            location.href = '/admin/accountGradeList'
            // getAccountGradeList(1);
        }

}
/** 영업담당자 거래처정보 등록 memberAccountInfoList.html **/
function saveMemberAccountInfo() {
    if ($('#accountCode').val().length == 0 || $('#salesManagerCode').val().length == 0 || $('#managerStartDate').val().length == 0) {
        $('#warnning-alert').show();
        $("#warnning-message").text("거래처코드, 영업담당자코드, 담당기간년월은 필수값입니다.");
        return;
    }

    // Get form
    var form = $('#createMemberAccountInfoModalForm')[0];

    var data = new FormData(form);

    $.ajax({
        type: "POST",
        url: "/admin/memberAccountInfoListSave.ajax",
        data: data,
        processData: false, //prevent jQuery from automatically transforming the data into a query string
        contentType: false,
        cache: false,
        timeout: 600000,
        success: function (data) {
            $('#createGroupCodeModal').modal('hide');
            location.href='/admin/memberAccountInfoList';
        },
        error: function (e) {
            console.log("ERROR : ", e);
        }
    });
} /** 영업담당자 거래처정보 등록 memberAccountInfoList.html **/

/** 영업담당자 거래처정보 관리 리스트 가져오기 memberAccountInfoList.html **/
function getMemberAccountInfoList(str) {
    $("#pageNumber").val(str);
    // Get form
    var form = $('#searchForm')[0];

    var data = new FormData(form);

    var url = "/admin/memberAccountInfoList.ajax";

    showModal('검색중입니다. 잠시만 기달려주세요.');
    var func = function (data) {
        hideModal();
        $('#total-count').text("총건수 : " + data.totalCount);
        if (data.totalPageCount > 0) {
            $('#pagination-here').bootpag({total: data.totalPageCount, page:$("#pageNumber").val()});
            $("#memberAccountInfoListBody2").empty();
            var trHtml = "";
            $.each(data.list, function(key, value) {
                trHtml = "<tr style='cursor: pointer;' onclick='setMemberAccountInfo(\""+value["accountCode"]+"\", \""+value["accountName"]+"\", \""+value["salesManagerCode"]+"\", \""+value["salesManagerName"]+"\", \""+value["managerStartDate"]+"\", \""+value["managerEndDate"]+"\")'>";
                trHtml += "<td>"+value["accountCode"]+"</td>";
                trHtml += "<td>"+value["accountName"]+"</td>";
                trHtml += "<td>"+value["salesManagerCode"]+"</td>";
                trHtml += "<td>"+value["salesManagerName"]+"</td>";
                trHtml += "<td>"+value["managerStartDate"]+"</td>";
                trHtml += "<td><button type='button' class='btn btn-success btn-xs' style='margin-right: 10px; margin-left: 20px;' onclick='setMemberAccountInfo(\""+value["accountCode"]+"\", \""+value["accountName"]+"\", \""+value["salesManagerCode"]+"\", \""+value["salesManagerName"]+"\", \""+value["managerStartDate"]+"\", \""+value["managerEndDate"]+"\")'>수정</button>";
                trHtml += "</tr>";
                $("#memberAccountInfoListBody2").append(trHtml);
            });

            $('#pagination-here').show();
        } else {
            $('#total-count').text("총건수 : 0");
            $('#pagination-here').hide();
        }
    }

    postAjax(url, data, func);

} /** 영업담당자 거래처정보 관리 리스트 가져오기 memberAccountInfoList.html **/

/** 영업담당자 거래처정보 관리 수정 화면  memberAccountInfoList.html **/
function setMemberAccountInfo(accountCode, accountName, salesManagerCode, salesManagerName, managerStartDate, managerEndDate) {
    $('#accountCode').val(accountCode);
    $('#accountCode').attr('readonly', true);
    $('#accountName').val(accountName);
    $('#salesManagerCode').val(salesManagerCode);
    $('#salesManagerName').val(salesManagerName);
    $('#managerStartDate').val(managerStartDate);
    $('#managerEndDate').val(managerEndDate);
    $('#createMemberAccountInfoBtn').text("수정");
    $('#deleteMemberAccountInfoBtn').show();
    $('#createMemberAccountInfoModal').modal();
    $('#createMemberAccountInfoModal').on('shown.bs.modal', function () {
        $('#salesManagerCode').focus()
    });
} /** 영업담당자 거래처정보 관리 수정 화면  memberAccountInfoList.html **/

/** 영업담당자 거래처정보 삭제 memberAccountInfoList.html **/
function deleteMemberAccountInfo() {
    // Get form
    var form = $('#createMemberAccountInfoModalForm')[0];

    var data = new FormData(form);

    $.ajax({
        type: "POST",
        url: "/admin/memberAccountInfoListDelete.ajax",
        data: data,
        processData: false, //prevent jQuery from automatically transforming the data into a query string
        contentType: false,
        cache: false,
        timeout: 600000,
        success: function (data) {
            $('#createGroupCodeModal').modal('hide');
            location.href='/admin/memberAccountInfoList';
        },
        error: function (e) {
            console.log("ERROR : ", e);
        }
    });
} /** 영업담당자 거래처정보 삭제 memberAccountInfoList.html **/

/** 팝업 거래처정보 리스트 가져오기 memberAccountInfoList.html **/
function popupAccountInfoList(str) {
    if (str != null && str != '') {
        $("#pageNumber2").val(str);
    } else {
        $("#pageNumber2").val(1);
    }

    // Get form
    var form = $('#popupAccountInfoForm')[0];

    var data = new FormData(form);

    var url = "/admin/popupAccountInfoModal.ajax";

    showModal('검색중입니다. 잠시만 기달려주세요.');;
    var func = function (data) {
        hideModal();
        if (data.totalPageCount > 0) {
            $('#pagination-popupAccountInfoModal').bootpag({total: data.totalPageCount});
            $("#popupAccountInfoBody").empty();
            var trHtml = "";
            $.each(data.list, function(key, value) {
                trHtml = "<tr onclick='selectAccountInfo(this)'>";
                trHtml +="<td>"+value['accountCode']+"</td>";
                trHtml +="<td>"+value['accountName']+"</td>";
                trHtml +="<td>"+value['corpRegNum']+"</td>";
                trHtml +="<td>"+value['representativeName']+"</td>";
                trHtml +="<td>"+value['address']+"</td>";
                trHtml += "</tr>";
                $("#popupAccountInfoBody").append(trHtml);
            });

            $('#pagination-popupAccountInfoModal').show();
        } else {
            $('#pagination-popupAccountInfoModal').hide();
        }
    };

    postAjax(url, data, func);

} /** 팝업 거래처정보 리스트 가져오기 memberAccountInfoList.html **/

/** 팝업 거래처정보 선택 memberAccountInfoList.html **/
function selectAccountInfo(obj) {
    $('#popupAccountInfoModal').modal('hide');
    $('#popupAccountInfoModal2').modal('hide');
    $('#createMemberAccountInfoModal').modal();
    $('#accountCode').val($(obj).children('td').eq(0).html());
    $('#accountName').val($(obj).children('td').eq(1).html());
} /** 팝업 거래처정보 선택 memberAccountInfoList.html **/

/** 팝업 영업담당자 리스트 가져오기 memberAccountInfoList.html **/
function popupMemberList(str) {
    if (str != null && str != '') {
        $("#pageNumber3").val(str);
    } else {
        $("#pageNumber3").val(1);
    }

    var form = $('#popupMemberListForm')[0];

    var data = new FormData(form);

    var url = "/admin/popupMemberListModal.ajax";

    showModal('검색중입니다. 잠시만 기달려주세요.');;
    var func = function (data) {
        hideModal();
        if (data.totalPageCount > 0) {
            $('#pagination-popupAccountInfoModal').bootpag({total: data.totalPageCount});
            $("#popupMemberListBody").empty();
            var trHtml = "";
            $.each(data.list, function(key, value) {
                trHtml = "<tr onclick='selectMemberList(this)'>";
                trHtml += "<td>"+value["teamName"]+"</td>";
                trHtml += "<td>"+value["areaName"]+"</td>";
                trHtml += "<td>"+value["salesManagerName"]+"</td>";
                trHtml += "<td>"+value["salesManagerCode"]+"</td>";
                trHtml += "<td>"+value["rankName"]+"</td>";
                trHtml += "</tr>";
                $("#popupMemberListBody").append(trHtml);
            });

            $('#pagination-popupMemberListModal').show();
        } else {
            $('#pagination-popupMemberListModal').hide();
        }
    };

    postAjax(url, data, func);

} /** 팝업 영업담당자 리스트 가져오기 memberAccountInfoList.html **/

/** 팝업 영업담당자 선택 memberAccountInfoList.html **/
function selectMemberList(obj) {
    $('#popupMemberListModal').modal('hide');
    $('#createMemberAccountInfoModal').modal();
    $('#salesManagerCode').val($(obj).children('td').eq(3).html());
    $('#salesManagerName').val($(obj).children('td').eq(2).html());
} /** 팝업 거래처정보 선택 memberAccountInfoList.html **/

/** 영업담당자 등록 모달 memberList2.html **/
function createMemberList2() {
    $('#warnning-alert').hide();
    $('#createMemberList2ModalForm').each(function () {
        this.reset();
    });
    $('#salesManagerCode').attr('readonly', false);
    $('#createMemberList2Btn').text("등록");
    $('#deleteMemberList2Btn').hide();
    $('#createMemberList2Modal').modal();
    $('#createMemberList2Modal').on('shown.bs.modal', function () {
        $('#salesManagerCode').focus()
    });

} /** 영업담당자 등록 모달 memberList2.html **/

/** 영업담당자 등록 memberList2.html **/
function saveMemberList2() {
    if ($('#salesManagerCode').val().length == 0) {
        $('#warnning-alert').show();
        $("#warnning-message").text("영업담당자코드는 필수값입니다.");
        return;
    }

    // Get form
    var form = $('#createMemberList2ModalForm')[0];

    var data = new FormData(form);

    if ($('#createMemberList2Btn').text() === '등록') {
        $.ajax({
            type: "POST",
            url: "/admin/memberList2SaveCheck.ajax",
            data: data,
            processData: false, //prevent jQuery from automatically transforming the data into a query string
            contentType: false,
            cache: false,
            timeout: 600000,
            success: function (data) {
            	if (data.saveCheck == 1) {
                    if (confirm("등록된 데이터가 있습니다. 수정하시겠습니까?") == true){    //확인
                    	saveMemberList2ByCheck();
                    }else{   //취소
                        $('#createMemberList2Modal').modal('hide');
                        return;
                    }
            	} else {
            		saveMemberList2ByCheck()
            	}
            },
            error: function (e) {
                console.log("ERROR : ", e);
            }
        });
    } else {
    	saveMemberList2ByCheck();
    }

} /** 영업담당자 등록 memberList2.html **/

/** 영업담당자 등록 체크 후 저장 memberList2.html **/
function saveMemberList2ByCheck() {
    // Get form
    var form = $('#createMemberList2ModalForm')[0];

    var data = new FormData(form);

    $.ajax({
        type: "POST",
        url: "/admin/memberList2Save.ajax",
        data: data,
        processData: false, //prevent jQuery from automatically transforming the data into a query string
        contentType: false,
        cache: false,
        timeout: 600000,
        success: function (data) {
            $('#createMemberList2Modal').modal('hide');
            location.href='/admin/memberList2';
        },
        error: function (e) {
            console.log("ERROR : ", e);
        }
    });
} /** 영업담당자 등록 memberList2.html **/

/** 영업담당자 리스트 가져오기 memberList2.html **/
function getMemberList2(str) {

    $("#pageNumber").val(str);
    // Get form
    var form = $('#searchForm')[0];

    var data = new FormData(form);

    var url = "/admin/memberList2.ajax";

    showModal('검색중입니다. 잠시만 기달려주세요.');
    var func = function (data) {
        hideModal();
        if (data.totalPageCount > 0) {
            $('#total-count').text("총건수 : " + data.totalCount);
            $('#pagination-here').show();
            $('#pagination-here').bootpag({total: data.totalPageCount, page:$("#pageNumber").val()});
            $("#memberAccountInfoListBody").empty();
            var trHtml = "";
            $.each(data.list, function(key, value) {
                trHtml = "<tr style='cursor: pointer;' onclick='setMemberList2(\""+value["salesManagerCode"]+"\", \""+value["salesManagerName"]+"\", \""+value["teamCode"]+"\", \""+value["areaCode"]+"\", \""+value["rankCode"]+"\", \""+value["phoneNumber"]+"\", \""+value["cellPhoneNumber"]+"\", \""+value["zipCode"]+"\", \""+value["address"]+"\", \""+value["detailAddress"]+"\", \""+value["remarks"]+"\")'>";

                if (value["teamName"]=="입력무") {
                    trHtml += "<td class='foo'>"+value["teamName"]+"</td>";
                } else {
                    trHtml += "<td>"+value["teamName"]+"</td>";
                }
                if (value["areaName"]=="입력무") {
                    trHtml += "<td class='foo'>"+value["areaName"]+"</td>";
                } else {
                    trHtml += "<td>"+value["areaName"]+"</td>";
                }
                trHtml += "<td>"+value["salesManagerName"]+"</td>";
                trHtml += "<td>"+value["salesManagerCode"]+"</td>";
                if (value["rankName"]=="입력무") {
                    trHtml += "<td class='foo'>"+value["rankName"]+"</td>";
                } else {
                    trHtml += "<td>"+value["rankName"]+"</td>";
                }
                trHtml += "<td>"+value["phoneNumber"]+"</td>";
                trHtml += "<td>"+value["cellPhoneNumber"]+"</td>";
                trHtml += "<td>"+value["zipCode"]+"</td>";
                trHtml += "<td>"+value["address"]+"</td>";
                trHtml += "<td>"+value["detailAddress"]+"</td>";
                trHtml += "<td>"+value["remarks"]+"</td>";
                // trHtml += "<td>"+value["creaId"]+"</td>";
                // trHtml += "<td>"+value["creaDate"]+"</td>";
                trHtml += "<td><button type='button' class='btn btn-success btn-xs' style='margin-right: 10px; margin-left: 20px;' onclick='setMemberList2(\""+value["salesManagerCode"]+"\", \""+value["salesManagerName"]+"\", \""+value["teamCode"]+"\", \""+value["areaCode"]+"\", \""+value["rankCode"]+"\", \""+value["phoneNumber"]+"\", \""+value["cellPhoneNumber"]+"\", \""+value["zipCode"]+"\", \""+value["address"]+"\", \""+value["detailAddress"]+"\", \""+value["remarks"]+"\")'>수정</button>";
                trHtml += "</tr>";

                $("#memberAccountInfoListBody").append(trHtml);
            });
        } else {
            $('#total-count').text("총건수 : 0");
            $('#pagination-here').hide();
        }
    }

    postAjax(url, data, func);

} /** 영업담당자 리스트 가져오기 memberList2.html **/

/** 영업담당자 수정 화면  memberList2.html **/
function setMemberList2(salesManagerCode, salesManagerName, teamCode, areaCode, rankCode, phoneNumber, cellPhoneNumber, zipCode, address, detailAddress, remarks) {
    $('#salesManagerCode').val(salesManagerCode);
    $('#salesManagerCode').attr('readonly', true);
    $('#salesManagerName').val(salesManagerName);
    $('#teamCode').val(teamCode);
    $('#areaCode').val(areaCode);
    $('#rankCode').val(rankCode);
    $('#phoneNumber').val(phoneNumber);
    $('#cellPhoneNumber').val(cellPhoneNumber);
    $('#zipCode').val(zipCode);
    $('#address').val(address);
    $('#detailAddress').val(detailAddress);
    $('#remarks').val(remarks);
    $('#createMemberList2Btn').text("수정");
    $('#deleteMemberList2Btn').show();
    $('#createMemberList2Modal').modal();
    $('#createMemberList2Modal').on('shown.bs.modal', function () {
        $('#salesManagerName').focus()
    });
} /** 영업담당자 수정 화면  memberList2.html **/

function deleteMemberList2() {
    // Get form
    var form = $('#createMemberList2ModalForm')[0];

    var data = new FormData(form);

    $.ajax({
        type: "POST",
        url: "/admin/memberList2Delete.ajax",
        data: data,
        processData: false, //prevent jQuery from automatically transforming the data into a query string
        contentType: false,
        cache: false,
        timeout: 600000,
        success: function (data) {
            $('#createMemberList2Modal').modal('hide');
            location.href='/admin/memberList2';
        },
        error: function (e) {
            console.log("ERROR : ", e);
        }
    });
}

/** 엑셀업로드확인 가져오기 uploadList.html **/
function getUploadList(str) {

    if (str != null && str != '') {
        $("#pageNumber").val(str);
    } else {

    }

    // Get form
    var form = $('#searchForm')[0];

    var data = new FormData(form);

    var url = "/admin/uploadList.ajax";

    showModal('검색중입니다. 잠시만 기달려주세요.');

    var func = function (data) {
        hideModal();
        if (data.totalPageCount > 0) {
            $('#total-count').text("총건수 : " + data.totalCount);
            $('#pagination-here').bootpag({total: data.totalPageCount, page:$("#pageNumber").val()});
            $("table tbody").empty();

            var newURL = window.location.protocol + "//" + window.location.host;

            var trHtml = "";
            $.each(data.list, function(key, value) {
                var fileSize = "";
                if (value['fileSize'] < 1048576) {
                    var i = value['fileSize'] / 1024
                    fileSize = i.toFixed(2) + "KB";
                } else if (value['fileSize'] > 1048576) {
                    var i = value['fileSize'] / 1024 / 1024
                    fileSize = i.toFixed(2) + "MB";
                }
                trHtml += "<tr>";
                trHtml += "<td>"+value['idx']+'</td>';
                trHtml += "<td><a href='" + newURL + value['linkPath'] + "' download='"+value['originalFileName']+"'>" + value['originalFileName'] + "</a></td>";
                trHtml += "<td>"+fileSize+"</td>";
                trHtml += "<td>"+value['creaDtm']+'</td>';
                trHtml += "<td>"+value['creaId']+'</td>';
                trHtml += "<td>"+value['delGb']+'</td>';
                trHtml += "<td>"+value['insertStartTime']+'</td>';
                trHtml += "<td>"+value['insertFinishTime']+'</td>';
                trHtml += "<td>"+value['insertTable']+'</td>';
                trHtml += "<td>"+value['insertGb']+'</td>';

                trHtml += "</tr>";
            });
            $("table tbody").append(trHtml);
            $('#pagination-here').show();
        } else {
            $("table tbody").empty();
            $('#total-count').text("총건수 : 0");
            $('#pagination-here').hide();
        }
    };

    postAjax(url, data, func);

} /** 엑셀업로드확인 가져오기 uploadList.html **/

/** 담당자별 종합실적(callnote) 가져오기  cpList.html **/
function getCpList(str) {
    if ($('#searchYearMonth').val() == '') {
        var Now = new Date();
        var monthOfYear = Now.getMonth()
        Now.setMonth(monthOfYear - 1)
        $('#searchYearMonth').val(getDateStrYearMonth(Now));
    } else {
    }

    if (str != null && str != '') {
        $("#pageNumber").val(str);
    } else {
        $("#pageNumber").val(1);
    }

    var form = $('#searchForm')[0];

    var data = new FormData(form);

    var url = "/cpList.ajax";

    showModal('검색중입니다. 잠시만 기달려주세요.');
    var func = function (data) {
        hideModal();
        if (data.list.length > 0) {
            $("table tbody").empty();
            var trHtml = "";
            $.each(data.list, function(key, value) {
                trHtml += "<tr>";
                if (value['num'] == 9999) {
                    if (value['teamOrders'] == 7777) {
                        trHtml += "<td colspan='3' class='foo'> 전제합계 </td>";
                        trHtml += "<td class='foo right'>" + comma(value['goal']) + "</td>";
                        trHtml += "<td class='foo right'>" + comma(value['orderAmount']) + "</td>";
                        trHtml += "<td class='foo right'>" + comma(value['deliveryRequestAmount']) + "</td>";
                        trHtml += "<td class='foo right'>" + comma(value['sealesDeadlineAmount']) + "</td>";
                        trHtml += "<td class='foo right'>" + value['goalRate'] + "%</td>";
                        trHtml += "<td class='foo right'>" + comma(value['remainingGoal']) + "</td>";
                        trHtml += "<td class='foo right'>" + "목표" + "</td>";
                        trHtml += "<td class='foo right'>" + comma(value['collectionAmount']) + "</td>";
                        trHtml += "<td class='foo right'>" + "달성율" + "</td>";
                        trHtml += "<td class='foo right'>" + "처수" + "</td>";
                        trHtml += "<td class='foo right'>" + "매출" + "</td>";
                        trHtml += "<td class='foo right'>" + "처수" + "</td>";
                        trHtml += "<td class='foo right'>" + "매출" + "</td>";
                    } else {
                        trHtml += "<td>" + value['teamNm'] + "</td>";
                        trHtml += "<td colspan='2' class='foo'>소계</td>";
                        trHtml += "<td class='foo right'>" + comma(value['goal']) + "</td>";
                        trHtml += "<td class='foo right'>" + comma(value['orderAmount']) + "</td>";
                        trHtml += "<td class='foo right'>" + comma(value['deliveryRequestAmount']) + "</td>";
                        trHtml += "<td class='foo right'>" + comma(value['sealesDeadlineAmount']) + "</td>";
                        trHtml += "<td class='foo right'>" + value['goalRate'] + "%</td>";
                        trHtml += "<td class='foo right'>" + comma(value['remainingGoal']) + "</td>";
                        trHtml += "<td class='foo right'>" + "목표" + "</td>";
                        trHtml += "<td class='foo right'>" + comma(value['collectionAmount']) + "</td>";
                        trHtml += "<td class='foo right'>" + "달성율" + "</td>";
                        trHtml += "<td class='foo right'>" + "처수" + "</td>";
                        trHtml += "<td class='foo right'>" + "매출" + "</td>";
                        trHtml += "<td class='foo right'>" + "처수" + "</td>";
                        trHtml += "<td class='foo right'>" + "매출" + "</td>";
                    }
                } else {
                    trHtml += "<td>" + value['teamNm'] + "</td>";
                    trHtml += "<td>" + value['areaNm'] + "</td>";
                    trHtml += "<td>" + value['name'] + "</td>";
                    trHtml += "<td class='right'>" + comma(value['goal']) + "</td>";
                    trHtml += "<td class='right'>" + comma(value['orderAmount']) + "</td>";
                    trHtml += "<td class='right'>" + comma(value['deliveryRequestAmount']) + "</td>";
                    trHtml += "<td class='right'>" +comma( value['sealesDeadlineAmount']) + "</td>";
                    trHtml += "<td class='right'>" + value['goalRate'] + "%</td>";
                    trHtml += "<td class='right'>" + comma(value['remainingGoal']) + "</td>";
                    trHtml += "<td class='right'>" + "목표" + "</td>";
                    trHtml += "<td class='right'>" + comma(value['collectionAmount']) + "</td>";
                    trHtml += "<td class='right'>" + "달성율" + "</td>";
                    trHtml += "<td class='right'>" + "처수" + "</td>";
                    trHtml += "<td class='right'>" + "매출" + "</td>";
                    trHtml += "<td class='right'>" + "처수" + "</td>";
                    trHtml += "<td class='right'>" + "매출" + "</td>";
                }
                trHtml += "</tr>";
            });
            $("table tbody").append(trHtml);
        } else {
            $('#pagination-here').hide();
        }

    };

    postAjax(url, data, func);
} /** 담당자별 종합실적(callnote) 가져오기  cpList.html **/

/** 담당자별 종합실적(sfa) 가져오기  cpList2.html **/
function getCpList2(str) {
    if ($('#searchYearMonth').val() == '') {
        var Now = new Date();
        var monthOfYear = Now.getMonth()
        Now.setMonth(monthOfYear - 1)
        $('#searchYearMonth').val(getDateStrYearMonth(Now));
    } else {
    }

    if (str != null && str != '') {
        $("#pageNumber").val(str);
    } else {
        $("#pageNumber").val(1);
    }

    // Get form
    var form = $('#searchForm')[0];

    var data = new FormData(form);

    var url = "/cpList2.ajax";

    showModal('검색중입니다. 잠시만 기달려주세요.');
    var func = function (data) {
        hideModal();
        if (data.list.length > 0) {
            $('#total-count').text("영업담당자 수 : " + data.totalCount);
            $("table tbody").empty();
            var trHtml = "";
            $.each(data.list, function(key, value) {
                trHtml += "<tr>";
                if (value['num'] == 9999) {
                    if (value['teamOrders'] == 7777) {
                        trHtml += "<td colspan='3' class='foo'> 전제합계 </td>";
                        trHtml += "<td class='foo right'>" + comma(value['goal']) + "</td>";
                        trHtml += "<td class='foo right'>" + comma(value['orderAmount']) + "</td>";
                        trHtml += "<td class='foo right'>" + comma(value['deliveryRequestAmount']) + "</td>";
                        trHtml += "<td class='foo right'>" + comma(value['sealesDeadlineAmount']) + "</td>";
                        trHtml += "<td class='foo right'>" + value['goalRate'] + "%</td>";
                        trHtml += "<td class='foo right'>" + comma(value['remainingGoal']) + "</td>";
                        trHtml += "<td class='foo right'>" + comma(value['collectionAmountGoal']) + "</td>";
                        trHtml += "<td class='foo right'>" + comma(value['collectionAmount']) + "</td>";
                        trHtml += "<td class='foo right'>" + value['collectionAmountRate'] + "%</td>";
                        trHtml += "<td class='foo right'>" + "처수" + "</td>";
                        trHtml += "<td class='foo right'>" + "매출" + "</td>";
                        trHtml += "<td class='foo right'>" + comma(value['godomallCnt']) + "</td>";
                        trHtml += "<td class='foo right'>" + comma(value['godomallAmount']) + "</td>";
                    }
                }
                trHtml += "</tr>";
            });
            $.each(data.list, function(key, value) {
                trHtml += "<tr>";
                if (value['num'] == 9999) {
                    if (value['teamOrders'] == 7777) {
                    } else {
                        trHtml += "<td>" + value['teamNm'] + "</td>";
                        trHtml += "<td colspan='2' class='foo'>소계</td>";
                        trHtml += "<td class='foo right'>" + comma(value['goal']) + "</td>";
                        trHtml += "<td class='foo right'>" + comma(value['orderAmount']) + "</td>";
                        trHtml += "<td class='foo right'>" + comma(value['deliveryRequestAmount']) + "</td>";
                        trHtml += "<td class='foo right'>" + comma(value['sealesDeadlineAmount']) + "</td>";
                        trHtml += "<td class='foo right'>" + value['goalRate'] + "%</td>";
                        trHtml += "<td class='foo right'>" + comma(value['remainingGoal']) + "</td>";
                        trHtml += "<td class='foo right'>" + comma(value['collectionAmountGoal']) + "</td>";
                        trHtml += "<td class='foo right'>" + comma(value['collectionAmount']) + "</td>";
                        trHtml += "<td class='foo right'>" + value['collectionAmountRate'] + "%</td>";
                        trHtml += "<td class='foo right'>" + "처수" + "</td>";
                        trHtml += "<td class='foo right'>" + "매출" + "</td>";
                        trHtml += "<td class='foo right'>" + comma(value['godomallCnt']) + "</td>";
                        trHtml += "<td class='foo right'>" + comma(value['godomallAmount']) + "</td>";
                    }
                } else {
                    trHtml += "<td>" + value['teamNm'] + "</td>";
                    trHtml += "<td>" + value['areaNm'] + "</td>";
                    trHtml += "<td>" + value['name'] + "</td>";
                    trHtml += "<td class='right'>" + comma(value['goal']) + "</td>";
                    trHtml += "<td class='right'>" + comma(value['orderAmount']) + "</td>";
                    trHtml += "<td class='right'>" + comma(value['deliveryRequestAmount']) + "</td>";
                    trHtml += "<td class='right'>" +comma( value['sealesDeadlineAmount']) + "</td>";
                    trHtml += "<td class='right'>" + value['goalRate'] + "%</td>";
                    trHtml += "<td class='right'>" + comma(value['remainingGoal']) + "</td>";
                    trHtml += "<td class='right'>" + comma(value['collectionAmountGoal']) + "</td>";
                    trHtml += "<td class='right'>" + comma(value['collectionAmount']) + "</td>";
                    trHtml += "<td class='right'>" + value['collectionAmountRate'] + "%</td>";
                    trHtml += "<td class='right'>" + "처수" + "</td>";
                    trHtml += "<td class='right'>" + "매출" + "</td>";
                    trHtml += "<td class='right'>" + comma(value['godomallCnt']) + "</td>";
                    trHtml += "<td class='right'>" + comma(value['godomallAmount']) + "</td>";
                }
                trHtml += "</tr>";
            });
            $("table tbody").append(trHtml);
        } else {
            $('#pagination-here').hide();
        }

    };

    postAjax(url, data, func);
} /** 담당자별 종합실적(sfa) 가져오기  cpList2.html **/

/** 행정구역 정보 관리 리스트 가져오기 adminDistrictList.html **/
function getAdminDistrictList(str) {

    $("#pageNumber").val(str);
    // Get form
    var form = $('#searchForm')[0];

    var data = new FormData(form);

    var url = "/admin/adminDistrictList.ajax";

    showModal('검색중입니다. 잠시만 기달려주세요.');
    var func = function (data) {
        hideModal();
        if (data.totalPageCount > 0) {
            $('#total-count').text("총건수 : " + data.totalCount);
            $('#pagination-here').bootpag({total: data.totalPageCount, page:$("#pageNumber").val()});
            $("#adminDistrictListBody").empty();
            var trHtml = "";
            $.each(data.list, function(key, value) {
                trHtml = "<tr>";
                trHtml += "<td>"+value["administrativeDistrictCode"]+"</td>";
                trHtml += "<td>"+value["administrativeDistrictName"]+"</td>";
                trHtml += "<td>"+value["pharmacyNumber"]+"</td>";
                trHtml += "<td>"+value["dealYn"]+"</td>";
                trHtml += "<td>"+value["useYn"]+"</td>";
                trHtml += "</tr>";
                $("#adminDistrictListBody").append(trHtml);
            });

            $('#pagination-here').show();
        } else {
            $('#total-count').text("총건수 : 0");
            $('#pagination-here').hide();
        }
    }

    postAjax(url, data, func);

} /** 행정구역 정보 관리 리스트 가져오기 adminDistrictList.html **/

/** 행정구역 거래처정보 관리 리스트 가져오기 zipAccountList.html **/
function getZipAccountList(str) {
    $("#pageNumber").val(str);
    // Get form
    var form = $('#searchForm')[0];

    var data = new FormData(form);

    var url = "/admin/zipAccountList.ajax";

    showModal('검색중입니다. 잠시만 기달려주세요.');
    var func = function (data) {
        hideModal();
        if (data.totalPageCount > 0) {
            $('#total-count').text("총건수 : " + data.totalCount);
            $('#pagination-here').bootpag({total: data.totalPageCount, page:$("#pageNumber").val()});
            $("#adminDistrictAccountListBody").empty();
            var trHtml = "";
            $.each(data.list, function(key, value) {
                trHtml = "<tr>";
                trHtml += "<td>"+value["administrativeDistrictCode"]+"</td>";
                trHtml += "<td>"+value["administrativeDistrictName"]+"</td>";
                trHtml += "<td>"+value["zipCode"]+"</td>";
                trHtml += "<td>"+value["address"]+"</td>";
                trHtml += "<td>"+value["detailAddress"]+"</td>";
                trHtml += "<td>"+value["accountCode"]+"</td>";
                trHtml += "<td>"+value["accountName"]+"</td>";
                trHtml += "</tr>";
                $("#adminDistrictAccountListBody").append(trHtml);
            });

            $('#pagination-here').show();
        } else {
            $('#total-count').text("총건수 : 0");
            $('#pagination-here').hide();
        }
    }

    postAjax(url, data, func);

} /** 행정구역 거래처정보 관리 리스트 가져오기 zipAccountList.html **/

/** 거래처별 등급 리스트 가져오기 accountGradeList.html **/
function getAccountGradeList(str) {
    $("#pageNumber").val(str);
    // Get form
    var form = $('#searchForm')[0];

    var data = new FormData(form);

    var url = "/admin/getAccountGradeList.ajax";

    showModal('검색중입니다. 잠시만 기달려주세요.');
    var func = function (data) {
        hideModal();
        if (data.totalPageCount > 0) {
            $('#total-count').text("총건수 : " + data.totalCount);
            $('#pagination-here').bootpag({total: data.totalPageCount, page:$("#pageNumber").val()});
            $("#listBody").empty();
            var trHtml = "";
            $.each(data.list, function(key, value) {
                trHtml = "<tr style='cursor: pointer;' onclick='setAccountGradeInfo(\""+value["accountCode"]+"\", \""+value["accountName"]+"\", \""+value["gradeCode"]+"\", \""+value["godomollYn"]+"\")'>";
                trHtml += "<td>"+value["accountCode"]+"</td>";
                trHtml += "<td>"+value["accountName"]+"</td>";
                trHtml += "<td>"+value["gradeCode"]+"</td>";
                trHtml += "<td>"+value["gradeName"]+"</td>";
                trHtml += "<td>"+value["godomollYn"]+"</td>";
                trHtml += "<td><button type='button' class='btn btn-success btn-xs' style='margin-right: 10px; margin-left: 20px;' onclick='setAccountGradeInfo(\""+value["accountCode"]+"\", \""+value["accountName"]+"\", \""+value["gradeCode"]+"\", \""+value["godomollYn"]+"\")'>수정</button>";
                trHtml += "</tr>";
                $("#listBody").append(trHtml);
            });

            $('#pagination-here').show();
        } else {
            $('#total-count').text("총건수 : 0");
            $('#pagination-here').hide();
        }
    }

    postAjax(url, data, func);

} /** 거래처별 등급 리스트 가져오기 accountGradeList.html **/

/** 거래처별 등급 등록 모달 accountGradeList.html **/
function createAccountGradeInfo() {
    $('#warnning-alert').hide();
    $('#accountCode').val("");
    $('#accountCode').attr('readonly', false);
    $('#accountName').val("");
    $('#accountGrade').val("");
    $('#godomollYn').val("");
    $('#createAccountGradeInfoBtn').text("등록");
    $('#deleteAccountGradeInfoBtn').hide();
    $('#createAccountGradeInfoModal').modal({backdrop: 'static', keyboard: false});
    $('#createAccountGradeInfoModal').on('shown.bs.modal', function () {
        $('#accountCode').focus()
    });

} /** 거래처별 등급 등록 모달 accountGradeList.html **/

/** 거래처별 등급 수정 모달 accountGradeList.html **/
function setAccountGradeInfo(accountCode, accountName, gradeCode, godomollYn) {
    $('#accountCode').val(accountCode);
    $('#accountCode').attr('readonly', true);
    $('#accountName').val(accountName);
    $('#gradeCode').val(gradeCode);
    $('#godomollYn').val(godomollYn);
    $('#createAccountGradeInfoBtn').text("수정");
    $('#deleteAccountGradeInfoBtn').show();
    $('#createAccountGradeInfoModal').modal({backdrop: 'static', keyboard: false});
    $('#createAccountGradeInfoModal').on('shown.bs.modal', function () {
        $('#gradeCode').focus()
    });
} /** 거래처별 등급 수정 모달 accountGradeList.html **/


/** 거래처별 등급 삭제 accountGradeList.html **/
function deleteAccountGradeInfo() {
    // Get form
    var form = $('#createAccountGradeInfoModalForm')[0];

    var data = new FormData(form);

    $.ajax({
        type: "POST",
        url: "/admin/accountGradeInfoDelete.ajax",
        data: data,
        processData: false, //prevent jQuery from automatically transforming the data into a query string
        contentType: false,
        cache: false,
        timeout: 600000,
        success: function (data) {
            $('#createAccountGradeInfoModal').modal('hide');
            location.href='/admin/accountGradeList';
        },
        error: function (e) {
            console.log("ERROR : ", e);
        }
    });
} /** 거래처별 등급 삭제 accountGradeList.html **/

/** 거래처별 등급 등록 accountGradeList.html **/
function saveAccountGradeInfo() {
    if ($('#accountCode').val().length == 0 || $('#gradeCode').val().length == 0) {
        $('#warnning-alert').show();
        $("#warnning-message").text("거래처코드, 등급은 필수값입니다.");
        return;
    }

    // Get form
    var form = $('#createAccountGradeInfoModalForm')[0];

    var data = new FormData(form);

    if ($('#createAccountGradeInfoBtn').text() === '등록') {
        $.ajax({
            type: "POST",
            url: "/admin/accountGradeInfoSaveCheck.ajax",
            data: data,
            processData: false, //prevent jQuery from automatically transforming the data into a query string
            contentType: false,
            cache: false,
            timeout: 600000,
            success: function (data) {
            	if (data.saveCheck == 1) {
                    if (confirm("등록된 데이터가 있습니다. 수정하시겠습니까?") == true){    //확인
                    	saveAccountGradeInfoByCheck();
                    }else{   //취소
                        $('#createAccountGradeInfoModal').modal('hide');
                        location.href='/admin/accountGradeList';
                    }
            	} else {
            		saveAccountGradeInfoByCheck();
            	}
            },
            error: function (e) {
                console.log("ERROR : ", e);
            }
        });
    } else {
    	saveAccountGradeInfoByCheck();
    }

} /** 거래처별 등급 등록 accountGradeList.html **/

/** 거래처별 등급 등록 체크 후 저장 accountGradeList.html **/
function saveAccountGradeInfoByCheck() {
    // Get form
    var form = $('#createAccountGradeInfoModalForm')[0];

    var data = new FormData(form);

    $.ajax({
        type: "POST",
        url: "/admin/accountGradeInfoSave.ajax",
        data: data,
        processData: false, //prevent jQuery from automatically transforming the data into a query string
        contentType: false,
        cache: false,
        timeout: 600000,
        success: function (data) {
            $('#createAccountGradeInfoModal').modal('hide');
            location.href='/admin/accountGradeList';
        },
        error: function (e) {
            console.log("ERROR : ", e);
        }
    });
} /** 거래처별 등급 등록 체크 후 저장 accountGradeList.html **/

/** 등급별 거래처 현황 가져오기  accountStatusList.html **/
function getAccountStatusList(str) {
    if ($('#searchYearMonth').val() == '') {
        var Now = new Date();
        var monthOfYear = Now.getMonth()
        Now.setMonth(monthOfYear - 1)
        $('#searchYearMonth').val(getDateStrYearMonth(Now));
    } else {
    }

    if (str != null && str != '') {
        $("#pageNumber").val(str);
    } else {
        $("#pageNumber").val(1);
    }

    var form = $('#searchForm')[0];

    var data = new FormData(form);

    var url = "/getAccountStatusList.ajax";

    showModal('검색중입니다. 잠시만 기달려주세요.');
    var func = function (data) {
        hideModal();
        if (data.list.length > 0) {
            $("table tbody").empty();
            $('#total-count').text("영업담당자 수 : " + data.totalCount);
            var trHtml = "";
            $.each(data.list, function(key, value) {
                trHtml += "<tr>";
                if (value['num'] == 9999) {
                    if (value['teamOrders'] == 7777) {
                        trHtml += "<td colspan='3' class='foo'> 전제합계 </td>";
                        trHtml += "<td class='right foo'>" + comma(value['totalCnt']) + "</td>";
                        trHtml += "<td class='right foo'>" + comma(value['totalTotal']) + "</td>";
                        trHtml += "<td class='right foo'>" + comma(value['aCnt']) + "</td>";
                        trHtml += "<td class='right foo'>" + comma(value['aTotal']) + "</td>";
                        trHtml += "<td class='right foo'>" + comma(value['bCnt']) + "</td>";
                        trHtml += "<td class='right foo'>" + comma(value['bTotal']) + "</td>";
                        trHtml += "<td class='right foo'>" + comma(value['cCnt']) + "</td>";
                        trHtml += "<td class='right foo'>" + comma(value['cTotal']) + "</td>";
                        trHtml += "<td class='right foo'>" + comma(value['dCnt']) + "</td>";
                        trHtml += "<td class='right foo'>" + comma(value['dTotal']) + "</td>";
                        trHtml += "<td class='right foo'>" + comma(value['eCnt']) + "</td>";
                        trHtml += "<td class='right foo'>" + comma(value['eTotal']) + "</td>";
                    }
                }
                trHtml += "</tr>";
            });
            $.each(data.list, function(key, value) {
                trHtml += "<tr>";
                if (value['num'] == 9999) {
                    if (value['teamOrders'] == 7777) {
                    } else {
                        trHtml += "<td>" + value['teamNm'] + "</td>";
                        trHtml += "<td colspan='2' class='foo'>소계</td>";
                        trHtml += "<td class='right foo'>" + comma(value['totalCnt']) + "</td>";
                        trHtml += "<td class='right foo'>" + comma(value['totalTotal']) + "</td>";
                        trHtml += "<td class='right foo'>" + comma(value['aCnt']) + "</td>";
                        trHtml += "<td class='right foo'>" + comma(value['aTotal']) + "</td>";
                        trHtml += "<td class='right foo'>" + comma(value['bCnt']) + "</td>";
                        trHtml += "<td class='right foo'>" + comma(value['bTotal']) + "</td>";
                        trHtml += "<td class='right foo'>" + comma(value['cCnt']) + "</td>";
                        trHtml += "<td class='right foo'>" + comma(value['cTotal']) + "</td>";
                        trHtml += "<td class='right foo'>" + comma(value['dCnt']) + "</td>";
                        trHtml += "<td class='right foo'>" + comma(value['dTotal']) + "</td>";
                        trHtml += "<td class='right foo'>" + comma(value['eCnt']) + "</td>";
                        trHtml += "<td class='right foo'>" + comma(value['eTotal']) + "</td>";
                    }
                } else {
                    trHtml += "<td>" + value['teamNm'] + "</td>";
                    trHtml += "<td>" + value['areaNm'] + "</td>";
                    trHtml += "<td>" + value['name'] + "</td>";
                    trHtml += "<td class='right'>" + comma(value['totalCnt']) + "</td>";
                    trHtml += "<td class='right'>" + comma(value['totalTotal']) + "</td>";
                    trHtml += "<td class='right'>" + comma(value['aCnt']) + "</td>";
                    trHtml += "<td class='right'>" + comma(value['aTotal']) + "</td>";
                    trHtml += "<td class='right'>" + comma(value['bCnt']) + "</td>";
                    trHtml += "<td class='right'>" + comma(value['bTotal']) + "</td>";
                    trHtml += "<td class='right'>" + comma(value['cCnt']) + "</td>";
                    trHtml += "<td class='right'>" + comma(value['cTotal']) + "</td>";
                    trHtml += "<td class='right'>" + comma(value['dCnt']) + "</td>";
                    trHtml += "<td class='right'>" + comma(value['dTotal']) + "</td>";
                    trHtml += "<td class='right'>" + comma(value['eCnt']) + "</td>";
                    trHtml += "<td class='right'>" + comma(value['eTotal']) + "</td>";
                }
                trHtml += "</tr>";
            });
            $("table tbody").append(trHtml);
        } else {
            $('#pagination-here').hide();
        }

    };

    postAjax(url, data, func);
} /** 등급별 거래처 현황 가져오기  accountStatusList.html **/

/** 담당별/품목별 매출 현황 가져오기  itemSalesStatusList.html **/
function getItemSalesStatusList(str) {
    if ($('#searchYearMonth').val() == '') {
        var Now = new Date();
        var monthOfYear = Now.getMonth()
        Now.setMonth(monthOfYear - 1)
        $('#searchYearMonth').val(getDateStrYearMonth(Now));
    } else {
    }

    if (str != null && str != '') {
        $("#pageNumber").val(str);
    } else {
        $("#pageNumber").val(1);
    }

    var form = $('#searchForm')[0];

    var data = new FormData(form);

    var url = "/getItemSalesStatusList.ajax";

    showModal('검색중입니다. 잠시만 기달려주세요.');
    var func = function (data) {
        hideModal();
        if (data.list.length > 0) {
            $("table tbody").empty();
            $('#total-count').text("영업담당자 수 : " + data.totalCount);
            var trHtml = "";
            $.each(data.list, function(key, value) {
                trHtml += "<tr>";
                if (value['num'] == 9999) {
                    if (value['teamOrders'] == 7777) {
                        trHtml += "<td colspan='3' class='foo'> 전제합계 </td>";
                        trHtml += "<td class='right foo'>" + comma(value['totalGold']) + "</td>";
                        trHtml += "<td class='right foo'>" + comma(value['totalJJJ']) + "</td>";
                        trHtml += "<td class='right foo'>" + comma(value['totalKKK']) + "</td>";
                        trHtml += "<td class='right foo'>" + comma(value['totalLLL']) + "</td>";
                        trHtml += "<td class='right foo'>" + comma(value['totalBaby']) + "</td>";
                        trHtml += "<td class='right foo'>" + comma(value['totalMMM']) + "</td>";
                        trHtml += "<td class='right foo'>" + comma(value['totalNNN']) + "</td>";
                        trHtml += "<td class='right foo'>" + comma(value['totalOOO']) + "</td>";
                        trHtml += "<td class='right foo'>" + comma(value['totalCare']) + "</td>";
                        trHtml += "<td class='right foo'>" + comma(value['totalPPP']) + "</td>";
                        trHtml += "<td class='right foo'>" + comma(value['totalQQQ']) + "</td>";
                        trHtml += "<td class='right foo'>" + comma(value['totalAtp']) + "</td>";
                        trHtml += "<td class='right foo'>" + comma(value['totalIII']) + "</td>";
                        trHtml += "<td class='right foo'>" + comma(value['totalHHH']) + "</td>";
                        trHtml += "<td class='right foo'>" + comma(value['totalIbs']) + "</td>";
                        trHtml += "<td class='right foo'>" + comma(value['totalGGG']) + "</td>";
                        trHtml += "<td class='right foo'>" + comma(value['totalFFF']) + "</td>";
                        trHtml += "<td class='right foo'>" + comma(value['totalEEE']) + "</td>";
                        trHtml += "<td class='right foo'>" + comma(value['totalBBB']) + "</td>";
                        trHtml += "<td class='right foo'>" + comma(value['totalCCC']) + "</td>";
                        trHtml += "<td class='right foo'>" + comma(value['totalDDD']) + "</td>";
                    }
                }
                trHtml += "</tr>";
            });
            $.each(data.list, function(key, value) {
                trHtml += "<tr>";
                if (value['num'] == 9999) {
                    if (value['teamOrders'] == 7777) {
                    } else {
                        trHtml += "<td>" + value['teamNm'] + "</td>";
                        trHtml += "<td colspan='2' class='foo'>소계</td>";
                        trHtml += "<td class='right foo'>" + comma(value['totalGold']) + "</td>";
                        trHtml += "<td class='right foo'>" + comma(value['totalJJJ']) + "</td>";
                        trHtml += "<td class='right foo'>" + comma(value['totalKKK']) + "</td>";
                        trHtml += "<td class='right foo'>" + comma(value['totalLLL']) + "</td>";
                        trHtml += "<td class='right foo'>" + comma(value['totalBaby']) + "</td>";
                        trHtml += "<td class='right foo'>" + comma(value['totalMMM']) + "</td>";
                        trHtml += "<td class='right foo'>" + comma(value['totalNNN']) + "</td>";
                        trHtml += "<td class='right foo'>" + comma(value['totalOOO']) + "</td>";
                        trHtml += "<td class='right foo'>" + comma(value['totalCare']) + "</td>";
                        trHtml += "<td class='right foo'>" + comma(value['totalPPP']) + "</td>";
                        trHtml += "<td class='right foo'>" + comma(value['totalQQQ']) + "</td>";
                        trHtml += "<td class='right foo'>" + comma(value['totalAtp']) + "</td>";
                        trHtml += "<td class='right foo'>" + comma(value['totalIII']) + "</td>";
                        trHtml += "<td class='right foo'>" + comma(value['totalHHH']) + "</td>";
                        trHtml += "<td class='right foo'>" + comma(value['totalIbs']) + "</td>";
                        trHtml += "<td class='right foo'>" + comma(value['totalGGG']) + "</td>";
                        trHtml += "<td class='right foo'>" + comma(value['totalFFF']) + "</td>";
                        trHtml += "<td class='right foo'>" + comma(value['totalEEE']) + "</td>";
                        trHtml += "<td class='right foo'>" + comma(value['totalBBB']) + "</td>";
                        trHtml += "<td class='right foo'>" + comma(value['totalCCC']) + "</td>";
                        trHtml += "<td class='right foo'>" + comma(value['totalDDD']) + "</td>";
                    }
                } else {
                    trHtml += "<td>" + value['teamNm'] + "</td>";
                    trHtml += "<td>" + value['areaNm'] + "</td>";
                    trHtml += "<td>" + value['name'] + "</td>";
                    trHtml += "<td class='right'>" + comma(value['totalGold']) + "</td>";
                    trHtml += "<td class='right'>" + comma(value['totalJJJ']) + "</td>";
                    trHtml += "<td class='right'>" + comma(value['totalKKK']) + "</td>";
                    trHtml += "<td class='right'>" + comma(value['totalLLL']) + "</td>";
                    trHtml += "<td class='right'>" + comma(value['totalBaby']) + "</td>";
                    trHtml += "<td class='right'>" + comma(value['totalMMM']) + "</td>";
                    trHtml += "<td class='right'>" + comma(value['totalNNN']) + "</td>";
                    trHtml += "<td class='right'>" + comma(value['totalOOO']) + "</td>";
                    trHtml += "<td class='right'>" + comma(value['totalCare']) + "</td>";
                    trHtml += "<td class='right'>" + comma(value['totalPPP']) + "</td>";
                    trHtml += "<td class='right'>" + comma(value['totalQQQ']) + "</td>";
                    trHtml += "<td class='right'>" + comma(value['totalAtp']) + "</td>";
                    trHtml += "<td class='right'>" + comma(value['totalIII']) + "</td>";
                    trHtml += "<td class='right'>" + comma(value['totalHHH']) + "</td>";
                    trHtml += "<td class='right'>" + comma(value['totalIbs']) + "</td>";
                    trHtml += "<td class='right'>" + comma(value['totalGGG']) + "</td>";
                    trHtml += "<td class='right'>" + comma(value['totalFFF']) + "</td>";
                    trHtml += "<td class='right'>" + comma(value['totalEEE']) + "</td>";
                    trHtml += "<td class='right'>" + comma(value['totalBBB']) + "</td>";
                    trHtml += "<td class='right'>" + comma(value['totalCCC']) + "</td>";
                    trHtml += "<td class='right'>" + comma(value['totalDDD']) + "</td>";
                }
                trHtml += "</tr>";
            });
            $("table tbody").append(trHtml);
        } else {
            $('#pagination-here').hide();
        }

    };

    postAjax(url, data, func);
} /** 담당별/품목별 매출 현황 가져오기  itemSalesStatusList.html **/

/** 지역별 약국 거래율 현황 가져오기  areaStatusList.html **/
function getAreaStatusList(str) {
    if ($('#searchYearMonth').val() == '') {
        var Now = new Date();
        var monthOfYear = Now.getMonth()
        Now.setMonth(monthOfYear - 1)
        $('#searchYearMonth').val(getDateStrYearMonth(Now));
    } else {
    }

    if (str != null && str != '') {
        $("#pageNumber").val(str);
    } else {
        $("#pageNumber").val(1);
    }

    var form = $('#searchForm')[0];

    var data = new FormData(form);

    var url = "/getAreaStatusList.ajax";

    showModal('검색중입니다. 잠시만 기달려주세요.');
    var func = function (data) {
        hideModal();
        if (data.list.length > 0) {
            $('#total-count').text("총건수 : " + data.totalCount);
            $('#pagination-here').bootpag({total: data.totalPageCount, page:$("#pageNumber").val()});
            $("table tbody").empty();
            var trHtml = "";
            trHtml += "<tr>";
            trHtml += "<td colspan='4'>합계</td>";
            trHtml += "<td class='right'>" + comma(data.totalInfo['total1']) + "</td>";
            trHtml += "<td class='right'>" + comma(data.totalInfo['total']) + "</td>";
            trHtml += "<td class='right'>" + comma(data.totalInfo['totalAmt']) + "</td>";
            trHtml += "</tr>";
            $.each(data.list, function(key, value) {
                trHtml += "<tr>";
                    trHtml += "<td>" + value['TEAM_NM'] + "</td>";
                    trHtml += "<td>" + value['AREA_NM'] + "</td>";
                    trHtml += "<td>" + value['name'] + "</td>";
                    trHtml += "<td>" + value['adName'] + "</td>";
                    trHtml += "<td class='right'>" + comma(value['total1']) + "</td>";
                    trHtml += "<td class='right'>" + comma(value['total']) + "</td>";
                    trHtml += "<td class='right'>" + comma(value['totalAmt']) + "</td>";
                trHtml += "</tr>";
            });
            $("table tbody").append(trHtml);
            $('#pagination-here').show();
        } else {
            $('#total-count').text("총건수 : " + data.totalCount);
            $('#pagination-here').hide();
        }

    };

    postAjax(url, data, func);
} /** 지역별 약국 거래율 현황 가져오기  areaStatusList.html **/

/** 재고 - 외부몰 리스트 가져오기 **/
/*
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
*/
