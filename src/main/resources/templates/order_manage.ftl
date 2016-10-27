<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta charset="utf-8">
    <title>订单管理</title>

    <meta name="description" content="Static &amp; Dynamic Tables">

    <script language="javascript" type="text/javascript" src="../js/bootstrap-paginator.js"></script>
    <#--<script language="javascript" type="text/javascript" src="../js/dateutil.js"></script>-->

</head>

<body class="no-skin">
<!-- #section:basics/navbar.layout -->


<!-- /section:basics/navbar.layout -->
<div class="main-container ace-save-state" id="main-container">
    <!-- /section:basics/sidebar -->
    <div class="main-content">
        <div class="main-content-inner">
            <!-- /section:basics/content.breadcrumbs -->
            <div class="page-content">

                <!-- /section:settings.box -->
                <div class="page-header">
                    <p>
                        订单管理
                    </p>
                </div>
                <!-- /.page-header -->

                <div class="row">
                    <div class="col-xs-12">
                        <div class="widget-box">
                            <div class="widget-header">
                                <h4 class="widget-title">筛选条件</h4>
                                <span class="widget-toolbar">
                                    <a href="#" data-action="collapse">
                                        <i class="ace-icon fa fa-chevron-up"></i>
                                    </a>
                                </span>
                            </div>
                            <div class="widget-body">
                                <div class="widget-main">
                                    <form id="screenOrderForm" class="form-horizontal" role="form">
                                        <div class="form-group">
                                            <label class="control-label col-sm-1 no-padding-right" for="name">订单日期</label>
                                            <div class="col-sm-6">
                                                <div class="clearfix">
                                                    <input id="orderTimePre" name="orderTimePre" type="datetime"  placeholder="" aria-controls="dynamic-table" onClick="WdatePicker({isShowClear:true,readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})">
                                                    至
                                                    <input id="orderTimeSuf" name="orderTimeSuf" type="search"  placeholder="" aria-controls="dynamic-table"onClick="WdatePicker({isShowClear:true,readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})">
                                                </div>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="control-label col-sm-1 no-padding-right" for="name">订单编号</label>
                                            <div class="col-sm-6">
                                                <div class="clearfix">
                                                    <input id="orderCode" name="orderCode" type="search" class="form-control" placeholder="" aria-controls="dynamic-table">
                                                </div>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="control-label col-sm-1 no-padding-right" for="name">客户订单编号</label>
                                            <div class="col-sm-6">
                                                <div class="clearfix">
                                                    <input id="custOrderCode" name="custOrderCode" style="color: black" type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table">
                                                </div>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="control-label col-sm-1 no-padding-right" for="name">订单状态</label>
                                            <div class="col-sm-6">
                                                <div class="clearfix">
                                                    <select class="chosen-select form-control" data-placeholder="请选择订单状态" id="orderStatus" name="orderStatus">
                                                        <option value="">----</option>
                                                        <option value="10">待审核</option>
                                                        <option value="20">已审核</option>
                                                        <option value="30">执行中</option>
                                                        <option value="40">已完成</option>
                                                        <option value="50">已取消</option>
                                                    </select>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="control-label col-sm-1 no-padding-right" for="name">订单类型</label>
                                            <div class="col-sm-6">
                                                <div class="clearfix">
                                                    <select class="chosen-select form-control" data-placeholder="请选择订单类型" id="orderType" name="orderType">
                                                        <option value="">----</option>
                                                        <option value="60">运输订单</option>
                                                        <option value="61">仓配订单</option>
                                                    </select>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="control-label col-sm-1 no-padding-right" for="name">业务类型</label>
                                            <div class="col-sm-6">
                                                <div class="clearfix">
                                                    <select class="chosen-select form-control" data-placeholder="请选择业务类型" id="businessType" name="businessType">
                                                        <option value="">----</option>
                                                        <option value="600">城配</option>
                                                        <option value="601">干线</option>
                                                        <option value="----------">----------</option>
                                                        <option value="610">销售出库</option>
                                                        <option value="611">调拨出库</option>
                                                        <option value="612">报损出库</option>
                                                        <option value="613">其他出库</option>
                                                        <option value="----------">----------</option>
                                                        <option value="620">采购入库</option>
                                                        <option value="621">调拨入库</option>
                                                        <option value="622">退货入库</option>
                                                        <option value="623">加工入库</option>
                                                    </select>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <span class="btn btn-info btn-sm popover-info" data-rel="popover" data-placement="bottom" title="" data-content="" data-original-title="Some Info" id="screenOrderFormBtn">搜索</span>
                                        </div>
                                    <#--<input type="hidden" name="tag" value="manage"/>-->

                                    </form>
                                </div>
                            </div>
                        </div>
                        <!-- div.table-responsive -->
                            <div id="dynamic-table_wrapper" class="dataTables_wrapper form-inline no-footer">


                                <div class="table-header">
                                    订单列表
                                </div>
                                <div class="col-xs-12">

                                    <div class="dataTables_length" id="dynamic-table_length">
                                        <label>显示
                                            <select aria-controls="dynamic-table" class="form-control input-sm">
                                                <option value="10">10</option>
                                                <option value="25">25</option>
                                                <option value="50">50</option>
                                                <option value="100">100</option>
                                            </select>
                                            条
                                        </label>
                                    </div>
                                </div>
                                <table id="dynamic-table" class="table table-striped table-bordered table-hover dataTable no-footer" role="grid" aria-describedby="dynamic-table_info">
                                    <thead>
                                    <tr role="row"><th class="center sorting_disabled" rowspan="1" colspan="1" aria-label="">
                                        <label class="pos-rel">
                                            <input type="checkbox" class="ace">
                                            <span class="lbl"></span>
                                        </label>
                                    </th>
                                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Status: activate to sort column ascending">订单操作</th>
                                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Domain: activate to sort column ascending">订单编号</th>
                                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Price: activate to sort column ascending">客户订单编号</th>
                                        <th class="sorting" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Update: activate to sort column ascending">
                                            <i class="ace-icon fa fa-clock-o bigger-110 hidden-480"></i>
                                            订单日期
                                        </th>
                                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">订单类型</th>
                                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">业务类型</th>
                                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">订单状态</th>
                                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">收货方姓名</th>
                                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">仓库名称</th>
                                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">店铺</th>
                                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">备注</th>

                                    </thead>
                                    <tbody>
                                    <#--订单列表数据-->
                                    <#list orderList! as order>
                                    <tr role="row" class="odd">
                                        <td class="center">
                                            <label class="pos-rel">
                                                <input type="checkbox" class="ace">
                                                <span class="lbl"></span>
                                            </label>
                                        </td>
                                        <td>

                                            <#if order.orderStatus?? >
                                                <#if ((order.orderStatus)== '10')>
                                                    <button type="button" id="review" +${order_index} onclick="reviewOrder('${order.orderCode!"null"}','${order.orderStatus!"null"}')" class="btn btn-minier btn-yellow">审核</button>
                                                    <button type="button" id="edit" +${order_index} onclick="editOrder('${order.orderCode!"null"}')"  class="btn btn-minier btn-success">编辑</button>
                                                    <button type="button" id="delete" +${order_index} onclick="deleteOrder('${order.orderCode!"null"}','${order.orderStatus!"null"}')"  class="btn btn-minier btn-danger">删除</button>
                                                </#if>
                                            </#if>
                                            <#if order.orderStatus?? >
                                                <#if ((order.orderStatus)== '20')>
                                                    <button type="button" id="rereview" +${order_index} onclick="reReviewOrder('${order.orderCode!"null"}','${order.orderStatus!"null"}')"  class="btn btn-minier btn-inverse">反审核</button>
                                                </#if>
                                            </#if>
                                            <#if order.orderStatus?? >
                                                <#if ((order.orderStatus)== '20') || (order.orderStatus)=='30'>
                                                    <button type="button" id="cancel" +${order_index} onclick="cancelOrder('${order.orderCode!"null"}','${order.orderStatus!"null"}')"  class="btn btn-minier btn-default">取消</button>
                                                </#if>
                                            </#if>
                                        </td>

                                        <td>
                                            <a href="javascript:orderDetail('${(order.orderCode)!""}')">${(order.orderCode)!""}</a>
                                        </td>
                                        <td>${order.custOrderCode!"null"}</td>
                                        <td class="hidden-480">${((order.orderTime)?string("yyyy-MM-dd HH:mm:ss"))!}</td>
                                        <td>
                                            <#if order.orderType ??><#if order.orderType == '60'>运输订单</#if></#if>
                                            <#if order.orderType ??><#if order.orderType == '61'>仓配订单</#if></#if>
                                        </td>
                                        <td class="hidden-480">
                                            <#if order.businessType ??><#if order.businessType == '600'>城配</#if></#if>
                                            <#if order.businessType ??><#if order.businessType == '601'>干线</#if></#if>
                                            <#if order.businessType ??><#if order.businessType == '610'>销售出库</#if></#if>
                                            <#if order.businessType ??><#if order.businessType == '611'>调拨出库</#if></#if>
                                            <#if order.businessType ??><#if order.businessType == '612'>报损出库</#if></#if>
                                            <#if order.businessType ??><#if order.businessType == '613'>其他出库</#if></#if>
                                            <#if order.businessType ??><#if order.businessType == '620'>采购入库</#if></#if>
                                            <#if order.businessType ??><#if order.businessType == '621'>调拨入库</#if></#if>
                                            <#if order.businessType ??><#if order.businessType == '622'>退货入库</#if></#if>
                                            <#if order.businessType ??><#if order.businessType == '623'>加工入库</#if></#if>
                                        </td>
                                        <td class="hidden-480">

                                            <#if order.orderStatus ??><#if order.orderStatus == '10'><span class="label label-sm label-yellow">待审核</span></#if></#if>
                                            <#if order.orderStatus ??><#if order.orderStatus == '20'><span class="label label-sm label-warning">已审核</span></#if></#if>
                                            <#if order.orderStatus ??><#if order.orderStatus == '30'><span class="label label-sm label-info">执行中</span></#if></#if>
                                            <#if order.orderStatus ??><#if order.orderStatus == '40'><span class="label label-sm label-success">已完成</span></#if></#if>
                                            <#if order.orderStatus ??><#if order.orderStatus == '50'><span class="label label-sm label-default">已取消</span></#if></#if>

                                        </td>
                                        <td class="hidden-480">
                                        ${order.consigneeName!"null"}
                                        </td>
                                        <td class="hidden-480">
                                        ${order.warehouseName!"null"}
                                        </td>
                                        <td class="hidden-480">
                                        ${order.notes!"null"}
                                        </td>
                                        <td class="hidden-480">
                                        ${order.store!"null"}
                                        </td>

                                    </tr>
                                    </#list>
                                    </tbody>
                                </table>
                                查询结果共${(totalNum)!"0"}条记录,共${(totalPage)!"1"}页
                                <div class="row">
                                    <#--<div class="col-xs-6">
                                        <div class="dataTables_info" id="dynamic-table_info" role="status" aria-live="polite"></div>
                                    </div>-->
                                    <div id="example" style="text-align: center"> <ul id="pageLimit" class="pagination"></ul> </div>
                                   <#-- <div class="col-xs-6">
                                        <div class="dataTables_paginate paging_simple_numbers" id="dynamic-table_paginate">
                                            <ul class="pagination">
                                                <li class="paginate_button previous disabled" aria-controls="dynamic-table" tabindex="0" id="dynamic-table_previous">
                                                    <a href="#">Previous</a>
                                                </li>
                                                <li class="paginate_button active" aria-controls="dynamic-table" tabindex="0">
                                                    <a href="#">1</a>
                                                </li>
                                                <li class="paginate_button " aria-controls="dynamic-table" tabindex="0">
                                                    <a href="#">2</a>
                                                </li>
                                                <li class="paginate_button " aria-controls="dynamic-table" tabindex="0">
                                                    <a href="#">3</a>
                                                </li>
                                                <li class="paginate_button next" aria-controls="dynamic-table" tabindex="0" id="dynamic-table_next">
                                                    <a href="#">Next</a>
                                                </li>
                                            </ul>
                                        </div>
                                    </div>-->
                                </div>
                            </div>
                    </div>
                </div>

                <!-- PAGE CONTENT ENDS -->
            </div><!-- /.col -->
        </div><!-- /.row -->
    </div><!-- /.page-content -->
