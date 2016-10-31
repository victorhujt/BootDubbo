<head>
    <style type="text/css">
        #goodsListDiv {
            position:fixed;
            left:285px;
            top:85px;
            width:946px;
            height:500px;
            z-index:3;
            overflow: auto;
            border:solid #7A7A7A 2px;
        }
        #consignorListDiv {
            position:fixed;
            left:285px;
            top:77px;
            width:946px;
            height:500px;
            z-index:3;
            overflow: auto;
            border:solid #7A7A7A 2px;
        }
        #consigneeListDiv {
            position:fixed;
            left:285px;
            top:77px;
            width:946px;
            height:500px;
            z-index:3;
            overflow: auto;
            border:solid #7A7A7A 2px;
        }
        #supportListDiv {
            position:fixed;
            left:285px;
            top:77px;
            width:946px;
            height:500px;
            z-index:3;
            overflow: auto;
            border:solid #7A7A7A 2px;
        }
        input{
            color: black;
        }
    </style>
</head>

<body class="no-skin">
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
                    <tbody id="goodsSelectListTbody"></tbody>
                </table>


            </form>
        </div>
    </div>
    <div class="modal-footer"><span id="goodsListDivNoneBottom" style="cursor:pointer"><button  data-bb-handler="cancel" type="button" class="btn btn-default">Cancel</button></span><button id="goodsEnter" data-bb-handler="confirm" type="button" class="btn btn-primary">OK</button></div>
</div>

<!--consignorListDiv-->
<div class="modal-content" id="consignorListDiv" style="display: none;">
    <div class="modal-header"><span id="consignorListDivNoneTop" style="cursor:pointer"><button type="button" id="" style="cursor:pointer" class="bootbox-close-button close" data-dismiss="modal" aria-hidden="true">×</button></span>
        <h4 class="modal-title">发货方联系人</h4></div>
    <div class="modal-body">
        <div class="bootbox-body">
            <form id="consignorSelConditionForm" class="form-horizontal" role="form">
                <input id="purpose2" name="purpose" type="hidden" value="2">
                <div class="form-group">
                    <label class="control-label col-sm-1 no-padding-right" for="name">名称</label>
                    <div class="col-sm-3">
                        <div class="clearfix">
                            <input  id = "consignorName2" name="contactCompanyName" type="text" style="color: black" class="form-control input-sm" placeholder="" aria-controls="dynamic-table">
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-sm-1 no-padding-right" for="name">联系人</label>
                    <div class="col-sm-3">
                        <div class="clearfix">
                            <input  id = "consignorPerson2" name="contactName" type="text" style="color: black" class="form-control input-sm" placeholder="" aria-controls="dynamic-table">
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-sm-1 no-padding-right" for="name">联系电话</label>
                    <div class="col-sm-3">
                        <div class="clearfix">
                            <input  id = "consignorPhoneNumber2" name="phone" type="text" style="color: black" class="form-control input-sm" placeholder="" aria-controls="dynamic-table">
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-sm-1 no-padding-right" for="name"></label>
                    <div class="col-sm-3">
                        <div class="clearfix">
                            <span id="consignorSelectFormBtn" class="btn btn-info btn-sm popover-info">筛选</span>
                        </div>
                    </div>
                </div>
            </form>
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
                    <tbody id="contactSelectListTbody2"></tbody>
                </table>


            </form>
        </div>
    </div>
    <div class="modal-footer"><span id="consignorListDivNoneBottom" style="cursor:pointer"><button  data-bb-handler="cancel" type="button" class="btn btn-default">Cancel</button></span><button id="contactinEnter" data-bb-handler="confirm" type="button" class="btn btn-primary">OK</button></div>
</div>
<!--consigneeListDiv-->
<div class="modal-content" id="consigneeListDiv" style="display: none;">
    <div class="modal-header"><span id="consigneeListDivNoneTop" style="cursor:pointer"><button type="button" id="" style="cursor:pointer" class="bootbox-close-button close" data-dismiss="modal" aria-hidden="true">×</button></span>
        <h4 class="modal-title">收货方联系人</h4></div>
    <div class="modal-body">
        <div class="bootbox-body">
            <form id="consigneeSelConditionForm" class="form-horizontal" role="form">
                <input id="purpose2" name="purpose" type="hidden" value="1">
                <div class="form-group">
                    <label class="control-label col-sm-1 no-padding-right" for="name">名称</label>
                    <div class="col-sm-3">
                        <div class="clearfix">
                            <input  id = "consignorName1" name="contactCompanyName" type="text" style="color: black" class="form-control input-sm" placeholder="" aria-controls="dynamic-table">
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-sm-1 no-padding-right" for="name">联系人</label>
                    <div class="col-sm-3">
                        <div class="clearfix">
                            <input  id = "consignorPerson1" name="contactName" type="text" style="color: black" class="form-control input-sm" placeholder="" aria-controls="dynamic-table">
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-sm-1 no-padding-right" for="name">联系电话</label>
                    <div class="col-sm-3">
                        <div class="clearfix">
                            <input  id = "consignorPhoneNumber1" name="phone" type="text" style="color: black" class="form-control input-sm" placeholder="" aria-controls="dynamic-table">
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-sm-1 no-padding-right" for="name"></label>
                    <div class="col-sm-3">
                        <div class="clearfix">
                            <span id="consigneeSelectFormBtn" class="btn btn-info btn-sm popover-info">筛选</span>
                        </div>
                    </div>
                </div>
            </form>
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
                    <tbody id="contactSelectListTbody1"></tbody>
                </table>


            </form>
        </div>
    </div>
    <div class="modal-footer"><span id="consigneeListDivNoneBottom" style="cursor:pointer"><button  data-bb-handler="cancel" type="button" class="btn btn-default">Cancel</button></span><button id="contactoutEnter" data-bb-handler="confirm" type="button" class="btn btn-primary">OK</button></div>
