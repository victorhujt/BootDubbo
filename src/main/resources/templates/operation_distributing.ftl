<head>
    <title>城配下单</title>
</head>
<body>
<!--goodsListDiv-->
<div class="modal-content" id="goodsListDiv" style="display: none;">
    <div class="modal-header"><span id="goodsListDivNoneTop" style="cursor:pointer"><button type="button" id="" style="cursor:pointer" class="bootbox-close-button close" data-dismiss="modal" aria-hidden="true">×</button></span>
        <h4 class="modal-title">货品列表</h4></div>
    <div class="modal-body">
        <div class="bootbox-body">
            <form id="goodsSelConditionForm" class="form-horizontal" role="form">
                <div class="form-group">
                    <label class="control-label col-sm-1 no-padding-right" for="name">货品编码</label>
                    <div class="col-sm-3">
                        <div class="clearfix">
                            <input  id = "goodsCodeCondition" name="goodsCode" type="text" style="color: black" class="form-control input-sm" placeholder="" aria-controls="dynamic-table">
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-sm-1 no-padding-right" for="name">货品名称</label>
                    <div class="col-sm-3">
                        <div class="clearfix">
                            <input  id = "goodsNameCondition" name="goodsName" type="text" style="color: black" class="form-control input-sm" placeholder="" aria-controls="dynamic-table">
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-sm-1 no-padding-right" for="name"></label>
                    <div class="col-sm-3">
                        <div class="clearfix">
                            <span id="goodsSelectFormBtn" class="btn btn-info btn-sm popover-info">筛选</span>
                        </div>
                    </div>
                </div>
            </form>
            <form class="bootbox-form">
                <table id="dynamic-table" class="table table-striped table-bordered table-hover dataTable no-footer" role="grid" aria-describedby="dynamic-table_info">
                    <thead>
                    <tr role="row"><th class="center sorting_disabled" rowspan="1" colspan="1" aria-label="">
                        <label class="pos-rel">
                            <input id="goodcheck" type="checkbox" class="ace">
                            <span class="lbl"></span>
                        </label>
                    </th>
                    <#--<th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Domain: activate to sort column ascending">序号</th>-->
                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Price: activate to sort column ascending">货品编码</th>
                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">货品名称</th>
                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">货品规格</th>
                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">单位</th>
                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">单价</th>

                    </thead>
                    <tbody id="goodsSelectListTbody"></tbody>
                </table>


            </form>
        </div>
    </div>
    <div class="modal-footer"><span id="goodsListDivNoneBottom" style="cursor:pointer"><button  data-bb-handler="cancel" type="button" class="btn btn-default">Cancel</button></span><button id="goodsEnter" data-bb-handler="confirm" type="button" class="btn btn-primary">OK</button></div>
</div>
<div class="col-xs-9">
    <button class="btn btn-white btn-info btn-bold btn-interval" id="">
        <i class="ace-icon fa fa-floppy-o bigger-120 blue"></i>
        历史订单选择
    </button>

    <button class="btn btn-white btn-info btn-bold btn-interval" id="">
        <i class="ace-icon fa fa-floppy-o bigger-120 blue"></i>
        Excel导入
    </button>

    <button class="btn btn-white btn-info btn-bold btn-interval" id="">
        <i class="ace-icon fa fa-floppy-o bigger-120 blue"></i>
        收发货方档案
    </button>
</div>

