<link rel="stylesheet" href="/components/select2.v3/select2.min.css" />
<link rel="stylesheet" href="/components/select2.v3/select2-bootstrap.css" />
<title>导入模板配置添加</title>
<span hidden="true" id = "ofc_web_url">${(OFC_WEB_URL)!}</span>
<div id="vm">
    <div class="list-mian-01">

        <el-dialog title="设置列默认值" v-model="colDefaultValDia" size="small">
           <el-form :model="colDefaultValModel" label-width="120px">
             <div class="xe-block">
               <el-form-item label="订单日期" class="xe-col-2">
                   {{colDefaultValModel.orderTime}}
               </el-form-item>
             </div>
             <div class="xe-block">
               <el-form-item label="开单员" class="xe-col-2">
                 <el-input v-model="colDefaultValModel.merchandiser"  placeholder="请输入内容"></el-input>
               </el-form-item>
             </div>
             <div class="xe-block">
               <el-form-item label="仓库名称" class="xe-col-2">
                 <el-select placeholder="请选择" v-model="colDefaultValModel.warehouseName">
                   <el-option  v-for="item in warehouseNameList" :label="item.label" :value="item.value"></el-option>
                 </el-select>
               </el-form-item>
             </div>
             <div class="xe-block">
               <el-form-item label="业务类型" class="xe-col-2">
                 <el-select placeholder="请选择" v-model="colDefaultValModel.businessType">
                   <el-option  v-for="item in businessTypeList" :label="item.label" :value="item.value"></el-option>
                 </el-select>
               </el-form-item>
             </div>
             <div class="xe-block">
               <el-form-item label="是否提供运输服务" class="xe-col-2">
                 <el-checkbox v-model="colDefaultValModel.provideTransport" ></el-checkbox>
               </el-form-item>
             </div>

           </el-form>
          <div slot="footer" class="dialog-footer">
            <el-button @click="cancelSetDefault">取 消</el-button>
            <el-button type="primary" @click="confirmSetDefault">确 定</el-button>
          </div>

        </el-dialog>

        <div class="xe-pageHeader">
            模板配置信息
        </div>
        <el-form :model="templateForm"  label-width="100px" class="demo-ruleForm">
            <div class="xe-block">
                <el-form-item label="模板类型"  class="xe-col-2" required>
                    <el-select placeholder="请选择" v-model="templateForm.templateType" v-on:change="templateTypeChange">
                        <el-option  v-for="item in templatesTypeList"  :label="item.label" :value="item.value"></el-option>
                    </el-select>
                    <div  v-if="templateTypeNotNull"><p style="color: red">模板类型不能为空</p></div>
                </el-form-item>
                <el-form-item label="模板名称"  class="xe-col-2" required>
                    <el-input v-model="templateForm.templateName"  placeholder="请输入配置名称" v-on:change="templateNameChange"></el-input>
                    <div  v-if="templateNameNotNull"><p style="color: red">模板名称不能为空</p></div>
                </el-form-item >
            </div>
            <div class="xe-block">
                <el-form-item label="客户名称"  class="xe-col-2" required>
                    <el-input v-model="templateForm.custName" v-if="custNameShow"  placeholder="请输入客户名称"></el-input>
                    <input class="form-control select2-single"  name="custName" id="custName" placeholder="请输入客户名称"/>
                    <div  v-if="custNameNotNull"><p style="color: red">客户名称不能为空</p></div>
                </el-form-item>
                <el-form-item label="客户编码"  class="xe-col-2">
                    <el-input v-model="templateForm.custCode"  :disabled="true" placeholder="请输入客户编码" v-on:change="custCodeChange"></el-input>
                    <input  name="custCode" id="custCode" hidden placeholder="请输入客户编码"/>
                    <#--<el-input v-model="templateForm.custCode" :disabled="true" id="custName" placeholder="请输入客户编码" v-on:change="custCodeChange"></el-input>-->
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
                <el-table-column property="indexNum" label="序号">
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
            vm.templateForm.custName = e.choice.name;
            vm.templateForm.custCode = e.choice.code;
            vm.custNameNotNull = false;
        });
    }

    function initCustomerName() {
        var ofc_web_url = $("#ofc_web_url").html();
        var url = ofc_web_url + "/ofc/distributing/queryCustomerSelect2";
        var notice = "没有找到相关客户";
        Select2Util.singleSelectInit("#custName",url,notice,"#custCode");
    }

    var vm = new Vue({
        el:'#vm',
        data:function () {
            return{
                orderTime:'${orderTime!}',
                warehouseName:'',
                businessType:'',
                custNameShow:false,
                templateTypeNotNull:false,
                templateNameNotNull:false,
                custNameNotNull:false,
                custCodeNotNull:false,
                standardColCodeShow:false,
                merchandiserList:[],
                templateForm:{
                    templateType:'storageIn',
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
                    merchandiser:'${userName!}',
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
                    {indexNum:'1',standardColName:'客户订单号',reflectColName:'',colDefaultVal:'',standardColCode:'custOrderCode'},
                    {indexNum:'2',standardColName:'订单日期',reflectColName:'',colDefaultVal:'',standardColCode:'orderTime'},
                    {indexNum:'3',standardColName:'开单员',reflectColName:'',colDefaultVal:'',standardColCode:'merchandiser'},
                    {indexNum:'4',standardColName:'仓库名称',reflectColName:'',colDefaultVal:'',standardColCode:'warehouseName'},
                    {indexNum:'5',standardColName:'业务类型',reflectColName:'',colDefaultVal:'',standardColCode:'businessType'},
                    {indexNum:'6',standardColName:'备注',reflectColName:'',colDefaultVal:'',standardColCode:'notes'},
                    {indexNum:'7',standardColName:'货品编码',reflectColName:'',colDefaultVal:'',standardColCode:'goodsCode'},
                    {indexNum:'8',standardColName:'货品名称',reflectColName:'',colDefaultVal:'',standardColCode:'goodsName'},
                    {indexNum:'9',standardColName:'规格',reflectColName:'',colDefaultVal:'',standardColCode:'goodsSpec'},
                    {indexNum:'10',standardColName:'单位',reflectColName:'',colDefaultVal:'',standardColCode:'unit'},
                    {indexNum:'11',standardColName:'单价',reflectColName:'',colDefaultVal:'',standardColCode:'unitPrice'},
                    {indexNum:'12',standardColName:'入库数量',reflectColName:'',colDefaultVal:'',standardColCode:'quantity'},
                    {indexNum:'13',standardColName:'批次号',reflectColName:'',colDefaultVal:'',standardColCode:'productionBatch'},
                    {indexNum:'14',standardColName:'生产日期',reflectColName:'',colDefaultVal:'',standardColCode:'productionTime'},
                    {indexNum:'15',standardColName:'失效日期',reflectColName:'',colDefaultVal:'',standardColCode:'invalidTime'},
                    {indexNum:'16',standardColName:'供应商名称',reflectColName:'',colDefaultVal:'',standardColCode:'supportName'},
                    {indexNum:'17',standardColName:'预计入库时间',reflectColName:'',colDefaultVal:'',standardColCode:'arriveTime'},
                    {indexNum:'18',standardColName:'是否提供运输服务',reflectColName:'',colDefaultVal:'',standardColCode:'provideTransport'},
                    {indexNum:'19',standardColName:'车牌号',reflectColName:'',colDefaultVal:'',standardColCode:'plateNumber'},
                    {indexNum:'20',standardColName:'司机姓名',reflectColName:'',colDefaultVal:'',standardColCode:'driverName'},
                    {indexNum:'21',standardColName:'联系电话',reflectColName:'',colDefaultVal:'',standardColCode:'contactNumber'}
                ]
            }
        } ,
        beforeMount:function () {
          var vm = this;
          vm.colDefaultValModel = {
              orderTime:vm.orderTime,
              merchandiser:'${userName!}',
              warehouseName:'',
              businessType:'',
              provideTransport:''
          };
          CommonClient.post("/ofc/storage_template/warehouse",{},function (result) {
              vm.warehouseNameList = [];
              if(result.code != 200){
                  vm.$message("加载所有仓库失败!");
                  return;
              }
              $.each(result.result, function (index, item) {
                  var warehouseName = {};
                  warehouseName.lable = item.warehouseName;
                  warehouseName.value = item.warehouseName;
                  vm.warehouseNameList.push(warehouseName);
              })
          })

        },
        methods:{
            templateTypeChange:function (val) {
                var vm = this;
                vm.colDefaultValModel.businessType = '';
                if(!StringUtil.isEmpty(val)){
                    vm.templateTypeNotNull = false;
                }else {
                    vm.templateTypeNotNull = true;
                }
                if(val == 'storageIn'){
                    vm.tableData = [
                        {indexNum:'1',standardColName:'客户订单号',reflectColName:'',colDefaultVal:'',standardColCode:'custOrderCode'},
                        {indexNum:'2',standardColName:'订单日期',reflectColName:'',colDefaultVal:'',standardColCode:'orderTime'},
                        {indexNum:'3',standardColName:'开单员',reflectColName:'',colDefaultVal:'',standardColCode:'merchandiser'},
                        {indexNum:'4',standardColName:'仓库名称',reflectColName:'',colDefaultVal:'',standardColCode:'warehouseName'},
                        {indexNum:'5',standardColName:'业务类型',reflectColName:'',colDefaultVal:'',standardColCode:'businessType'},
                        {indexNum:'6',standardColName:'备注',reflectColName:'',colDefaultVal:'',standardColCode:'notes'},
                        {indexNum:'7',standardColName:'货品编码',reflectColName:'',colDefaultVal:'',standardColCode:'goodsCode'},
                        {indexNum:'8',standardColName:'货品名称',reflectColName:'',colDefaultVal:'',standardColCode:'goodsName'},
                        {indexNum:'9',standardColName:'规格',reflectColName:'',colDefaultVal:'',standardColCode:'goodsSpec'},
                        {indexNum:'10',standardColName:'单位',reflectColName:'',colDefaultVal:'',standardColCode:'unit'},
                        {indexNum:'11',standardColName:'单价',reflectColName:'',colDefaultVal:'',standardColCode:'unitPrice'},
                        {indexNum:'12',standardColName:'入库数量',reflectColName:'',colDefaultVal:'',standardColCode:'quantity'},
                        {indexNum:'13',standardColName:'批次号',reflectColName:'',colDefaultVal:'',standardColCode:'productionBatch'},
                        {indexNum:'14',standardColName:'生产日期',reflectColName:'',colDefaultVal:'',standardColCode:'productionTime'},
                        {indexNum:'15',standardColName:'失效日期',reflectColName:'',colDefaultVal:'',standardColCode:'invalidTime'},
                        {indexNum:'16',standardColName:'供应商名称',reflectColName:'',colDefaultVal:'',standardColCode:'supportName'},
                        {indexNum:'17',standardColName:'预计入库时间',reflectColName:'',colDefaultVal:'',standardColCode:'arriveTime'},
                        {indexNum:'18',standardColName:'是否提供运输服务',reflectColName:'',colDefaultVal:'',standardColCode:'provideTransport'},
                        {indexNum:'19',standardColName:'车牌号',reflectColName:'',colDefaultVal:'',standardColCode:'plateNumber'},
                        {indexNum:'20',standardColName:'司机姓名',reflectColName:'',colDefaultVal:'',standardColCode:'driverName'},
                        {indexNum:'21',standardColName:'联系电话',reflectColName:'',colDefaultVal:'',standardColCode:'contactNumber'}
                    ];
                    vm.businessTypeList=[
                        {value:'采购入库',label:'采购入库'},
                        {value:'调拨入库',label:'调拨入库'},
                        {value:'退货入库',label:'退货入库'},
                        {value:'加工入库',label:'加工入库'},
                        {value:'盘盈入库',label:'盘盈入库'},
                        {value:'流通入库',label:'流通入库'},
                        {value:'其他入库',label:'其他入库'}
                    ];
                }else if(val == 'storageOut'){
                    vm.tableData = [
                        {indexNum:'1',standardColName:'客户订单号',reflectColName:'',colDefaultVal:'',standardColCode:'custOrderCode'},
                        {indexNum:'2',standardColName:'订单日期',reflectColName:'',colDefaultVal:'',standardColCode:'orderTime'},
                        {indexNum:'3',standardColName:'开单员',reflectColName:'',colDefaultVal:'',standardColCode:'merchandiser'},
                        {indexNum:'4',standardColName:'仓库名称',reflectColName:'',colDefaultVal:'',standardColCode:'warehouseName'},
                        {indexNum:'5',standardColName:'业务类型',reflectColName:'',colDefaultVal:'',standardColCode:'businessType'},
                        {indexNum:'6',standardColName:'备注',reflectColName:'',colDefaultVal:'',standardColCode:'notes'},
                        {indexNum:'7',standardColName:'货品编码',reflectColName:'',colDefaultVal:'',standardColCode:'goodsCode'},
                        {indexNum:'8',standardColName:'货品名称',reflectColName:'',colDefaultVal:'',standardColCode:'goodsName'},
                        {indexNum:'9',standardColName:'规格',reflectColName:'',colDefaultVal:'',standardColCode:'goodsSpec'},
                        {indexNum:'10',standardColName:'单位',reflectColName:'',colDefaultVal:'',standardColCode:'unit'},
                        {indexNum:'11',standardColName:'单价',reflectColName:'',colDefaultVal:'',standardColCode:'unitPrice'},
                        {indexNum:'12',standardColName:'出库数量',reflectColName:'',colDefaultVal:'',standardColCode:'quantity'},
                        {indexNum:'13',standardColName:'批次号',reflectColName:'',colDefaultVal:'',standardColCode:'productionBatch'},
                        {indexNum:'14',standardColName:'生产日期',reflectColName:'',colDefaultVal:'',standardColCode:'productionTime'},
                        {indexNum:'15',standardColName:'失效日期',reflectColName:'',colDefaultVal:'',standardColCode:'invalidTime'},
                        {indexNum:'16',standardColName:'供应商名称',reflectColName:'',colDefaultVal:'',standardColCode:'supportName'},
                        {indexNum:'17',standardColName:'预计出库时间',reflectColName:'',colDefaultVal:'',standardColCode:'shipmentTime'},
                        {indexNum:'18',standardColName:'是否提供运输服务',reflectColName:'',colDefaultVal:'',standardColCode:'provideTransport'},
                        {indexNum:'19',standardColName:'车牌号',reflectColName:'',colDefaultVal:'',standardColCode:'plateNumber'},
                        {indexNum:'20',standardColName:'司机姓名',reflectColName:'',colDefaultVal:'',standardColCode:'driverName'},
                        {indexNum:'21',standardColName:'联系电话',reflectColName:'',colDefaultVal:'',standardColCode:'contactNumber'},
                        {indexNum:'22',standardColName:'收货方名称',reflectColName:'',colDefaultVal:'',standardColCode:'consigneeName'}
                    ];
                    vm.businessTypeList=[
                        {value:'销售出库',label:'销售出库'},
                        {value:'调拨出库',label:'调拨出库'},
                        {value:'报损出库',label:'报损出库'},
                        {value:'其他出库',label:'其他出库'},
                        {value:'分拨出库',label:'分拨出库'}
                    ];
                }
            },
            templateNameChange:function (val) {
                var vm = this;
                if(!StringUtil.isEmpty(val)){
                    vm.templateNameNotNull = false;
                }else{
                    vm.templateNameNotNull = true;
                }
            },
            custNameChange:function (val) {
                var vm = this;
                if(!StringUtil.isEmpty(val)){
                    vm.custNameNotNull = false;
                }else{
                    vm.custNameNotNull = true;
                }
            },
            custCodeChange:function (val) {
                var vm = this;
                if(!StringUtil.isEmpty(val)){
                    vm.custCodeNotNull = false;
                }else{
                    vm.custCodeNotNull = true;
                }
            },

            templateSaveBtn:function () {
                var vm = this;
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
                    var reflectColName = StringUtil.isEmpty(design.reflectColName) ? "" : StringUtil.trim(design.reflectColName);
                    var index = design.indexNum;
                    if(index == 1 && StringUtil.isEmpty(reflectColName)){
                        vm.$message('客户订单号模板列名为空!');
                        return;
                    } else if(index == 3 && StringUtil.isEmpty(reflectColName)){
                        vm.$message('开单员模板列名为空!');
                        return;
                    } else if(index == 4 && StringUtil.isEmpty(reflectColName)){
                        vm.$message('仓库名称模板列名为空!');
                        return;
                    } else if(index == 5 && StringUtil.isEmpty(reflectColName)){
                        vm.$message('业务类型模板列名为空!');
                        return;
                    } else if(index == 7 && StringUtil.isEmpty(reflectColName)){
                        vm.$message('货品编码模板列名为空!');
                        return;
                    } else if(index == 12 && StringUtil.isEmpty(reflectColName)){
                        designData.length == 21 ? vm.$message('入库数量模板列名为空!') : vm.$message('出库数量模板列名为空!');
                        return;
                    } else if(index == 22 && StringUtil.isEmpty(reflectColName)){
                        vm.$message('收货方名称模板列名为空!');
                        return;
                    }
                    template.indexNum = design.indexNum;
                    template.standardColCode = design.standardColCode;
                    template.standardColName = design.standardColName;
                    template.reflectColName = reflectColName;
                    template.colDefaultVal = StringUtil.isEmpty(design.colDefaultVal) ? "" : StringUtil.trim(design.colDefaultVal);
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
                }else{
                    table[17].colDefaultVal = "是";
                }
                this.colDefaultValDia = false;
            }
        }

    });
</script>