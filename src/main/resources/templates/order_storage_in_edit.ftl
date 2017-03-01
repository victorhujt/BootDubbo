<!DOCTYPE html>
<html>
<head>
    <style lang="css">
        .block {
            margin: 20px 0;
        }
    </style>
</head>
<body>
<div id="app">
    <div class="list-mian-01">
        <el-dialog title="选择客户" v-model="chosenCus" size="small">
            <el-form :model="chosenCusForm">
                <el-form-item label="名称" :label-width="formLabelWidth">
                    <el-input v-model="chosenCusForm.name" auto-complete="off"></el-input>
                </el-form-item>
                  <el-form-item label="" :label-width="formLabelWidth">
                    <el-button type="primary" @click="selectCustomer">查询</el-button>
                  </el-form-item>
            </el-form>

            <el-table :data="customerData" highlight-current-row @current-change="handleCurrentChange" style="width: 100%">
                <el-table-column type="index"></el-table-column>
                <el-table-column property="customerCode" label="客户编码"></el-table-column>
                <el-table-column property="type" label="类型"></el-table-column>
                <el-table-column property="customerName" label="公司名称"></el-table-column>
                <el-table-column property="channel" label="渠道"></el-table-column>
                <el-table-column property="productType" label="产品类别"></el-table-column>
            </el-table>
            <el-pagination @size-change="handleCustomerSizeChange" @current-change="handleCustomerCurrentPage" :current-page="currentPage4" :page-sizes="pageSizes" :page-size="customerPageSize" :total="total" layout="total, sizes, prev, pager, next, jumper">
            </el-pagination>
            <div slot="footer" class="dialog-footer">
                <el-button @click="cancelSelectCustomer">取 消</el-button>
                <el-button type="primary" @click="setCurrentCustInfo(currentRow)">确 定</el-button>
            </div>
        </el-dialog>

        <el-dialog title="发货方联系人" v-model="chosenSend" size="small">
            <el-form :model="consignorForm">
                <el-form-item label="名称" :label-width="formLabelWidth">
                    <el-input v-model="consignorForm.consignorName" auto-complete="off"></el-input>
                </el-form-item>

                <el-form-item label="联系人" :label-width="formLabelWidth">
                    <el-input v-model="consignorForm.consignorContactName" auto-complete="off"></el-input>
                </el-form-item>
                <el-form-item label="联系电话" :label-width="formLabelWidth">
                    <el-input v-model="consignorForm.consignorPhoneNumber" auto-complete="off"></el-input>
                </el-form-item>
              <el-form-item label="" :label-width="formLabelWidth">
                    <el-button type="primary" @click="selectConsignor">筛选</el-button>
                </el-form-item>
            </el-form>

            <el-table :data="consignorData" highlight-current-row @current-change="consignorHandleCurrentChange" style="width: 100%">
                <el-table-column type="index"></el-table-column>
                <el-table-column property="consignorName" label="名称"></el-table-column>
                <el-table-column property="consignorContactName" label="联系人"></el-table-column>
                <el-table-column property="consignorPhoneNumber" label="联系电话"></el-table-column>
                <el-table-column property="consignorAddress" label="地址"></el-table-column>
                <el-table-column property="consignorCode" label="发货方编码"></el-table-column>
                <el-table-column property="consignorType" label="发货方类型"></el-table-column>
                <el-table-column property="consignorContactCode" label="发货方联系人编码"></el-table-column>
                <el-table-column property="consignorAddressCode" label="发货方地址编码"></el-table-column>
            </el-table>
            <el-pagination @size-change="handleConsignorSizeChange" @current-change="handleConsignorCurrentPage" :current-page="currentPage4" :page-sizes="pageSizes" :page-size="consignorPageSize" layout="total, sizes, prev, pager, next, jumper" :total="totalConsignor">
            </el-pagination>
            <div slot="footer" class="dialog-footer">
                <el-button @click="cancelSelectConsignor">取 消</el-button>
                <el-button type="primary" @click="setCurrentConsignorInfo(consignorCurrentRow)">确 定</el-button>
            </div>
        </el-dialog>


        <el-dialog title="供应商信息" v-model="chosenSupplier" size="small">
            <el-form :model="supplierForm">
                <el-form-item label="名称" :label-width="formLabelWidth">
                    <el-input v-model="supplierForm.supplierName" auto-complete="off"></el-input>
                </el-form-item>
                <el-form-item label="联系人" :label-width="formLabelWidth">
                    <el-input v-model="supplierForm.contactName" auto-complete="off"></el-input>
                </el-form-item>
                <el-form-item label="联系电话" :label-width="formLabelWidth">
                    <el-input v-model="supplierForm.contactPhone" auto-complete="off"></el-input>
                </el-form-item>
              <el-form-item label="" :label-width="formLabelWidth">
                    <el-button type="primary" @click="selectSupplier">筛选</el-button>
                </el-form-item>
            </el-form>

            <el-table :data="supplierData" highlight-current-row @current-change="handlSuppliereCurrentChange" style="width: 100%">
                <el-table-column type="index"></el-table-column>
                <el-table-column property="supplierName" label="名称"></el-table-column>
                <el-table-column property="contactName" label="联系人"></el-table-column>
                <el-table-column property="contactPhone" label="联系电话"></el-table-column>
                <el-table-column property="fax" label="传真"></el-table-column>
                <el-table-column property="email" label="邮箱"></el-table-column>
                <el-table-column property="postCode" label="邮编"></el-table-column>
                <el-table-column property="supplierCode" label="供应商编码"></el-table-column>
                <el-table-column property="completeAddress" label="地址"></el-table-column>
            </el-table>
            <el-pagination @size-change="handleSupplierSizeChange" @current-change="handleSupplierCurrentPage" :current-page="currentPage4" :page-sizes="pageSizes" :page-size="supplierPageSize" layout="total, sizes, prev, pager, next, jumper" :total="totalSupplier">
            </el-pagination>
            <div slot="footer" class="dialog-footer">
                <el-button @click="cancelSelectSupplier">取 消</el-button>
                <el-button type="primary" @click="setCurrentSupplierInfo(supplierCurrentRow)">确 定</el-button>
            </div>
        </el-dialog>


        <el-dialog title="货品列表" v-model="chosenGoodCode" size="small">
            <el-form :model="goodsForm">
                <el-form-item label="货品编码" :label-width="formLabelWidth">
                    <el-input v-model="goodsForm.goodsCode" auto-complete="off"></el-input>
                </el-form-item>
                <el-form-item label="货品名称" :label-width="formLabelWidth">
                    <el-input v-model="goodsForm.goodsName" auto-complete="off"></el-input>
                </el-form-item>
              <el-form-item label="" :label-width="formLabelWidth">
                    <el-button type="primary" @click="selectGoods">筛选</el-button>
                </el-form-item>
            </el-form>

            <el-table :data="goodsCodeData" highlight-current-row @current-change="handlGoodCurrentChange" style="width: 100%">
                <el-table-column type="index"></el-table-column>
                <el-table-column property="goodsType" label="货品类别"></el-table-column>
                <el-table-column property="goodsCategory" label="货品小类"></el-table-column>
                <el-table-column property="goodsCode" label="货品编码"></el-table-column>
                <el-table-column property="goodsName" label="货品名称"></el-table-column>
                <el-table-column property="goodsSpec" label="规格"></el-table-column>
                <el-table-column property="unit" label="单位"></el-table-column>
            </el-table>
            <el-pagination @size-change="handleGoodSizeChange" @current-change="handleGoodCurrentPage" :current-page="currentPage4" :page-sizes="pageSizes" :page-size="goodPageSize" layout="total, sizes, prev, pager, next, jumper" :total="totalGoods">
            </el-pagination>
            <div slot="footer" class="dialog-footer">
                <el-button @click="cancelSelectGood">取 消</el-button>
                <el-button type="primary" @click="setCurrentGoodsInfo(goodCurrentRow)">确 定</el-button>
            </div>
        </el-dialog>
          <div class="xe-pageHeader">
            基本信息
          </div>
      <el-form :model="orderForm" ref="orderForm" :rules="rules"  label-width="100px" class="demo-ruleForm">
          <div class="xe-block">
            <el-form-item label="订单日期" required prop="orderTime" class="xe-col-3">
              <el-date-picker
                      v-model="orderForm.orderTime"
                      align="right"
                      type="date"
                      placeholder="选择日期"
                      :picker-options="pickerOptions">
              </el-date-picker>
            </el-form-item>
            <el-form-item label="开单员" required prop="merchandiser" class="xe-col-3">
              <el-input v-model="orderForm.merchandiser" placeholder="请输入内容"></el-input>
            </el-form-item>
            <el-form-item label="客户名称" required prop="customerName" class="xe-col-3">
              <el-input
                      placeholder="请选择"
                      icon="search"
                      v-model="orderForm.customerName"
                      @click="chosenCus = true">
              </el-input>
            </el-form-item>
          </div>
          <div class="xe-block">
            <el-form-item label="仓库名称" required prop="wareHouse" class="xe-col-3">
              <el-select v-model="orderForm.wareHouse" placeholder="请选择">
                <el-option
                        v-for="item in wareHouseOptions"
                        :label="item.label"
                        :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="业务名称" required prop="serviceType" class="xe-col-3">
              <el-select v-model="orderForm.serviceType" placeholder="请选择">
                <el-option
                        v-for="item in serviceTypeOptions"
                        :label="item.label"
                        :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="客户订单号"  prop="customerOrderNum" class="xe-col-3">
              <el-input v-model="orderForm.customerOrderNum" placeholder="请输入内容"></el-input>
            </el-form-item>
          </div>
          <div class="xe-block">
            <el-form-item label="供应商名称" class="xe-col-3">
              <el-input
                      placeholder="请选择"
                      icon="search"
                      v-model="orderForm.supplierName"
                      v-bind:disabled = "isDisabled"
                      @click="chosenSupplier = true">
              </el-input>
            </el-form-item>
            <el-form-item label="备注" prop="notes" class="xe-col-3">
              <el-input type="textarea" placeholder="请输入内容" v-model="orderForm.notes">
            </el-form-item>
          </div>

          <div class="xe-pageHeader">
            运输信息
          </div>
          <div class="xe-block">
            <el-form-item label="预计入库时间" class="xe-col-3">
              <el-date-picker
                    v-model="orderForm.arriveTime"
                    align="right"
                    type="date"
                    placeholder="选择日期"
                    :picker-options="pickerOptions1">
            </el-date-picker>
            </el-form-item>
            <el-form-item label="是否提供运输" class="xe-col-3">
              <el-checkbox v-model="orderForm.isNeedTransport" @click="isNeedTransport = true"></el-checkbox>
            </el-form-item>
            <el-form-item label="车牌号"  prop="plateNumber" class="xe-col-3">
              <el-input v-model="orderForm.plateNumber" placeholder="请输入内容"></el-input>
            </el-form-item>
          </div>
          <div class="xe-block">
            <el-form-item label="司机姓名" prop="driverName" class="xe-col-3">
              <el-input v-model="orderForm.driverName" placeholder="请输入内容"></el-input>
            </el-form-item>
            <el-form-item label="联系电话" prop="driverContactNumber" class="xe-col-3">
              <el-input v-model="orderForm.driverContactNumber" placeholder="请输入内容"></el-input>
            </el-form-item>
          </div>
          <div class="xe-pageHeader">
            发货方信息
          </div>
          <div class="xe-block">
            <el-form-item label="名称" class="xe-col-3">
              <el-input
                      placeholder="请选择"
                      icon="search"
                      v-model="orderForm.consignorName"
                      v-bind:disabled = "isDisabled"
                      @click="chosenSend = true">
              </el-input>
            </el-form-item>
            <el-form-item label="联系人" class="xe-col-3">
              <el-input v-model="orderForm.consignorContactName" placeholder="请输入内容" :readOnly="true"></el-input>
            </el-form-item>
            <el-form-item label="联系电话" class="xe-col-3">
              <el-input v-model="orderForm.consignorPhoneNumber" placeholder="请输入内容" :readOnly="true"></el-input>
            </el-form-item>
          </div>
          <div class="xe-block">
            <el-form-item label="地址" class="xe-col-3">
              <el-input v-model="orderForm.consignorAddress" placeholder="请输入内容" :readOnly="true"></el-input>
            </el-form-item>
          </div>
          <div class="xe-pageHeader">
            货品信息
          </div>
          <div class="block" style="float:right;">
            <el-button type="primary" @click="add">添加货品</el-button>
          </div>
          <el-table :data="goodsData" border highlight-current-row @current-change="GoodsCurrentChange" style="width: 100%">
            <el-table-column type="index"></el-table-column>
            <el-table-column property="goodsType" label="货品种类">
              <template scope="scope">
                <el-select size="small" v-model="scope.row.goodsType" placeholder="请选择"  >
                  <el-option
                          v-for="item in goodsMsgOptions"
                          :label="item.label"
                          :value="item.value"
                          style="width:100px;">
                  </el-option>
                </el-select>
              </template>
            </el-table-column>
            <el-table-column property="goodsCategory" label="货品类别">
              <template scope="scope">
                <el-select  size="small" v-model="scope.row.goodsCategory" @visible-change="getGoodsCategory(scope.row)" placeholder="请选择">
                  <el-option
                          v-for="subitem in goodsCategoryOptions"
                          :label="subitem.label"
                          :value="subitem.value"
                          style="width:100px;">
                  </el-option>
                </el-select>
              </template>
            </el-table-column>
            <el-table-column property="goodsCode" label="货品编码">
              <template scope="scope">
                <el-input
                        placeholder="请选择"
                        icon="search"
                        v-model="scope.row.goodsCode"
                        v-bind:disabled = "isDisabled"
                        @click="openGoodsList(scope.row)">
                </el-input>
              </template>
            </el-table-column>
            <el-table-column property="goodsName" label="货品名称">
              <template scope="scope">
                <el-input v-model="scope.row.goodsName" placeholder="请输入内容"></el-input>
              </template>
            </el-table-column>
            <el-table-column property="goodsSpec" label="规格">
              <template scope="scope">
                <el-input v-model="scope.row.goodsSpec" placeholder="请输入内容"></el-input>
              </template>
            </el-table-column>
            <el-table-column property="unit" label="单位">
              <template scope="scope">
                <el-input v-model="scope.row.unit" placeholder="请输入内容"></el-input>
              </template>
            </el-table-column>
            <el-table-column property="quantity" label="入库数量">
              <template scope="scope">
                <el-input v-model="scope.row.quantity" placeholder="请输入内容"></el-input>
              </template>
            </el-table-column>
            <el-table-column property="unitPrice" label="单价">
              <template scope="scope">
                <el-input v-model="scope.row.unitPrice" placeholder="请输入内容"></el-input>
              </template>
            </el-table-column>
            <el-table-column property="productionBatch" label="批次号">
              <template scope="scope">
                <el-input v-model="scope.row.productionBatch" placeholder="请输入内容"></el-input>
              </template>
            </el-table-column>
            <el-table-column property="productionTime" label="生产日期">
              <template scope="scope">
                <el-date-picker
                        v-model="scope.row.productionTime"
                        align="right"
                        type="date"
                        placeholder="选择日期"
                        :picker-options="pickerOptions1">
                </el-date-picker>
              </template>
            </el-table-column>
            <el-table-column property="invalidTime" label="失效日期">
              <template scope="scope">
                <el-date-picker
                        v-model="scope.row.invalidTime"
                        align="right"
                        type="date"
                        placeholder="选择日期"
                        :picker-options="pickerOptions1">
                </el-date-picker>
              </template>
            </el-table-column>
            <el-table-column property="goodsOperation" label="操作">
              <template scope="scope">
                <el-button type="text" @click="deleteRow(scope.$index, goodsData)">删除</el-button>
              </template>
            </el-table-column>
          </el-table>
        <div class="block">
          <el-button type="primary" @click="submitForm('orderForm')">确认下单</el-button>
        </div>

        </el-form>
    </div>
