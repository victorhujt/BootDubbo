<head>
    <title>订单管理</title>
    <style type="text/css">
        #goodsListDiv {
            position: fixed;
            left: 285px;
            top: 85px;
            width: 946px;
            height: 500px;
            z-index: 3;
            overflow: auto;
            border: solid #7A7A7A 2px;
        }

        #consignorListDiv {
            position: fixed;
            left: 285px;
            top: 77px;
            width: 946px;
            height: 500px;
            z-index: 3;
            overflow: auto;
            border: solid #7A7A7A 2px;
        }

        #consigneeListDiv {
            position: fixed;
            left: 285px;
            top: 77px;
            width: 946px;
            height: 500px;
            z-index: 3;
            overflow: auto;
            border: solid #7A7A7A 2px;
        }

        #custListDiv {
            position: fixed;
            left: 50%;
            top: 77px;
            width: 946px;
            height: 500px;
            z-index: 3;
            overflow: auto;
            border: solid #7A7A7A 2px;
            margin-left: -400px;
        }

        #goodsAndConsigneeDiv {
            position: fixed;
            left: 285px;
            top: 77px;
            width: 946px;
            height: 500px;
            z-index: 3;
            overflow: auto;
            border: solid #7A7A7A 2px;
        }
    </style>
    <link rel="stylesheet" type="text/css" href="../css/jquery.editable-select.min.css"/>
</head>
<body class="no-skin">


<div class="page-header">
    <p>
        筛选条件
    </p>
</div>
<div class="row">
    <div class="col-xs-12">
        <div class="form-horizontal">
            <div class="form-group">
                <label class="control-label col-label no-padding-right" for="name">客户名称</label>
                <div class="col-xs-3">
                    <input id="custName" class="w-width-220" name="custName" type="search" placeholder=""
                           aria-controls="dynamic-table">
                    <button type="button" onclick="selectCust();" style="width:20px;height:20px;">
                        <span class="glyphicon glyphicon-search" style="color: #0f5f9f;left:-3px;top:0px;"></span>
                    </button>
                </div>
                <label class="control-label col-label no-padding-right" for="name">订单编号</label>
                <div class="col-xs-2">
                    <input id="orderCode" class="col-width-168" name="" type="search" placeholder=""
                           aria-controls="dynamic-table">
                </div>
                <label class="control-label col-label no-padding-right" for="name">订单状态</label>
                <div class="col-width-168">
                    <select data-placeholder="请选择订单状态" id="orderStatus" name="orderStatus" class=" chosen-select">
                        <option value=""></option>
                        <option value="10">待审核</option>
                        <option value="20">已审核</option>
                        <option value="30">执行中</option>
                        <option value="40">已完成</option>
                        <option value="50">已取消</option>
                    </select>
                </div>
            </div>
            <div class="form-group">
                <label class="control-label col-label no-padding-right" for="name">订单日期</label>
                <div class="col-xs-3">
                    <input readonly="readonly" style="width: 101px;" id="startDate" name="startDate" type="search"
                           placeholder=""
                           aria-controls="dynamic-table"
                           onClick="WdatePicker({isShowClear:true,readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})">
                    至
                    <input readonly="readonly" style="width: 101px;" id="endDate" name="endDate" type="search"
                           placeholder=""
                           aria-controls="dynamic-table"
                           onClick="WdatePicker({isShowClear:true,readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})">
                </div>
                <label class="control-label col-label no-padding-right" for="name">订单类型</label>
                <div class="col-xs-2">
                    <div class="col-width-168">
                        <select data-placeholder="请选择订单类型" id="orderType" class="chosen-select" name="orderType">
                            <option value=""></option>
                            <option value="60">运输订单</option>
                            <option value="61">仓配订单</option>
                        </select>
                    </div>
                </div>
                <label class="control-label col-label no-padding-right" for="name">业务类型</label>

                <div class="col-width-168">
                    <select data-placeholder="请选择业务类型" id="businessType" class="chosen-select" name="businessType">
                        <option value=""></option>
                        <option value="600">城配</option>
                        <option value="601">干线</option>
                        <option value="602">卡班</option>
                    </select>
                </div>
                <span>
                    <button class="btn btn-primary btn-xs" id="doSearch" style="margin-left:10px;">查询</button>
                </span>

            </div>
        </div>
    </div>
</div>
<div class="page-header">
    <p>
        订单列表
    </p>
