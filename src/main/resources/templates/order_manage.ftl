<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:th="http://www.thymeleaf.org"
      xmlns:sec="http://www.thymeleaf.org/thymeleaf-extras-springsecurity3">
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta charset="utf-8">
    <title>订单管理</title>

    <meta name="description" content="Static &amp; Dynamic Tables">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
    <script src="components/bootbox.js/bootbox.js"></script>
    <script src="js/jquery.js"></script>
    <script language="javascript" type="text/javascript" src="js/My97DatePicker/WdatePicker.js"></script>
    <!-- bootstrap & fontawesome -->
    <link rel="stylesheet" href="assets/css/bootstrap.css">
    <link rel="stylesheet" href="components/font-awesome/css/font-awesome.css">

    <!-- page specific plugin styles -->

    <!-- text fonts -->
    <link rel="stylesheet" href="assets/css/ace-fonts.css">

    <!-- ace styles -->
    <link rel="stylesheet" href="assets/css/ace.css" class="ace-main-stylesheet" id="main-ace-style">

    <!--[if lte IE 9]>
    <link rel="stylesheet" href="assets/css/ace-part2.css" class="ace-main-stylesheet" />
    <![endif]-->
    <link rel="stylesheet" href="assets/css/ace-skins.css">
    <link rel="stylesheet" href="assets/css/ace-rtl.css">

    <!--[if lte IE 9]>
    <link rel="stylesheet" href="assets/css/ace-ie.css" />
    <![endif]-->

    <!-- inline styles related to this page -->

    <!-- ace settings handler -->
    <script src="assets/js/ace-extra.js"></script><style>@keyframes nodeInserted{from{outline-color:#fff}to{outline-color:#000}}@-moz-keyframes nodeInserted{from{outline-color:#fff}to{outline-color:#000}}@-webkit-keyframes nodeInserted{from{outline-color:#fff}to{outline-color:#000}}@-ms-keyframes nodeInserted{from{outline-color:#fff}to{outline-color:#000}}@-o-keyframes nodeInserted{from{outline-color:#fff}to{outline-color:#000}}.ace-save-state{animation-duration:10ms;-o-animation-duration:10ms;-ms-animation-duration:10ms;-moz-animation-duration:10ms;-webkit-animation-duration:10ms;animation-delay:0s;-o-animation-delay:0s;-ms-animation-delay:0s;-moz-animation-delay:0s;-webkit-animation-delay:0s;animation-name:nodeInserted;-o-animation-name:nodeInserted;-ms-animation-name:nodeInserted;-moz-animation-name:nodeInserted;-webkit-animation-name:nodeInserted}</style>

    <!-- HTML5shiv and Respond.js for IE8 to support HTML5 elements and media queries -->

    <!--[if lte IE 8]>
    <script src="components/html5shiv/dist/html5shiv.min.js"></script>
    <script src="components/respond/dest/respond.min.js"></script>
    <![endif]-->
</head>

<body class="no-skin">
<!-- #section:basics/navbar.layout -->


<!-- /section:basics/navbar.layout -->
<div class="main-container ace-save-state" id="main-container">
    <script type="text/javascript">
        try{ace.settings.loadState('main-container')}catch(e){}
    </script>


    <!-- /section:basics/sidebar -->
    <div class="main-content">
        <div class="main-content-inner">
            <!-- #section:basics/content.breadcrumbs -->
            <div class="breadcrumbs ace-save-state" id="breadcrumbs">
                <ul class="breadcrumb">
                    <li>
                        <i class="ace-icon fa fa-home home-icon"></i>
                        <a href="#">xescm</a>
                    </li>

                    <li>
                        <a href="#">ofc</a>
                    </li>
                    <li class="active">order_manage</li>
                </ul><!-- /.breadcrumb -->

                <!-- #section:basics/content.searchbox -->
                <div class="nav-search" id="nav-search">
                    <form class="form-search">
								<span class="input-icon">
									<input type="text" placeholder="Search ..." class="nav-search-input" id="nav-search-input" autocomplete="off">
									<i class="ace-icon fa fa-search nav-search-icon"></i>
								</span>
                    </form>
                </div><!-- /.nav-search -->

                <!-- /section:basics/content.searchbox -->
            </div>

            <!-- /section:basics/content.breadcrumbs -->
            <div class="page-content">
                <!-- #section:settings.box -->
                <div class="ace-settings-container" id="ace-settings-container">
                    <div class="btn btn-app btn-xs btn-warning ace-settings-btn" id="ace-settings-btn">
                        <i class="ace-icon fa fa-cog bigger-130"></i>
                    </div>

                    <div class="ace-settings-box clearfix" id="ace-settings-box">
                        <div class="pull-left width-50">
                            <!-- #section:settings.skins -->
                            <div class="ace-settings-item">
                                <div class="pull-left">
                                    <select id="skin-colorpicker" class="hide">
                                        <option data-skin="no-skin" value="#438EB9">#438EB9</option>
                                        <option data-skin="skin-1" value="#222A2D">#222A2D</option>
                                        <option data-skin="skin-2" value="#C6487E">#C6487E</option>
                                        <option data-skin="skin-3" value="#D0D0D0">#D0D0D0</option>
                                    </select><div class="dropdown dropdown-colorpicker">		<a data-toggle="dropdown" class="dropdown-toggle"><span class="btn-colorpicker" style="background-color:#438EB9"></span></a><ul class="dropdown-menu dropdown-caret"><li><a class="colorpick-btn selected" style="background-color:#438EB9;" data-color="#438EB9"></a></li><li><a class="colorpick-btn" style="background-color:#222A2D;" data-color="#222A2D"></a></li><li><a class="colorpick-btn" style="background-color:#C6487E;" data-color="#C6487E"></a></li><li><a class="colorpick-btn" style="background-color:#D0D0D0;" data-color="#D0D0D0"></a></li></ul></div>
                                </div>
                                <span>&nbsp; Choose Skin</span>
                            </div>

                            <!-- /section:settings.skins -->

                            <!-- #section:settings.navbar -->
                            <div class="ace-settings-item">
                                <input type="checkbox" class="ace ace-checkbox-2 ace-save-state" id="ace-settings-navbar" autocomplete="off">
                                <label class="lbl" for="ace-settings-navbar"> Fixed Navbar</label>
                            </div>

                            <!-- /section:settings.navbar -->

                            <!-- #section:settings.sidebar -->
                            <div class="ace-settings-item">
                                <input type="checkbox" class="ace ace-checkbox-2 ace-save-state" id="ace-settings-sidebar" autocomplete="off">
                                <label class="lbl" for="ace-settings-sidebar"> Fixed Sidebar</label>
                            </div>

                            <!-- /section:settings.sidebar -->

                            <!-- #section:settings.breadcrumbs -->
                            <div class="ace-settings-item">
                                <input type="checkbox" class="ace ace-checkbox-2 ace-save-state" id="ace-settings-breadcrumbs" autocomplete="off">
                                <label class="lbl" for="ace-settings-breadcrumbs"> Fixed Breadcrumbs</label>
                            </div>

                            <!-- /section:settings.breadcrumbs -->

                            <!-- #section:settings.rtl -->
                            <div class="ace-settings-item">
                                <input type="checkbox" class="ace ace-checkbox-2" id="ace-settings-rtl" autocomplete="off">
                                <label class="lbl" for="ace-settings-rtl"> Right To Left (rtl)</label>
                            </div>

                            <!-- /section:settings.rtl -->

                            <!-- #section:settings.container -->
                            <div class="ace-settings-item">
                                <input type="checkbox" class="ace ace-checkbox-2 ace-save-state" id="ace-settings-add-container" autocomplete="off">
                                <label class="lbl" for="ace-settings-add-container">
                                    Inside
                                    <b>.container</b>
                                </label>
                            </div>

                            <!-- /section:settings.container -->
                        </div><!-- /.pull-left -->

                        <div class="pull-left width-50">
                            <!-- #section:basics/sidebar.options -->
                            <div class="ace-settings-item">
                                <input type="checkbox" class="ace ace-checkbox-2" id="ace-settings-hover" autocomplete="off">
                                <label class="lbl" for="ace-settings-hover"> Submenu on Hover</label>
                            </div>

                            <div class="ace-settings-item">
                                <input type="checkbox" class="ace ace-checkbox-2" id="ace-settings-compact" autocomplete="off">
                                <label class="lbl" for="ace-settings-compact"> Compact Sidebar</label>
                            </div>

                            <div class="ace-settings-item">
                                <input type="checkbox" class="ace ace-checkbox-2" id="ace-settings-highlight" autocomplete="off">
                                <label class="lbl" for="ace-settings-highlight"> Alt. Active Item</label>
                            </div>

                            <!-- /section:basics/sidebar.options -->
                        </div><!-- /.pull-left -->
                    </div><!-- /.ace-settings-box -->
                </div><!-- /.ace-settings-container -->

                <!-- /section:settings.box -->
                <div class="page-header">
                    <h1>
                        订单管理
                    </h1>
                </div><!-- /.page-header -->

                <div class="row">
                    <div class="col-xs-12">

                        <div class="clearfix">
                            <div class="pull-right tableTools-container"><div class="dt-buttons btn-overlap btn-group"><a class="dt-button buttons-collection buttons-colvis btn btn-white btn-primary btn-bold" tabindex="0" aria-controls="dynamic-table" data-original-title="" title=""><span><i class="fa fa-search bigger-110 blue"></i> <span class="hidden">Show/hide columns</span></span></a><a class="dt-button buttons-copy buttons-html5 btn btn-white btn-primary btn-bold" tabindex="0" aria-controls="dynamic-table" data-original-title="" title=""><span><i class="fa fa-copy bigger-110 pink"></i> <span class="hidden">Copy to clipboard</span></span></a><a class="dt-button buttons-csv buttons-html5 btn btn-white btn-primary btn-bold" tabindex="0" aria-controls="dynamic-table" data-original-title="" title=""><span><i class="fa fa-database bigger-110 orange"></i> <span class="hidden">Export to CSV</span></span></a><a class="dt-button buttons-excel buttons-flash btn btn-white btn-primary btn-bold" tabindex="0" aria-controls="dynamic-table"><span><i class="fa fa-file-excel-o bigger-110 green"></i> <span class="hidden">Export to Excel</span></span><div style="position: absolute; left: 0px; top: 0px; width: 39px; height: 35px; z-index: 99;" data-original-title="" title=""><embed id="ZeroClipboard_TableToolsMovie_1" src="components/datatables.net-buttons-swf/index.swf" loop="false" menu="false" quality="best" bgcolor="#ffffff" width="39" height="35" name="ZeroClipboard_TableToolsMovie_1" align="middle" allowscriptaccess="always" allowfullscreen="false" type="application/x-shockwave-flash" pluginspage="http://www.macromedia.com/go/getflashplayer" flashvars="id=1&amp;width=39&amp;height=35" wmode="transparent"></div></a><a class="dt-button buttons-pdf buttons-flash btn btn-white btn-primary btn-bold" tabindex="0" aria-controls="dynamic-table"><span><i class="fa fa-file-pdf-o bigger-110 red"></i> <span class="hidden">Export to PDF</span></span><div style="position: absolute; left: 0px; top: 0px; width: 39px; height: 35px; z-index: 99;" data-original-title="" title=""><embed id="ZeroClipboard_TableToolsMovie_2" src="components/datatables.net-buttons-swf/index.swf" loop="false" menu="false" quality="best" bgcolor="#ffffff" width="39" height="35" name="ZeroClipboard_TableToolsMovie_2" align="middle" allowscriptaccess="always" allowfullscreen="false" type="application/x-shockwave-flash" pluginspage="http://www.macromedia.com/go/getflashplayer" flashvars="id=2&amp;width=39&amp;height=35" wmode="transparent"></div></a><a class="dt-button buttons-print btn btn-white btn-primary btn-bold" tabindex="0" aria-controls="dynamic-table" data-original-title="" title=""><span><i class="fa fa-print bigger-110 grey"></i> <span class="hidden">Print</span></span></a></div></div>
                        </div>
                        <div class="table-header">
                            筛选条件
                        </div>

                        <!-- div.table-responsive -->

                        <!-- div.dataTables_borderWrap -->
                        <div>
                            <div id="dynamic-table_wrapper" class="dataTables_wrapper form-inline no-footer">
                                <form action="/orderScreenByCondition" method="post" id="screenOrderForm">
                                    <input type="hidden" name="tag" value="manage"/>
                                <div class="row">

                                    <div id="dynamic-table_filter" class="dataTables_length">
                                        <label>
                                            &nbsp;&nbsp;&nbsp;订单日期:<input name="" type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" onClick="WdatePicker()">
                                            至<input type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table"onClick="WdatePicker()">
                                            订单编号:<input name="orderCode" type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table">
                                            客户订单编号:<input name="custOrderCode" type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table">
                                            订单状态:
                                            <select id="" name="orderStatus">
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
                                            <select id="" name="orderType">
                                                <option value="">----</option>
                                                <option value="60">运输订单</option>
                                                <option value="61">仓配订单</option>
                                            </select>
                                            业务类型:
                                            <select id="" name="businessType">
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

                                            <span class="btn btn-info btn-sm popover-info" data-rel="popover" data-placement="bottom" title="" data-content="Heads up! This alert needs your attention, but it's not super important." data-original-title="Some Info" onclick="document.getElementById('screenOrderForm').submit();">搜索</span>
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

                                            <button type="button" id="review" +${order_index} onclick="reviewOrder('${order.orderCode!"null"}','${order.orderStatus!"null"}')" class="btn btn-minier btn-yellow">审核</button>
                                            <button type="button" id="edit" +${order_index} onclick="editOrder('${order.orderCode!"null"}')"  class="btn btn-minier btn-success">编辑</button>
                                            <button type="button" id="cancel" +${order_index} onclick="deleteOrder('${order.orderCode!"null"}','${order.orderStatus!"null"}')"  class="btn btn-minier btn-danger">删除</button>
                                            <button type="button" id="rereview" +${order_index} onclick="reReviewOrder('${order.orderCode!"null"}','${order.orderStatus!"null"}')"  class="btn btn-minier btn-inverse">反审核</button>
                                            <button type="button" id="cancel" +${order_index} onclick="cancelOrder('${order.orderCode!"null"}','${order.orderStatus!"null"}')"  class="btn btn-minier btn-default">取消</button>

                                        </td>


                                        <td>
                                            <a href="#">${order.orderCode!"null"}</a>
                                        </td>
                                        <td>${order.custOrderCode!"null"}</td>
                                        <td class="hidden-480">${order.orderTime?string("yyyy-MM-dd HH:mm:ss")}</td>
                                        <td>
                                            <#if order.orderType ??><#if order.orderType == '60'>运输订单</#if></#if>
                                            <#if order.orderType ??><#if order.orderType == '61'>仓储订单</#if></#if>
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

<div class="footer">
    <div class="footer-inner">
        <!-- #section:basics/footer -->
        <div class="footer-content">
						<span class="bigger-120">
							<span class="blue bolder">Xescm</span>
							Application © 2016
						</span>

            &nbsp; &nbsp;
            <span class="action-buttons">
							<a href="#">
								<i class="ace-icon fa fa-twitter-square light-blue bigger-150"></i>
							</a>

							<a href="#">
								<i class="ace-icon fa fa-facebook-square text-primary bigger-150"></i>
							</a>

							<a href="#">
								<i class="ace-icon fa fa-rss-square orange bigger-150"></i>
							</a>
						</span>
        </div>

        <!-- /section:basics/footer -->
    </div>
</div>

<a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
    <i class="ace-icon fa fa-angle-double-up icon-only bigger-110"></i>
</a>
</div><!-- /.main-container -->


<!-- basic scripts -->

<!--[if !IE]> -->
<script src="components/jquery/dist/jquery.js"></script>

<!-- <![endif]-->

<!--[if IE]>
<script src="components/jquery.1x/dist/jquery.js"></script>
<![endif]-->
<script type="text/javascript">
    if('ontouchstart' in document.documentElement) document.write("<script src='components/_mod/jquery.mobile.custom/jquery.mobile.custom.js'>"+"<"+"/script>");
</script>
<script src="components/bootstrap/dist/js/bootstrap.js"></script>

<!-- page specific plugin scripts -->
<script src="components/datatables/media/js/jquery.dataTables.js"></script>
<script src="components/_mod/datatables/jquery.dataTables.bootstrap.js"></script>
<script src="components/datatables.net-buttons/js/dataTables.buttons.js"></script>
<script src="components/datatables.net-buttons/js/buttons.flash.js"></script>
<script src="components/datatables.net-buttons/js/buttons.html5.js"></script>
<script src="components/datatables.net-buttons/js/buttons.print.js"></script>
<script src="components/datatables.net-buttons/js/buttons.colVis.js"></script>
<script src="components/datatables.net-select/js/dataTables.select.js"></script>

<!-- ace scripts -->
<script src="assets/js/src/elements.scroller.js"></script>
<script src="assets/js/src/elements.colorpicker.js"></script>
<script src="assets/js/src/elements.fileinput.js"></script>
<script src="assets/js/src/elements.typeahead.js"></script>
<script src="assets/js/src/elements.wysiwyg.js"></script>
<script src="assets/js/src/elements.spinner.js"></script>
<script src="assets/js/src/elements.treeview.js"></script>
<script src="assets/js/src/elements.wizard.js"></script>
<script src="assets/js/src/elements.aside.js"></script>
<script src="assets/js/src/ace.js"></script>
<script src="assets/js/src/ace.basics.js"></script>
<script src="assets/js/src/ace.scrolltop.js"></script>
<script src="assets/js/src/ace.ajax-content.js"></script>
<script src="assets/js/src/ace.touch-drag.js"></script>
<script src="assets/js/src/ace.sidebar.js"></script>
<script src="assets/js/src/ace.sidebar-scroll-1.js"></script>
<script src="assets/js/src/ace.submenu-hover.js"></script>
<script src="assets/js/src/ace.widget-box.js"></script>
<script src="assets/js/src/ace.settings.js"></script>
<script src="assets/js/src/ace.settings-rtl.js"></script>
<script src="assets/js/src/ace.settings-skin.js"></script>
<script src="assets/js/src/ace.widget-on-reload.js"></script>
<script src="assets/js/src/ace.searchbox-autocomplete.js"></script>

<!-- inline scripts related to this page -->
<script type="text/javascript">
    function editOrder(orderCode) {
        /*跳转到订单的可编辑页(跟下单页面一样!), 并回显该订单数据*/
        $("#screenOrderForm").attr("action","/getOrderDetailByCode?dtotag=orderCode&orderCode="+orderCode);
        $("#screenOrderForm").submit();
    }

    function deleteOrder(ordercode,orderStatus) {
        var result  = confirm("您确定要删除此订单?");
        if(result == true) {
            $.get("/orderDelete",{"orderCode":ordercode,"orderStatus":orderStatus},function (data) {
                $("#confirmBox").modal('hide');
                if(data == 200){
                    window.location.href="/orderScreenByCondition?tag=manage";
                } else {
                    alert("删除订单失败,请联系管理员!");
                }
            });
        }


    }

    function reviewOrder(ordercode,orderStatus) {

        var result  = confirm("您确定要审核订单?");
        if(result == true) {
            $.get("/orderOrNotAudit",{"orderCode":ordercode,"orderStatus":orderStatus,"reviewTag":"review"},function (data) {
                if(data == 200){
                    window.location.href="/orderScreenByCondition?tag=manage";
                } else {
                    alert("审核订单失败,请联系管理员!");
                }
            });
        }

    }
    function reReviewOrder(ordercode,orderStatus) {
        var result  = confirm("您确定要反审核此订单?");
        if(result == true) {
            $.get("/orderOrNotAudit",{"orderCode":ordercode,"orderStatus":orderStatus,"reviewTag":"rereview"},function (data) {
                if(data == 200){
                    window.location.href="/orderScreenByCondition?tag=manage";
                } else {
                    alert("反审核订单失败,请联系管理员!");
                }
            });
        }
    }
    function cancelOrder(ordercode,orderStatus) {
        var result  = confirm("您确定要取消此订单?");
        if(result == true) {
            $.get("/orderCancel",{"orderCode":ordercode,"orderStatus":orderStatus},function (data) {

                if(data == 200){
                    window.location.href="/orderScreenByCondition?tag=manage";
                } else {
                    alert("取消订单失败,请联系管理员!");
                }
            });
        }

    }
    jQuery(function($) {



        $.fn.dataTable.Buttons.swfPath = "components/datatables.net-buttons-swf/index.swf"; //in Ace demo components will be replaced by correct assets path
        $.fn.dataTable.Buttons.defaults.dom.container.className = 'dt-buttons btn-overlap btn-group btn-overlap';


        ////

        setTimeout(function() {
            $($('.tableTools-container')).find('a.dt-button').each(function() {
                var div = $(this).find(' > div').first();
                if(div.length == 1) div.tooltip({container: 'body', title: div.parent().text()});
                else $(this).tooltip({container: 'body', title: $(this).text()});
            });
        }, 500);





        /////////////////////////////////
        //table checkboxes
        $('th input[type=checkbox], td input[type=checkbox]').prop('checked', false);


        //And for the first simple table, which doesn't have TableTools or dataTables
        //select/deselect all rows according to table header checkbox
        var active_class = 'active';
        $('#simple-table > thead > tr > th input[type=checkbox]').eq(0).on('click', function(){
            var th_checked = this.checked;//checkbox inside "TH" table header

            $(this).closest('table').find('tbody > tr').each(function(){
                var row = this;
                if(th_checked) $(row).addClass(active_class).find('input[type=checkbox]').eq(0).prop('checked', true);
                else $(row).removeClass(active_class).find('input[type=checkbox]').eq(0).prop('checked', false);
            });
        });

        //select/deselect a row when the checkbox is checked/unchecked
        $('#simple-table').on('click', 'td input[type=checkbox]' , function(){
            var $row = $(this).closest('tr');
            if($row.is('.detail-row ')) return;
            if(this.checked) $row.addClass(active_class);
            else $row.removeClass(active_class);
        });



        /********************************/
        //add tooltip for small view action buttons in dropdown menu
        $('[data-rel="tooltip"]').tooltip({placement: tooltip_placement});

        //tooltip placement on right or left
        function tooltip_placement(context, source) {
            var $source = $(source);
            var $parent = $source.closest('table')
            var off1 = $parent.offset();
            var w1 = $parent.width();

            var off2 = $source.offset();
            //var w2 = $source.width();

            if( parseInt(off2.left) < parseInt(off1.left) + parseInt(w1 / 2) ) return 'right';
            return 'left';
        }




        /***************/
        $('.show-details-btn').on('click', function(e) {
            e.preventDefault();
            $(this).closest('tr').next().toggleClass('open');
            $(this).find(ace.vars['.icon']).toggleClass('fa-angle-double-down').toggleClass('fa-angle-double-up');
        });
        /***************/





        /**
         //add horizontal scrollbars to a simple table
         $('#simple-table').css({'width':'2000px', 'max-width': 'none'}).wrap('<div style="width: 1000px;" />').parent().ace_scroll(
         {
           horizontal: true,
           styleClass: 'scroll-top scroll-dark scroll-visible',//show the scrollbars on top(default is bottom)
           size: 2000,
           mouseWheelLock: true
         }
         ).css('padding-top', '12px');
         */

    })
</script>

<!-- the following scripts are used in demo only for onpage help and you don't need them -->
<link rel="stylesheet" href="assets/css/ace.onpage-help.css">
<link rel="stylesheet" href="docs/assets/js/themes/sunburst.css">

<script type="text/javascript"> ace.vars['base'] = '..'; </script>
<script src="assets/js/src/elements.onpage-help.js"></script>
<script src="assets/js/src/ace.onpage-help.js"></script>
<script src="docs/assets/js/rainbow.js"></script>
<script src="docs/assets/js/language/generic.js"></script>
<script src="docs/assets/js/language/html.js"></script>
<script src="docs/assets/js/language/css.js"></script>
<script src="docs/assets/js/language/javascript.js"></script>

</body></html>