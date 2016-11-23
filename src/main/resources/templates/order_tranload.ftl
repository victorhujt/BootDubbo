<head>
    <title>运输开单</title>
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
        #custListDiv{
            position:fixed;
            left:285px;
            top:77px;
            width:946px;
            height:500px;
            z-index:3;
            overflow: auto;
            border:solid #7A7A7A 2px;
        }
        #goodsAndConsigneeDiv{
            position:fixed;
            left:285px;
            top:77px;
            width:946px;
            height:500px;
            z-index:3;
            overflow: auto;
            border:solid #7A7A7A 2px;
        }
    </style>
    <link rel="stylesheet" type="text/css" href="../css/jquery.editable-select.min.css" />
</head>

<body class="no-skin">
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
                    <tr role="row">
                        <th class="center sorting_disabled" rowspan="1" colspan="1" aria-label="">
                            <label class="pos-rel">
                                <input id="goodcheck" type="checkbox" class="ace">
                                <span class="lbl"></span>
                            </label>
                        </th>
                    <#--<th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Domain: activate to sort column ascending">序号</th>-->
                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Price: activate to sort column ascending">货品种类</th>
                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">货品小类</th>
                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">品牌</th>
                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">货品编码</th>
                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">货品名称</th>
                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">规格</th>
                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">单位</th>
                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">条形码</th>

                    </thead>
                    <tbody id="goodsSelectListTbody"></tbody>
                </table>


            </form>
        </div>
    </div>
    <div class="modal-footer"><button id="goodsEnter" data-bb-handler="confirm" type="button" class="btn btn-primary">选中</button><span id="goodsListDivNoneBottom" style="cursor:pointer"><button  data-bb-handler="cancel" type="button" class="btn btn-default">关闭</button></span></div>
</div>
<!--consignorListDiv-->
<div class="modal-content" id="consignorListDiv" style="display: none;">
    <div class="modal-header"><span id="consignorListDivNoneTop" style="cursor:pointer"><button type="button" id="" style="cursor:pointer" class="bootbox-close-button close" data-dismiss="modal" aria-hidden="true">×</button></span>
        <h4 class="modal-title">发货方联系人</h4></div>
    <div class="modal-body">
        <div class="bootbox-body">
            <form id="consignorSelConditionForm" class="form-horizontal" role="form">
            <#--<input id="purpose2" name="cscContact.purpose" type="hidden" value="2">-->
                <div class="form-group">
                    <label class="control-label col-sm-1 no-padding-right" for="name">名称</label>
                    <div class="col-sm-3">
                        <div class="clearfix">
                            <input  id = "consignorName2" name="cscContactCompany.contactCompanyName" type="text" style="color: black" class="form-control input-sm" placeholder="" aria-controls="dynamic-table">
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-sm-1 no-padding-right" for="name">联系人</label>
                    <div class="col-sm-3">
                        <div class="clearfix">
                            <input  id = "consignorPerson2" name="cscContact.contactName" type="text" style="color: black" class="form-control input-sm" placeholder="" aria-controls="dynamic-table">
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-sm-1 no-padding-right" for="name">联系电话</label>
                    <div class="col-sm-3">
                        <div class="clearfix">
                            <input  id = "consignorPhoneNumber2" name="cscContact.phone" type="text" style="color: black" class="form-control input-sm" placeholder="" aria-controls="dynamic-table">
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
                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">地址</th>

                    </thead>
                    <tbody id="contactSelectListTbody2"></tbody>
                </table>


            </form>
        </div>
    </div>
    <div class="modal-footer"><button id="contactinEnter" data-bb-handler="confirm" type="button" class="btn btn-primary">选中</button><span id="consignorListDivNoneBottom" style="cursor:pointer"><button  data-bb-handler="cancel" type="button" class="btn btn-default">关闭</button></span></div>
</div>

<!--consigneeListDiv-->
<div class="modal-content" id="consigneeListDiv" style="display: none;">
    <div class="modal-header"><span id="consigneeListDivNoneTop" style="cursor:pointer"><button type="button" id="" style="cursor:pointer" class="bootbox-close-button close" data-dismiss="modal" aria-hidden="true">×</button></span>
        <h4 class="modal-title">收货方联系人</h4></div>
    <div class="modal-body">
        <div class="bootbox-body">
            <form id="consigneeSelConditionForm" class="form-horizontal" role="form">
            <#--<input id="purpose2" name="purpose" type="hidden" value="1">-->
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
                            <input id="consigneecheck" type="checkbox" class="ace">
                            <span class="lbl"></span>
                        </label>
                    </th>
                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Domain: activate to sort column ascending">序号</th>
                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Price: activate to sort column ascending">名称</th>
                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">联系人</th>
                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">联系电话</th>
                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">地址</th>
                    </thead>
                    <tbody id="contactSelectListTbody1"></tbody>
                </table>


            </form>
        </div>
    </div>
    <div class="modal-footer"><button id="contactoutEnter" data-bb-handler="confirm" type="button" class="btn btn-primary">选中</button><span id="consigneeListDivNoneBottom" style="cursor:pointer"><button  data-bb-handler="cancel" type="button" class="btn btn-default">关闭</button></span></div>
</div>

