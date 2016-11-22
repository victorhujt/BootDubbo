<head>
    <title>订单管理</title>
    <link rel="stylesheet" type="text/css" href="../css/jquery.editable-select.min.css"/>
</head>
<body class="no-skin">


<div class="page-header">
    <p>
        筛选条件
    </p>
</div>
<div class="row">
    <div class="col-xs-12">
        <form class="form-horizontal">
            <div class="form-group">
                <label class="control-label col-label no-padding-right" for="name">客户名称</label>
                <div class="col-xs-4">
                    <input id="custName" name="custName" type="search" placeholder="" aria-controls="dynamic-table">
                    <button type="button">
                        <span class="glyphicon glyphicon-search" style="color: #0f5f9f"></span>
                    </button>
                </div>
                <label class="control-label col-label no-padding-right" for="name">订单编号</label>
                <div class="col-xs-2">
                    <input id="orderCode" name="custName" type="search" placeholder="" aria-controls="dynamic-table">
                </div>
                <label class="control-label col-label no-padding-right" for="name">订单状态</label>
                <div class="col-xs-3">
                    <select data-placeholder="请选择订单状态" id="orderStatus" name="orderStatus">
                        <option value="">----</option>
                        <option value="10">待审核</option>
                        <option value="20">已审核</option>
                        <option value="30">执行中</option>
                        <option value="40">已完成</option>
                        <option value="50">已取消</option>
                    </select>
                </div>
            </div>
            <div class="form-group">
                <label class="control-label col-label no-padding-right" for="name">订单日期</label>
                <div class="col-xs-4">
                    <input id="orderDateBegin" name="orderDateBegin" type="search" placeholder=""
                           aria-controls="dynamic-table"
                           onClick="WdatePicker({isShowClear:true,readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})">
                    -
                    <input id="orderDateEnd" name="orderDateEnd" type="search" placeholder=""
                           aria-controls="dynamic-table"
                           onClick="WdatePicker({isShowClear:true,readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})">
                </div>
                <label class="control-label col-label no-padding-right" for="name">订单类型</label>
                <div class="col-xs-2">
                    <select data-placeholder="请选择订单类型" id="orderType" name="orderType">
                        <option value="">----</option>
                        <option value="60">运输订单</option>
                        <option value="61">仓配订单</option>
                    </select>
                </div>
                <label class="control-label col-label no-padding-right" for="name">业务类型</label>
                <div class="col-xs-3">
                    <select data-placeholder="请选择业务类型" id="businessType" name="businessType">
                        <option value="">----</option>
                        <option value="600">城配</option>
                        <option value="601">干线</option>
                    </select>
                    <span>&nbsp;<button class="btn btn-primary btn-xs">筛选</button></span>
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
<!-- div.table-responsive -->
<div id="dynamic-table_wrapper" class="dataTables_wrapper form-inline no-footer">
    <table id="dynamic-table" class="table table-striped table-bordered table-hover dataTable no-footer" role="grid"
           aria-describedby="dynamic-table_info">
        <thead>
        <th>序号</th>
        <th>操作列</th>
        <th>客户名称</th>
        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1"
            aria-label="Price: activate to sort column ascending">订单编号
        </th>
        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1"
            aria-label="Update: activate to sort column ascending">
            订单批次号
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

        </thead>
        <tbody id="dataTbody">
        <tr>
            <td>1</td>
            <td id="operate"></td>
            <td><a onclick="jumpUrlByOrderCode('111111')">111111</a></td>
            <td><a onclick="jumpUrlByOrderBatchCode('111111')">111111</a></td>
            <td>111111</td>
            <td>111111</td>
            <td>111111</td>
            <td>111111</td>
            <td>111111</td>
            <td>111111</td>
            <td>111111</td>
            <td>111111</td>
        </tr>

        </tbody>
    </table>
    <div class="row">
        <div id="pageBarDiv" style="float: right;padding-top: 0px;margin-top: 0px;">
        </div>
    </div>

    <link rel="stylesheet" href="../components/chosen/chosen.css"/>
    <script src="../components/chosen/chosen.jquery.js"></script>

    <script type="text/javascript">

        function appendSelect(type) {
            if (type == "60") {
                return "<option value=''>----</option><option value='600'>城配</option><option value='601'>干线</option>";
            } else if (type == "61") {
                var html = "";
                html += "<option value='----------'>----------</option>";
                html += "<option value='610'>销售出库</option>";
                html += "<option value='611'>调拨出库</option>";
                html += "<option value='612'>报损出库</option>";
                html += "<option value='613'>其他出库</option>";
                html += "<option value='620'>采购入库</option>";
                html += "<option value='621'>调拨入库</option>";
                html += "<option value='622'>退货入库</option>";
                html += "<option value='623'>加工入库</option>";
                return html;
            }
            return null;
        }
        $("#orderType").on("change", function () {
            var type = $(this).val();
            if (type == "60" || type == "61") {
                $("#businessType").empty().append(appendSelect(type));
            }
        });

        $(function () {
            $("#operate").append(operateEle("10"));
        });

        /**
         * 订单状态【待审核】，显示【审核】、【删除】三个按钮。
         订单状态【已审核】，显示【反审核】、【取消】两个按钮。
         订单状态【执行中】，显示【取消】一个按钮。
         订单状态【已完成】或【已取消】，不显示按钮。
         * 显示操作按钮
         * @param flag
         * @returns {*}
         */
        function operateEle(flag) {
            var cannelObj = "<button class='btn btn-danger btn-xs' onclick='"+cancelOperate('a')+"'>取消</button>";
            var applyObj = "<button class='btn btn-info btn-xs' onclick='"+applyOperate('a')+"'>审核</button>";
            var deleteObj = "<button class='btn btn-warning btn-xs' onclick='"+deleteObjOperate('a')+"'>删除</button>";
            var rereviewObj = "<button class='btn btn-info btn-xs' onclick='"+rereviewOperate('a')+"'>反审核</button>";
            if (flag == "10") {
                return ( applyObj + "&nbsp;&nbsp;" + deleteObj);
            } else if (flag == "20") {
                return ( rereviewObj + "&nbsp;&nbsp;" + deleteObj);
            } else if (flag == "30") {
                return append(cannelObj);
            }
        }

        function cancelOperate(a){
            if (confirm("确定取消订单？")) {
                alert("取消订单");
            }
        }
        function deleteOpera(a){
            if (confirm("确定删除订单？")) {
                alert("删除订单");
            }
        }
        function applyOperate(a){
            if (confirm("确定审核订单？")) {
                alert("审核订单");
            }
        }
        function rereviewOperate(a){
            if (confirm("确定反审核订单？")) {
                alert("反审核订单");
            }
        }

        function jumpUrlByOrderCode(orderCode){
            location.href="";
        }

        function jumpUrlByOrderBatchCode(orderBatchNumber){
            location.href="";
        }

    </script>

</body>