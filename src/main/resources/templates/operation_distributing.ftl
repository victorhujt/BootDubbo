<head>
    <title>城配下单</title>
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
<body>
<!--goodsListDiv-->
<div class="modal-content" id="goodsListDiv" style="display: none;">
    <div class="modal-header"><span id="goodsListDivNoneTop" style="cursor:pointer"><button type="button" id="" style="cursor:pointer" class="bootbox-close-button close" data-dismiss="modal" aria-hidden="true">×</button></span>
        <h4 class="modal-title">货品列表</h4></div>
    <div class="modal-body">
        <div class="bootbox-body">
            <form id="goodsSelConditionForm" class="form-horizontal" role="form">
                <div class="form-group">
                    <label class="control-label col-sm-1 no-padding-right" for="name">货品种类</label>
                    <div class="col-sm-3">
                        <div class="clearfix">
                            <select id="goodsTypeId" name="goodsTypeId" ></select>
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-sm-1 no-padding-right" for="name">货品小类</label>
                    <div class="col-sm-3">
                        <div class="clearfix">
                            <select id="goodsSecTypeId" name="goodsTypeSonId"></select>
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-sm-1 no-padding-right" for="name">货品名称</label>
                    <div class="col-sm-3">
                        <div class="clearfix">
                            <input  id = "goodsName" name="goodsName" type="text" style="color: black" class="form-control input-sm" placeholder="" aria-controls="dynamic-table">
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-sm-1 no-padding-right" for="name">条形码</label>
                    <div class="col-sm-3">
                        <div class="clearfix">
                            <input  id = "barCode" name="barCode" type="text" style="color: black" class="form-control input-sm" placeholder="" aria-controls="dynamic-table">
                            <input id="customerId" name ="customerId" type="hidden"/>
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
                            <input  id = "consignorName2" name="cscContactCompany.contactCompanyName" onkeyup="this.value=this.value.replace(/(^\s*)|(\s*$)/g, '')" type="text" style="color: black" class="form-control input-sm" placeholder="" aria-controls="dynamic-table">
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-sm-1 no-padding-right" for="name">联系人</label>
                    <div class="col-sm-3">
                        <div class="clearfix">
                            <input  id = "consignorPerson2" name="cscContact.contactName" onkeyup="this.value=this.value.replace(/(^\s*)|(\s*$)/g, '')" type="text" style="color: black" class="form-control input-sm" placeholder="" aria-controls="dynamic-table">
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-sm-1 no-padding-right" for="name">联系电话</label>
                    <div class="col-sm-3">
                        <div class="clearfix">
                            <input  id = "consignorPhoneNumber2" name="cscContact.phone" onkeyup="this.value=this.value.replace(/(^\s*)|(\s*$)/g, '')" type="text" style="color: black" class="form-control input-sm" placeholder="" aria-controls="dynamic-table">
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
                            <input  id = "consignorName1" name="contactCompanyName" onkeyup="this.value=this.value.replace(/(^\s*)|(\s*$)/g, '')"  type="text" style="color: black" class="form-control input-sm" placeholder="" aria-controls="dynamic-table">
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-sm-1 no-padding-right" for="name">联系人</label>
                    <div class="col-sm-3">
                        <div class="clearfix">
                            <input  id = "consignorPerson1" name="contactName" onkeyup="this.value=this.value.replace(/(^\s*)|(\s*$)/g, '')" type="text" style="color: black" class="form-control input-sm" placeholder="" aria-controls="dynamic-table">
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-sm-1 no-padding-right" for="name">联系电话</label>
                    <div class="col-sm-3">
                        <div class="clearfix">
                            <input  id = "consignorPhoneNumber1" name="phone" onkeyup="this.value=this.value.replace(/(^\s*)|(\s*$)/g, '')" type="text" style="color: black" class="form-control input-sm" placeholder="" aria-controls="dynamic-table">
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
                            <input  id = "custNameDiv" name="cscContactCompany.contactCompanyName" onkeyup="this.value=this.value.replace(/(^\s*)|(\s*$)/g, '')" type="text" style="color: black" class="form-control input-sm" placeholder="" aria-controls="dynamic-table">
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
<!--goods&Consigee-->
<div class="modal-content" id="goodsAndConsigneeDiv" style="display: none;">
    <div class="modal-body">
        <div class="bootbox-body">
            <form class="form-horizontal" role="form">

                <div class="form-group">
                    <label class="control-label col-sm-1 no-padding-right" for="name">货品编码</label>
                    <div class="col-sm-3">
                        <div class="clearfix">
                            <input  id = "goodsCodeDiv" name="" type="text" readonly="readonly" style="color: black" class="form-control input-sm" placeholder="" aria-controls="dynamic-table">
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-sm-1 no-padding-right" for="name">货品名称</label>
                    <div class="col-sm-3">
                        <div class="clearfix">
                            <input  id = "goodsNameDiv" name="" type="text" onkeyup="this.value=this.value.replace(/(^\s*)|(\s*$)/g, '')" readonly="readonly" style="color: black" class="form-control input-sm" placeholder="" aria-controls="dynamic-table">
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-sm-1 no-padding-right" for="name">规格</label>
                    <div class="col-sm-3">
                        <div class="clearfix">
                            <input  id = "specificationDiv" name="" type="text" onkeyup="this.value=this.value.replace(/(^\s*)|(\s*$)/g, '')" readonly="readonly" style="color: black" class="form-control input-sm" placeholder="" aria-controls="dynamic-table">
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-sm-1 no-padding-right" for="name">单位</label>
                    <div class="col-sm-3">
                        <div class="clearfix">
                            <input  id = "unitDiv" name="" type="text" readonly="readonly" style="color: black" class="form-control input-sm" placeholder="" aria-controls="dynamic-table">
                        </div>
                    </div>
                </div>

                <table id="dynamic-table" style="float: left;width: 30%" class="table table-striped table-bordered table-hover dataTable no-footer" role="grid" aria-describedby="dynamic-table_info">
                    <thead>
                    <tr role="row">
                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Domain: activate to sort column ascending">收货方名称</th>
                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Price: activate to sort column ascending">发货数量</th>
                    </tr>
                    </thead>
                    <tbody id="goodsAndConsigneeTbody">
                    </tbody>
                </table>
            </form>

        </div>
    </div>
    <div class="form-group" >
        <div class="modal-footer"><button id="goodsAndConsigneeEnter" data-bb-handler="confirm" type="button" class="btn btn-primary">保存</button><span id="goodsAndConsigneeDivNoneBottom" style="cursor:pointer"><button  data-bb-handler="cancel" type="button" class="btn btn-default">不保存</button></span></div>
    </div>
</div>

<br/>
<div class="col-xs-9">
    <button class="btn btn-white btn-info btn-bold btn-interval" id="">
        <i class="ace-icon fa fa-floppy-o bigger-120 blue"></i>
        历史订单选择
    </button>

    <button class="btn btn-white btn-info btn-bold btn-interval" id="to_operation_distributing_excel">
        <i class="ace-icon fa fa-floppy-o bigger-120 blue"></i>
        Excel导入
    </button>

    <button class="btn btn-white btn-info btn-bold btn-interval" id="">
        <i class="ace-icon fa fa-floppy-o bigger-120 blue"></i>
        收发货方档案
    </button>
