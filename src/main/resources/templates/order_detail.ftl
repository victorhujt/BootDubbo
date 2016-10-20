<!DOCTYPE html>
<#assign base=request.contextPath />
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:th="http://www.thymeleaf.org"
      xmlns:sec="http://www.thymeleaf.org/thymeleaf-extras-springsecurity3">
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta charset="utf-8">
    <title>订单详情</title>
    <meta name="description" content="Static &amp; Dynamic Tables">

    <!-- bootstrap & fontawesome -->
    <link rel="stylesheet" href="${base}/assets/css/bootstrap.css">
</head>
<body class="no-skin">
<!-- /section:basics/navbar.layout -->
<div class="main-container ace-save-state" id="main-container">

    <div class="main-content">
        <div class="main-content-inner">

            <div class="page-content">
                <div class="page-header">
                    <h1>
                        订单详情
                    </h1>
                </div><!-- /.page-header -->
                <div class="row">
                    <div class="col-xs-12">
                        <div class="clearfix">
                            <div class="pull-right tableTools-container"><div class="dt-buttons btn-overlap btn-group"><a class="dt-button buttons-collection buttons-colvis btn btn-white btn-primary btn-bold" tabindex="0" aria-controls="dynamic-table" data-original-title="" title=""><span><i class="fa fa-search bigger-110 blue"></i> <span class="hidden">Show/hide columns</span></span></a><a class="dt-button buttons-copy buttons-html5 btn btn-white btn-primary btn-bold" tabindex="0" aria-controls="dynamic-table" data-original-title="" title=""><span><i class="fa fa-copy bigger-110 pink"></i> <span class="hidden">Copy to clipboard</span></span></a><a class="dt-button buttons-csv buttons-html5 btn btn-white btn-primary btn-bold" tabindex="0" aria-controls="dynamic-table" data-original-title="" title=""><span><i class="fa fa-database bigger-110 orange"></i> <span class="hidden">Export to CSV</span></span></a><a class="dt-button buttons-excel buttons-flash btn btn-white btn-primary btn-bold" tabindex="0" aria-controls="dynamic-table"><span><i class="fa fa-file-excel-o bigger-110 green"></i> <span class="hidden">Export to Excel</span></span><div style="position: absolute; left: 0px; top: 0px; width: 39px; height: 35px; z-index: 99;" data-original-title="" title=""><embed id="ZeroClipboard_TableToolsMovie_1" src="${base}/components/datatables.net-buttons-swf/index.swf" loop="false" menu="false" quality="best" bgcolor="#ffffff" width="39" height="35" name="ZeroClipboard_TableToolsMovie_1" align="middle" allowscriptaccess="always" allowfullscreen="false" type="application/x-shockwave-flash" pluginspage="http://www.macromedia.com/go/getflashplayer" flashvars="id=1&amp;width=39&amp;height=35" wmode="transparent"></div></a><a class="dt-button buttons-pdf buttons-flash btn btn-white btn-primary btn-bold" tabindex="0" aria-controls="dynamic-table"><span><i class="fa fa-file-pdf-o bigger-110 red"></i> <span class="hidden">Export to PDF</span></span><div style="position: absolute; left: 0px; top: 0px; width: 39px; height: 35px; z-index: 99;" data-original-title="" title=""><embed id="ZeroClipboard_TableToolsMovie_2" src="${base}/components/datatables.net-buttons-swf/index.swf" loop="false" menu="false" quality="best" bgcolor="#ffffff" width="39" height="35" name="ZeroClipboard_TableToolsMovie_2" align="middle" allowscriptaccess="always" allowfullscreen="false" type="application/x-shockwave-flash" pluginspage="http://www.macromedia.com/go/getflashplayer" flashvars="id=2&amp;width=39&amp;height=35" wmode="transparent"></div></a><a class="dt-button buttons-print btn btn-white btn-primary btn-bold" tabindex="0" aria-controls="dynamic-table" data-original-title="" title=""><span><i class="fa fa-print bigger-110 grey"></i> <span class="hidden">Print</span></span></a></div></div>
                        </div>


                        <!-- div.table-responsive -->

                        <!-- div.dataTables_borderWrap -->
                        <div>
                            <div id="dynamic-table_wrapper" class="dataTables_wrapper form-inline no-footer">
                                <div class="table-header">
                                    订单信息
                                </div>
                                <table id="dynamic-table" class="table table-striped table-bordered table-hover dataTable no-footer" role="grid" aria-describedby="dynamic-table_info">
                                    <thead>
                                    <tr role="row">
                                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">订单日期</th>
                                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">订单编号</th>
                                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">客户订单编号</th>
                                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">订单状态</th>
                                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">订单类型</th>
                                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">业务类型</th>
                                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">店铺</th>
                                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">平台类型</th>
                                    </thead>
                                    <tbody>
                                    <#--
                                            vo  value object  拼出来的结果集
                                            dto  参数,给后端当参数,或者返回的dot.
                                            -->
                                    <tr role="row" class="odd">
                                        <td>
                                        ${(ofcOrderDTO.orderTime?string("yyyy-MM-dd HH:mm:ss"))!"null"}
                                        </td>
                                        <td>
                                        ${(ofcOrderDTO.orderCode)!"null"}
                                        </td>
                                        <td>
                                        ${(ofcOrderDTO.custOrderCode)!"null"}
                                        </td>
                                        <td>
                                        ${(ofcOrderDTO.orderStatus)!"null"}
                                        </td>
                                        <td>
                                        ${(ofcOrderDTO.orderType)!"null"}
                                        </td>
                                        <td>
                                        ${(ofcOrderDTO.businessType)!"null"}
                                        </td>
                                        <td>
                                        ${(ofcOrderDTO.storeName)!"null"}
                                        </td>
                                        <td>
                                        ${(ofcOrderDTO.platformType)!"null"}
                                        </td>
                                    </tr>
                                    </tbody>
                                </table>
                                <table id="dynamic-table" class="table table-striped table-bordered table-hover dataTable no-footer" role="grid" aria-describedby="dynamic-table_info">
                                    <thead>
                                    <tr role="row">
                                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">服务产品</th>
                                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">订单来源</th>
                                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">备注</th>
                                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">创建日期</th>
                                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">创建人员</th>
                                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">完成日期</th>
                                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">取消日期</th>
                                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">取消人</th>
                                    </thead>
                                    <tbody>
                                    <#--
                                            vo  value object  拼出来的结果集
                                            dto  参数,给后端当参数,或者返回的dot.
                                            -->
                                    <tr role="row" class="odd">
                                        <td>
                                        ${(ofcOrderDTO.productName)!"null"}
                                        </td>
                                        <td>
                                        ${(ofcOrderDTO.orderSource)!"null"}
                                        </td>
                                        <td>
                                        ${(ofcOrderDTO.notes)!"null"}
                                        </td>
                                        <td>
                                        ${(ofcOrderDTO.creationTime?string("yyyy-MM-dd HH:mm:ss"))!"null"}
                                        </td>
                                        <td>
                                        ${(ofcOrderDTO.creator)!"null"}
                                        </td>
                                        <td>
                                        ${("null")!"null"}
                                        </td>
                                        <td>
                                        ${("null")!"null"}
                                        </td>
                                        <td>
                                        ${("null")!"null"}
                                        </td>
                                    </tr>
                                    </tbody>
                                </table>
                                <div class="page-header">
                                    <h4>配送信息</h4>

                                </div>
                                <table id="dynamic-table" class="table table-striped table-bordered table-hover dataTable no-footer" role="grid" aria-describedby="dynamic-table_info">
                                    <thead>
                                    <tr role="row">
                                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">货品类型</th>
                                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">是否加急</th>
                                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">出发地</th>
                                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">目的地</th>
                                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">数量</th>
                                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">重量</th>
                                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">体积</th>
                                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">运输要求</th>
                                    </thead>
                                    <tbody>
                                    <#--
                                            vo  value object  拼出来的结果集
                                            dto  参数,给后端当参数,或者返回的dot.
                                            -->
                                    <tr role="row" class="odd">
                                        <td>
                                        ${(ofcOrderDTO.goodsType)!"null"}
                                        </td>
                                        <td>
                                        ${(ofcOrderDTO.urgent)!"null"}
                                        </td>
                                        <td>
                                        ${(ofcOrderDTO.departurePlace)!"null"}
                                        </td>
                                        <td>
                                        ${(ofcOrderDTO.destination)!"null"}
                                        </td>
                                        <td>
                                        ${(ofcOrderDTO.quantity)!"null"}
                                        </td>
                                        <td>
                                        ${(ofcOrderDTO.weight)!"null"}
                                        </td>
                                        <td>
                                        ${(ofcOrderDTO.cubage)!"null"}
                                        </td>
                                        <td>
                                        ${(ofcOrderDTO.transRequire)!"null"}
                                        </td>
                                    </tr>
                                    </tbody>
                                </table>
                                <table id="dynamic-table" class="table table-striped table-bordered table-hover dataTable no-footer" role="grid" aria-describedby="dynamic-table_info">
                                    <thead>
                                    <tr role="row">
                                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">取货时间</th>
                                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">运输单号</th>
                                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">车牌号</th>
                                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">期望送达时间</th>
                                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">司机姓名</th>
                                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">联系电话</th>
                                    </thead>
                                    <tbody>
                                    <#--
                                            vo  value object  拼出来的结果集
                                            dto  参数,给后端当参数,或者返回的dot.
                                            -->
                                    <tr role="row" class="odd">
                                        <td>
                                        ${(ofcOrderDTO.pickupTime?string("yyyy-MM-dd HH:mm:ss"))!"null"}
                                        </td>
                                        <td>
                                        ${(ofcOrderDTO.transCode)!"null"}
                                        </td>
                                        <td>
                                        ${(ofcOrderDTO.plateNumber)!"null"}
                                        </td>
                                        <td>
                                        ${(ofcOrderDTO.destiexpectedArrivedTimenation?string("yyyy-MM-dd HH:mm:ss"))!"null"}
                                        </td>
                                        <td>
                                        ${(ofcOrderDTO.driverName)!"null"}
                                        </td>
                                        <td>
                                        ${(ofcOrderDTO.contactNumber)!"null"}
                                        </td>
                                    </tr>
                                    </tbody>
                                </table>
                                <div class="page-header">
                                    <h4>发货方信息</h4>

                                </div>
                                <table id="dynamic-table" class="table table-striped table-bordered table-hover dataTable no-footer" role="grid" aria-describedby="dynamic-table_info">
                                    <thead>
                                    <tr role="row">
                                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">名称</th>
                                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">联系人</th>
                                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">Email</th>
                                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">联系电话</th>
                                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">传真</th>
                                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">地址</th>
                                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">邮编</th>
                                    </thead>
                                    <tbody>
                                    <#--
                                            vo  value object  拼出来的结果集
                                            dto  参数,给后端当参数,或者返回的dot.
                                            -->
                                    <tr role="row" class="odd">
                                        <td>
                                        ${("众品有限公司")!"null"}
                                        </td>
                                        <td>
                                        ${("王经理")!"null"}
                                        </td>
                                        <td>
                                        ${("mayanlong@hnxianyi.com")!"null"}
                                        </td>
                                        <td>
                                        ${("15668159000")!"null"}
                                        </td>
                                        <td>
                                        ${("0355-56379811")!"null"}
                                        </td>
                                        <td>
                                        ${("河南省郑州市经济开发区田江路110号")!"null"}
                                        </td>
                                        <td>
                                        ${("100100")!"null"}
                                        </td>
                                    </tr>
                                    </tbody>
                                </table>
                                <div class="page-header">
                                    <h4>收货方信息</h4>
                                </div>
                                <table id="dynamic-table" class="table table-striped table-bordered table-hover dataTable no-footer" role="grid" aria-describedby="dynamic-table_info">
                                    <thead>
                                    <tr role="row">
                                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">名称</th>
                                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">联系人</th>
                                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">Email</th>
                                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">联系电话</th>
                                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">传真</th>
                                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">地址</th>
                                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">邮编</th>
                                    </thead>
                                    <tbody>
                                    <#--
                                            vo  value object  拼出来的结果集
                                            dto  参数,给后端当参数,或者返回的dot.
                                            -->
                                    <tr role="row" class="odd">
                                        <td>
                                        ${("众品有限公司")!"null"}
                                        </td>
                                        <td>
                                        ${("王经理")!"null"}
                                        </td>
                                        <td>
                                        ${("mayanlong@hnxianyi.com")!"null"}
                                        </td>
                                        <td>
                                        ${("15668159000")!"null"}
                                        </td>
                                        <td>
                                        ${("0355-56379811")!"null"}
                                        </td>
                                        <td>
                                        ${("河南省郑州市经济开发区田江路110号")!"null"}
                                        </td>
                                        <td>
                                        ${("100100")!"null"}
                                        </td>
                                    </tr>
                                    </tbody>
                                </table>
                                <div class="page-header">
                                    <h4>货品信息</h4>
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
                                        ${(goodsDetails_index+1)!"null"}
                                        </td>
                                        <td>
                                        ${(goodsDetails.goodsCode)!"null"}
                                        </td>
                                        <td>
                                        ${(goodsDetails.goodsName)!"null"}
                                        </td>
                                        <td>
                                        ${(goodsDetails.goodsSpec)!"null"}
                                        </td>
                                        <td>
                                        ${(goodsDetails.unit)!"null"}
                                        </td>
                                        <td>
                                        ${(goodsDetails.productionBatch)!"null"}
                                        </td>
                                        <td>
                                        ${(goodsDetails.productionTime)!"null"}
                                        </td>
                                        <td>
                                        ${(goodsDetails.invalidTime)!"null"}
                                        </td>
                                        <td>
                                        ${(goodsDetails.quantity)!"null"}
                                        </td>
                                    </tr>
                                    </#list>
                                    </tbody>
                                </table>
                                <div class="page-header">
                                    <h4>订单跟踪信息</h4>
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
                                            . ${(orderStatus.notes)!"null"}
                                        </td>
                                    </tr>
                                    </#list>
                                    </tbody>
                                </table>
                            </div><!-- /.dynamic-table_wrapper -->
                        </div>
                        <!-- PAGE CONTENT ENDS -->
                    </div><!-- /.col -->
                </div><!-- /.row -->
            </div><!-- /.page-content -->
        </div>
    </div><!-- /.main-content -->
    <div class="footer">
        <div class="footer-inner">
            <!-- #section:basics/footer -->
            <div class="footer-content">
						<span class="bigger-120">
							<span class="blue bolder">Xescm</span>
							Application © 2016
						</span>

                &nbsp; &nbsp;
                <span class="action-buttons">
							<a href="#">
								<i class="ace-icon fa fa-twitter-square light-blue bigger-150"></i>
							</a>

							<a href="#">
								<i class="ace-icon fa fa-facebook-square text-primary bigger-150"></i>
							</a>

							<a href="#">
								<i class="ace-icon fa fa-rss-square orange bigger-150"></i>
							</a>
						</span>
            </div>

            <!-- /section:basics/footer -->
        </div>
    </div>

    <a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
        <i class="ace-icon fa fa-angle-double-up icon-only bigger-110"></i>
    </a>
</div><!-- /.main-container -->
</body>
</html>
