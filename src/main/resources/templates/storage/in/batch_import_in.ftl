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
                var fileList = vm.fileList;
                if(fileList.length > 0){
                    layer.msg("只允许上传一个文件!");
                    return false;
                }
            },
            uploading(event, file, fileList){
                //文件大小限制
                var fileSize = file.size;


            }
        }
    }
    var Ctor = Vue.extend(Main)
    new Ctor().$mount('#app')
</script>