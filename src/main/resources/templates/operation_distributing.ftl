<head>
    <title>城配下单</title>
</head>
<body>

<div class="col-xs-9">
    <button class="btn btn-white btn-info btn-bold btn-interval" id="">
        <i class="ace-icon fa fa-floppy-o bigger-120 blue" ></i>
        历史订单选择
    </button>

    <button class="btn btn-white btn-info btn-bold btn-interval" id="">
        <i class="ace-icon fa fa-floppy-o bigger-120 blue" ></i>
        Excel导入
    </button>

    <button class="btn btn-white btn-info btn-bold btn-interval" id="">
        <i class="ace-icon fa fa-floppy-o bigger-120 blue" ></i>
        收发货方档案
    </button>
</div>

<form id="" method="post" class="form-horizontal" role="form" >
    <div class="col-xs-12">
        <div class="form-group">
            <label class="control-label col-label no-padding-right" for="supplierCode">订单日期</label>
            <div class="col-xs-3">
                <div class="clearfix">
                    <input class="col-xs-10 col-xs-12"  name="orderTime" id="orderTime" type="text" placeholder="订单日期" onClick="WdatePicker({isShowClear:true,readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})" />
                </div>
            </div>
            <label class="control-label col-label no-padding-right" for="supplierCode">开单员</label>
            <div class="col-xs-3">
                <div class="clearfix">
                    <input class="col-xs-10 col-xs-12"  name="" id="" type="text" placeholder="开单员"  />
                </div>
            </div>
            <label class="control-label col-label no-padding-right" for="supplierCode">预发货时间</label>
            <div class="col-xs-3">
                <div class="clearfix">
                    <input class="col-xs-10 col-xs-12"  name="" id="" type="text" placeholder="预计发货时间" onClick="WdatePicker({isShowClear:true,readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})"/>
                </div>
            </div>
        </div>
    </div>
    <div class="col-xs-12">
        <div class="form-group">
            <label class="control-label col-label no-padding-right" for="supplierCode">客户名称</label>
            <div class="col-xs-3">
                <div class="clearfix">
                    <input class="col-xs-10 col-xs-12"  name="orderTime" id="orderTime" type="text" placeholder="客户名称" onClick="WdatePicker({isShowClear:true,readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})" />
                </div>
            </div>
            <label class="control-label col-label no-padding-right" for="supplierCode">配送仓库</label>
            <div class="col-xs-3">
                <div class="clearfix">
                    <input class="col-xs-10 col-xs-12"  name="" id="" type="text" placeholder="配送仓库"  />
                </div>
            </div>
            <label class="control-label col-label no-padding-right" for="supplierCode">备注</label>
            <div class="col-xs-3">
                <div class="clearfix">
                    <input class="col-xs-10 col-xs-12"  name="" id="" type="text" placeholder="备注" />
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
                        <input class="col-xs-10 col-xs-12"  name="orderTime" id="orderTime" type="text" placeholder="名称"  />
                    </div>
                </div>
                <label class="control-label col-label no-padding-right" for="supplierCode">联系人</label>
                <div class="col-xs-3">
                    <div class="clearfix">
                        <input class="col-xs-10 col-xs-12"  name="" id="" type="text" placeholder="联系人"  />
                    </div>
                </div>
                <label class="control-label col-label no-padding-right" for="supplierCode">联系电话</label>
                <div class="col-xs-3">
                    <div class="clearfix">
                        <input class="col-xs-10 col-xs-12"  name="" id="" type="text" placeholder="联系电话" />
                    </div>
                </div>
            </div>
        </div>
        <div class="col-xs-12">
            <div class="form-group">
                <label class="control-label col-label no-padding-right" for="supplierCode">地址</label>
                <div class="col-xs-9">
                    <div class="clearfix">
                        <input class="col-xs-10 col-xs-12"  name="orderTime" id="orderTime" type="text" placeholder="地址" onClick="WdatePicker({isShowClear:true,readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})" />
                    </div>
                </div>

            </div>
        </div>
    </div>
</form>
<div class="col-sm-12">
    <div class="tabbable" style="width:1000px;" >
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
                <span style="cursor:pointer" id="goodsListDivBlock"><button type="button" class="btn btn-info" id="bootbox-confirm">添加一行</button></span>
                <table id="orderGoodsListTable" class="table table-striped table-bordered table-hover dataTable no-footer" role="grid" aria-describedby="dynamic-table_info">
                    <thead>
                    <tr role="row"><th class="center sorting_disabled" rowspan="1" colspan="1" aria-label="">
                        操作
                    </th>
                    <#--<th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Status: activate to sort column ascending">序号</th>-->
                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Domain: activate to sort column ascending">货品编码</th>
                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Price: activate to sort column ascending">货品名称</th>
                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Update: activate to sort column ascending">规格</th>
                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">单位</th>
                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">数量</th>
                    </thead>
                    <tbody id="goodsInfoListDiv"></tbody>

                </table>
            </div>

            <div id="profile4" class="tab-pane">

            </div>

        </div>
    </div>
