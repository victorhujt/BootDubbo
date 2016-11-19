<head>
    <title>城配下单</title>
    <style type="text/css">
        #goodsListDiv {
            position:fixed;
            left:285px;
            top:85px;
            width:946px;
            height:500px;
            z-index:3;
            overflow: auto;
            border:solid #7A7A7A 2px;
        }
    </style>


</head>
<body>
<!--goodsListDiv-->
<div class="modal-content" id="goodsListDiv" style="display: none;">
    <div class="modal-header"><span id="goodsListDivNoneTop" style="cursor:pointer"><button type="button" id="" style="cursor:pointer" class="bootbox-close-button close" data-dismiss="modal" aria-hidden="true">×</button></span>
        <h4 class="modal-title">货品列表</h4></div>
    <div class="modal-body">
        <div class="bootbox-body">
            <form id="goodsSelConditionForm" class="form-horizontal" role="form">
                <div class="form-group">
                    <label class="control-label col-sm-1 no-padding-right" for="name">货品编码</label>
                    <div class="col-sm-3">
                        <div class="clearfix">
                            <input  id = "goodsCodeCondition" name="goodsCode" type="text" style="color: black" class="form-control input-sm" placeholder="" aria-controls="dynamic-table">
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-sm-1 no-padding-right" for="name">货品名称</label>
                    <div class="col-sm-3">
                        <div class="clearfix">
                            <input  id = "goodsNameCondition" name="goodsName" type="text" style="color: black" class="form-control input-sm" placeholder="" aria-controls="dynamic-table">
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-sm-1 no-padding-right" for="name"></label>
                    <div class="col-sm-3">
                        <div class="clearfix">
                            <span id="goodsSelectFormBtn" class="btn btn-info btn-sm popover-info">筛选</span>
                        </div>
                    </div>
                </div>
            </form>
            <form class="bootbox-form">
                <table id="dynamic-table" class="table table-striped table-bordered table-hover dataTable no-footer" role="grid" aria-describedby="dynamic-table_info">
                    <thead>
                    <tr role="row"><th class="center sorting_disabled" rowspan="1" colspan="1" aria-label="">
                        <label class="pos-rel">
                            <input id="goodcheck" type="checkbox" class="ace">
                            <span class="lbl"></span>
                        </label>
                    </th>
                    <#--<th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Domain: activate to sort column ascending">序号</th>-->
                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Price: activate to sort column ascending">货品种类</th>
                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">货品小类</th>
                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">品牌</th>
                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">货品编码</th>
                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">货品名称</th>
                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">规格</th>
                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">单位</th>
                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">条形码</th>

                    </thead>
                    <tbody id="goodsSelectListTbody"></tbody>
                </table>


            </form>
        </div>
    </div>
    <div class="modal-footer"><span id="goodsListDivNoneBottom" style="cursor:pointer"><button  data-bb-handler="cancel" type="button" class="btn btn-default">Cancel</button></span><button id="goodsEnter" data-bb-handler="confirm" type="button" class="btn btn-primary">OK</button></div>
</div>

<br/>
<div class="col-xs-9">
    <button class="btn btn-white btn-info btn-bold btn-interval" id="">
        <i class="ace-icon fa fa-floppy-o bigger-120 blue"></i>
        历史订单选择
    </button>

    <button class="btn btn-white btn-info btn-bold btn-interval" id="">
        <i class="ace-icon fa fa-floppy-o bigger-120 blue"></i>
        Excel导入
    </button>

    <button class="btn btn-white btn-info btn-bold btn-interval" id="">
        <i class="ace-icon fa fa-floppy-o bigger-120 blue"></i>
        收发货方档案
    </button>
