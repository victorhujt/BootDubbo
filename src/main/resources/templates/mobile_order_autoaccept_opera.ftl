</head>
<body>
<div id="app" class="col-xs-12">
    <div class="xe-pageHeader">
        拍照开单信息
            <el-button type="primary" @click="deleteMobileOrder" :disabled = "isDisabledDelete" style="float: right;position: relative;bottom:9px;background: #ff0000;border-color: #ff0000">删除</el-button>
    </div>
    <div class="drag_con" id="drag_con_id">
        <div id='drag_img'>
            <img src="" alt="" id="viewBiggerImg" class="">
        </div>
        <div class="scales"><img src="${OFC_WEB_URL!}/docs/images/scales.png" alt=""></div>
    </div>
    <div class="MaxImg">
        <#if urls?? && (urls?size > 0)>
            <#list urls as url>
                <div class="imgClass  imgone" style="position: relative;">
                    <img src="${url!""}" class="dragAble"/>
                </div>
            </#list>
        </#if>
    </div>
    <el-form :model="mobileOrderVo" ref="mobileOrderVo" label-width="100px" class="demo-ruleForm">
        <div class="xe-block">
            <el-form-item label="流水号" prop="mobileOrderCode" class="xe-col-1">
                <el-input v-model="mobileOrderVo.mobileOrderCode" :readonly="true"></el-input>
            </el-form-item>
            <el-form-item label="上传日期" prop="uploadDate" class="xe-col-1">
                <el-input v-model="mobileOrderVo.uploadDate" :readonly="true"></el-input>
            </el-form-item>
            <el-form-item label="钉钉账号" prop="dingdingAccountNo" class="xe-col-1">
                <el-input v-model="mobileOrderVo.dingdingAccountNo" :readonly="true"></el-input>
            </el-form-item>
            <el-form-item label="开单员" prop="operator" class="xe-col-1">
                <el-input v-model="mobileOrderVo.operator" :readonly="true"></el-input>
            </el-form-item>
            <el-form-item label="业务类型" prop="businessType" class="xe-col-1">
                <el-select v-model="mobileOrderVo.businessType" disabled style="width: 141px;">
                    <el-option v-for="item in businessTypeOptions"
                               :label="item.label"
                               :value="item.value" style="width:80px;">
                    </el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="运输单号" prop="tranCode" class="xe-col-1">
                <el-input v-model="mobileOrderVo.tranCode" :readonly="true"></el-input>
            </el-form-item>
        </div>
    </el-form>


    <div class="col-xs-12">
    <div class="list-mian-01" style="height: 300px;overflow: auto" id="Overflow">
        <!-- 选择客户弹出框 -->
        <el-dialog title="选择客户" v-model="showCustDialog" v-loading.body="custDialog.loading" :close-on-click-modal="closeOnClick" :close-on-press-escape="closeOnPressEsc"
                   :show-close="showClose" @close="closeDialog" size="small">
            <el-form :model="custDialog.custForm">
                <el-form-item label="名称" label-width="100px" class="xe-col-4">
                    <el-input v-model="custDialog.custForm.name" auto-complete="off"></el-input>
                </el-form-item>
                <el-form-item label-width="50px">
                    <el-button type="primary" @click="queryCustomer">筛选</el-button>
                </el-form-item>
            </el-form>

            <el-table :data="custDialog.custData" highlight-current-row @current-change="handleCustCurrentChange"
                      @row-dblclick="setSelectedCustInfo(custDialog.currentSelectedRow)" style="width: 100%">
                <el-table-column type="index"></el-table-column>
                <el-table-column property="custCode" label="客户编码"></el-table-column>
                <el-table-column property="type" label="类型"></el-table-column>
                <el-table-column property="custName" label="公司名称"></el-table-column>
                <el-table-column property="channel" label="渠道"></el-table-column>
                <el-table-column property="productType" label="产品类别"></el-table-column>
            </el-table>
            <el-pagination @size-change="handleCustSizeChange" @current-change="handleCustCurrentPage"
                           :current-page="custDialog.currentPage" :page-sizes="custDialog.pageSizes" :page-size="custDialog.pageSize"
                           :total="custDialog.total" layout="total, sizes, prev, pager, next, jumper">
            </el-pagination>
            <div slot="footer" class="dialog-footer">
                <el-button @click="cancelSelectCustomer">取 消</el-button>
                <el-button type="primary" @click="setSelectedCustInfo(custDialog.currentSelectedRow)">确 定</el-button>
            </div>
        </el-dialog>

        <!-- 发货方联系人弹出框 -->
        <el-dialog title="发货方联系人" v-model="showConsignorDialog" :close-on-click-modal="closeOnClick" :close-on-press-escape="closeOnPressEsc"
                   :show-close="showClose" @close="closeDialog" size="small">
            <el-form :model="consignorDialog.consignorForm">
                <el-form-item label="名称" label-width="100px" class="xe-col-4">
                    <el-input v-model="consignorDialog.consignorForm.name" auto-complete="off"></el-input>
                </el-form-item>
                <el-form-item label="联系人" label-width="100px" class="xe-col-4">
                    <el-input v-model="consignorDialog.consignorForm.contactName" auto-complete="off"></el-input>
                </el-form-item>
                <el-form-item label="联系电话" label-width="100px" class="xe-col-4">
                    <el-input v-model="consignorDialog.consignorForm.phone" auto-complete="off"></el-input>
                </el-form-item>
                <el-form-item label-width="50px">
                    <el-button type="primary" @click="queryConsignor">查询</el-button>
                </el-form-item>
            </el-form>

            <el-table :data="consignorDialog.consignorData" highlight-current-row @current-change="handleConsignorCurrentChange"
                      @row-dblclick="setSelectedContactInfo(consignorDialog.currentSelectedRow, 'consignor')" style="width: 100%">
                <el-table-column type="index"></el-table-column>
                <el-table-column property="contactCompanyName" label="名称"></el-table-column>
                <el-table-column property="contactName" label="联系人"></el-table-column>
                <el-table-column property="phone" label="联系电话"></el-table-column>
                <el-table-column property="detailAddress" label="地址"></el-table-column>
            </el-table>
            <el-pagination @size-change="handleConsignorSizeChange" @current-change="handleConsignorCurrentPage"
                           :current-page="consignorDialog.currentPage" :page-sizes="consignorDialog.pageSizes" :page-size="consignorDialog.pageSize"
                           :total="consignorDialog.total" layout="total, sizes, prev, pager, next, jumper">
            </el-pagination>
            <div slot="footer" class="dialog-footer">
                <el-button @click="cancelSelectContactDialog('consignor')">取 消</el-button>
                <el-button type="primary" @click="setSelectedContactInfo(consignorDialog.currentSelectedRow, 'consignor')">确 定</el-button>
            </div>
        </el-dialog>

        <!-- 收货方联系人弹出框 -->
        <el-dialog title="收货方联系人" v-model="showConsigneeDialog" :close-on-click-modal="closeOnClick" :close-on-press-escape="closeOnPressEsc"
                   :show-close="showClose" @close="closeDialog" size="small">
            <el-form :model="consigneeDialog.consigneeForm">
                <el-form-item label="名称" label-width="100px" class="xe-col-4">
                    <el-input v-model="consigneeDialog.consigneeForm.name" auto-complete="off"></el-input>
                </el-form-item>
                <el-form-item label="联系人" label-width="100px" class="xe-col-4">
                    <el-input v-model="consigneeDialog.consigneeForm.contactName" auto-complete="off"></el-input>
                </el-form-item>
                <el-form-item label="联系电话" label-width="100px" class="xe-col-4">
                    <el-input v-model="consigneeDialog.consigneeForm.phone" auto-complete="off"></el-input>
                </el-form-item>
                <el-form-item label-width="50px">
                    <el-button type="primary" @click="queryConsignee">查询</el-button>
                </el-form-item>
            </el-form>

            <el-table :data="consigneeDialog.consigneeData" highlight-current-row @current-change="handleConsigneeCurrentChange"
                      @row-dblclick="setSelectedContactInfo(consigneeDialog.currentSelectedRow, 'consignee')" style="width: 100%">
                <el-table-column type="index"></el-table-column>
                <el-table-column property="contactCompanyName" label="名称"></el-table-column>
                <el-table-column property="contactName" label="联系人"></el-table-column>
                <el-table-column property="phone" label="联系电话"></el-table-column>
                <el-table-column property="detailAddress" label="地址"></el-table-column>
            </el-table>
            <el-pagination @size-change="handleConsigneeSizeChange" @current-change="handleConsigneeCurrentPage"
                           :current-page="consigneeDialog.currentPage" :page-sizes="consigneeDialog.pageSizes" :page-size="consigneeDialog.pageSize"
                           :total="consigneeDialog.total" layout="total, sizes, prev, pager, next, jumper">
            </el-pagination>
            <div slot="footer" class="dialog-footer">
                <el-button @click="cancelSelectContactDialog('consignee')">取 消</el-button>
                <el-button type="primary" @click="setSelectedContactInfo(consigneeDialog.currentSelectedRow, 'consignee')">确 定</el-button>
            </div>
        </el-dialog>

        <!-- 货品列表 -->
        <el-dialog title="货品列表" v-model="showGoodsDialog" :close-on-click-modal="closeOnClick" :close-on-press-escape="closeOnPressEsc"
                   :show-close="showClose" @close="closeDialog" size="small">
            <el-form :model="goodsDialog.goodsForm">
                <el-form-item label="货品种类" label-width="100px" class="xe-col-4">
                    <template scope="scope">
                        <el-select size="small" v-model="goodsDialog.goodsForm.goodsType" @change="getGoodsCategory(goodsDialog.goodsForm)" placeholder="请选择" >
                            <el-option
                                    v-for="item in goodsTypeOptions"
                                    :label="item.label"
                                    :value="item.value"
                                    style="width:100px;">
                            </el-option>
                        </el-select>
                    </template>
                </el-form-item>
                <el-form-item label="货品小类" label-width="100px" class="xe-col-4">
                    <template scope="scope">
                        <el-select  size="small" v-model="goodsDialog.goodsForm.goodsCategory" placeholder="请选择">
                            <el-option
                                    v-for="item in goodsCategoryOptions"
                                    :label="item.label"
                                    :value="item.value"
                                    style="width:100px;">
                            </el-option>
                        </el-select>
                    </template>
                </el-form-item>
                <el-form-item label="货品名称" label-width="100px" class="xe-col-4">
                    <el-input v-model="goodsDialog.goodsForm.goodsName" auto-complete="off"></el-input>
                </el-form-item>
                <el-form-item label="货品编码" label-width="100px" class="xe-col-4">
                    <el-input v-model="goodsDialog.goodsForm.goodsCode" auto-complete="off"></el-input>
                </el-form-item>
                <el-form-item label="条形码" label-width="100px" class="xe-col-4">
                    <el-input v-model="goodsDialog.goodsForm.barCode" auto-complete="off"></el-input>
                </el-form-item>
                <el-form-item label-width="100px">
                    <el-button type="primary" @click="queryGoods">筛选</el-button>
                    <el-button @click="resetGoodsSearchForm">重置</el-button>
                </el-form-item>
            </el-form>
            <el-table :data="goodsDialog.goodsDialogData" highlight-current-row @current-change="handleGoodsCurrentChange"
                      @row-dblclick="setCurrentGoodsInfo(goodDataInfo.goodCurrentRow)" style="width: 100%" max-height="350">
                <el-table-column type="index" label="序号"></el-table-column>
                <el-table-column property="goodsType" label="货品种类"></el-table-column>
                <el-table-column property="goodsCategory" label="货品小类"></el-table-column>
                <el-table-column property="goodsBrand" label="品牌"></el-table-column>
                <el-table-column property="goodsCode" label="货品编码"></el-table-column>
                <el-table-column property="goodsName" label="货品名称"></el-table-column>
                <el-table-column property="goodsSpec" label="规格"></el-table-column>
                <el-table-column property="unit" label="单位"></el-table-column>
                <el-table-column property="barCode" label="条形码"></el-table-column>
            </el-table>
            <el-pagination @size-change="handleGoodsSizeChange" @current-change="handleGoodsCurrentPage"
                           :current-page="goodsDialog.currentPage" :page-sizes="goodsDialog.pageSizes" :page-size="goodsDialog.pageSize"
                           :total="goodsDialog.total" layout="total, sizes, prev, pager, next, jumper">
            </el-pagination>
            <div slot="footer" class="dialog-footer">
                <el-button @click="cancelSelectGoodsDialog">取 消</el-button>
                <el-button type="primary" @click="setSelectGoodsInfo(goodsDialog.currentSelectedRow)">确 定</el-button>
            </div>
        </el-dialog>

        <el-form :model="orderForm" :rules="rules" ref="orderForm" label-width="100px" class="demo-ruleForm" style = "position: relative; z-index: 100">
            <div class="xe-pageHeader">
                基本信息
            </div>
            <div class="xe-block">
                <el-form-item label="业务类型" prop="businessType" class="xe-col-3">
                    <el-select v-model="orderForm.businessType" disabled>
                        <el-option
                                v-for="item in businessTypeOptions"
                                :label="item.label"
                                :value="item.value">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="开单员" prop="merchandiser" class="xe-col-3">
                    <el-input v-model="orderForm.merchandiser" :readonly="true"></el-input>
                </el-form-item>
                <el-form-item label="运输类型" prop="transportType" class="xe-col-3">
                    <el-radio-group v-model="orderForm.transportType">
                        <el-radio label="10">零担</el-radio>
                        <el-radio label="20">整车</el-radio>
                    </el-radio-group>
                </el-form-item>
            </div>
            <div class="xe-block">
                <el-form-item label="订单日期" prop="orderDate" required class="xe-col-3">
                    <el-date-picker type="date" v-model="orderForm.orderDate" :editable="false"></el-date-picker>
                </el-form-item>
                <el-form-item label="运输单号" prop="transCode" class="xe-col-3">
                    <el-input v-model="orderForm.transCode" :readonly="true"></el-input>
                </el-form-item>
                <el-form-item label="客户订单号" prop="custOrderCode" class="xe-col-3">
                    <el-input v-model="orderForm.custOrderCode"></el-input>
                </el-form-item>
            </div>
            <div class="xe-block">
                <input type="hidden" v-model="orderForm.custCode" />
                <el-form-item label="客户名称" prop="custName" class="xe-col-3">
                    <el-input
                            icon="search"
                            v-model="orderForm.custName"
                            @click="showCustDialog = true" :disabled='true'>
                    </el-input>
                </el-form-item>
                <el-form-item label="承诺到达时间" class="xe-col-3" class="xe-col-3">
                    <el-form-item prop="expectedArrivedTime">
                        <el-date-picker type="date" v-model="orderForm.expectedArrivedTime" :editable="false"></el-date-picker>
                    </el-form-item>
                </el-form-item>
                <el-form-item label="备注" prop="notes" class="xe-col-3">
                    <el-input v-model="orderForm.notes"></el-input>
                </el-form-item>
            </div>
            <!-- 收发货方信息 -->
            <div class="xe-block" style="overflow:visible; position: relative; z-index: 100">
                <div style="float:left;margin-right:18px;" class="xe-col-2">
                    <div class="xe-pageHeader">
                        发货方信息
                    </div>
                    <div class="xe-block">
                        <input type="hidden" v-model="orderForm.consignorCode"/>
                        <el-form-item label="名称" required prop="consignorName" class="xe-col-2">
                            <el-input
                                    icon="search"
                                    v-model="orderForm.consignorName"
                                    v-bind:disabled="isDisabled"
                                    @click="showDialog('consignor', null)">
                            </el-input>
                        </el-form-item>
                    </div>
                    <div class="xe-block">
                        <input type="hidden" v-model="orderForm.consignorContactCode" />
                        <el-form-item label="联系人" required prop="consignorContactName" class="xe-col-4">
                            <el-input v-model="orderForm.consignorContactName"></el-input>
                        </el-form-item>
                        <el-form-item label="联系电话" required prop="consignorContactPhone" class="xe-col-4">
                            <el-input v-model="orderForm.consignorContactPhone"></el-input>
                        </el-form-item>
                    </div>
                    <div class="xe-block" style="overflow: visible">
                        <el-form-item label="地址选择" required prop="departurePlace" class="xe-col-2">
                            <city-picker class = "cp cityPicker" :url="cityUrl"
                                         :default-data="cpConsignorData" :options="cityPickerOptions"
                                         :success-callBack="consignorCallBack" style="position:relative;z-index: 200;"></city-picker>
                        </el-form-item>
                        <el-form-item label="详细地址" prop="consignorAddress" class="xe-col-2">
                        <el-input v-model="orderForm.consignorAddress"></el-input>
                    </el-form-item>
                    </div>
                </div>
                <div style="float:left;" class="xe-col-2">
                    <div class="xe-pageHeader">
                        收货方信息
                    </div>
                    <div class="xe-block">
                        <input type="hidden" v-model="orderForm.consigneeCode" />
                        <el-form-item label="名称" required prop="consigneeName" class="xe-col-2">
                            <el-input
                                    icon="search"
                                    v-model="orderForm.consigneeName"
                                    v-bind:disabled="isDisabled"
                                    @click="showDialog('consignee', null)">
                            </el-input>
                        </el-form-item>
                        <input type="hidden" v-model="orderForm.consigneeContactCode" />
                        <el-form-item label="联系人" required prop="consigneeContactName" class="xe-col-4">
                            <el-input v-model="orderForm.consigneeContactName"></el-input>
                        </el-form-item>
                        <el-form-item label="联系电话" required prop="consigneeContactPhone" class="xe-col-4">
                            <el-input v-model="orderForm.consigneeContactPhone"></el-input>
                        </el-form-item>
                    </div>
                    <div class="xe-block" style="overflow: visible">
                        <el-form-item label="地址选择" required prop="destination" class="xe-col-2">
                            <city-picker class = "cp cityPicker" :url="cityUrl"
                                         :default-data="cpConsigneeData" :options="cityPickerOptions"
                                         :success-callBack="consigneeCallBack" style="position:relative;z-index: 200;"></city-picker>
                        </el-form-item>
                        <el-form-item label="详细地址" prop="consigneeAddress" class="xe-col-2">
                            <el-input v-model="orderForm.consigneeAddress"></el-input>
                        </el-form-item>
                    </div>
                </div>
                <div style="clear: both"></div>
            </div>
        </el-form>
        <el-form :model="feeForm" :rules="rules" ref="feeForm" label-width="100px" class="demo-ruleForm" style = "position: relative; z-index: 10" >
            <!-- 服务项目及费用 -->
            <div class="xe-pageHeader">
                服务项目及费用
            </div>
            <div class="xe-block">
                <el-checkbox v-model="feeForm.isPickUpGoods" class="xe-checkbox"></el-checkbox>
                <el-form-item label="上门提货:费用" prop="homeDeliveryFee" class="xe-col-4">
                    <el-input v-model="feeForm.homeDeliveryFee" v-bind:disabled="!feeForm.isPickUpGoods" class="xe-col-8"></el-input>
                    <label for="">元</label>
                </el-form-item>
            </div>
            <div class="xe-block">
                <el-checkbox v-model="feeForm.isInsure" class="xe-checkbox"></el-checkbox>
                <el-form-item label="货物保险:费用" prop="cargoInsuranceFee" class="xe-col-4">
                    <el-input v-model="feeForm.cargoInsuranceFee" v-bind:disabled="!feeForm.isInsure" class="xe-col-8"></el-input>
                    <label for="">元</label>
                </el-form-item>
                <el-form-item label="声明价值" prop="insureValue" class="xe-col-4">
                    <el-input v-model="feeForm.insureValue" v-bind:disabled="!feeForm.isInsure" class="xe-col-8"></el-input>
                    <label for="">元</label>
                </el-form-item>
            </div>
            <div class="xe-block">
                <el-checkbox v-model="feeForm.isTwoDistribution" class="xe-checkbox"></el-checkbox>
                <el-form-item label="二次配送:费用" prop="twoDistributionFee" class="xe-col-4">
                    <el-input v-model="feeForm.twoDistributionFee" v-bind:disabled="!feeForm.isTwoDistribution" class="xe-col-8"></el-input>
                    <label for="">元</label>
                </el-form-item>
            </div>
            <div class="xe-block">
                <el-checkbox v-model="feeForm.isCollectFlag" class="xe-checkbox"></el-checkbox>
                <el-form-item label="代收货款:费用" prop="collectServiceCharge" class="xe-col-4">
                    <el-input v-model="feeForm.collectServiceCharge" v-bind:disabled="!feeForm.isCollectFlag" class="xe-col-8"></el-input>
                    <label for="">元</label>
                </el-form-item>
                <el-form-item label="代收金额" prop="collectLoanAmount" class="xe-col-4">
                    <el-input v-model="feeForm.collectLoanAmount" v-bind:disabled="!feeForm.isCollectFlag" class="xe-col-8"></el-input>
                    <label for="">元</label>
                </el-form-item>
            </div>
            <div class="xe-block">
                <el-checkbox v-model="feeForm.isReturnList" class="xe-checkbox"></el-checkbox>
                <el-form-item label="签单返回:费用" prop="returnListFee" class="xe-col-4">
                    <el-input v-model="feeForm.returnListFee" v-bind:disabled="!feeForm.isReturnList" class="xe-col-8"></el-input>
                    <label for="">元</label>
                </el-form-item>
                <el-form-item label="运费" prop="luggage" class="xe-col-4">
                    <el-input v-model="feeForm.luggage" class="xe-col-8"></el-input>
                    <label for="">元</label>
                </el-form-item>
            </div>
            <div class="xe-block">
                <el-form-item label="费用总计" porp="serviceCharge" class="xe-col-4" style="margin-left: 18px;">
                    <el-input v-model="feeForm.serviceCharge" disabled class="xe-col-8"></el-input>
                    <label for="">元</label>
                </el-form-item>
                <el-form-item label="费用支付" class="xe-col-4">
                    <el-radio class="radio" v-model="feeForm.expensePaymentParty" label="1">发货方</el-radio>
                    <el-radio class="radio" v-model="feeForm.expensePaymentParty" label="2">收货方</el-radio>
                </el-form-item>
                <el-form-item label="支付方式" class="xe-col-4">
                    <el-select v-model="feeForm.payment" class="xe-col-8">
                        <el-option
                                v-for="item in paymentOptions"
                                :label="item.label"
                                :value="item.value">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="是否开发票" class="xe-col-4">
                    <el-checkbox v-model="feeForm.openInvoices"></el-checkbox>
                </el-form-item>
            </div>
            <div class="xe-block">
                <label style="float:left;line-height:34px;">结算方式</label>
                <el-form-item label="现结" prop="currentAmount" class="xe-col-4" style="margin-left:-30px;">
                    <el-input v-model="feeForm.currentAmount" class="xe-col-8"></el-input>
                    <label for="">元</label>
                </el-form-item>
                <el-form-item label="到付" prop="toPayAmount" class="xe-col-4">
                    <el-input v-model="feeForm.toPayAmount" class="xe-col-8"></el-input>
                    <label for="">元</label>
                </el-form-item>
                <el-form-item label="回付" prop="returnAmount" class="xe-col-4">
                    <el-input v-model="feeForm.returnAmount" class="xe-col-8"></el-input>
                    <label for="">元</label>
                </el-form-item>
                <el-form-item label="月结" prop="monthlyAmount" class="xe-col-4">
                    <el-input v-model="feeForm.monthlyAmount" class="xe-col-8"></el-input>
                    <label for="">元</label>
                </el-form-item>
            </div>
            <!-- 货品信息 -->
            <div class="xe-pageHeader">
                货品信息
            </div>
            <div class="xe-block" style="float:right;margin-bottom:15px; margin-right: 30px;position: relative; z-index: 10">
                <label class="xe-label">数量合计:</label>
                <span>{{totalQuantity}}</span>
                <label class="xe-label" style="margin-left: 20px;">重量合计:</label>
                <span>{{totalWeight}}&nbsp;&nbsp;KG</span>
                <label class="xe-label" style="margin-left: 20px;">体积合计:</label>
                <span>{{totalCubage}}&nbsp;&nbsp;m³</span>
                <el-button type="primary" v-on:click="addGoods" style="margin-left: 20px;">添加一行</el-button>
            </div>
            <el-table :data="goodsData" border highlight-current-row @current-change="">
                <el-table-column type="index" label="序号" width="40"></el-table-column>
                <el-table-column property="goodsType" label="货品种类">
                    <template scope="scope">
                        <el-select v-model="scope.row.goodsType" @change="getGoodsCategory(scope.row)" placeholder="请选择">
                            <el-option
                                    v-for="item in goodsTypeOptions"
                                    :label="item.label"
                                    :value="item.value"
                                    style="width:100px;">
                            </el-option>
                        </el-select>
                    </template>
                </el-table-column>
                <el-table-column property="goodsCategory" label="货品小类">
                    <template scope="scope">
                        <el-select v-model="scope.row.goodsCategory" placeholder="请选择">
                            <el-option
                                    v-for="item in goodsCategoryOptions"
                                    :label="item.label"
                                    :value="item.value"
                                    style="width:100px;">
                            </el-option>
                        </el-select>
                    </template>
                </el-table-column>
                <el-table-column property="goodsCode" label="货品编码">
                    <template scope="scope">
                        <el-input
                                icon="search"
                                v-model="scope.row.goodsCode"
                                v-bind:disabled = "isDisabled"
                                @click="showDialog('goods', scope.row)">
                        </el-input>
                    </template>
                </el-table-column>
                <el-table-column property="goodsName" label="货品名称">
                    <template scope="scope">
                        <el-input v-model="scope.row.goodsName"></el-input>
                    </template>
                </el-table-column>
                <el-table-column property="goodsSpec" label="规格">
                    <template scope="scope">
                        <el-input v-model="scope.row.goodsSpec"></el-input>
                    </template>
                </el-table-column>
                <el-table-column property="unit" label="单位">
                    <template scope="scope">
                        <el-input v-model="scope.row.unit"></el-input>
                    </template>
                </el-table-column>
                <el-table-column property="pack" label="包装">
                    <template scope="scope">
                        <el-select v-model="scope.row.pack">
                            <el-option
                                    v-for="item in goodsPackOptions"
                                    :label="item.label"
                                    :value="item.value">
                            </el-option>
                        </el-select>
                    </template>
                </el-table-column>
                <el-table-column property="chargingWays" label="计费方式">
                    <template scope="scope">
                        <el-select v-model="scope.row.chargingWays">
                            <el-option
                                    v-for="item in chargingWayOptions"
                                    :label="item.label"
                                    :value="item.value">
                            </el-option>
                        </el-select>
                    </template>
                </el-table-column>
                <el-table-column property="chargingUnitPrice" label="计费单价">
                    <template scope="scope">
                        <el-input v-model="scope.row.chargingUnitPrice"></el-input>
                    </template>
                </el-table-column>
                <el-table-column property="quantity" label="数量">
                    <template scope="scope">
                        <el-input v-model="scope.row.quantity" @blur="accountQuantity(scope.row)"></el-input>
                    </template>
                </el-table-column>
                <el-table-column property="weight" label="重量（KG）">
                    <template scope="scope">
                        <el-input v-model="scope.row.weight" @blur="accountWeight(scope.row)"></el-input>
                    </template>
                </el-table-column>
                <el-table-column property="cubage" label="体积（m³）">
                    <template scope="scope">
                        <el-input v-model="scope.row.cubage" @blur="accountCubage(scope.row)"></el-input>
                    </template>
                </el-table-column>
                <el-table-column property="goodsOperation" label="操作" width="45">
                    <template scope="scope">
                        <el-button
                                size="small"
                                type="text"
                                @click="deleteRow(scope.$index, goodsData);">删除
                        </el-button>
                    </template>
                </el-table-column>
            </el-table>
            <el-button type="primary" @click="confirmPlaceOrder">确认下单</el-button>
        </el-form>
    </div>
    </div>
