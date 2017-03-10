<head>
    <title>订单编辑</title>
    <style type="text/css">
        #goodsListDiv {
            position:fixed;
            left:50%;
            top:85px;
            margin-left:-400px;
            width:946px;
            height:500px;
            z-index:3;
            overflow: auto;
            border:solid #7A7A7A 1px;
        }
        #consignorListDiv {
            position:fixed;
            left:50%;
            top:77px;
            margin-left:-400px;
            width:946px;
            height:500px;
            z-index:3;
            overflow: auto;
            border:solid #7A7A7A 1px;
        }
        #consigneeListDiv {
            position:fixed;
            left:50%;
            top:77px;
            margin-left:-400px;
            width:946px;
            height:500px;
            z-index:3;
            overflow: auto;
            border:solid #7A7A7A 1px;
        }
        #supportListDiv {
            position:fixed;
            left:50%;
            top:77px;
            margin-left:-400px;
            width:946px;
            height:500px;
            z-index:3;
            overflow: auto;
            border:solid #7A7A7A 1px;
        }
        input{
            color: black;
        }
        #warehouseName_chosen{
            width:100%!important;
        }
        .table > tbody + tbody{
            border-top:0px;
        }

    </style>

</head>

<body class="no-skin">
<!--goodsListDiv-->
<div class="modal-content" id="goodsListDiv" style="display: none;">
    <div class="modal-header"><span id="goodsListDivNoneTop" style="cursor:pointer"><button type="button" id="" style="cursor:pointer" class="bootbox-close-button close" data-dismiss="modal" aria-hidden="true">×</button></span>
        <div class="w-font">货品列表</div></div>
    <div class="modal-body">
        <div class="bootbox-body">
            <div class="widget-main">
                <form id="goodsSelConditionForm"  class="form-horizontal" role="form">
                    <div class="form-group">
                        <label class="control-label col-label no-padding-right" for="name">货品编码</label>
                        <div class="col-sm-3">
                            <div class="clearfix">
                            <#--<input  id = "goodsCodeCondition" name="goodsCode" type="text" style="color: black" class="form-control input-sm" placeholder="" aria-controls="dynamic-table">-->
                                <input class="width-100" id="goodsCodeCondition" name="goodsCode" type="text">
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-label no-padding-right" for="name">货品名称</label>
                        <div class="col-sm-3">
                            <div class="clearfix">
                                <input class="width-100" id="goodsNameCondition" name="goodsName" type="text">
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-label no-padding-right" for="name"></label>
                        <div class="col-sm-3">
                            <div class="clearfix">
                                <span id="goodsSelectFormBtn" class="btn btn-white btn-info btn-bold btn-interval">筛选</span>
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
    <div class="modal-footer"><span id="goodsListDivNoneBottom" style="cursor:pointer"><button style="margin-right:10px;"  data-bb-handler="cancel" type="button" class="btn btn-default">取消</button></span><button id="goodsEnter" data-bb-handler="confirm" type="button" class="btn btn-primary">确定</button></div>
</div>
<!--consignorListDiv-->
<div class="modal-content" id="consignorListDiv" style="display: none;">
    <div class="modal-header"><span id="consignorListDivNoneTop" style="cursor:pointer"><button type="button" id="" style="cursor:pointer" class="bootbox-close-button close" data-dismiss="modal" aria-hidden="true">×</button></span>
        <div class="w-font">发货方联系人</div></div>
    <div class="modal-body">
        <div class="bootbox-body">
            <div class="widget-main">
                <form id="consignorSelConditionForm"  class="form-horizontal" role="form">
                    <div class="form-group">
                        <label class="control-label col-label no-padding-right" for="name">名称</label>
                        <div class="col-sm-3">
                            <div class="clearfix">
                                <input class="width-100" id="consignorName2" name="contactCompanyName" type="text">
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-label no-padding-right" for="name">联系人</label>
                        <div class="col-sm-3">
                            <div class="clearfix">
                                <input class="width-100" id="consignorPerson2" name="contactName" type="text">
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-label no-padding-right" for="name">联系电话</label>
                        <div class="col-sm-3">
                            <div class="clearfix">
                                <input class="width-100" id="consignorPhoneNumber2" name="phone" type="text">
                            <#--<input id="purpose2" name="purpose" type="hidden" value="2">-->
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-label no-padding-right" for="name"></label>
                        <div class="col-sm-3">
                            <div class="clearfix">
                                <span id="consignorSelectFormBtn" class="btn btn-white btn-info btn-bold btn-interval">筛选</span>
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
    <div class="modal-footer"><span id="consignorListDivNoneBottom" style="cursor:pointer"><button style="margin-right:10px;"  data-bb-handler="返回" type="button" class="btn btn-default">取消</button></span><button id="contactinEnter" data-bb-handler="confirm" type="button" class="btn btn-primary">确定</button></div>
</div>
<!--consigneeListDiv-->
<div class="modal-content" id="consigneeListDiv" style="display: none;">
    <div class="modal-header"><span id="consigneeListDivNoneTop" style="cursor:pointer"><button type="button" id="" style="cursor:pointer" class="bootbox-close-button close" data-dismiss="modal" aria-hidden="true">×</button></span>
        <div class="w-font">收货方联系人</div></div>
    <div class="modal-body">
        <div class="bootbox-body">
            <div class="widget-main">
                <form id="consigneeSelConditionForm"  class="form-horizontal" role="form">
                    <div class="form-group">
                        <label class="control-label col-label no-padding-right" for="name">名称</label>
                        <div class="col-sm-3">
                            <div class="clearfix">
                                <input class="width-100" id="consignorName1" name="contactCompanyName" type="text">
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-label no-padding-right" for="name">联系人</label>
                        <div class="col-sm-3">
                            <div class="clearfix">
                                <input class="width-100" id="consignorPerson1" name="contactName" type="text">
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-label no-padding-right" for="name">联系电话</label>
                        <div class="col-sm-3">
                            <div class="clearfix">
                                <input class="width-100" id="consignorPhoneNumber1" name="phone" type="text">
                                <input id="purpose1" name="purpose" type="hidden" value="1">
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-label no-padding-right" for="name"></label>
                        <div class="col-sm-3">
                            <div class="clearfix">
                                <span id="consigneeSelectFormBtn" class="btn btn-white btn-info btn-bold btn-interval">筛选</span>
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
        <div class="modal-footer"><span id="consigneeListDivNoneBottom" style="cursor:pointer"><button style="margin-right:10px;"  data-bb-handler="cancel" type="button" class="btn btn-default">取消</button></span><button id="contactoutEnter" data-bb-handler="confirm" type="button" class="btn btn-primary">确定</button></div>
    </div>
</div>

<!--supportListDiv-->
<div class="modal-content" id="supportListDiv" style="display: none;">
    <div class="modal-header">
        <span id="supportListDivNoneTop" style="cursor:pointer">
            <button type="button" id="" style="cursor:pointer" class="bootbox-close-button close" data-dismiss="modal" aria-hidden="true">×</button>
        </span>
        <h4 class="modal-title">供应商联系人</h4>
    </div>


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
                        <label class="control-label col-label no-padding-right" for="name">名称</label>
                        <div class="col-sm-3">
                            <div class="clearfix">
                                <input class="width-100" id="supplierName" name="supplierName" type="text">
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-label no-padding-right" for="name">联系人</label>
                        <div class="col-sm-3">
                            <div class="clearfix">
                                <input class="width-100" id="contactName" name="contactName" type="text">
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-label no-padding-right" for="name">联系电话</label>
                        <div class="col-sm-3">
                            <div class="clearfix">
                                <input class="width-100" id="contactPhone" name="contactPhone" type="text">
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-label no-padding-right" for="name"></label>
                        <div class="col-sm-3">
                            <div class="clearfix">
                                <button type="button" class="btn btn-white btn-info btn-bold btn-interval" id="supplierSelectFormBtn" >筛选</button>
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
    <div class="modal-footer"><span id="supportListDivNoneBottom" style="cursor:pointer"><button  style="margin-right:10px;" data-bb-handler="cancel" type="button" class="btn btn-default">取消</button></span><button id="supplierEnter" data-bb-handler="confirm" type="button" class="btn btn-primary">确定</button></div>
