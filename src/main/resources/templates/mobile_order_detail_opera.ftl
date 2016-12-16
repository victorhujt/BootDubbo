<head>
    <title>订单详情</title>
</head>
<body class="no-skin">

<div class="col-xs-12">
    <div class="col-sm-6" style="float: right">
    <#--<button style="float:right;" class="btn btn-white btn-info btn-bold filters" id="goBack" value="" onclick="detailBackToHistory()">
            返回
        </button>-->
    </div>
    <form id="" method="post" class="form-horizontal" role="form">
        <div class="page-header">
            <p>
                订单详情
            </p>
        </div>
        <div class="row">
            <div class="col-xs-12">
                <form class="form-horizontal">
                    <div class="form-group">
                        <label class="control-label col-label no-padding-right" for="name">流水号</label>
                        <div class="w-width-220 col-float">
                            <input id="orderCode" name="" type="search" placeholder=""
                                   aria-controls="dynamic-table" value="${(mobileOrder.mobileOrderCode)!""}">
                        </div>
                        <label class="control-label col-label no-padding-right" for="name">上传日期</label>
                        <div class="w-width-220 col-float">
                            <input id="uploadDate" name="" type="search" placeholder=""
                                   aria-controls="dynamic-table"
                                   value="${(mobileOrder.uploadDate?string("yyyy-MM-dd"))!""}">
                        </div>
                        <label class="control-label col-label no-padding-right" for="name">钉钉账号</label>
                        <div class="w-width-220 col-float">
                            <input id="dingdingAccountNo" name="" type="search" placeholder=""
                                   aria-controls="dynamic-table"
                                   value="${(mobileOrder.dingdingAccountNo)!""}">
                        </div>
                        <label class="control-label col-label no-padding-right" for="name">开单员</label>
                        <div class="w-width-220 col-float">
                            <input id="operator" name="" type="search" placeholder=""
                                   aria-controls="dynamic-table"
                                   value="${(mobileOrder.operator)!""}">
                        </div>
                        <label class="control-label col-label no-padding-right" for="name">业务类型</label>
                        <div class="w-width-220 col-float">
                            <input id="businessType" name="custName" type="search" placeholder=""
                                   aria-controls="dynamic-table" value="${(mobileOrder.businessType)!""}">
                        </div>
                        <label class="control-label col-label no-padding-right" for="name">运输单号</label>
                        <div class="w-width-220 col-float">
                            <input id="tranCode" name="tranCode" type="search" placeholder=""
                                   aria-controls="dynamic-table"
                                   value="${(mobileOrder.tranCode)!""}">
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </form>
</div><!-- /.col -->

<script>
    $(function () {
        $("input[type='search']").attr("readonly", "readonly")
    })

    $(function () {
        var businessType = "${(mobileOrder.businessType)!""}"
        $("#businessType").val(getBusiType(businessType));
    });
    function getBusiType(businessType) {
        var value = "";
        if (businessType == '600') {
            value = "城配"
        } else if (businessType == "601") {
            value = "干线";
        } else if (businessType == "602") {
            value = "卡班";
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
        } else if (businessType == "621") {
            value = "调拨入库";
        } else if (businessType == "622") {
            value = "退货入库";
        } else if (businessType == "623") {
            value = "加工入库";
        }
        return value;
    }
</script>
</body>
