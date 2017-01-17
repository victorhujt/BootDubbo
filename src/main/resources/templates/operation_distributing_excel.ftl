<title>城配开单Excel导入</title>
<style type="text/css">
  /*  #goodsAndConsigneeDiv{
        position:fixed;
        left:50%;
        top:77px;
        margin-left:-400px;
        width:946px;
        height:500px;
        z-index:3;
        overflow: auto;
        border:solid #7A7A7A 2px;
    }
*/
    #acrossImg{
        position:fixed;
        left:14%;
        top:230px;
        overflow: auto;
        z-index: 3
    }

    #broadwiseImg{
        position:fixed;
        left:14%;
        top:270px;
        overflow: auto;
        z-index: 3
    }


</style>
<link rel="stylesheet" href="/plugins/bootstrap-fileinput/css/fileinput.min.css" type="text/css">

<span hidden="true" id = "csc_url">${(CSC_URL)!}</span>
<div class="modal-content" id="acrossImg" style="display: none;" >
    <div class="modal-body">
        <img  src="${(OFC_WEB_URL)!}/templates/across.png">
    </div>
</div>
<div class="modal-content" id="broadwiseImg" style="display: none;">
    <div class="modal-body">
        <img   src="${(OFC_WEB_URL)!}/templates/boradwise.png">
    </div>
</div>
<!--goods&Consigee-->
<div class="adoptModal " id="goodsAndConsigneeDiv" style="display: none;">
    <div class="modal-body">
        <div class="bootbox-body">
            <form class="form-horizontal" role="form">
                <div class="form-group">
                    <label class="control-label col-sm-1 no-padding-right" for="name">货品编码</label>
                    <div class="col-sm-3">
                        <div class="clearfix">
                            <input id="goodsIndexDivHidden" type="hidden"/>
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
                            <input  id = "specificationDiv" name="" type="text" onkeyup="this.value=this.value.replace(/(^\s*)|(\s*$)/g, '')"  readonly="readonly" style="color: black" class="form-control input-sm" placeholder="" aria-controls="dynamic-table">
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

                <table id="dynamic-table" style="float: left;width: 30%;margin-left:85px;" class="table table-striped table-bordered table-hover dataTable no-footer" role="grid" aria-describedby="dynamic-table_info">
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
   <#-- <div class="form-group" >
        <div class="modal-footer"><span id="goodsAndConsigneeDivNoneBottom" style="cursor:pointer"><button  data-bb-handler="cancel" type="button" class="btn btn-default">关闭</button></span></div>
    </div>-->
</div>

<div class="adoptModal" id="errorExcelImport" class="bootbox modal fade in" tabindex="-1" role="dialog" style="display: none;"
     aria-hidden="false">
    <#--<div class="modal-dialog">-->
        <div>
           <#-- <div class="modal-header">
                <button type="button" class="bootbox-close-button close" id="errorExcelImportCloseBtn">×</button>
                <h4 class="w-font">提示</h4></div>-->
            <div class="modal-body">
                <div class="bootbox-body">
                    <div class="form-group">
                         导入源数据中收货方名称与货品档案在系统中不存在,您可以进行批量创建:
                    </div>
                    <div class="form-group">
                        创建成功后请重新加载Excel
                    </div>

                </div>
            </div>
          <div class="modal-footer">
            <div style="float:right;">
              <button id="errorExcelImportEEBtn" data-bb-handler="confirm" type="button" class="btn btn-white btn-info btn-bold btn-interval" style="margin-bottom:0;">收货方档案</button>
              <button id="errorExcelImportGoodsBtn" data-bb-handler="confirm" type="button" class="btn btn-white btn-info btn-bold btn-interval">货品档案</button>
            </div>
          </div>
       <#-- &lt;#&ndash; <div class="col-xs-3 tktp-1">
             <button id="errorExcelImportGoodsBtn" data-bb-handler="confirm" type="button" class="btn btn-white btn-info btn-bold btn-interval">货品档案</button>
         </div>&ndash;&gt;
        -->
        </div>
   <#-- </div>-->