</div>
</body>
<script>
    new Vue({
        el: '#app',
        data: function () {
            return {
                isDisabled: false,
                showCustDialog: false,
                showConsignorDialog: false,
                showConsigneeDialog: false,
                showGoodsDialog: false,
                closeOnClick: false,
                closeOnPressEsc: false,
                isDisabledDelete:false,
                showClose: true,
                businessTypeOptions:[
                    {value: '600', label: '城配'},
                    {value: '601', label: '干线'},
                    {value: '602', label: '卡班'}
                ],
                merchandiserOptions:[],
                goodsPackOptions: [
                    {value: '01', label: '纸箱'},
                    {value: '02', label: '木箱'},
                    {value: '03', label: '桶'},
                    {value: '04', label: '混包'},
                    {value: '05', label: '裸装'},
                    {value: '06', label: '编袋'},
                    {value: '07', label: '托盘'},
                    {value: '08', label: '木框架'},
                    {value: '09', label: '泡沫箱'},
                    {value: '10', label: '缠绕膜'},
                    {value: '11', label: '盘'},
                    {value: '12', label: '铁框'},
                    {value: '13', label: '布袋'}
                ],
                chargingWayOptions: [
                    {value: '01', label: '件数'},
                    {value: '02', label: '重量Kg'},
                    {value: '03', label: '体积m³'}
                ],
                paymentOptions: [
                    {value: '6810', label: '现金'},
                    {value: '6820', label: 'POS刷卡'},
                    {value: '6830', label: '微信'},
                    {value: '6840', label: '支付宝'},
                    {value: '6850', label: '银行支付'},
                    {value: '6860', label: '账户结算'}
                ],
                mobileOrderVo: {
                    mobileOrderCode: '${mobileOrderCode !}',
                    uploadDate: '',
                    dingdingAccountNo: '',
                    operator: '',
                    businessType: '',
                    tranCode: '',
                    urls: []
                },
                totalQuantity: 0,
                totalWeight: 0,
                totalCubage:0,
                goodsData: [],
                currentEditGoodsData:'',
                goodsTypeOptions: [],
                goodsCategoryOptions: [],
                orderForm: {
                    businessType: '',
                    merchandiser: '',
                    transportType: '10',
                    orderTime: '',
                    orderDate:'',
                    transCode: '',
                    custOrderCode: '',
                    custCode: '',
                    custName: '',
                    expectedArrivedTime: '',
                    notes: '',
                    consignorCode: '',
                    consignorName: '',
                    consignorContactCode: '',
                    consignorContactName: '',
                    consignorContactPhone: '',
                    departureProvince: '',
                    departureCity: '',
                    departureDistrict: '',
                    departureTowns: '',
                    departurePlaceCode: '',
                    departurePlace: '',
                    consignorAddress: '',
                    consigneeCode: '',
                    consigneeName: '',
                    consigneeContactCode: '',
                    consigneeContactName: '',
                    consigneeContactPhone: '',
                    destinationProvince: '',
                    destinationCity: '',
                    destinationDistrict: '',
                    destinationTowns: '',
                    destinationCode: '',
                    destination: '',
                    consigneeAddress: ''
                },
                feeForm: {
                    isPickUpGoods: false,
                    homeDeliveryFee: 0,
                    isInsure: false,
                    cargoInsuranceFee: 0,
                    insureValue: 0,
                    isTwoDistribution: false,
                    twoDistributionFee: 0,
                    isCollectFlag: false,
                    collectServiceCharge: 0,
                    collectLoanAmount: 0,
                    isReturnList: false,
                    returnListFee: 0,
                    luggage: 0,
                    serviceCharge: 0,
                    expensePaymentParty: 1,
                    payment: '6810',
                    openInvoices: false,
                    currentAmount: 0,
                    toPayAmount: 0,
                    returnAmount: 0,
                    monthlyAmount: 0
                },
                rules: {
                    businessType: [{required: true, message: '请选择业务类型', trigger: 'change'}],
                    merchandiser: [{required: true, message: '请输入开单员', trigger: 'blur'},
                        { min: 1, max: 50, message: '长度在 1 到 50 个字符', trigger: 'change' }],
                    transportType: [{required: true, message: '请选择运输类型', trigger: 'change'}],
                    orderDate: [{type: 'date', required: true, validator: this.validateOrdeTime, trigger: 'change'}],
                    transCode: [{min: 0, max: 20, validator: this.validateTransCode, trigger: 'blur'}],
                    custOrderCode:[{ min: 0, max: 30, message: '长度在 0 到 30 个字符', trigger: 'change' }],
                    custName: [{required: true, message: '请选择客户', trigger: 'change'}],
                    expectedArrivedTime: [{validator: this.validateArrivedTime, trigger: 'change'}],
                    notes: [{ min: 0, max: 200, message: '长度在 0 到 200 个字符', trigger: 'change' }],
                    consignorName: [{required: true, min: 1, max: 100, message: '发货方名称必填，长度在 1 到 100 个字符', trigger: 'change'}],
                    consignorContactName: [{required: true, min: 1, max: 50, message: '联系人长度在 1 到 50 个字符', trigger: 'change'}],
                    consignorContactPhone: [{required: true, min: 11, max: 14, validator: this.validatePhone, message: '请输入正确手机号', trigger: 'change'}],
                    consignorAddress: [{ min: 0, max: 200, message: '长度在 0 到 200 个字符', trigger: 'change'}],
                    consigneeName: [{required: true, min: 1, max: 100, message: '收货方名称必填，长度在 1 到 100 个字符', trigger: 'change'}],
                    consigneeContactName: [{required: true, min: 1, max: 50, message: '联系人长度在 1 到 50 个字符', trigger: 'change'}],
                    consigneeContactPhone: [{required: true, min: 11, max: 14, validator: this.validatePhone, message: '请输入正确手机号', trigger: 'change'}],
                    consigneeAddress: [{ min: 0, max: 200, message: '长度在 0 到 200 个字符', trigger: 'change'}],
                    homeDeliveryFee: [{min: 0, max: 999999.99, validator: this.validatePickGoods, message: '上门提货费用为0~999999.99', trigger: 'change'}],
                    cargoInsuranceFee: [{min: 0, max: 999999.99, validator: this.validateInsure, message: '货物保险费用为0~999999.99', trigger: 'blur'}],
                    insureValue: [{min: 0, max: 999999.99, validator: this.validateInsureValue, message: '声明价值费用为0~999999.99', trigger: 'blur'}],
                    twoDistributionFee: [{min: 0, max: 999999.99, validator: this.validateTwoDist, message: '二次配送费用为0~999999.99', trigger: 'blur'}],
                    collectServiceCharge: [{min: 0, max: 999999.99, validator: this.validateCollect, message: '代收货款费用为0~999999.99', trigger: 'blur'}],
                    collectLoanAmount: [{min: 0, max: 999999.99, validator: this.validateCollectLoan, message: '代收金额费用为0~999999.99', trigger: 'blur'}],
                    returnListFee: [{min: 0, max: 999999.99, validator: this.validateReturnList, message: '签单返回费用为0~999999.99', trigger: 'blur'}],
                    luggage: [{min: 0, max: 999999.99, validator: this.validateLaggage, message: '运费为0~999999.99', trigger: 'blur'}],
                    currentAmount: [{min: 0, max: 999999.99, validator: this.validateCurrentAmount, message: '现结金额为0~999999.99', trigger: 'blur'}],
                    toPayAmount: [{min: 0, max: 999999.99, validator: this.validateToPayAmount, message: '到付金额为0~999999.99', trigger: 'blur'}],
                    returnAmount: [{min: 0, max: 999999.99, validator: this.validateReturnAmount, message: '回付金额为0~999999.99', trigger: 'blur'}],
                    monthlyAmount: [{min: 0, max: 999999.99, validator: this.validateMonthlyAmount, message: '月结金额为0~999999.99', trigger: 'blur'}]
                },
                custDialog: {
                    loading: true,
                    currentPage: 1,
                    pageSize: 10,
                    pageSizes:[10, 20, 30, 40,50],
                    total: 0,
                    currentSelectedRow: '',
                    custForm: {
                        name: ''
                    },
                    custData: []
                },
                consignorDialog: {
                    currentPage: 1,
                    pageSize: 10,
                    pageSizes:[10, 20, 30, 40,50],
                    total: 0,
                    currentSelectedRow: '',
                    consignorForm: {
                        name: '',
                        contactName: '',
                        phone: ''
                    },
                    consignorData: []
                },
                consigneeDialog: {
                    currentPage: 1,
                    pageSize: 10,
                    pageSizes:[10, 20, 30, 40,50],
                    total: 0,
                    currentSelectedRow: '',
                    consigneeForm: {
                        name: '',
                        contactName: '',
                        phone: ''
                    },
                    consigneeData: []
                },
                goodsDialog: {
                    currentPage: 1,
                    pageSize: 10,
                    pageSizes:[10, 20, 30, 40,50],
                    total: 0,
                    currentSelectedRow: '',
                    goodsForm: {
                        goodsType: '',
                        goodsCategory: '',
                        goodsName: '',
                        goodsCode: '',
                        barCode: ''
                    },
                    goodsDialogData: []
                },
                cityUrl: sys.rmcPath +"/rmc/addr/citypicker/findByCodeAndType",
                cityPickerOptions: {
                    province: true,
                    city: true,
                    district: true,
                    street: true,
                },
                cpConsignorData: {
                    province: {
                        code: '',
                        keyword: "province",
                        title: '请选择省'
                    },
                    city: {
                        code: '',
                        keyword: "city",
                        title: '市'
                    },
                    district: {
                        code: '',
                        keyword: "district",
                        title: '区'
                    },
                    street: {
                        code: '',
                        keyword: "street",
                        title: '街道'
                    }
                },
                cpConsigneeData: {
                    province: {
                        code: '',
                        keyword: "province",
                        title: '请选择省'
                    },
                    city: {
                        code: '',
                        keyword: "city",
                        title: '市'
                    },
                    district: {
                        code: '',
                        keyword: "district",
                        title: '区'
                    },
                    street: {
                        code: '',
                        keyword: "street",
                        title: '街道'
                    }
                }
            };
        },
        mounted: function() {
            // 查询易录单信息
            this.initOrder(this.mobileOrderVo.mobileOrderCode);
        },
        methods: {
            initOrder: function (code) {
                var vm = this;
                CommonClient.post(sys.rootPath + "/ofc/autoAcceptMobileOrderDetail", {"orderCode": code}, function(result) {
                    var code = result.code;
                    if (code == 200) {
                        if (result.result != undefined) {
                            var data = result.result;
                            // 钉钉开单信息
                            vm.mobileOrderVo = data;
                            // 初始化基本信息
                            vm.orderForm.businessType = data.businessType;
                            vm.orderForm.merchandiser = data.operator;
                            vm.orderForm.orderDate = new Date(data.uploadDate);
                            vm.orderForm.transCode = data.tranCode;
                        }
                    } else if (code == 500) {
                        vm.$message(result.message);
                    }
                }, "json");
            },
            promptInfo:function(message,type){
                this.$message({
                    message: message,
                    type: type
                });
            },
            addGoods: function () {
                var custCode = this.orderForm.custCode;
                if (custCode != undefined && custCode != null && custCode != '') {
                    var newGoods = {
                        goodsType: '',
                        goodsTypeId: '',
                        goodsCategory: '',
                        goodsCategoryId: '',
                        goodsCode: '',
                        goodsName: '',
                        goodsSpec: '',
                        unit: '',
                        pack: '01',
                        chargingWays: '01',
                        chargingUnitPrice: '',
                        quantity: '',
                        weight: '',
                        cubage: ''
                    };
                    this.getGoodsType();
                    this.goodsData.push(newGoods);
                } else {
                    this.$message({showClose: true, message: '请选择客户！', type: 'error'});
                }
            },
            deleteRow: function(index, rows) {
                rows.splice(index, 1);
            },
            getGoodsType: function () {
                var vm = this;
                vm.goodsTypeOptions = [];
                CommonClient.syncpost(sys.rootPath + "/ofc/getCscGoodsTypeList",{"pid":null},function(result) {
                    var data=eval(result);
                    $.each(data,function (index,CscGoodsTypeVo) {
                        var goodsType = {};
                        goodsType.label=CscGoodsTypeVo.goodsTypeName;
                        goodsType.value=CscGoodsTypeVo.id;
                        vm.goodsTypeOptions.push(goodsType);
                    });
                });
            },
            getGoodsCategory: function (row) {
                var vm = this;
                vm.goodsCategoryOptions=[];
                var typeId = row.goodsType;
                CommonClient.syncpost(sys.rootPath + "/ofc/getCscGoodsTypeList",{"cscGoodsType":typeId},function(result) {
                    var data=eval(result);
                    $.each(data,function (index,CscGoodsTypeVo) {
                        var goodsCategory={};
                        goodsCategory.label=CscGoodsTypeVo.goodsTypeName;
                        goodsCategory.value=CscGoodsTypeVo.goodsTypeName;
                        vm.goodsCategoryOptions.push(goodsCategory);
                    });
                });
            },
            getGoodsCategoryWhenSelectGoods: function (typeId) {
                var vm = this;
                vm.goodsCategoryOptions=[];
                CommonClient.syncpost(sys.rootPath + "/ofc/getCscGoodsTypeList",{"cscGoodsType":typeId},function(result) {
                    var data=eval(result);
                    $.each(data,function (index,CscGoodsTypeVo) {
                        var goodsCategory={};
                        goodsCategory.label=CscGoodsTypeVo.goodsTypeName;
                        goodsCategory.value=CscGoodsTypeVo.goodsTypeName;
                        vm.goodsCategoryOptions.push(goodsCategory);
                    });
                });
            },
            showDialog: function (type, currentRow) {
                var custCode = this.orderForm.custCode;
                if (custCode != undefined && custCode != null && custCode != '') {
                    if (type == 'consignor') {
                        this.showConsignorDialog = true;
                    } else if (type == 'consignee') {
                        this.showConsigneeDialog = true;
                    } else if (type == 'goods') {
                        this.showGoodsDialog = true;
                        this.currentEditGoodsData = currentRow;
                    }
                } else {
                    this.$message({showClose: true, message: '请选择客户！', type: 'error'});
                }
            },
            checkCust: function () {
                var exist = false;
                var custCode = this.orderForm.custCode;
                if (custCode != undefined && custCode != null && custCode != '') {
                    exist = true;
                }
                return exist;
            },
            queryCustomer: function () {
                var param = {};
                param.pageNum = this.custDialog.currentPage;
                param.pageSize=this.custDialog.pageSize;
                param.custName = this.custDialog.custForm.name;
                this.custDialog.custData=[];
                var vm = this;
                vm.custDialog.custData = [];
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
                                } else if (custType == '2'){
                                    customer.type="个人";
                                } else{
                                    customer.type=custType;
                                }
                                customer.custName=cscCustomerVo.customerName;
                                customer.channel=channel;
                                customer.productType=cscCustomerVo.productType;
                                vm.custDialog.custData.push(customer);
                            });
                            vm.custDialog.total=result.result.total;
                        } else if (result.code == 403) {
                            vm.promptInfo("没有权限",'error');
                        }
                    },"json");
            },
            queryConsignor: function () {
                var custCode = this.orderForm.custCode;
                if (custCode != undefined && custCode != null && custCode != '') {
                    var param = {};
                    var cscContactCompanyDto = {};
                    cscContactCompanyDto.contactCompanyName = this.consignorDialog.consignorForm.name;
                    var cscContactDto = {};
                    cscContactDto.purpose = "2";
                    cscContactDto.contactName = this.consignorDialog.consignorForm.contactName;
                    cscContactDto.phone = this.consignorDialog.consignorForm.phone;
                    param.pageNum = this.consignorDialog.currentPage;
                    param.pageSize=this.consignorDialog.pageSize;
                    param.customerCode = custCode;
                    param.cscContactDto = cscContactDto;
                    param.cscContactCompanyDto = cscContactCompanyDto;
                    param = JSON.stringify(param);
                    this.custDialog.custData=[];
                    var vm = this;
                    vm.consignorDialog.consignorData = [];
                    CommonClient.post(sys.rootPath + "/ofc/contactSelectForPage", {'cscContantAndCompanyDto': param, 'customerCode': custCode}, function(result) {
                        if (result == undefined || result == null || result.result == null ||  result.result.size == 0 || result.result.list == null) {
                            layer.msg("暂时未查询到发货方信息！！");
                            vm.$message({message: '暂时未查询到发货方信息！'});
                        } else if (result.code == 200) {
                            $.each(result.result.list,function (index,company) {
                                var data = {};
                                data.contactCompanyName = company.contactCompanyName;
                                data.contactName = company.contactName;
                                data.phone = company.phone;
                                data.detailAddress = company.detailAddress;
                                data.contactCompanySerialNo = company.contactCompanySerialNo;
                                data.contactSerialNo = company.contactSerialNo;
                                data.type = company.type;
                                data.address = company.address;
                                data.provinceCode = company.province;
                                data.provinceName = company.provinceName;
                                data.cityCode = company.city;
                                data.cityName = company.cityName;
                                data.areaCode = company.area;
                                data.areaName = company.areaName;
                                data.streetCode = company.street;
                                data.streetName = company.streetName;
                                vm.consignorDialog.consignorData.push(data);
                            });
                            vm.consignorDialog.total=result.result.total;
                        } else if (result.code == 403) {
                            vm.promptInfo("没有权限",'error');
                        }
                    },"json");
                } else {
                    this.$message({showClose: true, message: '请选择客户！', type: 'error'});
                }
            },
            queryConsignee: function () {
                var custCode = this.orderForm.custCode;
                if (custCode != undefined && custCode != null && custCode != '') {
                    var param = {};
                    var cscContactCompanyDto = {};
                    cscContactCompanyDto.contactCompanyName = this.consigneeDialog.consigneeForm.name;
                    var cscContactDto = {};
                    cscContactDto.purpose = "1";
                    cscContactDto.contactName = this.consigneeDialog.consigneeForm.contactName;
                    cscContactDto.phone = this.consigneeDialog.consigneeForm.phone;
                    param.pageNum = this.consigneeDialog.currentPage;
                    param.pageSize = this.consigneeDialog.pageSize;
                    param.customerCode = custCode;
                    param.cscContactDto = cscContactDto;
                    param.cscContactCompanyDto = cscContactCompanyDto;
                    param = JSON.stringify(param);
                    this.custDialog.custData=[];
                    var vm = this;
                    vm.consigneeDialog.consigneeData = [];
                    CommonClient.post(sys.rootPath + "/ofc/contactSelectForPage", {'cscContantAndCompanyDto': param, 'customerCode': custCode}, function(result) {
                        if (result == undefined || result == null || result.result == null ||  result.result.size == 0 || result.result.list == null) {
                            layer.msg("暂时未查询到收货方信息！！");
                            vm.$message({message: '暂时未查询到收货方信息！'});
                        } else if (result.code == 200) {
                            $.each(result.result.list,function (index,company) {
                                var data = {};
                                data.contactCompanyName = company.contactCompanyName;
                                data.contactName = company.contactName;
                                data.phone = company.phone;
                                data.detailAddress = company.detailAddress;
                                data.contactCompanySerialNo = company.contactCompanySerialNo;
                                data.contactSerialNo = company.contactSerialNo;
                                data.type = company.type;
                                data.address = company.address;
                                data.provinceCode = company.province;
                                data.provinceName = company.provinceName;
                                data.cityCode = company.city;
                                data.cityName = company.cityName;
                                data.areaCode = company.area;
                                data.areaName = company.areaName;
                                data.streetCode = company.street;
                                data.streetName = company.streetName;
                                vm.consigneeDialog.consigneeData.push(data);
                            });
                            vm.consigneeDialog.total=result.result.total;
                        } else if (result.code == 403) {
                            vm.promptInfo("没有权限",'error');
                        }
                    },"json");
                } else {
                    this.$message({showClose: true, message: '请选择客户！', type: 'error'});
                }
            },
            queryGoods: function () {
                if (this.checkCust()) {
                    this.goodsDialog.goodsForm.goodsData = [];
                    var cscGoods = {};
                    var customerCode = this.orderForm.custCode;
                    cscGoods.goodsName = this.goodsDialog.goodsForm.goodsName;
                    cscGoods.goodsTypeId = this.goodsDialog.goodsForm.goodsType;
                    cscGoods.goodsTypeSonId = this.goodsDialog.goodsForm.goodsCategory;
                    cscGoods.barCode = this.goodsDialog.goodsForm.barCode;
                    cscGoods.goodsCode = this.goodsDialog.goodsForm.goodsCode;
                    cscGoods.pNum = this.goodsDialog.currentPage;
                    cscGoods.pSize = this.goodsDialog.pageSize;
                    var param = JSON.stringify(cscGoods);
                    var vm = this;
                    vm.goodsDialog.goodsDialogData = [];
                    CommonClient.post(sys.rootPath + "/ofc/goodsSelects", {"cscGoods":param,"customerCode":customerCode}, function(data) {
                        if (data == undefined || data == null || data.result ==null || data.result.size == 0 || data.result.list == null) {
                            layer.msg("暂时未查询到货品信息！！");
                        } else if (data.code == 200) {
                            $.each(data.result.list,function (index,cscGoodsVo) {
                                var goodCode={};
                                goodCode.goodsType = cscGoodsVo.goodsTypeParentName;
                                goodCode.goodsTypeId = cscGoodsVo.goodsTypeId;
                                goodCode.goodsCategory = cscGoodsVo.goodsTypeName;
                                goodCode.goodsCategoryId = cscGoodsVo.goodsType;
                                goodCode.goodsBrand = cscGoodsVo.brand;
                                goodCode.goodsCode = cscGoodsVo.goodsCode;
                                goodCode.goodsName = cscGoodsVo.goodsName;
                                goodCode.goodsSpec = cscGoodsVo.specification;
                                goodCode.unit = cscGoodsVo.unit;
                                goodCode.barCode = cscGoodsVo.barCode;
                                vm.goodsDialog.goodsDialogData.push(goodCode);
                            });
                            vm.goodsDialog.total = data.result.total;
                        } else if (data.code == 403) {
                            vm.promptInfo("没有权限",'error');
                        }
                    },"json");
                } else {
                    this.$message({showClose: true, message: '请选择客户！', type: 'error'});
                }
            },
            handleCustCurrentChange: function (currentRow, oldCurrentRow) {
                this.custDialog.currentSelectedRow = currentRow;
            },
            handleCustSizeChange: function (val) {
                this.custDialog.pageSize=val;
                this.queryCustomer();
            },
            handleCustCurrentPage: function (val) {
                this.custDialog.currentPage = val;
                this.queryCustomer();
            },
            cancelSelectCustomer: function () {
                this.custDialog.currentPage = 1;
                this.custDialog.pageSize = 10;
                this.custDialog.total = 0;
                this.custDialog.custForm.name = '';
                this.custDialog.custData = [];

                this.showCustDialog = false;
                this.custDialog.currentSelectedRow = '';
            },
            setSelectedCustInfo: function (row) {
                if (row != null) {
                    this.orderForm.custCode = row.custCode;
                    this.orderForm.custName = row.custName;

                    this.cancelSelectCustomer();
                }
            },
            handleConsignorCurrentChange: function (currentRow, oldCurrentRow) {
                this.consignorDialog.currentSelectedRow = currentRow;
            },
            handleConsigneeCurrentChange: function(currentRow, oldCurrentRow) {
                this.consigneeDialog.currentSelectedRow = currentRow;
            },
            handleGoodsCurrentChange: function(currentRow, oldCurrentRow) {
                this.goodsDialog.currentSelectedRow = currentRow;
            },
            handleConsignorSizeChange: function (val) {
                this.consignorDialog.pageSize = val;
                this.queryConsignor();
            },
            handleConsigneeSizeChange: function (val) {
                this.consigneeDialog.pageSize = val;
                this.queryConsignee();
            },
            handleGoodsSizeChange: function (val) {
                this.goodsDialog.pageSize = val;
                this.queryGoods();
            },
            handleConsignorCurrentPage: function (val) {
                this.consignorDialog.currentPage = val;
                this.queryConsignor();
            },
            handleConsigneeCurrentPage: function (val) {
                this.consigneeDialog.currentPage = val;
                this.queryConsignee();
            },
            handleGoodsCurrentPage: function (val) {
                this.goodsDialog.currentPage = val;
                this.queryGoods();
            },
            cancelSelectContactDialog: function (type) {
                if (type == 'consignor') {
                    this.consignorDialog.currentPage = 1;
                    this.consignorDialog.pageSize = 10;
                    this.consignorDialog.total = 0;
                    // 清空窗口搜索项
                    this.consignorDialog.consignorForm.name = '';
                    this.consignorDialog.consignorForm.contactName= '';
                    this.consignorDialog.consignorForm.phone = '';
                    this.consignorDialog.consignorData = [];
                    // 关闭窗口
                    this.showConsignorDialog = false;
                    // 清空选中行
                    this.consignorDialog.currentSelectedRow = '';
                } else if (type == 'consignee') {
                    this.consigneeDialog.currentPage = 1;
                    this.consigneeDialog.pageSize = 10;
                    this.consigneeDialog.total = 0;
                    this.consigneeDialog.consigneeForm.name = '';
                    this.consigneeDialog.consigneeForm.contactName = '';
                    this.consigneeDialog.consigneeForm.phone = '';
                    this.consigneeDialog.consigneeData = [];

                    this.showConsigneeDialog = false;
                    this.consigneeDialog.currentSelectedRow = '';
                }

            },
            setSelectedContactInfo: function (row, type) {
                if (row != undefined && row != null && row != '') {
                    var data = {
                        province: {
                            code: row.provinceCode,
                            keyword: "province",
                            title: row.provinceName
                        },
                        city: {
                            code: row.cityCode,
                            keyword: "city",
                            title: row.cityName
                        },
                        district: {
                            code: row.areaCode,
                            keyword: "district",
                            title: row.areaName
                        },
                        street: {
                            code: row.streetCode,
                            keyword: "street",
                            title: row.streetName
                        }
                    };
                    if (type == 'consignor') {
                        this.orderForm.consignorName = this.handelStrNull(row.contactCompanyName);
                        this.orderForm.consignorContactName = this.handelStrNull(row.contactName);
                        this.orderForm.consignorCode = this.handelStrNull(row.contactCompanySerialNo);
                        this.orderForm.consignorContactCode = this.handelStrNull(row.contactSerialNo);
                        this.orderForm.consignorContactPhone = this.handelStrNull(row.phone);
                        this.orderForm.consignorAddress = this.handelStrNull(row.address);
                        this.orderForm.departureProvince = this.handelStrNull(row.provinceName);
                        this.orderForm.departureCity = this.handelStrNull(row.cityName);
                        this.orderForm.departureDistrict = this.handelStrNull(row.areaName);
                        this.orderForm.departureTowns = this.handelStrNull(row.streetName);
                        this.orderForm.departurePlace = this.handelStrNull(row.provinceName) +"/"+ this.handelStrNull(row.cityName) +"/"+
                                this.handelStrNull(row.areaName) +"/"+ this.handelStrNull(row.streetName);
                        this.orderForm.departurePlaceCode = this.handelStrNull(row.provinceCode) + "," + this.handelStrNull(row.cityCode) + "," +
                                this.handelStrNull(row.areaCode) + "," + this.handelStrNull(row.streetCode);

                        this.cpConsignorData = data;

                    } else if (type == 'consignee') {
                        this.orderForm.consigneeName = this.handelStrNull(row.contactCompanyName);
                        this.orderForm.consigneeContactName = this.handelStrNull(row.contactName);
                        this.orderForm.consigneeCode = this.handelStrNull(row.contactCompanySerialNo);
                        this.orderForm.consigneeContactCode = this.handelStrNull(row.contactSerialNo);
                        this.orderForm.consigneeContactPhone = this.handelStrNull(row.phone);
                        this.orderForm.consigneeAddress = this.handelStrNull(row.address);
                        this.orderForm.destinationProvince = this.handelStrNull(row.provinceName);
                        this.orderForm.destinationCity = this.handelStrNull(row.cityName);
                        this.orderForm.destinationDistrict = this.handelStrNull(row.areaName);
                        this.orderForm.destinationTowns = this.handelStrNull(row.streetName);
                        this.orderForm.destination = this.handelStrNull(row.provinceName) +"/"+ this.handelStrNull(row.cityName) +"/"+
                                this.handelStrNull(row.areaName) +"/"+ this.handelStrNull(row.streetName);
                        this.orderForm.destinationCode = this.handelStrNull(row.provinceCode) + "," + this.handelStrNull(row.cityCode) + "," +
                                this.handelStrNull(row.areaCode) + "," + this.handelStrNull(row.streetCode);
                        this.cpConsigneeData = data;
                    }
                    this.cancelSelectContactDialog(type);
                } else {
                    this.$message({showClose: true, message: '请选择发货方！', type: 'error'});
                }
            },
            checkGoodsDataDupl: function (newGoodsCode) {
                var result = true;
                var vm = this;
                $.each(this.goodsData, function (index, goods) {

                    var goodsCode = goods.goodsCode;
                    if (goodsCode == newGoodsCode) {
                        result = false;
                        return false;
                    }
                });
                return result;
            },
            setSelectGoodsInfo: function(row) {
                var result = this.checkGoodsDataDupl(row.goodsCode);
                if (result) {
                    this.getGoodsCategoryWhenSelectGoods(row.goodsTypeId);
                    this.currentEditGoodsData.goodsType = row.goodsType;
                    this.currentEditGoodsData.goodsTypeId = row.goodsTypeId;
                    this.currentEditGoodsData.goodsCategory = row.goodsCategory;
                    this.currentEditGoodsData.goodsCategoryId = row.goodsCategoryId;
                    this.currentEditGoodsData.goodsCode = row.goodsCode;
                    this.currentEditGoodsData.goodsName = row.goodsName;
                    this.currentEditGoodsData.goodsSpec = row.goodsSpec;
                    this.currentEditGoodsData.unit = row.unit;
                    this.currentEditGoodsData.pack = this.handelStrNull(row.pack) == '' ? '01' : this.handelStrNull(row.pack);
                    this.cancelSelectGoodsDialog();
                } else {
                    this.promptInfo('已经存在相同商品', 'error');
                }
            },
            consignorCallBack: function(address){
                console.log(JSON.stringify(address))
                var code = '', addr = '';
                var vm = this;
                $.each(address, function(index, area) {
                    if(!StringUtil.isEmpty(area.code)) {
                        if (index == address.length - 1) {
                            code += area.code;
                        } else {
                            code += area.code + ',';
                        }
                        addr += area.title;
                        if (area.keyword == 'province') {
                            vm.orderForm.departureProvince = area.title;
                        }
                        if (area.keyword == 'city') {
                            vm.orderForm.departureCity = area.title;
                        }
                        if (area.keyword == 'district') {
                            vm.orderForm.departureDistrict = area.title;
                        }
                        if (area.keyword == 'street') {
                            vm.orderForm.departureTowns = area.title;
                        }
                     }
                    });
                    this.orderForm.departurePlaceCode = code;
                    this.orderForm.departurePlace = addr;
               },
            consigneeCallBack: function(address){
                var code = '', addr = '';
                var vm = this;
                $.each(address, function(index, area) {
                    if(!StringUtil.isEmpty(area.code)){
                        if (index == address.length - 1) {
                            code += area.code;
                        } else {
                            code += area.code + ',';
                        }
                        addr += area.title;
                        if (area.keyword == 'province') {
                            vm.orderForm.destinationProvince = area.title;
                        }
                        if (area.keyword == 'city') {
                            vm.orderForm.destinationCity = area.title;
                        }
                        if (area.keyword == 'district') {
                            vm.orderForm.destinationDistrict = area.title;
                        }
                        if (area.keyword == 'street') {
                            vm.orderForm.destinationTowns = area.title;
                        }
                    }
                });
                this.orderForm.destinationCode = code;
                this.orderForm.destination = addr;
            },
            handelStrNull: function (str) {
                if (str == undefined || str == null || str == 'null') {
                    str = '';
                }
                return str;
            },
            openGoodsList: function () {
                this.showGoodsDialog = true;
            },
            resetGoodsSearchForm: function () {
                this.goodsDialog.goodsForm.goodsName = '';
                this.goodsDialog.goodsForm.goodsType = '';
                this.goodsDialog.goodsForm.goodsCode = '';
                this.goodsDialog.goodsForm.barCode = '';
                this.goodsDialog.goodsForm.goodsCategory = '';
            },
            cancelSelectGoodsDialog: function () {
                this.resetGoodsSearchForm();
                this.goodsDialog.currentSelectedRow = '';
                this.goodsDialog.goodsDialogData = [];
                this.showGoodsDialog = false;
            },
            closeDialog: function() {
                this.cancelSelectCustomer();
                this.cancelSelectContactDialog('consignor');
                this.cancelSelectContactDialog('consignee');
                this.cancelSelectGoodsDialog();
            },
            // 验证表单
            validateForm: function(formName) {
                var result;
                this.$refs[formName].validate(function(valid){
                    if (valid) {
                        console.log(formName + ' validate successed!!');
                        result = true;
                    } else {
                        console.log(formName + ' error submit!!');
                        result = false;
                    }
                });
                return result;
            },
            validateOrdeTime: function(rule, value, callback){
                if (value == undefined || value == '') {
                    callback(new Error('请选择订单日期!'));
                } else {
                    if(value.getTime()<new Date().getTime() - 3600 * 1000 * 24 * 7){
                        callback(new Error('只能选择一周之内的日期!'));
                    }else if(value.getTime()>new Date().getTime()){
                        callback(new Error('不能选择当前日期的往后日期!'));
                    }else{
                        callback();
                    }
                }
            },
            validateArrivedTime: function (rule, value, callback) {
                if (value != undefined && value != '') {
                    if (value.getTime() < this.orderForm.orderDate.getTime()) {
                        callback(new Error("承诺到达时间不能大于订单日期！"));
                    } else {
                        callback();
                    }
                } else {
                    callback();
                }
            },
            validatePickGoods: function (rule, value, callback) {
                var pickGoods = this.feeForm.isPickUpGoods;   // 上门提货
                var homeDeliveryFee = this.feeForm.homeDeliveryFee;// 上门提货费
                if (pickGoods) {
                    this.validateCustomField(homeDeliveryFee, '上门提货费用为0~999999.99', callback);
                } else {
                    this.feeForm.homeDeliveryFee = 0;
                    callback();
                }
            },
            validateInsure: function (rule, value, callback) {
                var insure = this.feeForm.isInsure;// 货物保险
                var cargoInsuranceFee = this.feeForm.cargoInsuranceFee;
                if (insure) {
                    this.validateCustomField(cargoInsuranceFee, '货物保险费用为0~999999.99', callback);
                } else {
                    this.feeForm.cargoInsuranceFee = 0;
                    callback();
                }
            },
            validateInsureValue: function (rule, value, callback) {
                var insure = this.feeForm.isInsure;// 货物保险
                var insureValue = this.feeForm.insureValue;// 声明价值
                if (insure) {
                    this.validateCustomField(insureValue, '声明价值费用为0~999999.99', callback);
                } else {
                    this.feeForm.insureValue = 0;
                    callback();
                }
            },
            validateTwoDist: function (rule, value, callback) {
                var twoDistribution = this.feeForm.isTwoDistribution;// 二次配送
                var twoDistributionFee = this.feeForm.twoDistributionFee;// 二次配送费
                if (twoDistribution) {
                    this.validateCustomField(twoDistributionFee, '二次配送费用为0~999999.99', callback);
                } else {
                    this.feeForm.twoDistributionFee = 0;
                    callback();
                }
            },
            validateCollect: function (rule, value, callback) {
                var collectFlag = this.feeForm.isCollectFlag;// 代收货款
                var collectServiceCharge = this.feeForm.collectServiceCharge;// 代收货款费
                if (collectFlag) {
                    this.validateCustomField(collectServiceCharge, '代收货款费用为0~999999.99', callback);
                } else {
                    this.feeForm.collectServiceCharge = 0;
                    callback();
                }
            },
            validateCollectLoan: function (rule, value, callback) {
                var collectFlag = this.feeForm.isCollectFlag;// 代收货款
                var collectLoanAmount = this.feeForm.collectLoanAmount;// 代收金额
                if (collectFlag) {
                    this.validateCustomField(collectLoanAmount, '代收金额费用为0~999999.99', callback);
                } else {
                    this.feeForm.collectLoanAmount = 0;
                    callback();
                }
            },
            validateReturnList: function (rule, value, callback) {
                var returnList = this.feeForm.isReturnList;// 签收返回
                var returnListFee = this.feeForm.returnListFee;// 签收返回费
                if (returnList) {
                    this.validateCustomField(returnListFee, '签单返回费用为0~999999.99', callback);
                } else {
                    this.feeForm.returnListFee = 0;
                    callback();
                }
            },
            validateLaggage: function (rule, value, callback) {
                var luggage = this.feeForm.luggage;// 运费
                if (luggage != '' && luggage != 0) {
                    this.validateCustomField(luggage, '运费为0~999999.99', callback);
                } else {
                    callback();
                }
            },
            validateCurrentAmount: function (rule, value, callback) {
                var currentAmount = this.feeForm.currentAmount;// 现结
                if (currentAmount != '' && currentAmount != 0) {
                    this.validateCustomField(currentAmount, '现结金额为0~999999.99', callback);
                } else {
                    callback();
                }
            },
            validateToPayAmount: function (rule, value, callback) {
                var toPayAmount = this.feeForm.toPayAmount;// 到付
                if (toPayAmount != '' && toPayAmount != 0) {
                    this.validateCustomField(toPayAmount, '到付金额为0~999999.99', callback);
                } else {
                    callback();
                }
            },
            validateReturnAmount: function (rule, value, callback) {
                var returnAmount = this.feeForm.returnAmount;// 回付
                if (returnAmount != '' && returnAmount != 0) {
                    this.validateCustomField(returnAmount, '回付金额为0~999999.99', callback);
                } else {
                    callback();
                }
            },
            validateMonthlyAmount: function (rule, value, callback) {
                var monthlyAmount = this.feeForm.monthlyAmount;// 月结
                if (monthlyAmount != '' && monthlyAmount != 0) {
                    this.validateCustomField(monthlyAmount, '月结金额为0~999999.99', callback);
                } else {
                    callback();
                }
            },
            validateCustomField: function (field, msg, callback) {
                var expr = /^([1-9][\d]{0,5}|0)(\.[\d]{1,2})?$/;
                if (field != '' && !expr.test(field)) {
                    callback(new Error(msg))
                } else {
                    callback();
                }
            },
            validateTransCode: function(rule, value, callback) {
                var transCode =  this.orderForm.transCode;
                var businessType = this.orderForm.businessType;
                if (businessType != '' && businessType == '602') {
                    if (transCode == undefined || transCode == '') {
                        callback(new Error('业务类型为【卡班】需填运输单号！'));
                    } else {
                        var expr = /(^[A-Za-z0-9]+$)/;
                        if (!expr.test(transCode)) {
                            callback(new Error('运输单号只能输入20位数字和字母'));
                        } else {
                            // 验证运单号是否存在
                            CommonClient.syncpost(sys.rootPath + "/ofc/checkTransCode", {"transCode": transCode}, function(result) {
                                if (result) {
                                    callback();
                                } else {
                                    callback(new Error('该运单号已存在！'));
                                }
                            });
                        }
                    }
                } else {
                    callback();
                }
            },
            onlyNumber: function (value) {
                //先把非数字的都替换掉，除了数字和.
                value = value.replace(/[^\d\.]/g,'');
                //obj.value = obj.value.replace(/[^\d{1,6}]/,'');
                //必须保证第一个为数字而不是.
                value = value.replace(/^\./g,'');
                //保证只有出现一个.而没有多个.
                value = value.replace(/\.{2,}/g,'.');
                //保证.只出现一次，而不能出现两次以上
                value = value.replace('.','$#$').replace(/\./g,'').replace('$#$','.');
                return value;
            },
            validateGoodsRow: function (i, row) {
                var line = i + 1;
                var gnLen = row.goodsName.length;
                if (gnLen <= 0 || gnLen > 50) {
                    this.$notify.error({title: '第'+ line +'行错误', message: '货品名称必输,长度必须小于50', duration: 3000, offset: 500});
                    return false;
                }
                var goodsCode = this.handelStrNull(row.goodsCode);
                if (goodsCode != '' && goodsCode.length > 50 ) {
                    var expr = /(^[A-Za-z0-9]+$)/;
                    if (!expr.test(goodsCode)) {
                        this.$notify.error({title: '第'+ line +'行错误', message: '货品编码长度必须小于50', duration: 3000, offset: 500});
                        return false;
                    }
                }
                var goodsSpec = this.handelStrNull(row.goodsSpec);
                if (goodsSpec != '' && goodsSpec.length > 50) {
                    this.$notify.error({title: '第'+ line +'行错误', message: '货品规格长度必须小于50', duration: 3000, offset: 500});
                    return false;
                }
                var unit = this.handelStrNull(row.unit);
                if (unit != '' && unit.length > 10) {
                    this.$notify.error({title: '第'+ line +'行错误', message: '货品单位长度必须小于10', duration: 3000, offset: 500});
                    return false;
                }
                // 计费单价
                var chargingUnitPrice = this.handelStrNull(row.chargingUnitPrice);
                row.chargingUnitPrice = chargingUnitPrice;
                if (chargingUnitPrice != '' && !(/^([1-9][\d]{0,6}|0)(\.[\d]{1,4})?$/.test(chargingUnitPrice))) {
                    this.$notify.error({title: '第'+ line +'行错误', message: '计费单价只允许输入金额', duration: 3000, offset: 500});
                    return false;
                } else if (chargingUnitPrice > 9999) {
                    this.$notify.error({title: '第'+ line +'行错误', message: '计费单价最大值为9999.00', duration: 3000, offset: 500});
                    return false;
                }
                // 计费方式
                var chargingWays = row.chargingWays;
                if (chargingWays == '01') {
                    var quantity = this.onlyNumber(row.quantity);
                    row.quantity = quantity;
                    if (quantity == '' || quantity > 50000) {
                        this.$notify.error({title: '第'+ line +'行错误', message: '件数计费：数量必填,最大值为50000.00', duration: 3000, offset: 500});
                        return false;
                    }
                } else if (chargingWays == '02') {
                    var weight = this.onlyNumber(row.weight);
                    row.weight = weight;
                    if (weight == '' || weight > 50000) {
                        this.$notify.error({title: '第'+ line +'行错误', message: '重量计费：重量必填,最大值为50000.00', duration: 3000, offset: 500});
                        return false;
                    }
                } else if (chargingWays == '03') {
                    var cubage = this.onlyNumber(row.cubage);
                    row.cubage = cubage;
                    if (cubage == '' || cubage > 50000) {
                        this.$notify.error({title: '第'+ line +'行错误', message: '体积计费：体积必填,最大值为50000.00', duration: 3000, offset: 500});
                        return false;
                    }
                }
                var weight = this.onlyNumber(row.weight);
                row.weight = weight;
                if (weight == '' || weight > 50000) {
                    this.$notify.error({title: '第'+ line +'行错误', message: '重量必填，最大值为50000.00', duration: 3000, offset: 500});
                    return false;
                }
                return true;
            },
            validateGoodsInfo: function () {
                var goodsData = this.goodsData;
                var vm = this;
                if (goodsData == null || goodsData == undefined || goodsData.length == 0) {
                    vm.promptInfo("请添加至少一条货品!",'error');
                    return;
                } else {
                    for (var i = 0; i < goodsData.length; i++) {
                        var row = goodsData[i];
                        var flag = this.validateGoodsRow(i, row);
                        if (!flag) {
                            return false;
                        }
                    }
                }
            },
            validatePhone: function (rule, value, callback) {
                if (value != '') {
                    var expr = /^((\+86)|(86))?(13|15|17|18)\d{9}$/;
                    if (!expr.test(value)) {
                        callback(new Error('请输入正确的手机号码'));
                    } else {
                        callback();
                    }
                } else {
                    callback(new Error('请输入正确手机号'));
                }
            },
            confirmPlaceOrder: function() {
                // 验证订单基础信息
                var odrest = this.validateForm('orderForm');
                // 使用表单验证费用，存在bug
                var ferest = this.validateForm('feeForm');
                console.log(odrest + "    " + ferest);
                var vm = this;
                if (odrest && ferest) {
                    // 验证商品明细
                    var goodsData = this.goodsData;
                    if (goodsData == null || goodsData == undefined || goodsData.length == 0) {
                        vm.promptInfo("请添加至少一条货品!",'error');
                        return;
                    } else {
                        for (var i = 0; i < goodsData.length; i++) {
                            var row = goodsData[i];
                            var flag = this.validateGoodsRow(i, row);
                            if (!flag) {
                                return;
                            }
                        }
                    }
                    jsonStr =  Object.assign(vm.orderForm,vm.feeForm);
                    jsonStr.orderTime=DateUtil.format(this.orderForm.orderDate, "yyyy-MM-dd HH:mm:ss");
                    if(!StringUtil.isEmpty(this.orderForm.expectedArrivedTime)){
                        jsonStr.expectedArrivedTime=DateUtil.format(this.orderForm.expectedArrivedTime, "yyyy-MM-dd HH:mm:ss");
                    }
                    if(this.feeForm.isCollectFlag){
                        jsonStr.collectFlag="1";
                    }else{
                        jsonStr.collectFlag="0";
                    }
                    if(this.feeForm.isPickUpGoods){
                        jsonStr.pickUpGoods="1";
                    }else{
                        jsonStr.pickUpGoods="0";
                    }
                    if(this.feeForm.insure){
                        jsonStr.insure="1";
                    }else{
                        jsonStr.insure="0";
                    }
                    if(this.feeForm.isTwoDistribution){
                        jsonStr.twoDistribution="1";
                    }else{
                        jsonStr.twoDistribution="0";
                    }
                    if(this.feeForm.isReturnList){
                        jsonStr.returnList="1";
                    }else{
                        jsonStr.returnList="0";
                    }
                    jsonStr.quantity=this.totalQuantity;
                    jsonStr.weight=this.totalWeight;
                    jsonStr.cubage=this.totalCubage;
                    var ofcOrderDTO=JSON.stringify(jsonStr);
                    var cscContantAndCompanyDtoConsignorStr=this.getCscContantAndCompanyDtoConsignorStrOrConsigneeStr("consignor");
                    var cscContantAndCompanyDtoConsigneeStr=this.getCscContantAndCompanyDtoConsignorStrOrConsigneeStr("consignee");
                    var orderGoodsListStr=JSON.stringify(vm.goodsData);
                    var mobileOrderCode=this.mobileOrderVo.mobileOrderCode;
                    xescm.common.submit("/ofc/mobileorderPlaceCon"
                            ,{"ofcOrderDTOStr":ofcOrderDTO
                                ,"orderGoodsListStr":orderGoodsListStr+"~`"
                                ,"cscContantAndCompanyDtoConsignorStr":cscContantAndCompanyDtoConsignorStr
                                ,"cscContantAndCompanyDtoConsigneeStr":cscContantAndCompanyDtoConsigneeStr
                                ,"cscSupplierInfoDtoStr":null
                                ,"mobileOrderCode":mobileOrderCode
                            }
                            ,"您确认提交订单吗?"
                            ,function () {
                                location.reload();
                            });
                }
            },
            accountQuantity:function(val){
                if(!StringUtil.isEmpty(val.quantity)){
                    this.totalQuantity+=parseFloat(val.quantity);
                }
            },
            accountWeight:function(val){
                if(!StringUtil.isEmpty(val.weight)){
                    this.totalWeight+=parseFloat(val.weight);
                }
            },
            accountCubage:function(val){
                if(!StringUtil.isEmpty(val.cubage)){
                    this.totalCubage+=parseFloat(val.cubage);
                }
            },
            getCscContantAndCompanyDtoConsignorStrOrConsigneeStr:function(type){
                if(type == "consignor"){
                    var paramConsignor = {};
                    var cscContactDto = {};
                    var cscContactCompanyDto = {};
                    cscContactCompanyDto.contactCompanyName = this.orderForm.consignorName;
                    cscContactDto.contactName = this.orderForm.consignorContactName;
                    cscContactDto.purpose = "2";
                    cscContactDto.phone =this.orderForm.consignorContactPhone;
                    cscContactDto.contactCompanyName = this.orderForm.consignorName;
                    var consignorAddressCodeMessage = this.orderForm.departurePlaceCode.split(',');
                    cscContactDto.province = consignorAddressCodeMessage[0];
                    cscContactDto.city = consignorAddressCodeMessage[1];
                    cscContactDto.area = consignorAddressCodeMessage[2];
                    if(!StringUtil.isEmpty(consignorAddressCodeMessage[3])){
                        cscContactDto.street = consignorAddressCodeMessage[3];
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
                }else if(type == 'consignee'){
                    var paramConsignee = {};
                    var cscContactDto = {};
                    var cscContactCompanyDto = {};
                    cscContactCompanyDto.contactCompanyName = this.orderForm.consigneeName;
                    cscContactDto.contactName = this.orderForm.consigneeContactName;
                    cscContactDto.purpose = "1";
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
                }
            },
            deleteMobileOrder:function(){
                var _this=this;
                var mobileOrderCode =_this.mobileOrderVo.mobileOrderCode;
                if(StringUtil.isEmpty(mobileOrderCode)){
                    _this.promptInfo("没有删除的订单!",'warning');
                    return;
                }
                _this.isDisabledDelete = true;
                CommonClient.syncpost(sys.rootPath + "/ofc/deleteMobileOrder", {"mobileOrderCode":mobileOrderCode}, function(result) {
                    if (result == undefined || result == null ) {
                        _this.promptInfo("手机订单删除失败",'error');
                        _this.isDisabledDelete = false;
                    } else if (result.code == 200) {
                        _this.promptInfo("手机订单删除成功",'success');
                        _this.isDisabledDelete = false;
                        var url = "/ofc/autoAcceptMobileOrder";
                        var html = window.location.href;
                        var index = html.indexOf("/index#");
                        window.open(html.substring(0,index) + "/index#" + url);
                    } else if (result.code == 403) {
                        _this.promptInfo("没有权限",'error');
                        _this.isDisabledDelete = false;
                    } else {
                        _this.promptInfo(result.message,'error');
                        _this.isDisabledDelete = false;
                    }
                },"json");
            }
        }
    });
