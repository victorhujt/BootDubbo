<!DOCTYPE html>
<html>
<head>
  <title>入库开单</title>
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

            <el-table :data="consignorData" highlight-current-row @current-change="consignorHandleCurrentChange" border style="width: 100%">
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
                <el-form-item  label="" :label-width="formLabelWidth">
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
      <el-form label-width="100px">
            <div class="xe-block">
              <el-form-item label="订单日期" required class="xe-col-3">
                <el-form-item>
                  <el-date-picker type="date" v-model="orderTime" :picker-options="pickerOptions"></el-date-picker>
                </el-form-item>
              </el-form-item>
              <div  v-if="seenOrderDateNotNull">订单日期不能为空</div>
              <el-form-item label="开单员" required prop="merchandiser" class="xe-col-3">
                <el-input v-model="merchandiser" placeholder="请输入内容"   value="${(merchandiser)!""}"></el-input>
              </el-form-item>
              <el-form-item label="客户名称" class="xe-col-3">
                <el-input
                        placeholder="请选择"
                        icon="search"
                        v-model="customerName"
                        @click="chosenCus = true" >
                </el-input>
              </el-form-item>
            </div>
            <div class="xe-block">
              <el-form-item label="仓库名称" prop="wareHouse" class="xe-col-3">
                <el-select v-model="wareHouse" placeholder="请选择">
                  <el-option
                          v-for="item in wareHouseOptions"
                          :label="item.label"
                          :value="item.value">
                  </el-option>
                </el-select>
              </el-form-item>
              <el-form-item label="业务类型" prop="serviceType" class="xe-col-3">
                <el-select v-model="serviceType" placeholder="请选择">
                  <el-option
                          v-for="item in serviceTypeOptions"
                          :label="item.label"
                          :value="item.value">
                  </el-option>
                </el-select>
              </el-form-item>
              <el-form-item label="客户订单号" prop="customerOrderNum" class="xe-col-3">
                <el-input v-model="customerOrderNum" placeholder="请输入内容"></el-input>
              </el-form-item>
            </div>
            <div class="xe-block">
              <el-form-item label="备注" prop="notes" class="xe-col-3">
                <el-input type="textarea" placeholder="请输入内容" v-model="notes" ></el-input>
              </el-form-item>
            </div>
            <div class="xe-pageHeader">
              供应商信息
            </div>
            <div class="xe-block">
              <el-form-item label="供应商名称" class="xe-col-3">
                <el-input
                        placeholder="请选择"
                        icon="search"
                        v-model="supplierName"
                        v-bind:disabled = "isDisabled"
                        @click="chosenSupplier = true">
                </el-input>
              </el-form-item>
            </div>
            <div class="xe-pageHeader">
              运输信息
            </div>
            <div class="xe-block">
              <el-form-item label="预计入库时间" required class="xe-col-3">
                <el-date-picker
                        v-model="arriveTime"
                        align="right"
                        type="date"
                        placeholder="选择日期"
                        :picker-options="pickerOptions1">
                </el-date-picker>
              </el-form-item>
              <el-form-item label="是否提供运输">
                <el-checkbox v-model="isNeedTransport" @click="isNeedTransport = true"></el-checkbox>
              </el-form-item>
            </div>
            <div class="xe-block">
              <el-form-item label="车牌号" class="xe-col-3">
                <el-input v-model="plateNumber" placeholder="请输入内容"></el-input>
              </el-form-item>
              <el-form-item label="司机姓名" class="xe-col-3">
                <el-input v-model="driverName"  placeholder="请输入内容"></el-input>
              </el-form-item>
              <el-form-item label="联系电话" class="xe-col-3">
                <el-input v-model="driverContactNumber"  placeholder="请输入内容"></el-input>
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
                        v-model="consignorName"
                        v-bind:disabled = "isDisabled"
                        @click="chosenSend = true">
                </el-input>
              </el-form-item>
              <el-form-item label="联系人" class="xe-col-3">
                <el-input v-model="consignorContactName" placeholder="请输入内容"></el-input>
              </el-form-item>
              <el-form-item label="联系电话" class="xe-col-3">
                <el-input v-model="consignorPhoneNumber" placeholder="请输入内容"></el-input>
              </el-form-item>
            </div>
            <div class="xe-block">
              <el-form-item label="地址选择" class="xe-col-3">
                <el-input v-model="consignorAddress" placeholder="请输入内容"></el-input>
              </el-form-item>
            </div>
            <div class="xe-pageHeader">
              货品信息
            </div>
          <div style="float:right;margin-bottom:15px;">
            <el-button type="primary" @click="add">添加货品</el-button>
          </div>
            <el-table :data="goodsData" border highlight-current-row @current-change="GoodsCurrentChange" style="width: 100%">
              <el-table-column type="index"></el-table-column>
              <el-table-column property="goodsType" label="货品种类">
                <template scope="scope">
                    <el-select size="small" v-model="scope.row.goodsType" placeholder="请选择"  @change="getGoodsCategory(scope.row)">
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
                    <el-select  size="small" v-model="scope.row.goodsCategory"  placeholder="请选择">
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
                  <#--v-model="scope.row.goodsCode"-->
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
                  <el-input v-model="scope.row.productionBatch"  placeholder="请输入内容"></el-input>
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
          <el-button type="primary" @click="saveStorage" style="float:right">下单</el-button>
      </el-form>