</div>
<br/>
<br/>
<br/>
<form id="" method="post" class="form-horizontal" role="form">
    <div class="col-xs-12">
        <div class="form-group">
            <label class="control-label col-label no-padding-right" for="supplierCode">订单日期</label>
            <div class="col-xs-3">
                <div class="clearfix">
                    <input class="col-xs-10 col-xs-12" name="orderTime" id="orderTime" type="text" placeholder="订单日期"
                           onClick="WdatePicker({isShowClear:true,readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})"/>
                </div>
            </div>
            <label class="control-label col-label no-padding-right" for="supplierCode">开单员</label>
            <div class="col-xs-3">
                <div class="clearfix">
                    <input class="col-xs-10 col-xs-12" name="" id="" type="text" placeholder="开单员"/>
                </div>
            </div>
            <label class="control-label col-label no-padding-right" for="supplierCode">预发货时间</label>
            <div class="col-xs-3">
                <div class="clearfix">
                    <input class="col-xs-10 col-xs-12" name="" id="" type="text" placeholder="预计发货时间"
                           onClick="WdatePicker({isShowClear:true,readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})"/>
                </div>
            </div>
        </div>
    </div>
    <div class="col-xs-12">
        <div class="form-group">
            <label class="control-label col-label no-padding-right" for="supplierCode">客户名称</label>
            <div class="col-xs-3">
                <div class="clearfix">
                    <input class="col-xs-10 col-xs-12" name="orderTime" id="orderTime" type="text" placeholder="客户名称"/>
                    <button type="button" class="btn btn-minier btn-inverse no-padding-right"
                            id="consignorListDivBlock">选择
                    </button>
                </div>
            </div>
            <label class="control-label col-label no-padding-right" for="supplierCode">配送仓库</label>
            <div class="col-xs-3">
                <div class="clearfix">
                    <input class="col-xs-10 col-xs-12" name="" id="" type="text" placeholder="配送仓库"/>
                </div>
            </div>
            <label class="control-label col-label no-padding-right" for="supplierCode">备注</label>
            <div class="col-xs-3">
                <div class="clearfix">
                    <input class="col-xs-10 col-xs-12" name="" id="" type="text" placeholder="备注"/>
                </div>
            </div>
        </div>
    </div>
    <br/>
    <br/>
    <br/>
    <div class="row">
        <div class="col-xs-12">
            <div class="form-group">
                <label class="control-label col-label no-padding-right" for="supplierCode"><b>发货方</b></label>
                <div class="col-xs-6">
                </div>
                <label class="control-label col-label no-padding-right" for="supplierCode"><b>出发地:</b></label>
                <div class="col-xs-3">
                    <div class="clearfix">
                        <span id="departurePlace"></span>
                    </div>
                </div>

            </div>
        </div>
        <div class="col-xs-12">
            <div class="form-group">
                <label class="control-label col-label no-padding-right" for="supplierCode">名称</label>
                <div class="col-xs-3">
                    <div class="clearfix">
                        <input class="col-xs-10 col-xs-12" name="orderTime" id="orderTime" type="text"
                               placeholder="名称"/>
                        <button type="button" class="btn btn-minier btn-inverse no-padding-right"
                                id="consignorListDivBlock">选择
                        </button>
                    </div>
                </div>
                <label class="control-label col-label no-padding-right" for="supplierCode">联系人</label>
                <div class="col-xs-3">
                    <div class="clearfix">
                        <input class="col-xs-10 col-xs-12" name="" id="" type="text" placeholder="联系人"/>
                    </div>
                </div>
                <label class="control-label col-label no-padding-right" for="supplierCode">联系电话</label>
                <div class="col-xs-3">
                    <div class="clearfix">
                        <input class="col-xs-10 col-xs-12" name="" id="" type="text" placeholder="联系电话"/>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-xs-12">
            <div class="form-group">
                <label class="control-label col-label no-padding-right" for="supplierCode">地址</label>
                <div class="col-xs-9">
                    <div class="clearfix">
                        <input class="col-xs-10 col-xs-12" name="orderTime" id="orderTime" type="text" placeholder="地址" />
                    </div>
                </div>

            </div>
        </div>
    </div>
