<title>入库开单_批量导入</title>
<div id="app">
    <div class="list-mian-01">
        <el-form :model="templateBatchIn"  label-width="100px" class="demo-ruleForm">
            <div class="xe-block" >
                <el-form-item label="客户名称"  class="xe-col-3" requird>
                    <el-input v-model="templateBatchIn.custName" id="custName" placeholder="请输入内容"></el-input>
                <#--<el-input  id="custCode" ></el-input>-->
                </el-form-item>
            </div>
            <div class="xe-block">
                <el-form-item label="模板名称"   class="xe-col-3" requird>
                    <el-select placeholder="请选择" v-model="templateBatchIn.templateName">
                        <el-option  v-for="item in templateNameList" :label="item.label" :value="item.value"></el-option>
                    </el-select>
                </el-form-item>
            </div>
            <div class="xe-block">
                <el-form-item label="上传文件"  class="xe-col-3">
                    <el-input v-model="templateBatchIn.uploadFile"  placeholder="请输入内容"    value=""></el-input>
                    <#--<el-button type="primary" v-on:click="fileUpload">保存</el-button>-->
                    <#--<el-button @click="fileUpload">上传</el-button>-->
                </el-form-item>
            </div>
            <div class="xe-block">
                <el-form-item label="Sheet页"  class="xe-col-3">
                    <el-select placeholder="请选择" v-model="templateBatchIn.pageSheet">
                        <el-option  v-for="item in pageSheetList" :label="item.label" :value="item.value"></el-option>
                    </el-select>
                    <el-button @click="excelLoad">加载</el-button>
                </el-form-item>
            </div>

        </el-form>
    </div>


    <el-upload    :action="uploadAction" type="drag" :accept="fileTypeAccept" :on-progress="uploading" :multiple="false" :before-upload="beforeUpload" :on-preview="handlePreview" :on-remove="handleRemove" :on-success="handleSuccess" :on-error="handleError" <#--:default-file-list="fileList"-->>
        <i class="el-icon-upload"></i>
        <div class="el-dragger__text">将文件拖到此处，或<em>点击上传</em></div>
        <div class="el-upload__tip" slot="tip">只能上传xls/xlsx文件，且不超过500kb</div>
    </el-upload>
</div>
<script type="text/javascript">

    var Main = {
        data() {
            return {
                fileList: [],
                excel:'',
                fileTypeAccept:'.xls',
                uploadAction:'${OFC_WEB_URL!}/ofc/storage_template/batch_in_upload',
                authResDto:'',
                templateBatchIn:{
                    custName:'',
                    templateName:'',
                    uploadFile:'',
                    pageSheet:''
                },
                pageSheetList:[],
                templateNameList:[]
            };
        },
        methods: {
            handleRemove(file, fileList) {
                var vm = this;
                vm.templateBatchIn = {
                    pageSheet:''
                }
            },
            handlePreview(file) {
                console.log(file);
            },
            handleSuccess(response, file, fileList) {
                debugger
                var vm = this;
                if(response.code == 500) {
                    layer.msg("文件格式不正确或服务器出错!请稍后重试!");
                    fileList = [];
                }else if(response.code == 200) {
                    vm.excel = file;
                    //加载所有sheet页
                    var pageSheetList = vm.pageSheetList;
                    $.each(response.result,function (index,item) {
                        var pageSheet = {};
                        var sh = item.split("@");
                        pageSheet.label = sh[0];
                        pageSheet.value = index;
                        if("active" == sh[1]){
                            vm.templateBatchIn = {
                                pageSheet:index
                            }
                        }
                        pageSheetList.push(pageSheet);
                    });

                }
            },
            handleError(err, response, file) {

            },
            beforeUpload(file){
                var vm = this;
                var fileList = vm.fileList;
                if(fileList.length > 0){
                    layer.msg("只允许上传一个文件!");
                    return false;
                }
            },
            uploading(event, file, fileList){
                //文件大小限制
                var fileSize = file.size;


            },
            fileUpload() {
            },
            excelLoad() {
                var vm = this;
                if(vm.pageSheetList.length == 0){
                    layer.msg("请先上传文件!");
                    return;
                }
                debugger
                var sheetNum = vm.templateBatchIn.pageSheet;
                var formData = new FormData();
                var customerCode = vm.templateBatchIn.custName;
                var templatesMapping = vm.templateBatchIn.templateName;
                vm.excel.status = 'ready';
                formData.append('file',vm.excel.url);
                formData.append('customerCode',customerCode);
                formData.append('sheetNum',sheetNum);
                formData.append('templatesMapping',templatesMapping);
                var url = '${OFC_WEB_URL!}/ofc/storage_template/batch_in_load';
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

                        } else if (result.code == "500") {

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
            }
        }
    }
    var Ctor = Vue.extend(Main)
    new Ctor().$mount('#app')
</script>