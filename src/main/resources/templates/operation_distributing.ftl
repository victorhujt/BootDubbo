<title>城配下单</title>
<#include "dialog/customer.ftl" >
<style type="text/css">
  /*  #goodsListDiv,#consignorListDiv,#consigneeListDiv,#custListDiv,#goodsAndConsigneeDiv {
        position:fixed;
        left:50%;
        top:85px;
        margin-left:-400px;
        width:946px;
        height:500px;
        z-index:3;
        overflow: auto;
        border:solid #7A7A7A 1px;
    }*/
  .help-block{
    color:#f00 !important;
  }
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
  .width-267{
    width:267px;
    padding:0 12px;
    float:left;
  }
  .dataTable > thead > tr > th[class*=sorting_],.dataTable > thead > tr > th[class*=sort]:hover{
    color:#707070;
  }
  .tp-1{
    margin-left: 10px;
  }
  .bk-1{
    width: 245px;
  }
  .dz-1{
    width: 376px;
  }
  .l-bj{
    margin-left: 0;!important;
  }
  .db-1{
    margin-top: 10px;
  }
  .l-cor{
    color: #666;
  }
  .bg-1{
    margin-top: 5px;
  }
  .xz-1{
    margin-left: -33px;
    background-color: #F5F5F5 !important;
    border:none;
    margin-left: -20px;
    margin-top: 7px;
  }
  .xz-1:hover{
    background: #F5F5F5;
    background-color: #F5F5F5 !important;
  }
  /*  .tktp-1{
        width: 168px;
    }*/
  .qrshf{
    margin: 0 10px;
  }
  .l_dbx{
    padding-bottom: 11px;
    border-bottom:1px solid #ccc;
    box-sizing: border-box;
  }
  .l-dw{
    position: absolute;
    left: 278px;
    top: 16px;
  }
  .disabled:hover{
    background-color: #fff !important;
  }
  #goodsSecTypeId_chosen{
    width:245px!important;
  }
  #goodsTypeId_chosen{
    width:245px!important;
  }
  .adoptModal  .table>tbody>tr>td, .adoptModal  .table>tbody>tr>th, .adoptModal  .table>tfoot>tr>td, .adoptModal  .table>tfoot>tr>th, .adoptModal  .table>thead>tr>td, .adoptModal  .table>thead>tr>th{
    padding:6px 8px !important;
    line-height:19px!important;
  }
</style>
<link rel="stylesheet" type="text/css" href="../css/jquery.editable-select.min.css" />
<!--goodsListDiv-->
<div class="adoptModal " id="goodsListDiv" style="display: none;">
<#--  <div class="modal-header"><span id="goodsListDivNoneTop" style="cursor:pointer"><button type="button" id="" style="cursor:pointer" class="bootbox-close-button close" data-dismiss="modal" aria-hidden="true">×</button></span>
    <p class="modal-title">货品列表</p></div>-->
  <div class="modal-body">
    <div class="bootbox-body">
      <form id="goodsSelConditionForm" class="form-horizontal" role="form">
        <div class="form-group">
          <label class="control-label  col-label no-padding-right" for="name">货品种类</label>
          <div class="col-xs-3 tktp-1">
            <div class="clearfix">
              <select id="goodsTypeId" name="goodsTypeId" class="bk-1 chosen-select"></select>
            </div>
          </div>
          <label class="control-label  col-label no-padding-right" for="name">货品小类</label>
          <div class="col-xs-3 tktp-1">
            <div class="clearfix">
              <select id="goodsSecTypeId" name="goodsTypeSonId" class="bk-1 chosen-select"></select>
            </div>
          </div>
        </div>
        <div class="form-group">
          <label class="control-label  col-label no-padding-right" for="name">货品名称</label>
          <div class="col-xs-3">
            <div class="clearfix">
              <input  id = "goodsName" name="goodsName" type="text" style="color: black" onpaste="return false" onkeydown="this.value=this.value.replace(/(^\s*)|(\s*$)/g, '')"   onkeyup="this.value=this.value.replace(/(^\s*)|(\s*$)/g, '')" class="form-control input-sm bk-1" placeholder="" aria-controls="dynamic-table">
            </div>
          </div>
          <label class="control-label  col-label no-padding-right" for="name">条形码</label>
          <div class="col-xs-3">
            <div class="clearfix">
              <input  id = "barCode" name="barCode" type="text" style="color: black" onpaste="return false" onkeydown="this.value=this.value.replace(/(^\s*)|(\s*$)/g, '')"  onkeyup="this.value=this.value.replace(/(^\s*)|(\s*$)/g, '')" class="form-control input-sm  bk-1" placeholder="" aria-controls="dynamic-table">
              <input id="customerCodeForGoods" name ="customerCode" type="hidden"/>
            </div>
          </div>
          <div class="col-xs-1">
            <div class="clearfix">
              <span id="goodsSelectFormBtn" class="btn btn-info btn-sm popover-info" style="margin-left:30px;">筛选</span>
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
        <div class="row">
          <div id="pageBarDivGoodsDistri" style="float: right;padding-top: 0px;margin-top: 10px;">
          </div>
        </div>

      </form>
    </div>
  </div>

</div>
<!--consignorListDiv-->
<div class="adoptModal " id="consignorListDiv" style="display: none;">
  <#--<div class="modal-header"><span id="consignorListDivNoneTop" style="cursor:pointer"><button type="button" id="" style="cursor:pointer" class="bootbox-close-button close" data-dismiss="modal" aria-hidden="true">×</button></span>
    <h4 class="modal-title w-font">发货方联系人</h4></div>-->
  <div class="modal-body">
    <div class="bootbox-body">
      <form id="consignorSelConditionForm" class="form-horizontal" role="form">
      <#--<input id="purpose2" name="cscContactDto.purpose" type="hidden" value="2">-->
        <div class="form-group">
          <label class="control-label  col-xs-1 no-padding-right" style="width:7%;line-height:34px;" for="name">名称</label>
          <div class="col-xs-2">
            <div class="clearfix" >
              <input  id = "consignorName2" name="cscContactCompanyDto.contactCompanyName" onpaste="return false" onkeydown="this.value=this.value.replace(/(^\s*)|(\s*$)/g, '')"  onkeyup="this.value=this.value.replace(/(^\s*)|(\s*$)/g, '')" type="text" style="color: black" class="form-control input-sm" placeholder="" aria-controls="dynamic-table">
            </div>
          </div>
          <label class="control-label  col-xs-1 no-padding-right" style="width:7%;line-height:34px;" for="name">联系人</label>
          <div class="col-xs-2">
            <div class="clearfix">
              <input  id = "consignorPerson2" name="cscContactDto.contactName" onpaste="return false" onkeydown="this.value=this.value.replace(/(^\s*)|(\s*$)/g, '')"  onkeyup="this.value=this.value.replace(/(^\s*)|(\s*$)/g, '')"  type="text" style="color: black" class="form-control input-sm" placeholder="" aria-controls="dynamic-table">
            </div>
          </div>
          <label class="control-label  col-xs-1 no-padding-right" style="width:7%;line-height:34px;" for="name">联系电话</label>
          <div class="col-xs-2">
            <div class="clearfix">
              <input  id = "consignorPhoneNumber2" name="cscContactDto.phone" onpaste="return false" onkeydown="this.value=this.value.replace(/(^\s*)|(\s*$)/g, '')" onkeyup="this.value=this.value.replace(/(^\s*)|(\s*$)/g, '')" type="text" style="color: black" class="form-control input-sm" placeholder="" aria-controls="dynamic-table">
            </div>
          </div>
          <div class="col-xs-1">
            <div class="clearfix">
              <span id="consignorSelectFormBtn" class="btn btn-info btn-sm popover-info">筛选</span>
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
            <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Domain: activate to sort column ascending">序号</th>
            <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Price: activate to sort column ascending">名称</th>
            <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">联系人</th>
            <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">联系电话</th>
            <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">地址</th>

          </thead>
          <tbody id="contactSelectListTbody2"></tbody>
        </table>

        <div class="row">
          <div id="pageBarDivConsignorDistri" style="float: right;padding-top: 0px;margin-top: 10px;">
          </div>
        </div>
      </form>
    </div>
  </div>

</div>
<!--consigneeListDiv-->
<div class="adoptModal " id="consigneeListDiv" style="display: none;">
  <#--<div class="modal-header"><span id="consigneeListDivNoneTop" style="cursor:pointer"><button type="button" id="" style="cursor:pointer" class="bootbox-close-button close" data-dismiss="modal" aria-hidden="true">×</button></span>
    <h4 class="modal-title w-font">收货方联系人</h4></div>-->
  <div class="modal-body">
    <div class="bootbox-body">
      <form id="consigneeSelConditionForm" class="form-horizontal" role="form">
      <#--<input id="purpose2" name="purpose" type="hidden" value="1">-->
        <div class="form-group">
          <label class="control-label col-xs-1 no-padding-right" style="width:7%;line-height:34px;" for="name">名称</label>
          <div class="col-xs-2">
            <div class="clearfix">
              <input  id = "consignorName1" name="contactCompanyName" onpaste="return false" onkeydown="this.value=this.value.replace(/(^\s*)|(\s*$)/g, '')"  onkeyup="this.value=this.value.replace(/(^\s*)|(\s*$)/g, '')"  type="text" style="color: black" class="form-control input-sm" placeholder="" aria-controls="dynamic-table">
            </div>
          </div>
          <label class="control-label col-xs-1 no-padding-right" style="width:7%;line-height:34px;" for="name">联系人</label>
          <div class="col-xs-2">
            <div class="clearfix">
              <input  id = "consignorPerson1" name="contactName" onpaste="return false" onkeydown="this.value=this.value.replace(/(^\s*)|(\s*$)/g, '')" onkeyup="this.value=this.value.replace(/(^\s*)|(\s*$)/g, '')" type="text" style="color: black" class="form-control input-sm" placeholder="" aria-controls="dynamic-table">
            </div>
          </div>
          <label class="control-label col-xs-1 no-padding-right" style="width:7%;line-height:34px;" for="name">联系电话</label>
          <div class="col-xs-2">
            <div class="clearfix">
              <input  id = "consignorPhoneNumber1" name="phone" onpaste="return false" onkeydown="this.value=this.value.replace(/(^\s*)|(\s*$)/g, '')"  onkeyup="this.value=this.value.replace(/(^\s*)|(\s*$)/g, '')" type="text" style="color: black" class="form-control input-sm" placeholder="" aria-controls="dynamic-table">
            </div>
          </div>
          <div class="col-xs-1">
            <div class="clearfix">
              <span id="consigneeSelectFormBtn" class="btn btn-info btn-sm popover-info">筛选</span>
            </div>
          </div>
        </div>
       <#-- <div class="form-group">
          <label class="control-label col-xs-1 no-padding-right" style="width:7%;line-height:34px;" for="name"></label>

          <div class="modal-footer">
            <button id="contactoutEnter" data-bb-handler="confirm" type="button" class="btn btn-primary">选中</button>
            <span id="consigneeListDivNoneBottom" style="cursor:pointer"><button  data-bb-handler="cancel" type="button" class="btn btn-default">关闭</button></span>
          </div>
        </div>-->
      </form>
      <form class="bootbox-form">
        <table id="dynamic-table" class="table table-striped table-bordered table-hover dataTable no-footer" role="grid" aria-describedby="dynamic-table_info">
          <thead>
          <tr role="row">
            <th class="center sorting_disabled" rowspan="1" colspan="1" aria-label=""  style="width:42px;">
              <label class="pos-rel">
                <input id="consigneecheck" type="checkbox" class="ace">
                <span class="lbl"></span>
              </label>
            </th>
            <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Domain: activate to sort column ascending" style="width:42px;">序号</th>
            <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Price: activate to sort column ascending">名称</th>
            <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">联系人</th>
            <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">联系电话</th>
            <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">地址</th>
          </thead>
          <tbody id="contactSelectListTbody1"></tbody>
        </table>

        <div class="row">
          <div id="pageBarDivConsigneeDistri" style="float: right;padding-top: 0px;margin-top: 10px;">
          </div>
        </div>
      </form>
    </div>
  </div>

