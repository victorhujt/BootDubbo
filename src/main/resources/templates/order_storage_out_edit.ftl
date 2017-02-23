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

        <el-dialog title="收货方联系人" v-model="chosenSend" size="small">
            <el-form :model="consigneeForm">
                <el-form-item label="名称" :label-width="formLabelWidth">
                    <el-input v-model="consigneeForm.consigneeName" auto-complete="off"></el-input>
                </el-form-item>

                <el-form-item label="联系人" :label-width="formLabelWidth">
                    <el-input v-model="consigneeForm.consigneeContactName" auto-complete="off"></el-input>
                </el-form-item>
                <el-form-item label="联系电话" :label-width="formLabelWidth">
                    <el-input v-model="consigneeForm.consigneePhoneNumber" auto-complete="off"></el-input>
                </el-form-item>
                <el-form-item label="" :label-width="formLabelWidth">
                    <el-button type="primary" @click="selectConsignee">筛选</el-button>
                </el-form-item>
            </el-form>

            <el-table :data="consigneeData" highlight-current-row @current-change="consigneeHandleCurrentChange" style="width: 100%">
                <el-table-column type="index"></el-table-column>
                <el-table-column property="consigneeName" label="名称"></el-table-column>
                <el-table-column property="consigneeContactName" label="联系人"></el-table-column>
                <el-table-column property="consigneePhoneNumber" label="联系电话"></el-table-column>
                <el-table-column property="consigneeAddress" label="地址"></el-table-column>
                <el-table-column property="consigneeCode" label="收货方编码"></el-table-column>
                <el-table-column property="consigneeType" label="收货方类型"></el-table-column>
                <el-table-column property="consigneeContactCode" label="收货方联系人编码"></el-table-column>
                <el-table-column property="consigneeAddressCode" label="收货方地址编码"></el-table-column>
            </el-table>
            <el-pagination @size-change="handleConsigneeSizeChange" @current-change="handleConsigneeCurrentPage" :current-page="currentPage4" :page-sizes="pageSizes" :page-size="consigneePageSize" layout="total, sizes, prev, pager, next, jumper" :total="totalConsignee">
            </el-pagination>
            <div slot="footer" class="dialog-footer">
                <el-button @click="cancelSelectConsignee">取 消</el-button>
                <el-button type="primary" @click="setCurrentConsigneeInfo(consigneeCurrentRow)">确 定</el-button>
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
        <el-form  label-width="100px">
            <div class="xe-block">
                <el-form-item label="订单日期" class="xe-col-3">
                    <el-date-picker
                            v-model="orderTime"
                            align="right"
                            type="date"
                            placeholder="选择日期"
                            :picker-options="pickerOptions">
                    </el-date-picker>
                </el-form-item>
                <el-form-item label="开单员" class="xe-col-3">
                    <el-input v-model="merchandiser" placeholder="请输入内容"></el-input>
                </el-form-item>
                <el-form-item label="客户名称" class="xe-col-3">
                    <el-input
                            placeholder="请选择"
                            icon="search"
                            v-model="customerName"
                            @click="chosenCus = true">
                    </el-input>
                </el-form-item>
            </div>
            <div class="xe-block">
                <el-form-item label="仓库名称" class="xe-col-3">
                    <el-select v-model="wareHouseName" placeholder="请选择">
                        <el-option
                                v-for="item in wareHouseOptions"
                                :label="item.label"
                                :value="item.value">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="业务名称" class="xe-col-3">
                    <el-select v-model="serviceType" placeholder="请选择">
                        <el-option
                                v-for="item in serviceTypeOptions"
                                :label="item.label"
                                :value="item.value">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="客户订单号" class="xe-col-3">
                    <el-input v-model="customerOrderNum" placeholder="请输入内容"></el-input>
                </el-form-item>
            </div>
            <div class="xe-block">
                <el-form-item label="备注" class="xe-col-3">
                    <el-input type="textarea" placeholder="请输入内容" v-model="notes">
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
                <el-form-item label="预计出库时间" class="xe-col-3">
                    <el-date-picker
                            v-model="shipmentTime"
                            align="right"
                            type="date"
                            placeholder="选择日期"
                            :picker-options="pickerOptions1">
                    </el-date-picker>
                </el-form-item>
                <el-form-item label="是否提供运输" class="xe-col-3">
                    <el-checkbox v-model="isNeedTransport" @click="isNeedTransport = true"></el-checkbox>
                </el-form-item>
                <el-form-item label="车牌号" class="xe-col-3">
                    <el-input v-model="plateNumber" placeholder="请输入内容"></el-input>
                </el-form-item>
            </div>
            <div class="xe-block">
                <el-form-item label="司机姓名" class="xe-col-3">
                    <el-input v-model="driverName" placeholder="请输入内容"></el-input>
                </el-form-item>
                <el-form-item label="联系电话" class="xe-col-3">
                    <el-input v-model="driverContactNumber" placeholder="请输入内容"></el-input>
                </el-form-item>
            </div>
            <div class="xe-pageHeader">
                收货方信息
            </div>
            <div class="xe-block">
                <el-form-item label="名称" class="xe-col-3">
                    <el-input
                            placeholder="请选择"
                            icon="search"
                            v-model="consigneeName"
                            v-bind:disabled = "isDisabled"
                            @click="chosenSend = true">
                    </el-input>
                </el-form-item>
                <el-form-item label="联系人" class="xe-col-3">
                    <el-input v-model="consigneeContactName" placeholder="请输入内容"></el-input>
                </el-form-item>
                <el-form-item label="联系电话" class="xe-col-3">
                    <el-input v-model="consigneePhoneNumber" placeholder="请输入内容"></el-input>
                </el-form-item>
            </div>
            <div class="xe-block">
                <el-form-item label="地址选择" class="xe-col-3">
                    <el-input v-model="consigneeAddress" placeholder="请输入内容"></el-input>
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
            <div class="block" style="float:right;">
                <el-button type="primary" @click="saveStorage">下单</el-button>
            </div>

        </el-form>
    </div>
