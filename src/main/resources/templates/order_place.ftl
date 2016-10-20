<title>我要下单</title>
<!-- <link rel="stylesheet" href="${request.contextPath}/static/plugins/laypage/skin/laypage.css" /> -->
<!-- ajax layout which only needs content area -->
<div class="page-header">
    <p>
        我要下单
    </p>
</div>
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
<!-- #section:basics/navbar.layout -->
<!--goodsListDiv-->
<div class="modal-content" id="goodsListDiv" style="display: none;">
    <div class="modal-header"><span id="goodsListDivNoneTop" style="cursor:pointer"><button type="button" id="" style="cursor:pointer" class="bootbox-close-button close" data-dismiss="modal" aria-hidden="true">×</button></span>
        <h4 class="modal-title">货品列表</h4></div>
    <div class="modal-body">
        <div class="bootbox-body">
            <form>
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
    <div class="modal-footer"><span id="goodsListDivNoneBottom" style="cursor:pointer"><button  data-bb-handler="cancel" type="button" class="btn btn-default">Cancel</button></span><button data-bb-handler="confirm" type="button" class="btn btn-primary">OK</button></div>
</div>
<!--consignorListDiv-->
<div class="modal-content" id="consignorListDiv" style="display: none;">
    <div class="modal-header"><span id="consignorListDivNoneTop" style="cursor:pointer"><button type="button" id="" style="cursor:pointer" class="bootbox-close-button close" data-dismiss="modal" aria-hidden="true">×</button></span>
        <h4 class="modal-title">发货方联系人</h4></div>
    <div class="modal-body">
        <div class="bootbox-body">
            <form>
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
            <form>
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
            <form>
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
<div class="row">
    <form action="/ofc/orderPlaceCon" method="post" id="orderPlaceConTable">
        <div class="col-xs-12">

            <div class="clearfix">

            </div>
            <div class="table-header">
                基本信息
            </div>

            <!-- div.table-responsive -->

            <!-- div.dataTables_borderWrap -->
            <div>
                <div id="dynamic-table_wrapper" class="dataTables_wrapper form-inline no-footer">
                    <form action="" method="post" id="">
                        <input type="hidden" name="tag" value="place">
                        <div class="row">

                            <div id="dynamic-table_filter" class="dataTables_length">
                                <label>
                                    &nbsp;&nbsp;&nbsp;订单日期:<input name="orderTime" type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" onClick="WdatePicker({isShowClear:true,readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})">


                                    客户订单编号:<input name="custOrderCode" type="text">

                                    &nbsp;&nbsp;&nbsp;
                                    订单类型:
                                    <select id="orderTypeSel" name="orderType">
                                        <option value="60">运输订单</option>
                                        <option value="61">仓配订单</option>
                                    </select>
                                    <span id="businessTypeDiv" style="display: none">
                                                    业务类型:
                                                    <select id="" name="businessType">
                                                        <option value="610">销售出库</option>
                                                        <option value="611">调拨出库</option>
                                                        <option value="612">报损出库</option>
                                                        <option value="613">其他出库</option>
                                                        <option value="----------">----------</option>
                                                        <option value="620">采购入库</option>
                                                        <option value="621">调拨入库</option>
                                                        <option value="622">退货入库</option>
                                                        <option value="623">加工入库</option>
                                                    </select>
                                                        </span>

                                    <span id="provideTransportDiv" style="display: none">
                                                        是否需要运输
                                                        <input  id="provideTransport" type="checkbox" name = "provideTransport" onclick="this.value=this.checked?1:0"/>
                                                    </span>
                                    店铺:
                                    <select id="" name="storeCode">
                                        <option value="线下销售">线下销售</option>
                                        <option value="众品天猫生鲜旗舰店">众品天猫生鲜旗舰店</option>
                                        <option value="众品京东旗舰店">众品京东旗舰店</option>
                                    </select>


                                </label>
                            </div>

                            <div id="dynamic-table_filter" class="dataTables_length">
                                <label>
                                    &nbsp;&nbsp;&nbsp;备注:<input  name="notes"  type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" >
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

                                <li class="transLi">
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
                                            <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Status: activate to sort column ascending">序号</th>
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

                                        <tr role="row" class="odd">
                                            <td class="center">
                                                <button type="button" id=""    class="btn btn-minier btn-danger">删除</button>
                                            </td>
                                            <td>
                                                序号

                                            </td>

                                            <td>
                                                <input name="goodsCode" type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" size="6">
                                            </td>
                                            <td>
                                                <input name="goodsName" type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" size="6">
                                            </td>
                                            <td class="hidden-480">
                                                <input name="goodsSpec" type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" size="6">
                                            </td>
                                            <td>
                                                <input name="unit" type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" size="6">
                                            </td>
                                            <td class="hidden-480">
                                                <input name="quantity" type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" size="6">
                                            </td>
                                            <td class="hidden-480">

                                                <input name="productionBatch" type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" size="6">
                                            </td>
                                            <td class="hidden-480">
                                                <input name="" type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" size="6">
                                            </td>
                                            <td class="hidden-480">
                                                <input name="" type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" size="6">
                                            </td>


                                        </tr>

                                        </tbody>
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

                                                数量:<input name="quantity" type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" >


                                                重量:<input name="weight" type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table">
                                                体积:<input name="cubage" type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table">(长*宽*高,单位:cm)<br/>
                                                合计标准箱:<input name="totalStandardBox" type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table"><br/>

                                                &nbsp;&nbsp;&nbsp;
                                                出发地:
                                                <input name="departurePlace" type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" readonly="readonly">
                                                目的地:
                                                <input name="destination" type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" readonly="readonly">
                                                取货时间:
                                                <input name="pickupTime" type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" onClick="WdatePicker({isShowClear:true,readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})">
                                                期望送达时间:
                                                <input name="expectedArrivedTime" type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" onClick="WdatePicker({isShowClear:true,readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})">
                                                是否加急:<input type="checkbox" name="urgent" value="0" onclick="this.value=this.checked?1:0"/>

                                            </label>
                                        </div>

                                        <div id="dynamic-table_filter" class="dataTables_length">
                                            <label>
                                                &nbsp;&nbsp;&nbsp;运输要求:<input name="transRequire" type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" onClick="WdatePicker()">
                                            </label>
                                        </div>

                                        <br/>

                                    </div>

                                    <div class="page-header">
                                        <h4>发货方信息</h4>
                                    </div>
                                    <span style="cursor:pointer" id="consignorListDivBlock"><button type="button" class="btn btn-info" id="consignorselbtn">选择</button></span>
                                    <div class="">
                                        名称:
                                        <input name="consignorName" type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" >
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
                                    <span style="cursor:pointer" id="consigneeListDivBlock"><button type="button" class="btn btn-info" id="consigneeselbtn">选择</button></span>
                                    <div class="">
                                        名称:
                                        <input name="consigneeName" type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" >
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
                                                &nbsp;&nbsp;&nbsp;
                                                仓库名称:<select id="" name="wareHouseName">
                                                <option value="">----</option>
                                                <option value="仓库1">仓库1</option>
                                                <option value="仓库2">仓库2</option>
                                            </select>
                                                入库预计到达时间:<input name="arriveTime" type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" onClick="WdatePicker({isShowClear:true,readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})">
                                                车牌号:<input name="plateNumber" type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table">
                                                司机姓名:<input name="driverName" type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table"><br/>
                                                &nbsp;&nbsp;&nbsp;
                                                联系电话:
                                                <input name="contactNumber" type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table">
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
                                        <input name="supportName" type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table" >
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

        <button type="button" class="btn btn-info" id="bootbox-confirm" onclick="document.getElementById('orderPlaceConTable').submit();">确认下单</button>

    </form>
