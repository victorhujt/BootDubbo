<title>运输开单</title>
<style type="text/css">
    #goodsListDiv,#consignorListDiv,#consigneeListDiv,#custListDiv,#goodsAndConsigneeDiv {
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
    .date_a{
        line-height:21px !important;
    }
    .form-group > label[class*="col-"]{
        margin-top:0;
        line-height:34px;
    }
    .form-horizontal .control-label{
        padding-top:0;
        line-height:30px;
    }
    input[type=checkbox], input[type=radio]{
        margin:10px 0 0;
    }
    .has-error .checkbox, .has-error .checkbox-inline, .has-error .control-label, .has-error .help-block, .has-error .radio, .has-error .radio-inline, .has-error.checkbox label, .has-error.checkbox-inline label, .has-error.radio label, .has-error.radio-inline label{
        color:#393939;
    }
    .has-success .checkbox, .has-success .checkbox-inline, .has-success .control-label, .has-success .help-block, .has-success .radio, .has-success .radio-inline, .has-success.checkbox label, .has-success.checkbox-inline label, .has-success.radio label, .has-success.radio-inline label{
        color:#393939;
    }
    .dataTable > thead > tr > th[class*=sort]:hover{
        color:#707070;
    }
    .dataTable > thead > tr > th[class*=sorting_]{
        color:#707070;
    }
    .has-success .form-control{
        border-color:#cacaca;
    }
    .help-block{
        color:#f00 !important;
    }
    .has-error .form-control{
        border-color:#b5b5b5 !important;
    }
    .custNameIcon:hover{color:#2868c6 !important;}
    .initBtn{
        line-height:32px;
        width:34px;
        border:1px solid #cacaca;
        background:#f7f7f7!important;
        cursor:pointer;
        position:absolute;
        top:0;
        right:0;
    }
    .initBtn:hover{
        background:#fff!important;
        border:1px solid #cacaca!important;
    }
    .col-label{
        margin-right:2px;
        margin-bottom:0;
    }
    #goodsInfoListDiv .help-block{
        line-height:20px;
    }
    .chosen-container .chosen-results{
        max-height:120px;
    }
    .pay .chosen-container-single .chosen-search:after{
        top:4px;
    }
    .form-horizontal .checkbox, .form-horizontal .checkbox-inline, .form-horizontal .radio, .form-horizontal .radio-inline{
        padding-top:0;
    }
</style>
<link rel="stylesheet" type="text/css" href="../css/jquery.editable-select.min.css" />

<div class="modal-content" id="goodsListDiv" style="display: none;">
    <div class="modal-header"><span id="goodsListDivNoneTop" style="cursor:pointer"><button type="button" id="" style="cursor:pointer" class="bootbox-close-button close" data-dismiss="modal" aria-hidden="true">×</button></span>
        <h4 class="modal-title" style="font-size: 14px;font-family:'微软雅黑'">货品列表</h4></div>
    <div class="modal-body">
        <div class="bootbox-body">
            <form id="goodsSelConditionForm" class="form-horizontal" role="form">
                <div class="form-group">
                    <label class="control-label col-xs-1 no-padding-right" for="name">货品编码</label>
                    <div class="col-xs-3">
                        <div class="clearfix">
                            <input  id = "goodsCodeCondition" name="goodsCode" type="text" style="color: black" class="form-control input-sm" placeholder="" aria-controls="dynamic-table">
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-xs-1 no-padding-right" for="name">货品名称</label>
                    <div class="col-xs-3">
                        <div class="clearfix">
                            <input  id = "goodsNameCondition" name="goodsName" type="text" style="color: black" class="form-control input-sm" placeholder="" aria-controls="dynamic-table">
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-xs-1 no-padding-right" for="name"></label>
                    <div class="col-xs-3">
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
                                选择
                                <span class="lbl"></span>
                            </label>
                        </th>
                    <#--<th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Domain: activate to sort column ascending">序号</th>-->
                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Price: activate to sort column ascending">货品类别</th>
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
                <div class="row">
                    <div id="pageBarDivGoods" style="float: right;padding-top: 0px;margin-top: 20px;">
                    </div>
                </div>
            </form>
        </div>
    </div>
    <div class="modal-footer" style="background-color:#fff;"><button id="goodsEnter" data-bb-handler="confirm" type="button" class="btn btn-primary">选中</button><span id="goodsListDivNoneBottom" style="cursor:pointer"><button  data-bb-handler="cancel" type="button" class="btn btn-default">关闭</button></span></div>
</div>
<!--consignorListDiv-->
<div class="modal-content" id="consignorListDiv" style="display: none;">
    <div class="modal-header"><span id="consignorListDivNoneTop" style="cursor:pointer"><button type="button" id="" style="cursor:pointer" class="bootbox-close-button close" data-dismiss="modal" aria-hidden="true">×</button></span>
        <h4 class="modal-title"  style="font-size: 14px;font-family:'微软雅黑'">发货方联系人</h4></div>
    <div class="modal-body">
        <div class="bootbox-body">
            <form id="consignorSelConditionForm" class="form-horizontal" role="form">
            <#--<input id="purpose2" name="cscContact.purpose" type="hidden" value="2">-->
                <div class="form-group">
                    <label class="control-label col-xs-1 no-padding-right" for="name">名称</label>
                    <div class="col-xs-3">
                        <div class="clearfix">
                            <input  id = "consignorName2" name="cscContactCompany.contactCompanyName" type="text" style="color: black" class="form-control input-sm" placeholder="" aria-controls="dynamic-table">
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-xs-1 no-padding-right" for="name">联系人</label>
                    <div class="col-xs-3">
                        <div class="clearfix">
                            <input  id = "consignorPerson2" name="cscContact.contactName" type="text" style="color: black" class="form-control input-sm" placeholder="" aria-controls="dynamic-table">
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-xs-1 no-padding-right" for="name">联系电话</label>
                    <div class="col-xs-3">
                        <div class="clearfix">
                            <input  id = "consignorPhoneNumber2" name="cscContact.phone" type="text" style="color: black" class="form-control input-sm" placeholder="" aria-controls="dynamic-table">
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-xs-1 no-padding-right" for="name"></label>
                    <div class="col-xs-3">
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
                <div class="row">
                    <div id="pageBarDivConsignor" style="float: right;padding-top: 0px;margin-top: 20px;">
                    </div>
                </div>
            </form>
        </div>
    </div>
    <div class="modal-footer" style="background-color:#fff;"><button id="contactinEnter" data-bb-handler="confirm" type="button" class="btn btn-primary">选中</button><span id="consignorListDivNoneBottom" style="cursor:pointer"><button  data-bb-handler="cancel" type="button" class="btn btn-default">关闭</button></span></div>
</div>

<!--consigneeListDiv-->
<div class="modal-content" id="consigneeListDiv" style="display: none;">
    <div class="modal-header"><span id="consigneeListDivNoneTop" style="cursor:pointer"><button type="button" id="" style="cursor:pointer" class="bootbox-close-button close" data-dismiss="modal" aria-hidden="true">×</button></span>
        <h4 class="modal-title" style="font-size: 14px;font-family:'微软雅黑'">收货方联系人</h4></div>
    <div class="modal-body">
        <div class="bootbox-body">
            <form id="consigneeSelConditionForm" class="form-horizontal" role="form">
            <#--<input id="purpose2" name="purpose" type="hidden" value="1">-->
                <div class="form-group">
                    <label class="control-label col-xs-1 no-padding-right" for="name">名称</label>
                    <div class="col-xs-3">
                        <div class="clearfix">
                            <input  id = "consignorName1" name="contactCompanyName" type="text" style="color: black" class="form-control input-sm" placeholder="" aria-controls="dynamic-table">
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-xs-1 no-padding-right" for="name">联系人</label>
                    <div class="col-xs-3">
                        <div class="clearfix">
                            <input  id = "consignorPerson1" name="contactName" type="text" style="color: black" class="form-control input-sm" placeholder="" aria-controls="dynamic-table">
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-xs-1 no-padding-right" for="name">联系电话</label>
                    <div class="col-xs-3">
                        <div class="clearfix">
                            <input  id = "consignorPhoneNumber1" name="phone" type="text" style="color: black" class="form-control input-sm" placeholder="" aria-controls="dynamic-table">
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-xs-1 no-padding-right" for="name"></label>
                    <div class="col-xs-3">
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
                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">地址</th>
                    </thead>
                    <tbody id="contactSelectListTbody1"></tbody>
                </table>
                <div class="row">
                    <div id="pageBarDivConsignee" style="float: right;padding-top: 0px;margin-top: 20px;">
                    </div>
                </div>
            </form>
        </div>
    </div>
    <div class="modal-footer" style="background-color:#fff;"><button id="contactoutEnter" data-bb-handler="confirm" type="button" class="btn btn-primary">选中</button><span id="consigneeListDivNoneBottom" style="cursor:pointer"><button  data-bb-handler="cancel" type="button" class="btn btn-default">关闭</button></span></div>
</div>

<!--custListDiv-->
<div class="modal-content" id="custListDiv" style="display: none;">
    <div class="modal-header"><span id="custListDivNoneTop" style="cursor:pointer"><button type="button" id="" style="cursor:pointer" class="bootbox-close-button close" data-dismiss="modal" aria-hidden="true">×</button></span>
        <h4 class="modal-title" style="font-size: 14px;font-family:'微软雅黑'">选择客户</h4></div>
    <div class="modal-body">
        <div class="bootbox-body">
            <form id="consignorSelConditionForm" class="form-horizontal" role="form">
            <#--<input id="purpose2" name="cscContact.purpose" type="hidden" value="2">-->
                <div class="form-group">
                    <label class="control-label col-xs-1 no-padding-right" for="name" style="margin-top:0;">名称</label>
                    <div class="col-width-220 padding-15 y-float">
                        <div class="clearfix">
                            <input  id = "custNameDiv" name="cscContactCompany.contactCompanyName" type="text" style="color: black" class="form-control input-sm" placeholder="" aria-controls="dynamic-table">
                        </div>
                    </div>

                    <div class="col-xs-3 y-float">
                        <div class="clearfix">
                            <span id="custSelectFormBtn" class="btn btn-white btn-info btn-bold btn-inatervl">筛选</span>
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
                <div class="row">
                    <div id="pageBarDiv" style="float: right;padding-top: 0px;margin-top: 20px;">
                    </div>
                </div>
            </form>

        </div>
    </div>
    <div class="form-group">
        <div class="modal-footer" style="background-color:#fff;"><button style="float: left;display: none;" id="createCustBtn" data-bb-handler="confirm" type="button" class="btn btn-primary">创建新客户</button>
            <button id="custEnter" data-bb-handler="confirm" type="button" class="btn btn-primary">选中</button>
            <span id="custListDivNoneBottom" style="cursor:pointer"><button  data-bb-handler="cancel" type="button" class="btn btn-default">关闭</button></span></div>
    </div>
