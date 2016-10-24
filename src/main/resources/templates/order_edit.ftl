<head>
    <title>订单编辑</title>
    <style type="text/css">
        #goodsListDiv {

            position:fixed;

            left:138px;

            top:91px;

            width:946px;

            height:294px;

            z-index:3;

            border:solid #7A7A7A 4px;

        }

        #consignorListDiv {
            position:fixed;

            left:138px;

            top:91px;

            width:946px;

            height:294px;

            z-index:3;

            border:solid #7A7A7A 4px;
        }

        #consigneeListDiv {
            position:fixed;

            left:138px;

            top:91px;

            width:946px;

            height:294px;

            z-index:3;

            border:solid #7A7A7A 4px;
        }


        #supportListDiv {
            position:fixed;

            left:138px;

            top:91px;

            width:946px;

            height:294px;

            z-index:3;

            border:solid #7A7A7A 4px;
        }
    </style>
    <script src="../../js/serialize-to-json.js"></script>
</head>

<body class="no-skin">
<!-- #section:basics/navbar.layout -->
<!--goodsListDiv-->
<div class="modal-content" id="goodsListDiv" style="display: none;">
    <div class="modal-header"><span id="goodsListDivNoneTop" style="cursor:pointer"><button type="button" id="" style="cursor:pointer" class="bootbox-close-button close" data-dismiss="modal" aria-hidden="true">×</button></span>
        <h4 class="modal-title">货品列表</h4></div>
    <div class="modal-body">
        <div class="bootbox-body">
            <form id="goodsSelConditionForm">
                货品编码:<input id="goodsCodeCondition" name="goodsCode" type="text">
                货品名称:<input id="goodsNameCondition" name="goodsName" type="text">
                <button type="button" class="btn btn-info" id="goodsSelectFormBtn" >筛选</button>
            </form>
            <form class="bootbox-form">
                <table id="dynamic-table" class="table table-striped table-bordered table-hover dataTable no-footer" role="grid" aria-describedby="dynamic-table_info">
                    <thead>
                    <tr role="row"><th class="center sorting_disabled" rowspan="1" colspan="1" aria-label="">
                        <label class="pos-rel">
                            <input type="checkbox" class="ace">
                            <span class="lbl"></span>
                        </label>
                    </th>
                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Domain: activate to sort column ascending">序号</th>
                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Price: activate to sort column ascending">货品编码</th>
                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">货品名称</th>
                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">货品规格</th>
                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">单位</th>

                    </thead>
                    <tbody id="goodsSelectListTbody"></tbody>
                </table>


            </form>
        </div>
    </div>
    <div class="modal-footer"><span id="goodsListDivNoneBottom" style="cursor:pointer"><button  data-bb-handler="cancel" type="button" class="btn btn-default">Cancel</button></span><button id="goodsEnter" data-bb-handler="confirm" type="button" class="btn btn-primary">OK</button></div>
</div>
<!--consignorListDiv-->
<div class="modal-content" id="consignorListDiv" style="display: none;">
    <div class="modal-header"><span id="consignorListDivNoneTop" style="cursor:pointer"><button type="button" id="" style="cursor:pointer" class="bootbox-close-button close" data-dismiss="modal" aria-hidden="true">×</button></span>
        <h4 class="modal-title">发货方联系人</h4></div>
    <div class="modal-body">
        <div class="bootbox-body">
            <form id="consignorSelConditionForm">
                名称:<input id="consignorName2" name="contactCompanyName" type="text">
                联系人:<input id="consignorPerson2" name="contactName" type="text">
                联系电话:<input id="consignorPhoneNumber2" name="phone" type="text">
                <input id="purpose2" name="purpose" type="hidden" value="2">
                <button type="button" class="btn btn-info" id="contactSelectFormBtn2" >筛选</button>
            </form>
            <form class="bootbox-form">
                <table id="dynamic-table" class="table table-striped table-bordered table-hover dataTable no-footer" role="grid" aria-describedby="dynamic-table_info">
                    <thead>
                    <tr role="row"><th class="center sorting_disabled" rowspan="1" colspan="1" aria-label="">
                        <label class="pos-rel">
                            选择
                            <span class="lbl"></span>
                        </label>
                    </th>
                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Domain: activate to sort column ascending">序号</th>
                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Price: activate to sort column ascending">名称</th>
                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">联系人</th>
                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">联系电话</th>
                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">传真</th>
                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">Email</th>
                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">邮编</th>
                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">地址</th>

                    </thead>
                    <tbody id="contactSelectListTbody2"></tbody>
                </table>
            </form>
        </div>
    </div>
    <div class="modal-footer"><span id="consignorListDivNoneBottom" style="cursor:pointer"><button  data-bb-handler="cancel" type="button" class="btn btn-default">Cancel</button></span><button data-bb-handler="confirm" type="button" class="btn btn-primary">OK</button></div>
</div>

