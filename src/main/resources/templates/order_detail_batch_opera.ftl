<head>
    <title>订单批次</title>
</head>
<body class="no-skin">

<div class="col-xs-12">
    <div class="col-sm-6" style="float: right">
    <button class="btn btn-white btn-info btn-bold" style="float:right;width:105px;height:34px;" id="InvoicePrinting" value="" onclick="invoicePrint()">
        发货单打印
    </button>
    <button class="btn btn-white btn-info btn-bold filters" style="float:right;margin-right:10px;" id="goBack" value="" onclick="detailBackToHistory()">
        返回
    </button>
    </div>
    <form id="" method="post" class="form-horizontal" role="form">
        <div class="page-header">
            <p>
                订单批次号
            </p>
        </div>
        <div class="row">
            <div class="col-xs-12">
                <form class="form-horizontal">
                    <div class="form-group">
                        <label class="control-label col-label no-padding-right" for="name">订单批次号</label>
                        <div class="w-width-220 col-float">
                            <input readonly="readonly" id="orderBatchNumber" name="orderBatchNumber" type="search" placeholder=""
                                   aria-controls="dynamic-table" value="${(orderBatchNumber)!""}">
                        </div>
                        <label class="control-label col-label no-padding-right" for="name">客户名称</label>
                        <div class="w-width-220 col-float">
                            <input readonly="readonly" id="orderCode" name="custName" type="search" placeholder=""
                                   aria-controls="dynamic-table" value="${(ofcBatchOrderVo.custName)!""}">
                        </div>
                        <label class="control-label col-label no-padding-right" for="name">开单员</label>
                        <div class="w-width-220 col-float">
                            <input readonly="readonly" id="merchandiser" name="merchandiser" type="search" placeholder=""
                                   aria-controls="dynamic-table" value="${(ofcBatchOrderVo.merchandiser)!""}">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-label no-padding-right" for="name">订单日期</label>
                        <div class="w-width-220 col-float">
                            <input readonly="readonly" id="orderTime" name="orderTime" type="search" placeholder=""
                                   aria-controls="dynamic-table"
                                   value="${(ofcBatchOrderVo.orderTime?string("yyyy-MM-dd"))!""}">
                        </div>
                        <label class="control-label col-label no-padding-right" for="name">配送仓库</label>
                        <div class="w-width-220 col-float">
                            <input readonly="readonly" id="warehouseName" name="warehouseName" type="search" placeholder=""
                                   aria-controls="dynamic-table" value="${(ofcBatchOrderVo.warehouseName)!""}">
                        </div>
                        <label class="control-label col-label no-padding-right" for="name">备注</label>
                        <div class="w-width-220 col-float">
                            <input readonly="readonly" id="notes" name="notes" type="search" placeholder=""
                                   aria-controls="dynamic-table" value="${(ofcBatchOrderVo.notes)!""}">
                        </div>
                    </div>
                </form>
            </div>
        </div>

        <div class="page-header">
            <p>
                发货方
            </p>
        </div>
        <div class="row">
            <div class="col-xs-12">
                <form class="form-horizontal">
                    <div class="form-group">
                        <label class="control-label col-label no-padding-right" for="name">名称</label>
                        <div class="w-width-220 col-float">
                            <input readonly="readonly" id="consignorName" name="consignorName" type="search" placeholder=""
                                   aria-controls="dynamic-table" value="${(ofcBatchOrderVo.consignorName)!""}">
                        </div>
                        <label class="control-label col-label no-padding-right" for="name">联系人</label>
                        <div class="w-width-220 col-float">
                            <input readonly="readonly" id="consignorContactName" name="consignorContactName" type="search" placeholder=""
                                   aria-controls="dynamic-table" value="${(ofcBatchOrderVo.consignorContactName)!""}">
                        </div>
                        <label class="control-label col-label no-padding-right" for="name">联系电话</label>
                        <div class="w-width-220 col-float">
                            <input readonly="readonly" id="consignorContactPhone" name="consignorContactPhone" type="search" placeholder=""
                                   aria-controls="dynamic-table" value="${(ofcBatchOrderVo.consignorContactPhone)!""}">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-label no-padding-right" for="name">地址</label>
                        <div class="col-xs-6">
                            <input readonly="readonly" id="address" name="address" style="width: 100%;width:462px;" type="search"
                                   placeholder=""
                                   aria-controls="dynamic-table" value="${(ofcBatchOrderVo.departure)!"" }">
                        </div>
                    </div>
                </form>
            </div>
        </div>

        <div class="page-header">
            <p>
                订单列表
            </p>
        </div>
        <table id="dynamic-table" class="table table-striped table-bordered table-hover dataTable no-footer" role="grid"
               aria-describedby="dynamic-table_info">
            <thead>
            <tr role="row">
                <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1"
                    aria-label="Clicks: activate to sort column ascending" >
                    <label class="pos-rel">
                        <input id="selOrder" type="checkbox" class="ace" onchange="selOrder()">
                        <span class="lbl"></span>
                    </label>选择
                </th>
                <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1"
                    aria-label="Clicks: activate to sort column ascending">订单编号
                </th>
                <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1"
                    aria-label="Clicks: activate to sort column ascending">客户订单编号
                </th>
                <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1"
                    aria-label="Clicks: activate to sort column ascending">订单日期
                </th>
                <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1"
                    aria-label="Clicks: activate to sort column ascending">订单类型
                </th>
                <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1"
                    aria-label="Clicks: activate to sort column ascending">业务类型
                </th>
                <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1"
                    aria-label="Clicks: activate to sort column ascending">订单状态
                </th>
                <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1"
                    aria-label="Clicks: activate to sort column ascending">收货方名称
                </th>
                <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1"
                    aria-label="Clicks: activate to sort column ascending">仓库名称
                </th>
                <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1"
                    aria-label="Clicks: activate to sort column ascending">备注
                </th>
            </thead>
            <tbody id="dataTbody">
            </tbody>
        </table>
        <div class="row">
            <div id="pageBarDiv" style="float: right;padding-top: 0px;margin-top: 0px;">
            </div>
        </div>

        <div class="page-header">
            <p>
                货品信息
            </p>
        </div>
        <table id="dynamic-table" class="table table-striped table-bordered table-hover dataTable no-footer" role="grid"
               aria-describedby="dynamic-table_info">
            <thead>
            <tr role="row">
                <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1"
                    aria-label="Clicks: activate to sort column ascending">货品种类
                </th>
                <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1"
                    aria-label="Clicks: activate to sort column ascending">货品小类
                </th>
                <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1"
                    aria-label="Clicks: activate to sort column ascending">货品编码
                </th>
                <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1"
                    aria-label="Clicks: activate to sort column ascending">货品名称
                </th>
                <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1"
                    aria-label="Clicks: activate to sort column ascending">货品规格
                </th>
                <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1"
                    aria-label="Clicks: activate to sort column ascending">单位
                </th>
                <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1"
                    aria-label="Clicks: activate to sort column ascending">包装
                </th>
                <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1"
                    aria-label="Clicks: activate to sort column ascending">计费方式
                </th>
                <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1"
                    aria-label="Clicks: activate to sort column ascending">计费单价
                </th>
                <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1"
                    aria-label="Clicks: activate to sort column ascending">计费数量
                </th>
                <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1"
                    aria-label="Clicks: activate to sort column ascending">重量(kg)
                </th>
            </tr>
            </thead>
            <tbody id="goodsTbody">
            </tbody>
        </table>
    </form>
