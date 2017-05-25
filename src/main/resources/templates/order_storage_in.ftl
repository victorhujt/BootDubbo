<head>
    <link rel="stylesheet" href="/components/select2.v3/select2.min.css" />
    <link rel="stylesheet" href="/components/select2.v3/select2-bootstrap.css" />
    <title>入库开单</title>
    <style>
        .el-dialog{
            top:50%!important;
            margin-top:-300px;
            margin-bottom:0!important;
        }
        .el-dialog__body{
            padding:10px 20px 30px;
        }
        .el-dialog__footer{
            padding:15px 20px;
        }
        .el-dialog--small .el-table{
            min-height:350px;
        }
        .el-dialog--small .el-table tr{
            cursor:pointer;
        }
        .el-table__body-wrapper{
            overflow-y: auto;
            overflow-x: hidden;
        }
    </style>
</head>
<span hidden="true" id = "ofc_web_url">${(OFC_WEB_URL)!}</span>
<body>
<div id="app">
    <div class="list-mian-01">
        <el-dialog title="选择客户" v-model="customerDataInfo.chosenCus" size="small">
            <el-form :model="customerDataInfo.chosenCusForm">
                <el-form-item label="名称" :label-width="formLabelWidth">
                    <el-input v-model="customerDataInfo.chosenCusForm.name" auto-complete="off"></el-input>
                </el-form-item>
                <el-form-item label="" :label-width="formLabelWidth20">
                    <el-button type="primary" @click="selectCustomer">筛选</el-button>
                </el-form-item>
            </el-form>

            <el-table :data="customerDataInfo.customerData" highlight-current-row @current-change="handleCurrentChange"
                      @row-dblclick="setCurrentCustInfo(customerDataInfo.currentRow)" style="width: 100%" border  max-height="400">
                <el-table-column type="index" label="序号"></el-table-column>
                <el-table-column property="custCode" label="客户编码"></el-table-column>
                <el-table-column property="type" label="类型"></el-table-column>
                <el-table-column property="custName" label="公司名称"></el-table-column>
                <el-table-column property="channel" label="渠道"></el-table-column>
                <el-table-column property="productType" label="产品类别"></el-table-column>
            </el-table>
            <el-pagination @size-change="handleCustomerSizeChange" @current-change="handleCustomerCurrentPage" :current-page="customerDataInfo.currentCustomerPage" :page-sizes="pageSizes" :page-size="customerDataInfo.customerPageSize" :total="customerDataInfo.total" layout="total, sizes, prev, pager, next, jumper">
            </el-pagination>
            <div slot="footer" class="dialog-footer">
                <el-button @click="cancelSelectCustomer">取 消</el-button>
                <el-button type="primary" @click="setCurrentCustInfo(customerDataInfo.currentRow)">确 定</el-button>
            </div>
        </el-dialog>

        <el-dialog title="发货方联系人" v-model="consignorDataInfo.chosenSend" size="small">
            <el-form :model="consignorDataInfo.consignorForm">
                <el-form-item label="名称" :label-width="formLabelWidth">
                    <el-input v-model="consignorDataInfo.consignorForm.consignorName" auto-complete="off"></el-input>
                </el-form-item>
                <el-form-item label="联系人" :label-width="formLabelWidth">
                    <el-input v-model="consignorDataInfo.consignorForm.consignorContactName" auto-complete="off"></el-input>
                </el-form-item>
                <el-form-item label="联系电话" :label-width="formLabelWidth">
                    <el-input v-model="consignorDataInfo.consignorForm.consignorContactPhone" auto-complete="off"></el-input>
                </el-form-item>
                <el-form-item label="" :label-width="formLabelWidth20">
                    <el-button type="primary" @click="selectConsignor">筛选</el-button>
                </el-form-item>
            </el-form>

            <el-table :data="consignorDataInfo.consignorData" highlight-current-row @current-change="consignorHandleCurrentChange"
                      @row-dblclick="setCurrentConsignorInfo(consignorDataInfo.consignorCurrentRow)" border style="width: 100%" max-height="400">
                <el-table-column type="index" label="序号"></el-table-column>
                <el-table-column property="consignorName" label="名称"></el-table-column>
                <el-table-column property="consignorContactName" label="联系人"></el-table-column>
                <el-table-column property="consignorContactPhone" label="联系电话"></el-table-column>
                <el-table-column property="departurePlace"  v-if="false" label="地址"></el-table-column>
                <el-table-column property="consignorCode" v-if="false" label="发货方编码"></el-table-column>
                <el-table-column property="consignorType" v-if="false" label="发货方类型"></el-table-column>
                <el-table-column property="consignorContactCode" v-if="false" label="发货方联系人编码"></el-table-column>
                <el-table-column property="departurePlaceCode"  v-if="false"label="发货方地址编码"></el-table-column>
                <el-table-column property="departureDetailAddress" label="发货方地址"></el-table-column>
                <el-table-column property="proviceName" v-if="false" label="省"></el-table-column>
                <el-table-column property="cityName" v-if="false" label="城市"></el-table-column>
                <el-table-column property="areaName" v-if="false" label="区"></el-table-column>
                <el-table-column property="streetName" v-if="false" label="街道"></el-table-column>
            </el-table>
            <el-pagination @size-change="handleConsignorSizeChange" @current-change="handleConsignorCurrentPage" :current-page="consignorDataInfo.currentConsignorPage" :page-sizes="pageSizes" :page-size="consignorDataInfo.consignorPageSize" layout="total, sizes, prev, pager, next, jumper" :total="consignorDataInfo.totalConsignor">
            </el-pagination>
            <div slot="footer" class="dialog-footer">
                <el-button @click="cancelSelectConsignor">取 消</el-button>
                <el-button type="primary" @click="setCurrentConsignorInfo(consignorDataInfo.consignorCurrentRow)">确 定</el-button>
            </div>
        </el-dialog>


        <el-dialog title="货品列表" v-model="goodDataInfo.chosenGoodCode" size="small">
            <el-form :model="goodDataInfo.goodsForm">
                <el-form-item label="货品种类" :label-width="formLabelWidth" class="xe-col-4">
                    <template scope="scope">
                        <el-select size="small" v-model="goodDataInfo.goodsForm.goodsTypeId"  @change="getGoodsCategory" placeholder="请选择" >
                            <el-option
                                    v-for="item in goodsMsgOptions"
                                    :label="item.label"
                                    :value="item.value"
                                    style="width:100px;">
                            </el-option>
                        </el-select>
                    </template>
                </el-form-item>
                <el-form-item label="货品小类" :label-width="formLabelWidth" class="xe-col-4">
                    <template scope="scope">
                        <el-select  size="small" v-model="goodDataInfo.goodsForm.goodsTypeSonId"   placeholder="请选择">
                            <el-option
                                    v-for="subitem in goodsCategoryOptions"
                                    :label="subitem.label"
                                    :value="subitem.value"
                                    style="width:100px;">
                            </el-option>
                        </el-select>
                    </template>
                </el-form-item>
                <el-form-item label="货品名称" :label-width="formLabelWidth" class="xe-col-4">
                    <el-input v-model="goodDataInfo.goodsForm.goodsName" auto-complete="off"></el-input>
                </el-form-item>
                <el-form-item label="条形码" :label-width="formLabelWidth" class="xe-col-4">
                    <el-input v-model="goodDataInfo.goodsForm.barCode" auto-complete="off"></el-input>
                </el-form-item>
                <el-form-item label="货品编码" :label-width="formLabelWidth" class="xe-col-4">
                    <el-input v-model="goodDataInfo.goodsForm.goodsCode" auto-complete="off"></el-input>
                </el-form-item>
                <el-form-item label="" :label-width="formLabelWidth">
                    <el-button type="primary" :disabled="isCanClick" @click="selectGoods">筛选</el-button>
                </el-form-item>
                <el-form-item label="" :label-width="formLabelWidth20">
                    <el-button @click="reSetCondition">重置</el-button>
                </el-form-item>
            </el-form>

            <el-table :data="goodDataInfo.goodsCodeData" @selection-change="handleSelectionChange" border style="width: 100%" max-height="350">
                <el-table-column type="selection"></el-table-column>
                <el-table-column type="index" label="序号"></el-table-column>
                <el-table-column property="goodsType" label="货品种类"></el-table-column>
                <el-table-column property="goodsCategory" label="货品小类"></el-table-column>
                <el-table-column property="goodsBrand" label="品牌"></el-table-column>
                <el-table-column property="goodsCode" label="货品编码"></el-table-column>
                <el-table-column property="goodsName" label="货品名称"></el-table-column>
                <el-table-column property="goodsSpec" label="规格"></el-table-column>
                <el-table-column property="unit" label="单位"></el-table-column>
                <el-table-column property="unitWeight" label="毛重"></el-table-column>
                <el-table-column property="barCode" label="条形码"></el-table-column>
                <el-table-column property="expiryDate" v-if="false" label="保质期限"></el-table-column>
            </el-table>
            <el-pagination @size-change="handleGoodSizeChange" @current-change="handleGoodCurrentPage" :current-page="goodDataInfo.currentGoodPage" :page-sizes="pageSizes" :page-size="goodDataInfo.goodPageSize" layout="total, sizes, prev, pager, next, jumper" :total="goodDataInfo.totalGoods">
            </el-pagination>
            <div slot="footer" class="dialog-footer">
                <el-button @click="cancelSelectGood">取 消</el-button>
                <el-button type="primary" :disabled="isRepeatClick" @click="setCurrentGoodsInfo">确 定</el-button>
            </div>
        </el-dialog>



        <el-form :model="orderForm"   :rules="rules" ref="orderForm" label-width="100px" class="demo-ruleForm">
            <div class="xe-pageHeader">
                基本信息
            </div>

            <div class="xe-block">
                <el-form-item label="订单日期" required class="xe-col-3" prop="orderDate">
                    <el-date-picker type="date" :clearable="false" :editable="false" v-model="orderForm.orderDate" :picker-options="pickerOptions"></el-date-picker>
                </el-form-item>
                <el-form-item label="开单员" required prop="merchandiser" class="xe-col-3">
                    <el-input v-model="orderForm.merchandiser" placeholder="请输入内容"></el-input>
                </el-form-item>
                <el-form-item label="客户名称" required class="xe-col-3" prop="custName">
                    <el-input
                            placeholder="请选择"
                            icon="search"
                            v-model="orderForm.custName"
                            :readOnly="true"
                            @click="openCustomer" >
                    </el-input>
                </el-form-item>
            </div>
            <div class="xe-block">
                <el-form-item label="仓库名称" required prop="wareHouse" class="xe-col-3">
                    <el-select v-model="orderForm.wareHouse" placeholder="请选择"  @change="clearGoodsData">
                        <el-option
                                v-for="item in wareHouseOptions"
                                :label="item.label"
                                :value="item.value">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="业务名称" required  prop="businessType" class="xe-col-3">
                    <el-select v-model="orderForm.businessType" placeholder="请选择">
                        <el-option
                                v-for="item in businessTypeOptions"
                                :label="item.label"
                                :value="item.value">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="客户订单号" prop="custOrderCode" class="xe-col-3">
                    <el-input v-model="orderForm.custOrderCode" placeholder="请输入内容"></el-input>
                </el-form-item>
            </div>
            <div class="xe-block">
                <el-form-item label="供应商名称" class="xe-col-3">
                    <input class="form-control select2-single" name="custName" id="custName" placeholder="请输入供应商名称" />
                    <input  hidden name="custCode" id="custCode"  />
                </el-form-item>
                <el-form-item label="备注" prop="notes" class="xe-col-3">
                    <el-input type="textarea" v-model="orderForm.notes" ></el-input>
                </el-form-item>
            </div>
            <div>
                <el-collapse v-model="activeNames" accordion>
                    <el-collapse-item title="运输信息" name="1">
                        <div class="xe-block">
                            <el-form-item label="预计入库时间" class="xe-col-3">
                                <el-date-picker
                                        v-model="orderForm.arriveTime"
                                        type="datetime"
                                        :clearable="false"
                                        :editable="false"
                                        placeholder="选择日期"
                                        :picker-options="pickerOptions1">
                                </el-date-picker>
                            </el-form-item>
                            <el-form-item label="是否提供运输"  class="xe-col-3">
                                <el-checkbox v-model="orderForm.isNeedTransport" @click="isNeedTransport = true"></el-checkbox>
                            </el-form-item>
                            <el-form-item  prop="transCode"  class="xe-col-3" label="运输单号">
                                <el-input v-model="orderForm.transCode" placeholder="请输入内容"></el-input>
                            </el-form-item>
                        </div>
                        <div class="xe-block">
                            <el-form-item label="车牌号"  prop="plateNumber" class="xe-col-3">
                                <el-input v-model="orderForm.plateNumber" placeholder="请输入内容"></el-input>
                            </el-form-item>
                            <el-form-item label="司机姓名"  prop="driverName"  class="xe-col-3">
                                <el-input v-model="orderForm.driverName"  placeholder="请输入内容"></el-input>
                            </el-form-item>
                            <el-form-item label="联系电话" prop="contactNumber" class="xe-col-3">
                                <el-input v-model="orderForm.contactNumber"  placeholder="请输入内容"></el-input>
                            </el-form-item>
                        </div>
                        <div class="xe-pageHeader">
                            &nbsp;&nbsp;&nbsp;发货方信息
                        </div>
                        <div class="xe-block">
                            <el-form-item label="名称" class="xe-col-3">
                                <el-input
                                        placeholder="请选择"
                                        icon="search"
                                        v-model="orderForm.consignorName"
                                        v-bind:disabled = "isDisabled"
                                        :readOnly="true"
                                        @click="openConsignor">
                                </el-input>
                            </el-form-item>
                            <el-form-item label="联系人" class="xe-col-3">
                                <el-input v-model="orderForm.consignorContactName" :readOnly="true"></el-input>
                            </el-form-item>
                            <el-form-item label="联系电话" class="xe-col-3">
                                <el-input v-model="orderForm.consignorContactPhone" :readOnly="true"></el-input>
                            </el-form-item>
                        </div>
                        <div class="xe-block">
                            <el-form-item label="地址" class="xe-col-3">
                                <el-input v-model="orderForm.departureDetailAddress" :readOnly="true"></el-input>
                            </el-form-item>
                        </div>
                    </el-collapse-item>
                </el-collapse>
            </div>

            <div class="xe-pageHeader">
                货品信息
            </div>
            <el-table :data="goodsData" border highlight-current-row @current-change="GoodsCurrentChange" style="width: 100%">
                <el-table-column property="goodsType" label="货品种类">
                    <template scope="scope">
                        <el-input v-model="scope.row.goodsType" :readOnly="true"></el-input>
                    </template>
                </el-table-column>
                <el-table-column property="goodsCategory" label="货品小类">
                    <template scope="scope">
                        <el-input v-model="scope.row.goodsCategory" :readOnly="true"></el-input>
                    </template>
                </el-table-column>
                <el-table-column property="goodsCode" label="货品编码">
                    <template scope="scope">
                        <el-input v-model="scope.row.goodsCode" :readOnly="true"></el-input>
                    </template>
                </el-table-column>
                <el-table-column property="goodsName" label="货品名称">
                    <template scope="scope">
                        <el-input v-model="scope.row.goodsName" :readOnly="true"></el-input>
                    </template>
                </el-table-column>
                <el-table-column property="goodsSpec" label="规格">
                    <template scope="scope">
                        <el-input v-model="scope.row.goodsSpec" :readOnly="true"></el-input>
                    </template>
                </el-table-column>
                <el-table-column property="unit" label="单位">
                    <template scope="scope">
                        <el-select v-model="scope.row.unit"  @change="accountSpecification(scope.row)" placeholder="请选择">
                            <el-option
                                    v-for="item in scope.row.unitsOptions"
                                    :label="item.label"
                                    :value="item.value">
                                <span style="float: right; color: #8492a6; font-size: 13px">{{ item.value }}</span>
                                <span style="float: left">{{ item.label }}</span>
                            </el-option>
                        </el-select>
                    </template>
                </el-table-column>
                <el-table-column property="goodsName" label="单位毛重">
                    <template scope="scope">
                        <el-input v-model="scope.row.unitWeight" :readOnly="true"></el-input>
                    </template>
                </el-table-column>
                <el-table-column property="quantity" label="入库数量">
                    <template scope="scope">
                        <el-input v-model="scope.row.quantity" @blur="accountPrimaryQuantity(scope.row)" placeholder="请输入内容"></el-input>
                    </template>
                </el-table-column>
                <el-table-column property="primaryQuantity" label="主单位数量">
                    <template scope="scope">
                        <el-input v-model="scope.row.primaryQuantity"  :readOnly="true"></el-input>
                    </template>
                </el-table-column>
                <el-table-column property="primaryQuantity" label="重量">
                    <template scope="scope">
                        <el-input v-model="scope.row.weight"  :readOnly="true"></el-input>
                    </template>
                </el-table-column>
                <el-table-column property="packageType" v-if="false" label="包装类型">
                    <template scope="scope">
                        <el-input v-model="scope.row.packageType"></el-input>
                    </template>
                </el-table-column>
                <el-table-column property="packageName" v-if="false" label="包装名称">
                    <template scope="scope">
                        <el-input v-model="scope.row.packageName"></el-input>
                    </template>
                </el-table-column>
                <el-table-column property="conversionRate" v-if="false" label="与主单位的换算规格">
                    <template scope="scope">
                        <el-input v-model="scope.row.conversionRate"></el-input>
                    </template>
                </el-table-column>
                <el-table-column property="expiryDate" label="保质期限" v-if="false">
                    <template scope="scope">
                        <el-input v-model="scope.row.expiryDate"></el-input>
                    </template>
                </el-table-column>
                <el-table-column property="" label="" v-if="false">
                    <template scope="scope">
                        <el-input v-model="scope.row.levelSpecificationOptions"></el-input>
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
                <el-table-column property="productionTime" width ="140px" label="生产日期">
                    <template scope="scope">
                        <el-date-picker
                                v-model="scope.row.productionTime"
                                align="right"
                                type="date"
                                :clearable="false"
                                :editable="false"
                                placeholder="选择日期"
                                @change="accountInvalidTime(scope.row)"
                                :picker-options="pickerOptions1">
                        </el-date-picker>
                    </template>
                </el-table-column>
                <el-table-column property="invalidTime" width ="140px" label="失效日期">
                    <template scope="scope">
                        <el-date-picker
                                v-model="scope.row.invalidTime"
                                align="right"
                                type="date"
                                :clearable="false"
                                :editable="false"
                                placeholder="选择日期"
                                :picker-options="pickerOptions1">
                        </el-date-picker>
                    </template>
                </el-table-column>
                <el-table-column property="supportBatch" label="供应商批次">
                    <template scope="scope">
                        <el-select v-model="scope.row.supportBatch" placeholder="请选择">
                            <el-option
                                    v-for="item in supportBatchData"
                                    :label="item.label"
                                    :value="item.value">
                            </el-option>
                        </el-select>
                    </template>
                </el-table-column>
                <el-table-column property="goodsOperation" label="操作">
                    <template scope="scope">
                        <el-button type="text" @click="deleteRow(scope.$index, goodsData)">删除</el-button>
                    </template>
                </el-table-column>
            </el-table>
            <el-button @click="addGoods">添加货品</el-button>
            <el-button type="primary" @click="submitForm('orderForm')">确认下单</el-button>
        </el-form>
    </div>
