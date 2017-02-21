<title>导入模板配置添加</title>
<div id="vm">
    <div class="list-mian-01">

        <el-dialog title="设置列默认值" v-model="colDefaultValDia" size="small">
            <div :model="colDefaultValModel">
                <label class="label">订单日期</label>
                <el-input v-model="colDefaultValModel.orderTime" placeholder="请输入内容"    value=""></el-input>
                <label class="label">开单员</label>
                <el-input v-model="colDefaultValModel.merchandiser" placeholder="请输入内容"   value=""></el-input>
                <label class="label">仓库名称</label>
                <el-select placeholder="请选择" v-model="colDefaultValModel.warehouseName">
                    <el-option  v-for="item in warehouseNameList" :label="item.label" :value="item.value"></el-option>
                </el-select>
                <label class="label">业务类型</label>
                <el-select placeholder="请选择" v-model="colDefaultValModel.businessType">
                    <el-option  v-for="item in businessTypeList" :label="item.label" :value="item.value"></el-option>
                </el-select>
                <label class="label">是否提供运输服务</label>
                <el-checkbox v-model="colDefaultValModel.provideTransport" ></el-checkbox>

                <div slot="footer" class="dialog-footer">
                    <el-button @click="cancelSetDefault">取 消</el-button>
                    <el-button type="primary" @click="confirmSetDefault">确 定</el-button>
                </div>
            </div>

        </el-dialog>

        <div class="xe-pageHeader">
            模板配置信息
        </div>
        <el-form :model="templateForm"  label-width="100px" class="demo-ruleForm">
            <div class="xe-block">
                <el-form-item label="模板类型"  class="xe-col-2">
                    <el-select placeholder="请选择" v-model="templateForm.templateType" >
                        <el-option  v-for="item in templatesTypeList" :label="item.label" :value="item.value"></el-option>
                    </el-select>
                    <div  v-if="templateTypeNotNull"><p style="color: red">模板类型不能为空</p></div>
                </el-form-item>
                <el-form-item label="模板名称"  class="xe-col-2">
                    <el-input v-model="templateForm.templateName"  placeholder="请输入配置名称"></el-input>
                    <div  v-if="templateNameNotNull"><p style="color: red">模板名称不能为空</p></div>
                </el-form-item >

            </div>
            <div class="xe-block">
                <el-form-item label="客户名称"  class="xe-col-2">
                    <el-input v-model="templateForm.custName" id="custName" placeholder="请输入客户名称"></el-input>
                    <div  v-if="custNameNotNull"><p style="color: red">客户名称不能为空</p></div>
                </el-form-item>
                <el-form-item label="客户编码"  class="xe-col-2">
                    <el-input v-model="templateForm.custCode" style="readonly" id="custName" placeholder="请输入客户编码"></el-input>
                    <div  v-if="custCodeNotNull"><p style="color: red">客户编码不能为空</p></div>
                </el-form-item>
            </div>
        </el-form>
        <template>
            <div class="xe-block">
                <el-button type="primary" v-on:click="templateDefaultSetBtn" icon="save">设置默认值</el-button>
            </div>
            <el-table
                    :data="tableData"
                    highlight-current-row
                    <#--@current-change="handleCurrentChange"-->
                    <#--v-html="tableData.reflectColName"-->
                    :model="tableData.colDefaultVal"
                    border
                    style="width: 100%">
                <el-table-column type="index" label="序号">
                </el-table-column>
                <el-table-column property="standardColName"  label="平台列名">
                </el-table-column>
                <el-table-column property="reflectColName" label="模板列名">
                    <template scope="scope">
                        <el-input v-model="scope.row.reflectColName" class="xe-col-8"></el-input>
                    </template>
                </el-table-column>
                <el-table-column property="colDefaultVal" label="默认值">
                    <#--<template scope="scope">-->
                        <#--<div slot="reference" >-->
                        <#--</div>-->
                    <#--</template>-->
                </el-table-column>
                <el-table-column property="standardColCode"  label="平台列名编码" v-if="standardColCodeShow">
                </el-table-column>
            </el-table>
            <div class="xe-block">
                <el-button type="primary" v-on:click="templateSaveBtn" icon="save">保存</el-button>
            </div>
        </template>
    </div>