<form id="" method="post" class="form-horizontal" role="form">
    <div class="col-xs-12">
        <div class="form-group">
            <label class="control-label col-label no-padding-right" for="supplierCode">订单日期</label>
            <div class="col-xs-3">
                <div class="clearfix">
                    <input class="col-xs-10 col-xs-12" name="orderTime" id="orderTime" type="text" placeholder="订单日期"
                           onClick="WdatePicker({isShowClear:true,readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})"/>
                </div>
            </div>
            <label class="control-label col-label no-padding-right" for="supplierCode">开单员</label>
            <div class="col-xs-3">
                <div class="clearfix">
                    <input class="col-xs-10 col-xs-12" name="" id="" type="text" placeholder="开单员"/>
                </div>
            </div>
            <label class="control-label col-label no-padding-right" for="supplierCode">预发货时间</label>
            <div class="col-xs-3">
                <div class="clearfix">
                    <input class="col-xs-10 col-xs-12" name="" id="" type="text" placeholder="预计发货时间"
                           onClick="WdatePicker({isShowClear:true,readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})"/>
                </div>
            </div>
        </div>
    </div>
    <div class="col-xs-12">
        <div class="form-group">
            <label class="control-label col-label no-padding-right" for="supplierCode">客户名称</label>
            <div class="col-xs-3">
                <div class="clearfix">
                    <input class="col-xs-10 col-xs-12" name="orderTime" id="orderTime" type="text" placeholder="客户名称"/>
                    <button type="button" class="btn btn-minier btn-inverse no-padding-right"
                            id="consignorListDivBlock">选择
                    </button>
                </div>
            </div>
            <label class="control-label col-label no-padding-right" for="supplierCode">配送仓库</label>
            <div class="col-xs-3">
                <div class="clearfix">
                    <input class="col-xs-10 col-xs-12" name="" id="" type="text" placeholder="配送仓库"/>
                </div>
            </div>
            <label class="control-label col-label no-padding-right" for="supplierCode">备注</label>
            <div class="col-xs-3">
                <div class="clearfix">
                    <input class="col-xs-10 col-xs-12" name="" id="" type="text" placeholder="备注"/>
                </div>
            </div>
        </div>
    </div>

    <div class="row">
        <span style="float: left">发货方</span>
        <span style="float: left">出发地:</span>
        <div class="col-xs-12">
            <div class="form-group">
                <label class="control-label col-label no-padding-right" for="supplierCode">名称</label>
                <div class="col-xs-3">
                    <div class="clearfix">
                        <input class="col-xs-10 col-xs-12" name="orderTime" id="orderTime" type="text"
                               placeholder="名称"/>
                        <button type="button" class="btn btn-minier btn-inverse no-padding-right"
                                id="consignorListDivBlock">选择
                        </button>
                    </div>
                </div>
                <label class="control-label col-label no-padding-right" for="supplierCode">联系人</label>
                <div class="col-xs-3">
                    <div class="clearfix">
                        <input class="col-xs-10 col-xs-12" name="" id="" type="text" placeholder="联系人"/>
                    </div>
                </div>
                <label class="control-label col-label no-padding-right" for="supplierCode">联系电话</label>
                <div class="col-xs-3">
                    <div class="clearfix">
                        <input class="col-xs-10 col-xs-12" name="" id="" type="text" placeholder="联系电话"/>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-xs-12">
            <div class="form-group">
                <label class="control-label col-label no-padding-right" for="supplierCode">地址</label>
                <div class="col-xs-9">
                    <div class="clearfix">
                        <input class="col-xs-10 col-xs-12" name="orderTime" id="orderTime" type="text" placeholder="地址"
                               onClick="WdatePicker({isShowClear:true,readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})"/>
                    </div>
                </div>

            </div>
        </div>
    </div>
</form>
<div class="col-sm-12">
    <div class="tabbable" style="width:1000px;">
        <ul class="nav nav-tabs" id="myTab4">
            <li class="active">
                <a data-toggle="tab" href="#home4" aria-expanded="false">货品信息</a>
            </li>

            <li class="transLi">
                <a data-toggle="tab" href="#profile4" aria-expanded="true">收货方列表</a>
            </li>

        </ul>

        <div class="tab-content">
            <div id="home4" class="tab-pane active">
                <!--货品明细-->
                <span style="cursor:pointer" id="goodsListDivBlock"><button type="button" class="btn btn-info"  id="bootbox-confirm">添加货品</button></span>
                <table id="orderGoodsListTable" class="table table-striped table-bordered table-hover dataTable no-footer" role="grid"
                       aria-describedby="dynamic-table_info">
                    <thead>
                    <tr role="row">
                        <th class="center sorting_disabled" rowspan="1" colspan="1" aria-label="">
                            操作
                        </th>
                    <#--<th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Status: activate to sort column ascending">序号</th>-->
                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1"
                            aria-label="Domain: activate to sort column ascending">货品编码
                        </th>
                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1"
                            aria-label="Price: activate to sort column ascending">货品名称
                        </th>
                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1"
                            aria-label="Update: activate to sort column ascending">规格
                        </th>
                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1"
                            aria-label="Clicks: activate to sort column ascending">单位
                        </th>
                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1"
                            aria-label="Clicks: activate to sort column ascending">数量
                        </th>
                    </thead>
                    <tbody id="goodsInfoListDiv"></tbody>

                </table>
            </div>

            <div id="profile4" class="tab-pane">

            </div>

        </div>
    </div>
</div>


