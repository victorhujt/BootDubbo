<head>
    <title>订单编辑</title>
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
<!-- #section:basics/navbar.layout -->
<!--goodsListDiv-->
<div class="modal-content" id="goodsListDiv" style="display: none;">
    <div class="modal-header"><span id="goodsListDivNoneTop" style="cursor:pointer"><button type="button" id="" style="cursor:pointer" class="bootbox-close-button close" data-dismiss="modal" aria-hidden="true">×</button></span>
        <h4 class="modal-title">货品列表</h4></div>
    <div class="modal-body">
        <div class="bootbox-body">
            <div class="widget-main">
                <form id="goodsSelConditionForm"  class="form-horizontal" role="form">
                    <div class="form-group">
                        <label class="control-label col-sm-1 no-padding-right" for="name">货品编码</label>
                        <div class="col-sm-3">
                            <div class="clearfix">
                            <#--<input  id = "goodsCodeCondition" name="goodsCode" type="text" style="color: black" class="form-control input-sm" placeholder="" aria-controls="dynamic-table">-->
                                <input id="goodsCodeCondition" name="goodsCode" type="text">
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-1 no-padding-right" for="name">货品名称</label>
                        <div class="col-sm-3">
                            <div class="clearfix">
                                <input id="goodsNameCondition" name="goodsName" type="text">
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
            </div>
            <form class="bootbox-form">
                <table id="dynamic-table" class="table table-striped table-bordered table-hover dataTable no-footer" role="grid" aria-describedby="dynamic-table_info">
                    <thead>
                    <tr role="row"><th class="center sorting_disabled" rowspan="1" colspan="1" aria-label="">
                        <label class="pos-rel">
                            <input type="checkbox" class="ace">
                            <span class="lbl"></span>
                        </label>
                    </th>
                        <#--<th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Domain: activate to sort column ascending">序号</th>-->
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
            <div class="widget-main">
                <form id="consignorSelConditionForm"  class="form-horizontal" role="form">
                    <div class="form-group">
                        <label class="control-label col-sm-1 no-padding-right" for="name">名称</label>
                        <div class="col-sm-3">
                            <div class="clearfix">
                                <input id="consignorName2" name="contactCompanyName" type="text">
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-1 no-padding-right" for="name">联系人</label>
                        <div class="col-sm-3">
                            <div class="clearfix">
                                <input id="consignorPerson2" name="contactName" type="text">
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-1 no-padding-right" for="name">联系电话</label>
                        <div class="col-sm-3">
                            <div class="clearfix">
                                <input id="consignorPhoneNumber2" name="phone" type="text">
                            <#--<input id="purpose2" name="purpose" type="hidden" value="2">-->
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
            </div>
        <#--   </div>
       </div>-->
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
            <div class="widget-main">
                <form id="consigneeSelConditionForm"  class="form-horizontal" role="form">
                    <div class="form-group">
                        <label class="control-label col-sm-1 no-padding-right" for="name">名称</label>
                        <div class="col-sm-3">
                            <div class="clearfix">
                                <input id="consignorName1" name="contactCompanyName" type="text">
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-1 no-padding-right" for="name">联系人</label>
                        <div class="col-sm-3">
                            <div class="clearfix">
                                <input id="consignorPerson1" name="contactName" type="text">
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-1 no-padding-right" for="name">联系电话</label>
                        <div class="col-sm-3">
                            <div class="clearfix">
                                <input id="consignorPhoneNumber1" name="phone" type="text">
                                <input id="purpose1" name="purpose" type="hidden" value="1">
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
</div>

<!--supportListDiv-->
<div class="modal-content" id="supportListDiv" style="display: none;">
    <div class="modal-header"><span id="supportListDivNoneTop" style="cursor:pointer"><button type="button" id="" style="cursor:pointer" class="bootbox-close-button close" data-dismiss="modal" aria-hidden="true">×</button></span>
        <h4 class="modal-title">供应商联系人</h4></div>


    <#--
    <div class="modal-body">
        <div class="bootbox-body">
            <div class="widget-main">
                <form id="consigneeSelConditionForm"  class="form-horizontal" role="form">
    -->
    <div class="modal-body">
        <div class="bootbox-body">
            <div class="widget-main">
            <form id="supplierSelConditionForm" class="form-horizontal" role="form">
                <div class="form-group">
                    <label class="control-label col-sm-1 no-padding-right" for="name">名称</label>
                    <div class="col-sm-3">
                        <div class="clearfix">
                            <input id="supplierName" name="supplierName" type="text">
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-sm-1 no-padding-right" for="name">联系人</label>
                    <div class="col-sm-3">
                        <div class="clearfix">
                            <input id="contactName" name="contactName" type="text">
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-sm-1 no-padding-right" for="name">联系电话</label>
                    <div class="col-sm-3">
                        <div class="clearfix">
                            <input id="contactPhone" name="contactPhone" type="text">
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-sm-1 no-padding-right" for="name"></label>
                    <div class="col-sm-3">
                        <div class="clearfix">
                            <button type="button" class="btn btn-info" id="supplierSelectFormBtn" >筛选</button>
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
    </div>
    <div class="modal-footer"><span id="supportListDivNoneBottom" style="cursor:pointer"><button  data-bb-handler="cancel" type="button" class="btn btn-default">Cancel</button></span><button id="supplierEnter" data-bb-handler="confirm" type="button" class="btn btn-primary">OK</button></div>
