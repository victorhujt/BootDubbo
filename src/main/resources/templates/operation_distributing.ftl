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
                            <input  id = "consignorName2" name="cscContactCompany.contactCompanyName" type="text" style="color: black" class="form-control input-sm" placeholder="" aria-controls="dynamic-table">
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
                    <tbody id=""></tbody>
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
                            <input  id = "goodsNameDiv" name="" type="text" readonly="readonly" style="color: black" class="form-control input-sm" placeholder="" aria-controls="dynamic-table">
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-sm-1 no-padding-right" for="name">规格</label>
                    <div class="col-sm-3">
                        <div class="clearfix">
                            <input  id = "specificationDiv" name="" type="text" readonly="readonly" style="color: black" class="form-control input-sm" placeholder="" aria-controls="dynamic-table">
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
                        <tr>
                            <td>五道口1</td>
                            <td>20</td>
                        </tr>
                        <tr>
                            <td>六道口</td>
                            <td>30</td>
                        </tr>

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

    <button class="btn btn-white btn-info btn-bold btn-interval" id="">
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
<form id="" method="post" class="form-horizontal" role="form">
    <div class="col-xs-12">
        <div class="form-group">
            <label class="control-label col-label no-padding-right" for="">订单日期</label>
            <div class="col-xs-3">
                <div class="clearfix">
                    <input class="col-xs-10 col-xs-12" name="" id="" type="text" placeholder="订单日期"
                           onClick="WdatePicker({isShowClear:true,readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})"/>
                </div>
            </div>
            <label class="control-label col-label no-padding-right" for="">开单员</label>
            <div class="col-xs-3">
                <div class="clearfix">
                    <select  id="merchandiser" name="merchandiser">
                        <option value="00001">张无忌</option>
                        <option value="00002">杨过</option>
                    </select>
                </div>
            </div>
            <label class="control-label col-label no-padding-right" for="">预发货时间</label>
            <div class="col-xs-3">
                <div class="clearfix">
                    <input class="col-xs-10 col-xs-12" name="" id="" type="text" placeholder="预计发货时间"
                           onClick="WdatePicker({isShowClear:true,readOnly:true,dateFmt:'yyyy-MM-dd HH:mm'})"/>
                </div>
            </div>
        </div>
    </div>
    <div class="col-xs-12">
        <div class="form-group">
            <label class="control-label col-label no-padding-right" for="">客户名称</label>
            <div class="col-xs-3">
                <div class="clearfix">
                    <input class="col-xs-10 col-xs-12" name=""  id="custName" type="text" readonly="readonly" placeholder="客户名称"/>
                    <span style="cursor:pointer" id="custListDivBlock">
                    <button type="button" class="btn btn-minier btn-inverse no-padding-right"
                            id="">选择
                    </button></span>
                </div>
            </div>
            <label class="control-label col-label no-padding-right" for="">配送仓库</label>
            <div class="col-xs-3">
                <div class="clearfix">
                    <select  id="store" name="store">
                        <option value="00001">北京仓库</option>
                        <option value="00002">天津仓库</option>
                    </select>
                </div>
            </div>
            <label class="control-label col-label no-padding-right" for="">备注</label>
            <div class="col-xs-3">
                <div class="clearfix">
                    <input class="col-xs-10 col-xs-12" name="" id="" type="text" placeholder="备注"/>
                </div>
            </div>
        </div>
    </div>
    <br/>
    <br/>
    <br/>
    <div class="row">
        <div class="col-xs-12">
            <div class="form-group">
                <label class="control-label col-label no-padding-right" for=""><b>发货方</b></label>
                <div class="col-xs-6">
                </div>
                <label class="control-label col-label no-padding-right" for=""><b>出发地:</b></label>
                <div class="col-xs-3">
                    <div class="clearfix">
                        <span id="departurePlace"></span>
                    </div>
                </div>

            </div>
        </div>
        <div class="col-xs-12">
            <div class="form-group">
                <label class="control-label col-label no-padding-right" for="">名称</label>
                <div class="col-xs-3">
                    <div class="clearfix">
                        <input class="col-xs-10 col-xs-12" name="consignorName" id="consignorName" type="text"
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
                        <input class="col-xs-10 col-xs-12" name="consignorContactName" id="consignorContactName" type="text" placeholder="联系人"/>
                    </div>
                </div>
                <label class="control-label col-label no-padding-right" for="">联系电话</label>
                <div class="col-xs-3">
                    <div class="clearfix">
                        <input class="col-xs-10 col-xs-12" name="consignorContactPhone" id="consignorContactPhone" type="text" placeholder="联系电话"/>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-xs-12">
            <div class="form-group">
                <label class="control-label col-label no-padding-right" for="">地址</label>
                <div class="col-xs-9">
                    <div class="clearfix">
                        <input class="col-xs-10 col-xs-12" name="consignorContactAddress" id="consignorContactAddress" type="text" placeholder="地址" />
                    </div>
                </div>

            </div>
        </div>
    </div>
