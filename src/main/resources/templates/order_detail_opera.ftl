<head>
    <title>订单详情</title>
</head>
<body class="no-skin">

<div class="col-xs-12">
    <div class="col-sm-12" style="float: right;">
        <div class="col-sm-12" style="float: right"><span hidden="true" id = "REPORT">${(REPORT)!}</span>
        <button style="float:left;padding-left: 11px;margin:0 0 20px -11px;" class="btn btn-white btn-info btn-bold filters" id="goBack" value="" onclick="facePrint()">
            打印面单
        </button>
    </div>
    <form id="" method="post" class="form-horizontal" role="form">
        <div class="page-header">
            <p>
                订单详情
            </p>
        </div>
        <div class="row">
            <div class="col-xs-12">
                <form class="form-horizontal">
                    <div class="form-group">
                        <label class="control-label col-label no-padding-right" for="name">订单编号</label>
                        <div class="w-width-220 col-float">
                            <input id="orderCode" name="" type="search" placeholder=""
                                   aria-controls="dynamic-table" value="${(ofcFundamentalInformation.orderCode)!""}">
                        </div>
                        <label class="control-label col-label no-padding-right" for="name">客户订单编号</label>
                        <div class="w-width-220 col-float">
                            <input id="custOrderCode" name="" type="search" placeholder=""
                                   aria-controls="dynamic-table"
                                   value="${(ofcFundamentalInformation.custOrderCode)!""}">
                        </div>
                        <label class="control-label col-label no-padding-right" for="name">订单批次号</label>
                        <div class="w-width-220 col-float">
                            <input id="orderBatchNumber" name="" type="search" placeholder=""
                                   aria-controls="dynamic-table"
                                   value="${(ofcFundamentalInformation.orderBatchNumber)!""}">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-label no-padding-right" for="name">订单日期</label>
                        <div class="w-width-220 col-float">
                            <input id="orderCode" name="" type="search" placeholder=""
                                   aria-controls="dynamic-table"
                                   value="${(ofcFundamentalInformation.orderTime?string("yyyy-MM-dd"))!""}">
                        </div>
                        <label class="control-label col-label no-padding-right" for="name">客户名称</label>
                        <div class="w-width-220 col-float">
                            <input id="orderCode" name="" type="search" placeholder=""
                                   aria-controls="dynamic-table" value="${(ofcFundamentalInformation.custName)!""}">
                        </div>
                        <label class="control-label col-label no-padding-right" for="name">订单状态</label>
                        <div class="w-width-220 col-float">
                            <input id="orderStatus" name="" type="search" placeholder=""
                                   aria-controls="dynamic-table" value="${(ofcFundamentalInformation.orderStatus)!""}">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-label no-padding-right" for="name">订单类型</label>
                        <div class="w-width-220 col-float">
                            <input id="orderType" name="custName" type="search" placeholder=""
                                   aria-controls="dynamic-table" value="${(ofcFundamentalInformation.orderType)!""}">
                        </div>
                        <label class="control-label col-label no-padding-right" for="name">业务类型</label>
                        <div class="w-width-220 col-float">
                            <input id="businessType" name="custName" type="search" placeholder=""
                                   aria-controls="dynamic-table" value="${(ofcFundamentalInformation.businessType)!""}">
                        </div>
                        <label class="control-label col-label no-padding-right" for="name">运输类型</label>
                        <div class="w-width-220 col-float">
                            <input id="transportType" name="custName" type="search" placeholder=""
                                   aria-controls="dynamic-table"
                                   value="${(ofcFundamentalInformation.transportType)!""}">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-label no-padding-right" for="name">渠道</label>
                        <div class="w-width-220 col-float">
                            <input id="orderCode" name="custName" type="search" placeholder=""
                                   aria-controls="dynamic-table">
                        </div>
                        <label class="control-label col-label no-padding-right" for="name">平台类型</label>
                        <div class="w-width-220 col-float">
                            <input id="platformType" name="" type="search" placeholder=""
                                   aria-controls="dynamic-table" value="${(ofcFundamentalInformation.platformType)!""}">
                        </div>
                        <label class="control-label col-label no-padding-right" for="name">订单来源</label>
                        <div class="w-width-220 col-float">
                            <input id="orderSource" name="custName" type="search" placeholder=""
                                   aria-controls="dynamic-table" value="${(ofcFundamentalInformation.orderSource)!""}">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-label no-padding-right" for="name">服务产品</label>
                        <div class="w-width-220 col-float">
                            <input id="productName" name="custName" type="search" placeholder=""
                                   aria-controls="dynamic-table" value="${(ofcFundamentalInformation.productName)!""}">
                        </div>
                        <label class="control-label col-label no-padding-right" for="name">开单员</label>
                        <div class="w-width-220 col-float">
                            <input id="merchandiser" name="custName" type="search" placeholder=""
                                   aria-controls="dynamic-table" value="${(ofcFundamentalInformation.merchandiser)!""}">
                        </div>
                        <label class="control-label col-label no-padding-right" for="name">备注</label>
                        <div class="w-width-220 col-float">
                            <input id="notes" name="custName" type="search" placeholder=""
                                   aria-controls="dynamic-table" value="${(ofcFundamentalInformation.notes)!""}">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-label no-padding-right" for="name">创建日期</label>
                        <div class="w-width-220 col-float">
                            <input id="creationTime" name="custName" type="search" placeholder=""
                                   aria-controls="dynamic-table"
                                   value="${(ofcFundamentalInformation.creationTime?string("yyyy-MM-dd HH:mm:ss"))!""}">
                        </div>
                        <label class="control-label col-label no-padding-right" for="name">创建人员</label>
                        <div class="w-width-220 col-float">
                            <input id="creator" name="custName" type="search" placeholder=""
                                   aria-controls="dynamic-table" value="${(ofcFundamentalInformation.creatorName)!""}">
                        </div>
                        <label class="control-label col-label no-padding-right" for="name">完成日期</label>
                        <div class="w-width-220 col-float">
                            <input id="finishedTime" name="custName" type="search" placeholder=""
                                   aria-controls="dynamic-table"
                                   value="${(ofcFundamentalInformation.finishedTime?string("yyyy-MM-dd HH:mm:ss"))!""}">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-label no-padding-right" for="name">取消日期</label>
                        <div class="w-width-220 col-float">
                            <input id="abolishTime" name="abolishTime" type="search" placeholder=""
                                   aria-controls="dynamic-table"
                                   value="${(ofcFundamentalInformation.abolishTime?string("yyyy-MM-dd HH:mm:ss"))!""}">
                        </div>
                        <label class="control-label col-label no-padding-right" for="name">取消人</label>
                        <div class="w-width-220 col-float">
                            <input id="abolisherName" name="abolisherName" type="search" placeholder=""
                                   aria-controls="dynamic-table" value="${(ofcFundamentalInformation.abolisherName)!""}">
                        </div>
                    </div>
                </form>
            </div>
        </div>

        <div class="page-header">
            <p>
                配送信息
            </p>
        </div>
        <div class="row">
            <div class="col-xs-12">
                <form class="form-horizontal">
                    <div class="form-group">
                        <label class="control-label col-label no-padding-right" for="name">运输单号</label>
                        <div class="w-width-220 col-float">
                            <input id="transCode" name="" type="search" placeholder=""
                                   aria-controls="dynamic-table" value="${(ofcDistributionBasicInfo.transCode)!""}">
                        </div>
                        <label class="control-label col-label no-padding-right" for="name">出发地</label>
                        <div class="col-float padding-15" style="width:460px;">
                            <input id="departurePlace" name="" type="search" placeholder="" style="width:460px;"
                                   aria-controls="dynamic-table"
                                   value="${(ofcDistributionBasicInfo.departureProvince)!""}${(ofcDistributionBasicInfo.departureCity)!""}${(ofcDistributionBasicInfo.departureDistrict)!""}${(ofcDistributionBasicInfo.departureTowns)!""}${(ofcDistributionBasicInfo.departurePlace)!""}">
                        </div>

                    </div>
                    <div class="form-group">
                        <label class="control-label col-label no-padding-right" for="name">车牌号</label>
                        <div class="w-width-220 col-float">
                            <input id="plateNumber" name="plateNumber" type="search" placeholder=""
                                   aria-controls="dynamic-table" value="${(ofcDistributionBasicInfo.plateNumber)!""}">
                        </div>
                        <label class="control-label col-label no-padding-right" for="name">目的地</label>
                        <div class="padding-15 col-float" style="width:460px;">
                            <input id="destination" name="" type="search" placeholder="" style="width:460px;"
                                   aria-controls="dynamic-table" value="${(ofcDistributionBasicInfo.destinationProvince)!""}${(ofcDistributionBasicInfo.destinationCity)!""}${(ofcDistributionBasicInfo.destinationDistrict)!""}${(ofcDistributionBasicInfo.destinationTowns)!""}${(ofcDistributionBasicInfo.destination)!"" }">
                        </div>

                    </div>
                    <div class="form-group">
                        <label class="control-label col-label no-padding-right" for="name">司机姓名</label>
                        <div class="w-width-220 col-float">
                            <input id="driverName" name="driverName" type="search" placeholder=""
                                   aria-controls="dynamic-table" value="${(ofcDistributionBasicInfo.driverName)!""}">
                        </div>
                        <label class="control-label col-label no-padding-right" for="name">联系电话</label>
                        <div class="w-width-220 col-float">
                            <input id="contactNumber" name="contactNumber" type="search" placeholder=""
                                   aria-controls="dynamic-table" value="${(ofcDistributionBasicInfo.contactNumber)!""}">
                        </div>
                        <label class="control-label col-label no-padding-right" for="name">数量</label>
                        <div class="w-width-220 col-float">
                            <input id="quantity" name="quantity" type="search" placeholder=""
                                   aria-controls="dynamic-table" value="${(ofcDistributionBasicInfo.quantity)!""}">
                        </div>

                    </div>
                    <div class="form-group">
                        <label class="control-label col-label no-padding-right" for="name">重量</label>
                        <div class="w-width-220 col-float">
                            <input id="weight" name="weight" type="search" placeholder="" style="margin-right:5px;"
                                   aria-controls="dynamic-table" value="${(ofcDistributionBasicInfo.weight)!""}">KG
                        </div>
                        <label class="control-label col-label no-padding-right" for="name">体积</label>
                        <div class="w-width-220 col-float">
                            <input id="cubage" name="cubage" type="search" placeholder=""
                                   aria-controls="dynamic-table" value="${(ofcDistributionBasicInfo.cubage)!""}">m³
                        </div>
                        <label class="control-label col-label no-padding-right" for="name">运输要求</label>
                        <div class="w-width-220 col-float">
                            <input id="transRequire" name="transRequire" type="search" placeholder=""
                                   aria-controls="dynamic-table" value="${(ofcDistributionBasicInfo.transRequire)!""}">
                        </div>
                    </div>
                </form>
            </div>
        </div>
        <div class="page-header">
            <p>
                发货方
            </p>
        </div>
        <div class="row">
            <div class="col-xs-12">
                <form class="form-horizontal">
                    <div class="form-group">
                        <label class="control-label col-label no-padding-right" for="name">名称</label>
                        <div class="w-width-220 col-float">
                            <input id="consignorName" name="consignorName" type="search" placeholder=""
                                   aria-controls="dynamic-table" value="${(ofcDistributionBasicInfo.consignorName)!""}">
                        </div>
                        <label class="control-label col-label no-padding-right" for="name">联系人</label>
                        <div class="w-width-220 col-float">
                            <input id="consignorContactName" name="consignorContactName" type="search" placeholder=""
                                   aria-controls="dynamic-table"
                                   value="${(ofcDistributionBasicInfo.consignorContactName)!""}">
                        </div>
                        <label class="control-label col-label no-padding-right" for="name">联系电话</label>
                        <div class="w-width-220 col-float">
                            <input id="consignorContactPhone" name="consignorContactPhone" type="search" placeholder=""
                                   aria-controls="dynamic-table"
                                   value="${(ofcDistributionBasicInfo.consignorContactPhone)!""}">
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="control-label col-label no-padding-right" for="name">地址</label>
                        <div class="padding-15 y-float" style="width:492px;">
                            <input id="orderCode" name="custName" type="search" style="width: 100%;" placeholder=""
                                   aria-controls="dynamic-table"
                                   value="${(ofcDistributionBasicInfo.departureProvince)!""}${(ofcDistributionBasicInfo.departureCity)!""}${(ofcDistributionBasicInfo.departureDistrict)!""}${(ofcDistributionBasicInfo.departureTowns)!""}${(ofcDistributionBasicInfo.departurePlace)!""}">
                        </div>
                    </div>
                </form>
            </div>
        </div>

        <div class="page-header">
            <p>
                收货方
            </p>
        </div>
        <div class="row">
            <div class="col-xs-12">
                <form class="form-horizontal">
                    <div class="form-group">
                        <label class="control-label col-label no-padding-right" for="name">名称</label>
                        <div class="w-width-220 col-float">
                            <input id="orderCode" name="custName" type="search" placeholder=""
                                   aria-controls="dynamic-table" value="${(ofcDistributionBasicInfo.consigneeName)!""}">
                        </div>
                        <label class="control-label col-label no-padding-right" for="name">联系人</label>
                        <div class="w-width-220 col-float">
                            <input id="consigneeContactName" name="consigneeContactName" type="search" placeholder=""
                                   aria-controls="dynamic-table"
                                   value="${(ofcDistributionBasicInfo.consigneeContactName)!""}">
                        </div>
                        <label class="control-label col-label no-padding-right" for="name">联系电话</label>
                        <div class="w-width-220 col-float">
                            <input id="consigneeContactPhone" name="consigneeContactPhone" type="search" placeholder=""
                                   aria-controls="dynamic-table"
                                   value="${(ofcDistributionBasicInfo.consigneeContactPhone)!""}">
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="control-label col-label no-padding-right" for="name">地址</label>
                        <div class="padding-15 y-float" style="width:492px;">
                            <input id="address" name="address" type="search" style="width: 100%;" placeholder=""
                                   aria-controls="dynamic-table"
                                   value="${(ofcDistributionBasicInfo.destinationProvince)!""}${(ofcDistributionBasicInfo.destinationCity)!""}${(ofcDistributionBasicInfo.destinationDistrict)!""}${(ofcDistributionBasicInfo.destinationTowns)!""}${(ofcDistributionBasicInfo.destination)!"" }">
                        </div>
                    </div>
                </form>
            </div>
        </div>

        <div class="page-header">
            <p>
                服务项目及费用
            </p>
        </div>
        <div class="row">
            <div class="col-xs-12">
                <form class="form-horizontal">
                    <div class="form-group">
                        <label class="control-label col-label no-padding-right" for="name">运费</label>
                        <div class="w-width-220 col-float">
                            <input id="luggage" name="luggage" type="search" placeholder=""
                                   aria-controls="dynamic-table" value="${(ofcFinanceInformation.luggage)!"0.00"}">
                        </div>
                        <label class="control-label col-label no-padding-right" for="name">费用总计</label>
                        <div class="w-width-220 col-float">
                            <input id="serviceCharge" name="serviceCharge" type="search" placeholder=""
                                   aria-controls="dynamic-table" value="${(ofcFinanceInformation.serviceCharge)!"0.00"}">
                        </div>
                        <label class="control-label col-label no-padding-right" for="name">费用支付方</label>
                        <div class="w-width-220 col-float">
                            <input id="expensePaymentParty" name="expensePaymentParty" type="search" placeholder=""
                                   aria-controls="dynamic-table"
                                   value="${(ofcFinanceInformation.expensePaymentParty)!""}">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-label no-padding-right" for="name">支付方式</label>
                        <div class="w-width-220 col-float">
                            <input id="payment" name="payment" type="search" placeholder=""
                                   aria-controls="dynamic-table">
                        </div>
                        <label class="control-label col-label no-padding-right" for="name">现结</label>
                        <div class="w-width-220 col-float">
                            <input id="currentAmount" name="currentAmount" type="search" placeholder=""
                                   aria-controls="dynamic-table" value="${(ofcFinanceInformation.currentAmount)!"0.00"}">
                        </div>
                        <label class="control-label col-label no-padding-right" for="name">到付</label>
                        <div class="w-width-220 col-float">
                            <input id="toPayAmount" name="toPayAmount" type="search" placeholder=""
                                   aria-controls="dynamic-table"  value="${(ofcFinanceInformation.toPayAmount)!"0.00"}">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-label no-padding-right" for="name">回付</label>
                        <div class="w-width-220 col-float">
                            <input id="returnAmount" name="returnAmount" type="search" placeholder=""
                                   aria-controls="dynamic-table" value="${(ofcFinanceInformation.returnAmount)!"0.00"}">
                        </div>
                        <label class="control-label col-label no-padding-right" for="name">月结</label>
                        <div class="w-width-220 col-float">
                            <input id="monthlyAmount" name="monthlyAmount" type="search" placeholder=""
                                   aria-controls="dynamic-table" value="${(ofcFinanceInformation.monthlyAmount)!"0.00"}">
                        </div>
                        <label class="control-label col-label no-padding-right" for="name">是否开发票</label>
                        <div class="w-width-220 col-float">
                            <input id="openInvoices" name="openInvoices" type="search" placeholder=""
                                   aria-controls="dynamic-table" value="${(ofcFinanceInformation.openInvoices)!""}" >
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-label no-padding-right" for="name">是否上门提货</label>
                        <div class="w-width-220 col-float">
                            <input id="pickUpGoods" name="pickUpGoods" type="search" placeholder=""
                                   aria-controls="dynamic-table" value="${(ofcFinanceInformation.pickUpGoods)!""}" >
                        </div>
                        <label class="control-label col-label no-padding-right" for="name">上门提货费用</label>
                        <div class="w-width-220 col-float">
                            <input id="homeDeliveryFee" name="homeDeliveryFee" type="search" placeholder=""
                                   aria-controls="dynamic-table" value="${(ofcFinanceInformation.homeDeliveryFee)!"0.00"}">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-label no-padding-right" for="name">是否二次配送</label>
                        <div class="w-width-220 col-float">
                            <input id="twoDistribution" name="twoDistribution" type="search" placeholder=""
                                   aria-controls="dynamic-table" value="${(ofcFinanceInformation.twoDistribution)!""}" >
                        </div>
                        <label class="control-label col-label no-padding-right" for="name">二次配送费用</label>
                        <div class="w-width-220 col-float">
                            <input id="twoDistributionFee" name="twoDistributionFee" type="search" placeholder=""
                                   aria-controls="dynamic-table" value="${(ofcFinanceInformation.twoDistributionFee)!"0.00"}">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-label no-padding-right" for="name">是否签单返回</label>
                        <div class="w-width-220 col-float">
                            <input id="returnList" name="returnList" type="search" placeholder=""
                                   aria-controls="dynamic-table" value="${(ofcFinanceInformation.returnList)!""}">
                        </div>
                        <label class="control-label col-label no-padding-right" for="name">签单返回费用</label>
                        <div class="w-width-220 col-float">
                            <input id="returnListFee" name="returnListFee" type="search" placeholder=""
                                   aria-controls="dynamic-table" value="${(ofcFinanceInformation.returnListFee)!"0.00"}">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-label no-padding-right" for="name">是否货物保险</label>
                        <div class="w-width-220 col-float">
                            <input id="insure" name="insure" type="search" placeholder=""
                                   aria-controls="dynamic-table" value="${(ofcFinanceInformation.insure)!""}">
                        </div>
                        <label class="control-label col-label no-padding-right" for="name">保险费用</label>
                        <div class="w-width-220 col-float">
                            <input id="insurance" name="insurance" type="search" placeholder=""
                                   aria-controls="dynamic-table" value="${(ofcFinanceInformation.cargoInsuranceFee)!"0.00"}">
                        </div>
                        <label class="control-label col-label no-padding-right" for="name">声明价值</label>
                        <div class="w-width-220 col-float">
                            <input id="insureValue" name="insureValue" type="search" placeholder=""
                                   aria-controls="dynamic-table" value="${(ofcFinanceInformation.insureValue)!"0.00"}">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-label no-padding-right" for="name">是否代收货款</label>
                        <div class="w-width-220 col-float">
                            <input id="collectFlag" name="collectFlag" type="search" placeholder=""
                                   aria-controls="dynamic-table" value="${(ofcFinanceInformation.collectFlag)!""}">
                        </div>
                        <label class="control-label col-label no-padding-right" for="name">代收费用</label>
                        <div class="w-width-220 col-float">
                            <input id="collectServiceCharge" name="collectServiceCharge" type="search" placeholder=""
                                   aria-controls="dynamic-table" value="${(ofcFinanceInformation.collectServiceCharge)!"0.00"}">
                        </div>
                        <label class="control-label col-label no-padding-right" for="name">代收金额</label>
                        <div class="w-width-220 col-float">
                            <input id="collectLoanAmount" name="collectLoanAmount" type="search" placeholder=""
                                   aria-controls="dynamic-table"
                                   value="${(ofcFinanceInformation.collectLoanAmount)!"0.00"}">
                        </div>
                    </div>
                </form>
            </div>
        </div>


        <div class="page-header">
            <p>
                货品信息
            </p>
        </div>
        <table id="dynamic-table" class="table table-striped table-bordered table-hover dataTable no-footer" role="grid"
               aria-describedby="dynamic-table_info">
            <thead>
            <tr role="row">
                <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1"
                    aria-label="Clicks: activate to sort column ascending">货品种类
                </th>
                <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1"
                    aria-label="Clicks: activate to sort column ascending">货品小类
                </th>
                <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1"
                    aria-label="Clicks: activate to sort column ascending">货品编码
                </th>
                <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1"
                    aria-label="Clicks: activate to sort column ascending">货品名称
                </th>
                <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1"
                    aria-label="Clicks: activate to sort column ascending">规格
                </th>
                <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1"
                    aria-label="Clicks: activate to sort column ascending">单位
                </th>
                <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1"
                    aria-label="Clicks: activate to sort column ascending">包装
                </th>
                <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1"
                    aria-label="Clicks: activate to sort column ascending">计费方式
                </th>
                <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1"
                    aria-label="Clicks: activate to sort column ascending">计费单价
                </th>
                <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1"
                    aria-label="Clicks: activate to sort column ascending">数量
                </th>
                <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1"
                    aria-label="Clicks: activate to sort column ascending">重量(kg)
                </th>
                <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1"
                    aria-label="Clicks: activate to sort column ascending">体积(m³)
                </th>
            </tr>
            </thead>
            <tbody>
            <#if ofcGoodsDetailsInfoList ?? && (ofcGoodsDetailsInfoList?size > 0) >
                <#list ofcGoodsDetailsInfoList as goods>
                <tr>
                    <td class="center">${(goods.goodsType)!"" }</td>
                    <td class="center">${(goods.goodsCategory)!"" }</td>
                    <td class="center">${(goods.goodsCode)!"" }</td>
                    <td class="center">${(goods.goodsName)!"" }</td>
                    <td class="center">${(goods.goodsSpec)!"" }</td>
                    <td class="center">${goods.unit!"" }</td>
                    <td class="center">
                        <#if (goods.pack)! == '01'>
                            纸箱
                        <#elseif (goods.pack)! == '02'>
                            木箱
                        <#elseif (goods.pack)! == '03'>
                            桶
                        <#elseif (goods.pack)! == '04'>
                            混包
                        <#elseif (goods.pack)! == '05'>
                            裸装
                        <#elseif (goods.pack)! == '06'>
                            编袋
                        <#elseif (goods.pack)! == '07'>
                            托盘
                        <#elseif (goods.pack)! == '08'>
                            木框架
                        <#elseif (goods.pack)! == '09'>
                            泡沫箱
                        <#elseif (goods.pack)! == '10'>
                            缠绕膜
                        <#elseif (goods.pack)! == '11'>
                            盘
                        <#elseif (goods.pack)! == '12'>
                            铁框
                        <#elseif (goods.pack)! == '13'>
                            布袋
                        <#else>
                        </#if>
                    </td>
                    <td class="center">
                        <#if (goods.chargingWays)! == '01'>
                            件数
                        <#elseif (goods.chargingWays)! == '02'>
                            重量Kg
                        <#elseif (goods.chargingWays)! == '03'>
                            体积m³
                        <#else>
                        </#if>
                    </td>
                    <td class="center">${goods.chargingUnitPrice!"" }</td>
                    <td class="center">${goods.quantity!"" }</td>
                    <td class="center">${goods.weight!"" }</td>
                    <td class="center">${goods.cubage!"" }</td>
                </tr>
                </#list>
            </#if>
            </tbody>
        </table>
        <div class="page-header">
            <p>
                订单跟踪信息
                <span id="hiddenOrderType" hidden="true"></span>
                <span id="hiddenBusinessType" hidden="true"></span>
            </p>
        </div>
        <table id="dynamic-table" class="table table-striped table-bordered table-hover dataTable no-footer" role="grid"
               aria-describedby="dynamic-table_info">
            <thead>
            <tr role="row">
                <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1"
                    aria-label="Clicks: activate to sort column ascending">订单状态
                </th>
            </thead>
            <tbody>
            <#if ofcOrderStatusList ?? && (ofcOrderStatusList?size > 0) >
                <#list ofcOrderStatusList as status>
                <tr>
                    <td>
                    ${status.notes!""}
                    </td>
                </tr>
                </#list>
            </#if>
            </tbody>
        </table>
    </form>