</script>
<script type="text/javascript">
    var oConId=document.getElementById('drag_con_id');
    var oDiv=document.getElementById('drag_img');
    var disX=0;
    var disY=0;
    var  dra_Img=function (ev) {
        var oEvent=ev||event;
        //距离存起来
        disX=oEvent.clientX-oDiv.offsetLeft;
        disY=oEvent.clientY-oDiv.offsetTop;

        function fnMove(ev) {
            var oEvent=ev||event;
            var l=oEvent.clientX-disX;
            var t=oEvent.clientY-disY;
            if(l < -(oDiv.clientWidth - oConId.clientWidth)) {
                l = -(oDiv.clientWidth - oConId.clientWidth)
            }
            if(l > 0){
                l = 0
            }
            if(t < -(oDiv.clientHeight - oConId.clientHeight)) {
                t = -(oDiv.clientHeight - oConId.clientHeight)
            }
            if(t > 0) {
                t = 0
            }
            oDiv.style.left=l+'px';
            oDiv.style.top=t+'px';
        }

        function fnUp() {
            this.onmousemove=null;
            this.onmouseup=null;
            if(this.releaseCapture) {
                this.releaseCapture();
            }
        }

        if(oDiv.setCapture) {
            oDiv.onmousemove=fnMove;
            oDiv.onmouseup=fnUp;

            oDiv.setCapture();
        } else {
            document.onmousemove=fnMove;
            document.onmouseup=fnUp;
            return false;
        }
    };
    oDiv.onmousedown=dra_Img;