</div>
</div><!-- /.main-content -->

</div><!-- /.main-container -->




<#include "common/include.ftl">
<script type="text/javascript">
    var scripts = [ null, "../components/chosen/chosen.jquery.js", null ]
    $(".page-content-area").ace_ajax("loadScripts", scripts, function() {
        $(document).ready(main);
        $('.chosen-select').chosen({allow_single_deselect:true});
        //resize the chosen on window resize

        $(window)
                .off('resize.chosen')
                .on('resize.chosen', function() {
                    $('.chosen-select').each(function() {
                        var $this = $(this);
                        $this.next().css({'width': $this.parent().width()});
                    })
                }).trigger('resize.chosen');
        //resize chosen on sidebar collapse/expand
        $(document).on('settings.ace.chosen', function(e, event_name, event_val) {
            if(event_name != 'sidebar_collapsed') return;
            $('.chosen-select').each(function() {
                var $this = $(this);
                $this.next().css({'width': $this.parent().width()});
            })
        });
    });

    function main(){
        //初始化页面数据
        /*initPageData();*/
        // 查询




    }

    //页面数据初始化
    /*function initPageData(){
        var active_class = "active";
        $("#simple-table > thead > tr > th input[type=checkbox]").eq(0).on("click", function(){
            var th_checked = this.checked;//checkbox inside "TH" table header

            $(this).closest("table").find("tbody > tr").each(function(){
                var row = this;
                if(th_checked) $(row).addClass(active_class).find("input[type=checkbox]").eq(0).prop("checked", true);
                else $(row).removeClass(active_class).find("input[type=checkbox]").eq(0).prop("checked", false);
            });
        });
        $("#simple-table").on("click", "td input[type=checkbox]" , function(){
            var $row = $(this).closest("tr");
            if($row.is(".detail-row ")) return;
            if(this.checked) $row.addClass(active_class);
            else $row.removeClass(active_class);
        });
        //xxxx();
    }*/
