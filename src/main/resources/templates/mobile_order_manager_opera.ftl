<head>
    <title>拍照开单</title>
    <style>
        input{
            width: 240px;
        }
        td{
            text-align: center;
        }
    </style>
</head>
<body class="no-skin">


<div class="page-header">
    <p>
        订单筛选
    </p>
</div>
<div class="row">
    <div class="col-xs-12">
        <div class="form-horizontal">
            <div class="form-group">
                <label class="control-label col-label no-padding-right" style="margin-right:20px;" for="name">上传日期</label>
                <div class="y-float">
                    <div class="clearfix">
                        <input id="orderTimePre" name="startDate" type="datetime" style="width:196px;float:left;margin-right:12px;"  placeholder="" aria-controls="dynamic-table" onclick="laydate({istime: true, format: 'YYYY-MM-DD hh:mm:ss',isclear: true,istoday: true,min: laydate.now(-30),max: laydate.now()})">
                        <label class="control-label col-label no-padding-right y-float" style="margin:0 20px;text-align:center;" for="name">至</label>
                        <input id="orderTimeSuf" name="endDate" type="datetime" style="width:196px;float:left;margin-right:12px;"  placeholder="" aria-controls="dynamic-table" onclick="laydate({istime: true, format: 'YYYY-MM-DD hh:mm:ss',isclear: true,istoday: true,min: laydate.now(-30),max: laydate.now()})">
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-label no-padding-right" for="name">受理状态</label>
                    <div class="w-width-220 y-float">
                        <div class="clearfix">
                            <select class="chosen-select form-control" data-placeholder="请选择订单类型" id="mobileOrderStatus" name="mobileOrderStatus">
                                <option value=""></option>
                                <option value="0">未受理</option>
                                <option value="1">已受理</option>
                            </select>
                        </div>
                    </div>
                </div>
                <div class="form-group" style="padding-left: 12px">
                    <label class="control-label col-label no-padding-right" for="name">业务类型</label>
                    <div class="w-width-220 y-float">
                        <div class="clearfix">
                            <select class="chosen-select form-control" data-placeholder="请选择业务类型" id="businessType" name="businessType">
                                <option value=""></option>
                                <option value="602">卡班</option>
                                <option value="601">干线</option>
                                <option value="600">城配</option>
                            </select>
                        </div>
                    </div>
                </div>
            </div>



            <div class="form-group">
                <label class="control-label col-label no-padding-right"></label>
                <div class="col-sm-6">
                    <button class="btn btn-white btn-info btn-bold filters" id="doSearch">
                        筛选
                    </button>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="page-header">
    <p>
        订单列表
    </p>