</div>
<!--goods&Consigee-->
<div class="adoptModal " id="goodsAndConsigneeDiv" style="display: none;">
  <div class="modal-body">
    <div class="bootbox-body">
      <form class="form-horizontal" role="form">

        <div class="form-group">
          <label class="control-label col-label no-padding-right" for="name">货品编码</label>
          <div class="col-xs-3">
            <div class="clearfix">
              <input id="goodsIndexDivHidden" type="hidden"/>
              <input  id = "goodsCodeDiv" name="" type="text" readonly="readonly" style="color: black" class="form-control input-sm" placeholder="" aria-controls="dynamic-table">
              <input id="goodsUnitPriceDiv" type="text" hidden/>
            </div>
          </div>
          <label class="control-label col-label no-padding-right" for="name">货品名称</label>
          <div class="col-xs-3">
            <div class="clearfix">
              <input  id = "goodsNameDiv" name="" type="text" onpaste="return false" onkeydown="this.value=this.value.replace(/(^\s*)|(\s*$)/g, '')"  onkeyup="this.value=this.value.replace(/(^\s*)|(\s*$)/g, '')" readonly="readonly" style="color: black" class="form-control input-sm" placeholder="" aria-controls="dynamic-table">
            </div>
          </div>
        </div>

        <div class="form-group">
          <label class="control-label col-label no-padding-right" for="name">规格</label>
          <div class="col-xs-3">
            <div class="clearfix">
              <input  id = "specificationDiv" name="" type="text" onpaste="return false" onkeydown="this.value=this.value.replace(/(^\s*)|(\s*$)/g, '')"   onkeyup="this.value=this.value.replace(/(^\s*)|(\s*$)/g, '')"  readonly="readonly" style="color: black" class="form-control input-sm" placeholder="" aria-controls="dynamic-table">
            </div>
          </div>
          <label class="control-label col-label no-padding-right" for="name">单位</label>
          <div class="col-xs-3">
            <div class="clearfix">
              <input  id = "unitDiv" name="" type="text" readonly="readonly" style="color: black" class="form-control input-sm" placeholder="" aria-controls="dynamic-table">
            </div>
          </div>
        </div>
        <div class="form-groupp" style="overflow:hidden;">
          <table id="dynamic-table" style="float: left;width: 30%;margin-left:100px;" class="table table-striped table-bordered table-hover dataTable no-footer" role="grid" aria-describedby="dynamic-table_info">
            <thead>
            <tr role="row">
              <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Domain: activate to sort column ascending">收货方名称</th>
              <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Price: activate to sort column ascending">发货数量</th>
            </tr>
            </thead>
            <tbody id="goodsAndConsigneeTbody">
            </tbody>
          </table>
        </div>

      </form>
    </div>
  </div>

</div>

<br/>
<div class="col-xs-9">
  <button id="historyOrderSelect" class="btn btn-white btn-info btn-bold btn-interval tp-1 disabled" disabled style="border-color:#999;color:#666 !important;cursor:default">
    <i class="ace-icon fa fa-floppy-o bigger-120 blue" style="color:#666 !important"></i>
    历史订单选择
  </button>

  <button class="btn btn-white btn-info btn-bold btn-interval tp-1" id="toOperationDistributingExcel">
    <i class="ace-icon fa fa-floppy-o bigger-120 blue"></i>
    <span hidden="true" id = "excelImportTag">${(excelImportTag)!}</span>
    <span hidden="true" id = "customerCodeFromExcelImport">${(custIdFromExcelImport)!}</span>
    <span hidden="true" id = "custNameFromExcelImport">${(custNameFromExcelImport)!}</span>
    Excel导入
  </button>

  <button class="btn btn-white btn-info btn-bold btn-interval tp-1" id="to_operation_csc_contact_manage">
    <i class="ace-icon fa fa-floppy-o bigger-120 blue"></i>
    收发货方档案
    <span hidden="true" id = "csc_url">${(CSC_URL)!}</span>
  </button>
</div>
<br/>
<br/>
<br/>
<form id="operationDistributingFormValidate" method="post" class="form-horizontal" role="form">
  <div class="col-xs-12">
    <div class="form-group l-bj">
      <div><label class="control-label col-label no-padding-right l-bj" for=""><span class="w-label-icon">*</span>订单日期</label>
        <div class="width-267">
          <div class="position-relative bk-1 ">
            <input class="col-xs-10 col-label2 bk-1" name="orderTime" id="orderTime" value="${(currentTime?string("yyyy-MM-dd"))!""}" type="text" placeholder="订单日期" aria-controls="dynamic-table" readonly class="laydate-icon" value="" onclick="laydate({istime: true, format: 'YYYY-MM-DD',isclear: false,istoday: true,min: laydate.now(-30),max: laydate.now()})">
            <label class="btn btn-minier no-padding-right initBtn" id="" for="orderTime">
              <i class="fa fa-calendar l-cor bigger-130"></i>
            </label>
          </div>
        </div></div>


      <div><label class="control-label col-label no-padding-right l-bj" for=""><span class="w-label-icon">*</span>开单员</label>
        <div class="width-267">
          <div class="clearfix">
            <select class="col-xs-10 col-xs-12 bk-1" name="merchandiser" onpaste="return false" onkeydown="this.value=this.value.replace(/(^\s*)|(\s*$)/g, '')"  onkeyup="this.value=this.value.replace(/(^\s*)|(\s*$)/g, '')"  id="merchandiser" type="text" placeholder="">
            <#list merchandiserList! as merchandiser>
              <option>${(merchandiser.merchandiser)!""}</option>
            </#list>
            </select>
          </div>
        </div></div>
      <div><label class="control-label col-label no-padding-right l-bj" for="">预计发货时间</label>
        <div class="width-267">
          <div class="bk-1 position-relative">
            <input class="col-xs-10 col-xs-12 bk-1 " name="expectedArrivedTime" id="expectedArrivedTime" value="" type="text" placeholder="" aria-controls="dynamic-table" readonly class="laydate-icon" value="" onclick="laydate({istime: true, format: 'YYYY-MM-DD hh:mm:ss',isclear: true,istoday: true,festival: true,start: laydate.now(0, 'YYYY/MM/DD hh:ss:00')})">
            <label class="btn btn-minier no-padding-right initBtn" id="" for="expectedArrivedTime">
              <i class="fa fa-calendar l-cor bigger-130"></i>
            </label>
          </div>
        </div></div>
    </div>
  </div>
  <div class="col-xs-12">
    <div class="form-group">
      <div> <label class="control-label col-label no-padding-right l-bj" for=""><span class="w-label-icon">*</span>客户名称</label>
        <div class="width-267">
          <div class="bk-1 position-relative">
            <input class="bk-1" name="custName" value=""  id="custName" type="text" readonly="readonly" placeholder=""/>
            <input class="bk-1" name=""  id="customerCode" type="text"  style="display: none"  />
            <button type="button" class="btn btn-minier no-padding-right initBtn" id="custListDivBlock">
              <i class="fa fa-user l-cor bigger-130"></i>
            </button>
          </div>
        <#--   <span style="cursor:pointer" id="custListDivBlock xz-1">
               <button type="button" class="btn btn-minier btn-inverse no-padding-right xz-1"
                       id="custListDivBlock"><i class="fa fa-user l-cor"></i>
               </button></span>-->

        </div></div>
      <div> <label class="control-label col-label no-padding-right l-bj" for=""><span class="w-label-icon">*</span>配送仓库</label>
        <div class="width-267">
          <div class="clearfix" style="width:245px;">
            <select  id="warehouseCode" name="warehouseCode" onclick="warehouseByCust()" class="chosen-select">
              <option value="">无</option>

            </select>
          </div>
        </div></div>
      <div><label class="control-label col-label no-padding-right l-bj" for="">备注</label>
        <div class="width-267">
          <div class="clearfix">
            <input class="col-xs-10 col-xs-12 bk-1" name="notes" onpaste="return false" onkeydown="this.value=this.value.replace(/(^\s*)|(\s*$)/g, '')"  onkeyup="this.value=this.value.replace(/(^\s*)|(\s*$)/g, '')" id="notes" type="text" placeholder=""/>
          </div>
        </div></div>
    </div>
  </div>
  <br/>
  <br/>
  <br/>
  <div class="row" style="margin-right: -10px">
    <div class="col-xs-12">
      <div class="form-group"style="border-top:1px solid #ccc;">
        <label class="control-label col-label no-padding-right l-bj" for="" style="margin-right:0;margin-top:1px;"><b class="l-bj" style="border-bottom:1px solid #ccc;padding-bottom: 5px;">发货方</b></label>
        <div class="width-267" style="border-bottom: 1px solid #ccc;height: 32px">
        </div>
        <label class="control-label col-label no-padding-right l-bj" for="" style="margin-right:0;margin-top:1px;"><b class="l-bj" style="border-bottom:1px solid #ccc;padding-bottom: 5px;">出发地:</b></label>
        <div class="width-267" style="border-bottom: 1px solid #ccc;height: 32px;line-height:32px;">
          <div class="clearfix">
            <span id="showDepaturePlace" class="l-bj"></span>
          </div>
        </div>
      </div>
    </div>
    <div class="col-xs-12">
      <div class="form-group" style="margin-left: 0">
        <label class="control-label col-label no-padding-right l-bj" for="" style="margin-right:0">名称</label>
        <div class="width-267">
          <div class="bk-1 position-relative" style="height:34px;">
            <input class="col-xs-10 col-xs-12 bk-1" readonly="readonly" name="consignorName" id="consignorName" type="text"
                   placeholder=""/>
          <#-- <span style="cursor:pointer" id="consignorListDivBlock">
           <button type="button" class="btn btn-minier btn-inverse no-padding-right y-float"
                   id=""><i class="fa fa-user l-cor"></i>
           </button></span>-->
            <button type="button" class="btn btn-minier no-padding-right y-float initBtn" id="consignorListDivBlock" >
              <i class="fa fa-user l-cor bigger-130"></i>
            </button>
          </div>

        </div>
        <label class="control-label col-label no-padding-right l-bj" for="" style="margin-right:0">联系人</label>
        <div class="width-267">
          <div class="clearfix">
            <input style="margin-left: -2px" class="col-xs-10 col-xs-12 bk-1"  readonly="readonly" name="consignorContactName" id="consignorContactName" type="text" placeholder=""/>
          </div>
        </div>
        <label class="control-label col-label no-padding-right l-bj" for="" style="margin-right:0">联系电话</label>
        <div class="width-267">
          <div class="clearfix">
            <input class="col-xs-10 col-xs-12 bk-1" readonly="readonly" name="consignorContactPhone" id="consignorContactPhone" type="text" placeholder=""/>
          </div>
        </div>
      </div>
    </div>
    <div class="col-xs-12">
      <div class="form-group" style="margin-left: 0">
        <label class="control-label col-label no-padding-right l-bj" for="" style="margin-right:0">地址</label>
        <div class="col-xs-9">
          <div class="clearfix">
            <input style="width:583px;"  readonly="readonly" name="consignorContactAddress" id="consignorContactAddress" type="text" placeholder="" />
            <input class="col-xs-10 col-xs-12 bk-1" name="consignorType" id="consignorType" type="hidden" />
            <input class="col-xs-10 col-xs-12 bk-1" name="consignorContactCompanyId" id="consignorContactCompanyId" type="hidden" />
            <input class="col-xs-10 col-xs-12 bk-1" name="consignorContactCode" id="consignorContactCode" type="hidden" />
            <input class="col-xs-10 col-xs-12 bk-1" name="consignorPhone" id="consignorPhone" type="hidden" />
            <input class="col-xs-10 col-xs-12 bk-1" name="consignorProvince" id="consignorProvince" type="hidden" />
            <input class="col-xs-10 col-xs-12 bk-1" name="consignorProvinceName" id="consignorProvinceName" type="hidden" />
            <input class="col-xs-10 col-xs-12 bk-1" name="consignorCity" id="consignorCity" type="hidden" />
            <input class="col-xs-10 col-xs-12 bk-1" name="consignorCityName" id="consignorCityName" type="hidden" />
            <input class="col-xs-10 col-xs-12 bk-1" name="consignorArea" id="consignorArea" type="hidden" />
            <input class="col-xs-10 col-xs-12 bk-1" name="consignorAreaName" id="consignorAreaName" type="hidden" />
            <input class="col-xs-10 col-xs-12 bk-1" name="consignorStreet" id="consignorStreet" type="hidden" />
            <input class="col-xs-10 col-xs-12 bk-1" name="consignorStreetName" id="consignorStreetName" type="hidden" />
            <input class="col-xs-10 col-xs-12 bk-1" name="consignorAddress" id="consignorAddress" type="hidden" />
          </div>
        </div>

      </div>
    </div>
  </div>