</script>
<!-- inline scripts related to this page -->
<script type="text/javascript">


    $("#screenOrderFormBtn").click(function () {
        var jsonStr={};
        var orderTimePre = $dp.$('orderTimePre').value;
        jsonStr.orderTimePre=orderTimePre;
        var orderTimeSuf = $dp.$('orderTimeSuf').value;
        jsonStr.orderTimeSuf=orderTimeSuf;
        jsonStr.orderCode=$("#orderCode").val();
        jsonStr.custOrderCode=$("#custOrderCode").val();
        jsonStr.orderStatus=$("#orderStatus").val();
        jsonStr.orderType=$("#orderType").val();
        jsonStr.businessType=$("#businessType").val();
        var tag = "manage";
        var orderScreenConditionJSON = JSON.stringify(jsonStr);
        var currPage = "1";
        var pageSize = "10";
        var url = "/ofc/orderScreenByCondition/" + orderScreenConditionJSON + "/" + tag + "/" + currPage + "/" + pageSize;
        xescm.common.loadPage(url);
    });

    $('#pageLimit').bootstrapPaginator({
        currentPage: ${currPage!"1"},//当前页码
        totalPages: ${totalPage!"1"}, //总页数
        size:"normal",
        bootstrapMajorVersion: 3,
        alignment:"right",
        numberOfPages:10,//每页显示多少
        itemTexts: function (type, page, current) {
            switch (type) {
                case "first":
                    return "首页";
                case "prev":
                    return "上一页";
                case "next":
                    return "下一页";
                case "last":
                    return "末页";
                case "page":
                    return page;
            }
        },onPageClicked:function (event, originalEvent, type, page) {//异步刷新页面
            var jsonStr={};
            var orderTimePre = $dp.$('orderTimePre').value;
            jsonStr.orderTimePre=orderTimePre;
            var orderTimeSuf = $dp.$('orderTimeSuf').value;
            jsonStr.orderTimeSuf=orderTimeSuf;
            jsonStr.orderCode=$("#orderCode").val();
            jsonStr.custOrderCode=$("#custOrderCode").val();
            jsonStr.orderStatus=$("#orderStatus").val();
            jsonStr.orderType=$("#orderType").val();
            jsonStr.businessType=$("#businessType").val();
            var tag = "manage";
            var orderScreenConditionJSON = JSON.stringify(jsonStr);
            var currPage = page;
            var pageNum = "10";
            var url = "/ofc/orderScreenByCondition/" + orderScreenConditionJSON + "/" + tag + "/" + currPage + "/" + pageNum;
            xescm.common.loadPage(url);

        }
    });

    function editOrder(orderCode) {
        /*跳转到订单的可编辑页(跟下单页面一样!), 并回显该订单数据*/
        var url = "/ofc/getOrderDetailByCode/" + orderCode + "/orderCode";
        xescm.common.loadPage(url);
    }

    function deleteOrder(ordercode,orderStatus) {
        xescm.common.submit("/ofc/orderDelete","您确定要删除此订单?",{"orderCode":ordercode,"orderStatus":orderStatus},function () {
            var jsonStr={};
            var orderScreenConditionJSON = JSON.stringify(jsonStr);
            var tag = "manage";
            var currPage = "1";
            var pageSize = "10"
            xescm.common.loadPage("/ofc/orderScreenByCondition/" + orderScreenConditionJSON + "/" + tag + "/" + currPage + "/" + pageSize);
        });
    }
    function orderDetail(orderCode) {
        var followTag = "orderCode";
        var url = "/ofc/orderDetails/" + orderCode + "/" + followTag;
        xescm.common.loadPage(url);
    }

    function reviewOrder(ordercode,orderStatus) {
        xescm.common.submit("/ofc/orderOrNotAudit","您确定要审核此订单?",{"orderCode":ordercode,"orderStatus":orderStatus,"reviewTag":"review"},function () {
            var jsonStr={};
            var orderScreenConditionJSON = JSON.stringify(jsonStr);
            var tag = "manage";
            var currPage = "1";
            var pageSize = "10";
            xescm.common.loadPage("/ofc/orderScreenByCondition/" + orderScreenConditionJSON + "/" + tag + "/" + currPage + "/" + pageSize);
        });
    }
    function reReviewOrder(ordercode,orderStatus) {

        console.log("reReviewOrder----------")
        xescm.common.submit("/ofc/orderOrNotAudit","您确定要反审核此订单?",{"orderCode":ordercode,"orderStatus":orderStatus,"reviewTag":"rereview"},function () {
            console.log("===/ofc/orderOrNotAudit")
            var jsonStr = {};
            var orderScreenConditionJSON = JSON.stringify(jsonStr);
            var tag = "manage";
            var currPage = "1";
            var pageSize = "10";
            xescm.common.loadPage("/ofc/orderScreenByCondition/" + orderScreenConditionJSON + "/" + tag + "/" + currPage + "/" + pageSize);
        });
    }
    function cancelOrder(ordercode,orderStatus) {
        xescm.common.submit("/ofc/orderCancel","您确定要取消此订单?",{"orderCode":ordercode,"orderStatus":orderStatus},function () {
            var jsonStr = {};
            var orderScreenConditionJSON = JSON.stringify(jsonStr);
            var tag = "manage";
            var currPage = "1";
            var pageSize = "10"
            xescm.common.loadPage("/ofc/orderScreenByCondition/" + orderScreenConditionJSON + "/" + tag + "/" + currPage + "/" + pageSize);
        });
    }


</script>
<link rel="stylesheet" href="../components/chosen/chosen.css" />
<script src="../components/chosen/chosen.jquery.js"></script>

</body>