</div>
<div class="row">

    <!-- /section:basics/content.breadcrumbs -->
    <div class="page-content no-padding-top" >


        <div class="col-xs-12">
            <div class="page-header">
                <p style="font-size: 14px;font-family:'微软雅黑'">
                    基本信息
                    <span hidden="true" id = "ofc_url">${(OFC_URL)!}</span>
                    <span hidden="true" id = "csc_url">${(CSC_URL)!}</span>
                    <span hidden="true" id = "login_user">${(loginUser)!}</span>
                <#--<span hidden="true" id = "addr_url">${(ADDR_URL)!}</span>-->
                <#--<#import "address.ftl" as apiAddrFtl>-->
                </p>
            </div>

            <form id="orderFundamentalFormValidate" method="post" class="form-horizontal" role="form" >
                <div class="form-group" id="transBusinessTypeDiv">
                    <div><label class="control-label col-label no-padding-right" for="name" style="margin-right:8px;"><span class="w-label-icon">*</span>业务类型</label>
                        <div class="col-width-168 padding-15">
                            <div class="clearfix col-width-168">
                                <select  id="businessType" name="businessType" class="chosen-select form-control ">
                                    <option value="602">卡班</option>
                                    <option value="600">城配</option>
                                    <option value="601">干线</option>
                                </select>
                            </div>
                        </div></div>

                    <div><label class="control-label col-label no-padding-right" for="merchandiser"><span class="w-label-icon">*</span>开单员</label>
                        <div class="col-width-168 padding-15" style="margin-left:3px;">
                            <div class="col-width-168">
                                <div class="clearfix">
                                    <select id="merchandiser" name="merchandiser" class="col-width-168" placeholder="开单员">
                                    <#--<#list merchandiserList! as merchandiser>-->
                                    <#--<option <#if merchandiser.merchandiser?? ><#if ((merchandiser.merchandiser)! == (merchandiserLast))>selected="selected"</#if></#if>>${(merchandiser.merchandiser)!""}</option>-->
                                    <#--</#list>-->
                                    <#list merchandiserList! as merchandiser>
                                        <option>${(merchandiser.merchandiser)!""}</option>
                                    </#list>
                                    </select></div>
                            </div>
                        </div></div>
                    <div><label class="control-label col-label" for="name" style="margin-right:18px;"><span class="w-label-icon">*</span>运输类型</label>

                        <div class="radio y-float">
                            <label>
                                <input id="transportTypeV1" type="radio" class="ace" name="transportTypeV" checked="checked" value="10"/>
                                <span class="lbl">零担</span>
                            </label>
                        </div>
                        <div class="radio y-float">
                            <label>
                                <input id="transportTypeV2" type="radio" class="ace" name="transportTypeV" value="20"/>
                                <span class="lbl">整车</span>
                            </label>
                        </div>
                        <input id="transportType" type="hidden" name="transportType"/></div>
                </div>
                <div class="form-group">
                    <div><label class="control-label col-label no-padding-right" for="supplierCode" style="margin-right:8px;"><span class="w-label-icon">*</span>订单日期</label>
                        <div class="col-width-168 padding-15">
                            <div class="clearfix" >
                                <div class="col-width-168 position-relative" style="height:34px;">
                                    <input class="col-width-168 es-input" name="orderTime" id="orderTime" type="text" placeholder="订单日期" aria-controls="dynamic-table" readonly class="laydate-icon" id="startDate" value="${(currentTime?string("yyyy-MM-dd"))!""}" onclick="laydate({istime: true, format: 'YYYY-MM-DD',isclear: true,istoday: true,min: laydate.now(-30),max: laydate.now()})">
                                    <label for="orderTime" class="initBtn" style="height:34px;"><i class="ace-icon fa fa-calendar icon-pic bigger-130" style="color:#333;"></i></label>
                                </div>
                            </div>
                        </div></div>
                    <div><label class="control-label col-label no-padding-right" for="transCode"><span class="w-label-icon toggle">*</span>运输单号</label>
                        <div class="col-width-168 padding-15" style="margin-left:3px;">
                            <div class="col-width-168">
                                <input class="col-width-168"  name="transCode" id="transCode" type="text" placeholder="运输单号" style="padding-left:8px;" />
                            </div>
                        </div></div>
                    <div>
                        <label class="control-label col-label no-padding-right" for="custOrderCode">客户订单号</label>
                        <div class="col-width-168 padding-15" style="margin-left:3px;">
                            <div class="col-width-168">
                                <input class="col-width-168"  name="custOrderCode" id="custOrderCode" type="text" placeholder="客户订单号" style="padding-left:8px;" />
                            </div>
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <div><label class="control-label col-label no-padding-right" for="custName" style="margin-right:8px;"><span class="w-label-icon">*</span>客户名称</label>
                        <div class="col-xs-2">
                            <div class="position-relative" style="width:433px;">
                                <input readonly name="custName" id="custName" type="text" placeholder="客户名称" style="padding-left:8px;width:430px;" />
                                <input class="col-xs-10 col-xs-12" name=""  id="custGroupId" type="text" style="display: none"  />
                                <input class="col-xs-10 col-xs-12" name=""  id="customerCode" type="text"  style="display: none"  />
                                <button type="button" class="btn btn-minier no-padding-right y-float initBtn" id="custListDivBlock"style="outline:none;color: #666 !important;" >
                                    <i class="fa fa-user l-cor bigger-130"></i>
                                </button>
                            <#-- <span style="cursor:pointer line-height:33px;" id="custListDivBlock"><i class="ace-icon fa fa-user bigger-130 icon-pic custNameIcon" style="color:#008bca;"></i></span>-->
                            </div>
                        </div></div>

                </div>
                <div class="form-group">
                    <div><label class="control-label col-label no-padding-right" for="transRequire" style="margin-right:8px;">备注</label>
                        <div class="col-xs-2">
                            <div class="position-relative" style="width:433px;">
                                <input name="transRequire" id="transRequire" type="text" placeholder="备注" style="padding-left:8px;width:433px;" />
                            <#--<span style="cursor:pointer line-height:33px;" id="custListDivBlock">  <i class="ace-icon fa fa-user bigger-130 position-absolute icon-pic" style="color:#333;"></i></span>-->
                            </div>
                        </div></div>
                </div>

            </form>

            <form name="orderInfoTableValidate" id="orderInfoTableValidate"  class="form-horizontal" role="form" style="min-height:360px;">
                <div style="width:515px;margin-right:35px;float:left;">
                    <div class="page-header">
                        <h4 style="font-size: 14px;font-family:'微软雅黑'">发货方信息</h4>
                    </div>
                    <span style="cursor:pointer" id="consignorListDivBlock">
                    <button type="button" class="btn btn-white btn-info btn-bold btn-interval" id="consignorselbtn">选择</button></span>
                    <label id="departure_place" class="control-label" style="float:right;" for="name">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>
                    <label class="control-label" style="float:right;" for="name">出发地：</label>
                    <div id="consignorin" style="margin-top:15px;">
                        <div class="form-group" >
                            <label class="control-label col-label no-padding-right" for="name"><span class="w-label-icon">*</span>名称</label>
                            <div class="col-width-168 padding-15">
                                <div class="clearfix">
                                    <input id="consignorCode" name="consignorCode" type="hidden">
                                    <input id="consignorType" name="consignorType" type="hidden">
                                    <input id="consignorName"  name="consignorName" type="text" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" >
                                </div>
                            </div>
                        </div>
                        <div class="form-group" >
                            <label class="control-label col-label no-padding-right" for="name"><span class="w-label-icon">*</span>联系人</label>
                            <div class="col-width-168 padding-15">
                                <div class="clearfix">
                                    <input id="consignorContactCode"   name="consignorContactCode" type="hidden" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" >
                                    <input id="consignorContactName"   name="consignorContactName" type="text" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" >
                                </div>
                            </div>
                        </div>
                        <div class="form-group" >
                            <label class="control-label col-label no-padding-right" for="name"><span class="w-label-icon">*</span>联系电话</label>
                            <div class="col-width-168 padding-15">
                                <div class="clearfix">
                                    <input id="consignorPhone" name="consignorPhone" type="text" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" >

                                </div>
                            </div>
                        </div>
                        <div class="form-group" >
                            <label class="control-label col-label no-padding-right" for="name"><span class="w-label-icon">*</span>地址选择</label>
                            <div class="col-width-376 padding-15">
                                <div class="clearfix">
                                    <span style="cursor:pointer;position:relative;" id="city-picker-consignor"><input id="city-picker3-consignor" class="form-control input-sm" readonly type="text" value="" data-toggle="city-picker"></span>
                                </div>
                            </div>
                        </div>
                        <div class="form-group" >
                            <label class="control-label col-label no-padding-right" for="name">详细地址</label>
                            <div class="col-width-376 padding-15">
                                <div class="clearfix">
                                    <input id="consignorAddress" name="consignorAddress" type="text" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" >
                                </div>
                            </div>
                        </div>
                    </div>


                </div>


                <div style="width:450px;float:left;">
                    <div class="page-header">
                        <h4 style="font-size: 14px;font-family:'微软雅黑'">收货方信息</h4>
                    </div>
                    <span style="cursor:pointer" id="consigneeListDivBlock"><button type="button" class="btn btn-white btn-info btn-bold btn-interval" id="consigneeselbtn">选择</button></span>
                    <label id="" class="control-label" style="float:right;" for="name">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>
                    <label id="destination" class="control-label" style="float:right;" for="name"></label>
                    <label class="control-label" style="float:right;" for="name">目的地：</label>
                    <div id="consignorout" style="margin-top:15px;">
                        <div class="form-group" >
                            <label class="control-label col-label no-padding-right" for="name"><span class="w-label-icon">*</span>名称</label>
                            <div class="col-width-168 padding-15">
                                <div class="clearfix">
                                    <input id="consigneeCode" name="consigneeCode" type="hidden">
                                    <input id="consigneeType" name="consigneeType" type="hidden">
                                    <input id="consigneeName"  name="consigneeName" type="text" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" >

                                </div>
                            </div>
                        </div>
                        <div class="form-group" >
                            <label class="control-label col-label no-padding-right" for="name"><span class="w-label-icon">*</span>联系人</label>
                            <div class="col-width-168 padding-15">
                                <div class="clearfix">
                                    <input id="consigneeContactCode" name="consigneeContactCode" type="hidden" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" >
                                    <input id="consigneeContactName" name="consigneeContactName" type="text" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" >

                                </div>
                            </div>
                        </div>
                        <div class="form-group" >
                            <label class="control-label col-label no-padding-right" for="name"><span class="w-label-icon">*</span>联系电话</label>
                            <div class="col-width-168 padding-15">
                                <div class="clearfix">
                                    <input id="consigneePhone" name="consigneePhone" type="text" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" >

                                </div>
                            </div>
                        </div>
                        <div class="form-group" >
                            <label class="control-label col-label no-padding-right" for="name"><span class="w-label-icon">*</span>地址选择</label>
                            <div class="col-width-376 padding-15">
                                <div class="clearfix">
                                    <span style="cursor:pointer;position:relative;" id="city-picker-consignee"><input id="city-picker3-consignee" class="form-control input-sm" readonly type="text" value="" data-toggle="city-picker"></span>
                                </div>
                            </div>
                        </div>
                        <div class="form-group" >
                            <label class="control-label col-label no-padding-right" for="name">详细地址</label>
                            <div class="col-width-376 padding-15">
                                <div class="clearfix">
                                    <input id="consigneeAddress" name="consigneeAddress" type="text" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" >
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </form>

            <div class="page-header" style="clear:left;">
                <p style="font-size: 14px;font-family:'微软雅黑'">
                    服务项目及费用
                </p>
            </div>
            <form id="orderFinanceFormValidate" method="post" class="form-horizontal" role="form" >
                <div class="form-group">
                    <div style="width:280px;margin:0 0 0 15px;float:left;">
                        <div class="clearfix col-xs-1" style="margin-top:4px;">
                            <input id="pickUpGoodsV" type="checkbox" name=""  class="btn btn-minier btn-inverse no-padding-right" onchange=""/>
                            <input id="pickUpGoods" type="hidden" name="pickUpGoods"  value="0" />
                        </div>
                        <label class="control-label col-width-110" for="name" style="padding:0px 15px 0 10px;">上门提货:费用</label>
                        <div style="width:140px;float:left;">
                            <div class="col-width-100 margin-right-15">
                                <input class="col-width-100" id="homeDeliveryFee" disabled="true" style="color: #000" name="homeDeliveryFee" type="text" class="form-control input-sm " placeholder="" aria-controls="dynamic-table" onblur= "countCostCheck()" />
                            </div>
                            <label class="control-label" for="name" style="height:34px;line-height:34px;">元</label>
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <div style="width:280px;margin:0 0 0 15px;float:left;">
                        <div class="clearfix col-xs-1" style="margin-top:4px;">
                            <input id="insureV" type="checkbox" name=""  class="btn btn-minier btn-inverse no-padding-right"/>
                            <input id="insure" type="hidden" name="insure"  value="0" />
                        </div>
                        <label class="control-label col-width-110" for="name" style="padding:0px 15px 0 10px;">货物保险:费用</label>
                        <div style="width:140px;float:left;">
                            <div  class="col-width-100 margin-right-15" >
                                <input class="col-width-100"  id="cargoInsuranceFee" disabled="true" style="color: #000" name="cargoInsuranceFee" type="text" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" onblur="countCostCheck()">
                            </div>
                            <label class="control-label" for="name" style="height:34px;line-height:34px;">元</label>
                        </div>
                    </div>
                    <div class="col-xs-6 no-padding-left">
                        <label class="control-label col-width-80" for="name" style="padding:0px 15px 0;">声明价值</label>
                        <div style="width:140px;float:left;">
                            <div  class="col-width-100 margin-right-15">
                                <input class="col-width-100" id="insureValue" disabled="true" style="color: #000" name="insureValue" type="text" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" onblur="countCostCheck()">
                            </div>
                            <label class="control-label" for="name" style="height:34px;line-height:34px;">元</label>
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <div style="width:280px;margin:0 0 0 15px;float:left;">
                        <div class="clearfix col-xs-1" style="margin-top:4px;">
                            <input id="twoDistributionV" type="checkbox" name=""  class="btn btn-minier btn-inverse no-padding-right"/>
                            <input id="twoDistribution" type="hidden" name="twoDistribution"  value="0" />
                        </div>
                        <label class="control-label col-width-110" for="name" style="padding:0px 15px 0; 10px">二次配送:费用</label>
                        <div style="width:140px;float:left;">
                            <div  class="col-width-100 margin-right-15">
                                <input class="col-width-100" id="twoDistributionFee" disabled="true" style="color: #000" name="twoDistributionFee" type="text" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" onblur="countCostCheck()">
                            </div>
                            <label class="control-label" for="name" style="height:34px;line-height:34px;">元</label>
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <div style="width:280px;margin:0 0 0 15px;float:left;">
                        <div class="clearfix col-xs-1" style="margin-top:4px;">
                            <input id="collectFlagV" type="checkbox" name=""  class="btn btn-minier btn-inverse no-padding-right"/>
                            <input id="collectFlag" type="hidden" name="collectFlag"  value="0" />
                        </div>
                        <label class="control-label col-width-110" for="name" style="padding:0px 15px 0 10px;">代收货款:费用</label>
                        <div style="width:140px;float:left;">
                            <div  class="col-width-100 margin-right-15" >
                                <input class="col-width-100" id="collectServiceCharge" disabled="true" style="color: #000" name="collectServiceCharge" type="text" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" onblur="countCostCheck()">
                            </div>
                            <label class="control-label" for="name" style="height:34px;line-height:34px;">元</label>
                        </div>
                    </div>
                    <div class="col-xs-6 no-padding-left">
                        <label class="control-label col-width-80" for="name" style="padding:0px 15px 0;">代收金额</label>
                        <div style="width:140px;float:left;">
                            <div  class="col-width-100 margin-right-15">
                                <input class="col-width-100" id="collectLoanAmount" disabled="true" style="color: #FF0000;" name="collectLoanAmount" type="text" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" onblur="countCostCheck()">
                            </div>
                            <label class="control-label" for="name" style="height:34px;line-height:34px;">元</label>
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <div style="width:280px;margin:0 0 0 15px;float:left;">
                        <div class="clearfix col-xs-1" style="margin-top:4px;">
                            <input id="returnListV" type="checkbox" name=""  class="btn btn-minier btn-inverse no-padding-right"/>
                            <input id="returnList" type="hidden" name="returnList"  value="0" />
                        </div>
                        <label class="control-label col-width-110" for="name" style="padding:0px 15px 0; 10px">签单返回:费用</label>
                        <div style="width:140px;float:left;">
                            <div  class="col-width-100 margin-right-15">
                                <input class="col-width-100" id="returnListFee" disabled="true" style="color: #000" name="returnListFee" type="text" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" onblur="countCostCheck()">
                            </div>
                            <label class="control-label" for="name" style="height:34px;line-height:34px;">元</label>
                        </div>
                    </div>
                    <div class="col-xs-6 no-padding-left">
                        <label class="control-label col-width-80" for="name" style="padding:0px 15px 0;">运费</label>
                        <div style="width:140px;float:left;">
                            <div  class="col-width-100 margin-right-15">
                                <input class="col-width-100" id="luggage" style="color: #000" name="luggage" type="text" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" onblur="countCostCheck()" value="0">
                            </div>
                            <label class="control-label" for="name" style="height:34px;line-height:34px;">元</label>
                        </div>
                    </div>
                </div>
            </form>

            <form id="orderFinanceChargeFormValidate" method="post" class="form-horizontal" role="form">
                <div class="form-group" id="transBusinessTypeDiv" style="line-height:34px;">
                    <div style="width:242px;float:left;margin-left:45px;" >
                        <label class="col-label" for="name" style="float:left;">费用总计</label>
                        <div class="col-width-100 padding-15" style="width:130px">
                            <input id="serviceCharge" value="0" disabled="true" style="color: #000;display:block;float:left;" name="serviceCharge" type="text" class="form-control input-sm" placeholder="" aria-controls="dynamic-table">
                        </div>
                        <label class="" for="name" style="margin-bottom:0;" >元</label>
                    </div>
                    <div >
                        <label class=" no-padding-right" style="float:left; margin:0 15px 0 24px;" for="name">费用支付</label>
                        <div class="col-width-70" style="margin-right:10px;float:left;height:34px;">
                            <label class="clearfix">
                                <input id="expensePayntPartyV1" type="radio" class="ace" name="expensePaymentPartyV" value="10" checked="checked" style="margin:5px;float:left;margin-top:11px;padding-to"/>
                                <span class="lbl" style="float:left;margin-right:5px;">发货方</span>
                            </label>
                        </div></div>
                    <div>
                        <div class="col-width-70" style="float:left;height:34px;">
                            <label class="clearfix">
                                <input id="expensePaymentPartyV2" type="radio" class="ace" name="expensePaymentPartyV" value="20" style="margin:5px;float:left;margin-top:11px;"/>
                                <span class="lbl" style="float:left;margin-right:5px;">收货方</span>
                            </label>
                        </div>
                    </div>
                    <input id="transportType" type="hidden" name="transportType"/>
                    <input id="expensePaymentParty" type="hidden" name="expensePaymentParty"/>
                    <div>
                        <label class="no-padding-right" for="name" style="margin-left:30px;float:left;margin-bottom:0;line-height:34px;">支付方式</label>
                        <div class="col-width-168 padding-15">
                            <div class="clearfix col-width-100 pay">
                                <select class="chosen-select form-control" id="payment" name="payment">
                                    <option value="6810">现金</option>
                                    <option value="6820">POS刷卡</option>
                                    <option value="6830">微信</option>
                                    <option value="6840">支付宝</option>
                                    <option value="6850">银行支付</option>
                                    <option value="6860">账户结算</option>
                                </select>
                            </div>
                        </div>
                    </div>
                    <div style="width:230px;margin:0 0 0 15px;float:left;">
                        <div class="clearfix col-xs-1" style="margin-top:-6px;margin-left:5px;">
                            <input id="openInvoicesV" type="checkbox" name="" class="btn btn-minier btn-inverse no-padding-right" onchange=""/>
                            <input id="openInvoices" type="hidden" name="openInvoices"  value="0" />
                        </div>
                        <label class="control-label col-width-110" for="name" style="padding:0px 15px;text-align:left">是否开发票</label>
                    </div>
                </div>

                <div class="form-group" id="transBusinessTypeDiv" style="line-height:34px;">

                    <div style="width:400px;">
                        <label class=" col-label col-float" for="name" >结算方式</label>
                        <label class=" col-label-50 col-float" for="name" style="width:45px;margin-left:0;margin-right:15px;">现结</label>
                        <div style="float:left;width:130px;">
                            <div class="col-float" style="width:100px;margin-right:15px;">
                                <input id="currentAmount"  style="color: #000" name="currentAmount" type="text" class="col-width-100 form-control input-sm" placeholder="" aria-controls="dynamic-table" onblur="countSettlement()"  value="0">
                            </div>
                            <label class=" col-float" for="name">元</label></div>
                    </div>
                    <div >
                        <label class=" col-label-50 col-float" for="name" style="margin-left:25px;margin-right:15px;">到付</label>
                        <div style="width:130px;float:left;">
                            <div class="col-float" style="width:100px;margin-right:15px;">
                                <input id="toPayAmount"  style="color: #000" name="toPayAmount" type="text" class="col-width-100 form-control input-sm" placeholder="" aria-controls="dynamic-table" onblur="countSettlement()"  value="0">
                            </div>
                            <label class=" col-float" for="name">元</label>
                        </div>
                    </div>
                    <div>
                        <label class=" col-label-50 col-float" for="name" style="margin-left:49px;margin-right:14px;">回付</label>
                        <div style="width:130px;float:left;">
                            <div class="col-float" style="width:100px;margin-right:15px;">
                                <input id="returnAmount"  style="color: #000" name="returnAmount" type="text" class="col-float form-control input-sm" placeholder="" aria-controls="dynamic-table" onblur="countSettlement()"  value="0">
                            </div>
                            <label class=" col-float" for="name">元</label>
                        </div>
                    </div>
                    <div>
                        <label class=" col-label-50" for="name" style="margin-right:15px;">月结</label>
                        <div style="width:130px;float:left;">
                            <div class="col-float" style="width:100px;margin-right:15px;">
                                <input id="monthlyAmount"  style="color: #000" name="monthlyAmount" type="text" class="col-float form-control input-sm" placeholder="" aria-controls="dynamic-table" onblur="countSettlement()"  value="0">
                            </div>
                            <label class=" col-float" for="name">元</label>
                        </div>
                    </div>
                </div>

            </form>
            <!-- /section:elements.tab.option -->
        </div>
        <!-- PAGE CONTENT ENDS -->
    </div><!-- /.col -->