</div>
<!-- /section:basics/navbar.layout -->
<div class="main-container ace-save-state" id="main-container">
    <div class="main-content">
        <div class="main-content-inner">
            <div class="page-content">
                <div class="page-header">
                    <p>
                        基本信息
                    </p>
                </div>
                <div class="row">
                    <div class="col-xs-12">
                        <div class="widget-box"  style="border: none">
                            <div class="widget-body">
                                <div class="widget-main">
                                    <form name="orderInfoTable" id="orderInfoTable"  class="form-horizontal" role="form" >

                                        <div class="form-group">
                                            <label class="control-label col-sm-1 no-padding-right" for="name">订单日期</label>
                                            <div class="col-sm-6">
                                                <div class="clearfix">
                                                <input type="hidden" id="orderCode" name="orderCode" <#if orderInfo.orderCode?? >value="${orderInfo.orderCode}"</#if>">
                                                    <input id="orderTime" name="orderTime" <#if orderInfo.orderTime?? >value="${((orderInfo.orderTime)?string('yyyy-MM-dd HH:mm:ss'))!}"</#if> type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" onClick="WdatePicker({isShowClear:true,readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})">
                                                </div>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="control-label col-sm-1 no-padding-right" for="name">订单日期</label>
                                            <div class="col-sm-6">
                                                <div class="clearfix">
                                                    <input id="orderTime" name="orderTime" <#if orderInfo.orderTime?? >value="${((orderInfo.orderTime)?string('yyyy-MM-dd HH:mm:ss'))!}"</#if> type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" onClick="WdatePicker({isShowClear:true,readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})">
                                                </div>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="control-label col-sm-1 no-padding-right" for="name">客户订单编号</label>
                                            <div class="col-sm-6">
                                                <div class="clearfix">
                                                    <input id="custOrderCode" name="custOrderCode" type="text" <#if orderInfo.custOrderCode?? >value="${orderInfo.custOrderCode}"</#if>>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="control-label col-sm-1 no-padding-right" for="name">订单类型</label>
                                            <div class="col-sm-6">
                                                <div class="clearfix">
                                                    <select id="orderType" name="orderType" value="${(orderInfo.orderType)!"60"}">
                                                        <option value="60" <#if orderInfo.orderType?? ><#if ((orderInfo.orderType)! == '60')>selected="selected"</#if></#if>>运输订单</option>
                                                        <option value="61" <#if orderInfo.orderType?? ><#if ((orderInfo.orderType)! == '61')>selected="selected"</#if></#if>>仓配订单</option>
                                                    </select>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="form-group" id="businessTypeDiv" style="display: none">
                                            <label class="control-label col-sm-1 no-padding-right" for="name">业务类型</label>
                                            <div class="col-sm-6">
                                                <div class="clearfix">
                                                    <select id="businessType" name="businessType">

                                                        <option value="610" <#if orderInfo.businessType?? ><#if ((orderInfo.businessType)! == '610')>selected="selected"</#if></#if>>销售出库</option>
                                                        <option value="611" <#if orderInfo.businessType?? ><#if ((orderInfo.businessType)! == '611')>selected="selected"</#if></#if>>调拨出库</option>
                                                        <option value="612" <#if orderInfo.businessType?? ><#if ((orderInfo.businessType)! == '612')>selected="selected"</#if></#if>>报损出库</option>
                                                        <option value="613" <#if orderInfo.businessType?? ><#if ((orderInfo.businessType)! == '613')>selected="selected"</#if></#if>>其他出库</option>
                                                        <option value="----------">----------</option>
                                                        <option value="620" <#if orderInfo.businessType?? ><#if ((orderInfo.businessType)! == '620')>selected="selected"</#if></#if>>采购入库</option>
                                                        <option value="621" <#if orderInfo.businessType?? ><#if ((orderInfo.businessType)! == '621')>selected="selected"</#if></#if>>调拨入库</option>
                                                        <option value="622" <#if orderInfo.businessType?? ><#if ((orderInfo.businessType)! == '622')>selected="selected"</#if></#if>>退货入库</option>
                                                        <option value="623" <#if orderInfo.businessType?? ><#if ((orderInfo.businessType)! == '623')>selected="selected"</#if></#if>>加工入库</option>
                                                    </select>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="form-group" id="provideTransportDiv" style="display: none">
                                            <label class="control-label col-sm-1 no-padding-right" for="name">是否需要运输</label>
                                            <div class="col-sm-6">
                                                <div class="clearfix">
                                                    <input id="provideTransport" type="checkbox" name = "" <#if orderInfo.provideTransport?? ><#if ((orderInfo.provideTransport) == 1)> checked="checked"</#if></#if>/>
                                                    <input id="provideTransport1" type="hidden" name="provideTransport" value="${(orderInfo.provideTransport)!""}"/>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="control-label col-sm-1 no-padding-right" for="name">店铺</label>
                                            <div class="col-sm-6">
                                                <div class="clearfix">
                                                    <select id="storeCode" name="storeCode" value="${(orderInfo.storeName)!""}">
                                                        <option value="线下销售" <#if orderInfo.storeName?? ><#if ((orderInfo.storeName)== '线下销售')>selected="selected"</#if></#if>>线下销售</option>
                                                        <option value="众品天猫生鲜旗舰店" <#if orderInfo.storeName?? ><#if ((orderInfo.storeName)== '众品天猫生鲜旗舰店')>selected="selected"</#if></#if>>众品天猫生鲜旗舰店</option>
                                                        <option value="众品京东旗舰店" <#if orderInfo.storeName?? ><#if ((orderInfo.storeName)== '众品京东旗舰店')>selected="selected"</#if></#if>>众品京东旗舰店</option>
                                                    </select>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="form-group" >
                                            <label class="control-label col-sm-1 no-padding-right" for="name">备注</label>
                                            <div class="col-sm-6">
                                                <div class="clearfix">
                                                    <input id="notes"  name="notes" value="${(orderInfo.notes)!""}"  type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table">
                                                </div>
                                            </div>
                                        </div>
                                    </form>
                                </div>
                            </div>

                            <div class="widget-body">
                                <div class="widget-main">
                                    <form name="orderInfoTable" id="orderInfoTable"  class="form-horizontal" role="form" >
                                        <div class="col-sm-6">
                                            <!-- #section:elements.tab.option -->
                                            <div class="tabbable" style="width: 1000px;" >
                                                <ul class="nav nav-tabs" id="myTab4">
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

                                                        <table id="orderGoodsListTable" class="table table-striped table-bordered table-hover dataTable no-footer" role="grid" aria-describedby="dynamic-table_info">
                                                            <thead>
                                                            <tr role="row"><th class="center sorting_disabled" rowspan="1" colspan="1" aria-label="">
                                                                操作
                                                            </th>
                                                                <#--<th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Domain: activate to sort column ascending">选择</th>-->
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
                                                            <tr role="row" class="odd" align="center">
                                                                <td class="center">
                                                                    <button type="button" id=""    class="btn btn-minier btn-danger" onclick="deleteGoods('${(goodsDetails.orderCode)!"null"}','${goodsDetails.goodsCode!"null"}')">删除</button>
                                                                </td>
                                                                <td>${(goodsDetails.goodsCode)!""}</td>
                                                                <td>${(goodsDetails.goodsName)!""}</td>
                                                                <td class="hidden-480">${(goodsDetails.goodsSpec)!""}</td>
                                                                <td>${(goodsDetails.unit)!""}</td>
                                                                <td class="hidden-480"><input name='' type='search' value='${(goodsDetails.quantity)!""}' class='form-control input-sm' placeholder='' aria-controls='dynamic-table' ></td>
                                                                <td class="hidden-480"><input name='' type='search' value='${(goodsDetails.productionBatch)!""}' class='form-control input-sm' placeholder='' aria-controls='dynamic-table' ></td>
                                                                <td class="hidden-480"><input name='' type='search' value='${(goodsDetails.productionTime)!""}' class='form-control input-sm' placeholder='' aria-controls='dynamic-table'></td>
                                                                <td class="hidden-480"><input name='' type='search' value='${(goodsDetails.invalidTime)!""}' class='form-control input-sm' placeholder='' aria-controls='dynamic-table' ></td>
                                                            </tr>
                                                            </#list>
                                                            </tbody>
                                                            <tbody id="goodsInfoListDiv"></tbody>
                                                        </table>
                                                    <#--<div class="row">
                                                        <div class="col-xs-6">
                                                            <div class="dataTables_info" id="dynamic-table_info" role="status" aria-live="polite">

                                                            </div>
                                                        </div>
                                                        <div class="col-xs-6">
                                                        </div></div>-->

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
                                                                            <input id="quantity" name="quantity" <#if orderInfo.quantity?? >value="${orderInfo.quantity}"</#if> type="text" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" >
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="form-group" >
                                                                    <label class="control-label col-sm-1 no-padding-right" for="name">重量</label>
                                                                    <div class="col-sm-6">
                                                                        <div class="clearfix">
                                                                            <input id="weight" name="weight" <#if orderInfo.weight?? >value="${orderInfo.weight}"</#if> type="text" class="form-control input-sm" placeholder="" aria-controls="dynamic-table">
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="form-group" >
                                                                    <label class="control-label col-sm-1 no-padding-right" for="name">体积</label>
                                                                    <div class="col-sm-6">
                                                                        <div class="clearfix">
                                                                            <input id="cubage" name="cubage" <#if orderInfo.cubage?? >value="${orderInfo.cubage}"</#if> type="text" class="form-control input-sm" placeholder="" aria-controls="dynamic-table">(长*宽*高,单位:cm)
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="form-group" >
                                                                    <label class="control-label col-sm-1 no-padding-right" for="name">合计标准箱</label>
                                                                    <div class="col-sm-6">
                                                                        <div class="clearfix">
                                                                            <input id="totalStandardBox" name="totalStandardBox" <#if orderInfo.totalStandardBox?? >value="${orderInfo.totalStandardBox}"</#if> type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table">
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="form-group" >
                                                                    <label class="control-label col-sm-1 no-padding-right" for="name">出发地</label>
                                                                    <div class="col-sm-6">
                                                                        <div class="clearfix">
                                                                            <input id="departurePlace" name="departurePlace" <#if orderInfo.departurePlace?? >value="${orderInfo.departurePlace}"</#if> type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" readonly="readonly">
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="form-group" >
                                                                    <label class="control-label col-sm-1 no-padding-right" for="name">目的地</label>
                                                                    <div class="col-sm-6">
                                                                        <div class="clearfix">
                                                                            <input id="destination" name="destination" <#if orderInfo.destination?? >value="${orderInfo.destination}"</#if> type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" readonly="readonly">
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="form-group" >
                                                                    <label class="control-label col-sm-1 no-padding-right" for="name">取货时间</label>
                                                                    <div class="col-sm-6">
                                                                        <div class="clearfix">
                                                                            <input id="pickupTime" name="pickupTime" <#if orderInfo.pickupTime?? >value="${((orderInfo.pickupTime)?string('yyyy-MM-dd HH:mm:ss'))!}"</#if> type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" onClick="WdatePicker({isShowClear:true,readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})">
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="form-group" >
                                                                    <label class="control-label col-sm-1 no-padding-right" for="name">期望送达时间</label>
                                                                    <div class="col-sm-6">
                                                                        <div class="clearfix">
                                                                            <input id="expectedArrivedTime" name="expectedArrivedTime" <#if orderInfo.expectedArrivedTime?? >value="${((orderInfo.expectedArrivedTime)?string('yyyy-MM-dd HH:mm:ss'))!}"</#if> type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" onClick="WdatePicker({isShowClear:true,readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})">
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="form-group" >
                                                                    <label class="control-label col-sm-1 no-padding-right" for="name">是否加急</label>
                                                                    <div class="col-sm-6">
                                                                        <div class="clearfix">
                                                                            <input  id="urgent" type="checkbox" name="" <#if orderInfo.urgent?? ><#if ((orderInfo.urgent) == 1)> checked="checked"</#if></#if>/>
                                                                            <input id="urgent1" type="hidden" name="urgent" value="${(orderInfo.urgent)!""}"/>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="form-group" >
                                                                    <label class="control-label col-sm-1 no-padding-right" for="name">运输要求</label>
                                                                    <div class="col-sm-6">
                                                                        <div class="clearfix">
                                                                            <input id="transRequire" name="transRequire" <#if orderInfo.transRequire?? >value="${orderInfo.transRequire}"</#if> type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table">
                                                                        </div>
                                                                    </div>
                                                                </div>

                                                            </div>
                                                        </div>
                                                        <div class="page-header">
                                                            <h4>发货方信息</h4>
                                                        </div>
                                                        <span style="cursor:pointer" id="consignorListDivBlock"><button type="button" class="btn btn-info" id="bootbox-confirm">选择</button></span>
                                                        <div id="consignorin" class="">

                                                            <div class="form-group" >
                                                                <label class="control-label col-sm-1 no-padding-right" for="name">名称</label>
                                                                <div class="col-sm-6">
                                                                    <div class="clearfix">
                                                                        <input id="consignorCode" name="consignorCode" <#if (consignorMessage.contactCompanyId)!?? >value="${(consignorMessage.contactCompanyId)!}"</#if> type="hidden">
                                                                        <input id="consignorName"  name="consignorName" <#if (consignorMessage.contactCompanyName)!?? >value="${(consignorMessage.contactCompanyName)!}"</#if> type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" >

                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="form-group" >
                                                                <label class="control-label col-sm-1 no-padding-right" for="name">联系人</label>
                                                                <div class="col-sm-6">
                                                                    <div class="clearfix">
                                                                        <input id="consignorContactName" name="" <#if (consignorMessage.contactName)!?? >value="${(consignorMessage.contactName)!}"</#if> type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" >

                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="form-group" >
                                                                <label class="control-label col-sm-1 no-padding-right" for="name">联系电话</label>
                                                                <div class="col-sm-6">
                                                                    <div class="clearfix">
                                                                        <input id="consignorPhone" name="" <#if (consignorMessage.phone)!?? >value="${(consignorMessage.phone)!}"</#if> type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" >

                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="form-group" >
                                                                <label class="control-label col-sm-1 no-padding-right" for="name">传真</label>
                                                                <div class="col-sm-6">
                                                                    <div class="clearfix">
                                                                        <input id="consignorFax" name="" <#if (consignorMessage.fax)!?? >value="${(consignorMessage.fax)!}"</#if> type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" >

                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="form-group" >
                                                                <label class="control-label col-sm-1 no-padding-right" for="name">Email</label>
                                                                <div class="col-sm-6">
                                                                    <div class="clearfix">
                                                                        <input id="consignorEmail" name="" <#if (consignorMessage.email)!?? >value="${(consignorMessage.email)!}"</#if> type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" >
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="form-group" >
                                                                <label class="control-label col-sm-1 no-padding-right" for="name">邮编</label>
                                                                <div class="col-sm-6">
                                                                    <div class="clearfix">
                                                                        <input id="consignorPostCode" name="" <#if (consignorMessage.postCode)!?? >value="${(consignorMessage.postCode)!}"</#if> type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" >
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
                                                                        <input id="consignorAddress" name="" <#if (consignorMessage.address)!?? >value="${(consignorMessage.address)!}"</#if> type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" >
                                                                    </div>
                                                                </div>
                                                            </div>

                                                        </div>

                                                        <div class="page-header">
                                                            <h4>收货方信息</h4>
                                                        </div>
                                                        <span style="cursor:pointer" id="consigneeListDivBlock"><button type="button" class="btn btn-info" id="bootbox-confirm">选择</button></span>
                                                        <div id="consignorout" class="">
                                                            <div class="form-group" >
                                                                <label class="control-label col-sm-1 no-padding-right" for="name">名称</label>
                                                                <div class="col-sm-6">
                                                                    <div class="clearfix">
                                                                        <input id="cosigneeCode" name="cosigneeCode" type="hidden" <#if (consigneeMessage.contactCompanyId)!?? >value="${(consigneeMessage.contactCompanyId)!}"</#if> >
                                                                        <input id="consigneeName" name="contactCompanyName" <#if (consigneeMessage.contactCompanyName)!?? >value="${(consigneeMessage.contactCompanyName)!}"</#if> type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" >

                                                                    </div>
                                                                </div>
                                                            </div>

                                                            <div class="form-group" >
                                                                <label class="control-label col-sm-1 no-padding-right" for="name">联系人</label>
                                                                <div class="col-sm-6">
                                                                    <div class="clearfix">
                                                                        <input id="consigneeContactName" name="" <#if (consigneeMessage.contactName)!?? >value="${(consigneeMessage.contactName)!}"</#if> type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" >
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="form-group" >
                                                                <label class="control-label col-sm-1 no-padding-right" for="name">联系电话</label>
                                                                <div class="col-sm-6">
                                                                    <div class="clearfix">
                                                                        <input id="consigneePhone" name="" <#if (consigneeMessage.phone)!?? >value="${(consigneeMessage.phone)!}"</#if> type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" >
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="form-group" >
                                                                <label class="control-label col-sm-1 no-padding-right" for="name">传真</label>
                                                                <div class="col-sm-6">
                                                                    <div class="clearfix">
                                                                        <input id="consigneeFax" name="" <#if (consigneeMessage.fax)!?? >value="${(consigneeMessage.fax)!}"</#if> type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" >
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="form-group" >
                                                                <label class="control-label col-sm-1 no-padding-right" for="name">Email</label>
                                                                <div class="col-sm-6">
                                                                    <div class="clearfix">
                                                                        <input id="consigneeEmail" name="" <#if (consigneeMessage.email)!?? >value="${(consigneeMessage.email)!}"</#if> type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" >
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="form-group" >
                                                                <label class="control-label col-sm-1 no-padding-right" for="name">邮编</label>
                                                                <div class="col-sm-6">
                                                                    <div class="clearfix">
                                                                        <input id="consigneePostCode" name="" <#if (consigneeMessage.postCode)!?? >value="${(consigneeMessage.postCode)!}"</#if> type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" >
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
                                                                        <input id="consigneeAddress" name="" <#if (consigneeMessage.address)!?? >value="${(consigneeMessage.address)!}"</#if> type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" >
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
                                                                            <select id="wareHouseName" name="wareHouseName"  <#-- value="${(orderInfo.wareHouseName)!""}"-->>
                                                                            <#list rmcWarehouseByCustCode! as warehouse>
                                                                                <option value="${(warehouse.id)!}">${(warehouse.warehouseName)!}</option>
                                                                            </#list>
                                                                            </select>

                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="form-group" >
                                                                    <label class="control-label col-sm-1 no-padding-right" for="name">入库预计到达时间</label>
                                                                    <div class="col-sm-6">
                                                                        <div class="clearfix">
                                                                            <input id="arriveTime" name="arriveTime" <#if orderInfo.arriveTime?? >value="${((orderInfo.arriveTime)?string('yyyy-MM-dd HH:mm:ss'))!}"</#if>  type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" onClick="WdatePicker({isShowClear:true,readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})">

                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="form-group" >
                                                                    <label class="control-label col-sm-1 no-padding-right" for="name">车牌号</label>
                                                                    <div class="col-sm-6">
                                                                        <div class="clearfix">
                                                                            <input id="plateNumber" name="plateNumber" <#if orderInfo.plateNumber?? >value="${orderInfo.plateNumber}"</#if> type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table">

                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="form-group" >
                                                                    <label class="control-label col-sm-1 no-padding-right" for="name">司机姓名</label>
                                                                    <div class="col-sm-6">
                                                                        <div class="clearfix">
                                                                            <input id="driverName" name="driverName" <#if orderInfo.driverName?? >value="${orderInfo.driverName}"</#if> type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table"><br/>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="form-group" >
                                                                    <label class="control-label col-sm-1 no-padding-right" for="name">联系电话</label>
                                                                    <div class="col-sm-6">
                                                                        <div class="clearfix">
                                                                            <input id="contactNumber" name="contactNumber" <#if orderInfo.contactNumber?? >value="${orderInfo.contactNumber}"</#if> type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table">
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>

                                                        <div id="supportMessageShowDiv" class="" style="display: none">
                                                            <div class="page-header">
                                                                <h4>供应商信息</h4>
                                                            </div>
                                                            <span style="cursor:pointer" id="supportListDivBlock"><button type="button" class="btn btn-info" id="bootbox-confirm">选择</button></span>

                                                            <div class="form-group" >
                                                                <label class="control-label col-sm-1 no-padding-right" for="name">名称</label>
                                                                <div class="col-sm-6">
                                                                    <div class="clearfix">
                                                                        <input id="supportCode" name="supportCode" type="hidden" <#if (supportMessage.supplierCode)!?? >value="${(supportMessage.supplierCode)!}"</#if>>
                                                                        <input id="supportName" name="supportName" <#if (supportMessage.supplierName)!?? >value="${(supportMessage.supplierName)!}"</#if> type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" >
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="form-group" >
                                                                <label class="control-label col-sm-1 no-padding-right" for="name">联系人</label>
                                                                <div class="col-sm-6">
                                                                    <div class="clearfix">
                                                                        <input id="supportContactName" name="" <#if (supportMessage.contactName)!?? >value="${(supportMessage.contactName)!}"</#if> type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" >

                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="form-group" >
                                                                <label class="control-label col-sm-1 no-padding-right" for="name">联系电话</label>
                                                                <div class="col-sm-6">
                                                                    <div class="clearfix">
                                                                        <input id="supportPhone" name="" <#if (supportMessage.contactPhone)!?? >value="${(supportMessage.contactPhone)!}"</#if> type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" >

                                                                    </div>
                                                                </div>
                                                            </div>
                                                        <div class="form-group" >
                                                            <label class="control-label col-sm-1 no-padding-right" for="name">传真</label>
                                                            <div class="col-sm-6">
                                                                <div class="clearfix">
                                                                    <input id="supportFax" name="" <#if (supportMessage.fax)!?? >value="${(supportMessage.fax)!}"</#if> type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" >

                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="form-group" >
                                                            <label class="control-label col-sm-1 no-padding-right" for="name">Email</label>
                                                            <div class="col-sm-6">
                                                                <div class="clearfix">
                                                                    <input id="supportEmail" name="" <#if (supportMessage.email)!?? >value="${(supportMessage.email)!}"</#if> type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" >

                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="form-group" >
                                                            <label class="control-label col-sm-1 no-padding-right" for="name">邮编</label>
                                                            <div class="col-sm-6">
                                                                <div class="clearfix">
                                                                    <input id="supportPostCode" name="" <#if (supportMessage.postCode)!?? >value="${(supportMessage.postCode)!}"</#if> type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" >
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
                                                                    <input id="supportAddress" name="" <#if (supportMessage.address)!?? >value="${(supportMessage.address)!}"</#if> type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" >
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
                    </div>
                    <button type="button" class="btn btn-info" id="bootbox-confirm" onclick="subOrder()">保存修改</button>
                </div>
            </div><!-- /.page-content -->
        </div>
    </div><!-- /.main-content -->