</div>

                <div class="page-header">
                    <p>
                        基本信息
                        <span hidden="true" id = "ofc_url">${(OFC_URL)!}</span>
                    </p>
                </div>
               <div class="page-content">
                    <div class="row">
                  <#--       <div class="col-xs-12">-->
                            <#--<div class="widget-box"  style="border: none">
                                <div class="widget-body">
                                    <div class="widget-main">-->
                                        <form name="orderFundamentalFormValidate" id="orderFundamentalFormValidate"  class="form-horizontal" role="form" >
                                            <div class="form-group">
                                                <label class="control-label col-label no-padding-right" for="name">订单日期</label>
                                                <div class="w-width-220">
                                                    <div class="clearfix position-relative">
                                                    <input type="hidden" id="orderCode" name="orderCode" <#if orderInfo.orderCode?? >value="${orderInfo.orderCode}"</#if>">
                                                        <input id="orderTime" name="orderTime" <#if orderInfo.orderTime?? >value="${((orderInfo.orderTime)?string('yyyy-MM-dd HH:mm:ss'))!}"</#if> type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" onclick="laydate({istime: true, format: 'YYYY-MM-DD hh:mm:ss',isclear: true,istoday: true,min: laydate.now(-30),max: laydate.now()})">
                                                        <label for="orderTime" class="initBtn">
                                                            <i class="fa fa-calendar bigger-130"></i>
                                                        </label>
                                                    </div>
                                                </div>
                                                <label class="control-label col-label no-padding-right" for="name">客户订单编号</label>
                                                <div class="w-width-220">
                                                    <div class="clearfix">
                                                        <input id="selfCustOrderCode" name="selfCustOrderCode" type="hidden" value="${(orderInfo.custOrderCode)!}">
                                                        <input style="width:100%;" id="custOrderCode" name="custOrderCode" type="text" <#if orderInfo.custOrderCode?? >value="${(orderInfo.custOrderCode)!}"</#if>>
                                                    </div>
                                                </div>
                                            </div>
                                        <#-- <div class="form-group y-float">
                                             <label class="control-label col-label no-padding-right" for="name">订单日期</label>
                                             <div class="w-width-220">
                                                 <div class="clearfix">
                                                     <input id="orderTime" name="orderTime" <#if orderInfo.orderTime?? >value="${((orderInfo.orderTime)?string('yyyy-MM-dd HH:mm:ss'))!}"</#if> type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" onClick="WdatePicker({isShowClear:true,readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})">
                                                 </div>
                                             </div>
                                         </div>-->
                                        <#-- <div class="form-group">
                                             <#-bel class="control-label col-label no-padding-right" for="name">客户订单编号</label>
                                             <div class="w-width-220">
                                                 <div class="clearfix">
                                                     <input id="selfCustOrderCode" name="selfCustOrderCode" type="hidden" value="${(orderInfo.custOrderCode)!}">
                                                     <input style="width:100%;" id="custOrderCode" name="custOrderCode" type="text" <#if orderInfo.custOrderCode?? >value="${(orderInfo.custOrderCode)!}"</#if>>
                                                 </div>
                                             </div>
                                            </div>-->
                                            <div class="form-group">
                                                <label class="control-label col-label no-padding-right" for="name">店铺</label>
                                                <div class="w-width-220">
                                                    <div class="clearfix">
                                                    <#--<input id="hiddenStoreCode" name="hiddenStoreCode" value="${(orderInfo.storeCode)!""}">-->
                                                        <select id="storeCode" name="storeCode"  class="width-100 chosen-select">
                                                        <#list cscStoreByCustId! as cscStore>
                                                            <option <#if cscStore.storeCode == orderInfo.storeCode >selected="selected"</#if> value="${(cscStore.storeCode)!""}/${(cscStore.platformType)!""}">${(cscStore.storeName)!""}</option>
                                                        </#list>
                                                        </select>
                                                    </div>
                                                </div>
                                                <label class="control-label col-label no-padding-right" for="name">订单类型</label>
                                                <div class="w-width-220">
                                                    <div class="clearfix">
                                                        <select id="orderType" name="orderType" value="${(orderInfo.orderType)!"60"}"  class="width-100 chosen-select">
                                                            <option value="60" <#if orderInfo.orderType?? ><#if ((orderInfo.orderType)! == '60')>selected="selected"</#if></#if>>运输订单</option>
                                                            <option value="61" <#if orderInfo.orderType?? ><#if ((orderInfo.orderType)! == '61')>selected="selected"</#if></#if>>仓配订单</option>
                                                        </select>
                                                    </div>
                                                </div>
                                            </div>
                                        <#--<div class="form-group y-float" style="clear:left;">
                                           <#--bel class="control-label col-label no-padding-right" for="name">订单类型</label>
                                            <div class="w-width-220">
                                                <div class="clearfix">
                                                    <select id="orderType" name="orderType" value="${(orderInfo.orderType)!"60"}"  class="width-100 chosen-select">
                                                        <option value="60" <#if orderInfo.orderType?? ><#if ((orderInfo.orderType)! == '60')>selected="selected"</#if></#if>>运输订单</option>
                                                        <option value="61" <#if orderInfo.orderType?? ><#if ((orderInfo.orderType)! == '61')>selected="selected"</#if></#if>>仓配订单</option>
                                                    </select>
                                                </div>
                                            </div>
                                            </div>-->
                                            <div class="form-group" id="businessTypeDiv" style="display: none">
                                                <label class="control-label col-label no-padding-right" for="name">业务类型</label>
                                                <div class="w-width-220">
                                                    <div class="clearfix">
                                                        <select id="businessType" name="businessType"  class="width-100 chosen-select">

                                                            <option value="610" <#if orderInfo.businessType?? ><#if ((orderInfo.businessType)! == '610')>selected="selected"</#if></#if>>销售出库</option>
                                                            <option value="611" <#if orderInfo.businessType?? ><#if ((orderInfo.businessType)! == '611')>selected="selected"</#if></#if>>调拨出库</option>
                                                            <option value="612" <#if orderInfo.businessType?? ><#if ((orderInfo.businessType)! == '612')>selected="selected"</#if></#if>>报损出库</option>
                                                            <option value="613" <#if orderInfo.businessType?? ><#if ((orderInfo.businessType)! == '613')>selected="selected"</#if></#if>>其他出库</option>
                                                            <option value="614" <#if orderInfo.businessType?? ><#if ((orderInfo.businessType)! == '614')>selected="selected"</#if></#if>>分拨出库</option>
                                                            <option value="----------">----------</option>
                                                            <option value="620" <#if orderInfo.businessType?? ><#if ((orderInfo.businessType)! == '620')>selected="selected"</#if></#if>>采购入库</option>
                                                            <option value="621" <#if orderInfo.businessType?? ><#if ((orderInfo.businessType)! == '621')>selected="selected"</#if></#if>>调拨入库</option>
                                                            <option value="622" <#if orderInfo.businessType?? ><#if ((orderInfo.businessType)! == '622')>selected="selected"</#if></#if>>退货入库</option>
                                                            <option value="623" <#if orderInfo.businessType?? ><#if ((orderInfo.businessType)! == '623')>selected="selected"</#if></#if>>加工入库</option>
                                                            <option value="624" <#if orderInfo.businessType?? ><#if ((orderInfo.businessType)! == '624')>selected="selected"</#if></#if>>盘盈入库</option>
                                                            <option value="625" <#if orderInfo.businessType?? ><#if ((orderInfo.businessType)! == '625')>selected="selected"</#if></#if>>流通入库</option>
                                                            <option value="626" <#if orderInfo.businessType?? ><#if ((orderInfo.businessType)! == '626')>selected="selected"</#if></#if>>其他入库</option>
                                                            <option value="627" <#if orderInfo.businessType?? ><#if ((orderInfo.businessType)! == '627')>selected="selected"</#if></#if>>分拨入库</option>
                                                        </select>
                                                    </div>
                                                </div>
                                                <label class="control-label col-label no-padding-right" for="name">是否需要运输</label>
                                                <div class="w-width-220">
                                                    <div class="clearfix">
                                                    <#-- <input id="provideTransport" type="checkbox" name = "" <#if orderInfo.provideTransport?? ><#if ((orderInfo.provideTransport) == 1)> checked="checked"</#if></#if>/>-->
                                                        <label><input id="provideTransport" class="ace" type="checkbox" name = "" <#if orderInfo.provideTransport?? ><#if ((orderInfo.provideTransport) == 1)> checked="checked"</#if></#if>/><span class="lbl"></span></label>
                                                        <input id="provideTransport1" type="hidden" name="provideTransport" value="${(orderInfo.provideTransport)!""}"/>
                                                    </div>
                                                </div>
                                            </div>
                                        <#--<div class="form-group y-float" id="provideTransportDiv" style="display: none">
                                           <#--bel class="control-label col-label no-padding-right" for="name">是否需要运输</label>
                                            <div class="w-width-220">
                                                <div class="clearfix">
                                                   &lt;#&ndash; <input id="provideTransport" type="checkbox" name = "" <#if orderInfo.provideTransport?? ><#if ((orderInfo.provideTransport) == 1)> checked="checked"</#if></#if>/>&ndash;&gt;
                                                    <label><input id="provideTransport" class="ace" type="checkbox" name = "" <#if orderInfo.provideTransport?? ><#if ((orderInfo.provideTransport) == 1)> checked="checked"</#if></#if>/><span class="lbl"></span></label>
                                                    <input id="provideTransport1" type="hidden" name="provideTransport" value="${(orderInfo.provideTransport)!""}"/>
                                                </div>
                                            </div>
                                            </div>-->

                                            <div class="form-group">
                                                <label class="control-label col-label no-padding-right" for="name">备注</label>
                                                <div class="w-width-220" style="width:541px;">
                                                    <div class="clearfix">
                                                        <input id="notes"  name="notes" value="${(orderInfo.notes)!""}" type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" />
                                                    </div>
                                                </div>
                                            </div>
                                        </form>
                                 <#--   </div>
                                </div>-->

                               <#-- <div class="widget-body">-->
                                  <#--  <div class="widget-main">-->
                                        <form name="orderInfoTableValidate" id="orderInfoTableValidate"  class="form-horizontal" role="form"  style="margin-top:20px;">
                                            <div class="col-sm-12">
                                                <!-- #section:elements.tab.option -->
                                                <div class="tabbable">
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

                                                            <span style="cursor:pointer" id="goodsListDivBlock"><button type="button" class="btn btn-white btn-info btn-bold btn-interval" id="bootbox-confirm">添加货品</button></span>

                                                            <table id="orderGoodsListTable" style="margin-top:20px;" class="table table-striped table-bordered table-hover dataTable no-footer" role="grid" aria-describedby="dynamic-table_info">
                                                                <thead>
                                                                <tr role="row">
                                                                <#--<th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Domain: activate to sort column ascending">选择</th>-->
                                                                    <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Domain: activate to sort column ascending">货品编码</th>
                                                                    <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Price: activate to sort column ascending">货品名称</th>
                                                                    <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Update: activate to sort column ascending">货品规格
                                                                    </th>
                                                                    <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">单位</th>
                                                                    <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">单价</th>
                                                                    <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">数量</th>
                                                                    <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">生产批次</th>
                                                                    <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">
                                                                        生产日期</th>
                                                                    <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">
                                                                        失效日期</th>
                                                                    <th class="center sorting_disabled" rowspan="1" colspan="1" aria-label="">
                                                                        操作
                                                                    </th>


                                                                </thead>
                                                                <tbody>
                                                                <#--货品明细-->
                                                                <#list ofcGoodsDetailsList! as goodsDetails>
                                                                <tr role="row" class="odd" align="center">

                                                                    <td>${(goodsDetails.goodsCode)!""}</td>
                                                                    <td>${(goodsDetails.goodsName)!""}</td>
                                                                    <td class="hidden-480">${(goodsDetails.goodsSpec)!""}</td>
                                                                    <td>${(goodsDetails.unit)!""}</td>
                                                                    <td>${((goodsDetails.unitPrice)?replace(',',''))!""}</td>
                                                                    <td class="hidden-480"><input name='' type='search' value='${((goodsDetails.quantity)?replace(',',''))!""}' class='form-control input-sm' placeholder='' aria-controls='dynamic-table' ></td>
                                                                    <td class="hidden-480"><input name='' type='search' value='${(goodsDetails.productionBatch)!""}' class='form-control input-sm' placeholder='' aria-controls='dynamic-table' ></td>
                                                                    <td class="hidden-480"><input name='' type='search' value='${((goodsDetails.productionTime)?string('yyyy-MM-dd'))!""}' onClick="laydate({istime: true, format: 'YYYY-MM-DD hh:mm',isclear: true,istoday: true})" class='form-control input-sm' placeholder='' aria-controls='dynamic-table'></td>
                                                                    <td class="hidden-480"><input name='' type='search' value='${((goodsDetails.invalidTime)?string('yyyy-MM-dd'))!""}' onClick="laydate({istime: true, format: 'YYYY-MM-DD hh:mm',isclear: true,istoday: true})" class='form-control input-sm' placeholder='' aria-controls='dynamic-table' ></td>
                                                                    <td  style='display:none'>${(goodsDetails.weight)!""}</td>
                                                                    <td  style='display:none'>${(goodsDetails.cubage)!""}</td>
                                                                    <td class="center">
                                                                        <a id=""    class="red" onclick='deleteGood(this)' <#--onclick="deleteGoods('${(goodsDetails.orderCode)!"null"}','${goodsDetails.goodsCode!"null"}')"-->>删除</a>
                                                                    </td>
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
                                                                运输基本信息
                                                            </div>

                                                            <div class="row">

                                                                <div id="dynamic-table_filter" class="dataTables_length">

                                                                    <div class="form-group y-float" >
                                                                        <label class="control-label col-label no-padding-right" for="name">数量</label>
                                                                        <div class="w-width-220">
                                                                            <div class="clearfix">
                                                                                <input id="quantity" name="quantity" <#if orderInfo.quantity?? >value="${(orderInfo.quantity)?replace(',','')}"</#if> type="text" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" >
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                    <div class="form-group y-float" >
                                                                        <label class="control-label col-label no-padding-right" for="name">重量</label>
                                                                        <div class="w-width-220">
                                                                            <div class="clearfix">
                                                                                <input id="weight" name="weight" <#if orderInfo.weight?? >value="${(orderInfo.weight)?replace(',','')}"</#if> type="text" class="form-control input-sm" placeholder="" aria-controls="dynamic-table">
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                    <div class="form-group y-float" >
                                                                        <label class="control-label col-label no-padding-right" for="name">体积</label>
                                                                        <div class="w-width-220" style="width:280px;">
                                                                            <div class="clearfix">
                                                                                <input id="cubage" style="width:196px;float:left;" name="cubage" <#if orderInfo.cubage?? >value="${orderInfo.cubage}"</#if> type="text" class="form-control input-sm" placeholder="" aria-controls="dynamic-table">
                                                                                <span style="margin-left:5px;">(单位:m³)</span>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                    <div class="form-group y-float" style="clear:left;">
                                                                        <label class="control-label col-label no-padding-right" for="name">合计标准箱</label>
                                                                        <div class="w-width-220">
                                                                            <div class="clearfix">
                                                                                <input id="totalStandardBox" name="totalStandardBox" <#if orderInfo.totalStandardBox?? >value="${((orderInfo.totalStandardBox)?replace(',',''))!}"</#if> type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table">
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                <#-- <div class="form-group y-float" >
                                                                     <label class="control-label col-label no-padding-right" for="name">出发地</label>
                                                                     <div class="w-width-220">
                                                                         <div class="clearfix">
                                                                             <input id="departurePlace" name="departurePlace" <#if orderInfo.departurePlace?? >value="${orderInfo.departurePlace}"</#if> type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" readonly="readonly">
                                                                         </div>
                                                                     </div>
                                                                 </div>
                                                                 <div class="form-group y-float" >
                                                                     <label class="control-label col-label no-padding-right" for="name">目的地</label>
                                                                     <div class="w-width-220">
                                                                         <div class="clearfix">
                                                                             <input id="destination" name="destination" <#if orderInfo.destination?? >value="${orderInfo.destination}"</#if> type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" readonly="readonly">
                                                                         </div>
                                                                     </div>
                                                                 </div>-->
                                                                    <div class="form-group y-float" >
                                                                        <label class="control-label col-label no-padding-right" for="name">取货时间</label>
                                                                        <div class="w-width-220">
                                                                            <div class="clearfix position-relative">
                                                                                <input id="pickupTime" name="pickupTime" <#if orderInfo.pickupTime?? >value="${((orderInfo.pickupTime)?string('yyyy-MM-dd HH:mm:ss'))!}"</#if> type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table"onclick="laydate({istime: true, format: 'YYYY-MM-DD hh:mm',isclear: true,istoday: true})">
                                                                                <label for="pickupTime" class="initBtn">
                                                                                    <i class="fa fa-calendar bigger-130"></i>
                                                                                </label>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                    <div class="form-group y-float" >
                                                                        <label class="control-label col-label no-padding-right" for="name">期望送达时间</label>
                                                                        <div class="w-width-220">
                                                                            <div class="clearfix position-relative">
                                                                                <input id="expectedArrivedTime" name="expectedArrivedTime" <#if orderInfo.expectedArrivedTime?? >value="${((orderInfo.expectedArrivedTime)?string('yyyy-MM-dd HH:mm:ss'))!}"</#if> type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" onclick="laydate({istime: true, format: 'YYYY-MM-DD hh:mm',isclear: true,istoday: true})">
                                                                                <label for="expectedArrivedTime" class="initBtn">
                                                                                    <i class="fa fa-calendar bigger-130"></i>
                                                                                </label>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                    <div class="form-group y-float"  style="clear:left;">
                                                                        <label class="control-label col-label no-padding-right" for="name">是否加急</label>
                                                                        <div class="w-width-220">
                                                                            <div class="clearfix">
                                                                                <#--<input  id="urgent" type="checkbox" name="" <#if orderInfo.urgent?? ><#if ((orderInfo.urgent) == 1)> checked="checked"</#if></#if>/>-->
                                                                                <label>
                                                                                    <input class="ace" id="urgent" type="checkbox" name="" <#if orderInfo.urgent?? ><#if ((orderInfo.urgent) == 1)> checked="checked"</#if></#if>/>
                                                                                    <span class="lbl"></span>
                                                                                </label>
                                                                                <input id="urgent1" type="hidden" name="urgent" value="${(orderInfo.urgent)!""}"/>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                    <div class="form-group y-float" >
                                                                        <label class="control-label col-label no-padding-right" for="name">运输要求</label>
                                                                        <div class="w-width-220">
                                                                            <div class="clearfix">
                                                                                <input id="transRequire" name="transRequire" <#if orderInfo.transRequire?? >value="${orderInfo.transRequire}"</#if> type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table">
                                                                            </div>
                                                                        </div>
                                                                    </div>

                                                                </div>
                                                            </div>
                                                            <div class="page-header">
                                                                发货方信息
                                                            </div>
                                                            <span style="cursor:pointer;" id="consignorListDivBlock">
                                                                <button type="button" class="btn btn-white btn-info btn-bold btn-interval" id="bootbox-confirm">选择</button>
                                                            </span>
                                                            <div id="consignorin" class="row" style="min-height:100px;">

                                                                <div class="form-group y-float" >
                                                                    <label class="control-label col-label no-padding-right" for="name">名称</label>
                                                                    <div class="w-width-220">
                                                                        <div class="clearfix">
                                                                            <input id="consignorCode" name="consignorCode" <#if (consignorMessage.contactCompanyCode)!?? >value="${(consignorMessage.contactCompanyCode)!}"</#if> type="hidden">
                                                                            <input id="consignorType" name="consignorType" <#if (consignorMessage.type)!?? >value="${(consignorMessage.type)!}"</#if> type="hidden">
                                                                            <input id="consignorName"  name="consignorName" readonly <#if (consignorMessage.contactCompanyName)!?? >value="${(consignorMessage.contactCompanyName)!}"</#if> type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" >

                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="form-group y-float" >
                                                                    <label class="control-label col-label no-padding-right" for="name">联系人</label>
                                                                    <div class="w-width-220">
                                                                        <div class="clearfix">
                                                                            <input id="consignorContactCode"   name="consignorContactCode" <#if (consignorMessage.contactCode)!?? >value="${(consignorMessage.contactCode)!}"</#if>  type="hidden" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" >
                                                                            <input id="consignorContactName" name="consignorContactName" readonly <#if (consignorMessage.contactName)!?? >value="${(consignorMessage.contactName)!}"</#if> type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" >

                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="form-group y-float" >
                                                                    <label class="control-label col-label no-padding-right" for="name">联系电话</label>
                                                                    <div class="w-width-220">
                                                                        <div class="clearfix">
                                                                            <input id="consignorPhone" name="consignorPhone" readonly <#if (consignorMessage.phone)!?? >value="${(consignorMessage.phone)!}"</#if> type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" >

                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="form-group y-float" style="clear:left;">
                                                                    <label class="control-label col-label no-padding-right" for="name">传真</label>
                                                                    <div class="w-width-220">
                                                                        <div class="clearfix">
                                                                            <input id="consignorFax" name="consignorFax" readonly <#if (consignorMessage.fax)!?? >value="${(consignorMessage.fax)!}"</#if> type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" >

                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="form-group y-float" >
                                                                    <label class="control-label col-label no-padding-right" for="name">Email</label>
                                                                    <div class="w-width-220">
                                                                        <div class="clearfix">
                                                                            <input id="consignorEmail" name="consignorEmail" readonly <#if (consignorMessage.email)!?? >value="${(consignorMessage.email)!}"</#if> type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" >
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="form-group y-float" >
                                                                    <label class="control-label col-label no-padding-right" for="name">邮编</label>
                                                                    <div class="w-width-220">
                                                                        <div class="clearfix">
                                                                            <input id="consignorPostCode" name="consignorPostCode" readonly <#if (consignorMessage.postCode)!?? >value="${(consignorMessage.postCode)!}"</#if> type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" >
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="form-group y-float" style="clear:left;">
                                                                    <label class="control-label col-label no-padding-right" for="name">地址选择</label>
                                                                    <div class="w-width-220" style="width:517px;">
                                                                        <div class="clearfix position-relative">
                                                                            <input id="city-picker3-consignor" value="${(consignorMessage.provinceName)!}/${(consignorMessage.cityName)!}/${(consignorMessage.areaName)!}/${(consignorMessage.streetName)!}" class="form-control" readonly type="text" value="" data-toggle="city-picker">

                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="form-group y-float"  style="clear:left;">
                                                                    <label class="control-label col-label no-padding-right" for="name">详细地址</label>
                                                                    <div class="w-width-220" style="width:517px;">
                                                                        <div class="clearfix">
                                                                            <input id="consignorAddress" name="consignorAddress" readonly <#if (orderInfo.departurePlace)!?? >value="${(orderInfo.departurePlace)!}"</#if> type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" >
                                                                        </div>
                                                                    </div>
                                                                </div>

                                                            </div>

                                                            <div class="page-header width-100 y-float">
                                                               收货方信息
                                                            </div>
                                                            <span style="cursor:pointer;" id="consigneeListDivBlock">
                                                                <button type="button" class="btn btn-white btn-info btn-bold btn-interval" id="bootbox-confirm">选择</button>
                                                            </span>
                                                            <div id="consignorout" class="row">
                                                                <div class="form-group y-float" >
                                                                    <label class="control-label col-label no-padding-right" for="name">名称</label>
                                                                    <div class="w-width-220">
                                                                        <div class="clearfix">
                                                                            <input id="cosigneeCode" name="cosigneeCode" type="hidden" <#if (consigneeMessage.contactCompanyCode)!?? >value="${(consigneeMessage.contactCompanyCode)!}"</#if> >
                                                                            <input id="consigneeType" name="consigneeType" <#if (consigneeMessage.type)!?? >value="${(consigneeMessage.type)!}"</#if> type="hidden">
                                                                            <input id="consigneeName" name="consigneeName" readonly <#if (consigneeMessage.contactCompanyName)!?? >value="${(consigneeMessage.contactCompanyName)!}"</#if> type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" >

                                                                        </div>
                                                                    </div>
                                                                </div>

                                                                <div class="form-group y-float" >
                                                                    <label class="control-label col-label no-padding-right" for="name">联系人</label>
                                                                    <div class="w-width-220">
                                                                        <div class="clearfix">
                                                                            <input id="consigneeContactCode" name="consigneeContactCode" <#if (consigneeMessage.contactCode)!?? >value="${(consigneeMessage.contactCode)!}"</#if> type="hidden" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" >
                                                                            <input id="consigneeContactName" name="consigneeContactName" readonly <#if (consigneeMessage.contactName)!?? >value="${(consigneeMessage.contactName)!}"</#if> type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" >
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="form-group y-float" >
                                                                    <label class="control-label col-label no-padding-right" for="name">联系电话</label>
                                                                    <div class="w-width-220">
                                                                        <div class="clearfix">
                                                                            <input id="consigneePhone" name="consigneePhone" readonly <#if (consigneeMessage.phone)!?? >value="${(consigneeMessage.phone)!}"</#if> type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" >
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="form-group y-float" style="clear:left;">
                                                                    <label class="control-label col-label no-padding-right" for="name">传真</label>
                                                                    <div class="w-width-220">
                                                                        <div class="clearfix">
                                                                            <input id="consigneeFax" name="consigneeFax" readonly <#if (consigneeMessage.fax)!?? >value="${(consigneeMessage.fax)!}"</#if> type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" >
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="form-group y-float" >
                                                                    <label class="control-label col-label no-padding-right" for="name">Email</label>
                                                                    <div class="w-width-220">
                                                                        <div class="clearfix">
                                                                            <input id="consigneeEmail" name="consigneeEmail" readonly <#if (consigneeMessage.email)!?? >value="${(consigneeMessage.email)!}"</#if> type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" >
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="form-group y-float" >
                                                                    <label class="control-label col-label no-padding-right" for="name">邮编</label>
                                                                    <div class="w-width-220">
                                                                        <div class="clearfix">
                                                                            <input id="consigneePostCode" name="consigneePostCode" readonly <#if (consigneeMessage.postCode)!?? >value="${(consigneeMessage.postCode)!}"</#if> type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" >
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="form-group y-float" style="clear:left;">
                                                                    <label class="control-label col-label no-padding-right" for="name">地址选择</label>
                                                                    <div class="w-width-220" style="width:517px;">
                                                                        <div class="clearfix position-relative">
                                                                            <input id="city-picker3-consignee" value="${(consigneeMessage.provinceName)!}/${(consigneeMessage.cityName)!}/${(consigneeMessage.areaName)!}/${(consigneeMessage.streetName)!}" class="form-control" readonly type="text" value="" data-toggle="city-picker">

                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="form-group y-float" style="clear:left;">
                                                                    <label class="control-label col-label no-padding-right" for="name">详细地址</label>
                                                                    <div class="w-width-220" style="width:517px;">
                                                                        <div class="clearfix">
                                                                            <input id="consigneeAddress" name="consigneeAddress" readonly <#if (orderInfo.destination)!?? >value="${(orderInfo.destination)!}"</#if> type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" >
                                                                        </div>
                                                                    </div>
                                                                </div>


                                                            </div>

                                                        </div>

                                                        <div id="dropdown14" class="tab-pane">

                                                            <div class="page-header">
                                                                仓配基本信息
                                                            </div>

                                                            <div class="row">
                                                                <div id="dynamic-table_filter" class="dataTables_length">


                                                                    <div class="form-group y-float" >
                                                                        <label class="control-label col-label no-padding-right" for="name">仓库名称</label>
                                                                        <div class="w-width-220">
                                                                            <div class="clearfix">
                                                                                <select id="warehouseName" name="warehouseName" class="chosen-select"  <#-- value="${(orderInfo.wareHouseCode)!""}"-->>
                                                                                <#list rmcWarehouseByCustCode! as warehouse>

                                         <option <#if orderInfo.warehouseCode ??><#if warehouse.id == orderInfo.warehouseCode> selected="selected" </#if></#if> value="${(warehouse.id)!}">${(warehouse.warehouseName)!""}</option>



                                                                                </#list>
                                                                                </select>

                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                    <div class="form-group y-float" >
                                                                        <label class="control-label col-label no-padding-right" for="name">预计到达</label>
                                                                        <div class="w-width-220">
                                                                            <div class="clearfix position-relative">
                                                                                <input id="arriveTime" name="arriveTime" <#if orderInfo.arriveTime?? >value="${((orderInfo.arriveTime)?string('yyyy-MM-dd HH:mm'))!}"</#if>  type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" onclick="laydate({istime: true, format: 'YYYY-MM-DD hh:mm',isclear: true,istoday: true,min: laydate.now(-30),max: laydate.now()})">
                                                                                <label for="arriveTime" class="initBtn">
                                                                                    <i class="fa fa-calendar bigger-130"></i>
                                                                                </label>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                    <div class="form-group y-float" >
                                                                        <label class="control-label col-label no-padding-right" for="name">车牌号</label>
                                                                        <div class="w-width-220">
                                                                            <div class="clearfix">
                                                                                <input id="plateNumber" name="plateNumber" <#if orderInfo.plateNumber?? >value="${orderInfo.plateNumber}"</#if> type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table">

                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                    <div class="form-group y-float" style="clear:left;">
                                                                        <label class="control-label col-label no-padding-right" for="name">司机姓名</label>
                                                                        <div class="w-width-220">
                                                                            <div class="clearfix" style="height:34px;">
                                                                                <input id="driverName" name="driverName" <#if orderInfo.driverName?? >value="${orderInfo.driverName}"</#if> type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table"><br/>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                    <div class="form-group y-float" >
                                                                        <label class="control-label col-label no-padding-right" for="name">联系电话</label>
                                                                        <div class="w-width-220">
                                                                            <div class="clearfix">
                                                                                <input id="contactNumber" name="contactNumber" <#if orderInfo.contactNumber?? >value="${orderInfo.contactNumber}"</#if> type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table">
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>

                                                            <div id="supportMessageShowDiv" class="row" style="display: none;">
                                                                <div style="padding:0 12px;">
                                                                    <div class="page-header">
                                                                        供应商信息
                                                                    </div>
                                                                    <span style="cursor:pointer;" id="supportListDivBlock">
                                                                        <button type="button" class="btn btn-white btn-info btn-bold btn-interval" id="bootbox-confirm">选择</button>
                                                                    </span>
                                                                </div>

                                                                <div class="form-group y-float" >
                                                                    <label class="control-label col-label no-padding-right" for="name">名称</label>
                                                                    <div class="w-width-220">
                                                                        <div class="clearfix">
                                                                            <input id="supportCode" name="supportCode" type="hidden" <#if (supportMessage.supplierCode)!?? >value="${(supportMessage.supplierCode)!}"</#if>>
                                                                            <input id="supportName" name="supportName" readonly <#if (supportMessage.supplierName)!?? >value="${(supportMessage.supplierName)!}"</#if> type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" >
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="form-group y-float" >
                                                                    <label class="control-label col-label no-padding-right" for="name">联系人</label>
                                                                    <div class="w-width-220">
                                                                        <div class="clearfix">
                                                                            <input id="supportContactName" name="supportContactName" readonly <#if (supportMessage.contactName)!?? >value="${(supportMessage.contactName)!}"</#if> type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" >

                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="form-group y-float" >
                                                                    <label class="control-label col-label no-padding-right" for="name">联系电话</label>
                                                                    <div class="w-width-220">
                                                                        <div class="clearfix">
                                                                            <input id="supportPhone" name="supportPhone" readonly <#if (supportMessage.contactPhone)!?? >value="${(supportMessage.contactPhone)!}"</#if> type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" >

                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="form-group y-float" style="clear:left;">
                                                                    <label class="control-label col-label no-padding-right" for="name">传真</label>
                                                                    <div class="w-width-220">
                                                                        <div class="clearfix">
                                                                            <input id="supportFax" name="supportFax" readonly <#if (supportMessage.fax)!?? >value="${(supportMessage.fax)!}"</#if> type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" >

                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="form-group y-float" >
                                                                    <label class="control-label col-label no-padding-right" for="name">Email</label>
                                                                    <div class="w-width-220">
                                                                        <div class="clearfix">
                                                                            <input id="supportEmail" name="supportEmail" readonly <#if (supportMessage.email)!?? >value="${(supportMessage.email)!}"</#if> type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" >

                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="form-group y-float" >
                                                                    <label class="control-label col-label no-padding-right" for="name">邮编</label>
                                                                    <div class="w-width-220">
                                                                        <div class="clearfix">
                                                                            <input id="supportPostCode" name="supportPostCode" readonly <#if (supportMessage.postCode)!?? >value="${(supportMessage.postCode)!}"</#if> type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" >
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="form-group y-float" style="clear:left;">
                                                                    <label class="control-label col-label no-padding-right" for="name">地址选择</label>
                                                                    <div class="w-width-220" style="width:517px;">
                                                                        <div class="clearfix position-relative">
                                                                            <input id="city-picker3-support" class="form-control"  value="${(supportMessage.provinceName)!}/${(supportMessage.cityName)!}/${(supportMessage.areaName)!}/${(supportMessage.streetName)!}" readonly type="text" value="" data-toggle="city-picker">
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="form-group y-float y-float" style="clear:left;">
                                                                    <label class="control-label col-label no-padding-right" for="name">详细地址</label>
                                                                    <div class="w-width-220" style="width:517px;">
                                                                        <div class="clearfix">
                                                                            <input id="supportAddress" name="supportAddress" readonly <#if (supportMessage.address)!?? >value="${(supportMessage.address)!}"</#if> type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" >
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
                                   <#-- </div>-->
                               <#-- </div>-->
                            <#--</div>-->

                       <#-- </div>-->
                   <#--  <button type="button" class="btn btn-info" style="margin-left:35px;" id="bootbox-confirm" onclick="subOrder()">保存修改</button>-->
                    </div>
                </div>
