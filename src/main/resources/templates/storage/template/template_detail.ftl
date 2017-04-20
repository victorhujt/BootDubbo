<title>模板配置详情</title>
<style type="text/css">
    .el-checkbox__input.is-disabled.is-checked .el-checkbox__inner{
        background-color:#20a0ff;
        border-color:#20a0ff;
    }
</style>
<div id="vm">
    <div class="list-mian-01">
        <div class="xe-pageHeader">
            模板配置信息
        </div>
        <el-form :model="templateForm"  label-width="100px" class="demo-ruleForm">
            <div class="xe-block">
                <el-form-item label="模板类型"  class="xe-col-2">
                    <el-select placeholder="请选择"  :disabled="true" v-model="templateForm.templateType" v-for="item in templatesTypeList" :label="item.label" :value="item.value">
                    </el-select>
                </el-form-item>
                <el-form-item label="模板名称"  class="xe-col-2">
                    <el-input v-model="templateForm.templateName" :disabled="true"  placeholder="请输入配置名称"></el-input>
                </el-form-item >
            </div>
            <div class="xe-block">
                <el-form-item label="客户名称"  class="xe-col-2">
                    <el-input v-model="templateForm.custName" :disabled="true" id="custName" placeholder="请输入客户名称"></el-input>
                </el-form-item>
                <el-form-item label="客户编码"  class="xe-col-2">
                    <el-input v-model="templateForm.custCode" :disabled="true" style="readonly" id="custName" placeholder="请输入客户编码"></el-input>
                </el-form-item>
            </div>
        </el-form>
        <template>
            <el-table
                    :data="tableData"
                    highlight-current-row
                    :model="tableData.colDefaultVal"
                    border
                    style="width: 100%">
                <el-table-column type="index" label="序号">
                </el-table-column>
                <el-table-column property="standardColName"  label="平台列名">
                </el-table-column>
                <el-table-column property="reflectColName" label="模板列名">
                    <template scope="scope">
                        <el-input v-model="scope.row.reflectColName" :disabled="true" class="xe-col-8"></el-input>
                    </template>
                </el-table-column>
                <el-table-column property="isRequired" label="是否必填">
                    <template scope="scope">
                        <el-checkbox v-model="scope.row.isRequired" disabled=true class="xe-col-8"></el-checkbox>
                    </template>
                </el-table-column>
                <el-table-column property="colDefaultVal" label="默认值">
                </el-table-column>
            </el-table>
        </template>
    </div>
</div>
<script type="text/javascript" >
    var vm = new Vue({
        el:'#vm',
        data:function () {
            return{
                selectData:{},
                typeOfTemplate:false,
                templateCodeShow:'${templateCode!}',
                templateForm:{
                    templateType:'',
                    templateName:'',
                    custName:'',
                    custCode:''
                },
                templatesTypeList:[

                ],
                colDefaultValModel:{
                    orderTime:'',
                    merchandiser:'',
                    warehouseName:'',
                    businessType:'',
                    provideTransport:''
                },
                warehouseNameList:[
                    //加载所有仓库
                ],
                businessTypeList:[
                    {value:'采购入库',label:'采购入库'},
                    {value:'调拨入库',label:'调拨入库'},
                    {value:'退货入库',label:'退货入库'},
                    {value:'加工入库',label:'加工入库'},
                    {value:'盘盈入库',label:'盘盈入库'},
                    {value:'流通入库',label:'流通入库'},
                    {value:'其他入库',label:'其他入库'}
                ],
                tableData:[]
            }
        },
        beforeMount:function () {
            var vm = this;
            var templateCode = vm.templateCodeShow;
            if(undefined == templateCode || StringUtil.isEmpty(templateCode)){
                vm.$message.error("错误!模板编码为空!");
                return;
            }
            var url = "/ofc/storage_template/detail_data/" + templateCode;
            CommonClient.post(url, {}, function (result) {
                var itemOut = {};
                $.each(result.result,function (index, item) {
                    if(index == 0) {
                        itemOut = item;
                        vm.typeOfTemplate = itemOut.templateType == 'storageOut';
                    }
                    var tableItem = {};
                    var standardColCode = item.standardColCode;
                    if(standardColCode == 'custOrderCode' || standardColCode == 'merchandiser' || standardColCode == 'warehouseName'
                            || standardColCode == 'businessType' || standardColCode == 'goodsCode' || standardColCode == 'quantity' || (standardColCode == 'consigneeName' && vm.typeOfTemplate)){
                        tableItem.isRequired = true;
                    }
                    tableItem.standardColName = item.standardColName;
                    tableItem.reflectColName = item.reflectColName;
                    if(standardColCode == 'orderTime'){
                        item.colDefaultVal = '当前日期';
                    }
                    tableItem.colDefaultVal = item.colDefaultVal;

                    vm.tableData.push(tableItem);
                });
                var templateType = itemOut.templateType;
                var templateTypeName = itemOut.templateType == 'storageIn' ? '入库单' : '出库单';

                vm.templatesTypeList = [
                    {label:templateType,value:templateTypeName}
                ];
                vm.templateForm.templateName = itemOut.templateName;
                vm.templateForm.custName = itemOut.custName;
                vm.templateForm.custCode = itemOut.custCode;

            })
        },
        methods:{

        }
    })
</script>