</div>
<!--supportListDiv-->
<div class="modal-content" id="supportListDiv" style="display: none;">
    <div class="modal-header"><span id="supportListDivNoneTop" style="cursor:pointer"><button type="button" id="" style="cursor:pointer" class="bootbox-close-button close" data-dismiss="modal" aria-hidden="true">×</button></span>
        <h4 class="modal-title">供应商联系人</h4></div>
    <div class="modal-body">
        <div class="bootbox-body">
            <form id="supplierSelConditionForm" class="form-horizontal" role="form">
                <div class="form-group">
                    <label class="control-label col-sm-1 no-padding-right" for="name">名称</label>
                    <div class="col-sm-3">
                        <div class="clearfix">
                            <input  id = "supplierName" name="supplierName" type="text" style="color: black" class="form-control input-sm" placeholder="" aria-controls="dynamic-table">
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-sm-1 no-padding-right" for="name">联系人</label>
                    <div class="col-sm-3">
                        <div class="clearfix">
                            <input  id = "contactName" name="contactName" type="text" style="color: black" class="form-control input-sm" placeholder="" aria-controls="dynamic-table">
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-sm-1 no-padding-right" for="name">联系电话</label>
                    <div class="col-sm-3">
                        <div class="clearfix">
                            <input  id = "contactPhone" name="contactPhone" type="text" style="color: black" class="form-control input-sm" placeholder="" aria-controls="dynamic-table">
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-sm-1 no-padding-right" for="name"></label>
                    <div class="col-sm-3">
                        <div class="clearfix">
                            <span id="supplierSelectFormBtn" class="btn btn-info btn-sm popover-info">筛选</span>
                        </div>
                    </div>
                </div>
            </form>
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
                    <tbody id="supplierSelectListTbody"></tbody>
                </table>


            </form>
        </div>
    </div>
    <div class="modal-footer"><span id="supportListDivNoneBottom" style="cursor:pointer"><button  data-bb-handler="cancel" type="button" class="btn btn-default">Cancel</button></span><button id="supplierEnter" data-bb-handler="confirm" type="button" class="btn btn-primary">OK</button></div>
</div>