</div>
<div class="col-sm-12">
    <form class="form-horizontal" role="form">
        <div class="form-group">
            <label class="control-label col-label no-padding-right" for="name"></label>
            <div class="col-xs-3">
                <input type="button" id="templatesConfig"  name="templatesConfig"  value="模板映射配置" style="display:none;" disabled="disabled" />
                <label for="templatesConfig" class="btn btn-white btn-info btn-bold "  style="top:10px;border-color:#999;color:#666 !important;cursor:default">模板映射配置</label>
            </div>
        </div>
        <div class="form-group" style="margin-top:20px;">
            <label class="control-label col-label no-padding-right" for="name">下载模板</label>
            <div class="col-xs-6">
                <table id="dynamic-table" class="table table-striped table-bordered table-hover dataTable no-footer" role="grid" aria-describedby="dynamic-table_info">
                    <thead>
                    <tr role="row">
                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">序号</th>
                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">模板类型</th>
                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">模板名称</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td>1</td>
                        <td>交叉</td>
                        <td><a  id="acrossA" href="${(OFC_WEB_URL)!}/templates/template_for_cp.xlsx">批量下单导入模版_商超配送(点击下载)</a></td>
                    </tr>
                    <tr>
                        <td>2</td>
                        <td>明细列表</td>
                        <td><a  id="broadwiseA" href="${(OFC_WEB_URL)!}/templates/template_for_cp_orderlist.xlsx">订单批量导入_明细列表_模板(点击下载)</a></td>
                    </tr>
                    </tbody>
                </table>
                    <#--<a href="${(OFC_URL)!}/templates/template_for_cp.xlsx">批量下单导入模版_商超配送(点击下载)</a>-->
                    <p style="color: red">(提示:必须与模版中的列名保持一致，货品信息与收货方信息必须在基本信息中维护)</p>

                <input id="historyUrl" value="${historyUrl!""}" hidden/>
                <input id="customerCode" value="${customerCode!""}" hidden/>
                <input id="custName" value="${custName!""}" hidden/>
                <input id="cookieKey" value="${cookieKey!""}" hidden/>
            </div>
        </div>
        <div class="form-group">
            <label class="control-label col-label no-padding-right" for="name">模板类型</label>
            <div class="col-xs-3">
               <#-- <input id="templatesTypeAcross" value="MODEL_TYPE_ACROSS" name="templatesType" checked type="radio"/>交叉
                <input id="templatesTypeBoradwise" value="MODEL_TYPE_BORADWISE" name="templatesType" type="radio"/>明细列表-->
                <div class="radio y-float">
                    <label style="width:56px;padding-left:10px;">
                        <input id="templatesTypeAcross" value="MODEL_TYPE_ACROSS" name="templatesType" checked="checked" type="radio" class="ace"/>
                        <span class="lbl">交叉</span>
                    </label>
                </div>
                <div class="radio y-float">
                    <label>
                        <input id="templatesTypeBoradwise" value="MODEL_TYPE_BORADWISE" name="templatesType" type="radio" class="ace"/>
                        <span class="lbl">明细列表</span>
                    </label>
                </div>
            </div>

        </div>
        <div class="form-group">
            <label class="control-label col-label no-padding-right" for="name">模板映射</label>
            <div class="col-xs-3">
                <select class="col-xs-12 chosen-select" id="templatesMapping" >
                    <option value="standard">标准</option>
                </select>
            </div>
        </div>
        <div class="form-group">
            <label class="control-label col-label no-padding-right" for="name">上传文件</label>
            <div class="col-xs-3 y-float">
                <span hidden="true" id = "ofc_url">${(OFC_URL)!}</span>
                <span hidden="true" id = "ofc_web_url">${(OFC_WEB_URL)!}</span>
                <span hidden="true" id = "csc_url_local">${(CSC_URL_LOCAL)!}</span>
                <input id = "uploadFileShow" name="" type="text"  readonly class="col-xs-12 form-control input-sm " aria-controls="dynamic-table">
            </div>
           <#-- <form method="POST" name="uploadFileForm" id="uploadFileForm" role="form" &lt;#&ndash;enctype="multipart/form-data"&ndash;&gt; >-->
            <div class="y-float">
                <p class="y-float">
                    <input type="file" id="uploadFile" name="uploadFile" style="display:none;" />
                    <label for="uploadFile" class="btn btn-white btn-info btn-bold ">选择文件</label>
                </p>
                <p class="y-float padding-12">
                    <input type="button" id="uploadFileInput" name="uploadFileInput"  value="上传" style="display:none;"/>
                    <label for="uploadFileInput" class="btn btn-primary" style="padding:2px 12px;">上传</label>
                </p>

            </div>
            <#--</form>-->

            <#-- <div class="col-xs-6 y-float"></div>-->
        </div>
        <div class="form-group">
            <label class="control-label col-label no-padding-right" for="name">Sheet页</label>
            <div class="col-xs-3">
                <select class="col-xs-12 chosen-select" id="uploadExcelSheet">

                </select>
            </div>
            <button id="loadSheetAndCheckBtn" data-bb-handler="confirm" type="button" class="btn btn-white btn-info btn-bold">加载</button>
        </div>
    </form>
