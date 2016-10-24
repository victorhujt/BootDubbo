
<head>
    <title>订单跟踪</title>

</head>
<body class="no-skin">

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
                        订单跟踪
                    </h1>
                </div><!-- /.page-header -->
                <div class="row">
                    <div class="col-xs-12">
                        <div class="clearfix">
                            <div class="pull-right tableTools-container"><div class="dt-buttons btn-overlap btn-group"><a class="dt-button buttons-collection buttons-colvis btn btn-white btn-primary btn-bold" tabindex="0" aria-controls="dynamic-table" data-original-title="" title=""><span><i class="fa fa-search bigger-110 blue"></i> <span class="hidden">Show/hide columns</span></span></a><a class="dt-button buttons-copy buttons-html5 btn btn-white btn-primary btn-bold" tabindex="0" aria-controls="dynamic-table" data-original-title="" title=""><span><i class="fa fa-copy bigger-110 pink"></i> <span class="hidden">Copy to clipboard</span></span></a><a class="dt-button buttons-csv buttons-html5 btn btn-white btn-primary btn-bold" tabindex="0" aria-controls="dynamic-table" data-original-title="" title=""><span><i class="fa fa-database bigger-110 orange"></i> <span class="hidden">Export to CSV</span></span></a><a class="dt-button buttons-excel buttons-flash btn btn-white btn-primary btn-bold" tabindex="0" aria-controls="dynamic-table"><span><i class="fa fa-file-excel-o bigger-110 green"></i> <span class="hidden">Export to Excel</span></span><div style="position: absolute; left: 0px; top: 0px; width: 39px; height: 35px; z-index: 99;" data-original-title="" title=""><embed id="ZeroClipboard_TableToolsMovie_1" src="/components/datatables.net-buttons-swf/index.swf" loop="false" menu="false" quality="best" bgcolor="#ffffff" width="39" height="35" name="ZeroClipboard_TableToolsMovie_1" align="middle" allowscriptaccess="always" allowfullscreen="false" type="application/x-shockwave-flash" pluginspage="http://www.macromedia.com/go/getflashplayer" flashvars="id=1&amp;width=39&amp;height=35" wmode="transparent"></div></a><a class="dt-button buttons-pdf buttons-flash btn btn-white btn-primary btn-bold" tabindex="0" aria-controls="dynamic-table"><span><i class="fa fa-file-pdf-o bigger-110 red"></i> <span class="hidden">Export to PDF</span></span><div style="position: absolute; left: 0px; top: 0px; width: 39px; height: 35px; z-index: 99;" data-original-title="" title=""><embed id="ZeroClipboard_TableToolsMovie_2" src="/components/datatables.net-buttons-swf/index.swf" loop="false" menu="false" quality="best" bgcolor="#ffffff" width="39" height="35" name="ZeroClipboard_TableToolsMovie_2" align="middle" allowscriptaccess="always" allowfullscreen="false" type="application/x-shockwave-flash" pluginspage="http://www.macromedia.com/go/getflashplayer" flashvars="id=2&amp;width=39&amp;height=35" wmode="transparent"></div></a><a class="dt-button buttons-print btn btn-white btn-primary btn-bold" tabindex="0" aria-controls="dynamic-table" data-original-title="" title=""><span><i class="fa fa-print bigger-110 grey"></i> <span class="hidden">Print</span></span></a></div></div>
                        </div>


                        <!-- div.table-responsive -->

                        <!-- div.dataTables_borderWrap -->
                        <div>
                            <div id="dynamic-table_wrapper" class="dataTables_wrapper form-inline no-footer">
                                <form>
                                    <div class="row">
                                        <div id="dynamic-table_filter" class="dataTables_length">
                                            <label>
                                                &nbsp;&nbsp;&nbsp;
                                                <select id="followTag" data-label="lsl_条件类型" name="followTag">
                                                    <option value="orderCode">订单编号</option>
                                                    <option value="custOrderCode">客户订单编号</option>
                                                    <option value="transCode">运输单号</option>
                                                </select>
                                                <input  id = "code" name="code" type="search" style="color: black" class="form-control input-sm" placeholder="" aria-controls="dynamic-table">
                                                <span  class="btn btn-info btn-sm popover-info" data-rel="popover" data-placement="bottom" title="" data-content="Heads up! This alert needs your attention, but it's not super important." data-original-title="Some Info" id="followOrderFormBtn">查询</span>
                                            </label>
                                        </div>
                                        <br/>
                                        <div class="table-header">
                                            订单跟踪记录
                                        </div>
                                    </div>
                                </form>

                                <table id="dynamic-table" class="table table-striped table-bordered table-hover dataTable no-footer" role="grid" aria-describedby="dynamic-table_info">
                                    <thead>
                                    <tr role="row">
                                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">订单日期</th>
                                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">订单编号</th>
                                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">客户订单编号</th>
                                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">订单状态</th>
                                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">订单类型</th>
                                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">业务类型</th>
                                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">备注</th>
                                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">货品类型</th>
                                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">出发地</th>
                                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">目的地</th>
                                    </thead>
                                    <tbody>
                                    <#--
                                            vo  value object  拼出来的结果集
                                            dto  参数,给后端当参数,或者返回的dot.
                                            -->
                                    <tr role="row" class="odd">
                                        <td>
                                        ${(ofcOrderDTO.orderTime?string("MM-dd-yyyy"))!" "}
                                        </td>
                                        <td>
                                        ${(ofcOrderDTO.orderCode)!" "}
                                        </td>
                                        <td>
                                        ${(ofcOrderDTO.custOrderCode)!" "}
                                        </td>
                                        <td>
                                        ${(ofcOrderDTO.orderStatus)!" "}
                                        </td>
                                        <td>
                                        ${(ofcOrderDTO.orderType)!" "}
                                        </td>
                                        <td>
                                        ${(ofcOrderDTO.businessType)!" "}
                                        </td>
                                        <td>
                                        ${(ofcOrderDTO.notes)!" "}
                                        </td>
                                        <td>
                                        ${(ofcOrderDTO.goodsType)!" "}
                                        </td>
                                        <td>
                                        ${(ofcOrderDTO.departurePlace)!" "}
                                        </td>
                                        <td>
                                        ${(ofcOrderDTO.destination)!" "}
                                        </td>
                                    </tr>
                                    </tbody>
                                </table>
                                <table id="dynamic-table" class="table table-striped table-bordered table-hover dataTable no-footer" role="grid" aria-describedby="dynamic-table_info">
                                    <thead>
                                    <tr role="row">
                                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">数量</th>
                                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">重量</th>
                                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">体积</th>
                                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">运输要求</th>
                                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">运输单号</th>
                                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">车牌号</th>
                                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">司机姓名</th>
                                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">联系电话</th>
                                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">收货地址</th>
                                    </thead>
                                    <tbody>
                                    <#--
                                            vo  value object  拼出来的结果集
                                            dto  参数,给后端当参数,或者返回的dot.
                                            -->
                                    <tr role="row" class="odd">
                                        <td>
                                        ${(ofcOrderDTO.quantity)!" "}
                                        </td>
                                        <td>
                                        ${(ofcOrderDTO.weight)!" "}
                                        </td>
                                        <td>
                                        ${(ofcOrderDTO.cubage)!" "}
                                        </td>
                                        <td>
                                        ${(ofcOrderDTO.transRequire)!" "}
                                        </td>
                                        <td>
                                        ${(ofcOrderDTO.transCode)!" "}
                                        </td>
                                        <td>
                                        ${(ofcOrderDTO.plateNumber)!" "}
                                        </td>
                                        <td>
                                        ${(ofcOrderDTO.driverName)!" "}
                                        </td>
                                        <td>
                                        ${(ofcOrderDTO.contactNumber)!" "}
                                        </td>
                                        <td>
                                        ${(ofcOrderDTO.odestination)!" "}
                                        </td>
                                    </tr>
                                    </tbody>
                                </table>



                                <hr />
                                <table id="dynamic-table" class="table table-striped table-bordered table-hover dataTable no-footer" role="grid" aria-describedby="dynamic-table_info">
                                    <thead>
                                    <tr role="row">
                                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">订单状态</th>
                                    </thead>
                                    <tbody>
                                    <#--
                                            vo  value object  拼出来的结果集
                                            dto  参数,给后端当参数,或者返回的dot.
                                            -->
                                    <#list orderStatusList! as orderStatus>
                                    <tr role="row" class="odd">
                                        <td>
                                            . ${(orderStatus.notes)!"null"}
                                        </td>
                                    </tr>
                                    </#list>
                                    </tbody>
                                </table>
                            </div><!-- /.dynamic-table_wrapper -->
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
    var scripts = [ null, "", null ]
    $(".page-content-area").ace_ajax("loadScripts", scripts, function() {
        $(document).ready(main);
    });

    function main(){
        /**
         * var code=$("input[name='code']").val();
         if(code==""||code==null){
            alert("请先输入数据再进行查询");
            return false;
        }else{
         */
        //初始化页面数据
        initPageData();
        // 查询
        $("#followOrderFormBtn").click(function () {
            var code=$("#code").val();
            var followTag = $("#followTag").val();
            if(code==""||code==null){
                alert("请先输入编号再进行查询");
                return false;
            }else {
                var url = "/ofc/orderFollowCon/" + code + "/" + followTag;
                xescm.common.loadPage(url);
            }
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
<script type="text/javascript">

   /* function orderFollow() {
        /!*进行非空校验，然后进行查询*!/
        var code=$("input[name='code']").val();
        if(code==""||code==null){
            alert("请先输入数据再进行查询");
            return false;
        }else{

            CommonClient.post(sys.rootPath + "/ofc/orderFollowCon", $("#followOrderForm").serialize(), function(data) {
                xescm.common.loadPage("");
            }

        }
    }*/
</script>
</body></html>