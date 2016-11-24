<head>
    <title>订单追踪</title>
    <!-- bootstrap & fontawesome -->
</head>
<body class="no-skin">

<div class="col-xs-12">
    <div class="page-header">
        <p>
            筛选条件
        </p>
    </div>
    <div class=" form-group">
        <div class="col-sm-3 ">
            <select class="chosen-select col-xs-2 col-sm-12" id="followTag" name="followTag">
                <option value="orderCode">订单编号</option>
                <option value="custOrderCode">客户订单编号</option>
                <option value="transCode">运输单号</option>
            </select>
        </div>
        <div class="col-sm-3 ">
            <div class="clearfix">
                <input id="code" name="code" type="search" class="col-xs-2 col-sm-12" placeholder=""
                       aria-controls="dynamic-table">
            </div>
        </div>
    </div>
    <button class="btn btn-white btn-info btn-bold filters" id="followOrderFormBtn">
        筛选
    </button>
    <div class="page-header">
        <p>
            订单跟踪记录
        </p>
    </div>
    <div class="row">
        <div class="col-xs-12">
            <form id="" method="post" class="form-horizontal" role="form">
                <div class="form-group">
                    <label class="control-label col-label no-padding-right" for="name">订单编号</label>
                    <div class="col-xs-3">
                        <div class="clearfix">
                            <input id="orderTime" readonly="readonly" style="color: #000" name="orderTime" type="text"
                                   class="form-control input-sm" placeholder="" aria-controls="dynamic-table">
                        </div>
                    </div>
                    <label class="control-label col-label no-padding-right" for="name">客户订单编号</label>
                    <div class="col-xs-3">
                        <div class="clearfix">
                            <input id="orderTime" readonly="readonly" style="color: #000" name="orderTime" type="text"
                                   class="form-control input-sm" placeholder="" aria-controls="dynamic-table">
                        </div>
                    </div>
                    <label class="control-label col-label no-padding-right" for="name">订单批次号</label>
                    <div class="col-xs-3">
                        <div class="clearfix">
                            <input id="orderTime" readonly="readonly" style="color: #000" name="orderTime" type="text"
                                   class="form-control input-sm" placeholder="" aria-controls="dynamic-table">
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-label no-padding-right" for="name">订单日期</label>
                    <div class="col-xs-3">
                        <div class="clearfix">
                            <input id="orderCode" readonly="readonly" style="color: #000" name="" type="text"
                                   class="form-control input-sm" placeholder="" aria-controls="dynamic-table">
                        </div>
                    </div>
                    <label class="control-label col-label no-padding-right" for="name">客户名称</label>
                    <div class="col-xs-3">
                        <div class="clearfix">
                            <input id="orderCode" readonly="readonly" style="color: #000" name="" type="text"
                                   class="form-control input-sm" placeholder="" aria-controls="dynamic-table">
                        </div>
                    </div>
                    <label class="control-label col-label no-padding-right" for="name">订单状态</label>
                    <div class="col-xs-3">
                        <div class="clearfix">
                            <input id="orderCode" readonly="readonly" style="color: #000" name="" type="text"
                                   class="form-control input-sm" placeholder="" aria-controls="dynamic-table">
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-label no-padding-right" for="name">订单类型</label>
                    <div class="col-xs-3">
                        <div class="clearfix">
                            <input id="custOrderCode" readonly="readonly" style="color: #000" name="" type="text"
                                   class="form-control input-sm" placeholder="" aria-controls="dynamic-table">
                        </div>
                    </div>
                    <label class="control-label col-label no-padding-right" for="name">业务类型</label>
                    <div class="col-xs-3">
                        <div class="clearfix">
                            <input id="custOrderCode" readonly="readonly" style="color: #000" name="" type="text"
                                   class="form-control input-sm" placeholder="" aria-controls="dynamic-table">
                        </div>
                    </div>
                    <label class="control-label col-label no-padding-right" for="name">运输类型</label>
                    <div class="col-xs-3">
                        <div class="clearfix">
                            <input id="custOrderCode" readonly="readonly" style="color: #000" name="" type="text"
                                   class="form-control input-sm" placeholder="" aria-controls="dynamic-table">
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-label no-padding-right" for="name">出发地</label>
                    <div class="col-xs-3">
                        <div class="clearfix">
                            <input class="form-control input-sm" id="orderStatus" readonly="readonly" style="color: #000" type="text">
                        </div>
                    </div>
                    <label class="control-label col-label no-padding-right" for="name">目的地</label>
                    <div class="col-xs-3">
                        <div class="clearfix">
                            <input class="form-control input-xl" id="orderStatus" readonly="readonly" style="color: #000" type="text">
                        </div>
                    </div>
                    <label class="control-label col-label no-padding-right" for="name">运输单号</label>
                    <div class="col-xs-3">
                        <div class="clearfix">
                            <input id="orderStatus" readonly="readonly" style="color: #000" type="text">
                        </div>
                    </div>
                </div>

                <div class="form-group">
                    <label class="control-label col-label no-padding-right" for="name">车牌号</label>
                    <div class="col-xs-3">
                        <div class="clearfix">
                            <input id="orderType" readonly="readonly" style="color: #000" type="text">
                        </div>
                    </div>
                    <label class="control-label col-label no-padding-right" for="name">司机姓名</label>
                    <div class="col-xs-3">
                        <div class="clearfix">
                            <input id="orderType" readonly="readonly" style="color: #000" type="text">
                        </div>
                    </div>
                    <label class="control-label col-label no-padding-right" for="name">联系电话</label>
                    <div class="col-xs-3">
                        <div class="clearfix">
                            <input id="orderType" readonly="readonly" style="color: #000" type="text">
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-label no-padding-right" for="name">收货地址</label>
                    <div class="col-xs-6">
                        <div class="clearfix">
                            <input id="businessType" readonly="readonly" style="color: #000" type="text">
                        </div>
                    </div>
                </div>
            </form>
        </div>
    </div>
    <table id="dynamic-table" class="table table-striped table-bordered table-hover dataTable no-footer" role="grid"
           aria-describedby="dynamic-table_info">
        <thead>
        <tr role="row">
            <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1"
                aria-label="Clicks: activate to sort column ascending">订单状态
            </th>
        </thead>
        <tbody id="orderFollowStatusListTBody">

        </tbody>
    </table>