<!--consigneeListDiv-->
<div class="modal-content" id="consigneeListDiv" style="display: none;">
    <div class="modal-header"><span id="consigneeListDivNoneTop" style="cursor:pointer"><button type="button" id="" style="cursor:pointer" class="bootbox-close-button close" data-dismiss="modal" aria-hidden="true">×</button></span>
        <h4 class="modal-title">收货方联系人</h4></div>
    <div class="modal-body">
        <div class="bootbox-body">
            <form id="consigneeSelConditionForm">
                名称:<input id="consignorName1" name="contactCompanyName" type="text">
                联系人:<input id="consignorPerson1" name="contactName" type="text">
                联系电话:<input id="consignorPhoneNumber1" name="phone" type="text">
                <input id="purpose1" name="purpose" type="hidden" value="1">
                <button type="button" class="btn btn-info" id="contactSelectFormBtn1" >筛选</button>
            </form>
            <form class="bootbox-form">
                <table id="dynamic-table" class="table table-striped table-bordered table-hover dataTable no-footer" role="grid" aria-describedby="dynamic-table_info">
                    <thead>
                    <tr role="row"><th class="center sorting_disabled" rowspan="1" colspan="1" aria-label="">
                        <label class="pos-rel">
                            选择
                            <span class="lbl"></span>
                        </label>
                    </th>
                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Domain: activate to sort column ascending">序号</th>
                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Price: activate to sort column ascending">名称</th>
                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">联系人</th>
                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">联系电话</th>
                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">传真</th>
                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">Email</th>
                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">邮编</th>
                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">地址</th>
                    </thead>
                    <tbody id="contactSelectListTbody1"></tbody>
                </table>


            </form>
        </div>
    </div>
    <div class="modal-footer"><span id="consigneeListDivNoneBottom" style="cursor:pointer"><button  data-bb-handler="cancel" type="button" class="btn btn-default">Cancel</button></span><button data-bb-handler="confirm" type="button" class="btn btn-primary">OK</button></div>
</div>


<!--supportListDiv-->
<div class="modal-content" id="supportListDiv" style="display: none;">
    <div class="modal-header"><span id="supportListDivNoneTop" style="cursor:pointer"><button type="button" id="" style="cursor:pointer" class="bootbox-close-button close" data-dismiss="modal" aria-hidden="true">×</button></span>
        <h4 class="modal-title">供应商联系人</h4></div>
    <div class="modal-body">
        <div class="bootbox-body">
            <form id="supplierSelConditionForm">
                名称:<input id="supplierName" name="supplierName" type="text">
                联系人:<input id="contactName" name="contactName" type="text">
                联系电话:<input id="contactPhone" name="contactPhone" type="text">
                <button type="button" class="btn btn-info" id="supplierSelectFormBtn" >筛选</button>
            </form>
            <form class="bootbox-form">
                <table id="dynamic-table" class="table table-striped table-bordered table-hover dataTable no-footer" role="grid" aria-describedby="dynamic-table_info">
                    <thead>
                    <tr role="row"><th class="center sorting_disabled" rowspan="1" colspan="1" aria-label="">
                        <label class="pos-rel">
                            选择
                            <span class="lbl"></span>
                        </label>
                    </th>
                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Domain: activate to sort column ascending">序号</th>
                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Price: activate to sort column ascending">名称</th>
                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">联系人</th>
                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">联系电话</th>
                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">传真</th>
                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">Email</th>
                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">邮编</th>
                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">地址</th>

                    </thead>
                    <tbody id="supplierSelectListTbody"></tbody>
                </table>
            </form>
        </div>
    </div>
    <div class="modal-footer"><span id="supportListDivNoneBottom" style="cursor:pointer"><button  data-bb-handler="cancel" type="button" class="btn btn-default">Cancel</button></span><button data-bb-handler="confirm" type="button" class="btn btn-primary">OK</button></div>
</div>

