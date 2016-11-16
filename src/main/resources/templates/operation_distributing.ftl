<head>
    <title>城配下单</title>
</head>
<body>
<div class="col-xs-9">
    <button class="btn btn-white btn-info btn-bold btn-interval" id="">
        <i class="ace-icon fa fa-floppy-o bigger-120 blue" ></i>
        历史订单选择
    </button>

    <button class="btn btn-white btn-info btn-bold btn-interval" id="">
        <i class="ace-icon fa fa-floppy-o bigger-120 blue" ></i>
        Excel导入
    </button>

    <button class="btn btn-white btn-info btn-bold btn-interval" id="">
        <i class="ace-icon fa fa-floppy-o bigger-120 blue" ></i>
        收发货方档案
    </button>
</div>

<form id="" method="post" class="form-horizontal" role="form" >
    <div class="col-xs-12">
        <div class="form-group">
            <label class="control-label col-label no-padding-right" for="supplierCode">订单日期</label>
            <div class="col-xs-3">
                <div class="clearfix">
                    <input class="col-xs-10 col-xs-12"  name="orderTime" id="orderTime" type="text" placeholder="订单日期" onClick="WdatePicker({isShowClear:true,readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})" />
                </div>
            </div>
            <label class="control-label col-label no-padding-right" for="supplierCode">开单员</label>
            <div class="col-xs-3">
                <div class="clearfix">
                    <input class="col-xs-10 col-xs-12"  name="" id="" type="text" placeholder="开单员"  />
                </div>
            </div>
            <label class="control-label col-label no-padding-right" for="supplierCode">预发货时间</label>
            <div class="col-xs-3">
                <div class="clearfix">
                    <input class="col-xs-10 col-xs-12"  name="" id="" type="text" placeholder="预计发货时间" onClick="WdatePicker({isShowClear:true,readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})"/>
                </div>
            </div>
        </div>
    </div>
    <div class="col-xs-12">
        <div class="form-group">
            <label class="control-label col-label no-padding-right" for="supplierCode">客户名称</label>
            <div class="col-xs-3">
                <div class="clearfix">
                    <input class="col-xs-10 col-xs-12"  name="orderTime" id="orderTime" type="text" placeholder="客户名称" onClick="WdatePicker({isShowClear:true,readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})" />
                </div>
            </div>
            <label class="control-label col-label no-padding-right" for="supplierCode">配送仓库</label>
            <div class="col-xs-3">
                <div class="clearfix">
                    <input class="col-xs-10 col-xs-12"  name="" id="" type="text" placeholder="配送仓库"  />
                </div>
            </div>
            <label class="control-label col-label no-padding-right" for="supplierCode">备注</label>
            <div class="col-xs-3">
                <div class="clearfix">
                    <input class="col-xs-10 col-xs-12"  name="" id="" type="text" placeholder="备注" />
                </div>
            </div>
        </div>
    </div>
    <div class="row">
        <span style="float: left">发货方</span>
        <span style="float: left">出发地:</span>
        <div class="col-xs-12">
            <div class="form-group">
                <label class="control-label col-label no-padding-right" for="supplierCode">名称</label>
                <div class="col-xs-3">
                    <div class="clearfix">
                        <input class="col-xs-10 col-xs-12"  name="orderTime" id="orderTime" type="text" placeholder="名称"  />
                    </div>
                </div>
                <label class="control-label col-label no-padding-right" for="supplierCode">联系人</label>
                <div class="col-xs-3">
                    <div class="clearfix">
                        <input class="col-xs-10 col-xs-12"  name="" id="" type="text" placeholder="联系人"  />
                    </div>
                </div>
                <label class="control-label col-label no-padding-right" for="supplierCode">联系电话</label>
                <div class="col-xs-3">
                    <div class="clearfix">
                        <input class="col-xs-10 col-xs-12"  name="" id="" type="text" placeholder="联系电话" />
                    </div>
                </div>
            </div>
        </div>
        <div class="col-xs-12">
            <div class="form-group">
                <label class="control-label col-label no-padding-right" for="supplierCode">地址</label>
                <div class="col-xs-9">
                    <div class="clearfix">
                        <input class="col-xs-10 col-xs-12"  name="orderTime" id="orderTime" type="text" placeholder="地址" onClick="WdatePicker({isShowClear:true,readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})" />
                    </div>
                </div>

            </div>
        </div>
    </div>
</form>
<div class="col-sm-12">
    <div class="tabbable" style="width:1000px;" >
        <ul class="nav nav-tabs" id="myTab4">
            <li class="active">
                <a data-toggle="tab" href="#home4" aria-expanded="false">货品信息</a>
            </li>

            <li class="transLi">
                <a data-toggle="tab" href="#profile4" aria-expanded="true">收货方列表</a>
            </li>

        </ul>

        <div class="tab-content">
            <div id="home4" class="tab-pane active">
                <!--货品明细-->
                <span style="cursor:pointer" id="goodsListDivBlock"><button type="button" class="btn btn-info" id="bootbox-confirm">添加货品</button></span>
                <table id="orderGoodsListTable" class="table table-striped table-bordered table-hover dataTable no-footer" role="grid" aria-describedby="dynamic-table_info">
                    <thead>
                    <tr role="row"><th class="center sorting_disabled" rowspan="1" colspan="1" aria-label="">
                        操作
                    </th>
                    <#--<th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Status: activate to sort column ascending">序号</th>-->
                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Domain: activate to sort column ascending">货品编码</th>
                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Price: activate to sort column ascending">货品名称</th>
                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Update: activate to sort column ascending">货品规格
                        </th>
                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">单位</th>
                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">单价</th>
                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">数量</th>
                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">生产批次</th>
                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">
                            <i class="ace-icon fa fa-clock-o bigger-110 hidden-480"></i>
                            生产日期</th>
                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">
                            <i class="ace-icon fa fa-clock-o bigger-110 hidden-480"></i>
                            失效日期</th>
                    </thead>
                <#--货品明细-->

                    <tbody id="goodsInfoListDiv"></tbody>

                </table>
                <div class="row">
                    <div class="col-xs-6">
                        <div class="dataTables_info" id="dynamic-table_info" role="status" aria-live="polite">

                        </div>
                    </div>
                    <div class="col-xs-6">
                    </div>
                </div>

            </div>

            <div id="profile4" class="tab-pane">


            </div>

        </div>
    </div>

    <!-- /section:elements.tab.option -->
</div>

<div class="col-xs-12">
    <button class="btn btn-white btn-info btn-bold btn-interval" id="orderPlaceConTableBtn">
        <i class="ace-icon fa fa-floppy-o bigger-120 blue" ></i>
        确认下单
    </button>
</div>

<script type="text/javascript">

</script>
</body>