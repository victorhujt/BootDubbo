<title>城配开单Excel导入</title>
<div class="col-sm-12">
    <form class="form-horizontal" role="form">
        <div class="form-group">
            <label class="control-label col-label no-padding-right" for="name">下载模板</label>
            <div class="col-xs-3">
                    <a href="${(OFC_URL)!}/open/downloadTemplate">批量下单导入模版_商超配送(点击下载)</a>
                    <p style="color: red">(提示:必须与模版中的列名保持一致，货品信息与收货方信息必须在基本信息中维护)</p>

                <input id="historyUrl" value="${historyUrl!""}" hidden/>
                <input id="custId" value="${custId!""}" hidden/>
            </div>
        </div>
        <div class="form-group">
            <label class="control-label col-label no-padding-right" for="name">上传文件</label>
            <div class="col-xs-3">
                <span hidden="true" id = "ofc_url">${(OFC_URL)!}</span>
                <input id = "uploadFileShow" name="" type="text"  readonly class="col-xs-12 form-control input-sm " aria-controls="dynamic-table">
            </div>
            <div class="col-xs-3">
                <form method="POST" name="uploadFileForm" id="uploadFileForm" role="form" <#--enctype="multipart/form-data"--> >
                    <p><input type="file" id="uploadFile" multiple name="uploadFile" class="file-loading"/></p>
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
                    <span style="cursor:pointer" id="goodsListDivBlock"><button type="button" class="btn btn-info"  id="bootbox-confirm">添加货品</button></span>
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
                    <span style="cursor:pointer" id="consigneeListDivBlock"><button type="button" class="btn btn-info" style="" id="">添加收货方</button></span>
                    <span style="cursor:pointer" id="consigneeListConfirmDivBlock"><button type="button" class="btn btn-info qrshf" id="">确认收货方</button></span>
                    <span style="cursor:pointer" id="consigneeListClearDivBlock"><button type="button" class="btn btn-info" id="">重置收货方</button></span>
                    <table id="consigneeListTable" class="table table-striped table-bordered table-hover dataTable no-footer bg-1" role="grid"
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
    <div class="modal-footer"><button id="goodsEnter" data-bb-handler="confirm" type="button" class="btn btn-primary">确认导入</button><span id="ExcelNoneBottom" style="cursor:pointer"><button id="ExcelNoneBtnBottom"  data-bb-handler="cancel" type="button" class="btn btn-default">不导入</button></span></div>

</div>


<script type="text/javascript">
    var scripts = [null,
        sys.rootPath + "/plugins/bootstrap-fileinput/js/fileinput.min.js",
        sys.rootPath + "/plugins/bootstrap-fileinput/js/locales/zh.js",
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
    $("#ExcelNoneBottom").click(function () {
        var historyUrl = $("#historyUrl").val();
        console.log("0000"+historyUrl)
        xescm.common.loadPage(historyUrl);
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

    $(function () {
        var file;
        var fileName;
        var viewMap = new HashMap();

        $("#uploadFile").change(function () {
            debugger
            file = this.files[0];
            fileName = $("#uploadFile").val();
            $("#uploadFileShow").val(fileName);
        })

        $("#uploadFileInput").click(function () {
            debugger
            var formData = new FormData();
            var custId = $("#custId").val();
            formData.append('file',file);
            formData.append('fileName',fileName);
            formData.append('custId',custId);
            var url = ofc_url + '/ofc/distributing/fileUploadAndCheck';
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


        })

        $("#loadSheetAndCheckBtn").click(function () {
            var sheetNum = $("#uploadExcelSheet").val();
            var formData = new FormData();
            var custId = $("#custId").val();
            formData.append('file',file);
            formData.append('fileName',fileName);
            formData.append('custId',custId);
            formData.append('sheetNum',sheetNum);
            var url = ofc_url + '/ofc/distributing/excelCheckBySheet';
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
                        $("#goodsListDiv").show();
                        $("#errorMsgDiv").hide();
                        //如果校验成功!
                        layer.msg(result.message, {
                            skin: 'layui-layer-molv',
                            icon: 1
                        });
                        var resultMap =  JSON.parse(result.result);
                        var consigneeList = [];
                        var consigneeTag = true;
                        for(var key in resultMap){
                            debugger
                            var resultMapValue = resultMap[key]; //一条货品记录
                            var viewMapValue = [];
                            var consigeeMsg = {};
                            for(var index = 0; index < resultMapValue.length; index ++){
                                var data = resultMapValue[index];

                                if(index == 0){//货品信息
                                    viewMapValue[0] = data;
                                }else if(index % 3 == 1){//收货人和货品需求量
                                    for(var inkey in data){
                                        consigeeMsg[inkey] = data[inkey];
                                    }
                                }else if(index % 3 == 2 && consigneeTag){//收货人信息
                                    consigneeList.push(data);
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
        })
    })

</script>