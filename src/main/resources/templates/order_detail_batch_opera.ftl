<head>
    <title>订单批次</title>
</head>
<body class="no-skin">

<div class="col-xs-12">
    <div class="col-sm-6" style="float: left">
        <button class="btn btn-white btn-info btn-bold filters" style="bottom: 5px;" id="goBack" value="" onclick="detailBackToHistory()">
            返回
        </button>
    </div>
    <form id="" method="post" class="form-horizontal" role="form">
        <div class="page-header">
            <p>
            </p>
        </div>
        <div class="row">
            <div class="col-xs-12">
                <form class="form-horizontal">
                    <div class="form-group">
                        <label class="control-label col-label no-padding-right" for="name">订单批次号</label>
                        <div class="col-xs-3">
                            <input id="custName" name="custName" type="search" placeholder="" aria-controls="dynamic-table">
                        </div>
                        <label class="control-label col-label no-padding-right" for="name">客户名称</label>
                        <div class="col-xs-3">
                            <input id="orderCode" name="custName" type="search" placeholder=""
                                   aria-controls="dynamic-table">
                        </div>
                        <label class="control-label col-label no-padding-right" for="name">开单员</label>
                        <div class="col-xs-3">
                            <input id="orderCode" name="custName" type="search" placeholder=""
                                   aria-controls="dynamic-table">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-label no-padding-right" for="name">订单日期</label>
                        <div class="col-xs-3">
                            <input id="orderCode" name="custName" type="search" placeholder=""
                                   aria-controls="dynamic-table">
                        </div>
                        <label class="control-label col-label no-padding-right" for="name">配送仓库</label>
                        <div class="col-xs-3">
                            <input id="orderCode" name="custName" type="search" placeholder=""
                                   aria-controls="dynamic-table">
                        </div>
                        <label class="control-label col-label no-padding-right" for="name">备注</label>
                        <div class="col-xs-3">
                            <input id="orderCode" name="custName" type="search" placeholder=""
                                   aria-controls="dynamic-table">
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
                                   aria-controls="dynamic-table">
                        </div>
                        <label class="control-label col-label no-padding-right" for="name">联系人</label>
                        <div class="col-xs-3">
                            <input id="orderCode" name="custName" type="search" placeholder=""
                                   aria-controls="dynamic-table">
                        </div>
                        <label class="control-label col-label no-padding-right" for="name">联系电话</label>
                        <div class="col-xs-3">
                            <input id="orderCode" name="custName" type="search" placeholder=""
                                   aria-controls="dynamic-table">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-label no-padding-right" for="name">地址</label>
                        <div class="col-xs-6">
                            <input id="orderCode" name="custName" type="search" placeholder=""
                                   aria-controls="dynamic-table">
                        </div>
                    </div>
                </form>
            </div>
        </div>

        <div class="page-header">
            <p>
                订单列表
            </p>
        </div>
        <table id="dynamic-table" class="table table-striped table-bordered table-hover dataTable no-footer" role="grid"
               aria-describedby="dynamic-table_info">
            <thead>
            <tr role="row">
                <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1"
                    aria-label="Clicks: activate to sort column ascending">选择
                </th>
                <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1"
                    aria-label="Clicks: activate to sort column ascending">订单编号
                </th>
                <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1"
                    aria-label="Clicks: activate to sort column ascending">客户订单编号
                </th>
                <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1"
                    aria-label="Clicks: activate to sort column ascending">订单日期
                </th>
                <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1"
                    aria-label="Clicks: activate to sort column ascending">订单类型
                </th>
                <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1"
                    aria-label="Clicks: activate to sort column ascending">业务类型
                </th>
                <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1"
                    aria-label="Clicks: activate to sort column ascending">订单状态
                </th>
                <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1"
                    aria-label="Clicks: activate to sort column ascending">收货方名称
                </th>
                <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1"
                    aria-label="Clicks: activate to sort column ascending">仓库名称
                </th>
                <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1"
                    aria-label="Clicks: activate to sort column ascending">备注
                </th>
            </thead>
            <tbody>
            </tbody>
        </table>


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

            </tbody>
        </table>
    </form>
</div><!-- /.col -->

<script type="text/javascript">
    function detailBackToHistory() {
        if ("orderManageOpera" == tag) {
            xescm.common.loadPage("/ofc/orderManageOpera");
        }
    }
</script>
</body>