</div>
<script type="text/javascript" >
    var vm = new Vue({
        el:'#vm',
        data:function () {
            return{
                templateTypeNotNull:false,
                templateNameNotNull:false,
                custNameNotNull:false,
                custCodeNotNull:false,
                standardColCodeShow:false,
                templateForm:{
                    templateType:'',
                    templateName:'',
                    custName:'',
                    custCode:''
                },
                templatesTypeList:[
                    {value:'storageIn',label:'入库单'},
                    {value:'storageOut',label:'出库单'}
                ],
                colDefaultValDia:false,
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
                tableData:[
                    {standardColName:'客户订单号',reflectColName:'',colDefaultVal:'',standardColCode:'custOrderCode'},
                    {standardColName:'订单日期',reflectColName:'',colDefaultVal:'',standardColCode:'orderTime'},
                    {standardColName:'开单员',reflectColName:'',colDefaultVal:'',standardColCode:'merchandiser'},
                    {standardColName:'仓库名称',reflectColName:'',colDefaultVal:'',standardColCode:'warehouseName'},
                    {standardColName:'业务类型',reflectColName:'',colDefaultVal:'',standardColCode:'businessType'},
                    {standardColName:'备注',reflectColName:'',colDefaultVal:'',standardColCode:'notes'},
                    {standardColName:'货品编码',reflectColName:'',colDefaultVal:'',standardColCode:'goodsCode'},
                    {standardColName:'货品名称',reflectColName:'',colDefaultVal:'',standardColCode:'goodsName'},
                    {standardColName:'规格',reflectColName:'',colDefaultVal:'',standardColCode:'goodsSpec'},
                    {standardColName:'单位',reflectColName:'',colDefaultVal:'',standardColCode:'unit'},
                    {standardColName:'单价',reflectColName:'',colDefaultVal:'',standardColCode:'unitPrice'},
                    {standardColName:'入库数量',reflectColName:'',colDefaultVal:'',standardColCode:'inStorageNum'},
                    {standardColName:'批次号',reflectColName:'',colDefaultVal:'',standardColCode:'productionBatch'},
                    {standardColName:'生产日期',reflectColName:'',colDefaultVal:'',standardColCode:'productionTime'},
                    {standardColName:'失效日期',reflectColName:'',colDefaultVal:'',standardColCode:'invalidTime'},
                    {standardColName:'供应商名称',reflectColName:'',colDefaultVal:'',standardColCode:'supportName'},
                    {standardColName:'预计入库时间',reflectColName:'',colDefaultVal:'',standardColCode:'arriveTime'},
                    {standardColName:'是否提供运输服务',reflectColName:'',colDefaultVal:'',standardColCode:'provideTransport'},
                    {standardColName:'车牌号',reflectColName:'',colDefaultVal:'',standardColCode:'plate_number'},
                    {standardColName:'司机姓名',reflectColName:'',colDefaultVal:'',standardColCode:'driverName'},
                    {standardColName:'联系电话',reflectColName:'',colDefaultVal:'',standardColCode:'contactNumber'}
                ]
            }
        },
        methods:{
            templateSaveBtn:function () {
                debugger
                var templateList = [];
                var designData = this.tableData;
                var templateType = this.templateForm.templateType;
                var templateName = this.templateForm.templateName;
                var custName = this.templateForm.custName;
                var custCode = this.templateForm.custCode;
                if(StringUtil.isEmpty(templateType)){
                    this.templateTypeNotNull = true;
                    return;
                }else if(StringUtil.isEmpty(templateName)){
                    this.templateNameNotNull = true;
                    return;
                }else if(StringUtil.isEmpty(custName)){
                    this.custNameNotNull = true;
                    return;
                }else if(StringUtil.isEmpty(custCode)){
                    this.custCodeNotNull = true;
                    return;
                }
                for (var i = 0; i < designData.length; i ++){
                    var design = designData[i];
                    var template = {};
                    template.templateType = templateType;
                    template.templateName = templateName;
                    template.custName = custName;
                    template.custCode = custCode;
                    var reflectColName = StringUtil.trim(design.reflectColName);
                    var index = design.index;
                    if(index == 0 && StringUtil.isEmpty(reflectColName)){
                        alert('客户订单号模板列名为空!');
                        return;
                    } else if(index == 2 && StringUtil.isEmpty(reflectColName)){
                        alert('开单员模板列名为空!');
                        return;
                    } else if(index == 3 && StringUtil.isEmpty(reflectColName)){
                        alert('仓库名称模板列名为空!');
                        return;
                    } else if(index == 4 && StringUtil.isEmpty(reflectColName)){
                        alert('业务类型模板列名为空!');
                        return;
                    } else if(index == 6 && StringUtil.isEmpty(reflectColName)){
                        alert('货品编码模板列名为空!');
                        return;
                    } else if(index == 11 && StringUtil.isEmpty(reflectColName)){
                        designData.length == 21 ? alert('入库数量模板列名为空!') : alert('出库数量模板列名为空!');
                        return;
                    } else if(index == 21 && StringUtil.isEmpty(reflectColName)){
                        alert('收货方名称模板列名为空!');
                        return;
                    }
                    template.standardColCode = design.standardColCode;
                    template.standardColName = design.standardColName;
                    template.reflectColName = reflectColName;
                    template.colDefaultVal = StringUtil.trim(design.colDefaultVal);
                    templateList.push(template);
                }
                xescm.common.submit("/ofc/storage_template/save", {"templateList":JSON.stringify(templateList)}, "确认保存该模板配置?", function () {

                });
            },
            templateDefaultSetBtn:function () {
                this.colDefaultValDia = true;
            },
            cancelSetDefault:function () {
                this.colDefaultValDia = false;
            },
            confirmSetDefault:function () {
                this.colDefaultValDia = false;
                var defOrderTime = this.colDefaultValModel.orderTime;
                var defMerchandiser = this.colDefaultValModel.merchandiser;
                var defWarehouseName = this.colDefaultValModel.warehouseName;
                var defBusinessType = this.colDefaultValModel.businessType;
                var defProvideTransport = this.colDefaultValModel.provideTransport;
                var table = this.tableData;
                table[1].colDefaultVal = defOrderTime;
                table[2].colDefaultVal = defMerchandiser;
                table[3].colDefaultVal = defWarehouseName;
                table[4].colDefaultVal = defBusinessType;
                if(!defProvideTransport){
                    table[17].colDefaultVal = "否";
                }
                table[17].colDefaultVal = "是";
            }
        }
    })
</script>