</div>
<!-- div.table-responsive -->
<div id="dynamic-table_wrapper" class="dataTables_wrapper form-inline no-footer">
    <table id="dynamic-table" class="table table-striped table-bordered table-hover dataTable no-footer" role="grid" aria-describedby="dynamic-table_info">
        <thead>
        <th>序号</th>
        <th>流水号</th>
        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1"
            aria-label="Clicks: activate to sort column ascending">上传日期
        </th>
        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1"
            aria-label="Price: activate to sort column ascending">钉钉账号
        </th>

        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1"
            aria-label="Clicks: activate to sort column ascending">开单员
        </th>
        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1"
            aria-label="Clicks: activate to sort column ascending">业务类型
        </th>
        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1"
            aria-label="Clicks: activate to sort column ascending">运输单号
        </th>
        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1"
            aria-label="Clicks: activate to sort column ascending">受理状态
        </th>
        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1"
            aria-label="Clicks: activate to sort column ascending">订单编号
        </th>
        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1"
            aria-label="Clicks: activate to sort column ascending">受理人
        </th>
        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1"
            aria-label="Clicks: activate to sort column ascending">受理时间
        </th>
        </thead>
        <tbody id="dataTbody"></tbody>
    </table>
    <div class="row">
        <div id="pageBarDiv" style="float: right;padding-top: 0px;margin-top: 20px;">
        </div>
    </div>

    <link rel="stylesheet" href="../components/chosen/chosen.css" />
    <script src="../components/chosen/chosen.jquery.js"></script>

    <script type="text/javascript">
        var scripts = [ null, "../components/chosen/chosen.jquery.js", null ]
        $(".page-content-area").ace_ajax("loadScripts", scripts, function() {
            $(document).ready(main);
            $('.chosen-select').chosen({allow_single_deselect:true});
            //resize the chosen on window resize

            $(window)
                .off('resize.chosen')
                .on('resize.chosen', function() {
                    $('.chosen-select').each(function() {
                        var $this = $(this);
                        $this.next().css({'width': $this.parent().width()});
                    })
                }).trigger('resize.chosen');
            //resize chosen on sidebar collapse/expand
            $(document).on('settings.ace.chosen', function(e, event_name, event_val) {
                if(event_name != 'sidebar_collapsed') return;
                $('.chosen-select').each(function() {
                    var $this = $(this);
                    $this.next().css({'width': $this.parent().width()});
                })
            });
        });

        function main(){
            //初始化页面数据
            initPageData();
            // 查询
            //  queryData(1);

            $("#doSearch").click(function () {
//                debugger;
                queryData(1);
            });
        }



        //页面数据初始化
        function initPageData(){
//            debugger;
            var active_class = "active";
            $("#simple-table > thead > tr > th input[type=checkbox]").eq(0).on("click", function(){
                var th_checked = this.checked;//checkbox inside "TH" table header

                $(this).closest("table").find("tbody > tr").each(function(){
                    var row = this;
                    if(th_checked) $(row).addClass(active_class).find("input[type=checkbox]").eq(0).prop("checked", true);
                    else $(row).removeClass(active_class).find("input[type=checkbox]").eq(0).prop("checked", false);
                });
            });
            $("#simple-table").on("click", "td input[type=checkbox]" , function(){
                var $row = $(this).closest("tr");
                if($row.is(".detail-row ")) return;
                if(this.checked) $row.addClass(active_class);
                else $row.removeClass(active_class);
            });
        }
    </script>
    <!-- inline scripts related to this page -->
    <script type="text/javascript">

        function queryData(pageNum) {
            //debugger;
            var param = {};
            param.pageNum = pageNum;
            param.pageSize = 10;
            var orderTimePre = $('#orderTimePre').val();
            var orderTimeSuf = $('#orderTimeSuf').val();
            param.startDate = orderTimePre;
            param.endDate = orderTimeSuf;
            param.mobileOrderStatus = $("#mobileOrderStatus").val();
            param.businessType= $("#businessType option:selected").val();

            CommonClient.post(sys.rootPath + "/ofc/queryMobileOrderData", param, function(result) {

                if (result == undefined || result == null) {
                    alert("HTTP请求无数据返回！");
                } else if (result.code == 200) {// 1:normal
                    reloadGrid(result);// 刷新页面数据
                    laypage({
                        cont: $("#pageBarDiv"), // 容器。值支持id名、原生dom对象，jquery对象,
                        pages: result.result.pages, // 总页数
                        skip: true, // 是否开启跳页
                        skin: "molv",
                        groups: 3, // 连续显示分页数
                        curr: result.result.pageNum, // 当前页
                        jump: function(obj, first){ // 触发分页后的回调
                            if(!first){ // 点击跳页触发函数自身，并传递当前页：obj.curr
                                queryData(obj.curr);
                            }
                        }
                    });
                } else if (result.code == 403){
                    alert("没有权限")
                } else {
                    $("#dataTbody").html("");
                }
            });
        }

        function reloadGrid(data) {
            var htmlText = "";

            var length = data.result.list.length;

            if(length < 1){
                $("#dataTbody").html("");
                return;
            }

            for ( var i = 0; i < data.result.list.length; i++) {
                var order = data.result.list[i];
                htmlText += "<tr role=\"row\" class=\"odd\">"
                    + "<td>" + [ i + 1] + "</td>"
                        + "<td>"
                        + "<a onclick=\"orderDetailOper('" + order.mobileOrderCode + "')\">" + StringUtil.nullToEmpty(order.mobileOrderCode) + "</a>"
                        + "</td>"
                    + "<td class=\"hidden-480\">" + subTimeString(StringUtil.nullToEmpty(order.uploadDate)) + "</td>"
                    + "<td class=\"hidden-480\">" + StringUtil.nullToEmpty(order.dcustOrderCodeingdingAccountNo) + "</td>"
                    + "<td class=\"hidden-480\">" + StringUtil.nullToEmpty(order.operator) + "</td>"
                    + "<td class=\"hidden-480\">" + getBusiType(order) + "</td>"
                    + "<td class=\"hidden-480\">" + StringUtil.nullToEmpty(order.tranCode) + "</td>"
                        + "<td class=\"hidden-480\">" + getOperatorByStatus(order,i) + "</td>"
                    + "<td class=\"hidden-480\">" + StringUtil.nullToEmpty(order.orderCode) + "</td>"
                    + "<td class=\"hidden-480\">" + StringUtil.nullToEmpty(order.accepter) + "</td>"
                    + "<td class=\"hidden-480\">" + subTimeString(StringUtil.nullToEmpty(order.appcetDate)) + "</td>"
                    + "</tr>";
            }
            $("#dataTbody").html(htmlText);

        }

        function getOperatorByStatus(order,index) {
            var value = "";
            if (order.mobileOrderStatus == "0") {
                 value = "<button type=\"button\" id=\"review\" onclick=\"acceptMobileOrder('" + order.mobileOrderCode + "')\" class=\"hidden-480\">未处理</button>"
            }else{
                value = "<span class=\"hidden-480\">已处理</span>"
            }
            return value;
        }

        function subTimeString(s) {
            try {
                return s.substring(0, 11);
            } catch (e) {
                return s;
            }
        }


        function getBusiType(order) {
            var value = "";
            if(order.businessType == '600'){
                value = "城配"
            }else if(order.businessType == "601"){
                value = "干线";
            }else if(order.businessType == "610"){
                value = "销售出库";
            }else if(order.businessType == "611"){
                value = "调拨出库";
            }else if(order.businessType == "612"){
                value = "报损出库";
            }else if(order.businessType == "613"){
                value = "其他出库";
            }else if(order.businessType == "620"){
                value = "采购入库";
            }else if(order.businessType == "621"){
                value = "调拨入库";
            }else if(order.businessType == "622"){
                value = "退货入库";
            }else if(order.businessType == "623"){
                value = "加工入库";
            }
            return value;
        }

        //订单详情
        function orderDetailOper(orderCode) {
//            debugger;
            var url = "/ofc/mobileOrderDetails/" + orderCode;
            var html = window.location.href;
            var index = html.indexOf("/index#");
            window.open(html.substring(0,index) + "/index#" + url);
//            xescm.common.loadPage("/ofc/orderDetailPageByCode/" + orderCode);
        }

        //订单受理
        function acceptMobileOrder(orderCode) {
//            debugger;
            var url = "/ofc/acceptMobileOrder/" + orderCode;
            var html = window.location.href;
            var index = html.indexOf("/index#");
            window.open(html.substring(0,index) + "/index#" + url);
//            xescm.common.loadPage("/ofc/orderDetailPageByCode/" + orderCode);
        }








    </script>

</body>