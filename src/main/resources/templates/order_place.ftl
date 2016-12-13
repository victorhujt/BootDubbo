<head>
    <title>我要下单</title>
    <style type="text/css">
        #goodsListDiv {
            position:fixed;
            left:50%;
            top:85px;
            margin-left:-400px;
            width:946px;
            height:500px;
          z-index:3;margin-left:-400px;
            overflow: auto;
            border:solid #7A7A7A 2px;
        }
        #consignorListDiv {
            position:fixed;
            left:50%;
            top:77px;
            width:946px;
            height:500px;
           z-index:3;margin-left:-400px;
            overflow: auto;
            border:solid #7A7A7A 2px;
        }
        #consigneeListDiv {
            position:fixed;
            left:50%;
            top:77px;
            width:946px;
            height:500px;
           z-index:3;margin-left:-400px;
            overflow: auto;
            border:solid #7A7A7A 2px;
        }
        #supportListDiv {
            position:fixed;
            left:50%;
            top:77px;
            width:946px;
            height:500px;
          z-index:3;margin-left:-400px;
            overflow: auto;
            border:solid #7A7A7A 2px;
        }
        input{
            color: black;
        }
        .form-horizontal .control-label{padding-top:0;}
       #dropdown14 .col-label{
           margin-left:53px;
       }
        .form-group > label[class*="col-"]{
            margin-right:0;
        }
        #dropdown14 .chosen-container{
            width:220px!important;
        }
        #dynamic-table_filter{
            margin-left:12px;
        }
        .city-picker-span{
            border:1px solid #cacaca;
        }
        .help-block{
            color:#f00 !important;
        }
        .has-error .checkbox, .has-error .checkbox-inline, .has-error .control-label, .has-error .help-block, .has-error .radio, .has-error .radio-inline, .has-error.checkbox label, .has-error.checkbox-inline label, .has-error.radio label, .has-error.radio-inline label{
            color:#393939;
        }
        .has-success .checkbox, .has-success .checkbox-inline, .has-success .control-label, .has-success .help-block, .has-success .radio, .has-success .radio-inline, .has-success.checkbox label, .has-success.checkbox-inline label, .has-success.radio label, .has-success.radio-inline label{
            color:#393939;
        }
        .has-error .form-control{
            border-color:#cacaca;
        }
        .has-success .form-control{
            border-color:#cacaca;
        }
        #businessTypeDiv .chosen-container{
            width:510px!important;
        }
        .city-picker-span{width:510px;}
    </style>
<#--<script src="../js/My97DatePicker/WdatePicker.js" type="text/javascript"></script>-->

</head>

<body class="no-skin">

<#--<form id="orderPlaceForm">-->

<!--goodsListDiv-->
<div class="modal-content" id="goodsListDiv" style="display: none;">
    <div class="modal-header"><span id="goodsListDivNoneTop" style="cursor:pointer"><button type="button" id="" style="cursor:pointer" class="bootbox-close-button close" data-dismiss="modal" aria-hidden="true">×</button></span>
        <p class="modal-title" style="font-size:14px;font-family:'微软雅黑'">货品列表</p></div>
    <div class="modal-body">
        <div class="bootbox-body">
            <form id="goodsSelConditionForm" class="form-horizontal" role="form">
                <div class="form-group">
                    <label class="control-label col-label no-padding-right" for="name">货品编码</label>
                    <div class="col-sm-3">
                        <div class="clearfix">
                            <input  id = "goodsCodeCondition" name="goodsCode" type="text" style="color: black" class="form-control input-sm" placeholder="" aria-controls="dynamic-table">
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-label no-padding-right" for="name">货品名称</label>
                    <div class="col-sm-3">
                        <div class="clearfix">
                            <input  id = "goodsNameCondition" name="goodsName" type="text" style="color: black" class="form-control input-sm" placeholder="" aria-controls="dynamic-table">
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-label no-padding-right" for="name"></label>
                    <div class="col-sm-3">
                        <div class="clearfix">
                            <span id="goodsSelectFormBtn" class="btn btn-white btn-info btn-bold btn-interval" style="margin-top:3px;">筛选</span>
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
    <div class="modal-footer" style="background:#fff;"><span id="goodsListDivNoneBottom" style="cursor:pointer"><button  data-bb-handler="cancel" type="button" class="btn btn-white btn-info btn-bold btn-interval y-rt-btn">返回</button></span><button id="goodsEnter" data-bb-handler="confirm" type="button" class="btn btn-white btn-info btn-bold btn-interval">确定</button></div>
</div>
<!--consignorListDiv-->
<div class="modal-content" id="consignorListDiv" style="display: none;">
    <div class="modal-header"><span id="consignorListDivNoneTop" style="cursor:pointer"><button type="button" id="" style="cursor:pointer" class="bootbox-close-button close" data-dismiss="modal" aria-hidden="true">×</button></span>
        <p class="w-font">发货方联系人</p></div>
    <div class="modal-body">
        <div class="bootbox-body">
            <form id="consignorSelConditionForm" class="form-horizontal" role="form">
            <#--<input id="purpose2" name="cscContact.purpose" type="hidden" value="2">-->
                <div class="form-group">
                    <label class="control-label col-label no-padding-right" for="name">名称</label>
                    <div class="col-sm-3">
                        <div class="clearfix">
                            <input  id = "consignorName2" name="cscContactCompany.contactCompanyName" type="text" style="color: black" class="form-control input-sm" placeholder="" aria-controls="dynamic-table">
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-label no-padding-right" for="name">联系人</label>
                    <div class="col-sm-3">
                        <div class="clearfix">
                            <input  id = "consignorPerson2" name="cscContact.contactName" type="text" style="color: black" class="form-control input-sm" placeholder="" aria-controls="dynamic-table">
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-label no-padding-right" for="name">联系电话</label>
                    <div class="col-sm-3">
                        <div class="clearfix">
                            <input  id = "consignorPhoneNumber2" name="cscContact.phone" type="text" style="color: black" class="form-control input-sm" placeholder="" aria-controls="dynamic-table">
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-label no-padding-right" for="name"></label>
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
    <div class="modal-footer"><span id="consignorListDivNoneBottom" style="cursor:pointer"><button  data-bb-handler="cancel" type="button" class="btn btn-default">取消</button></span><button id="contactinEnter" data-bb-handler="confirm" type="button" style="margin-left:15px;" class="btn btn-primary">确定</button></div>
</div>
<!--consigneeListDiv-->
<div class="modal-content" id="consigneeListDiv" style="display: none;">
    <div class="modal-header"><span id="consigneeListDivNoneTop" style="cursor:pointer"><button type="button" id="" style="cursor:pointer" class="bootbox-close-button close" data-dismiss="modal" aria-hidden="true">×</button></span>
        <h4 class="w-font">收货方联系人</h4></div>
    <div class="modal-body">
        <div class="bootbox-body">
            <form id="consigneeSelConditionForm" class="form-horizontal" role="form">
            <#--<input id="purpose2" name="purpose" type="hidden" value="1">-->
                <div class="form-group">
                    <label class="control-label col-label no-padding-right" for="name">名称</label>
                    <div class="col-sm-3">
                        <div class="clearfix">
                            <input  id = "consignorName1" name="contactCompanyName" type="text" style="color: black" class="form-control input-sm" placeholder="" aria-controls="dynamic-table">
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-label no-padding-right" for="name">联系人</label>
                    <div class="col-sm-3">
                        <div class="clearfix">
                            <input  id = "consignorPerson1" name="contactName" type="text" style="color: black" class="form-control input-sm" placeholder="" aria-controls="dynamic-table">
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-label no-padding-right" for="name">联系电话</label>
                    <div class="col-sm-3">
                        <div class="clearfix">
                            <input  id = "consignorPhoneNumber1" name="phone" type="text" style="color: black" class="form-control input-sm" placeholder="" aria-controls="dynamic-table">
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-label no-padding-right" for="name"></label>
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
    <div class="modal-footer"><span id="consigneeListDivNoneBottom" style="cursor:pointer"><button  data-bb-handler="cancel" type="button" class="btn btn-default" style="margin:0 15px;">取消</button></span><button id="contactoutEnter" data-bb-handler="confirm" type="button" class="btn btn-primary">确认</button></div>