</div>
</body>
<script>
    new Vue({
        el: '#app',
        data :function() {
            return {
                seenOrderDateNotNull:false,
                consignorCode:'',
                wareHouseObj:'',
                consignorContactCode:'',
                goodsCode:'',
                consignorType:'',
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
                customerOrderNum: '',
                customerName: '',
                consignorContactCode:'',
                consignorAddressCode:'',
                receiveName: '',
                receiveContacts: '',
                consignorName:'',
                consignorContactName:'',
                consignorPhoneNumber:'',
                consignorAddress:'',
                chosenSupplier:false,
                chosenGoodCode:false,
                supplierName:'',
                supplierCode:'',
                fax:'',
                email:'',
                postCode:'',
                receivePhone: '',
                receiveAddress: '',
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
                serviceType: '',
                wareHouse:'',
                merchandiser: '',
                orderTime: new Date(),
                arriveTime: '',
                isNeedTransport:false,
                plateNumber:'',
                driverName:'',
                driverContactNumber:'',
                customerData: [],
                consignorData:[],
                customerCode:'',
                receiveCusTableDate: '',
                currentRow: null,
                chosenCus: false,
                pageSizes:[10, 20, 30, 40,50],
                chosenCusForm: {
                    name: ''
                },
                formLabelWidth: '100px',
                currentPage4: 1,
                chosenSend: false,
                consignorCurrentRow:null,
                supplierCurrentRow:null,
                goodCurrentRow:null,
                chosenReceive: false,
                receiveCurrentRow: null,
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
                receiveForm: {
                    name: '',
                    region: '',
                    date1: '',
                    date2: '',
                    delivery: false,
                    type: [],
                    resource: '',
                    desc: ''
                },
                isDisabled: false,
                isDisabled11: false,
                goodsData: [],
                goodsCodeData: [],
                goodsCurrentRow: null
            };
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
                this.customerName = val.customerName;
                this.customerCode=val.customerCode;
                this.chosenCus = false;
                var vueObj=this;
                CommonClient.post(sys.rootPath + "/ofc/queryWarehouseByCustomerCode", {"customerCode":this.customerCode}, function(result) {
                    var data=result.result;
                    if (data == undefined || data == null || data.length ==0) {
                        layer.msg("暂时未查询到该客户下的仓库信息！！");
                    } else if (result.code == 200) {
                        $.each(data,function (index,rmcWarehouseRespDto) {
                            var rmcWarehouse = JSON.stringify(rmcWarehouseRespDto);
                            var warehouse={};
                            warehouse.label=rmcWarehouseRespDto.warehouseName;
                            warehouse.value= rmcWarehouse;
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
            receiveHandleCurrentChange:function(val) {
                this.receiveCurrentRow = val;
            },
            receiveSetCurrentCusInfo:function(val) {
                this.receiveName = val.receiveCusName;
                this.receiveContacts = val.receiveContacts;
                this.receivePhone = val.receiveNumPhone;
                this.receiveAddress = val.receiveAddress;
                this.chosenReceive = false;
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
                if(!this.customerName){
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
                val.goodsCategory = null;
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
                            if(!this.customerName && !this.customerCode){
                                alert("请选择客户");
                                this.chosenSupplier=false;
                                return;
                            }
                            this.supplierData=[];
                            var vueObj=this;
                            var param = {};
                            param = vueObj.supplierForm;
                            param.customerCode = this.customerCode;
                            CommonClient.post(sys.rootPath + "/ofc/supplierSelect",param, function(result) {
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
                this.supplierName=val.supplierName;
                this.supplierCode=val.supplierCode;
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
            deleteGood:function(){


            },

            selectConsignor:function(){
                if(!this.customerName){
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
                var customerCode = this.customerCode;
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
                this.consignorName=val.consignorName;
                this.consignorPhoneNumber=val.consignorPhoneNumber;
                this.consignorContactName=val.consignorContactName;
                this.consignorAddress=val.consignorAddress;
                this.consignorType=val.type;
                this.consignorCode=val.consignorCode;
                this.consignorContactCode=val.consignorContactCode;
                this.consignorAddressCode=val.consignorAddressCode;
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
            saveStorage:function(){
                if(!this.orderTime){
                    this.seenOrderDateNotNull=true;
                    return;
                }else{
                    if(this.orderTime.getTime()<new Date().getTime() - 3600 * 1000 * 24 * 7){
                        alert('只能选择一周之前的日期!');
                        return;
                    }
                    if(this.orderTime.getTime()>new Date().getTime()){
                        alert('只能选择一周之前的日期!');
                        return;
                    }
                }
                if(!this.customerName){
                    alert('客户名称不能为空!');
                    return;
                }
                if(!this.wareHouse){
                    alert('仓库名称不能为空!');
                    return;
                }
                if(!this.serviceType){
                    alert('业务类型不能为空!');
                    return;
                }
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
                if(this.isNeedTransport){
                    ofcOrderDTOStr.provideTransport="1";
                    if(!this.consignorName){
                        alert("提供运输时,发货方不能为空，请选择发货方");
                        return;
                    }
                }else{
                    ofcOrderDTOStr.provideTransport="0";
                }
                //订单基本信息
                ofcOrderDTOStr.businessType =this.serviceType;
                ofcOrderDTOStr.merchandiser = this.merchandiser;
                if(this.orderTime){
                    ofcOrderDTOStr.orderTime = this.formatDate(this.orderTime);
                }
                this.wareHouseObj=JSON.parse(this.wareHouse);

                //订单基本信息
                ofcOrderDTOStr.custName = this.customerName;
                ofcOrderDTOStr.custCode =this.customerCode;
                ofcOrderDTOStr.custOrderCode =this.customerOrderNum;
                ofcOrderDTOStr.notes =this.notes;
                //仓库信息
                ofcOrderDTOStr.supportName=this.supplierName;//供应商名称
                cscSupplierInfoDtoStr.supportName==this.supplierName;
                ofcOrderDTOStr.supportCode=this.supplierCode;//供应商编码
                cscSupplierInfoDtoStr.supportCode==this.supplierCode;
                ofcOrderDTOStr.warehouseName=this.wareHouseObj.warehouseName;//仓库名称
                ofcOrderDTOStr.warehouseCode=this.wareHouseObj.warehouseCode;//仓库编码
                if(this.arriveTime){
                ofcOrderDTOStr.arriveTime=this.formatDate(this.arriveTime);
                }
                ofcOrderDTOStr.plateNumber=this.plateNumber;
                ofcOrderDTOStr.driverName=this.driverName;
                ofcOrderDTOStr.contactNumber=this.driverContactNumber;

                //发货方信息
                ofcOrderDTOStr.consignorName=this.consignorName;
                ofcOrderDTOStr.consignorCode=this.consignorCode;
                ofcOrderDTOStr.consignorType=this.consignorType;
                ofcOrderDTOStr.consignorContactCode=this.consignorContactCode;
                ofcOrderDTOStr.consignorContactName=this.consignorContactName;
                ofcOrderDTOStr.consignorContactPhone=this.consignorPhoneNumber;

                //收货方信息(仓库的信息)
                ofcOrderDTOStr.consigneeName=this.wareHouseObj.warehouseName;
                ofcOrderDTOStr.consigneeCode=this.wareHouseObj.warehouseCode;
                ofcOrderDTOStr.consigneeContactName=this.wareHouseObj.contactName;
                ofcOrderDTOStr.consigneeContactPhone=this.wareHouseObj.phone;

                cscContantAndCompanyDtoConsignorStr=this.getCscContantAndCompanyDtoConsignorStr();
                cscContantAndCompanyDtoConsigneeStr=this.getCscContantAndCompanyDtoConsigneeStr(this.wareHouseObj);
                //出发地
                ofcOrderDTOStr.departurePlace=this.consignorAddress;
                var consignorAddressNameMessage =this.consignorAddress.split(',');
                ofcOrderDTOStr.departureProvince=consignorAddressNameMessage[0];
                ofcOrderDTOStr.departureCity=consignorAddressNameMessage[1];
                ofcOrderDTOStr.departureDistrict=consignorAddressNameMessage[2];
                if(!StringUtil.isEmpty(consignorAddressNameMessage[3])){
                    ofcOrderDTOStr.departureTowns=consignorAddressNameMessage[3];
                }
                ofcOrderDTOStr.departurePlaceCode=this.consignorAddressCode;
                ofcOrderDTOStr.destinationCode=this.wareHouseObj.provinceCode+","+this.wareHouseObj.cityCode+","+this.wareHouseObj.areaCode;
                if(this.wareHouseObj.streetCode){
                    ofcOrderDTOStr.destinationCode= ofcOrderDTOStr.destinationCode+","+this.wareHouseObj.streetCode;
                }

                //目的地
                ofcOrderDTOStr.destination=this.wareHouseObj.detailAddress;
                ofcOrderDTOStr.destinationProvince=this.wareHouseObj.province;
                ofcOrderDTOStr.destinationCity=this.wareHouseObj.city;
                ofcOrderDTOStr.destinationDistrict=this.wareHouseObj.area;
                if(!StringUtil.isEmpty(this.wareHouseObj.street)){
                    ofcOrderDTOStr.destinationTowns=this.wareHouseObj.street;
                }

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
                        //good.productionTime=this.formatDate(good.productionTime);
                       // good.invalidTime=this.formatDate(good.invalidTime);

                    }
                    goodDetail.push(good);

                }

                if(goodDetail.length <1){
                    alert('请添加至少一条货品!');
                    return;
                }
                var ofcOrderDto = JSON.stringify(ofcOrderDTOStr);
                var orderGoodsListStr = JSON.stringify(goodDetail);
                var tag="save";
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
                cscContactCompanyDto.contactCompanyName = this.consignorName;
                cscContactDto.contactName = this.consignorContactName;
                cscContactDto.purpose = "2";
                cscContactDto.phone =this.consignorPhoneNumber;
                cscContactDto.contactCompanyName = this.consignorName;
                var consignorAddressCodeMessage = this.consignorAddressCode.split(',');
                var consignorAddressNameMessage =this.consignorAddress.split(',');
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

                cscContactDto.address=this.consignorAddress;
                paramConsignor.cscContactDto = cscContactDto;
                paramConsignor.cscContactCompanyDto = cscContactCompanyDto;
                var cscContantAndCompanyDtoConsignorStr = JSON.stringify(paramConsignor);
                return cscContantAndCompanyDtoConsignorStr;
            },
            getCscContantAndCompanyDtoConsigneeStr:function(warehouse){
                var paramConsignee = {};
                var cscContactDto = {};
                var cscContactCompanyDto = {};
                cscContactCompanyDto.contactCompanyName =warehouse.warehouseName;
                cscContactDto.contactName = warehouse.contactName;
                cscContactDto.purpose = "1";
                cscContactDto.phone =warehouse.phone;

                cscContactDto.contactCompanyName =warehouse.warehouseName;

                cscContactDto.province = warehouse.provinceCode;
                cscContactDto.city = warehouse.cityCode;
                cscContactDto.area = warehouse.areaCode;
                if(!StringUtil.isEmpty(warehouse.streetCode)){
                    cscContactDto.street = warehouse.streetCode;
                }

                cscContactDto.provinceName =warehouse.province;
                cscContactDto.cityName = warehouse.city;
                cscContactDto.areaName = warehouse.area;
                if(!StringUtil.isEmpty(warehouse.street)){
                    cscContactDto.streetName=warehouse.street;
                }
                cscContactDto.address=warehouse.detailAddress;
                paramConsignee.cscContactDto = cscContactDto;
                paramConsignee.cscContactCompanyDto = cscContactCompanyDto;
                var cscContantAndCompanyDtoConsigneeStr = JSON.stringify(paramConsignee);
                return cscContantAndCompanyDtoConsigneeStr;
            },
            formatDate:function(date){
                return date.getFullYear()+"-"+date.getMonth()+"-"+date.getDate()+" 00:00:00";
            },
            openGoodsList: function(currentRowData) {
                this.chosenGoodCode=true;
                this.currentRowData = currentRowData;
            }
        }
    });
</script>


</html>