<#--</form>-->
  <br/>
  <div class="col-xs-12">
    <div class="tabbable">
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
          <table id="orderGoodsListTable" class="table table-striped table-bordered table-hover dataTable no-footer bg-1" role="grid"
                 aria-describedby="dynamic-table_info" style="margin-top:17px;">
            <thead>
            <tr role="row" id="222">
              <th class="center sorting_disabled" rowspan="1" colspan="1" aria-label="">
                操作
              </th>
            <#--<th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Status: activate to sort column ascending">序号</th>-->
              <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1"
                  aria-label="Domain: activate to sort column ascending" style="width:42px;">序号
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
                  aria-label="Clicks: activate to sort column ascending">销售单价
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
        <#--<span style="cursor:pointer" id="consigneeListConfirmDivBlock"><button type="button" class="btn btn-info qrshf" id="">确认收货方</button></span>-->
          <span style="cursor:pointer" id="consigneeListClearDivBlock"><button type="button" class="btn btn-info" id="">重置收货方</button></span>
          <table id="consigneeListTable" class="table table-striped table-bordered table-hover dataTable no-footer bg-1" role="grid"
                 aria-describedby="dynamic-table_info" style="margin-top:17px;">
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
  <button class="btn btn-white btn-info btn-bold btn-interval db-1" id="orderPlaceConTableBtn" onclick="javascript:$('#operationDistributingFormValidate').submit();">
    <i class="ace-icon fa fa-floppy-o bigger-120 blue"></i>
    确认下单
  </button>
</div>