</div>
</body>
<script>
    var scripts = [null,
        "/components/select2.v3/select2.min.js",
        "/components/select2.v3/select2_locale_zh-CN.js",
        null];

    $(".page-content-area").ace_ajax("loadScripts", scripts, function () {
        $(document).ready(main);
    });

    function main() {
        initSupplierName("default");
    }

    function initSupplierName(val) {
        var customerCode;
        if("default" == val){
            customerCode = "xxxxxx";
        }else{
            customerCode =  _this.orderForm.custCode;
        }
        var ofc_web_url = $("#ofc_web_url").html();
        var url =  ofc_web_url+"/ofc/distributing/querySupplierSelect2?customerCode="+customerCode;
        var notice = "没有找到相关供应商";
        Select2Util.singleSelectInit("#custName",url,notice,"#custCode");
        $("#custName").on("select2-selecting", function(e) {
            _this.orderForm.supportName = e.choice.name;
            _this.orderForm.supportCode = e.choice.code;
        });
    }



    var _this = new Vue({
        el: '#app',
        data:function () {
            var validateOrdeTime = function(rule, value, callback){
                if(value.getTime()<new Date().getTime() - 3600 * 1000 * 24 * 7){
                    callback(new Error('只能选择一周之内的日期!'));
                }else if(value.getTime()>new Date().getTime()){
                    callback(new Error('不能选择当前日期的往后日期'));
                }else{
                    callback();
                }
            };
            var checkTransCode = function(rule, value, callback) {
                if(value!==""){
                    var mp=/^\w+$/;
                    var tranCode =mp.test(value);
                    if(tranCode!==true){
                        callback(new Error('请正确输入运输单号'));
                    }else{
                        callback();
                    }
                }else{
                    callback();
                }
            };
            var checkPhoneOrMobile = function(rule, value, callback) {
                if(value!==""){
                    var pp= /^((0\d{2,3}-\d{7,8})|(1[35874]\d{9}))$/;
                    var phone = pp.test(value);
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
                isCanClick:false,
                isRepeatClick:false,
                isShow:false,
                activeNames:'',
                wareHouseObj:'',
                oldWarehouse:'',
                oldCustomerCode:'',
                oldCustomerName:'',
                goodsCategoryOptions:[],
                unitsOptions:[],
                customerDataInfo:{
                    currentCustomerPage:1,
                    customerPageSize:10,
                    total:0,
                    currentRow:null,
                    customerData:[],
                    chosenCus:false,
                    chosenCusForm: {
                        name: ''
                    }
                },
                consignorDataInfo:{
                    currentConsignorPage:1,
                    consignorPageSize:10,
                    totalConsignor:0,
                    consignorCurrentRow:null,
                    consignorData:[],
                    chosenSend:false,
                    consignorForm: {
                        consignorName: '',
                        consignorContactName: '',
                        consignorContactPhone:''
                    }
                },
                supportBatchData:[],
                supplierDataInfo:{
                    currentSupplierPage:1,
                    supplierPageSize:10,
                    totalSupplier:0,
                    supplierCurrentRow:null,
                    supplierData:[],
                    chosenSupplier:false,
                    supplierForm:{
                        supportName:'',
                        contactName:'',
                        contactPhone:''
                    }
                },
                goodDataInfo:{
                    currentGoodPage:1,
                    goodPageSize:10,
                    totalGoods:0,
                    goodCurrentRow:null,
                    goodsCodeData:[],
                    chosenGoodCode:false,
                    goodsForm:{
                        goodsName:'',
                        goodsTypeId:'',
                        goodsTypeSonId:'',
                        goodsCode:'',
                        barCode:''
                    }
                },
                currentRowData : '',
                wareHouseOptions:[],
                businessTypeOptions: [{
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
                pageSizes:[10, 20, 30, 40,50],
                formLabelWidth: '100px',
                formLabelWidth20: '20px',
                isDisabled: false,
                goodsData: [],
                orderForm:{
                    orderDate:new Date(),
                    merchandiser:'${merchandiser!}',
                    custName:'',
                    custCode:'',
                    wareHouse:'',
                    businessType:'620',
                    custOrderCode:'',
                    notes:'',
                    supportName:'',
                    supportCode:'',
                    arriveTime:'',
                    isNeedTransport:false,
                    plateNumber:'',
                    driverName:'',
                    contactNumber:'',
                    consignorName:'',
                    consignorContactPhone:'',
                    departurePlace:'',
                    consignorCode:'',
                    consignorType:'',
                    consignorContactCode:'',
                    consignorContactName:'',
                    departureDetailAddress:'',
                    departurePlaceCode:'',
                    departureProvince:'',
                    departureCity:'',
                    departureDistrict:'',
                    departureTowns:'',
                    transCode:''


                },
                rules: {
                    orderDate:[
                        { type: 'date', required: true, message: '请选择日期', trigger: 'blur' },
                        {validator: validateOrdeTime, trigger: 'blur'}
                    ],
                    merchandiser:[
                        { required: true, message: '请输入开单员', trigger: 'blur' },
                        { min: 1, max: 50, message: '长度在 1 到 50 个字符', trigger: 'change' }
                    ],
                    custName:[
                        { required: true, message: '请输入客户名称', trigger: 'change' },
                        { min: 1, max: 100, message: '长度在 1 到 100 个字符', trigger: 'change' }
                    ],
                    wareHouse:[
                        { required: true, message: '请选择仓库名称', trigger: 'change' }
                    ],
                    businessType:[
                        { required: true, message: '请选择业务名称', trigger: 'change' }
                    ],
                    custOrderCode:[
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
                    transCode:[
                        { min: 0, max: 30, message: '长度在 0 到 30 个字符', trigger: 'change' },
                        {validator: checkTransCode,trigger: 'blur'}
                    ],
                    contactNumber:[
                        { min: 0, max: 30, message: '长度在 0 到 30 个字符', trigger: 'change' },
                        {validator: checkPhoneOrMobile,trigger: 'blur'}
                    ]
                }
            };
        },
        methods: {
            handleCurrentChange:function(val) {
                this.customerDataInfo.currentRow = val;
            },
            accountSpecification:function(val){
                //计算主单位数量
                if(!StringUtil.isEmpty(val.unit)){
                    var specification = this.getLevelSpecification(val);
                    val.conversionRate = specification;
                    val.packageType = val.unit;
                    val.packageName = this.getLevelName(val);
                    this.accountPrimaryQuantity(val);
                }
            },
            accountPrimaryQuantity:function(val){
                if(!StringUtil.isEmpty(val.quantity)){
                    if(!StringUtil.isEmpty(val.conversionRate)){
                        val.primaryQuantity = val.quantity*(val.conversionRate);
                    }
                    if (!StringUtil.isEmpty(val.unitWeight) && !StringUtil.isEmpty(val.primaryQuantity)) {
                        val.weight = val.primaryQuantity*(val.unitWeight);
                        val.weight = new Number(val.weight).toFixed(4);
                    }
                }
            },
            setCurrentCustInfo:function(val) {
                var vueObj=this;
                if (val != null) {
                    this.orderForm.custName = val.custName;
                    this.orderForm.custCode=val.custCode;
                    this.customerDataInfo.chosenCus = false;
                    CommonClient.post(sys.rootPath + "/ofc/queryWarehouseByCustomerCode", {"customerCode":vueObj.orderForm.custCode}, function(result) {
                        vueObj.wareHouseOptions = [];// 仓库下拉列表清空
                        vueObj.orderForm.wareHouse = '';       // 清空仓库
                        vueObj.supplierDataInfo.supplierData = [];    // 供应商列表清空
                        vueObj.orderForm.supportName = '';    // 清空供应商
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
                            if(vueObj.wareHouseOptions.length==1){
                                vueObj.orderForm.wareHouse=vueObj.wareHouseOptions[0].value;
                            }
                            if(vueObj.orderForm.custCode != vueObj.oldCustomerCode ){
                                vueObj.clearGoodsData();
                            }
                        } else if (result.code == 403) {
                            vueObj.promptInfo("没有权限",'error');
                        } else {
                            vueObj.promptInfo(result.message,'error');
                        }
                    },"json");
                    initSupplierName();
                } else {
                    vueObj.promptInfo("请选择客户信息!",'warning');
                }
            },
            consignorHandleCurrentChange:function(val) {
                this.consignorDataInfo.consignorCurrentRow=val;
            },
            handleCustomerSizeChange:function(val) {
                this.customerDataInfo.customerPageSize=val;
                this.selectCustomer();
            },
            handleSelectionChange:function(val){
                this.multipleSelection = val;
            },
            handleCustomerCurrentPage:function(val) {
                this.customerDataInfo.currentCustomerPage = val;
                this.selectCustomer();
            },
            deleteRow:function(index, rows) {
                rows.splice(index, 1);
            },
            getGoodsCategory:function() {
                var vueObj=this;
                vueObj.goodDataInfo.goodsForm.goodsTypeSonId="";
                var typeId=vueObj.goodDataInfo.goodsForm.goodsTypeId;
                CommonClient.syncpost(sys.rootPath + "/ofc/getCscGoodsTypeList",{"cscGoodsType":typeId},function(result) {
                    var data=eval(result);
                    vueObj.goodsCategoryOptions=[];
                    $.each(data,function (index,CscGoodsTypeVo) {
                        var goodClass={};
                        goodClass.label=CscGoodsTypeVo.goodsTypeName;
                        goodClass.value=CscGoodsTypeVo.id;
                        vueObj.goodsCategoryOptions.push(goodClass);
                    });
                });
            },
            GoodsCurrentChange:function(val) {
                this.goodsCurrentRow = val;
            },
            selectSupplier:function(){
                this.supplierDataInfo.supplierData=[];
                this.supportBatchData=[];
                var vueObj=this;
                var param = {};
                param = vueObj.supplierDataInfo.supplierForm;
                param.customerCode = vueObj.orderForm.custCode;
                param.pNum = vueObj.supplierDataInfo.currentSupplierPage;
                param.pSize=vueObj.supplierDataInfo.supplierPageSize;
                CommonClient.syncpost(sys.rootPath + "/ofc/supplierSelect",param, function(result) {
                    vueObj.supplierDataInfo.supplierData = [];
                    // vueObj.orderForm.supportName = '';
                    var data = eval(result);
                    if (data == undefined || data == null || data.result == undefined || data.result ==null || data.result.size == 0) {
                        if(!vueObj.isShow){
                            layer.msg("暂时未查询到供应商信息！！");
                        }
                    } else if (data.code == 200) {
                        $.each(data.result.list,function (index,CscSupplierInfoDto) {
                            var supplier={};
                            var option={};
                            supplier.supportName=StringUtil.nullToEmpty(CscSupplierInfoDto.supplierName);
                            option.label=StringUtil.nullToEmpty(CscSupplierInfoDto.supplierName);
                            supplier.contactName=StringUtil.nullToEmpty(CscSupplierInfoDto.contactName);
                            supplier.contactPhone=StringUtil.nullToEmpty(CscSupplierInfoDto.contactPhone);
                            supplier.fax=StringUtil.nullToEmpty(CscSupplierInfoDto.fax);
                            supplier.email=StringUtil.nullToEmpty(CscSupplierInfoDto.email);
                            supplier.postCode=StringUtil.nullToEmpty(CscSupplierInfoDto.postCode);
                            supplier.supportCode=StringUtil.nullToEmpty(CscSupplierInfoDto.supplierCode);
                            option.value=StringUtil.nullToEmpty(CscSupplierInfoDto.supplierCode);
                            supplier.completeAddress=StringUtil.nullToEmpty(CscSupplierInfoDto.completeAddress);
                            vueObj.supportBatchData.push(option);
                            vueObj.supplierDataInfo.supplierData.push(supplier);

                        });
                        vueObj.supplierDataInfo.totalSupplier=data.result.total;
                    } else if (result.code == 403) {
                        vueObj.promptInfo("没有权限",'error');
                    }
                },"json");

            },
            setCurrentGoodsInfo:function(){
                if(this.multipleSelection.length<1){
                    this.promptInfo("请至少选择一条货品明细!",'warning');
                    return;
                }
                this.isRepeatClick = true;
                this.goodDataInfo.chosenGoodCode = false;
                this.unitsOptions = [];
                this.levelSpecificationOptions = [];
                for(var i=0;i<this.multipleSelection.length;i++){
                    var val=this.multipleSelection[i];
                    var unitVar = '';
                    if(val.unitsOptions != undefined){
                        if(val.unitsOptions.length == 1){
                            unitVar = "主单位";
                        }
                    }
                    var newData = {
                        goodsType: val.goodsType,
                        goodsCategory: val.goodsCategory,
                        goodsCode: val.goodsCode,
                        goodsName: val.goodsName,
                        goodsSpec:val.goodsSpec,
                        unit:unitVar,
                        unitWeight:val.unitWeight,
                        quantity: '',
                        unitsOptions:val.unitsOptions,
                        levelSpecificationOptions:val.levelSpecificationOptions,
                        primaryQuantity:'',
                        unitPrice:'',
                        productionBatch:'',
                        expiryDate:val.expiryDate,
                        productionTime:'',
                        invalidTime:'',
                        supportBatch:'',
                        packageType:'主单位',
                        packageName:'EA',
                        conversionRate:'1'

                    };
                    this.goodsData.push(newData);
                    this.multipleSelection = [];
                    if(this.supportBatchData.length==0){
                        this.isShow = true;
                        this.selectSupplier();

                    }
                }
            },
            selectConsignor:function(){
                this.consignorDataInfo.consignorData=[];
                var vueObj=this;
                var cscContactDto = {};
                var cscContactCompanyDto = {};
                var cscContantAndCompanyDto={};
                cscContactCompanyDto.contactCompanyName = this.consignorDataInfo.consignorForm.consignorName;
                cscContactDto.purpose = "2";
                cscContactDto.contactName = this.consignorDataInfo.consignorForm.consignorContactName;
                cscContactDto.phone = this.consignorDataInfo.consignorForm.consignorContactPhone;
                cscContantAndCompanyDto.cscContactDto = cscContactDto;
                cscContantAndCompanyDto.cscContactCompanyDto = cscContactCompanyDto;
                var customerCode = this.orderForm.custCode;
                cscContantAndCompanyDto.pageNum=this.consignorDataInfo.currentConsignorPage;
                cscContantAndCompanyDto.pageSize=this.consignorDataInfo.consignorPageSize;
                cscContantAndCompanyDto = JSON.stringify(cscContantAndCompanyDto);
                CommonClient.syncpost(sys.rootPath + "/ofc/contactSelectForPage",{"cscContantAndCompanyDto":cscContantAndCompanyDto,"customerCode":customerCode}, function(result) {
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
                            consignor.consignorContactPhone=CscContantAndCompanyDto.phone;
                            consignor.departureDetailAddress=CscContantAndCompanyDto.detailAddress;
                            consignor.proviceName=CscContantAndCompanyDto.provinceName;
                            consignor.cityName=CscContantAndCompanyDto.cityName;
                            consignor.areaName=CscContantAndCompanyDto.areaName;
                            consignor.streetName=CscContantAndCompanyDto.streetName;
                            consignor.departurePlace=CscContantAndCompanyDto.address;
                            consignor.consignorContactCode=CscContantAndCompanyDto.contactCode;
                            consignor.consignorCode=CscContantAndCompanyDto.contactCompanyCode;
                            consignor.departurePlaceCode=CscContantAndCompanyDto.province+","+CscContantAndCompanyDto.city;
                            if(CscContantAndCompanyDto.area!=null){
                                consignor.departurePlaceCode=consignor.departurePlaceCode+","+CscContantAndCompanyDto.area;
                            }
                            if(CscContantAndCompanyDto.street!=null){
                                consignor.departurePlaceCode=consignor.departurePlaceCode+","+CscContantAndCompanyDto.street;
                            }

                            vueObj.consignorDataInfo.consignorData.push(consignor);
                        });
                        vueObj.consignorDataInfo.totalConsignor=result.result.total;
                    } else if (result.code == 403) {
                        vueObj.promptInfo("没有权限",'error');
                    }
                },"json");

            },
            handleConsignorSizeChange:function(val){
                this.consignorDataInfo.consignorPageSize=val;
                this.selectConsignor();
            },
            handleConsignorCurrentPage:function(val){
                this.consignorDataInfo.currentConsignorPage=val;
                this.selectConsignor();
            },
            cancelSelectConsignor:function(){
                this.consignorDataInfo.consignorData=[];
                this.consignorDataInfo.consignorPageSize=10;
                this.consignorDataInfo.totalConsignor=0;
                this.consignorDataInfo.chosenSend=false;
            },
            setCurrentConsignorInfo:function(val){
                this.orderForm.consignorName="";
                this.orderForm.consignorContactPhone="";
                this.orderForm.consignorContactName="";
                this.orderForm.departureDetailAddress="";
                this.orderForm.consignorName=val.consignorName;
                this.orderForm.consignorContactPhone=val.consignorContactPhone;
                this.orderForm.consignorContactName=val.consignorContactName;
                this.orderForm.consignorType=val.type;
                this.orderForm.consignorCode=val.consignorCode;
                this.orderForm.consignorContactCode=val.consignorContactCode;
                this.orderForm.departurePlaceCode=val.departurePlaceCode;
                this.orderForm.departureProvince=val.proviceName;
                this.orderForm.departureCity=val.cityName;
                this.orderForm.departureDistrict=val.areaName;
                this.orderForm.departureTowns=val.areaName;
                this.orderForm.departureDetailAddress=val.departureDetailAddress;
                this.consignorDataInfo.chosenSend = false;
            },
            selectGoods:function(){
                var vueObj = this;
                try{
                    vueObj.isCanClick = true;
                    vueObj.goodDataInfo.goodsCodeData=[];
                    var cscGoods = {};
                    this.wareHouseObj=JSON.parse(this.orderForm.wareHouse);
                    var customerCode = vueObj.orderForm.custCode;
                    var warehouseCode = vueObj.wareHouseObj.warehouseCode;
                    cscGoods.goodsName = vueObj.goodDataInfo.goodsForm.goodsName;
                    cscGoods.goodsTypeId=vueObj.goodDataInfo.goodsForm.goodsTypeId;
                    cscGoods.goodsTypeSonId=vueObj.goodDataInfo.goodsForm.goodsTypeSonId;
                    cscGoods.barCode=vueObj.goodDataInfo.goodsForm.barCode;
                    cscGoods.goodsCode=vueObj.goodDataInfo.goodsForm.goodsCode;
                    cscGoods.pNum=vueObj.goodDataInfo.currentGoodPage;
                    cscGoods.pSize =vueObj.goodDataInfo.goodPageSize;
                    var param = JSON.stringify(cscGoods);
                    CommonClient.post(sys.rootPath + "/ofc/goodsSelectsStorage", {"cscGoods":param,"customerCode":customerCode,"warehouseCode":warehouseCode}, function(data) {
                        if (data == undefined || data == null || data.result ==null || data.result.size == 0 || data.result.list == null) {
                            vueObj.isCanClick = false;
                            layer.msg("暂时未查询到货品信息！！");
                        } else if (data.code == 200) {
                            $.each(data.result.list,function (index,cscGoodsVo) {
                                var goodCode={};
                                var unitsOptions = [];
                                var levelSpecificationOptions = [];
                                goodCode.goodsType=cscGoodsVo.goodsTypeParentName;
                                goodCode.goodsCategory=cscGoodsVo.goodsTypeName;
                                goodCode.goodsCode=cscGoodsVo.goodsCode;
                                goodCode.goodsName=cscGoodsVo.goodsName;
                                goodCode.goodsBrand=cscGoodsVo.brand;
                                goodCode.goodsSpec=cscGoodsVo.specification;
                                goodCode.unit=cscGoodsVo.unit;
                                goodCode.unitWeight=cscGoodsVo.weight;
                                goodCode.barCode=cscGoodsVo.barCode;
                                if(cscGoodsVo.expiryDate==null||StringUtil.isEmpty(cscGoodsVo.expiryDate)){
                                    goodCode.expiryDate=0;
                                }else{
                                    goodCode.expiryDate=cscGoodsVo.expiryDate;
                                }
                                if(cscGoodsVo.goodsPackingDtoList!=null){
                                    if(cscGoodsVo.goodsPackingDtoList.length>0){
                                        var unitsOptions =[];
                                        var levelSpecificationOptions = [];
                                        for(var i = 0;i < cscGoodsVo.goodsPackingDtoList.length;i++){
                                            var goodsPacking = cscGoodsVo.goodsPackingDtoList[i];
                                            if(StringUtil.isEmpty(goodsPacking.levelSpecification)||goodsPacking.levelSpecification == "0") {
                                                continue;
                                            }
                                            var unit = {};
                                            var levelSpecification = {};
                                            unit.label = goodsPacking.levelDescription;
                                            unit.value = goodsPacking.level;
                                            levelSpecification.label = goodsPacking.levelSpecification;
                                            levelSpecification.value =  goodsPacking.level;
                                            unitsOptions.push(unit);
                                            levelSpecificationOptions.push(levelSpecification);
                                        }
                                        goodCode.unitsOptions = unitsOptions;
                                        goodCode.levelSpecificationOptions = levelSpecificationOptions;
                                    }
                                }
                                vueObj.goodDataInfo.goodsCodeData.push(goodCode);
                            });
                            vueObj.goodDataInfo.totalGoods=data.result.total;
                            vueObj.isCanClick = false;
                        } else if (data.code == 403) {
                            vueObj.isCanClick = false;
                            vueObj.promptInfo("没有权限",'error');
                        }
                    },"json");
                }catch(e){
                    vueObj.isCanClick = false;
                }
            },
            handleGoodSizeChange:function(val){
                this.goodDataInfo.goodPageSize=val;
                this.selectGoods();
            },
            handleGoodCurrentPage:function(val){
                this.goodDataInfo.currentGoodPage=val;
                this.selectGoods();
            },
            cancelSelectGood:function(){
                this.goodDataInfo.goodsCodeData=[];
                this.goodDataInfo.goodPageSize=10;
                this.goodDataInfo.totalGoods=0;
                this.goodDataInfo.chosenGoodCode=false;
                this.goodDataInfo.goodsForm.goodsTypeId="";
                this.goodDataInfo.goodsForm.goodsTypeSonId="";
                this.goodDataInfo.goodsForm.goodsName="";
                this.goodDataInfo.goodsForm.barCode="";
                this.goodDataInfo.goodsForm.goodsCode="";
            },
            cancelSelectCustomer:function(){
                this.customerDataInfo.customerData=[];
                this.customerDataInfo.customerPageSize=10;
                this.customerDataInfo.total=0;
                this.customerDataInfo.chosenCus=false;
            },
            selectCustomer:function(){
                var param = {};
                param.pageNum = this.customerDataInfo.currentCustomerPage;
                param.pageSize=this.customerDataInfo.customerPageSize;
                param.custName = this.customerDataInfo.chosenCusForm.name;
                this.customerDataInfo.customerData=[];
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
                                    customer.custCode=cscCustomerVo.customerCode;
                                    var custType = StringUtil.nullToEmpty(cscCustomerVo.type);
                                    if(custType == '1'){
                                        customer.type="公司";
                                    }else if (custType == '2'){
                                        customer.type="个人";
                                    }else{
                                        customer.type=custType;
                                    }
                                    customer.custName=cscCustomerVo.customerName;
                                    customer.channel=channel;
                                    customer.productType=cscCustomerVo.productType;
                                    vueObj.customerDataInfo.customerData.push(customer);
                                });
                                vueObj.customerDataInfo.total=result.result.total;
                            } else if (result.code == 403) {
                                vueObj.promptInfo("没有权限",'error');
                            }
                        },"json");
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
                ofcOrderDTOStr=this.orderForm;
                if(this.orderForm.isNeedTransport){
                    ofcOrderDTOStr.provideTransport="1";
                    if(!this.orderForm.consignorName){
                        this.promptInfo("提供运输时,发货方不能为空，请选择发货方!",'warning');
                        return;
                    }
                }else{
                    ofcOrderDTOStr.provideTransport="0";
                    if(!StringUtil.isEmpty(this.orderForm.transCode)){
                        this.promptInfo("不提供运输时,请不要填写运输单号!",'warning');
                        return;
                    }
                }
                ofcOrderDTOStr.orderTime=DateUtil.format(this.orderForm.orderDate, "yyyy-MM-dd HH:mm:ss");
                //收货方信息(仓库的信息)
                this.wareHouseObj=JSON.parse(this.orderForm.wareHouse);
                ofcOrderDTOStr.warehouseName=this.wareHouseObj.warehouseName;//仓库名称
                ofcOrderDTOStr.warehouseCode=this.wareHouseObj.warehouseCode;//仓库编码
                if(this.orderForm.arriveTime){
                    ofcOrderDTOStr.arriveTime=DateUtil.format(this.orderForm.arriveTime, "yyyy-MM-dd HH:mm:ss");
                }
                ofcOrderDTOStr.consigneeName=this.wareHouseObj.warehouseName;
                ofcOrderDTOStr.consigneeCode=this.wareHouseObj.warehouseCode;
                ofcOrderDTOStr.consigneeContactName=this.wareHouseObj.contactName;
                ofcOrderDTOStr.consigneeContactPhone=this.wareHouseObj.phone;
                cscContantAndCompanyDtoConsignorStr=this.getCscContantAndCompanyDtoConsignorStr();
                cscContantAndCompanyDtoConsigneeStr=this.getCscContantAndCompanyDtoConsigneeStr(this.wareHouseObj);
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
                var  str = "您确认提交订单吗?";
                var goodsTable =this.goodsData;
                var goodDetail=[];
                if(goodsTable.length <1){
                    this.promptInfo("请添加至少一条货品!",'warning');
                    return;
                }
                //校验金额和格式化日期时间
                var messageReminder = "货品";
                var reminder = "";
                for(var i=0;i<goodsTable.length;i++){
                    var good=goodsTable[i];
                    good.unitPrice = StringUtil.trim(good.unitPrice);
                    if(!StringUtil.isEmpty(good.unitPrice)){
                        if(isNaN(good.unitPrice)){
                            this.promptInfo("货品单价必须为数字",'error');
                            return;
                        }
                        if(good.unitPrice>99999.99||good.unitPrice<0){
                            this.promptInfo("货品单价不能大于99999.99或小于0",'warning');
                            return;
                        }
                        if(isNaN(good.unitPrice)){
                            this.promptInfo("货品数量必须为数字",'error');
                            return;
                        }
                    }
                    good.quantity = StringUtil.trim(good.quantity);
                    if(good.quantity>99999.999||good.quantity<0||good.quantity!=""||good.quantity==0){
                        if(!good.quantity){
                            this.promptInfo("货品入库数量不能为空",'warning');
                            return;
                        }
                        if(isNaN(good.quantity)){
                            this.promptInfo("货品入库数量必须为数字",'error');
                            return;
                        }
                        if(good.quantity>99999.999){
                            this.promptInfo("货品数量不能大于99999.999",'warning');
                            return;
                        }
                        if(good.quantity<0){
                            this.promptInfo("货品数量不能小于0",'error');
                            return
                        }
                        if(good.quantity==0){
                            this.promptInfo("货品数量不能小于0",'error');
                            return;
                        }
                    }else{
                        this.promptInfo("货品数量不能为空",'warning');
                        return;
                    }
                    if( good.productionTime&& good.invalidTime){
                        if( good.productionTime.getTime()> good.invalidTime.getTime()){
                            this.promptInfo("生产日期不能大于失效日期",'error');
                            return;
                        }
                    }
                    if(!this.isInteger(good.primaryQuantity)){
                        if(StringUtil.isEmpty(reminder)){
                            reminder = good.goodsCode;
                        }else{
                            reminder = reminder +"," + good.goodsCode;
                        }
                    }
                    goodDetail.push(good);
                }

                if(goodDetail.length <1){
                    this.promptInfo("请添加至少一条货品!",'warning');
                    return;
                }
                if(!StringUtil.isEmpty(reminder)){
                    str = messageReminder + reminder + "主单位数量为非正整数，你确认下单吗？";
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
                        ,str
                        ,function () {
                            location.reload();
                            var url=window.location.href;
                            if(url.indexOf("?")!=-1){
                                var param=url.split("?")[1].split("=");
                            }
                            if(param[1]=="manager"){
                                var newurl = "/ofc/orderStorageInManager";
                                var html = window.location.href;
                                var index = html.indexOf("/index#");
                                window.open(html.substring(0,index) + "/index#" + newurl,"_self");
                            }
                        });
            },
            submitForm:function(formName) {
                var _this = this;
                this.$refs[formName].validate(function(valid){
                    if (valid) {
                        _this.saveStorage();
                    } else {
                        console.log('error submit!!');
                        return false;
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
                cscContactDto.phone =this.orderForm.consignorContactPhone;
                cscContactDto.contactCompanyName = this.orderForm.consignorName;
                if(!StringUtil.isEmpty(this.orderForm.departurePlaceCode)){
                    var consignorAddressCodeMessage = this.orderForm.departurePlaceCode.split(',');
                    cscContactDto.province = consignorAddressCodeMessage[0];
                    cscContactDto.city = consignorAddressCodeMessage[1];
                    cscContactDto.area = consignorAddressCodeMessage[2];
                    if(!StringUtil.isEmpty(consignorAddressCodeMessage[3])){
                        cscContactDto.street = consignorAddressCodeMessage[3];
                    }
                }
                cscContactDto.provinceName = this.orderForm.departureProvince;
                cscContactDto.cityName = this.orderForm.departureProvince;
                cscContactDto.areaName = this.orderForm.departureProvince;
                cscContactDto.streetName = this.orderForm.departureTowns;
                cscContactDto.address=this.orderForm.departurePlace;
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
            openGoodsList: function(currentRowData) {
                this.goodDataInfo.chosenGoodCode = true;
                this.currentRowData = currentRowData;
            },
            addGoods:function(){
                if(StringUtil.isEmpty(this.orderForm.custName)&&StringUtil.isEmpty(this.orderForm.custCode)){
                    this.promptInfo("请选择客户!",'warning');
                    return;
                }
                if(StringUtil.isEmpty(this.orderForm.wareHouse)){
                    this.promptInfo("请先选择仓库名称!",'warning');
                    return;
                }
                this.isRepeatClick = false;
                this.goodDataInfo.chosenGoodCode = true;
                var vueObj=this;
                this.oldWarehouse = this.orderForm.wareHouse;
                this.oldCustomerCode = this.orderForm.custCode;
                this.oldCustomerName = this.orderForm.custName;
                vueObj.goodDataInfo.goodsForm.goodsName = "";
                vueObj.goodDataInfo.goodsForm.goodsTypeId = "";
                vueObj.goodDataInfo.goodsForm.goodsTypeSonId = "";
                vueObj.goodDataInfo.goodsForm.barCode = "";
                vueObj.goodDataInfo.goodsForm.goodsCode =　"";
                vueObj.multipleSelection=[];
                vueObj.goodDataInfo.goodsCodeData=[];
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
            },
            promptInfo:function(message,type){
                this.$message({
                    message: message,
                    type: type
                });
            },
            openCustomer:function(){
                this.customerDataInfo.customerData=[];
                this.customerDataInfo.customerPageSize=10;
                this.customerDataInfo.total=0;
                this.customerDataInfo.chosenCus=true;
            },
            openConsignor:function(){
                if(StringUtil.isEmpty(this.orderForm.custName)){
                    this.promptInfo("请选择客户",'warning');
                    this.consignorDataInfo.chosenSend=false;
                    return;
                }
                this.selectConsignor();
                if(this.consignorDataInfo.consignorData.length==1){
                    this.openMessage();
                }else{
                    this.consignorDataInfo.chosenSend = true;
                    this.consignorDataInfo.consignorData=[];
                }
            },
            openMessage:function(){
                var _this=this;
                _this.$confirm('您只有一条发货方记录, 点击确认将自动帮你加载?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(function() {
                    _this.setCurrentConsignorInfo(_this.consignorDataInfo.consignorData[0]);
                }).catch(function() {
                    _this.orderForm.consignorName="";
                    _this.orderForm.departureDetailAddress="";
                    _this.orderForm.consignorContactName="";
                    _this.orderForm.consignorContactPhone="";
                });
            },
            accountInvalidTime:function(val){
                if(val.productionTime!=null) {
                    val.invalidTime = new Date(val.productionTime.getTime() + val.expiryDate * 3600 * 1000 * 24);
                }
            },
            reSetCondition:function(){
                this.goodDataInfo.goodsForm.goodsName="";
                this.goodDataInfo.goodsForm.barCode="";
                this.goodDataInfo.goodsForm.goodsCode="";
                this.goodDataInfo.goodsForm.goodsTypeSonId="";
                this.goodDataInfo.goodsForm.goodsTypeId="";
            },
            getLevelSpecification:function(val){
                for(var i =0;i <val.levelSpecificationOptions.length;i++){
                    var option = val.levelSpecificationOptions[i];
                    if(val.unit == option.value){
                        return option.label;
                    }
                }
            },
            getLevelName:function(val){
                for(var i =0;i <val.unitsOptions.length;i++){
                    var option = val.unitsOptions[i];
                    if(val.unit == option.value){
                        return option.label;
                    }
                }
            },
            clearGoodsData:function(){
                var _this = this;
                if(_this.goodsData.length>0){
                    if(!StringUtil.isEmpty(this.orderForm.custCode)){
                        if((_this.orderForm.custCode == _this.oldCustomerCode) && (StringUtil.isEmpty( _this.orderForm.wareHouse))){
                            if(!StringUtil.isEmpty(_this.oldWarehouse)){
                                _this.orderForm.wareHouse = _this.oldWarehouse;
                            }
                        }
                    }
                    if(!StringUtil.isEmpty(_this.orderForm.wareHouse)){
                        var wareHouseObj=JSON.parse(_this.orderForm.wareHouse);
                        var warehouseCode = wareHouseObj.warehouseCode;
                    }
                    var oldwareHouseObj=JSON.parse(_this.oldWarehouse);
                    var oldwarehouseCode = oldwareHouseObj.warehouseCode;
                    if((oldwarehouseCode != warehouseCode)||(_this.orderForm.custCode != _this.oldCustomerCode && oldwarehouseCode == warehouseCode)){
                        _this.openChangeWarehouseMessage();
                    }
                }
            },
            openChangeWarehouseMessage:function(){
                var _this=this;
                _this.$confirm('更改客户名称或者仓库名称货品信息将会被清空, 确定要更改吗?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(function() {
                    _this.goodsData = [];
                }).catch(function() {
                    var param = {};
                    _this.orderForm.custCode = _this.oldCustomerCode;
                    _this.orderForm.custName = _this.oldCustomerName;
                    param.custCode =  _this.orderForm.custCode;
                    param.custName =  _this.orderForm.custName;
                    _this.setCurrentCustInfo(param);
                    _this.orderForm.wareHouse = _this.oldWarehouse;
                });
            },
            isInteger:function (obj) {
                return obj%1 === 0 ;
            }
        }
    })




</script>


</html>