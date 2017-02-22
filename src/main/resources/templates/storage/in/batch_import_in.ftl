<title>入库开单_批量导入</title>
<div id="vm">
    <div class="list-mian-01">
        <el-form :model="templateBatchIn"  label-width="100px" class="demo-ruleForm">
            <div class="xe-block">
                <el-form-item label="客户名称"  class="xe-col-3">
                    <el-input v-model="templateBatchIn.custName" id="custName" placeholder="请输入内容"></el-input>
                <#--<el-input  id="custCode" ></el-input>-->
                </el-form-item>
            </div>
            <div class="xe-block">
                <el-form-item label="模板名称"  class="xe-col-3">
                    <el-select placeholder="请选择" v-model="templateBatchIn.templateName">
                        <el-option  v-for="item in templateNameList" :label="item.label" :value="item.value"></el-option>
                    </el-select>
                </el-form-item>
            </div>
            <div class="xe-block">
                <el-form-item label="上传文件"  class="xe-col-3">
                    <el-input v-model="templateBatchIn.uploadFile"  placeholder="请输入内容"    value=""></el-input>
                    <el-button @click="fileUpload">上传</el-button>
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
</div>
<script type="text/javascript">
    var vm = new Vue({
        el:'#vm',
        data:function () {
            return{
                authResDto:'${authResDto!}',
                templateBatchIn:{
                    custName:'',
                    templateName:'',
                    uploadFile:'',
                    pageSheet:''
                },
                pageSheetList:[],
                templateNameList:[]
            }
        },
        method:{
            fileUpload:function (val) {

            },
            excelLoad:function (val) {

            }
        }
    })
</script> 