<script type="text/javascript">
  var scripts = [null,
    "/components/jquery-validation/dist/jquery.validate.min.js",
    "/components/jquery-validation/src/localization/messages_zh.js",
    "/components/jquery-validation/dist/additional-methods.js",

    null];
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

    validateFormData();
    $("body").find(".es-list:last").prevAll("ul").remove();
  }
  //链接到收发货方联系人档案
  $("#to_operation_csc_contact_manage").click(function () {
    /*var csc_url = $("#csc_url").html();
    var url = csc_url + "/csc/receive/toMainReceivAndDispatchFilePage";
    xescm.common.loadPage(url);*/
    var url = "/csc/receive/toMainReceivAndDispatchFilePage";
    var html = window.location.href;
    var index = html.indexOf("/index#");
    window.open(html.substring(0,index) + "/index#" + url);
  })
  //创建新客户
  $("#to_csc_createCustBtn").click(function () {
    var url = "/csc/customer/toAddCustomerPage";
    var html = window.location.href;
    var index = html.indexOf("/index#");
    window.open(html.substring(0,index) + "/index#" + url);
  })
  var goodsAndConsigneeMap = new HashMap();
  var couldChangeCust = true;

  $(function () {

    var excelImportTag = $("#excelImportTag").html();
    if("confirm" == excelImportTag){ // 如果是Excel导入进入这个页面//先将用户选择的客户显示出来
      var customerCode = $("#customerCodeFromExcelImport").html();


      var custName = $("#custNameFromExcelImport").html();
      //重新从接口里查一遍
      CommonClient.post(sys.rootPath + "/ofc/distributing/queryCustomerByName", {"custName":custName,"pageNum":1, "pageSize":1}, function(data) {
        if (data != null && data != '' && data != undefined && data.result != null && data.result.list != null  && data.result.list.length > 0) {
//                    data=eval(data);
          $.each(data.result.list,function (index,cscCustomerVo) {
            if(index == 0){//只显示第一条
              $("#custName").val(cscCustomerVo.customerName);
              $("#customerCode").val(cscCustomerVo.customerCode);
              $("#customerCodeForGoods").val(cscCustomerVo.customerCode);
            }
          });
        }
      })
      //加载完客户后自动加载仓库列表, 和货品种类
      //加载仓库列表
      $("#warehouseCode option").remove();
      //<option value="">无</option>
      $("#warehouseCode").append("<option value = ''>无</option>");
      CommonClient.post(sys.rootPath + "/ofc/distributing/queryWarehouseByCustId",{"customerCode":customerCode},function(data) {
        data=eval(data);
        $.each(data,function (index,warehouse) {
          $("#warehouseCode").append("<option value='"+warehouse.warehouseCode+"'>"+warehouse.warehouseName+"</option>");
        });
        $("#warehouseCode").trigger("chosen:updated")
      })

      //将用户选择的客户以及连带的仓库显示出来后,再在页面上展示用户插入的数据
      //显示收货人列表
      $("#consigneeInfoListDiv").html("");
      $.each(consigneeList,function(index,consignee){
        $("#consigneeInfoListDiv").append("<tr class='odd' role='row'>" +
                "<td><a onclick='deleteConsignee(this)'  class='red'>删除</a></td>"+
                "<td>" + StringUtil.nullToEmpty(consignee.contactCompanyName) + "</td>" +
                "<td><input onpaste='return false' onkeydown='this.value = onlyNumAndAbc(this.value)' onkeyup='this.value = onlyNumAndAbc(this.value)' value='"+ StringUtil.nullToEmpty(consignee.custOrderCode) +"' style='border:1px solid #cacaca'/></td>" +//===
                "<td>" + StringUtil.nullToEmpty(consignee.contactName) + "</td>" +
                "<td>" + StringUtil.nullToEmpty(consignee.phone) + "</td>" +
                "<td>" + StringUtil.nullToEmpty(consignee.detailAddress) + "</td>" +
                "<td style='display:none'>" + StringUtil.nullToEmpty(consignee.type) + "</td>" +
                "<td style='display:none'>" + StringUtil.nullToEmpty(consignee.contactCompanySerialNo) + "</td>" +
                "<td style='display:none'>" + StringUtil.nullToEmpty(consignee.contactSerialNo) + "</td>" +
                "<td style='display:none'>" + StringUtil.nullToEmpty(consignee.phone) + "</td>" +
                "<td style='display:none'>" + StringUtil.nullToEmpty(consignee.province) + "</td>" +
                "<td style='display:none'>" + StringUtil.nullToEmpty(consignee.provinceName) + "</td>" +
                "<td style='display:none'>" + StringUtil.nullToEmpty(consignee.city) + "</td>" +
                "<td style='display:none'>" + StringUtil.nullToEmpty(consignee.cityName) + "</td>" +
                "<td style='display:none'>" + StringUtil.nullToEmpty(consignee.area) + "</td>" +
                "<td style='display:none'>" + StringUtil.nullToEmpty(consignee.areaName) + "</td>" +
                "<td style='display:none'>" + StringUtil.nullToEmpty(consignee.street) + "</td>" +
                "<td style='display:none'>" + StringUtil.nullToEmpty(consignee.streetName) + "</td>" +
                "<td style='display:none'>" + StringUtil.nullToEmpty(consignee.address) + "</td>" +
                "</tr>");
      })


      //先做成死的, 暂时不允许收货方动态增删
      //ifConsigneeConfirm = true;

      //显示货品列表//viewMap
      $("#goodsInfoListDiv").html("");
      var viewMapKeys = viewMap.keys();
      var viewMapIndexOf = 0;
      for(var key in viewMapKeys){

        viewMapIndexOf += 1;
        var viewMapValue = viewMapKeys[key];
        var goodsDetail = viewMap.get(viewMapValue)[0];
        if(null == goodsDetail || undefined == goodsDetail){
          continue;
        }
        var goodsCode = goodsDetail.goodsCode; //这儿报错?
        var realGoodsCode0 = goodsCode.split('@')[0];
        var realGoodsCode1 = '';
        if(goodsCode.split('@').length > 1){
          realGoodsCode1 = '@' + goodsCode.split('@')[1];
        }
        goodsAndConsigneeMap.put(viewMapValue,viewMap.get(viewMapValue));//将导入的Map里的值放到当前页面中去! 减少页面改动!
        $("#goodsInfoListDiv").append("<tr role='row' class='odd' >" +
                "<td>" +
                "<a onclick='deleteGood(this)' class='red'>删除&nbsp;</a><span style='margin:0 5px;'>|</span>" +
                "<a onclick='goodsAndConsignee(this)' class='blue'>&nbsp;录入</a>" +
                "</td>" +
                "<td>" + viewMapIndexOf + "</td>" +
                "<td>" + realGoodsCode0 + "<textarea hidden>" + realGoodsCode1 + "</textarea>" + "</td>" +
                "<td>" + StringUtil.nullToEmpty(goodsDetail.goodsName) + "</td>" +
                "<td>" + StringUtil.nullToEmpty(goodsDetail.specification) + "</td>" +
                "<td>" + StringUtil.nullToEmpty(goodsDetail.unit) + "</td>" +
                "<td>" + StringUtil.nullToEmpty(goodsDetail.unitPrice) + "</td>" +
                "<td>" + StringUtil.nullToEmpty(goodsDetail.goodsAmount) + "</td>" +
                "<td  style='display:none'>" + goodsDetail.goodsTypeName + "</td>" +
                "<td  style='display:none'>" + goodsDetail.goodsTypeParentName + "</td>" +
                "</tr>");
      }
    }

    $("#goodsListDivBlock").click(function () {
      var consigneeChosen =  $("#consigneeInfoListDiv").find("tr").size();
      if(consigneeChosen < 1){
        alert("请先添加收货方")
      }/*else if(!ifConsigneeConfirm){
                alert("请先确认收货方");
            }*/else{
        $("#goodcheck").attr("checked", false);
        $("#goodsSelectListTbody tr td label input[type='checkbox']").attr("checked", false);
        //加载货品一级种类
        var customerCode = $("#customerCode").val();
        $("#goodsTypeId option").remove();
        $("#goodsSecTypeId option").remove();
        $("#goodsTypeId").append("<option value = ''>全部</option>");//123//$("#warehouseCode").append("<option value = ''>无</option>");
        $("#goodsSecTypeId").append("<option value = ''>全部</option>");

        var firstGoodsType = null;
        CommonClient.syncpost(sys.rootPath + "/ofc/distributing/queryGoodsTypeByCustId",{"customerCode":customerCode},function(data) {
          data=eval(data);
          $.each(data,function (index,goodsType) {
            if(0 == index){
              firstGoodsType = goodsType.id;
            }
            $("#goodsTypeId").append("<option value='"+goodsType.id+"'>"+goodsType.goodsTypeName+"</option>");
            $("#goodsTypeId").trigger("chosen:updated");
          });
        });
        //加载第一个一级货品的二级种类//000
        $("#goodsSecTypeId option").remove();
        $("#goodsSecTypeId").append("<option value = ''>全部</option>");
        if(null != firstGoodsType){
          CommonClient.syncpost(sys.rootPath + "/ofc/distributing/queryGoodsSecTypeByCAndT",{"customerCode":customerCode,"goodsType":firstGoodsType},function(data) {
            data=eval(data);
            $.each(data,function (index,secGoodsType) {
              $("#goodsSecTypeId").append("<option value='"+secGoodsType.id+"'>"+secGoodsType.goodsTypeName+"</option>").trigger("chosen:updated");

            });
          })

        }


//        $("#goodsListDiv").fadeIn(0);//淡入淡出效果 显示div
        confirmGood();
      }
    });
    $("#goodsListDivNoneTop").click(function(){

      $("#goodsListDiv").fadeOut(0);//淡入淡出效果 隐藏div

    });
    $("#goodsListDivNoneBottom").click(function(){

      $("#goodsListDiv").fadeOut(0);//淡入淡出效果 隐藏div

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

  })



  $("#goodsSelectFormBtn").click(function () {
    queryGoodsDataDisti(1)
  })

  function queryGoodsDataDisti(pageNum){
    $("#goodsSelectListTbody").html("");
    $("#goodcheck").attr("checked", false);
    var cscGoods = {};
    var customerCode = $("#customerCodeForGoods").val();
    var goodsTypeId = $("#goodsTypeId").val();
    var goodsSecTypeId = $("#goodsSecTypeId").val();
    var goodsName = $("#goodsName").val();
    var barCode = $("#barCode").val();
    cscGoods.goodsTypeId = goodsTypeId;
    cscGoods.goodsSecTypeId = goodsSecTypeId;
    cscGoods.goodsName = goodsName;
    cscGoods.barCode = barCode;
    cscGoods.pNum = pageNum;
    cscGoods.pSize = 9;
    var param = JSON.stringify(cscGoods);


    CommonClient.post(sys.rootPath + "/ofc/goodsSelects", {"cscGoods":param,"customerCode":customerCode}, function(data) {

      if (data == undefined || data == null || data.result ==null || data.result.size == 0 || data.result.list == null) {
        layer.msg("暂时未查询到货品信息！！");
      } else if (data.code == 200) {
        loadGoodsDistri(data.result.list);
        laypage({
          cont: $("#pageBarDivGoodsDistri"), // 容器。值支持id名、原生dom对象，jquery对象,
          pages: data.result.pages, // 总页数
          skip: true, // 是否开启跳页
          skin: "molv",
          groups: 3, // 连续显示分页数
          curr: data.result.pageNum, // 当前页
          jump: function (obj, first) { // 触发分页后的回调
            if (!first) { // 点击跳页触发函数自身，并传递当前页：obj.curr
              queryGoodsDataDisti(obj.curr);
            }
          }
        });
        $(".total").html(data.result.total+'&nbsp;条&nbsp;');
      } else if (data.code == 403) {
        alert("没有权限")
      } else {
        $("#goodsSelectListTbody").html("");
      }
      data=eval(data);

    },"json");
  }

  function loadGoodsDistri(data) {
    data=eval(data);

    var goodsList = "";
    $.each(data,function (index,cscGoodsVo) {

      goodsList =goodsList + "<tr role='row' class='odd' onclick='chosenTr(this)'>";
      goodsList =goodsList + "<td class='center'> "+"<label class='pos-rel'>"+"<input type='checkbox'  class='ace' >"+"<span class='lbl'></span>"+"</label>"+"</td>";
      goodsList =goodsList + "<td>"+StringUtil.nullToEmpty(cscGoodsVo.goodsTypeParentName)+"</td>";//货品种类
      goodsList =goodsList + "<td>"+StringUtil.nullToEmpty(cscGoodsVo.goodsTypeName)+"</td>";//货品小类
      goodsList =goodsList + "<td>"+StringUtil.nullToEmpty(cscGoodsVo.brand)+"</td>";//品牌
      goodsList =goodsList + "<td>"+StringUtil.nullToEmpty(cscGoodsVo.goodsCode)+"</td>";//货品编码
      goodsList =goodsList + "<td>"+StringUtil.nullToEmpty(cscGoodsVo.goodsName)+"</td>";//货品名称
      goodsList =goodsList + "<td>"+StringUtil.nullToEmpty(cscGoodsVo.specification)+"</td>";//规格
      goodsList =goodsList + "<td>"+StringUtil.nullToEmpty(cscGoodsVo.unit)+"</td>";//单位
      goodsList =goodsList + "<td>"+StringUtil.nullToEmpty(cscGoodsVo.barCode)+"</td>";//条形码
      /*goodsList =goodsList + "<td style='display:none'>"+cscGoodsVo.weight+"</td>";
      goodsList =goodsList + "<td style='display:none'>"+cscGoodsVo.volume+"</td>";*/
      goodsList =goodsList + "</tr>";
    });
    $("#goodsSelectListTbody").html(goodsList);
  }

  function warehouseByCust(){
    if(!validateCustChosen()){//如果没选客户
      alert("请先选择客户");
    }
  }

  $("#goodsTypeId").change(function () {

    var customerCode = $("#customerCode").val();
    var goodsType = $("#goodsTypeId").val();
    //$("#goodsTypeId option").remove();
    $("#goodsSecTypeId option").remove();
    $("#goodsSecTypeId").append("<option value = ''>全部</option>");
    CommonClient.post(sys.rootPath + "/ofc/distributing/queryGoodsSecTypeByCAndT",{"customerCode":customerCode,"goodsType":goodsType},function(data) {
      data=eval(data);
      $.each(data,function (index,secGoodsType) {
        $("#goodsSecTypeId").append("<option value='"+secGoodsType.id+"'>"+secGoodsType.goodsTypeName+"</option>").trigger("chosen:updated");
      });
    })
  })



  function deleteConsignee(obj) {

    //动态删除收货方,即从Map中从收货方给拆出来
    //遍历货品信息
    var contactCompanyId = $(obj).parent().parent().children().eq(7).text();//---
    var contactCode = $(obj).parent().parent().children().eq(8).text();
    $("#goodsInfoListDiv").find("tr").each(function(index) {
      var tdArr = $(this).children();

      var goodsIndex = tdArr.eq(1).text();//货品序号
      var goodsCode = tdArr.eq(2).text();//货品编码
      var goodsAmountTotal = tdArr.eq(7).text();//货品需求数量合计

      var mapKey = goodsCode + "@" + goodsIndex;
      var consigneeAndGoodsMsgJson = null;
      if(null != goodsAndConsigneeMap.get(mapKey) && undefined != goodsAndConsigneeMap.get(mapKey)){
        if(goodsAndConsigneeMap.get(mapKey).length > 0){
          consigneeAndGoodsMsgJson = goodsAndConsigneeMap.get(mapKey)[1];//联系人和货品的对应信息
        }else{
          return;
        }
      }

      if(null != consigneeAndGoodsMsgJson){

        var param = contactCompanyId +"@"+ contactCode;
        var goodsAmount = consigneeAndGoodsMsgJson[param];
        if(undefined != goodsAmount || !StringUtil.isEmpty(goodsAmount)){

          //delConsigneeTag = true;
          ///然后从每个货品数量的总量中减去对应的数量
          goodsAmountTotal = goodsAmountTotal - goodsAmount;
          //对货品列表重新进行展示
          tdArr.eq(7).text(goodsAmountTotal);
          delete consigneeAndGoodsMsgJson[param]; //遍历删除对应JSON结点
          return true;
        }

      }
    })
    $(obj).parent().parent().remove();
  }


  function deleteGood(obj) {
    layer.confirm('您确认删除该货品吗?', {
      skin : 'layui-layer-molv',
      icon : 3,
      title : '确认操作'
    }, function(index){
      $(obj).parent().parent().remove();
      //删除Map中对应的数据
      var goodsIndex = $(obj).parent().parent().children().eq(1).text();
      var goodsCode = $(obj).parent().parent().children().eq(2).text();
      var mapKey =  goodsCode + "@" + goodsIndex;
      if(null != goodsAndConsigneeMap.get(mapKey) || undefined != goodsAndConsigneeMap.get(mapKey)){
        goodsAndConsigneeMap.remove(mapKey);
      }
      layer.close(index);
    }, function(index){
      layer.close(index);
    });



  }

  function goodsAndConsignee(obj){

   /* $("#goodsAndConsigneeDiv").fadeIn(0);*/
    //显示货品信息
    var goodsIndex = $(obj).parent().parent().children().eq(1).text();//000
    var goodsCode = $(obj).parent().parent().children().eq(2).text();
    var useGoodsCode = goodsCode;
    var goodsCode1 = goodsCode.split('@')[0];
    var goodsName = $(obj).parent().parent().children().eq(3).text();
    var specification = $(obj).parent().parent().children().eq(4).text();
    var unit = $(obj).parent().parent().children().eq(5).text();
    $("#goodsIndexDivHidden").val(goodsIndex);
    $("#goodsCodeDiv").val(goodsCode1);
    var goodsUnitPrice = '';
    if(goodsCode.split('@').length > 1){
      goodsUnitPrice = goodsCode.split('@')[1];
      $("#goodsUnitPriceDiv").val('@' + goodsUnitPrice);
    }
    $("#goodsNameDiv").val(goodsName);
    $("#specificationDiv").val(specification);
    $("#unitDiv").val(unit);


    //最后提交订单的时候做个校验, 如果货品的需求数量为0就提示!
    //显示收货人信息
    var consignorout = "";
    $("#consigneeInfoListDiv").find("tr").each(function(index){//00000
      var tdArr = $(this).children();
      var consigneeName = tdArr.eq(1).text();//
      var consigneeContactName = tdArr.eq(3).text();//
      var consigneeType = tdArr.eq(6).text();//
      var consigneeCode = tdArr.eq(7).text();
      var consigneeContactCode = tdArr.eq(8).text();
      var mapKey = useGoodsCode + "@" + goodsIndex;
      var num = "0";

      if(undefined != goodsAndConsigneeMap.get(mapKey)){
        var preGoodsAndConsigneeJsonMsg = goodsAndConsigneeMap.get(mapKey)[1];
        //preGoodsAndConsigneeJsonMsg = JSON.stringify(preGoodsAndConsigneeJsonMsg);
        var cadj = consigneeCode + "@" + consigneeContactCode;
        num = preGoodsAndConsigneeJsonMsg[cadj];
        if(undefined == num || null == num){
          num = "0";
        }
      }

      consignorout =consignorout + "<tr role='row' class='odd' align='center'>";
      if("1" == consigneeType){
        consignorout =consignorout + "<td>"+consigneeName+"</td>";
      }else if("2" == consigneeType){
        consignorout =consignorout + "<td>"+consigneeName+"-"+consigneeContactName+"</td>";
      }else{
        consignorout =consignorout + "<td>"+consigneeName+"</td>";
      }
      consignorout =consignorout + "<td><input value='"+num+"' onpause='return false' onkeypress='onlyNumber(this)' onkeyup='onlyNumber(this)' style='border:1px solid #cacaca'/></td>";
      consignorout =consignorout + "<td style='display:none'>"+consigneeCode+"</td>";
      consignorout =consignorout + "<td style='display:none'>"+consigneeContactCode+"</td>";
      consignorout =consignorout + "</tr>";
    });
    $("#goodsAndConsigneeTbody").html(consignorout);
    confirmBlue();
  }//



  //校验是否选了客户
  function validateCustChosen() {
    var custChosen = $("#custName").val();
    var customerCode = $("#customerCode").val();
    if(StringUtil.isEmpty(custChosen) || StringUtil.isEmpty(customerCode)){
      return false;
    }else{
      return true;
    }
  }

  $("#consignorListDivBlock").click(function(){

    if(!validateCustChosen()){
      alert("请先选择客户")
    }else{
      $("#pageBarDivConsignor").hide();
      $("#contactSelectListTbody2").html("");
      var cscContantAndCompanyDto = {};
      var cscContactDto = {};
      var cscContactCompanyDto = {};
      cscContactCompanyDto.contactCompanyName = "";
      cscContactDto.purpose = "2";
      cscContactDto.contactName = "";
      cscContactDto.phone = "";
      cscContantAndCompanyDto.cscContactDto = cscContactDto;
      cscContantAndCompanyDto.cscContactCompanyDto = cscContactCompanyDto;
      cscContantAndCompanyDto.pageNum = 1;
      cscContantAndCompanyDto.pageSize = 10;
      var customerCode = $("#customerCode").val();
      var param = JSON.stringify(cscContantAndCompanyDto);
      var contactList = 0;

      var contactCompanyNameAuto = null;
      var contactNameAuto = null;
      //var phoneAuto = null;
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

      CommonClient.syncpost(sys.rootPath + "/ofc/contactSelectForPage",{"cscContantAndCompanyDto":param,"customerCode":customerCode}, function(data) {
        if(null == data || undefined == data || null == data.result || undefined == data.result || null == data.result.list || undefined == data.result.list){
          return ;
        }
        data=eval(data.result.list);

        $.each(data,function (index,CscContantAndCompanyDto) {
          contactList += 1;
          if(contactList == 1){
            contactCompanyNameAuto = StringUtil.nullToEmpty(CscContantAndCompanyDto.contactCompanyName);
            contactNameAuto = StringUtil.nullToEmpty(CscContantAndCompanyDto.contactName);
            //phoneAuto = CscContantAndCompanyDto.phone;
            detailAddressAuto = StringUtil.nullToEmpty(CscContantAndCompanyDto.detailAddress);
            typeAuto = StringUtil.nullToEmpty(CscContantAndCompanyDto.type);
            contactCompanyIdAuto = StringUtil.nullToEmpty(CscContantAndCompanyDto.contactCompanySerialNo);
            contactCodeAuto = StringUtil.nullToEmpty(CscContantAndCompanyDto.contactSerialNo);//000
            phoneAuto = StringUtil.nullToEmpty(CscContantAndCompanyDto.phone);
            provinceAuto = StringUtil.nullToEmpty(CscContantAndCompanyDto.province);
            provinceNameAuto = StringUtil.nullToEmpty(CscContantAndCompanyDto.provinceName);
            cityAuto = StringUtil.nullToEmpty(CscContantAndCompanyDto.city);
            cityNameAuto = StringUtil.nullToEmpty(CscContantAndCompanyDto.cityName);
            areaAuto = StringUtil.nullToEmpty(CscContantAndCompanyDto.area);
            areaNameAuto = StringUtil.nullToEmpty(CscContantAndCompanyDto.areaName);
            streetAuto = StringUtil.nullToEmpty(CscContantAndCompanyDto.street);
            streetNameAuto = StringUtil.nullToEmpty(CscContantAndCompanyDto.streetName);
            addressAuto = StringUtil.nullToEmpty(CscContantAndCompanyDto.address);
          }
          if(contactList > 1){
            return true;
          }
        });
      },"json");
      if(contactList == 1){
        //只有一个联系人
        layer.confirm('您只有一个发货方联系人,点击确定我们将为您自动加载!', {
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
          $("#consignorName-error").html("");
          $("#consignorName-error").parent().parent().removeClass("has-error").addClass("has-success");


          layer.close(index);
        }, function(index){
          layer.close(index);
        });
      }else if(contactList == 0){
        alert("您还未添加任何联系人,请在收发货档案中进行添加操作！");
        return;
      }else {
       confirmSend();
      }

    }


  });

  $("#consignorListDivNoneTop").click(function(){

    $("#consignorListDiv").fadeOut(0);//淡入淡出效果 隐藏div

  });

  $("#consignorListDivNoneBottom").click(function(){

    $("#consignorListDiv").fadeOut(0);//淡入淡出效果 隐藏div

  });


  $("#consignorSelectFormBtn").click(function () {
    queryConsignorDataDistri(1);
  })

  function queryConsignorDataDistri(pageNum){
    $("#contactSelectListTbody2").html("");
    var cscContantAndCompanyDto = {};
    var cscContactDto = {};
    var cscContactCompanyDto = {};

    cscContactCompanyDto.contactCompanyName = $("#consignorName2").val();
    cscContactDto.purpose = "2";
    cscContactDto.contactName = $("#consignorPerson2").val();
    cscContactDto.phone = $("#consignorPhoneNumber2").val();
    cscContantAndCompanyDto.cscContactDto = cscContactDto;
    cscContantAndCompanyDto.cscContactCompanyDto = cscContactCompanyDto;
    cscContantAndCompanyDto.pageNum = pageNum;
    cscContantAndCompanyDto.pageSize = 10;
    var customerCode = $("#customerCode").val();

    var param = JSON.stringify(cscContantAndCompanyDto);

    CommonClient.post(sys.rootPath + "/ofc/contactSelectForPage",{"cscContantAndCompanyDto":param,"customerCode":customerCode}, function(data) {
      /*if(null == data || undefined == data || null == data.result || undefined == data.result || null == data.result.list || undefined == data.result.list){
              return ;
          }*/
      if (data == undefined || data == null || null == data.result || undefined == data.result
              || data.result.size == 0 || data.result.list == null || undefined == data.result.list) {
        $("#pageBarDivConsignor").hide();
        layer.msg("暂时未查询到发货方信息！！");
      } else if (data.code == 200) {
        $("#pageBarDivConsignor").show();
        loadConsingorDistri(data.result.list);
        laypage({
          cont: $("#pageBarDivConsignorDistri"), // 容器。值支持id名、原生dom对象，jquery对象,
          pages: data.result.pages, // 总页数
          skip: true, // 是否开启跳页
          skin: "molv",
          groups: 3, // 连续显示分页数
          curr: data.result.pageNum, // 当前页
          jump: function (obj, first) { // 触发分页后的回调
            if (!first) { // 点击跳页触发函数自身，并传递当前页：obj.curr
              queryConsignorDataDistri(obj.curr);
            }
          }
        });
        $(".total").html(data.result.total+'&nbsp;条&nbsp;');
      } else if (data.code == 403) {
        alert("没有权限")
      } else {
        $("#contactSelectListTbody2").html("");
      }

    },"json");
  }


  function loadConsingorDistri(data){
    data=eval(data);
    var contactList = "";
    $.each(data,function (index,CscContantAndCompanyDto) {
      /*consignorCodeHide = CscContantAndCompanyDto.contactCompanyId;
      consignorContactCodeHide = CscContantAndCompanyDto.contactSerialNo;
      consignorTypeHide = CscContantAndCompanyDto.type;*/
      contactList =contactList + "<tr role='row' class='odd' onclick='chosenTr(this)'>";
      contactList =contactList + "<td class='center' onclick='chosenTr(this)'> "+"<label class='pos-rel'>"+"<input name='consignorSel' type='radio' class='ace'>"+"<span class='lbl'></span>"+"</label>"+"</td>";
      contactList =contactList + "<td class='center'>"+(index+1)+"</td>";
      contactList =contactList + "<td>"+StringUtil.nullToEmpty(CscContantAndCompanyDto.contactCompanyName)+"</td>";
      contactList =contactList + "<td>"+StringUtil.nullToEmpty(CscContantAndCompanyDto.contactName)+"</td>";
      contactList =contactList + "<td>"+StringUtil.nullToEmpty(CscContantAndCompanyDto.phone)+"</td>";
      contactList =contactList + "<td>"+StringUtil.nullToEmpty(CscContantAndCompanyDto.detailAddress)+"</td>";
      contactList =contactList + "<td style='display:none'>"+StringUtil.nullToEmpty(CscContantAndCompanyDto.type)+"</td>";
      contactList =contactList + "<td style='display:none'>"+StringUtil.nullToEmpty(CscContantAndCompanyDto.contactCompanySerialNo)+"</td>";
      contactList =contactList + "<td style='display:none'>"+StringUtil.nullToEmpty(CscContantAndCompanyDto.contactSerialNo)+"</td>";
      contactList =contactList + "<td style='display:none'>"+StringUtil.nullToEmpty(CscContantAndCompanyDto.phone)+"</td>";
      contactList =contactList + "<td style='display:none'>"+StringUtil.nullToEmpty(CscContantAndCompanyDto.province)+"</td>";
      contactList =contactList + "<td style='display:none'>"+StringUtil.nullToEmpty(CscContantAndCompanyDto.provinceName)+"</td>";
      contactList =contactList + "<td style='display:none'>"+StringUtil.nullToEmpty(CscContantAndCompanyDto.city)+"</td>";
      contactList =contactList + "<td style='display:none'>"+StringUtil.nullToEmpty(CscContantAndCompanyDto.cityName)+"</td>";
      contactList =contactList + "<td style='display:none'>"+StringUtil.nullToEmpty(CscContantAndCompanyDto.area)+"</td>";
      contactList =contactList + "<td style='display:none'>"+StringUtil.nullToEmpty(CscContantAndCompanyDto.areaName)+"</td>";
      contactList =contactList + "<td style='display:none'>"+StringUtil.nullToEmpty(CscContantAndCompanyDto.street)+"</td>";
      contactList =contactList + "<td style='display:none'>"+StringUtil.nullToEmpty(CscContantAndCompanyDto.streetName)+"</td>";
      contactList =contactList + "<td style='display:none'>"+StringUtil.nullToEmpty(CscContantAndCompanyDto.address)+"</td>";
      contactList =contactList + "</tr>";
    });
    $("#contactSelectListTbody2").html(contactList);
  }


  $("#consigneeListDivBlock").click(function(){

    if(!validateCustChosen()){
      alert("请先选择客户")
    }/* else if(ifConsigneeConfirm){
            alert("您已确认过一次,无法再次添加新客户!");
            return;
        }*/else{
      //$("#contactSelectListTbody1").html("");
      $("#consigneecheck").attr("checked", false);
      $("#contactSelectListTbody1 tr td label input[name='consigneeSel']").attr("checked", false);
//      $("#consigneeListDiv").fadeIn(0);//淡入淡出效果 显示div
      confirmRev();
    }
  });


  $("#consigneeListDivNoneTop").click(function(){

    $("#consigneeListDiv").fadeOut(0);//淡入淡出效果 隐藏div

  });

  $("#consigneeListDivNoneBottom").click(function(){

    $("#consigneeListDiv").fadeOut(0);//淡入淡出效果 隐藏div

  });

  $("#consigneeSelectFormBtn").click(function () {
    queryConsigneeDataDistri(1);
  })

  function queryConsigneeDataDistri(pageNum) {
    $("#contactSelectListTbody1").html("");
    $("#consigneecheck").attr("checked", false);
    var cscContantAndCompanyDto = {};
    var cscContactDto = {};
    var cscContactCompanyDto = {};
    cscContactCompanyDto.contactCompanyName = $("#consignorName1").val();
    cscContactDto.purpose = "1";
    cscContactDto.contactName = $("#consignorPerson1").val();
    cscContactDto.phone = $("#consignorPhoneNumber1").val();
    cscContantAndCompanyDto.cscContactDto = cscContactDto;
    cscContantAndCompanyDto.cscContactCompanyDto = cscContactCompanyDto;
    cscContantAndCompanyDto.pageNum = pageNum;
    cscContantAndCompanyDto.pageSize = 10;
    var customerCode = $("#customerCode").val();

    var param = JSON.stringify(cscContantAndCompanyDto);

    CommonClient.post(sys.rootPath + "/ofc/contactSelectForPage",{"cscContantAndCompanyDto":param,"customerCode":customerCode}, function(data) {
      if (data == undefined || data == null || null == data.result || undefined == data.result
              || data.result.size == 0 || data.result.list == null || undefined == data.result.list) {
        $("#pageBarDivConsigneeDistri").hide();
        layer.msg("暂时未查询到发货方信息！！");
      } else if (data.code == 200) {
        $("#pageBarDivConsigneeDistri").show();
        loadConsingeeDistri(data.result.list);
        laypage({
          cont: $("#pageBarDivConsigneeDistri"), // 容器。值支持id名、原生dom对象，jquery对象,
          pages: data.result.pages, // 总页数
          skip: true, // 是否开启跳页
          skin: "molv",
          groups: 3, // 连续显示分页数
          curr: data.result.pageNum, // 当前页
          jump: function (obj, first) { // 触发分页后的回调
            if (!first) { // 点击跳页触发函数自身，并传递当前页：obj.curr
              queryConsigneeDataDistri(obj.curr);
            }
          }
        });
        $(".total").html(data.result.total+'&nbsp;条&nbsp;');
      } else if (data.code == 403) {
        alert("没有权限")
      } else {
        $("#contactSelectListTbody1").html("");
      }

    },"json");
  }

  function loadConsingeeDistri(data){
    data=eval(data);
    var contactList = "";
    $.each(data,function (index,CscContantAndCompanyDto) {
      /*consigneeCodeHide = CscContantAndCompanyDto.contactCompanyId;
      consigneeContactCodeHide = CscContantAndCompanyDto.contactSerialNo;
      consigneeTypeHide = CscContantAndCompanyDto.type;*/
      contactList =contactList + "<tr role='row' class='odd' onclick='chosenTr(this)'>";
      contactList =contactList + "<td class='center'> "+"<label class='pos-rel'>"+"<input name='consigneeSel' type='checkbox' class='ace'>"+"<span class='lbl'></span>"+"</label>"+"</td>";
      contactList =contactList + "<td class='center'>"+(index+1)+"</td>";
      contactList =contactList + "<td>"+StringUtil.nullToEmpty(CscContantAndCompanyDto.contactCompanyName)+"</td>";
      contactList =contactList + "<td>"+StringUtil.nullToEmpty(CscContantAndCompanyDto.contactName)+"</td>";
      contactList =contactList + "<td>"+StringUtil.nullToEmpty(CscContantAndCompanyDto.phone)+"</td>";
      contactList =contactList + "<td>"+StringUtil.nullToEmpty(CscContantAndCompanyDto.detailAddress)+"</td>";
      contactList =contactList + "<td style='display:none'>"+StringUtil.nullToEmpty(CscContantAndCompanyDto.type)+"</td>";
      contactList =contactList + "<td style='display:none'>"+StringUtil.nullToEmpty(CscContantAndCompanyDto.contactCompanySerialNo)+"</td>";
      contactList =contactList + "<td style='display:none'>"+StringUtil.nullToEmpty(CscContantAndCompanyDto.contactSerialNo)+"</td>";
      contactList =contactList + "<td style='display:none'>"+StringUtil.nullToEmpty(CscContantAndCompanyDto.phone)+"</td>";
      contactList =contactList + "<td style='display:none'>"+StringUtil.nullToEmpty(CscContantAndCompanyDto.province)+"</td>";
      contactList =contactList + "<td style='display:none'>"+StringUtil.nullToEmpty(CscContantAndCompanyDto.provinceName)+"</td>";
      contactList =contactList + "<td style='display:none'>"+StringUtil.nullToEmpty(CscContantAndCompanyDto.city)+"</td>";
      contactList =contactList + "<td style='display:none'>"+StringUtil.nullToEmpty(CscContantAndCompanyDto.cityName)+"</td>";
      contactList =contactList + "<td style='display:none'>"+StringUtil.nullToEmpty(CscContantAndCompanyDto.area)+"</td>";
      contactList =contactList + "<td style='display:none'>"+StringUtil.nullToEmpty(CscContantAndCompanyDto.areaName)+"</td>";
      contactList =contactList + "<td style='display:none'>"+StringUtil.nullToEmpty(CscContantAndCompanyDto.street)+"</td>";
      contactList =contactList + "<td style='display:none'>"+StringUtil.nullToEmpty(CscContantAndCompanyDto.streetName)+"</td>";
      contactList =contactList + "<td style='display:none'>"+StringUtil.nullToEmpty(CscContantAndCompanyDto.address)+"</td>";
      contactList =contactList + "</tr>";
      $("#contactSelectListTbody1").html(contactList);
    });
  }

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

  $("#custListDivBlock").click(function () {

    var consigneeNum = $("#consigneeInfoListDiv").find('tr').length;
    if(consigneeNum == 0){
        showCustomer();
    }else if(consigneeNum > 0){
      alert("您不能再选择客户! 如需重选, 请重置收发货方!")
    }else if(couldChangeCust){
        showCustomer();
    }
  });

  function selectCustCallback() {
//加载完客户后自动加载仓库列表, 和货品种类
      //加载仓库列表
      var customerCode = $("#customerCode").val();
      if(!StringUtil.isEmpty(customerCode)){
          $("#warehouseCode option").remove();
          $("#warehouseCode").append("<option value = ''>无</option>");
          CommonClient.post(sys.rootPath + "/ofc/distributing/queryWarehouseByCustId",{"customerCode":customerCode},function(data) {
              data=eval(data);
              $.each(data,function (index,warehouse) {
                  $("#warehouseCode").append("<option value='"+warehouse.warehouseCode+"'>"+warehouse.warehouseName+"</option>");
              });
              $("#warehouseCode").trigger("chosen:updated");
          })
      }
      if($("#custName").val()!==""){
          $("#custName-error").html("");
          $("#custName").parent().parent().parent().removeClass("has-error").addClass("has-success");
      }
  }

  $("#custListDivNoneBottom").click(function () {
    $("#custListDiv").fadeOut(0);//淡入淡出效果 隐藏div
  });
  $("#custListDivNoneTop").click(function () {
    $("#custListDiv").fadeOut(0);//淡入淡出效果 隐藏div
  });

  $("#consigneeListClearDivBlock").click(function () {

    var consignorout = $("#consigneeInfoListDiv").find("tr").size();
    if(consignorout > 0){
      layer.confirm('您即将清空收货方列表,您之前输入的货品信息将被清空!', {
        skin : 'layui-layer-molv',
        icon : 3,
        title : '确认操作'
      }, function(index){
        $("#consigneeInfoListDiv").html("");
        goodsAndConsigneeMap = new HashMap();
        $("#goodsInfoListDiv").html("");
        layer.close(index);
      }, function(index){
        layer.close(index);
      });
    }

  })

  // 分页查询客户
  $("#custSelectFormBtn").click(function() {
    queryCustomerData(1);
  });

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
        layer.msg("暂时未查询到客户信息！");
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
        $(".total").html(result.result.total+'&nbsp;条&nbsp;');
      } else if (result.code == 403) {
        alert("没有权限")
      } else {
        $("#custListDivTbody").html("");
      }
    },"json");
  }

  // 加载客户列表
  function loadCustomer(data) {
    if ((data == null || data == '' || data == undefined) || data.result.list == null || (data.result.list.length < 1)) {
      $("#custListDivTbody").html("");
      return;
    }
    var custList = "";
    $.each(data.result.list,function (index,cscCustomerVo) {
      var channel = cscCustomerVo.channel;
      if(null == channel){
        channel = "";
      }
      custList =custList + "<tr role='row' class='odd' onclick='chosenTr(this)'>";
      custList =custList + "<td class='center' onclick='chosenTr(this)'> "+"<label class='pos-rel'>"+"<input name='cust' type='radio' class='ace'>"+"<span class='lbl'></span>"+"</label>"+"</td>";
      custList =custList + "<td class='center'>"+cscCustomerVo.customerCode+"</td>";
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

  $("#toOperationDistributingExcel").click(function () {
    var custChosen = $("#custName").val();
    var customerCode = $("#customerCode").val();
    if(StringUtil.isEmpty(custChosen)){
      alert("请先选择客户");
    }else if(StringUtil.isEmpty(customerCode)){
      alert("该客户没有客户编码,请维护!")
    }else{
      var historyUrl = "operation_distributing";
      var url = "/ofc/operationDistributingExcel" + "/" + historyUrl + "/" + customerCode;
      xescm.common.loadPage(url);
    }
  })

  $("#merchandiser").editableSelect();


  function distributingOrderPlaceCon() {

    if(!validateForm()){
      //alert("您输入的订单信息不完整!")
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

      var warehouseCodeMessage = $("#warehouseCode").val();
      if("" == warehouseCodeMessage){
        orderInfo.orderType = "60";//运输
        orderInfo.businessType = "600";//城配
        orderInfo.provideTransport = "0";//不需要运输
      }else{
        orderInfo.orderType = "61";//仓储
        orderInfo.businessType = "610";//销售出库
        orderInfo.provideTransport = "1";//需要运输
      }
      orderInfo.orderTime = $('#orderTime').val() + " 00:00:00";//000
      orderInfo.transportType = "20";//运输类型默认整车
      orderInfo.pickUpGoods = "0";//是否上门提货 默认否
      orderInfo.twoDistribution = "0";//是否二次配送 默认否
      orderInfo.returnList = "0";//是否签单返回 默认否
      orderInfo.insure = "0";//是否货物保险 默认否
      orderInfo.collectFlag = "0";//是否代收货款 默认否
      orderInfo.merchandiser = $("#merchandiser").val();
      if("" != $("#expectedArrivedTime").val()){
        orderInfo.expectedArrivedTime = $('#expectedArrivedTime').val()+ ":00";
      }

      orderInfo.custName = $("#custName").val();//后端需特别处理
      orderInfo.custCode = $("#customerCode").val();//后端需特别处理
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
      var provinceCode = StringUtil.nullToEmpty($("#consignorProvince").val());
      orderInfo.departureProvince = StringUtil.nullToEmpty($("#consignorProvinceName").val());
      var cityCode = StringUtil.nullToEmpty($("#consignorCity").val());
      orderInfo.departureCity = StringUtil.nullToEmpty($("#consignorCityName").val());
      var areaCode = StringUtil.nullToEmpty($("#consignorArea").val());
      orderInfo.departureDistrict = StringUtil.nullToEmpty($("#consignorAreaName").val());
      var streetCode = StringUtil.nullToEmpty($("#consignorStreet").val());
      orderInfo.departureTowns = StringUtil.nullToEmpty($("#consignorStreetName").val());
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
      var province = tdArr.eq(10).text().replace('null','');
      var provinceName = tdArr.eq(11).text().replace('null','');
      var city = tdArr.eq(12).text().replace('null','');
      var cityName = tdArr.eq(13).text().replace('null','');
      var area = tdArr.eq(14).text().replace('null','');
      var areaName = tdArr.eq(15).text().replace('null','');
      var street = tdArr.eq(16).text().replace('null','');
      var streetName = tdArr.eq(17).text().replace('null','');
      var address = tdArr.eq(18).text();
      orderInfo.consigneeName = consigneeName;
      orderInfo.custOrderCode = custOrderCode;
      orderInfo.consigneeType = type;
      orderInfo.consigneeContactName = consigneeContactName;//000
      orderInfo.consigneeCode = contactCompanyId;
      orderInfo.consigneeContactCode = contactCode.split('@')[0];
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

      var orderGoodsType = '';
      $("#goodsInfoListDiv").find("tr").each(function(index) {//&&&

        goods = {};
        var tdArr = $(this).children();
        var goodsIndex = tdArr.eq(1).text();//货品序号
        var goodsCode = tdArr.eq(2).text();//货品编码
        var goodsName = tdArr.eq(3).text();//货品名称
        var goodsSpec = tdArr.eq(4).text();//规格
        var goodsUnit = tdArr.eq(5).text();//单位
        //var goodsTotalAmount = tdArr.eq(6).text();//总数量
        var goodsUnitPrice = tdArr.eq(6).text();
        var goodsSecType = tdArr.eq(8).text();
        var goodsFirType = tdArr.eq(9).text();
        if(0 == index){
          orderGoodsType = goodsFirType;
        }
        goods.goodsCode = goodsCode;
        goods.goodsName = goodsName;
        goods.goodsSpec = goodsSpec;
        goods.unit = goodsUnit;
        goods.unitPrice = goodsUnitPrice;
        goods.goodsCategory = goodsSecType;
        goods.goodsType = goodsFirType;
        goods.chargingWays = '01';//计费方式按默认按件数

        var mapKey = goodsCode + "@" + goodsIndex;
//                var goodsMsgStr =  goodsAndConsigneeMap.get(mapKey)[0];//货品信息


        var consigneeAndGoodsMsgJson = goodsAndConsigneeMap.get(mapKey)[1];//联系人和货品的对应信息 ///====================
        var param =contactCompanyId + "@" + contactCode;
        var goodsAmount = consigneeAndGoodsMsgJson[param];
        goods.quantity = goodsAmount;
        goods.chargingQuantity = goodsAmount;
        goodsList[index] = goods;
      })
      orderInfo.goodsTypeName = orderGoodsType;
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
//                    location.reload();
              var getTimestamp  = new Date().getTime();
              xescm.common.loadPage("/ofc/operationDistributing");
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
      var contactCode = tdArr.eq(8).text();

      //遍历货品信息
      var consigneeGoodsIsEmpty = true;//收房方所有货品的数量校验标记
      var goodsIsEmpty = false;//货品列表中货品最后一列的货品数量校验标记
      var goodsNum = 0;//货品列表数量
//            var goodsAndConsigneeTag = true;
      var consigneeAndGoodsMsgJson = null;
      $("#goodsInfoListDiv").find("tr").each(function(index) {
        goodsNum += 1;

        goods = {};
        var tdArr = $(this).children();
        var goodsIndex = tdArr.eq(1).text();//货品序号
        var goodsCode = tdArr.eq(2).text();//货品编码
        var goodsName = tdArr.eq(3).text();//货品名称
        var goodsTotalAmount = tdArr.eq(7).text();//总数量
        if(goodsTotalAmount == 0){
          alert("货品列表中【"+goodsName+"】的数量为空,请检查!");
          //return false;
          goodsIsEmpty = true;
          return false;
        }

        var mapKey = goodsCode + "@" + goodsIndex;

        if(null != goodsAndConsigneeMap.get(mapKey) || undefined == goodsAndConsigneeMap.get(mapKey)){
//                    if(goodsAndConsigneeTag){
          consigneeAndGoodsMsgJson = goodsAndConsigneeMap.get(mapKey)[1];//联系人和货品的对应信息

//                    }
        }
        var goodsAmount = 0;
        if(null != consigneeAndGoodsMsgJson){//要么没进来要么goodsamount是个0
          var param = contactCompanyId +"@"+ contactCode;
          goodsAmount = consigneeAndGoodsMsgJson[param];

        }

        if(goodsAmount != 0 && undefined != goodsAmount && null != goodsAmount){

          consigneeGoodsIsEmpty = false;
          //return false;
        }
//                goodsAndConsigneeTag = false;
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
  var mistake="<i class='fa fa-times-circle w-error-icon bigger-130'></i>";
  function validateFormData() {
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
          required:mistake+"请输入订单日期"
        },
        merchandiser:{
          required:mistake+"请选择开单员"
        },
        custName: {
          required:mistake+"请选择客户",
          maxlength:mistake+"超过最大长度"
        },
        /* warehouseCode : {
             required:mistake+"请选择仓库"
         },*/
        notes:{
          maxlength:mistake+"超过最大长度"
        },
        consignorName:{
          required:mistake+"请选择发货方"
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
        //error.insertAfter(element.parent());
        error.insertAfter(element.parent());
      },
      submitHandler : function(form) {
        distributingOrderPlaceCon();
      },
      invalidHandler : function(form) {
      },
      onfocusout:function(element){
        $(element).valid();
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

  function onlyNumAndAbc(value){
    value = value.replace(/[^\a-\z\A-\Z0-9\u4E00-\u9FA5]/g,'');
    if(/\d{31}/.test(value)){
      value = value.replace(/\d$/gi,'');
    }
    return value;
  }
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
  function chosenTr(e){
    var rd =  $(e).children().first().find("input");
    if(rd.prop("checked")){
      rd.prop("checked",false);
    }else{
      rd.prop("checked","checked");
    }
  }

  //发货方联系人
 /* $("#consignorListDivBlock").click(function() {confirmSend();});*/
  function confirmSend() {
    layer.open({
      btn:['选中','关闭'],
      scrollbar:false,
      yes:function (adoptModalIndex) {
        couldChangeCust = false;
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
            var contactCompanyCode = tdArr.eq(7).text();
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
            $("#consignorContactCompanyId").val(contactCompanyCode);
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
        if($("#consignorName").val()!==""){
          $("#consignorName-error").html("");
          $("#consignorName").parent().parent().parent().removeClass("has-error").addClass("has-success");

        }
        if(consignorin==""){
          alert("请至少选择一行");
        }else{
          layer.close(adoptModalIndex);
          return false;
        }
      },
      type: 1,
      area: ['946px', '575px'],
      content: $('#consignorListDiv'), //这里content是一个DOM
      title: '发货方联系人',
      cancel: function (adoptModalIndex) {
        layer.close(adoptModalIndex);
        return false;
      }
    });
  }
  //收货方联系人
 /* $("#consigneeListDivBlock").click(function() {confirmRev();});*/
  function confirmRev() {
    layer.open({
      btn:['选中','关闭'],
      scrollbar:false,
      yes:function (adoptModalIndex) {
        couldChangeCust = false;
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
          contactCodeOut[contactCode] = 1;


          consignorout =consignorout + "<tr role='row' class='odd' onclick='chosenTr(this)'>";
          consignorout =consignorout + "<td><a onclick='deleteConsignee(this)' class='red'>删除</a></td>";
          consignorout =consignorout + "<td>"+consigneeName+"</td>";
          consignorout =consignorout + "<td><input onpaste='return false' onkeydown='this.value = onlyNumAndAbc(this.value)' onkeyup='this.value = onlyNumAndAbc(this.value)' value='" + consigneeCustOrderCode + "' style='border:1px solid #cacaca;' /></td>";
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

            if(undefined != contactCodeOut[contactCompanyId]
                    && contactCodeOut[contactCompanyId] == 1
                    && undefined != contactCodeOut[contactCode]&& contactCodeOut[contactCode] == 1){
              return true;
            }
            consignorout =consignorout + "<tr role='row' class='odd'  onclick='chosenTr(this)'>";
            consignorout =consignorout + "<td><a onclick='deleteConsignee(this)' class='red'>删除</a></td>";//###
            consignorout =consignorout + "<td>"+consigneeName+"</td>";
            consignorout =consignorout + "<td><input onpaste='return false'  onkeydown='this.value = onlyNumAndAbc(this.value)' onkeyup='this.value = onlyNumAndAbc(this.value)' style='border:1px solid #cacaca; ' /></td>";//-=-=onkeyup=\"this.value = this.value.replace(/[^\w]/ig,'')\"
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
          layer.close(adoptModalIndex);
          //遍历货品和收货人列表,将新增的收货人在map集合中增加对应的货品中收货人的信息,设初始收货数量为0

          $("#consigneeInfoListDiv").find("tr").each(function (index) {
            var tdArr = $(this).children();
            var contactCompanyId = tdArr.eq(7).text();
            var contactCode = tdArr.eq(8).text();
            //遍历货品信息
            $("#goodsInfoListDiv").find("tr").each(function(index) {
              var tdArr = $(this).children();
              var goodsIndex = tdArr.eq(1).text();//货品序号
              var goodsCode = tdArr.eq(2).text();//货品编码
              var mapKey = goodsCode + "@" + goodsIndex;
              var consigneeAndGoodsMsgJson = null;
              if(null != goodsAndConsigneeMap.get(mapKey) || undefined == goodsAndConsigneeMap.get(mapKey)){
                consigneeAndGoodsMsgJson = goodsAndConsigneeMap.get(mapKey)[1];//联系人和货品的对应信息
              }
              if(null != consigneeAndGoodsMsgJson){
                var param = contactCompanyId +"@"+ contactCode;
                var goodsAmount = consigneeAndGoodsMsgJson[param];
                if(undefined == goodsAmount || StringUtil.isEmpty(goodsAmount)){
                  consigneeAndGoodsMsgJson[param] = 0;
                }
              }
            })

          })

        }
      },
      type: 1,
      area: ['946px', '575px'],
      content: $('#consigneeListDiv'), //这里content是一个DOM
      title: '收货方联系人',
      cancel: function (adoptModalIndex) {
        layer.close(adoptModalIndex);
        return false;
      }
    });
  }
  //货品列表
  /*$("#goodsListDivBlock").click(function() {confirmGood();});*/
  function confirmGood() {
    layer.open({
      btn:['选中','关闭'],
      scrollbar:false,
      yes:function (adoptModalIndex) {
        couldChangeCust = false;
        var goodsInfoListDiv = "";
        var preIndex = "0";
        var goodsCodeOut = {};
        $("#goodsInfoListDiv").find("tr").each(function(index){
          var tdArr = $(this).children();
          var index = tdArr.eq(1).text();//序号
          var goodsCode = tdArr.eq(2).text();//货品编码


          goodsCodeOut[goodsCode] = 1;


          var goodsName = tdArr.eq(3).text();//货品名称
          var specification = tdArr.eq(4).text();//    货品规格
          var unit = tdArr.eq(5).text();//    单位
          var unitPrice = tdArr.eq(6).text();//    单位
          var sendGoods = tdArr.eq(7).text();//发货数量
          var goodsSecType = tdArr.eq(8).text();//货品二级类
          var goodsFirType = tdArr.eq(9).text();//货品一级类

          goodsInfoListDiv =goodsInfoListDiv + "<tr role='row' class='odd' align='center'>";
          goodsInfoListDiv =goodsInfoListDiv + "<td>" +
                  "<a onclick='deleteGood(this)' class='red'>删除&nbsp;</a><span style='margin:0 5px;'>|</span>" +
                  "<a onclick='goodsAndConsignee(this)' class='blue'>&nbsp;录入</a>" +
                  "</td>";
          goodsInfoListDiv =goodsInfoListDiv + "<td>"+index+"</td>";
          goodsInfoListDiv =goodsInfoListDiv + "<td>"+goodsCode+"</td>";
          goodsInfoListDiv =goodsInfoListDiv + "<td>"+goodsName+"</td>";
          goodsInfoListDiv =goodsInfoListDiv + "<td>"+specification+"</td>";
          goodsInfoListDiv =goodsInfoListDiv + "<td>"+unit+"</td>";
          goodsInfoListDiv =goodsInfoListDiv + "<td>"+unitPrice+"</td>";
          goodsInfoListDiv =goodsInfoListDiv + "<td>"+sendGoods+"</td>";
          goodsInfoListDiv =goodsInfoListDiv + "<td  style='display:none'>"+goodsSecType+"</td>";
          goodsInfoListDiv =goodsInfoListDiv + "<td  style='display:none'>"+goodsFirType+"</td>";
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
            var goodsFirType = tdArr.eq(1).text();//货品一级类
            var goodsSecType = tdArr.eq(2).text();//货品二级类
            var goodsCode = tdArr.eq(4).text();//货品编码
            var goodsName = tdArr.eq(5).text();//货品名称
            var specification = tdArr.eq(6).text();//规格
            var unit = tdArr.eq(7).text();//单位

            if(undefined != goodsCodeOut[goodsCode] && goodsCodeOut[goodsCode] == 1){
              return true;
            }

            goodsInfoListDiv =goodsInfoListDiv + "<tr role='row' class='odd' align='center' >";//class=\"btn btn-minier btn-yellow\"
            goodsInfoListDiv =goodsInfoListDiv + "<td>" +
                    "<a onclick='deleteGood(this)' class='red'>删除&nbsp;</a><span style='margin:0 5px;'>|</span>" +
                    "<a onclick='goodsAndConsignee(this)' class='blue'>&nbsp;录入</a>" +
                    "</td>";
            /*goodsInfoListDiv =goodsInfoListDiv + "<td><input id='deleteOrNot' type='checkbox'/></td>";*/
            goodsInfoListDiv =goodsInfoListDiv + "<td>"+preIndex+"</td>";
            goodsInfoListDiv =goodsInfoListDiv + "<td>"+goodsCode+"</td>";
            goodsInfoListDiv =goodsInfoListDiv + "<td>"+goodsName+"</td>";
            goodsInfoListDiv =goodsInfoListDiv + "<td>"+specification+"</td>";
            goodsInfoListDiv =goodsInfoListDiv + "<td>"+unit+"</td>";//单位
            goodsInfoListDiv =goodsInfoListDiv + "<td></td>";//单价
            goodsInfoListDiv =goodsInfoListDiv + "<td>0</td>";//数量
            goodsInfoListDiv =goodsInfoListDiv + "<td  style='display:none'>"+goodsSecType+"</td>";
            goodsInfoListDiv =goodsInfoListDiv + "<td  style='display:none'>"+goodsFirType+"</td>";
            goodsInfoListDiv =goodsInfoListDiv + "</tr>";
            str="str";
          }
        });
        if(goodsInfoListDiv==""){
          alert("请至少选择一行");
        }else{
          $("#goodsInfoListDiv").html(goodsInfoListDiv);
          layer.close(adoptModalIndex);

        }
      },
      type: 1,
      area: ['946px', '585px'],
      content: $('#goodsListDiv'), //这里content是一个DOM
      title: '货品列表',
      cancel: function (adoptModalIndex) {
        layer.close(adoptModalIndex);
        return false;
      }
    });
  }

  function confirmBlue() {
    layer.open({
      btn:['保存','不保存'],
      scrollbar:false,
      type: 1,
      area: '946px',
      content: $('#goodsAndConsigneeDiv'), //这里content是一个DOM
      title: '货品明细',
      yes:function(adoptModalIndex){
        var sendNum = 0;//统计某个货品的总的发货数量

        var mapValue = [];
        var mapKey = "";
        var goodsJson = {};
        var consigneeAndGoodsJson = {};

        $("#goodsAndConsigneeTbody").find("tr").each(function (index) {

          var tdArr = $(this).children();
          var num = tdArr.eq(1).children().val();//某个收货方该货品的需求量
          var consigneeCode = tdArr.eq(2).text();//某个收货方的编码
          var consigneeContactCode = tdArr.eq(3).text();
          //某个收货方联系人的编码
          if(StringUtil.isEmpty(num)){
            num = 0;
          }
          var cadj = consigneeCode + "@" + consigneeContactCode;
          consigneeAndGoodsJson[cadj] = num;
//            sendNum =( parseInt(Number(num) * 1000) + parseInt(Number(sendNum) * 1000) ) /1000;
          sendNum = (Number(num) + Number(sendNum)).toFixed(3)
        })
        var goodsInfoListDiv = "";
        $("#goodsInfoListDiv").find("tr").each(function(index) {
          var tdArr = $(this).children();
          var goodsIndex = tdArr.eq(1).text();//货品索引
          var goodsCode = tdArr.eq(2).text();//货品编码
          var goodsCodeDiv = $("#goodsCodeDiv").val();
          var goodsUnitPriceDiv = $("#goodsUnitPriceDiv").val();
          if(!StringUtil.isEmpty(goodsUnitPriceDiv)){
            goodsCodeDiv = goodsCodeDiv + goodsUnitPriceDiv;
          }
          var goodsIndexDivHidden = $("#goodsIndexDivHidden").val();
          if(goodsCode == goodsCodeDiv && goodsIndex == goodsIndexDivHidden){ //而且行号要卡
            tdArr.eq(7).text(sendNum);
            var indexIn = tdArr.eq(1).text();
            var goodsCodeIn = tdArr.eq(2).text();
            var goodsNameIn = tdArr.eq(3).text();
            var goodsSpecIn = tdArr.eq(4).text();
            var goodsUnitIn = tdArr.eq(5).text();
            var goodsUnitPriceIn = tdArr.eq(6).text();
            var goodsAmountIn = tdArr.eq(7).text();
            goodsJson.indexIn = indexIn;
            goodsJson.goodsCodeIn = goodsCodeIn;
            goodsJson.goodsNameIn = goodsNameIn;
            goodsJson.goodsSpecIn = goodsSpecIn;
            goodsJson.goodsUnitIn = goodsUnitIn;
            goodsJson.goodsUnitPriceIn = goodsUnitPriceIn;
            goodsJson.goodsAmountIn = goodsAmountIn;
            mapKey = goodsCodeIn + "@" + indexIn;
          }
        });
        /*
        往Map里放的数据结构
        key(货品编码@货品行号用来表示唯一一行):  value(json数组):[{"货品编码":xxx,"货品名称":xxx,...},{"收货人1code":20,"收货人2code":30}]
        */
        mapValue[0] = goodsJson;
        mapValue[1] = consigneeAndGoodsJson;
        goodsAndConsigneeMap.put(mapKey,mapValue);

        layer.close(adoptModalIndex);
        return false;
    },
      cancel: function (adoptModalIndex) {
        layer.close(adoptModalIndex);
        return false;
      }
    });
  }

</script>

<script type="text/javascript" src="../js/jquery.editable-select.min.js"></script>