</script>
<script>
    function Maxmin() {
        var imgs = document.getElementsByClassName("dragAble");
        for (var i=0;i<imgs.length;i++){
            (function(i){
                imgs[i].onclick=function () {
                    var _this=this;
                    $("#viewBiggerImg").attr('src',_this.src);
                    return
                }
            })(i)
        }
        var _ecIndex=-1,Deg_num=0;
        $('.imgClass').each(function(index, el){
            $(this).on('click',function(){
                $(this).css({"border":"4px solid #666"});
                $(this).siblings(".imgClass").css({"border":"1px solid #c2c2c2"});
                if(_ecIndex!=index){
                    _ecIndex=index;
                    Deg_num=0;
                    $("#viewBiggerImg").css({ "transform":"rotate(0deg)", })
                };
            });
        });
        $(".scales").on("click",function () {
            Deg_num++;
            $("#viewBiggerImg").css({
                "transform":"rotate("+90*Deg_num+"deg)"
            })
        });
    }
    Maxmin();
</script>
<style>
    .table>tbody>tr>td, .table>tbody>tr>th, .table>tfoot>tr>td, .table>tfoot>tr>th, .table>thead>tr>td, .table>thead>tr>th{
        padding:6px 8px !important;
        line-height:19px!important;
    }
    .col-width-376{
        width:417px;
    }
    .date_a{
        line-height:21px !important;
    }
    .form-group > label[class*="col-"]{
        margin-top:0;
        line-height:34px;
    }
    .form-horizontal .control-label{
        padding-top:0;
        line-height:30px;
    }
    /*  input[type=checkbox], input[type=radio]{
          margin:10px 0 0;
      }*/
    .has-error .checkbox, .has-error .checkbox-inline, .has-error .control-label, .has-error .help-block, .has-error .radio, .has-error .radio-inline, .has-error.checkbox label, .has-error.checkbox-inline label, .has-error.radio label, .has-error.radio-inline label{
        color:#393939;
    }
    .has-success .checkbox, .has-success .checkbox-inline, .has-success .control-label, .has-success .help-block, .has-success .radio, .has-success .radio-inline, .has-success.checkbox label, .has-success.checkbox-inline label, .has-success.radio label, .has-success.radio-inline label{
        color:#393939;
    }
    .dataTable > thead > tr > th[class*=sort]:hover{
        color:#707070;
    }
    .dataTable > thead > tr > th[class*=sorting_]{
        color:#707070;
    }
    .has-success .form-control{
        border-color:#cacaca;
    }
    .help-block{
        color:#f00 !important;
    }
    .has-error .form-control{
        border-color:#b5b5b5 !important;
    }
    .custNameIcon:hover{color:#2868c6 !important;}
    .initBtn{
        line-height:32px;
        width:34px;
        border:1px solid #cacaca;
        background:#f7f7f7!important;
        cursor:pointer;
        position:absolute;
        top:0;
        right:0;
    }
    .initBtn:hover{
        background:#fff!important;
        border:1px solid #cacaca!important;
    }
    .col-label{
        margin-right:2px;
        margin-bottom:0;
    }
    #goodsInfoListDiv .help-block{
        line-height:20px;
    }

    .imgClass{
        float: left;
        width:138px;
        height:96px;
        overflow: hidden;
        margin: 2px;
        box-sizing: border-box;
    }
    .imgClass img{
        border:1px solid #c2c2c2;
        width: 100%;
        height:100%;
    }
    .form-horizontal .checkbox, .form-horizontal .checkbox-inline, .form-horizontal .radio, .form-horizontal .radio-inline{
        padding-top:0;
    }
    .pay .chosen-container-single .chosen-search:after{
        top:3px;
    }

    .scales,.closes{
        position: relative;
        z-index: 2;
        width: 50px;height: 50px;
        float: right;
        background: #dbdbdb;
        line-height: 50px;
    }
    .scales img,.closes img{
        background-size: 100% 100%;
        margin-left: 14px;
        width: 24px;
        height: 22px;
        display: block;
        margin-top: 15px;
    }
    .MaxImg{
        margin-right: 5px;
        width: 144px;
        height: 401px;
        border: 1px solid #c2c2c2;
        float: left;
        margin-bottom: 20px;
    }
    .drag_con {
        background-image: url("${OFC_WEB_URL!}/docs/images/moren.png");
        background-size: 100% 100%;
        float: left;
        max-width: 900px;
        width:60%;
        height: 401px;
        overflow: hidden;
        position: relative;
    }
    #drag_img {
        width: 1050px;
        height: 1050px;
        position: absolute;
        left: 0;
        top: 0;

    }
    #drag_img img {
        width: 100%;
        height: 100%;
    }
</style>