</div>


<div class="col-xs-12">
    <div class="ui-jqgrid ui-widget ui-widget-content ui-corner-all" id="gbox_grid-table" dir="ltr"
         style="width: 823px;">
        <div class="jqgrid-overlay ui-widget-overlay" id="lui_grid-table"></div>
        <div class="loading ui-state-default ui-state-active" id="load_grid-table" style="display: none;">Loading...
        </div>
        <div class="ui-jqgrid-view " role="grid" id="gview_grid-table" style="width: 823px;">

            <div class="ui-jqgrid-hdiv ui-state-default" style="width: 823px;">
                <div class="ui-jqgrid-hbox">
                    <table class="ui-jqgrid-htable ui-common-table " style="width: 805px;" role="presentation"
                           aria-labelledby="gbox_grid-table">
                        <thead>
                        <tr class="ui-jqgrid-labels" role="row">
                            <th></th>
                            <th></th>
                            <th></th>
                            <th></th>
                            <th></th>
                            <th></th>
                            <th></th>
                            <th></th>
                            <th></th>
                        </tr>
                        </thead>
                    </table>
                </div>
            </div>
            <div class="ui-jqgrid-bdiv" style="height: 250px; width: 823px;">
                <div style="position:relative;">

                    <table id="grid-table" tabindex="0" role="presentation" aria-multiselectable="true"
                           aria-labelledby="gbox_grid-table" class="ui-jqgrid-btable ui-common-table"
                           style="width: 805px;">
                        <tbody>
                        <tr class="jqgfirstrow" role="row">
                            <td role="gridcell" style="height:0px;width:35px;"></td>
                            <td role="gridcell" style="height:0px;width:25px;"></td>
                            <td role="gridcell" style="height:0px;width:80px;"></td>
                            <td role="gridcell" style="height: 0px; width: 65px;"></td>
                            <td role="gridcell" style="height: 0px; width: 98px;"></td>
                            <td role="gridcell" style="height: 0px; width: 164px;"></td>
                            <td role="gridcell" style="height: 0px; width: 76px;"></td>
                            <td role="gridcell" style="height: 0px; width: 98px;"></td>
                            <td role="gridcell" style="height: 0px; width: 164px;"></td>
                        </tr>
                        <tr role="row" id="1" tabindex="-1" class="jqgrow ui-row-ltr ui-widget-content">
                            <td role="gridcell" style="text-align:center;width: 35px;" aria-describedby="grid-table_cb">
                                <input role="checkbox" type="checkbox" id="jqg_grid-table_1" class="cbox cbox"
                                       name="jqg_grid-table_1"></td>
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

                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>


    <!-- PAGE CONTENT ENDS -->
</div>


<div class="col-xs-12">
    <button class="btn btn-white btn-info btn-bold btn-interval" id="orderPlaceConTableBtn">
        <i class="ace-icon fa fa-floppy-o bigger-120 blue" ></i>
        确认下单
    </button>
</div>


<script type="text/javascript">
    var scripts = [ null,
        "/components/jquery-validation/dist/jquery.validate.min.js",
        "/components/jquery-validation/src/localization/messages_zh.js",
        "/components/jquery-validation/dist/additional-methods.js", null ];
    $(".page-content-area").ace_ajax("loadScripts", scripts, function() {
        /*jQuery(function($) {
            validateForm();//校验表单信息
            /!*validateFormPageBody();*!/
        })*/
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
        //validateForm();
    }
    $(function () {
        $("#goodsListDivBlock").click(function () {
            var goodsInfoListDiv = "";
            goodsInfoListDiv =goodsInfoListDiv + "<tr role='row' class='odd' align='center'>";
            goodsInfoListDiv =goodsInfoListDiv + "<td><button type='button' onclick='deleteGood(this)' class='btn btn-minier btn-danger'>删除</button></td>";
            /* goodsInfoListDiv =goodsInfoListDiv + "<td><input id='deleteOrNot' type='checkbox'/></td>";*/
            goodsInfoListDiv =goodsInfoListDiv + "<td>HP0001</td>";
            goodsInfoListDiv =goodsInfoListDiv + "<td>牛肉</td>";
            goodsInfoListDiv =goodsInfoListDiv + "<td>500g</td>";
            goodsInfoListDiv =goodsInfoListDiv + "<td>袋</td>";
            goodsInfoListDiv =goodsInfoListDiv + "<td>20</td>";
            goodsInfoListDiv =goodsInfoListDiv + "</tr>";
        })
    })
</script>
</body>