</div><!-- /.main-container -->
<!-- basic scripts -->
<script type="text/javascript">

    function orderPlaceAddTranInfo(jsonStr) {
        //运输基本信息
        jsonStr.quantity = $("#quantity").val();
        jsonStr.weight = $("#weight").val();
        jsonStr.cubage = $("#cubage").val();
        jsonStr.totalStandardBox = $("#totalStandardBox").val();
        jsonStr.departurePlace = $("#departurePlace").val();
        jsonStr.destination = $("#destination").val();
        jsonStr.pickupTime = $dp.$('pickupTime').value;
        jsonStr.expectedArrivedTime = $dp.$('expectedArrivedTime').value;
        jsonStr.urgent = $("#urgent1").val();
        jsonStr.transRequire = $("#transRequire").val();
        jsonStr.consignorCode = $("#consignorCode").val();
        jsonStr.consignorName = $("#consignorName").val();
        jsonStr.consigneeCode = $("#consigneeCode").val();
        jsonStr.consigneeName = $("#consigneeName").val();
        return jsonStr;
    }
    function orderPlaceAddWareInfoWithoutSupport(jsonStr) {
        //仓配基本信息
        jsonStr.warehouseName = $("#warehouseName").val();
        jsonStr.arriveTime = $dp.$('arriveTime').value;
        jsonStr.plateNumber = $("#plateNumber").val();
        jsonStr.driverName = $("#driverName").val();
        jsonStr.contactNumber = $("#contactNumber").val();

        return jsonStr;
    }
    function getCscContantAndCompanyDtoConsignorStr() {
        var paramConsignor = {};
        var paramConsignee = {};
        var cscContact = {};
        var cscContactCompany = {};
        cscContactCompany.contactCompanyName = $("#consignorName").val();
        cscContact.contactName = $("#consignorContactName").val();
        cscContact.phone = $("#consignorPhone").val();
        cscContact.fax = $("#consignorFax").val();
        cscContact.email = $("#consignorEmail").val();
        cscContact.postCode = $("#consignorPostCode").val();
        cscContact.address = $("#consignorAddress").val();
        cscContact.purpose = "2";
        paramConsignor.cscContact = cscContact;
        paramConsignor.cscContactCompany = cscContactCompany;
        var cscContantAndCompanyDtoConsignorStr = JSON.stringify(paramConsignor);
        console.log("function  consignor " + cscContantAndCompanyDtoConsignorStr);
        return cscContantAndCompanyDtoConsignorStr;

    }
    function getCscContantAndCompanyDtoConsigneeStr() {
        var paramConsignor = {};
        var paramConsignee = {};
        var cscContact = {};
        var cscContactCompany = {};
        cscContactCompany.contactCompanyName = $("#consigneeName").val();
        cscContact.contactName = $("#consigneeContactName").val();
        cscContact.phone = $("#consigneePhone").val();
        cscContact.fax = $("#consigneeFax").val();
        cscContact.email = $("#consigneeEmail").val();
        cscContact.postCode = $("#consigneePostCode").val();
        cscContact.address = $("#consigeeAddress").val();
        cscContact.purpose = "1";
        paramConsignee.cscContact = cscContact;
        paramConsignee.cscContactCompany = cscContactCompany;
        var cscContantAndCompanyDtoConsigneeStr = JSON.stringify(paramConsignee);
        console.log("function  consignee " + cscContantAndCompanyDtoConsigneeStr);
        return cscContantAndCompanyDtoConsigneeStr;
    }
    function getCscSupplierInfoDtoStr(){
        var paramSupport = {};
        debugger
        paramSupport.supplierName = $("#supportName").val();
        paramSupport.contactName = $("#supportContactName").val();
        paramSupport.contactPhone = $("#supportPhone").val();
        paramSupport.fax = $("#supportFax").val();
        paramSupport.email = $("#supportEmail").val();
        paramSupport.postCode = $("#supportPostCode").val();
        paramSupport.address = $("#supportAddress").val();
        var cscSupplierInfoDtoStr = JSON.stringify(paramSupport);
        console.log("function  support " + cscSupplierInfoDtoStr);
        return cscSupplierInfoDtoStr;
    }


    $(function(){
        $("#goodsSelectFormBtn").click(function () {
            CommonClient.post(sys.rootPath + "/ofc/goodsSelect", $("#goodsSelConditionForm").serialize(), function(data) {
                data=eval(data);
                var goodsList = "";
                $.each(data,function (index,cscGoodsVo) {
                    goodsList =goodsList + "<tr role='row' class='odd' align='center'>";
                    goodsList =goodsList + "<td class='center'> "+"<label class='pos-rel'>"+"<input type='checkbox' class='ace'>"+"<span class='lbl'></span>"+"</label>"+"</td>";
                    goodsList =goodsList + "<td>"+cscGoodsVo.goodsCode+"</td>";
                    goodsList =goodsList + "<td>"+cscGoodsVo.goodsName+"</td>";
                    goodsList =goodsList + "<td>"+cscGoodsVo.specification+"</td>";
                    goodsList =goodsList + "<td>"+cscGoodsVo.unit+"</td>";
                    goodsList =goodsList + "</tr>";

                });
                $("#goodsSelectListTbody").html(goodsList);
            },"json");
        });

        $("#consignorSelectFormBtn").click(function () {
            //$.post("/ofc/contactSelect",$("#consignorSelConditionForm").serialize(),function (data) {
            var cscContantAndCompanyDto = {};
            var cscContact = {};
            var cscContactCompany = {};
            cscContactCompany.contactCompanyName = $("#consignorName2").value;
            cscContact.purpose = "2";
            cscContact.contactName = $("#consignorPerson2").value;
            cscContact.phone = $("#consignorPhoneNumber2").value;
            cscContantAndCompanyDto.cscContact = cscContact;
            cscContantAndCompanyDto.cscContactCompany = cscContactCompany;
            var param = JSON.stringify(cscContantAndCompanyDto);
            CommonClient.post(sys.rootPath + "/ofc/contactSelect",{"cscContantAndCompanyDto":param}, function(data) {
                data=eval(data);
                var contactList = "";
                $.each(data,function (index,CscContantAndCompanyDto) {
                    contactList =contactList + "<tr role='row' class='odd' align='center'>";
                    contactList =contactList + "<td class='center'> "+"<label class='pos-rel'>"+"<input name='consigneeSel' type='radio' class='ace'>"+"<span class='lbl'></span>"+"</label>"+"</td>";
                    contactList =contactList + "<td>"+(index+1)+"</td>";
                    contactList =contactList + "<td>"+CscContantAndCompanyDto.contactCompanyName+"</td>";
                    contactList =contactList + "<td>"+CscContantAndCompanyDto.contactName+"</td>";
                    contactList =contactList + "<td>"+CscContantAndCompanyDto.phone+"</td>";
                    contactList =contactList + "<td>"+CscContantAndCompanyDto.fax+"</td>";
                    contactList =contactList + "<td>"+CscContantAndCompanyDto.email+"</td>";
                    contactList =contactList + "<td>"+CscContantAndCompanyDto.postCode+"</td>";
                    contactList =contactList + "<td>"+CscContantAndCompanyDto.address+"</td>";
                    contactList =contactList + "</tr>";
                });
                $("#contactSelectListTbody2").html(contactList);
            },"json");
        });

        $("#consigneeSelectFormBtn").click(function () {
            var cscContantAndCompanyDto = {};
            var cscContact = {};
            var cscContactCompany = {};
            cscContactCompany.contactCompanyName = $("#consignorName2").value;
            cscContact.purpose = "1";
            cscContact.contactName = $("#consignorPerson2").value;
            cscContact.phone = $("#consignorPhoneNumber2").value;
            cscContantAndCompanyDto.cscContact = cscContact;
            cscContantAndCompanyDto.cscContactCompany = cscContactCompany;
            var param = JSON.stringify(cscContantAndCompanyDto);
            CommonClient.post(sys.rootPath + "/ofc/contactSelect",{"cscContantAndCompanyDto":param}, function(data) {
                data=eval(data);
                var contactList = "";
                $.each(data,function (index,CscContantAndCompanyDto) {
                    contactList =contactList + "<tr role='row' class='odd' align='center'>";
                    contactList =contactList + "<td class='center'> "+"<label class='pos-rel'>"+"<input name='suppliersele' type='radio' class='ace'>"+"<span class='lbl'></span>"+"</label>"+"</td>";
                    contactList =contactList + "<td>"+(index+1)+"</td>";
                    contactList =contactList + "<td>"+CscContantAndCompanyDto.contactCompanyName+"</td>";
                    contactList =contactList + "<td>"+CscContantAndCompanyDto.contactName+"</td>";
                    contactList =contactList + "<td>"+CscContantAndCompanyDto.phone+"</td>";
                    contactList =contactList + "<td>"+CscContantAndCompanyDto.fax+"</td>";
                    contactList =contactList + "<td>"+CscContantAndCompanyDto.email+"</td>";
                    contactList =contactList + "<td>"+CscContantAndCompanyDto.postCode+"</td>";
                    contactList =contactList + "<td>"+CscContantAndCompanyDto.address+"</td>";
                    contactList =contactList + "</tr>";
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
                    supplierList =supplierList + "<tr role='row' class='odd' align='center'>";
                    supplierList =supplierList + "<td class='center'> "+"<label class='pos-rel'>"+"<input id='selGoods' type='radio' name = 'selGoods' class='ace'>"+"<span class='lbl'></span>"+"</label>"+"</td>";
                    supplierList =supplierList + "<td>"+(index+1)+"</td>";
                    supplierList =supplierList + "<td>"+CscSupplierInfoDto.supplierName+"</td>";
                    supplierList =supplierList + "<td>"+CscSupplierInfoDto.contactName+"</td>";
                    supplierList =supplierList + "<td>"+CscSupplierInfoDto.contactPhone+"</td>";
                    supplierList =supplierList + "<td>"+CscSupplierInfoDto.fax+"</td>";
                    supplierList =supplierList + "<td>"+CscSupplierInfoDto.email+"</td>";
                    supplierList =supplierList + "<td>"+CscSupplierInfoDto.postCode+"</td>";
                    supplierList =supplierList + "<td>"+CscSupplierInfoDto.address+"</td>";
                    supplierList =supplierList + "</tr>";
                    $("#supplierSelectListTbody").html(supplierList);
                });
            },"json");
        });

        $("#contactoutEnter").click(function () {
            var consignorout = "";
            $("#contactSelectListTbody1").find("tr").each(function(index){
                var tdArr = $(this).children();
                if(tdArr.eq(0).find("input").prop("checked")){
                    consignorout = "1";
                    var consignorName = tdArr.eq(2).text();//名称
                    var contacts = tdArr.eq(3).text();//联系人
                    var contactsNumber = tdArr.eq(4).text();//    联系电话
                    var fax = tdArr.eq(5).text();//    传真
                    var email = tdArr.eq(6).text();//    email
                    var code = tdArr.eq(7).text();//    邮编
                    var address = tdArr.eq(8).text();//    地址
                    $("#consigneeName").val(consignorName);
                    $("#consigneeContactName").val(contacts);
                    $("#consigneePhone").val(contactsNumber);
                    $("#consigneeFax").val(fax);
                    $("#consigneeEmail").val(email);
                    $("#consigneePostCode").val(code);
                    $("#consigneeAddress").val(address);


                }
            });
            if(consignorout==""){
                alert("请至少选择一行");
            }
        });

        $("#goodsEnter").click(function () {
            var goodsInfoListDiv = "";
            $("#goodsSelectListTbody").find("tr").each(function(index){
                var tdArr = $(this).children();
                if(tdArr.eq(0).find("input").prop("checked")){

                    var goodsCode = tdArr.eq(1).text();//货品编码
                    var goodsName = tdArr.eq(2).text();//货品名称
                    var specification = tdArr.eq(3).text();//    货品规格
                    var unit = tdArr.eq(4).text();//    单位
                    goodsInfoListDiv =goodsInfoListDiv + "<tr role='row' class='odd' align='center'>";
                    goodsInfoListDiv =goodsInfoListDiv + "<td><button type='button' onclick='deleteGood(this)' class='btn btn-minier btn-danger'>删除</button></td>";
                    /*goodsInfoListDiv =goodsInfoListDiv + "<td><input id='deleteOrNot' type='checkbox'/></td>";*/
                    goodsInfoListDiv =goodsInfoListDiv + "<td>"+goodsCode+"</td>";
                    goodsInfoListDiv =goodsInfoListDiv + "<td>"+goodsName+"</td>";
                    goodsInfoListDiv =goodsInfoListDiv + "<td>"+specification+"</td>";
                    goodsInfoListDiv =goodsInfoListDiv + "<td>"+unit+"</td>";
                    goodsInfoListDiv =goodsInfoListDiv + "<td><input name='' type='search' value='' class='form-control input-sm' placeholder='' aria-controls='dynamic-table' ></td>";
                    goodsInfoListDiv =goodsInfoListDiv + "<td><input name='' type='search' value='' class='form-control input-sm' placeholder='' aria-controls='dynamic-table' ></td>";
                    goodsInfoListDiv =goodsInfoListDiv + "<td><input name='' type='search' value='' class='form-control input-sm' placeholder='' aria-controls='dynamic-table' onClick=\"WdatePicker({isShowClear:true,readOnly:true,dateFmt:\"yyyy-MM-dd HH:mm:ss\"})\"></td>";
                    goodsInfoListDiv =goodsInfoListDiv + "<td><input name='' type='search' value='' class='form-control input-sm' placeholder='' aria-controls='dynamic-table' onClick=\"WdatePicker({isShowClear:true,readOnly:true,dateFmt:\"yyyy-MM-dd HH:mm:ss\"})\"></td>";
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
                    consignorin = "1";
                    var consignorName = tdArr.eq(2).text();//名称
                    var contacts = tdArr.eq(3).text();//联系人
                    var contactsNumber = tdArr.eq(4).text();//    联系电话
                    var fax = tdArr.eq(5).text();//    传真
                    var email = tdArr.eq(6).text();//    email
                    var code = tdArr.eq(7).text();//    邮编
                    var address = tdArr.eq(8).text();//    地址
                    $("#testtest").text(consignorName);
                    $("#consignorName").val(consignorName);
                    $("#consignorContactName").val(contacts);
                    $("#consignorPhone").val(contactsNumber);
                    $("#consignorFax").val(fax);
                    $("#consignorEmail").val(email);
                    $("#consignorPostCode").val(code);
                    $("#consignorAddress").val(address);
                }
            });
            if(consignorin==""){
                alert("请至少选择一行");
            }
        });

        $("#supplierEnter").click(function () {
            var support = "";
            $("#supplierSelectListTbody").find("tr").each(function(index){
                var tdArr = $(this).children();
                if(tdArr.eq(0).find("input").prop("checked")){
                    support = "1";
                    var consignorName = tdArr.eq(2).text();//名称
                    var contacts = tdArr.eq(3).text();//联系人
                    var contactsNumber = tdArr.eq(4).text();//    联系电话
                    var fax = tdArr.eq(5).text();//    传真
                    var email = tdArr.eq(6).text();//    email
                    var code = tdArr.eq(7).text();//    邮编
                    var address = tdArr.eq(8).text();//    地址
                    $("#supportName").val(consignorName);
                    $("#supportContactName").val(contacts);
                    $("#supportPhone").val(contactsNumber);
                    $("#supportFax").val(fax);
                    $("#supportEmail").val(email);
                    $("#supportPostCode").val(code);
                    $("#supportAddress").val(address);

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

        if($("#orderType").val()== '60'){
            $("#provideTransportDiv").hide();
            $("#businessTypeDiv").hide();
            $('.tranfr').show();
            $('.storeLi').hide();
            $('#myTab4').find('li').each(function () {
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
            $('#myTab4').find('li').each(function () {
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
        var orderTypeForSupportDiv = $("#orderType").val();
        var businessTypeForSupportDiv = $("#businessType").val().substring(0,2);
        console.log("===========orderTypeForSupportDiv============="+orderTypeForSupportDiv);
        console.log("===========businessTypeForSupportDiv============="+businessTypeForSupportDiv);
        if(orderTypeForSupportDiv == '61' && businessTypeForSupportDiv == '62'){
            $("#supportMessageShowDiv").show();
        }

        $("#orderType").change(function () {
            if($(this).children('option:selected').val() == '61'){
                $("#provideTransportDiv").show();
                $("#businessTypeDiv").show();
                $('.storeLi').show();
                $('.tranfr').hide();
                $('#myTab4').find('li').each(function () {
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

                $("#provideTransportDiv").hide();
                $("#businessTypeDiv").hide();
                $('.tranfr').show();
                $('.storeLi').hide();
                $('#myTab4').find('li').each(function () {
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

        $("#businessType").change(function () {
            var businessType = $("#businessType").val().substring(0,2);
            if('62' == businessType){
                $("#supportMessageShowDiv").show();
            }
            if('61' == businessType){
                $("#supportMessageShowDiv").hide();
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

    });
    function subOrder() {
        var jsonStr = {};
        //订单基本信息
        jsonStr.orderCode = $("#orderCode").val();
        jsonStr.orderTime = $dp.$('orderTime').value;
        jsonStr.custOrderCode = $("#custOrderCode").val();
        jsonStr.orderType = $("#orderType").val();
        jsonStr.businessType = $("#businessType").val();
        jsonStr.provideTransport = $("#provideTransport1").val();
        jsonStr.storeCode = $("#storeCode").val();
        jsonStr.notes = $("#notes").val();
        //货品添加
        debugger;
        var orderGoodsList = [];
        var goodsTable = document.getElementById("orderGoodsListTable");
        for(var tableRows = 1; tableRows < goodsTable.rows.length; tableRows ++ ){
            var orderGoods = {};
            for(var tableCells = 1; tableCells < goodsTable.rows[tableRows].cells.length; tableCells ++){
                var param = goodsTable.rows[tableRows].cells[tableCells].innerText;
                switch (tableCells){
                    case 1 :orderGoods.goodsCode = param;break;
                    case 2 :orderGoods.goodsName = param;break;
                    case 3 :orderGoods.goodsSpec = param;break;
                    case 4 :orderGoods.unit = param;break;
                    case 5 :orderGoods.quantity = goodsTable.rows[tableRows].cells[tableCells].getElementsByTagName("input")[0].value;break;
                    case 6 :orderGoods.productionBatch =  goodsTable.rows[tableRows].cells[tableCells].getElementsByTagName("input")[0].value;break;
                }
            }
            orderGoodsList[tableRows - 1] = orderGoods;
        }
        console.log("==orderGoodsList=="+orderGoodsList);
        debugger;

        //订单类型
        var orderType = $("#orderType").val();
        //业务类型前两位
        var businessType = $("#businessType").val().substring(0,2);
        ///仓配订单是否需要运输
        var provideTrans = $("#provideTransport1").val();
        //如果订单类型是运输订单,才拼装运单信息
        //如果订单类型是仓配订单而且是入库单,才拼装仓储信息和供应商信息,如果需要运输才需要拼装运输信息
        //别忘了在后台卡一下~!~~
        var cscContantAndCompanyDtoConsignorStr;
        var cscContantAndCompanyDtoConsigneeStr;
        var cscSupplierInfoDtoStr;
        if(orderType == '60'){//运输订单
            jsonStr = orderPlaceAddTranInfo(jsonStr);
            cscContantAndCompanyDtoConsignorStr = getCscContantAndCompanyDtoConsignorStr();
            cscContantAndCompanyDtoConsigneeStr = getCscContantAndCompanyDtoConsigneeStr();
        }
        debugger;
        if(orderType == '61' && businessType == '61'){//仓储出库订单
            jsonStr = orderPlaceAddWareInfoWithoutSupport(jsonStr);
            if('1' == provideTrans){
                cscContantAndCompanyDtoConsignorStr = getCscContantAndCompanyDtoConsignorStr();
                cscContantAndCompanyDtoConsigneeStr = getCscContantAndCompanyDtoConsigneeStr();
                jsonStr = orderPlaceAddTranInfo(jsonStr);
            }
        }
        if(orderType == '61' && businessType == '62'){ //仓储入库订单,才需要供应商信息
            jsonStr = orderPlaceAddWareInfoWithoutSupport(jsonStr);
            //仓配供应商基本信息
            jsonStr.supportCode = $("#supportCode").val();
            jsonStr.supportName = $("#supportName").val();
            cscSupplierInfoDtoStr = getCscSupplierInfoDtoStr();
            if('1' == provideTrans){
                cscContantAndCompanyDtoConsignorStr = getCscContantAndCompanyDtoConsignorStr();
                cscContantAndCompanyDtoConsigneeStr = getCscContantAndCompanyDtoConsigneeStr();
                jsonStr = orderPlaceAddTranInfo(jsonStr);
            }
        }
        var tag = "manage";
        var ofcOrderDTOJson = JSON.stringify(jsonStr);
        var orderGoodsListStr = JSON.stringify(orderGoodsList);
        /*xescm.common.loadPage("/ofc/orderEdit/" + tag + "/" + ofcOrderDTOJson
                + "/" + orderGoodsListStr+"~`"
                + "/" + cscContantAndCompanyDtoConsignorStr
                + "/" + cscContantAndCompanyDtoConsigneeStr
                + "/" + cscSupplierInfoDtoStr);*/
        debugger;
        xescm.common.submit("/ofc/orderEdit"
                ,{"ofcOrderDTOStr":ofcOrderDTOJson
                    ,"orderGoodsListStr":orderGoodsListStr+"~`"
                    ,"cscContantAndCompanyDtoConsignorStr":cscContantAndCompanyDtoConsignorStr
                    ,"cscContantAndCompanyDtoConsigneeStr":cscContantAndCompanyDtoConsigneeStr
                    ,"cscSupplierInfoDtoStr":cscSupplierInfoDtoStr
                    ,"tag":tag}
                ,"您确认提交订单吗?"
                ,function () {
                    debugger
                    xescm.common.loadPage("/ofc/orderManage");
                })
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

</script>

</body>