</div><!-- /.row -->
</div><!-- /.page-content -->
<#--<div class="page-content">
    <div class="row">
        <div class="col-xs-12">
        </div>

    </div>
</div>-->
<div class="page-header" style="clear:left;">
    <p style="font-size: 14px;font-family:'微软雅黑'">
        货品信息
    </p>
</div>
<div class="col-xs-12">
    <!-- #section:elements.tab.option -->
    <div class="tabbable" >
    <#-- <ul class="nav nav-tabs" id="myTab4">
         <li class="active">
             <a data-toggle="tab" href="#home4" aria-expanded="false">货品信息</a>
         </li>
     </ul>-->



        <div class="tab-content" style="border:none;padding-top:0;">
            <div id="home4" class="tab-pane active">

                <!--货品明细-->
                <button type="button" style="float:right;" class="btn btn-white btn-info btn-bold btn-interval"
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
                <label id="cubageCountHidden" class="control-label" style="float:right;" for="name" hidden></label>
            <#--dynamic-table-->
                <table id="orderGoodsListTable" class="table table-striped table-bordered table-hover dataTable no-footer" role="grid" aria-describedby="dynamic-table_info">
                    <thead>
                    <tr role="row"><th class="center sorting_disabled" style="width:45px;" rowspan="1" colspan="1" aria-label="">
                        操作
                    </th>
                    <#--<th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Status: activate to sort column ascending">序号</th>-->
                        <th class="" tabindex="0" style="width:120px;" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Domain: activate to sort column ascending">货品种类</th>
                        <th class="" tabindex="0" style="width:98px;" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Domain: activate to sort column ascending">货品小类</th>
                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Domain: activate to sort column ascending">货品编码</th>
                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Price: activate to sort column ascending">货品名称</th>
                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Update: activate to sort column ascending">规格
                        </th>
                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">单位</th>
                        <th class="" tabindex="0" style="width:94px;" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">包装</th>
                        <th class="" tabindex="0" style="width:95px;" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">计费方式</th>
                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">计费单价</th>
                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">数量</th>
                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">重量(Kg)</th>
                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">体积(m³)</th>
                    </thead>
                <#--货品明细-->

                    <tbody id="goodsInfoListDiv"></tbody>

                </table>
            </div>
        </div>
    </div>
</div>
<div class="col-xs-12" style="margin-top:20px;">

    <button class="btn btn-white btn-info btn-bold btn-interval" style="margin-left:12px;" id="orderPlaceConTableBtn">
        <i class="ace-icon fa fa-floppy-o bigger-120 blue" ></i>
        确认下单
    </button>
</div>

<#--
        </div>
    </div><!-- /.main-content &ndash;&gt;


</div><!-- /.main-container &ndash;&gt;-->

