
<head>
    <title>订单详情</title>
    <!-- bootstrap & fontawesome -->
</head>
<body class="no-skin">
<!-- /section:basics/navbar.layout -->
<div class="main-container ace-save-state" id="main-container">
    <div class="main-content">
        <div class="main-content-inner">
            <div class="page-content">
                <div class="row">
                    <div class="col-xs-12">

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

                                    <li>
                                        <a data-toggle="tab" href="#tramessages">
                                            配送信息
                                        </a>
                                    </li>

                                    <li class="dropdown">
                                        <a data-toggle="tab" href="#dropdown">
                                            收发货方信息
                                        </a>
                                    </li>

                                    <li class="dropdown">
                                        <a data-toggle="tab" href="#warmessages">
                                            仓储信息
                                        </a>
                                    </li>

                                    <li class="dropdown">
                                        <a data-toggle="tab" href="#goodsmessages">
                                            供应商信息
                                        </a>
                                    </li>
                                </ul>

                                <div class="tab-content">
                                    <div id="home" class="tab-pane fade in active">
                                        <div class="form-group">
                                            <label class="control-label col-sm-1 no-padding-right" for="name">订单日期</label>
                                            <div class="col-sm-3">
                                                <div class="clearfix">
                                                    <input id="orderTime" value = "${(ofcOrderDTO.orderTime?string("yyyy-MM-dd HH:mm:ss"))!""}" readonly="readonly" style="color: #000" name="orderTime" type="text" class="form-control input-sm" placeholder="" aria-controls="dynamic-table"1>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="control-label col-sm-1 no-padding-right" for="name">订单编号</label>
                                            <div class="col-sm-3">
                                                <div class="clearfix">
                                                    <input id="orderTime" value = "${(ofcOrderDTO.orderCode)!""}" readonly="readonly" style="color: #000" name="orderTime" type="text" class="form-control input-sm" placeholder="" aria-controls="dynamic-table"1>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="control-label col-sm-1 no-padding-right" for="name">客户订单编号</label>
                                            <div class="col-sm-3">
                                                <div class="clearfix">
                                                    <input id="orderTime" value = "${(ofcOrderDTO.custOrderCode)!""}" readonly="readonly" style="color: #000" name="orderTime" type="text" class="form-control input-sm" placeholder="" aria-controls="dynamic-table"1>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="control-label col-sm-1 no-padding-right" for="name">订单状态</label>
                                            <div class="col-sm-3">
                                                <div class="clearfix">
                                                    <input id="orderTime" value = "${(ofcOrderDTO.orderStatus)!""}" readonly="readonly" style="color: #000" name="orderTime" type="text" class="form-control input-sm" placeholder="" aria-controls="dynamic-table"1>
                                                </div>
                                            </div>
                                        </div>

                                        <div class="form-group">
                                            <label class="control-label col-sm-1 no-padding-right" for="name">订单类型</label>
                                            <div class="col-sm-3">
                                                <div class="clearfix">
                                                    <input id="orderTime" value = "${(ofcOrderDTO.orderType)!""}" readonly="readonly" style="color: #000" name="orderTime" type="text" class="form-control input-sm" placeholder="" aria-controls="dynamic-table"1>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="control-label col-sm-1 no-padding-right" for="name">业务类型</label>
                                            <div class="col-sm-3">
                                                <div class="clearfix">
                                                    <input id="orderTime" value = "${(ofcOrderDTO.businessType)!""}" readonly="readonly" style="color: #000" name="orderTime" type="text" class="form-control input-sm" placeholder="" aria-controls="dynamic-table"1>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="control-label col-sm-1 no-padding-right" for="name">店铺</label>
                                            <div class="col-sm-3">
                                                <div class="clearfix">
                                                    <input id="orderTime" value = "${(ofcOrderDTO.storeName)!""}" readonly="readonly" style="color: #000" name="orderTime" type="text" class="form-control input-sm" placeholder="" aria-controls="dynamic-table"1>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="control-label col-sm-1 no-padding-right" for="name">平台类型</label>
                                            <div class="col-sm-3">
                                                <div class="clearfix">
                                                    <input id="orderTime" value = "${(ofcOrderDTO.platformType)!""}" readonly="readonly" style="color: #000" name="orderTime" type="text" class="form-control input-sm" placeholder="" aria-controls="dynamic-table"1>
                                                </div>
                                            </div>
                                        </div>

                                        <div class="form-group">
                                            <label class="control-label col-sm-1 no-padding-right" for="name">服务产品</label>
                                            <div class="col-sm-3">
                                                <div class="clearfix">
                                                    <input id="orderTime" value = "${(ofcOrderDTO.productName)!""}" readonly="readonly" style="color: #000" name="orderTime" type="text" class="form-control input-sm" placeholder="" aria-controls="dynamic-table"1>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="control-label col-sm-1 no-padding-right" for="name">订单来源</label>
                                            <div class="col-sm-3">
                                                <div class="clearfix">
                                                    <input id="orderTime" value = "${(ofcOrderDTO.orderSource)!""}" readonly="readonly" style="color: #000" name="orderTime" type="text" class="form-control input-sm" placeholder="" aria-controls="dynamic-table"1>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="control-label col-sm-1 no-padding-right" for="name">备注</label>
                                            <div class="col-sm-3">
                                                <div class="clearfix">
                                                    <input id="orderTime" value = "${(ofcOrderDTO.notes)!""}" readonly="readonly" style="color: #000" name="orderTime" type="text" class="form-control input-sm" placeholder="" aria-controls="dynamic-table"1>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="control-label col-sm-1 no-padding-right" for="name">创建日期</label>
                                            <div class="col-sm-3">
                                                <div class="clearfix">
                                                    <input id="orderTime" value = "${(ofcOrderDTO.creationTime?string("yyyy-MM-dd HH:mm:ss"))!""}" readonly="readonly" style="color: #000" name="orderTime" type="text" class="form-control input-sm" placeholder="" aria-controls="dynamic-table"1>
                                                </div>
                                            </div>
                                        </div>

                                        <div class="form-group">
                                            <label class="control-label col-sm-1 no-padding-right" for="name">创建人员</label>
                                            <div class="col-sm-3">
                                                <div class="clearfix">
                                                    <input id="orderTime" value = "${(ofcOrderDTO.creator)!""}" readonly="readonly" style="color: #000" name="orderTime" type="text" class="form-control input-sm" placeholder="" aria-controls="dynamic-table"1>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="control-label col-sm-1 no-padding-right" for="name">完成日期</label>
                                            <div class="col-sm-3">
                                                <div class="clearfix">
                                                    <input id="orderTime" value = "${("")!""}" readonly="readonly" style="color: #000" name="orderTime" type="text" class="form-control input-sm" placeholder="" aria-controls="dynamic-table"1>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="control-label col-sm-1 no-padding-right" for="name">取消日期</label>
                                            <div class="col-sm-3">
                                                <div class="clearfix">
                                                    <input id="orderTime" value = "${("")!""}" readonly="readonly" style="color: #000" name="orderTime" type="text" class="form-control input-sm" placeholder="" aria-controls="dynamic-table"1>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="control-label col-sm-1 no-padding-right" for="name">取消人</label>
                                            <div class="col-sm-3">
                                                <div class="clearfix">
                                                    <input id="orderTime" value = "${("")!""}" readonly="readonly" style="color: #000" name="orderTime" type="text" class="form-control input-sm" placeholder="" aria-controls="dynamic-table"1>
                                                </div>
                                            </div>
                                        </div>
                                    </div>

                                    <div id="tramessages" class="tab-pane fade">
                                        <div class="form-group">
                                            <label class="control-label col-sm-1 no-padding-right" for="name">货品类型</label>
                                            <div class="col-sm-3">
                                                <div class="clearfix">
                                                    <input id="orderTime" value = "${(ofcOrderDTO.goodsType)!""}" readonly="readonly" style="color: #000" name="orderTime" type="text" class="form-control input-sm" placeholder="" aria-controls="dynamic-table"1>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="control-label col-sm-1 no-padding-right" for="name">是否加急</label>
                                            <div class="col-sm-3">
                                                <div class="clearfix">
                                                    <input id="orderTime" value = "${(ofcOrderDTO.urgent)!""}" readonly="readonly" style="color: #000" name="orderTime" type="text" class="form-control input-sm" placeholder="" aria-controls="dynamic-table"1>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="control-label col-sm-1 no-padding-right" for="name">出发地</label>
                                            <div class="col-sm-3">
                                                <div class="clearfix">
                                                    <input id="orderTime" value = "${(ofcOrderDTO.departurePlace)!""}" readonly="readonly" style="color: #000" name="orderTime" type="text" class="form-control input-sm" placeholder="" aria-controls="dynamic-table"1>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="control-label col-sm-1 no-padding-right" for="name">目的地</label>
                                            <div class="col-sm-3">
                                                <div class="clearfix">
                                                    <input id="orderTime" value = "${(ofcOrderDTO.destination)!""}" readonly="readonly" style="color: #000" name="orderTime" type="text" class="form-control input-sm" placeholder="" aria-controls="dynamic-table"1>
                                                </div>
                                            </div>
                                        </div>

                                        <div class="form-group">
                                            <label class="control-label col-sm-1 no-padding-right" for="name">数量</label>
                                            <div class="col-sm-3">
                                                <div class="clearfix">
                                                    <input id="orderTime" value = "${(ofcOrderDTO.quantity)!""}" readonly="readonly" style="color: #000" name="orderTime" type="text" class="form-control input-sm" placeholder="" aria-controls="dynamic-table"1>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="control-label col-sm-1 no-padding-right" for="name">重量</label>
                                            <div class="col-sm-3">
                                                <div class="clearfix">
                                                    <input id="orderTime" value = "${(ofcOrderDTO.weight)!""}" readonly="readonly" style="color: #000" name="orderTime" type="text" class="form-control input-sm" placeholder="" aria-controls="dynamic-table"1>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="control-label col-sm-1 no-padding-right" for="name">体积</label>
                                            <div class="col-sm-3">
                                                <div class="clearfix">
                                                    <input id="orderTime" value = "${(ofcOrderDTO.cubage)!""}" readonly="readonly" style="color: #000" name="orderTime" type="text" class="form-control input-sm" placeholder="" aria-controls="dynamic-table"1>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="control-label col-sm-1 no-padding-right" for="name">运输要求</label>
                                            <div class="col-sm-3">
                                                <div class="clearfix">
                                                    <input id="orderTime" value = "${(ofcOrderDTO.transRequire)!""}" readonly="readonly" style="color: #000" name="orderTime" type="text" class="form-control input-sm" placeholder="" aria-controls="dynamic-table"1>
                                                </div>
                                            </div>
                                        </div>

                                        <div class="form-group">
                                            <label class="control-label col-sm-1 no-padding-right" for="name">取货时间</label>
                                            <div class="col-sm-3">
                                                <div class="clearfix">
                                                    <input id="orderTime" value = "${(ofcOrderDTO.pickupTime?string("yyyy-MM-dd HH:mm:ss"))!""}" readonly="readonly" style="color: #000" name="orderTime" type="text" class="form-control input-sm" placeholder="" aria-controls="dynamic-table"1>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="control-label col-sm-1 no-padding-right" for="name">运输单号</label>
                                            <div class="col-sm-3">
                                                <div class="clearfix">
                                                    <input id="orderTime" value = "${(ofcOrderDTO.transCode)!""}" readonly="readonly" style="color: #000" name="orderTime" type="text" class="form-control input-sm" placeholder="" aria-controls="dynamic-table"1>
                                                </div>
                                            </div>
                                        </div>

                                        <div class="form-group">
                                            <label class="control-label col-sm-1 no-padding-right" for="name">车牌号</label>
                                            <div class="col-sm-3">
                                                <div class="clearfix">
                                                    <input id="orderTime" value = "${(ofcOrderDTO.plateNumber)!""}" readonly="readonly" style="color: #000" name="orderTime" type="text" class="form-control input-sm" placeholder="" aria-controls="dynamic-table"1>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="control-label col-sm-1 no-padding-right" for="name">期望送达时间</label>
                                            <div class="col-sm-3">
                                                <div class="clearfix">
                                                    <input id="orderTime" value = "${(ofcOrderDTO.destiexpectedArrivedTimenation?string("yyyy-MM-dd HH:mm:ss"))!""}" readonly="readonly" style="color: #000" name="orderTime" type="text" class="form-control input-sm" placeholder="" aria-controls="dynamic-table"1>
                                                </div>
                                            </div>
                                        </div>

                                        <div class="form-group">
                                            <label class="control-label col-sm-1 no-padding-right" for="name">司机姓名</label>
                                            <div class="col-sm-3">
                                                <div class="clearfix">
                                                    <input id="orderTime" value = "${(ofcOrderDTO.driverName)!""}" readonly="readonly" style="color: #000" name="orderTime" type="text" class="form-control input-sm" placeholder="" aria-controls="dynamic-table"1>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="control-label col-sm-1 no-padding-right" for="name">联系电话</label>
                                            <div class="col-sm-3">
                                                <div class="clearfix">
                                                    <input id="orderTime" value = "${(ofcOrderDTO.contactNumber)!""}" readonly="readonly" style="color: #000" name="orderTime" type="text" class="form-control input-sm" placeholder="" aria-controls="dynamic-table"1>
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
                                            <label class="control-label col-sm-1 no-padding-right" for="name">名称</label>
                                            <div class="col-sm-3">
                                                <div class="clearfix">
                                                    <input id="orderTime" value = "${("众品有限公司")!""}" readonly="readonly" style="color: #000" name="orderTime" type="text" class="form-control input-sm" placeholder="" aria-controls="dynamic-table"1>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="control-label col-sm-1 no-padding-right" for="name">联系人</label>
                                            <div class="col-sm-3">
                                                <div class="clearfix">
                                                    <input id="orderTime" value = "${("王经理")!""}" readonly="readonly" style="color: #000" name="orderTime" type="text" class="form-control input-sm" placeholder="" aria-controls="dynamic-table"1>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="control-label col-sm-1 no-padding-right" for="name">Email</label>
                                            <div class="col-sm-3">
                                                <div class="clearfix">
                                                    <input id="orderTime" value = "${("mayanlong@hnxianyi.com")!""}" readonly="readonly" style="color: #000" name="orderTime" type="text" class="form-control input-sm" placeholder="" aria-controls="dynamic-table"1>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="control-label col-sm-1 no-padding-right" for="name">联系电话</label>
                                            <div class="col-sm-3">
                                                <div class="clearfix">
                                                    <input id="orderTime" value = "${("15668159000")!""}" readonly="readonly" style="color: #000" name="orderTime" type="text" class="form-control input-sm" placeholder="" aria-controls="dynamic-table"1>
                                                </div>
                                            </div>
                                        </div>

                                        <div class="form-group">
                                            <label class="control-label col-sm-1 no-padding-right" for="name">传真</label>
                                            <div class="col-sm-3">
                                                <div class="clearfix">
                                                    <input id="orderTime" value = "${("0355-56379811")!""}" readonly="readonly" style="color: #000" name="orderTime" type="text" class="form-control input-sm" placeholder="" aria-controls="dynamic-table"1>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="control-label col-sm-1 no-padding-right" for="name">地址</label>
                                            <div class="col-sm-3">
                                                <div class="clearfix">
                                                    <input id="orderTime" value = "${("河南省郑州市经济开发区田江路110号")!""}" readonly="readonly" style="color: #000" name="orderTime" type="text" class="form-control input-sm" placeholder="" aria-controls="dynamic-table"1>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="control-label col-sm-1 no-padding-right" for="name">邮编</label>
                                            <div class="col-sm-3">
                                                <div class="clearfix">
                                                    <input id="orderTime" value = "${("100100")!""}" readonly="readonly" style="color: #000" name="orderTime" type="text" class="form-control input-sm" placeholder="" aria-controls="dynamic-table"1>
                                                </div>
                                            </div>
                                        </div>

                                        <div class="page-header">
                                            <p>
                                                收货方信息
                                            </p>
                                        </div>
                                        <div class="form-group">
                                            <label class="control-label col-sm-1 no-padding-right" for="name">名称</label>
                                            <div class="col-sm-3">
                                                <div class="clearfix">
                                                    <input id="orderTime" value = "${("众品有限公司")!""}" readonly="readonly" style="color: #000" name="orderTime" type="text" class="form-control input-sm" placeholder="" aria-controls="dynamic-table"1>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="control-label col-sm-1 no-padding-right" for="name">联系人</label>
                                            <div class="col-sm-3">
                                                <div class="clearfix">
                                                    <input id="orderTime" value = "${("王经理")!""}" readonly="readonly" style="color: #000" name="orderTime" type="text" class="form-control input-sm" placeholder="" aria-controls="dynamic-table"1>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="control-label col-sm-1 no-padding-right" for="name">Email</label>
                                            <div class="col-sm-3">
                                                <div class="clearfix">
                                                    <input id="orderTime" value = "${("mayanlong@hnxianyi.com")!""}" readonly="readonly" style="color: #000" name="orderTime" type="text" class="form-control input-sm" placeholder="" aria-controls="dynamic-table"1>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="control-label col-sm-1 no-padding-right" for="name">联系电话</label>
                                            <div class="col-sm-3">
                                                <div class="clearfix">
                                                    <input id="orderTime" value = "${("15668159000")!""}" readonly="readonly" style="color: #000" name="orderTime" type="text" class="form-control input-sm" placeholder="" aria-controls="dynamic-table"1>
                                                </div>
                                            </div>
                                        </div>

                                        <div class="form-group">
                                            <label class="control-label col-sm-1 no-padding-right" for="name">传真</label>
                                            <div class="col-sm-3">
                                                <div class="clearfix">
                                                    <input id="orderTime" value = "${("0355-56379811")!""}" readonly="readonly" style="color: #000" name="orderTime" type="text" class="form-control input-sm" placeholder="" aria-controls="dynamic-table"1>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="control-label col-sm-1 no-padding-right" for="name">地址</label>
                                            <div class="col-sm-3">
                                                <div class="clearfix">
                                                    <input id="orderTime" value = "${("河南省郑州市经济开发区田江路110号")!""}" readonly="readonly" style="color: #000" name="orderTime" type="text" class="form-control input-sm" placeholder="" aria-controls="dynamic-table"1>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="control-label col-sm-1 no-padding-right" for="name">邮编</label>
                                            <div class="col-sm-3">
                                                <div class="clearfix">
                                                    <input id="orderTime" value = "${("100100")!""}" readonly="readonly" style="color: #000" name="orderTime" type="text" class="form-control input-sm" placeholder="" aria-controls="dynamic-table"1>
                                                </div>
                                            </div>
                                        </div>
                                    </div>

                                    <div id="warmessages" class="tab-pane fade">
                                        <div class="form-group">
                                            <label class="control-label col-sm-1 no-padding-right" for="name">仓库名称</label>
                                            <div class="col-sm-3">
                                                <div class="clearfix">
                                                    <input id="warehouseName" value="${(ofcOrderDTO.wareHouseName)!""}" readonly="readonly" style="color: #000" name="warehouseName" type="text" class="form-control input-sm" placeholder="" aria-controls="dynamic-table"1>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="control-label col-sm-1 no-padding-right" for="name">入库预计到达时间</label>
                                            <div class="col-sm-3">
                                                <div class="clearfix">
                                                    <input id="arriveTime" value = "${(ofcOrderDTO.arriveTime)!""}" readonly="readonly" style="color: #000" name="arriveTime" type="text" class="form-control input-sm" placeholder="" aria-controls="dynamic-table"1>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="control-label col-sm-1 no-padding-right" for="name">车牌号</label>
                                            <div class="col-sm-3">
                                                <div class="clearfix">
                                                    <input id="plateNumber" value = "${(ofcOrderDTO.plateNumber)!""}" readonly="readonly" style="color: #000" name="plateNumber" type="text" class="form-control input-sm" placeholder="" aria-controls="dynamic-table"1>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="control-label col-sm-1 no-padding-right" for="name">司机姓名</label>
                                            <div class="col-sm-3">
                                                <div class="clearfix">
                                                    <input id="driverName" value = "${(ofcOrderDTO.driverName)!""}" readonly="readonly" style="color: #000" name="driverName" type="text" class="form-control input-sm" placeholder="" aria-controls="dynamic-table"1>
                                                </div>
                                            </div>
                                        </div>

                                        <div class="form-group">
                                            <label class="control-label col-sm-1 no-padding-right" for="name">联系电话</label>
                                            <div class="col-sm-3">
                                                <div class="clearfix">
                                                    <input id="contactNumber" value = "${(ofcOrderDTO.contactNumber)!""}" readonly="readonly" style="color: #000" name="contactNumber" type="text" class="form-control input-sm" placeholder="" aria-controls="dynamic-table"1>
                                                </div>
                                            </div>
                                        </div>
                                    </div>

                                    <div id="goodsmessages" class="tab-pane fade">
                                        <div class="form-group">
                                            <label class="control-label col-sm-1 no-padding-right" for="name">名称</label>
                                            <div class="col-sm-3">
                                                <div class="clearfix">
                                                    <input id="orderTime" value = "${("众品有限公司")!""}" readonly="readonly" style="color: #000" name="orderTime" type="text" class="form-control input-sm" placeholder="" aria-controls="dynamic-table"1>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="control-label col-sm-1 no-padding-right" for="name">联系人</label>
                                            <div class="col-sm-3">
                                                <div class="clearfix">
                                                    <input id="orderTime" value = "${("王经理")!""}" readonly="readonly" style="color: #000" name="orderTime" type="text" class="form-control input-sm" placeholder="" aria-controls="dynamic-table"1>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="control-label col-sm-1 no-padding-right" for="name">Email</label>
                                            <div class="col-sm-3">
                                                <div class="clearfix">
                                                    <input id="orderTime" value = "${("mayanlong@hnxianyi.com")!""}" readonly="readonly" style="color: #000" name="orderTime" type="text" class="form-control input-sm" placeholder="" aria-controls="dynamic-table"1>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="control-label col-sm-1 no-padding-right" for="name">联系电话</label>
                                            <div class="col-sm-3">
                                                <div class="clearfix">
                                                    <input id="orderTime" value = "${("15668159000")!""}" readonly="readonly" style="color: #000" name="orderTime" type="text" class="form-control input-sm" placeholder="" aria-controls="dynamic-table"1>
                                                </div>
                                            </div>
                                        </div>

                                        <div class="form-group">
                                            <label class="control-label col-sm-1 no-padding-right" for="name">传真</label>
                                            <div class="col-sm-3">
                                                <div class="clearfix">
                                                    <input id="orderTime" value = "${("0355-56379811")!""}" readonly="readonly" style="color: #000" name="orderTime" type="text" class="form-control input-sm" placeholder="" aria-controls="dynamic-table"1>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="control-label col-sm-1 no-padding-right" for="name">地址</label>
                                            <div class="col-sm-3">
                                                <div class="clearfix">
                                                    <input id="orderTime" value = "${("河南省郑州市经济开发区田江路110号")!""}" readonly="readonly" style="color: #000" name="orderTime" type="text" class="form-control input-sm" placeholder="" aria-controls="dynamic-table"1>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="control-label col-sm-1 no-padding-right" for="name">邮编</label>
                                            <div class="col-sm-3">
                                                <div class="clearfix">
                                                    <input id="orderTime" value = "${("100100")!""}" readonly="readonly" style="color: #000" name="orderTime" type="text" class="form-control input-sm" placeholder="" aria-controls="dynamic-table"1>
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
                                    ${(goodsDetails.productionTime)!""}
                                    </td>
                                    <td>
                                    ${(goodsDetails.invalidTime)!""}
                                    </td>
                                    <td>
                                    ${(goodsDetails.quantity)!""}
                                    </td>
                                </tr>
                                </#list>
                                </tbody>
                            </table>
                            <div class="page-header">
                                <p>
                                    订单跟踪信息
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

                </div><!-- /.row -->
            </div><!-- /.page-content -->
        </div>
    </div><!-- /.main-content -->
</div><!-- /.main-container -->
</body>
</html>