<!-- /section:basics/navbar.layout -->
<div class="main-container ace-save-state" id="main-container">


    <div class="main-content">
        <div class="main-content-inner">
            <div class="page-content">
                <div class="page-header">
                    <h1>
                        订单编辑
                    </h1>
                </div>
                <div class="row">
                    <form action="/ofc/orderPlaceCon" method="post" id="orderPlaceConTable">
                        <div class="col-xs-12">

                            <div class="clearfix">

                            </div>
                            <div class="table-header">
                                基本信息
                            </div>

                            <div>
                                <div id="dynamic-table_wrapper" class="dataTables_wrapper form-inline no-footer">
                                    <form action="" method="post" name="orderInfoTable" id="orderInfoTable">
                                        <input type="hidden" name="tag" value="manage">
                                        <input type="hidden" name="orderCode" <#if orderInfo.orderCode?? >value="${orderInfo.orderCode}"</#if>">
                                            <div class="row">
                                            <div id="dynamic-table_filter" class="dataTables_length">
                                                <label>
                                                    &nbsp;&nbsp;&nbsp;订单日期:<input name="orderTime" <#if orderInfo.orderTime?? >value="${((orderInfo.orderTime)?string('yyyy-MM-dd HH:mm:ss'))!}"</#if> type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" onClick="WdatePicker({isShowClear:true,readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})">


                                                    客户订单编号:<input name="custOrderCode" type="text" <#if orderInfo.custOrderCode?? >value="${orderInfo.custOrderCode}"</#if>>
                                                    &nbsp;&nbsp;&nbsp;
                                                    订单类型:

                                                    <select id="orderType" name="orderType" value="${(orderInfo.orderType)!"60"}">

                                                        <option value="60" <#if orderInfo.orderType?? ><#if ((orderInfo.orderType)== '60')>selected="selected"</#if></#if>>运输订单</option>
                                                        <option value="61" <#if orderInfo.orderType?? ><#if ((orderInfo.orderType)== '61')>selected="selected"</#if></#if>>仓配订单</option>
                                                    </select>
                                                    <span id="businessTypeDiv" style="display: none">
                                                        业务类型:
                                                        <select id="" name="businessType">

                                                            <option value="610" <#if orderInfo.businessType?? ><#if ((orderInfo.businessType)== '610')>selected="selected"</#if></#if>>销售出库</option>
                                                            <option value="611" <#if orderInfo.businessType?? ><#if ((orderInfo.businessType)== '611')>selected="selected"</#if></#if>>调拨出库</option>
                                                            <option value="612" <#if orderInfo.businessType?? ><#if ((orderInfo.businessType)== '612')>selected="selected"</#if></#if>>报损出库</option>
                                                            <option value="613" <#if orderInfo.businessType?? ><#if ((orderInfo.businessType)== '613')>selected="selected"</#if></#if>>其他出库</option>
                                                            <option value="----------">----------</option>
                                                            <option value="620" <#if orderInfo.businessType?? ><#if ((orderInfo.businessType)== '620')>selected="selected"</#if></#if>>采购入库</option>
                                                            <option value="621" <#if orderInfo.businessType?? ><#if ((orderInfo.businessType)== '621')>selected="selected"</#if></#if>>调拨入库</option>
                                                            <option value="622" <#if orderInfo.businessType?? ><#if ((orderInfo.businessType)== '622')>selected="selected"</#if></#if>>退货入库</option>
                                                            <option value="623" <#if orderInfo.businessType?? ><#if ((orderInfo.businessType)== '623')>selected="selected"</#if></#if>>加工入库</option>
                                                        </select>
                                                    </span>
                                                    <span id="provideTransportDiv" style="display: none">
                                                        是否需要运输
                                                        <input id="provideTransport" type="checkbox" name = "" <#if orderInfo.provideTransport?? ><#if ((orderInfo.provideTransport) == 1)> checked="checked"</#if></#if>/>
                                                    </span>
                                                    <input id="provideTransport1" type="hidden" name="provideTransport" value="${(orderInfo.provideTransport)!""}"/>
                                                    店铺:
                                                    <select id="" name="storeCode" value="${(orderInfo.storeName)!""}">

                                                        <option value="线下销售" <#if orderInfo.storeName?? ><#if ((orderInfo.storeName)== '线下销售')>selected="selected"</#if></#if>>线下销售</option>
                                                        <option value="众品天猫生鲜旗舰店" <#if orderInfo.storeName?? ><#if ((orderInfo.storeName)== '众品天猫生鲜旗舰店')>selected="selected"</#if></#if>>众品天猫生鲜旗舰店</option>
                                                        <option value="众品京东旗舰店" <#if orderInfo.storeName?? ><#if ((orderInfo.storeName)== '众品京东旗舰店')>selected="selected"</#if></#if>>众品京东旗舰店</option>
                                                    </select>


                                                </label>
                                            </div>

                                            <div id="dynamic-table_filter" class="dataTables_length">
                                                <label>
                                                    &nbsp;&nbsp;&nbsp;备注:<input  name="notes" value="${(orderInfo.notes)!""}"  type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table">
                                                </label>
                                            </div>

                                            <br/>

                                        </div>
                                    </form>


                                    <div class="col-sm-6">
                                        <!-- #section:elements.tab.option -->
                                        <div class="tabbable" style="float: left; text-align: left; margin: 0 auto; width: 1300px;" >
                                            <ul class="nav nav-tabs padding-12 tab-color-blue background-blue" id="myTab4">
                                                <li class="active">
                                                    <a data-toggle="tab" href="#home4" aria-expanded="false">货品明细</a>
                                                </li>

                                                <li class="tranfr">
                                                    <a data-toggle="tab" href="#profile4" aria-expanded="true">运输信息</a>
                                                </li>

                                                <li class="storeLi" style="display:none">
                                                    <a data-toggle="tab" href="#dropdown14" aria-expanded="false">仓配信息</a>
                                                </li>
                                            </ul>

                                            <div class="tab-content">
                                                <div id="home4" class="tab-pane active">

                                                    <!--货品明细-->

                                                    <span style="cursor:pointer" id="goodsListDivBlock"><button type="button" class="btn btn-info" id="bootbox-confirm">添加货品</button></span>

                                                    <table id="dynamic-table" class="table table-striped table-bordered table-hover dataTable no-footer" role="grid" aria-describedby="dynamic-table_info">
                                                        <thead>
                                                        <tr role="row"><th class="center sorting_disabled" rowspan="1" colspan="1" aria-label="">
                                                            操作
                                                        </th>
                                                            <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Domain: activate to sort column ascending">选择</th>
                                                            <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Domain: activate to sort column ascending">货品编码</th>
                                                            <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Price: activate to sort column ascending">货品名称</th>
                                                            <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Update: activate to sort column ascending">货品规格
                                                            </th>
                                                            <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">单位</th>
                                                            <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">数量</th>
                                                            <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">生产批次</th>
                                                            <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">
                                                                <i class="ace-icon fa fa-clock-o bigger-110 hidden-480"></i>
                                                                生产日期</th>
                                                            <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">
                                                                <i class="ace-icon fa fa-clock-o bigger-110 hidden-480"></i>
                                                                失效日期</th>


                                                        </thead>
                                                        <tbody>
                                                        <#--货品明细-->
                                                        <#list ofcGoodsDetailsList! as goodsDetails>
                                                        <tr role="row" class="odd" align="center">
                                                            <td class="center">
                                                                <button type="button" id=""    class="btn btn-minier btn-danger" onclick="deleteGoods('${(goodsDetails.orderCode)!"null"}','${goodsDetails.goodsCode!"null"}')">删除</button>
                                                            </td>
                                                            <td>
                                                                <input id="deleteOrNot" type="checkbox" />
                                                            </td>
                                                            <td>
                                                                ${(goodsDetails.goodsCode)!""}
                                                            </td>
                                                            <td>
                                                                ${(goodsDetails.goodsName)!""}
                                                            </td>
                                                            <td class="hidden-480">
                                                                ${(goodsDetails.goodsSpec)!""}
                                                            </td>
                                                            <td>
                                                                ${(goodsDetails.unit)!""}
                                                            </td>
                                                            <td class="hidden-480">
                                                                ${(goodsDetails.quantity)!""}
                                                            </td>
                                                            <td class="hidden-480">
                                                                ${(goodsDetails.productionBatch)!""}
                                                            </td>
                                                            <td class="hidden-480">
                                                                ${(goodsDetails.productionTime)!""}
                                                            </td>
                                                            <td class="hidden-480">
                                                                ${(goodsDetails.invalidTime)!""}
                                                            </td>

                                                        </tr>
                                                        </#list>
                                                        </tbody>
                                                        <tbody id="goodsInfoListDiv"></tbody>
                                                    </table>
                                                    <div class="row">
                                                        <div class="col-xs-6">
                                                            <div class="dataTables_info" id="dynamic-table_info" role="status" aria-live="polite"></div></div><div class="col-xs-6"><div class="dataTables_paginate paging_simple_numbers" id="dynamic-table_paginate"><ul class="pagination"><li class="paginate_button previous disabled" aria-controls="dynamic-table" tabindex="0" id="dynamic-table_previous"><a href="#">Previous</a></li><li class="paginate_button active" aria-controls="dynamic-table" tabindex="0"><a href="#">1</a></li><li class="paginate_button " aria-controls="dynamic-table" tabindex="0"><a href="#">2</a></li><li class="paginate_button " aria-controls="dynamic-table" tabindex="0"><a href="#">3</a></li><li class="paginate_button next" aria-controls="dynamic-table" tabindex="0" id="dynamic-table_next"><a href="#">Next</a></li></ul></div></div></div>

                                                </div>

                                                <div id="profile4" class="tab-pane">

                                                    <div class="page-header">
                                                        <h4>运输基本信息</h4>
                                                    </div>

                                                    <div class="row">

                                                        <div id="dynamic-table_filter" class="dataTables_length">
                                                            <label>
                                                                &nbsp;&nbsp;&nbsp;

                                                                数量:<input name="quantity" <#if orderInfo.quantity?? >value="${orderInfo.quantity}"</#if> type="text" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" >


                                                                重量:<input name="weight" <#if orderInfo.weight?? >value="${orderInfo.weight}"</#if> type="text" class="form-control input-sm" placeholder="" aria-controls="dynamic-table">
                                                                体积:<input name="cubage" <#if orderInfo.cubage?? >value="${orderInfo.cubage}"</#if> type="text" class="form-control input-sm" placeholder="" aria-controls="dynamic-table">(长*宽*高,单位:cm)<br/>
                                                                合计标准箱:<input name="totalStandardBox" <#if orderInfo.totalStandardBox?? >value="${orderInfo.totalStandardBox}"</#if> type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table"><br/>
                                                                &nbsp;&nbsp;&nbsp;
                                                                出发地:
                                                                <input name="departurePlace" <#if orderInfo.departurePlace?? >value="${orderInfo.departurePlace}"</#if> type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" readonly="readonly">
                                                                目的地:
                                                                <input name="destination" <#if orderInfo.destination?? >value="${orderInfo.destination}"</#if> type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" readonly="readonly">
                                                                取货时间:
                                                                <input name="pickupTime" <#if orderInfo.pickupTime?? >value="${((orderInfo.pickupTime)?string('yyyy-MM-dd HH:mm:ss'))!}"</#if> type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" onClick="WdatePicker({isShowClear:true,readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})">
                                                                期望送达时间:
                                                                <input name="expectedArrivedTime" <#if orderInfo.expectedArrivedTime?? >value="${((orderInfo.expectedArrivedTime)?string('yyyy-MM-dd HH:mm:ss'))!}"</#if> type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" onClick="WdatePicker({isShowClear:true,readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})">
                                                                是否加急:<input id="urgent" type="checkbox" name="" <#if orderInfo.urgent?? ><#if ((orderInfo.urgent) == 1)> checked="checked"</#if></#if>/>
                                                                <input id="urgent1" type="hidden" name="urgent" value="${(orderInfo.urgent)!""}"/>
                                                            </label>
                                                        </div>

                                                        <div id="dynamic-table_filter" class="dataTables_length">
                                                            <label>
                                                                &nbsp;&nbsp;&nbsp;运输要求:<input name="transRequire" <#if orderInfo.transRequire?? >value="${orderInfo.transRequire}"</#if> type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" onClick="WdatePicker()">
                                                            </label>
                                                        </div>

                                                        <br/>

                                                    </div>

                                                    <div class="page-header">
                                                        <h4>发货方信息</h4>
                                                    </div>
                                                    <span style="cursor:pointer" id="consignorListDivBlock"><button type="button" class="btn btn-info" id="bootbox-confirm">选择</button></span>
                                                    <div class="">
                                                        名称:
                                                        <input name="consignorName" <#if orderInfo.consignorName?? >value="${orderInfo.consignorName}"</#if> type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" >
                                                        联系人:
                                                        <input name="" type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" >
                                                        联系电话:
                                                        <input name="" type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" >
                                                        传真:
                                                        <input name="" type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" >
                                                        Email:
                                                        <input name="" type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" >
                                                        <br/>邮编:
                                                        <input name="" type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" >
                                                        地址:
                                                        <select><option value="">--省--</option></select>
                                                        <select><option value="">--市--</option></select>
                                                        <select><option value="">--区/县--</option></select>
                                                        <select><option value="">--乡镇/街道--</option></select>
                                                        <input name="" type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" >

                                                    </div>


                                                    <div class="page-header">
                                                        <h4>收货方信息</h4>
                                                    </div>
                                                    <span style="cursor:pointer" id="consigneeListDivBlock"><button type="button" class="btn btn-info" id="bootbox-confirm">选择</button></span>
                                                    <div class="">
                                                        名称:
                                                        <input name="consigneeName" <#if orderInfo.consigneeName?? >value="${orderInfo.consigneeName}"</#if> type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" >
                                                        联系人:
                                                        <input name="" type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" >
                                                        联系电话:
                                                        <input name="" type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" >
                                                        传真:
                                                        <input name="" type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" >
                                                        Email:
                                                        <input name="" type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" >
                                                        <br/>邮编:
                                                        <input name="" type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" >
                                                        地址:
                                                        <select><option value="">--省--</option></select>
                                                        <select><option value="">--市--</option></select>
                                                        <select><option value="">--区/县--</option></select>
                                                        <select><option value="">--乡镇/街道--</option></select>
                                                        <input name="" type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" >

                                                    </div>

                                                </div>

                                                <div id="dropdown14" class="tab-pane">

                                                    <div class="page-header">
                                                        <h4>仓配基本信息</h4>
                                                    </div>
                                                    <div class="row">
                                                        <div id="dynamic-table_filter" class="dataTables_length">
                                                            <label>
                                                                &nbsp;&nbsp;&nbsp;仓库名称:
                                                                <select id="" name="wareHouseName"  value="${(orderInfo.wareHouseName)!""}">
                                                                    <option value="">----</option>
                                                                    <option value="仓库1" <#if orderInfo.wareHouseName?? ><#if ((orderInfo.wareHouseName)== '0')>selected="selected"</#if></#if>>仓库1</option>
                                                                    <option value="仓库2" <#if orderInfo.wareHouseName?? ><#if ((orderInfo.wareHouseName)== '1')>selected="selected"</#if></#if>>仓库2</option>
                                                                </select>

                                                                入库预计到达时间:<input name="arriveTime" <#if orderInfo.arriveTime?? >value="${((orderInfo.arriveTime)?string('yyyy-MM-dd HH:mm:ss'))!}"</#if>  type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" onClick="WdatePicker({isShowClear:true,readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})">
                                                                车牌号:<input name="plateNumber" <#if orderInfo.plateNumber?? >value="${orderInfo.plateNumber}"</#if> type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table">
                                                                司机姓名:<input name="driverName" <#if orderInfo.driverName?? >value="${orderInfo.driverName}"</#if> type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table"><br/>
                                                                &nbsp;&nbsp;&nbsp;
                                                                联系电话:
                                                                <input name="contactNumber" <#if orderInfo.contactNumber?? >value="${orderInfo.contactNumber}"</#if> type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table">
                                                            </label>
                                                        </div>
                                                        <br/>
                                                    </div>
                                                    <div class="page-header">
                                                        <h4>供应商信息</h4>
                                                    </div>
                                                    <span style="cursor:pointer" id="supportListDivBlock"><button type="button" class="btn btn-info" id="bootbox-confirm">选择</button></span>
                                                    <div class="">
                                                        名称:
                                                        <input name="supportName" <#if orderInfo.supportName?? >value="${orderInfo.supportName}"</#if> type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" >
                                                        联系人:
                                                        <input name="" type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" >
                                                        联系电话:
                                                        <input name="" type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" >
                                                        传真:
                                                        <input name="" type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" >
                                                        Email:
                                                        <input name="" type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" >
                                                        <br/>邮编:
                                                        <input name="" type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" >
                                                        地址:
                                                        <select><option value="">--省--</option></select>
                                                        <select><option value="">--市--</option></select>
                                                        <select><option value="">--区/县--</option></select>
                                                        <select><option value="">--乡镇/街道--</option></select>
                                                        <input name="" type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" >

                                                    </div>
                                                </div>
                                            </div>
                                        </div>

                                        <!-- /section:elements.tab.option -->
                                    </div>
                                </div>
                            </div>
                        </div>

                        <button type="button" class="btn btn-info" id="bootbox-confirm" onclick="subOrder('${orderInfo}')">保存修改</button>

                    </form>
                </div>
            </div><!-- /.page-content -->
        </div>
    </div><!-- /.main-content -->