</div>
</body>
<script>
    new Vue({
        el: '#app',
        data :function() {
            var validateOrdeTime = function(rule, value, callback){
                
                if(value.getTime()<new Date().getTime() - 3600 * 1000 * 24 * 7){
                    callback(new Error('只能选择一周之前的日期!'));
                }else if(value.getTime()>new Date().getTime()){
                    callback(new Error('只能选择一周之前的日期!'));
                }else{
                    callback();
                }
            };
          var checkPhoneOrMobile = function(rule, value, callback) {
            if(value!==""){
              var mp=/^1\d{10}$/;
              var pp=/^\d{3,4}-\d{3,8}(-\d{3,4})?$/;
              var phone = pp.test(value)||mp.test(value);
              if(phone!==true){
                callback(new Error('请正确输入联系电话'));
              }else{
                callback();
              }
            }else{
                callback();
            }
          };
            return {
                orderCode:'',
                wareHouseName:'',
                wareHouseCode:'',
                goodsCode:'',
                goodsName:'',
                goodsSpec:'',
                unit:'',
                total:0,
                totalSupplier:0,
                totalConsignor:0,
                totalGoods:0,
                customerPageSize:10,
                goodPageSize:10,
                currentGoodPage:1,
                currentCustomerPage:1,
                supplierPageSize:10,
                consignorPageSize:10,
                currentSupplierPage:1,
                currentConsignorPage:1,
                chosenClassOptions: [],
                currentCust: [],
                goodsCategoryOptions:[],
                goodsType:'',
                goodsCategory:'',
                invalidTime:'',
                productionTime:'',
                notes:'',
                radio1: '选中且禁用',
                chosenSupplier:false,
                chosenGoodCode:false,
                fax:'',
                email:'',
                postCode:'',
                currentRowData : '',
                wareHouseOptions:[],
                serviceTypeOptions: [{
                    value: '620',
                    label: '采购入库'
                }, {
                    value: '621',
                    label: '调拨入库'
                }, {
                    value: '622',
                    label: '退货入库'
                }, {
                    value: '623',
                    label: '加工入库'
                }, {
                    value: '624',
                    label: '盘盈入库'
                }, {
                    value: '625',
                    label: '流通入库'
                }, {
                    value: '626',
                    label: '其他入库'
                }],
                goodsMsgOptions: [],
                pickerOptions1: {
                    shortcuts: [{
                        text: '今天',
                        onClick:function(picker) {
                            picker.$emit('pick', new Date());
                        }
                    }, {
                        text: '昨天',
                        onClick:function(picker) {
                            const date = new Date();
                            date.setTime(date.getTime() - 3600 * 1000 * 24);
                            picker.$emit('pick', date);
                        }
                    }, {
                        text: '一周前',
                        onClick:function(picker) {
                            const date = new Date();
                            date.setTime(date.getTime() - 3600 * 1000 * 24 * 7);
                            picker.$emit('pick', date);
                        }
                    }]
                },
                pickerOptions: {
                    shortcuts: [{
                        text: '今天',
                        onClick:function(picker) {
                            picker.$emit('pick', new Date());
                        }
                    }, {
                        text: '昨天',
                        onClick:function(picker) {
                            const date = new Date();
                            date.setTime(date.getTime() - 3600 * 1000 * 24);
                            picker.$emit('pick', date);
                        }
                    }, {
                        text: '一周前',
                        onClick:function(picker) {
                            const date = new Date();
                            date.setTime(date.getTime() - 3600 * 1000 * 24 * 7);
                            picker.$emit('pick', date);
                        }
                    }]
                },
                customerData: [],
                consignorData:[],
                currentRow: null,
                chosenCus: false,
                pageSizes:[10, 20, 30, 40,50],
                chosenCusForm: {
                    name: ''
                },
                formLabelWidth: '100px',
                currentPage4: 4,
                chosenSend: false,
                consignorCurrentRow:null,
                supplierCurrentRow:null,
                goodCurrentRow:null,
                supplierData:[],
                supplierForm:{
                    supplierName:'',
                    contactName:'',
                    contactPhone:''
                },
                goodsForm:{
                    goodsCode:'',
                    goodsName:''
                },
                consignorForm: {
                    consignorName: '',
                    consignorContactName: '',
                    consignorPhoneNumber:''
                },
                isDisabled: false,
                isDisabled11: false,
                goodsData: [],
                goodsCodeData: [],
                goodsCurrentRow: null,
                orderForm:{
                    orderTime:'',
                    merchandiser:'',
                    customerName:'',
                    customerCode:'',
                    wareHouse:'',
                    serviceType:'',
                    customerOrderNum:'',
                    notes:'',
                    supplierName:'',
                    supplierCode:'',
                    arriveTime:'',
                    isNeedTransport:false,
                    plateNumber:'',
                    driverName:'',
                    driverContactNumber:'',
                    consignorName:'',
                    consignorPhoneNumber:'',
                    consignorAddress:'',
                    consignorCode:'',
                    consignorType:'',
                    consignorContactCode:'',
                    consignorContactName:'',
                    consignorAddressCode:''
                },
                rules: {
                    orderTime:[
                        { type: 'date', required: true, message: '请选择日期', trigger: 'blur' },
                        {validator: validateOrdeTime, trigger: 'blur'}
                    ],
                    merchandiser:[
                        { required: true, message: '请输入开单员', trigger: 'blur' },
                        { min: 1, max: 50, message: '长度在 1 到 50 个字符', trigger: 'change' }
                    ],
                    customerName:[
                        { required: true, message: '请输入客户名称', trigger: 'change' },
                        { min: 1, max: 100, message: '长度在 1 到 100 个字符', trigger: 'change' }
                    ],
                    wareHouse:[
                        { required: true, message: '请选择仓库名称', trigger: 'change' }
                    ],
                    serviceType:[
                        { required: true, message: '请选择业务类型', trigger: 'change' }
                    ],
                    customerOrderNum:[
                        { min: 0, max: 30, message: '长度在 0 到 30 个字符', trigger: 'change' }
                    ],
                    notes:[
                        { min: 0, max: 200, message: '长度在 0 到 200 个字符', trigger: 'change' }
                    ],
                    plateNumber:[
                        { min: 0, max: 30, message: '长度在 0 到 30 个字符', trigger: 'change' }
                    ],
                    driverName:[
                        { min: 0, max: 30, message: '长度在 0 到 30 个字符', trigger: 'change' }
                    ],
                    driverContactNumber:[
                        {validator: checkPhoneOrMobile, trigger: 'blur'},
                        { min: 0, max: 30, message: '长度在 0 到 30 个字符', trigger: 'change' }
                    ]
                }

            };
        },

        beforeMount:function(){
            var vueObj=this;
            var url=window.location.href;
            CommonClient.syncpost(sys.rootPath + "/ofc/getCscGoodsTypeList",{"pid":null},function(result) {
                var data=eval(result);
                vueObj.goodsMsgOptions=[];
                $.each(data,function (index,CscGoodsTypeVo) {
                    var good={};
                    good.label=CscGoodsTypeVo.goodsTypeName;
                    good.value=CscGoodsTypeVo.id;
                    vueObj.goodsMsgOptions.push(good);
                });
            });
            if(url.indexOf("?")!=-1){
                var param=url.split("?")[1].split("=");
                if(param[0]=="orderCode"){
                    var orderCode=param[1];
                    CommonClient.post(sys.rootPath + "/ofc/orderStorageDetails", {"orderCode":orderCode}, function(result) {
                        if(result==undefined||result==null||result.result==null){
                            layer.msg("订单详情查询失败");
                            return;
                        }else if(result.code == 200){
                            var ofcFundamentalInformation=result.result.ofcFundamentalInformation;
                            var ofcWarehouseInformation=result.result.ofcWarehouseInformation;
                            var ofcGoodsDetailsInfo=result.result.ofcGoodsDetailsInfo;
                            var ofcDistributionBasicInfo=result.result.ofcDistributionBasicInfo;
                            if(ofcFundamentalInformation!=null){
                                vueObj.orderCode=ofcFundamentalInformation.orderCode;
                                
                                vueObj.orderForm.orderTime=DateUtil.parse(ofcFundamentalInformation.orderTime);
                                vueObj.orderForm.merchandiser=ofcFundamentalInformation.merchandiser;
                                vueObj.orderForm.customerName=ofcFundamentalInformation.custName;
                                vueObj.orderForm.customerCode=ofcFundamentalInformation.custCode;
                                vueObj.orderForm.customerOrderNum=ofcFundamentalInformation.custOrderCode;
                                vueObj.orderForm.serviceType=ofcFundamentalInformation.businessType;
                                vueObj.orderForm.notes=ofcFundamentalInformation.notes;
                                if(ofcWarehouseInformation!=null){
                                    var wareHouse={};
                                    wareHouse.label= ofcWarehouseInformation.warehouseName;
                                    wareHouse.value= ofcWarehouseInformation.warehouseCode;
                                    vueObj.wareHouseOptions.push(wareHouse);
                                    vueObj.orderForm.wareHouse=ofcWarehouseInformation.warehouseCode;
                                    vueObj.orderForm.supplierName=ofcWarehouseInformation.supportName;
                                    vueObj.orderForm.supplierCode=ofcWarehouseInformation.supportCode;
                                    vueObj.orderForm.arriveTime=DateUtil.parse(ofcWarehouseInformation.arriveTime);
                                    vueObj.orderForm.plateNumber=ofcWarehouseInformation.plateNumber;
                                    vueObj.orderForm.driverName=ofcWarehouseInformation.driverName;
                                    vueObj.orderForm.driverContactNumber=ofcWarehouseInformation.contactNumber;
                                    if(ofcWarehouseInformation.provideTransport=="1"){
                                        if(ofcDistributionBasicInfo!=null){
                                            //发货方
                                            vueObj.orderForm.consignorName=ofcDistributionBasicInfo.consignorName;
                                            vueObj.orderForm.consignorCode=ofcDistributionBasicInfo.consignorCode;
                                            vueObj.orderForm.consignorContactCode=ofcDistributionBasicInfo.consignorContactCode;
                                            vueObj.orderForm.consignorContactName=ofcDistributionBasicInfo.consignorContactName;
                                            vueObj.orderForm.consignorPhoneNumber=ofcDistributionBasicInfo.consignorContactPhone;
                                            vueObj.orderForm.isNeedTransport=true;
                                            vueObj.orderForm.consignorAddress=ofcDistributionBasicInfo.departurePlace;
                                            vueObj.orderForm.consignorAddressCode=ofcDistributionBasicInfo.departurePlaceCode;
                                        }
                                    }
                                    if(ofcGoodsDetailsInfo!=null&&ofcGoodsDetailsInfo.length>0){
                                        for(var i=0;i<ofcGoodsDetailsInfo.length;i++){
                                            var goodDetail=ofcGoodsDetailsInfo[i];
                                            var good={};
                                            good.goodsType=goodDetail.goodsType;
                                            good.goodsCategory=goodDetail.goodsCategory;
                                            var p={};
                                            p.label=goodDetail.goodsCategory;
                                            p.value=goodDetail.goodsCategory;
                                            vueObj.goodsCategoryOptions.push(p);
                                            good.goodsCode=goodDetail.goodsCode;
                                            good.goodsName=goodDetail.goodsName;
                                            good.goodsSpec=goodDetail.goodsSpec;
                                            good.quantity=goodDetail.quantity;
                                            good.unitPrice=goodDetail.unitPrice;
                                            good.productionBatch=goodDetail.productionBatch;
                                            good.productionTime=DateUtil.parse(goodDetail.productionTime);
                                            good.invalidTime=DateUtil.parse(goodDetail.invalidTime);
                                            vueObj.goodsData.push(good);
                                        }
                                    }
                                }
                            }
                        }
                    });
                }
            }
        },
        methods: {
            handleCurrentChange:function(val) {
                this.currentRow = val;
            },
            handlSuppliereCurrentChange:function(val) {
                this.supplierCurrentRow = val;
            },
            handlGoodCurrentChange:function(val) {
                this.goodCurrentRow = val;
            },
            setCurrentCustInfo:function(val) {
                this.orderForm.customerName = val.customerName;
                this.orderForm.customerCode=val.customerCode;
                this.chosenCus = false;
                this.loadWareHouseByCustomerCode();

            },
            loadWareHouseByCustomerCode:function(){
                var vueObj=this;
                CommonClient.syncpost(sys.rootPath + "/ofc/queryWarehouseByCustomerCode", {"customerCode":vueObj.customerCode}, function(result) {
                    var data=result.result;
                    if (data == undefined || data == null || data.length ==0) {
                        layer.msg("暂时未查询到该客户下的仓库信息！！");
                    } else if (result.code == 200) {
                        $.each(data,function (index,rmcWarehouseRespDto) {
                            var warehouse={};
                            warehouse.label=rmcWarehouseRespDto.warehouseName;
                            warehouse.value= rmcWarehouseRespDto.warehouseCode;
                            vueObj.wareHouseOptions.push(warehouse);
                        });
                    } else if (result.code == 403) {
                        alert("没有权限")
                    } else {
                        alert(result.message);
                    }
                },"json");
            },
            consignorHandleCurrentChange:function(val) {
                this.consignorCurrentRow=val;
            },
            handleCustomerSizeChange:function(val) {
                this.customerPageSize=val;
                this.selectCustomer();
            },
            handleCustomerCurrentPage:function(val) {
                this.currentCustomerPage = val;
                this.selectCustomer();
            },
            add:function() {
                if(!this.orderForm.customerName){
                    alert("请选择客户!");
                    return;
                }
                var vueObj=this;
                var newData = {
                    goodsType: '',
                    goodsCategory: '',
                    goodsCode: '',
                    goodsName: '',
                    goodsSpec: '',
                    unit: '',
                    quantity: '',
                    unitPrice:'',
                    productionBatch:'',
                    productionTime:'',
                    invalidTime:'',
                    goodsOperation: '',
                    goodsCategoryOptions: []
                };
                CommonClient.syncpost(sys.rootPath + "/ofc/getCscGoodsTypeList",{"pid":null},function(result) {
                    var data=eval(result);
                    vueObj.goodsMsgOptions=[];
                    $.each(data,function (index,CscGoodsTypeVo) {
                        var good={};
                        good.label=CscGoodsTypeVo.goodsTypeName;
                        good.value=CscGoodsTypeVo.id;
                        vueObj.goodsMsgOptions.push(good);
                    });
                });
                vueObj.goodsData.push(newData);
            },
            deleteRow:function(index, rows) {
                rows.splice(index, 1);
            },
            getGoodsCategory:function(val) {
                var vueObj=this;
                var typeId=val.goodsType;
                this.goodsType=typeId;
                CommonClient.syncpost(sys.rootPath + "/ofc/getCscGoodsTypeList",{"cscGoodsType":typeId},function(data) {
                    data=eval(data);
                    vueObj.goodsCategoryOptions=[];
                    $.each(data,function (index,CscGoodsTypeVo) {
                        var goodClass={};
                        goodClass.label=CscGoodsTypeVo.goodsTypeName;
                        goodClass.value=CscGoodsTypeVo.goodsTypeName;
                        vueObj.goodsCategoryOptions.push(goodClass);
                    });
                });
            },
            GoodsCurrentChange:function(val) {
                this.goodsCurrentRow = val;
            },
            goodsCodeClick:function() {
                console.log('弹窗');
            },
            selectSupplier:function(){
                if(!this.orderForm.customerName){
                    alert("请选择客户");
                    this.chosenSupplier=false;
                    return;
                }
                this.supplierData=[];
                var vueObj=this;
                CommonClient.post(sys.rootPath + "/ofc/supplierSelect",vueObj.supplierForm, function(result) {
                    var data=eval(result);
                    $.each(data,function (index,CscSupplierInfoDto) {
                        var supplier={};
                        supplier.supplierName=StringUtil.nullToEmpty(CscSupplierInfoDto.supplierName);
                        supplier.contactName=StringUtil.nullToEmpty(CscSupplierInfoDto.contactName);
                        supplier.contactPhone=StringUtil.nullToEmpty(CscSupplierInfoDto.contactPhone);
                        supplier.fax=StringUtil.nullToEmpty(CscSupplierInfoDto.fax);
                        supplier.email=StringUtil.nullToEmpty(CscSupplierInfoDto.email);
                        supplier.postCode=StringUtil.nullToEmpty(CscSupplierInfoDto.postCode);
                        supplier.supplierCode=StringUtil.nullToEmpty(CscSupplierInfoDto.supplierCode);
                        supplier.completeAddress=StringUtil.nullToEmpty(CscSupplierInfoDto.completeAddress);
                        vueObj.supplierData.push(supplier);
                    });
                },"json");
                this.totalSupplier=data.result.size;
            },
            handleSupplierSizeChange:function(){

            },
            handleSupplierCurrentPage:function(){

            },

            setCurrentSupplierInfo:function(val){
                this.orderForm.supplierName=val.supplierName;
                this.orderForm.supplierCode=val.supplierCode;
                this.chosenSupplier=false;
            },
            setCurrentGoodsInfo:function(val){
                this.currentRowData.goodsCode=val.goodsCode;
                this.currentRowData.goodsName=val.goodsName;
                this.currentRowData.goodsSpec=val.goodsSpec;
                this.currentRowData.unit=val.unit;
                this.chosenGoodCode = false;
            },
            cancelSelectSupplier:function(){
                this.supplierData=[];
                this.chosenSupplier=false;
            },
            selectConsignor:function(){
                if(!this.orderForm.customerName){
                    alert("请选择客户");
                    this.chosenSend=false;
                    return;
                }
                this.consignorData=[];
                var vueObj=this;
                var cscContactDto = {};
                var cscContactCompanyDto = {};
                var cscContantAndCompanyDto={};
                cscContactCompanyDto.contactCompanyName = this.consignorForm.consignorName;
                cscContactDto.purpose = "2";
                cscContactDto.contactName = this.consignorForm.consignorContactName;
                cscContactDto.phone = this.consignorForm.consignorPhoneNumber;
                cscContantAndCompanyDto.cscContactDto = cscContactDto;
                cscContantAndCompanyDto.cscContactCompanyDto = cscContactCompanyDto;
                var customerCode = this.orderForm.customerCode;
                cscContantAndCompanyDto.pageNum=this.currentConsignorPage;
                cscContantAndCompanyDto.pageSize=this.consignorPageSize;
                cscContantAndCompanyDto = JSON.stringify(cscContantAndCompanyDto);
                CommonClient.post(sys.rootPath + "/ofc/contactSelectForPage",{"cscContantAndCompanyDto":cscContantAndCompanyDto,"customerCode":customerCode}, function(result) {
                    if (result == undefined || result == null || result.result ==null || result.result.size == 0 || result.result.list == null) {
                        layer.msg("暂时未查询到发货方信息！！");
                    } else if (result.code == 200) {
                        $.each(result.result.list,function (index,CscContantAndCompanyDto) {
                            var consignor={};
                            if(CscContantAndCompanyDto.type=="1"){
                                consignor.consignorType="公司";
                            }else{
                                CscContantAndCompanyDto.consignorType="个人";
                            }
                            consignor.consignorName=CscContantAndCompanyDto.contactCompanyName;
                            consignor.consignorContactName=CscContantAndCompanyDto.contactName;
                            consignor.consignorPhoneNumber=CscContantAndCompanyDto.phone;
                            consignor.consignorAddress=CscContantAndCompanyDto.provinceName+","+CscContantAndCompanyDto.cityName+","+CscContantAndCompanyDto.areaName;
                            if(CscContantAndCompanyDto.streetName!=null){
                                consignor.consignorAddress=consignor.consignorAddress+","+CscContantAndCompanyDto.streetName;
                            }
                            consignor.consignorContactCode=CscContantAndCompanyDto.contactCode;
                            consignor.consignorCode=CscContantAndCompanyDto.contactCompanyCode;
                            consignor.consignorAddressCode=CscContantAndCompanyDto.province+","+CscContantAndCompanyDto.city+","+CscContantAndCompanyDto.area;
                            if(CscContantAndCompanyDto.street!=null){
                                consignor.consignorAddressCode=consignor.consignorAddressCode+","+CscContantAndCompanyDto.street;
                            }
                            vueObj.consignorData.push(consignor);
                        });
                        vueObj.totalConsignor=result.result.total;
                    } else if (result.code == 403) {
                        alert("没有权限")
                    }
                },"json");
            },

            handleConsignorSizeChange:function(val){
                this.consignorPageSize=val;
                this.selectConsignor();
            },
            handleConsignorCurrentPage:function(val){
                this.currentConsignorPage=val;
                this.selectConsignor();
            },
            cancelSelectConsignor:function(){
                this.consignorData=[];
                this.consignorPageSize=10;
                this.totalConsignor=0;
                this.chosenSend=false;
            },
            setCurrentConsignorInfo:function(val){
                this.orderForm.consignorName=val.consignorName;
                this.orderForm.consignorPhoneNumber=val.consignorPhoneNumber;
                this.orderForm.consignorContactName=val.consignorContactName;
                this.orderForm.consignorAddress=val.consignorAddress;
                this.orderForm.consignorType=val.type;
                this.orderForm.consignorCode=val.consignorCode;
                this.orderForm.consignorContactCode=val.consignorContactCode;
                this.orderForm.consignorAddressCode=val.consignorAddressCode;
                this.chosenSend = false;
            },
            selectGoods:function(){
                this.goodsCodeData=[];
                var vueObj=this;
                var cscGoods = {};
                var customerCode = vueObj.customerCode;
                cscGoods.goodsCode = vueObj.goodsForm.goodsCode;
                cscGoods.goodsName = vueObj.goodsForm.goodsName;
                cscGoods.pNum=vueObj.currentGoodPage;
                cscGoods.pSize =vueObj.goodPageSize;
                var param = JSON.stringify(cscGoods);
                CommonClient.post(sys.rootPath + "/ofc/goodsSelects", {"cscGoods":param,"customerCode":customerCode}, function(data) {
                    if (data == undefined || data == null || data.result ==null || data.result.size == 0 || data.result.list == null) {
                        layer.msg("暂时未查询到货品信息！！");
                    } else if (data.code == 200) {
                        $.each(data.result.list,function (index,cscGoodsVo) {
                            var goodCode={};
                            goodCode.goodsType=cscGoodsVo.goodsTypeParentName;
                            goodCode.goodsCategory=cscGoodsVo.goodsTypeName;
                            goodCode.goodsCode=cscGoodsVo.goodsCode;
                            goodCode.goodsName=cscGoodsVo.goodsName;
                            goodCode.goodsSpec=cscGoodsVo.specification;
                            goodCode.unit=cscGoodsVo.unit;
                            vueObj.goodsCodeData.push(goodCode);
                        });
                        vueObj.totalGoods=data.result.total;
                    } else if (data.code == 403) {
                        alert("没有权限")
                    }
                },"json");
            },
            handleGoodSizeChange:function(val){
                this.goodPageSize=val;
                this.selectGoods();
            },
            handleGoodCurrentPage:function(val){
                this.currentGoodPage=val;
                this.selectGoods();
            },
            cancelSelectGood:function(){
                this.goodsCodeData=[];
                this.customerPageSize=10;
                this.total=0;
                this.chosenGoodCode=false;
            },
            cancelSelectCustomer:function(){
                this.customerData=[];
                this.customerPageSize=10;
                this.total=0;
                this.chosenCus=false;
            },
            selectCustomer:function(){
                var param = {};
                param.pageNum = this.currentCustomerPage;
                param.pageSize=this.customerPageSize;
                param.custName = this.chosenCusForm.name;
                this.customerData=[];
                var vueObj=this;
                CommonClient.post(sys.rootPath + "/ofc/distributing/queryCustomerByName", param,
                        function(result) {
                            if (result == undefined || result == null || result.result == null ||  result.result.size == 0 || result.result.list == null) {
                                layer.msg("暂时未查询到客户信息！！");
                            } else if (result.code == 200) {
                                $.each(result.result.list,function (index,cscCustomerVo) {
                                    var channel = cscCustomerVo.channel;
                                    if(null == channel){
                                        channel = "";
                                    }
                                    var customer={};
                                    customer.customerCode=cscCustomerVo.customerCode;
                                    var custType = StringUtil.nullToEmpty(cscCustomerVo.type);
                                    if(custType == '1'){
                                        customer.type="公司";
                                    }else if (custType == '2'){
                                        customer.type="个人";
                                    }else{
                                        customer.type=custType;
                                    }
                                    customer.customerName=cscCustomerVo.customerName;
                                    customer.channel=channel;
                                    customer.productType=cscCustomerVo.productType;
                                    vueObj.customerData.push(customer);
                                });
                                vueObj.total=result.result.total;
                            } else if (result.code == 403) {
                                alert("没有权限")
                            }
                        },"json");
            },
            submitForm:function(formName) {
                var _this = this;
              this.$refs[formName].validate(function(valid){
                    if (valid) {
                       _this.saveStorage();
                    } else {
                        return false;
                    }
                });
            },
            saveStorage:function(){
                //订单基本信息
                var ofcOrderDTOStr = {};
                //发货方信息
                var cscContantAndCompanyDtoConsignorStr = {};
                //收货方信息
                var cscContantAndCompanyDtoConsigneeStr={};

                var cscContactDto={};
                //供应商信息
                var cscSupplierInfoDtoStr={};

                //是否提供运输
                if(this.orderForm.isNeedTransport){
                    ofcOrderDTOStr.provideTransport="1";
                    if(!this.orderForm.consignorName){
                        alert("提供运输时,发货方不能为空，请选择发货方");
                        return;
                    }
                }else{
                    ofcOrderDTOStr.provideTransport="0";
                }
                //订单基本信息
                ofcOrderDTOStr.businessType =this.orderForm.serviceType;
                ofcOrderDTOStr.merchandiser = this.orderForm.merchandiser;
                if(this.orderForm.orderTime){
                    ofcOrderDTOStr.orderTime=DateUtil.format(this.orderForm.orderTime, "yyyy-MM-dd HH:mm:ss");
                }
                //订单基本信息
                ofcOrderDTOStr.orderCode=this.orderCode;
                ofcOrderDTOStr.custName = this.orderForm.customerName;
                ofcOrderDTOStr.custCode =this.orderForm.customerCode;
                ofcOrderDTOStr.custOrderCode =this.orderForm.customerOrderNum;
                ofcOrderDTOStr.notes =this.orderForm.notes;
                //仓库信息
                ofcOrderDTOStr.supportName=this.orderForm.supplierName;//供应商名称
                cscSupplierInfoDtoStr.supportName==this.orderForm.supplierName;
                ofcOrderDTOStr.supportCode=this.orderForm.supplierCode;//供应商编码
                cscSupplierInfoDtoStr.supportCode==this.orderForm.supplierCode;
                ofcOrderDTOStr.warehouseName=this.getWareHouseNameByCode(this.orderForm.wareHouse);//仓库名称
                ofcOrderDTOStr.warehouseCode=this.orderForm.wareHouse;//仓库编码
                if(!ofcOrderDTOStr.warehouseCode){
                    ofcOrderDTOStr.warehouseCode=this.orderForm.wareHouseCode;
                }
                if(this.orderForm.arriveTime){
                    ofcOrderDTOStr.arriveTime=DateUtil.format(this.orderForm.arriveTime, "yyyy-MM-dd HH:mm:ss");
                }
                ofcOrderDTOStr.plateNumber=this.orderForm.plateNumber;
                ofcOrderDTOStr.driverName=this.orderForm.driverName;
                ofcOrderDTOStr.contactNumber=this.orderForm.driverContactNumber;
                //发货方信息
                ofcOrderDTOStr.consignorName=this.orderForm.consignorName;
                ofcOrderDTOStr.consignorCode=this.orderForm.consignorCode;
                ofcOrderDTOStr.consignorType=this.orderForm.consignorType;
                ofcOrderDTOStr.consignorContactCode=this.orderForm.consignorContactCode;
                ofcOrderDTOStr.consignorContactName=this.orderForm.consignorContactName;
//                if(this.orderForm.consignorPhoneNumber){
//                    if(!this.checkPhoneOrMobile(this.orderForm.consignorPhoneNumber)){
//                        alert("请输入正确的发货方联系方式");
//                        return;
//                    }
//                }
                ofcOrderDTOStr.consignorContactPhone=this.orderForm.consignorPhoneNumber;
                cscContantAndCompanyDtoConsignorStr=this.getCscContantAndCompanyDtoConsignorStr();
                //出发地
                ofcOrderDTOStr.departurePlace=this.orderForm.consignorAddress;
                var consignorAddressNameMessage =this.orderForm.consignorAddress.split(',');
                ofcOrderDTOStr.departureProvince=consignorAddressNameMessage[0];
                ofcOrderDTOStr.departureCity=consignorAddressNameMessage[1];
                ofcOrderDTOStr.departureDistrict=consignorAddressNameMessage[2];
                if(!StringUtil.isEmpty(consignorAddressNameMessage[3])){
                    ofcOrderDTOStr.departureTowns=consignorAddressNameMessage[3];
                }
                ofcOrderDTOStr.departurePlaceCode=this.orderForm.consignorAddressCode;
                var goodsTable =this.goodsData;
                var goodDetail=[];

                if(goodsTable.length <1){
                    alert('请添加至少一条货品!');
                    return;
                }
                //校验金额和格式化日期时间
                for(var i=0;i<goodsTable.length;i++){
                    var good=goodsTable[i];
                    if(good.unitPrice!=""){
                        if(isNaN(good.unitPrice)){
                            alert("货品单价必须为数字");
                            return;
                        }
                        if(good.unitPrice>99999.99||good.unitPrice<0){
                            alert("货品单价不能大于99999.99或小于0");
                            return;
                        }
                        if(isNaN(good.quantity)){
                            alert("货品数量必须为数字");
                            return;
                        }
                    }
                    if(good.quantity>99999.999||good.quantity<0||!good.quantity||good.quantity==0){
                        if(!good.quantity){
                            alert("货品数量不能为空");
                            return;
                        }
                        if(good.quantity>99999.999){
                            alert("货品数量不能大于99999.999");
                            return;
                        }
                        if(good.quantity<0){
                            alert("货品数量不能小于0");
                            return;
                        }
                        if(good.quantity==0){
                            alert("货品数量不能为0");
                            return;
                        }
                        return;
                    }
                    if( good.productionTime&& good.invalidTime){
                        if( good.productionTime.getTime()> good.invalidTime.getTime()){
                            alert("生产日期不能大于失效日期");
                            return;
                        }
                    }
                    goodDetail.push(good);

                }
                if(goodDetail.length <1){
                    alert('请添加至少一条货品!');
                    return;
                }
                var ofcOrderDto = JSON.stringify(ofcOrderDTOStr);
                var orderGoodsListStr = JSON.stringify(goodDetail);
                var tag="edit";
                xescm.common.submit("/ofc/saveStorage"
                        ,{"ofcOrderDTOStr":ofcOrderDto
                            ,"orderGoodsListStr":orderGoodsListStr
                            ,"cscContantAndCompanyDtoConsignorStr":cscContantAndCompanyDtoConsignorStr
                            ,"cscContantAndCompanyDtoConsigneeStr":cscContantAndCompanyDtoConsigneeStr
                            ,"cscSupplierInfoDtoStr":cscSupplierInfoDtoStr
                            ,"tag":tag
                        }
                        ,"您确认提交订单吗?"
                        ,function () {
                            location.reload();
                            var url=window.location.href;
                            if(url.indexOf("?")!=-1){
                                var param=url.split("?")[1].split("=");
                            }
                            if(param[1]=="manager"){
                                var newurl = "/ofc/orderStorageInManager/";
                                var html = window.location.href;
                                var index = html.indexOf("/index#");
                                window.open(html.substring(0,index) + "/index#" + newurl);
                            }
                        });
            },
            getCscContantAndCompanyDtoConsignorStr:function(){
                var paramConsignor = {};
                var cscContactDto = {};
                var cscContactCompanyDto = {};
                cscContactCompanyDto.contactCompanyName = this.orderForm.consignorName;
                cscContactDto.contactName = this.orderForm.consignorContactName;
                cscContactDto.purpose = "2";
                cscContactDto.phone =this.orderForm.consignorPhoneNumber;
                cscContactDto.contactCompanyName = this.orderForm.consignorName;
                var consignorAddressCodeMessage = this.orderForm.consignorAddressCode.split(',');
                var consignorAddressNameMessage =this.orderForm.consignorAddress.split(',');
                cscContactDto.province = consignorAddressCodeMessage[0];
                cscContactDto.city = consignorAddressCodeMessage[1];
                cscContactDto.area = consignorAddressCodeMessage[2];
                if(!StringUtil.isEmpty(consignorAddressCodeMessage[3])){
                    cscContactDto.street = consignorAddressCodeMessage[3];
                }
                cscContactDto.provinceName = consignorAddressNameMessage[0];
                cscContactDto.cityName = consignorAddressNameMessage[1];
                cscContactDto.areaName = consignorAddressNameMessage[2];
                if(!StringUtil.isEmpty(consignorAddressNameMessage[3])){
                    cscContactDto.streetName = consignorAddressNameMessage[3];
                }

                cscContactDto.address=this.orderForm.consignorAddress;
                paramConsignor.cscContactDto = cscContactDto;
                paramConsignor.cscContactCompanyDto = cscContactCompanyDto;
                var cscContantAndCompanyDtoConsignorStr = JSON.stringify(paramConsignor);
                return cscContantAndCompanyDtoConsignorStr;
            },
            openGoodsList: function(currentRowData) {
                this.chosenGoodCode=true;
                this.currentRowData = currentRowData;
            },
            getWareHouseNameByCode:function(val){
                for(var i=0;i<this.wareHouseOptions.length;i++){
                    var option=this.wareHouseOptions[i];
                    if(option.value==val){
                        return option.label;
                    }
                }
            }
        }
    });
</script>
</html>