</div><!-- /.col -->

<link rel="stylesheet" href="../components/chosen/chosen.css"/>
<script src="../components/chosen/chosen.jquery.js"></script>
<script type="text/javascript">
    function detailBackToHistory() {
        xescm.common.loadPage("/ofc/orderManageOpera");
    }
    function invoicePrint() {
        var sel = "";
        var url = "http://60.205.233.183:7020/WebReport/ReportServer?reportlets=";
        var code="";
        $("#dataTbody").find("tr").each(function(index){
            var tdArr = $(this).children();
            if(tdArr.eq(0).find("input").prop("checked")){
                sel="1";
                var order_code=tdArr.eq(1).find("a").html();
                code = code+"{reportlet:'/ofc/invoices/Invoice.cpt',orderCode:'"+order_code+"'},";
            }
        });
        if(sel==""){
            alert("请至少选择一个订单！");
        }else{
            code=code.substring(0,code.length-1);
            url=url+"["+code+"]";
            debugger;
            window.open(encodeURI(url));
        }
    }

    function selOrder() {
        debugger;
        if($("#selOrder").prop("checked")){
            $("#dataTbody").find("tr").each(function(index){
                $(this).children().eq(0).find("input").prop('checked',true);
            });
        }else{
            $("#dataTbody").find("tr").each(function(index){
                $(this).children().eq(0).find("input").prop('checked',false);
            });
        }
    }