<div class="page-content">
    <div class="row">
        <div class="col-xs-12">
            <button class="btn btn-white btn-info btn-bold btn-interval" id="bootbox-confirm" onclick="subOrder()">
                <i class="ace-icon fa fa-floppy-o bigger-120 blue" ></i>
                保存修改
            </button>
        </div>

    </div>
</div>

<link href="../css/city-picker.css" rel="stylesheet" type="text/css" />
<#--<link rel="stylesheet" href="../components/chosen/chosen.css" />
<script src="../components/chosen/chosen.jquery.js"></script>-->
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
        var csc_url = $("#csc_url").html();
        $('#orderFundamentalFormValidate').validate({//
            errorElement : 'div',
            errorClass : 'help-block',
            focusInvalid : false,
            ignore : "",
            rules : {
                orderTime:{
                    required:true
                },
                custOrderCode:{//ofc_url

                    maxlength: 30,
                    remote:{
                        url : ofc_url + "/ofc/checkCustOrderCode",
                        type : "POST",
                        dataType : "json",
                        data : {
                            custOrderCode : function() {
                                return $("#custOrderCode").val();
                            },
                            selfCustOrderCode : function () {
                                return $("#selfCustOrderCode").val();
                            }
                        }
                    }
                },
                notes:{
                    maxlength:300
                }
            },
            messages : {
                orderTime:{
                    required:true
                },
                custOrderCode:{

                    maxlength:mistake+ "超过最大长度",
                    remote: mistake+"该客户订单编号已经存在"
                },
                notes:{
                    maxlength:mistake+"超过最大长度"
                }
            },
            highlight : function(e) {
                $(e).closest('.form-group').removeClass('has-info').addClass('has-error');
            },
            success : function(e) {
                $(e).closest('.form-group').removeClass('has-error').addClass('has-success');
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
                    maxlength:8,
                    integer:true
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
                    integer:mistake+"必须输入整数"
                },
                weight:{
                    maxlength: mistake+"超过最大长度"
                },
                cubage:{
                    maxlength:mistake+"超过最大长度"
                },
                totalStandardBox:{
                    maxlength:mistake+"超过最大长度",
                    integer:mistake+"必须输入整数"
                },
                consignorName:{
                    required:mistake+"必须输入",
                    maxlength:"超过最大长度"
                },
                consignorContactName:{
                    required:mistake+"必须输入",
                    maxlength:"超过最大长度"
                },
                consignorPhone:{
                    isPhone:mistake+"请输入正确的手机号",
                    required:mistake+"必须输入",
                    maxlength:mistake+"超过最大长度"
                },
                consignorFax:{
                    isFax:mistake+"请输入正确的传真",
                    maxlength:mistake+"超过最大长度"
                },
                consignorEmail:{
                    isEmail:mistake+"请输入正确格式的邮箱",
                    maxlength:mistake+"超过最大长度"
                },
                consignorPostCode:{
                    isPostCode:mistake+"请输入正确格式的邮编",
                    maxlength:mistake+"超过最大长度"
                },
                consignorAddress:{
                    maxlength:mistake+"超过最大长度"
                },
                consigneeName:{
                    required:mistake+"必须输入",
                    maxlength:mistake+"超过最大长度"
                },
                consigneeContactName:{
                    required:mistake+"必须输入",
                    maxlength:mistake+"超过最大长度"
                },
                consigneePhone:{
                    isPhone:mistake+"请输入正确格式的手机号",
                    required:mistake+"必须输入",
                    maxlength:mistake+"超过最大长度"
                },
                consigneeFax:{
                    isFax:mistake+"请输入正确格式的传真",
                    maxlength:mistake+"超过最大长度"
                },
                consigneeEmail:{
                    isEmail:mistake+"请输入正确格式的邮箱",
                    maxlength:mistake+"超过最大长度"
                },
                consigneePostCode:{
                    isPostCode:mistake+"请输入正确格式的邮编",
                    maxlength:mistake+"超过最大长度"
                },
                consigneeAddress:{
                    maxlength:mistake+"超过最大长度"
                },
                warehouseName:{
                    required:mistake+"必须输入"
                },
                arriveTime:{
                    required:mistake+"必须输入"
                },
                plateNumber:{
                    maxlength:mistake+"超过最大长度"
                },
                driverName:{
                    maxlength:mistake+"超过最大长度"
                },
                contactNumber:{
                    isPhone:mistake+"请输入正确格式的手机号",
                    maxlength:mistake+"超过最大长度"
                },
                supportName:{
                    required:mistake+"必须输入",
                    maxlength:mistake+"超过最大长度"
                },
                supportContactName:{
                    required:mistake+"必须输入",
                    maxlength:mistake+"超过最大长度"
                },
                supportPhone:{
                    required:mistake+"必须输入",
                    isPhone:mistake+"请输入正确的手机号"
                },
                supportFax:{
                    isFax:mistake+"请输入正确格式的传真",
                    maxlength:mistake+"超过最大长度"
                },
                supportEmail:{
                    isEmail:mistake+"请输入正确格式的邮箱",
                    maxlength:mistake+"超过最大长度"
                },
                supportPostCode:{
                    isPostCode:mistake+"请输入正确格式的邮编",
                    maxlength:mistake+"超过最大长度"
                },
                supportAddress:{
                    maxlength:mistake+"超过最大长度"
                }



            },
            highlight : function(e) {
                $(e).closest('.form-group').removeClass('has-info').addClass('has-error');
            },
            success : function(e) {
                $(e).closest('.form-group').removeClass('has-error').addClass('has-success');
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
<script type="text/javascript">

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
        jsonStr.urgent = $("#urgent1").val();
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
        jsonStr.departureTowns = consignorAddressNameMessage[3];
        jsonStr.departurePlaceCode = consignorAddressMessage[1];
        jsonStr.departurePlace = $("#consignorAddress").val();
        jsonStr.destinationProvince = consigneeAddressNameMessage[0];
        jsonStr.destinationCity = consigneeAddressNameMessage[1];
        jsonStr.destinationDistrict = consigneeAddressNameMessage[2];
        jsonStr.destinationTowns = consigneeAddressNameMessage[3];
        jsonStr.destinationCode=consigneeAddressMessage[1];
        jsonStr.destination = $("#consigneeAddress").val();


        /*jsonStr.departureProvince=$("#consignorProvinceName").val();
        jsonStr.departureCity=$("#consignorCityName").val();
        jsonStr.departureDistrict=$("#consignorAreaName").val();
        jsonStr.departureTowns=$("#consignorStreetName").val();
        jsonStr.departurePlaceCode=$("#consignorProvinceCode").val()
                + "/" + $("#consignorCityCode").val()
                + "/" +  $("#consignorAreaCode").val()
                + "/" + $("#consignorStreetCode").val();
        jsonStr.departurePlace=$("#consignorProvinceName").val()
                + $("#consignorCityName").val()
                + $("#consignorAreaName").val()
                + $("#consignorStreetName").val()
                + $("#consignorAddress").val();
        jsonStr.destinationProvince=$("#consigneeProvinceName").val();
        jsonStr.destinationCity=$("#consigneeCityName").val();
        jsonStr.destinationDistrict=$("#consigneeAreaName").val();
        jsonStr.destinationTowns=$("#consigneeStreetName").val();
        jsonStr.destinationCode=$("#consigneeProvinceCode").val()
                + "/" + $("#consigneeCityCode").val()
                + "/" +  $("#consigneeAreaCode").val()
                + "/" + $("#consigneeStreetCode").val();
        jsonStr.destination=$("#consigneeProvinceName").val()
                + $("#consigneeCityName").val()
                + $("#consigneeAreaName").val()
                + $("#consigneeStreetName").val()
                + $("#consigneeAddress").val();*/




        return jsonStr;
    }
    function orderPlaceAddWareInfoWithoutSupport(jsonStr) {
        //仓配基本信息
        jsonStr.warehouseCode = $("#warehouseName").val();
        jsonStr.warehouseName = $("#warehouseName option:selected").text();
        jsonStr.arriveTime = $dp.$('arriveTime').value;
        jsonStr.plateNumber = $("#plateNumber").val();
        jsonStr.driverName = $("#driverName").val();
        jsonStr.contactNumber = $("#contactNumber").val();

        return jsonStr;
    }
    function getCscContantAndCompanyDtoConsignorStr() {
        var paramConsignor = {};
        var paramConsignee = {};
        var cscContactDto = {};
        var cscContactCompanyDto = {};
        cscContactCompanyDto.contactCompanyName = $("#consignorName").val();
        cscContactDto.contactName = $("#consignorContactName").val();
        cscContactDto.purpose = "2";
        cscContactDto.phone = $("#consignorPhone").val();
        /*var cscContantAndCompanyDto = {};
       cscContantAndCompanyDto.cscContactDto = cscContactDto;
       cscContantAndCompanyDto.cscContactCompanyDto = cscContactCompanyDto;
       var param = JSON.stringify(cscContantAndCompanyDto);

      CommonClient.syncpost(sys.rootPath + "/ofc/contactSelect",{"cscContantAndCompanyDto":param},function (data) {
          data = eval(data);
          $.each(data,function (index,CscContantAndCompanyDto) {
              
              resultConsignorCode = CscContantAndCompanyDto.contactCompanySerialNo;
              resultConsignorContactCode = CscContantAndCompanyDto.contactSerialNo;
              resultConsignorType = CscContantAndCompanyDto.type;
              $("#consignorCode").val(CscContantAndCompanyDto.contactCompanySerialNo);
              $("#consignorContactCode").val(CscContantAndCompanyDto.contactSerialNo);
              $("#consignorType").val(CscContantAndCompanyDto.type);
              console.log("consignorCode'val()=  inininin  ="+$("#consignorCode").val());
          });
      });*/

        cscContactDto.serialNo = $("#consignorContactCode").val();
        cscContactCompanyDto.type = $("#consignorType").val();
        //cscContactCompanyDto.id = $("#consignorCode").val();

        cscContactDto.fax = $("#consignorFax").val();
        cscContactDto.email = $("#consignorEmail").val();
        cscContactDto.postCode = $("#consignorPostCode").val();

        var consignorAddressMessage = $("#city-picker3-consignor").val().split('~');


        var consignorAddressCodeMessage = consignorAddressMessage[1].split(',');
        var consignorAddressNameMessage = consignorAddressMessage[0].split('/');


        cscContactDto.province = consignorAddressCodeMessage[0];
        cscContactDto.city = consignorAddressCodeMessage[1];
        cscContactDto.area = consignorAddressCodeMessage[2];
//        cscContactDto.streetCode = consigneeAddressCodeMessage[3];
        if(!StringUtil.isEmpty(consignorAddressCodeMessage[3])){
            cscContactDto.street = consignorAddressCodeMessage[3];
        }
        cscContactDto.provinceName = consignorAddressNameMessage[0];
        cscContactDto.cityName = consignorAddressNameMessage[1];
        cscContactDto.areaName = consignorAddressNameMessage[2];
        if(!StringUtil.isEmpty(consignorAddressNameMessage[3])){
            cscContactDto.streetName = consignorAddressNameMessage[3];
        }
        cscContactDto.address = $("#consigneeAddress").val();

//        cscContactDto.address = $("#consignorAddress").val();
        paramConsignor.cscContactDto = cscContactDto;
        paramConsignor.cscContactCompanyDto = cscContactCompanyDto;
        var cscContantAndCompanyDtoConsignorStr = JSON.stringify(paramConsignor);
        return cscContantAndCompanyDtoConsignorStr;


    }
    function getCscContantAndCompanyDtoConsigneeStr() {
        var paramConsignor = {};
        var paramConsignee = {};
        var cscContactDto = {};
        var cscContactCompanyDto = {};
        cscContactCompanyDto.contactCompanyName = $("#consigneeName").val();
        cscContactDto.contactName = $("#consigneeContactName").val();
        cscContactDto.purpose = "1";
        cscContactDto.phone = $("#consigneePhone").val();

        cscContactDto.serialNo = $("#consigneeContactCode").val();
        cscContactCompanyDto.type = $("#consigneeType").val();
        cscContactDto.fax = $("#consigneeFax").val();
        cscContactDto.email = $("#consigneeEmail").val();
        cscContactDto.postCode = $("#consigneePostCode").val();

        var consigneeAddressMessage = $("#city-picker3-consignee").val().split('~');
        var consigneeAddressCodeMessage = consigneeAddressMessage[1].split(',');
        var consigneeAddressNameMessage = consigneeAddressMessage[0].split('/');
        cscContactDto.province = consigneeAddressCodeMessage[0];
        cscContactDto.city = consigneeAddressCodeMessage[1];
        cscContactDto.area = consigneeAddressCodeMessage[2];
//        cscContactDto.streetCode = consigneeAddressCodeMessage[3];
        if(!StringUtil.isEmpty(consigneeAddressCodeMessage[3])){
            cscContactDto.street = consigneeAddressCodeMessage[3];
        }

        cscContactDto.provinceName = consigneeAddressNameMessage[0];
        cscContactDto.cityName = consigneeAddressNameMessage[1];
        cscContactDto.areaName = consigneeAddressNameMessage[2];
        if(!StringUtil.isEmpty(consigneeAddressNameMessage[3])){
            cscContactDto.streetName = consigneeAddressNameMessage[3];
        }
        cscContactDto.address = $("#consigneeAddress").val();


        paramConsignee.cscContactDto = cscContactDto;
        paramConsignee.cscContactCompanyDto = cscContactCompanyDto;
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
//        paramSupport.streetCode = supportAddressCodeMessage[3];
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




        $("#goodsSelectFormBtn").click(function () {
            CommonClient.post(sys.rootPath + "/ofc/goodsSelect", $("#goodsSelConditionForm").serialize(), function(data) {
                data=eval(data);
                var goodsList = "";
                $.each(data,function (index,cscGoodsVo) {
                    goodsList =goodsList + "<tr role='row' class='odd' align='center'>";
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

        var consignorCodeHide = "";
        var consignorContactCodeHide = "";
        var consignorTypeHide = "";
        var consigneeCodeHide = "";
        var consigneeContactCodeHide = "";
        var consigneeTypeHide = "";
        $("#consignorSelectFormBtn").click(function () {
            //$.post("/ofc/contactSelect",$("#consignorSelConditionForm").serialize(),function (data) {
            var cscContantAndCompanyDto = {};
            var cscContactDto = {};
            var cscContactCompanyDto = {};
            cscContactCompanyDto.contactCompanyName = $("#consignorName2").value;
            cscContactDto.purpose = "2";
            cscContactDto.contactName = $("#consignorPerson2").value;
            cscContactDto.phone = $("#consignorPhoneNumber2").value;
            cscContantAndCompanyDto.cscContactDto = cscContactDto;
            cscContantAndCompanyDto.cscContactCompanyDto = cscContactCompanyDto;
            var param = JSON.stringify(cscContantAndCompanyDto);
            CommonClient.post(sys.rootPath + "/ofc/contactSelect",{"cscContantAndCompanyDto":param}, function(data) {
                data=eval(data);
                var contactList = "";
                $.each(data,function (index,CscContantAndCompanyDto) {
                    consignorCodeHide = CscContantAndCompanyDto.contactCompanySerialNo;
                    consignorContactCodeHide = CscContantAndCompanyDto.contactSerialNo;
                    consignorTypeHide = CscContantAndCompanyDto.type;
                    contactList =contactList + "<tr role='row' class='odd' align='center'>";
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
                });
                $("#contactSelectListTbody2").html(contactList);
            },"json");
        });

        $("#consigneeSelectFormBtn").click(function () {
            var cscContantAndCompanyDto = {};
            var cscContactDto = {};
            var cscContactCompanyDto = {};
            cscContactCompanyDto.contactCompanyName = $("#consignorName2").value;
            cscContactDto.purpose = "1";
            cscContactDto.contactName = $("#consignorPerson2").value;
            cscContactDto.phone = $("#consignorPhoneNumber2").value;
            cscContantAndCompanyDto.cscContactDto = cscContactDto;
            cscContantAndCompanyDto.cscContactCompanyDto = cscContactCompanyDto;
            var param = JSON.stringify(cscContantAndCompanyDto);
            CommonClient.post(sys.rootPath + "/ofc/contactSelect",{"cscContantAndCompanyDto":param}, function(data) {
                data=eval(data);
                var contactList = "";
                $.each(data,function (index,CscContantAndCompanyDto) {
                    consigneeCodeHide = CscContantAndCompanyDto.contactCompanySerialNo;
                    consigneeContactCodeHide = CscContantAndCompanyDto.contactSerialNo;
                    consigneeTypeHide = CscContantAndCompanyDto.type;
                    contactList =contactList + "<tr role='row' class='odd' align='center'>";
                    contactList =contactList + "<td class='center'> "+"<label class='pos-rel'>"+"<input name='suppliersele' type='radio' class='ace'>"+"<span class='lbl'></span>"+"</label>"+"</td>";
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
                    supplierList =supplierList + "<tr role='row' class='odd' align='center'>";
                    supplierList =supplierList + "<td class='center'> "+"<label class='pos-rel'>"+"<input id='selGoods' type='radio' name = 'selGoods' class='ace'>"+"<span class='lbl'></span>"+"</label>"+"</td>";
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
                    /*$("#consigneeAddress").val(address);*/
                    var paramConsignor = {};
                    var paramConsignee = {};
                    var cscContactDto = {};
                    var cscContactCompanyDto = {};
                    cscContactCompanyDto.contactCompanyName = $("#consigneeName").val();
                    cscContactDto.contactName = $("#consigneeContactName").val();
                    cscContactDto.purpose = "1";
                    cscContactDto.phone = $("#consigneePhone").val();
                    var cscContantAndCompanyDto = {};
                    cscContantAndCompanyDto.cscContactDto = cscContactDto;
                    cscContantAndCompanyDto.cscContactCompanyDto = cscContactCompanyDto;
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
                goodsInfoListDiv =goodsInfoListDiv + "<td><a onclick='deleteGood(this)' class='red'>删除</a></td>";
                /* goodsInfoListDiv =goodsInfoListDiv + "<td><input id='deleteOrNot' type='checkbox'/></td>";*/
                goodsInfoListDiv =goodsInfoListDiv + "<td>"+goodsCode+"</td>";
                goodsInfoListDiv =goodsInfoListDiv + "<td>"+goodsName+"</td>";
                goodsInfoListDiv =goodsInfoListDiv + "<td>"+specification+"</td>";
                goodsInfoListDiv =goodsInfoListDiv + "<td>"+unit+"</td>";
                goodsInfoListDiv =goodsInfoListDiv + "<td>"+unitPrice+"</td>";
                goodsInfoListDiv =goodsInfoListDiv + "<td><input name='' type='text' class='form-control input-sm' placeholder='' aria-controls='dynamic-table' value="+quantity+"></td>";
                goodsInfoListDiv =goodsInfoListDiv + "<td><input name='' type='text' class='form-control input-sm' placeholder='' aria-controls='dynamic-table' value="+production_batch+"></td>";
                goodsInfoListDiv =goodsInfoListDiv + "<td><input name='' type='text' class='form-control input-sm' placeholder='' aria-controls='dynamic-table' onclick='laydate({istime: true, format: \"YYYY-MM-DD\",isclear: true,istoday: true})'></td>";
                goodsInfoListDiv =goodsInfoListDiv + "<td><input name='' type='text' class='form-control input-sm' placeholder='' aria-controls='dynamic-table' onclick='laydate({istime: true, format: \"YYYY-MM-DD\",isclear: true,istoday: true})'></td>";
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
                    goodsInfoListDiv =goodsInfoListDiv + "<td><a onclick='deleteGood(this)' class='red'>删除</a></td>";
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
                    /*$("#consignorAddress").val(address);*/

                    //在这里将所有的信息补全!!
                    var paramConsignor = {};
                    var paramConsignee = {};
                    var cscContactDto = {};
                    var cscContactCompanyDto = {};
                    cscContactCompanyDto.contactCompanyName = consignorName;
                    cscContactDto.contactName = contacts;
                    cscContactDto.purpose = "2";
                    cscContactDto.phone = contactsNumber;
                    var cscContantAndCompanyDto = {};
                    cscContantAndCompanyDto.cscContactDto = cscContactDto;
                    cscContantAndCompanyDto.cscContactCompanyDto = cscContactCompanyDto;
                    var param = JSON.stringify(cscContantAndCompanyDto);


                    CommonClient.syncpost(sys.rootPath + "/ofc/contactSelect",{"cscContantAndCompanyDto":param},function (data) {
                        
                        data = eval(data);
                        $.each(data,function (index,CscContantAndCompanyDto) {
                            
//                            resultConsignorCode = CscContantAndCompanyDto.contactCompanySerialNo;
//                            resultConsignorContactCode = CscContantAndCompanyDto.contactSerialNo;
//                            resultConsignorType = CscContantAndCompanyDto.type;
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
                    /*$("#supportAddress").val(address);*/
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
        var pageStoreMessage = $("#storeCode").val().split("/");
        jsonStr.storeCode = pageStoreMessage[0];
        jsonStr.storeName = $("#storeCode option:selected").text();
        jsonStr.platformType = pageStoreMessage[1];
        jsonStr.notes = $("#notes").val();
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
        var tag = "manage";
        var ofcOrderDTOJson = JSON.stringify(jsonStr);
        var orderGoodsListStr = JSON.stringify(orderGoodsList);

        xescm.common.submit("/ofc/orderEdit"
                ,{"ofcOrderDTOStr":ofcOrderDTOJson
                    ,"orderGoodsListStr":orderGoodsListStr+"~`"
                    ,"cscContantAndCompanyDtoConsignorStr":cscContantAndCompanyDtoConsignorStr
                    ,"cscContantAndCompanyDtoConsigneeStr":cscContantAndCompanyDtoConsigneeStr
                    ,"cscSupplierInfoDtoStr":cscSupplierInfoDtoStr
                    ,"tag":tag}
                ,"您确认提交订单吗?"
                ,function () {
                    
                    xescm.common.loadPage("/ofc/orderManage");
                })
    }


    function deleteGood(obj) {
        $(obj).parent().parent().remove();
    }

</script>

</body>