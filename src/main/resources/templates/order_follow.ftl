
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
                                            <label>
                                                <div class="col-sm-6">
                                                    <div class="clearfix">

                                                        <select class="chosen-select form-control" id="followTag" name="followTag">
                                                            <option value="orderCode">订单编号</option>
                                                            <option value="custOrderCode">客户订单编号</option>
                                                            <option value="transCode">运输单号</option>
                                                        </select>
                                                    </div>
                                                </div>



                                                <div class="col-sm-6">
                                                    <div class="clearfix">
                                                        <input  id = "code" name="code" type="search" style="color: black" class="form-control input-sm" placeholder="" aria-controls="dynamic-table">
                                                    </div>
                                                </div>

                                            </label>

                                        </div>
                                        <div class="form-group">
                                            <span  class="btn btn-info btn-sm popover-info" data-rel="popover" data-placement="bottom" title="" data-content="Heads up! This alert needs your attention, but it's not super important." data-original-title="Some Info" id="followOrderFormBtn">查询</span>
                                        </div>



                                    </form>
                                </div>
                            </div>
                        </div>
                        <!-- div.table-responsive -->

                        <!-- div.dataTables_borderWrap -->
                        <div>
                            <div id="dynamic-table_wrapper" class="dataTables_wrapper form-inline no-footer">

                                <div class="table-header">
                                    订单跟踪记录
                                </div>



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
<link rel="stylesheet" href="../components/chosen/chosen.css" />
<script src="../components/chosen/chosen.jquery.js"></script>
</body>