<link href= "../css/bootstrap.min.css" rel="stylesheet" type="text/css" />
<link href="../css/city-picker.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="../components/chosen/chosen.css" />
<script src="../components/chosen/chosen.jquery.js"></script>
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
        initChosen();
        validateForm();
        $("#weightCount").html("0");
        $("#quantityCount").html("0");

        // 上次选择的开单员
        setLastUserData();
        /*//$("#orderTime").val(new Date().toLocaleDateString());*/
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
                merchandiser:{
                    required:true
                },
                orderTime:{
                    required:true
                },
                transCode:{
                    maxlength:20,
                    pattern:/(^[A-Za-z0-9]+$)/,
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
                },
                transRequire:{
                    maxlength:255
                },custOrderCode: {
                    maxlength:30
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
                    required:mistake+"请填写开单员"
                },
                orderTime:{
                    required:mistake+"请填写订单日期"
                },
                transCode:{
                    maxlength: mistake+"超过最大长度20",
                    pattern:mistake+"只能输入数字和字母",
                    remote: mistake+"运输单号已存在"
                },
                custName:{
                    maxlength:mistake+"超过最大长度"
                },
                transRequire:{
                    maxlength:mistake+"超过最大长度255"
                },
                custOrderCode:{
                    maxlength:mistake+ "超过最大长度30"
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
            submitHandler : function(form) {
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
                    maxlength: mistake+"最大999999.99元",
                    required:mistake+"请填写运费",
                    pattern:mistake+"最大999999.99元"
                },
                homeDeliveryFee:{
                    maxlength: mistake+"最大999999.99元",
                    pattern:mistake+"最大999999.99元"
                },
                cargoInsuranceFee:{
                    maxlength: mistake+"最大999999.99元",
                    pattern:mistake+"最大999999.99元"
                },
                insureValue:{
                    maxlength: mistake+"最大999999.99元",
                    pattern:mistake+"最大999999.99元"
                },
                twoDistributionFee:{
                    maxlength: mistake+"最大999999.99元",
                    pattern:mistake+"最大999999.99元"
                },
                collectServiceCharge:{
                    maxlength: mistake+"最大999999.99元",
                    pattern:mistake+"最大999999.99元"
                },
                collectLoanAmount:{
                    maxlength: mistake+"最大999999.99元",
                    pattern:mistake+"最大999999.99元"
                },
                returnListFee:{
                    maxlength: mistake+"最大999999.99元",
                    pattern:mistake+"最大999999.99元"
                }
            },
            highlight : function(e) {
                $(e).parent().parent().parent().removeClass('has-info').addClass('has-error');
            },
            success : function(e) {
                $(e).parent().parent().removeClass('has-error').addClass('has-success');
                $(e).remove();
                //countCostCheck();
            },
            errorPlacement : function(error, element) {
                error.insertAfter(element.parent().next());
                $(error).attr("align","left");
            },
            submitHandler : function(form) {
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
                    pattern:/^([1-9][\d]{0,5}|0)(\.[\d]{1,2})?$/
                },
                toPayAmount:{
                    maxlength: 9,
                    pattern:/^([1-9][\d]{0,5}|0)(\.[\d]{1,2})?$/
                },
                returnAmount:{
                    maxlength: 9,
                    pattern:/^([1-9][\d]{0,5}|0)(\.[\d]{1,2})?$/
                },
                monthlyAmount:{
                    maxlength: 9,
                    pattern:/^([1-9][\d]{0,5}|0)(\.[\d]{1,2})?$/
                }
            },
            messages : {
                currentAmount:{
                    maxlength: mistake+"最大999999.99元",
                    pattern:mistake+"最大999999.99元"
                },
                toPayAmount:{
                    maxlength: mistake+"最大999999.99元",
                    pattern:mistake+"最大999999.99元"
                },
                returnAmount:{
                    maxlength: mistake+"最大999999.99元",
                    pattern:mistake+"最大999999.99元"
                },
                monthlyAmount:{
                    maxlength: mistake+"最大999999.99元",
                    pattern:mistake+"最大999999.99元"
                }
            },
            highlight : function(e) {
                $(e).parent().parent().removeClass('has-info').addClass('has-error');
            },
            success : function(e) {
                //alert($(e).attr('id'));

                if(countSettlement()=="false"){
                    $(e).parent().removeClass('has-info').addClass('has-error');
                    $(e).html(mistake+"结算方式的金额合计应于费用总计一致！");
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
            submitHandler : function(form) {
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
                    required:mistake+"必须输入",
                    maxlength:mistake+"超过最大长度100"
                },
                consignorContactName:{
                    required:mistake+"必须输入",
                    maxlength:mistake+"超过最大长度50"
                },
                consignorPhone:{
                    isPhone:mistake+"请输入正确的手机号",
                    required:mistake+"必须输入",
                    maxlength:mistake+"超过最大长度"
                },
                consignorAddress:{
                    maxlength:mistake+"超过最大长度200"
                },
                consigneeName:{
                    required:mistake+"必须输入",
                    maxlength:mistake+"超过最大长度100"
                },
                consigneeContactName:{
                    required:mistake+"必须输入",
                    maxlength:mistake+"超过最大长度50"
                },
                consigneePhone:{
                    isPhone:mistake+"请输入正确的手机号",
                    required:mistake+"必须输入",
                    maxlength:mistake+"超过最大长度"
                },
                consigneeAddress:{
                    maxlength:mistake+"超过最大长度200"
                }
            },
            highlight : function(e) {
                $(e).parent().parent().parent().removeClass('has-info').addClass('has-error');
            },
            success : function(e) {
                $(e).parent().parent().parent().removeClass('has-error').addClass('has-success');
                $(e).remove();
            },
            errorPlacement : function(error, element) {
                error.insertAfter(element);
                //error.insertAfter(element.parent().next());
            },
            submitHandler : function(form) {
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
        $("#goodsSelectListTbody").html("");
        $("#pageBarDivGoods").hide();
        $(obj).attr("id","yangdongxushinanshen");
        $(obj).parent().parent().find("td").eq(1).find("select").attr("id","typeSel");
        $("#goodsListDiv").fadeIn(0);//淡入淡出效果 显示div
    }

    $("#businessType").change(function(){
        if($("#businessType").val() == 602){
            $(".toggle").css({"display":"inline-block"})
        }else{
            $(".toggle").css({"display":"none"});
        };
    })

    function onlyNumber(value){
        //先把非数字的都替换掉，除了数字和.
        value = value.replace(/[^\d\.]/g,'');
        //obj.value = obj.value.replace(/[^\d{1,6}]/,'');
        //必须保证第一个为数字而不是.
        value = value.replace(/^\./g,'');
        //保证只有出现一个.而没有多个.
        value = value.replace(/\.{2,}/g,'.');
        //保证.只出现一次，而不能出现两次以上
        value = value.replace('.','$#$').replace(/\./g,'').replace('$#$','.');
        return value;
    }
    function countQuantOrWeightOrCubage(obj) {
        var value=onlyNumber($(obj).val());
        if(parseFloat($(obj).val())>30000){
            $(obj).css("border-color","#dd5a43");
            if($(obj).parent().children().length<2){
                $("<div id='price-error' class='help-block has-error'><i class='fa fa-times-circle w-error-icon bigger-130'></i>最大值为30000.00</div>").insertAfter($(obj));
                $(obj).parent().removeClass('has-info').addClass('has-error');
                $(obj).val("");
                countQuantityOrWeightOrCubageCheck();
            }else{
                $(obj).val("");
                countQuantityOrWeightOrCubageCheck();
            }
        }else if((!(/^([1-9][\d]{0,7}|0)(\.[\d]{1,3})?$/.test(value)) && $(obj).val()!="") || $(obj).val()=="0"){
            $(obj).css("border-color","#dd5a43");
            if($(obj).parent().children().length<2){
                $("<div id='price-error' class='help-block has-error'><i class='fa fa-times-circle w-error-icon bigger-130'></i>只允许输入金额</div>").insertAfter($(obj));
                $(obj).parent().removeClass('has-info').addClass('has-error');
                $(obj).val("");
                countQuantityOrWeightOrCubageCheck();
            }else{
                $(obj).val("");
                countQuantityOrWeightOrCubageCheck();
            }
        }else{
            $(obj).css("border-color","#cacaca")
            $(obj).val(value);
            $(obj).parent().find("div").remove();
            countQuantityOrWeightOrCubageCheck();
            if($(obj).parent().prev().prev().children().val()=="02"){
                $(obj).parent().next().children().val($(obj).val());
            }
        };
    }
    function countQuantityOrWeightOrCubagePrice(obj) {
        var value=onlyNumber($(obj).val());
        if((!(/^([1-9][\d]{0,6}|0)(\.[\d]{1,2})?$/.test(value)) && $(obj).val()!="")){
            $(obj).css("border-color","#dd5a43");
            if($(obj).parent().children().length<2){
                $("<div id='price-error' class='help-block has-error'><i class='fa fa-times-circle w-error-icon bigger-130'></i>只允许输入金额</div>").insertAfter($(obj));
                $(obj).parent().removeClass('has-info').addClass('has-error');
                $(obj).val("");
                countQuantityOrWeightOrCubageCheck();
            }else{
                $(obj).val("");
                countQuantityOrWeightOrCubageCheck();
            }
        }else if(parseFloat($(obj).val())>9999){
            $(obj).css("border-color","#dd5a43");
            if($(obj).parent().children().length<2){
                $("<div id='price-error' class='help-block has-error'><i class='fa fa-times-circle w-error-icon bigger-130'></i>最大值为9999.00</div>").insertAfter($(obj));
                $(obj).parent().removeClass('has-info').addClass('has-error');
                $(obj).val("");
                countQuantityOrWeightOrCubageCheck();
            }else{
                $(obj).val("");
                countQuantityOrWeightOrCubageCheck();
            }
        }else{
            $(obj).css("border-color","#cacaca")
            $(obj).val(value);
            $(obj).parent().find("div").remove();
            countQuantityOrWeightOrCubageCheck();
        };
    }
    function countQuantityOrWeightOrCubageCheck() {
        var quantityCount=0;
        var cubageCount=0;
        var weightCount=0;
        var luggage=0;
        var flg1="";
        var flg2="";
        var flg3="";

        /*$('input[name="quantity"]').each(function(){
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
                cubageCount=cubageCount+parseFloat($(this).val());

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
        if(flg3==""){$("#luggage").val(luggage);luggage=0;}*/
        $("#goodsInfoListDiv").find("tr").each(function(index){
            var tdArr = $(this).children();
            var chargingways=tdArr.eq(8).children().val();
            var st=tdArr.eq(10).find("input").val();
            if(chargingways=="01"){
                if(tdArr.eq(10).find("input").val()!="" && tdArr.eq(10).find("input").val()!="0"){
                    if(tdArr.eq(9).find("input").val()!="" && tdArr.eq(9).find("input").val()!="0"){
                        luggage=luggage+parseFloat(tdArr.eq(10).find("input").val())*parseFloat(tdArr.eq(9).find("input").val());
                    }else{
                        flg1="error";
                    }
                }else{
                    flg1="error";
                }
            }else if(chargingways=="02"){
                if(tdArr.eq(12).find("input").val()!="" && tdArr.eq(12).find("input").val()!="0"){
                    if(tdArr.eq(9).find("input").val()!="" && tdArr.eq(9).find("input").val()!="0"){
                        luggage=luggage+parseFloat(tdArr.eq(9).find("input").val())*parseFloat(tdArr.eq(12).find("input").val());
                    }else{
                        flg2="error";
                    }
                }else{
                    flg2="error";
                }
            }else if(chargingways=="03"){
                if(tdArr.eq(13).find("input").val()!="" && tdArr.eq(13).find("input").val()!="0"){
                    if(tdArr.eq(9).find("input").val()!="" && tdArr.eq(9).find("input").val()!="0"){
                        luggage=luggage+parseFloat(tdArr.eq(9).find("input").val())*parseFloat(tdArr.eq(13).find("input").val());
                    }else{
                        flg3="error";
                    }
                }else{
                    flg3="error";
                }
            }else{
                alert("计费方式有问题");
            }
        });
        $('input[name="quantity"]').each(function(){
            if($(this).val()!=""){
                quantityCount=quantityCount+parseFloat($(this).val());
                quantityCount=parseFloat((parseFloat(quantityCount)).toFixed(3));
            }
        });
        $('input[name="weight"]').each(function(){
            if($(this).val()!=""){
                weightCount=weightCount+parseFloat($(this).val());
                weightCount=parseFloat((parseFloat(weightCount)).toFixed(3));
            }
        });
        $('input[name="cubage"]').each(function(){
            if($(this).val()!=""){
                cubageCount=cubageCount+parseFloat($(this).val());
                cubageCount=parseFloat((parseFloat(cubageCount)).toFixed(3));
            }
        });
        if(flg1=="error" || flg2=="error" || flg3=="error"){
            $("#luggage").val(0);
        }else{
            luggage=(parseFloat(luggage)).toFixed(2);$("#luggage").val(luggage);
            $('#orderFinanceFormValidate').submit();
        }

        countCostCheck();
        $("#weightCount").html(weightCount);
        $("#quantityCount").html(quantityCount);
        $("#cubageCountHidden").html(cubageCount);
    }

    function  chargingWaysChange(obj){
        countQuantityOrWeightOrCubageCheck();
        if($(obj).val()=="02"){
            $(obj).parent().next().next().next().children().val($(obj).parent().next().next().children().val());
        }
    }

    function  goodsTypeParentChange(obj){
        var typeId=$(obj).val();
        CommonClient.syncpost(sys.rootPath + "/ofc/getCscGoodsTypeList",{"cscGoodsType":typeId},function(data) {
            data=eval(data);
            $(obj).parent().next().children("select").empty();
            $.each(data,function (index,CscGoodsTypeVo) {
                $(obj).parent().next().children("select").append("<option value='" + CscGoodsTypeVo.goodsTypeName + "'>" + CscGoodsTypeVo.goodsTypeName + "</option>");
            });
            $(obj).parent().next().children("select").find("option").each(function() {
                text = $(this).val();
                if($(obj).parent().next().children("select").find("option[value='"+text+"']")){
                    $(obj).parent().next().children("select").find("option[value='"+text+"']:gt(0)").remove();
                }
            });
            $(obj).parent().next().children("select").trigger("chosen:updated");

        });
    }

    function checkBillingWeight(obj){
        var value=onlyNumber($(obj).val());
        if((!(/^([1-9][\d]{0,7}|0)(\.[\d]{1,3})?$/.test(value))) || $(obj).val()=="0" || parseFloat($(obj).val())>30000){
            $(obj).css("border-color","#dd5a43")
            if($(obj).parent().children().length<2){
                $("<div id='price-error' class='help-block has-error'><i class='fa fa-times-circle w-error-icon bigger-130'></i>请检查相关数字</div>").insertAfter($(obj));
                $(obj).parent().removeClass('has-info').addClass('has-error');
                $(obj).val("");
            }else{
                $(obj).val("");
            }
        }else{
            $(obj).css("border-color","#cacaca")
            $(obj).val(value);
            $(obj).parent().find("div").remove();
        };
    }

    function countCostCheck() {
        var count=0;
        if($("#homeDeliveryFee").val()!="" && ((/^([1-9][\d]{0,5}|0)(\.[\d]{1,2})?$/).test($("#homeDeliveryFee").val()))){
            count=count+parseFloat($("#homeDeliveryFee").val());
        }
        if($("#cargoInsuranceFee").val()!="" && ((/^([1-9][\d]{0,5}|0)(\.[\d]{1,2})?$/).test($("#cargoInsuranceFee").val()))){
            count=count+parseFloat($("#cargoInsuranceFee").val());
        }
        if($("#twoDistributionFee").val()!="" && ((/^([1-9][\d]{0,5}|0)(\.[\d]{1,2})?$/).test($("#twoDistributionFee").val()))){
            count=count+parseFloat($("#twoDistributionFee").val());
        }
        if($("#collectServiceCharge").val()!="" && ((/^([1-9][\d]{0,5}|0)(\.[\d]{1,2})?$/).test($("#collectServiceCharge").val()))){
            count=count+parseFloat($("#collectServiceCharge").val());
        }
        if($("#returnListFee").val()!="" && ((/^([1-9][\d]{0,5}|0)(\.[\d]{1,2})?$/).test($("#returnListFee").val()))){
            count=count+parseFloat($("#returnListFee").val());
        }
        if($("#luggage").val()!="" && ((/^([1-9][\d]{0,5}|0)(\.[\d]{1,2})?$/).test($("#luggage").val()))){
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
        jsonStr.pickUpGoods = $("#pickUpGoods").val();
        jsonStr.homeDeliveryFee = $("#homeDeliveryFee").val();
        jsonStr.insure = $("#insure").val();
        jsonStr.cargoInsuranceFee = $("#cargoInsuranceFee").val();
        jsonStr.insureValue = $("#insureValue").val();
        jsonStr.twoDistribution = $("#twoDistribution").val();
        jsonStr.twoDistributionFee = $("#twoDistributionFee").val();
        jsonStr.collectFlag = $("#collectFlag").val();
        jsonStr.collectServiceCharge = $("#collectServiceCharge").val();
        jsonStr.collectLoanAmount = $("#collectLoanAmount").val();
        jsonStr.returnList = $("#returnList").val();
        jsonStr.returnListFee = $("#returnListFee").val();
        jsonStr.luggage = $("#luggage").val();
        //费用总计
        jsonStr.serviceCharge = $("#serviceCharge").val();
        jsonStr.expensePaymentParty = $("#expensePaymentParty").val();
        jsonStr.payment = $("#payment").val();
        jsonStr.openInvoices = $("#openInvoices").val();
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

        cscContact.contactCompanyName = $("#consignorName").val();
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
    //带出发货方
    function outConsignor(cscContact,cscContactCompany,groupId,customerCode){
        var cscContantAndCompanyDto = {};
        cscContantAndCompanyDto.cscContact = cscContact;
        cscContantAndCompanyDto.cscContactCompany = cscContactCompany;
        var param = JSON.stringify(cscContantAndCompanyDto);
        CommonClient.post(sys.rootPath + "/ofc/contactSelect", {"cscContantAndCompanyDto":param,"customerCode":customerCode}, function(data) {
            data=eval(data);
            if(data.length==1){
                $.each(data,function (index,CscContantAndCompanyDto) {
                    $("#consignorName").val(CscContantAndCompanyDto.contactCompanyName);
                    $("#consignorContactName").val(CscContantAndCompanyDto.contactName);
                    $("#consignorPhone").val(CscContantAndCompanyDto.phone);
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
                    departurePlace();
                    cscContact.purpose = "1";
                    checkConsignOrEe();
                    outConsignee(cscContact,cscContactCompany,groupId,customerCode);
                });
            }else{
                // 如果发货方条数大于1条，默认带出cookie中上次选择的
                clearConsignor();
                cscContact.purpose = "1";
                outConsignee(cscContact,cscContactCompany,groupId,customerCode);
                checkConsignOrEe();
            }
        },"json");

    }
    //带出收货方
    function outConsignee(cscContact,cscContactCompany,groupId,customerCode){
        var cscContantAndCompanyDto = {};
        cscContantAndCompanyDto.cscContact = cscContact;
        cscContantAndCompanyDto.cscContactCompany = cscContactCompany;
        var param = JSON.stringify(cscContantAndCompanyDto);
        CommonClient.post(sys.rootPath + "/ofc/contactSelect",{"cscContantAndCompanyDto":param,"customerCode":customerCode}, function(data) {
            data=eval(data);
            if(data.length==1){
                $.each(data,function (index,CscContantAndCompanyDto) {
                    $("#consigneeName").val(CscContantAndCompanyDto.contactCompanyName);
                    $("#consigneeContactName").val(CscContantAndCompanyDto.contactName);
                    $("#consigneePhone").val(CscContantAndCompanyDto.phone);
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
                    checkConsignOrEe();
                    destination();
                });
            }else{
                clearConsignee();
            }
        },"json");
    }
    //清空发货方
    function clearConsignor(){
        $("#consignorName").val("");
        $("#consignorContactName").val("");
        $("#consignorPhone").val("");
        $("#consignorCode").val("");
        $("#consignorContactCode").val("");
        $("#consignorType").val("");
        $("#consignorAddress").val("");
        //$("#city-picker3-consignor").val(" / / / ");
        //$("#city-picker3-consignor").citypicker('refresh');
        departurePlace();
    }
    //清空收货方
    function clearConsignee(){
        $("#consigneeName").val("");
        $("#consigneeContactName").val("");
        $("#consigneePhone").val("");
        $("#consigneeCode").val("");
        $("#consigneeContactCode").val("");
        $("#consigneeType").val("");
        $("#consigneeAddress").val("");
        $("#city-picker3-consignee").val(" / / / ");
        $("#city-picker3-consignee").citypicker('refresh');
        destination();
    }

    //添加一行货品……类别后的部分
    function goodsInfoListDivSupple(goodsInfoListDiv){
        goodsInfoListDiv = goodsInfoListDiv+"</select></td>";
        goodsInfoListDiv = goodsInfoListDiv + "<td>"+
                "<input class='col-xs-10 col-xs-6'  name='goodsCode' id='goodsCode' type='text' style='min-width:80px;'/>"+
                "<a  class='blue no-padding-right' style='display:inline-block;margin-top:5px;' id='goodCodeSel' onclick='seleGoods(this)'>选择</a>"
                +"</td>";
        goodsInfoListDiv = goodsInfoListDiv + "<td>"+
                "<input class='col-xs-12'  name='goodsName' id='goodsName' type='text' style='min-width:60px;'/>"
                +"</td>";
        goodsInfoListDiv = goodsInfoListDiv + "<td>"+
                "<input class='col-xs-12'  name='goodsSpec' id='goodsSpec' type='text'/>"
                +"</td>";
        goodsInfoListDiv = goodsInfoListDiv + "<td>"+
                "<input class='col-xs-12'  name='unit' id='unit' type='text'/>"
                +"</td>";
        goodsInfoListDiv = goodsInfoListDiv + "<td>"+
                "<select  id='pack' class='chosen-select form-control' name='pack' style='width:80px;'>"+
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
                "<select  id='chargingWays' name='chargingWays' class='chosen-select form-control' style='width:75px;' onchange='chargingWaysChange(this)'>"+
                "<option value='01'>件数</option>"+
                "<option value='02'>重量Kg</option>"+
                "<option value='03'>体积m³</option>"+
                +"</td>";
        goodsInfoListDiv = goodsInfoListDiv + "<td>"+
                "<input class='col-xs-12'  name='' id='' type='text' onblur='countQuantityOrWeightOrCubagePrice(this)'/>"
                +"</td>";
        goodsInfoListDiv = goodsInfoListDiv + "<td>"+
                "<input class='col-xs-12'  name='quantity' id='' type='text' onblur='countQuantOrWeightOrCubage(this)'/>"
                +"</td>";
        goodsInfoListDiv = goodsInfoListDiv + "<td id='1' style=\"display:none\">"+
                "<input class='col-xs-12'  name='billingWeight' id='' type='hidden' onblur='checkBillingWeight(this)'/>"
                +"</td>";
        goodsInfoListDiv = goodsInfoListDiv + "<td>"+
                "<input class='col-xs-12'  name='weight' id='' type='text' onblur='countQuantOrWeightOrCubage(this)'/>"
                +"</td>";
        goodsInfoListDiv = goodsInfoListDiv + "<td>"+
                "<input class='col-xs-12'  name='cubage' id='' type='text' onblur='countQuantOrWeightOrCubage(this)'/>"
                +"</td>";
        goodsInfoListDiv = goodsInfoListDiv + "</tr>";
        return goodsInfoListDiv;
        initChosen();
    }

    function checkConsignOrEe(){
        if($("#consignorName").val()!=""){
            $("#consignorName-error").parent().parent().parent().removeClass('has-error').addClass('has-success');
            $("#consignorName-error").remove();
        }
        if($("#consignorContactName").val()!=""){
            $("#consignorContactName-error").parent().parent().parent().removeClass('has-error').addClass('has-success');
            $("#consignorContactName-error").remove();
        }
        if($("#consignorPhone").val()!=""){
            $("#consignorPhone-error").parent().parent().parent().removeClass('has-error').addClass('has-success');
            $("#consignorPhone-error").remove();
        }
        if($("#consigneeName").val()!=""){
            $("#consigneeName-error").parent().parent().parent().removeClass('has-error').addClass('has-success');
            $("#consigneeName-error").remove();
        }
        if($("#consigneeContactName").val()!=""){
            $("#consigneeContactName-error").parent().parent().parent().removeClass('has-error').addClass('has-success');
            $("#consigneeContactName-error").remove();
        }
        if($("#consigneePhone").val()!=""){
            $("#consigneePhone-error").parent().parent().parent().removeClass('has-error').addClass('has-success');
            $("#consigneePhone-error").remove();
        }
    }

    // 分页查询客户列表
    function queryCustomerData(pageNum) {
        $("#custListDivTbody").html("");
        var custName = $("#custNameDiv").val();
        var param = {};
        param.pageNum = pageNum;
        param.pageSize = 10;
        param.custName = custName;
        CommonClient.post(sys.rootPath + "/ofc/distributing/queryCustomerByName", param, function(result) {
            if (result == undefined || result == null || result.result == null ||  result.result.size == 0 || result.result.list == null) {
                $("#pageBarDiv").hide();
                layer.msg("暂时未查询到客户信息！！");
            } else if (result.code == 200) {
                $("#pageBarDiv").show();
                loadCustomer(result);
                laypage({
                    cont: $("#pageBarDiv"), // 容器。值支持id名、原生dom对象，jquery对象,
                    pages: result.result.pages, // 总页数
                    skip: true, // 是否开启跳页
                    skin: "molv",
                    groups: 3, // 连续显示分页数
                    curr: result.result.pageNum, // 当前页
                    jump: function (obj, first) { // 触发分页后的回调
                        if (!first) { // 点击跳页触发函数自身，并传递当前页：obj.curr
                            queryCustomerData(obj.curr);
                        }
                    }
                });
            } else if (result.code == 403) {
                alert("没有权限")
            } else {
                $("#custListDivTbody").html("");
            }
        },"json");
    }

    // 加载客户列表
    function loadCustomer(data) {
        if ((data == null || data == '' || data == undefined) || (data.result.list.length < 1)) {
            $("#custListDivTbody").html("");
            return;
        }
        var custList = "";
        $.each(data.result.list,function (index,cscCustomerVo) {
            var channel = cscCustomerVo.channel;
            if(null == channel){
                channel = "";
            }
            custList =custList + "<tr role='row' class='odd'>";
            custList =custList + "<td class='center'> "+"<label class='pos-rel'>"+"<input name='cust' type='radio' class='ace'>"+"<span class='lbl'></span>"+"</label>"+"</td>";
            custList =custList + "<td>"+(index+1)+"</td>";
            var custType = StringUtil.nullToEmpty(cscCustomerVo.type);
            if(custType == '1'){
                custList =custList + "<td>公司</td>";
            }else if (custType == '2'){
                custList =custList + "<td>个人</td>";
            }else{
                custList =custList + "<td>"+custType+"</td>";
            }
            custList =custList + "<td>"+cscCustomerVo.customerName+"</td>";
            custList =custList + "<td>"+channel+"</td>";
            custList =custList + "<td>"+cscCustomerVo.productType+"</td>";
            custList =custList + "<td style='display: none'>"+cscCustomerVo.groupId+"</td>";
            custList =custList + "<td style='display: none'>"+cscCustomerVo.customerCode+"</td>";
            custList =custList + "</tr>";
            $("#custListDivTbody").html(custList);
        });
    }

    // 分页查询收货方或发货方列表
    function queryContactData(param,customerCode,contactType,pageNum) {
        param.pageNum = pageNum;
        param.pageSize = 10;
        var param1=param;
        param = JSON.stringify(param);
        var type= contactType == 1?"收货方":"发货方";
        var ptype= contactType == 1?"ee":"or";
        CommonClient.post(sys.rootPath + "/ofc/contactSelectForPage",{"cscContantAndCompanyDto":param,"customerCode":customerCode}, function(result) {
            if (result == undefined || result == null || result.result ==null || result.result.size == 0 || result.result.list == null) {
                $("#pageBarDivConsign"+ptype).hide();
                layer.msg("暂时未查询到"+type+"信息！！");
            } else if (result.code == 200) {
                $("#pageBarDivConsign"+ptype).show();
                loadConsignOrEE(result,contactType);
                laypage({
                    cont: $("#pageBarDivConsign"+ptype), // 容器。值支持id名、原生dom对象，jquery对象,
                    pages: result.result.pages, // 总页数
                    skip: true, // 是否开启跳页
                    skin: "molv",
                    groups: 3, // 连续显示分页数
                    curr: result.result.pageNum, // 当前页
                    jump: function (obj, first) { // 触发分页后的回调
                        if (!first) { // 点击跳页触发函数自身，并传递当前页：obj.curr

                            queryContactData(param1,customerCode,contactType,obj.curr);
                        }
                    }
                });
            } else if (result.code == 403) {
                alert("没有权限")
            } else {
                $("#contactSelectListTbody1").html("");
                $("#contactSelectListTbody2").html("");
            }
        },"json");
    }

    // 加载收货方或发货方列表
    function loadConsignOrEE(data,contactType) {
        var contactList = "";
        $.each(data.result.list,function (index,CscContantAndCompanyDto) {
            contactList =contactList + "<tr role='row' class='odd'>";
            contactList =contactList + "<td class='center'> "+"<label class='pos-rel'>"+"<input name='consignorSel' type='radio' class='ace'>"+"<span class='lbl'></span>"+"</label>"+"</td>";
            contactList =contactList + "<td>"+(index+1)+"</td>";
            contactList =contactList + "<td>"+CscContantAndCompanyDto.contactCompanyName+"</td>";
            contactList =contactList + "<td>"+CscContantAndCompanyDto.contactName+"</td>";
            contactList =contactList + "<td>"+CscContantAndCompanyDto.phone+"</td>";
            contactList =contactList + "<td>"+CscContantAndCompanyDto.detailAddress+"</td>";
            contactList =contactList + "<td style='display: none'>"+CscContantAndCompanyDto.contactCompanySerialNo+"</td>";
            contactList =contactList + "<td style='display: none'>"+CscContantAndCompanyDto.contactSerialNo+"</td>";
            contactList =contactList + "<td style='display: none'>"+CscContantAndCompanyDto.type+"</td>";
            contactList =contactList + "<td style='display: none'>"+CscContantAndCompanyDto.address+"</td>";
            contactList =contactList + "<td style='display: none'>"+CscContantAndCompanyDto.provinceName+"</td>";
            contactList =contactList + "<td style='display: none'>"+CscContantAndCompanyDto.cityName+"</td>";
            contactList =contactList + "<td style='display: none'>"+CscContantAndCompanyDto.areaName+"</td>";
            contactList =contactList + "<td style='display: none'>"+CscContantAndCompanyDto.streetName+"</td>";
            contactList =contactList + "</tr>";
        });
        if(contactType==1){
            $("#contactSelectListTbody1").html(contactList);
        }else if(contactType==2){
            $("#contactSelectListTbody2").html(contactList);
        }else{
            layer.msg("收发货方类型有误");
        }

    }

    // 分页查询货品列表
    function queryGoodsData(pageNum) {

        $("#goodsSelectListTbody").html("");
        var cscGoods = {};
        var groupId = $("#custGroupId").val();
        var customerCode = $("#customerCode").val();
        var goodsCode = $("#goodsCodeCondition").val();
        var goodsName = $("#goodsNameCondition").val();
        cscGoods.goodsCode = goodsCode;
        cscGoods.goodsName = goodsName;
        cscGoods.pNum = pageNum;
        cscGoods.pSize = 10;
        var param = JSON.stringify(cscGoods);
        CommonClient.post(sys.rootPath + "/ofc/goodsSelects", {"cscGoods":param,"customerCode":customerCode}, function(data) {

            if (data == undefined || data == null || data.result ==null || data.result.size == 0 || data.result.list == null) {
                $("#pageBarDivGoods").hide();
                layer.msg("暂时未查询到货品信息！！");
            } else if (data.code == 200) {
                $("#pageBarDivGoods").show();
                loadGoods(data.result.list);
                laypage({
                    cont: $("#pageBarDivGoods"), // 容器。值支持id名、原生dom对象，jquery对象,
                    pages: data.result.pages, // 总页数
                    skip: true, // 是否开启跳页
                    skin: "molv",
                    groups: 3, // 连续显示分页数
                    curr: data.result.pageNum, // 当前页
                    jump: function (obj, first) { // 触发分页后的回调
                        if (!first) { // 点击跳页触发函数自身，并传递当前页：obj.curr
                            queryGoodsData(obj.curr);
                        }
                    }
                });
            } else if (data.code == 403) {
                alert("没有权限")
            } else {
                $("#goodsSelectListTbody").html("");
            }
            data=eval(data);

        },"json");
    }

    // 加载货品列表
    function loadGoods(data) {
        var goodsList = "";
        $.each(data,function (index,cscGoodsVo) {
            goodsList =goodsList + "<tr role='row' class='odd'>";
            goodsList =goodsList + "<td class='center'> "+"<label class='pos-rel'>"+"<input type='radio' class='ace' name='goodse'>"+"<span class='lbl'></span>"+"</label>"+"</td>";
            goodsList =goodsList + "<td>"+cscGoodsVo.goodsTypeParentName+"</td>";//货品类别
            goodsList =goodsList + "<td>"+cscGoodsVo.goodsTypeName+"</td>";//货品小类
            goodsList =goodsList + "<td>"+cscGoodsVo.brand+"</td>";//品牌
            goodsList =goodsList + "<td>"+cscGoodsVo.goodsCode+"</td>";//货品编码
            goodsList =goodsList + "<td>"+cscGoodsVo.goodsName+"</td>";//货品名称
            goodsList =goodsList + "<td>"+cscGoodsVo.specification+"</td>";//规格
            goodsList =goodsList + "<td>"+cscGoodsVo.unit+"</td>";//单位
            goodsList =goodsList + "<td>"+cscGoodsVo.barCode+"</td>";//条形码
            goodsList =goodsList + "<td style=\"display:none\">"+cscGoodsVo.goodsTypeId+"</td>";//大类ID
            goodsList =goodsList + "</tr>";
        });
        $("#goodsSelectListTbody").html(goodsList);
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

        $("#openInvoicesV").change(function(){
            if($(this).prop("checked")){
                $("#openInvoices").val("1");
            }else{
                $("#openInvoices").val("0");
            }

        });

        $("#orderPlaceConTableBtn").click(function () {
            $("#goodsInfoListDiv tr td input").css("border-color","#cacaca");
            $("#goodsInfoListDiv tr td div.has-error").remove();
            $("select[name='chargingWays']").each(function(){
                if($(this).val()=="01"){
                    var value=onlyNumber($(this).parent().next().next().children().val());
                    checkValue($(this).parent().next().next().children(),value,"件数计件数量必填");
                }else if($(this).val()=="02"){
                    var value=onlyNumber($(this).parent().next().next().next().next().children().val());
                    checkValue($(this).parent().next().next().next().next().children(),value,"重量计件重量必填");
                }else if($(this).val()=="03"){
                    var value=onlyNumber($(this).parent().next().next().next().next().next().children().val());
                    checkValue($(this).parent().next().next().next().next().next().children(),value,"体积计件体积必填");
                }
            });
            $("input[name='weight']").each(function(){
                var value=onlyNumber($(this).val());
                checkValue($(this),value,"添加货品重量必输")
            });
            $('#orderFundamentalFormValidate').submit();
            $('#orderFinanceFormValidate').submit();
            $('#orderFinanceChargeFormValidate').submit();
            $('#orderInfoTableValidate').submit();
            if($("#orderFundamentalFormValidate").find("div.has-error").length>0
                    || $("#orderFinanceFormValidate").find("div.has-error").length>0
                    || $("#orderFinanceChargeFormValidate").find("div.has-error").length>0
                    || $("#orderInfoTableValidate").find("div.has-error").length>0
                    || $("#goodsInfoListDiv").find("div.has-error").length>0){
                alert("您输入的内容还有一些问题，请仔细检查哦");
                return false;
            }
            //卡班类型必须输入运输单号
            if($("#businessType").val() == "602"){
                var transCode = $("#transCode").val().trim();
                if(transCode == null || transCode == "" || transCode == undefined){
                    alert("业务类型选择卡班，必须输入运输单号！");
                    return false;
                }
            }

            var jsonStr = {};
            //订单基本信息
            jsonStr.businessType = $("#businessType").val();
            jsonStr.merchandiser = $("#merchandiser").val();
            jsonStr.transportType = $("#transportType").val();
            if($('#orderTime').val()!=""){
                jsonStr.orderTime = $('#orderTime').val()+" 00:00:00";
            }
            jsonStr.transCode = $("#transCode").val();
            jsonStr.custName = $("#custName").val();//000
            jsonStr.custCode = $("#customerCode").val();//000
            jsonStr.custOrderCode = $("#custOrderCode").val();  // 客户订单号
            jsonStr.notes = $("#transRequire").val();//
            jsonStr.weight = $("#weightCount").html();
            jsonStr.quantity = $("#quantityCount").html();
            var cubageAmount ="";
            if($("#cubageCountHidden").html()!=""){
                cubageAmount = $("#cubageCountHidden").html();
            }
            jsonStr.cubage = cubageAmount;
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
                        case 1 :orderGoods.goodsType = param.getElementsByTagName("select")[0].options[param.getElementsByTagName("select")[0].selectedIndex].text;break;
                        case 2 :orderGoods.goodsCategory = param.getElementsByTagName("select")[0].value;break;
                        case 3 :orderGoods.goodsCode = param.getElementsByTagName("input")[0].value;break;
                        case 4 :orderGoods.goodsName = param.getElementsByTagName("input")[0].value;break;
                        case 5 :orderGoods.goodsSpec = param.getElementsByTagName("input")[0].value;break;
                        case 6 :orderGoods.unit = param.getElementsByTagName("input")[0].value;break;
                        case 7 :orderGoods.pack = param.getElementsByTagName("select")[0].value;break;
                        case 8 :orderGoods.chargingWays = param.getElementsByTagName("select")[0].value;break;
                        case 9 :orderGoods.chargingUnitPrice = param.getElementsByTagName("input")[0].value;break;
                        case 10 :orderGoods.quantity = param.getElementsByTagName("input")[0].value;break;
                        case 11 :orderGoods.billingWeight = param.getElementsByTagName("input")[0].value;break;
                        case 12 :orderGoods.weight = param.getElementsByTagName("input")[0].value;break;
                        case 13 :orderGoods.cubage = param.getElementsByTagName("input")[0].value;break;
                    }
                    if(tableRows == 1 && tableCells == 1){
                        jsonStr.goodsType = param.getElementsByTagName("select")[0].value;
                        jsonStr.goodsTypeName=orderGoods.goodsType;
                    }
                }
                /*if(orderGoods.chargingWays=="01"){
                    orderGoods.quantity=orderGoods.chargingQuantity;
                    orderGoods.quantityUnitPrice=orderGoods.chargingUnitPrice;
                }else if(orderGoods.chargingWays=="02"){
                    orderGoods.weight=orderGoods.chargingQuantity;
                    orderGoods.weightUnitPrice=orderGoods.chargingUnitPrice;
                }else if(orderGoods.chargingWays=="03"){
                    orderGoods.cubage=orderGoods.chargingQuantity;
                    orderGoods.volumeUnitPrice=orderGoods.chargingUnitPrice;
                }*/
                orderGoodsList[tableRows - 1] = orderGoods;
            }
            var tag = "tranplace";
            var ofcOrderDTO = JSON.stringify(jsonStr);
            var orderGoodsListStr = JSON.stringify(orderGoodsList);
            var cscContantAndCompanyDtoConsignorStr;
            var cscContantAndCompanyDtoConsigneeStr;
            cscContantAndCompanyDtoConsignorStr = getCscContantAndCompanyDtoConsignorStr();
            cscContantAndCompanyDtoConsigneeStr = getCscContantAndCompanyDtoConsigneeStr();
            xescm.common.submit("/ofc/orderPlaceCon"
                    ,{"ofcOrderDTOStr":ofcOrderDTO
                        ,"orderGoodsListStr":orderGoodsListStr+"~`"
                        ,"cscContantAndCompanyDtoConsignorStr":cscContantAndCompanyDtoConsignorStr
                        ,"cscContantAndCompanyDtoConsigneeStr":cscContantAndCompanyDtoConsigneeStr
                        ,"cscSupplierInfoDtoStr":null
                        ,"tag":tag}
                    ,"您确认提交订单吗?"
                    ,function () {
                        // 更新开单员
                        updateLastUserData();
                        location.reload();
//                        var getTimestamp  = new Date().getTime();
//                        xescm.common.loadPage("/ofc/tranLoad"+"?timestamp="+getTimestamp);
                        //xescm.common.goBack("/ofc/orderPlace");
                    });

        });


        // 分页查询客户
        $("#custSelectFormBtn").click(function() {
            queryCustomerData(1);
        });

        $("#custEnter").click(function () {
            var custEnterTag = "";
            $("#custListDivTbody").find("tr").each(function(index){
                var tdArr = $(this).children();
                if(tdArr.eq(0).find("input").prop("checked")){
                    custEnterTag = "1";
                    if($("#customerCode").val()!=tdArr.eq(7).text()){
                        var type = tdArr.eq(2).text();//类型
                        var customerName = tdArr.eq(3).text();//公司名称
                        var channel = tdArr.eq(4).text();//    渠道
                        var productType = tdArr.eq(5).text();//    产品类别
                        var groupId = tdArr.eq(6).text();//    产品类别
                        var customerCode = tdArr.eq(7).text();//    产品类别
                        $("#custName").val(customerName);
                        $("#custGroupId").val(groupId);
                        $("#customerCode").val(customerCode);

                        var cscContact = {};
                        var cscContactCompany = {};
                        cscContact.purpose = "2";
                        outConsignor(cscContact,cscContactCompany,groupId,customerCode);
                        $("#goodsInfoListDiv").html("");

                        countQuantityOrWeightOrCubageCheck();
                    }
                }
            });
            if(custEnterTag==""){
                alert("请至少选择一行");
            }else{
                $("#custListDiv").fadeOut(0);//淡入淡出效果 隐藏div
            }
        });

        // 发货方地址选择
        $("#city-picker-consignor").click(function () {
            departurePlace();
        });

        // 收货方地址选择
        $("#city-picker-consignee").click(function () {
            destination();
        });

        $("#goodsSelectFormBtn").click(function () {
            queryGoodsData(1);
        });



        $("#consignorSelectFormBtn").click(function () {
            $("#contactSelectListTbody2").html("");
            var cscContantAndCompanyDto = {};
            var cscContact = {};
            var cscContactCompany = {};

            cscContactCompany.contactCompanyName = $("#consignorName2").val();
            cscContact.purpose = "2";
            cscContact.contactName = $("#consignorPerson2").val();
            cscContact.phone = $("#consignorPhoneNumber2").val();
            cscContantAndCompanyDto.cscContact = cscContact;
            cscContantAndCompanyDto.cscContactCompany = cscContactCompany;
            var customerCode = $("#customerCode").val();

            queryContactData(cscContantAndCompanyDto,customerCode,2,1);
        });

        $("#consigneeSelectFormBtn").click(function () {
            $("#contactSelectListTbody1").html("");
            var cscContantAndCompanyDto = {};
            var cscContact = {};
            var cscContactCompany = {};
            cscContactCompany.contactCompanyName = $("#consignorName1").val();
            cscContact.purpose = "1";
            cscContact.contactName = $("#consignorPerson1").val();
            cscContact.phone = $("#consignorPhoneNumber1").val();
            cscContantAndCompanyDto.cscContact = cscContact;
            cscContantAndCompanyDto.cscContactCompany = cscContactCompany;

            var customerCode = $("#customerCode").val();

            queryContactData(cscContantAndCompanyDto,customerCode,1,1);
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
                    var contactCompanySerialNo = tdArr.eq(6).text();//    发货方编码
                    var contactSerialNo = tdArr.eq(7).text();//    发货方联系人编码
                    var type = tdArr.eq(8).text();//    发货方类型
                    var address = tdArr.eq(9).text();//    门牌号
                    var provinceName = tdArr.eq(10).text();//    省
                    var cityName = tdArr.eq(11).text();//    市
                    var areaName = tdArr.eq(12).text();//    区
                    var streetName = tdArr.eq(13).text();//    县

                    $("#consignorName").val(consignorName);
                    $("#consignorContactName").val(contacts);
                    $("#consignorPhone").val(contactsNumber);
                    $("#consignorCode").val(contactCompanySerialNo);
                    $("#consignorContactCode").val(contactSerialNo);
                    $("#consignorType").val(type);
                    $("#consignorAddress").val(address);
                    var paramAddressNameToPage = provinceName
                            + "/" + cityName
                            + "/" + areaName
                            + "/" + streetName;
                    $("#city-picker3-consignor").val(paramAddressNameToPage);
                    $("#city-picker3-consignor").citypicker('refresh');
                    departurePlace();
                }
            });
            if(consignorin==""){
                alert("请至少选择一行");
            }else{
                $("#consignorListDiv").fadeOut(0);//淡入淡出效果 隐藏div
                checkConsignOrEe();
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

                    var contactCompanySerialNo = tdArr.eq(6).text();//    收货方编码
                    var contactSerialNo = tdArr.eq(7).text();//    收货方联系人编码
                    var type = tdArr.eq(8).text();//    收货方类型
                    var address = tdArr.eq(9).text();//    门牌号
                    var provinceName = tdArr.eq(10).text();//    省
                    var cityName = tdArr.eq(11).text();//    市
                    var areaName = tdArr.eq(12).text();//    区
                    var streetName = tdArr.eq(13).text();//    县
                    $("#consigneeName").val(consignorName);
                    $("#consigneeContactName").val(contacts);
                    $("#consigneePhone").val(contactsNumber);
                    $("#consigneeCode").val(contactCompanySerialNo);
                    $("#consigneeContactCode").val(contactSerialNo);
                    $("#consigneeType").val(type);
                    $("#consigneeAddress").val(address);
                    var paramAddressNameToPage = provinceName
                            + "/" + cityName
                            + "/" + areaName
                            + "/" + streetName;
                    $("#city-picker3-consignee").val(paramAddressNameToPage);
                    $("#city-picker3-consignee").citypicker('refresh');
                    destination();
                }
            });
            if(consignorout==""){
                alert("请至少选择一行");
            }else{
                $("#consigneeListDiv").fadeOut(0);//淡入淡出效果 隐藏div
                checkConsignOrEe();
            }
        });

        $("#consignorListDivBlock").click(function(){
            if(!validateCustChosen()){
                alert("请先选择客户");
            }else{
                $("#pageBarDivConsignor").hide();
                $("#contactSelectListTbody2").html("");
                $("#consignorListDiv").fadeIn(0);//淡入淡出效果 显示div
            }
        });

        $("#consignorListDivNoneTop").click(function(){

            $("#consignorListDiv").fadeOut(0);//淡入淡出效果 隐藏div

        });

        $("#consignorListDivNoneBottom").click(function(){

            $("#consignorListDiv").fadeOut(0);//淡入淡出效果 隐藏div

        });

        $("#consigneeListDivBlock").click(function(){
            if(!validateCustChosen()){
                alert("请先选择客户")
            }else{
                $("#pageBarDivConsignee").hide();
                $("#contactSelectListTbody1").html("");
                $("#consigneeListDiv").fadeIn(0);//淡入淡出效果 显示div
            }

        });

        $("#consigneeListDivNoneTop").click(function(){

            $("#consigneeListDiv").fadeOut(0);//淡入淡出效果 隐藏div

        });

        $("#consigneeListDivNoneBottom").click(function(){

            $("#consigneeListDiv").fadeOut(0);//淡入淡出效果 隐藏div

        });


        var transportTypePub = "10";
        $("#transportType").val(transportTypePub);
        var expensePaymentParty = "10";
        $("#expensePaymentParty").val(expensePaymentParty)
        $("input[name=transportTypeV]").change(function () {
            $("#transportType").val($("input[name=transportTypeV]:checked").val());
        });
        $("input[name=expensePaymentPartyV]").change(function () {
            $("#expensePaymentParty").val($("input[name=expensePaymentPartyV]:checked").val());
        });

        $("#addGoods").click(function () {
            if(!validateCustChosen()){
                alert("请先选择客户")
            }else{
                var goodsInfoListDiv = "";
                var groupId = $("#custGroupId").val();
                var firstGoodsType = null;
                goodsInfoListDiv = goodsInfoListDiv + "<tr role='row' class='odd' align='center'>";
                goodsInfoListDiv = goodsInfoListDiv + "<td><a onclick='deleteGood(this)' class='red' style='margin-top:5px;'>删除</a></td>";
                goodsInfoListDiv = goodsInfoListDiv + "<td>"+
                        "<select  id='goodsType' name='goodsType' class='chosen-select' style='width:100px;' onchange='goodsTypeParentChange(this)'>";
                if($("#goodsInfoListDiv").find("tr").length<1){

                    CommonClient.syncpost(sys.rootPath + "/ofc/getCscGoodsTypeList",{"pid":null},function(data) {
                        data=eval(data);
                        $.each(data,function (index,CscGoodsTypeVo) {
                            if(0 == index){
                                firstGoodsType = CscGoodsTypeVo.id;
                            }
                            goodsInfoListDiv = goodsInfoListDiv + "<option value='" + CscGoodsTypeVo.id + "'>" + CscGoodsTypeVo.goodsTypeName + "</option>";
                        });
                        if($("#goodsInfoListDiv").find("tr").length==1){
                            $("#goodsInfoListDiv tr:eq(0) td:eq(0) select option").each(function() {
                                text = $(this).val();
                                if($("select option[value='"+text+"']").length > 1)
                                    $("select option[value='"+text+"']:gt(0)").remove();
                            });
                        }
                    });
                }else{
                    $("#goodsInfoListDiv tr:eq(0) td:eq(1)").find("select:first").find("option").each(function() {
                        var provale=$("#goodsInfoListDiv tr:eq("+($("#goodsInfoListDiv").find("tr").length-1)+") td:eq(1)").find("select:first").val();
                        text = $(this).text();
                        value = $(this).val();
                        if(provale==value){
                            goodsInfoListDiv = goodsInfoListDiv +"<option value='"+value+"' selected = 'selected'>"+text+"</option>";
                        }else{
                            goodsInfoListDiv = goodsInfoListDiv +"<option value='"+value+"'>"+text+"</option>";
                        }

                    });
                }
                goodsInfoListDiv = goodsInfoListDiv + "</select>";


                goodsInfoListDiv = goodsInfoListDiv + "<td>"+
                        "<select  id='goodsCategory' name='goodsCategory' class='chosen-select form-control' style='width:80px;'>";
                if($("#goodsInfoListDiv").find("tr").length<1){
                    if(null != firstGoodsType){
                        CommonClient.syncpost(sys.rootPath + "/ofc/getCscGoodsTypeList",{"cscGoodsType":firstGoodsType},function(data) {
                            data=eval(data);
                            $.each(data,function (index,CscGoodsTypeVo) {
                                goodsInfoListDiv = goodsInfoListDiv + "<option value='" + CscGoodsTypeVo.goodsTypeName + "'>" + CscGoodsTypeVo.goodsTypeName + "</option>";
                            });
                            goodsInfoListDiv = goodsInfoListDiv + "</select>";
                            goodsInfoListDiv=goodsInfoListDivSupple(goodsInfoListDiv);
                            $("#goodsInfoListDiv").append(goodsInfoListDiv);
                            if($("#goodsInfoListDiv").find("tr").length==1){
                                $("#goodsInfoListDiv tr:eq(0) td:eq(1) select option").each(function() {
                                    text = $(this).val();
                                    if($("select option[value='"+text+"']").length > 1)
                                        $("select option[value='"+text+"']:gt(0)").remove();

                                });
                            }
                        });
                    }
                }else{
                    $("#goodsInfoListDiv tr:eq("+($("#goodsInfoListDiv").find("tr").length-1)+") td:eq(2)").find("select:first").find("option").each(function() {
                        var gateVal=$("#goodsInfoListDiv tr:eq("+($("#goodsInfoListDiv").find("tr").length-1)+") td:eq(2)").find("select:first").val();
                        text = $(this).text();
                        if(gateVal==text){
                            goodsInfoListDiv = goodsInfoListDiv +"<option value='"+text+"' selected = 'selected'>"+text+"</option>";
                        }else{
                            goodsInfoListDiv = goodsInfoListDiv +"<option value='"+text+"'>"+text+"</option>";
                        }
                    });
                    goodsInfoListDiv = goodsInfoListDiv + "</select>";
                    goodsInfoListDiv=goodsInfoListDivSupple(goodsInfoListDiv);
                    $("#goodsInfoListDiv").append(goodsInfoListDiv);

                }
                initChosen();
            }
            initChosen();

        });

        $("#consignorName,#consigneeName").blur(function () {
            if($(this).val().length>100){
                if($(this).parent().children().length==3){
                    $("<div id='price-error' class='help-block has-error'><i class='fa fa-times-circle w-error-icon bigger-130'></i>超过最大长度</div>").insertAfter($(this));
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
                    $("<div id='price-error' class='help-block has-error'><i class='fa fa-times-circle w-error-icon bigger-130'></i>超过最大长度</div>").insertAfter($(this));
                    $(this).parent().parent().parent().removeClass('has-info').addClass('has-error');
                }
            }else{
                $(this).parent().find("div").remove();
                $(this).parent().parent().parent().removeClass('has-error').addClass('has-success');
            }
        });
        /*$("#consignorPhone,#consigneePhone").blur(function () {
            if($(this).val().length>20){
                if($(this).parent().children().length==1){
                    $("<div id='price-error' class='help-block has-error'><i class='fa fa-times-circle w-error-icon bigger-130'></i>超过最大长度</div>").insertAfter($(this));
                    $(this).parent().parent().parent().removeClass('has-info').addClass('has-error');
                }
            }else if(!(/^(-?[1-9]\d*|0)$/.test($(this).val()))){
                if($(this).parent().children().length==1){
                    $("<div id='price-error' class='help-block has-error'><i class='fa fa-times-circle w-error-icon bigger-130'></i>电话格式错误</div>").insertAfter($(this));
                    $(this).parent().parent().parent().removeClass('has-info').addClass('has-error');
                }
            }else{
                $(this).parent().find("div").remove();
                $(this).parent().parent().parent().removeClass('has-error').addClass('has-success');
            }
        });*/

        $("#goodsListDivNoneTop").click(function(){

            $("#goodsListDiv").fadeOut(0);//淡入淡出效果 隐藏div
            $("#yangdongxushinanshen").attr("id","goodCodeSel");
            $("#typeSel").attr("id","");

        });

        $("#goodsListDivNoneBottom").click(function(){

            $("#goodsListDiv").fadeOut(0);//淡入淡出效果 隐藏div
            $("#yangdongxushinanshen").attr("id","goodCodeSel");
            $("#typeSel").attr("id","");

        });

        $("#goodsEnter").click(function () {
            var goodsInfoListDiv = "";
            $("#goodsSelectListTbody").find("tr").each(function(index){
                var tdArr = $(this).children();
                if(tdArr.eq(0).find("input").prop("checked")){
                    var goodsType = tdArr.eq(1).text();//货品种类
                    var goodsGate = tdArr.eq(2).text();//货品类别
                    var goodsCode = tdArr.eq(4).text();//货品编码
                    var goodsName = tdArr.eq(5).text();//货品名称
                    var specification = tdArr.eq(6).text();//规格
                    var unit = tdArr.eq(7).text();//单位
                    var typeID = tdArr.eq(9).text();//单位
                    $("#typeSel").val(typeID);
                    $("#typeSel").trigger("chosen:updated");
                    goodsTypeParentChange($("#typeSel"));
                    //$("#yangdongxushinanshen").parent().parent().find("td").eq(1).find("select").find("option[text='"+goodsType+"']").attr("selected", true);
                    $("#yangdongxushinanshen").parent().parent().find("td").eq(2).find("select").val(goodsGate);
                    $("#yangdongxushinanshen").parent().parent().find("td").eq(2).find("select").trigger("chosen:updated");
                    $("#yangdongxushinanshen").parent().parent().find("td").eq(3).find("input").val(goodsCode);
                    $("#yangdongxushinanshen").parent().parent().find("td").eq(4).find("input").val(goodsName);
                    $("#yangdongxushinanshen").parent().parent().find("td").eq(5).find("input").val(specification);
                    $("#yangdongxushinanshen").parent().parent().find("td").eq(6).find("input").val(unit);
                    $("#yangdongxushinanshen").attr("id","goodCodeSel");
                    goodsInfoListDiv="true";
                }
            });
            if(goodsInfoListDiv==""){
                alert("请至少选择一行");
            }else{
                $("#goodsListDiv").fadeOut(0);
                $("#yangdongxushinanshen").attr("id","goodCodeSel");
                $("#typeSel").attr("id","");
            }
        });

        //$('#merchandiser').editableSelect();

        $("#createCustBtn").click(function () {
            /*var csc_url = $("#csc_url").html();
            var url = csc_url + "/csc/customer/toAddCustomerPage";
            xescm.common.loadPage(url);*/
            var url = "/csc/customer/toAddCustomerPage";
            var html = window.location.href;
            var index = html.indexOf("/index#");
            window.open(html.substring(0,index) + "/index#" + url);
        });
    })
    $("#custListDivBlock").click(function () {
        $("#custListDiv").fadeIn(0);//淡入淡出效果 显示div
    });
    $("#custListDivNoneBottom").click(function () {
        $("#custListDiv").fadeOut(0);//淡入淡出效果 隐藏div
    });
    $("#custListDivNoneTop").click(function () {
        $("#custListDiv").fadeOut(0);//淡入淡出效果 隐藏div
    });

    $("#merchandiser").editableSelect();

    function initChosen() {
        $('.chosen-select').chosen({allow_single_deselect: true});
        //resize the chosen on window resize
        $(window).off('resize.chosen').on('resize.chosen', function () {
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
    }

    // 设置当前用户上次选择的开单员
    function setLastUserData() {
        // 设置当前用户上次选择的开单员
        var loginUser = $('#login_user').html();
        var lastSelMer = getCookie(loginUser);
        var lastSelConsignor = getCookie(loginUser+"@consignor");
//        console.log("用户:" + loginUser + " 最后选择的开单员为：" + lastSelMer);
//        console.log("用户:" + loginUser + " 最后选择的发货地址为：" + lastSelConsignor);

        if (lastSelMer != '') {
            $("#merchandiser").val(lastSelMer);
//            $("#merchandiser").find("option[text='"+lastSelMer+"']").attr("selected",true);
        }

        // 发货方地址
        if (lastSelConsignor != '') {
            var consignor = lastSelConsignor.split('~');
            $("#city-picker3-consignor").val(consignor[0]);
            $("#city-picker3-consignor").citypicker('refresh');
        }

    }

    // 更新开单员
    function updateLastUserData() {
        var loginUser = $('#login_user').html();

        // 设置上次开单员
        var merchandiser = $("#merchandiser").val();
        checkAndSetCookie(loginUser, merchandiser);

        // 发货方地址
        var consignor = $("#city-picker3-consignor").val();
        checkAndSetCookie(loginUser+"@consignor" ,consignor);

    }

    // 检查cookie是否存在旧值，如果不存在则创建，
    // 如果存在则判断新旧值是否相同，不同的更新
    function checkAndSetCookie(keyName, value) {
        var oldVal = getCookie(keyName);
        if(oldVal != '') {
            if (oldVal != value) {
                editCookie(keyName, value, -1);
            }
        } else {
            addCookie(keyName, value, -1)
        }
    }

    // 添加cookie
    function addCookie(name,value,expiresHours){
        var cookieString=name+"="+escape(value);
        //判断是否设置过期时间,0代表关闭浏览器时失效
        if(expiresHours>0){
            var date=new Date();
            date.setTime(date.getTime+expiresHours*3600*1000);
            cookieString=cookieString+"; expires="+date.toGMTString();
        }
        document.cookie=cookieString;
    }

    // 根据指定名称的cookie修改cookie的值
    function editCookie(name,value,expiresHours){
        var cookieString=name+"="+escape(value);
        //判断是否设置过期时间,0代表关闭浏览器时失效
        if(expiresHours>0){
            var date=new Date();
            date.setTime(date.getTime+expiresHours*3600*1000); //单位是多少小时后失效
            cookieString=cookieString+"; expires="+date.toGMTString();
        }
        document.cookie=cookieString;
    }

    // 获取指定名称的cookie值
    function getCookie(name){
        var strCookie=document.cookie;
        var arrCookie=strCookie.split("; ");
        for(var i=0;i<arrCookie.length;i++){
            var arr=arrCookie[i].split("=");
            if(arr[0]==name){
                return unescape(arr[1]);
            } else{
                continue;
            }
        }
        return "";
    }

    // 删除指定名称的cookie
    function deleteCookie(name){
        var date=new Date();
        date.setTime(-1); //设定一个过去的时间即可
        document.cookie=name+"=v; expires="+date.toGMTString();
    }
    //货品行校验数据并提示
    function checkValue(obj,value,msg){
        if(value==""){
            $(obj).css("border-color","#dd5a43")
            if($(obj).parent().children().length<2){
                $("<div id='price-error' class='help-block has-error'><i class='fa fa-times-circle w-error-icon bigger-130'></i>"+msg+"</div>").insertAfter($(obj));
                $(obj).parent().removeClass('has-info').addClass('has-error');
                $(obj).val("");
            }else{
                $(obj).val("");
            }
        }else{
            $(obj).css("border-color","#cacaca")
            $(obj).val(value);
            $(obj).parent().find("div").remove();
        }
    }
    //验证开单员不为空
    $(".es-list").click(function(){
        checkType();
    })

    function checkType() {
        var type = $("#merchandiser").val();
        if(type == null || type == ""){
            $("#merchandiser-error").html("<i class='fa fa-times-circle w-error-icon bigger-130'></i>请选择开单员");
        }else{
            $("#merchandiser-error").css("display","none")
            /*   $("#merchandiser-error").html("<i class='fa fa-check-circle-o w-error-icon bigger-130' style='color:#6bc827;'></i>");
               $("#merchandiser").parent().parent().parent().removeClass('has-error').addClass("has-success");*/
        }
    }
    //验证临时客户
    $("#custEnter").click(function(){
      if( $("#custName").val()=="临时客户"){
          $("#currentAmount").val("").attr("disabled","true").css("cursor","default");
          $("#toPayAmount").val("").attr("disabled","true").css("cursor","default");
          $("#returnAmount").val("").attr("disabled","true").css("cursor","default");
          $("#monthlyAmount").val("").attr("disabled","true").css("cursor","default");
      }else{
          $("#currentAmount").removeAttr("disabled");
          $("#toPayAmount").removeAttr("disabled");
          $("#returnAmount").removeAttr("disabled");
          $("#monthlyAmount").removeAttr("disabled");
      }
    })

</script>
<script type="text/javascript" src="../js/jquery.editable-select.min.js"></script>