<div class="main-container ace-save-state" id="main-container">


    <!-- /section:basics/sidebar -->
    <div class="main-content">
        <div class="main-content-inner">

            <!-- /section:basics/content.breadcrumbs -->
            <div class="page-content">
                <div class="page-header">
                    <p>
                        基本信息
                    </p>
                </div>

                <div class="row">
                    <div class="col-xs-12">
                        <div class="widget-box" style="border: none">
                            <div class="widget-body">
                                <div class="widget-main">
                                    <form id="" method="post" class="form-horizontal" role="form" >
                                        <div class="form-group">
                                            <label class="control-label col-sm-1 no-padding-right" for="name">订单日期</label>
                                            <div class="col-sm-6">
                                                <div class="clearfix">
                                                    <input id="orderTime" style="color: #000" name="orderTime" type="text" class="form-control input-sm" placeholder="" aria-controls="dynamic-table"1>

                                                </div>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="control-label col-sm-1 no-padding-right" for="name">客户订单编号</label>
                                            <div class="col-sm-6">
                                                <div class="clearfix">
                                                    <input id="custOrderCode" name="custOrderCode" type="text" style="color: #000">
                                                </div>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="control-label col-sm-1 no-padding-right" for="name">订单类型</label>
                                            <div class="col-sm-6">
                                                <div class="clearfix">
                                                    <select  id="orderTypeSel" name="orderType">
                                                        <option value="60">运输订单</option>
                                                        <option value="61">仓配订单</option>
                                                    </select>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="form-group" id="businessTypeDiv" style="display: none">
                                            <label class="control-label col-sm-1 no-padding-right" for="name">业务类型</label>
                                            <div class="col-sm-6">
                                                <div class="clearfix">
                                                <#--<span id="businessTypeDiv" style="display: none">-->
                                                    <select  id="businessType" name="businessType">
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
                                                <#--  </span>-->
                                                </div>
                                            </div>
                                        </div>

                                        <div class="form-group" id="provideTransportDiv" style="display: none">
                                            <label class="control-label col-sm-1 no-padding-right" for="name">是否需要运输</label>
                                            <div class="col-sm-6">
                                                <div class="clearfix">
                                                <#-- <span id="provideTransportDiv" style="display: none">-->
                                                    <input  id="provideTransport" type="checkbox" name = ""/>
                                                    <input id="provideTransportHel" type="hidden" name="provideTransport"  value="0" />
                                                <#-- </span>-->
                                                </div>
                                            </div>
                                        </div>

                                        <div class="form-group">
                                            <label class="control-label col-sm-1 no-padding-right" for="name">店铺</label>
                                            <div class="col-sm-6">
                                                <div class="clearfix">
                                                    <select id="storeCode" name="storeCode">
                                                        <option value="线下销售">线下销售</option>
                                                        <option value="众品天猫生鲜旗舰店">众品天猫生鲜旗舰店</option>
                                                        <option value="众品京东旗舰店">众品京东旗舰店</option>
                                                    </select>
                                                </div>
                                            </div>
                                        </div>

                                        <div class="form-group">
                                            <label class="control-label col-sm-1 no-padding-right" for="name">备注</label>
                                            <div class="col-sm-6">
                                                <div class="clearfix">
                                                    <input  name="notes" style="color: #000"  type="text" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" >
                                                </div>
                                            </div>
                                        </div>

                                    </form>
                                </div>
                            </div>
                            <div class="widget-body">
                                <div class="widget-main">
                                    <form name="orderInfoTable" id="orderInfoTable"  class="form-horizontal" role="form" >
                                        <div class="col-sm-12">
                                            <!-- #section:elements.tab.option -->
                                            <div class="tabbable" style="width:1000px;" >
                                                <ul class="nav nav-tabs" id="myTab4">
                                                   <li class="active">
                                                        <a data-toggle="tab" href="#home4" aria-expanded="false">货品明细</a>
                                                    </li>

                                                    <li class="transLi">
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
                                                        <#--货品明细-->

                                                            <tbody id="goodsInfoListDiv"></tbody>

                                                        </table>
                                                        <div class="row">
                                                            <div class="col-xs-6">
                                                                <div class="dataTables_info" id="dynamic-table_info" role="status" aria-live="polite">

                                                                </div>
                                                            </div>
                                                            <div class="col-xs-6">
                                                            </div></div>

                                                    </div>

                                                    <div id="profile4" class="tab-pane">

                                                        <div class="page-header">
                                                            <h4>运输基本信息</h4>
                                                        </div>

                                                        <div class="row">

                                                            <div id="dynamic-table_filter" class="dataTables_length">

                                                                <div class="form-group" >
                                                                    <label class="control-label col-sm-1 no-padding-right" for="name">数量</label>
                                                                    <div class="col-sm-6">
                                                                        <div class="clearfix">
                                                                            <input id="quantity" name="quantity" type="text" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" >
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="form-group" >
                                                                    <label class="control-label col-sm-1 no-padding-right" for="name">重量</label>
                                                                    <div class="col-sm-6">
                                                                        <div class="clearfix">
                                                                            <input id="weight" name="weight" type="text" class="form-control input-sm" placeholder="" aria-controls="dynamic-table">
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="form-group" >
                                                                    <label class="control-label col-sm-1 no-padding-right" for="name">体积</label>
                                                                    <div class="col-sm-6">
                                                                        <div class="clearfix">
                                                                            <input id="cubage" name="cubage" type="text" class="form-control input-sm" placeholder="" aria-controls="dynamic-table">(长*宽*高,单位:cm)
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="form-group" >
                                                                    <label class="control-label col-sm-1 no-padding-right" for="name">合计标准箱</label>
                                                                    <div class="col-sm-6">
                                                                        <div class="clearfix">
                                                                            <input id="totalStandardBox" name="totalStandardBox" type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table">
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="form-group" >
                                                                    <label class="control-label col-sm-1 no-padding-right" for="name">出发地</label>
                                                                    <div class="col-sm-6">
                                                                        <div class="clearfix">
                                                                            <input id="departurePlace" name="departurePlace" type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" readonly="readonly">
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="form-group" >
                                                                    <label class="control-label col-sm-1 no-padding-right" for="name">目的地</label>
                                                                    <div class="col-sm-6">
                                                                        <div class="clearfix">
                                                                            <input id="destination" name="destination" type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" readonly="readonly">
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="form-group" >
                                                                    <label class="control-label col-sm-1 no-padding-right" for="name">取货时间</label>
                                                                    <div class="col-sm-6">
                                                                        <div class="clearfix">
                                                                            <input id="pickupTime" name="pickupTime" type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" onClick="WdatePicker({isShowClear:true,readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})">
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="form-group" >
                                                                    <label class="control-label col-sm-1 no-padding-right" for="name">期望送达时间</label>
                                                                    <div class="col-sm-6">
                                                                        <div class="clearfix">
                                                                            <input id="expectedArrivedTime" name="expectedArrivedTime" type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" onClick="WdatePicker({isShowClear:true,readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})">
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="form-group" >
                                                                    <label class="control-label col-sm-1 no-padding-right" for="name">是否加急</label>
                                                                    <div class="col-sm-6">
                                                                        <div class="clearfix">
                                                                            <input id="urgent" type="checkbox" name=""  />
                                                                            <input id="urgentHel" type="hidden" name="urgent"  value="0" />
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="form-group" >
                                                                    <label class="control-label col-sm-1 no-padding-right" for="name">运输要求</label>
                                                                    <div class="col-sm-6">
                                                                        <div class="clearfix">
                                                                            <input name="transRequire" type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table">
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>



                                                        </div>


                                                        <div class="page-header">
                                                            <h4>发货方信息</h4>
                                                        </div>
                                                        <span style="cursor:pointer" id="consignorListDivBlock"><button type="button" class="btn btn-info" id="consignorselbtn">选择</button></span>

                                                        <div id="consignorin">
                                                            <div class="form-group" >
                                                                <label class="control-label col-sm-1 no-padding-right" for="name">名称</label>
                                                                <div class="col-sm-6">
                                                                    <div class="clearfix">
                                                                        <input id="consignorCode" name="consignorCode" type="hidden">
                                                                        <input id="contactCompanyName"  name="contactCompanyName" type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" >

                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="form-group" >
                                                                <label class="control-label col-sm-1 no-padding-right" for="name">联系人</label>
                                                                <div class="col-sm-6">
                                                                    <div class="clearfix">
                                                                        <input id="consignorName"   name="consignorName" type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" >


                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="form-group" >
                                                                <label class="control-label col-sm-1 no-padding-right" for="name">联系电话</label>
                                                                <div class="col-sm-6">
                                                                    <div class="clearfix">
                                                                        <input name="" type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" >

                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="form-group" >
                                                                <label class="control-label col-sm-1 no-padding-right" for="name">传真</label>
                                                                <div class="col-sm-6">
                                                                    <div class="clearfix">
                                                                        <input name="" type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" >

                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="form-group" >
                                                                <label class="control-label col-sm-1 no-padding-right" for="name">Email</label>
                                                                <div class="col-sm-6">
                                                                    <div class="clearfix">
                                                                        <input name="" type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" >
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="form-group" >
                                                                <label class="control-label col-sm-1 no-padding-right" for="name">邮编</label>
                                                                <div class="col-sm-6">
                                                                    <div class="clearfix">
                                                                        <input name="" type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" >
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="form-group" >
                                                                <label class="control-label col-sm-1 no-padding-right" for="name">地址</label>
                                                                <div class="col-sm-6">
                                                                    <div class="clearfix">
                                                                        <select><option value="">--省--</option></select>
                                                                        <select><option value="">--市--</option></select>
                                                                        <select><option value="">--区/县--</option></select>
                                                                        <select><option value="">--乡镇/街道--</option></select>
                                                                        <input name="" type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" >
                                                                    </div>
                                                                </div>
                                                            </div>

                                                        </div>


                                                        <div class="page-header">
                                                            <h4>收货方信息</h4>
                                                        </div>
                                                        <span style="cursor:pointer" id="consigneeListDivBlock"><button type="button" class="btn btn-info" id="consigneeselbtn">选择</button></span>
                                                        <div id="consignorout">
                                                            <div class="form-group" >
                                                                <label class="control-label col-sm-1 no-padding-right" for="name">名称</label>
                                                                <div class="col-sm-6">
                                                                    <div class="clearfix">
                                                                        <input id="consigneeCode" name="consigneeCode" type="hidden">
                                                                        <input id="contactCompanyName"  name="contactCompanyName" type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" >

                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="form-group" >
                                                                <label class="control-label col-sm-1 no-padding-right" for="name">联系人</label>
                                                                <div class="col-sm-6">
                                                                    <div class="clearfix">
                                                                        <input name="consigneeName" type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" >

                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="form-group" >
                                                                <label class="control-label col-sm-1 no-padding-right" for="name">联系电话</label>
                                                                <div class="col-sm-6">
                                                                    <div class="clearfix">
                                                                        <input name="" type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" >

                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="form-group" >
                                                                <label class="control-label col-sm-1 no-padding-right" for="name">传真</label>
                                                                <div class="col-sm-6">
                                                                    <div class="clearfix">
                                                                        <input name="" type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" >

                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="form-group" >
                                                                <label class="control-label col-sm-1 no-padding-right" for="name">Email</label>
                                                                <div class="col-sm-6">
                                                                    <div class="clearfix">
                                                                        <input name="" type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" >
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="form-group" >
                                                                <label class="control-label col-sm-1 no-padding-right" for="name">邮编</label>
                                                                <div class="col-sm-6">
                                                                    <div class="clearfix">
                                                                        <input name="" type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" >
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="form-group" >
                                                                <label class="control-label col-sm-1 no-padding-right" for="name">地址</label>
                                                                <div class="col-sm-6">
                                                                    <div class="clearfix">
                                                                        <select><option value="">--省--</option></select>
                                                                        <select><option value="">--市--</option></select>
                                                                        <select><option value="">--区/县--</option></select>
                                                                        <select><option value="">--乡镇/街道--</option></select>
                                                                        <input name="" type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" >
                                                                    </div>
                                                                </div>
                                                            </div>

                                                        </div>
                                                    </div>

                                                    <div id="dropdown14" class="tab-pane">

                                                        <div class="page-header">
                                                            <h4>仓配基本信息</h4>
                                                        </div>

                                                        <div class="row">
                                                            <div id="dynamic-table_filter" class="dataTables_length">


                                                                <div class="form-group" >
                                                                    <label class="control-label col-sm-1 no-padding-right" for="name">仓库名称</label>
                                                                    <div class="col-sm-6">
                                                                        <div class="clearfix">
                                                                            <select id="warehouseName" name="warehouseName"  value="${(orderInfo.wareHouseName)!""}">
                                                                            <#list warehouseListByCustCode! as warehouse>
                                                                                <option value="${(warehouseListByCustCode.warehouseCode)!}">${(warehouseListByCustCode.warehouseName)!}</option>
                                                                            </#list>
                                                                            </select>

                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="form-group" >
                                                                    <label class="control-label col-sm-1 no-padding-right" for="name">入库预计到达时间</label>
                                                                    <div class="col-sm-6">
                                                                        <div class="clearfix">
                                                                            <input id="arriveTime" name="arriveTime"  type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" onClick="WdatePicker({isShowClear:true,readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})">

                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="form-group" >
                                                                    <label class="control-label col-sm-1 no-padding-right" for="name">车牌号</label>
                                                                    <div class="col-sm-6">
                                                                        <div class="clearfix">
                                                                            <input id="plateNumber" name="plateNumber" type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table">

                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="form-group" >
                                                                    <label class="control-label col-sm-1 no-padding-right" for="name">司机姓名</label>
                                                                    <div class="col-sm-6">
                                                                        <div class="clearfix">
                                                                            <input id="driverName" name="driverName" type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table"><br/>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="form-group" >
                                                                    <label class="control-label col-sm-1 no-padding-right" for="name">联系电话</label>
                                                                    <div class="col-sm-6">
                                                                        <div class="clearfix">
                                                                            <input id="contactNumber" name="contactNumber" type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table">
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <br/>
                                                        </div>

                                                        <div class="page-header">
                                                            <h4>供应商信息</h4>
                                                        </div>
                                                        <span style="cursor:pointer" id="supportListDivBlock"><button type="button" class="btn btn-info" id="bootbox-confirm">选择</button></span>
                                                        <div id="support" class="">

                                                            <div class="form-group" >
                                                                <label class="control-label col-sm-1 no-padding-right" for="name">名称</label>
                                                                <div class="col-sm-6">
                                                                    <div class="clearfix">
                                                                        <input id="supportCode" name="supportCode" type="hidden">
                                                                        <input id="supportName"  name="supportName" type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" >

                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="form-group" >
                                                                <label class="control-label col-sm-1 no-padding-right" for="name">联系人</label>
                                                                <div class="col-sm-6">
                                                                    <div class="clearfix">
                                                                        <input name="contactName" type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" >

                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="form-group" >
                                                                <label class="control-label col-sm-1 no-padding-right" for="name">联系电话</label>
                                                                <div class="col-sm-6">
                                                                    <div class="clearfix">
                                                                        <input name="" type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" >

                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="form-group" >
                                                                <label class="control-label col-sm-1 no-padding-right" for="name">传真</label>
                                                                <div class="col-sm-6">
                                                                    <div class="clearfix">
                                                                        <input name="" type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" >

                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="form-group" >
                                                                <label class="control-label col-sm-1 no-padding-right" for="name">Email</label>
                                                                <div class="col-sm-6">
                                                                    <div class="clearfix">
                                                                        <input name="" type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" >
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="form-group" >
                                                                <label class="control-label col-sm-1 no-padding-right" for="name">邮编</label>
                                                                <div class="col-sm-6">
                                                                    <div class="clearfix">
                                                                        <input name="" type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" >
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="form-group" >
                                                                <label class="control-label col-sm-1 no-padding-right" for="name">地址</label>
                                                                <div class="col-sm-6">
                                                                    <div class="clearfix">
                                                                        <select><option value="">--省--</option></select>
                                                                        <select><option value="">--市--</option></select>
                                                                        <select><option value="">--区/县--</option></select>
                                                                        <select><option value="">--乡镇/街道--</option></select>
                                                                        <input name="" type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" >
                                                                    </div>
                                                                </div>
                                                            </div>

                                                        </div>
                                                    </div>
                                                </div>
                                            </div>

                                            <!-- /section:elements.tab.option -->
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                        <!-- PAGE CONTENT ENDS -->
                    </div><!-- /.col -->
                    <div style="float: inherit">
                        <button type="button" class="btn btn-info" id="orderPlaceConTableBtn">确认下单</button>
                    </div>
                </div><!-- /.row -->
            </div><!-- /.page-content -->
        </div>
    </div><!-- /.main-content -->