</form>
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

            <div id="profile4" class="tab-pane active">
                <span style="cursor:pointer" id="consigneeListDivBlock"><button type="button" class="btn btn-info" id="consigneeselbtn">添加收货方</button></span>
                <table id="orderGoodsListTable" class="table table-striped table-bordered table-hover dataTable no-footer" role="grid"
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
        $("#merchandiser").editableSelect();
        $("#store").editableSelect();
    }
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
        })

        $("#goodsListDivBlock").click(function () {
            var consigneeChosen = "";
            $("#consigneeInfoListDiv").find("tr").each(function () {
                consigneeChosen = "param";
            })
            if(""==consigneeChosen){
                alert("请先添加收货方")
            }else{
                $("#goodsListDiv").fadeIn("slow");//淡入淡出效果 显示div
            }
        })
        $("#goodsListDivNoneTop").click(function(){

            $("#goodsListDiv").fadeOut("slow");//淡入淡出效果 隐藏div

        });
        $("#goodsListDivNoneBottom").click(function(){

            $("#goodsListDiv").fadeOut("slow");//淡入淡出效果 隐藏div

        });
        $("#goodsSelectFormBtn").click(function () {
            CommonClient.post(sys.rootPath + "/ofc/goodsSelect", $("#goodsSelConditionForm").serialize(), function(data) {
                data=eval(data);

                var goodsList = "";
                $.each(data,function (index,cscGoodsVo) {
                    goodsList =goodsList + "<tr role='row' class='odd'>";
                    goodsList =goodsList + "<td class='center'> "+"<label class='pos-rel'>"+"<input type='checkbox' class='ace'>"+"<span class='lbl'></span>"+"</label>"+"</td>";
                    goodsList =goodsList + "<td>"+cscGoodsVo.goodsTypeName+"</td>";//货品种类
                    goodsList =goodsList + "<td>"+cscGoodsVo.goodsTypeName+"</td>";//货品小类
                    goodsList =goodsList + "<td>"+cscGoodsVo.goodsTypeName+"</td>";//品牌
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
                var sendGoods = tdArr.eq(5).text();//发货数量


                goodsInfoListDiv =goodsInfoListDiv + "<tr role='row' class='odd' align='center'>";
                goodsInfoListDiv =goodsInfoListDiv + "<td><button type='button' onclick='deleteGood(this)' class='btn btn-minier btn-danger'>删除</button></td>";
                goodsInfoListDiv =goodsInfoListDiv + "<td>"+goodsCode+"</td>";
                goodsInfoListDiv =goodsInfoListDiv + "<td>"+goodsName+"</td>";
                goodsInfoListDiv =goodsInfoListDiv + "<td>"+specification+"</td>";
                goodsInfoListDiv =goodsInfoListDiv + "<td>"+unit+"</td>";
                goodsInfoListDiv =goodsInfoListDiv + "<td>"+sendGoods+"</td>";
                goodsInfoListDiv =goodsInfoListDiv + "</tr>";
            });
            $("#goodsInfoListDiv").html("");
            var str = "";
            $("#goodsSelectListTbody").find("tr").each(function(index){
                var tdArr = $(this).children();
                if(tdArr.eq(0).find("input").prop("checked")){
                    var goodsCode = tdArr.eq(4).text();//货品编码
                    var goodsName = tdArr.eq(5).text();//货品名称
                    var specification = tdArr.eq(6).text();//规格
                    var unit = tdArr.eq(7).text();//单位
                    goodsInfoListDiv =goodsInfoListDiv + "<tr role='row' class='odd' align='center' onclick='goodsAndConsignee(this)'>";//class=\"btn btn-minier btn-yellow\"
                    goodsInfoListDiv =goodsInfoListDiv + "<td>" +
                            "<button type='button' onclick='deleteGood(this)' class='btn btn-minier btn-danger'>删除</button>" +
                            "</td>";
                    /* goodsInfoListDiv =goodsInfoListDiv + "<td><input id='deleteOrNot' type='checkbox'/></td>";*/
                    goodsInfoListDiv =goodsInfoListDiv + "<td>"+goodsCode+"</td>";
                    goodsInfoListDiv =goodsInfoListDiv + "<td>"+goodsName+"</td>";
                    goodsInfoListDiv =goodsInfoListDiv + "<td>"+specification+"</td>";
                    goodsInfoListDiv =goodsInfoListDiv + "<td>"+unit+"</td>";
                    goodsInfoListDiv =goodsInfoListDiv + "<td>20</td>";
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
    function deleteGood(obj) {
        $(obj).parent().parent().remove();
    }

    $("#goodsAndConsigneeDivNoneBottom").click(function () {
        $("#goodsAndConsigneeDiv").fadeOut("slow");
    });
    function goodsAndConsignee(obj){
        $("#goodsAndConsigneeDiv").fadeIn("slow");
        console.log($(obj).html())
        console.log($(obj).find('td')[1].innerText)

        var goodsCode = $(obj).find('td')[1].innerText;
        var goodsName = $(obj).find('td')[2].innerText;
        var specification = $(obj).find('td')[3].innerText;
        var unit = $(obj).find('td')[4].innerText;
        $("#goodsCodeDiv").val(goodsCode);
        $("#goodsNameDiv").val(goodsName);
        $("#specificationDiv").val(specification);
        $("#unitDiv").val(unit);
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

    $("#contactinEnter").click(function () {

        var consignorin = "";
        $("#contactSelectListTbody2").find("tr").each(function(index){
            var tdArr = $(this).children();
            if(tdArr.eq(0).find("input").prop("checked")){
                consignorin = "1";
                var consignorName = tdArr.eq(2).text();//名称
                var contacts = tdArr.eq(3).text();//联系人
                var contactsNumber = tdArr.eq(4).text();//    联系电话
                var address = tdArr.eq(5).text();//    地址

                $("#consignorName").val(consignorName);
                $("#consignorContactName").val(contacts);
                $("#consignorContactPhone").val(contactsNumber);
                $("#consignorContactAddress").val(address);

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
        $("#consigneeInfoListDiv").find("tr").each(function(index){
            var tdArr = $(this).children();
            var consigneeName = tdArr.eq(1).text();//
            var consigneeContactName = tdArr.eq(3).text();//
            var consigneeContactPhone = tdArr.eq(4).text();//
            var consigneeContactAddress = tdArr.eq(5).text();//
            consignorout =consignorout + "<tr role='row' class='odd' align='center'>";
            consignorout =consignorout + "<td><button type='button' onclick='deleteGood(this)' class='btn btn-minier btn-danger'>删除</button></td>";
            consignorout =consignorout + "<td>"+consigneeName+"</td>";
            consignorout =consignorout + "<td><input />" +
                    "<button type='button' class='btn btn-minier btn-inverse no-padding-right'  id=''>保存</button>" +
                    "</td>";
            consignorout =consignorout + "<td>"+consigneeContactName+"</td>";
            consignorout =consignorout + "<td>"+consigneeContactPhone+"</td>";
            consignorout =consignorout + "<td>"+consigneeContactAddress+"</td>";
            consignorout =consignorout + "</tr>";
        });
        $("#consigneeInfoListDiv").html("");

        $("#contactSelectListTbody1").find("tr").each(function(index){
            debugger
            var tdArr = $(this).children();
            if(tdArr.eq(0).find("input").prop("checked")){

                var consigneeName = tdArr.eq(2).text();//名称
                var consigneeContactName = tdArr.eq(3).text();//联系人
                var consigneeContactPhone = tdArr.eq(4).text();//    联系电话
                var consigneeContactAddress = tdArr.eq(5).text();//    地址
                consignorout =consignorout + "<tr role='row' class='odd' align='center'>";
                consignorout =consignorout + "<td><button type='button' onclick='deleteGood(this)' class='btn btn-minier btn-danger'>删除</button></td>";
                consignorout =consignorout + "<td>"+consigneeName+"</td>";
                consignorout =consignorout + "<td><input />" +
                        "<button type='button' class='btn btn-minier btn-inverse no-padding-right'  id=''>保存</button>" +
                        "</td>";
                consignorout =consignorout + "<td>"+consigneeContactName+"</td>";
                consignorout =consignorout + "<td>"+consigneeContactPhone+"</td>";
                consignorout =consignorout + "<td>"+consigneeContactAddress+"</td>";
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


</script>
<script type="text/javascript" src="../js/jquery.editable-select.min.js"></script>
</body>