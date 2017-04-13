<div id="app">
    <div class="xe-block" style="margin-left:100px;">
        <el-upload  :action="uploadAction" type="drag" :headers="headers" :accept="fileTypeAccept" :on-change="uploadChange" :on-progress="uploading"
                    :multiple="false"  :on-success="handleSuccess" :before-upload="beforeUpload" :default-file-list="fileList">
            <i class="el-icon-upload"></i>
            <div class="el-dragger__text">将文件拖到此处，或<em>点击上传</em></div>
            <div class="el-upload__tip" slot="tip">只能上传xls/xlsx文件，且不超过500kb</div>
        </el-upload>
    </div>
</div>
<style>
    .el-upload__input{
        opacity:0;
    }
</style>
<script type="text/javascript">
    var vm = new Vue({
        el: '#app',
        data: function () {
            return{
                fileList:[],
                headers:{
                    Authorization: 'Bearer ' + window.localStorage.getItem('token')
                },
                fileTypeAccept:'.xls,.xlsx',
                uploadAction:'${OFC_WEB_URL!}/ofc/zpMissOrderImport'
            }

        },
        methods:{
            uploadChange(file,fileList){
                var vm = this;
                if(fileList.length > 0){
                    vm.$message.error("只允许上传一个文件!");
                    return false;
                }
            },
            uploading(event, file, fileList){
                var vm = this;
                var fileList = vm.fileList;
                if(fileList.length > 0){
                    vm.$message.error("只允许上传一个文件!");
                    return false;
                }
                vm.loading2 = true;
                //文件大小限制
            },
            handleSuccess(response, file, fileList) {

            },
            beforeUpload(file){
                //限制只允许上传一个文件
                var fileList = vm.fileList;
                if(fileList.length > 0){
                    vm.$message.error("只允许上传一个文件!");
                    return false;
                }
            }
        }
    });
</script>