</div><!-- /.main-container -->


<!-- basic scripts -->

<!--[if !IE]> -->
<!-- <![endif]-->


<!-- inline scripts related to this page -->
<script type="text/javascript">

    $(function(){
        $("#goodsSelectFormBtn").click(function () {
            CommonClient.post(sys.rootPath + "/ofc/goodsSelect", $("#goodsSelConditionForm").serialize(), function(data) {
                data=eval(data);
                var goodsList = "";
                $.each(data,function (index,cscGoods) {
                    goodsList =goodsList + "<tr role='row' class='odd' align='center'>";
                    goodsList =goodsList + "<td class='center'> "+"<label class='pos-rel'>"+"<input type='checkbox' class='ace'>"+"<span class='lbl'></span>"+"</label>"+"</td>";
                    goodsList =goodsList + "<td>"+cscGoods.goodsCode+"</td>";
                    goodsList =goodsList + "<td>"+cscGoods.goodsName+"</td>";
                    goodsList =goodsList + "<td>"+cscGoods.specification+"</td>";
                    goodsList =goodsList + "<td>"+cscGoods.unit+"</td>";
                    goodsList =goodsList + "</tr>";

                });
                $("#goodsSelectListTbody").html(goodsList);
            },"json");
        });

        $("#contactSelectFormBtn2").click(function () {
            //$.post("/ofc/contactSelect",$("#consignorSelConditionForm").serialize(),function (data) {
            CommonClient.post(sys.rootPath + "/ofc/contactSelect", $("#consignorSelConditionForm").serialize(), function(data) {
                data=eval(data);
                var contactList = "";
                $.each(data,function (index,CscContantAndCompanyDto) {
                    contactList =contactList + "<tr role='row' class='odd' align='center'>";
                    contactList =contactList + "<td class='center'> "+"<label class='pos-rel'>"+"<input type='checkbox' class='ace'>"+"<span class='lbl'></span>"+"</label>"+"</td>";
                    contactList =contactList + "<td>"+(index+1)+"</td>";
                    contactList =contactList + "<td>"+CscContantAndCompanyDto.contactCompanyName+"</td>";
                    contactList =contactList + "<td>"+CscContantAndCompanyDto.contactName+"</td>";
                    contactList =contactList + "<td>"+CscContantAndCompanyDto.phone+"</td>";
                    contactList =contactList + "<td>"+CscContantAndCompanyDto.fax+"</td>";
                    contactList =contactList + "<td>"+CscContantAndCompanyDto.email+"</td>";
                    contactList =contactList + "<td>"+CscContantAndCompanyDto.postCode+"</td>";
                    contactList =contactList + "<td>"+CscContantAndCompanyDto.address+"</td>";
                    contactList =contactList + "</tr>";
                });
                $("#contactSelectListTbody2").html(contactList);
            },"json");
        });

        $("#contactSelectFormBtn1").click(function () {
            CommonClient.post(sys.rootPath + "/ofc/contactSelect", $("#consigneeSelConditionForm").serialize(), function(data) {
                data=eval(data);
                var contactList = "";
                $.each(data,function (index,CscContantAndCompanyDto) {
                    contactList =contactList + "<tr role='row' class='odd' align='center'>";
                    contactList =contactList + "<td class='center'> "+"<label class='pos-rel'>"+"<input type='checkbox' class='ace'>"+"<span class='lbl'></span>"+"</label>"+"</td>";
                    contactList =contactList + "<td>"+(index+1)+"</td>";
                    contactList =contactList + "<td>"+CscContantAndCompanyDto.contactCompanyName+"</td>";
                    contactList =contactList + "<td>"+CscContantAndCompanyDto.contactName+"</td>";
                    contactList =contactList + "<td>"+CscContantAndCompanyDto.phone+"</td>";
                    contactList =contactList + "<td>"+CscContantAndCompanyDto.fax+"</td>";
                    contactList =contactList + "<td>"+CscContantAndCompanyDto.email+"</td>";
                    contactList =contactList + "<td>"+CscContantAndCompanyDto.postCode+"</td>";
                    contactList =contactList + "<td>"+CscContantAndCompanyDto.address+"</td>";
                    contactList =contactList + "</tr>";
                    $("#contactSelectListTbody1").html(contactList);
                });
            },"json");
        });

        $("#supplierSelectFormBtn").click(function () {

            //$.post("/ofc/supplierSelect",$("#supplierSelConditionForm").serialize(),function (data) {
            CommonClient.post(sys.rootPath + "/ofc/supplierSelect", $("#supplierSelConditionForm").serialize(), function(data) {
                data=eval(data);
                var supplierList = "";
                $.each(data,function (index,CscSupplierInfoDto) {
                    supplierList =supplierList + "<tr role='row' class='odd' align='center'>";
                    supplierList =supplierList + "<td class='center'> "+"<label class='pos-rel'>"+"<input id='selGoods' type='checkbox' class='ace'>"+"<span class='lbl'></span>"+"</label>"+"</td>";
                    supplierList =supplierList + "<td>"+(index+1)+"</td>";
                    supplierList =supplierList + "<td>"+CscSupplierInfoDto.supplierName+"</td>";
                    supplierList =supplierList + "<td>"+CscSupplierInfoDto.contactName+"</td>";
                    supplierList =supplierList + "<td>"+CscSupplierInfoDto.contactPhone+"</td>";
                    supplierList =supplierList + "<td>"+CscSupplierInfoDto.fax+"</td>";
                    supplierList =supplierList + "<td>"+CscSupplierInfoDto.email+"</td>";
                    supplierList =supplierList + "<td>"+CscSupplierInfoDto.postCode+"</td>";
                    supplierList =supplierList + "<td>"+CscSupplierInfoDto.address+"</td>";
                    supplierList =supplierList + "</tr>";
                    $("#supplierSelectListTbody").html(supplierList);
                });
            },"json");
        });

        $("#goodsEnter").click(function () {
            var goodsInfoListDiv = "";
            $("#goodsSelectListTbody").find("tr").each(function(index){
                var tdArr = $(this).children();
                if(tdArr.eq(0).find("input").prop("checked")){
                    var goodsCode = tdArr.eq(2).text();//货品编码
                    var goodsName = tdArr.eq(3).text();//货品名称
                    var specification = tdArr.eq(4).text();//    货品规格
                    var unit = tdArr.eq(5).text();//    单位
                    goodsInfoListDiv =goodsInfoListDiv + "<tr role='row' class='odd' align='center'>";
                    goodsInfoListDiv =goodsInfoListDiv + "<td><button type='button' onclick='deleteGood(this)' class='btn btn-minier btn-danger'>删除</button></td>";
                    goodsInfoListDiv =goodsInfoListDiv + "<td><input id='deleteOrNot' type='checkbox'/></td>";
                    goodsInfoListDiv =goodsInfoListDiv + "<td>"+goodsCode+"</td>";
                    goodsInfoListDiv =goodsInfoListDiv + "<td>"+goodsName+"</td>";
                    goodsInfoListDiv =goodsInfoListDiv + "<td>"+specification+"</td>";
                    goodsInfoListDiv =goodsInfoListDiv + "<td>"+unit+"</td>";
                    goodsInfoListDiv =goodsInfoListDiv + "<td></td>";
                    goodsInfoListDiv =goodsInfoListDiv + "<td></td>";
                    goodsInfoListDiv =goodsInfoListDiv + "<td></td>";
                    goodsInfoListDiv =goodsInfoListDiv + "<td></td>";
                    goodsInfoListDiv =goodsInfoListDiv + "</tr>";
                    $("#goodsInfoListDiv").html(goodsInfoListDiv);
                }
                if(goodsInfoListDiv==""){
                    alert("请至少选择一行");
                }
            });
        });

        $("#goodsListDivBlock").click(function(){

            $("#goodsListDiv").fadeIn("slow");//淡入淡出效果 显示div

        });

        $("#goodsListDivNoneTop").click(function(){

            $("#goodsListDiv").fadeOut("slow");//淡入淡出效果 隐藏div

        });

        $("#goodsListDivNoneBottom").click(function(){

            $("#goodsListDiv").fadeOut("slow");//淡入淡出效果 隐藏div

        });


        $("#consignorListDivBlock").click(function(){

            $("#consignorListDiv").fadeIn("slow");//淡入淡出效果 显示div

        });

        $("#consignorListDivNoneTop").click(function(){

            $("#consignorListDiv").fadeOut("slow");//淡入淡出效果 隐藏div

        });

        $("#consignorListDivNoneBottom").click(function(){

            $("#consignorListDiv").fadeOut("slow");//淡入淡出效果 隐藏div

        });

        $("#consigneeListDivBlock").click(function(){

            $("#consigneeListDiv").fadeIn("slow");//淡入淡出效果 显示div

        });

        $("#consigneeListDivNoneTop").click(function(){

            $("#consigneeListDiv").fadeOut("slow");//淡入淡出效果 隐藏div

        });

        $("#consigneeListDivNoneBottom").click(function(){

            $("#consigneeListDiv").fadeOut("slow");//淡入淡出效果 隐藏div

        });

        $("#supportListDivBlock").click(function(){

            $("#supportListDiv").fadeIn("slow");//淡入淡出效果 显示div

        });

        $("#supportListDivNoneTop").click(function(){

            $("#supportListDiv").fadeOut("slow");//淡入淡出效果 隐藏div

        });

        $("#supportListDivNoneBottom").click(function(){

            $("#supportListDiv").fadeOut("slow");//淡入淡出效果 隐藏div

        });

        if($("#orderType").val()== '60'){
            $("#provideTransportDiv").hide();
            $("#businessTypeDiv").hide();
            $('.tranfr').show();
            $('.storeLi').hide();
        }
        if($("#orderType").val()== '61'){
            $("#provideTransportDiv").show();
            $("#businessTypeDiv").show();
            $('.storeLi').show();
            $('.tranfr').hide();
            if($("#provideTransport").prop("checked")){
                $('.tranfr').show();
            }else{
                $('.tranfr').hide();
            }
        }

        $("#orderType").change(function () {
            if($(this).children('option:selected').val() == '61'){
                $("#provideTransportDiv").show();
                $("#businessTypeDiv").show();
                $('.storeLi').show();
                $('.tranfr').hide();

            }
            if($(this).children('option:selected').val() == '60'){

                $("#provideTransportDiv").hide();
                $("#businessTypeDiv").hide();
                $('.tranfr').show();
                $('.storeLi').hide();
            }
        });
        $("#provideTransport").change(function () {
            if($(this).prop("checked")){
                $('.tranfr').show();
                $("#provideTransport1").val(1);
            }else{
                $('.tranfr').hide();
                $("#provideTransport1").val(0);
            }
        });
        $("#urgent").change(function () {
            if($(this).prop("checked")){
                $("#urgent1").val(1);
            }else{
                $("#urgent1").val(0);
            }
        });

    });
    function subOrder(orderInfo) {
        /*跳转到订单的可编辑页(跟下单页面一样!), 并回显该订单数据*/
        //alert(orderInfo);
        //DataDeal.formToJson(this.serialise())
        var ofcOrderDTOJson = DataDeal.formToJson($("form[name='orderInfoTable']").serialize());
        alert(ofcOrderDTOJson);
        var tag="orderCode";
        xescm.common.loadPage("/ofc/orderPlaceCon/"+tag+"/"+ofcOrderDTOJson);
        //$("#orderPlaceConTable").submit();
    }

    function deleteGoods(ordercode,goodsCode) {
        var result  = confirm("您确定要删除此货品?");
        if(result == true) {
            $.get("/ofc/goodsDelete",{"orderCode":ordercode,"goodsCode":goodsCode},function (data) {
                $("#confirmBox").modal('hide');
                if(data == 200){
                    window.location.href="/orderScreenByCondition?tag=manage";
                } else {
                    alert("删除货品失败,请联系管理员!");
                }
            });
        }


    }
    function deleteGood(obj) {
        //alert(obj);
        $(obj).parent().parent().remove();
        /*var goodsInfoListDiv = "";
        $("#goodsInfoListDiv").find("tr").each(function(index){
            var tdArr = $(this).children();
            if(tdArr.eq(1).find("input").prop("checked")){
                tdArr.parent().remove();
            }
        });*/
    }

</script>

</body>