</div>

<!-- inline scripts related to this page -->
<script type="text/javascript">

    $(function(){

        $("#goodsSelectFormBtn").click(function () {
            var goodsCode = document.getElementById("goodsCodeCondition").value;
            var goodsName = document.getElementById("goodsNameCondition").value;
            $.post("/ofc/goodsSelect",{"goodsCode":goodsCode,"goodsName":goodsName},function (data) {
                data=eval(data);
                var goodsList = "";
                $.each(data,function (index,cscGoods) {
                    goodsList =goodsList + "<tr role='row' class='odd'>";
                    goodsList =goodsList + "<td class='center'> "+"<label class='pos-rel'>"+"<input type='checkbox' class='ace'>"+"<span class='lbl'></span>"+"</label>"+"</td>";
                    goodsList =goodsList + "<td>"+(index+1)+"</td>";
                    goodsList =goodsList + "<td>"+cscGoods.goodsCode+"</td>";
                    goodsList =goodsList + "<td>"+cscGoods.goodsName+"</td>";
                    goodsList =goodsList + "<td>"+cscGoods.specification+"</td>";
                    goodsList =goodsList + "<td>"+cscGoods.unit+"</td>";
                });
                $("#goodsSelectListTbody").html(goodsList);
            },"json");
        });

        $("#contactSelectFormBtn2").click(function () {
            var contactCompanyName = document.getElementById("consignorName2").value;
            var contactName = document.getElementById("consignorPerson2").value;
            var phone = document.getElementById("consignorPhoneNumber2").value;
            var purpose = document.getElementById("purpose2").value;
            $.post("/ofc/contactSelect",{"contactCompanyName":contactCompanyName,"contactName":contactName,
            "phone":phone,"purpose":purpose},function (data) {
                data=eval(data);
                var contactList = "";
                $.each(data,function (index,CscContantAndCompanyDto) {
                    contactList =contactList + "<tr role='row' class='odd'>";
                    contactList =contactList + "<td class='center'> "+"<label class='pos-rel'>"+"<input type='checkbox' class='ace'>"+"<span class='lbl'></span>"+"</label>"+"</td>";
                    contactList =contactList + "<td>"+(index+1)+"</td>";
                    contactList =contactList + "<td>"+CscContantAndCompanyDto.contactCompanyName+"</td>";
                    contactList =contactList + "<td>"+CscContantAndCompanyDto.contactName+"</td>";
                    contactList =contactList + "<td>"+CscContantAndCompanyDto.phone+"</td>";
                    contactList =contactList + "<td>"+CscContantAndCompanyDto.fax+"</td>";
                    contactList =contactList + "<td>"+CscContantAndCompanyDto.email+"</td>";
                    contactList =contactList + "<td>"+CscContantAndCompanyDto.postCode+"</td>";
                    contactList =contactList + "<td>"+CscContantAndCompanyDto.address+"</td>";
                });
                $("#contactSelectListTbody2").html(contactList);
            },"json");
        });

        $("#contactSelectFormBtn1").click(function () {
            var contactCompanyName = document.getElementById("consignorName1").value;
            var contactName = document.getElementById("consignorPerson1").value;
            var phone = document.getElementById("consignorPhoneNumber1").value;
            var purpose = document.getElementById("purpose1").value;
            $.post("/ofc/contactSelect",{"contactCompanyName":contactCompanyName,"contactName":contactName,
                "phone":phone,"purpose":purpose},function (data) {
                data=eval(data);
                var contactList = "";
                $.each(data,function (index,CscContantAndCompanyDto) {
                    contactList =contactList + "<tr role='row' class='odd'>";
                    contactList =contactList + "<td class='center'> "+"<label class='pos-rel'>"+"<input type='checkbox' class='ace'>"+"<span class='lbl'></span>"+"</label>"+"</td>";
                    contactList =contactList + "<td>"+(index+1)+"</td>";
                    contactList =contactList + "<td>"+CscContantAndCompanyDto.contactCompanyName+"</td>";
                    contactList =contactList + "<td>"+CscContantAndCompanyDto.contactName+"</td>";
                    contactList =contactList + "<td>"+CscContantAndCompanyDto.phone+"</td>";
                    contactList =contactList + "<td>"+CscContantAndCompanyDto.fax+"</td>";
                    contactList =contactList + "<td>"+CscContantAndCompanyDto.email+"</td>";
                    contactList =contactList + "<td>"+CscContantAndCompanyDto.postCode+"</td>";
                    contactList =contactList + "<td>"+CscContantAndCompanyDto.address+"</td>";
                    $("#contactSelectListTbody1").html(contactList);
                });
            },"json");
        });

        $("#supplierSelectFormBtn").click(function () {
            alert("111111111111");
            var supplierName = document.getElementById("supplierName").value;
            var contactName = document.getElementById("contactName").value;
            var contactPhone = document.getElementById("contactPhone").value;
            $.post("/ofc/supplierSelect",{"supplierName":supplierName,"contactName":contactName,
                "contactPhone":contactPhone},function (data) {
                data=eval(data);
                var supplierList = "";
                $.each(data,function (index,CscSupplierInfoDto) {
                    supplierList =supplierList + "<tr role='row' class='odd'>";
                    supplierList =supplierList + "<td class='center'> "+"<label class='pos-rel'>"+"<input type='checkbox' class='ace'>"+"<span class='lbl'></span>"+"</label>"+"</td>";
                    supplierList =supplierList + "<td>"+(index+1)+"</td>";
                    supplierList =supplierList + "<td>"+CscSupplierInfoDto.supplierName+"</td>";
                    supplierList =supplierList + "<td>"+CscSupplierInfoDto.contactName+"</td>";
                    supplierList =supplierList + "<td>"+CscSupplierInfoDto.contactPhone+"</td>";
                    supplierList =supplierList + "<td>"+CscSupplierInfoDto.fax+"</td>";
                    supplierList =supplierList + "<td>"+CscSupplierInfoDto.email+"</td>";
                    supplierList =supplierList + "<td>"+CscSupplierInfoDto.postCode+"</td>";
                    supplierList =supplierList + "<td>"+CscSupplierInfoDto.address+"</td>";
                    $("#supplierSelectListTbody").html(supplierList);
                });
            },"json");
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


        $("#orderTypeSel").change(function () {
            if($(this).children('option:selected').val() == '61'){
                $("#provideTransportDiv").show();
                $("#businessTypeDiv").show();
                $('.storeLi').show();
                $('.transLi').hide();


            }
            if($(this).children('option:selected').val() == '60'){
                $('.transLi').show();
                $('.storeLi').hide();
                $("#provideTransportDiv").hide();
                $("#businessTypeDiv").hide();

            }
        });
        $("#provideTransport").change(function () {
            if($(this).prop("checked")){
                $('.transLi').show();
            }else{
                $('.transLi').hide();
            }
        });

    })

</script>