<#--<div class="col-xs-12">

    <div class="ui-jqgrid ui-widget ui-widget-content ui-corner-all" id="gbox_grid-table" dir="ltr"
         style="width: 1119px;">
        <div class="jqgrid-overlay ui-widget-overlay" id="lui_grid-table"></div>
        <div class="loading ui-state-default ui-state-active" id="load_grid-table" style="display: none;">Loading...
        </div>
        <div class="ui-jqgrid-view " role="grid" id="gview_grid-table" style="width: 1119px;">
            <div class="ui-jqgrid-hdiv ui-state-default" style="width: 1119px;">
                <div class="ui-jqgrid-hbox">
                    <table class="ui-jqgrid-htable ui-common-table " style="width: 1101px;" role="presentation"
                           aria-labelledby="gbox_grid-table">
                        <thead>
                        <tr class="ui-jqgrid-labels" role="row">
                           &lt;#&ndash; <th id="grid-table_cb" role="columnheader" class="ui-th-column ui-th-ltr ui-state-default"
                                style="width: 35px;">
                                <div id="jqgh_grid-table_cb"><input role="checkbox" id="cb_grid-table" class="cbox"
                                                                    type="checkbox"><span class="s-ico"
                                                                                          style="display:none"><span
                                        sort="asc"
                                        class="ui-grid-ico-sort ui-icon-asc ui-sort-ltr ui-state-disabled ui-icon ui-icon-triangle-1-n"></span><span
                                        sort="desc"
                                        class="ui-grid-ico-sort ui-icon-desc ui-sort-ltr ui-state-disabled ui-icon ui-icon-triangle-1-s"></span></span>
                                </div>
                            </th>&ndash;&gt;
                            <th id="grid-table_subgrid" role="columnheader"
                                class="ui-th-column ui-th-ltr ui-state-default" style="width: 25px;">
                                <div id="jqgh_grid-table_subgrid"><span class="s-ico" style="display:none"><span
                                        sort="asc"
                                        class="ui-grid-ico-sort ui-icon-asc ui-sort-ltr ui-state-disabled ui-icon ui-icon-triangle-1-n"></span><span
                                        sort="desc"
                                        class="ui-grid-ico-sort ui-icon-desc ui-sort-ltr ui-state-disabled ui-icon ui-icon-triangle-1-s"></span></span>
                                </div>
                            </th>
                            <th id="grid-table_myac" role="columnheader" class="ui-th-column ui-th-ltr ui-state-default"
                                style="width: 80px;"><span class="ui-jqgrid-resize ui-jqgrid-resize-ltr"
                                                           style="cursor: col-resize;">&nbsp;</span>
                                <div id="jqgh_grid-table_myac" class="ui-jqgrid-sortable"><span class="s-ico"
                                                                                                style="display:none"><span
                                        sort="asc"
                                        class="ui-grid-ico-sort ui-icon-asc ui-sort-ltr ui-state-disabled ui-icon ui-icon-triangle-1-n"></span><span
                                        sort="desc"
                                        class="ui-grid-ico-sort ui-icon-desc ui-sort-ltr ui-state-disabled ui-icon ui-icon-triangle-1-s"></span></span>
                                </div>
                            </th>
                            <th id="grid-table_id" role="columnheader" class="ui-th-column ui-th-ltr ui-state-default"
                                style="width: 95px;"><span class="ui-jqgrid-resize ui-jqgrid-resize-ltr"
                                                           style="cursor: col-resize;">&nbsp;</span>
                                <div id="jqgh_grid-table_id" class="ui-jqgrid-sortable">ID<span class="s-ico"
                                                                                                style="display:none"><span
                                        sort="asc"
                                        class="ui-grid-ico-sort ui-icon-asc ui-sort-ltr ui-state-disabled ui-icon ui-icon-triangle-1-n"></span><span
                                        sort="desc"
                                        class="ui-grid-ico-sort ui-icon-desc ui-sort-ltr ui-state-disabled ui-icon ui-icon-triangle-1-s"></span></span>
                                </div>
                            </th>
                            <th id="grid-table_sdate" role="columnheader"
                                class="ui-th-column ui-th-ltr ui-state-default" style="width: 142px;"><span
                                    class="ui-jqgrid-resize ui-jqgrid-resize-ltr"
                                    style="cursor: col-resize;">&nbsp;</span>
                                <div id="jqgh_grid-table_sdate" class="ui-jqgrid-sortable">Last Sales<span class="s-ico"
                                                                                                           style="display:none"><span
                                        sort="asc"
                                        class="ui-grid-ico-sort ui-icon-asc ui-sort-ltr ui-state-disabled ui-icon ui-icon-triangle-1-n"></span><span
                                        sort="desc"
                                        class="ui-grid-ico-sort ui-icon-desc ui-sort-ltr ui-state-disabled ui-icon ui-icon-triangle-1-s"></span></span>
                                </div>
                            </th>
                            <th id="grid-table_name" role="columnheader" class="ui-th-column ui-th-ltr ui-state-default"
                                style="width: 236px;"><span class="ui-jqgrid-resize ui-jqgrid-resize-ltr"
                                                            style="cursor: col-resize;">&nbsp;</span>
                                <div id="jqgh_grid-table_name" class="ui-jqgrid-sortable">Name<span class="s-ico"
                                                                                                    style="display:none"><span
                                        sort="asc"
                                        class="ui-grid-ico-sort ui-icon-asc ui-sort-ltr ui-state-disabled ui-icon ui-icon-triangle-1-n"></span><span
                                        sort="desc"
                                        class="ui-grid-ico-sort ui-icon-desc ui-sort-ltr ui-state-disabled ui-icon ui-icon-triangle-1-s"></span></span>
                                </div>
                            </th>
                            <th id="grid-table_stock" role="columnheader"
                                class="ui-th-column ui-th-ltr ui-state-default" style="width: 110px;"><span
                                    class="ui-jqgrid-resize ui-jqgrid-resize-ltr"
                                    style="cursor: col-resize;">&nbsp;</span>
                                <div id="jqgh_grid-table_stock" class="ui-jqgrid-sortable">Stock<span class="s-ico"
                                                                                                      style="display:none"><span
                                        sort="asc"
                                        class="ui-grid-ico-sort ui-icon-asc ui-sort-ltr ui-state-disabled ui-icon ui-icon-triangle-1-n"></span><span
                                        sort="desc"
                                        class="ui-grid-ico-sort ui-icon-desc ui-sort-ltr ui-state-disabled ui-icon ui-icon-triangle-1-s"></span></span>
                                </div>
                            </th>
                            <th id="grid-table_ship" role="columnheader" class="ui-th-column ui-th-ltr ui-state-default"
                                style="width: 142px;"><span class="ui-jqgrid-resize ui-jqgrid-resize-ltr"
                                                            style="cursor: col-resize;">&nbsp;</span>
                                <div id="jqgh_grid-table_ship" class="ui-jqgrid-sortable">Ship via<span class="s-ico"
                                                                                                        style="display:none"><span
                                        sort="asc"
                                        class="ui-grid-ico-sort ui-icon-asc ui-sort-ltr ui-state-disabled ui-icon ui-icon-triangle-1-n"></span><span
                                        sort="desc"
                                        class="ui-grid-ico-sort ui-icon-desc ui-sort-ltr ui-state-disabled ui-icon ui-icon-triangle-1-s"></span></span>
                                </div>
                            </th>
                            <th id="grid-table_note" role="columnheader" class="ui-th-column ui-th-ltr ui-state-default"
                                style="width: 236px;"><span class="ui-jqgrid-resize ui-jqgrid-resize-ltr"
                                                            style="cursor: col-resize;">&nbsp;</span>
                                <div id="jqgh_grid-table_note" class="ui-jqgrid-sortable">Notes<span class="s-ico"
                                                                                                     style="display:none"><span
                                        sort="asc"
                                        class="ui-grid-ico-sort ui-icon-asc ui-sort-ltr ui-state-disabled ui-icon ui-icon-triangle-1-n"></span><span
                                        sort="desc"
                                        class="ui-grid-ico-sort ui-icon-desc ui-sort-ltr ui-state-disabled ui-icon ui-icon-triangle-1-s"></span></span>
                                </div>
                            </th>
                        </tr>
                        </thead>
                    </table>
                </div>
            </div>
            <div class="ui-jqgrid-bdiv" style="height: 250px; width: 1119px;">
                <div style="position:relative;">
                    <div></div>
                    <table id="grid-table" tabindex="0" role="presentation" aria-multiselectable="true"
                           aria-labelledby="gbox_grid-table" class="ui-jqgrid-btable ui-common-table"
                           style="width: 1101px;">
                        <tbody>
                        <tr class="jqgfirstrow" role="row">
                            <td role="gridcell" style="height:0px;width:35px;"></td>
                            <td role="gridcell" style="height:0px;width:25px;"></td>
                            <td role="gridcell" style="height:0px;width:80px;"></td>
                            <td role="gridcell" style="height: 0px; width: 95px;"></td>
                            <td role="gridcell" style="height: 0px; width: 142px;"></td>
                            <td role="gridcell" style="height: 0px; width: 236px;"></td>
                            <td role="gridcell" style="height: 0px; width: 110px;"></td>
                            <td role="gridcell" style="height: 0px; width: 142px;"></td>
                            <td role="gridcell" style="height: 0px; width: 236px;"></td>
                        </tr>
                        <tr role="row" id="1" tabindex="-1" class="jqgrow ui-row-ltr ui-widget-content">

                            <td role="gridcell" aria-describedby="grid-table_subgrid" class="ui-sgcollapsed sgcollapsed"
                                style=""><a style="cursor:pointer;" class="ui-sghref"><span
                                    class="ui-icon ace-icon fa fa-plus center bigger-110 blue"></span></a></td>
                            <td role="gridcell" style="" title="" aria-describedby="grid-table_myac">
                                <div style="margin-left:8px;">
                                    <div title="" style="float:left;cursor:pointer;" class="ui-pg-div ui-inline-edit"
                                         id="jEditButton_1" onclick="jQuery.fn.fmatter.rowactions.call(this,'edit');"
                                         onmouseover="jQuery(this).addClass('ui-state-hover');"
                                         onmouseout="jQuery(this).removeClass('ui-state-hover');"
                                         data-original-title="Edit selected row"><span
                                            class="ui-icon ui-icon-pencil"></span></div>
                                    <div title="" style="float:left;" class="ui-pg-div ui-inline-del"
                                         id="jDeleteButton_1" onclick="jQuery.fn.fmatter.rowactions.call(this,'del');"
                                         onmouseover="jQuery(this).addClass('ui-state-hover');"
                                         onmouseout="jQuery(this).removeClass('ui-state-hover');"
                                         data-original-title="Delete selected row"><span
                                            class="ui-icon ui-icon-trash"></span></div>
                                    <div title="" style="float:left;display:none" class="ui-pg-div ui-inline-save"
                                         id="jSaveButton_1" onclick="jQuery.fn.fmatter.rowactions.call(this,'save');"
                                         onmouseover="jQuery(this).addClass('ui-state-hover');"
                                         onmouseout="jQuery(this).removeClass('ui-state-hover');"
                                         data-original-title="Save row"><span class="ui-icon ui-icon-disk"></span></div>
                                    <div title="" style="float:left;display:none;" class="ui-pg-div ui-inline-cancel"
                                         id="jCancelButton_1"
                                         onclick="jQuery.fn.fmatter.rowactions.call(this,'cancel');"
                                         onmouseover="jQuery(this).addClass('ui-state-hover');"
                                         onmouseout="jQuery(this).removeClass('ui-state-hover');"
                                         data-original-title="Cancel row editing"><span
                                            class="ui-icon ui-icon-cancel"></span></div>
                                </div>
                            </td>
                            <td role="gridcell" style="" title="1" aria-describedby="grid-table_id">1</td>
                            <td role="gridcell" style="" title="2007-12-03" aria-describedby="grid-table_sdate">
                                2007-12-03
                            </td>
                            <td role="gridcell" style="" title="Desktop Computer" aria-describedby="grid-table_name">
                                Desktop Computer
                            </td>
                            <td role="gridcell" style="" title="Yes" aria-describedby="grid-table_stock">Yes</td>
                            <td role="gridcell" style="" title="FedEx" aria-describedby="grid-table_ship">FedEx</td>
                            <td role="gridcell" style="" title="note" aria-describedby="grid-table_note">note</td>
                        </tr>
                        <tr role="row" id="2" tabindex="-1"
                            class="jqgrow ui-row-ltr ui-widget-content ui-priority-secondary">

                            <td role="gridcell" aria-describedby="grid-table_subgrid" class="ui-sgcollapsed sgexpanded"
                                style=""><a style="cursor:pointer;" class="ui-sghref"><span
                                    class="ui-icon ace-icon fa fa-minus center bigger-110 blue"></span></a></td>
                            <td role="gridcell" style="" title="" aria-describedby="grid-table_myac">
                                <div style="margin-left:8px;">
                                    <div title="" style="float:left;cursor:pointer;" class="ui-pg-div ui-inline-edit"
                                         id="jEditButton_2" onclick="jQuery.fn.fmatter.rowactions.call(this,'edit');"
                                         onmouseover="jQuery(this).addClass('ui-state-hover');"
                                         onmouseout="jQuery(this).removeClass('ui-state-hover');"
                                         data-original-title="Edit selected row"><span
                                            class="ui-icon ui-icon-pencil"></span></div>
                                    <div title="" style="float:left;" class="ui-pg-div ui-inline-del"
                                         id="jDeleteButton_2" onclick="jQuery.fn.fmatter.rowactions.call(this,'del');"
                                         onmouseover="jQuery(this).addClass('ui-state-hover');"
                                         onmouseout="jQuery(this).removeClass('ui-state-hover');"
                                         data-original-title="Delete selected row"><span
                                            class="ui-icon ui-icon-trash"></span></div>
                                    <div title="" style="float:left;display:none" class="ui-pg-div ui-inline-save"
                                         id="jSaveButton_2" onclick="jQuery.fn.fmatter.rowactions.call(this,'save');"
                                         onmouseover="jQuery(this).addClass('ui-state-hover');"
                                         onmouseout="jQuery(this).removeClass('ui-state-hover');"
                                         data-original-title="Save row"><span class="ui-icon ui-icon-disk"></span></div>
                                    <div title="" style="float:left;display:none;" class="ui-pg-div ui-inline-cancel"
                                         id="jCancelButton_2"
                                         onclick="jQuery.fn.fmatter.rowactions.call(this,'cancel');"
                                         onmouseover="jQuery(this).addClass('ui-state-hover');"
                                         onmouseout="jQuery(this).removeClass('ui-state-hover');"
                                         data-original-title="Cancel row editing"><span
                                            class="ui-icon ui-icon-cancel"></span></div>
                                </div>
                            </td>
                            <td role="gridcell" style="" title="2" aria-describedby="grid-table_id">2</td>
                            <td role="gridcell" style="" title="2007-12-03" aria-describedby="grid-table_sdate">
                                2007-12-03
                            </td>
                            <td role="gridcell" style="" title="Laptop" aria-describedby="grid-table_name">Laptop</td>
                            <td role="gridcell" style="" title="Yes" aria-describedby="grid-table_stock">Yes</td>
                            <td role="gridcell" style="" title="InTime" aria-describedby="grid-table_ship">InTime</td>
                            <td role="gridcell" style="" title="Long text " aria-describedby="grid-table_note">Long
                                text
                            </td>
                        </tr>
                        <tr role="row" id="grid-table_2_expandedContent" class="ui-subgrid ui-sg-expanded">
                            <td colspan="1">&nbsp;</td>
                            <td class="ui-widget-content subgrid-cell"><span
                                    class="ui-icon ace-icon fa fa-chevron-right center orange"></span></td>
                            <td colspan="7" class="ui-widget-content subgrid-data">
                                <div id="grid-table_2" class="tablediv">
                                    <div class="ui-jqgrid ui-widget ui-widget-content ui-corner-all"
                                         id="gbox_grid-table_2_t" dir="ltr" style="width: 250px;">
                                        <div class="jqgrid-overlay ui-widget-overlay" id="lui_grid-table_2_t"></div>
                                        <div class="loading ui-state-default ui-state-active" id="load_grid-table_2_t"
                                             style="display: none;">Loading...
                                        </div>
                                        <div class="ui-jqgrid-view " role="grid" id="gview_grid-table_2_t"
                                             style="width: 250px;">
                                            <div class="ui-jqgrid-titlebar ui-jqgrid-caption ui-widget-header ui-corner-top ui-helper-clearfix"
                                                 style="display: none;"><a role="link"
                                                                           class="ui-jqgrid-titlebar-close HeaderButton ui-corner-all"
                                                                           title="Toggle Expand Collapse Grid"
                                                                           style="right: 0px;"><span
                                                    class="ui-jqgrid-headlink ui-icon ui-icon-circle-triangle-n"></span></a><span
                                                    class="ui-jqgrid-title"></span></div>
                                            <div class="ui-jqgrid-hdiv ui-state-default ui-corner-top"
                                                 style="width: 250px;">
                                                <div class="ui-jqgrid-hbox">
                                                    <table class="ui-jqgrid-htable ui-common-table " style="width:232px"
                                                           role="presentation" aria-labelledby="gbox_grid-table_2_t">
                                                        <thead>
                                                        <tr class="ui-jqgrid-labels" role="row">
                                                            <th id="grid-table_2_t_id" role="columnheader"
                                                                class="ui-th-column ui-th-ltr ui-state-default"
                                                                style="width: 46px;"><span
                                                                    class="ui-jqgrid-resize ui-jqgrid-resize-ltr"
                                                                    style="cursor: col-resize;">&nbsp;</span>
                                                                <div id="jqgh_grid-table_2_t_id"
                                                                     class="ui-jqgrid-sortable">No<span class="s-ico"
                                                                                                        style="display:none"><span
                                                                        sort="asc"
                                                                        class="ui-grid-ico-sort ui-icon-asc ui-sort-ltr ui-state-disabled ui-icon ui-icon-triangle-1-n"></span><span
                                                                        sort="desc"
                                                                        class="ui-grid-ico-sort ui-icon-desc ui-sort-ltr ui-state-disabled ui-icon ui-icon-triangle-1-s"></span></span>
                                                                </div>
                                                            </th>
                                                            <th id="grid-table_2_t_name" role="columnheader"
                                                                class="ui-th-column ui-th-ltr ui-state-default"
                                                                style="width: 139px;"><span
                                                                    class="ui-jqgrid-resize ui-jqgrid-resize-ltr"
                                                                    style="cursor: col-resize;">&nbsp;</span>
                                                                <div id="jqgh_grid-table_2_t_name"
                                                                     class="ui-jqgrid-sortable">Item Name<span
                                                                        class="s-ico" style="display:none"><span
                                                                        sort="asc"
                                                                        class="ui-grid-ico-sort ui-icon-asc ui-sort-ltr ui-state-disabled ui-icon ui-icon-triangle-1-n"></span><span
                                                                        sort="desc"
                                                                        class="ui-grid-ico-sort ui-icon-desc ui-sort-ltr ui-state-disabled ui-icon ui-icon-triangle-1-s"></span></span>
                                                                </div>
                                                            </th>
                                                            <th id="grid-table_2_t_qty" role="columnheader"
                                                                class="ui-th-column ui-th-ltr ui-state-default"
                                                                style="width: 47px;"><span
                                                                    class="ui-jqgrid-resize ui-jqgrid-resize-ltr"
                                                                    style="cursor: col-resize;">&nbsp;</span>
                                                                <div id="jqgh_grid-table_2_t_qty"
                                                                     class="ui-jqgrid-sortable">Qty<span class="s-ico"
                                                                                                         style="display:none"><span
                                                                        sort="asc"
                                                                        class="ui-grid-ico-sort ui-icon-asc ui-sort-ltr ui-state-disabled ui-icon ui-icon-triangle-1-n"></span><span
                                                                        sort="desc"
                                                                        class="ui-grid-ico-sort ui-icon-desc ui-sort-ltr ui-state-disabled ui-icon ui-icon-triangle-1-s"></span></span>
                                                                </div>
                                                            </th>
                                                        </tr>
                                                        </thead>
                                                    </table>
                                                </div>
                                            </div>
                                            <div class="ui-jqgrid-bdiv" style="height: 150px; width: 250px;">
                                                <div style="position:relative;">
                                                    <div></div>
                                                    <table id="grid-table_2_t" tabindex="0" role="presentation"
                                                           aria-multiselectable="false"
                                                           aria-labelledby="gbox_grid-table_2_t"
                                                           class="ui-jqgrid-btable ui-common-table"
                                                           style="width: 232px;">
                                                        <tbody>
                                                        <tr class="jqgfirstrow" role="row">
                                                            <td role="gridcell" style="height:0px;width:46px;"></td>
                                                            <td role="gridcell" style="height:0px;width:139px;"></td>
                                                            <td role="gridcell" style="height:0px;width:47px;"></td>
                                                        </tr>
                                                        <tr role="row" id="1" tabindex="-1"
                                                            class="jqgrow ui-row-ltr ui-widget-content ui-state-hover">
                                                            <td role="gridcell" style="" title="1"
                                                                aria-describedby="grid-table_2_t_id">1
                                                            </td>
                                                            <td role="gridcell" style="" title="sub grid item 1"
                                                                aria-describedby="grid-table_2_t_name">sub grid item 1
                                                            </td>
                                                            <td role="gridcell" style="" title="11"
                                                                aria-describedby="grid-table_2_t_qty">11
                                                            </td>
                                                        </tr>
                                                        <tr role="row" id="2" tabindex="-1"
                                                            class="jqgrow ui-row-ltr ui-widget-content">
                                                            <td role="gridcell" style="" title="2"
                                                                aria-describedby="grid-table_2_t_id">2
                                                            </td>
                                                            <td role="gridcell" style="" title="sub grid item 2"
                                                                aria-describedby="grid-table_2_t_name">sub grid item 2
                                                            </td>
                                                            <td role="gridcell" style="" title="3"
                                                                aria-describedby="grid-table_2_t_qty">3
                                                            </td>
                                                        </tr>
                                                        <tr role="row" id="3" tabindex="-1"
                                                            class="jqgrow ui-row-ltr ui-widget-content">
                                                            <td role="gridcell" style="" title="3"
                                                                aria-describedby="grid-table_2_t_id">3
                                                            </td>
                                                            <td role="gridcell" style="" title="sub grid item 3"
                                                                aria-describedby="grid-table_2_t_name">sub grid item 3
                                                            </td>
                                                            <td role="gridcell" style="" title="12"
                                                                aria-describedby="grid-table_2_t_qty">12
                                                            </td>
                                                        </tr>
                                                        <tr role="row" id="4" tabindex="-1"
                                                            class="jqgrow ui-row-ltr ui-widget-content ">
                                                            <td role="gridcell" style="" title="4"
                                                                aria-describedby="grid-table_2_t_id">4
                                                            </td>
                                                            <td role="gridcell" style="" title="sub grid item 4"
                                                                aria-describedby="grid-table_2_t_name">sub grid item 4
                                                            </td>
                                                            <td role="gridcell" style="" title="5"
                                                                aria-describedby="grid-table_2_t_qty">5
                                                            </td>
                                                        </tr>
                                                        <tr role="row" id="5" tabindex="-1"
                                                            class="jqgrow ui-row-ltr ui-widget-content">
                                                            <td role="gridcell" style="" title="5"
                                                                aria-describedby="grid-table_2_t_id">5
                                                            </td>
                                                            <td role="gridcell" style="" title="sub grid item 5"
                                                                aria-describedby="grid-table_2_t_name">sub grid item 5
                                                            </td>
                                                            <td role="gridcell" style="" title="2"
                                                                aria-describedby="grid-table_2_t_qty">2
                                                            </td>
                                                        </tr>
                                                        <tr role="row" id="6" tabindex="-1"
                                                            class="jqgrow ui-row-ltr ui-widget-content ">
                                                            <td role="gridcell" style="" title="6"
                                                                aria-describedby="grid-table_2_t_id">6
                                                            </td>
                                                            <td role="gridcell" style="" title="sub grid item 6"
                                                                aria-describedby="grid-table_2_t_name">sub grid item 6
                                                            </td>
                                                            <td role="gridcell" style="" title="9"
                                                                aria-describedby="grid-table_2_t_qty">9
                                                            </td>
                                                        </tr>
                                                        <tr role="row" id="7" tabindex="-1"
                                                            class="jqgrow ui-row-ltr ui-widget-content">
                                                            <td role="gridcell" style="" title="7"
                                                                aria-describedby="grid-table_2_t_id">7
                                                            </td>
                                                            <td role="gridcell" style="" title="sub grid item 7"
                                                                aria-describedby="grid-table_2_t_name">sub grid item 7
                                                            </td>
                                                            <td role="gridcell" style="" title="3"
                                                                aria-describedby="grid-table_2_t_qty">3
                                                            </td>
                                                        </tr>
                                                        <tr role="row" id="8" tabindex="-1"
                                                            class="jqgrow ui-row-ltr ui-widget-content ">
                                                            <td role="gridcell" style="" title="8"
                                                                aria-describedby="grid-table_2_t_id">8
                                                            </td>
                                                            <td role="gridcell" style="" title="sub grid item 8"
                                                                aria-describedby="grid-table_2_t_name">sub grid item 8
                                                            </td>
                                                            <td role="gridcell" style="" title="8"
                                                                aria-describedby="grid-table_2_t_qty">8
                                                            </td>
                                                        </tr>
                                                        </tbody>
                                                    </table>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="ui-jqgrid-resize-mark" id="rs_mgrid-table_2_t">&nbsp;</div>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr role="row" id="3" tabindex="-1" class="jqgrow ui-row-ltr ui-widget-content">

                            <td role="gridcell" aria-describedby="grid-table_subgrid" class="ui-sgcollapsed sgcollapsed"
                                style=""><a style="cursor:pointer;" class="ui-sghref"><span
                                    class="ui-icon ace-icon fa fa-plus center bigger-110 blue"></span></a></td>
                            <td role="gridcell" style="" title="" aria-describedby="grid-table_myac">
                                <div style="margin-left:8px;">
                                    <div title="" style="float:left;cursor:pointer;" class="ui-pg-div ui-inline-edit"
                                         id="jEditButton_3" onclick="jQuery.fn.fmatter.rowactions.call(this,'edit');"
                                         onmouseover="jQuery(this).addClass('ui-state-hover');"
                                         onmouseout="jQuery(this).removeClass('ui-state-hover');"
                                         data-original-title="Edit selected row"><span
                                            class="ui-icon ui-icon-pencil"></span></div>
                                    <div title="" style="float:left;" class="ui-pg-div ui-inline-del"
                                         id="jDeleteButton_3" onclick="jQuery.fn.fmatter.rowactions.call(this,'del');"
                                         onmouseover="jQuery(this).addClass('ui-state-hover');"
                                         onmouseout="jQuery(this).removeClass('ui-state-hover');"
                                         data-original-title="Delete selected row"><span
                                            class="ui-icon ui-icon-trash"></span></div>
                                    <div title="" style="float:left;display:none" class="ui-pg-div ui-inline-save"
                                         id="jSaveButton_3" onclick="jQuery.fn.fmatter.rowactions.call(this,'save');"
                                         onmouseover="jQuery(this).addClass('ui-state-hover');"
                                         onmouseout="jQuery(this).removeClass('ui-state-hover');"
                                         data-original-title="Save row"><span class="ui-icon ui-icon-disk"></span></div>
                                    <div title="" style="float:left;display:none;" class="ui-pg-div ui-inline-cancel"
                                         id="jCancelButton_3"
                                         onclick="jQuery.fn.fmatter.rowactions.call(this,'cancel');"
                                         onmouseover="jQuery(this).addClass('ui-state-hover');"
                                         onmouseout="jQuery(this).removeClass('ui-state-hover');"
                                         data-original-title="Cancel row editing"><span
                                            class="ui-icon ui-icon-cancel"></span></div>
                                </div>
                            </td>
                            <td role="gridcell" style="" title="3" aria-describedby="grid-table_id">3</td>
                            <td role="gridcell" style="" title="2007-12-03" aria-describedby="grid-table_sdate">
                                2007-12-03
                            </td>
                            <td role="gridcell" style="" title="LCD Monitor" aria-describedby="grid-table_name">LCD
                                Monitor
                            </td>
                            <td role="gridcell" style="" title="Yes" aria-describedby="grid-table_stock">Yes</td>
                            <td role="gridcell" style="" title="TNT" aria-describedby="grid-table_ship">TNT</td>
                            <td role="gridcell" style="" title="note3" aria-describedby="grid-table_note">note3</td>
                        </tr>

                        </tbody>
                    </table>
                </div>
            </div>
        </div>

    </div>


    <!-- PAGE CONTENT ENDS &ndash;&gt;
</div>-->


<div class="col-xs-12">
    <button class="btn btn-white btn-info btn-bold btn-interval" id="orderPlaceConTableBtn">
        <i class="ace-icon fa fa-floppy-o bigger-120 blue"></i>
        确认下单
    </button>
</div>


<script type="text/javascript">
    var scripts = [null,
        "/components/jquery-validation/dist/jquery.validate.min.js",
        "/components/jquery-validation/src/localization/messages_zh.js",
        "/components/jquery-validation/dist/additional-methods.js", null];
    $(".page-content-area").ace_ajax("loadScripts", scripts, function () {
        /*jQuery(function($) {
            validateForm();//校验表单信息
            /!*validateFormPageBody();*!/
        })*/
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
        //validateForm();
    }
    $(function () {
        $("#goodsListDivBlock").click(function () {
            $("#goodsListDiv").fadeIn("slow");//淡入淡出效果 显示div
        })
    })
    function deleteGood(obj) {
        $(obj).parent().parent().remove();
    }
</script>
</body>