</script>
<script type="text/javascript">
    var scripts = [null, "../components/chosen/chosen.jquery.js", null]
    $(".page-content-area").ace_ajax("loadScripts", scripts, function () {
        $(document).ready(main);
        $('.chosen-select').chosen({allow_single_deselect: true});
        //resize the chosen on window resize

        $(window)
                .off('resize.chosen')
                .on('resize.chosen', function () {
                    $('.chosen-select').each(function () {
                        var $this = $(this);
                        $this.next().css({'width': $this.parent().width()});
                    })
                }).trigger('resize.chosen');
        //resize chosen on sidebar collapse/expand
        $(document).on('settings.ace.chosen', function (e, event_name, event_val) {
            if (event_name != 'sidebar_collapsed') return;
            $('.chosen-select').each(function () {
                var $this = $(this);
                $this.next().css({'width': $this.parent().width()});
            })
        });
    });

    function main() {
        //初始化页面数据
        initPageData();
        // 查询
        queryData(1);

    }


    //页面数据初始化
    function initPageData() {
        var active_class = "active";
        $("#simple-table > thead > tr > th input[type=checkbox]").eq(0).on("click", function () {
            var th_checked = this.checked;//checkbox inside "TH" table header

            $(this).closest("table").find("tbody > tr").each(function () {
                var row = this;
                if (th_checked) $(row).addClass(active_class).find("input[type=checkbox]").eq(0).prop("checked", true);
                else $(row).removeClass(active_class).find("input[type=checkbox]").eq(0).prop("checked", false);
            });
        });
        $("#simple-table").on("click", "td input[type=checkbox]", function () {
            var $row = $(this).closest("tr");
            if ($row.is(".detail-row ")) return;
            if (this.checked) $row.addClass(active_class);
            else $row.removeClass(active_class);
        });
    }