</div><!-- /.col -->

<script type="text/javascript">
    function facePrint() {
        var sel = "";
        var post = $("#REPORT").html();
        var url = post+"/OfcReport/ReportServer?reportlet=";
        var code="";
        var order_code=$("#orderCode").val();
        code = code+"facePrint.cpt&orderCode="+order_code;
        url=url+code;
        window.open(encodeURI(url));
    }
</script>
<script>
    $(function () {
        $("input[type='search']").attr("readonly", "readonly");
    })

    $(function () {
        var orderType = "${(ofcFundamentalInformation.orderType)!""}";
        $("#orderType").val(getOrderType(orderType));
        var businessType = "${(ofcFundamentalInformation.businessType)!""}"
        $("#businessType").val(getBusiType(businessType));
        var transportType = "${(ofcFundamentalInformation.transportType)!""}";
        $("#transportType").val(getTransportType(transportType));
        var expensePaymentParty = "${(ofcFinanceInformation.expensePaymentParty)!""}"
        $("#expensePaymentParty").val(getExpensePaymentParty(expensePaymentParty));
        var payment = "${(ofcFinanceInformation.payment)!""}";
        $("#payment").val(getPayment(payment));
        var orderStatus = "${(ofcOrderStatus.orderStatus)!""}";
        $("#orderStatus").val(getOrderStatus(orderStatus));
        var platformType = $("#platformType").val();
        $("#platformType").val(getPlatformType(platformType));
        var orderSorce = $("#orderSource").val();
        $("#orderSource").val(getOrderSource(orderSorce));
    });

    function getOrderSource(type) {
        if("6001" == type) {
            return "EDI";
        }
        return type;
    }
    function getPlatformType(type) {
        if(type == "4"){
            return "鲜易网";
        }
        return type;
    }

    function getOrderStatus(status) {
        var value ="";
        if(status == '10'){
            value = "待审核"
        }else if(status == '20'){
            value = "已审核"
        }else if(status == '30'){
            value = "执行中"
        }else if(status == '40'){
            value = "已完成"
        }else if(status == '50'){
            value = "已取消"
        }
        return value;
    }

    function getPayment(payment) {
        if (payment == "6810") {
            return "现金支付";
        } else if (payment == "6820") {
            return "POS刷卡";
        } else if (payment == "6830") {
            return "微信";
        } else if (payment == "6840") {
            return "支付宝";
        } else if (payment == "6850") {
            return "银行支付";
        } else if (payment == "6860") {
            return "账户结算";
        }
        return "";
    }

    function getExpensePaymentParty(type) {
        if (type == "10") {
            return "发货方";
        } else if (type == "20") {
            return "收货方";
        }
        return "";
    }

    function getBusiType(businessType) {
        var value = "";
        if (businessType == '600') {
            value = "城配"
        } else if (businessType == "601") {
            value = "干线";
        } else if (businessType == "602") {
            value = "卡班";
        } else if (businessType == "610") {
            value = "销售出库";
        } else if (businessType == "611") {
            value = "调拨出库";
        } else if (businessType == "612") {
            value = "报损出库";
        } else if (businessType == "613") {
            value = "其他出库";
        } else if (businessType == "620") {
            value = "采购入库";
        } else if (businessType == "621") {
            value = "调拨入库";
        } else if (businessType == "622") {
            value = "退货入库";
        } else if (businessType == "623") {
            value = "加工入库";
        }
        return value;
    }

    function getOrderType(orderType) {
        var value = "";
        if (orderType == "60") {
            value = "运输订单";
        } else if (orderType == "61") {
            value = "仓配订单";
        }
        return value;
    }

    function getTransportType(type) {
        var value = "";
        if (type == "10") {
            value = "零担"
        } else if (type == "20") {
            value = "整车";
        }
        return value;
    }

    $(function () {
        if($("#orderStatus").val()=="已取消"){
            $("#goBack").css('display','none');
        }
    })
</script>
</body>