</div>
<br/>
<br/>
<br/>
<form id="operationDistributingFormValidate" method="post" class="form-horizontal" role="form">
    <div class="col-xs-12">
        <div class="form-group">
            <div><label class="control-label col-label no-padding-right" for="">订单日期</label>
            <div class="col-xs-3">
                <div class="clearfix">
                    <input class="col-xs-10 col-xs-12" name="orderTime" id="orderTime" value="${(currentTime?string("yyyy-MM-dd"))!""}" type="text" placeholder="订单日期"
                           onClick="WdatePicker({isShowClear:true,readOnly:true,dateFmt:'yyyy-MM-dd',minDate:'%y-%M-{%d-30}',maxDate:'%y-%M-%d'})"/>
                </div>
            </div></div>

            <div><label class="control-label col-label no-padding-right" for="">开单员</label>
            <div class="col-xs-3">
                <div class="clearfix">
                    <select  id="merchandiser" name="merchandiser">
                        <option value="00001">张无忌</option>
                        <option value="00002">杨过</option>
                    </select>
                </div>
            </div></div>
            <div><label class="control-label col-label no-padding-right" for="">预计发货时间</label>
            <div class="col-xs-3">
                <div class="clearfix">
                    <input class="col-xs-10 col-xs-12" name="expectedArrivedTime" id="expectedArrivedTime" type="text" placeholder="预计发货时间"
                           onClick="WdatePicker({isShowClear:true,readOnly:true,dateFmt:'yyyy-MM-dd HH:mm'})"/>
                </div>
            </div></div>
        </div>
    </div>
    <div class="col-xs-12">
        <div class="form-group">
            <div><label class="control-label col-label no-padding-right" for="">客户名称</label>
            <div class="col-xs-3">
                <div class="clearfix">
                    <input class="col-xs-10 col-xs-12" name="custName" value=""  id="custName" type="text" readonly="readonly" placeholder="客户名称"/>
                    <input class="col-xs-10 col-xs-12" name=""  id="custGroupId" type="text" style="display: none"  />
                    <input class="col-xs-10 col-xs-12" name=""  id="custId" type="text"  style="display: none"  />
                    <span style="cursor:pointer" id="custListDivBlock">
                    <button type="button" class="btn btn-minier btn-inverse no-padding-right"
                            id="">选择
                    </button></span>
                </div>
            </div></div>
            <div><label class="control-label col-label no-padding-right" for="">配送仓库</label>
            <div class="col-xs-3">
                <div class="clearfix">
                    <select  id="warehouseCode" name="warehouseCode" onclick="warehouseByCust()">
                        <option value="">无</option>
                    <#-- <option value="00002">天津仓库</option>-->
                    </select>
                </div>
            </div></div>
            <div><label class="control-label col-label no-padding-right" for="">备注</label>
            <div class="col-xs-3">
                <div class="clearfix">
                    <input class="col-xs-10 col-xs-12" name="notes" onkeyup="this.value=this.value.replace(/(^\s*)|(\s*$)/g, '')" id="notes" type="text" placeholder="备注"/>
                </div>
            </div></div>
        </div>
    </div>
    <br/>
    <br/>
    <br/>
    <div class="row">
        <div class="col-xs-12">
            <div class="form-group">
                <label class="control-label col-label no-padding-right" for=""><b>发货方</b></label>
                <div class="col-xs-3">
                </div>
                <label class="control-label col-label no-padding-right" for=""><b>出发地:</b></label>
                <div class="col-xs-3">
                    <div class="clearfix">
                        <span id="showDepaturePlace"></span>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-xs-12">
            <div class="form-group">
                <label class="control-label col-label no-padding-right" for="">名称</label>
                <div class="col-xs-3">
                    <div class="clearfix">
                        <input class="col-xs-10 col-xs-12" readonly="readonly" name="consignorName" id="consignorName" type="text"
                               placeholder="名称"/>
                        <span style="cursor:pointer" id="consignorListDivBlock">
                        <button type="button" class="btn btn-minier btn-inverse no-padding-right"
                                id="">选择
                        </button></span>
                    </div>
                </div>
                <label class="control-label col-label no-padding-right" for="">联系人</label>
                <div class="col-xs-3">
                    <div class="clearfix">
                        <input class="col-xs-10 col-xs-12"  readonly="readonly" name="consignorContactName" id="consignorContactName" type="text" placeholder="联系人"/>
                    </div>
                </div>
                <label class="control-label col-label no-padding-right" for="">联系电话</label>
                <div class="col-xs-3">
                    <div class="clearfix">
                        <input class="col-xs-10 col-xs-12" readonly="readonly" name="consignorContactPhone" id="consignorContactPhone" type="text" placeholder="联系电话"/>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-xs-12">
            <div class="form-group">
                <label class="control-label col-label no-padding-right" for="">地址</label>
                <div class="col-xs-9">
                    <div class="clearfix">
                        <input class="col-xs-10 col-xs-12"  readonly="readonly" name="consignorContactAddress" id="consignorContactAddress" type="text" placeholder="地址" />
                        <input class="col-xs-10 col-xs-12" name="consignorType" id="consignorType" type="hidden" />
                        <input class="col-xs-10 col-xs-12" name="consignorContactCompanyId" id="consignorContactCompanyId" type="hidden" />
                        <input class="col-xs-10 col-xs-12" name="consignorContactCode" id="consignorContactCode" type="hidden" />
                        <input class="col-xs-10 col-xs-12" name="consignorPhone" id="consignorPhone" type="hidden" />
                        <input class="col-xs-10 col-xs-12" name="consignorProvince" id="consignorProvince" type="hidden" />
                        <input class="col-xs-10 col-xs-12" name="consignorProvinceName" id="consignorProvinceName" type="hidden" />
                        <input class="col-xs-10 col-xs-12" name="consignorCity" id="consignorCity" type="hidden" />
                        <input class="col-xs-10 col-xs-12" name="consignorCityName" id="consignorCityName" type="hidden" />
                        <input class="col-xs-10 col-xs-12" name="consignorArea" id="consignorArea" type="hidden" />
                        <input class="col-xs-10 col-xs-12" name="consignorAreaName" id="consignorAreaName" type="hidden" />
                        <input class="col-xs-10 col-xs-12" name="consignorStreet" id="consignorStreet" type="hidden" />
                        <input class="col-xs-10 col-xs-12" name="consignorStreetName" id="consignorStreetName" type="hidden" />
                        <input class="col-xs-10 col-xs-12" name="consignorAddress" id="consignorAddress" type="hidden" />
                    </div>
                </div>

            </div>
        </div>
    </div>
<#--</form>-->
<br/>
<div class="col-sm-12">
    <div class="tabbable" style="width:1000px;">
        <ul class="nav nav-tabs" id="myTab4">
            <li class="goodsLi disable" >
                <a data-toggle="tab" href="#home4" aria-expanded="false">货品信息</a>
            </li>

            <li class="consigneeLi active">
                <a data-toggle="tab" href="#profile4" aria-expanded="true">收货方列表</a>
            </li>

        </ul>

        <div class="tab-content">
            <div id="home4" class="tab-pane ">
                <!--货品明细-->
                <span style="cursor:pointer" id="goodsListDivBlock"><button type="button" class="btn btn-info"  id="bootbox-confirm">添加货品</button></span>
                <table id="orderGoodsListTable" class="table table-striped table-bordered table-hover dataTable no-footer" role="grid"
                       aria-describedby="dynamic-table_info">
                    <thead>
                    <tr role="row" id="222">
                        <th class="center sorting_disabled" rowspan="1" colspan="1" aria-label="">
                            操作
                        </th>
                    <#--<th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Status: activate to sort column ascending">序号</th>-->
                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1"
                            aria-label="Domain: activate to sort column ascending">序号
                        </th>
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
                    <tbody id="goodsInfoListDiv">
                    </tbody>
                </table>
            </div>
            <div id="profile4" class="tab-pane active">
                <span style="cursor:pointer" id="consigneeListDivBlock"><button type="button" class="btn btn-info" style="" id="">添加收货方</button></span>
                <span style="cursor:pointer" id="consigneeListConfirmDivBlock"><button type="button" class="btn btn-info" id="">确认收货方</button></span>
                <span style="cursor:pointer" id="consigneeListClearDivBlock"><button type="button" class="btn btn-info" id="">重置收货方</button></span>
                <table id="consigneeListTable" class="table table-striped table-bordered table-hover dataTable no-footer" role="grid"
                       aria-describedby="dynamic-table_info">
                    <thead>
                    <tr role="row">
                        <th class="center sorting_disabled" rowspan="1" colspan="1" aria-label="">
                            操作
                        </th>
                    <#--<th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Status: activate to sort column ascending">序号</th>-->
                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1"
                            aria-label="Domain: activate to sort column ascending">收货方名称
                        </th>
                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1"
                            aria-label="Price: activate to sort column ascending">客户订单编号
                        </th>
                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1"
                            aria-label="Update: activate to sort column ascending">联系人
                        </th>
                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1"
                            aria-label="Clicks: activate to sort column ascending">联系电话
                        </th>
                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1"
                            aria-label="Clicks: activate to sort column ascending">地址
                        </th>
                    </thead>
                    <tbody id="consigneeInfoListDiv"></tbody>

                </table>
            </div>

        </div>
    </div>
</div>
</form>

