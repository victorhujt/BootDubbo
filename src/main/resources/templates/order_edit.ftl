<!DOCTYPE html>
<#assign base=request.contextPath />
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:th="http://www.thymeleaf.org"
      xmlns:sec="http://www.thymeleaf.org/thymeleaf-extras-springsecurity3">
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta charset="utf-8">
    <title>订单编辑</title>

    <meta name="description" content="Static &amp; Dynamic Tables">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">

    <script src="${base}/js/jquery.js"></script>
    <script language="javascript" type="text/javascript" src="${base}/js/My97DatePicker/WdatePicker.js"></script>
    <!-- bootstrap & fontawesome -->
    <link rel="stylesheet" href="${base}/assets/css/bootstrap.css">
    <link rel="stylesheet" href="${base}/components/font-awesome/css/font-awesome.css">

    <style type="text/css">
        #goodsListDiv {

            position:fixed;

            left:138px;

            top:91px;

            width:946px;

            height:294px;

            z-index:3;

            border:solid #7A7A7A 4px;

        }

        #consignorListDiv {
            position:fixed;

            left:138px;

            top:91px;

            width:946px;

            height:294px;

            z-index:3;

            border:solid #7A7A7A 4px;
        }

        #consigneeListDiv {
            position:fixed;

            left:138px;

            top:91px;

            width:946px;

            height:294px;

            z-index:3;

            border:solid #7A7A7A 4px;
        }


        #supportListDiv {
            position:fixed;

            left:138px;

            top:91px;

            width:946px;

            height:294px;

            z-index:3;

            border:solid #7A7A7A 4px;
        }
    </style>

    <!-- page specific plugin styles -->

    <!-- text fonts -->
    <link rel="stylesheet" href="${base}/assets/css/ace-fonts.css">

    <!-- ace styles -->
    <link rel="stylesheet" href="${base}/assets/css/ace.css" class="ace-main-stylesheet" id="main-ace-style">

    <!--[if lte IE 9]>
    <link rel="stylesheet" href="${base}/assets/css/ace-part2.css" class="ace-main-stylesheet" />
    <![endif]-->
    <link rel="stylesheet" href="${base}/assets/css/ace-skins.css">
    <link rel="stylesheet" href="${base}/assets/css/ace-rtl.css">

    <!--[if lte IE 9]>
    <link rel="stylesheet" href="${base}/assets/css/ace-ie.css" />
    <![endif]-->

    <!-- inline styles related to this page -->

    <!-- ace settings handler -->
    <script src="${base}/assets/js/ace-extra.js"></script><style>@keyframes nodeInserted{from{outline-color:#fff}to{outline-color:#000}}@-moz-keyframes nodeInserted{from{outline-color:#fff}to{outline-color:#000}}@-webkit-keyframes nodeInserted{from{outline-color:#fff}to{outline-color:#000}}@-ms-keyframes nodeInserted{from{outline-color:#fff}to{outline-color:#000}}@-o-keyframes nodeInserted{from{outline-color:#fff}to{outline-color:#000}}.ace-save-state{animation-duration:10ms;-o-animation-duration:10ms;-ms-animation-duration:10ms;-moz-animation-duration:10ms;-webkit-animation-duration:10ms;animation-delay:0s;-o-animation-delay:0s;-ms-animation-delay:0s;-moz-animation-delay:0s;-webkit-animation-delay:0s;animation-name:nodeInserted;-o-animation-name:nodeInserted;-ms-animation-name:nodeInserted;-moz-animation-name:nodeInserted;-webkit-animation-name:nodeInserted}</style>

    <!-- HTML5shiv and Respond.js for IE8 to support HTML5 elements and media queries -->

    <!--[if lte IE 8]>
    <script src="${base}/components/html5shiv/dist/html5shiv.min.js"></script>
    <script src="${base}/components/respond/dest/respond.min.js"></script>
    <![endif]-->
</head>

<body class="no-skin">
<!-- #section:basics/navbar.layout -->
<!--goodsListDiv-->
<div class="modal-content" id="goodsListDiv" style="display: none;">
    <div class="modal-header"><span id="goodsListDivNoneTop" style="cursor:pointer"><button type="button" id="" style="cursor:pointer" class="bootbox-close-button close" data-dismiss="modal" aria-hidden="true">×</button></span>
        <h4 class="modal-title">货品列表</h4></div>
    <div class="modal-body">
        <div class="bootbox-body">
            <form class="bootbox-form">
                <table id="dynamic-table" class="table table-striped table-bordered table-hover dataTable no-footer" role="grid" aria-describedby="dynamic-table_info">
                    <thead>
                    <tr role="row"><th class="center sorting_disabled" rowspan="1" colspan="1" aria-label="">
                        <label class="pos-rel">
                            <input type="checkbox" class="ace">
                            <span class="lbl"></span>
                        </label>
                    </th>
                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Domain: activate to sort column ascending">序号</th>
                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Price: activate to sort column ascending">货品编码</th>
                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">货品名称</th>
                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">货品规格</th>
                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">单位</th>

                    </thead>
                    <tbody>
                    <#list orderList! as order>
                    <tr role="row" class="odd">
                        <td class="center">
                            <label class="pos-rel">
                                <input type="checkbox" class="ace">
                                <span class="lbl"></span>
                            </label>
                        </td>


                        <td>

                        </td>
                        <td>${order.custOrderCode!"null"}</td>
                        <td class="hidden-480">

                        </td>
                        <td>

                        </td>
                        <td class="hidden-480">

                        </td>

                    </tr>
                    </#list>
                    </tbody>
                </table>


            </form>
        </div>
    </div>
    <div class="modal-footer"><span id="goodsListDivNoneBottom" style="cursor:pointer"><button  data-bb-handler="cancel" type="button" class="btn btn-default">Cancel</button></span><button data-bb-handler="confirm" type="button" class="btn btn-primary">OK</button></div>
</div>
<!--consignorListDiv-->
<div class="modal-content" id="consignorListDiv" style="display: none;">
    <div class="modal-header"><span id="consignorListDivNoneTop" style="cursor:pointer"><button type="button" id="" style="cursor:pointer" class="bootbox-close-button close" data-dismiss="modal" aria-hidden="true">×</button></span>
        <h4 class="modal-title">发货方联系人</h4></div>
    <div class="modal-body">
        <div class="bootbox-body">
            <form class="bootbox-form">
                <table id="dynamic-table" class="table table-striped table-bordered table-hover dataTable no-footer" role="grid" aria-describedby="dynamic-table_info">
                    <thead>
                    <tr role="row"><th class="center sorting_disabled" rowspan="1" colspan="1" aria-label="">
                        <label class="pos-rel">
                            选择
                            <span class="lbl"></span>
                        </label>
                    </th>
                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Domain: activate to sort column ascending">序号</th>
                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Price: activate to sort column ascending">名称</th>
                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">联系人</th>
                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">联系电话</th>
                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">传真</th>
                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">Email</th>
                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">邮编</th>
                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">地址</th>

                    </thead>
                    <tbody>
                    <#list orderList! as order>
                    <tr role="row" class="odd">
                        <td class="center">
                            <label class="pos-rel">
                                <input type="radio" class="ace">
                                <span class="lbl"></span>
                            </label>
                        </td>


                        <td>

                        </td>
                        <td>${order.custOrderCode!"null"}</td>
                        <td class="hidden-480">

                        </td>
                        <td>

                        </td>
                        <td class="hidden-480">

                        </td>
                        <td class="hidden-480">

                        </td>
                        <td class="hidden-480">

                        </td>
                        <td class="hidden-480">

                        </td>

                    </tr>
                    </#list>
                    </tbody>
                </table>


            </form>
        </div>
    </div>
    <div class="modal-footer"><span id="consignorListDivNoneBottom" style="cursor:pointer"><button  data-bb-handler="cancel" type="button" class="btn btn-default">Cancel</button></span><button data-bb-handler="confirm" type="button" class="btn btn-primary">OK</button></div>
</div>

<!--consigneeListDiv-->
<div class="modal-content" id="consigneeListDiv" style="display: none;">
    <div class="modal-header"><span id="consigneeListDivNoneTop" style="cursor:pointer"><button type="button" id="" style="cursor:pointer" class="bootbox-close-button close" data-dismiss="modal" aria-hidden="true">×</button></span>
        <h4 class="modal-title">收货方联系人</h4></div>
    <div class="modal-body">
        <div class="bootbox-body">
            <form class="bootbox-form">
                <table id="dynamic-table" class="table table-striped table-bordered table-hover dataTable no-footer" role="grid" aria-describedby="dynamic-table_info">
                    <thead>
                    <tr role="row"><th class="center sorting_disabled" rowspan="1" colspan="1" aria-label="">
                        <label class="pos-rel">
                            选择
                            <span class="lbl"></span>
                        </label>
                    </th>
                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Domain: activate to sort column ascending">序号</th>
                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Price: activate to sort column ascending">名称</th>
                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">联系人</th>
                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">联系电话</th>
                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">传真</th>
                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">Email</th>
                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">邮编</th>
                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">地址</th>
                    </thead>
                    <tbody>
                    <#list orderList! as order>
                    <tr role="row" class="odd">
                        <td class="center">
                            <label class="pos-rel">
                                <input type="radio" class="ace">
                                <span class="lbl"></span>
                            </label>
                        </td>


                        <td>

                        </td>
                        <td>${order.custOrderCode!"null"}</td>
                        <td class="hidden-480">

                        </td>
                        <td>

                        </td>
                        <td class="hidden-480">

                        </td>
                        <td class="hidden-480">

                        </td>
                        <td class="hidden-480">

                        </td>
                        <td class="hidden-480">

                        </td>

                    </tr>
                    </#list>
                    </tbody>
                </table>


            </form>
        </div>
    </div>
    <div class="modal-footer"><span id="consigneeListDivNoneBottom" style="cursor:pointer"><button  data-bb-handler="cancel" type="button" class="btn btn-default">Cancel</button></span><button data-bb-handler="confirm" type="button" class="btn btn-primary">OK</button></div>
</div>


<!--supportListDiv-->
<div class="modal-content" id="supportListDiv" style="display: none;">
    <div class="modal-header"><span id="supportListDivNoneTop" style="cursor:pointer"><button type="button" id="" style="cursor:pointer" class="bootbox-close-button close" data-dismiss="modal" aria-hidden="true">×</button></span>
        <h4 class="modal-title">供应商联系人</h4></div>
    <div class="modal-body">
        <div class="bootbox-body">
            <form class="bootbox-form">
                <table id="dynamic-table" class="table table-striped table-bordered table-hover dataTable no-footer" role="grid" aria-describedby="dynamic-table_info">
                    <thead>
                    <tr role="row"><th class="center sorting_disabled" rowspan="1" colspan="1" aria-label="">
                        <label class="pos-rel">
                            选择
                            <span class="lbl"></span>
                        </label>
                    </th>
                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Domain: activate to sort column ascending">序号</th>
                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Price: activate to sort column ascending">名称</th>
                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">联系人</th>
                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">联系电话</th>
                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">传真</th>
                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">Email</th>
                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">邮编</th>
                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">地址</th>

                    </thead>
                    <tbody>
                    <#list orderList! as order>
                    <tr role="row" class="odd">
                        <td class="center">
                            <label class="pos-rel">
                                <input type="radio" class="ace">
                                <span class="lbl"></span>
                            </label>
                        </td>


                        <td>

                        </td>
                        <td>${order.custOrderCode!"null"}</td>
                        <td class="hidden-480">

                        </td>
                        <td>

                        </td>
                        <td class="hidden-480">

                        </td>

                    </tr>
                    </#list>
                    </tbody>
                </table>


            </form>
        </div>
    </div>
    <div class="modal-footer"><span id="supportListDivNoneBottom" style="cursor:pointer"><button  data-bb-handler="cancel" type="button" class="btn btn-default">Cancel</button></span><button data-bb-handler="confirm" type="button" class="btn btn-primary">OK</button></div>
</div>

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
                    <li class="active">order_place</li>
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
                                    </select><div class="dropdown dropdown-colorpicker">        <a data-toggle="dropdown" class="dropdown-toggle"><span class="btn-colorpicker" style="background-color:#438EB9"></span></a><ul class="dropdown-menu dropdown-caret"><li><a class="colorpick-btn selected" style="background-color:#438EB9;" data-color="#438EB9"></a></li><li><a class="colorpick-btn" style="background-color:#222A2D;" data-color="#222A2D"></a></li><li><a class="colorpick-btn" style="background-color:#C6487E;" data-color="#C6487E"></a></li><li><a class="colorpick-btn" style="background-color:#D0D0D0;" data-color="#D0D0D0"></a></li></ul></div>
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
                        订单编辑
                    </h1>
                </div><!-- /.page-header -->

                <div class="row">
                    <form action="/ofc/orderPlaceCon" method="post" id="orderPlaceConTable">
                        <div class="col-xs-12">

                            <div class="clearfix">

                            </div>
                            <div class="table-header">
                                基本信息
                            </div>

                            <!-- div.table-responsive -->

                            <!-- div.dataTables_borderWrap -->
                            <div>
                                <div id="dynamic-table_wrapper" class="dataTables_wrapper form-inline no-footer">
                                    <form action="" method="post" id="">
                                        <input type="hidden" name="tag" value="manage">
                                            <input type="hidden" name="orderCode" <#if orderInfo.orderCode?? >value="${orderInfo.orderCode}"</#if>">
                                        <div class="row">

                                            <div id="dynamic-table_filter" class="dataTables_length">
                                                <label>
                                                    &nbsp;&nbsp;&nbsp;订单日期:<input name="orderTime" <#if orderInfo.orderTime?? >value="${((orderInfo.orderTime)?string('yyyy-MM-dd HH:mm:ss'))!}"</#if> type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" onClick="WdatePicker({isShowClear:true,readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})">


                                                    客户订单编号:<input name="custOrderCode" type="text" <#if orderInfo.custOrderCode?? >value="${orderInfo.custOrderCode}"</#if>>
                                                    &nbsp;&nbsp;&nbsp;
                                                    订单类型:

                                                    <select id="orderType" name="orderType" value="${(orderInfo.orderType)!"60"}">

                                                        <option value="60" <#if orderInfo.orderType?? ><#if ((orderInfo.orderType)== '60')>selected="selected"</#if></#if>>运输订单</option>
                                                        <option value="61" <#if orderInfo.orderType?? ><#if ((orderInfo.orderType)== '61')>selected="selected"</#if></#if>>仓配订单</option>
                                                    </select>
                                                    <span id="businessTypeDiv" style="display: none">
                                                        业务类型:
                                                        <select id="" name="businessType">

                                                            <option value="610" <#if orderInfo.businessType?? ><#if ((orderInfo.businessType)== '610')>selected="selected"</#if></#if>>销售出库</option>
                                                            <option value="611" <#if orderInfo.businessType?? ><#if ((orderInfo.businessType)== '611')>selected="selected"</#if></#if>>调拨出库</option>
                                                            <option value="612" <#if orderInfo.businessType?? ><#if ((orderInfo.businessType)== '612')>selected="selected"</#if></#if>>报损出库</option>
                                                            <option value="613" <#if orderInfo.businessType?? ><#if ((orderInfo.businessType)== '613')>selected="selected"</#if></#if>>其他出库</option>
                                                            <option value="----------">----------</option>
                                                            <option value="620" <#if orderInfo.businessType?? ><#if ((orderInfo.businessType)== '620')>selected="selected"</#if></#if>>采购入库</option>
                                                            <option value="621" <#if orderInfo.businessType?? ><#if ((orderInfo.businessType)== '621')>selected="selected"</#if></#if>>调拨入库</option>
                                                            <option value="622" <#if orderInfo.businessType?? ><#if ((orderInfo.businessType)== '622')>selected="selected"</#if></#if>>退货入库</option>
                                                            <option value="623" <#if orderInfo.businessType?? ><#if ((orderInfo.businessType)== '623')>selected="selected"</#if></#if>>加工入库</option>
                                                        </select>
                                                    </span>
                                                    <span id="provideTransportDiv" style="display: none">
                                                        是否需要运输
                                                        <input id="provideTransport" type="checkbox" name = "" <#if orderInfo.provideTransport?? ><#if ((orderInfo.provideTransport) == 1)> checked="checked"</#if></#if>/>
                                                    </span>
                                                    <input id="provideTransport1" type="hidden" name="provideTransport" value="${(orderInfo.provideTransport)!""}"/>
                                                    店铺:
                                                    <select id="" name="storeCode" value="${(orderInfo.storeName)!""}">

                                                        <option value="线下销售" <#if orderInfo.storeName?? ><#if ((orderInfo.storeName)== '线下销售')>selected="selected"</#if></#if>>线下销售</option>
                                                        <option value="众品天猫生鲜旗舰店" <#if orderInfo.storeName?? ><#if ((orderInfo.storeName)== '众品天猫生鲜旗舰店')>selected="selected"</#if></#if>>众品天猫生鲜旗舰店</option>
                                                        <option value="众品京东旗舰店" <#if orderInfo.storeName?? ><#if ((orderInfo.storeName)== '众品京东旗舰店')>selected="selected"</#if></#if>>众品京东旗舰店</option>
                                                    </select>


                                                </label>
                                            </div>

                                            <div id="dynamic-table_filter" class="dataTables_length">
                                                <label>
                                                    &nbsp;&nbsp;&nbsp;备注:<input  name="notes" value="${(orderInfo.notes)!""}"  type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table">
                                                </label>
                                            </div>

                                            <br/>

                                        </div>
                                    </form>


                                    <div class="col-sm-6">
                                        <!-- #section:elements.tab.option -->
                                        <div class="tabbable" style="float: left; text-align: left; margin: 0 auto; width: 1300px;" >
                                            <ul class="nav nav-tabs padding-12 tab-color-blue background-blue" id="myTab4">
                                                <li class="active">
                                                    <a data-toggle="tab" href="#home4" aria-expanded="false">货品明细</a>
                                                </li>

                                                <li class="tranfr">
                                                    <a data-toggle="tab" href="#profile4" aria-expanded="true">运输信息</a>
                                                </li>

                                                <li class="storeLi" style="display:none">
                                                    <a data-toggle="tab" href="#dropdown14" aria-expanded="false">仓配信息</a>
                                                </li>
                                            </ul>

                                            <div class="tab-content">
                                                <div id="home4" class="tab-pane active">

                                                    <!--货品明细-->

                                                    <span style="cursor:pointer" id="goodsListDivBlock"><button type="button" class="btn btn-info" id="bootbox-confirm">添加货品</button></span>

                                                    <table id="dynamic-table" class="table table-striped table-bordered table-hover dataTable no-footer" role="grid" aria-describedby="dynamic-table_info">
                                                        <thead>
                                                        <tr role="row"><th class="center sorting_disabled" rowspan="1" colspan="1" aria-label="">
                                                            操作
                                                        </th>
                                                            <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Status: activate to sort column ascending">序号</th>
                                                            <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Domain: activate to sort column ascending">货品编码</th>
                                                            <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Price: activate to sort column ascending">货品名称</th>
                                                            <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Update: activate to sort column ascending">货品规格
                                                            </th>
                                                            <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">单位</th>
                                                            <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">数量</th>
                                                            <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">生产批次</th>
                                                            <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">
                                                                <i class="ace-icon fa fa-clock-o bigger-110 hidden-480"></i>
                                                                生产日期</th>
                                                            <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">
                                                                <i class="ace-icon fa fa-clock-o bigger-110 hidden-480"></i>
                                                                失效日期</th>


                                                        </thead>
                                                        <tbody>
                                                        <#--货品明细-->
                                                        <#list ofcGoodsDetailsList! as goodsDetails>
                                                        <tr role="row" class="odd">
                                                            <td class="center">
                                                                <button type="button" id=""    class="btn btn-minier btn-danger" onclick="deleteGoods('${(goodsDetails.orderCode)!"null"}','${goodsDetails.goodsCode!"null"}')">删除</button>
                                                            </td>
                                                            <td>
                                                                序号

                                                            </td>

                                                            <td>
                                                                <input name="goodsCode" type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" size="6" value="${(goodsDetails.goodsCode)!""}">
                                                            </td>
                                                            <td>
                                                                <input name="goodsName" type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" size="6" value="${(goodsDetails.goodsName)!""}">
                                                            </td>
                                                            <td class="hidden-480">
                                                                <input name="goodsSpec" type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" size="6" value="${(goodsDetails.goodsSpec)!""}">
                                                            </td>
                                                            <td>
                                                                <input name="unit" type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" size="6" value="${(goodsDetails.unit)!""}">
                                                            </td>
                                                            <td class="hidden-480">
                                                                <input name="quantity" type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" size="6" value="${(goodsDetails.quantity)!""}">
                                                            </td>
                                                            <td class="hidden-480">

                                                                <input name="productionBatch" type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" size="6" value="${(goodsDetails.productionBatch)!""}">
                                                            </td>
                                                            <td class="hidden-480">
                                                                <input name="" type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" size="6" value="${(goodsDetails.productionTime)!""}">
                                                            </td>
                                                            <td class="hidden-480">
                                                                <input name="" type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" size="6" value="${(goodsDetails.invalidTime)!""}">
                                                            </td>

                                                        </tr>
                                                        </#list>
                                                        </tbody>
                                                    </table>
                                                    <div class="row">
                                                        <div class="col-xs-6">
                                                            <div class="dataTables_info" id="dynamic-table_info" role="status" aria-live="polite"></div></div><div class="col-xs-6"><div class="dataTables_paginate paging_simple_numbers" id="dynamic-table_paginate"><ul class="pagination"><li class="paginate_button previous disabled" aria-controls="dynamic-table" tabindex="0" id="dynamic-table_previous"><a href="#">Previous</a></li><li class="paginate_button active" aria-controls="dynamic-table" tabindex="0"><a href="#">1</a></li><li class="paginate_button " aria-controls="dynamic-table" tabindex="0"><a href="#">2</a></li><li class="paginate_button " aria-controls="dynamic-table" tabindex="0"><a href="#">3</a></li><li class="paginate_button next" aria-controls="dynamic-table" tabindex="0" id="dynamic-table_next"><a href="#">Next</a></li></ul></div></div></div>

                                                </div>

                                                <div id="profile4" class="tab-pane">

                                                    <div class="page-header">
                                                        <h4>运输基本信息</h4>
                                                    </div>

                                                    <div class="row">

                                                        <div id="dynamic-table_filter" class="dataTables_length">
                                                            <label>
                                                                &nbsp;&nbsp;&nbsp;

                                                                数量:<input name="quantity" <#if orderInfo.quantity?? >value="${orderInfo.quantity}"</#if> type="text" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" >


                                                                重量:<input name="weight" <#if orderInfo.weight?? >value="${orderInfo.weight}"</#if> type="text" class="form-control input-sm" placeholder="" aria-controls="dynamic-table">
                                                                体积:<input name="cubage" <#if orderInfo.cubage?? >value="${orderInfo.cubage}"</#if> type="text" class="form-control input-sm" placeholder="" aria-controls="dynamic-table">(长*宽*高,单位:cm)<br/>
                                                                合计标准箱:<input name="totalStandardBox" <#if orderInfo.totalStandardBox?? >value="${orderInfo.totalStandardBox}"</#if> type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table"><br/>
                                                                &nbsp;&nbsp;&nbsp;
                                                                出发地:
                                                                <input name="departurePlace" <#if orderInfo.departurePlace?? >value="${orderInfo.departurePlace}"</#if> type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" readonly="readonly">
                                                                目的地:
                                                                <input name="destination" <#if orderInfo.destination?? >value="${orderInfo.destination}"</#if> type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" readonly="readonly">
                                                                取货时间:
                                                                <input name="pickupTime" <#if orderInfo.pickupTime?? >value="${((orderInfo.pickupTime)?string('yyyy-MM-dd HH:mm:ss'))!}"</#if> type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" onClick="WdatePicker({isShowClear:true,readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})">
                                                                期望送达时间:
                                                                <input name="expectedArrivedTime" <#if orderInfo.expectedArrivedTime?? >value="${((orderInfo.expectedArrivedTime)?string('yyyy-MM-dd HH:mm:ss'))!}"</#if> type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" onClick="WdatePicker({isShowClear:true,readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})">
                                                                是否加急:<input id="urgent" type="checkbox" name="" <#if orderInfo.urgent?? ><#if ((orderInfo.urgent) == 1)> checked="checked"</#if></#if>/>
                                                                <input id="urgent1" type="hidden" name="urgent" value="${(orderInfo.urgent)!""}"/>
                                                            </label>
                                                        </div>

                                                        <div id="dynamic-table_filter" class="dataTables_length">
                                                            <label>
                                                                &nbsp;&nbsp;&nbsp;运输要求:<input name="transRequire" <#if orderInfo.transRequire?? >value="${orderInfo.transRequire}"</#if> type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" onClick="WdatePicker()">
                                                            </label>
                                                        </div>

                                                        <br/>

                                                    </div>

                                                    <div class="page-header">
                                                        <h4>发货方信息</h4>
                                                    </div>
                                                    <span style="cursor:pointer" id="consignorListDivBlock"><button type="button" class="btn btn-info" id="bootbox-confirm">选择</button></span>
                                                    <div class="">
                                                        名称:
                                                        <input name="consignorName" <#if orderInfo.consignorName?? >value="${orderInfo.consignorName}"</#if> type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" >
                                                        联系人:
                                                        <input name="" type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" >
                                                        联系电话:
                                                        <input name="" type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" >
                                                        传真:
                                                        <input name="" type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" >
                                                        Email:
                                                        <input name="" type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" >
                                                        <br/>邮编:
                                                        <input name="" type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" >
                                                        地址:
                                                        <select><option value="">--省--</option></select>
                                                        <select><option value="">--市--</option></select>
                                                        <select><option value="">--区/县--</option></select>
                                                        <select><option value="">--乡镇/街道--</option></select>
                                                        <input name="" type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" >

                                                    </div>


                                                    <div class="page-header">
                                                        <h4>收货方信息</h4>
                                                    </div>
                                                    <span style="cursor:pointer" id="consigneeListDivBlock"><button type="button" class="btn btn-info" id="bootbox-confirm">选择</button></span>
                                                    <div class="">
                                                        名称:
                                                        <input name="consigneeName" <#if orderInfo.consigneeName?? >value="${orderInfo.consigneeName}"</#if> type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" >
                                                        联系人:
                                                        <input name="" type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" >
                                                        联系电话:
                                                        <input name="" type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" >
                                                        传真:
                                                        <input name="" type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" >
                                                        Email:
                                                        <input name="" type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" >
                                                        <br/>邮编:
                                                        <input name="" type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" >
                                                        地址:
                                                        <select><option value="">--省--</option></select>
                                                        <select><option value="">--市--</option></select>
                                                        <select><option value="">--区/县--</option></select>
                                                        <select><option value="">--乡镇/街道--</option></select>
                                                        <input name="" type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" >

                                                    </div>

                                                </div>

                                                <div id="dropdown14" class="tab-pane">

                                                    <div class="page-header">
                                                        <h4>仓配基本信息</h4>
                                                    </div>
                                                    <div class="row">
                                                        <div id="dynamic-table_filter" class="dataTables_length">
                                                            <label>
                                                                &nbsp;&nbsp;&nbsp;仓库名称:
                                                                <select id="" name="wareHouseName"  value="${(orderInfo.wareHouseName)!""}">
                                                                    <option value="">----</option>
                                                                    <option value="仓库1" <#if orderInfo.wareHouseName?? ><#if ((orderInfo.wareHouseName)== '0')>selected="selected"</#if></#if>>仓库1</option>
                                                                    <option value="仓库2" <#if orderInfo.wareHouseName?? ><#if ((orderInfo.wareHouseName)== '1')>selected="selected"</#if></#if>>仓库2</option>
                                                                </select>

                                                                入库预计到达时间:<input name="arriveTime" <#if orderInfo.arriveTime?? >value="${((orderInfo.arriveTime)?string('yyyy-MM-dd HH:mm:ss'))!}"</#if>  type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" onClick="WdatePicker({isShowClear:true,readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})">
                                                                车牌号:<input name="plateNumber" <#if orderInfo.plateNumber?? >value="${orderInfo.plateNumber}"</#if> type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table">
                                                                司机姓名:<input name="driverName" <#if orderInfo.driverName?? >value="${orderInfo.driverName}"</#if> type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table"><br/>
                                                                &nbsp;&nbsp;&nbsp;
                                                                联系电话:
                                                                <input name="contactNumber" <#if orderInfo.contactNumber?? >value="${orderInfo.contactNumber}"</#if> type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table">
                                                            </label>
                                                        </div>
                                                        <br/>
                                                    </div>
                                                    <div class="page-header">
                                                        <h4>供应商信息</h4>
                                                    </div>
                                                    <span style="cursor:pointer" id="supportListDivBlock"><button type="button" class="btn btn-info" id="bootbox-confirm">选择</button></span>
                                                    <div class="">
                                                        名称:
                                                        <input name="supportName" <#if orderInfo.supportName?? >value="${orderInfo.supportName}"</#if> type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" >
                                                        联系人:
                                                        <input name="" type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" >
                                                        联系电话:
                                                        <input name="" type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" >
                                                        传真:
                                                        <input name="" type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" >
                                                        Email:
                                                        <input name="" type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" >
                                                        <br/>邮编:
                                                        <input name="" type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" >
                                                        地址:
                                                        <select><option value="">--省--</option></select>
                                                        <select><option value="">--市--</option></select>
                                                        <select><option value="">--区/县--</option></select>
                                                        <select><option value="">--乡镇/街道--</option></select>
                                                        <input name="" type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" >

                                                    </div>
                                                </div>
                                            </div>
                                        </div>

                                        <!-- /section:elements.tab.option -->
                                    </div>
                                </div>
                            </div>
                        </div>

                        <button type="button" class="btn btn-info" id="bootbox-confirm" onclick="subOrder()">保存修改</button>

                    </form>
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
<script src="${base}/components/jquery/dist/jquery.js"></script>
<script src="${base}/components/bootbox.js/bootbox.js"></script>
<!-- <![endif]-->

<!--[if IE]>
<script src="${base}/components/jquery.1x/dist/jquery.js"></script>
<![endif]-->
<script type="text/javascript">
    if('ontouchstart' in document.documentElement) document.write("<script src='${base}/components/_mod/jquery.mobile.custom/jquery.mobile.custom.js'>"+"<"+"/script>");
</script>
<script src="${base}/components/bootstrap/dist/js/bootstrap.js"></script>

<!-- page specific plugin scripts -->
<script src="${base}/components/datatables/media/js/jquery.dataTables.js"></script>
<script src="${base}/components/_mod/datatables/jquery.dataTables.bootstrap.js"></script>
<script src="${base}/components/datatables.net-buttons/js/dataTables.buttons.js"></script>
<script src="${base}/components/datatables.net-buttons/js/buttons.flash.js"></script>
<script src="${base}/components/datatables.net-buttons/js/buttons.html5.js"></script>
<script src="${base}/components/datatables.net-buttons/js/buttons.print.js"></script>
<script src="${base}/components/datatables.net-buttons/js/buttons.colVis.js"></script>
<script src="${base}/components/datatables.net-select/js/dataTables.select.js"></script>

<!-- ace scripts -->
<script src="${base}/assets/js/src/elements.scroller.js"></script>
<script src="${base}/assets/js/src/elements.colorpicker.js"></script>
<script src="${base}/assets/js/src/elements.fileinput.js"></script>
<script src="${base}/assets/js/src/elements.typeahead.js"></script>
<script src="${base}/assets/js/src/elements.wysiwyg.js"></script>
<script src="${base}/assets/js/src/elements.spinner.js"></script>
<script src="${base}/assets/js/src/elements.treeview.js"></script>
<script src="${base}/assets/js/src/elements.wizard.js"></script>
<script src="${base}/assets/js/src/elements.aside.js"></script>
<script src="${base}/assets/js/src/ace.js"></script>
<script src="${base}/assets/js/src/ace.basics.js"></script>
<script src="${base}/assets/js/src/ace.scrolltop.js"></script>
<script src="${base}/assets/js/src/ace.ajax-content.js"></script>
<script src="${base}/assets/js/src/ace.touch-drag.js"></script>
<script src="${base}/assets/js/src/ace.sidebar.js"></script>
<script src="${base}/assets/js/src/ace.sidebar-scroll-1.js"></script>
<script src="${base}/assets/js/src/ace.submenu-hover.js"></script>
<script src="${base}/assets/js/src/ace.widget-box.js"></script>
<script src="${base}/assets/js/src/ace.settings.js"></script>
<script src="${base}/assets/js/src/ace.settings-rtl.js"></script>
<script src="${base}/assets/js/src/ace.settings-skin.js"></script>
<script src="${base}/assets/js/src/ace.widget-on-reload.js"></script>
<script src="${base}/assets/js/src/ace.searchbox-autocomplete.js"></script>

<!-- inline scripts related to this page -->
<script type="text/javascript">

    $(function(){

        $("#goodsListDivBlock").click(function(){

            $("#goodsListDiv").fadeIn("slow");//淡入淡出效果 显示div

        });

        $("#goodsListDivNoneTop").click(function(){

            $("#goodsListDiv").fadeOut("slow");//淡入淡出效果 隐藏div

        });

        $("#goodsListDivNoneBottom").click(function(){

            $("#goodsListDiv").fadeOut("slow");//淡入淡出效果 隐藏div

        });


        $("#consignorListDivBlock").click(function(){

            $("#consignorListDiv").fadeIn("slow");//淡入淡出效果 显示div

        });

        $("#consignorListDivNoneTop").click(function(){

            $("#consignorListDiv").fadeOut("slow");//淡入淡出效果 隐藏div

        });

        $("#consignorListDivNoneBottom").click(function(){

            $("#consignorListDiv").fadeOut("slow");//淡入淡出效果 隐藏div

        });

        $("#consigneeListDivBlock").click(function(){

            $("#consigneeListDiv").fadeIn("slow");//淡入淡出效果 显示div

        });

        $("#consigneeListDivNoneTop").click(function(){

            $("#consigneeListDiv").fadeOut("slow");//淡入淡出效果 隐藏div

        });

        $("#consigneeListDivNoneBottom").click(function(){

            $("#consigneeListDiv").fadeOut("slow");//淡入淡出效果 隐藏div

        });

        $("#supportListDivBlock").click(function(){

            $("#supportListDiv").fadeIn("slow");//淡入淡出效果 显示div

        });

        $("#supportListDivNoneTop").click(function(){

            $("#supportListDiv").fadeOut("slow");//淡入淡出效果 隐藏div

        });

        $("#supportListDivNoneBottom").click(function(){

            $("#supportListDiv").fadeOut("slow");//淡入淡出效果 隐藏div

        });

        if($("#orderType").val()== '60'){
            $("#provideTransportDiv").hide();
            $("#businessTypeDiv").hide();
            $('.tranfr').show();
            $('.storeLi').hide();
        }
        if($("#orderType").val()== '61'){
            $("#provideTransportDiv").show();
            $("#businessTypeDiv").show();
            $('.storeLi').show();
            $('.tranfr').hide();
            if($("#provideTransport").prop("checked")){
                $('.tranfr').show();
            }else{
                $('.tranfr').hide();
            }
        }

        $("#orderType").change(function () {
            if($(this).children('option:selected').val() == '61'){
                $("#provideTransportDiv").show();
                $("#businessTypeDiv").show();
                $('.storeLi').show();
                $('.tranfr').hide();

            }
            if($(this).children('option:selected').val() == '60'){

                $("#provideTransportDiv").hide();
                $("#businessTypeDiv").hide();
                $('.tranfr').show();
                $('.storeLi').hide();
            }
        });
        $("#provideTransport").change(function () {
            if($(this).prop("checked")){
                $('.tranfr').show();
                $("#provideTransport1").val(1);
            }else{
                $('.tranfr').hide();
                $("#provideTransport1").val(0);
            }
        });
        $("#urgent").change(function () {
            if($(this).prop("checked")){
                $("#urgent1").val(1);
            }else{
                $("#urgent1").val(0);
            }
        });

    })
    function subOrder(orderCode) {
        /*跳转到订单的可编辑页(跟下单页面一样!), 并回显该订单数据*/document.getElementById('orderPlaceConTable').submit();
        $("#orderPlaceConTable").submit();
    }

    function deleteGoods(ordercode,goodsCode) {
        var result  = confirm("您确定要删除此货品?");
        if(result == true) {
            $.get("/ofc/goodsDelete",{"orderCode":ordercode,"goodsCode":goodsCode},function (data) {
                $("#confirmBox").modal('hide');
                if(data == 200){
                    window.location.href="/orderScreenByCondition?tag=manage";
                } else {
                    alert("删除货品失败,请联系管理员!");
                }
            });
        }


    }

    jQuery(function($) {
        $("#bootbox-regular").on(ace.click_event, function() {
            bootbox.prompt("What is your name?", function(result) {
                if (result === null) {

                } else {

                }
            });
        });



        $.fn.dataTable.Buttons.swfPath = "${base}/components/datatables.net-buttons-swf/index.swf"; //in Ace demo ${base}/components will be replaced by correct assets path
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
<link rel="stylesheet" href="${base}/assets/css/ace.onpage-help.css">
<link rel="stylesheet" href="${base}/docs/assets/js/themes/sunburst.css">

<script type="text/javascript"> ace.vars['base'] = '..'; </script>
<script src="${base}/assets/js/src/elements.onpage-help.js"></script>
<script src="${base}/assets/js/src/ace.onpage-help.js"></script>
<script src="${base}/docs/assets/js/rainbow.js"></script>
<script src="${base}/docs/assets/js/language/generic.js"></script>
<script src="${base}/docs/assets/js/language/html.js"></script>
<script src="${base}/docs/assets/js/language/css.js"></script>
<script src="${base}/docs/assets/js/language/javascript.js"></script>

</body></html>