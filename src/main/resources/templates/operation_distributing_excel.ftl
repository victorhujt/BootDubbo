<title>城配开单Excel导入</title>
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
                <#--<button id="uploadFileBtn" data-bb-handler="confirm" type="file" class="btn btn-white btn-info btn-bold btn-interval">浏览</button>
                <button id="uploadFileBtn" data-bb-handler="confirm" type="submit" class="btn btn-white btn-info btn-bold btn-interval">上传</button>-->
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
    <div class="modal-header"><span id="goodsListDivNoneTop" style="cursor:pointer"><button type="button" id="" style="cursor:pointer" class="bootbox-close-button close" data-dismiss="modal" aria-hidden="true">×</button></span>
        <h4 class="modal-title" style="font-size: 14px">货品列表</h4></div>
    <div class="modal-body">
        <div class="bootbox-body">
            <form class="bootbox-form">

                    <table id="orderGoodsListTable" class="table table-striped table-bordered table-hover dataTable no-footer" role="grid"
                           aria-describedby="dynamic-table_info">
                        <thead>
                        <tr role="row">
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
                                aria-label="Clicks: activate to sort column ascending">发货数量
                            </th>

                        </thead>
                        <tbody id="goodsSelectListTbody"></tbody>

                    </table>
            </form>
        </div>
    </div>
    <div class="modal-footer"><button id="goodsEnter" data-bb-handler="confirm" type="button" class="btn btn-primary">确认导入</button><span id="ExcelNoneBottom" style="cursor:pointer"><button id="ExcelNoneBtnBottom"  data-bb-handler="cancel" type="button" class="btn btn-default">不导入</button></span></div>
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

    var file;
    var fileName;
    $(function () {

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
                    debugger
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
                    /*if (result == undefined || result == null) {
                        layer.msg("HTTP请求无数据返回", {
                            icon: 1
                        });
                    } else if (result.code == "200") {
                        //将校验结果进行展示

                        layer.msg(result.message, {
                            skin: 'layui-layer-molv',
                            icon: 1
                        });
                    } else if (result.code == "500") {
                        //将校验结果进行展示
                        layer.msg(result.message, {
                            skin: 'layui-layer-molv',
                            icon: 5
                        });
                    } else {
                        layer.msg(result.message, {
                            skin: 'layui-layer-molv',
                            icon: 5
                        });


                    }*/
                },
                error: function (data) {
                    alert("操作失败");
                }
            });
        })
    })

</script>