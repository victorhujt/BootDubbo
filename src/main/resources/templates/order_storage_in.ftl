<!DOCTYPE html>
<html>
<head>
    <style lang="css">
        .list-mian-01 {
            padding:20px;
        }
        .block {
            margin: 20px 0;
        }
        .el-select {
            width: 150px;
        }
        .el-input{
            width:150px!important;
        }
        .hr{
            margin:10px 0;
        }
        .label{
            display:inline-block;
            width:100px;
            font-size:14px;
            margin-right:10px;
            text-align:right;
        }
        .el-date-editor{
            width:150px!important;
        }
        .el-pagination{
            float:right;
            margin-top:5px;
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
                <el-form-item>
                    <el-button type="primary" @click="selectConsignor">筛选</el-button>
                </el-form-item>
            </el-form>

            <el-table :data="consignorData" highlight-current-row @current-change="consignorHandleCurrentChange" style="width: 100%">
                <el-table-column type="index"></el-table-column>
                <el-table-column property="consignorName" label="名称"></el-table-column>
                <el-table-column property="consignorContactName" label="联系人"></el-table-column>
                <el-table-column property="consignorPhoneNumber" label="联系电话"></el-table-column>
                <el-table-column property="consignorAddress" label="地址"></el-table-column>
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
                <el-form-item>
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
                <el-form-item>
                    <el-button type="primary" @click="selectGoods">筛选</el-button>
                </el-form-item>
            </el-form>

            <el-table :data="goodsCodeData" highlight-current-row @current-change="handlGoodCurrentChange" style="width: 100%">
                <el-table-column type="index"></el-table-column>
                <el-table-column property="goodsType" label="货品类别"></el-table-column>
                <el-table-column property="goodsClass" label="货品小类"></el-table-column>
                <el-table-column property="goodsCode" label="货品编码"></el-table-column>
                <el-table-column property="goodsName" label="货品名称"></el-table-column>
                <el-table-column property="goodsSpecifications" label="规格"></el-table-column>
                <el-table-column property="goodsUnit" label="单位"></el-table-column>
            </el-table>
            <el-pagination @size-change="handleGoodSizeChange" @current-change="handleGoodCurrentPage" :current-page="currentPage4" :page-sizes="pageSizes" :page-size="goodPageSize" layout="total, sizes, prev, pager, next, jumper" :total="totalGoods">
            </el-pagination>
            <div slot="footer" class="dialog-footer">
                <el-button @click="cancelSelectGood">取 消</el-button>
                <el-button type="primary" @click="setCurrentGoodsInfo(goodCurrentRow)">确 定</el-button>
            </div>
        </el-dialog>





        <#--<el-dialog title="收货方联系人" v-model="chosenReceive" size="small">-->
            <#--<el-form :model="receiveForm">-->
                <#--<el-form-item label="名称" :label-width="formLabelWidth">-->
                    <#--<el-input v-model="receiveForm.name" auto-complete="off"></el-input>-->
                    <#--<el-button type="primary" @click="">查询</el-button>-->
                <#--</el-form-item>-->
            <#--</el-form>-->

            <#--<el-table :data="receiveCusTableDate" highlight-current-row @current-change="receiveHandleCurrentChange" style="width: 100%">-->
                <#--<el-table-column type="index"></el-table-column>-->
                <#--<el-table-column property="receiveCusName" label="名称"></el-table-column>-->
                <#--<el-table-column property="receiveContacts" label="联系人"></el-table-column>-->
                <#--<el-table-column property="receiveNumPhone" label="联系电话"></el-table-column>-->
                <#--<el-table-column property="receiveAddress" label="地址"></el-table-column>-->
            <#--</el-table>-->
            <#--<el-pagination @size-change="handleSizeChange" @current-change="handleCurrentPage" :current-page="currentPage4" :page-sizes="[100, 200, 300, 400]" :page-size="100" layout="total, sizes, prev, pager, next, jumper" :total="400">-->
            <#--</el-pagination>-->
            <#--<div slot="footer" class="dialog-footer">-->
                <#--<el-button @click="chosenReceive = false">取 消</el-button>-->
                <#--<el-button type="primary" @click="receiveSetCurrentCusInfo(receiveCurrentRow)">确 定</el-button>-->
            <#--</div>-->
        <#--</el-dialog>-->
        <div class="hr">
            基本信息
            <hr>
        </div>
        <div class="block">
            <label class="label">订单日期</label>
            <el-date-picker
                    v-model="orderdate"
                    align="right"
                    type="date"
                    placeholder="选择日期"
                    value="${(orderTime)!""}"
                    :picker-options="pickerOptions1">
            </el-date-picker>
            <div  v-if="seenOrderDateNotNull">订单日期不能为空</div>
            <label class="label">开单员</label>
            <el-input v-model="merchandiser" placeholder="请输入内容"  maxlength="50"  value="${(merchandiser)!""}"></el-input>
            <label class="label">客户名称</label>
            <el-input
                    placeholder="请选择"
                    icon="search"
                    v-model="customerName"
                    @click="chosenCus = true" maxlength="100">
            </el-input>
            <div v-if="seenCustomerNameNotNull">客户名称不能为空</div>

        </div>
        <div class="block">
            <label class="label">仓库名称</label>
            <el-select v-model="wareHouse" placeholder="请选择">
                <el-option
                        v-for="item in wareHouseOptions"
                        :label="item.label"
                        :value="item.value">
                </el-option>
            </el-select>
            <div v-if="seenWareHouseNotNull">仓库名称不能为空</div>
            <label class="label">业务类型</label>
            <el-select v-model="serviceType" placeholder="请选择">
                <el-option
                        v-for="item in serviceTypeOptions"
                        :label="item.label"
                        :value="item.value">
                </el-option>
            </el-select>
            <div v-if="seenServiceTypeNotNull">业务类型不能为空</div>
            <label class="label">客户订单号</label>
            <el-input v-model="customerOrderNum" placeholder="请输入内容" maxlength="30"></el-input>
        </div>
        <div  class="block">
            <label class="label">备注</label>
            <el-input type="textarea" :rows="2" placeholder="请输入内容" v-model="notes">
            </el-input>
        </div>
        <div class="block">
        </div>
        <el-col :span="11"><div class="grid-content">
            <div class="hr">
                供应商信息
                <hr>
            </div>
            <div class="block">
                <label class="label">供应商名称</label>
                <el-input
                        placeholder="请选择"
                        icon="search"
                        v-model="supplierName"
                        v-bind:disabled = "isDisabled"
                        @click="chosenSupplier = true">
                </el-input>
                <label class="label">联系人</label>
                <el-input v-model="contactName" placeholder="请输入内容"></el-input>
            </div>
            <div class="block">
                <label class="label">联系电话</label>
                <el-input v-model="contactPhone" placeholder="请输入内容"></el-input>
                <label class="label">地址</label>
                <el-input v-model="completeAddress" placeholder="请输入内容"></el-input>
            </div>
        </div></el-col>

        <div class="hr">
            运输信息
            <hr>
        </div>
        <div class="block">
            <label class="label">预计入库时间</label>
            <el-date-picker
                    v-model="indate"
                    align="right"
                    type="date"
                    placeholder="选择日期"
                    :picker-options="pickerOptions1">
            </el-date-picker>
            <el-checkbox v-model="isNeedTransport">是否提供运输服务</el-checkbox>
            <label class="label">车牌号</label>
            <el-input v-model="plateNumber" placeholder="请输入内容"></el-input>
            <label class="label">司机姓名</label>
            <el-input v-model="driverName" placeholder="请输入内容"></el-input>
            <label class="label">联系电话</label>
            <el-input v-model="driverContactNumber" placeholder="请输入内容"></el-input>


        </div>

        <el-col :span="11"><div class="grid-content">
            <div class="hr">
                发货方信息
                <hr>
            </div>
            <div class="block">
                <label class="label">名称</label>
                <el-input
                        placeholder="请选择"
                        icon="search"
                        v-model="consignorName"
                        v-bind:disabled = "isDisabled"
                        @click="chosenSend = true">
                </el-input>
                <label class="label">联系人</label>
                <el-input v-model="consignorContactName" placeholder="请输入内容"></el-input>
            </div>
            <div class="block">
                <label class="label">联系电话</label>
                <el-input v-model="consignorPhoneNumber" placeholder="请输入内容"></el-input>
                <label class="label">地址选择</label>
                <el-input v-model="consignorAddress" placeholder="请输入内容"></el-input>
            </div>
        </div></el-col>
        <el-col :span="2" style="height:1px;"><div class="grid-content"></div></el-col>
        <#--<el-col :span="11"><div class="grid-content">-->
            <#--<div class="hr">-->
                <#--收货方信息-->
                <#--<hr>-->
            <#--</div>-->
            <#--<div class="block">-->
                <#--<label class="label">名称</label>-->
                <#--<el-input-->
                        <#--placeholder="请选择"-->
                        <#--icon="search"-->
                        <#--v-model="receiveName"-->
                        <#--v-bind:disabled = "isDisabled"-->
                        <#--@click="chosenReceive = true">-->
                <#--</el-input>-->
                <#--<label class="label">联系人</label>-->
                <#--<el-input v-model="receiveContacts" placeholder="请输入内容"></el-input>-->
            <#--</div>-->
            <#--<div class="block">-->
                <#--<label class="label">联系电话</label>-->
                <#--<el-input v-model="receivePhone" placeholder="请输入内容"></el-input>-->
                <#--<label class="label">地址选择</label>-->
                <#--<el-input v-model="receiveAddress" placeholder="请输入内容"></el-input>-->
            <#--</div>-->
        <#--</div></el-col>-->
        <div class="hr">
            服务项目及费用
            <hr>
        </div>
        <div class="hr">
            货品信息
            <hr>
        </div>
        <div class="block" style="float:right;">
            <el-button type="primary" v-on:click="add">添加一行</el-button>
        </div>
        <el-table :data="goodsData" border highlight-current-row @current-change="GoodsCurrentChange" style="width: 100%">
            <el-table-column type="index"></el-table-column>
            <el-table-column property="goodsType" label="货品种类">
                <template scope="scope">
                    <el-select size="small" v-model="scope.row.goodsType" placeholder="请选择"  @change="getGoodsClass(scope.row)">
                        <el-option
                                v-for="item in goodsMsgOptions"
                                :label="item.label"
                                :value="item.value"
                                style="width:100px;">
                        </el-option>
                    </el-select>
                </template>
            </el-table-column>
            <el-table-column property="goodsClass" label="货品类别">
                <template scope="scope">
                    <el-select  size="small" v-model="scope.row.goodsClass"  placeholder="请选择">
                        <el-option
                                v-for="subitem in goodsClassOptions"
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
                            v-model="goodsCode"
                            v-bind:disabled = "isDisabled"
                            @click="chosenGoodCode = true">
                    </el-input>
                </template>
            </el-table-column>
            <el-table-column property="goodsName" label="货品名称">
                <template scope="scope">
                    <el-input v-model="goodsName" placeholder="请输入内容"></el-input>
                </template>
            </el-table-column>
            <el-table-column property="goodsSpecifications" label="规格">
                <template scope="scope">
                    <el-input v-model="goodsSpecifications" placeholder="请输入内容"></el-input>
                </template>
            </el-table-column>
            <el-table-column property="goodsUnit" label="单位">
                <template scope="scope">
                    <el-input v-model="goodsUnit" placeholder="请输入内容"></el-input>
                </template>
            </el-table-column>
            <el-table-column property="goodsNumber" label="入库数量">
                <template scope="scope">
                    <el-input v-model="scope.row.goodsNumber" placeholder="请输入内容"></el-input>
                </template>
            </el-table-column>
            <el-table-column property="goodsPrice" label="单价">
                <template scope="scope">
                    <el-input v-model="scope.row.goodsPrice" placeholder="请输入内容"></el-input>
                </template>
            </el-table-column>
            <el-table-column property="goodsBatch" label="批次号">
                <template scope="scope">
                    <el-input v-model="scope.row.goodsBatch" placeholder="请输入内容"></el-input>
                </template>
            </el-table-column>
            <el-table-column property="manufactureTime" label="生产日期">
                <template scope="scope">
                    <el-date-picker
                            v-model="manufactureTime"
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
                            v-model="invalidTime"
                            align="right"
                            type="date"
                            placeholder="选择日期"
                            :picker-options="pickerOptions1">
                    </el-date-picker>
                </template>
            </el-table-column>
            <el-table-column property="goodsOperation" label="操作">
                <template scope="scope">
                    <el-button type="text">删除</el-button>
                </template>
            </el-table-column>
        </el-table>
        <div class="block" style="float:right;">
            <el-button type="primary" v-on:click="saveStorage">下单</el-button>
        </div>
    </div>
</div>
</body>
<script>
    new Vue({
        el: '#app',
        data :function() {
            return {
                seenOrderDateNotNull:false,
                seenCustomerNameNotNull:false,
                seenWareHouseNotNull:false,
                seenServiceTypeNotNull:false,
                goodsCode:'',
                goodsName:'',
                goodsSpecifications:'',
                goodsUnit:'',
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
                goodsClassOptions:[],
                invalidTime:'',
                manufactureTime:'',
                notes:'',
                radio1: '选中且禁用',
                customerOrderNum: '',
                customerName: '',
                receiveName: '',
                receiveContacts: '',
                consignorName:'',
                consignorContactName:'',
                consignorPhoneNumber:'',
                consignorAddress:'',
                chosenSupplier:false,
                chosenGoodCode:false,
                contactName:'',
                contactPhone:'',
                completeAddress:'',
                supplierName:'',
                fax:'',
                email:'',
                postCode:'',
                receivePhone: '',
                receiveAddress: '',
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
                serviceType: '',
                wareHouse:'',
                merchandiser: '',
                orderdate: '',
                indate: '',
                isNeedTransport:'',
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
                    name: '',
                    region: '',
                    date1: '',
                    date2: '',
                    delivery: false,
                    type: [],
                    resource: '',
                    desc: ''
                },
                formLabelWidth: '100px',
                currentPage4: 4,
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
                debugger;
                this.customerName = val.customerName;
                this.customerCode=val.customerCode;
                this.chosenCus = false;
                var vueObj=this;
                CommonClient.post(sys.rootPath + "/ofc/queryWarehouseByCustomerCode", {"customerCode":this.customerCode}, function(result) {
                    debugger;
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
                debugger;
                if(!this.customerName){
                    alert("请选择客户!");
                    return;
                }
                var vueObj=this;
                var newData = {
                    goodsType: '',
                    goodsClass: '',
                    goodsCode: '',
                    goodsName: '',
                    goodsSpecifications: '',
                    goodsUnit: '',
                    goodsNumber: '',
                    goodsPrice:'',
                    goodsBatch:'',
                    manufactureTime:'',
                    invalidTime:'',
                    goodsOperation: '',
                    goodsClassOptions: []
                };
                CommonClient.syncpost(sys.rootPath + "/ofc/getCscGoodsTypeList",{"pid":null},function(data) {
                    data=eval(data);
                    $.each(data,function (index,CscGoodsTypeVo) {
                        var good={};
                        good.label=CscGoodsTypeVo.goodsTypeName;
                        good.value=CscGoodsTypeVo.id;
                        vueObj.goodsMsgOptions.push(good);
                    });
                });
                vueObj.goodsData.push(newData);
            },
            getGoodsClass:function(val) {
                debugger;
                var vueObj=this;
                val.goodsClass = null;
                var typeId=val.goodsType;
                CommonClient.syncpost(sys.rootPath + "/ofc/getCscGoodsTypeList",{"cscGoodsType":typeId},function(data) {
                    data=eval(data);
                    $.each(data,function (index,CscGoodsTypeVo) {
                        var goodClass={};
                        goodClass.label=CscGoodsTypeVo.goodsTypeName;
                        goodClass.value=CscGoodsTypeVo.goodsTypeName;
                        vueObj.goodsClassOptions.push(goodClass);
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
                            debugger;
                            if(!this.customerName){
                                alert("请选择客户");
                                this.chosenSupplier=false;
                                return;
                            }
                            this.supplierData=[];
                            var vueObj=this;
                            CommonClient.post(sys.rootPath + "/ofc/supplierSelect",vueObj.supplierForm, function(data) {
                                data=eval(data);
                                $.each(data,function (index,CscSupplierInfoDto) {
                                    var supplier={};
                                    supplier.supplierName=StringUtil.nullToEmpty(CscSupplierInfoDto.supplierName);
                                    supplier.contactName=StringUtil.nullToEmpty(CscSupplierInfoDto.contactName);
                                    supplier.contactPhone=StringUtil.nullToEmpty(CscSupplierInfoDto.contactPhone);
                                    supplier.fax=StringUtil.nullToEmpty(CscSupplierInfoDto.fax);
                                    supplier.email=StringUtil.nullToEmpty(CscSupplierInfoDto.email);
                                    supplier.postCode=StringUtil.nullToEmpty(CscSupplierInfoDto.postCode);
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
                this.contactName=val.contactName;
                this.contactPhone=val.contactPhone;
                this.completeAddress=val.completeAddress;
                this.chosenSupplier=false;
            },
            setCurrentGoodsInfo:function(val){
                debugger;
                this.goodsCode=val.goodsCode;
                this.goodsName=val.goodsName;
                this.goodsSpecifications=val.goodsSpecifications;
                this.goodsUnit=val.goodsUnit;
                this.chosenGoodCode = false;
            },
            cancelSelectSupplier:function(){
                this.supplierData=[];
                this.chosenSupplier=false;
            },

            selectConsignor:function(){
                if(!this.customerName){
                    alert("请选择客户");
                    this.chosenSend=false;
                    return;
                }
                debugger;
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
                        layer.msg("暂时未查询到收货方信息！！");
                    } else if (result.code == 200) {
                        $.each(result.result.list,function (index,CscContantAndCompanyDto) {
                            var consignor={};
                            consignor.consignorName=CscContantAndCompanyDto.contactCompanyName;
                            consignor.consignorContactName=CscContantAndCompanyDto.contactName;
                            consignor.consignorPhoneNumber=CscContantAndCompanyDto.phone;
                            consignor.consignorAddress=CscContantAndCompanyDto.detailAddress;
                            vueObj.consignorData.push(consignor);
//                            contactList =contactList + "<td style='display: none'>"+CscContantAndCompanyDto.contactCompanySerialNo+"</td>";
//                            contactList =contactList + "<td style='display: none'>"+CscContantAndCompanyDto.contactSerialNo+"</td>";
//                            contactList =contactList + "<td style='display: none'>"+CscContantAndCompanyDto.type+"</td>";
//                            contactList =contactList + "<td style='display: none'>"+CscContantAndCompanyDto.address+"</td>";
//                            contactList =contactList + "<td style='display: none'>"+CscContantAndCompanyDto.provinceName+"</td>";
//                            contactList =contactList + "<td style='display: none'>"+CscContantAndCompanyDto.cityName+"</td>";
//                            contactList =contactList + "<td style='display: none'>"+CscContantAndCompanyDto.areaName+"</td>";
//                            contactList =contactList + "<td style='display: none'>"+CscContantAndCompanyDto.streetName+"</td>";
//                            contactList =contactList + "</tr>";
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
                debugger;
                this.consignorName=val.consignorName;
                this.consignorPhoneNumber=val.consignorPhoneNumber;
                this.consignorContactName=val.consignorContactName;
                this.consignorAddress=val.consignorAddress;
                this.chosenSend = false;
            },


            selectGoods:function(){
                debugger;
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
                            goodCode.goodsClass=cscGoodsVo.goodsTypeName;
                            goodCode.goodsCode=cscGoodsVo.goodsCode;
                            goodCode.goodsName=cscGoodsVo.goodsName;
                            goodCode.goodsSpecifications=cscGoodsVo.specification;
                            goodCode.goodsUnit=cscGoodsVo.unit;
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
                debugger;
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
                                   // debugger;
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
                                    //customer.groupId=cscCustomerVo.groupId;
                                    //customer.customer=cscCustomerVo.customerCode;
                                    vueObj.customerData.push(customer);
                                });
                                debugger;
                                vueObj.total=result.result.total;
                               // vueObj.currentPage=pageNum;
                            } else if (result.code == 403) {
                                 alert("没有权限")
                             }
                        },"json");
            },
            saveStorage:function(){
                debugger;
                if(!this.orderdate){
                    this.seenOrderDateNotNull=true;
                    return;
                }
                if(!this.customerName){
                    this.seenCustomerNameNotNull=true;
                    return;
                }
                if(!this.wareHouse){
                    this.seenWareHouseNotNull=true;
                    return;
                }
                if(!this.serviceType){
                    this.seenServiceTypeNotNull=true;
                    return;
                }
//                //是否提供运输
//                if(){
//
//                }





            }
        }
    });
</script>


</html>