</div><!-- /.main-container -->


<link rel="stylesheet" href="../components/chosen/chosen.css" />
<script src="../components/chosen/chosen.jquery.js"></script>


<#include "common/include.ftl">
<script type="text/javascript">
    var scripts = [ null, "", null ]
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

    }
</script>
<!-- inline scripts related to this page -->
<script type="text/javascript">

    $(function(){




        $("#provideTransport").change(function () {
            if($(this).prop("checked")){
                $("#provideTransportHel").val("1");
            } else {
                $("#provideTransportHel").val("0");
            }
        });
        $("#urgent").change(function(){
            if($(this).prop("checked")){
                $("#urgentHel").val("1");
            }else{
                $("#urgentHel").val("0");
            }
        });



        $("#orderPlaceConTableBtn").click(function () {

            var jsonStr = {};
            //订单基本信息
            //$dp.$('orderTimePre').value;
            jsonStr.orderTime = $dp.$('orderTime').value;
            jsonStr.custOrderCode = $("#custOrderCode").val();
            jsonStr.orderType = $("#orderTypeSel").val();
            jsonStr.businessType = $("#businessType").val();
            jsonStr.provideTransport = $("#provideTransportHel").val();
            jsonStr.storeCode = $("#storeCode").val();
            jsonStr.notes = $("#notes").val();
            //运输基本信息
            jsonStr.quantity = $("#quantity").val();
            jsonStr.weight = $("#weight").val();
            jsonStr.cubage = $("#cubage").val();
            jsonStr.totalStandardBox = $("#totalStandardBox").val();
            jsonStr.departurePlace = $("#departurePlace").val();
            jsonStr.destination = $("#destination").val();
            jsonStr.pickupTime = $dp.$('pickupTime').value;
            jsonStr.expectedArrivedTime = $dp.$('expectedArrivedTime').value;
            jsonStr.urgent = $("#urgentHel").val();
            jsonStr.consignorCode = $("#consignorCode").val();
            jsonStr.consignorName = $("#consignorName").val();
            jsonStr.consigneeCode = $("#consigneeCode").val();
            jsonStr.consigneeName = $("#consigneeName").val();
            //仓配基本信息
            jsonStr.warehouseName = $("#warehouseName").val();
            jsonStr.arriveTime = $dp.$('arriveTime').value;
            jsonStr.plateNumber = $("#plateNumber").val();
            jsonStr.driverName = $("#driverName").val();
            jsonStr.contactNumber = $("#contactNumber").val();
            jsonStr.supportCode = $("#supportCode").val();
            jsonStr.supportName = $("#supportName").val();

            var tag = "place";
            var ofcOrderDTO = JSON.stringify(jsonStr);
            //var url = "/ofc/orderPlaceCon/" + ofcOrderDTO + "/" + tag;
            console.log("111111111221111111--"+tag);
            //alert(url);
            // xescm.common.loadPage(url);
            xescm.common.submit("/ofc/orderPlaceCon",{"ofcOrderDTOStr":ofcOrderDTO,"tag":tag},function () {
                console.log("1111111111111111--"+tag);
                debugger
                //xescm.common.goBack("/ofc/orderPlace");
            })
        });






        $("#goodsSelectFormBtn").click(function () {
            CommonClient.post(sys.rootPath + "/ofc/goodsSelect", $("#goodsSelConditionForm").serialize(), function(data) {
                data=eval(data);
                var goodsList = "";
                $.each(data,function (index,cscGoods) {
                    goodsList =goodsList + "<tr role='row' class='odd'>";
                    goodsList =goodsList + "<td class='center'> "+"<label class='pos-rel'>"+"<input type='checkbox' class='ace'>"+"<span class='lbl'></span>"+"</label>"+"</td>";
                    goodsList =goodsList + "<td>"+(index+1)+"</td>";
                    goodsList =goodsList + "<td>"+cscGoods.goodsCode+"</td>";
                    goodsList =goodsList + "<td>"+cscGoods.goodsName+"</td>";
                    goodsList =goodsList + "<td>"+cscGoods.specification+"</td>";
                    goodsList =goodsList + "<td>"+cscGoods.unit+"</td>";
                });
                $("#goodsSelectListTbody").html(goodsList);
            },"json");
        });

        $("#consignorSelectFormBtn").click(function () {
            //$.post("/ofc/contactSelect",$("#consignorSelConditionForm").serialize(),function (data) {
            CommonClient.post(sys.rootPath + "/ofc/contactSelect", $("#consignorSelConditionForm").serialize(), function(data) {
                data=eval(data);
                var contactList = "";
                $.each(data,function (index,CscContantAndCompanyDto) {
                    contactList =contactList + "<tr role='row' class='odd'>";
                    contactList =contactList + "<td class='center'> "+"<label class='pos-rel'>"+"<input name='consignorSel' type='radio' class='ace'>"+"<span class='lbl'></span>"+"</label>"+"</td>";
                    contactList =contactList + "<td>"+(index+1)+"</td>";
                    contactList =contactList + "<td>"+CscContantAndCompanyDto.contactCompanyName+"</td>";
                    contactList =contactList + "<td>"+CscContantAndCompanyDto.contactName+"</td>";
                    contactList =contactList + "<td>"+CscContantAndCompanyDto.phone+"</td>";
                    contactList =contactList + "<td>"+CscContantAndCompanyDto.fax+"</td>";
                    contactList =contactList + "<td>"+CscContantAndCompanyDto.email+"</td>";
                    contactList =contactList + "<td>"+CscContantAndCompanyDto.postCode+"</td>";
                    contactList =contactList + "<td>"+CscContantAndCompanyDto.address+"</td>";
                });
                $("#contactSelectListTbody2").html(contactList);
            },"json");
        });

        $("#consigneeSelectFormBtn").click(function () {
            CommonClient.post(sys.rootPath + "/ofc/contactSelect", $("#consigneeSelConditionForm").serialize(), function(data) {
                data=eval(data);
                var contactList = "";
                $.each(data,function (index,CscContantAndCompanyDto) {
                    contactList =contactList + "<tr role='row' class='odd'>";
                    contactList =contactList + "<td class='center'> "+"<label class='pos-rel'>"+"<input name='consigneeSel' type='radio' class='ace'>"+"<span class='lbl'></span>"+"</label>"+"</td>";
                    contactList =contactList + "<td>"+(index+1)+"</td>";
                    contactList =contactList + "<td>"+CscContantAndCompanyDto.contactCompanyName+"</td>";
                    contactList =contactList + "<td>"+CscContantAndCompanyDto.contactName+"</td>";
                    contactList =contactList + "<td>"+CscContantAndCompanyDto.phone+"</td>";
                    contactList =contactList + "<td>"+CscContantAndCompanyDto.fax+"</td>";
                    contactList =contactList + "<td>"+CscContantAndCompanyDto.email+"</td>";
                    contactList =contactList + "<td>"+CscContantAndCompanyDto.postCode+"</td>";
                    contactList =contactList + "<td>"+CscContantAndCompanyDto.address+"</td>";
                    $("#contactSelectListTbody1").html(contactList);
                });
            },"json");
        });

        $("#supplierSelectFormBtn").click(function () {

            //$.post("/ofc/supplierSelect",$("#supplierSelConditionForm").serialize(),function (data) {
            CommonClient.post(sys.rootPath + "/ofc/supplierSelect", $("#supplierSelConditionForm").serialize(), function(data) {
                data=eval(data);
                var supplierList = "";
                $.each(data,function (index,CscSupplierInfoDto) {
                    supplierList =supplierList + "<tr role='row' class='odd'>";
                    supplierList =supplierList + "<td class='center'> "+"<label class='pos-rel'>"+"<input name='suppliersele' type='radio' class='ace'>"+"<span class='lbl'></span>"+"</label>"+"</td>";
                    supplierList =supplierList + "<td>"+(index+1)+"</td>";
                    supplierList =supplierList + "<td>"+CscSupplierInfoDto.supplierName+"</td>";
                    supplierList =supplierList + "<td>"+CscSupplierInfoDto.contactName+"</td>";
                    supplierList =supplierList + "<td>"+CscSupplierInfoDto.contactPhone+"</td>";
                    supplierList =supplierList + "<td>"+CscSupplierInfoDto.fax+"</td>";
                    supplierList =supplierList + "<td>"+CscSupplierInfoDto.email+"</td>";
                    supplierList =supplierList + "<td>"+CscSupplierInfoDto.postCode+"</td>";
                    supplierList =supplierList + "<td>"+CscSupplierInfoDto.address+"</td>";
                    $("#supplierSelectListTbody").html(supplierList);
                });
            },"json");
        });

        $("#goodsEnter").click(function () {
            var goodsInfoListDiv = "";
            $("#goodsSelectListTbody").find("tr").each(function(index){
                var tdArr = $(this).children();
                if(tdArr.eq(0).find("input").prop("checked")){
                    var goodsCode = tdArr.eq(2).text();//货品编码
                    var goodsName = tdArr.eq(3).text();//货品名称
                    var specification = tdArr.eq(4).text();//    货品规格
                    var unit = tdArr.eq(5).text();//    单位
                    goodsInfoListDiv =goodsInfoListDiv + "<tr role='row' class='odd' align='center'>";
                    goodsInfoListDiv =goodsInfoListDiv + "<td><button type='button' onclick='deleteGood(this)' class='btn btn-minier btn-danger'>删除</button></td>";
                    goodsInfoListDiv =goodsInfoListDiv + "<td><input id='deleteOrNot' type='checkbox'/></td>";
                    goodsInfoListDiv =goodsInfoListDiv + "<td>"+goodsCode+"</td>";
                    goodsInfoListDiv =goodsInfoListDiv + "<td>"+goodsName+"</td>";
                    goodsInfoListDiv =goodsInfoListDiv + "<td>"+specification+"</td>";
                    goodsInfoListDiv =goodsInfoListDiv + "<td>"+unit+"</td>";
                    goodsInfoListDiv =goodsInfoListDiv + "<td><input name='fax' type='search' value='' class='form-control input-sm' placeholder='' aria-controls='dynamic-table' ></td>";
                    goodsInfoListDiv =goodsInfoListDiv + "<td><input name='fax' type='search' value='' class='form-control input-sm' placeholder='' aria-controls='dynamic-table' ></td>";
                    goodsInfoListDiv =goodsInfoListDiv + "<td><input name='fax' type='search' value='' class='form-control input-sm' placeholder='' aria-controls='dynamic-table' onClick=\"WdatePicker({isShowClear:true,readOnly:true,dateFmt:\"yyyy-MM-dd HH:mm:ss\"})\"></td>";
                    goodsInfoListDiv =goodsInfoListDiv + "<td><input name='fax' type='search' value='' class='form-control input-sm' placeholder='' aria-controls='dynamic-table' onClick=\"WdatePicker({isShowClear:true,readOnly:true,dateFmt:\"yyyy-MM-dd HH:mm:ss\"})\"></td>";
                    goodsInfoListDiv =goodsInfoListDiv + "</tr>";
                    $("#goodsInfoListDiv").html(goodsInfoListDiv);
                }
            });
            if(goodsInfoListDiv==""){
                alert("请至少选择一行");
            }
        });

        $("#contactinEnter").click(function () {
            var consignorin = "";
            $("#contactSelectListTbody2").find("tr").each(function(index){
                var tdArr = $(this).children();
                if(tdArr.eq(0).find("input").prop("checked")){
                    var consignorName = tdArr.eq(2).text();//名称
                    var contacts = tdArr.eq(3).text();//联系人
                    var contactsNumber = tdArr.eq(4).text();//    联系电话
                    var fax = tdArr.eq(5).text();//    传真
                    var email = tdArr.eq(6).text();//    email
                    var code = tdArr.eq(7).text();//    邮编
                    var address = tdArr.eq(8).text();//    地址
                    $("#testtest").text(consignorName);
                    consignorin =consignorin + "名称:";
                    consignorin =consignorin + "<input name='consignorName' type='search' value='"+consignorName+"' class='form-control input-sm' placeholder='' aria-controls='dynamic-table' >";
                    consignorin =consignorin + "联系人:";
                    consignorin =consignorin + "<input name='contacts' type='search' value='"+contacts+"' class='form-control input-sm' placeholder='' aria-controls='dynamic-table' >";
                    consignorin =consignorin + "联系电话:";
                    consignorin =consignorin + "<input name='contactsNumber' type='search' value='"+contactsNumber+"' class='form-control input-sm' placeholder='' aria-controls='dynamic-table' >";
                    consignorin =consignorin + "传真:";
                    consignorin =consignorin + "<input name='fax' type='search' value='"+fax+"' class='form-control input-sm' placeholder='' aria-controls='dynamic-table' >";
                    consignorin =consignorin + "Email:";
                    consignorin =consignorin + "<input name='email' type='search' value='"+email+"' class='form-control input-sm' placeholder='' aria-controls='dynamic-table' >";
                    consignorin =consignorin + "邮编:";
                    consignorin =consignorin + "<input name='code' type='search' value='"+code+"' class='form-control input-sm' placeholder='' aria-controls='dynamic-table' >";
                    consignorin =consignorin + "地址:";
                    consignorin =consignorin + "<select><option value=''>--省--</option></select>";
                    consignorin =consignorin + "<select><option value=''>--市--</option></select>";
                    consignorin =consignorin + "<select><option value=''>--区/县--</option></select>";
                    consignorin =consignorin + "<select><option value=''>--乡镇/街道--</option></select>";
                    consignorin =consignorin + "<input name='address' type='search' value='"+address+"' class='form-control input-sm' placeholder='' aria-controls='dynamic-table'>";
                    $("#consignorin").html(consignorin);
                }
            });
            if(consignorin==""){
                alert("请至少选择一行");
            }
        });

        $("#contactoutEnter").click(function () {
            var consignorout = "";
            $("#contactSelectListTbody1").find("tr").each(function(index){
                var tdArr = $(this).children();
                if(tdArr.eq(0).find("input").prop("checked")){
                    var consignorName = tdArr.eq(2).text();//名称
                    var contacts = tdArr.eq(3).text();//联系人
                    var contactsNumber = tdArr.eq(4).text();//    联系电话
                    var fax = tdArr.eq(5).text();//    传真
                    var email = tdArr.eq(6).text();//    email
                    var code = tdArr.eq(7).text();//    邮编
                    var address = tdArr.eq(8).text();//    地址
                    consignorout =consignorout + "名称:";
                    consignorout =consignorout + "<input name='consignorName' type='search' value='"+consignorName+"' class='form-control input-sm' placeholder='' aria-controls='dynamic-table' >";
                    consignorout =consignorout + "联系人:";
                    consignorout =consignorout + "<input name='contacts' type='search' value='"+contacts+"' class='form-control input-sm' placeholder='' aria-controls='dynamic-table' >";
                    consignorout =consignorout + "联系电话:";
                    consignorout =consignorout + "<input name='contactsNumber' type='search' value='"+contactsNumber+"' class='form-control input-sm' placeholder='' aria-controls='dynamic-table' >";
                    consignorout =consignorout + "传真:";
                    consignorout =consignorout + "<input name='fax' type='search' value='"+fax+"' class='form-control input-sm' placeholder='' aria-controls='dynamic-table' >";
                    consignorout =consignorout + "Email:";
                    consignorout =consignorout + "<input name='email' type='search' value='"+email+"' class='form-control input-sm' placeholder='' aria-controls='dynamic-table' >";
                    consignorout =consignorout + "邮编:";
                    consignorout =consignorout + "<input name='code' type='search' value='"+code+"' class='form-control input-sm' placeholder='' aria-controls='dynamic-table' >";
                    consignorout =consignorout + "地址:";
                    consignorout =consignorout + "<select><option value=''>--省--</option></select>";
                    consignorout =consignorout + "<select><option value=''>--市--</option></select>";
                    consignorout =consignorout + "<select><option value=''>--区/县--</option></select>";
                    consignorout =consignorout + "<select><option value=''>--乡镇/街道--</option></select>";
                    consignorout =consignorout + "<input name='address' type='search' value='"+address+"' class='form-control input-sm' placeholder='' aria-controls='dynamic-table'>";
                    $("#consignorout").html(consignorout);
                }
            });
            if(consignorout==""){
                alert("请至少选择一行");
            }
        });

        $("#supplierEnter").click(function () {
            var support = "";
            $("#supplierSelectListTbody").find("tr").each(function(index){
                var tdArr = $(this).children();
                if(tdArr.eq(0).find("input").prop("checked")){
                    var consignorName = tdArr.eq(2).text();//名称
                    var contacts = tdArr.eq(3).text();//联系人
                    var contactsNumber = tdArr.eq(4).text();//    联系电话
                    var fax = tdArr.eq(5).text();//    传真
                    var email = tdArr.eq(6).text();//    email
                    var code = tdArr.eq(7).text();//    邮编
                    var address = tdArr.eq(8).text();//    地址
                    support =support + "名称:";
                    support =support + "<input name='consignorName' type='search' value='"+consignorName+"' class='form-control input-sm' placeholder='' aria-controls='dynamic-table' >";
                    support =support + "联系人:";
                    support =support + "<input name='contacts' type='search' value='"+contacts+"' class='form-control input-sm' placeholder='' aria-controls='dynamic-table' >";
                    support =support + "联系电话:";
                    support =support + "<input name='contactsNumber' type='search' value='"+contactsNumber+"' class='form-control input-sm' placeholder='' aria-controls='dynamic-table' >";
                    support =support + "传真:";
                    support =support + "<input name='fax' type='search' value='"+fax+"' class='form-control input-sm' placeholder='' aria-controls='dynamic-table' >";
                    support =support + "Email:";
                    support =support + "<input name='email' type='search' value='"+email+"' class='form-control input-sm' placeholder='' aria-controls='dynamic-table' >";
                    support =support + "邮编:";
                    support =support + "<input name='code' type='search' value='"+code+"' class='form-control input-sm' placeholder='' aria-controls='dynamic-table' >";
                    support =support + "地址:";
                    support =support + "<select><option value=''>--省--</option></select>";
                    support =support + "<select><option value=''>--市--</option></select>";
                    support =support + "<select><option value=''>--区/县--</option></select>";
                    support =support + "<select><option value=''>--乡镇/街道--</option></select>";
                    support =support + "<input name='address' type='search' value='"+address+"' class='form-control input-sm' placeholder='' aria-controls='dynamic-table'>";
                    $("#support").html(support);
                }
            });
            if(support==""){
                alert("请至少选择一行");
            }
        });

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


        $("#orderTypeSel").change(function () {
            $("#businessType").addClass("chosen-select form-control");
            if($(this).children('option:selected').val() == '61'){
                $("#provideTransportDiv").show();
                $("#businessTypeDiv").show();
                $('.storeLi').show();
                $('.transLi').hide();
                //$('#myTab4').children('li').first().addClass("active");
                $('#myTab4').find('li').each(function () {
                    console.log($(this).index());
                    if($(this).index()=='0'){

                        $(this).addClass("active");
                    }
                    if($(this).index()=='1'){
                        $(this).removeClass("active");
                    }
                    if($(this).index()=='2'){
                        $(this).removeClass("active");
                    }
                })
                $("#home4").addClass("active");
                $("#profile4").removeClass("active");
                $("#dropdown14").removeClass("active");

            }
            if($(this).children('option:selected').val() == '60'){
                $('.transLi').show();
                $('.storeLi').hide();
                $("#provideTransportDiv").hide();
                $("#businessTypeDiv").hide();
                $('#myTab4').find('li').each(function () {
                    console.log($(this).index());
                    if($(this).index()=='0'){
                        $(this).addClass("active");
                    }
                    if($(this).index()=='1'){
                        $(this).removeClass("active");
                    }
                    if($(this).index()=='2'){
                        $(this).removeClass("active");
                    }
                })
                $("#home4").addClass("active");
                $("#profile4").removeClass("active");
                $("#dropdown14").removeClass("active");


            }
        });
        $("#provideTransport").change(function () {
            if($(this).prop("checked")){
                $('.transLi').show();
            }else{
                $('.transLi').hide();
            }
        });

        function deleteGood(obj) {
            $(obj).parent().parent().remove();
        }

        function deleteGood(obj) {
            $(obj).parent().parent().remove();
        }

    })
</script>

</body>