</script>
<!-- inline scripts related to this page -->
<script type="text/javascript">

    function queryData(pageNum) {
        var param = {};
        param.pageNum = pageNum;
        param.pageSize = 20;
        param.orderBatchNumber = "${(orderBatchNumber)!""}";
        CommonClient.post(sys.rootPath + "/ofc/queryOrderByOrderBatchNumber", param, function (result) {

            if (result == undefined || result == null) {
                alert("HTTP请求无数据返回！");
            } else if (result.code == 200) {// 1:normal
                reloadGrid(result);// 刷新页面数据
                laypage({
                    cont: $("#pageBarDiv"), // 容器。值支持id名、原生dom对象，jquery对象,
                    pages: result.result.pages, // 总页数
                    skip: true, // 是否开启跳页
                    skin: "molv",
                    groups: 3, // 连续显示分页数
                    curr: result.result.pageNum, // 当前页
                    jump: function (obj, first) { // 触发分页后的回调
                        if (!first) { // 点击跳页触发函数自身，并传递当前页：obj.curr
                            queryData(obj.curr);
                        }
                    }
                });
            } else if (result.code == 403) {
                alert("没有权限")
            } else {
                $("#dataTbody").html("");
            }
        });
    }

    function reloadGrid(data) {
        var htmlText = "";

        if (data == null || data == "" || data == undefined) {
            $("#dataTbody").html("");
            return;
        }
        if (data.result.list == null || data.result.list == "" || data.result.list == undefined) {
            $("#dataTbody").html("");
            return;
        }

        var length = data.result.list.length;
        if (length < 1) {
            $("#dataTbody").html("");
            return;
        }
        $.each(data.result.list, function (index, item) {
            var order = item;
            var consigneeName = "";
            if ("2" == StringUtil.nullToEmpty(order.consigneeType)) {
                consigneeName = "个人-" + order.consigneeContactName;
            } else {
                consigneeName = order.consigneeName;
            }
            htmlText += "<tr onclick=\"queryGoodsData('" + order.orderCode + "')\" role='row' class='odd' >"
                    + "<td>" + "<input type='checkbox'>" + "</td>"
                    + "<td class='center'>"
                    + "<a onclick=\"orderDetailOper('" + order.orderCode + "')\">" + StringUtil.nullToEmpty(order.orderCode) + "</a>"
                    + "</td>"
                    + "<td class='center'>" + StringUtil.nullToEmpty(order.custOrderCode) + "</td>"
                    + "<td class='center'>" + subTimeString(StringUtil.nullToEmpty(order.orderTime)) + "</td>"
                    + "<td class='center'>" + getOrderType(order) + "</td>"
                    + "<td class='center'>" + getBusiType(order) + "</td>"
                    + "<td class='center'>" + getOrderStatus(order.orderStatus) + "</td>"
                    + "<td class='center'>" + StringUtil.nullToEmpty(order.consigneeName) + "</td>"
                    + "<td class='center'>" + StringUtil.nullToEmpty(order.warehouseName) + "</td>"
                    + "<td class='center'>" + StringUtil.nullToEmpty(order.notes) + "</td>"
                    + "</tr>";
        })
        $("#dataTbody").html(htmlText);
    }

    function getOrderStatus(status) {
        var value = "";
        if (status == '10') {
            value = "<span class=\"label label-sm label-yellow\">待审核</span>"
        } else if (status == '20') {
            value = "<span class=\"label label-sm label-warning\">已审核</span>"
        } else if (status == '30') {
            value = "<span class=\"label label-sm label-info\">执行中</span>"
        } else if (status == '40') {
            value = "<span class=\"label label-sm label-success\">已完成</span>"
        } else if (status == '50') {
            value = "<span class=\"label label-sm label-default\">已取消</span>"
        }
        return value;
    }

    function getBusiType(order) {
        var value = "";
        if (order.businessType == '600') {
            value = "城配"
        } else if (order.businessType == "601") {
            value = "干线";
        } else if (order.businessType == "610") {
            value = "销售出库";
        } else if (order.businessType == "611") {
            value = "调拨出库";
        } else if (order.businessType == "612") {
            value = "报损出库";
        } else if (order.businessType == "613") {
            value = "其他出库";
        } else if (order.businessType == "620") {
            value = "采购入库";
        } else if (order.businessType == "621") {
            value = "调拨入库";
        } else if (order.businessType == "622") {
            value = "退货入库";
        } else if (order.businessType == "623") {
            value = "加工入库";
        }
        return value;
    }

    function subTimeString(s) {
        try {
            return s.substring(0, 11);
        } catch (e) {
            return s;
        }
    }

    function getOrderType(order) {
        var value = "";
        if (order.orderType == "60") {
            value = "运输订单";
        } else if (order.orderType == "61") {
            value = "仓配订单";
        }
        return value;
    }

    function getOperatorByStatusOper(order, index) {
        var value = "";

        var newStatus = "<button type=\"button\" id=\"review\" " + index + " onclick=\"reviewOrderOper('" + order.orderCode + "','" + order.orderStatus + "')\" class=\"btn btn-minier btn-yellow\">审核</button>"
                + "<button type=\"button\" id=\"delete\" " + index + " onclick=\"deleteOrder('" + order.orderCode + "','" + order.orderStatus + "')\"  class=\"btn btn-minier btn-danger\">删除</button>";

        var unApproveStatus = "<button type=\"button\" id=\"rereview\" " + index + " onclick=\"reReviewOrderOper('" + order.orderCode + "','" + order.orderStatus + "')\"  class=\"btn btn-minier btn-inverse\">反审核</button>";
        var cancelStatus = "<button type=\"button\" id=\"cancel\" " + index + " onclick=\"cancelOrderOper('" + order.orderCode + "','" + order.orderStatus + "')\"  class=\"btn btn-minier btn-default\">取消</button>";

        if (order.orderStatus == "10") {
            value = newStatus;
        }
        if (order.orderStatus == "20" || order.orderStatus == "30") {
            value = unApproveStatus + cancelStatus;
        }
        if (order.orderStatus == "30") {
            value = cancelStatus;
        }
        return value;
    }

    //订单详情
    function orderDetailOper(orderCode) {
        xescm.common.loadPage("/ofc/orderDetailPageByCode/" + orderCode);
    }

    function queryOrderDetailBatchOpera(orderBatchCode) {
        xescm.common.loadPage("/ofc/orderDetailBatchOpera/" + orderBatchCode);
    }

    function queryGoodsData(orderCode) {
        var param = {};
        param.orderCode = orderCode;
        CommonClient.post(sys.rootPath + "/ofc/queryGoodsInfoByOrderCode", param, function (result) {

            if (result == undefined || result == null) {
                alert("HTTP请求无数据返回！");
            } else if (result.code == 200) {// 1:normal
                reloadGoods(result);// 刷新页面数据
            } else if (result.code == 403) {
                alert("没有权限")
            } else {
                $("#dataTbody").html("");
            }
        });
    }

    function reloadGoods(data) {
        if (data == null || data == "" || data == undefined) {
            return;
        }
        var html = "";
        $.each(data.result, function (index, item) {
            html += "<tr>" +
                    "<td>" + StringUtil.nullToEmpty(item.goodsType) + "</td>" +
                    "<td>" + StringUtil.nullToEmpty(item.goodsCategory) + "</td>" +
                    "<td>" + StringUtil.nullToEmpty(item.goodsCode) + "</td>" +
                    "<td>" + StringUtil.nullToEmpty(item.goodsName) + "</td>" +
                    "<td>" + StringUtil.nullToEmpty(item.goodsSpec) + "</td>" +
                    "<td>" + StringUtil.nullToEmpty(item.unit) + "</td>" +
                    "<td>" + StringUtil.nullToEmpty(item.pack) + "</td>" +
                    "<td>" + getChargingWays(StringUtil.nullToEmpty(item.chargingWays)) + "</td>" +
                    "<td>" + StringUtil.nullToEmpty(item.chargingUnitPrice) + "</td>" +
                    "<td>" + StringUtil.nullToEmpty(item.chargingQuantity) + "</td>" +
                    "<td>" + StringUtil.nullToEmpty(item.billingWeight) + "</td>" +
                    "</tr>";
        });
        $("#goodsTbody").empty().append(html);
    }

