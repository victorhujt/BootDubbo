<!DOCTYPE html>
<#assign base=request.contextPath />
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:th="http://www.thymeleaf.org"
      xmlns:sec="http://www.thymeleaf.org/thymeleaf-extras-springsecurity3">
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta charset="utf-8">
    <title>订单管理</title>

    <meta name="description" content="Static &amp; Dynamic Tables">

    <script language="javascript" type="text/javascript" src="${base}js/My97DatePicker/WdatePicker.js"></script>

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
                    <h1>
                        订单管理
                    </h1>
                </div><!-- /.page-header -->

                <div class="row">
                    <div class="col-xs-12">

                        <div class="clearfix">
                            <div class="pull-right tableTools-container"><div class="dt-buttons btn-overlap btn-group"><a class="dt-button buttons-collection buttons-colvis btn btn-white btn-primary btn-bold" tabindex="0" aria-controls="dynamic-table" data-original-title="" title=""><span><i class="fa fa-search bigger-110 blue"></i> <span class="hidden">Show/hide columns</span></span></a><a class="dt-button buttons-copy buttons-html5 btn btn-white btn-primary btn-bold" tabindex="0" aria-controls="dynamic-table" data-original-title="" title=""><span><i class="fa fa-copy bigger-110 pink"></i> <span class="hidden">Copy to clipboard</span></span></a><a class="dt-button buttons-csv buttons-html5 btn btn-white btn-primary btn-bold" tabindex="0" aria-controls="dynamic-table" data-original-title="" title=""><span><i class="fa fa-database bigger-110 orange"></i> <span class="hidden">Export to CSV</span></span></a><a class="dt-button buttons-excel buttons-flash btn btn-white btn-primary btn-bold" tabindex="0" aria-controls="dynamic-table"><span><i class="fa fa-file-excel-o bigger-110 green"></i> <span class="hidden">Export to Excel</span></span><div style="position: absolute; left: 0px; top: 0px; width: 39px; height: 35px; z-index: 99;" data-original-title="" title=""><embed id="ZeroClipboard_TableToolsMovie_1" src="${base}/components/datatables.net-buttons-swf/index.swf" loop="false" menu="false" quality="best" bgcolor="#ffffff" width="39" height="35" name="ZeroClipboard_TableToolsMovie_1" align="middle" allowscriptaccess="always" allowfullscreen="false" type="application/x-shockwave-flash" pluginspage="http://www.macromedia.com/go/getflashplayer" flashvars="id=1&amp;width=39&amp;height=35" wmode="transparent"></div></a><a class="dt-button buttons-pdf buttons-flash btn btn-white btn-primary btn-bold" tabindex="0" aria-controls="dynamic-table"><span><i class="fa fa-file-pdf-o bigger-110 red"></i> <span class="hidden">Export to PDF</span></span><div style="position: absolute; left: 0px; top: 0px; width: 39px; height: 35px; z-index: 99;" data-original-title="" title=""><embed id="ZeroClipboard_TableToolsMovie_2" src="${base}/components/datatables.net-buttons-swf/index.swf" loop="false" menu="false" quality="best" bgcolor="#ffffff" width="39" height="35" name="ZeroClipboard_TableToolsMovie_2" align="middle" allowscriptaccess="always" allowfullscreen="false" type="application/x-shockwave-flash" pluginspage="http://www.macromedia.com/go/getflashplayer" flashvars="id=2&amp;width=39&amp;height=35" wmode="transparent"></div></a><a class="dt-button buttons-print btn btn-white btn-primary btn-bold" tabindex="0" aria-controls="dynamic-table" data-original-title="" title=""><span><i class="fa fa-print bigger-110 grey"></i> <span class="hidden">Print</span></span></a></div></div>
                        </div>
                        <div class="table-header">
                            筛选条件
                        </div>

                        <!-- div.table-responsive -->

                        <!-- div.dataTables_borderWrap -->
                        <div>
                            <div id="dynamic-table_wrapper" class="dataTables_wrapper form-inline no-footer">
                                <form id="screenOrderForm">
                                    <#--<input type="hidden" name="tag" value="manage"/>-->
                                    <div class="row">

                                        <div id="dynamic-table_filter" class="dataTables_length">
                                            <label>
                                                &nbsp;&nbsp;&nbsp;订单日期:<input id="orderTimePre" name="orderTimePre" type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" onClick="WdatePicker({isShowClear:true,readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})">
                                                至<input id="orderTimeSuf" name="orderTimeSuf" type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table"onClick="WdatePicker({isShowClear:true,readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})">
                                                订单编号:<input id="orderCode" name="orderCode" type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table">
                                                客户订单编号:<input id="custOrderCode" name="custOrderCode" type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table">
                                                订单状态:
                                                <select id="orderStatus" name="orderStatus">
                                                    <option value="">----</option>
                                                    <option value="10">待审核</option>
                                                    <option value="20">已审核</option>
                                                    <option value="30">执行中</option>
                                                    <option value="40">已完成</option>
                                                    <option value="50">已取消</option>
                                                </select>
                                            </label>
                                        </div>

                                        <div id="dynamic-table_filter" class="dataTables_length">
                                            <label>

                                                &nbsp;&nbsp;&nbsp;
                                                订单类型:
                                                <select id="orderType" name="orderType">
                                                    <option value="">----</option>
                                                    <option value="60">运输订单</option>
                                                    <option value="61">仓配订单</option>
                                                </select>
                                                业务类型:
                                                <select id="businessType" name="businessType">
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

                                                <span class="btn btn-info btn-sm popover-info" data-rel="popover" data-placement="bottom" title="" data-content="Heads up! This alert needs your attention, but it's not super important." data-original-title="Some Info" id="screenOrderFormBtn">搜索</span>
                                            </label>
                                        </div>
                                        <br/>
                                        <div class="table-header">
                                            订单列表
                                        </div>
                                        <div class="col-xs-12">
                                            <div class="dataTables_length" id="dynamic-table_length">
                                                <label>Display
                                                    <select aria-controls="dynamic-table" class="form-control input-sm">
                                                        <option value="10">10</option>
                                                        <option value="25">25</option>
                                                        <option value="50">50</option>
                                                        <option value="100">100</option>
                                                    </select>
                                                    records
                                                </label>
                                            </div>
                                        </div>

                                    </div>
                                </form>
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
                                            <a href="/ofc/orderDetails?followTag=orderCode&code=${order.orderCode!'null'}">${order.orderCode!"null"}</a>
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
                                <div class="row">
                                    <div class="col-xs-6">
                                        <div class="dataTables_info" id="dynamic-table_info" role="status" aria-live="polite"></div></div><div class="col-xs-6"><div class="dataTables_paginate paging_simple_numbers" id="dynamic-table_paginate"><ul class="pagination"><li class="paginate_button previous disabled" aria-controls="dynamic-table" tabindex="0" id="dynamic-table_previous"><a href="#">Previous</a></li><li class="paginate_button active" aria-controls="dynamic-table" tabindex="0"><a href="#">1</a></li><li class="paginate_button " aria-controls="dynamic-table" tabindex="0"><a href="#">2</a></li><li class="paginate_button " aria-controls="dynamic-table" tabindex="0"><a href="#">3</a></li><li class="paginate_button next" aria-controls="dynamic-table" tabindex="0" id="dynamic-table_next"><a href="#">Next</a></li></ul></div></div></div></div>
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


