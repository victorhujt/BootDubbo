<head>
    <title>城配开单Excel导入</title>
</head>
<body>
<span></span>
    <form class="form-horizontal" role="form">
        <div class="form-group">
            <label class="control-label col-sm-1 no-padding-right" for="name">下载模板</label>
            <div class="col-sm-3">
                <div class="clearfix">
                    <a>批量下单导入模版_商超配送</a>
                    <p style="color: red">(提示:必须与模版中的列名保持一致，货品信息与收货方信息必须在基本信息中维护)</p>
                </div>
            </div>
        </div>
        <div class="form-group">
            <label class="control-label col-sm-1 no-padding-right" for="name">上传文件</label>
            <div class="col-sm-3">
                <div class="clearfix">
                    <input  id = "" name="" type="text" style="color: black" class="form-control input-sm bk-1" placeholder="" aria-controls="dynamic-table">
                </div>
            </div>
        </div>
        <div class="form-group">
            <label class="control-label col-sm-1 no-padding-right " for="name">Sheet页</label>
            <div class="col-sm-3">
                <div class="clearfix">
                    <select class="bk-1">
                    </select>
                </div>
            </div>
        </div>
    </form>
    <div class="modal-header"><span id="goodsListDivNoneTop" style="cursor:pointer"><button type="button" id="" style="cursor:pointer" class="bootbox-close-button close" data-dismiss="modal" aria-hidden="true">×</button></span>
        <h4 class="modal-title" style="font-size: 14px">货品列表</h4></div>
    <div class="modal-body">
        <div class="bootbox-body">
            <form class="bootbox-form">

                    <table id="orderGoodsListTable" class="table table-striped table-bordered table-hover dataTable no-footer" role="grid"
                           aria-describedby="dynamic-table_info">
                        <thead>
                        <tr role="row">
                            <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1"
                                aria-label="Domain: activate to sort column ascending">货品编码
                            </th>
                            <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1"
                                aria-label="Price: activate to sort column ascending">货品名称
                            </th>
                            <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1"
                                aria-label="Update: activate to sort column ascending">规格
                            </th>
                            <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1"
                                aria-label="Clicks: activate to sort column ascending">单位
                            </th>
                            <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1"
                                aria-label="Clicks: activate to sort column ascending">发货数量
                            </th>
                        </thead>
                        <tbody id="goodsSelectListTbody"></tbody>

                    </table>
            </form>
        </div>
    </div>
    <div class="modal-footer"><button id="goodsEnter" data-bb-handler="confirm" type="button" class="btn btn-primary">确认导入</button><span id="ExcelNoneBottom" style="cursor:pointer"><button id="ExcelNoneBtnBottom" value="${(historyUrl)!""}" data-bb-handler="cancel" type="button" class="btn btn-default">不导入</button></span></div>
<script type="text/javascript">
    $("#ExcelNoneBottom").click(function () {
        var historyUrl = $("#ExcelNoneBtnBottom").val();
        xescm.common.loadPage(historyUrl);
    })
</script>
</body>