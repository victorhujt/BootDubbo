<head>
    <title>城配开单Excel导入</title>
    <style type="text/css">
        #goodsAndConsigneeDiv{
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

    </style>
    <link rel="stylesheet" href="/plugins/bootstrap-fileinput/css/fileinput.min.css" type="text/css">
</head>
<!--goods&Consigee-->
<div class="modal-content" id="goodsAndConsigneeDiv" style="display: none;">
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
        <div class="modal-footer"><span id="goodsAndConsigneeDivNoneBottom" style="cursor:pointer"><button  data-bb-handler="cancel" type="button" class="btn btn-default">关闭</button></span></div>
    </div>
</div>
<div class="col-sm-12">
    <form class="form-horizontal" role="form">
        <div class="form-group">
            <label class="control-label col-label no-padding-right" for="name">下载模板</label>
            <div class="col-xs-3">
                    <a href="${(OFC_URL)!}/ofc/distributing/downloadTemplate">批量下单导入模版_商超配送(点击下载)</a>
                    <#--<a href="${(OFC_WEB_URL)!}/open/downloadTemplate">批量下单导入模版_商超配送(点击下载)</a>-->
                    <#--<a href="javascript:downloadTemplate()">批量下单导入模版_商超配送(点击下载)</a>-->
                    <p style="color: red">(提示:必须与模版中的列名保持一致，货品信息与收货方信息必须在基本信息中维护)</p>

                <input id="historyUrl" value="${historyUrl!""}" hidden/>
                <input id="custId" value="${custId!""}" hidden/>
                <input id="custName" value="${custName!""}" hidden/>
            </div>
        </div>
        <div class="form-group">
            <label class="control-label col-label no-padding-right" for="name">上传文件</label>
            <div class="col-xs-3">
                <span hidden="true" id = "ofc_url">${(OFC_URL)!}</span>
                <span hidden="true" id = "ofc_web_url">${(OFC_WEB_URL)!}</span>
                <input id = "uploadFileShow" name="" type="text"  readonly class="col-xs-12 form-control input-sm " aria-controls="dynamic-table">
            </div>
            <div class="col-xs-3">
                <form method="POST" name="uploadFileForm" id="uploadFileForm" role="form" <#--enctype="multipart/form-data"--> >
                    <p><input type="file" id="uploadFile" name="uploadFile" /></p>
                    <p><input type="button" id="uploadFileInput"  value="上传"/></p>
                </form>
            </div>
        </div>
        <div class="form-group">
            <label class="control-label col-label no-padding-right" for="name">Sheet页</label>
            <div class="col-xs-3">
                    <select class="col-xs-12" id="uploadExcelSheet">
                    </select>
            </div>
            <div class="col-xs-3">
                <button id="loadSheetAndCheckBtn" data-bb-handler="confirm" type="button" class="btn btn-white btn-info btn-bold btn-interval">加载</button>
            </div>
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

                    <table id="orderGoodsListTable" class="table table-striped table-bordered table-hover dataTable no-footer bg-1" role="grid"
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
    <div class="modal-footer"><button id="excelImportEnter" data-bb-handler="confirm" type="button" class="btn btn-primary">确认导入</button><span id="ExcelNoneBottom" style="cursor:pointer"><button id="ExcelNoneBtnBottom"  data-bb-handler="cancel" type="button" class="btn btn-default">不导入</button></span></div>