</div>
</body>
<script>
    new Vue({
        el: '#app',
        data :function() {
            return {
                consigneeCode:'',
                orderCode:'',
                consigneeContactCode:'',
                wareHouseName:'',
                wareHouseCode:'',
                goodsCode:'',
                consigneeType:'',
                goodsName:'',
                goodsSpec:'',
                unit:'',
                total:0,
                totalSupplier:0,
                totalConsignee:0,
                totalGoods:0,
                customerPageSize:10,
                goodPageSize:10,
                currentGoodPage:1,
                currentCustomerPage:1,
                supplierPageSize:10,
                consigneePageSize:10,
                currentSupplierPage:1,
                currentConsigneePage:1,
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
                consigneeContactCode:'',
                consigneeAddressCode:'',
                receiveName: '',
                receiveContacts: '',
                consigneeName:'',
                consigneeContactName:'',
                consigneePhoneNumber:'',
                consigneeAddress:'',
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
                    value: '610',
                    label: '销售出库'
                }, {
                    value: '611',
                    label: '调拨出库'
                }, {
                    value: '612',
                    label: '报损出库'
                }, {
                    value: '613',
                    label: '其它出库'
                }, {
                    value: '614',
                    label: '分拨出库'
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
                shipmentTime: '',
                isNeedTransport:false,
                plateNumber:'',
                driverName:'',
                driverContactNumber:'',
                customerData: [],
                consigneeData:[],
                customerCode:'',
                receiveCusTableDate: '',
                currentRow: null,
                chosenCus: false,
                pageSizes:[10, 20, 30, 40,50],
                chosenCusForm: {
                    name: ''
                },
                formLabelWidth: '100px',
                currentPage4: 4,
                chosenSend: false,
                consigneeCurrentRow:null,
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
                consigneeForm: {
                    consigneeName: '',
                    consigneeContactName: '',
                    consigneePhoneNumber:''
                },
                isDisabled: false,
                isDisabled11: false,
                goodsData: [],
                goodsCodeData: [],
                goodsCurrentRow: null
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
                                vueObj.orderTime=DateUtil.parse(ofcFundamentalInformation.orderTime);
                                vueObj.merchandiser=ofcFundamentalInformation.merchandiser;
                                vueObj.customerName=ofcFundamentalInformation.custName;
                                vueObj.customerCode=ofcFundamentalInformation.custCode;
                                vueObj.customerOrderNum=ofcFundamentalInformation.custOrderCode;
                                vueObj.serviceType=ofcFundamentalInformation.businessType;
                                vueObj.notes=ofcFundamentalInformation.notes;
                                if(ofcWarehouseInformation!=null){
                                    vueObj.wareHouseName=ofcWarehouseInformation.warehouseName;
                                    vueObj.wareHouseCode=ofcWarehouseInformation.warehouseCode;
                                    var wareHouse={};
                                    wareHouse.label= vueObj.wareHouseName;
                                    wareHouse.value= vueObj.wareHouseCode;
                                    vueObj.wareHouseOptions.push(wareHouse);
                                    vueObj.wareHouseName=ofcWarehouseInformation.warehouseCode;
                                    vueObj.supplierName=ofcWarehouseInformation.supportName;
                                    vueObj.supplierCode=ofcWarehouseInformation.supportCode;
                                    vueObj.shipmentTime=DateUtil.parse(ofcWarehouseInformation.shipmentTime);
                                    vueObj.plateNumber=ofcWarehouseInformation.plateNumber;
                                    vueObj.driverName=ofcWarehouseInformation.driverName;
                                    vueObj.driverContactNumber=ofcWarehouseInformation.contactNumber;
                                    if(ofcWarehouseInformation.provideTransport=="1"){
                                        if(ofcDistributionBasicInfo!=null){
                                            //收货方
                                            vueObj.consigneeName=ofcDistributionBasicInfo.consigneeName;
                                            vueObj.consigneeCode=ofcDistributionBasicInfo.consigneeCode;
                                            vueObj.consigneeContactCode=ofcDistributionBasicInfo.consigneeContactCode;
                                            vueObj.consigneeContactName=ofcDistributionBasicInfo.consigneeContactName;
                                            vueObj.consigneePhoneNumber=ofcDistributionBasicInfo.consigneeContactPhone;
                                            vueObj.isNeedTransport=true;
                                            vueObj.consigneeAddress=ofcDistributionBasicInfo.destination;
                                            vueObj.consigneeAddressCode=ofcDistributionBasicInfo.destinationCode;
                                        }
                                    }
                                    if(ofcGoodsDetailsInfo!=null&&ofcGoodsDetailsInfo.length>0){
                                        for(var i=0;i<ofcGoodsDetailsInfo.length;i++){
                                            var goodDetail=ofcGoodsDetailsInfo[i];
                                            var good={};
                                            good.goodsType=goodDetail.goodsType;
                                            good.goodsCategory=goodDetail.goodsCategory;
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
                this.customerName = val.customerName;
                this.customerCode=val.customerCode;
                this.chosenCus = false;
                this.wareHouseOptions=[];
                this.loadWareHouseByCustomerCode();
            },
            loadWareHouseByCustomerCode:function(){
                var vueObj=this;
                CommonClient.syncpost(sys.rootPath + "/ofc/queryWarehouseByCustomerCode", {"customerCode":vueObj.customerCode}, function(result) {
                    var data=result.result;
                    if (data == undefined || data == null || data.length ==0) {
                        vueObj.wareHouseName="";
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
            consigneeHandleCurrentChange:function(val) {
                this.consigneeCurrentRow=val;
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
                if(!this.customerName){
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
            selectConsignee:function(){
                if(!this.customerName){
                    alert("请选择客户");
                    this.chosenSend=false;
                    return;
                }
                this.consigneeData=[];
                var vueObj=this;
                var cscContactDto = {};
                var cscContactCompanyDto = {};
                var cscContantAndCompanyDto={};
                cscContactCompanyDto.contactCompanyName = this.consigneeForm.consigneeName;
                cscContactDto.purpose = "2";
                cscContactDto.contactName = this.consigneeForm.consigneeContactName;
                cscContactDto.phone = this.consigneeForm.consigneePhoneNumber;
                cscContantAndCompanyDto.cscContactDto = cscContactDto;
                cscContantAndCompanyDto.cscContactCompanyDto = cscContactCompanyDto;
                var customerCode = this.customerCode;
                cscContantAndCompanyDto.pageNum=this.currentConsigneePage;
                cscContantAndCompanyDto.pageSize=this.consigneePageSize;
                cscContantAndCompanyDto = JSON.stringify(cscContantAndCompanyDto);
                CommonClient.post(sys.rootPath + "/ofc/contactSelectForPage",{"cscContantAndCompanyDto":cscContantAndCompanyDto,"customerCode":customerCode}, function(result) {
                    if (result == undefined || result == null || result.result ==null || result.result.size == 0 || result.result.list == null) {
                        layer.msg("暂时未查询到收货方信息！！");
                    } else if (result.code == 200) {
                        $.each(result.result.list,function (index,CscContantAndCompanyDto) {
                            var consignee={};
                            if(CscContantAndCompanyDto.type=="1"){
                                consignee.consigneeType="公司";
                            }else{
                                CscContantAndCompanyDto.consigneeType="个人";
                            }
                            consignee.consigneeName=CscContantAndCompanyDto.contactCompanyName;
                            consignee.consigneeContactName=CscContantAndCompanyDto.contactName;
                            consignee.consigneePhoneNumber=CscContantAndCompanyDto.phone;
                            consignee.consigneeAddress=CscContantAndCompanyDto.provinceName+","+CscContantAndCompanyDto.cityName+","+CscContantAndCompanyDto.areaName;
                            if(CscContantAndCompanyDto.streetName!=null){
                                consignee.consigneeAddress=consignee.consigneeAddress+","+CscContantAndCompanyDto.streetName;
                            }
                            consignee.consigneeContactCode=CscContantAndCompanyDto.contactCode;
                            consignee.consigneeCode=CscContantAndCompanyDto.contactCompanyCode;
                            consignee.consigneeAddressCode=CscContantAndCompanyDto.province+","+CscContantAndCompanyDto.city+","+CscContantAndCompanyDto.area;
                            if(CscContantAndCompanyDto.street!=null){
                                consignee.consigneeAddressCode=consignee.consigneeAddressCode+","+CscContantAndCompanyDto.street;
                            }
                            vueObj.consigneeData.push(consignee);
                        });
                        vueObj.totalConsignee=result.result.total;
                    } else if (result.code == 403) {
                        alert("没有权限")
                    }
                },"json");

            },

            handleConsigneeSizeChange:function(val){
                this.consigneePageSize=val;
                this.selectConsignee();
            },
            handleConsigneeCurrentPage:function(val){
                this.currentConsigneePage=val;
                this.selectConsignee();
            },
            cancelSelectConsignee:function(){
                this.consigneeData=[];
                this.consigneePageSize=10;
                this.totalConsignee=0;
                this.chosenSend=false;
            },
            setCurrentConsigneeInfo:function(val){
                this.consigneeName=val.consigneeName;
                this.consigneePhoneNumber=val.consigneePhoneNumber;
                this.consigneeContactName=val.consigneeContactName;
                this.consigneeAddress=val.consigneeAddress;
                this.consigneeType=val.type;
                this.consigneeCode=val.consigneeCode;
                this.consigneeContactCode=val.consigneeContactCode;
                this.consigneeAddressCode=val.consigneeAddressCode;
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
                    alert("订单日期不能为空");
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
                if(!this.wareHouseName){
                    alert('仓库名称不能为空!');
                    return;
                }
                if(!this.serviceType){
                    alert('业务类型不能为空!');
                    return;
                }

                if(this.serviceType=="614"){
                    if(!this.supplierName){
                        alert('业务类型为分拨出库时，供应商必须选择!');
                        return;
                    }
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
                    if(!this.consigneeName){
                        alert("提供运输时,收货方不能为空，请选择收货方");
                        return;
                    }
                }else{
                    ofcOrderDTOStr.provideTransport="0";
                }
                //订单基本信息
                ofcOrderDTOStr.orderCode=this.orderCode;
                ofcOrderDTOStr.businessType =this.serviceType;
                ofcOrderDTOStr.merchandiser = this.merchandiser;
                if(this.orderTime){
                    ofcOrderDTOStr.orderTime=DateUtil.format(this.orderTime, "yyyy-MM-dd HH:mm:ss");
                }

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
                ofcOrderDTOStr.warehouseName=this.getWareHouseNameByCode(this.wareHouseName);//仓库名称
                ofcOrderDTOStr.warehouseCode=this.wareHouseName;//仓库编码

                if(this.shipmentTime){
                    ofcOrderDTOStr.shipmentTime= DateUtil.format(this.shipmentTime, "yyyy-MM-dd HH:mm:ss");
                }
                ofcOrderDTOStr.plateNumber=this.plateNumber;
                ofcOrderDTOStr.driverName=this.driverName;
                if(this.driverContactNumber){
                    if(!this.checkPhoneOrMobile(this.driverContactNumber)){
                        alert("输入运输信息时输入正确的联系方式");
                        return;
                    }
                }
                ofcOrderDTOStr.contactNumber=this.driverContactNumber;
                ofcOrderDTOStr.consigneeName=this.consigneeName;
                ofcOrderDTOStr.consigneeCode=this.consigneeCode;
                ofcOrderDTOStr.consigneeContactName=this.consigneeContactName;
                if(this.consigneePhoneNumber){
                    if(!this.checkPhoneOrMobile(this.consigneePhoneNumber)){
                        alert("请输入正确的收货方联系方式");
                        return;
                    }
                }
                ofcOrderDTOStr.consigneeContactPhone=this.consigneePhoneNumber;
                ofcOrderDTOStr.consigneeType=this.consigneeType;
                ofcOrderDTOStr.consigneeContactCode=this.consigneeContactCode;
                cscContantAndCompanyDtoConsigneeStr=this.getCscContantAndCompanyDtoConsigneeStr();
                //目的地
                var consigneeAddressNameMessage =this.consigneeAddress.split(',');
                ofcOrderDTOStr.destinationCode=this.consigneeAddressCode;
                ofcOrderDTOStr.destination=this.consigneeAddress;
                ofcOrderDTOStr.destinationProvince=consigneeAddressNameMessage[0];
                ofcOrderDTOStr.destinationCity=consigneeAddressNameMessage[1];
                ofcOrderDTOStr.destinationDistrict=consigneeAddressNameMessage[2];
                if(!StringUtil.isEmpty(consigneeAddressNameMessage[3])){
                    ofcOrderDTOStr.destinationTowns=consigneeAddressNameMessage[3];
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
                                var newurl = "/ofc/orderStorageOutManager/";
                                var html = window.location.href;
                                var index = html.indexOf("/index#");
                                window.open(html.substring(0,index) + "/index#" + newurl);
                            }
                        });
            },
            getCscContantAndCompanyDtoConsigneeStr:function(){
                var paramConsignee = {};
                var cscContactDto = {};
                var cscContactCompanyDto = {};
                cscContactCompanyDto.contactCompanyName = this.consigneeName;
                cscContactDto.contactName = this.consigneeContactName;
                cscContactDto.purpose = "2";
                cscContactDto.phone =this.consigneePhoneNumber;
                cscContactDto.contactCompanyName = this.consigneeName;
                var consigneeAddressCodeMessage = this.consigneeAddressCode.split(',');
                var consigneeAddressNameMessage =this.consigneeAddress.split(',');
                cscContactDto.province = consigneeAddressCodeMessage[0];
                cscContactDto.city = consigneeAddressCodeMessage[1];
                cscContactDto.area = consigneeAddressCodeMessage[2];
                if(!StringUtil.isEmpty(consigneeAddressCodeMessage[3])){
                    cscContactDto.street = consigneeAddressCodeMessage[3];
                }
                cscContactDto.provinceName = consigneeAddressNameMessage[0];
                cscContactDto.cityName = consigneeAddressNameMessage[1];
                cscContactDto.areaName = consigneeAddressNameMessage[2];
                if(!StringUtil.isEmpty(consigneeAddressNameMessage[3])){
                    cscContactDto.streetName = consigneeAddressNameMessage[3];
                }
                cscContactDto.address=this.consigneeAddress;
                paramConsignee.cscContactDto = cscContactDto;
                paramConsignee.cscContactCompanyDto = cscContactCompanyDto;
                var cscContantAndCompanyDtoConsigneeStr = JSON.stringify(paramConsignee);
                return cscContantAndCompanyDtoConsigneeStr;
            },
            openGoodsList: function(currentRowData) {
                this.chosenGoodCode=true;
                this.currentRowData = currentRowData;
            },
            checkPhoneOrMobile:function(phone){
                var mp=/^1\d{10}$/;
                var pp=/^0\d{2,3}-?\d{7,8}$/;
                if(mp.test(phone)||pp.test(phone)){
                    return true;
                } else {
                    return false;
                }
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