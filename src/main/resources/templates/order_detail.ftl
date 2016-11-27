<head>
    <title>订单详情</title>
</head>
<body class="no-skin">

                    <div class="col-xs-12">
                        <div class="col-sm-6" style="float: right">
                            <button class="btn btn-white btn-info btn-bold filters" style="float: right" id="goBack" value="${(historyUrl)!}" onclick="detailBackToHistory()">
                                返回
                            </button>
                        </div>
                        <form id="" method="post" class="form-horizontal" role="form" >
                            <div class="page-header">
                                <p>
                                    订单详情
                                </p>
                            </div>
                            <div class="tabbable">
                                <ul class="nav nav-tabs" id="myTab">
                                    <li class="active">
                                        <a data-toggle="tab" href="#home">
                                            基本信息
                                        </a>
                                    </li>

                                    <li class="tramessages">
                                        <a data-toggle="tab" href="#tramessages">
                                            配送信息
                                        </a>
                                    </li>

                                    <li class="dropdown">
                                        <a data-toggle="tab" href="#dropdown">
                                            收发货方信息
                                        </a>
                                    </li>

                                    <li class="warmessages">
                                        <a data-toggle="tab" href="#warmessages">
                                            仓储信息
                                        </a>
                                    </li>

                                    <li class="supportMessages">
                                        <a data-toggle="tab" href="#supportMessages">
                                            供应商信息
                                        </a>
                                    </li>
                                </ul>

                                <div class="tab-content">
                                    <div id="home" class="tab-pane fade in active">
                                        <div class="form-group">
                                            <label class="control-label col-label no-padding-right" for="name">订单日期</label>
                                            <div class="w-width-220 y-float">
                                                <div class="clearfix">
                                                    <input id="orderTime" value = "${(ofcOrderDTO.orderTime?string("yyyy-MM-dd HH:mm:ss"))!""}" readonly="readonly" style="color: #000" name="" type="text" class="form-control input-sm" placeholder="" aria-controls="dynamic-table">
                                                </div>
                                            </div>
                                            <label class="control-label col-label no-padding-right" for="name">订单编号</label>
                                            <div class="w-width-220 y-float">
                                                <div class="clearfix">
                                                    <input id="orderCode" value = "${(ofcOrderDTO.orderCode)!""}" readonly="readonly" style="color: #000" name="" type="text" class="form-control input-sm" placeholder="" aria-controls="dynamic-table">
                                                </div>
                                            </div>
                                            <label class="control-label col-label no-padding-right" for="name">客户订单编号</label>
                                            <div class="w-width-220 y-float">
                                                <div class="clearfix">
                                                    <input id="custOrderCode" value = "${(ofcOrderDTO.custOrderCode)!""}" readonly="readonly" style="color: #000" name="" type="text" class="form-control input-sm" placeholder="" aria-controls="dynamic-table">
                                                </div>
                                            </div>
                                        </div>

                                        <div class="form-group">
                                            <label class="control-label col-label no-padding-right" for="name">订单状态</label>
                                            <div class="w-width-220 y-float" style="height:34px;">

                                            <#if (ofcOrderDTO.orderStatus)! == '10'>
                                                <input id="" value = "待审核" readonly="readonly"  class="form-control input-sm"  type="text" >
                                            <#elseif (ofcOrderDTO.orderStatus)! == '20'>
                                                <input id="" value = "已审核" readonly="readonly"  class="form-control input-sm"  type="text" >
                                            <#elseif (ofcOrderDTO.orderStatus)! == '30'>
                                                <input id="" value = "已审核" readonly="readonly"  class="form-control input-sm"  type="text" >
                                            <#elseif (ofcOrderDTO.orderStatus)! == '40'>
                                                <input id="" value = "已完成" readonly="readonly"  class="form-control input-sm"  type="text" >
                                            <#elseif (ofcOrderDTO.orderStatus)! == '50'>
                                                <input id="" value = "已取消" readonly="readonly"  class="form-control input-sm"  type="text" >
                                            <#else>
                                                <input type="text" readonly="readonly" class="form-control input-sm" />
                                            </#if>
                                               <#-- <div class="w-width-220 y-float">-->
                                                <#--<#if (ofcOrderDTO.orderStatus)! ??><#if (ofcOrderDTO.orderStatus)! == '10'><input id="" value = "待审核" readonly="readonly" style="color: #000" class="form-control input-sm"  type="text" ></#if></#if>
                                                <#if (ofcOrderDTO.orderStatus)! ??><#if (ofcOrderDTO.orderStatus)! == '20'><input id="" value = "已审核" readonly="readonly" style="color: #000" class="form-control input-sm"  type="text" ></#if></#if>
                                                <#if (ofcOrderDTO.orderStatus)! ??><#if (ofcOrderDTO.orderStatus)! == '30'><input id="" value = "已审核" readonly="readonly" style="color: #000" class="form-control input-sm"  type="text" ></#if></#if>
                                                <#if (ofcOrderDTO.orderStatus)! ??><#if (ofcOrderDTO.orderStatus)! == '40'><input id="" value = "已完成" readonly="readonly" style="color: #000" class="form-control input-sm"  type="text" ></#if></#if>
                                                <#if (ofcOrderDTO.orderStatus)! ??><#if (ofcOrderDTO.orderStatus)! == '50'><input id="" value = "已取消" readonly="readonly" style="color: #000" class="form-control input-sm"  type="text" ></#if></#if>-->
                                              <#--  </div>-->
                                            </div>
                                            <label class="control-label col-label no-padding-right" for="name">订单类型</label>
                                            <div class="w-width-220 y-float">
                                                <div class="clearfix">
                                                <#if ofcOrderDTO.orderType ??><#if ofcOrderDTO.orderType == '60'><input id="" value = "运输订单" readonly="readonly" style="color: #000" class="form-control input-sm"  type="text" ></#if></#if>
                                                <#if ofcOrderDTO.orderType ??><#if ofcOrderDTO.orderType == '61'><input id="" value = "仓配订单" readonly="readonly" style="color: #000" class="form-control input-sm"  type="text" ></#if></#if>
                                                </div>
                                            </div>
                                            <label class="control-label col-label no-padding-right" for="name">业务类型</label>
                                            <div class="w-width-220 y-float">
                                                <div class="clearfix">
                                                <#if ofcOrderDTO.businessType ??><#if ofcOrderDTO.businessType == '600'><input id="" class="form-control input-sm" value = "城配" readonly="readonly" style="color: #000"  type="text" ></#if></#if>
                                                <#if ofcOrderDTO.businessType ??><#if ofcOrderDTO.businessType == '601'><input id="" class="form-control input-sm" value = "干线" readonly="readonly" style="color: #000"  type="text" ></#if></#if>
                                                <#if ofcOrderDTO.businessType ??><#if ofcOrderDTO.businessType == '610'><input id="" class="form-control input-sm" value = "销售出库" readonly="readonly" style="color: #000"  type="text" ></#if></#if>
                                                <#if ofcOrderDTO.businessType ??><#if ofcOrderDTO.businessType == '611'><input id="" class="form-control input-sm" value = "调拨出库" readonly="readonly" style="color: #000"  type="text" ></#if></#if>
                                                <#if ofcOrderDTO.businessType ??><#if ofcOrderDTO.businessType == '612'><input id="" class="form-control input-sm" value = "报损出库" readonly="readonly" style="color: #000"  type="text" ></#if></#if>
                                                <#if ofcOrderDTO.businessType ??><#if ofcOrderDTO.businessType == '613'><input id="" class="form-control input-sm" value = "其他出库" readonly="readonly" style="color: #000"  type="text" ></#if></#if>
                                                <#if ofcOrderDTO.businessType ??><#if ofcOrderDTO.businessType == '620'><input id="" class="form-control input-sm" value = "采购入库" readonly="readonly" style="color: #000"  type="text" ></#if></#if>
                                                <#if ofcOrderDTO.businessType ??><#if ofcOrderDTO.businessType == '621'><input id="" class="form-control input-sm" value = "调拨入库" readonly="readonly" style="color: #000"  type="text" ></#if></#if>
                                                <#if ofcOrderDTO.businessType ??><#if ofcOrderDTO.businessType == '622'><input id="" class="form-control input-sm" value = "退货入库" readonly="readonly" style="color: #000"  type="text" ></#if></#if>
                                                <#if ofcOrderDTO.businessType ??><#if ofcOrderDTO.businessType == '623'><input id="" class="form-control input-sm" value = "加工入库" readonly="readonly" style="color: #000"  type="text" ></#if></#if>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="control-label col-label no-padding-right" for="name">店铺</label>
                                            <div class="w-width-220 y-float">
                                                <div class="clearfix">
                                                    <input id="storeName" value = "${(ofcOrderDTO.storeName)!""}" readonly="readonly" style="color: #000" name="" type="text" class="form-control input-sm" placeholder="" aria-controls="dynamic-table">
                                                </div>
                                            </div>
                                            <label class="control-label col-label no-padding-right" for="name">平台类型</label>
                                            <div class="w-width-220 y-float" style="height:34px;">
                                                <div class="clearfix">

                                                <#if (ofcOrderDTO.platformType)! == '1'>
                                                    <input id="platformType" value = "线下" readonly="readonly" style="color: #000" name="" type="text" class="form-control input-sm" placeholder="" aria-controls="dynamic-table">
                                                <#elseif (ofcOrderDTO.platformType)! == '2'>
                                                    <input id="platformType" value = "天猫" readonly="readonly" style="color: #000" name="" type="text" class="form-control input-sm" placeholder="" aria-controls="dynamic-table">
                                                <#elseif (ofcOrderDTO.platformType)! == '3'>
                                                    <input id="platformType" value = "京东" readonly="readonly" style="color: #000" name="" type="text" class="form-control input-sm" placeholder="" aria-controls="dynamic-table">
                                                <#elseif (ofcOrderDTO.platformType)! == '4'>
                                                    <input id="platformType" value = "鲜易网" readonly="readonly" style="color: #000" name="" type="text" class="form-control input-sm" placeholder="" aria-controls="dynamic-table">
                                                <#else>
                                                    <input type="text" readonly="readonly" class="form-control input-sm" />
                                                </#if>

                                                </div>
                                            </div>
                                            <label class="control-label col-label no-padding-right" for="name">服务产品</label>
                                            <div class="w-width-220 y-float">
                                                <div class="clearfix">
                                                    <input id="productName" value = "${(ofcOrderDTO.productName)!""}" readonly="readonly" style="color: #000" name="" type="text" class="form-control input-sm" placeholder="" aria-controls="dynamic-table">
                                                </div>
                                            </div>
                                        </div>

                                        <div class="form-group">
                                            <label class="control-label col-label no-padding-right" for="name">订单来源</label>
                                            <div class="w-width-220 y-float">
                                                <div class="clearfix">
                                                    <input id="orderSource" value = "${(ofcOrderDTO.orderSource)!""}" readonly="readonly" style="color: #000" name="" type="text" class="form-control input-sm" placeholder="" aria-controls="dynamic-table">
                                                </div>
                                            </div>
                                            <label class="control-label col-label no-padding-right" for="name">备注</label>
                                            <div class="y-float" style="width:517px; margin-left:12px;">
                                                <div class="clearfix">
                                                    <input id="notes" value = "${(ofcOrderDTO.notes)!""}" readonly="readonly" style="color: #000" name="" type="text" class="form-control input-sm" placeholder="" aria-controls="dynamic-table">
                                                </div>
                                            </div>
                                        </div>

                                        <div class="form-group">
                                            <label class="control-label col-label no-padding-right" for="name">创建日期</label>
                                            <div class="w-width-220 y-float">
                                                <div class="clearfix">
                                                    <input id="creationTime" value = "${(ofcOrderDTO.creationTime?string("yyyy-MM-dd HH:mm:ss"))!""}" readonly="readonly" style="color: #000" name="" type="text" class="form-control input-sm" placeholder="" aria-controls="dynamic-table">
                                                </div>
                                            </div>
                                            <label class="control-label col-label no-padding-right" for="name">创建人员</label>
                                            <div class="w-width-220 y-float">
                                                <div class="clearfix">
                                                    <input id="" value = "${(ofcOrderDTO.creatorName)!""}" readonly="readonly" style="color: #000" name="" type="text" class="form-control input-sm" placeholder="" aria-controls="dynamic-table">
                                                </div>
                                            </div>
                                            <label class="control-label col-label no-padding-right" for="name">完成日期</label>
                                            <div class="w-width-220 y-float">
                                                <div class="clearfix">
                                                    <input id="" value = "${((ofcOrderDTO.finishedTime)?string('yyyy-MM-dd HH:mm:ss'))!""}" readonly="readonly" style="color: #000" name="" type="text" class="form-control input-sm" placeholder="" aria-controls="dynamic-table">
                                                </div>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="control-label col-label no-padding-right" for="name">取消日期</label>
                                            <div class="w-width-220 y-float">
                                                <div class="clearfix">
                                                    <input id="" value = "${((ofcOrderDTO.abolishTime)?string('yyyy-MM-dd HH:mm:ss'))!""}" readonly="readonly" style="color: #000" name="" type="text" class="form-control input-sm" placeholder="" aria-controls="dynamic-table">
                                                </div>
                                            </div>
                                            <label class="control-label col-label no-padding-right" for="name">取消人</label>
                                            <div class="w-width-220 y-float">
                                                <div class="clearfix">
                                                    <input id="" value = "${(ofcOrderDTO.abolisherName)!""}" readonly="readonly" style="color: #000" name="" type="text" class="form-control input-sm" placeholder="" aria-controls="dynamic-table">
                                                </div>
                                            </div>
                                        </div>

                                    </div>

                                    <div id="tramessages" class="tab-pane fade">
                                        <#--<div class="form-group">
                                            <label class="control-label col-label no-padding-right" for="name">货品类型</label>
                                            <div class="w-width-220 y-float">
                                                <div class="clearfix">
                                                    <input id="goodsType" value = "${(ofcOrderDTO.goodsType)!""}" readonly="readonly" style="color: #000" name="" type="text" class="form-control input-sm" placeholder="" aria-controls="dynamic-table">
                                                </div>
                                            </div>
                                        </div>-->
                                        <div class="form-group">
                                            <label class="control-label col-label no-padding-right" for="name">是否加急</label>
                                            <div class="w-width-220 y-float" style="height:34px;">
                                                <div class="clearfix">

                                                    <#if (ofcOrderDTO.urgent) ??>
                                                        <#if (ofcOrderDTO.urgent)! == 1>
                                                            <input id="urgent" value = "加急" readonly="readonly" style="color: #000" name="" type="text" class="form-control input-sm" placeholder="" aria-controls="dynamic-table">
                                                        <#elseif (ofcOrderDTO.urgent)! == 0>
                                                            <input id="urgent" value = "不加急" readonly="readonly" style="color: #000" name="" type="text" class="form-control input-sm" placeholder="" aria-controls="dynamic-table">
                                                        <#else>
                                                            <input type="text" readonly="readonly" class="form-control input-sm" />
                                                        </#if>
                                                    </#if>

                                                </div>
                                            </div>
                                            <label class="control-label col-label no-padding-right" for="name">出发地</label>
                                            <div class="w-width-220 y-float">
                                                <div class="clearfix">
                                                    <input id="departurePlace" value = "${((ofcOrderDTO.departureProvince)?replace('null',''))!""}${((ofcOrderDTO.departureCity)?replace('null',''))!""}${((ofcOrderDTO.departureDistrict)!"")?replace('null','')}${((ofcOrderDTO.departureTowns)?replace('null',''))!""}${((ofcOrderDTO.departurePlace)?replace('null',''))!""}" readonly="readonly" style="color: #000" name="" type="text" class="form-control input-sm" placeholder="" aria-controls="dynamic-table">
                                                </div>
                                            </div>
                                            <label class="control-label col-label no-padding-right" for="name">目的地</label>
                                            <div class="w-width-220 y-float">
                                                <div class="clearfix">
                                                    <input id="destination" value = "${((ofcOrderDTO.destinationProvince)?replace('null',''))!""}${((ofcOrderDTO.destinationCity)?replace('null',''))!""}${(ofcOrderDTO.destinationDistrict)!""}${((ofcOrderDTO.destinationTowns)?replace('null',''))!""}${((ofcOrderDTO.destination)?replace('null',''))!""}" readonly="readonly" style="color: #000" name="" type="text" class="form-control input-sm" placeholder="" aria-controls="dynamic-table">
                                                </div>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="control-label col-label no-padding-right" for="name">数量</label>
                                            <div class="w-width-220 y-float">
                                                <div class="clearfix">
                                                    <input id="quantity" value = "${((ofcOrderDTO.quantity)?replace(',',''))!""}" readonly="readonly" style="color: #000" name="" type="text" class="form-control input-sm" placeholder="" aria-controls="dynamic-table">
                                                </div>
                                            </div>
                                            <label class="control-label col-label no-padding-right" for="name">重量</label>
                                            <div class="w-width-220 y-float">
                                                <div class="clearfix">
                                                    <input id="weight" value = "${((ofcOrderDTO.weight)?replace(',',''))!""}" readonly="readonly" style="color: #000" name="" type="text" class="form-control input-sm" placeholder="" aria-controls="dynamic-table">
                                                </div>
                                            </div>
                                            <label class="control-label col-label no-padding-right" for="name">体积</label>
                                            <div class="w-width-220 y-float">
                                                <div class="clearfix">
                                                    <input id="cubage" value = "${(ofcOrderDTO.cubage)!""}" readonly="readonly" style="color: #000" name="" type="text" class="form-control input-sm" placeholder="" aria-controls="dynamic-table">
                                                </div>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="control-label col-label no-padding-right" for="name">运输要求</label>
                                            <div class="w-width-220 y-float">
                                                <div class="clearfix">
                                                    <input id="transRequire" value = "${(ofcOrderDTO.transRequire)!""}" readonly="readonly" style="color: #000" name="" type="text" class="form-control input-sm" placeholder="" aria-controls="dynamic-table">
                                                </div>
                                            </div>
                                            <label class="control-label col-label no-padding-right" for="name">取货时间</label>
                                            <div class="w-width-220 y-float">
                                                <div class="clearfix">
                                                    <input id="pickupTime" value = "${(ofcOrderDTO.pickupTime?string("yyyy-MM-dd HH:mm"))!""}" readonly="readonly" style="color: #000" name="" type="text" class="form-control input-sm" placeholder="" aria-controls="dynamic-table">
                                                </div>
                                            </div>
                                            <label class="control-label col-label no-padding-right" for="name">运输单号</label>
                                            <div class="w-width-220 y-float">
                                                <div class="clearfix">
                                                    <input id="transCode" value = "${(ofcOrderDTO.transCode)!""}" readonly="readonly" style="color: #000" name="" type="text" class="form-control input-sm" placeholder="" aria-controls="dynamic-table">
                                                </div>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="control-label col-label no-padding-right" for="name">车牌号</label>
                                            <div class="w-width-220 y-float">
                                                <div class="clearfix">
                                                    <input id="orderTime" value = "${(ofcOrderDTO.plateNumber)!""}" readonly="readonly" style="color: #000" name="" type="text" class="form-control input-sm" placeholder="" aria-controls="dynamic-table">
                                                </div>
                                            </div>
                                            <label class="control-label col-label no-padding-right" for="name">期望送达时间</label>
                                            <div class="w-width-220 y-float">
                                                <div class="clearfix">
                                                    <input id="expectedArrivedTime" value = "${(ofcOrderDTO.expectedArrivedTime?string("yyyy-MM-dd HH:mm"))!""}" readonly="readonly" style="color: #000" name="" type="text" class="form-control input-sm" placeholder="" aria-controls="dynamic-table">
                                                </div>
                                            </div>
                                            <label class="control-label col-label no-padding-right" for="name">司机姓名</label>
                                            <div class="w-width-220 y-float">
                                                <div class="clearfix">
                                                    <input id="driverName" value = "${(ofcOrderDTO.driverName)!""}" readonly="readonly" style="color: #000" name="" type="text" class="form-control input-sm" placeholder="" aria-controls="dynamic-table">
                                                </div>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="control-label col-label no-padding-right" for="name">联系电话</label>
                                            <div class="w-width-220 y-float">
                                                <div class="clearfix">
                                                    <input id="contactNumber" value = "${(ofcOrderDTO.contactNumber)!""}" readonly="readonly" style="color: #000" name="" type="text" class="form-control input-sm" placeholder="" aria-controls="dynamic-table">
                                                </div>
                                            </div>
                                        </div>
                                    </div>

                                    <div id="dropdown" class="tab-pane fade">


                                        <div class="page-header">
                                            <p>
                                                发货方信息
                                            </p>
                                        </div>
                                        <div class="form-group">
                                            <label class="control-label col-label no-padding-right" for="name">名称</label>
                                            <div class="w-width-220 y-float">
                                                <div class="clearfix">
                                                    <input id="orderTime" value = "${(ofcOrderDTO.consignorName)!""}" readonly="readonly" style="color: #000" name="" type="text" class="form-control input-sm" placeholder="" aria-controls="dynamic-table">
                                                </div>
                                            </div>
                                            <label class="control-label col-label no-padding-right" for="name">联系人</label>
                                            <div class="w-width-220 y-float">
                                                <div class="clearfix">
                                                    <input id="orderTime" value = "${(ofcOrderDTO.consignorContactName)!""}" readonly="readonly" style="color: #000" name="" type="text" class="form-control input-sm" placeholder="" aria-controls="dynamic-table">
                                                </div>
                                            </div>
                                            <label class="control-label col-label no-padding-right" for="name">联系电话</label>
                                            <div class="w-width-220 y-float">
                                                <div class="clearfix">
                                                    <input id="orderTime" value = "${(ofcOrderDTO.consignorContactPhone)!""}" readonly="readonly" style="color: #000" name="" type="text" class="form-control input-sm" placeholder="" aria-controls="dynamic-table">
                                                </div>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="control-label col-label no-padding-right" for="name">地址</label>
                                            <div class="w-width-220 y-float">
                                                <div class="clearfix">
                                                    <input id="orderTime" value = "${((ofcOrderDTO.departureProvince)?replace('null',''))!""}${((ofcOrderDTO.departureCity)?replace('null',''))!""}${((ofcOrderDTO.departureDistrict)!"")?replace('null','')}${((ofcOrderDTO.departureTowns)?replace('null',''))!""}" readonly="readonly" style="color: #000" name="" type="text" class="form-control input-sm" placeholder="" aria-controls="dynamic-table">
                                                </div>
                                            </div>
                                            <label class="control-label col-label no-padding-right" for="name">Email</label>
                                            <div class="w-width-220 y-float">
                                                <div class="clearfix">
                                                    <input id="orderTime" value = "${(consignorMessage.email)!""}" readonly="readonly" style="color: #000" name="" type="text" class="form-control input-sm" placeholder="" aria-controls="dynamic-table">
                                                </div>
                                            </div>
                                            <label class="control-label col-label no-padding-right" for="name">传真</label>
                                            <div class="w-width-220 y-float">
                                                <div class="clearfix">
                                                    <input id="orderTime" value = "${(consignorMessage.fax)!""}" readonly="readonly" style="color: #000" name="" type="text" class="form-control input-sm" placeholder="" aria-controls="dynamic-table">
                                                </div>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="control-label col-label no-padding-right" for="name">邮编</label>
                                            <div class="w-width-220 y-float">
                                                <div class="clearfix">
                                                    <input id="orderTime" value = "${(consignorMessage.postCode)!""}" readonly="readonly" style="color: #000" name="" type="text" class="form-control input-sm" placeholder="" aria-controls="dynamic-table">
                                                </div>
                                            </div>
                                        </div>

                                        <div class="page-header">
                                            <p>
                                                收货方信息
                                            </p>
                                        </div>
                                        <div class="form-group">
                                            <label class="control-label col-label no-padding-right" for="name">名称</label>
                                            <div class="w-width-220 y-float">
                                                <div class="clearfix">
                                                    <input id="orderTime" value = "${(ofcOrderDTO.consigneeName)!""}" readonly="readonly" style="color: #000" name="" type="text" class="form-control input-sm" placeholder="" aria-controls="dynamic-table">
                                                </div>
                                            </div>
                                            <label class="control-label col-label no-padding-right" for="name">联系人</label>
                                            <div class="w-width-220 y-float">
                                                <div class="clearfix">
                                                    <input id="orderTime" value = "${(ofcOrderDTO.consigneeContactName)!""}" readonly="readonly" style="color: #000" name="" type="text" class="form-control input-sm" placeholder="" aria-controls="dynamic-table">
                                                </div>
                                            </div>
                                            <label class="control-label col-label no-padding-right" for="name">联系电话</label>
                                            <div class="w-width-220 y-float">
                                                <div class="clearfix">
                                                    <input id="orderTime" value = "${(ofcOrderDTO.consigneeContactPhone)!""}" readonly="readonly" style="color: #000" name="" type="text" class="form-control input-sm" placeholder="" aria-controls="dynamic-table">
                                                </div>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="control-label col-label no-padding-right" for="name">地址</label>
                                            <div class="w-width-220 y-float">
                                                <div class="clearfix">
                                                    <input id="orderTime" value = "${((ofcOrderDTO.destinationProvince)?replace('null',''))!""}${((ofcOrderDTO.destinationCity)?replace('null',''))!""}${(ofcOrderDTO.destinationDistrict)!""}${((ofcOrderDTO.destinationTowns)?replace('null',''))!""}" readonly="readonly" style="color: #000" name="" type="text" class="form-control input-sm" placeholder="" aria-controls="dynamic-table">
                                                </div>
                                            </div>
                                            <label class="control-label col-label no-padding-right" for="name">Email</label>
                                            <div class="w-width-220 y-float">
                                                <div class="clearfix">
                                                    <input id="orderTime" value = "${(consigneeMessage.email)!""}" readonly="readonly" style="color: #000" name="" type="text" class="form-control input-sm" placeholder="" aria-controls="dynamic-table">
                                                </div>
                                            </div>
                                            <label class="control-label col-label no-padding-right" for="name">传真</label>
                                            <div class="w-width-220 y-float">
                                                <div class="clearfix">
                                                    <input id="orderTime" value = "${(consigneeMessage.fax)!""}" readonly="readonly" style="color: #000" name="" type="text" class="form-control input-sm" placeholder="" aria-controls="dynamic-table">
                                                </div>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="control-label col-label no-padding-right" for="name">邮编</label>
                                            <div class="w-width-220 y-float">
                                                <div class="clearfix">
                                                    <input id="orderTime" value = "${(consigneeMessage.postCode)!""}" readonly="readonly" style="color: #000" name="" type="text" class="form-control input-sm" placeholder="" aria-controls="dynamic-table">
                                                </div>
                                            </div>
                                        </div>
                                    </div>

                                    <div id="warmessages" class="tab-pane fade">
                                        <div class="form-group">
                                            <label class="control-label col-label no-padding-right" for="name">仓库名称</label>
                                            <div class="w-width-220 y-float">
                                                <div class="clearfix">
                                                    <input id="warehouseName" value="${(ofcOrderDTO.warehouseName)!""}" readonly="readonly" style="color: #000" name="warehouseName" type="text" class="form-control input-sm" placeholder="" aria-controls="dynamic-table">
                                                </div>
                                            </div>
                                            <label class="control-label col-width-100 no-padding-right y-float" style="margin-left:20px;"  for="name">入库预计到达时间</label>
                                            <div class="w-width-220 y-float">
                                                <div class="clearfix">
                                                    <input id="arriveTime" value = "${(ofcOrderDTO.arriveTime?string("yyyy-MM-dd HH:mm"))!""}" readonly="readonly" style="color: #000" name="arriveTime" type="text" class="form-control input-sm" placeholder="" aria-controls="dynamic-table">
                                                </div>
                                            </div>
                                            <label class="control-label col-label no-padding-right" for="name">车牌号</label>
                                            <div class="w-width-220 y-float">
                                                <div class="clearfix">
                                                    <input id="plateNumber" value = "${(ofcOrderDTO.plateNumber)!""}" readonly="readonly" style="color: #000" name="plateNumber" type="text" class="form-control input-sm" placeholder="" aria-controls="dynamic-table">
                                                </div>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="control-label col-label no-padding-right" for="name">司机姓名</label>
                                            <div class="w-width-220 y-float">
                                                <div class="clearfix">
                                                    <input id="driverName" value = "${(ofcOrderDTO.driverName)!""}" readonly="readonly" style="color: #000" name="driverName" type="text" class="form-control input-sm" placeholder="" aria-controls="dynamic-table">
                                                </div>
                                            </div>
                                            <label class="control-label col-width-100 no-padding-right y-float" style="margin-left:20px;"  for="name">联系电话</label>
                                            <div class="w-width-220 y-float">
                                                <div class="clearfix">
                                                    <input id="contactNumber" value = "${(ofcOrderDTO.contactNumber)!""}" readonly="readonly" style="color: #000" name="contactNumber" type="text" class="form-control input-sm" placeholder="" aria-controls="dynamic-table">
                                                </div>
                                            </div>
                                        </div>

                                        <div class="form-group">

                                        </div>
                                    </div>

                                    <div id="supportMessages" class="tab-pane fade">
                                        <div class="form-group">
                                            <label class="control-label col-label no-padding-right" for="name">名称</label>
                                            <div class="w-width-220 y-float">
                                                <div class="clearfix">
                                                    <input id="orderTime" value = "${(supportMessage.supplierName)!""}" readonly="readonly" style="color: #000" name="" type="text" class="form-control input-sm" placeholder="" aria-controls="dynamic-table">
                                                </div>
                                            </div>
                                            <label class="control-label col-label no-padding-right" for="name">联系人</label>
                                            <div class="w-width-220 y-float">
                                                <div class="clearfix">
                                                    <input id="orderTime" value = "${(supportMessage.contactName)!""}" readonly="readonly" style="color: #000" name="" type="text" class="form-control input-sm" placeholder="" aria-controls="dynamic-table">
                                                </div>
                                            </div>
                                            <label class="control-label col-label no-padding-right" for="name">Email</label>
                                            <div class="w-width-220 y-float">
                                                <div class="clearfix">
                                                    <input id="orderTime" value = "${(supportMessage.email)!""}" readonly="readonly" style="color: #000" name="" type="text" class="form-control input-sm" placeholder="" aria-controls="dynamic-table">
                                                </div>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="control-label col-label no-padding-right" for="name">联系电话</label>
                                            <div class="w-width-220 y-float">
                                                <div class="clearfix">
                                                    <input id="orderTime" value = "${(supportMessage.contactPhone)!""}" readonly="readonly" style="color: #000" name="" type="text" class="form-control input-sm" placeholder="" aria-controls="dynamic-table">
                                                </div>
                                            </div>
                                            <label class="control-label col-label no-padding-right" for="name">传真</label>
                                            <div class="w-width-220 y-float">
                                                <div class="clearfix">
                                                    <input id="orderTime" value = "${(supportMessage.fax)!""}" readonly="readonly" style="color: #000" name="" type="text" class="form-control input-sm" placeholder="" aria-controls="dynamic-table">
                                                </div>
                                            </div>
                                            <label class="control-label col-label no-padding-right" for="name">地址</label>
                                            <div class="w-width-220 y-float">
                                                <div class="clearfix">
                                                    <input id="orderTime" value = "${(supportMessage.completeAddress)!""}" readonly="readonly" style="color: #000" name="" type="text" class="form-control input-sm" placeholder="" aria-controls="dynamic-table">
                                                </div>
                                            </div>
                                        </div>


                                        <div class="form-group">
                                            <label class="control-label col-label no-padding-right" for="name">邮编</label>
                                            <div class="w-width-220 y-float">
                                                <div class="clearfix">
                                                    <input id="orderTime" value = "${(supportMessage.postCode)!""}" readonly="readonly" style="color: #000" name="" type="text" class="form-control input-sm" placeholder="" aria-controls="dynamic-table">
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>


                            <div class="page-header">
                                <p>
                                    货品信息
                                </p>
                            </div>
                            <table id="dynamic-table" class="table table-striped table-bordered table-hover dataTable no-footer" role="grid" aria-describedby="dynamic-table_info">
                                <thead>
                                <tr role="row">
                                    <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">序号</th>
                                    <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">货品编码</th>
                                    <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">货品名称</th>
                                    <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">货品规格</th>
                                    <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">单位</th>
                                    <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">生产批次</th>
                                    <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">生产日期</th>
                                    <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">失效日期</th>
                                    <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">数量</th>
                                </thead>
                                <tbody>
                                <#--
                                        vo  value object  拼出来的结果集
                                        dto  参数,给后端当参数,或者返回的dot.
                                -->
                                <#list goodsDetailsList! as goodsDetails>
                                <tr role="row" class="odd">
                                    <td>
                                    ${(goodsDetails_index+1)!""}
                                    </td>
                                    <td>
                                    ${(goodsDetails.goodsCode)!""}
                                    </td>
                                    <td>
                                    ${(goodsDetails.goodsName)!""}
                                    </td>
                                    <td>
                                    ${(goodsDetails.goodsSpec)!""}
                                    </td>
                                    <td>
                                    ${(goodsDetails.unit)!""}
                                    </td>
                                    <td>
                                    ${(goodsDetails.productionBatch)!""}
                                    </td>
                                    <td>
                                    ${((goodsDetails.productionTime)?string('yyyy-MM-dd'))!""}
                                    </td>
                                    <td>
                                    ${((goodsDetails.invalidTime)?string('yyyy-MM-dd'))!""}
                                    </td>
                                    <td>
                                    ${((goodsDetails.quantity)?replace(',',''))!""}
                                    </td>
                                </tr>
                                </#list>
                                </tbody>
                            </table>
                            <div class="page-header">
                                <p>
                                    订单跟踪信息
                                    <span id="hiddenOrderType" hidden="true">${(ofcOrderDTO.orderType)!}</span>
                                    <span id="hiddenBusinessType" hidden="true">${(ofcOrderDTO.businessType)!}</span>
                                </p>
                            </div>
                            <table id="dynamic-table" class="table table-striped table-bordered table-hover dataTable no-footer" role="grid" aria-describedby="dynamic-table_info">
                                <thead>
                                <tr role="row">
                                    <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">订单状态</th>
                                </thead>
                                <tbody>
                                <#--
                                        vo  value object  拼出来的结果集
                                        dto  参数,给后端当参数,或者返回的dot.
                                        -->
                                <#list orderStatusList! as orderStatus>
                                <tr role="row" class="odd">
                                    <td>
                                        . ${(orderStatus.notes)!""}
                                    </td>
                                </tr>
                                </#list>
                                </tbody>
                            </table>
                        </form>
                    </div><!-- /.col -->