<!-- basic scripts -->

<!--[if !IE]> -->
<script src="${base}/components/bootbox.js/bootbox.js"></script>
<!-- <![endif]-->

<#include "common/include.ftl">
<script type="text/javascript">
    var scripts = [ null, "", null ]
    $(".page-content-area").ace_ajax("loadScripts", scripts, function() {
        $(document).ready(main);
    });

    function main(){
        //初始化页面数据
        initPageData();
        // 查询
        $("#screenOrderFormBtn").click(function () {
            var jsonStr={};
            jsonStr.orderTimePre=$("#orderTimePre").val();
            jsonStr.orderTimeSuf=$("#orderTimeSuf").val();
            jsonStr.orderCode=$("#orderCode").val();
            jsonStr.custOrderCode=$("#custOrderCode").val();
            jsonStr.orderStatus=$("#orderStatus").val();
            jsonStr.orderType=$("#orderType").val();
            jsonStr.businessType=$("#businessType").val();
            var tag = "manage";
            var orderScreenConditionJSON = JSON.stringify(jsonStr);
            var url = "/ofc/orderScreenByCondition/" + orderScreenConditionJSON + "/" + tag;
            xescm.common.loadPage(url);
        });

    }

    //页面数据初始化
    function initPageData(){
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
    }
</script>
<!-- inline scripts related to this page -->
<script type="text/javascript">


    function editOrder(orderCode) {
        /*跳转到订单的可编辑页(跟下单页面一样!), 并回显该订单数据*/
        $("#screenOrderForm").attr("action","/ofc/getOrderDetailByCode?dtotag=orderCode&orderCode="+orderCode);
        $("#screenOrderForm").submit();
    }

    function deleteOrder(ordercode,orderStatus) {

        //xescm.common.submit("/ofc/orderDelete",{"orderCode":ordercode,"orderStatus":orderStatus});
        /*var result  = confirm("您确定要删除此订单?");
        if(result == true) {
            $.get("/ofc/orderDelete",{"orderCode":ordercode,"orderStatus":orderStatus},function (data) {
                $("#confirmBox").modal('hide');
                if(data == 200){
                    window.location.href="/ofc/orderScreenByCondition?tag=manage";
                } else {
                    alert("删除订单失败,请联系管理员!");
                }
            });
        }*/
       /* layer.confirm('您确定要删除此订单？', {
            skin : 'layui-layer-molv',
            icon : 3,
            title : '确认操作'
        }, function(){
            alert("=====");
        }
*/
        xescm.common.submit("/ofc/orderDelete","您确定要删除此订单?",{"orderCode":ordercode,"orderStatus":orderStatus},function () {
            var jsonStr={};
            var orderScreenConditionJSON = JSON.stringify(jsonStr);
            var tag = "manage";
            xescm.common.loadPage("/ofc/orderScreenByCondition/" + orderScreenConditionJSON + "/" + tag);
        });

    }

    function reviewOrder(ordercode,orderStatus) {
        xescm.common.submit("/ofc/orderOrNotAudit","您确定要审核此订单?",{"orderCode":ordercode,"orderStatus":orderStatus,"reviewTag":"review"},function () {
            var jsonStr={};
            var orderScreenConditionJSON = JSON.stringify(jsonStr);
            var tag = "manage";
            xescm.common.loadPage("/ofc/orderScreenByCondition/" + orderScreenConditionJSON + "/" + tag);
        });

    }
    function reReviewOrder(ordercode,orderStatus) {
        xescm.common.submit("/ofc/orderOrNotAudit","您确定要反审核此订单?",{"orderCode":ordercode,"orderStatus":orderStatus,"reviewTag":"rereview"},function () {
            var jsonStr = {};
            var orderScreenConditionJSON = JSON.stringify(jsonStr);
            var tag = "manage";
            xescm.common.loadPage("/ofc/orderScreenByCondition/" + orderScreenConditionJSON + "/" + tag);
        });
    }
    function cancelOrder(ordercode,orderStatus) {
        xescm.common.submit("/ofc/orderCancel","您确定要取消此订单?",{"orderCode":ordercode,"orderStatus":orderStatus},function () {
            var jsonStr = {};
            var orderScreenConditionJSON = JSON.stringify(jsonStr);
            var tag = "manage";
            xescm.common.loadPage("/ofc/orderScreenByCondition/" + orderScreenConditionJSON + "/" + tag);
        });
    }

</script>

</body></html>