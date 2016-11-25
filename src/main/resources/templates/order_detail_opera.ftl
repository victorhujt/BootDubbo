<head>
    <title>订单详情</title>
</head>
<body class="no-skin">

<div class="col-xs-12">
    <div class="col-sm-6" style="float: right">
        <button class="btn btn-white btn-info btn-bold filters" id="goBack" value="" onclick="detailBackToHistory()">
            返回
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
                        <div class="col-xs-3">
                            <input id="orderCode" name="" type="search" placeholder=""
                                   aria-controls="dynamic-table" value="${(ofcFundamentalInformation.orderCode)!""}">
                        </div>
                        <label class="control-label col-label no-padding-right" for="name">客户订单编号</label>
                        <div class="col-xs-3">
                            <input id="custOrderCode" name="" type="search" placeholder=""
                                   aria-controls="dynamic-table"
                                   value="${(ofcFundamentalInformation.custOrderCode)!""}">
                        </div>
                        <label class="control-label col-label no-padding-right" for="name">订单批次号</label>
                        <div class="col-xs-3">
                            <input id="orderBatchNumber" name="" type="search" placeholder=""
                                   aria-controls="dynamic-table"
                                   value="${(ofcFundamentalInformation.orderBatchNumber)!""}">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-label no-padding-right" for="name">订单日期</label>
                        <div class="col-xs-3">
                            <input id="orderCode" name="" type="search" placeholder=""
                                   aria-controls="dynamic-table"
                                   value="${(ofcFundamentalInformation.orderTime?string("yyyy-MM-dd HH:mm:SS"))!""}">
                        </div>
                        <label class="control-label col-label no-padding-right" for="name">客户名称</label>
                        <div class="col-xs-3">
                            <input id="orderCode" name="" type="search" placeholder=""
                                   aria-controls="dynamic-table" value="${(ofcFundamentalInformation.custName)!""}">
                        </div>
                        <label class="control-label col-label no-padding-right" for="name">订单状态</label>
                        <div class="col-xs-3">
                            <input id="orderStatus" name="" type="search" placeholder=""
                                   aria-controls="dynamic-table" value="${(ofcFundamentalInformation.orderStatus)!""}">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-label no-padding-right" for="name">订单类型</label>
                        <div class="col-xs-3">
                            <input id="orderType" name="custName" type="search" placeholder=""
                                   aria-controls="dynamic-table" value="${(ofcFundamentalInformation.orderType)!""}">
                        </div>
                        <label class="control-label col-label no-padding-right" for="name">业务类型</label>
                        <div class="col-xs-3">
                            <input id="businessType" name="custName" type="search" placeholder=""
                                   aria-controls="dynamic-table" value="${(ofcFundamentalInformation.businessType)!""}">
                        </div>
                        <label class="control-label col-label no-padding-right" for="name">运输类型</label>
                        <div class="col-xs-3">
                            <input id="transportType" name="custName" type="search" placeholder=""
                                   aria-controls="dynamic-table"
                                   value="${(ofcFundamentalInformation.transportType)!""}">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-label no-padding-right" for="name">渠道</label>
                        <div class="col-xs-3">
                            <input id="orderCode" name="custName" type="search" placeholder=""
                                   aria-controls="dynamic-table">
                        </div>
                        <label class="control-label col-label no-padding-right" for="name">平台类型</label>
                        <div class="col-xs-3">
                            <input id="platformType" name="custName" type="search" placeholder=""
                                   aria-controls="dynamic-table" value="${(ofcFundamentalInformation.platformType)!""}">
                        </div>
                        <label class="control-label col-label no-padding-right" for="name">订单来源</label>
                        <div class="col-xs-3">
                            <input id="orderSource" name="custName" type="search" placeholder=""
                                   aria-controls="dynamic-table" value="${(ofcFundamentalInformation.orderSource)!""}">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-label no-padding-right" for="name">服务产品</label>
                        <div class="col-xs-3">
                            <input id="productName" name="custName" type="search" placeholder=""
                                   aria-controls="dynamic-table" value="${(ofcFundamentalInformation.productName)!""}">
                        </div>
                        <label class="control-label col-label no-padding-right" for="name">开单员</label>
                        <div class="col-xs-3">
                            <input id="merchandiser" name="custName" type="search" placeholder=""
                                   aria-controls="dynamic-table" value="${(ofcFundamentalInformation.merchandiser)!""}">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-label no-padding-right" for="name">备注</label>
                        <div class="col-xs-6">
                            <input id="notes" name="custName" type="search" placeholder=""
                                   aria-controls="dynamic-table" value="${(ofcFundamentalInformation.notes)!""}">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-label no-padding-right" for="name">创建日期</label>
                        <div class="col-xs-3">
                            <input id="creationTime" name="custName" type="search" placeholder=""
                                   aria-controls="dynamic-table"
                                   value="${(ofcFundamentalInformation.creationTime?string("yyyy-MM-dd HH:mm:SS"))!""}">
                        </div>
                        <label class="control-label col-label no-padding-right" for="name">创建人员</label>
                        <div class="col-xs-3">
                            <input id="creator" name="custName" type="search" placeholder=""
                                   aria-controls="dynamic-table" value="${(ofcFundamentalInformation.creator)!""}">
                        </div>
                        <label class="control-label col-label no-padding-right" for="name">完成日期</label>
                        <div class="col-xs-3">
                            <input id="finishedTime" name="custName" type="search" placeholder=""
                                   aria-controls="dynamic-table"
                                   value="${(ofcFundamentalInformation.finishedTime?string("yyyy-MM-dd HH:mm:SS"))!""}">
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
                        <div class="col-xs-3">
                            <input id="transCode" name="" type="search" placeholder=""
                                   aria-controls="dynamic-table" value="${(ofcDistributionBasicInfo.transCode)!""}">
                        </div>
                        <label class="control-label col-label no-padding-right" for="name">出发地</label>
                        <div class="col-xs-3">
                            <input id="departurePlace" name="" type="search" placeholder=""
                                   aria-controls="dynamic-table"
                                   value="${(ofcDistributionBasicInfo.departurePlace)!""}">
                        </div>
                        <label class="control-label col-label no-padding-right" for="name">目的地</label>
                        <div class="col-xs-3">
                            <input id="destination" name="" type="search" placeholder=""
                                   aria-controls="dynamic-table" value="${(ofcDistributionBasicInfo.destination)!""}">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-label no-padding-right" for="name">车牌号</label>
                        <div class="col-xs-3">
                            <input id="plateNumber" name="plateNumber" type="search" placeholder=""
                                   aria-controls="dynamic-table" value="${(ofcDistributionBasicInfo.plateNumber)!""}">
                        </div>
                        <label class="control-label col-label no-padding-right" for="name">司机姓名</label>
                        <div class="col-xs-3">
                            <input id="driverName" name="driverName" type="search" placeholder=""
                                   aria-controls="dynamic-table" value="${(ofcDistributionBasicInfo.driverName)!""}">
                        </div>
                        <label class="control-label col-label no-padding-right" for="name">联系电话</label>
                        <div class="col-xs-3">
                            <input id="contactNumber" name="contactNumber" type="search" placeholder=""
                                   aria-controls="dynamic-table" value="${(ofcDistributionBasicInfo.contactNumber)!""}">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-label no-padding-right" for="name">数量</label>
                        <div class="col-xs-3">
                            <input id="quantity" name="quantity" type="search" placeholder=""
                                   aria-controls="dynamic-table" value="${(ofcDistributionBasicInfo.quantity)!""}">
                        </div>
                        <label class="control-label col-label no-padding-right" for="name">重量</label>
                        <div class="col-xs-3">
                            <input id="weight" name="weight" type="search" placeholder=""
                                   aria-controls="dynamic-table" value="${(ofcDistributionBasicInfo.weight)!""}">KG
                        </div>
                        <label class="control-label col-label no-padding-right" for="name">体积</label>
                        <div class="col-xs-3">
                            <input id="cubage" name="cubage" type="search" placeholder=""
                                   aria-controls="dynamic-table" value="${(ofcDistributionBasicInfo.cubage)!""}">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-label no-padding-right" for="name">运输要求</label>
                        <div class="col-xs-6">
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
                        <div class="col-xs-3">
                            <input id="consignorName" name="consignorName" type="search" placeholder=""
                                   aria-controls="dynamic-table" value="${(ofcDistributionBasicInfo.consignorName)!""}">
                        </div>
                        <label class="control-label col-label no-padding-right" for="name">联系人</label>
                        <div class="col-xs-3">
                            <input id="consignorContactName" name="consignorContactName" type="search" placeholder=""
                                   aria-controls="dynamic-table"
                                   value="${(ofcDistributionBasicInfo.consigneeContactName)!""}">
                        </div>
                        <label class="control-label col-label no-padding-right" for="name">联系电话</label>
                        <div class="col-xs-3">
                            <input id="consignorContactPhone" name="consignorContactPhone" type="search" placeholder=""
                                   aria-controls="dynamic-table"
                                   value="${(ofcDistributionBasicInfo.consignorContactPhone)!""}">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-label no-padding-right" for="name">传真</label>
                        <div class="col-xs-3">
                            <input id="" name="" type="search" placeholder=""
                                   aria-controls="dynamic-table">
                        </div>
                        <label class="control-label col-label no-padding-right" for="name">Email</label>
                        <div class="col-xs-3">
                            <input id="" name="" type="search" placeholder=""
                                   aria-controls="dynamic-table">
                        </div>
                        <label class="control-label col-label no-padding-right" for="name">邮编</label>
                        <div class="col-xs-3">
                            <input id="" name="" type="search" placeholder=""
                                   aria-controls="dynamic-table">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-label no-padding-right" for="name">地址</label>
                        <div class="col-xs-6">
                            <input id="orderCode" name="custName" type="search" placeholder=""
                                   aria-controls="dynamic-table"
                                   value="${(ofcDistributionBasicInfo.departureProvince)!""+(ofcDistributionBasicInfo.departureCity)!""+(ofcDistributionBasicInfo.departureDistrict)!""+(ofcDistributionBasicInfo.departureTowns)!""+(ofcDistributionBasicInfo.departurePlace)!"" }">
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
                        <div class="col-xs-3">
                            <input id="orderCode" name="custName" type="search" placeholder=""
                                   aria-controls="dynamic-table" value="${(ofcDistributionBasicInfo.consigneeName)!""}">
                        </div>
                        <label class="control-label col-label no-padding-right" for="name">联系人</label>
                        <div class="col-xs-3">
                            <input id="consigneeContactName" name="consigneeContactName" type="search" placeholder=""
                                   aria-controls="dynamic-table"
                                   value="${(ofcDistributionBasicInfo.consigneeContactName)!""}">
                        </div>
                        <label class="control-label col-label no-padding-right" for="name">联系电话</label>
                        <div class="col-xs-3">
                            <input id="consigneeContactPhone" name="consigneeContactPhone" type="search" placeholder=""
                                   aria-controls="dynamic-table"
                                   value="${(ofcDistributionBasicInfo.consigneeContactPhone)!""}">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-label no-padding-right" for="name">传真</label>
                        <div class="col-xs-3">
                            <input id="orderCode" name="custName" type="search" placeholder=""
                                   aria-controls="dynamic-table">
                        </div>
                        <label class="control-label col-label no-padding-right" for="name">Email</label>
                        <div class="col-xs-3">
                            <input id="orderCode" name="custName" type="search" placeholder=""
                                   aria-controls="dynamic-table">
                        </div>
                        <label class="control-label col-label no-padding-right" for="name">邮编</label>
                        <div class="col-xs-3">
                            <input id="orderCode" name="custName" type="search" placeholder=""
                                   aria-controls="dynamic-table">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-label no-padding-right" for="name">地址</label>
                        <div class="col-xs-6">
                            <input id="address" name="address" type="search" placeholder=""
                                   aria-controls="dynamic-table"
                                   value="${(ofcDistributionBasicInfo.destinationProvince)!""+(ofcDistributionBasicInfo.destinationCity)!""+(ofcDistributionBasicInfo.destinationDistrict)!""+(ofcDistributionBasicInfo.destinationTowns)!""+(ofcDistributionBasicInfo.destinationPlace)!"" }">
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
                        <div class="col-xs-3">
                            <input id="orderCode" name="custName" type="search" placeholder=""
                                   aria-controls="dynamic-table">
                        </div>
                        <label class="control-label col-label no-padding-right" for="name">费用总计</label>
                        <div class="col-xs-3">
                            <input id="orderCode" name="custName" type="search" placeholder=""
                                   aria-controls="dynamic-table">
                        </div>
                        <label class="control-label col-label no-padding-right" for="name">费用支付方</label>
                        <div class="col-xs-3">
                            <input id="orderCode" name="custName" type="search" placeholder=""
                                   aria-controls="dynamic-table">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-label no-padding-right" for="name">支付方式</label>
                        <div class="col-xs-3">
                            <input id="orderCode" name="custName" type="search" placeholder=""
                                   aria-controls="dynamic-table">
                        </div>
                        <label class="control-label col-label no-padding-right" for="name">现结</label>
                        <div class="col-xs-3">
                            <input id="orderCode" name="custName" type="search" placeholder=""
                                   aria-controls="dynamic-table">
                        </div>
                        <label class="control-label col-label no-padding-right" for="name">到付</label>
                        <div class="col-xs-3">
                            <input id="orderCode" name="custName" type="search" placeholder=""
                                   aria-controls="dynamic-table">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-label no-padding-right" for="name">回付</label>
                        <div class="col-xs-3">
                            <input id="orderCode" name="custName" type="search" placeholder=""
                                   aria-controls="dynamic-table">
                        </div>
                        <label class="control-label col-label no-padding-right" for="name">月结</label>
                        <div class="col-xs-3">
                            <input id="orderCode" name="custName" type="search" placeholder=""
                                   aria-controls="dynamic-table">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-label no-padding-right" for="name">是否上门提货</label>
                        <div class="col-xs-3">
                            <input id="orderCode" name="custName" type="search" placeholder=""
                                   aria-controls="dynamic-table">
                        </div>
                        <label class="control-label col-label no-padding-right" for="name">上门提货费用</label>
                        <div class="col-xs-3">
                            <input id="orderCode" name="custName" type="search" placeholder=""
                                   aria-controls="dynamic-table">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-label no-padding-right" for="name">是否二次配送</label>
                        <div class="col-xs-3">
                            <input id="orderCode" name="custName" type="search" placeholder=""
                                   aria-controls="dynamic-table">
                        </div>
                        <label class="control-label col-label no-padding-right" for="name">二次配送费用</label>
                        <div class="col-xs-3">
                            <input id="orderCode" name="custName" type="search" placeholder=""
                                   aria-controls="dynamic-table">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-label no-padding-right" for="name">是否签单返回</label>
                        <div class="col-xs-3">
                            <input id="orderCode" name="custName" type="search" placeholder=""
                                   aria-controls="dynamic-table">
                        </div>
                        <label class="control-label col-label no-padding-right" for="name">签单返回费用</label>
                        <div class="col-xs-3">
                            <input id="orderCode" name="custName" type="search" placeholder=""
                                   aria-controls="dynamic-table">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-label no-padding-right" for="name">是否货物保险</label>
                        <div class="col-xs-3">
                            <input id="orderCode" name="custName" type="search" placeholder=""
                                   aria-controls="dynamic-table">
                        </div>
                        <label class="control-label col-label no-padding-right" for="name">保险费用</label>
                        <div class="col-xs-3">
                            <input id="orderCode" name="custName" type="search" placeholder=""
                                   aria-controls="dynamic-table">
                        </div>
                        <label class="control-label col-label no-padding-right" for="name">声明价值</label>
                        <div class="col-xs-3">
                            <input id="orderCode" name="custName" type="search" placeholder=""
                                   aria-controls="dynamic-table">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-label no-padding-right" for="name">是否代收货款</label>
                        <div class="col-xs-3">
                            <input id="orderCode" name="custName" type="search" placeholder=""
                                   aria-controls="dynamic-table" value="">
                        </div>
                        <label class="control-label col-label no-padding-right" for="name">代收费用</label>
                        <div class="col-xs-3">
                            <input id="orderCode" name="custName" type="search" placeholder=""
                                   aria-controls="dynamic-table" value="0.00">
                        </div>
                        <label class="control-label col-label no-padding-right" for="name">代收金额</label>
                        <div class="col-xs-3">
                            <input id="orderCode" name="custName" type="search" placeholder=""
                                   aria-controls="dynamic-table"
                                   value="${(ofcFinanceInformation.collectLoanAmount)!""}">
                        </div>
                    </div>
                </form>
            </div>
        </div>

        <div class="page-header">
            <p>
                相关计划单列表
            </p>
        </div>
        <table id="dynamic-table" class="table table-striped table-bordered table-hover dataTable no-footer" role="grid"
               aria-describedby="dynamic-table_info">
            <thead>
            <tr role="row">
                <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1"
                    aria-label="Clicks: activate to sort column ascending">序号
                </th>
                <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1"
                    aria-label="Clicks: activate to sort column ascending">计划单编号
                </th>
                <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1"
                    aria-label="Clicks: activate to sort column ascending">类型
                </th>
                <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1"
                    aria-label="Clicks: activate to sort column ascending">业务类型
                </th>
                <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1"
                    aria-label="Clicks: activate to sort column ascending">资源分配状态
                </th>
                <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1"
                    aria-label="Clicks: activate to sort column ascending">服务商名称
                </th>
                <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1"
                    aria-label="Clicks: activate to sort column ascending">联系人
                </th>
                <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1"
                    aria-label="Clicks: activate to sort column ascending">联系电话
                </th>
                <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1"
                    aria-label="Clicks: activate to sort column ascending">计划单状态
                </th>
                <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1"
                    aria-label="Clicks: activate to sort column ascending">出发地
                </th>
                <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1"
                    aria-label="Clicks: activate to sort column ascending">目的地
                </th>
                <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1"
                    aria-label="Clicks: activate to sort column ascending">仓库名称
                </th>
                <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1"
                    aria-label="Clicks: activate to sort column ascending">完成时间
                </th>
            </thead>
            <tbody>
            <#if storageList ?? && (storageList?size > 0) >
                <#list storageList as stroage>
                <tr>
                    <td>
                    ${stroage_index+1}
                    </td>
                    <td>
                    ${stroage.planCode!""}
                    </td>
                    <td>
                    ${stroage.type!""}
                    </td>
                    <td>
                    ${stroage.businessType!""}
                    </td>
                    <td>
                    ${stroage.resourceAllocationStatus!""}
                    </td>
                    <td>
                    ${stroage.serviceProviderName!""}
                    </td>
                    <td>
                    ${stroage.serviceProviderContact!""}
                    </td>
                    <td>
                    ${stroage.serviceProviderContactPhone!""}
                    </td>
                    <td>
                    ${stroage.plannedSingleState!""}
                    </td>
                    <td>
                    ${stroage.departure!""}
                    </td>
                    <td>
                    ${stroage.destination!""}
                    </td>
                    <td>
                    ${stroage.warehouseName!""}
                    </td>
                    <td>
                    ${stroage.finishedTime?string("yyyy-MM-dd HH:mm:ss")!""}
                    </td>
                </tr>
                </#list>
            </#if>
            </tbody>
        </table>

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
                    aria-label="Clicks: activate to sort column ascending">序号
                </th>
                <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1"
                    aria-label="Clicks: activate to sort column ascending">货品编码
                </th>
                <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1"
                    aria-label="Clicks: activate to sort column ascending">货品名称
                </th>
                <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1"
                    aria-label="Clicks: activate to sort column ascending">货品规格
                </th>
                <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1"
                    aria-label="Clicks: activate to sort column ascending">单位
                </th>
                <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1"
                    aria-label="Clicks: activate to sort column ascending">生产批次
                </th>
                <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1"
                    aria-label="Clicks: activate to sort column ascending">生产日期
                </th>
                <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1"
                    aria-label="Clicks: activate to sort column ascending">失效日期
                </th>
                <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1"
                    aria-label="Clicks: activate to sort column ascending">数量
                </th>
            </thead>
            <tbody>
            <#if ofcGoodsDetailsInfoList ?? && (ofcGoodsDetailsInfoList?size > 0) >
                <#list ofcGoodsDetailsInfoList as goods>
                <tr>
                    <td>${(goods_index + 1)!"" }</td>
                    <td>${(goods.goodsCode)!"" }</td>
                    <td>${(goods.goodsName)!"" }</td>
                    <td>${(goods.goodsSpec)!"" }</td>
                    <td>${goods.unit!"" }</td>
                    <td>${goods.productionBatch!"" }</td>
                    <td>${(goods.productionTime?string("yyyy-MM-dd HH:mm:SS"))!"" }</td>
                    <td>${(goods.invalidTime?string("yyyy-MM-dd HH:mm:SS"))!"" }</td>
                    <td>${goods.quantity!"" }</td>
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
    function detailBackToHistory() {
            xescm.common.loadPage("/ofc/orderManageOpera");
    }
</script>
</body>