</div>
<!--supportListDiv-->
<div class="modal-content" id="supportListDiv" style="display: none;">
    <div class="modal-header"><span id="supportListDivNoneTop" style="cursor:pointer"><button type="button" id="" style="cursor:pointer" class="bootbox-close-button close" data-dismiss="modal" aria-hidden="true">×</button></span>
        <h4 class="modal-title">供应商联系人</h4></div>
    <div class="modal-body">
        <div class="bootbox-body">
            <form id="supplierSelConditionForm" class="form-horizontal" role="form">
                <div class="form-group">
                    <label class="control-label col-label no-padding-right" for="name">名称</label>
                    <div class="col-sm-3">
                        <div class="clearfix">
                            <input  id = "supplierName" name="supplierName" type="text" style="color: black" class="form-control input-sm" placeholder="" aria-controls="dynamic-table">
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-label no-padding-right" for="name">联系人</label>
                    <div class="col-sm-3">
                        <div class="clearfix">
                            <input  id = "contactName" name="contactName" type="text" style="color: black" class="form-control input-sm" placeholder="" aria-controls="dynamic-table">
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-label no-padding-right" for="name">联系电话</label>
                    <div class="col-sm-3">
                        <div class="clearfix">
                            <input  id = "contactPhone" name="contactPhone" type="text" style="color: black" class="form-control input-sm" placeholder="" aria-controls="dynamic-table">
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-label no-padding-right" for="name"></label>
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

            <div class="page-header">
                <p>
                    基本信息
                    <span hidden="true" id = "ofc_url">${(OFC_URL)!}</span>
                <#--<span hidden="true" id = "addr_url">${(ADDR_URL)!}</span>-->
                <#--<#import "address.ftl" as apiAddrFtl>-->
                </p>
            </div>
            <!-- /section:basics/content.breadcrumbs -->
            <div class="page-content">

                <div class="row">
                    <div class="col-xs-12">
                        <form id="orderFundamentalFormValidate" method="post" class="form-horizontal" role="form" >
                           <#-- <div class="form-group">
                                <label class="control-label col-label no-padding-right" for="name">订单日期</label>
                                <div class="col-sm-6">
                                    <div class="clearfix">
                                        <input id="orderTime" style="color: #000" name="orderTime" type="text" class="form-control input-sm" placeholder="" aria-controls="dynamic-table"1 onClick="WdatePicker({isShowClear:true,readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})">

                                    </div>
                                </div>
                            </div>-->
                            <#--<div class="form-group">
                                <label class="control-label col-label no-padding-right" for="name">客户订单编号</label>
                                <div class="col-sm-6">
                                    <div class="clearfix">
                                        <input id="custOrderCode" name="custOrderCode" type="text" style="color: #000">
                                    </div>
                                </div>
                            </div>-->
                           <div class="form-group">
                               <div><label class="control-label col-label no-padding-right no-padding-top" for="supplierCode">订单日期</label>
                               <div class="w-width-220 y-float">
                                   <div class="clearfix">
                                       <input class="col-xs-12"  name="orderTime" id="orderTime" type="text" placeholder="订单日期" onclick="laydate({istime: true, format: 'YYYY-MM-DD hh:mm:ss',isclear: true,istoday: true,min: laydate.now(-30),max: laydate.now()})" />
                                   </div>
                               </div></div>
                               <div><label class="control-label col-label no-padding-right no-padding-top" for="custOrderCode">客户订单编号</label>
                               <div class="w-width-220 y-float">
                                   <div class="clearfix">
                                       <input class="col-xs-12"  name="custOrderCode" id="custOrderCode" type="text" placeholder="客户订单编号" />

                                   </div>
                               </div></div>
                           </div>
                            <div class="form-group">
                                <div><label class="control-label col-label no-padding-right no-padding-top" for="name">订单类型</label>
                                <div class="w-width-220 y-float">
                                    <div class="clearfix">
                                        <select class="w-width-220 chosen-select" id="orderTypeSel" name="orderType">
                                            <option value="60">运输订单</option>
                                            <option value="61">仓配订单</option>
                                        </select>
                                    </div>
                                </div></div>
                                <div><label class="control-label col-label no-padding-right no-padding-top" for="name">店铺</label>
                                <div class="w-width-220 y-float">
                                    <div class="clearfix">
                                        <select class="w-width-220 chosen-select" id="storeCode" name="storeCode">
                                        <#list cscStoreByCustId! as cscStore>
                                            <option value="${(cscStore.storeCode)!""}/${(cscStore.platformType)!""}">${(cscStore.storeName)!""}</option>
                                        </#list>
                                        <#--<option value="众品天猫生鲜旗舰店">众品天猫生鲜旗舰店</option>
                                        <option value="众品京东旗舰店">众品京东旗舰店</option>-->
                                        </select>
                                    </div>
                                </div></div>
                            </div>

                           <#--<div class="form-group" id="transBusinessTypeDiv" style="display: block">
                               <label class="control-label col-label no-padding-right" for="name">业务类型</label>
                               <div class="w-width-220 y-float">
                                   <div class="clearfix">
                                       <select  id="businessType" name="businessType">
                                           <option value="600">城配</option>
                                           <option value="601">干线</option>
                                           <option value="602">卡班</option>
                                       </select>
                                   </div>
                               </div>
                           </div>-->
                            <div class="form-group" id="businessTypeDiv" style="display: none">
                                <div><label class="control-label col-label no-padding-right" for="name">业务类型</label>
                                <div class="padding-12 y-float" style="width:536px;">
                                    <div class="clearfix">
                                    <#--<span id="businessTypeDiv" style="display: none">-->
                                        <select class="chosen-select"  id="businessType" name="businessType" style="padding-left:0;">
                                            <option value="610">销售出库</option>
                                            <option value="611">调拨出库</option>
                                            <option value="612">报损出库</option>
                                            <option value="613">其他出库</option>
                                            <option value="614">分拨出库</option>
                                            <option value="----------">----------</option>
                                            <option value="620">采购入库</option>
                                            <option value="621">调拨入库</option>
                                            <option value="622">退货入库</option>
                                            <option value="623">加工入库</option>
                                            <option value="624">盘盈入库</option>
                                            <option value="625">流通入库</option>
                                            <option value="626">其他入库</option>
                                            <option value="627">分拨入库</option>
                                        </select>
                                    <#--  </span>-->
                                    </div>
                                </div></div>
                            </div>

                            <div class="form-group" id="provideTransportDiv" style="display: none">
                                <div><label class="control-label col-label no-padding-right" for="name">是否需要运输</label>
                                <div class="col-sm-6" style="height:34px;line-height:34px;">
                                    <div class="clearfix">
                                    <#-- <span id="provideTransportDiv" style="display: none">-->
                                        <input  id="provideTransport" type="checkbox" name = ""/>
                                        <input id="provideTransportHel" type="hidden" name="provideTransport"  value="0" />
                                    <#-- </span>-->
                                    </div>
                                </div></div>
                            </div>
                            <div class="form-group">
                                <div><label class="control-label col-label no-padding-right no-padding-top y-float" for="name">备注</label>
                                <div class="col-sm-6" style="padding-left:12px;">
                                    <div class="clearfix">
                                        <input id="orderNotes" name="notes" style="color: #000;width:510px;"  type="text" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" >
                                </div></div>
                            </div>
                                </div>
                        </form>
                        <form name="orderInfoTableValidate" id="orderInfoTableValidate"  class="form-horizontal" role="form" style="margin-top:20px;" >
                            <div class="col-label2">
                                <!-- #section:elements.tab.option -->
                                <div class="tabbable" style="width:1000px;float:left;" >
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

                                            <span style="cursor:pointer" id="goodsListDivBlock"><button type="button" class="btn btn-white btn-info btn-bold btn-interval" id="bootbox-confirm">添加货品</button></span>
                                        <#--dynamic-table-->
                                            <table id="orderGoodsListTable" class="table table-striped table-bordered table-hover dataTable no-footer" role="grid" aria-describedby="dynamic-table_info">
                                                <thead>
                                                <tr role="row"><th class="center sorting_disabled" rowspan="1" colspan="1" aria-label="">
                                                    操作
                                                </th>
                                                <#--<th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Status: activate to sort column ascending">序号</th>-->
                                                    <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Domain: activate to sort column ascending">货品编码</th>
                                                    <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Price: activate to sort column ascending">货品名称</th>
                                                    <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Update: activate to sort column ascending">货品规格
                                                    </th>
                                                    <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">单位</th>
                                                    <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">单价</th>
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
                                                </div>
                                            </div>

                                        </div>

                                        <div id="profile4" class="tab-pane">

                                            <div class="page-header">
                                                <h4 class="w-font">运输基本信息</h4>
                                            </div>

                                            <div class="row">

                                                <div id="dynamic-table_filter" class="dataTables_length">

                                                                <div class="form-group" >
                                                                    <div><label class="control-label col-label no-padding-right no-padding-top no-padding-top no-padding-top" for="name">数量</label>
                                                                    <div class="w-width-220 y-float">
                                                                        <div class="clearfix">
                                                                            <input id="quantity" name="quantity" type="text" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" >
                                                                        </div>
                                                                    </div></div>
                                                                    <div><label class="control-label col-label no-padding-right no-padding-top" for="name">重量</label>
                                                                    <div class="w-width-220 y-float">
                                                                        <div class="clearfix">
                                                                            <input id="weight" name="weight" type="text" class="form-control input-sm" placeholder="" aria-controls="dynamic-table">
                                                                        </div>
                                                                    </div></div>
                                                                    <div><label class="control-label col-label no-padding-right no-padding-top" for="name">体积</label>
                                                                    <div class="w-width-220 y-float">
                                                                        <div class="clearfix" style="color:#c3c3c3;">
                                                                            <input id="cubage" name="cubage" type="text" class="form-control input-sm" placeholder="" aria-controls="dynamic-table">(长*宽*高,单位:m)
                                                                        </div>
                                                                    </div></div>
                                                                </div>

                                                                <div class="form-group" >
                                                                    <div><label class="control-label col-label no-padding-right no-padding-top" for="name">合计标准箱</label>
                                                                    <div class="w-width-220 y-float">
                                                                        <div class="clearfix">
                                                                            <input id="totalStandardBox" name="totalStandardBox" type="text" class="form-control input-sm" placeholder="" aria-controls="dynamic-table">
                                                                        </div>
                                                                    </div></div>
                                                                    <div><label class="control-label col-label no-padding-right no-padding-top" for="name">取货时间</label>
                                                                    <div class="w-width-220 y-float">
                                                                        <div class="clearfix">
                                                                            <input id="pickupTime" name="pickupTime" type="text" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" onclick="laydate({istime: true, format: 'YYYY-MM-DD hh:mm',isclear: true,istoday: true})">
                                                                        </div>
                                                                    </div></div>
                                                                    <div><label class="control-label col-label no-padding-right no-padding-top" for="name">期望送达时间</label>
                                                                    <div class="w-width-220 y-float">
                                                                        <div class="clearfix">
                                                                            <input id="expectedArrivedTime" name="expectedArrivedTime" type="text" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" onclick="laydate({istime: true, format: 'YYYY-MM-DD hh:mm',isclear: true,istoday: true})">
                                                                        </div>
                                                                    </div></div>
                                                                </div>

                                                                <div class="form-group" >
                                                                    <div><label class="control-label col-label no-padding-right no-padding-top" for="name">是否加急</label>
                                                                    <div class="w-width-220 y-float">
                                                                        <div class="clearfix">
                                                                            <input id="urgent" type="checkbox" name="" style="margin-top:10px;" />
                                                                            <input id="urgentHel" type="hidden" name="urgent"  value="0" />
                                                                        </div>
                                                                    </div></div>
                                                                    <div><label class="control-label col-label no-padding-right no-padding-top" for="name">运输要求</label>
                                                                    <div class="w-width-220 y-float">
                                                                        <div class="clearfix">
                                                                            <input id="transRequire" name="transRequire" type="text" class="form-control input-sm" placeholder="" aria-controls="dynamic-table">
                                                                        </div>
                                                                    </div></div>
                                                                </div>

                                                            </div>
                                                        </div>

                                            <div class="page-header">
                                                <h4 class="w-font">发货方信息</h4>
                                            </div>
                                            <span style="cursor:pointer" id="consignorListDivBlock"><button type="button" class="btn btn-white btn-info btn-bold btn-interval" id="consignorselbtn">选择</button></span>

                                            <div id="consignorin">
                                                <div class="form-group" >
                                                    <div><label class="control-label col-label no-padding-right no-padding-top" for="name">名称</label>
                                                    <div class="w-width-220 y-float">
                                                        <div class="clearfix">
                                                            <input id="consignorCode" name="consignorCode" type="hidden">
                                                            <input id="consignorType" name="consignorType" type="hidden">
                                                            <input id="consignorName"  name="consignorName" type="text" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" >
                                                        </div>
                                                    </div></div>
                                                    <div><label class="control-label col-label no-padding-right no-padding-top" for="name">联系人</label>
                                                    <div class="w-width-220 y-float">
                                                        <div class="clearfix">
                                                            <input id="consignorContactCode"   name="consignorContactCode" type="hidden" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" >
                                                            <input id="consignorContactName"   name="consignorContactName" type="text" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" >
                                                        </div>
                                                    </div></div>
                                                    <div><label class="control-label col-label no-padding-right no-padding-top" for="name">联系电话</label>
                                                    <div class="w-width-220 y-float">
                                                        <div class="clearfix">
                                                            <input id="consignorPhone" name="consignorPhone" type="text" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" >

                                                        </div>
                                                    </div></div>
                                                </div>

                                                <div class="form-group" >
                                                    <div><label class="control-label col-label no-padding-right no-padding-top" for="name">传真</label>
                                                    <div class="w-width-220 y-float">
                                                        <div class="clearfix">
                                                            <input id="consignorFax" name="consignorFax" type="text" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" >

                                                        </div>
                                                    </div></div>
                                                    <div><label class="control-label col-label no-padding-right no-padding-top" for="name">Email</label>
                                                    <div class="w-width-220 y-float">
                                                        <div class="clearfix">
                                                            <input id="consignorEmail" name="consignorEmail" type="text" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" >
                                                        </div>
                                                    </div></div>
                                                    <div><label class="control-label col-label no-padding-right no-padding-top" for="name">邮编</label>
                                                    <div class="w-width-220 y-float">
                                                        <div class="clearfix">
                                                            <input id="consignorPostCode" name="consignorPostCode" type="text" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" >
                                                        </div>
                                                    </div></div>
                                                </div>

                                                <div class="form-group" >
                                                    <label class="control-label col-label no-padding-right no-padding-top" style="margin-right:0;" for="name">地址选择</label>
                                                    <div class="y-float padding-12" style="width:534px;">
                                                        <div class="clearfix position-relative">
                                                            <input id="city-picker3-consignor" class="form-control input-sm" readonly type="text" value="" data-toggle="city-picker">
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="form-group" >
                                                    <label class="control-label col-label no-padding-right no-padding-top " style="margin-right:0;"  for="name">详细地址</label>
                                                    <div class="y-float padding-12" style="width:534px;">
                                                        <div class="clearfix">
                                                            <input id="consignorAddress" name="consignorAddress" type="text" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" >
                                                        </div>
                                                    </div>
                                                </div>

                                            </div>

                                            <div class="page-header">
                                                <h4 class="w-font">收货方信息</h4>
                                            </div>
                                            <span style="cursor:pointer" id="consigneeListDivBlock"><button type="button" class="btn btn-white btn-info btn-bold btn-interval" id="consigneeselbtn">选择</button></span>
                                            <div id="consignorout">
                                                <div class="form-group" >
                                                    <div><label class="control-label col-label no-padding-right no-padding-top" for="name">名称</label>
                                                    <div class="w-width-220 y-float">
                                                        <div class="clearfix">
                                                            <input id="consigneeCode" name="consigneeCode" type="hidden">
                                                            <input id="consigneeType" name="consigneeType" type="hidden">
                                                            <input id="consigneeName"  name="consigneeName" type="text" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" >

                                                        </div>
                                                    </div></div>
                                                    <div><label class="control-label col-label no-padding-right no-padding-top" for="name">联系人</label>
                                                    <div class="w-width-220 y-float">
                                                        <div class="clearfix">
                                                            <input id="consigneeContactCode" name="consigneeContactCode" type="hidden" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" >
                                                            <input id="consigneeContactName" name="consigneeContactName" type="text" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" >

                                                        </div>
                                                    </div></div>
                                                    <div><label class="control-label col-label no-padding-right no-padding-top" for="name">联系电话</label>
                                                    <div class="w-width-220 y-float">
                                                        <div class="clearfix">
                                                            <input id="consigneePhone" name="consigneePhone" type="text" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" >

                                                        </div>
                                                    </div></div>
                                                </div>

                                                <div class="form-group" >
                                                    <div><label class="control-label col-label no-padding-right no-padding-top" for="name">传真</label>
                                                    <div class="w-width-220 y-float">
                                                        <div class="clearfix">
                                                            <input id="consigneeFax" name="consigneeFax" type="text" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" >

                                                        </div>
                                                    </div></div>
                                                    <label class="control-label col-label no-padding-right no-padding-top" style="margin-right:0;" for="name">Email</label>
                                                    <div><div class="w-width-220 y-float">
                                                        <div class="clearfix">
                                                            <input id="consigneeEmail" name="consigneeEmail" type="text" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" >
                                                        </div>
                                                    </div></div>
                                                    <div><label class="control-label col-label no-padding-right no-padding-top" for="name">邮编</label>
                                                    <div class="w-width-220 y-float">
                                                        <div class="clearfix">
                                                            <input id="consigneePostCode" name="consigneePostCode" type="text" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" >
                                                        </div>
                                                    </div></div>
                                                </div>

                                                <div class="form-group" >
                                                    <label class="control-label col-label no-padding-right no-padding-top" for="name" style="margin-right:0;">地址选择</label>
                                                    <div class="y-float padding-12" style="width:534px;">
                                                        <div class="clearfix position-relative">
                                                            <input id="city-picker3-consignee" class="form-control input-sm" readonly type="text" value="" data-toggle="city-picker">
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="form-group" >
                                                    <label class="control-label col-label no-padding-right no-padding-top" for="name" style="margin-right:0px;">详细地址</label>
                                                    <div class="y-float padding-12" style="width:534px;">
                                                        <div class="clearfix">
                                                            <input id="consigneeAddress" name="consigneeAddress" type="text" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" >
                                                        </div>
                                                    </div>
                                                </div>

                                            </div>
                                        </div>

                                        <div id="dropdown14" class="tab-pane">

                                            <div class="page-header">
                                                <p class="w-font">仓配基本信息</p>
                                            </div>

                                            <div class="row">
                                                <div id="dynamic-table_filter" class="dataTables_length">


                                                    <div class="form-group" >
                                                        <label class="control-label col-label no-padding-right no-padding-top" for="name">仓库名称</label>
                                                        <div class="col-sm-6">
                                                            <div class="clearfix">
                                                                <select id="warehouseName" name="warehouseName" class="chosen-select" style="width:220px;">
                                                                <#list rmcWarehouseByCustCode! as warehouse>
                                                                    <option value="${(warehouse.id)!}">${(warehouse.warehouseName)!""}</option>
                                                                </#list>

                                                                </select>

                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="form-group" >
                                                        <label class="control-label col-label no-padding-right no-padding-top" for="name" style="margin-left:0;width:125px;">入库预计到达时间</label>
                                                        <div class="col-sm-6">
                                                            <div class="clearfix">
                                                                <input id="arriveTime" name="arriveTime"  type="text" class="form-control input-sm" style="width:220px;" placeholder="" aria-controls="dynamic-table" onClick="WdatePicker({isShowClear:true,readOnly:true,dateFmt:'yyyy-MM-dd HH:mm'})">

                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="form-group" >
                                                        <label class="control-label col-label no-padding-right no-padding-top" for="name">车牌号</label>
                                                        <div class="col-sm-6">
                                                            <div class="clearfix">
                                                                <input id="plateNumber" name="plateNumber" type="text" class="form-control input-sm"  style="width:220px;" placeholder="" aria-controls="dynamic-table">

                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="form-group" >
                                                        <label class="control-label col-label no-padding-right no-padding-top" for="name">司机姓名</label>
                                                        <div class="col-sm-6">
                                                            <div class="clearfix" style="height:34px;">
                                                                <input id="driverName" name="driverName" type="text" class="form-control input-sm" style="width:220px;" placeholder="" aria-controls="dynamic-table"><br/>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="form-group" >
                                                        <label class="control-label col-label no-padding-right no-padding-top" for="name">联系电话</label>
                                                        <div class="col-sm-6">
                                                            <div class="clearfix">
                                                                <input id="contactNumber" name="contactNumber" type="text" class="form-control input-sm" style="width:220px;" placeholder="" aria-controls="dynamic-table">
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <br/>
                                            </div>

                                            <div id="supportMessageShowDiv" class="" style="display: none">
                                                <div class="page-header">
                                                    <h4 class="w-font">供应商信息</h4>
                                                </div>
                                                <span style="cursor:pointer" id="supportListDivBlock"><button type="button" class="btn btn-white btn-info btn-bold btn-interval" id="bootbox-confirm">选择</button></span>




                                                <div class="form-group" >
                                                    <label class="control-label col-label no-padding-right no-padding-top" for="name">名称</label>
                                                    <div class="col-sm-6">
                                                        <div class="clearfix">
                                                            <input id="supportCode" name="supportCode" type="hidden">
                                                            <input id="supportName"  name="supportName" type="text" class="form-control input-sm" style="width:220px;" placeholder="" aria-controls="dynamic-table" >

                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="form-group" >
                                                    <label class="control-label col-label no-padding-right no-padding-top" for="name">联系人</label>
                                                    <div class="col-sm-6">
                                                        <div class="clearfix">
                                                            <input id="supportContactName" name="supportContactName" type="text" style="width:220px;"  class="form-control input-sm" placeholder="" aria-controls="dynamic-table" >
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="form-group" >
                                                    <label class="control-label col-label no-padding-right no-padding-top" for="name">联系电话</label>
                                                    <div class="col-sm-6">
                                                        <div class="clearfix">
                                                            <input id="supportPhone" name="supportPhone" type="text" style="width:220px;"  class="form-control input-sm" placeholder="" aria-controls="dynamic-table" >

                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="form-group" >
                                                    <label class="control-label col-label no-padding-right no-padding-top" for="name">传真</label>
                                                    <div class="col-sm-6">
                                                        <div class="clearfix">
                                                            <input id="supportFax" name="supportFax" type="text" style="width:220px;"  class="form-control input-sm" placeholder="" aria-controls="dynamic-table" >

                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="form-group" >
                                                    <label class="control-label col-label no-padding-right no-padding-top" for="name">Email</label>
                                                    <div class="col-sm-6">
                                                        <div class="clearfix">
                                                            <input id="supportEmail" name="supportEmail" type="text"  style="width:220px;" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" >
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="form-group" >
                                                    <label class="control-label col-label no-padding-right no-padding-top" for="name">邮编</label>
                                                    <div class="col-sm-6">
                                                        <div class="clearfix">
                                                            <input id="supportPostCode" name="supportPostCode" type="text" style="width:220px;"  class="form-control input-sm" placeholder="" aria-controls="dynamic-table" >
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="form-group" >
                                                    <label class="control-label col-label no-padding-right no-padding-top" for="name">地址</label>
                                                    <div class="col-sm-6">
                                                        <div class="clearfix">
                                                            <div class="docs-methods">
                                                                <form class="form-inline">
                                                                    <div id="distpicker">
                                                                        <div class="form-group">
                                                                            <div style="position: relative;margin-left:15px;">
                                                                                <input id="city-picker3-support" class="form-control" style="width:220px;margin-left:15px;"  readonly type="text" value="" data-toggle="city-picker">
                                                                                <input type="hidden" id="city-picker-hidden"/>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </form>
                                                            </div>

                                                            <input id="supportAddress" name="supportAddress" style="width:510px;"  type="text" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" >
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
                        <!-- PAGE CONTENT ENDS -->
                    </div><!-- /.col -->
                </div><!-- /.row -->
            </div><!-- /.page-content -->
            <div class="page-content">
                <div class="row">
                    <div class="col-xs-12">

                        <button class="btn btn-white btn-info btn-bold btn-interval" id="orderPlaceConTableBtn">
                            <i class="ace-icon fa fa-floppy-o bigger-120 blue" ></i>
                            确认下单
                        </button>
                    </div>

                </div>
            </div>