</div><!-- /.col -->


<link rel="stylesheet" href="../components/chosen/chosen.css"/>
<script src="../components/chosen/chosen.jquery.js"></script>
<script type="text/javascript">
    var scripts = [null, "../components/chosen/chosen.jquery.js", null]
    $(".page-content-area").ace_ajax("loadScripts", scripts, function () {
        $(document).ready(main);
        $('.chosen-select').chosen({allow_single_deselect: true});
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
        //xxxx();
    }

    $("#followOrderFormBtn").click(function () {
        var code = $("#code").val();
        var followTag = $("#followTag").val();
        if (code == "" || code == null) {
            alert("请先输入编号再进行查询");
            return false;
        } else {
            queryData(code, followTag);
        }
    });


    function queryData(code, followTag) {
        CommonClient.post(sys.rootPath + "/ofc/orderFollowCon", {
            "code": code,
            "followTag": followTag
        }, function (result) {

            if (result == undefined || result == null) {
                alert("HTTP请求无数据返回！");
            } else if (result.code == 200) {// 1:normal
                reloadGrid(result);// 刷新页面数据
            } else if (result.code == 403) {
                alert("没有权限")
            } else {
                $("#followBody").html("");
            }
        });
    }
    function reloadGrid(data) {
        var htmlText = "";
        var orderDTO = data.result.ofcOrderDTO;
        var orderStatusList = data.result.ofcOrderStatus;
        $.each(orderStatusList, function (index, orderStatus) {
            htmlText += "<tr role=\"row\" class=\"odd\">"
                    + "<td class=\"hidden-480\">" + StringUtil.nullToEmpty(orderStatus.notes) + "</td>"
                    + "</tr>";
        })
        $("#orderFollowStatusListTBody").html(htmlText);
        $("#orderTime").val(orderDTO.orderTime);
        $("#orderCode").val(orderDTO.orderCode);
        $("#custOrderCode").val(orderDTO.custOrderCode);
        var orderStatus = getOrderStatus(orderDTO.orderStatus);
        $("#orderStatus").val(orderStatus);
        var orderType = getOrderType(orderDTO.orderType);
        $("#orderType").val(orderType);
        var businessType = getBusiType(orderDTO.businessType);
        $("#businessType").val(businessType);
        $("#notes").val(orderDTO.notes);
        var departurePlace = (orderDTO.departureProvince
        + orderDTO.departureCity
        + orderDTO.departureDistrict
        + orderDTO.departureTowns).replace(/null/, "");
        $("#departurePlace").val(departurePlace);
        var destination = (orderDTO.destinationProvince
        + orderDTO.destinationCity
        + orderDTO.destinationDistrict
        + orderDTO.destinationTowns).replace(/null/, "");
        $("#destination").val(destination);
        var destinationPlace = (orderDTO.destinationProvince
        + orderDTO.destinationCity
        + orderDTO.destinationDistrict
        + orderDTO.destinationTowns
        + orderDTO.destination).replace(/null/, "");
        $("#destinationPlace").val(destinationPlace);
        $("#quantity").val(orderDTO.quantity);
        $("#weight").val(orderDTO.weight);
        $("#cubage").val(orderDTO.cubage);
        $("#transRequire").val(orderDTO.transRequire);
        $("#transCode").val(orderDTO.transCode);
        $("#plateNumber").val(orderDTO.plateNumber);
        $("#driverName").val(orderDTO.driverName);
        $("#contactNumber").val(orderDTO.contactNumber);
        /* $("#destination").val(orderDTO.destination);*/
    }

    function getOrderStatus(status) {
        var value = "";
        if (status == '10') {
            value = "待审核"
        } else if (status == '20') {
            value = "已审核"
        } else if (status == '30') {
            value = "执行中"
        } else if (status == '40') {
            value = "已完成"
        } else if (status == '50') {
            value = "已取消"
        }
        return value;
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
        } else if (businessType == "621") {
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
</script>
</body>