</form>
<br/>
<div class="col-sm-12">
    <div class="tabbable" style="width:1000px;">
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
                <span style="cursor:pointer" id="goodsListDivBlock"><button type="button" class="btn btn-info"  id="bootbox-confirm">添加货品</button></span>
                <table id="orderGoodsListTable" class="table table-striped table-bordered table-hover dataTable no-footer" role="grid"
                       aria-describedby="dynamic-table_info">
                    <thead>
                    <tr role="row">
                        <th class="center sorting_disabled" rowspan="1" colspan="1" aria-label="">
                            操作
                        </th>
                    <#--<th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Status: activate to sort column ascending">序号</th>-->
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
                            aria-label="Clicks: activate to sort column ascending">数量
                        </th>
                    </thead>
                    <tbody id="goodsInfoListDiv"></tbody>

                </table>
            </div>

            <div id="profile4" class="tab-pane">

            </div>

        </div>
    </div>
</div>


<div class="col-xs-12">
    <button class="btn btn-white btn-info btn-bold btn-interval" id="orderPlaceConTableBtn">
        <i class="ace-icon fa fa-floppy-o bigger-120 blue"></i>
        确认下单
    </button>
</div>




<script type="text/javascript">
    var scripts = [null,
        "/components/jquery-validation/dist/jquery.validate.min.js",
        "/components/jquery-validation/src/localization/messages_zh.js",
        "/components/jquery-validation/dist/additional-methods.js", null];
    $(".page-content-area").ace_ajax("loadScripts", scripts, function () {
        /*jQuery(function($) {
            validateForm();//校验表单信息
            /!*validateFormPageBody();*!/
        })*/
        $(document).ready(main);
        $('.chosen-select').chosen({allow_single_deselect: true});
        //resize the chosen on window resize

        $(window)
                .off('resize.chosen')
                .on('resize.chosen', function () {
                    $('.chosen-select').each(function () {
                        var $this = $(this);
                        $this.next().css({'width': $this.parent().width()});
                    })
                }).trigger('resize.chosen');
        //resize chosen on sidebar collapse/expand
        $(document).on('settings.ace.chosen', function (e, event_name, event_val) {
            if (event_name != 'sidebar_collapsed') return;
            $('.chosen-select').each(function () {
                var $this = $(this);
                $this.next().css({'width': $this.parent().width()});
            })
        });
    });

    function main() {
        //validateForm();
    }
    $(function () {

        $("#goodsListDivBlock").click(function () {
            $("#goodsListDiv").fadeIn("slow");//淡入淡出效果 显示div
        })
        $("#goodsListDivNoneTop").click(function(){

            $("#goodsListDiv").fadeOut("slow");//淡入淡出效果 隐藏div

        });
        $("#goodsListDivNoneBottom").click(function(){

            $("#goodsListDiv").fadeOut("slow");//淡入淡出效果 隐藏div

        });
        $("#goodsSelectFormBtn").click(function () {
            CommonClient.post(sys.rootPath + "/ofc/goodsSelect", $("#goodsSelConditionForm").serialize(), function(data) {
                data=eval(data);

                var goodsList = "";
                $.each(data,function (index,cscGoodsVo) {
                    goodsList =goodsList + "<tr role='row' class='odd'>";
                    goodsList =goodsList + "<td class='center'> "+"<label class='pos-rel'>"+"<input type='checkbox' class='ace'>"+"<span class='lbl'></span>"+"</label>"+"</td>";
                    goodsList =goodsList + "<td>"+cscGoodsVo.goodsTypeName+"</td>";//货品种类
                    goodsList =goodsList + "<td>"+cscGoodsVo.goodsTypeName+"</td>";//货品小类
                    goodsList =goodsList + "<td>"+cscGoodsVo.goodsTypeName+"</td>";//品牌
                    goodsList =goodsList + "<td>"+cscGoodsVo.goodsCode+"</td>";//货品编码
                    goodsList =goodsList + "<td>"+cscGoodsVo.goodsName+"</td>";//货品名称
                    goodsList =goodsList + "<td>"+cscGoodsVo.specification+"</td>";//规格
                    goodsList =goodsList + "<td>"+cscGoodsVo.unit+"</td>";//单位
                    goodsList =goodsList + "<td>"+cscGoodsVo.barCode+"</td>";//条形码
                    /*goodsList =goodsList + "<td style='display:none'>"+cscGoodsVo.weight+"</td>";
                    goodsList =goodsList + "<td style='display:none'>"+cscGoodsVo.volume+"</td>";*/
                    goodsList =goodsList + "</tr>";
                });
                $("#goodsSelectListTbody").html(goodsList);
            },"json");
        });
        $("#goodcheck").change(function () {
            if($("#goodcheck").prop("checked")){
                $("#goodsSelectListTbody").find("tr").each(function(index){
                    $(this).children().eq(0).find("input").prop('checked',true);
                });
            }else{
                $("#goodsSelectListTbody").find("tr").each(function(index){
                    $(this).children().eq(0).find("input").prop('checked',false);
                });
            }
        });
        $("#goodsEnter").click(function () {
            var goodsInfoListDiv = "";
            $("#goodsInfoListDiv").find("tr").each(function(index){
                var tdArr = $(this).children();
                goodsList =goodsList + "<td>"+cscGoodsVo.goodsTypeName+"</td>";//货品种类
                goodsList =goodsList + "<td>"+cscGoodsVo.goodsTypeName+"</td>";//货品小类
                goodsList =goodsList + "<td>"+cscGoodsVo.goodsTypeName+"</td>";//品牌
                goodsList =goodsList + "<td>"+cscGoodsVo.goodsCode+"</td>";//货品编码
                goodsList =goodsList + "<td>"+cscGoodsVo.goodsName+"</td>";//货品名称
                goodsList =goodsList + "<td>"+cscGoodsVo.specification+"</td>";//规格
                goodsList =goodsList + "<td>"+cscGoodsVo.unit+"</td>";//单位
                goodsList =goodsList + "<td>"+cscGoodsVo.barCode+"</td>";//条形码
                var goodsTypeName = tdArr.eq(1).text();
                var goodsTypeName = tdArr.eq(1).text();
                var goodsTypeName = tdArr.eq(1).text();
                var goodsTypeName = tdArr.eq(1).text();
                var goodsTypeName = tdArr.eq(1).text();
                var goodsTypeName = tdArr.eq(1).text();
                var goodsTypeName = tdArr.eq(1).text();
                var goodsTypeName = tdArr.eq(1).text();
                var goodsTypeName = tdArr.eq(1).text();
                var goodsTypeName = tdArr.eq(1).text();
                var goodsTypeName = tdArr.eq(1).text();

                var goodsCode = tdArr.eq(1).text();//货品编码
                var goodsName = tdArr.eq(2).text();//货品名称
                var specification = tdArr.eq(3).text();//    货品规格
                var unit = tdArr.eq(4).text();//    单位
                var unitPrice = tdArr.eq(5).text();//    单价
                var quantity = tdArr.eq(6).children().val();//    数量
                var production_batch = tdArr.eq(7).children().val();//    批次
                var production_time = tdArr.eq(8).children().val();//    生产日期
                var invalid_time = tdArr.eq(9).children().val();//    失效日期
                var weight = tdArr.eq(10).children().val();//    重量
                var volume = tdArr.eq(11).children().val();//    体积

                goodsInfoListDiv =goodsInfoListDiv + "<tr role='row' class='odd' align='center'>";
                goodsInfoListDiv =goodsInfoListDiv + "<td><button type='button' onclick='deleteGood(this)' class='btn btn-minier btn-danger'>删除</button></td>";
                /* goodsInfoListDiv =goodsInfoListDiv + "<td><input id='deleteOrNot' type='checkbox'/></td>";*/
                goodsInfoListDiv =goodsInfoListDiv + "<td>"+goodsCode+"</td>";
                goodsInfoListDiv =goodsInfoListDiv + "<td>"+goodsName+"</td>";
                goodsInfoListDiv =goodsInfoListDiv + "<td>"+specification+"</td>";
                goodsInfoListDiv =goodsInfoListDiv + "<td>"+unit+"</td>";
                goodsInfoListDiv =goodsInfoListDiv + "<td>"+unitPrice+"</td>";
                goodsInfoListDiv =goodsInfoListDiv + "<td style='display:none'>"+weight+"</td>";
                goodsInfoListDiv =goodsInfoListDiv + "<td style='display:none'>"+volume+"</td>";
                goodsInfoListDiv =goodsInfoListDiv + "<tr role='row' class='odd' align='center'>";
                goodsInfoListDiv =goodsInfoListDiv + "<td colspan='6'>"
                        + "<button type='button' onclick='deleteGood(this)' class='btn btn-minier btn-danger'>删除(隐藏)</button>"
                        + "</td>";

                goodsInfoListDiv =goodsInfoListDiv + "</tr>";
            });
            $("#goodsInfoListDiv").html("");
            var str = "";
            $("#goodsSelectListTbody").find("tr").each(function(index){
                var tdArr = $(this).children();
                if(tdArr.eq(0).find("input").prop("checked")){
                    var goodsCode = tdArr.eq(1).text();//货品编码
                    var goodsName = tdArr.eq(2).text();//货品名称
                    var specification = tdArr.eq(3).text();//    货品规格
                    var unit = tdArr.eq(4).text();//    单位
                    var unitPrice = tdArr.eq(5).text();//    单价
                    var weight = tdArr.eq(6).text();//    重量
                    var volume = tdArr.eq(7).text();//    体积
                    goodsInfoListDiv =goodsInfoListDiv + "<tr role='row' class='odd' align='center'>";
                    goodsInfoListDiv =goodsInfoListDiv + "<td><button type='button' onclick='deleteGood(this)' class='btn btn-minier btn-danger'>删除</button></td>";
                    /* goodsInfoListDiv =goodsInfoListDiv + "<td><input id='deleteOrNot' type='checkbox'/></td>";*/
                    goodsInfoListDiv =goodsInfoListDiv + "<td>"+goodsCode+"</td>";
                    goodsInfoListDiv =goodsInfoListDiv + "<td>"+goodsName+"</td>";
                    goodsInfoListDiv =goodsInfoListDiv + "<td>"+specification+"</td>";
                    goodsInfoListDiv =goodsInfoListDiv + "<td>"+unit+"</td>";
                    goodsInfoListDiv =goodsInfoListDiv + "<td>"+unitPrice+"</td>";
                    goodsInfoListDiv =goodsInfoListDiv + "<td style='display:none'>"+weight+"</td>";
                    goodsInfoListDiv =goodsInfoListDiv + "<td style='display:none'>"+volume+"</td>";
                    goodsInfoListDiv =goodsInfoListDiv + "</tr>";
                    goodsInfoListDiv =goodsInfoListDiv + "<tr role='row' class='odd' align='center'>";
                    goodsInfoListDiv =goodsInfoListDiv + "<td colspan='6'>"
                            + "<table style='float: left; width: 30%' class='table table-striped table-bordered table-hover dataTable no-footer' role='grid' aria-describedby='dynamic-table_info'>" +
                            "<thead><th>收货方名称</th><th>发货数量</th></thead><tbody><tr><td>五道口</td><td>20</td></tr><tr><td>六道口</td><td>30</td></tr></tbody>" +
                            "</table>"
                            + "</td>";
                    goodsInfoListDiv =goodsInfoListDiv + "</tr>";

                    str="str";
                }
            });
            if(goodsInfoListDiv==""){
                alert("请至少选择一行");
            }else{
                $("#goodsInfoListDiv").html(goodsInfoListDiv);
                $("#goodsListDiv").fadeOut("slow");
            }
            //validateForm();
        });
    })
    function deleteGood(obj) {
        $(obj).parent().parent().remove();
    }
</script>
</body>