</div>


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

    }

    var ofc_url = $("#ofc_url").html();
    var ofc_web_url = $("#ofc_web_url").html();
    $("#ExcelNoneBottom").click(function () {
        var historyUrl = $("#historyUrl").val();
        console.log("0000"+historyUrl)
        xescm.common.loadPage(historyUrl);
    })
    function downloadTemplate(){
        var url = ofc_url + "/ofc/distributing/downloadTemplate";
        window.location.href = url;
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
    function goodsAndConsignee(obj){
        $("#goodsAndConsigneeDiv").fadeIn("slow");
        //显示货品信息
        var goodsIndex = $(obj).parent().parent().children().eq(1).text();//000
        var goodsCode = $(obj).parent().parent().children().eq(2).text();
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
            var mapKey = goodsCode + "@" + goodsIndex;
            var num = "0";

            if(undefined != viewMap.get(mapKey)){
                debugger
                var preGoodsAndConsigneeJsonMsg = viewMap.get(mapKey)[1];
                //preGoodsAndConsigneeJsonMsg = JSON.stringify(preGoodsAndConsigneeJsonMsg);
                var cadj = consigneeCode + "@" + consigneeContactCode;
                num = preGoodsAndConsigneeJsonMsg[cadj];
            }

            consignorout =consignorout + "<tr role='row' class='odd' align='center'>";
            if("1" == consigneeType){
                consignorout =consignorout + "<td>"+consigneeName+"</td>";
            }else if("2" == consigneeType){
                consignorout =consignorout + "<td>"+consigneeName+"-"+consigneeContactName+"</td>";
            }else{
                consignorout =consignorout + "<td>"+consigneeName+"</td>";
            }
            consignorout =consignorout + "<td><input readonly value='"+num+"' onpause='return false' onkeypress='onlyNumber(this)' onkeyup='onlyNumber(this)'/></td>";
            consignorout =consignorout + "<td style='display:none'>"+consigneeCode+"</td>";
            consignorout =consignorout + "<td style='display:none'>"+consigneeContactCode+"</td>";
            consignorout =consignorout + "</tr>";
        });
        $("#goodsAndConsigneeTbody").html(consignorout);


    }//
    var viewMap = new HashMap();
    var loadSheetTag = false;
    var consigneeList = [];

    function uploadFileChange(target) {

    }
    $(function () {
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
            debugger
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
                debugger
                var formData = new FormData();
                var custId = $("#custId").val();
                formData.append('file',file);
                formData.append('fileName',fileName);
                formData.append('custId',custId);
//                var url = ofc_url + '/ofc/distributing/fileUploadAndCheck';
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
                                    $("#uploadExcelSheet").append("<option selected value='" + index + "'>" + sh[0] + "</option>");
                                }else{
                                    $("#uploadExcelSheet").append("<option value='" + index + "'>" + sh[0] + "</option>");
                                }
                            })
                            layer.msg(result.message, {
                                skin: 'layui-layer-molv',
                                icon: 1
                            });
                        } else {
                            layer.msg(result.message, {
                                skin: 'layui-layer-molv',
                                icon: 5
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

            if($("#uploadExcelSheet option").size() > 0){

                loadSheetTag = true;

                var sheetNum = $("#uploadExcelSheet").val();
                var formData = new FormData();
                var custId = $("#custId").val();
                formData.append('file',file);
                formData.append('fileName',fileName);
                formData.append('custId',custId);
                formData.append('sheetNum',sheetNum);
//                var url = ofc_url + '/ofc/distributing/excelCheckBySheet';
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
                        debugger
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
                                icon: 1
                            });
                            var resultMap =  JSON.parse(result.result);

                            var consigneeTag = true;
                            var indexView = 0;
                            for(var key in resultMap){
                                indexView += 1;
                                debugger
                                var resultMapValue = resultMap[key]; //一条货品记录
                                var viewMapValue = [];
                                var consigeeMsg = {};
                                for(var index = 0; index < resultMapValue.length; index ++){
                                    var data = resultMapValue[index];

                                    if(index == 0){//货品详细信息
                                        viewMapValue[0] = data;
                                        //顺便在页面中进行展示
                                        $("#goodsInfoListDiv").append("<tr class='odd' role='row'>" +
                                                "<td><button type='button' onclick='goodsAndConsignee(this)' class='btn btn-minier btn-success'>查看</button></td>" +
                                                "<td>" + indexView + "</td>" +
                                                "<td>" + data.goodsCode + "</td>" +
                                                "<td>" + data.goodsName + "</td>" +
                                                "<td>" + data.specification + "</td>" +
                                                "<td>" + data.unit + "</td>" +
                                                "<td>" + data.goodsAmount + "</td>" +
                                                "</tr>");
                                    }else if(index % 3 == 1){//收货人和货品需求量
                                        for(var inkey in data){
                                            consigeeMsg[inkey] = data[inkey];
                                        }
                                    }else if(index % 3 == 2 && consigneeTag){//收货人详细信息
                                        consigneeList.push(data);
                                        $("#consigneeInfoListDiv").append("<tr class='odd' role='row'>" +
                                                "<td>" + data.contactCompanyName + "</td>" +
                                                "<td>" + data.contactName + "</td>" +
                                                "<td>" + data.phone + "</td>" +
                                                "<td>" + data.detailAddress + "</td>" +
                                                "<td style='display:none'>" + data.type + "</td>" +
                                                "<td style='display:none'>" + data.contactCompanyId + "</td>" +
                                                "<td style='display:none'>" + data.id + "</td>" +
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
                                icon: 5
                            });
                            $("#goodsListDiv").hide();
                            $("#errorMsgDiv").show();
                            $("#errorMsgTbody").html("");
                            var errorMsgList = result.result;
                            $.each(errorMsgList,function (index, errorMsg) {
                                $("#errorMsgTbody").append("<tr class='odd' role='row'><td>" + (index + 1) + ". " + errorMsg + "</td></tr>");
                            })
                        } else {
                            layer.msg(result.message, {
                                skin: 'layui-layer-molv',
                                icon: 5
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
            $("#goodsAndConsigneeDiv").fadeOut("slow");
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
                        debugger
                        var excelImportTag = "confirm";
                        var custId = $("#custId").val();
                        var custName = $("#custName").val();
                        var url = "/ofc/distributing/excelImportConfirm/" + excelImportTag + "/" + custId + "/" + custName;
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
            var custId = $("#custId").val();
            var url = "/ofc/distributing/excelImportConfirm/" + excelImportTag + "/" + custId;
            xescm.common.loadPage(url);
        })
    })

</script>