<!--custListDiv-->
<div class="modal-content" id="custListDiv" style="display: none;">
    <div class="modal-header"><span id="custListDivNoneTop" style="cursor:pointer"><button type="button" id="" style="cursor:pointer" class="bootbox-close-button close" data-dismiss="modal" aria-hidden="true">×</button></span>
        <h4 class="modal-title">选择客户</h4></div>
    <div class="modal-body">
        <div class="bootbox-body">
            <form id="consignorSelConditionForm" class="form-horizontal" role="form">
            <#--<input id="purpose2" name="cscContact.purpose" type="hidden" value="2">-->
                <div class="form-group">
                    <label class="control-label col-sm-1 no-padding-right" for="name">名称</label>
                    <div class="col-sm-3">
                        <div class="clearfix">
                            <input  id = "custNameDiv" name="cscContactCompany.contactCompanyName" type="text" style="color: black" class="form-control input-sm" placeholder="" aria-controls="dynamic-table">
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-sm-1 no-padding-right" for="name"></label>
                    <div class="col-sm-3">
                        <div class="clearfix">
                            <span id="custSelectFormBtn" class="btn btn-info btn-sm popover-info">筛选</span>
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
                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Price: activate to sort column ascending">类型</th>
                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">公司名称</th>
                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">渠道</th>
                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">产品类别</th>
                    </thead>
                    <tbody id="custListDivTbody"></tbody>
                </table>
            </form>

        </div>
    </div>
    <div class="form-group">
        <div class="modal-footer"><button style="float: left" id="createCustBtn" data-bb-handler="confirm" type="button" class="btn btn-primary">创建新客户</button><button id="custEnter" data-bb-handler="confirm" type="button" class="btn btn-primary">选中</button><span id="custListDivNoneBottom" style="cursor:pointer"><button  data-bb-handler="cancel" type="button" class="btn btn-default">关闭</button></span></div>
    </div>
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
                            <div class="form-group" id="transBusinessTypeDiv">
                                <div><label class="control-label col-label no-padding-right" for="name">业务类型</label>
                                <div class="col-sm-3">
                                    <div class="clearfix">
                                        <select  id="businessType" name="businessType">
                                            <option value="600">城配</option>
                                            <option value="601">干线</option>
                                            <option value="602">卡班</option>
                                        </select>
                                    </div>
                                </div></div>
                                <div><label class="control-label col-label no-padding-right" for="name">开单员</label>
                                <div class="col-sm-3">
                                    <div class="clearfix">
                                        <select id="merchandiser" name="merchandiser">
                                            <option>中国人民</option>
                                            <option>人民中国</option>
                                            <option>民中国人</option>
                                            <option>国人民中</option>
                                        </select>
                                    </div>
                                </div></div>
                                <div><label class="control-label col-sm-1 no-padding-right" for="name">运输类型</label>
                                <div class="col-sm-1">
                                    <div class="clearfix control-label">
                                        <input id="transportTypeV1" type="radio" name="transportTypeV" checked="checked" value="10"/>零担
                                    </div>
                                </div>
                                <div class="col-sm-1">
                                    <div class="clearfix control-label">
                                        <input id="transportTypeV2" type="radio" name="transportTypeV" value="20"/>整车
                                    </div>
                                </div>
                                <input id="transportType" type="hidden" name="transportType"/></div>
                            </div>
                           <div class="form-group">
                               <div><label class="control-label col-label no-padding-right" for="supplierCode">订单日期</label>
                               <div class="col-xs-3">
                                   <div class="clearfix">
                                       <input class="col-xs-10 col-xs-12"  name="orderTime" id="orderTime" type="text" placeholder="订单日期" onClick="WdatePicker({isShowClear:true,readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})" />
                                   </div>
                               </div></div>
                               <div><label class="control-label col-label no-padding-right" for="custOrderCode">运输单号</label>
                               <div class="col-xs-3">
                                   <div class="clearfix">
                                       <input class="col-xs-10 col-xs-12"  name="transCode" id="transCode" type="text" placeholder="运输单号" />
                                   </div>
                               </div></div>
                               <div><label class="control-label col-label no-padding-right" for="custOrderCode">客户名称</label>
                               <div class="col-xs-3">
                                   <div class="clearfix">
                                       <input class="col-xs-10 col-xs-12" readonly name="custName" id="custName" type="text" placeholder="客户名称" />
                                       <input class="col-xs-10 col-xs-12" name=""  id="custGroupId" type="text" style="display: none"  />
                                       <input class="col-xs-10 col-xs-12" name=""  id="custId" type="text"  style="display: none"  />
                                       <span style="cursor:pointer" id="custListDivBlock"><button type="button" class="btn btn-minier btn-inverse no-padding-right" id="custNameSel">选择</button></span>
                                   </div>
                               </div></div>
                           </div>
                        </form>
                        <div class="page-header">
                            <p>
                                服务项目及费用
                            </p>
                        </div>
                        <form id="orderFinanceFormValidate" method="post" class="form-horizontal" role="form" >
                            <div class="form-group">
                                <div class="col-sm-5">
                                    <div class="clearfix col-sm-1">
                                        <input id="pickUpGoodsV" type="checkbox" name=""  class="btn btn-minier btn-inverse no-padding-right" onchange=""/>
                                        <input id="pickUpGoods" type="hidden" name="pickUpGoods"  value="0" />
                                    </div>
                                    <label class="control-label col-sm-3" for="name">上门提货:费用</label>
                                    <div class="clearfix col-sm-5">
                                        <input id="homeDeliveryFee" disabled="true" style="color: #000" name="homeDeliveryFee" type="text" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" onblur= "countCost(this)" />
                                    </div>
                                    <label class="control-label" for="name">元</label>
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-sm-5">
                                    <div class="clearfix col-sm-1">
                                        <input id="insureV" type="checkbox" name=""  class="btn btn-minier btn-inverse no-padding-right"/>
                                        <input id="insure" type="hidden" name="insure"  value="0" />
                                    </div>
                                    <label class="control-label col-sm-3" for="name">货物保险:费用</label>
                                    <div class="clearfix col-sm-5">
                                        <input id="cargoInsuranceFee" disabled="true" style="color: #000" name="cargoInsuranceFee" type="text" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" onblur="countCost(this)">
                                    </div>
                                    <label class="control-label" for="name">元</label>
                                </div>
                                <div class="col-sm-4">
                                    <label class="control-label col-sm-3" for="name">声明价值</label>
                                    <div class="clearfix col-sm-6">
                                        <input id="insureValue" disabled="true" style="color: #000" name="insureValue" type="text" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" onblur="countCost(this)">
                                    </div>
                                    <label class="control-label" for="name">元</label>
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-sm-5">
                                    <div class="clearfix col-sm-1">
                                        <input id="twoDistributionV" type="checkbox" name=""  class="btn btn-minier btn-inverse no-padding-right"/>
                                        <input id="twoDistribution" type="hidden" name="twoDistribution"  value="0" />
                                    </div>
                                    <label class="control-label col-sm-3" for="name">二次配送:费用</label>
                                    <div class="clearfix col-sm-5">
                                        <input id="twoDistributionFee" disabled="true" style="color: #000" name="twoDistributionFee" type="text" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" onblur="countCost(this)">
                                    </div>
                                    <label class="control-label" for="name">元</label>
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-sm-5">
                                    <div class="clearfix col-sm-1">
                                        <input id="collectFlagV" type="checkbox" name=""  class="btn btn-minier btn-inverse no-padding-right"/>
                                        <input id="collectFlag" type="hidden" name="collectFlag"  value="0" />
                                    </div>
                                    <label class="control-label col-sm-3" for="name">代收货款:费用</label>
                                    <div class="clearfix col-sm-5">
                                        <input id="collectServiceCharge" disabled="true" style="color: #000" name="collectServiceCharge" type="text" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" onblur="countCost(this)">
                                    </div>
                                    <label class="control-label" for="name">元</label>
                                </div>
                                <div class="col-sm-4">
                                    <label class="control-label col-sm-3" for="name">代收金额</label>
                                    <div class="clearfix col-sm-6">
                                        <input id="collectLoanAmount" disabled="true" style="color: #FF0000;" name="collectLoanAmount" type="text" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" onblur="countCost(this)">
                                    </div>
                                    <label class="control-label" for="name">元</label>
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-sm-5">
                                    <div class="clearfix col-sm-1">
                                        <input id="returnListV" type="checkbox" name=""  class="btn btn-minier btn-inverse no-padding-right"/>
                                        <input id="returnList" type="hidden" name="returnList"  value="0" />
                                    </div>
                                    <label class="control-label col-sm-3" for="name">签单返回:费用</label>
                                    <div class="clearfix col-sm-5">
                                        <input id="returnListFee" disabled="true" style="color: #000" name="returnListFee" type="text" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" onblur="countCost(this)">
                                    </div>
                                    <label class="control-label" for="name">元</label>
                                </div>
                                <div class="col-sm-4">
                                    <label class="control-label col-sm-3" for="name">运费</label>
                                    <div class="clearfix col-sm-6">
                                        <input id="luggage" style="color: #000" name="luggage" type="text" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" onblur="countCost(this)">
                                    </div>
                                    <label class="control-label" for="name">元</label>
                                </div>
                            </div>
                        </form>
                        <div class="page-header">
                        </div>
                        <form id="orderFinanceChargeFormValidate" method="post" class="form-horizontal" role="form" >
                            <div class="form-group" id="transBusinessTypeDiv">
                                <div class="col-sm-4">
                                    <label class="control-label col-sm-3" for="name">费用总计</label>
                                    <div class="clearfix col-sm-6">
                                        <input id="serviceCharge" value="0" disabled="true" style="color: #000" name="serviceCharge" type="text" class="form-control input-sm" placeholder="" aria-controls="dynamic-table">
                                    </div>
                                    <label class="control-label" for="name">元</label>
                                </div>
                                <label class="control-label col-sm-1 no-padding-right" for="name">费用支付</label>
                                <div class="col-sm-1">
                                    <div class="clearfix control-label">
                                        <input id="transportTypeV1" type="radio" name="transportTypeV" checked="checked"/>发货方
                                    </div>
                                </div>
                                <div class="col-sm-1">
                                    <div class="clearfix control-label">
                                        <input id="transportTypeV2" type="radio" name="transportTypeV"/>收货方
                                    </div>
                                </div>
                                <input id="transportType" type="hidden" name="transportType"/>
                                <label class="control-label col-label no-padding-right" for="name">支付方式</label>
                                <div class="col-sm-3">
                                    <div class="clearfix">
                                        <select  id="businessType" name="businessType">
                                            <option value="01">现金</option>
                                            <option value="02">POS刷卡</option>
                                            <option value="03">微信</option>
                                            <option value="04">支付宝</option>
                                            <option value="05">银行支付</option>
                                            <option value="06">账户结算</option>
                                        </select>
                                    </div>
                                </div>
                            </div>
                            <div class="form-group" id="transBusinessTypeDiv">
                                <label class="control-label col-sm-1" for="name">结算方式</label>
                                <div class="col-sm-2">
                                    <label class="control-label col-sm-5" for="name">现结</label>
                                    <div class="clearfix col-sm-6">
                                        <input id="currentAmount"  style="color: #000" name="currentAmount" type="text" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" onblur="countSettlement()">
                                    </div>
                                    <label class="control-label" for="name">元</label>
                                </div>
                                <div class="col-sm-2">
                                    <label class="control-label col-sm-5" for="name">到付</label>
                                    <div class="clearfix col-sm-6">
                                        <input id="toPayAmount"  style="color: #000" name="toPayAmount" type="text" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" onblur="countSettlement()">
                                    </div>
                                    <label class="control-label" for="name">元</label>
                                </div>
                                <div class="col-sm-2">
                                    <label class="control-label col-sm-5" for="name">回付</label>
                                    <div class="clearfix col-sm-6">
                                        <input id="returnAmount"  style="color: #000" name="returnAmount" type="text" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" onblur="countSettlement()">
                                    </div>
                                    <label class="control-label" for="name">元</label>
                                </div>
                                <div class="col-sm-2">
                                    <label class="control-label col-sm-5" for="name">月结</label>
                                    <div class="clearfix col-sm-6">
                                        <input id="monthlyAmount"  style="color: #000" name="monthlyAmount" type="text" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" onblur="countSettlement()">
                                    </div>
                                    <label class="control-label" for="name">元</label>
                                </div>
                            </div>
                        <div class="page-header">
                        </div>
                        </form>
                        <form name="orderInfoTableValidate" id="orderInfoTableValidate"  class="form-horizontal" role="form" >
                            <div class="col-sm-12">
                                <!-- #section:elements.tab.option -->
                                <div class="tabbable" style="width:1000px;" >
                                    <ul class="nav nav-tabs" id="myTab4">
                                        <li class="active">
                                            <a data-toggle="tab" href="#home4" aria-expanded="false">货品信息</a>
                                        </li>
                                        <li class="transLi">
                                            <a data-toggle="tab" href="#consignor14" aria-expanded="true">发货方</a>
                                        </li>
                                        <li class="transLi">
                                            <a data-toggle="tab" href="#consignee14" aria-expanded="true">收货方</a>
                                        </li>
                                    </ul>

                                    <div class="tab-content">
                                        <div id="home4" class="tab-pane active">

                                            <!--货品明细-->
                                            <button type="button" style="float:right;" class="btn btn-minier btn-inverse"
                                                    id="addGoods">添加一行</button>
                                            <label id="" class="control-label" style="float:right;" for="name">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>
                                            <label id="" class="control-label" style="float:right;" for="name">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>
                                            <label id="" class="control-label" style="float:right;" for="name">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>
                                            <label class="control-label" style="float:right;" for="name">kg</label>
                                            <label id="" class="control-label" style="float:right;" for="name">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>
                                            <label id="weightCount" class="control-label" style="float:right;" for="name"></label>
                                            <label id="" class="control-label" style="float:right;" for="name">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>
                                            <label class="control-label" style="float:right;" for="name">重量合计：</label>
                                            <label id="" class="control-label" style="float:right;" for="name">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>
                                            <label id="quantityCount" class="control-label" style="float:right;" for="name"></label>
                                            <label id="" class="control-label" style="float:right;" for="name">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>
                                            <label class="control-label" style="float:right;" for="name">数量合计：</label>

                                        <#--dynamic-table-->
                                            <table id="orderGoodsListTable" class="table table-striped table-bordered table-hover dataTable no-footer" role="grid" aria-describedby="dynamic-table_info">
                                                <thead>
                                                <tr role="row"><th class="center sorting_disabled" rowspan="1" colspan="1" aria-label="">
                                                    操作
                                                </th>
                                                <#--<th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Status: activate to sort column ascending">序号</th>-->
                                                    <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Domain: activate to sort column ascending">货品类别</th>
                                                    <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Domain: activate to sort column ascending">货品编码</th>
                                                    <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Price: activate to sort column ascending">货品名称</th>
                                                    <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Update: activate to sort column ascending">规格
                                                    </th>
                                                    <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">单位</th>
                                                    <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">包装</th>
                                                    <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">数量</th>
                                                    <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">数量单价</th>
                                                    <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">重量</th>
                                                    <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">重量单价</th>
                                                    <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">体积</th>
                                                    <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">体积单价</th>
                                                </thead>
                                            <#--货品明细-->

                                                <tbody id="goodsInfoListDiv"></tbody>

                                            </table>
                                        </div>

                                        <div id="consignor14" class="tab-pane">
                                            <div class="page-header">
                                                <h4>发货方信息</h4>
                                            </div>
                                            <span style="cursor:pointer" id="consignorListDivBlock"><button type="button" class="btn btn-info" id="consignorselbtn">选择</button></span>
                                            <label id="departure_place" class="control-label" style="float:right;" for="name">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>
                                            <label class="control-label" style="float:right;" for="name">出发地：</label>
                                            <div id="consignorin">
                                                <div class="form-group" >
                                                    <label class="control-label col-sm-1 no-padding-right" for="name">名称</label>
                                                    <div class="col-sm-6">
                                                        <div class="clearfix">
                                                            <input id="consignorCode" name="consignorCode" type="hidden">
                                                            <input id="consignorType" name="consignorType" type="hidden">
                                                            <input id="consignorName"  name="consignorName" type="text" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" >
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="form-group" >
                                                    <label class="control-label col-sm-1 no-padding-right" for="name">联系人</label>
                                                    <div class="col-sm-6">
                                                        <div class="clearfix">
                                                            <input id="consignorContactCode"   name="consignorContactCode" type="hidden" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" >
                                                            <input id="consignorContactName"   name="consignorContactName" type="text" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" >
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="form-group" >
                                                    <label class="control-label col-sm-1 no-padding-right" for="name">联系电话</label>
                                                    <div class="col-sm-6">
                                                        <div class="clearfix">
                                                            <input id="consignorPhone" name="consignorPhone" type="text" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" >

                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="form-group" >
                                                    <label class="control-label col-sm-1 no-padding-right" for="name">地址选择</label>
                                                    <div class="col-sm-6">
                                                        <div class="clearfix">
                                                            <span style="cursor:pointer" id="city-picker-consignor"><input id="city-picker3-consignor" class="form-control input-sm" readonly type="text" value="" data-toggle="city-picker"></span>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="form-group" >
                                                    <label class="control-label col-sm-1 no-padding-right" for="name">详细地址</label>
                                                    <div class="col-sm-6">
                                                        <div class="clearfix">
                                                            <input id="consignorAddress" name="consignorAddress" type="text" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" >
                                                        </div>
                                                    </div>
                                                </div>

                                            </div>
                                        </div>
                                        <div id="consignee14" class="tab-pane">
                                            <div class="page-header">
                                                <h4>收货方信息</h4>
                                            </div>
                                            <span style="cursor:pointer" id="consigneeListDivBlock"><button type="button" class="btn btn-info" id="consigneeselbtn">选择</button></span>
                                            <label id="" class="control-label" style="float:right;" for="name">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>
                                            <label id="destination" class="control-label" style="float:right;" for="name"></label>
                                            <label class="control-label" style="float:right;" for="name">目的地：</label>
                                            <div id="consignorout">
                                                <div class="form-group" >
                                                    <label class="control-label col-sm-1 no-padding-right" for="name">名称</label>
                                                    <div class="col-sm-6">
                                                        <div class="clearfix">
                                                            <input id="consigneeCode" name="consigneeCode" type="hidden">
                                                            <input id="consigneeType" name="consigneeType" type="hidden">
                                                            <input id="consigneeName"  name="consigneeName" type="text" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" >

                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="form-group" >
                                                    <label class="control-label col-sm-1 no-padding-right" for="name">联系人</label>
                                                    <div class="col-sm-6">
                                                        <div class="clearfix">
                                                            <input id="consigneeContactCode" name="consigneeContactCode" type="hidden" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" >
                                                            <input id="consigneeContactName" name="consigneeContactName" type="text" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" >

                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="form-group" >
                                                    <label class="control-label col-sm-1 no-padding-right" for="name">联系电话</label>
                                                    <div class="col-sm-6">
                                                        <div class="clearfix">
                                                            <input id="consigneePhone" name="consigneePhone" type="text" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" >

                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="form-group" >
                                                    <label class="control-label col-sm-1 no-padding-right" for="name">地址选择</label>
                                                    <div class="col-sm-6">
                                                        <div class="clearfix">
                                                            <span style="cursor:pointer" id="city-picker-consignee"><input id="city-picker3-consignee" class="form-control input-sm" readonly type="text" value="" data-toggle="city-picker"></span>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="form-group" >
                                                    <label class="control-label col-sm-1 no-padding-right" for="name">详细地址</label>
                                                    <div class="col-sm-6">
                                                        <div class="clearfix">
                                                            <input id="consigneeAddress" name="consigneeAddress" type="text" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" >
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        </div>
                                    </div>
                                </div>

                                <!-- /section:elements.tab.option -->
                            </div>
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

    function validateForm() {
        var ofc_url = $("#ofc_url").html();
        $('#orderFundamentalFormValidate').validate({
            errorElement : 'div',
            errorClass : 'help-block',
            focusInvalid : false,
            ignore : "",
            rules : {
                merchandiser:{
                    required:true
                },
                orderTime:{
                    required:true
                },
                transCode:{
                    maxlength:50,
                    remote:{
                        url : ofc_url + "/ofc/checkTransCode",
                        type : "POST",
                        dataType : "json",
                        data : {
                            transCode : function() {
                                return $("#transCode").val();
                            }
                        }
                    }
                },
                custName:{
                    maxlength:100
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
                merchandiser:{
                    required:"请填写开单员"
                },
                orderTime:{
                    required:"请填写订单日期"
                },
                transCode:{
                    maxlength: "超过最大长度50",
                    remote: "运输单号已存在"
                },
                custName:{
                    maxlength:"超过最大长度"
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
        $('#orderFinanceFormValidate').validate({//
            errorElement : 'div',
            errorClass : 'help-block',
            focusInvalid : false,
            ignore : "",
            rules : {
                luggage:{
                    maxlength: 9,
                    required:true,
                    pattern:/^([1-9][\d]{0,5}|0)(\.[\d]{1,2})?$/
                },
                homeDeliveryFee:{
                    maxlength: 9,
                    pattern:/^([1-9][\d]{0,5}|0)(\.[\d]{1,2})?$/
                },
                cargoInsuranceFee:{
                    maxlength: 9,
                    pattern:/^([1-9][\d]{0,5}|0)(\.[\d]{1,2})?$/
                },
                insureValue:{
                    maxlength: 9,
                    pattern:/^([1-9][\d]{0,5}|0)(\.[\d]{1,2})?$/
                },
                twoDistributionFee:{
                    maxlength: 9,
                    pattern:/^([1-9][\d]{0,5}|0)(\.[\d]{1,2})?$/
                },
                collectServiceCharge:{
                    maxlength: 9,
                    pattern:/^([1-9][\d]{0,5}|0)(\.[\d]{1,2})?$/
                },
                collectLoanAmount:{
                    maxlength: 9,
                    pattern:/^([1-9][\d]{0,5}|0)(\.[\d]{1,2})?$/
                },
                returnListFee:{
                    maxlength: 9,
                    pattern:/^([1-9][\d]{0,5}|0)(\.[\d]{1,2})?$/
                }
            },
            messages : {
                luggage:{
                    maxlength: "最大999999.99元",
                    required:"请填写运费",
                    pattern:"只能输入金额"
                },
                homeDeliveryFee:{
                    maxlength: "最大999999.99元",
                    pattern:"只能输入金额"
                },
                cargoInsuranceFee:{
                    maxlength: "最大999999.99元",
                    pattern:"只能输入金额"
                },
                insureValue:{
                    maxlength: "最大999999.99元",
                    pattern:"只能输入金额"
                },
                twoDistributionFee:{
                    maxlength: "最大999999.99元",
                    pattern:"只能输入金额"
                },
                collectServiceCharge:{
                    maxlength: "最大999999.99元",
                    pattern:"只能输入金额"
                },
                collectLoanAmount:{
                    maxlength: "最大999999.99元",
                    pattern:"只能输入金额"
                },
                returnListFee:{
                    maxlength: "最大999999.99元",
                    pattern:"只能输入金额"
                }
            },
            highlight : function(e) {
                $(e).parent().parent().removeClass('has-info').addClass('has-error');
            },
            success : function(e) {
                $(e).parent().removeClass('has-error').addClass('has-success');
                $(e).remove();
                countCostCheck();
            },
            errorPlacement : function(error, element) {
                error.insertAfter(element.parent().next());
                $(error).attr("align","center");
            },
            invalidHandler : function(form) {
            }
        });

        $('#orderFinanceChargeFormValidate').validate({//
            errorElement : 'div',
            errorClass : 'help-block',
            focusInvalid : false,
            ignore : "",
            rules : {
                currentAmount:{
                    maxlength: 9,
                    required:true,
                    pattern:/^([1-9][\d]{0,5}|0)(\.[\d]{1,2})?$/
                },
                toPayAmount:{
                    maxlength: 9,
                    required:true,
                    pattern:/^([1-9][\d]{0,5}|0)(\.[\d]{1,2})?$/
                },
                returnAmount:{
                    maxlength: 9,
                    required:true,
                    pattern:/^([1-9][\d]{0,5}|0)(\.[\d]{1,2})?$/
                },
                monthlyAmount:{
                    maxlength: 9,
                    required:true,
                    pattern:/^([1-9][\d]{0,5}|0)(\.[\d]{1,2})?$/
                },
                serviceCharge:{
                    required:true,
                    pattern:/^([1-9][\d]{0,6}|0)(\.[\d]{1,2})?$/
                }
            },
            messages : {
                currentAmount:{
                    maxlength: "最大999999.99元",
                    pattern:"只能输入金额"
                },
                toPayAmount:{
                    maxlength: "最大999999.99元",
                    pattern:"只能输入金额"
                },
                returnAmount:{
                    maxlength: "最大999999.99元",
                    pattern:"只能输入金额"
                },
                monthlyAmount:{
                    maxlength: "最大999999.99元",
                    pattern:"只能输入金额"
                },
                serviceCharge:{
                    required:"缺少费用总计",
                    pattern:"金额格式错误"
                }
            },
            highlight : function(e) {
                $(e).parent().parent().removeClass('has-info').addClass('has-error');
            },
            success : function(e) {
                //alert($(e).attr('id'));

                if(countSettlement()=="false"){
                    $(e).parent().removeClass('has-info').addClass('has-error');
                    $(e).html("结算方式的金额合计应于费用总计一致！");
                }else{
                    $("#monthlyAmount-error").parent().removeClass('has-error').addClass('has-success');
                    $("#currentAmount-error").parent().removeClass('has-error').addClass('has-success');
                    $("#toPayAmount-error").parent().removeClass('has-error').addClass('has-success');
                    $("#returnAmount-error").parent().removeClass('has-error').addClass('has-success');
                    $("#monthlyAmount-error").remove();
                    $("#currentAmount-error").remove();
                    $("#toPayAmount-error").remove();
                    $("#returnAmount-error").remove();
                }
            },
            errorPlacement : function(error, element) {
                error.insertAfter(element.parent().next());
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
                consignorName:{
                    required:true,
                    maxlength:10
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
                consigneeAddress:{
                    maxlength:200
                }
            },
            messages : {
                consignorName:{
                    required:"必须输入",
                    maxlength:"超过最大长度"
                },
                consignorContactName:{
                    required:"必须输入",
                    maxlength:"超过最大长度"
                },
                consignorPhone:{
                    isPhone:"请输入正确的手机号",
                    required:"必须输入",
                    maxlength:"超过最大长度"
                },
                consignorAddress:{
                    maxlength:"超过最大长度"
                },
                consigneeName:{
                    required:"必须输入",
                    maxlength:"超过最大长度"
                },
                consigneeContactName:{
                    required:"必须输入",
                    maxlength:"超过最大长度"
                },
                consigneePhone:{
                    isPhone:"请输入正确格式的手机号",
                    required:"必须输入",
                    maxlength:"超过最大长度"
                },
                consigneeAddress:{
                    maxlength:"超过最大长度"
                }
            },
            highlight : function(e) {

            },
            success : function(e) {

            },
            errorPlacement : function(error, element) {
                error.insertAfter(element.parent().next());
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
        countQuantityOrWeightOrCubageCheck();
    }
    function seleGoods(obj) {
        $(obj).attr("id","yangdongxushinanshen");
        $("#goodsListDiv").fadeIn("slow");//淡入淡出效果 显示div
    }
    function countQuantOrWeightOrCubage(obj) {
        if(!(/^([1-9][\d]{0,4}|0)(\.[\d]{1,3})?$/).test($(obj).val()) && $(obj).val()!=""){
            if($(obj).parent().children().length<2){
                $("<div id='price-error' class='help-block has-error'>请检查数字格式</div>").insertAfter($(obj));
                $(obj).parent().removeClass('has-info').addClass('has-error');
                $(obj).val("");
                countQuantityOrWeightOrCubageCheck();
            }else{
                $(obj).val("");
                countQuantityOrWeightOrCubageCheck();
            }
        }else{
            $(obj).parent().find("div").remove();
            countQuantityOrWeightOrCubageCheck();
        };
    }
    function countQuantityOrWeightOrCubagePrice(obj) {
        if(!(/^([1-9][\d]{0,5}|0)(\.[\d]{1,2})?$/).test($(obj).val()) && $(obj).val()!=""){
            //alert('只能输入数字，最大值不能超过999999.99元，不允许负数，可以为空或为0，小数点后只能保留两位');
            if($(obj).parent().children().length<2){
                $("<div id='price-error' class='help-block has-error'>请检查金额格式</div>").insertAfter($(obj));
                $(obj).parent().removeClass('has-info').addClass('has-error');
                $(obj).val("");
                countQuantityOrWeightOrCubageCheck();
            }else{
                $(obj).val("");
                countQuantityOrWeightOrCubageCheck();
            }
        }else{
            $(obj).parent().find("div").remove();
            countQuantityOrWeightOrCubageCheck();
        };
    }
    function countQuantityOrWeightOrCubageCheck() {
        var quantityCount=0;
        var weightCount=0;
        var luggage=0;
        var flg1="";
        var flg2="";
        var flg3="";
        $('input[name="quantity"]').each(function(){
            if($(this).val()!=""){
                quantityCount=quantityCount+parseFloat($(this).val());
                if($(this).parent().next().find('input').first().val()!="" && $(this).parent().next().find('input').first()!="0" && $(this).val()!="0"){
                    luggage=luggage+parseFloat($(this).val())*parseFloat($(this).parent().next().find('input').first().val());
                }else{
                    flg1="error";
                }
            }else{
                flg1="error";
            }

        });
        if(flg1==""){$("#luggage").val(luggage);luggage=0;flg2="true";flg3="true";}
        luggage=0;
        $('input[name="weight"]').each(function(){
            if($(this).val()!=""){
                //alert($(this).parent().next().find('input').first().attr("id"));
                weightCount=weightCount+parseFloat($(this).val());
                if(flg1=="error"){
                    if($(this).parent().next().find('input').first().val()!="" && $(this).parent().next().find('input').first()!="0" && $(this).val()!="0"){
                        luggage=luggage+parseFloat($(this).val())*parseFloat($(this).parent().next().find('input').first().val());
                    }else{
                        flg2="error";
                    }
                }
            }else{
                flg2="error";
            }
        });
        if(flg2==""){$("#luggage").val(luggage);luggage=0;flg2="true";flg3="true";}
        luggage=0;
        $('input[name="cubage"]').each(function(){
            if($(this).val()!=""){
                if(flg1=="error" && flg2=="error"){
                    if($(this).parent().next().find('input').first().val()!="" && $(this).parent().next().find('input').first()!="0" && $(this).val()!="0"){
                        luggage=luggage+parseFloat($(this).val())*parseFloat($(this).parent().next().find('input').first().val());
                    }else{
                        flg3="error";
                    }
                }
            }else{
                flg3="error";
            }
        });
        if(flg3==""){$("#luggage").val(luggage);luggage=0;}
        if(flg1=="error" && flg2=="error" && flg3=="error"){$("#luggage").val(0);}
        countCostCheck();
        $("#weightCount").html(weightCount);
        $("#quantityCount").html(quantityCount);
    }
    function countCostCheck() {
        var count=0;
        if($("#homeDeliveryFee").val()!=""){
            count=count+parseFloat($("#homeDeliveryFee").val());
        }
        if($("#cargoInsuranceFee").val()!=""){
            count=count+parseFloat($("#cargoInsuranceFee").val());
        }
        if($("#insureValue").val()!=""){
            count=count+parseFloat($("#insureValue").val());
        }
        if($("#twoDistributionFee").val()!=""){
            count=count+parseFloat($("#twoDistributionFee").val());
        }
        if($("#collectServiceCharge").val()!=""){
            count=count+parseFloat($("#collectServiceCharge").val());
        }
        if($("#collectLoanAmount").val()!=""){
            count=count+parseFloat($("#collectLoanAmount").val());
        }
        if($("#returnListFee").val()!=""){
            count=count+parseFloat($("#returnListFee").val());
        }
        if($("#luggage").val()!=""){
            count=count+parseFloat($("#luggage").val());
        }
        if((/^([1-9][\d]{0,5}|0)(\.[\d]{1,2})?$/).test(count)){
            $("#serviceCharge").val(count);
            $("#currentAmount").val(count);
            $("#toPayAmount").val(0);
            $("#returnAmount").val(0);
            $("#monthlyAmount").val(0);
            $("#monthlyAmount-error").parent().removeClass('has-error').addClass('has-success');
            $("#currentAmount-error").parent().removeClass('has-error').addClass('has-success');
            $("#toPayAmount-error").parent().removeClass('has-error').addClass('has-success');
            $("#returnAmount-error").parent().removeClass('has-error').addClass('has-success');
            $("#monthlyAmount-error").remove();
            $("#currentAmount-error").remove();
            $("#toPayAmount-error").remove();
            $("#returnAmount-error").remove();
        }

    }

    function countSettlement(){
        var count=0;
        if($("#currentAmount").val()!=""){
            /*if(!(/^([1-9][\d]{0,5}|0)(\.[\d]{1,2})?$/).test($("#currentAmount").val())){
                alert('只能输入数字，最大值不能超过999999.99元，不允许负数，可以为空或为0，小数点后只能保留两位');
                $("#currentAmount").val(0);
            }else{*/
            count=count+parseFloat($("#currentAmount").val());
            //};
        }
        if($("#toPayAmount").val()!=""){
            count=count+parseFloat($("#toPayAmount").val());
        }
        if($("#returnAmount").val()!=""){
            count=count+parseFloat($("#returnAmount").val());
        }
        if($("#monthlyAmount").val()!=""){
            count=count+parseFloat($("#monthlyAmount").val());
        }
        if(count-parseFloat($("#serviceCharge").val())!=0){
            return "false";
        }
    }

    function orderFinanceInfo(jsonStr) {
        //服务项目及费用
        jsonStr.homeDeliveryFee = $("#homeDeliveryFee").val();
        jsonStr.cargoInsuranceFee = $("#cargoInsuranceFee").val();
        jsonStr.insureValue = $("#insureValue").val();
        jsonStr.twoDistributionFee = $("#twoDistributionFee").val();
        jsonStr.collectServiceCharge = $("#collectServiceCharge").val();
        jsonStr.collectLoanAmount = $("#collectLoanAmount").val();
        jsonStr.returnListFee = $("#returnListFee").val();
        jsonStr.luggage = $("#luggage").val();
        //费用总计
        jsonStr.serviceCharge = $("#serviceCharge").val();
        jsonStr.expensePaymentParty = $("input[name=expensePaymentPartyV]:checked").val();
        jsonStr.payment = $("#payment").val();
        jsonStr.currentAmount = $("#currentAmount").val();
        jsonStr.toPayAmount = $("#toPayAmount").val();
        jsonStr.returnAmount = $("#returnAmount").val();
        jsonStr.monthlyAmount = $("#monthlyAmount").val();
        return jsonStr;
    }

    function orderPlaceAddTranInfo(jsonStr) {
        //收发货方信息
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

    function getCscContantAndCompanyDtoConsignorStr() {
        var paramConsignor = {};
        var paramConsignee = {};
        var cscContact = {};
        var cscContactCompany = {};
        cscContactCompany.contactCompanyName = $("#consignorName").val();
        cscContact.contactName = $("#consignorContactName").val();
        cscContact.purpose = "2";
        cscContact.phone = $("#consignorPhone").val();

        cscContact.contactCompanyId = $("#consignorCode").val();
        cscContact.contactCode = $("#consignorContactCode").val();
        cscContactCompany.type = $("#consignorType").val();
        //cscContactCompany.id = $("#consignorCode").val();
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
        cscContact.purpose = "1";
        cscContact.phone = $("#consigneePhone").val();

        cscContact.contactCompanyId = $("#consigneeCode").val();
        cscContact.contactCode = $("#consigneeContactCode").val();
        cscContactCompany.type = $("#consigneeType").val();

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
        console.log("function  consignee " + cscContantAndCompanyDtoConsigneeStr);
        return cscContantAndCompanyDtoConsigneeStr;
    }

    //校验是否选了客户
    function validateCustChosen() {
        var custChosen = $("#custName").val();
        if(""==custChosen){
            return false;
        }else{
            return true;
        }
    }

    //出发地
    function departurePlace() {
        var consignorAddressMessage = $("#city-picker3-consignor").val().split('~');
        var consignorAddressCodeMessage = consignorAddressMessage[1].split(',');
        var consignorAddressNameMessage = consignorAddressMessage[0].split('/');
        var departure_place="";
        if(!StringUtil.isEmpty(consignorAddressNameMessage[0])){
            departure_place =departure_place + consignorAddressNameMessage[0];
        }
        if(!StringUtil.isEmpty(consignorAddressNameMessage[1])){
            departure_place =departure_place + consignorAddressNameMessage[1];
        }
        if(!StringUtil.isEmpty(consignorAddressNameMessage[2])){
            departure_place =departure_place + consignorAddressNameMessage[2];
        }
        if(!StringUtil.isEmpty(consignorAddressNameMessage[3])){
            departure_place =departure_place + consignorAddressNameMessage[3];
        }
        $("#departure_place").html(departure_place);
    }

    //目的地
    function destination() {
        var consignorAddressMessage = $("#city-picker3-consignee").val().split('~');
        var consignorAddressCodeMessage = consignorAddressMessage[1].split(',');
        var consignorAddressNameMessage = consignorAddressMessage[0].split('/');
        var destination="";
        if(!StringUtil.isEmpty(consignorAddressNameMessage[0])){
            destination =destination + consignorAddressNameMessage[0];
        }
        if(!StringUtil.isEmpty(consignorAddressNameMessage[1])){
            destination =destination + consignorAddressNameMessage[1];
        }
        if(!StringUtil.isEmpty(consignorAddressNameMessage[2])){
            destination =destination + consignorAddressNameMessage[2];
        }
        if(!StringUtil.isEmpty(consignorAddressNameMessage[3])){
            destination =destination + consignorAddressNameMessage[3];
        }
        $("#destination").html(destination);
    }



    $(function(){

        $("#pickUpGoodsV").change(function(){
            if($(this).prop("checked")){
                $("#pickUpGoods").val("1");
                $("#homeDeliveryFee").removeAttr("disabled");//要变成Enable，JQuery只能这么写
            }else{
                $("#pickUpGoods").val("0");
                $("#homeDeliveryFee").val("");
                $("#homeDeliveryFee").attr("disabled","disabled");
                countCostCheck();
            }

        });
        $("#insureV").change(function(){
            if($(this).prop("checked")){
                $("#insure").val("1");
                $("#cargoInsuranceFee").removeAttr("disabled");//要变成Enable，JQuery只能这么写
                $("#insureValue").removeAttr("disabled");//要变成Enable，JQuery只能这么写
            }else{
                $("#insure").val("0");
                $("#cargoInsuranceFee").attr("disabled","disabled");
                $("#cargoInsuranceFee").val("");
                $("#insureValue").attr("disabled","disabled");
                $("#insureValue").val("");
                countCostCheck();
            }

        });
        $("#twoDistributionV").change(function(){
            if($(this).prop("checked")){
                $("#twoDistribution").val("1");
                $("#twoDistributionFee").removeAttr("disabled");//要变成Enable，JQuery只能这么写
            }else{
                $("#twoDistribution").val("0");
                $("#twoDistributionFee").attr("disabled","disabled");
                $("#twoDistributionFee").val("");
                countCostCheck();
            }

        });
        $("#collectFlagV").change(function(){
            if($(this).prop("checked")){
                $("#collectFlag").val("1");
                $("#collectServiceCharge").removeAttr("disabled");//要变成Enable，JQuery只能这么写
                $("#collectLoanAmount").removeAttr("disabled");//要变成Enable，JQuery只能这么写
            }else{
                $("#collectFlag").val("0");
                $("#collectServiceCharge").attr("disabled","disabled");
                $("#collectServiceCharge").val("");
                $("#collectLoanAmount").attr("disabled","disabled");
                $("#collectLoanAmount").val("");
                countCostCheck();
            }

        });
        $("#returnListV").change(function(){
            if($(this).prop("checked")){
                $("#returnList").val("1");
                $("#returnListFee").removeAttr("disabled");//要变成Enable，JQuery只能这么写
            }else{
                $("#returnList").val("0");
                $("#returnListFee").attr("disabled","disabled");
                $("#returnListFee").val("");
                countCostCheck();
            }

        });

        $("#orderPlaceConTableBtn").click(function () {
            var jsonStr = {};
            //订单基本信息
            jsonStr.businessType = $("#businessType").val();
            jsonStr.merchandiser = $("#merchandiser").val();
            jsonStr.transportType = $("input[name=transportTypeV]:checked").val();
            jsonStr.orderTime = $dp.$('orderTime').value;
            jsonStr.transCode = $("#transCode").val();
            jsonStr.custName = $("#custName").val();
            jsonStr=orderFinanceInfo(jsonStr);
            jsonStr=orderPlaceAddTranInfo(jsonStr);
            //货品添加
            var orderGoodsList = [];
            var goodsTable = document.getElementById("orderGoodsListTable");
            for(var tableRows = 1; tableRows < goodsTable.rows.length; tableRows ++ ){
                var orderGoods = {};
                for(var tableCells = 1; tableCells < goodsTable.rows[tableRows].cells.length; tableCells ++){
                    var param = goodsTable.rows[tableRows].cells[tableCells];
                    switch (tableCells){
                        case 1 :orderGoods.goodsCategory = param.getElementsByTagName("select")[0].value;break;
                        case 2 :orderGoods.goodsCode = param.getElementsByTagName("input")[0].value;break;
                        case 3 :orderGoods.goodsName = param.getElementsByTagName("input")[0].value;break;
                        case 4 :orderGoods.goodsSpec = param.getElementsByTagName("input")[0].value;break;
                        case 5 :orderGoods.unit = param.getElementsByTagName("input")[0].value;break;
                        case 6 :orderGoods.pack = param.getElementsByTagName("select")[0].value;break;
                        case 7 :orderGoods.quantity = param.getElementsByTagName("input")[0].value;break;
                        case 8 :orderGoods.quantityUnitPrice = param.getElementsByTagName("input")[0].value;break;
                        case 9 :orderGoods.weight =  param.getElementsByTagName("input")[0].value;break;
                        case 10 :orderGoods.weightUnitPrice = param.getElementsByTagName("input")[0].value;break;
                        case 11 :orderGoods.cubage = param.getElementsByTagName("input")[0].value;break;
                        case 12 :orderGoods.volumeUnitPrice = param.getElementsByTagName("input")[0].value;break;
                    }
                }
                orderGoodsList[tableRows - 1] = orderGoods;
            }
            var tag = "tranplace";
            var ofcOrderDTO = JSON.stringify(jsonStr);
            var orderGoodsListStr = JSON.stringify(orderGoodsList);
            var cscContantAndCompanyDtoConsignorStr;
            var cscContantAndCompanyDtoConsigneeStr;
            console.log("======orderGoodsListStr======"+orderGoodsListStr)
            cscContantAndCompanyDtoConsignorStr = getCscContantAndCompanyDtoConsignorStr();
            cscContantAndCompanyDtoConsigneeStr = getCscContantAndCompanyDtoConsigneeStr();
            debugger;
            xescm.common.submit("/ofc/orderPlaceCon"
                    ,{"ofcOrderDTOStr":ofcOrderDTO
                        ,"orderGoodsListStr":orderGoodsListStr+"~`"
                        ,"cscContantAndCompanyDtoConsignorStr":cscContantAndCompanyDtoConsignorStr
                        ,"cscContantAndCompanyDtoConsigneeStr":cscContantAndCompanyDtoConsigneeStr
                        ,"cscSupplierInfoDtoStr":null
                        ,"tag":tag}
                    ,"您确认提交订单吗?"
                    ,function () {

                        //xescm.common.goBack("/ofc/orderPlace");
                    });

        });

        $("#custListDivBlock").click(function () {
            $("#custListDiv").fadeIn("slow");//淡入淡出效果 显示div
        });
        $("#custListDivNoneBottom").click(function () {
            $("#custListDiv").fadeOut("slow");//淡入淡出效果 隐藏div
        });
        $("#custListDivNoneTop").click(function () {
            $("#custListDiv").fadeOut("slow");//淡入淡出效果 隐藏div
        });

        $("#custSelectFormBtn").click(function () {
            var custName = $("#custNameDiv").val();
            CommonClient.post(sys.rootPath + "/ofc/distributing/queryCustomerByName", {"queryCustomerName":custName,"currPage":"1"}, function(data) {
                data=eval(data);
                var custList = "";
                $.each(data,function (index,cscCustomerVo) {
                    var channel = cscCustomerVo.channel;
                    if(null == channel){
                        channel = "";
                    }
                    custList =custList + "<tr role='row' class='odd'>";
                    custList =custList + "<td class='center'> "+"<label class='pos-rel'>"+"<input name='' type='radio' class='ace'>"+"<span class='lbl'></span>"+"</label>"+"</td>";
                    custList =custList + "<td>"+(index+1)+"</td>";
                    custList =custList + "<td>"+cscCustomerVo.type+"</td>";
                    custList =custList + "<td>"+cscCustomerVo.customerName+"</td>";
                    custList =custList + "<td>"+channel+"</td>";
                    custList =custList + "<td>"+cscCustomerVo.productType+"</td>";
                    custList =custList + "<td style='display: none'>"+cscCustomerVo.groupId+"</td>";
                    custList =custList + "<td style='display: none'>"+cscCustomerVo.id+"</td>";
                    custList =custList + "</tr>";
                    $("#custListDivTbody").html(custList);
                });
            },"json");

        });

        $("#custEnter").click(function () {
            var custEnterTag = "";
            $("#custListDivTbody").find("tr").each(function(index){
                var tdArr = $(this).children();
                if(tdArr.eq(0).find("input").prop("checked")){
                    custEnterTag = "1";
                    var type = tdArr.eq(2).text();//类型
                    var customerName = tdArr.eq(3).text();//公司名称
                    var channel = tdArr.eq(4).text();//    渠道
                    var productType = tdArr.eq(5).text();//    产品类别
                    var groupId = tdArr.eq(6).text();//    产品类别
                    var custId = tdArr.eq(7).text();//    产品类别
                    $("#custName").val(customerName);
                    $("#custGroupId").val(groupId);
                    $("#custId").val(custId);
                }
            });
            if(custEnterTag==""){
                alert("请至少选择一行");
            }else{
                $("#custListDiv").fadeOut("slow");//淡入淡出效果 隐藏div
            }
        });

        $("#city-picker-consignor").click(function () {
            departurePlace();
        });

        $("#city-picker-consignee").click(function () {
            destination();
        });

        $("#goodsSelectFormBtn").click(function () {
            CommonClient.post(sys.rootPath + "/ofc/goodsSelect", $("#goodsSelConditionForm").serialize(), function(data) {
                data=eval(data);

                var goodsList = "";
                $.each(data,function (index,cscGoodsVo) {
                    goodsList =goodsList + "<tr role='row' class='odd'>";
                    goodsList =goodsList + "<td class='center'> "+"<label class='pos-rel'>"+"<input type='radio' class='ace' name='goodse'>"+"<span class='lbl'></span>"+"</label>"+"</td>";
                    goodsList =goodsList + "<td>"+cscGoodsVo.parentGoodsType+"</td>";//货品种类
                    goodsList =goodsList + "<td>"+cscGoodsVo.goodsTypeName+"</td>";//货品小类
                    goodsList =goodsList + "<td>"+cscGoodsVo.brand+"</td>";//品牌
                    goodsList =goodsList + "<td>"+cscGoodsVo.goodsCode+"</td>";//货品编码
                    goodsList =goodsList + "<td>"+cscGoodsVo.goodsName+"</td>";//货品名称
                    goodsList =goodsList + "<td>"+cscGoodsVo.specification+"</td>";//规格
                    goodsList =goodsList + "<td>"+cscGoodsVo.unit+"</td>";//单位
                    goodsList =goodsList + "<td>"+cscGoodsVo.barCode+"</td>";//条形码
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


            var groupId = $("#custGroupId").val();
            var custId = $("#custId").val();

            var param = JSON.stringify(cscContantAndCompanyDto);
            CommonClient.post(sys.rootPath + "/ofc/contactSelect",{"cscContantAndCompanyDto":param,"groupId":groupId,"custId":custId}, function(data) {
                data=eval(data);
                var contactList = "";
                $.each(data,function (index,CscContantAndCompanyDto) {
                    /*consignorCodeHide = CscContantAndCompanyDto.contactCompanyId;
                    consignorContactCodeHide = CscContantAndCompanyDto.contactCode;
                    consignorTypeHide = CscContantAndCompanyDto.type;*/
                    contactList =contactList + "<tr role='row' class='odd'>";
                    contactList =contactList + "<td class='center'> "+"<label class='pos-rel'>"+"<input name='consignorSel' type='radio' class='ace'>"+"<span class='lbl'></span>"+"</label>"+"</td>";
                    contactList =contactList + "<td>"+(index+1)+"</td>";
                    contactList =contactList + "<td>"+CscContantAndCompanyDto.contactCompanyName+"</td>";
                    contactList =contactList + "<td>"+CscContantAndCompanyDto.contactName+"</td>";
                    contactList =contactList + "<td>"+CscContantAndCompanyDto.phone+"</td>";
                    contactList =contactList + "<td>"+CscContantAndCompanyDto.detailAddress+"</td>";
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

            var groupId = $("#custGroupId").val();
            var custId = $("#custId").val();

            var param = JSON.stringify(cscContantAndCompanyDto);
            CommonClient.post(sys.rootPath + "/ofc/contactSelect", {"cscContantAndCompanyDto":param,"groupId":groupId,"custId":custId}, function(data) {
                data=eval(data);
                var contactList = "";
                $.each(data,function (index,CscContantAndCompanyDto) {
                    /*consigneeCodeHide = CscContantAndCompanyDto.contactCompanyId;
                    consigneeContactCodeHide = CscContantAndCompanyDto.contactCode;
                    consigneeTypeHide = CscContantAndCompanyDto.type;*/
                    contactList =contactList + "<tr role='row' class='odd'>";
                    contactList =contactList + "<td class='center'> "+"<label class='pos-rel'>"+"<input name='consigneeSel' type='radio' class='ace'>"+"<span class='lbl'></span>"+"</label>"+"</td>";
                    contactList =contactList + "<td>"+(index+1)+"</td>";
                    contactList =contactList + "<td>"+CscContantAndCompanyDto.contactCompanyName+"</td>";
                    contactList =contactList + "<td>"+CscContantAndCompanyDto.contactName+"</td>";
                    contactList =contactList + "<td>"+CscContantAndCompanyDto.phone+"</td>";
                    contactList =contactList + "<td>"+CscContantAndCompanyDto.detailAddress+"</td>";
                    contactList =contactList + "</tr>";
                    $("#contactSelectListTbody1").html(contactList);
                });
            },"json");
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
                    var address = tdArr.eq(5).text();//    邮编

                    $("#consignorName").val(consignorName);
                    $("#consignorContactName").val(contacts);
                    $("#consignorPhone").val(contactsNumber);
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
                            $("#consignorCode").val(CscContantAndCompanyDto.contactCompanyId);
                            $("#consignorContactCode").val(CscContantAndCompanyDto.contactCode);
                            $("#consignorType").val(CscContantAndCompanyDto.type);
                            $("#consignorAddress").val(CscContantAndCompanyDto.address);
                            console.log("consignorCode'val()=  inininin  ="+$("#consignorCode").val());
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
                            departurePlace();
                        });
                    });

                }
            });
            if(consignorin==""){
                alert("请至少选择一行");
            }else{
                $("#consignorListDiv").fadeOut("slow");//淡入淡出效果 隐藏div
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
                    var address = tdArr.eq(5).text();//    地址
                    $("#consigneeName").val(consignorName);
                    $("#consigneeContactName").val(contacts);
                    $("#consigneePhone").val(contactsNumber);
                    $("#consigneeAddress").val(address);

                    debugger;
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
                        console.log("-=-=-=-------234-ee--------111-11"+JSON.stringify(data));
                        $.each(data,function (index,CscContantAndCompanyDto) {
                            $("#consigneeCode").val(CscContantAndCompanyDto.contactCompanyId);
                            $("#consigneeContactCode").val(CscContantAndCompanyDto.contactCode);
                            $("#consigneeType").val(CscContantAndCompanyDto.type);
                            $("#consigneeAddress").val(CscContantAndCompanyDto.address);
                            debugger;
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
                            destination();
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

        $("#consignorListDivBlock").click(function(){
            if(!validateCustChosen()){
                alert("请先选择客户")
            }else{
                $("#consignorListDiv").fadeIn("slow");//淡入淡出效果 显示div
            }
        });

        $("#consignorListDivNoneTop").click(function(){

            $("#consignorListDiv").fadeOut("slow");//淡入淡出效果 隐藏div

        });

        $("#consignorListDivNoneBottom").click(function(){

            $("#consignorListDiv").fadeOut("slow");//淡入淡出效果 隐藏div

        });

        $("#consigneeListDivBlock").click(function(){
            if(!validateCustChosen()){
                alert("请先选择客户")
            }else{
                $("#consigneeListDiv").fadeIn("slow");//淡入淡出效果 显示div
            }

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

        $("input[name=transportTypeV]").change(function () {
            $("#transportType").val($("input[name=transportTypeV]:checked").val());
        });
        $("input[name=expensePaymentPartyV]").change(function () {
            $("#expensePaymentParty").val($("input[name=expensePaymentPartyV]:checked").val());
        });

        $("#addGoods").click(function () {
            var goodsInfoListDiv = "";
            goodsInfoListDiv = goodsInfoListDiv + "<tr role='row' class='odd' align='center'>";
            goodsInfoListDiv = goodsInfoListDiv + "<td><button type='button' onclick='deleteGood(this)' class='btn btn-minier btn-danger'>删除</button></td>";
            goodsInfoListDiv = goodsInfoListDiv + "<td>"+
                    "<select  id='goodsCategory' name='goodsCategory'>";
            if($("#goodsInfoListDiv").find("tr").length<1){
                    CommonClient.post(sys.rootPath + "/ofc/goodsSelect", null, function(data) {
                        data=eval(data);
                        $.each(data,function (index,cscGoodsVo) {
                            goodsInfoListDiv = goodsInfoListDiv + "<option value='" + cscGoodsVo.goodsTypeName + "'>" + cscGoodsVo.goodsTypeName + "</option>";
                        });
                        goodsInfoListDiv = goodsInfoListDiv+"</select></td>";
                        goodsInfoListDiv = goodsInfoListDiv + "<td>"+
                                "<input class='col-xs-10 col-xs-6'  name='goodsCode' id='goodsCode' type='text'/>"+
                                "<button type='button' class='btn btn-minier btn-inverse no-padding-right' style='display:inline-block' id='goodCodeSel' onclick='seleGoods(this)'>选择</button>"
                                +"</td>";
                        goodsInfoListDiv = goodsInfoListDiv + "<td>"+
                                "<input class='col-xs-10 col-xs-12'  name='goodsName' id='goodsName' type='text'/>"
                                +"</td>";
                        goodsInfoListDiv = goodsInfoListDiv + "<td>"+
                                "<input class='col-xs-10 col-xs-12'  name='goodsSpec' id='goodsSpec' type='text'/>"
                                +"</td>";
                        goodsInfoListDiv = goodsInfoListDiv + "<td>"+
                                "<input class='col-xs-10 col-xs-12'  name='unit' id='unit' type='text'/>"
                                +"</td>";
                        goodsInfoListDiv = goodsInfoListDiv + "<td>"+
                                "<select  id='pack' name='pack'>"+
                                "<option value='01'>纸箱</option>"+
                                "<option value='02'>木箱</option>"+
                                "<option value='03'>桶</option>"+
                                "<option value='04'>混包</option>"+
                                "<option value='05'>裸装</option>"+
                                "<option value='06'>编袋</option>"+
                                "<option value='07'>托盘</option>"+
                                "<option value='08'>木框架</option>"+
                                "<option value='09'>泡沫箱</option>"+
                                "<option value='10'>缠绕膜</option>"+
                                "<option value='11'>盘</option>"+
                                "<option value='12'>铁框</option>"+
                                "<option value='13'>布袋</option></select>"
                                +"</td>";
                        goodsInfoListDiv = goodsInfoListDiv + "<td>"+
                                "<input class='col-xs-10 col-xs-12'  name='quantity' id='quantity' type='text' onblur='countQuantOrWeightOrCubage(this)'/>"
                                +"</td>";
                        goodsInfoListDiv = goodsInfoListDiv + "<td>"+
                                "<input class='col-xs-10 col-xs-12'  name='quantityUnitPrice' id='quantityUnitPrice' type='text' onblur='countQuantityOrWeightOrCubagePrice(this)'/>"
                                +"</td>";
                        goodsInfoListDiv = goodsInfoListDiv + "<td id='1'>"+
                                "<input class='col-xs-10 col-xs-12'  name='weight' id='weight' type='text' onblur='countQuantOrWeightOrCubage(this)'/>"
                                +"</td>";
                        goodsInfoListDiv = goodsInfoListDiv + "<td id='2'>"+
                                "<input class='col-xs-10 col-xs-12'  name='weightUnitPrice' id='weightUnitPrice' type='text' onblur='countQuantityOrWeightOrCubagePrice(this)'/>"
                                +"</td>";
                        goodsInfoListDiv = goodsInfoListDiv + "<td>"+
                                "<input class='col-xs-10 col-xs-12'  name='cubage' id='cubage' type='text' onblur='countQuantOrWeightOrCubage(this)'/>"
                                +"</td>";
                        goodsInfoListDiv = goodsInfoListDiv + "<td>"+
                                "<input class='col-xs-10 col-xs-12'  name='volumeUnitPrice' id='volumeUnitPrice' type='text' onblur='countQuantityOrWeightOrCubagePrice(this)'/>"
                                +"</td>";
                        goodsInfoListDiv = goodsInfoListDiv + "</tr>";
                        $("#goodsInfoListDiv").append(goodsInfoListDiv);
                        if($("#goodsInfoListDiv").find("tr").length==1){
                            $("select option").each(function() {
                                text = $(this).text();
                                if($("select option:contains("+text+")").length > 1)
                                    $("select option:contains("+text+"):gt(0)").remove();
                            });
                        }
                    });
            }else{
                $("#goodsInfoListDiv tr:first-child").children().eq(1).find("select:first").find("option").each(function() {
                    text = $(this).text();
                    goodsInfoListDiv = goodsInfoListDiv +"<option value='"+text+"'>"+text+"</option>";
                });
                goodsInfoListDiv = goodsInfoListDiv+"</select></td>";
                goodsInfoListDiv = goodsInfoListDiv + "<td>"+
                        "<input class='col-xs-10 col-xs-6'  name='goodsCode' id='goodsCode' type='text'/>"+
                        "<button type='button' class='btn btn-minier btn-inverse no-padding-right' style='display:inline-block' id='goodCodeSel' onclick='seleGoods(this)'>选择</button>"
                        +"</td>";
                goodsInfoListDiv = goodsInfoListDiv + "<td>"+
                        "<input class='col-xs-10 col-xs-12'  name='goodsName' id='goodsName' type='text'/>"
                        +"</td>";
                goodsInfoListDiv = goodsInfoListDiv + "<td>"+
                        "<input class='col-xs-10 col-xs-12'  name='goodsSpec' id='goodsSpec' type='text'/>"
                        +"</td>";
                goodsInfoListDiv = goodsInfoListDiv + "<td>"+
                        "<input class='col-xs-10 col-xs-12'  name='unit' id='unit' type='text'/>"
                        +"</td>";
                goodsInfoListDiv = goodsInfoListDiv + "<td>"+
                        "<select  id='pack' name='pack'>"+
                        "<option value='01'>纸箱</option>"+
                        "<option value='02'>木箱</option>"+
                        "<option value='03'>桶</option>"+
                        "<option value='04'>混包</option>"+
                        "<option value='05'>裸装</option>"+
                        "<option value='06'>编袋</option>"+
                        "<option value='07'>托盘</option>"+
                        "<option value='08'>木框架</option>"+
                        "<option value='09'>泡沫箱</option>"+
                        "<option value='10'>缠绕膜</option>"+
                        "<option value='11'>盘</option>"+
                        "<option value='12'>铁框</option>"+
                        "<option value='13'>布袋</option></select>"
                        +"</td>";
                goodsInfoListDiv = goodsInfoListDiv + "<td>"+
                        "<input class='col-xs-10 col-xs-12'  name='quantity' id='quantity' type='text' onblur='countQuantOrWeightOrCubage(this)'/>"
                        +"</td>";
                goodsInfoListDiv = goodsInfoListDiv + "<td>"+
                        "<input class='col-xs-10 col-xs-12'  name='quantityUnitPrice' id='quantityUnitPrice' type='text' onblur='countQuantityOrWeightOrCubagePrice(this)'/>"
                        +"</td>";
                goodsInfoListDiv = goodsInfoListDiv + "<td id='1'>"+
                        "<input class='col-xs-10 col-xs-12'  name='weight' id='weight' type='text' onblur='countQuantOrWeightOrCubage(this)'/>"
                        +"</td>";
                goodsInfoListDiv = goodsInfoListDiv + "<td id='2'>"+
                        "<input class='col-xs-10 col-xs-12'  name='weightUnitPrice' id='weightUnitPrice' type='text' onblur='countQuantityOrWeightOrCubagePrice(this)'/>"
                        +"</td>";
                goodsInfoListDiv = goodsInfoListDiv + "<td>"+
                        "<input class='col-xs-10 col-xs-12'  name='cubage' id='cubage' type='text' onblur='countQuantOrWeightOrCubage(this)'/>"
                        +"</td>";
                goodsInfoListDiv = goodsInfoListDiv + "<td>"+
                        "<input class='col-xs-10 col-xs-12'  name='volumeUnitPrice' id='volumeUnitPrice' type='text' onblur='countQuantityOrWeightOrCubagePrice(this)'/>"
                        +"</td>";
                goodsInfoListDiv = goodsInfoListDiv + "</tr>";
                $("#goodsInfoListDiv").append(goodsInfoListDiv);
            }



        });

        $("#consignorName,#consigneeName").blur(function () {
            if($(this).val().length>100){
                if($(this).parent().children().length==3){
                    $("<div id='price-error' class='help-block has-error'>超过最大长度</div>").insertAfter($(this));
                    $(this).parent().parent().parent().removeClass('has-info').addClass('has-error');
                }
            }else{
                $(this).parent().find("div").remove();
                $(this).parent().parent().parent().removeClass('has-error').addClass('has-success');
            }
        });
        $("#consignorContactName,#consigneeContactName").blur(function () {
            if($(this).val().length>20){
                if($(this).parent().children().length==2){
                    $("<div id='price-error' class='help-block has-error'>超过最大长度</div>").insertAfter($(this));
                    $(this).parent().parent().parent().removeClass('has-info').addClass('has-error');
                }
            }else{
                $(this).parent().find("div").remove();
                $(this).parent().parent().parent().removeClass('has-error').addClass('has-success');
            }
        });
        $("#consignorPhone,#consigneePhone").blur(function () {
            if($(this).val().length>20){
                if($(this).parent().children().length==1){
                    $("<div id='price-error' class='help-block has-error'>超过最大长度</div>").insertAfter($(this));
                    $(this).parent().parent().parent().removeClass('has-info').addClass('has-error');
                }
            }else if(!(/^(-?[1-9]\d*|0)$/.test($(this).val()))){
                if($(this).parent().children().length==1){
                    $("<div id='price-error' class='help-block has-error'>电话格式错误</div>").insertAfter($(this));
                    $(this).parent().parent().parent().removeClass('has-info').addClass('has-error');
                }
            }else{
                $(this).parent().find("div").remove();
                $(this).parent().parent().parent().removeClass('has-error').addClass('has-success');
            }
        });

        $("#goodsListDivNoneTop").click(function(){

            $("#goodsListDiv").fadeOut("slow");//淡入淡出效果 隐藏div

        });

        $("#goodsListDivNoneBottom").click(function(){

            $("#goodsListDiv").fadeOut("slow");//淡入淡出效果 隐藏div

        });

        $("#goodsEnter").click(function () {
            var goodsInfoListDiv = "";
            $("#goodsSelectListTbody").find("tr").each(function(index){
                var tdArr = $(this).children();
                if(tdArr.eq(0).find("input").prop("checked")){
                    var goodsGate = tdArr.eq(1).text();//货品类别
                    var goodsCode = tdArr.eq(4).text();//货品编码
                    var goodsName = tdArr.eq(5).text();//货品名称
                    var specification = tdArr.eq(6).text();//规格
                    var unit = tdArr.eq(7).text();//单位
                    $("#yangdongxushinanshen").parent().parent().find("td").eq(1).find("select").val(goodsGate);
                    $("#yangdongxushinanshen").parent().parent().find("td").eq(2).find("input").val(goodsCode);
                    $("#yangdongxushinanshen").parent().parent().find("td").eq(3).find("input").val(goodsName);
                    $("#yangdongxushinanshen").parent().parent().find("td").eq(4).find("input").val(specification);
                    $("#yangdongxushinanshen").parent().parent().find("td").eq(5).find("input").val(unit);
                    $("#yangdongxushinanshen").attr("id","goodCodeSel");
                    goodsInfoListDiv="true";
                }
            });
            if(goodsInfoListDiv==""){
                alert("请至少选择一行");
            }else{
                $("#goodsListDiv").fadeOut("slow");
            }
        });

        $('#merchandiser').editableSelect();
    })

</script>
<script type="text/javascript" src="../js/jquery.editable-select.min.js"></script>
</body>