<script type="text/javascript">
    $(function(){
        

        console.log(${(ofcOrderDTO.orderType)!});
        console.log(${(ofcOrderDTO.businessType)!});
        var businessType = $("#hiddenBusinessType").html();
        businessType = businessType.toString().substring(0,2);
        var orderType = $("#hiddenOrderType").html();
        var provideTrans = ${(ofcOrderDTO.provideTransport)!"0"};
        provideTrans = provideTrans.toString();
        if(orderType == '60'){//运输订单
            $('.warmessages').hide();
            $('.supportMessages').hide();
        }else if(orderType == '61'){//仓配订单
            if(businessType == '62'){//入库
                if(provideTrans == 0){//不需运输
                    $('.tramessages').hide();
                    $('.dropdown').hide();
                }
            }else if(businessType == '61'){//出库
                $('.supportMessages').hide();
                if(provideTrans == 0){//不需运输
                    $('.tramessages').hide();
                    $('.dropdown').hide();
                }

            }
        }


    });
    function detailBackToHistory() {

        console.log($("#goBack").val())
        var tag = $("#goBack").val();
        if("orderManage" == tag){
            xescm.common.loadPage("/ofc/orderManage");
        }
        else if("orderScreen" == tag){
            xescm.common.loadPage("/ofc/orderScreen");
        }
    }
</script>
</body>