</div>

<div id="errorMsgDiv" hidden>
    <table id="dynamic-table" class="table table-striped table-bordered table-hover dataTable no-footer" role="grid" aria-describedby="dynamic-table_info">
        <thead>
        <tr role="row">
            <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">错误信息</th>
        </thead>
        <tbody id="errorMsgTbody">
        </tbody>
    </table>
</div>


<div id="goodsListDiv">

    <div class="col-sm-12">
        <div class="tabbable" style="margin:20px 0;">
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

                    <table id="orderGoodsListTable" class="table table-striped table-bordered table-hover dataTable no-footer bg-1" role="grid"
                           aria-describedby="dynamic-table_info">
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
                    <table id="consigneeListTable" class="table table-striped table-bordered table-hover dataTable no-footer bg-1" role="grid"
                           aria-describedby="dynamic-table_info">
                        <thead>
                        <tr role="row">
                            <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1"
                                aria-label="Domain: activate to sort column ascending">收货方名称
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
    <div class="modal-footer" style="text-align:left;margin-left:-2px;">
        <button id="excelImportEnter" data-bb-handler="confirm" type="button" class="btn btn-primary">确认导入</button>
        <span id="ExcelNoneBottom" style="cursor:pointer"><button id="ExcelNoneBtnBottom"  data-bb-handler="cancel" type="button" class="btn btn-default">不导入</button></span>
    </div>

</div>

<#--
<form action="/index#/open/csc/batchimport/toMaintainBatchGoodsImportPage" id="toMaintainBatchGoodsImportPage" target="_blank" method="post">
    <textarea rows="30" cols="30" id="goodsJsonStr" name="goodsJsonStr"></textarea>
</form>
<form action="/index#/open/csc/batchimport/toMaintainBatchCustomerImportPage" id="toMaintainBatchCustomerImportPage" target="_blank" method="post">
    <textarea rows="30" cols="30" id="cscContantAndCompanyInportDtos" name="cscContantAndCompanyInportDtos"></textarea>
