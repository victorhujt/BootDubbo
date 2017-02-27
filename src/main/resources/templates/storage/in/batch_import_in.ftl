<link rel="stylesheet" href="/components/select2.v3/select2.min.css" />
<link rel="stylesheet" href="/components/select2.v3/select2-bootstrap.css" />
<title>入库开单_批量导入</title>
<span hidden="true" id = "ofc_web_url">${(OFC_WEB_URL)!}</span>
<div id="app">
    <div class="list-mian-01">
        <el-form :model="templateBatchIn"  label-width="100px" class="demo-ruleForm">
            <div class="xe-block" >
                <el-form-item label="客户名称"  class="xe-col-3" requird>
                    <el-input v-model="templateBatchIn.custName" v-if="custNameShow"   ></el-input>
                    <el-input v-model="templateBatchIn.custCode" v-if="custCodeShow"   ></el-input>
                    <input class="form-control select2-single"  name="custName" id="custName" placeholder="请输入客户名称"/>
                    <input  hidden name="custCode" id="custCode"  />
                </el-form-item>
            </div>
            <div class="xe-block">
                <el-form-item label="模板名称"   class="xe-col-3" requird>
                    <el-select placeholder="请选择" v-model="templateBatchIn.templateName">
                        <el-option  v-for="item in templateNameList" :label="item.label" :value="item.value"></el-option>
                    </el-select>
                </el-form-item>
            </div>
        </el-form>
    </div>


    <el-upload    :action="uploadAction" type="drag" :data="uploadParam" :accept="fileTypeAccept" :on-change="uploadChange" :on-progress="uploading"
                  :multiple="false" :before-upload="beforeUpload" :on-preview="handlePreview" :on-remove="handleRemove" :on-success="handleSuccess" :on-error="handleError" <#--:default-file-list="fileList"-->>
        <i class="el-icon-upload"></i>
        <div class="el-dragger__text">将文件拖到此处，或<em>点击上传</em></div>
        <div class="el-upload__tip" slot="tip">只能上传xls/xlsx文件，且不超过500kb</div>
    </el-upload>
</div>
<script type="text/javascript">

    var scripts = [null,
        "/components/select2.v3/select2.min.js",
        "/components/select2.v3/select2_locale_zh-CN.js",
        null];

    $(".page-content-area").ace_ajax("loadScripts", scripts, function () {
        $(document).ready(main);
    });

    function main() {
        initCustomerName();
        $("#custName").on("select2-selecting", function(e) {
            vm.templateBatchIn.custName = e.choice.name;
            vm.templateBatchIn.custCode = e.choice.code;
            //加载当前客户下所有模板!
            var param = {};
            param.custCode = e.choice.code;
            CommonClient.post("/ofc/storage_template/templist", param, function (result) {
                vm.templateNameList = [];
                var templateNameList = vm.templateNameList;
                if(result == null || result.length == 0) {
                    return;
                }
                $.each(result, function (index, item) {
                    if(index == 0) {
                        vm.templateBatchIn.templateName = item.templateCode;
                    }
                    var templateName = {};
                    templateName.label = item.templateName;
                    templateName.value = item.templateCode;
                    templateNameList.push(templateName);
                });

            })
        });
    }

    function initCustomerName() {
        var ofc_web_url = $("#ofc_web_url").html();
        var url = ofc_web_url + "/ofc/distributing/queryCustomerSelect2";
        var notice = "没有找到相关客户";
        Select2Util.singleSelectInit("#custName",url,notice,"#custCode");
    }


    var Main = {
        data() {
            return {
                uploadParam:{},
                custNameShow:false,
                custCodeShow:false,
                fileList: [],
                excel:'',
                fileTypeAccept:'.xls,.xlsx',
                uploadAction:'${OFC_WEB_URL!}/ofc/storage_template/batch_in_upload',
                authResDto:'',
                templateBatchIn:{
                    custName:'',
                    custCode:'',
                    templateName:''
                },
                pageSheetList:[],
                templateNameList:[]
            };
        },
        methods: {
            handleRemove(file, fileList) {
                fileList = [];
            },
            handlePreview(file) {
                console.log(file);
            },
            handleSuccess(response, file, fileList) {
                var vm = this;
                if(response.code == 500) {

                }else if(response.code == 200) {


                }
            },
            handleError(err, response, file) {

            },
            beforeUpload(file){
                var vm = this;
                //必须选好客户和模板
                if(undefined == vm.templateBatchIn.custName || StringUtil.isEmpty(vm.templateBatchIn.custName)){
                    layer.msg("请先选择客户!")
                    return;
                }
                //限制只允许上传一个文件
                var fileList = vm.fileList;
                if(fileList.length > 0){
                    layer.msg("只允许上传一个文件!");
                    return false;
                }
                vm.uploadParam = {"custCode":vm.templateBatchIn.custCode, "templateName":vm.templateBatchIn.templateName, "templateType":"storageIn"};
            },
            uploading(event, file, fileList){
                //文件大小限制
                var fileSize = file.size;
                /*var vm = this;
                //必须选好客户和模板
                if(undefined == vm.templateBatchIn.custName || StringUtil.isEmpty(vm.templateBatchIn.custName)){
                    layer.msg("请先选择客户!")
                    return;
                }*/
            },
            uploadChange(file,fileList){
                var vm = this;
                //必须选好客户和模板
                /*if(undefined == vm.templateBatchIn.custName || StringUtil.isEmpty(vm.templateBatchIn.custName)){
                    layer.msg("请先选择客户!")
                    return;
                }*/
            }
        }
    }
    var Ctor = Vue.extend(Main)
    var vm = new Ctor().$mount('#app')
</script>