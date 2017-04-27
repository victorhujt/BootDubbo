
<!DOCTYPE html>
<html>
<head>
    <title>入库单详情</title>
    <style lang="css">
        .borderNone .el-input__inner{border:none;}
        .el-table__body-wrapper{
            overflow-y: auto;
            overflow-x: hidden;
        }
    </style>
</head>
<body>
<div id="app">
    <div class="list-mian-01">
        <el-dialog title="货品实收详情" v-model="chosenRealGood" size="small">
            <el-table :data="realGoodsData" border style="width: 100%">
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
                <el-table-column property="quantity" label="实收数量">
                    <template scope="scope">
                        <el-input v-model="scope.row.realQuantity" :readOnly="true"></el-input>
                    </template>
                </el-table-column>
                <el-table-column property="unitPrice" label="单价">
                    <template scope="scope">
                        <el-input v-model="scope.row.unitPrice" :readOnly="true"></el-input>
                    </template>
                </el-table-column>
                <el-table-column property="productionBatch" label="批次号">
                    <template scope="scope">
                        <el-input v-model="scope.row.productionBatch"  :readOnly="true"></el-input>
                    </template>
                </el-table-column>
                <el-table-column property="productionTime" label="生产日期">
                    <template scope="scope">
                        <el-date-picker
                                v-model="scope.row.productionTime"
                                align="right"
                                type="date"
                                :readOnly="true"
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
                                :readOnly="true"
                                :picker-options="pickerOptions1">
                        </el-date-picker>
                    </template>
                </el-table-column>
            </el-table>
        </el-dialog>
        <div class="xe-pageHeader">
            订单信息
        </div>
        <el-form  label-width="100px">
            <div class="xe-block">
                <el-form-item label="订单号" class="xe-col-3">
                    <el-input v-model="orderCode" :readOnly="true"></el-input>
                </el-form-item>
                <el-form-item label="客户订单号" class="xe-col-3">
                    <el-input v-model="customerOrderNum" :readOnly="true"></el-input>
                </el-form-item>
                <el-form-item label="订单批次号" class="xe-col-3">
                    <el-input v-model="orderBatchNumber" :readOnly="true"></el-input>
                </el-form-item>
            </div>
            <div class="xe-block">
                <el-form-item label="订单日期" class="xe-col-3">
                    <el-date-picker
                            v-model="orderTime" :readOnly="true">
                    </el-date-picker>
                </el-form-item>
                <el-form-item label="客户名称" class="xe-col-3">
                    <el-input v-model="customerName" :readOnly="true">
                    </el-input>
                </el-form-item>
                <el-form-item label="订单状态" class="xe-col-3">
                    <el-input v-model="orderStatus" :readOnly="true">
                    </el-input>
                </el-form-item>
            </div>
            <div class="xe-block">
                <el-form-item label="订单类型" class="xe-col-3">
                    <el-input v-model="orderType" :readOnly="true">
                    </el-input>
                </el-form-item>
                <el-form-item label="业务类型" class="xe-col-3">
                    <el-input v-model="serviceType" :readOnly="true"></el-input>

                </el-form-item>
                <el-form-item label="供应商名称" class="xe-col-3">
                    <el-input v-model="supplierName" :readOnly="true"></el-input>
                </el-form-item>
            </div>
            <div class="xe-block">
                <el-form-item label="仓库名称" class="xe-col-3">
                    <el-input v-model="wareHouseName" :readOnly="true"></el-input>
                </el-form-item>
                <el-form-item label="预计到仓时间" class="xe-col-3">
                    <el-date-picker
                            v-model="arriveTime"
                            align="right"
                            type="datetime"
                            :readOnly="true"
                            placeholder="选择日期">
                    </el-date-picker>
                </el-form-item>
                </div>
            <div class="xe-block">
                <el-form-item label="备注" class="xe-col-3">
                    <el-input type="textarea" v-model="notes" :disabled="true">
                </el-form-item>
            </div>
            <div class="xe-block">
            <el-form-item label="开单员" class="xe-col-3">
                 <el-input v-model="merchandiser" :readOnly="true"></el-input>
             </el-form-item>
                <el-form-item label="订单来源" class="xe-col-3">
                    <el-input v-model="orderSource" :readOnly="true"></el-input>
                </el-form-item>
            </div>
            <div class="xe-block">
                <el-form-item label="创建日期" class="xe-col-3">
                    <el-date-picker
                            v-model="createTime" :readOnly="true">
                    </el-date-picker>
                </el-form-item>
                <el-form-item label="创建人员" class="xe-col-3">
                    <el-input v-model="creator" :readOnly="true"></el-input>
                </el-form-item>
                <el-form-item label="完成日期" class="xe-col-3">
                    <el-date-picker
                            v-model="finishedTime" :readOnly="true">
                    </el-date-picker>
                </el-form-item>
            </div>
            <div class="xe-block">
                <el-form-item label="取消日期" class="xe-col-3">
                    <el-date-picker
                            v-model="abolishTime" :readOnly="true">
                    </el-date-picker>
                </el-form-item>
                <el-form-item label="取消人" class="xe-col-3">
                    <el-input v-model="abolisher" :readOnly="true"></el-input>
                </el-form-item>
            </div>
            <div class="xe-pageHeader">
                配送信息
            </div>
            <div class="xe-block">
                <el-form-item label="是否提供运输" class="xe-col-3">
                    <el-input v-model="needTransport" :readOnly="true"></el-input>
                </el-form-item>
                <el-form-item label="运输单号" class="xe-col-3">
                    <el-input v-model="transCode" :readOnly="true"></el-input>
                </el-form-item>
            </div>
            <div class="xe-block">
                <el-form-item label="出发地" class="xe-col-3">
                    <el-input v-model="consignorAddress"  :readOnly="true"></el-input>
                </el-form-item>
            </div>
            <div class="xe-block">
                <el-form-item label="目的地" class="xe-col-3">
                    <el-input v-model="destinationAddress"  :readOnly="true"></el-input>
                </el-form-item>
            </div>
            <div class="xe-block">
                <el-form-item label="车牌号" class="xe-col-3">
                    <el-input v-model="plateNumber"  :readOnly="true"></el-input>
                </el-form-item>
                <el-form-item label="司机姓名" class="xe-col-3">
                    <el-input v-model="driverName"  :readOnly="true"></el-input>
                </el-form-item>
                <el-form-item label="联系电话" class="xe-col-3">
                    <el-input v-model="driverContactNumber"  :readOnly="true"></el-input>
                </el-form-item>
            </div>
            <div class="xe-pageHeader">
                发货方
            </div>
            <div class="xe-block">
                <el-form-item label="名称" class="xe-col-3">
                    <el-input v-model="consignorName" :readOnly="true"></el-input>
                </el-form-item>
                <el-form-item label="联系人" class="xe-col-3">
                    <el-input v-model="consignorContactName" :readOnly="true"></el-input>
                </el-form-item>
                <el-form-item label="联系电话" class="xe-col-3">
                    <el-input v-model="consignorContactPhone" :readOnly="true"></el-input>
                </el-form-item>
            </div>
            <div class="xe-block">
                <el-form-item label="地址" class="xe-col-3">
                    <el-input v-model="consignorAddress" :readOnly="true"></el-input>
                </el-form-item>
            </div>
            <div class="xe-pageHeader">
                货品信息
            </div>
            <div>
                <el-button type="primary" @click="realGood">实收详情</el-button>
            </div>
            <el-table :data="goodsData" border highlight-current-row  style="width: 100%">
                <el-table-column property="goodsType" label="货品种类">
                    <template scope="scope">
                        <el-input v-model="scope.row.goodsType" :readOnly="true"></el-input>
                    </template>
                </el-table-column>
                <el-table-column property="goodsCategory" label="货品类别">
                    <template scope="scope">
                        <el-input v-model="scope.row.goodsCategory" :readOnly="true"></el-input>
                    </template>
                    </el-table-column >
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
                        <el-input v-model="scope.row.goodsSpec"  :readOnly="true"></el-input>
                    </template>
                </el-table-column>
                <el-table-column property="unit" label="单位">
                    <template scope="scope">
                        <el-input v-model="scope.row.unit" :readOnly="true"></el-input>
                    </template>
                </el-table-column>
                <el-table-column property="quantity" label="入库数量">
                    <template scope="scope">
                        <el-input v-model="scope.row.quantity" :readOnly="true"></el-input>
                    </template>
                </el-table-column>
                <el-table-column property="primaryQuantity" label="主单位数量">
                    <template scope="scope">
                        <el-input v-model="scope.row.primaryQuantity"  :readOnly="true"></el-input>
                    </template>
                </el-table-column>
                <el-table-column property="unitPrice" label="单价">
                    <template scope="scope">
                        <el-input v-model="scope.row.unitPrice" :readOnly="true"></el-input>
                    </template>
                </el-table-column>
                <el-table-column property="productionBatch" label="批次号">
                    <template scope="scope">
                        <el-input v-model="scope.row.productionBatch" :readOnly="true"></el-input>
                    </template>
                </el-table-column>
                <el-table-column property="productionTime" label="生产日期">
                    <template scope="scope">
                        <el-date-picker
                                v-model="scope.row.productionTime"
                                align="right"
                                type="date"
                                :readOnly="true"
                                placeholder="选择日期">
                        </el-date-picker>
                    </template>
                </el-table-column>
                <el-table-column property="invalidTime" label="失效日期">
                    <template scope="scope">
                        <el-date-picker
                                v-model="scope.row.invalidTime"
                                align="right"
                                type="date"
                                :readOnly="true"
                                placeholder="选择日期">
                        </el-date-picker>
                    </template>
                </el-table-column>
                <el-table-column property="supportBatch" label="供应商批次">
                    <template scope="scope">
                        <el-input v-model="scope.row.supportBatch" class="borderNone" :readOnly="true"></el-input>
                    </template>
                </el-table-column>
            </el-table>
            <el-table :data="orderStatusData" border highlight-current-row >
                    <el-table-column property="notes" label="跟踪信息">
                        <template scope="scope">
                            <el-input v-model="scope.row.notes" class="borderNone" :readOnly="true"></el-input>
                        </template>
                    </el-table-column>
            </el-table>
            <div>
                <el-button type="primary" @click="goBack">返回</el-button>
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
                orderCode:'',
                consigneeName:'',
                consigneeContactName:'',
                consigneeContactPhone:'',
                destinationAddress:'',
                createTime:'',
                abolishTime:'',
                abolisher:'',
                finishedTime:'',
                creator:'',
                orderType:'仓储订单',
                wareHouseName:'',
                goodsName:'',
                goodsSpec:'',
                unit:'',
                goodsCategoryOptions:[],
                transCode:'',
                goodsType:'',
                goodsCategory:'',
                invalidTime:'',
                productionTime:'',
                notes:'',
                customerOrderNum: '',
                orderBatchNumber:'',
                customerName: '',
                customerCode:'',
                orderSource:'',
                orderStatus:'',
                consignorName:'',
                consignorContactName:'',
                consignorPhoneNumber:'',
                consignorAddress:'',
                supplierName:'',
                wareHouseOptions:[],
                supportBatchData:[],
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
                orderStatusOptions:[
                    {
                        value: '10',
                        label: '待审核'
                    },{
                        value: '20',
                        label: '已审核'
                    },{ value: '30',
                        label: '执行中'},
                    {
                        value: '40',
                        label: '已完成'
                    },{
                        value: '50',
                        label: '已取消'
                    }
                ],
                goodsMsgOptions: [],
                consignorContactPhone:'',
                serviceType: '',
                merchandiser: '',
                orderTime: new Date(),
                arriveTime: '',
                isNeedTransport:false,
                needTransport:'',
                plateNumber:'',
                driverName:'',
                driverContactNumber:'',
                formLabelWidth: '100px',
                isDisabled: false,
                isDisabled11: false,
                goodsData:[],
                realGoodsData:[],
                chosenRealGood:false,
                orderStatusData:[]
            };
        },

        beforeMount:function(){
            var vueObj=this;
            var url=window.location.href;
            CommonClient.syncpost(sys.rootPath + "/ofc/getCscGoodsTypeList",{"pid":null},function(result) {
                var data=eval(result);
                vueObj.goodsMsgOptions=[];
                vueObj.realGoodsData = [];
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
                    CommonClient.syncpost(sys.rootPath + "/ofc/queryRealGood",{"orderCode":orderCode,"businessType":"RK"},function(result) {
                        if(result==undefined||result==null||result.result==null){
                            return;
                        }else if(result.code == 200) {
                            var data = eval(result.result);
                            $.each(data, function (index, wmsDetailsValueDTO) {
                                var good = {};
                                good.goodsCode = wmsDetailsValueDTO.itemCode;
                                good.goodsName = wmsDetailsValueDTO.itemName;
                                good.goodsSpec = wmsDetailsValueDTO.standard;
                                good.unit = wmsDetailsValueDTO.uom;
                                good.realQuantity = wmsDetailsValueDTO.sendQty;//
                                good.unitPrice = wmsDetailsValueDTO.price;
                                good.productionBatch = wmsDetailsValueDTO.lotatt05;//批次号
                                good.productionTime = DateUtil.parse(wmsDetailsValueDTO.lotatt01);//生产日期
                                good.invalidTime = DateUtil.parse(wmsDetailsValueDTO.lotatt02);//失效日期
                                vueObj.realGoodsData.push(good);
                            });
                        }
                    });
                    CommonClient.post(sys.rootPath + "/ofc/orderStorageDetails", {"orderCode":orderCode}, function(result) {
                        if(result==undefined||result==null||result.result==null){
                            layer.msg("订单详情查询失败");
                            return;
                        }else if(result.code == 200){
                            var ofcFundamentalInformation=result.result.ofcFundamentalInformation;
                            var ofcWarehouseInformation=result.result.ofcWarehouseInformation;
                            var ofcGoodsDetailsInfo=result.result.ofcGoodsDetailsInfo;
                            var ofcDistributionBasicInfo=result.result.ofcDistributionBasicInfo;
                            var status=result.result.status;
                            var statusArray=result.result.statusLog;

                            if(ofcFundamentalInformation!=null){
                                vueObj.orderCode=ofcFundamentalInformation.orderCode;
                                vueObj.orderTime=DateUtil.parse(ofcFundamentalInformation.orderTime);
                                vueObj.merchandiser=ofcFundamentalInformation.merchandiser;
                                vueObj.customerName=ofcFundamentalInformation.custName;
                                vueObj.customerCode=ofcFundamentalInformation.custCode;
                                vueObj.customerOrderNum=ofcFundamentalInformation.custOrderCode;
                                vueObj.serviceType =vueObj.getServiceTypeName(ofcFundamentalInformation.businessType);
                                vueObj.orderStatus=vueObj.getOrderStatusName(status.orderStatus);
                                vueObj.notes=ofcFundamentalInformation.notes;
                                vueObj.orderBatchNumber=ofcFundamentalInformation.orderBatchNumber;
                                vueObj.orderSource=ofcFundamentalInformation.orderSource;
                                vueObj.createTime=DateUtil.parse(ofcFundamentalInformation.creationTime);
                                vueObj.finishedTime=DateUtil.parse(ofcFundamentalInformation.finishedTime);
                                vueObj.abolishTime=DateUtil.parse(ofcFundamentalInformation.abolishTime);
                                vueObj.creator=ofcFundamentalInformation.creatorName;
                                vueObj.abolisher=ofcFundamentalInformation.abolisherName;
                                if(ofcWarehouseInformation!=null){
                                    vueObj.wareHouseName=ofcWarehouseInformation.warehouseName;
                                    vueObj.supplierName=ofcWarehouseInformation.supportName;
                                    vueObj.arriveTime=DateUtil.parse(ofcWarehouseInformation.arriveTime);
                                    vueObj.plateNumber=ofcWarehouseInformation.plateNumber;
                                    vueObj.driverName=ofcWarehouseInformation.driverName;
                                    vueObj.driverContactNumber=ofcWarehouseInformation.contactNumber;
                                    if(ofcWarehouseInformation.provideTransport=="1"){
                                        vueObj.isNeedTransport=true;
                                        vueObj.needTransport="是";
                                    }else{
                                        vueObj.isNeedTransport=false;
                                        vueObj.needTransport="否";
                                    }
                                    if(ofcDistributionBasicInfo!=null){
                                        //发货方
                                        vueObj.consignorName=ofcDistributionBasicInfo.consignorName;
                                        vueObj.consignorContactName=ofcDistributionBasicInfo.consignorContactName;
                                        vueObj.transCode = ofcDistributionBasicInfo.transCode;
                                        vueObj.consignorContactPhone=ofcDistributionBasicInfo.consignorContactPhone;
                                        if(ofcDistributionBasicInfo.departureProvince!=null){
                                            vueObj.consignorAddress=ofcDistributionBasicInfo.departureProvince;
                                        }
                                        if(ofcDistributionBasicInfo.departureCity!=null){
                                            vueObj.consignorAddress=vueObj.consignorAddress+ofcDistributionBasicInfo.departureCity;
                                        }
                                        if(ofcDistributionBasicInfo.departureDistrict!=null){
                                            vueObj.consignorAddress=vueObj.consignorAddress+ofcDistributionBasicInfo.departureDistrict;
                                        }
                                        if(ofcDistributionBasicInfo.departureTowns!=null){
                                            vueObj.consignorAddress=vueObj.consignorAddress+ofcDistributionBasicInfo.departureTowns;
                                        }
                                        if(ofcDistributionBasicInfo.departurePlace!=null){
                                            vueObj.consignorAddress=vueObj.consignorAddress+ofcDistributionBasicInfo.departurePlace;
                                        }
                                        if(ofcDistributionBasicInfo.destinationProvince!=null){
                                            vueObj.destinationAddress=ofcDistributionBasicInfo.destinationProvince;
                                        }
                                        if(ofcDistributionBasicInfo.destinationCity!=null){
                                            vueObj.destinationAddress=vueObj.destinationAddress+ofcDistributionBasicInfo.destinationCity;
                                        }
                                        if(ofcDistributionBasicInfo.destinationDistrict!=null){
                                            vueObj.destinationAddress=vueObj.destinationAddress+ofcDistributionBasicInfo.destinationDistrict;
                                        }
                                        if(ofcDistributionBasicInfo.destinationTowns!=null){
                                            vueObj.destinationAddress= vueObj.destinationAddress+ofcDistributionBasicInfo.destinationTowns;
                                        }
                                        if(ofcDistributionBasicInfo.destination!=null){
                                            vueObj.destinationAddress= vueObj.destinationAddress+ofcDistributionBasicInfo.destination;
                                        }
                                        vueObj.consigneeName=ofcDistributionBasicInfo.consigneeName;
                                        vueObj.consigneeContactName=ofcDistributionBasicInfo.consigneeContactName;
                                        vueObj.consigneeContactPhone=ofcDistributionBasicInfo.consigneeContactPhone;
                                    };
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
                                            good.unit=goodDetail.unit;
                                            good.productionBatch=goodDetail.productionBatch;
                                            good.productionTime=DateUtil.parse(goodDetail.productionTime);
                                            good.invalidTime=DateUtil.parse(goodDetail.invalidTime);
                                           // good.primaryQuantity = goodDetail.primaryQuantity;
                                            if(vueObj.supportBatchData.length==0){
                                                vueObj.selectSupplier();
                                            }
                                            good.supportBatch=vueObj.getGoodSupportName(goodDetail.supportBatch);
                                            vueObj.goodsData.push(good);
                                        }
                                    }
                                    if(statusArray!=null&&statusArray.length>0){
                                        for(var i=0;i<statusArray.length;i++){
                                            var orderStatus=statusArray[i];
                                            var status={};
                                            status.notes=orderStatus.notes;
                                            vueObj.orderStatusData.push(status);
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
            getServiceTypeName:function(val){
                for(var i=0;i<this.serviceTypeOptions.length;i++){
                    var option=this.serviceTypeOptions[i];
                    if(val==option.value){
                        return option.label;
                    }
                }
            },
            getOrderStatusName:function(val){
                for(var i=0;i<this.orderStatusOptions.length;i++){
                    var option=this.orderStatusOptions[i];
                    if(val==option.value){
                        return option.label;
                    }
                }
            },
            goBack:function(){
                var newurl = "/ofc/orderStorageInManager/";
                var html = window.location.href;
                var index = html.indexOf("/index#");
                window.open(html.substring(0,index) + "/index#" + newurl);
            },
            realGood:function(){
                var _this=this;
               // _this.realGoodsData=[];
                _this.chosenRealGood=true;

            },
            selectSupplier:function(){
                this.supportBatchData=[];
                var vueObj=this;
                var param = {};
                param.customerCode = vueObj.customerCode;
                param.pNum = 1;
                param.pSize= 50;
                CommonClient.syncpost(sys.rootPath + "/ofc/supplierSelect",param, function(result) {
                    var data = eval(result);
                    if (data == undefined || data == null || data.result == undefined || data.result ==null || data.result.length == 0) {
                      //  layer.msg("暂时未查询到供应商信息！！");
                    } else if (data.code == 200) {
                        $.each(data.result.list,function (index,CscSupplierInfoDto) {
                            var option={};
                            option.label=StringUtil.nullToEmpty(CscSupplierInfoDto.supplierName);
                            option.value=StringUtil.nullToEmpty(CscSupplierInfoDto.supplierCode);
                            vueObj.supportBatchData.push(option);
                        });
                    } else if (result.code == 403) {
                      //  vueObj.promptInfo("没有权限",'error');
                    }
                },"json");
            },
            getGoodSupportName:function(val){
                for(var i=0;i<this.supportBatchData.length;i++){
                    var option=this.supportBatchData[i];
                    if(val==option.value){
                        return option.label;
                    }
                }
            }
        }
    });
</script>
</html>