</form>-->
<script type="text/javascript">
    var scripts = [null,
        "/plugins/bootstrap-fileinput/js/fileinput.min.js",
        "/plugins/bootstrap-fileinput/js/locales/zh.js",
        null];
    $(".page-content-area").ace_ajax("loadScripts", scripts, function () {
        $(document).ready(main);
        $('.chosen-select').chosen({allow_single_deselect: true});
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
        initChosen();
    }

    var ofc_url = $("#ofc_url").html();
    var csc_url = $("#csc_url").html();
    var ofc_web_url = $("#ofc_web_url").html();
    var csc_url_local = $("#csc_url_local").html();
    var cookieKey = $("#cookieKey").val();
    $("#ExcelNoneBottom").click(function () {
        var historyUrl = $("#historyUrl").val();
        var url = '';
        if(!StringUtil.isEmpty(historyUrl) && 'operation_distributing' == historyUrl){
            url = "/ofc/operationDistributing";
            xescm.common.loadPage(url);
        }
    })

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
    function goodsAndConsignee(obj){

       // $("#goodsAndConsigneeDiv").fadeIn(0);

        //显示货品信息
        var goodsIndex = $(obj).parent().parent().children().eq(1).text();//000
        var goodsCode = $(obj).parent().parent().children().eq(2).text();
        var useGoodsCode = goodsCode;
        goodsCode = goodsCode.split('@')[0];
        var goodsName = $(obj).parent().parent().children().eq(3).text();
        var specification = $(obj).parent().parent().children().eq(4).text();
        var unit = $(obj).parent().parent().children().eq(5).text();
        $("#goodsIndexDivHidden").val(goodsIndex);
        $("#goodsCodeDiv").val(goodsCode);
        $("#goodsNameDiv").val(goodsName);
        $("#specificationDiv").val(specification);
        $("#unitDiv").val(unit);

        //最后提交订单的时候做个校验, 如果货品的需求数量为0就提示!
        //显示收货人信息
        var consignorout = "";

        $("#consigneeInfoListDiv").find("tr").each(function(index){//00000
            var tdArr = $(this).children();
            var consigneeName = tdArr.eq(0).text();//
            var consigneeContactName = tdArr.eq(1).text();//
            var consigneeType = tdArr.eq(4).text();//
            var consigneeCode = tdArr.eq(5).text();
            var consigneeContactCode = tdArr.eq(6).text();
            var mapKey = useGoodsCode + "@" + goodsIndex;
            var num = "0";


            if(undefined != viewMap.get(mapKey)){

                var preGoodsAndConsigneeJsonMsg = viewMap.get(mapKey)[1];
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
            consignorout =consignorout + "<td><input readonly value='"+num+"' style='border:1px solid #c3c3c3;' onpause='return false' onkeypress='onlyNumber(this)' onkeyup='onlyNumber(this)'/></td>";
            consignorout =consignorout + "<td style='display:none'>"+consigneeCode+"</td>";
            consignorout =consignorout + "<td style='display:none'>"+consigneeContactCode+"</td>";
            consignorout =consignorout + "</tr>";
        });
        $("#goodsAndConsigneeTbody").html(consignorout);
      confirmBlue();


    }//
    var viewMap = null;
    var loadSheetTag = false;
    var consigneeList = null;
    var batchconsingeeKey = null;
    var batchgoodsKey = '';
    var errorEEsNum = 0;
    var errorGoodsNum = 0;


    function uploadFileChange(target) {

    }
    $(function () {

        $("#templatesConfig").click(function () {
            var custCode = $("#customerCode").val();
            var custName = $("#custName").val();
            var historyUrl = $("#historyUrl").val();
            var url = '';
            if(!StringUtil.isEmpty(historyUrl) && 'operation_distributing' == historyUrl){
                url = "/ofc/distributing/toTemplatesList/" + custCode + "/" + historyUrl;
            }
            xescm.common.loadPage(url);
        })

        document.getElementById("acrossA").onmouseover = function(){
            document.getElementById("acrossImg").style.display="block";
            document.getElementById("broadwiseImg").style.display="none";
        }
        document.getElementById("broadwiseA").onmouseover = function(){
            document.getElementById("acrossImg").style.display="none";
            document.getElementById("broadwiseImg").style.display="block";
        }
        document.getElementById("acrossA").onmouseout = function(){
            document.getElementById("broadwiseImg").style.display="none";
            document.getElementById("acrossImg").style.display="none";
        }
        document.getElementById("broadwiseA").onmouseout = function(){
            document.getElementById("broadwiseImg").style.display="none";
            document.getElementById("acrossImg").style.display="none";
        }
        var file;
        var fileName;
        var uploadFileTag = false;
        $("#uploadFile").change(function () {
            $("#uploadExcelSheet").html("");
            //清空错误和正确的加载项
            $("#errorMsgTbody").html("");
            $("#goodsInfoListDiv").html("");
            $("#consigneeInfoListDiv").html("");
            $("#goodsListDiv").show();
            $("#errorMsgDiv").hide();

            file = this.files[0];
            var fileSize = file.size;
            if(fileSize / 1024 > 1000){
                alert("附件大小不能大于1M");
                this.value = "";
                $("#uploadFileShow").val("");
                uploadFileTag = false;
                return;
            }else{
                fileName = $("#uploadFile").val();
                var suffix = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
                if(suffix != "xls" && suffix != "xlsx"){
                    alert("请选择excel格式文件上传")
                    this.value = "";
                    $("#uploadFileShow").val("");
                    uploadFileTag = false;
                    return;
                }else{
                    uploadFileTag = true;
                    $("#uploadFileShow").val(fileName);
                }
            }

        })

        $("#uploadFileInput").click(function () {//上传

            if(uploadFileTag){

                var formData = new FormData();
                var customerCode = $("#customerCode").val();
                formData.append('file',file);
                formData.append('fileName',fileName);
                formData.append('customerCode',customerCode);
                var url = ofc_web_url + '/ofc/distributing/fileUploadAndCheck';

                $.ajax({
                    url: url,
                    type: 'POST',
                    data: formData,
                    async: false,
                    cache: false,
                    contentType: false,
                    processData: false,
                    success: function (result) {
                        if (result == undefined || result == null) {
                            layer.msg("HTTP请求无数据返回", {
                                icon: 1
                            });
                        } else if (result.code == "200") {
                            //加载用户的Sheet页
                            var sheetMsg = eval(result.result);
                            $("#uploadExcelSheet option").remove();
                            $.each(sheetMsg,function (index,sheet) {
                                var sh = sheet.split("@");
                                if("active" == sh[1]){
                                    $("#uploadExcelSheet").append("<option selected value='" + index + "'>" + sh[0] + "</option>").trigger("chosen:updated");
                                }else{
                                    $("#uploadExcelSheet").append("<option value='" + index + "'>" + sh[0] + "</option>").trigger("chosen:updated");
                                }
                            })
                            layer.msg(result.message, {
                                skin: 'layui-layer-molv',
                                icon: 1,
                                time:500
                            });
                        } else {
                            layer.msg(result.message, {
                                skin: 'layui-layer-molv',
                                icon: 5,
                                time:500
                            });
                        }
                    },
                    error: function (data) {
                        alert("操作失败");
                    }
                });
            }else {
                alert('请先选择文件!')
            }
        })

        $("#loadSheetAndCheckBtn").click(function () {//加载
//            viewMap = null;
            viewMap = new HashMap();
//            consigneeList = null;
            consigneeList = [];

            if($("#uploadExcelSheet option").size() > 0){

                loadSheetTag = true;

                var sheetNum = $("#uploadExcelSheet").val();
                var formData = new FormData();
                var customerCode = $("#customerCode").val();
                var templatesMapping = $("#templatesMapping").val();
                var templatesType = $('input[name="templatesType"]:checked ').val();
                formData.append('file',file);
                formData.append('fileName',fileName);
                formData.append('customerCode',customerCode);
                formData.append('sheetNum',sheetNum);
                formData.append('templatesType',templatesType);
                formData.append('templatesMapping',templatesMapping);
                var url = ofc_web_url + '/ofc/distributing/excelCheckBySheet';
                $.ajax({
                    url: url,
                    type: 'POST',
                    data: formData,
                    async: false,
                    cache: false,
                    contentType: false,
                    processData: false,
                    success: function (result) {

                        if (result == undefined || result == null) {
                            layer.msg("HTTP请求无数据返回", {
                                icon: 1
                            });
                        } else if (result.code == "200") {
                            $("#goodsInfoListDiv").html("");
                            $("#consigneeInfoListDiv").html("");
                            $("#goodsListDiv").show();
                            $("#errorMsgDiv").hide();
                            //如果校验成功!
                            layer.msg(result.message, {
                                skin: 'layui-layer-molv',
                                icon: 1,
                                time:500
                            });
                            var resultMap =  JSON.parse(result.result);

                            var consigneeTag = true;
                           // var goodsAndEETag = true;
                            var indexView = 0;
                            for(var key in resultMap){
                                indexView += 1;

                                var resultMapValue = resultMap[key]; //一条货品记录
                                var viewMapValue = [];
                                var consigeeMsg = {};
                                for(var index = 0; index < resultMapValue.length; index ++){
                                    var data = resultMapValue[index];

                                    if(index == 0){//货品详细信息
                                        viewMapValue[0] = data;
                                        //顺便在页面中进行展示
                                        var goodsCode = data.goodsCode;
                                        var realGoodsCode0 = goodsCode.split('@')[0];
                                        var realGoodsCode1 = '';
                                        if(goodsCode.split('@').length > 1){
                                            realGoodsCode1 = '@' + goodsCode.split('@')[1];
                                        }

                                        $("#goodsInfoListDiv").append("<tr class='odd' role='row'>" +
                                                "<td><a onclick='goodsAndConsignee(this)' class='blue'>查看</a></td>" +
                                                "<td class='center'>" + indexView + "</td>" +
                                                "<td>" + realGoodsCode0 + "<textarea hidden>" + realGoodsCode1 + "</textarea>" + "</td>" +
                                                "<td>" + StringUtil.nullToEmpty(data.goodsName) + "</td>" +
                                                "<td>" + StringUtil.nullToEmpty(data.specification) + "</td>" +
                                                "<td>" + StringUtil.nullToEmpty(data.unit) + "</td>" +
                                                "<td>" + StringUtil.nullToEmpty(data.unitPrice) + "</td>" +
                                                "<td>" + StringUtil.nullToEmpty(data.goodsAmount) + "</td>" +
                                                "</tr>");
//                                    }else if(index % 3 == 1 && goodsAndEETag){//收货人和货品需求量
                                    }else if(index % 3 == 1){//收货人和货品需求量
                                        for(var inkey in data){
                                            consigeeMsg[inkey] = data[inkey];
                                        }
                                    }else if(index % 3 == 2 && consigneeTag){//收货人详细信息
                                        consigneeList.push(data);
                                        $("#consigneeInfoListDiv").append("<tr class='odd' role='row'>" +
                                                "<td>" + StringUtil.nullToEmpty(data.contactCompanyName) + "</td>" +
                                                "<td>" + StringUtil.nullToEmpty(data.contactName) + "</td>" +
                                                "<td>" + StringUtil.nullToEmpty(data.phone) + "</td>" +
                                                "<td>" + StringUtil.nullToEmpty(data.detailAddress) + "</td>" +
                                                "<td style='display:none'>" + StringUtil.nullToEmpty(data.type) + "</td>" +
                                                "<td style='display:none'>" + StringUtil.nullToEmpty(data.contactCompanySerialNo) + "</td>" +
                                                "<td style='display:none'>" + StringUtil.nullToEmpty(data.contactSerialNo) + "</td>" +
                                                "</tr>");
                                    }
                                }
                                consigneeTag = false;
                                viewMapValue[1] = consigeeMsg;
                                viewMap.put(key,viewMapValue);
                            }


                        } else if (result.code == "500") {
                            //如果校验失败!
                            layer.msg(result.message, {
                                skin: 'layui-layer-molv',
                                icon: 5,
                                time:500
                            });
                            $("#goodsListDiv").hide();
                            $("#errorMsgDiv").show();
                            $("#errorMsgTbody").html("");
                            var errorMsgList = result.result.xlsErrorMsg;
                            $.each(errorMsgList,function (index, errorMsg) {
                                $("#errorMsgTbody").append("<tr class='odd' role='row'><td>" + (index + 1) + ". " + errorMsg + "</td></tr>");
                            })
                            var cscContantAndCompanyInportDtoList = result.result.cscContantAndCompanyInportDtoList;
                            var cscGoodsImportDtoList = result.result.cscGoodsImportDtoList;
                            if(null != cscContantAndCompanyInportDtoList){
                                errorEEsNum = cscContantAndCompanyInportDtoList.length;
                            }
                            if(null != cscGoodsImportDtoList){
                                errorGoodsNum = cscGoodsImportDtoList.length;
                            }
                            if(errorEEsNum > 0 || errorGoodsNum > 0){
                               // $("#errorExcelImport").show();
                                confirm();
                                if(errorGoodsNum > 0){
                                    batchgoodsKey = result.result.batchgoodsKey;
                                }
                                if (errorEEsNum > 0){
                                    batchconsingeeKey = result.result.batchconsingeeKey;
                                }

                            }

                        } else {
                            layer.msg(result.message, {
                                skin: 'layui-layer-molv',
                                icon: 5,
                                time:500
                            });


                        }
                    },
                    error: function (data) {
                        alert("操作失败");
                    }
                });
            }else{
                alert('请先上传文件!')
            }
        })

        $("#goodsAndConsigneeDivNoneBottom").click(function () {
            $("#goodsAndConsigneeDiv").fadeOut(0);
        });

        $("#excelImportEnter").click(function () {
            var consigneeNum = $("#consigneeInfoListDiv tr").size();
            var goodsNum = $("#goodsInfoListDiv tr").size();
            if(loadSheetTag){
                if(consigneeNum < 1){
                    alert('请先上传Excel导入数据，再加载后执行导入！')
                }else if(goodsNum < 0){
                    alert('请先上传Excel导入数据，再加载后执行导入！');
                }else{
                    //所有校验通过
                    layer.confirm('请您再次确认订单信息,我们将把订单信息导入下单界面!', {
                        skin : 'layui-layer-molv',
                        icon : 3,
                        title : '确认操作'
                    }, function(index){

                        var excelImportTag = "confirm";
                        var customerCode = $("#customerCode").val();
                        var url = "/ofc/distributing/excelImportConfirm/" + excelImportTag + "/" + customerCode  + "/" + cookieKey;
                        xescm.common.loadPage(url);
                        layer.close(index);
                    }, function(index){
                        layer.close(index);
                    });
                }
            }else {
                alert('请先加载Sheet页!')
            }
        })

        $("#ExcelNoneBtnBottom").click(function () {
            var excelImportTag = "cancel";
            var customerCode = $("#customerCode").val();
            var url = "/ofc/distributing/excelImportConfirm/" + excelImportTag + "/" + customerCode + "/" + cookieKey;
            xescm.common.loadPage(url);
        })
        $("#errorExcelImportCloseBtn").click(function () {
            $("#errorExcelImport").hide()
        })


    })
    $("#errorExcelImportEEBtn").click(function(){//toMaintainBatchCustomerImportPage
        if(errorEEsNum < 1){
            alert("您无需添加收货人")
            return;
        }
        var url = "/csc/batchimport/toMaintainBatchCustomerImportPage/" + batchconsingeeKey;
        var html = window.location.href;
        var index = html.indexOf("/index#");
        window.open(html.substring(0,index) + "/index#" + url);

       // $("#errorExcelImport").hide();
        layer.closeAll('page');
    })
    $("#errorExcelImportGoodsBtn").click(function () {
        if(errorGoodsNum < 1){
            alert("您无需添加货品")
            return;
        }

        var url = "/csc/batchimport/toMaintainBatchGoodsImportPage/" + batchgoodsKey;
        var html = window.location.href;
        var index = html.indexOf("/index#");
        window.open(html.substring(0,index) + "/index#" + url);
       // $("#errorExcelImport").hide();
        layer.closeAll('page');
    })
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

    //加载弹窗
   // $("#loadSheetAndCheckBtn").click(function() {confirm();});
    function confirm() {
      layer.open({
        btn:['取消'],
        scrollbar:false,
        type: 1,
        area: ['412px', '250px'],
        content: $('#errorExcelImport'), //这里content是一个DOM
        title: '提示',
        no: function (adoptModalIndex) {
          layer.close(adoptModalIndex);
          return false;
        }

      });
    }
    function confirmBlue() {
      layer.open({
        btn:['关闭'],
        scrollbar:false,
        type: 1,
        area:'946px',
        content: $('#goodsAndConsigneeDiv'), //这里content是一个DOM
        title: '货品明细',
        cancel: function (adoptModalIndex) {
          layer.close(adoptModalIndex);
          return false;
        }
      });
    }
</script>