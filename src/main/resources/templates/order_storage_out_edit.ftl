<!DOCTYPE html>
<html>
<head>
    <style lang="css">
        .block {
            margin: 20px 0;
        }
        .el-textarea__inner{
          font-size:12px;
        }
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
        .xe-block{
            overflow:visible;
        }
        .xe-block:after{
            content:".";
            display:block;
            height:0;
            clear:both;
            visibility:hidden;
        }
        .el-table__body-wrapper{
            overflow-y: auto;
            overflow-x: hidden;
        }
    </style>
</head>
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
                      @row-dblclick="setCurrentCustInfo(customerDataInfo.currentRow)" border style="width: 100%" max-height="400">
                <el-table-column type="index"></el-table-column>
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

        <el-dialog title="收货方联系人" v-model="consigneeDataInfo.chosenSend" size="small">
            <el-form :model="consigneeDataInfo.consigneeForm">
                <el-form-item label="名称" :label-width="formLabelWidth">
                    <el-input v-model="consigneeDataInfo.consigneeForm.consigneeName" auto-complete="off"></el-input>
                </el-form-item>
                <el-form-item label="联系人" :label-width="formLabelWidth">
                    <el-input v-model="consigneeDataInfo.consigneeForm.consigneeContactName" auto-complete="off"></el-input>
                </el-form-item>
                <el-form-item label="联系电话" :label-width="formLabelWidth">
                    <el-input v-model="consigneeDataInfo.consigneeForm.consigneeContactPhone" auto-complete="off"></el-input>
                </el-form-item>
                <el-form-item label="" :label-width="formLabelWidth20">
                    <el-button type="primary" @click="selectConsignee">筛选</el-button>
                </el-form-item>
            </el-form>
            <el-table :data="consigneeDataInfo.consigneeData" highlight-current-row @current-change="consigneeHandleCurrentChange"
                      @row-dblclick="setCurrentConsigneeInfo(consigneeDataInfo.consigneeCurrentRow)" border style="width: 100%" max-height="400">
                <el-table-column type="index" label="序号"></el-table-column>
                <el-table-column property="consigneeName" label="名称"></el-table-column>
                <el-table-column property="consigneeContactName" label="联系人"></el-table-column>
                <el-table-column property="consigneeContactPhone" label="联系电话"></el-table-column>
                <el-table-column property="destination" v-if="false" label="地址"></el-table-column>
                <el-table-column property="consigneeCode"  v-if="false" label="收货方编码"></el-table-column>
                <el-table-column property="consigneeType"  v-if="false" label="收货方类型"></el-table-column>
                <el-table-column property="consigneeContactCode"  v-if="false" label="收货方联系人编码"></el-table-column>
                <el-table-column property="destinationCode"  v-if="false" label="收货方地址编码"></el-table-column>
                <el-table-column property="destinationDetailAddress" label="收货方地址"></el-table-column>
                <el-table-column property="provinceName" v-if="false" label="省"></el-table-column>
                <el-table-column property="cityName" v-if="false" label="城市"></el-table-column>
                <el-table-column property="areaName" v-if="false" label="区"></el-table-column>
                <el-table-column property="streetName" v-if="false" label="街道"></el-table-column>
            </el-table>
            <el-pagination @size-change="handleConsigneeSizeChange" @current-change="handleConsigneeCurrentPage" :current-page="consigneeDataInfo.currentConsigneePage" :page-sizes="pageSizes" :page-size="consigneeDataInfo.consigneePageSize" layout="total, sizes, prev, pager, next, jumper" :total="consigneeDataInfo.totalConsignee">
            </el-pagination>
            <div slot="footer" class="dialog-footer">
                <el-button @click="cancelSelectConsignee">取 消</el-button>
                <el-button type="primary" @click="setCurrentConsigneeInfo(consigneeDataInfo.consigneeCurrentRow)">确 定</el-button>
            </div>
        </el-dialog>


        <el-dialog title="供应商信息" v-model="supplierDataInfo.chosenSupplier" size="small">
            <el-form :model="supplierDataInfo.supplierForm">
                <el-form-item label="名称" :label-width="formLabelWidth">
                    <el-input v-model="supplierDataInfo.supplierForm.supportName" auto-complete="off"></el-input>
                </el-form-item>
                <el-form-item label="联系人" :label-width="formLabelWidth">
                    <el-input v-model="supplierDataInfo.supplierForm.contactName" auto-complete="off"></el-input>
                </el-form-item>
                <el-form-item label="联系电话" :label-width="formLabelWidth">
                    <el-input v-model="supplierDataInfo.supplierForm.contactPhone" auto-complete="off"></el-input>
                </el-form-item>
                <el-form-item  label="" :label-width="formLabelWidth20">
                    <el-button type="primary" @click="selectSupplier">筛选</el-button>
                </el-form-item>
            </el-form>

            <el-table border :data="supplierDataInfo.supplierData" highlight-current-row @current-change="handlSuppliereCurrentChange"
                      @row-dblclick="setCurrentSupplierInfo(supplierDataInfo.supplierCurrentRow)" style="width: 100%" max-height="400">
                <el-table-column type="index"></el-table-column>
                <el-table-column property="supportName" label="名称"></el-table-column>
                <el-table-column property="contactName" label="联系人"></el-table-column>
                <el-table-column property="contactPhone" label="联系电话"></el-table-column>
                <el-table-column property="fax" label="传真"></el-table-column>
                <el-table-column property="email" label="邮箱"></el-table-column>
                <el-table-column property="postCode" label="邮编"></el-table-column>
                <el-table-column property="supportCode" label="供应商编码"></el-table-column>
                <el-table-column property="completeAddress" label="地址"></el-table-column>
            </el-table>
            <el-pagination @size-change="handleSupplierSizeChange" @current-change="handleSupplierCurrentPage" :current-page="supplierDataInfo.currentSupplierPage" :page-sizes="pageSizes" :page-size="supplierDataInfo.supplierPageSize" layout="total, sizes, prev, pager, next, jumper" :total="supplierDataInfo.totalSupplier">
            </el-pagination>
            <div slot="footer" class="dialog-footer">
                <el-button @click="cancelSelectSupplier">取 消</el-button>
                <el-button type="primary" @click="setCurrentSupplierInfo(supplierDataInfo.supplierCurrentRow)">确 定</el-button>
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
                    <el-button type="primary" @click="selectGoods">筛选</el-button>
                </el-form-item>
                <el-form-item label="" :label-width="formLabelWidth20">
                    <el-button @click="reSetCondition">重置</el-button>
                </el-form-item>
            </el-form>

            <el-table border :data="goodDataInfo.goodsCodeData" @selection-change="handleSelectionChange" style="width: 100%" max-height="350">
                <el-table-column type="selection"></el-table-column>
                <el-table-column type="index" label="序号"></el-table-column>
                <el-table-column property="goodsType" label="货品种类"></el-table-column>
                <el-table-column property="goodsCategory" label="货品小类"></el-table-column>
                <el-table-column property="goodsBrand" label="品牌"></el-table-column>
                <el-table-column property="goodsCode" label="货品编码"></el-table-column>
                <el-table-column property="goodsName" label="货品名称"></el-table-column>
                <el-table-column property="goodsSpec" label="规格"></el-table-column>
                <el-table-column property="unit" label="单位"></el-table-column>
                <el-table-column property="barCode" label="条形码"></el-table-column>
                <el-table-column property="expiryDate" v-if="false" label="保质期限"></el-table-column>
            </el-table>
            <el-pagination @size-change="handleGoodSizeChange" @current-change="handleGoodCurrentPage" :current-page="goodDataInfo.currentGoodPage" :page-sizes="pageSizes" :page-size="goodDataInfo.goodPageSize" layout="total, sizes, prev, pager, next, jumper" :total="goodDataInfo.totalGoods">
            </el-pagination>
            <div slot="footer" class="dialog-footer">
                <el-button @click="cancelSelectGood">取 消</el-button>
                <el-button type="primary" @click="setCurrentGoodsInfo">确 定</el-button>
            </div>
        </el-dialog>
        <el-dialog title="货品当前库存" v-model="chosenGoodStock" size="small">
            <el-table border :data="goodsStockData"  style="width: 100%">
                <el-table-column type="index" label="序号"></el-table-column>
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
                <el-table-column property="stockQty" label="当前库存量">
                    <template scope="scope">
                        <el-input v-model="scope.row.stockQty" :readOnly="true"></el-input>
                    </template>
                </el-table-column>
                <el-table-column property="deliveryQty" label="导入货品总量">
                    <template scope="scope">
                        <el-input v-model="scope.row.deliveryQty" :readOnly="true"></el-input>
                    </template>
                </el-table-column>
                <el-table-column property="resultQty" label="差异量">
                    <template scope="scope">
                        <el-input v-model="scope.row.resultQty" :readOnly="true"></el-input>
                    </template>
                </el-table-column>
            </el-table>
        </el-dialog>

        <el-form :model="orderForm" :rules="rules" ref="orderForm" label-width="100px" class="demo-ruleForm">
            <div class="xe-pageHeader">
                基本信息
            </div>
            <div class="xe-block">
                <el-form-item label="订单日期" required  prop="orderDate" class="xe-col-3">
                    <el-form-item>
                        <el-date-picker type="date" :clearable="false" :editable="false" v-model="orderForm.orderDate" :picker-options="pickerOptions"></el-date-picker>
                    </el-form-item>
                </el-form-item>
                <el-form-item label="开单员" required  prop="merchandiser" class="xe-col-3">
                    <el-input v-model="orderForm.merchandiser" placeholder="请输入内容"></el-input>
                </el-form-item>
                <el-form-item label="客户名称" required  prop="custName"  class="xe-col-3">
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
                    <el-select v-model="orderForm.wareHouse" placeholder="请选择">
                        <el-option
                                v-for="item in wareHouseOptions"
                                :label="item.label"
                                :value="item.value">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="业务名称" required prop="businessType" class="xe-col-3">
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
                    <el-input
                            placeholder="请选择"
                            icon="search"
                            v-model="orderForm.supportName"
                            v-bind:disabled = "isDisabled"
                            :readOnly="true"
                            @click="openSupplier">
                    </el-input>
                </el-form-item>
                <el-form-item label="备注" prop="notes" class="xe-col-3">
                    <el-input type="textarea"  v-model="orderForm.notes"></el-input>
                </el-form-item>
            </div>

            <div>
                <el-collapse v-model="activeNames" accordion>
                    <el-collapse-item title="运输信息" name="1">
                        <div class="xe-block">
                            <el-form-item label="预计出库时间" class="xe-col-3">
                                <el-date-picker
                                        v-model="orderForm.shipmentTime"
                                        type="datetime"
                                        :clearable="false"
                                        :editable="false"
                                        placeholder="选择日期"
                                        :picker-options="pickerOptions1">
                                </el-date-picker>
                            </el-form-item>
                            <el-form-item label="是否提供运输">
                                <el-checkbox v-model="orderForm.isNeedTransport" @click="isNeedTransport = true"></el-checkbox>
                            </el-form-item>
                        </div>
                        <div class="xe-block">
                            <el-form-item label="车牌号"  prop="plateNumber" class="xe-col-3">
                                <el-input v-model="orderForm.plateNumber" placeholder="请输入内容"></el-input>
                            </el-form-item>
                            <el-form-item label="司机姓名" prop="driverName" class="xe-col-3">
                                <el-input v-model="orderForm.driverName"  placeholder="请输入内容"></el-input>
                            </el-form-item>
                            <el-form-item label="联系电话"  prop="contactNumber" class="xe-col-3">
                                <el-input v-model="orderForm.contactNumber"  placeholder="请输入内容"></el-input>
                            </el-form-item>
                        </div>
                    </el-collapse-item>
                </el-collapse>
            </div>
            <div class="xe-pageHeader">
                收货方信息
            </div>
            <div class="xe-block">
                <el-form-item label="名称"  v-if="!orderForm.isEditable" prop="consigneeName" class="xe-col-3">
                    <el-input
                            placeholder="请选择"
                            icon="search"
                            v-model="orderForm.consigneeName"
                            v-bind:readOnly="!orderForm.isEditable"
                            @click="openConsignee">
                    </el-input>
                </el-form-item>
                <el-form-item label="名称" v-if="orderForm.isEditable" required prop="consigneeName" class="xe-col-3">
                    <el-input v-model="orderForm.consigneeName"></el-input>
                </el-form-item>
                <el-form-item label="联系人" class="xe-col-3" required prop="consigneeContactName">
                    <el-input v-model="orderForm.consigneeContactName" v-bind:readOnly="!orderForm.isEditable"></el-input>
                </el-form-item>
                <el-form-item label="编辑">
                    <el-checkbox v-model="orderForm.isEditable" @change="emptyConsigneeInfo"></el-checkbox>
                </el-form-item>
            </div>
            <div class="xe-block">
                <el-form-item label="联系电话" class="xe-col-3" required prop="consigneeContactPhone">
                    <el-input v-model="orderForm.consigneeContactPhone" v-bind:readOnly="!orderForm.isEditable"></el-input>
                </el-form-item>
                <el-form-item label="地址选择" class="xe-col-3"  v-if="orderForm.isEditable">
                    <city-picker class = "cp cityPicker"
                                 :url = "cityUrl" :readonly = !orderForm.isEditable
                                 :default-data = "defaultData"
                                 :success-callback = "addressCallback"></city-picker>
                </el-form-item>
                <el-form-item label="详细地址" class="xe-col-3">
                    <el-input v-if="!orderForm.isEditable" v-model="orderForm.destinationDetailAddress" v-bind:readOnly="!orderForm.isEditable"></el-input>
                    <el-input v-if="orderForm.isEditable" v-model="orderForm.destination"></el-input>
                </el-form-item>
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
                        <el-input v-model="scope.row.unit" :readOnly="true"></el-input>
                    </template>
                </el-table-column>
                <el-table-column property="quantity" label="出库数量">
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
              <el-table-column property="expiryDate" label="保质期限" v-if="false">
                <template scope="scope">
                  <el-input v-model="scope.row.expiryDate"></el-input>
                </template>
              </el-table-column>
                <el-table-column property="productionTime" label="生产日期">
                    <template scope="scope">
                        <el-date-picker
                                v-model="scope.row.productionTime"
                                align="right"
                                type="date"
                                :clearable="false"
                                :editable="false"
                                @change="accountInvalidTime(scope.row)"
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
                                :clearable="false"
                                :editable="false"
                                placeholder="选择日期"
                                :picker-options="pickerOptions1">
                        </el-date-picker>
                    </template>
                </el-table-column>
                <el-table-column property="supportName" label="供应商批次">
                    <template scope="scope">
                        <el-input v-model="orderForm.supportName" :readOnly="true"></el-input>
                    </template>
                </el-table-column>
                <el-table-column property="supportBatch" v-if="false" label="供应商编码">
                    <template scope="scope">
                        <el-input v-model="orderForm.supporCode" :readOnly="true"></el-input>
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
            <#--<el-button type="primary" @click="validateStockCount">校验当前库存</el-button>-->
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
              callback(new Error('只能选择一周之内的日期!'));
            }else if(value.getTime()>new Date().getTime()){
              callback(new Error('不能选择当前日期的往后日期!'));
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
                cityUrl: sys.rmcPath +"/rmc/addr/citypicker/findByCodeAndType",
                defaultData: {
                    province: {
                        code: "",
                        keyword: "province",
                        title: "请选择省"
                    },
                    city: {
                        code: "",
                        keyword: "city",
                        title: "市"
                    },
                    district: {
                        code: "",
                        keyword: "district",
                        title: "区"
                    },
                    street: {
                        code: "",
                        keyword: "street",
                        title: "街道"
                    }
                },
                activeNames:'',
                wareHouseObj:'',
                thirdLevelAddress:'',
                goodsStockData:[],
                chosenGoodStock:false,
                goodsCode:'',
                goodsName:'',
                goodsSpec:'',
                unit:'',
                goodsCategoryOptions:[],
                goodsType:'',
                goodsCategory:'',
                invalidTime:'',
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
                consigneeDataInfo:{
                    currentConsigneePage:1,
                    consigneePageSize:10,
                    totalConsignee:0,
                    consigneeCurrentRow:null,
                    consigneeData:[],
                    chosenSend:false,
                    consigneeForm: {
                        consigneeName: '',
                        consigneeContactName: '',
                        consigneeContactPhone:''
                    }
                },
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
                productionTime:'',
                fax:'',
                email:'',
                postCode:'',
                currentRowData : '',
                wareHouseOptions:[],
                businessTypeOptions: [{
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
                },
                {
                    value: '617',
                    label: '退车间'
                }
                ],
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
                isDisabled11: false,
                goodsData: [],
                wareHouse:'',
                orderForm:{
                    orderDate:new Date(),
                    merchandiser:'${merchandiser!}',
                    custName:'',
                    customerCode:'',
                    wareHouse:'',
                    businessType:'',
                    custOrderCode:'',
                    notes:'',
                    supportName:'',
                    supportCode:'',
                    shipmentTime:'',
                    isNeedTransport:false,
                    isEditable:false,
                    plateNumber:'',
                    driverName:'',
                    contactNumber:'',
                    consigneeName:'',
                    consigneeContactPhone:'',
                    destination:'',
                    consigneeCode:'',
                    consigneeType:'',
                    consigneeContactCode:'',
                    consigneeContactName:'',
                    destinationCode:'',
                    destinationDetailAddress:'',
                    destinationProvince:'',
                    destinationCity:'',
                    destinationDistrict:'',
                    destinationTowns:''
                },
                rules:{
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
                    contactNumber:[
                        {validator: checkPhoneOrMobile, trigger: 'blur'},
                        { min: 0, max: 30, message: '长度在 0 到 30 个字符', trigger: 'change' }
                    ],
                    consigneeName:[
                        { required: true, message: '请输入收货方名称', trigger: 'change' },
                        { min: 0, max: 30, message: '长度在 0 到 30 个字符', trigger: 'change' }
                    ],
                    consigneeContactName:[
                        { required: true, message: '请输入收货方联系人名称', trigger: 'change' },
                        { min: 0, max: 30, message: '长度在 0 到 30 个字符', trigger: 'change' }
                    ],
                    consigneeContactPhone:[
                        { required: true, message: '请输入联系电话', trigger: 'change' },
                        {validator: checkPhoneOrMobile, trigger: 'blur'},
                        {min: 0, max: 30, message: '长度在 0 到 30 个字符', trigger: 'change' }
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
                                vueObj.orderForm.orderDate=DateUtil.parse(ofcFundamentalInformation.orderTime);
                                vueObj.orderForm.merchandiser=ofcFundamentalInformation.merchandiser;
                                vueObj.orderForm.custName=ofcFundamentalInformation.custName;
                                vueObj.orderForm.custCode=ofcFundamentalInformation.custCode;
                                vueObj.orderForm.custOrderCode=ofcFundamentalInformation.custOrderCode;
                                vueObj.orderForm.businessType=ofcFundamentalInformation.businessType;
                                vueObj.orderForm.notes=ofcFundamentalInformation.notes;
                                if(ofcWarehouseInformation!=null){
                                    vueObj.wareHouseName=ofcWarehouseInformation.warehouseName;
                                    vueObj.wareHouseCode=ofcWarehouseInformation.warehouseCode;
                                    var wareHouse={};
                                    wareHouse.label= vueObj.wareHouseName;
                                    wareHouse.value= vueObj.wareHouseCode;
                                    vueObj.wareHouseOptions.push(wareHouse);
                                    vueObj.orderForm.wareHouse=ofcWarehouseInformation.warehouseCode;
                                    vueObj.orderForm.supplierName=ofcWarehouseInformation.supportName;
                                    vueObj.orderForm.supplierCode=ofcWarehouseInformation.supportCode;
                                    vueObj.orderForm.shipmentTime=DateUtil.parse(ofcWarehouseInformation.shipmentTime);
                                    vueObj.orderForm.plateNumber=ofcWarehouseInformation.plateNumber;
                                    vueObj.orderForm.driverName=ofcWarehouseInformation.driverName;
                                    if(ofcWarehouseInformation.contactNumber!=null){
                                        vueObj.orderForm.contactNumber=ofcWarehouseInformation.contactNumber;
                                    }
                                    if(ofcWarehouseInformation.provideTransport=="1"){
                                        vueObj.orderForm.isNeedTransport=true;
                                    }else{
                                        vueObj.orderForm.isNeedTransport=false;
                                    }
                                    if(ofcDistributionBasicInfo!=null){
                                        //收货方
                                        vueObj.orderForm.consigneeName=ofcDistributionBasicInfo.consigneeName;
                                        vueObj.orderForm.consigneeCode=ofcDistributionBasicInfo.consigneeCode;
                                        vueObj.orderForm.consigneeContactCode=ofcDistributionBasicInfo.consigneeContactCode;
                                        vueObj.orderForm.consigneeContactName=ofcDistributionBasicInfo.consigneeContactName;
                                        vueObj.orderForm.consigneeContactPhone=ofcDistributionBasicInfo.consigneeContactPhone;
                                        if(ofcDistributionBasicInfo.destinationProvince!=null){
                                            vueObj.orderForm.destinationDetailAddress=ofcDistributionBasicInfo.destinationProvince;
                                            vueObj.orderForm.destinationProvince=ofcDistributionBasicInfo.destinationProvince;
                                        }
                                        if(ofcDistributionBasicInfo.destinationCity!=null){
                                            vueObj.orderForm.destinationDetailAddress=vueObj.orderForm.destinationDetailAddress+ofcDistributionBasicInfo.destinationCity;
                                            vueObj.orderForm.destinationCity=ofcDistributionBasicInfo.destinationCity;
                                        }
                                        if(ofcDistributionBasicInfo.destinationDistrict!=null){
                                            vueObj.orderForm.destinationDetailAddress=vueObj.orderForm.destinationDetailAddress+ofcDistributionBasicInfo.destinationDistrict;
                                            vueObj.orderForm.destinationDistrict=ofcDistributionBasicInfo.destinationDistrict;
                                        }
                                        if(ofcDistributionBasicInfo.destinationTowns!=null){
                                            vueObj.orderForm.destinationDetailAddress=vueObj.orderForm.destinationDetailAddress+ofcDistributionBasicInfo.destinationTowns;
                                            vueObj.orderForm.destinationTowns=ofcDistributionBasicInfo.destinationTowns;
                                        }
                                        if(ofcDistributionBasicInfo.destination!=null){
                                            vueObj.orderForm.destinationDetailAddress=vueObj.orderForm.destinationDetailAddress+ofcDistributionBasicInfo.destination;
                                            vueObj.orderForm.destination=ofcDistributionBasicInfo.destination;
                                        }
                                        vueObj.orderForm.destinationCode=ofcDistributionBasicInfo.destinationCode;
                                    }
                                    if(ofcGoodsDetailsInfo!=null&&ofcGoodsDetailsInfo.length>0){
                                        for(var i=0;i<ofcGoodsDetailsInfo.length;i++){
                                            var goodDetail=ofcGoodsDetailsInfo[i];
                                            var good={};
                                            good.goodsType=goodDetail.goodsType;
                                            var p={};
                                            p.label=goodDetail.goodsCategory;
                                            p.value=goodDetail.goodsCategory;
                                            vueObj.goodsCategoryOptions.push(p);
                                            good.goodsCategory=goodDetail.goodsCategory;
                                            good.goodsCode=goodDetail.goodsCode;
                                            good.goodsName=goodDetail.goodsName;
                                            good.goodsSpec=goodDetail.goodsSpec;
                                            good.quantity=goodDetail.quantity;
                                            good.unitPrice=goodDetail.unitPrice;
                                            good.productionBatch=goodDetail.productionBatch;
                                            good.productionTime=DateUtil.parse(goodDetail.productionTime);
                                            good.invalidTime=DateUtil.parse(goodDetail.invalidTime);
                                            good.supportName=ofcWarehouseInformation.supportName;
                                            good.supportBatch=goodDetail.supportBatch;
                                            vueObj.orderForm.supportCode=goodDetail.supportBatch;
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
                this.customerDataInfo.currentRow = val;
            },
            handlSuppliereCurrentChange:function(val) {
                this.supplierDataInfo.supplierCurrentRow = val;
            },
            handleSelectionChange:function(val){
                this.multipleSelection = val;
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
                            layer.msg("暂时未查询到该客户下的仓库信息！");
                        } else if (result.code == 200) {
                            $.each(data,function (index,rmcWarehouseRespDto) {
                                var warehouse={};
                                warehouse.label=rmcWarehouseRespDto.warehouseName;
                                warehouse.value= rmcWarehouseRespDto.warehouseCode;
                                vueObj.wareHouseOptions.push(warehouse);
                            });
                            if(vueObj.wareHouseOptions.length==1){
                                vueObj.orderForm.wareHouse=vueObj.wareHouseOptions[0].value;
                            }
                        } else if (result.code == 403) {
                            vueObj.promptInfo("没有权限",'error');
                        } else {
                            if(result.message==null||result.message==""){
                                vueObj.promptInfo("查询到该客户下的仓库信息异常",'error');
                            }else{
                                vueObj.promptInfo(result.message,'error');
                            }

                        }
                    },"json");
                } else {
                    vueObj.promptInfo("请选择客户信息!",'warning');
                }
            },
            loadWareHouseByCustomerCode:function(){
                var vueObj=this;
                CommonClient.syncpost(sys.rootPath + "/ofc/queryWarehouseByCustomerCode", {"customerCode":vueObj.orderForm.customerCode}, function(result) {
                    var data=result.result;
                    if (data == undefined || data == null || data.length ==0) {
                        vueObj.wareHouseName="";
                        layer.msg("暂时未查询到该客户下的仓库信息！");
                    } else if (result.code == 200) {
                        $.each(data,function (index,rmcWarehouseRespDto) {
                            var warehouse={};
                            warehouse.label=rmcWarehouseRespDto.warehouseName;
                            warehouse.value= rmcWarehouseRespDto.warehouseCode;
                            vueObj.wareHouseOptions.push(warehouse);
                        });
                    } else if (result.code == 403) {
                        vueObj.promptInfo("没有权限","error");
                    } else {
                        if(result.message==null||result.message==""){
                            vueObj.promptInfo("查询到该客户下的仓库信息","error");
                        }else{
                            vueObj.promptInfo(result.message,"error");
                        }
                    }
                },"json");
            },
            consigneeHandleCurrentChange:function(val) {
                this.consigneeDataInfo.consigneeCurrentRow=val;

            },
            handleCustomerSizeChange:function(val) {
                this.customerDataInfo.customerPageSize=val;
                this.selectCustomer();
            },
            handleCustomerCurrentPage:function(val) {
                this.customerDataInfo.currentCustomerPage = val;
                this.selectCustomer();
            },
            addGoods:function(){
                if(StringUtil.isEmpty(this.orderForm.custName)&&StringUtil.isEmpty(this.orderForm.custCode)){
                    this.promptInfo("请选择客户!",'warning');
                    return;
                }
                this.goodDataInfo.chosenGoodCode = true;
                var vueObj=this;
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
            deleteRow:function(index, rows) {
                rows.splice(index, 1);
            },
            getGoodsCategory:function(val) {
                var vueObj=this;
               // val.goodsCategory = null;
                var typeId=val.goodsType;
                this.goodsType=typeId;
                CommonClient.syncpost(sys.rootPath + "/ofc/getCscGoodsTypeList",{"cscGoodsType":typeId},function(data) {
                    data=eval(data);
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
            goodsCodeClick:function() {
                console.log('弹窗');
            },
            selectSupplier:function(){
                if(StringUtil.isEmpty(this.orderForm.custName)&&StringUtil.isEmpty(this.orderForm.custCode)){
                    this.promptInfo("请选择客户!",'warning');
                    this.supplierDataInfo.chosenSupplier=false;
                    return;
                }
                this.supplierDataInfo.supplierData=[];
                var vueObj=this;
                var param = {};
                param = vueObj.supplierDataInfo.supplierForm;
                param.customerCode = vueObj.orderForm.custCode;
                param.pNum = vueObj.supplierDataInfo.currentSupplierPage;
                param.pSize=vueObj.supplierDataInfo.supplierPageSize;
                CommonClient.syncpost(sys.rootPath + "/ofc/supplierSelect",param, function(result) {
                    vueObj.supplierDataInfo.supplierData = [];
                    vueObj.orderForm.supportName = '';
                    var data = eval(result);
                    if (data == undefined || data == null || data.result == undefined || data.result ==null || data.result.size == 0) {
                        layer.msg("暂时未查询到供应商信息！！");
                    } else if (data.code == 200) {
                        $.each(data.result.list,function (index,CscSupplierInfoDto) {
                            var supplier={};
                            supplier.supportName=StringUtil.nullToEmpty(CscSupplierInfoDto.supplierName);
                            supplier.contactName=StringUtil.nullToEmpty(CscSupplierInfoDto.contactName);
                            supplier.contactPhone=StringUtil.nullToEmpty(CscSupplierInfoDto.contactPhone);
                            supplier.fax=StringUtil.nullToEmpty(CscSupplierInfoDto.fax);
                            supplier.email=StringUtil.nullToEmpty(CscSupplierInfoDto.email);
                            supplier.postCode=StringUtil.nullToEmpty(CscSupplierInfoDto.postCode);
                            supplier.supportCode=StringUtil.nullToEmpty(CscSupplierInfoDto.supplierCode);
                            supplier.completeAddress=StringUtil.nullToEmpty(CscSupplierInfoDto.completeAddress);

                            vueObj.supplierDataInfo.supplierData.push(supplier);

                        });
                        vueObj.supplierDataInfo.totalSupplier=data.result.total;
                    } else if (result.code == 403) {
                        vueObj.promptInfo("没有权限",'error');
                    }
                },"json");

            },
            handleSupplierSizeChange:function(val){
                this.supplierDataInfo.supplierPageSize = val;
                this.selectSupplier();
            },
            handleSupplierCurrentPage:function(val){
                this.supplierDataInfo.currentSupplierPage = val;
                this.selectSupplier();
            },

            setCurrentSupplierInfo:function(val){
                if (val != null) {
                    this.orderForm.supportName=val.supportName;
                    this.orderForm.supportCode=val.supportCode;
                    this.supplierDataInfo.chosenSupplier=false;
                } else {
                    this.promptInfo("请选择供应商!",'warning');
                }
            },
            setCurrentGoodsInfo:function(){
                
                if(this.multipleSelection.length<1){
                    this.promptInfo("请至少选择一条货品明细!",'warning');
                    return;
                }
                this.goodDataInfo.chosenGoodCode = false;
                for(var i=0;i<this.multipleSelection.length;i++){
                    var val=this.multipleSelection[i];
                    var newData = {
                        goodsType: val.goodsType,
                        goodsCategory: val.goodsCategory,
                        goodsCode: val.goodsCode,
                        goodsName: val.goodsName,
                        goodsSpec: val.goodsSpec,
                        unit: val.unit,
                        quantity: '',
                        unitPrice:'',
                        productionBatch:'',
                        expiryDate:val.expiryDate,
                        productionTime:'',
                        invalidTime:'',
                        supportName:this.orderForm.supportName,
                        supportBatch:this.orderForm.supportCode
                    };
                    this.goodsData.push(newData);
                }
            },
            cancelSelectSupplier:function(){
                this.supplierDataInfo.supplierData=[];
                this.supplierDataInfo.chosenSupplier=false;
            },
            selectConsignee:function(){
                this.consigneeDataInfo.consigneeData=[];
                var vueObj=this;
                var cscContactDto = {};
                var cscContactCompanyDto = {};
                var cscContantAndCompanyDto={};
                cscContactCompanyDto.contactCompanyName = this.consigneeDataInfo.consigneeForm.consigneeName;
                cscContactDto.purpose = "1";
                cscContactDto.contactName = this.consigneeDataInfo.consigneeForm.consigneeContactName;
                cscContactDto.phone = this.consigneeDataInfo.consigneeForm.consigneeContactPhone;
                cscContantAndCompanyDto.cscContactDto = cscContactDto;
                cscContantAndCompanyDto.cscContactCompanyDto = cscContactCompanyDto;
                var customerCode = this.orderForm.custCode;
                cscContantAndCompanyDto.pageNum=this.consigneeDataInfo.currentConsigneePage;
                cscContantAndCompanyDto.pageSize=this.consigneeDataInfo.consigneePageSize;
                cscContantAndCompanyDto = JSON.stringify(cscContantAndCompanyDto);
                CommonClient.syncpost(sys.rootPath + "/ofc/contactSelectForPage",{"cscContantAndCompanyDto":cscContantAndCompanyDto,"customerCode":customerCode}, function(result) {
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
                            consignee.consigneeContactPhone=CscContantAndCompanyDto.phone;
                            consignee.provinceName=CscContantAndCompanyDto.provinceName;
                            consignee.cityName=CscContantAndCompanyDto.cityName;
                            consignee.areaName=CscContantAndCompanyDto.areaName;
                            consignee.streetName=CscContantAndCompanyDto.streetName;
                            consignee.destination=CscContantAndCompanyDto.address;
                            consignee.destinationDetailAddress=CscContantAndCompanyDto.detailAddress;
                            consignee.consigneeContactCode=CscContantAndCompanyDto.contactCode;
                            consignee.consigneeCode=CscContantAndCompanyDto.contactCompanyCode;
                            consignee.destinationCode=CscContantAndCompanyDto.province+","+CscContantAndCompanyDto.city+","+CscContantAndCompanyDto.area;
                            if(CscContantAndCompanyDto.street!=null){
                                consignee.destinationCode=consignee.destinationCode+","+CscContantAndCompanyDto.street;
                            }
                            vueObj.consigneeDataInfo.consigneeData.push(consignee);
                        });
                        vueObj.consigneeDataInfo.totalConsignee=result.result.total;
                        if( vueObj.consigneeDataInfo.consigneeData.length==1){
                            vueObj.setCurrentConsigneeInfo(vueObj.consigneeDataInfo.consigneeData[0]);
                        }
                    } else if (result.code == 403) {
                        vueObj.promptInfo("没有权限",'error');
                    }
                },"json");

            },

            handleConsigneeSizeChange:function(val){
                this.consigneeDataInfo.consigneePageSize=val;
                this.selectConsignee();
            },
            handleConsigneeCurrentPage:function(val){
                this.consigneeDataInfo.currentConsigneePage=val;
                this.selectConsignee();
            },
            cancelSelectConsignee:function(){
                this.consigneeDataInfo.consigneeData=[];
                this.consigneeDataInfo.consigneePageSize=10;
                this.consigneeDataInfo.totalConsignee=0;
                this.consigneeDataInfo.chosenSend=false;
            },
            setCurrentConsigneeInfo:function(val){
                this.orderForm.consigneeName="";
                this.orderForm.destination="";
                this.orderForm.consigneeContactName="";
                this.orderForm.consigneeContactPhone="";
                this.orderForm.consigneeContactCode="";
                this.orderForm.consigneeName=val.consigneeName;
                this.orderForm.consigneeContactPhone=val.consigneeContactPhone;
                this.orderForm.consigneeContactName=val.consigneeContactName;
                this.orderForm.destinationProvince=val.provinceName;
                this.orderForm.destinationCity=val.cityName;
                this.orderForm.destinationDistrict=val.areaName;
                this.orderForm.destinationTowns=val.areaName;
                this.orderForm.destinationDetailAddress=val.destinationDetailAddress;
                this.orderForm.consigneeType=val.type;
                this.orderForm.consigneeCode=val.consigneeCode;
                this.orderForm.consigneeContactCode=val.consigneeContactCode;
                this.orderForm.destinationCode=val.destinationCode;
                this.consigneeDataInfo.chosenSend = false;
            },
            selectGoods:function(){
                this.goodDataInfo.goodsCodeData=[];
                var vueObj=this;
                var cscGoods = {};
                var customerCode = vueObj.orderForm.custCode;
                cscGoods.goodsName = vueObj.goodDataInfo.goodsForm.goodsName;
                cscGoods.goodsTypeId=vueObj.goodDataInfo.goodsForm.goodsTypeId;
                cscGoods.goodsTypeSonId=vueObj.goodDataInfo.goodsForm.goodsTypeSonId;
                cscGoods.barCode=vueObj.goodDataInfo.goodsForm.barCode;
                cscGoods.goodsCode=vueObj.goodDataInfo.goodsForm.goodsCode;
                cscGoods.pNum=vueObj.goodDataInfo.currentGoodPage;
                cscGoods.pSize =vueObj.goodDataInfo.goodPageSize;
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
                            goodCode.goodsBrand=cscGoodsVo.brand;
                            goodCode.goodsSpec=cscGoodsVo.specification;
                            goodCode.unit=cscGoodsVo.unit;
                            goodCode.barCode=cscGoodsVo.barCode;
                            if(cscGoodsVo.expiryDate==null||StringUtil.isEmpty(cscGoodsVo.expiryDate)){
                                goodCode.expiryDate=0;
                            }else{
                                goodCode.expiryDate=cscGoodsVo.expiryDate;
                            }
                            vueObj.goodDataInfo.goodsCodeData.push(goodCode);
                        });
                        vueObj.goodDataInfo.totalGoods=data.result.total;
                    } else if (data.code == 403) {
                        vueObj.promptInfo("没有权限",'error');
                    }
                },"json");
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
          saveStorage:function(){
                if(this.orderForm.businessType=="614"){
                    if(StringUtil.isEmpty(this.orderForm.supportName)){
                        this.promptInfo("业务名称为分拨出库时，供应商必须选择!","warning");
                        return;
                    }
                }

              if(this.orderForm.isEditable){
                  if(StringUtil.isEmpty(this.thirdLevelAddress)){
                      this.promptInfo("编辑收货方时，请选择三级地址!",'warning');
                      return;
                  }
              }
              if(StringUtil.isEmpty(this.orderForm.consigneeContactCode)){
                  this.orderForm.consigneeContactCode="XE";
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
              if(StringUtil.isEmpty(this.orderForm.consigneeName)){
                  this.promptInfo("收货方不能为空，请选择收货方","warning");
                  return;
              }
              ofcOrderDTOStr=this.orderForm;
                //是否提供运输
                if(this.orderForm.isNeedTransport){
                    ofcOrderDTOStr.provideTransport="1";
                }else{
                    ofcOrderDTOStr.provideTransport="0";
                }
                //订单基本信息
                ofcOrderDTOStr.orderCode=this.orderCode;
                if(this.orderForm.orderDate){
                    ofcOrderDTOStr.orderTime=DateUtil.format(this.orderForm.orderDate, "yyyy-MM-dd HH:mm:ss");
                }

                //订单基本信息
                ofcOrderDTOStr.warehouseName=this.getWareHouseNameByCode(this.orderForm.wareHouse);//仓库名称
                ofcOrderDTOStr.warehouseCode=this.orderForm.wareHouse;//仓库编码

                if(this.orderForm.shipmentTime){
                    ofcOrderDTOStr.shipmentTime= DateUtil.format(this.orderForm.shipmentTime, "yyyy-MM-dd HH:mm:ss");
                }
                cscContantAndCompanyDtoConsigneeStr=this.getCscContantAndCompanyDtoConsigneeStr();
                var goodsTable =this.goodsData;
                var goodDetail=[];
                if(goodsTable.length <1){
                    this.promptInfo("请添加至少一条货品!",'warning');
                    return;
                }
                //校验金额和格式化日期时间
              for(var i=0;i<goodsTable.length;i++){
                  var good=goodsTable[i];

                  if(StringUtil.isEmpty(good.supportBatch)){
                      if(!StringUtil.isEmpty(this.orderForm.supportCode)){
                          good.supportBatch=this.orderForm.supportCode;
                      }
                  }
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
                  if(good.quantity>99999.999||good.quantity<0||good.quantity!=""||good.quantity==0){
                      if(!good.quantity){
                          this.promptInfo("货品出库数量不能为空",'warning');
                          return;
                      }
                      if(isNaN(good.quantity)){
                          this.promptInfo("货品出库数量必须为数字",'error');
                          return;
                      }
                      if(good.quantity>99999.999){
                          this.promptInfo("货品数量不能大于99999.999",'warning');
                          return;
                      }
                      if(good.quantity<0){
                          this.promptInfo("货品数量不能小于0",'error');
                          return;
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
                  goodDetail.push(good);
              }

                if(goodDetail.length <1){
                    this.promptInfo("请添加至少一条货品!",'warning');
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
                cscContactCompanyDto.contactCompanyName = this.orderForm.consigneeName;
                cscContactDto.contactName = this.orderForm.consigneeContactName;
                cscContactDto.purpose = "2";
                cscContactDto.phone =this.orderForm.consigneeContactPhone;
                cscContactDto.contactCompanyName = this.orderForm.consigneeName;
                var consigneeAddressCodeMessage = this.orderForm.destinationCode.split(',');
                cscContactDto.province = consigneeAddressCodeMessage[0];
                cscContactDto.city = consigneeAddressCodeMessage[1];
                cscContactDto.area = consigneeAddressCodeMessage[2];
                if(!StringUtil.isEmpty(consigneeAddressCodeMessage[3])){
                    cscContactDto.street = consigneeAddressCodeMessage[3];
                }
                cscContactDto.provinceName = this.orderForm.destinationProvince;
                cscContactDto.cityName = this.orderForm.destinationCity;
                cscContactDto.areaName = this.orderForm.destinationDistrict;
                cscContactDto.streetName = this.orderForm.destinationTowns;
                cscContactDto.address=this.orderForm.destination;
                paramConsignee.cscContactDto = cscContactDto;
                paramConsignee.cscContactCompanyDto = cscContactCompanyDto;
                var cscContantAndCompanyDtoConsigneeStr = JSON.stringify(paramConsignee);
                return cscContantAndCompanyDtoConsigneeStr;
            },
            openGoodsList: function(currentRowData) {
                this.goodDataInfo.chosenGoodCode = true;
                this.currentRowData = currentRowData;
            },
            getWareHouseNameByCode:function(val){
                for(var i=0;i<this.wareHouseOptions.length;i++){
                    var option=this.wareHouseOptions[i];
                    if(option.value==val){
                        return option.label;
                    }
                }
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
            emptyConsigneeInfo:function(event){
                this.orderForm.consigneeName="";
                this.orderForm.destination="";
                this.orderForm.consigneeContactName="";
                this.orderForm.consigneeContactPhone="";
                this.orderForm.consigneeCode="";
                this.orderForm.destinationCode="";
                this.orderForm.consigneeContactCode="";
                this.orderForm.destinationDetailAddress="";
                if(event.target.checked){
                    this.orderForm.isEditable=true;
                }else{
                    this.orderForm.isEditable=false;
                }
            },
            openConsignee:function(){
                if(StringUtil.isEmpty(this.orderForm.custName)){
                    this.promptInfo("请选择客户",'warning');
                    this.consignorDataInfo.chosenSend=false;
                    return;
                }
                this.selectConsignee();
                if(this.consigneeDataInfo.consigneeData.length==1){
                    this.openMessage();
                }else{
                    this.consigneeDataInfo.chosenSend = true;
                    this.consigneeDataInfo.consigneeData=[];
                }
            },
            openMessage:function(){
                var _this=this;
                _this.$confirm('您只有一条发货方记录, 点击确认将自动帮你加载?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(function() {
                    _this.setCurrentConsigneeInfo(_this.consigneeDataInfo.consigneeData[0]);
                }).catch(function() {
                    _this.orderForm.consigneeName="";
                    _this.orderForm.destination="";
                    _this.orderForm.consigneeContactName="";
                    _this.orderForm.consigneeContactPhone="";
                    _this.orderForm.consigneeContactCode="";
                });
            },
            openSupplier:function(){
                if(StringUtil.isEmpty(this.orderForm.custName)){
                    this.promptInfo("请选择客户",'warning');
                    this.supplierDataInfo.chosenSupplier=false;
                    return;
                }
                this.selectSupplier();
                if(this.supplierDataInfo.supplierData.length==1){
                    this.openSupplierMessage();
                }else{
                    this.supplierDataInfo.chosenSupplier = true;
                    this.supplierDataInfo.supplierData=[];
                }
            },
            openSupplierMessage:function(){
                var _this=this;
                _this.$confirm('您只有一条供应商记录, 点击确认将自动帮你加载?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(function() {
                    _this.setCurrentSupplierInfo(_this.supplierDataInfo.supplierData[0]);
                }).catch(function() {
                    _this.orderForm.supportName="";
                });
            },
            accountInvalidTime:function(val){
                if(!val.expiryDate){
                    val.expiryDate = null;
                    if(val.productionTime!=null) {
                        val.invalidTime = new Date(val.productionTime.getTime() + val.expiryDate * 3600 * 1000 * 24);
                    }
                }
            },
          reSetCondition:function(){
                this.goodDataInfo.goodsForm.goodsName="";
                this.goodDataInfo.goodsForm.barCode="";
                this.goodDataInfo.goodsForm.goodsCode="";
                this.goodDataInfo.goodsForm.goodsTypeSonId="";
                this.goodDataInfo.goodsForm.goodsTypeId="";
            },
           addressCallback:function(val){
                if(val!=null&&val.length>0){
                    for(var i=0;i<val.length;i++){
                        var addr=val[i];
                        if(addr.keyword=="province"){
                            this.orderForm.destinationProvince=addr.title;
                            this.orderForm.destinationDetailAddress=addr.title;
                            this.orderForm.destinationCode=addr.code;
                        }else if(addr.keyword=="city"){
                            this.orderForm.destinationCity=addr.title;
                            this.orderForm.destinationDetailAddress=this.orderForm.destinationDetailAddress+addr.title;
                            this.orderForm.destinationCode=this.orderForm.destinationCode+","+addr.code;
                        }else if(addr.keyword=="district"){
                            this.orderForm.destinationDistrict=addr.title;
                            this.orderForm.destinationDetailAddress=this.orderForm.destinationDetailAddress+addr.title;
                            this.orderForm.destinationCode=this.orderForm.destinationCode+","+addr.code;
                        }else if(addr.keyword=="street"){
                            this.orderForm.destinationTowns=addr.title;
                            this.orderForm.destinationDetailAddress=this.orderForm.destinationDetailAddress+addr.title;
                            this.orderForm.destinationCode=this.orderForm.destinationCode+","+addr.code;
                        }
                    }
                    this.thirdLevelAddress=this.orderForm.destinationCode;
                }
            },
            validateStockCount:function(){
                
                var _this=this;
                _this.goodsStockData=[];
                if(StringUtil.isEmpty(_this.orderForm.custCode)){
                    _this.promptInfo("请选择客户",'warning');
                    return;
                }
                if(StringUtil.isEmpty(_this.orderForm.wareHouse)){
                    _this.promptInfo("请选择仓库",'warning');
                    return;
                }
                if(_this.goodsData.length<1){
                    _this.promptInfo("请至少选择一条货品",'warning');
                    return;
                }
                var orderGoodsListStr = JSON.stringify(_this.goodsData);
                CommonClient.syncpost(sys.rootPath + "/ofc/validateStockCount", {"orderGoodsListStr":orderGoodsListStr,"custCode":_this.orderForm.custCode,"warehouseCode":_this.orderForm.wareHouse}, function(result) {
                    if (result == undefined || result == null) {
                    } else if (result.code == 200) {
                        _this.promptInfo("货品库存充足！","success");
                    } else if (result.code == 403) {
                        _this.promptInfo("没有权限",'error');
                    } else if (result.code == 500) {
                        if(result.result!=null&&result.result.length>0){
                            $.each(result.result,function (index,responseMsg) {
                                var stock={};
                                stock.goodsCode=responseMsg.skuCode;
                                stock.goodsName=responseMsg.skuName;
                                stock.stockQty=responseMsg.stockQty;
                                stock.deliveryQty=responseMsg.deliveryQty;
                                stock.resultQty=responseMsg.resultQty;
                                _this.goodsStockData.push(stock);
                            });
                        }
                        _this.chosenGoodStock=true;
                    }
                },"json");
            }
        }
    });
</script>


</html>