<#--
        </div>
    </div><!-- /.main-content &ndash;&gt;


</div><!-- /.main-container &ndash;&gt;-->

<link href= "../css/bootstrap.min.css" rel="stylesheet" type="text/css" />
<link href="../css/city-picker.css" rel="stylesheet" type="text/css" />
<script src="../js/city-picker.data.js"></script><#--111-->
<script src="../js/city-picker.js"></script><#--111-->



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
        validateForm();
    }
    /**
     *表单验证
     */
    var mistake="<i class='fa fa-times-circle w-error-icon bigger-130'></i>";
    function validateForm() {
        var ofc_url = $("#ofc_url").html();
        $('#orderFundamentalFormValidate').validate({
            errorElement : 'div',
            errorClass : 'help-block',
            focusInvalid : false,
            ignore : "",
            rules : {
                orderTime:{
                    required:true
                },
                custOrderCode:{

                    maxlength:30,
                    remote:{
                        url : ofc_url + "/ofc/checkCustOrderCode",
                        type : "POST",
                        dataType : "json",
                        data : {
                            custOrderCode : function() {
                                return $("#custOrderCode").val();
                            }
                        }
                    }
                },
                notes:{
                    maxlength:300
                }/*/!*,
                goodsListQuantity:{
                    numberFormat:true,
                    maxlength:19

                *!/}*//*,
                goodsListProductionBatch:{
                    maxlength:100
                }*/
            },
            messages : {
                orderTime:{
                    required:true
                },
                custOrderCode:{

                    maxlength:mistake+ "超过最大长度",
                    remote: mistake+ "该客户订单编号已经存在"
                },
                notes:{
                    maxlength:mistake+ "超过最大长度"
                }/*,
                goodsListQuantity:{
                    numberFormat:"请输入正确格式的货品数量",
                    maxlength:"超过最大长度"

                }*//*,
                goodsListProductionBatch:{
                    maxlength:"超过最大长度"
                }*/
            },
            highlight : function(e) {
                $(e).parent().parent().parent().removeClass('has-info').addClass('has-error');
            },
            success : function(e) {
                $(e).parent().parent().removeClass('has-error').addClass('has-success');
                $(e).remove();
            },
            errorPlacement : function(error, element) {
                error.insertAfter(element.parent());
            },
            invalidHandler : function(form) {
            }
        });
        $('#orderInfoTableValidate').validate({//
            errorElement : 'div',
            errorClass : 'help-block',
            focusInvalid : false,
            ignore : "",
            rules : {
                quantity:{
                    maxlength: 5,
                    integer:true
                },
                weight:{
                    maxlength: 5
                },
                cubage:{
                    maxlength:14
                },
                totalStandardBox:{
                    maxlength: 8,
                    integer:true
                },
                transRequire:{
                    maxlength:300
                },
                consignorName:{
                    required:true,
                    maxlength:100
                },
                consignorContactName:{
                    required:true,
                    maxlength:50
                },
                consignorPhone:{
                    isPhone:true,
                    required:true,
                    maxlength:50
                },
                consignorFax:{
                    isFax:true,
                    maxlength:50
                },
                consignorEmail:{
                    isEmail:true,
                    maxlength:50
                },
                consignorPostCode:{
                    isPostCode:true,
                    maxlength:6
                },
                consignorAddress:{
                    maxlength:200
                },
                consigneeName:{
                    required:true,
                    maxlength:100
                },
                consigneeContactName:{
                    required:true,
                    maxlength:50
                },
                consigneePhone:{
                    isPhone:true,
                    required:true,
                    maxlength:50
                },
                consigneeFax:{
                    isFax:true,
                    maxlength:50
                },
                consigneeEmail:{
                    isEmail:true,
                    maxlength:50
                },
                consigneePostCode:{
                    isPostCode:true,
                    maxlength:6
                },
                consigneeAddress:{
                    maxlength:200
                },
                warehouseName:{
                    required:true
                },
                arriveTime:{
                    required:true
                },
                plateNumber:{
                    maxlength:20
                },
                driverName:{
                    maxlength:20
                },
                contactNumber:{
                    isPhone:true,
                    maxlength:50
                },
                supportName:{
                    required:true,
                    maxlength:100
                },
                supportContactName:{
                    required:true,
                    maxlength:50
                },
                supportPhone:{
                    required:true,
                    isPhone:true
                },
                supportFax:{
                    isFax:true,
                    maxlength:50
                },
                supportEmail:{
                    isEmail:true,
                    maxlength:50
                },
                supportPostCode:{
                    isPostCode:true,
                    maxlength:6
                },
                supportAddress:{
                    maxlength:200
                }
            },
            messages : {
                quantity:{
                    maxlength: mistake+"超过最大长度",
                    integer: mistake+"必须输入整数"
                },
                weight:{
                    maxlength: mistake+ "超过最大长度"
                },
                cubage:{
                    maxlength: mistake+"超过最大长度"
                },
                totalStandardBox:{
                    maxlength:  mistake+"超过最大长度",
                    integer: mistake+"必须输入整数"
                },
                transRequire:{
                    maxlength: mistake+"超过最大长度"
                },
                consignorName:{
                    required: mistake+"必须输入",
                    maxlength: mistake+"超过最大长度"
                },
                consignorContactName:{
                    required: mistake+"必须输入",
                    maxlength: mistake+"超过最大长度"
                },
                consignorPhone:{
                    isPhone: mistake+"请输入正确的手机号",
                    required: mistake+"必须输入",
                    maxlength: mistake+"超过最大长度"
                },
                consignorFax:{
                    isFax: mistake+"请输入正确的传真",
                    maxlength: mistake+"超过最大长度"
                },
                consignorEmail:{
                    isEmail: mistake+"请输入正确格式的邮箱",
                    maxlength: mistake+"超过最大长度"
                },
                consignorPostCode:{
                    isPostCode: mistake+"请输入正确格式的邮编",
                    maxlength: mistake+"超过最大长度"
                },
                consignorAddress:{
                    maxlength: mistake+"超过最大长度"
                },
                consigneeName:{
                    required: mistake+"必须输入",
                    maxlength: mistake+"超过最大长度"
                },
                consigneeContactName:{
                    required: mistake+"必须输入",
                    maxlength: mistake+"超过最大长度"
                },
                consigneePhone:{
                    isPhone: mistake+"请输入正确格式的手机号",
                    required: mistake+"必须输入",
                    maxlength: mistake+"超过最大长度"
                },
                consigneeFax:{
                    isFax: mistake+"请输入正确格式的传真",
                    maxlength: mistake+"超过最大长度"
                },
                consigneeEmail:{
                    isEmail: mistake+"请输入正确格式的邮箱",
                    maxlength: mistake+"超过最大长度"
                },
                consigneePostCode:{
                    isPostCode: mistake+"请输入正确格式的邮编",
                    maxlength: mistake+"超过最大长度"
                },
                consigneeAddress:{
                    maxlength: mistake+"超过最大长度"
                },
                warehouseName:{
                    required: mistake+"必须输入"
                },
                arriveTime:{
                    required: mistake+"必须输入"
                },
                plateNumber:{
                    maxlength: mistake+"超过最大长度"
                },
                driverName:{
                    maxlength: mistake+"超过最大长度"
                },
                contactNumber:{
                    isPhone: mistake+"请输入正确格式的手机号",
                    maxlength: mistake+"超过最大长度"
                },
                supportName:{
                    required: mistake+"必须输入",
                    maxlength: mistake+"超过最大长度"
                },
                supportContactName:{
                    required: mistake+"必须输入",
                    maxlength: mistake+"超过最大长度"
                },
                supportPhone:{
                    required: mistake+"必须输入",
                    isPhone: mistake+"请输入正确的手机号"
                },
                supportFax:{
                    isFax: mistake+"请输入正确格式的传真",
                    maxlength: mistake+"超过最大长度"
                },
                supportEmail:{
                    isEmail: mistake+"请输入正确格式的邮箱",
                    maxlength: mistake+"超过最大长度"
                },
                supportPostCode:{
                    isPostCode: mistake+"请输入正确格式的邮编",
                    maxlength: mistake+"超过最大长度"
                },
                supportAddress:{
                    maxlength: mistake+"超过最大长度"
                }



            },
            highlight : function(e) {
                $(e).parent().parent().parent().removeClass('has-info').addClass('has-error');
            },
            success : function(e) {
                $(e).parent().parent().removeClass('has-error').addClass('has-success');
                $(e).remove();
            },
            errorPlacement : function(error, element) {
                error.insertAfter(element.parent());
            },
            invalidHandler : function(form) {
            }
        });
    }