</div>
<!-- div.table-responsive -->
<div id="dynamic-table_wrapper" class="dataTables_wrapper form-inline no-footer">
    <table id="dynamic-table" class="table table-striped table-bordered table-hover dataTable no-footer" role="grid"
           aria-describedby="dynamic-table_info">
        <thead>
        <th>序号</th>
        <th>操作列</th>
        <th>客户名称</th>
        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1"
            aria-label="Price: activate to sort column ascending">订单编号
        </th>
        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1"
            aria-label="Update: activate to sort column ascending">
            订单批次号
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

        </thead>
        <tbody id="dataTbody">
        </tbody>
    </table>
    <div class="row">
        <div id="pageBarDiv" style="float: right;padding-top: 0px;margin-top: 0px;">
        </div>
    </div>


    <div class="modal-content" id="custListDiv" style="display: none;">
        <div class="modal-header"><span id="custListDivNoneTop" style="cursor:pointer"><button type="button" id=""
                                                                                               style="cursor:pointer"
                                                                                               class="bootbox-close-button close"
                                                                                               data-dismiss="modal"
                                                                                               aria-hidden="true">×</button></span>
            <h4 class="modal-title">选择客户</h4></div>
        <div class="modal-body">
            <div class="bootbox-body">
                <form id="consignorSelConditionForm" class="form-horizontal" role="form">
                <#--<input id="purpose2" name="cscContact.purpose" type="hidden" value="2">-->
                    <div class="form-group">
                        <label class="control-label col-xs-1 no-padding-right" for="name">名称</label>
                        <div class="col-xs-3">
                            <div class="clearfix">
                                <input id="custNameDiv" name="cscContactCompany.contactCompanyName" type="text"
                                       style="color: black" class="form-control input-sm" placeholder=""
                                       aria-controls="dynamic-table">
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-xs-1 no-padding-right" for="name"></label>
                        <div class="col-xs-3">
                            <div class="clearfix">
                                <span id="custSelectFormBtn" class="btn btn-info btn-sm popover-info">筛选</span>
                            </div>
                        </div>
                    </div>
                </form>
                <form class="bootbox-form">
                    <table id="dynamic-table" class="table table-striped table-bordered table-hover dataTable no-footer"
                           role="grid" aria-describedby="dynamic-table_info">
                        <thead>
                        <tr role="row">
                            <th class="center sorting_disabled" rowspan="1" colspan="1" aria-label="">
                                <label class="pos-rel">
                                    选择
                                    <span class="lbl"></span>
                                </label>
                            </th>
                            <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1"
                                aria-label="Price: activate to sort column ascending">类型
                            </th>
                            <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1"
                                aria-label="Clicks: activate to sort column ascending">公司名称
                            </th>
                            <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1"
                                aria-label="Clicks: activate to sort column ascending">渠道
                            </th>
                            <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1"
                                aria-label="Clicks: activate to sort column ascending">产品类别
                            </th>
                        </thead>
                        <tbody id="custListDivTbody"></tbody>
                    </table>
                    <div class="row">
                        <div id="selectCustPage" style="float: right;padding-top: 0px;margin-top: 0px;">
                        </div>
                    </div>
                </form>

            </div>
        </div>
        <div class="form-group">
            <div class="modal-footer">
                <button style="float: left" id="createCustBtn" data-bb-handler="confirm" type="button"
                        class="btn btn-primary">创建新客户
                </button>
                <button id="custEnter" data-bb-handler="confirm" type="button" class="btn btn-primary">选中</button>
                <span id="custListDivNoneBottom" style="cursor:pointer"><button data-bb-handler="cancel" type="button"
                                                                                class="btn btn-default">关闭</button></span>
            </div>
        </div>
    </div>

    <link rel="stylesheet" href="../components/chosen/chosen.css"/>
    <script src="../components/chosen/chosen.jquery.js"></script>

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
            initPageDataOrder();
            // 查询
            queryOrderData(1);

            $("#doSearch").click(function () {
                queryOrderData(1);
            });
        }


        //页面数据初始化
        function initPageDataOrder() {
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

        function queryOrderData(pageNum) {
            var param = {};
            param.pageNum = pageNum;
            param.pageSize = 10;
            param.custName = $("#custName").val();
            var startDate = $dp.$('startDate').value;
            var endDate = $dp.$('endDate').value;
            param.startDate = startDate;
            param.endDate = endDate;
            param.orderCode = $("#orderCode").val();
            param.orderStatus = $("#orderStatus").val();
            param.orderType = $("#orderType").val();
            param.businessType = $("#businessType").val();
            CommonClient.post(sys.rootPath + "/ofc/queryOrderDataOper", param, function (result) {

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
                                queryOrderData(obj.curr);
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
                htmlText += "<tr role=\"row\" class=\"odd\">"
                        + "<td>" + [index + 1] + "</td>"
                        + "<td class=\"center\">" + getOperatorByStatusOper(order, index) + "</td>"
                        + "<td class=\"center\">" + order.custName + "</td>"
                        + "<td>"
                        + "<a onclick=\"orderDetailOper('" + order.orderCode + "')\">" + StringUtil.nullToEmpty(order.orderCode) + "</a>"
                        + "</td>"
                        + "<td>"
                        + "<a onclick=\"queryOrderDetailBatchOpera('" + order.orderBatchNumber + "')\">" + StringUtil.nullToEmpty(order.orderBatchNumber) + "</a>"
                        + "</td>"
                        + "<td class=\"hidden-480\">" + StringUtil.nullToEmpty(order.custOrderCode) + "</td>"
                        + "<td class=\"hidden-480\">" + subTimeString(StringUtil.nullToEmpty(order.orderTime)) + "</td>"
                        + "<td class=\"hidden-480\">" + getOrderType(order) + "</td>"
                        + "<td class=\"hidden-480\">" + getBusiType(order) + "</td>"
                        + "<td class=\"hidden-480\">" + getOrderStatus(order.orderStatus) + "</td>"
                        + "<td class=\"hidden-480\">" + StringUtil.nullToEmpty(consigneeName) + "</td>"
                        + "<td class=\"hidden-480\">" + StringUtil.nullToEmpty(order.warehouseName) + "</td>"
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

        //删除订单
        function deleteOrder(ordercode, orderStatus) {
            xescm.common.submit("/ofc/orderDeleteOper", {
                "orderCode": ordercode,
                "orderStatus": orderStatus
            }, "您确定要删除此订单?", function () {
                xescm.common.loadPage("/ofc/orderManageOpera");
            });
        }
        //订单详情
        function orderDetailOper(orderCode) {
//            var url = "/ofc/orderDetailPageByCode/" + orderCode ;
//            $.get("/ofc/orderDetailPageByCode",{"orderCode":orderCode});
            xescm.common.loadPage("/ofc/orderDetailPageByCode/" + orderCode);
        }

        function queryOrderDetailBatchOpera(orderBatchCode) {
            xescm.common.loadPage("/ofc/orderDetailBatchOpera/" + orderBatchCode);
        }

        //订单审核、反审核
        function reviewOrderOper(ordercode, orderStatus) {
            xescm.common.submit("/ofc/orderOrNotAuditOper", {
                "orderCode": ordercode,
                "orderStatus": orderStatus,
                "reviewTag": "review"
            }, "您确定要审核此订单?", function () {

                xescm.common.loadPage("/ofc/orderManageOpera");
            });
        }
        function reReviewOrderOper(ordercode, orderStatus) {
            xescm.common.submit("/ofc/orderOrNotAuditOper", {
                "orderCode": ordercode,
                "orderStatus": orderStatus,
                "reviewTag": "rereview"
            }, "您确定要反审核此订单?", function () {
                xescm.common.loadPage("/ofc/orderManageOpera");
            });
        }
        //订单取消
        function cancelOrderOper(ordercode, orderStatus) {
            xescm.common.submit("/ofc/orderCancelOper", {
                "orderCode": ordercode,
                "orderStatus": orderStatus
            }, "您确定要取消此订单?", function () {
                xescm.common.loadPage("/ofc/orderManageOpera");
            });
        }

        function selectCust() {
            $("#custListDiv").show();
        }

        $("#custListDivNoneBottom,#custListDivNoneTop").on("click", function () {
            $("#custListDiv").hide();
        });

        $("#custSelectFormBtn").on("click", function () {
            var custName = $("#custNameDiv").val();
            loadCust(custName);
        });

        function loadCust(custName) {
            CommonClient.post(sys.rootPath + "/ofc/distributing/queryCustomerByName", {
                "queryCustomerName": custName,
                "currPage": "1"
            }, function (data) {
                data = eval(data);
                var custList = "";
                $.each(data, function (index, cscCustomerVo) {
                    var channel = cscCustomerVo.channel;
                    if (null == channel) {
                        channel = "";
                    }
                    custList = custList + "<tr role='row' class='odd'>";
                    custList = custList + "<td class='center'> " + "<label class='pos-rel'>" + "<input value='" + cscCustomerVo.customerName + "' name='cust' type='radio' class='ace'>" + "<span class='lbl'></span>" + "</label>" + "</td>";
                    custList = custList + "<td>" + (cscCustomerVo.type == "1" ? "个人" : "企业") + "</td>";
                    custList = custList + "<td>" + cscCustomerVo.customerName + "</td>";
                    custList = custList + "<td>" + channel + "</td>";
                    custList = custList + "<td>" + cscCustomerVo.productType + "</td>";
                    custList = custList + "</tr>";
                    $("#custListDivTbody").empty().html(custList);
                });
            }, "json");
        }

        $("#custEnter").on("click", function () {
            var val = $('input:radio[name="cust"]:checked').val();
            if (val == "" || val == null || val == undefined) {
                alert("请选择客户");
                return;
            }
            $("#custName").val(val);
            $("#custListDiv").hide();
        })

        function appendSelect(type) {
            if (type == "60") {
                return "<option value=''>----</option><option value='600'>城配</option><option value='601'>干线</option><option value='602'>卡班</option>";
            } else if (type == "61") {
                var html = "";
                html += "<option value='----------'>----------</option>";
                html += "<option value='610'>销售出库</option>";
                html += "<option value='611'>调拨出库</option>";
                html += "<option value='612'>报损出库</option>";
                html += "<option value='613'>其他出库</option>";
                html += "<option value='620'>采购入库</option>";
                html += "<option value='621'>调拨入库</option>";
                html += "<option value='622'>退货入库</option>";
                html += "<option value='623'>加工入库</option>";
                return html;
            }
            return null;
        }
        $("#orderType").on("change", function () {
            var type = $(this).val();
            if (type == "60" || type == "61") {
                $("#businessType").empty().append(appendSelect(type));
            }
        });

    </script>

</body>