</script>

<script>
    $(function () {
        $("input[type='search']").attr("readonly", "readonly");
    })
    $(function () {
        var orderType = "${(ofcFundamentalInformation.orderType)!""}";
        $("#orderType").val(getOrderType(orderType));
        var businessType = "${(ofcFundamentalInformation.businessType)!""}"
        $("#businessType").val(getBusiType(businessType));
        var transportType = "${(ofcFundamentalInformation.transport_type)!""}";
        $("#transportType").val(getTransportType(transportType));
        var expensePaymentParty = "${(ofcFinanceInformation.expensePaymentParty)!""}"
        $("#expensePaymentParty").val(getExpensePaymentParty(expensePaymentParty));
        var payment = "${(ofcFinanceInformation.payment)!""}"";
        $("#payment").val(getPayment(payment));
    });

    function getPayment(payment) {
        if (payment == "6810") {
            return "现金支付";
        } else if (payment == "6820") {
            return "POS刷卡";
        } else if (payment == "6830") {
            return "微信";
        } else if (payment == "6840") {
            return "支付宝";
        } else if (payment == "6850") {
            return "银行支付";
        } else if (payment == "6860") {
            return "账户结算";
        }
        return "";
    }

    function getExpensePaymentParty(type) {
        if (type == "10") {
            return "发货方";
        } else if (type == "20") {
            return "收货方";
        }
        return "";
    }

    function getBusiType(businessType) {
        var value = "";
        if (businessType == '600') {
            value = "城配"
        } else if (businessType == "601") {
            value = "干线";
        } else if (businessType == "610") {
            value = "销售出库";
        } else if (businessType == "611") {
            value = "调拨出库";
        } else if (businessType == "612") {
            value = "报损出库";
        } else if (businessType == "613") {
            value = "其他出库";
        } else if (businessType == "620") {
            value = "采购入库";
        } else if (order.businessType == "621") {
            value = "调拨入库";
        } else if (businessType == "622") {
            value = "退货入库";
        } else if (businessType == "623") {
            value = "加工入库";
        }
        return value;
    }

    function getOrderType(orderType) {
        var value = "";
        if (orderType == "60") {
            value = "运输订单";
        } else if (orderType == "61") {
            value = "仓配订单";
        }
        return value;
    }

    function getTransportType(type) {
        var value = "";
        if (type == "10") {
            value = "零担"
        } else if (type == "20") {
            value = "整车";
        }
        return value;
    }

    function getChargingWays(type) {
        var value = "";
        if (type == "01") {
            value = "件数"
        } else if (type == "02") {
            value = "重量Kg";
        }else if (type == "03") {
            value = "体积m³";
        }
        return value;
    }


</script>

</body>