<div class="col-xs-12">
    <button class="btn btn-white btn-info btn-bold btn-interval" id="orderPlaceConTableBtn" onclick="javascript:$('#operationDistributingFormValidate').submit();">
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
        //$("#orderTime").val(new Date().toLocaleDateString());
        validateFormData();
        $("#merchandiser").editableSelect();
        $("#store").editableSelect();

    }
    var goodsAndConsigneeMap = new HashMap();
    $(function () {


        $(".goodsLi").click(function () {
            /*var consigneeChosen = "";
            $("#consigneeInfoListDiv").find("tr").each(function () {
                consigneeChosen = "param";
            })
            if(""==consigneeChosen){
                debugger
                $(".goodsLi").off('click').on("click",function(){
                    alert("请先添加收货方")
                });
            }*/
        });

        $("#goodsListDivBlock").click(function () {
            var consigneeChosen =  $("#consigneeInfoListDiv").find("tr").size();
            if(consigneeChosen < 1){
                alert("请先添加收货方")
            }else if(!ifConsigneeConfirm){
                alert("请先确认收货方");
            }else{
                //$("#goodsSelectListTbody").html("");
                //加载货品一级种类
                //goodsTypeId  goodsSecTypeId
                var custId = $("#custId").val();
                $("#goodsTypeId option").remove();
                $("#goodsSecTypeId option").remove();
                $("#goodsTypeId").append("<option value = ''>全部</option>");//123//$("#warehouseCode").append("<option value = ''>无</option>");
                $("#goodsSecTypeId").append("<option value = ''>全部</option>");

                var firstGoodsType = null;
                CommonClient.syncpost(sys.rootPath + "/ofc/distributing/queryGoodsTypeByCustId",{"custId":custId},function(data) {
                    data=eval(data);
                    $.each(data,function (index,goodsType) {
                        console.log('-----------'+index);
                        if(0 == index){
                            firstGoodsType = goodsType.id;
                            console.log("++++"+firstGoodsType)
                        }
                        $("#goodsTypeId").append("<option value='"+goodsType.id+"'>"+goodsType.goodsTypeName+"</option>");
                    });
                });
                //加载第一个一级货品的二级种类//000
                $("#goodsSecTypeId option").remove();
                $("#goodsSecTypeId").append("<option value = ''>全部</option>");
                console.log('----------'+firstGoodsType)
                if(null != firstGoodsType){
                    console.log("sdfsdf")
                    CommonClient.syncpost(sys.rootPath + "/ofc/distributing/queryGoodsSecTypeByCAndT",{"custId":custId,"goodsType":firstGoodsType},function(data) {
                        data=eval(data);
                        $.each(data,function (index,secGoodsType) {
                            $("#goodsSecTypeId").append("<option value='"+secGoodsType.id+"'>"+secGoodsType.goodsTypeName+"</option>");
                        });
                    })
                }


                $("#goodsListDiv").fadeIn("slow");//淡入淡出效果 显示div
            }
        });
        $("#goodsListDivNoneTop").click(function(){

            $("#goodsListDiv").fadeOut("slow");//淡入淡出效果 隐藏div

        });
        $("#goodsListDivNoneBottom").click(function(){

            $("#goodsListDiv").fadeOut("slow");//淡入淡出效果 隐藏div

        });
        $("#goodsSelectFormBtn").click(function () {
            debugger
            CommonClient.post(sys.rootPath + "/ofc/distributing/queryGoodsListInDistrbuting", $("#goodsSelConditionForm").serialize(), function(data) {
                data=eval(data);

                var goodsList = "";
                $.each(data,function (index,cscGoodsVo) {

                    goodsList =goodsList + "<tr role='row' class='odd'>";
                    ///遍历已经选好的货品列表, 如果已存在就将复选框禁掉
                    /*if($("#goodsInfoListDiv").find("tr").size() < 1){
                        $("#goodsInfoListDiv").find("tr").each(function(index) {
                            var tdArr = $(this).children();
                            var goodsCodeIn = tdArr.eq(2).text();//货品编码
                            if(cscGoodsVo.goodsCode == goodsCodeIn){
                                goodsList =goodsList + "<td class='center'> "+"<label class='pos-rel'>"+"<input type='checkbox'  class='ace' disabled='disabled' >"+"<span class='lbl'></span>"+"</label>"+"</td>";
                            }else{
                                
                            }
                        })
                    }else{
                    }*/
                    goodsList =goodsList + "<td class='center'> "+"<label class='pos-rel'>"+"<input type='checkbox'  class='ace' >"+"<span class='lbl'></span>"+"</label>"+"</td>";
                    goodsList =goodsList + "<td>"+cscGoodsVo.goodsTypeParentName+"</td>";//货品种类
                    goodsList =goodsList + "<td>"+cscGoodsVo.goodsTypeName+"</td>";//货品小类
                    goodsList =goodsList + "<td>"+cscGoodsVo.brand+"</td>";//品牌
                    goodsList =goodsList + "<td>"+cscGoodsVo.goodsCode+"</td>";//货品编码
                    goodsList =goodsList + "<td>"+cscGoodsVo.goodsName+"</td>";//货品名称
                    goodsList =goodsList + "<td>"+cscGoodsVo.specification+"</td>";//规格
                    goodsList =goodsList + "<td>"+cscGoodsVo.unit+"</td>";//单位
                    goodsList =goodsList + "<td>"+cscGoodsVo.barCode+"</td>";//条形码
                    /*goodsList =goodsList + "<td style='display:none'>"+cscGoodsVo.weight+"</td>";
                    goodsList =goodsList + "<td style='display:none'>"+cscGoodsVo.volume+"</td>";*/
                    goodsList =goodsList + "</tr>";
                });
                $("#goodsSelectListTbody").html(goodsList);
                /*debugger
                if($("#goodsInfoListDiv").find("tr").size() > 1){//页面
                    var goodsCodeForCheck = {};
                    $("#goodsInfoListDiv").find("tr").each(function () {//页面
                        var tdArr = $(this).children();
                        var goodsCodeForCheckBox = tdArr.eq(2).text();//货品编码
                        goodsCodeForCheck[goodsCodeForCheckBox] = 1;
                    })
                    $("#goodsSelectListTbody").find("tr").each(function () {//弹框
                        var tdArr = $(this).children();
                        var goodsCodeForCheckBoxDiv = tdArr.eq(4).text();//货品编码
                        //var checkBox = tdArr.eq(0).children('');
                        if(undefined != goodsCodeForCheck[goodsCodeForCheckBoxDiv] && 1 == goodsCodeForCheck[goodsCodeForCheckBoxDiv]){
                            debugger
                            tdArr.eq(0).children('input').css('disabled','disabled');
                        }
                    })
                }*/
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
            var preIndex = "0";
            var goodsCodeOut = {};
            $("#goodsInfoListDiv").find("tr").each(function(index){
                var tdArr = $(this).children();
                var index = tdArr.eq(1).text();//序号
                var goodsCode = tdArr.eq(2).text();//货品编码

                debugger
                goodsCodeOut[goodsCode] = 1;

                var goodsName = tdArr.eq(3).text();//货品名称
                var specification = tdArr.eq(4).text();//    货品规格
                var unit = tdArr.eq(5).text();//    单位
                var sendGoods = tdArr.eq(6).text();//发货数量


                goodsInfoListDiv =goodsInfoListDiv + "<tr role='row' class='odd' align='center'>";
                goodsInfoListDiv =goodsInfoListDiv + "<td>" +
                        "<button type='button' onclick='deleteGood(this)' class='btn btn-minier btn-danger'>删除</button>" +
                        "<button type='button' onclick='goodsAndConsignee(this)' class='btn btn-minier btn-success'>录入</button>" +
                        "</td>";
                goodsInfoListDiv =goodsInfoListDiv + "<td>"+index+"</td>";
                goodsInfoListDiv =goodsInfoListDiv + "<td>"+goodsCode+"</td>";
                goodsInfoListDiv =goodsInfoListDiv + "<td>"+goodsName+"</td>";
                goodsInfoListDiv =goodsInfoListDiv + "<td>"+specification+"</td>";
                goodsInfoListDiv =goodsInfoListDiv + "<td>"+unit+"</td>";
                goodsInfoListDiv =goodsInfoListDiv + "<td>"+sendGoods+"</td>";
                goodsInfoListDiv =goodsInfoListDiv + "</tr>";
                preIndex = index;
            });
            $("#goodsInfoListDiv").html("");
            var str = "";
            $("#goodsSelectListTbody").find("tr").each(function(index){
                var tdArr = $(this).children();
                if(tdArr.eq(0).find("input").prop("checked")){

                    var numIndex = parseInt(preIndex);
                    preIndex = numIndex + 1;
                    var goodsCode = tdArr.eq(4).text();//货品编码
                    var goodsName = tdArr.eq(5).text();//货品名称
                    var specification = tdArr.eq(6).text();//规格
                    var unit = tdArr.eq(7).text();//单位

                    debugger;
                    if(undefined != goodsCodeOut[goodsCode] && goodsCodeOut[goodsCode] == 1){
                        return true;
                    }

                    goodsInfoListDiv =goodsInfoListDiv + "<tr role='row' class='odd' align='center' >";//class=\"btn btn-minier btn-yellow\"
                    goodsInfoListDiv =goodsInfoListDiv + "<td>" +
                            "<button type='button' onclick='deleteGood(this)' class='btn btn-minier btn-danger'>删除</button>" +
                            "<button type='button' onclick='goodsAndConsignee(this)' class='btn btn-minier btn-success'>录入</button>" +
                            "</td>";
                    /* goodsInfoListDiv =goodsInfoListDiv + "<td><input id='deleteOrNot' type='checkbox'/></td>";*/
                    goodsInfoListDiv =goodsInfoListDiv + "<td>"+preIndex+"</td>";
                    goodsInfoListDiv =goodsInfoListDiv + "<td>"+goodsCode+"</td>";
                    goodsInfoListDiv =goodsInfoListDiv + "<td>"+goodsName+"</td>";
                    goodsInfoListDiv =goodsInfoListDiv + "<td>"+specification+"</td>";
                    goodsInfoListDiv =goodsInfoListDiv + "<td>"+unit+"</td>";
                    goodsInfoListDiv =goodsInfoListDiv + "<td>0</td>";
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
            //validateForm();

        });
    })


    function warehouseByCust(){
        if(!validateCustChosen()){//如果没选客户
            alert("请先选择客户");
        }
    }

    $("#goodsTypeId").change(function () {
        debugger
        var custId = $("#custId").val();
        var goodsType = $("#goodsTypeId").val();
        //$("#goodsTypeId option").remove();
        $("#goodsSecTypeId option").remove();
        CommonClient.post(sys.rootPath + "/ofc/distributing/queryGoodsSecTypeByCAndT",{"custId":custId,"goodsType":goodsType},function(data) {
            data=eval(data);
            $.each(data,function (index,secGoodsType) {
                $("#goodsSecTypeId").append("<option value='"+secGoodsType.id+"'>"+secGoodsType.goodsTypeName+"</option>");
            });
        })
    })





    $("#goodsAndConsigneeDivNoneBottom").click(function () {
        $("#goodsAndConsigneeDiv").fadeOut("slow");
    });


    var deleteGoodsTag = false;

    function deleteConsignee(obj) {
        if(ifConsigneeConfirm){//000
            alert("您已确认,无法删除收货方")
        }else{
            $(obj).parent().parent().remove();

        }
    }
    function deleteGood(obj) {
        deleteGoodsTag = true;
        debugger
        ///$(obj).parent().parent().off('click')
        $(obj).parent().parent().remove();
        //$("#goodsAndConsigneeDiv").fadeIn("slow");
        //删除Map中对应的数据

        var goodsIndex = $(obj).parent().parent().children().eq(1).text();
        var goodsCode = $(obj).parent().parent().children().eq(2).text();
        var mapKey =  goodsCode + "@" + goodsIndex;
        if(null != goodsAndConsigneeMap.get(mapKey) || undefined != goodsAndConsigneeMap.get(mapKey)){
            goodsAndConsigneeMap.remove(mapKey);
        }
    }

    function goodsAndConsignee(obj){
        $(obj).not($(obj).find('td')[0])

        console.log("---"+obj)
        if(deleteGoodsTag){
            deleteGoodsTag = false;
            return;
        }else{
            $("#goodsAndConsigneeDiv").fadeIn("slow");
            //显示货品信息
            /**
             * var goodsIndex = $(obj).parent().parent().children().eq(1).text();
             var goodsCode = $(obj).parent().parent().children().eq(2).text();
             */
            /*var goodsIndex = $(obj).find('td')[1].innerText;//000
            var goodsCode = $(obj).find('td')[2].innerText;
            var goodsName = $(obj).find('td')[3].innerText;
            var specification = $(obj).find('td')[4].innerText;
            var unit = $(obj).find('td')[5].innerText;*/
            var goodsIndex = $(obj).parent().parent().children().eq(1).text();//000
            var goodsCode = $(obj).parent().parent().children().eq(2).text();
            var goodsName = $(obj).parent().parent().children().eq(3).text();
            var specification = $(obj).parent().parent().children().eq(4).text();
            var unit = $(obj).parent().parent().children().eq(5).text();
            $("#goodsCodeDiv").val(goodsCode);
            $("#goodsNameDiv").val(goodsName);
            $("#specificationDiv").val(specification);
            $("#unitDiv").val(unit);


            //最后提交订单的时候做个校验, 如果货品的需求数量为0就提示!
            //显示收货人信息
            //goodsAndConsigneeTbody
            var consignorout = "";
            $("#consigneeInfoListDiv").find("tr").each(function(index){//00000
                var tdArr = $(this).children();
                var consigneeName = tdArr.eq(1).text();//
                var consigneeContactName = tdArr.eq(3).text();//
                var consigneeType = tdArr.eq(6).text();//
                var consigneeCode = tdArr.eq(7).text();//
                var mapKey = goodsCode + "@" + goodsIndex;
                var num = "0";

                if(undefined != goodsAndConsigneeMap.get(mapKey)){
                    var preGoodsAndConsigneeJsonMsg = goodsAndConsigneeMap.get(mapKey)[1];
                    //preGoodsAndConsigneeJsonMsg = JSON.stringify(preGoodsAndConsigneeJsonMsg);
                    num = preGoodsAndConsigneeJsonMsg[consigneeCode];
                }

                consignorout =consignorout + "<tr role='row' class='odd' align='center'>";
                if("1" == consigneeType){
                    consignorout =consignorout + "<td>"+consigneeName+"</td>";
                }else if("2" == consigneeType){
                    consignorout =consignorout + "<td>"+consigneeName+"-"+consigneeContactName+"</td>";
                }else{
                    consignorout =consignorout + "<td>"+consigneeName+"</td>";
                }
               // consignorout =consignorout + "<td>"+consigneeName+"</td>";
                consignorout =consignorout + "<td><input value='"+num+"' onpause='return false' onkeypress='onlyNumber(this)' onkeyup='onlyNumber(this)'/></td>";
                consignorout =consignorout + "<td style='display:none'>"+consigneeCode+"</td>";
                consignorout =consignorout + "</tr>";
            });
            $("#goodsAndConsigneeTbody").html(consignorout);
        }

    }//

    //统计货品发货数量
    $("#goodsAndConsigneeEnter").click(function(){
        var sendNum = 0;//统计某个货品的总的发货数量

        var mapValue = [];
        var mapKey = "";
        var goodsJson = {};
        var consigneeAndGoodsJson = {};

        $("#goodsAndConsigneeTbody").find("tr").each(function (index) {
            debugger
            var tdArr = $(this).children();
            var num = tdArr.eq(1).children().val();//某个收货方该货品的需求量
            var consigneeCode = tdArr.eq(2).text();//某个收货方的编码
            if(StringUtil.isEmpty(num)){
                num = 0;
            }
            consigneeAndGoodsJson[consigneeCode] = num;
            sendNum += parseInt(num);
        })
        var goodsInfoListDiv = "";
        $("#goodsInfoListDiv").find("tr").each(function(index) {
            var tdArr = $(this).children();
            var goodsCode = tdArr.eq(2).text();//货品编码
            var goodsCodeDiv = $("#goodsCodeDiv").val();
            if(goodsCode == goodsCodeDiv){
                tdArr.eq(6).text(sendNum);
                var indexIn = tdArr.eq(1).text();
                var goodsCodeIn = tdArr.eq(2).text();
                var goodsNameIn = tdArr.eq(3).text();
                var goodsSpecIn = tdArr.eq(4).text();
                var goodsUnitIn = tdArr.eq(5).text();
                var goodsAmountIn = tdArr.eq(6).text();
                goodsJson.indexIn = indexIn;
                goodsJson.goodsCodeIn = goodsCodeIn;
                goodsJson.goodsNameIn = goodsNameIn;
                goodsJson.goodsSpecIn = goodsSpecIn;
                goodsJson.goodsUnitIn = goodsUnitIn;
                goodsJson.goodsAmountIn = goodsAmountIn;
                mapKey = goodsCodeIn + "@" + indexIn;
            }
        });
        //000
        /*
        往Map里放的数据结构
        key(货品编码@货品行号用来表示唯一一行):  value(json数组):[{"货品编码":xxx,"货品名称":xxx,...},{"收货人1code":20,"收货人2code":30}]
        */
        mapValue[0] = goodsJson;
        mapValue[1] = consigneeAndGoodsJson;
        goodsAndConsigneeMap.put(mapKey,mapValue);

        console.log("mapKey:"+mapKey);
        console.log("mapValue:" + JSON.stringify(mapValue));

        console.log(JSON.stringify(goodsAndConsigneeMap.get(mapKey)[0]))
        console.log(JSON.stringify(goodsAndConsigneeMap.get(mapKey)[1]))

        $("#goodsAndConsigneeDiv").fadeOut("slow");
    })



    //校验是否选了客户
    function validateCustChosen() {
        var custChosen = $("#custName").val();
        if(""==custChosen){
            return false;
        }else{
            return true;
        }
    }

    $("#consignorListDivBlock").click(function(){

        if(!validateCustChosen()){
            alert("请先选择客户")
        }else{
            var cscContantAndCompanyDto = {};
            var cscContact = {};
            var cscContactCompany = {};
            cscContactCompany.contactCompanyName = "";
            cscContact.purpose = "2";
            cscContact.contactName = "";
            cscContact.phone = "";
            cscContantAndCompanyDto.cscContact = cscContact;
            cscContantAndCompanyDto.cscContactCompany = cscContactCompany;
            var groupId = $("#custGroupId").val();
            var custId = $("#custId").val();
            var param = JSON.stringify(cscContantAndCompanyDto);
            var contactList = 0;

            var contactCompanyNameAuto = null;
            var contactNameAuto = null;
            var phoneAuto = null;
            var detailAddressAuto = null;
            var typeAuto = null;
            var contactCompanyIdAuto = null;
            var contactCodeAuto = null;
            var phoneAuto = null;
            var provinceAuto = null;
            var provinceNameAuto = null;
            var cityAuto = null;
            var cityNameAuto = null;
            var areaAuto = null;
            var areaNameAuto = null;
            var streetAuto = null;
            var streetNameAuto = null;
            var addressAuto = null;
            debugger;
            CommonClient.syncpost(sys.rootPath + "/ofc/contactSelect",{"cscContantAndCompanyDto":param,"groupId":groupId,"custId":custId}, function(data) {
                data=eval(data);
                $.each(data,function (index,CscContantAndCompanyDto) {
                    contactList += 1;
                    if(contactList == 1){
                        contactCompanyNameAuto = CscContantAndCompanyDto.contactCompanyName;
                        contactNameAuto = CscContantAndCompanyDto.contactName;
                        phoneAuto = CscContantAndCompanyDto.phone;
                        detailAddressAuto = CscContantAndCompanyDto.detailAddress;
                        typeAuto = CscContantAndCompanyDto.type;
                        contactCompanyIdAuto = CscContantAndCompanyDto.contactCompanyId;
                        contactCodeAuto = CscContantAndCompanyDto.contactCode;
                        phoneAuto = CscContantAndCompanyDto.phone;
                        provinceAuto = CscContantAndCompanyDto.province;
                        provinceNameAuto = CscContantAndCompanyDto.provinceName;
                        cityAuto = CscContantAndCompanyDto.city;
                        cityNameAuto = CscContantAndCompanyDto.cityName;
                        areaAuto = CscContantAndCompanyDto.area;
                        areaNameAuto = CscContantAndCompanyDto.areaName;
                        streetAuto = CscContantAndCompanyDto.street;
                        streetNameAuto = CscContantAndCompanyDto.streetName;
                        addressAuto = CscContantAndCompanyDto.address;
                    }
                    if(contactList > 1){
                        return true;
                    }
                });
            },"json");
            if(contactList == 1){
                //只有一个联系人
                layer.confirm('您只有一个收货方联系人,点击确定我们将为您自动加载!', {
                    skin : 'layui-layer-molv',
                    icon : 3,
                    title : '确认操作'
                }, function(index){
                    //自动加载
                    $("#consignorName").val(contactCompanyNameAuto);
                    $("#consignorContactName").val(contactNameAuto);
                    $("#consignorContactPhone").val(phoneAuto);
                    $("#consignorContactAddress").val(detailAddressAuto);
                    $("#consignorType").val(typeAuto);
                    $("#consignorContactCompanyId").val(contactCompanyIdAuto);
                    $("#consignorContactCode").val(contactCodeAuto);
                    $("#consignorPhone").val(phoneAuto);
                    $("#consignorProvince").val(provinceAuto);
                    $("#consignorProvinceName").val(provinceNameAuto);
                    $("#consignorCity").val(cityAuto);
                    $("#consignorCityName").val(cityNameAuto);
                    $("#consignorArea").val(areaAuto);
                    $("#consignorAreaName").val(areaNameAuto);
                    $("#consignorStreet").val(streetAuto);
                    $("#consignorStreetName").val(streetNameAuto);
                    $("#consignorAddress").val(addressAuto);

                    $("#showDepaturePlace").html(provinceNameAuto+cityNameAuto+areaNameAuto+streetNameAuto);


                    layer.close(index);
                }, function(index){
                    layer.close(index);
                });
            }else if(contactList == 0){
                alert("您还未添加任何联系人,请在收发货档案中进行添加操作！");
                return;
            }else {
                $("#consignorListDiv").fadeIn("slow");//淡入淡出效果 显示div
            }

        }


    });

    $("#consignorListDivNoneTop").click(function(){

        $("#consignorListDiv").fadeOut("slow");//淡入淡出效果 隐藏div

    });

    $("#consignorListDivNoneBottom").click(function(){

        $("#consignorListDiv").fadeOut("slow");//淡入淡出效果 隐藏div

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
                contactList =contactList + "<td style='display:none'>"+CscContantAndCompanyDto.type+"</td>";
                contactList =contactList + "<td style='display:none'>"+CscContantAndCompanyDto.contactCompanyId+"</td>";
                contactList =contactList + "<td style='display:none'>"+CscContantAndCompanyDto.contactCode+"</td>";
                contactList =contactList + "<td style='display:none'>"+CscContantAndCompanyDto.phone+"</td>";
                contactList =contactList + "<td style='display:none'>"+CscContantAndCompanyDto.province+"</td>";
                contactList =contactList + "<td style='display:none'>"+CscContantAndCompanyDto.provinceName+"</td>";
                contactList =contactList + "<td style='display:none'>"+CscContantAndCompanyDto.city+"</td>";
                contactList =contactList + "<td style='display:none'>"+CscContantAndCompanyDto.cityName+"</td>";
                contactList =contactList + "<td style='display:none'>"+CscContantAndCompanyDto.area+"</td>";
                contactList =contactList + "<td style='display:none'>"+CscContantAndCompanyDto.areaName+"</td>";
                contactList =contactList + "<td style='display:none'>"+CscContantAndCompanyDto.street+"</td>";
                contactList =contactList + "<td style='display:none'>"+CscContantAndCompanyDto.streetName+"</td>";
                contactList =contactList + "<td style='display:none'>"+CscContantAndCompanyDto.address+"</td>";
                contactList =contactList + "</tr>";
            });
            $("#contactSelectListTbody2").html(contactList);
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
                var detailaddress = tdArr.eq(5).text();//    地址
                var type = tdArr.eq(6).text();
                var contactCompanyId = tdArr.eq(7).text();
                var contactCode = tdArr.eq(8).text();
                var phone = tdArr.eq(9).text();
                var province = tdArr.eq(10).text();
                var provinceName = tdArr.eq(11).text();
                var city = tdArr.eq(12).text();
                var cityName = tdArr.eq(13).text();
                var area = tdArr.eq(14).text();
                var areaName = tdArr.eq(15).text();
                var street = tdArr.eq(16).text();
                var streetName = tdArr.eq(17).text();
                var address = tdArr.eq(18).text();

                $("#consignorName").val(consignorName);
                $("#consignorContactName").val(contacts);
                $("#consignorContactPhone").val(contactsNumber);
                $("#consignorContactAddress").val(detailaddress);
                $("#consignorType").val(type);
                $("#consignorContactCompanyId").val(contactCompanyId);
                $("#consignorContactCode").val(contactCode);
                $("#consignorPhone").val(phone);
                $("#consignorProvince").val(province);
                $("#consignorProvinceName").val(provinceName);
                $("#consignorCity").val(city);
                $("#consignorCityName").val(cityName);
                $("#consignorArea").val(area);
                $("#consignorAreaName").val(areaName);
                $("#consignorStreet").val(street);
                $("#consignorStreetName").val(streetName);
                $("#consignorAddress").val(address);

                $("#showDepaturePlace").html(provinceName+cityName+areaName+streetName);
            }
        });
        if(consignorin==""){
            alert("请至少选择一行");
        }else{
            $("#consignorListDiv").fadeOut("slow");
        }
    });

    $("#consigneeListDivBlock").click(function(){

        if(!validateCustChosen()){
            alert("请先选择客户")
        } else if(ifConsigneeConfirm){
            alert("您已确认过一次,无法再次添加新客户!");
            return;
        }else{
            //$("#contactSelectListTbody1").html("");
            $("#consigneeListDiv").fadeIn("slow");//淡入淡出效果 显示div
        }
    });


    $("#consigneeListDivNoneTop").click(function(){

        $("#consigneeListDiv").fadeOut("slow");//淡入淡出效果 隐藏div

    });

    $("#consigneeListDivNoneBottom").click(function(){

        $("#consigneeListDiv").fadeOut("slow");//淡入淡出效果 隐藏div

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
                contactList =contactList + "<td class='center'> "+"<label class='pos-rel'>"+"<input name='consigneeSel' type='checkbox' class='ace'>"+"<span class='lbl'></span>"+"</label>"+"</td>";
                contactList =contactList + "<td>"+(index+1)+"</td>";
                contactList =contactList + "<td>"+CscContantAndCompanyDto.contactCompanyName+"</td>";
                contactList =contactList + "<td>"+CscContantAndCompanyDto.contactName+"</td>";
                contactList =contactList + "<td>"+CscContantAndCompanyDto.phone+"</td>";
                contactList =contactList + "<td>"+CscContantAndCompanyDto.detailAddress+"</td>";
                contactList =contactList + "<td style='display:none'>"+CscContantAndCompanyDto.type+"</td>";
                contactList =contactList + "<td style='display:none'>"+CscContantAndCompanyDto.contactCompanyId+"</td>";
                contactList =contactList + "<td style='display:none'>"+CscContantAndCompanyDto.contactCode+"</td>";
                contactList =contactList + "<td style='display:none'>"+CscContantAndCompanyDto.phone+"</td>";
                contactList =contactList + "<td style='display:none'>"+CscContantAndCompanyDto.province+"</td>";
                contactList =contactList + "<td style='display:none'>"+CscContantAndCompanyDto.provinceName+"</td>";
                contactList =contactList + "<td style='display:none'>"+CscContantAndCompanyDto.city+"</td>";
                contactList =contactList + "<td style='display:none'>"+CscContantAndCompanyDto.cityName+"</td>";
                contactList =contactList + "<td style='display:none'>"+CscContantAndCompanyDto.area+"</td>";
                contactList =contactList + "<td style='display:none'>"+CscContantAndCompanyDto.areaName+"</td>";
                contactList =contactList + "<td style='display:none'>"+CscContantAndCompanyDto.street+"</td>";
                contactList =contactList + "<td style='display:none'>"+CscContantAndCompanyDto.streetName+"</td>";
                contactList =contactList + "<td style='display:none'>"+CscContantAndCompanyDto.address+"</td>";
                contactList =contactList + "</tr>";
                $("#contactSelectListTbody1").html(contactList);
            });
        },"json");
    });
    $("#consigneecheck").change(function () {
        if($("#consigneecheck").prop("checked")){
            $("#contactSelectListTbody1").find("tr").each(function(index){
                $(this).children().eq(0).find("input").prop('checked',true);
            });
        }else{
            $("#contactSelectListTbody1").find("tr").each(function(index){
                $(this).children().eq(0).find("input").prop('checked',false);
            });
        }
    });
    $("#contactoutEnter").click(function () {
        var consignorout = "";

        var contactCodeOut = {};

        $("#consigneeInfoListDiv").find("tr").each(function(index){
            var tdArr = $(this).children();
            var consigneeName = tdArr.eq(1).text();//
            var consigneeCustOrderCode =  tdArr.eq(2).children().val();
            var consigneeContactName = tdArr.eq(3).text();//
            var consigneeContactPhone = tdArr.eq(4).text();//
            var consigneeContactAddress = tdArr.eq(5).text();//
            var type = tdArr.eq(6).text();
            var contactCompanyId = tdArr.eq(7).text();
            var contactCode = tdArr.eq(8).text();
            var phone = tdArr.eq(9).text();
            var province = tdArr.eq(10).text();
            var provinceName = tdArr.eq(11).text();
            var city = tdArr.eq(12).text();
            var cityName = tdArr.eq(13).text();
            var area = tdArr.eq(14).text();
            var areaName = tdArr.eq(15).text();
            var street = tdArr.eq(16).text();
            var streetName = tdArr.eq(17).text();
            var address = tdArr.eq(18).text();

            contactCodeOut[contactCompanyId] = 1;


            consignorout =consignorout + "<tr role='row' class='odd' align='center'>";
            consignorout =consignorout + "<td><button type='button' onclick='deleteConsignee(this)' class='btn btn-minier btn-danger'>删除</button></td>";
            consignorout =consignorout + "<td>"+consigneeName+"</td>";
            consignorout =consignorout + "<td><input value='" + consigneeCustOrderCode + "' /></td>";
            consignorout =consignorout + "<td>"+consigneeContactName+"</td>";
            consignorout =consignorout + "<td>"+consigneeContactPhone+"</td>";
            consignorout =consignorout + "<td>"+consigneeContactAddress+"</td>";
            consignorout =consignorout + "<td style='display: none'>" + type + "</td>";
            consignorout =consignorout + "<td style='display: none'>" + contactCompanyId + "</td>";
            consignorout =consignorout + "<td style='display: none'>" + contactCode + "</td>";
            consignorout =consignorout + "<td style='display: none'>" + phone + "</td>";
            consignorout =consignorout + "<td style='display: none'>" + province + "</td>";
            consignorout =consignorout + "<td style='display: none'>" + provinceName + "</td>";
            consignorout =consignorout + "<td style='display: none'>" + city + "</td>";
            consignorout =consignorout + "<td style='display: none'>" + cityName + "</td>";
            consignorout =consignorout + "<td style='display: none'>" + area + "</td>";
            consignorout =consignorout + "<td style='display: none'>" + areaName + "</td>";
            consignorout =consignorout + "<td style='display: none'>" + street + "</td>";
            consignorout =consignorout + "<td style='display: none'>" + streetName + "</td>";
            consignorout =consignorout + "<td style='display: none'>" + address + "</td>";
            consignorout =consignorout + "</tr>";
        });

        $("#consigneeInfoListDiv").html("");

        $("#contactSelectListTbody1").find("tr").each(function(index){
            
            var tdArr = $(this).children();
            if(tdArr.eq(0).find("input").prop("checked")){

                var consigneeName = tdArr.eq(2).text();//名称
                var consigneeContactName = tdArr.eq(3).text();//联系人
                var consigneeContactPhone = tdArr.eq(4).text();//    联系电话
                var consigneeContactAddress = tdArr.eq(5).text();//    地址
                var type = tdArr.eq(6).text();
                var contactCompanyId = tdArr.eq(7).text();
                var contactCode = tdArr.eq(8).text();
                var phone = tdArr.eq(9).text();
                var province = tdArr.eq(10).text();
                var provinceName = tdArr.eq(11).text();
                var city = tdArr.eq(12).text();
                var cityName = tdArr.eq(13).text();
                var area = tdArr.eq(14).text();
                var areaName = tdArr.eq(15).text();
                var street = tdArr.eq(16).text();
                var streetName = tdArr.eq(17).text();
                var address = tdArr.eq(18).text();

                if(undefined != contactCodeOut[contactCompanyId] && contactCodeOut[contactCompanyId] == 1){
                    return true;
                }
                consignorout =consignorout + "<tr role='row' class='odd' align='center'>";
                consignorout =consignorout + "<td><button type='button'  onclick='deleteConsignee(this)' class='btn btn-minier btn-danger'>删除</button></td>";
                consignorout =consignorout + "<td>"+consigneeName+"</td>";
                consignorout =consignorout + "<td><input /></td>";
                consignorout =consignorout + "<td>"+consigneeContactName+"</td>";
                consignorout =consignorout + "<td>"+consigneeContactPhone+"</td>";
                consignorout =consignorout + "<td>"+consigneeContactAddress+"</td>";
                consignorout =consignorout + "<td style='display: none'>" + type + "</td>";
                consignorout =consignorout + "<td style='display: none'>" + contactCompanyId + "</td>";
                consignorout =consignorout + "<td style='display: none'>" + contactCode + "</td>";
                consignorout =consignorout + "<td style='display: none'>" + phone + "</td>";
                consignorout =consignorout + "<td style='display: none'>" + province + "</td>";
                consignorout =consignorout + "<td style='display: none'>" + provinceName + "</td>";
                consignorout =consignorout + "<td style='display: none'>" + city + "</td>";
                consignorout =consignorout + "<td style='display: none'>" + cityName + "</td>";
                consignorout =consignorout + "<td style='display: none'>" + area + "</td>";
                consignorout =consignorout + "<td style='display: none'>" + areaName + "</td>";
                consignorout =consignorout + "<td style='display: none'>" + street + "</td>";
                consignorout =consignorout + "<td style='display: none'>" + streetName + "</td>";
                consignorout =consignorout + "<td style='display: none'>" + address + "</td>";

                consignorout =consignorout + "</tr>";
            }
        });
        if(consignorout==""){
            alert("请至少选择一行");
        }else{
            $("#consigneeInfoListDiv").html(consignorout);
            $("#consigneeListDiv").fadeOut("slow");//淡入淡出效果 隐藏div
        }
    });//custListDiv
    $("#custListDivBlock").click(function () {
        $("#custListDiv").fadeIn("slow");//淡入淡出效果 显示div
    });
    $("#custListDivNoneBottom").click(function () {
        $("#custListDiv").fadeOut("slow");//淡入淡出效果 隐藏div
    });
    $("#custListDivNoneTop").click(function () {
        $("#custListDiv").fadeOut("slow");//淡入淡出效果 隐藏div
    });


    //确定收货方列表
    var ifConsigneeConfirm = false;
    $("#consigneeListConfirmDivBlock").click(function () {
        var consignorout = $("#consigneeInfoListDiv").find("tr").size();
        if(consignorout < 1){
            alert("请先添加收货方");
            return;
        }
        if(ifConsigneeConfirm){
            alert("您已确认过一次,无法再次确认!");
            return;
        }
        //校验收货方列表中是否所有的客户订单编号都填写了
        //统计收货方列表数据传给后台
        //然后将添加收货人按钮禁掉,提示用户暂时不能在添加了货品后选择收货方, 让客户订单编号的输入框变为只读的
        layer.confirm('您即将确认收货方列表,您如果添加货品将无法再添加收货方!', {
            skin : 'layui-layer-molv',
            icon : 3,
            title : '确认操作'
        }, function(index){
            $("#consigneeInfoListDiv").find("tr").each(function(index) {
                var tdArr = $(this).children();
                tdArr.eq(2).children().attr("readonly","readonly");
                ifConsigneeConfirm = true;
                //禁用添加收货人和确认收货人
                debugger;
            })
            layer.close(index);
        }, function(index){
            layer.close(index);
        });
    })
    
    $("#consigneeListClearDivBlock").click(function () {
        //alert('3233')
        debugger
        var consignorout = $("#consigneeInfoListDiv").find("tr").size();
        if(consignorout > 0){
            layer.confirm('您即将清空收货方列表,您之前输入的货品信息将被清空!', {
                skin : 'layui-layer-molv',
                icon : 3,
                title : '确认操作'
            }, function(index){
                $("#consigneeInfoListDiv").html("");
                ifConsigneeConfirm = false;
                goodsAndConsigneeMap = new HashMap();
                $("#goodsInfoListDiv").html("");
                layer.close(index);
            }, function(index){
                layer.close(index);
            });
        }

    })

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
                custList =custList + "<td class='center'> "+"<label class='pos-rel'>"+"<input name='custList' type='radio' class='ace'>"+"<span class='lbl'></span>"+"</label>"+"</td>";
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
                $("#customerId").val(custId);
            }
        });
        if(custEnterTag==""){
            alert("请至少选择一行");
        }else{
            $("#custListDiv").fadeOut("slow");//淡入淡出效果 隐藏div
        }

        //加载完客户后自动加载仓库列表, 和货品种类
        //加载仓库列表
        var custId = $("#custId").val();
        $("#warehouseCode option").remove();
        //<option value="">无</option>
       /* $("#warehouseCode").append("<option value="">无</option>");*/
       $("#warehouseCode").append("<option value = ''>无</option>");
        CommonClient.post(sys.rootPath + "/ofc/distributing/queryWarehouseByCustId",{"custId":custId},function(data) {
            data=eval(data);
            $.each(data,function (index,warehouse) {
                $("#warehouseCode").append("<option value='"+warehouse.id+"'>"+warehouse.warehouseName+"</option>");
            });
        })



    });

    $("#to_operation_distributing_excel").click(function () {
        var historyUrl = "operation_distributing";
        var url = "/ofc/operationDistributingExcel" + "/" + historyUrl;
        xescm.common.loadPage(url);
    })




    function distributingOrderPlaceCon() {

        if(!validateForm()){
            return;
        }
        //如果没有仓库信息就是城配运输订单
        //如果有仓库信息就是仓配订单, 销售出库,
        var orderLists = [];
        //堆齐基本信息
        var orderInfo = null;

        //遍历收货方列表
        $("#consigneeInfoListDiv").find("tr").each(function (index) {


            orderInfo = {};

            var storeMessage = $("#store").val();
            if("" == storeMessage){
                orderInfo.orderType = "60";//运输
                orderInfo.businessType = "600";//城配
                orderInfo.provideTransport = "0";//不需要运输
            }else{
                orderInfo.orderType = "61";//仓储
                orderInfo.businessType = "610";//销售出库
                orderInfo.provideTransport = "1";//需要运输
            }
            orderInfo.orderTime = $dp.$('orderTime').value + " 00:00:00";//000
            orderInfo.merchandiser = $("#merchandiser").val();
            orderInfo.expectedArrivedTime = $dp.$('expectedArrivedTime').value;
            orderInfo.custName = $("#custName").val();//后端需特别处理
            orderInfo.custCode = $("#custId").val();//后端需特别处理
            orderInfo.warehouseCode = $("#warehouseCode").val();
            orderInfo.warehouseName = $("#warehouseCode option:selected").text();
            orderInfo.notes = $("#notes").val();
            //发货方信息
            orderInfo.consignorName = $("#consignorName").val();
            orderInfo.consignorContactName = $("#consignorContactName").val();
            orderInfo.consignorContactPhone = $("#consignorContactPhone").val();
            orderInfo.departurePlace = $("#consignorAddress").val();//出发地门牌号
            orderInfo.consignorType = $("#consignorType").val();
            orderInfo.consignorCode = $("#consignorContactCompanyId").val();
            orderInfo.consignorContactCode = $("#consignorContactCode").val();
            orderInfo.consignorContactPhone = $("#consignorPhone").val();
            var provinceCode = $("#consignorProvince").val();
            orderInfo.departureProvince = $("#consignorProvinceName").val();
            var cityCode = $("#consignorCity").val();
            orderInfo.departureCity = $("#consignorCityName").val();
            var areaCode = $("#consignorArea").val();
            orderInfo.departureDistrict = $("#consignorAreaName").val();
            var streetCode = $("#consignorStreet").val();
            orderInfo.departureTowns = $("#consignorStreetName").val();
            orderInfo.departurePlaceCode = provinceCode + "," + cityCode + "," + areaCode + "," + streetCode;
                 //orderInfo.departurePlace = $("#consignorAddress").val();

            var tdArr = $(this).children();
            var consigneeName = tdArr.eq(1).text();//名称
            var custOrderCode = tdArr.eq(2).children().val();
            var consigneeContactName = tdArr.eq(3).text();//联系人
            var consigneeContactPhone = tdArr.eq(4).text();//    联系电话
            var consigneeContactAddress = tdArr.eq(5).text();//    完整地址
            var type = tdArr.eq(6).text();
            var contactCompanyId = tdArr.eq(7).text();
            var contactCode = tdArr.eq(8).text();
            var phone = tdArr.eq(9).text();
            var province = tdArr.eq(10).text();
            var provinceName = tdArr.eq(11).text();
            var city = tdArr.eq(12).text();
            var cityName = tdArr.eq(13).text();
            var area = tdArr.eq(14).text();
            var areaName = tdArr.eq(15).text();
            var street = tdArr.eq(16).text();
            var streetName = tdArr.eq(17).text();
            var address = tdArr.eq(18).text();
            orderInfo.consigneeName = consigneeName;
            orderInfo.custOrderCode = custOrderCode;
            orderInfo.consigneeType = type;
            orderInfo.consigneeContactName = consigneeContactName;//000
            orderInfo.consigneeCode = contactCompanyId;
            orderInfo.consigneeContactCode = contactCode;
            orderInfo.consigneeContactPhone = consigneeContactPhone;
            orderInfo.destination= address;
            orderInfo.destinationProvince = provinceName;
            orderInfo.destinationCity = cityName;
            orderInfo.destinationDistrict = areaName;
            orderInfo.destinationTowns = streetName;
            orderInfo.destinationCode = province + "," + city + "," + area + "," + street;

            var goodsList = [];
            var goods = null;
            //遍历货品信息

            $("#goodsInfoListDiv").find("tr").each(function(index) {

                goods = {};
                var tdArr = $(this).children();
                var goodsIndex = tdArr.eq(1).text();//货品序号
                var goodsCode = tdArr.eq(2).text();//货品编码
                var goodsName = tdArr.eq(3).text();//货品名称
                var goodsSpec = tdArr.eq(4).text();//规格
                var goodsUnit = tdArr.eq(5).text();//单位
                //var goodsTotalAmount = tdArr.eq(6).text();//总数量
                goods.goodsCode = goodsCode;
                goods.goodsName = goodsName;
                goods.goodsSpec = goodsSpec;
                goods.unit = goodsUnit;

                var mapKey = goodsCode + "@" + goodsIndex;
                var goodsMsgStr =  goodsAndConsigneeMap.get(mapKey)[0];//货品信息
                var consigneeAndGoodsMsgJson = goodsAndConsigneeMap.get(mapKey)[1];//联系人和货品的对应信息
                var goodsAmount = consigneeAndGoodsMsgJson[contactCompanyId];
                goods.quantity = goodsAmount;
                goodsList[index] = goods;
            })
            orderInfo.goodsList  = goodsList;
            orderLists[index] = orderInfo;
        })

        var param = JSON.stringify(orderLists);
        if(StringUtil.isEmpty(param)){
            return;
        }
        xescm.common.submit("/ofc/distributing/placeOrdersListCon"
                ,{"orderLists":param}
                ,"您即将进行批量下单，自动对本批订单审核订单，请确认订单准确无误！是否继续下单？"
                ,function () {
                })
    }

    function validateForm() {//000
        //校验是否有某个收货人下所有货品的数量都是0//能不能直接从HashMap下手//000
        //校验货品某一行的需求数量不是//000
        //校验必须有货品
        //校验必须有收货方
        //还有页面所有的校验都还没有做
        //客户订单编号的校验也不好做
        //添加批次号

        var consigneeNum = 0;//收货方数量一级校验标记
        var goodsNumTag = true;//货品明细数量一级校验标记
        var consigneeGoodsIsEmptyOut = true;//收房方所有货品的数量是否都是0一级校验标记
        var goodsIsEmptyOut = false;//货品列表中货品最后一列的货品数量一级校验标记
        $("#consigneeInfoListDiv").find("tr").each(function (index) {
            consigneeNum += 1;
            var goods = null;
            var tdArr = $(this).children();
            var consigneeName = tdArr.eq(1).text();//名称
            var consigneeContactName = tdArr.eq(3).text();//联系人
            var consigneeType = tdArr.eq(6).text();
            var contactCompanyId = tdArr.eq(7).text();
            debugger
            //遍历货品信息
            var consigneeGoodsIsEmpty = true;//收房方所有货品的数量校验标记
            var goodsIsEmpty = false;//货品列表中货品最后一列的货品数量校验标记
            var goodsNum = 0;//货品列表数量
            $("#goodsInfoListDiv").find("tr").each(function(index) {
                goodsNum += 1;
                debugger
                goods = {};
                var tdArr = $(this).children();
                var goodsIndex = tdArr.eq(1).text();//货品序号
                var goodsCode = tdArr.eq(2).text();//货品编码
                var goodsName = tdArr.eq(3).text();//货品名称
                var goodsTotalAmount = tdArr.eq(6).text();//总数量
                if(goodsTotalAmount == 0){
                    alert("货品列表中【"+goodsName+"】的数量为空,请检查!");
                    //return false;
                    goodsIsEmpty = true;
                    return false;
                }
                var mapKey = goodsCode + "@" + goodsIndex;
                var consigneeAndGoodsMsgJson = null;
                if(null != goodsAndConsigneeMap.get(mapKey) || undefined == goodsAndConsigneeMap.get(mapKey)){
                    consigneeAndGoodsMsgJson = goodsAndConsigneeMap.get(mapKey)[1];//联系人和货品的对应信息
                }
                var goodsAmount = 0;
                if(null != consigneeAndGoodsMsgJson){
                    goodsAmount = consigneeAndGoodsMsgJson[contactCompanyId];
                }

                if(goodsAmount != 0){

                    consigneeGoodsIsEmpty = false;
                    //return false;
                }
            })
            if(0 == goodsNum){
                goodsNumTag = false;
                alert("请至少添加一个货品明细!");
                return false;
            }
            if(goodsIsEmpty){
                goodsIsEmptyOut = true;
                return false;
            }
            if(consigneeGoodsIsEmpty){
                if("1" == consigneeType) {
                    alert("收货方【"+consigneeName+"】未有发货数量,请检查!");
                }else if("2" == consigneeType){
                    alert("收货方【"+consigneeName+"-"+consigneeContactName+"】未有发货数量,请检查!");
                }else{
                    alert("收货方【"+consigneeName+"】未有发货数量,请检查!");
                }

                consigneeGoodsIsEmptyOut = false;
                return false;
            }
        })
        if(0 == consigneeNum){//如果收货人数量是0
            alert("请至少添加一个货品明细以及对应的收货方！");
            return false;
        }
        if(!goodsNumTag){//如果货品数量是0
            return false;
        }
        if(!consigneeGoodsIsEmptyOut){//收货方所有货品的数量都是0
            return false;
        }
        if(goodsIsEmptyOut){
            return false;
        }
        return true;
    }


    function HashMap() {
        /** Map 大小 **/
        var size = 0;
        /** 对象 **/
        var entry = new Object();

        /** 存 **/
        this.put = function (key , value)
        {
            if(!this.containsKey(key))
            {
                size ++ ;
            }
            entry[key] = value;
        }

        /** 取 **/
        this.get = function (key)
        {
            if( this.containsKey(key) )
            {
                return entry[key];
            }
            else
            {
                return null;
            }
        }

        /** 删除 **/
        this.remove = function ( key )
        {
            if( delete entry[key] )
            {
                size --;
            }
        }

        /** 是否包含 Key **/
        this.containsKey = function ( key )
        {
            return (key in entry);
        }

        /** 是否包含 Value **/
        this.containsValue = function ( value )
        {
            for(var prop in entry)
            {
                if(entry[prop] == value)
                {
                    return true;
                }
            }
            return false;
        }

        /** 所有 Value **/
        this.values = function ()
        {
            var values = new Array(size);
            for(var prop in entry)
            {
                values.push(entry[prop]);
            }
            return values;
        }

        /** 所有 Key **/
        this.keys = function ()
        {
            var keys = new Array(size);
            for(var prop in entry)
            {
                keys.push(prop);
            }
            return keys;
        }

        /** Map Size **/
        this.size = function ()
        {
            return size;
        }
    }





    /**
     *表单验证
     */

    function validateFormData() {
        var ofc_url = $("#ofc_url").html();
        $('#operationDistributingFormValidate').validate({
            errorElement : 'div',
            errorClass : 'help-block',
            focusInvalid : false,
            ignore : "",
            rules : {
                orderTime:{
                    required:true
                },
                merchandiser:{
                    required:true
                },
                custName: {
                    required:true,
                    maxlength:100
                },
                /*warehouseCode : {
                    required:true
                },*/
                notes:{
                    maxlength:300
                },
                consignorName:{
                    required:true
                }
            },
            messages : {
                orderTime:{
                    required:"请输入订单日期"
                },
                merchandiser:{
                    required:"请选择开单员"
                },
                custName: {
                    required:"请选择客户",
                    maxlength:"超过最大长度"
                },
               /* warehouseCode : {
                    required:"请选择仓库"
                },*/
                notes:{
                    maxlength:"超过最大长度"
                },
                consignorName:{
                    required:"请选择发货方"
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
                //error.insertAfter(element.parent());
                error.insertAfter(element.parent());
            },
            submitHandler : function(form) {
                /*xescm.common.commit('addSupplierContactForm', '/csc/supplier/addCscSupplierContact', function(){
                    var supplierId = $("#supplierId").val();
                    var url = "/csc/supplier/toMaintainCscSupplierContactListPage/" + supplierId;
                    xescm.common.loadPage(url);
                });*/
                distributingOrderPlaceCon();
            },
            invalidHandler : function(form) {
            }
        });

    }

    function onlyNumber(obj){

        //得到第一个字符是否为负号
        // var t = obj.value.charAt(0);
        //先把非数字的都替换掉，除了数字和.
        obj.value = obj.value.replace(/[^\d\.]/g,'');
        //obj.value = obj.value.replace(/[^\d{1,6}]/,'');
        //必须保证第一个为数字而不是.
        obj.value = obj.value.replace(/^\./g,'');
        //保证只有出现一个.而没有多个.
        obj.value = obj.value.replace(/\.{2,}/g,'.');
        //保证.只出现一次，而不能出现两次以上
        obj.value = obj.value.replace('.','$#$').replace(/\./g,'').replace('$#$','.');
        //限制连续数字不能超过6个
        if(/\d{7}/.test(obj.value)){
            obj.value = obj.value.replace(/\d$/gi,'');
        }
        //限制小数点后3位
        if(/\.\d{4}/.test(obj.value)){
            obj.value = obj.value.replace(/\d$/gi,'');
        }

    }
</script>

<script type="text/javascript" src="../js/jquery.editable-select.min.js"></script>
</body>