</script>
<!-- inline scripts related to this page -->
<script type="text/javascript">
    function deleteGood(obj) {
        $(obj).parent().parent().remove();
    }
    function orderPlaceAddTranInfo(jsonStr) {
        //运输基本信息
        jsonStr.quantity = $("#quantity").val();
        jsonStr.weight = $("#weight").val();
        jsonStr.cubage = $("#cubage").val();
        jsonStr.totalStandardBox = $("#totalStandardBox").val();
        /*jsonStr.departurePlace = $("#consignorAddress").val();
        jsonStr.destination = $("#consigneeAddress").val();*/
        jsonStr.pickupTime = $dp.$('pickupTime').value;
        jsonStr.expectedArrivedTime = $dp.$('expectedArrivedTime').value;
        jsonStr.urgent = $("#urgentHel").val();
        jsonStr.transRequire = $("#transRequire").val();


        jsonStr.consignorCode = $("#consignorCode").val();
        jsonStr.consignorType = $("#consignorType").val();
        jsonStr.consignorName = $("#consignorName").val();
        jsonStr.consigneeCode = $("#consigneeCode").val();
        jsonStr.consigneeType = $("#consigneeType").val();
        jsonStr.consigneeName = $("#consigneeName").val();
        jsonStr.consignorContactCode = $("#consignorContactCode").val();
        jsonStr.consignorContactName = $("#consignorContactName").val();
        jsonStr.consignorContactPhone = $("#consignorPhone").val();
        jsonStr.consigneeContactCode = $("#consigneeContactCode").val();
        jsonStr.consigneeContactName = $("#consigneeContactName").val();
        jsonStr.consigneeContactPhone = $("#consigneePhone").val();


        var consignorAddressMessage = $("#city-picker3-consignor").val().split('~');
        var consignorAddressCodeMessage = consignorAddressMessage[1].split(',');
        var consignorAddressNameMessage = consignorAddressMessage[0].split('/');

        var consigneeAddressMessage = $("#city-picker3-consignee").val().split('~');
        var consigneeAddressCodeMessage = consigneeAddressMessage[1].split(',');
        var consigneeAddressNameMessage = consigneeAddressMessage[0].split('/');




        jsonStr.departureProvince = consignorAddressNameMessage[0];
        jsonStr.departureCity = consignorAddressNameMessage[1];
        jsonStr.departureDistrict = consignorAddressNameMessage[2];
        if(!StringUtil.isEmpty(consignorAddressNameMessage[3])){
            jsonStr.departureTowns = consignorAddressNameMessage[3];
        }

        jsonStr.departurePlaceCode = consignorAddressMessage[1];
        jsonStr.departurePlace = $("#consignorAddress").val();
        jsonStr.destinationProvince = consigneeAddressNameMessage[0];
        jsonStr.destinationCity = consigneeAddressNameMessage[1];
        jsonStr.destinationDistrict = consigneeAddressNameMessage[2];
        if(!StringUtil.isEmpty(consigneeAddressNameMessage[3])){
            jsonStr.destinationTowns = consigneeAddressNameMessage[3];
        }

        jsonStr.destinationCode=consigneeAddressMessage[1];
        jsonStr.destination=$("#consigneeAddress").val();




        return jsonStr;
    }
    function orderPlaceAddWareInfoWithoutSupport(jsonStr) {

        //仓配基本信息
        jsonStr.warehouseCode = $("#warehouseName").val();
        jsonStr.warehouseName = $("#warehouseName option:selected").text();//$("#storeCode option:selected").text();
        jsonStr.arriveTime = $dp.$('arriveTime').value;
        jsonStr.plateNumber = $("#plateNumber").val();
        jsonStr.driverName = $("#driverName").val();
        jsonStr.contactNumber = $("#contactNumber").val();
        return jsonStr;
    }
    //    var resultConsignorCode = "";
    //    var resultConsignorContactCode = "";
    //    var resultConsignorType = "";
    function getCscContantAndCompanyDtoConsignorStr() {
        var paramConsignor = {};
        var paramConsignee = {};
        var cscContact = {};
        var cscContactCompany = {};
       cscContactCompany.contactCompanyName = $("#consignorName").val();
        cscContact.contactName = $("#consignorContactName").val();
        cscContact.purpose = "2";
        cscContact.phone = $("#consignorPhone").val();

        cscContact.contactCompanyName = $("#consignorName").val();
        cscContact.contactCode = $("#consignorContactCode").val();
        cscContactCompany.type = $("#consignorType").val();
        //cscContactCompany.id = $("#consignorCode").val();

        cscContact.fax = $("#consignorFax").val();
        cscContact.email = $("#consignorEmail").val();
        cscContact.postCode = $("#consignorPostCode").val();

        var consignorAddressMessage = $("#city-picker3-consignor").val().split('~');


        var consignorAddressCodeMessage = consignorAddressMessage[1].split(',');
        var consignorAddressNameMessage = consignorAddressMessage[0].split('/');


        cscContact.province = consignorAddressCodeMessage[0];
        cscContact.city = consignorAddressCodeMessage[1];
        cscContact.area = consignorAddressCodeMessage[2];
        if(!StringUtil.isEmpty(consignorAddressCodeMessage[3])){
            cscContact.street = consignorAddressCodeMessage[3];
        }

        cscContact.provinceName = consignorAddressNameMessage[0];
        cscContact.cityName = consignorAddressNameMessage[1];
        cscContact.areaName = consignorAddressNameMessage[2];
        if(!StringUtil.isEmpty(consignorAddressNameMessage[3])){
            cscContact.streetName = consignorAddressNameMessage[3];
        }

        cscContact.address = $("#consignorAddress").val();
        paramConsignor.cscContact = cscContact;
        paramConsignor.cscContactCompany = cscContactCompany;
        var cscContantAndCompanyDtoConsignorStr = JSON.stringify(paramConsignor);
        return cscContantAndCompanyDtoConsignorStr;

    }
    function getCscContantAndCompanyDtoConsigneeStr() {
        var paramConsignor = {};
        var paramConsignee = {};
        var cscContact = {};
        var cscContactCompany = {};
        cscContactCompany.contactCompanyName = $("#consigneeName").val();
        cscContact.contactName = $("#consigneeContactName").val();
        cscContact.purpose = "1";
        cscContact.phone = $("#consigneePhone").val();

        cscContact.contactCompanyName = $("#consigneeName").val();
        cscContact.contactCode = $("#consigneeContactCode").val();
        cscContactCompany.type = $("#consigneeType").val();
        cscContact.fax = $("#consigneeFax").val();
        cscContact.email = $("#consigneeEmail").val();
        cscContact.postCode = $("#consigneePostCode").val();

        var consigneeAddressMessage = $("#city-picker3-consignee").val().split('~');
        var consigneeAddressCodeMessage = consigneeAddressMessage[1].split(',');
        var consigneeAddressNameMessage = consigneeAddressMessage[0].split('/');
        cscContact.province = consigneeAddressCodeMessage[0];
        cscContact.city = consigneeAddressCodeMessage[1];
        cscContact.area = consigneeAddressCodeMessage[2];
        if(!StringUtil.isEmpty(consigneeAddressCodeMessage[3])){
            cscContact.street = consigneeAddressCodeMessage[3];
        }

        cscContact.provinceName = consigneeAddressNameMessage[0];
        cscContact.cityName = consigneeAddressNameMessage[1];
        cscContact.areaName = consigneeAddressNameMessage[2];
        if(!StringUtil.isEmpty(consigneeAddressNameMessage[3])){
            cscContact.streetName = consigneeAddressNameMessage[3];
        }

        cscContact.address = $("#consigneeAddress").val();


        paramConsignee.cscContact = cscContact;
        paramConsignee.cscContactCompany = cscContactCompany;
        var cscContantAndCompanyDtoConsigneeStr = JSON.stringify(paramConsignee);
        return cscContantAndCompanyDtoConsigneeStr;
    }
    function getCscSupplierInfoDtoStr(){
        var paramSupport = {};

        paramSupport.supplierName = $("#supportName").val();
        paramSupport.contactName = $("#supportContactName").val();
        paramSupport.contactPhone = $("#supportPhone").val();
        paramSupport.fax = $("#supportFax").val();
        paramSupport.email = $("#supportEmail").val();
        paramSupport.postCode = $("#supportPostCode").val();
        var supportAddressMessage = $("#city-picker3-support").val().split('~');
        var supportAddressCodeMessage = supportAddressMessage[1].split(',');
        var supportAddressNameMessage = supportAddressMessage[0].split('/');

        paramSupport.province = supportAddressCodeMessage[0];
        paramSupport.city = supportAddressCodeMessage[1];
        paramSupport.area = supportAddressCodeMessage[2];
        if(!StringUtil.isEmpty(supportAddressCodeMessage[3])){
            paramSupport.street = supportAddressCodeMessage[3];
        }

        paramSupport.provinceName = supportAddressNameMessage[0];
        paramSupport.cityName = supportAddressNameMessage[1];
        paramSupport.areaName = supportAddressNameMessage[2];
        if(!StringUtil.isEmpty(supportAddressNameMessage[3])){
            paramSupport.streetName = supportAddressNameMessage[3];
        }

        paramSupport.address = $("#supportAddress").val();

        var cscSupplierInfoDtoStr = JSON.stringify(paramSupport);
        return cscSupplierInfoDtoStr;
    }

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
            //订单类型
            var orderType = $("#orderTypeSel").val();
            //业务类型前两位
            var businessType = $("#businessType").val().substring(0,2);
            ///仓配订单是否需要运输
            var provideTrans = $("#provideTransportHel").val();


            //确认下单的按钮, 这里应该根据当前页面上的订单类型和业务类型进行判断, 应该提交哪几个表单
            //基本信息和货品的表单先提交
            /*$("#orderFundamentalFormValidate").submit;
            //如果是运输订单, 就再提交运输信息标签页
            if(orderType == '60'){//运输订单
                $("#orderDistributionFormValidate").submit;
            }
            //如果是仓配订单, 就再提交仓配信息标签页, 如果是入库单再提交供应商信息

            //如果是仓配需运输订单, 就再提交运输信息和仓配信息标签页, 如果是入库单再提交供应商信息
            if(orderType == '61' && businessType == '61'){//仓储出库订单
                $("#orderWarehouseBaseFormValidate").submit;
                if('1' == provideTrans){
                    $("#orderDistributionFormValidate").submit;
                }
            }
            if(orderType == '61' && businessType == '62'){ //仓储入库订单,才需要供应商信息
                $("#orderWarehouseBaseFormValidate").submit;
                //仓配供应商基本信息
                $("#orderWarehouseSupportFormValidate").submit;
                if('1' == provideTrans){
                    $("#orderDistributionFormValidate").submit;
                }
            }*/



            var jsonStr = {};
            //订单基本信息
            jsonStr.orderTime = $('#orderTime').val();
            jsonStr.custOrderCode = $("#custOrderCode").val();
            jsonStr.orderType = $("#orderTypeSel").val();
            jsonStr.businessType = $("#businessType").val();
            jsonStr.provideTransport = $("#provideTransportHel").val();
            var storeMsg = $("#storeCode").val();
            if(!StringUtil.isEmpty(storeMsg)){
                var pageStoreMessage = $("#storeCode").val().split("/");
                jsonStr.storeCode = pageStoreMessage[0];
                jsonStr.storeName = $("#storeCode option:selected").text();
                jsonStr.platformType = pageStoreMessage[1];
            }else{
                alert('没有店铺信息!');
                return;
            }
            jsonStr.notes = $("#orderNotes").val();

            //货品添加

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
                        case 5 :orderGoods.unitPrice = param;break;
                        case 6 :orderGoods.quantity = goodsTable.rows[tableRows].cells[tableCells].getElementsByTagName("input")[0].value;break;
                        case 7 :orderGoods.productionBatch =  goodsTable.rows[tableRows].cells[tableCells].getElementsByTagName("input")[0].value;break;
                        case 8 :orderGoods.productionTime =  goodsTable.rows[tableRows].cells[tableCells].getElementsByTagName("input")[0].value;break;
                        case 9 :orderGoods.invalidTime =  goodsTable.rows[tableRows].cells[tableCells].getElementsByTagName("input")[0].value;break;
                        case 10 :orderGoods.weight = param;break;
                        case 11 :orderGoods.cubage = param;break;
                    }
                }
                orderGoodsList[tableRows - 1] = orderGoods;
            }

            //如果订单类型是运输订单,才拼装运单信息
            //如果订单类型是仓配订单而且是入库单,才拼装仓储信息和供应商信息,如果需要运输才需要拼装运输信息
            //别忘了在后台卡一下~!~~
            var cscContantAndCompanyDtoConsignorStr;
            var cscContantAndCompanyDtoConsigneeStr;
            var cscSupplierInfoDtoStr;
            if(orderType == '60'){//运输订单
                cscContantAndCompanyDtoConsignorStr = getCscContantAndCompanyDtoConsignorStr();
                cscContantAndCompanyDtoConsigneeStr = getCscContantAndCompanyDtoConsigneeStr();
                jsonStr = orderPlaceAddTranInfo(jsonStr);
            }

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
            var tag = "place";
            var ofcOrderDTO = JSON.stringify(jsonStr);
            var orderGoodsListStr = JSON.stringify(orderGoodsList);

            xescm.common.submit("/ofc/orderPlaceCon"
                    ,{"ofcOrderDTOStr":ofcOrderDTO
                        ,"orderGoodsListStr":orderGoodsListStr+"~`"
                        ,"cscContantAndCompanyDtoConsignorStr":cscContantAndCompanyDtoConsignorStr
                        ,"cscContantAndCompanyDtoConsigneeStr":cscContantAndCompanyDtoConsigneeStr
                        ,"cscSupplierInfoDtoStr":cscSupplierInfoDtoStr
                        ,"tag":tag}
                    ,"您确认提交订单吗?"
                    ,function () {
                        location.reload();
                        //xescm.common.goBack("/ofc/orderPlace");
                    })
        });
        $("#goodsSelectFormBtn").click(function () {
            CommonClient.post(sys.rootPath + "/ofc/goodsSelect", $("#goodsSelConditionForm").serialize(), function(data) {
                data=eval(data);

                var goodsList = "";
                $.each(data,function (index,cscGoodsVo) {
                    goodsList =goodsList + "<tr role='row' class='odd'>";
                    goodsList =goodsList + "<td class='center'> "+"<label class='pos-rel'>"+"<input type='checkbox' class='ace'>"+"<span class='lbl'></span>"+"</label>"+"</td>";
                    goodsList =goodsList + "<td>"+StringUtil.nullToEmpty(cscGoodsVo.goodsCode)+"</td>";
                    goodsList =goodsList + "<td>"+StringUtil.nullToEmpty(cscGoodsVo.goodsName)+"</td>";
                    goodsList =goodsList + "<td>"+StringUtil.nullToEmpty(cscGoodsVo.specification)+"</td>";
                    goodsList =goodsList + "<td>"+StringUtil.nullToEmpty(cscGoodsVo.unit)+"</td>";
                    goodsList =goodsList + "<td>"+StringUtil.nullToEmpty(cscGoodsVo.unitPrice)+"</td>";

                    goodsList =goodsList + "<td style='display:none'>"+StringUtil.nullToEmpty(cscGoodsVo.weight)+"</td>";
                    goodsList =goodsList + "<td style='display:none'>"+StringUtil.nullToEmpty(cscGoodsVo.volume)+"</td>";

                    goodsList =goodsList + "</tr>";
                });
                $("#goodsSelectListTbody").html(goodsList);
            },"json");
        });



        $("#consignorSelectFormBtn").click(function () {
            var cscContantAndCompanyDto = {};
            var cscContact = {};
            var cscContactCompany = {};

            cscContactCompany.contactCompanyName = $("#consignorName2").val();
            cscContact.purpose = "2";
            cscContact.contactName = $("#consignorPerson2").val();
            cscContact.phone = $("#consignorPhoneNumber2").val();
            cscContantAndCompanyDto.cscContact = cscContact;
            cscContantAndCompanyDto.cscContactCompany = cscContactCompany;
            var param = JSON.stringify(cscContantAndCompanyDto);
            CommonClient.post(sys.rootPath + "/ofc/contactSelect",{"cscContantAndCompanyDto":param}, function(data) {
                data=eval(data);
                var contactList = "";
                $.each(data,function (index,CscContantAndCompanyDto) {
                    /*consignorCodeHide = CscContantAndCompanyDto.contactCompanySerialNo;
                    consignorContactCodeHide = CscContantAndCompanyDto.contactSerialNo;
                    consignorTypeHide = CscContantAndCompanyDto.type;*/
                    contactList =contactList + "<tr role='row' class='odd'>";
                    contactList =contactList + "<td class='center'> "+"<label class='pos-rel'>"+"<input name='consignorSel' type='radio' class='ace'>"+"<span class='lbl'></span>"+"</label>"+"</td>";
                    contactList =contactList + "<td>"+(index+1)+"</td>";
                    contactList =contactList + "<td>"+StringUtil.nullToEmpty(CscContantAndCompanyDto.contactCompanyName)+"</td>";
                    contactList =contactList + "<td>"+StringUtil.nullToEmpty(CscContantAndCompanyDto.contactName)+"</td>";
                    contactList =contactList + "<td>"+StringUtil.nullToEmpty(CscContantAndCompanyDto.phone)+"</td>";
                    contactList =contactList + "<td>"+StringUtil.nullToEmpty(CscContantAndCompanyDto.fax)+"</td>";
                    contactList =contactList + "<td>"+StringUtil.nullToEmpty(CscContantAndCompanyDto.email)+"</td>";
                    contactList =contactList + "<td>"+StringUtil.nullToEmpty(CscContantAndCompanyDto.postCode)+"</td>";
                    contactList =contactList + "<td>"+StringUtil.nullToEmpty(CscContantAndCompanyDto.detailAddress)+"</td>";
                    contactList =contactList + "</tr>";
                });
                $("#contactSelectListTbody2").html(contactList);
            },"json");
        });

        $("#consigneeSelectFormBtn").click(function () {
            var cscContantAndCompanyDto = {};
            var cscContact = {};
            var cscContactCompany = {};
            cscContactCompany.contactCompanyName = $("#consignorName1").val();
            cscContact.purpose = "1";
            cscContact.contactName = $("#consignorPerson1").val();
            cscContact.phone = $("#consignorPhoneNumber1").val();
            cscContantAndCompanyDto.cscContact = cscContact;
            cscContantAndCompanyDto.cscContactCompany = cscContactCompany;
            var param = JSON.stringify(cscContantAndCompanyDto);
            CommonClient.post(sys.rootPath + "/ofc/contactSelect", {"cscContantAndCompanyDto":param}, function(data) {
                data=eval(data);
                var contactList = "";
                $.each(data,function (index,CscContantAndCompanyDto) {
                    /*consigneeCodeHide = CscContantAndCompanyDto.contactCompanySerialNo;
                    consigneeContactCodeHide = CscContantAndCompanyDto.contactSerialNo;
                    consigneeTypeHide = CscContantAndCompanyDto.type;*/
                    contactList =contactList + "<tr role='row' class='odd'>";
                    contactList =contactList + "<td class='center'> "+"<label class='pos-rel'>"+"<input name='consigneeSel' type='radio' class='ace'>"+"<span class='lbl'></span>"+"</label>"+"</td>";
                    contactList =contactList + "<td>"+(index+1)+"</td>";
                    contactList =contactList + "<td>"+StringUtil.nullToEmpty(CscContantAndCompanyDto.contactCompanyName)+"</td>";
                    contactList =contactList + "<td>"+StringUtil.nullToEmpty(CscContantAndCompanyDto.contactName)+"</td>";
                    contactList =contactList + "<td>"+StringUtil.nullToEmpty(CscContantAndCompanyDto.phone)+"</td>";
                    contactList =contactList + "<td>"+StringUtil.nullToEmpty(CscContantAndCompanyDto.fax)+"</td>";
                    contactList =contactList + "<td>"+StringUtil.nullToEmpty(CscContantAndCompanyDto.email)+"</td>";
                    contactList =contactList + "<td>"+StringUtil.nullToEmpty(CscContantAndCompanyDto.postCode)+"</td>";
                    contactList =contactList + "<td>"+StringUtil.nullToEmpty(CscContantAndCompanyDto.detailAddress)+"</td>";
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
                    supplierList =supplierList + "<tr role='row' class='odd'>";
                    supplierList =supplierList + "<td class='center'> "+"<label class='pos-rel'>"+"<input name='suppliersele' type='radio' class='ace'>"+"<span class='lbl'></span>"+"</label>"+"</td>";
                    supplierList =supplierList + "<td>"+(index+1)+"</td>";
                    supplierList =supplierList + "<td>"+StringUtil.nullToEmpty(CscSupplierInfoDto.supplierName)+"</td>";
                    supplierList =supplierList + "<td>"+StringUtil.nullToEmpty(CscSupplierInfoDto.contactName)+"</td>";
                    supplierList =supplierList + "<td>"+StringUtil.nullToEmpty(CscSupplierInfoDto.contactPhone)+"</td>";
                    supplierList =supplierList + "<td>"+StringUtil.nullToEmpty(CscSupplierInfoDto.fax)+"</td>";
                    supplierList =supplierList + "<td>"+StringUtil.nullToEmpty(CscSupplierInfoDto.email)+"</td>";
                    supplierList =supplierList + "<td>"+StringUtil.nullToEmpty(CscSupplierInfoDto.postCode)+"</td>";
                    supplierList =supplierList + "<td>"+StringUtil.nullToEmpty(CscSupplierInfoDto.completeAddress)+"</td>";
                    supplierList =supplierList + "</tr>";
                    $("#supplierSelectListTbody").html(supplierList);
                });
            },"json");
        });

        $("#goodcheck").change(function () {
            if($("#goodcheck").prop("checked")){
                $("#goodsSelectListTbody").find("tr").each(function(index){
                    $(this).children().eq(0).find("input").prop('checked',true);
                });
            }else{
                $("#goodsSelectListTbody").find("tr").each(function(index){
                    $(this).children().eq(0).find("input").prop('checked',false);
                });
            }
        });

        $("#goodsEnter").click(function () {
            var goodsInfoListDiv = "";
            $("#goodsInfoListDiv").find("tr").each(function(index){
                var tdArr = $(this).children();
                var goodsCode = tdArr.eq(1).text();//货品编码
                var goodsName = tdArr.eq(2).text();//货品名称
                var specification = tdArr.eq(3).text();//    货品规格
                var unit = tdArr.eq(4).text();//    单位
                var unitPrice = tdArr.eq(5).text();//    单价
                var quantity = tdArr.eq(6).children().val();//    数量
                var production_batch = tdArr.eq(7).children().val();//    批次
                var production_time = tdArr.eq(8).children().val();//    生产日期
                var invalid_time = tdArr.eq(9).children().val();//    失效日期
                var weight = tdArr.eq(10).children().val();//    重量
                var volume = tdArr.eq(11).children().val();//    体积

                goodsInfoListDiv =goodsInfoListDiv + "<tr role='row' class='odd' align='center'>";
                goodsInfoListDiv =goodsInfoListDiv + "<td><button type='button' onclick='deleteGood(this)' class='btn btn-minier btn-danger'>删除</button></td>";
                /* goodsInfoListDiv =goodsInfoListDiv + "<td><input id='deleteOrNot' type='checkbox'/></td>";*/
                goodsInfoListDiv =goodsInfoListDiv + "<td>"+goodsCode+"</td>";
                goodsInfoListDiv =goodsInfoListDiv + "<td>"+goodsName+"</td>";
                goodsInfoListDiv =goodsInfoListDiv + "<td>"+specification+"</td>";
                goodsInfoListDiv =goodsInfoListDiv + "<td>"+unit+"</td>";
                goodsInfoListDiv =goodsInfoListDiv + "<td>"+unitPrice+"</td>";
                goodsInfoListDiv =goodsInfoListDiv + "<td><input name='' type='text' class='form-control input-sm' placeholder='' aria-controls='dynamic-table' value='"+quantity+"'></td>";
                goodsInfoListDiv =goodsInfoListDiv + "<td><input name='' type='text' class='form-control input-sm' placeholder='' aria-controls='dynamic-table' value='"+production_batch+"'></td>";
                goodsInfoListDiv =goodsInfoListDiv + "<td><input name='' type='text' class='form-control input-sm' placeholder='' aria-controls='dynamic-table' value='"+production_time+"' onclick='laydate({istime: true, format: \"YYYY-MM-DD\",isclear: true,istoday: true})'></td>";
                goodsInfoListDiv =goodsInfoListDiv + "<td><input name='' type='text' class='form-control input-sm' placeholder='' aria-controls='dynamic-table' value='"+invalid_time+"' onclick='laydate({istime: true, format: \"YYYY-MM-DD\",isclear: true,istoday: true})'></td>";
                goodsInfoListDiv =goodsInfoListDiv + "<td style='display:none'>"+weight+"</td>";
                goodsInfoListDiv =goodsInfoListDiv + "<td style='display:none'>"+volume+"</td>";
                goodsInfoListDiv =goodsInfoListDiv + "</tr>";
            });
            $("#goodsInfoListDiv").html("");
            var str = "";
            $("#goodsSelectListTbody").find("tr").each(function(index){
                var tdArr = $(this).children();
                if(tdArr.eq(0).find("input").prop("checked")){
                    var goodsCode = tdArr.eq(1).text();//货品编码
                    var goodsName = tdArr.eq(2).text();//货品名称
                    var specification = tdArr.eq(3).text();//    货品规格
                    var unit = tdArr.eq(4).text();//    单位
                    var unitPrice = tdArr.eq(5).text();//    单价
                    var weight = tdArr.eq(6).text();//    重量
                    var volume = tdArr.eq(7).text();//    体积
                    goodsInfoListDiv =goodsInfoListDiv + "<tr role='row' class='odd' align='center'>";
                    goodsInfoListDiv =goodsInfoListDiv + "<td><button type='button' onclick='deleteGood(this)' class='btn btn-minier btn-danger'>删除</button></td>";
                    /* goodsInfoListDiv =goodsInfoListDiv + "<td><input id='deleteOrNot' type='checkbox'/></td>";*/
                    goodsInfoListDiv =goodsInfoListDiv + "<td>"+goodsCode+"</td>";
                    goodsInfoListDiv =goodsInfoListDiv + "<td>"+goodsName+"</td>";
                    goodsInfoListDiv =goodsInfoListDiv + "<td>"+specification+"</td>";
                    goodsInfoListDiv =goodsInfoListDiv + "<td>"+unit+"</td>";
                    goodsInfoListDiv =goodsInfoListDiv + "<td>"+unitPrice+"</td>";
                    goodsInfoListDiv =goodsInfoListDiv + "<td><input  id='goodsListQuantity' name = 'goodsListQuantity' type='text' class='form-control input-sm' placeholder='' aria-controls='dynamic-table' ></td>";
                    goodsInfoListDiv =goodsInfoListDiv + "<td><input  id='goodsListProductionBatch' name = 'goodsListProductionBatch' type='text' class='form-control input-sm' placeholder='' aria-controls='dynamic-table' ></td>";/*WdatePicker({isShowClear:true,readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})*/
                    goodsInfoListDiv =goodsInfoListDiv + "<td><input name='' type='text' class='form-control input-sm' placeholder='' aria-controls='dynamic-table' onclick='laydate({istime: true, format: \"YYYY-MM-DD\",isclear: true,istoday: true})'></td>";
                    goodsInfoListDiv =goodsInfoListDiv + "<td><input name='' type='text' class='form-control input-sm' placeholder='' aria-controls='dynamic-table' onclick='laydate({istime: true, format: \"YYYY-MM-DD\",isclear: true,istoday: true})'></td>";
                    goodsInfoListDiv =goodsInfoListDiv + "<td style='display:none'>"+weight+"</td>";
                    goodsInfoListDiv =goodsInfoListDiv + "<td style='display:none'>"+volume+"</td>";
                    goodsInfoListDiv =goodsInfoListDiv + "</tr>";
                    str="str";
                }
            });
            if(goodsInfoListDiv==""){
                alert("请至少选择一行");
            }else{
                $("#goodsInfoListDiv").html(goodsInfoListDiv);
                $("#goodsListDiv").fadeOut("slow");
            }
            validateForm();
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
                    var address = tdArr.eq(8).text();//    地址
                    var code = tdArr.eq(7).text();//    邮编

                    $("#consignorName").val(consignorName);
                    $("#consignorContactName").val(contacts);
                    $("#consignorPhone").val(contactsNumber);
                    $("#consignorFax").val(fax);
                    $("#consignorEmail").val(email);
                    $("#consignorPostCode").val(code);
                    $("#consignorAddress").val(address);

                    //在这里将所有的信息补全!!
                    var paramConsignor = {};
                    var paramConsignee = {};
                    var cscContact = {};
                    var cscContactCompany = {};
                    cscContactCompany.contactCompanyName = consignorName;
                    cscContact.contactName = contacts;
                    cscContact.purpose = "2";
                    cscContact.phone = contactsNumber;
                    var cscContantAndCompanyDto = {};
                    cscContantAndCompanyDto.cscContact = cscContact;
                    cscContantAndCompanyDto.cscContactCompany = cscContactCompany;
                    var param = JSON.stringify(cscContantAndCompanyDto);


                    CommonClient.syncpost(sys.rootPath + "/ofc/contactSelect",{"cscContantAndCompanyDto":param},function (data) {

                        data = eval(data);
                        $.each(data,function (index,CscContantAndCompanyDto) {
                            $("#consignorCode").val(CscContantAndCompanyDto.contactCompanySerialNo);
                            $("#consignorContactCode").val(CscContantAndCompanyDto.contactSerialNo);
                            $("#consignorType").val(CscContantAndCompanyDto.type);
                            $("#consignorAddress").val(CscContantAndCompanyDto.address);
                            var provinceName = CscContantAndCompanyDto.provinceName;
                            var cityName = CscContantAndCompanyDto.cityName;
                            var areaName = CscContantAndCompanyDto.areaName;
                            var streetName = CscContantAndCompanyDto.streetName;
                            var paramAddressNameToPage = provinceName
                                    + "/" + cityName
                                    + "/" + areaName
                                    + "/" + streetName;
                            $("#city-picker3-consignor").val(paramAddressNameToPage);
                            $("#city-picker3-consignor").citypicker('refresh');
                        });
                    });

                }
            });
            if(consignorin==""){
                alert("请至少选择一行");
            }else{
                $("#consignorListDiv").fadeOut("slow");
            }
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


                    var paramConsignor = {};
                    var paramConsignee = {};
                    var cscContact = {};
                    var cscContactCompany = {};
                    cscContactCompany.contactCompanyName = $("#consigneeName").val();
                    cscContact.contactName = $("#consigneeContactName").val();
                    cscContact.purpose = "1";
                    cscContact.phone = $("#consigneePhone").val();
                    var cscContantAndCompanyDto = {};
                    cscContantAndCompanyDto.cscContact = cscContact;
                    cscContantAndCompanyDto.cscContactCompany = cscContactCompany;
                    var param = JSON.stringify(cscContantAndCompanyDto);
                    CommonClient.syncpost(sys.rootPath + "/ofc/contactSelect",{"cscContantAndCompanyDto":param},function (data) {
                        data = eval(data);
                        $.each(data,function (index,CscContantAndCompanyDto) {
                            $("#consigneeCode").val(CscContantAndCompanyDto.contactCompanySerialNo);
                            $("#consigneeContactCode").val(CscContantAndCompanyDto.contactSerialNo);
                            $("#consigneeType").val(CscContantAndCompanyDto.type);
                            $("#consigneeAddress").val(CscContantAndCompanyDto.address);
                            var provinceName = CscContantAndCompanyDto.provinceName;
                            var cityName = CscContantAndCompanyDto.cityName;
                            var areaName = CscContantAndCompanyDto.areaName;
                            var streetName = CscContantAndCompanyDto.streetName;
                            var paramAddressNameToPage = provinceName
                                    + "/" + cityName
                                    + "/" + areaName
                                    + "/" + streetName;
                            $("#city-picker3-consignee").val(paramAddressNameToPage);
                            $("#city-picker3-consignee").citypicker('refresh');

                        });
                    });
                }
            });
            if(consignorout==""){
                alert("请至少选择一行");
            }else{
                $("#consigneeListDiv").fadeOut("slow");//淡入淡出效果 隐藏div
            }
        });

        $("#supplierEnter").click(function () {//
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
                   /* $("#supportAddress").val(address);*/
                    CommonClient.post(sys.rootPath + "/ofc/supplierSelect"
                            ,"supplierName="+$("#supportName").val()
                            +"&contactName="+$("#supportContactName").val()
                            +"&contactPhone"+$("#supportPhone").val()
                            , function(data) {

                        data=eval(data);
                        var supplierList = "";
                        $.each(data,function (index,CscSupplierInfoDto) {
                            $("#supportAddress").val(CscSupplierInfoDto.address);
                            var provinceName = CscSupplierInfoDto.provinceName;
                            var cityName = CscSupplierInfoDto.cityName;
                            var areaName = CscSupplierInfoDto.areaName;
                            var streetName = CscSupplierInfoDto.streetName;
                            var paramAddressNameToPage = provinceName
                                    + "/" + cityName
                                    + "/" + areaName
                                    + "/" + streetName;
                            $("#city-picker3-support").val(paramAddressNameToPage);
                            $("#city-picker3-support").citypicker('refresh');
                        });
                    },"json");

                }
            });
            if(support==""){
                alert("请至少选择一行");
            }else{
                $("#supportListDiv").fadeOut("slow");//淡入淡出效果 隐藏div
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
            if($(this).children('option:selected').val() == '61'){//仓配订单
                //$("#transBusinessTypeDiv").hide():
                $("#provideTransportDiv").show();
                $("#businessTypeDiv").show();

                $('.storeLi').show();
                $('.transLi').hide();
                //$('#myTab4').children('li').first().addClass("active");
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
            if($(this).children('option:selected').val() == '60'){//运输订单
                //$("#transBusinessTypeDiv").show();
                $('.transLi').show();
                $('.storeLi').hide();
                $("#provideTransportDiv").hide();
                $("#businessTypeDiv").hide();
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
                $('.transLi').show();
            }else{
                $('.transLi').hide();
            }
        });




    })

</script>

</body>
