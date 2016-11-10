<head xmlns="http://www.w3.org/1999/html" xmlns="http://www.w3.org/1999/html">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta charset="utf-8">
    <title>运输计划单分配</title>

    <meta name="description" content="Static &amp; Dynamic Tables">

    <script language="javascript" type="text/javascript" src="../js/bootstrap-paginator.js"></script>
    <style type="text/css">
        #serviceProviderListDiv1 {
            position:fixed;
            left:185px;
            top:85px;
            width:1146px;
            height:500px;
            z-index:3;
            overflow: auto;
            border:solid #7A7A7A 2px;
        }
        #serviceProviderListDiv2 {
            position:fixed;
            left:185px;
            top:85px;
            width:1146px;
            height:500px;
            z-index:3;
            overflow: auto;
            border:solid #7A7A7A 2px;
        }
    </style>
</head>

<body class="no-skin">
<!-- #section:basics/navbar.layout -->
<div class="modal-content" id="serviceProviderListDiv1" style="display: none;">
    <div class="modal-header"><span id="serviceProviderDivNoneTop1" style="cursor:pointer"><button type="button" id="" style="cursor:pointer" class="bootbox-close-button close" data-dismiss="modal" aria-hidden="true">×</button></span>
        <h4 class="modal-title">服务商列表</h4></div>
    <div class="modal-body">
        <div class="bootbox-body">
            <form id="goodsSelConditionForm1" class="form-horizontal" role="form">
                <div class="form-group">
                    <label class="control-label col-sm-1 no-padding-right" for="name">出发地</label>
                    <div class="col-sm-3">
                        <div class="clearfix">
                            <div class="docs-methods">
                                <input id="city-picker-cfd1" class="form-control" readonly type="text" value="" data-toggle="city-picker">
                                <input  id = "goodsCodeCondition1" name="beginProvinceName" type="text" style="color: black" class="form-control input-sm" placeholder="" aria-controls="dynamic-table">
                            </div>
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-sm-1 no-padding-right" for="name">目的地</label>
                    <div class="col-sm-3">
                        <div class="clearfix">
                            <div class="docs-methods">
                                <input id="city-picker-mdd1" class="form-control" readonly type="text" value="" data-toggle="city-picker">
                                <input  id = "goodsNameCondition1" name="arriveProvinceName" type="text" style="color: black" class="form-control input-sm" placeholder="" aria-controls="dynamic-table">
                            </div>
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-sm-1 no-padding-right" for="name">服务商名称</label>
                    <div class="col-sm-3">
                        <div class="clearfix">
                            <input  id = "goodsNameCondition1" name="companyName" type="text" style="color: black" class="form-control input-sm" placeholder="" aria-controls="dynamic-table">
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-sm-1 no-padding-right" for="name"></label>
                    <div class="col-sm-3">
                        <div class="clearfix">
                            <span id="goodsSelectFormBtn1" class="btn btn-info btn-sm popover-info">筛选</span>
                        </div>
                    </div>
                </div>
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
                    <#--<th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Domain: activate to sort column ascending">序号</th>-->
                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Price: activate to sort column ascending">服务商名称</th>
                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">线路名称</th>
                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">发站</th>
                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">到站</th>
                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">发车频次</th>
                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">经停</th>
                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">容量</th>
                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">温度带</th>
                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">联系人</th>
                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">联系电话</th>

                    </thead>
                    <tbody id="goodsSelectListTbody1"></tbody>
                </table>


            </form>
        </div>
    </div>
    <div class="modal-footer"><span id="goodsListDivNoneBottom1" style="cursor:pointer"><button  data-bb-handler="cancel" type="button" class="btn btn-default">Cancel</button></span><button id="goodsEnter1" data-bb-handler="confirm" type="button" class="btn btn-primary">OK</button></div>
</div>
<div class="modal-content" id="serviceProviderListDiv2" style="display: none;">
    <div class="modal-header"><span id="serviceProviderDivNoneTop2" style="cursor:pointer"><button type="button" id="" style="cursor:pointer" class="bootbox-close-button close" data-dismiss="modal" aria-hidden="true">×</button></span>
        <h4 class="modal-title">服务商列表</h4></div>
    <div class="modal-body">
        <div class="bootbox-body">
            <form id="goodsSelConditionForm2" class="form-horizontal" role="form">
                <div class="form-group">
                    <label class="control-label col-sm-1 no-padding-right" for="name">出发地</label>
                    <div class="col-sm-3">
                        <div class="clearfix">
                            <div class="docs-methods">
                                <input id="city-picker-cfd2" class="form-control" readonly type="text" value="" data-toggle="city-picker">
                                <input  id = "goodsCodeCondition2" name="goodsCode" type="text" style="color: black" class="form-control input-sm" placeholder="" aria-controls="dynamic-table">
                            </div>
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-sm-1 no-padding-right" for="name">目的地</label>
                    <div class="col-sm-3">
                        <div class="clearfix">
                            <div class="docs-methods">
                                <input id="city-picker-mdd2" class="form-control" readonly type="text" value="" data-toggle="city-picker">
                                <input  id = "goodsNameCondition2" name="goodsName" type="text" style="color: black" class="form-control input-sm" placeholder="" aria-controls="dynamic-table">
                            </div>
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-sm-1 no-padding-right" for="name">服务商名称</label>
                    <div class="col-sm-3">
                        <div class="clearfix">
                            <input  id = "goodsNameCondition2" name="goodsName" type="text" style="color: black" class="form-control input-sm" placeholder="" aria-controls="dynamic-table">
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-sm-1 no-padding-right" for="name"></label>
                    <div class="col-sm-3">
                        <div class="clearfix">
                            <span id="goodsSelectFormBtn2" class="btn btn-info btn-sm popover-info">筛选</span>
                        </div>
                    </div>
                </div>
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
                    <#--<th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Domain: activate to sort column ascending">序号</th>-->
                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Price: activate to sort column ascending">服务商名称</th>
                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">城市</th>
                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">覆盖范围</th>
                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">车辆资源</th>
                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">配送频次</th>
                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">温度带</th>
                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">联系人</th>
                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">联系电话</th>

                    </thead>
                    <tbody id="goodsSelectListTbody2"></tbody>
                </table>


            </form>
        </div>
    </div>
    <div class="modal-footer"><span id="goodsListDivNoneBottom2" style="cursor:pointer"><button  data-bb-handler="cancel" type="button" class="btn btn-default">Cancel</button></span><button id="goodsEnter2" data-bb-handler="confirm" type="button" class="btn btn-primary">OK</button></div>
</div>

<!-- /section:basics/navbar.layout -->
<div class="main-container ace-save-state" id="main-container">
    <!-- /section:basics/sidebar -->
    <div class="main-content">
        <div class="main-content-inner">
            <!-- /section:basics/content.breadcrumbs -->
            <div class="page-content">
                <div class="page-header">
                    <p>
                        筛选条件
                    </p>
                </div>
                <div class="row">
                    <div class="col-xs-12">
                        <div class="form-horizontal">
                            <div class="form-group">
                                <label class="control-label col-sm-1 no-padding-right" for="name">资源分配状态</label>
                                <div class="col-sm-6">
                                    <div class="clearfix">
                                        <select multiple="" class="chosen-select form-control" id="resourceAllocationStatues" name="resourceAllocationStatues" data-placeholder="请选择一个状态..." style="display: none;">
                                            <option value="10">待分配</option>
                                            <option value="20">未分配</option>
                                            <option value="30">已分配</option>
                                            <option value="40">已确定</option>
                                        </select>
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-sm-1 no-padding-right" for="name">客户名称</label>
                                <div class="col-sm-6">
                                    <div class="clearfix">
                                        <input id="custName" name="custName" type="search" class="form-control" placeholder="" aria-controls="dynamic-table">
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-sm-1 no-padding-right" for="name">订单批次号</label>
                                <div class="col-sm-6">
                                    <div class="clearfix">
                                        <input id="orderBatchNumber" name="orderBatchNumber" style="color: black" type="search" class="form-control input-sm" placeholder="" aria-controls="dynamic-table">
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-sm-1 no-padding-right" for="name">订单日期</label>
                                <div class="col-sm-6">
                                    <div class="clearfix">
                                        <input id="orderTimePre" name="orderTimePre" type="datetime"  placeholder="" aria-controls="dynamic-table" onClick="WdatePicker({isShowClear:true,readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})">
                                        至
                                        <input id="orderTimeSuf" name="orderTimeSuf" type="search"  placeholder="" aria-controls="dynamic-table"onClick="WdatePicker({isShowClear:true,readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})">
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-sm-1 no-padding-right" for="name"></label>
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
                <span style="cursor:pointer" id="serviceProviderDivBlock"><button class="btn btn-info btn-sm popover-info" id="doSelt" value="">分配资源</button></span>
                <button class="btn btn-info btn-sm popover-info" id="doEnter" >资源确认</button>
                <div id="dynamic-table_wrapper" class="dataTables_wrapper form-inline no-footer">

                        <table id="dynamic-table" class="table table-striped table-bordered table-hover dataTable no-footer" role="grid" aria-describedby="dynamic-table_info">
                            <thead>
                            <tr role="row">
                                <th>序号</th>
                                <#--<th class="center sorting_disabled" rowspan="1" colspan="1" aria-label="">-->
                                    <#--<label class="pos-rel">-->
                                        <#--<input type="checkbox" class="ace">-->
                                        <#--<span class="lbl"></span>-->
                                    <#--</label>-->
                                <#--</th>-->
                                <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Domain: activate to sort column ascending">选择</th>
                                <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Domain: activate to sort column ascending">订单编号</th>
                                <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Price: activate to sort column ascending">计划单编号</th>
                                <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">订单批次号</th>
                                <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">类型</th>
                                <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">资源分配状态</th>
                                <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">服务商名称</th>
                                <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">订单日期</th>
                                <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">出发地</th>
                                <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">目的地</th>
                                <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">数量</th>
                                <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">重量</th>
                                <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">资源确认人员</th>
                                <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">资源确认时间</th>
                                <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">服务商联系人</th>
                                <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">服务商联系电话</th>

                            </thead>
                            <tbody id="dataTbody">
                            <#--订单列表数据-->
                            </tbody>
                        </table>
                        <div class="row">
                            <div id="pageBarDiv" style="float: right;padding-top: 0px;margin-top: 0px;">
                        </div>
                    </div>

                <!-- PAGE CONTENT ENDS -->
            </div><!-- /.col -->
        </div><!-- /.row -->
    </div><!-- /.page-content -->
</div>
</div><!-- /.main-content -->

</div><!-- /.main-container -->



<script type="text/javascript">
    $(function () {

    });
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
        queryData(1);

        $("#doSearch").click(function () {
            queryData(1);
        });

    }

    //页面数据初始化
    function initPageData(){
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

        $("#goodsSelectFormBtn1").click(function () {
            CommonClient.post(sys.rootPath + "/ofc/companySelect", $("#goodsSelConditionForm1").serialize()+"&lineType=1", function(data) {
                data=eval(data);

                if(data.length>1){
                    var goodsList = "";
                    var fre=null;
                    $.each(data,function (index,RmcCompanyLineVo) {
                        goodsList =goodsList + "<tr role='row' class='odd'>";
                        goodsList =goodsList + "<td class='center'> "+"<label class='pos-rel'>"+"<input name='compListQuantity' type='radio' class='ace'>"+"<span class='lbl'></span>"+"</label>"+"</td>";
                        goodsList =goodsList + "<td>"+StringUtil.nullToEmpty(RmcCompanyLineVo.companyName)+"</td>";
                        goodsList =goodsList + "<td>"+StringUtil.nullToEmpty(RmcCompanyLineVo.lineName)+"</td>";
                        goodsList =goodsList + "<td>"+StringUtil.nullToEmpty(RmcCompanyLineVo.beginProvinceName)+StringUtil.nullToEmpty(RmcCompanyLineVo.beginCityName)+StringUtil.nullToEmpty(RmcCompanyLineVo.beginAreaName)+"</td>";
                        goodsList =goodsList + "<td>"+StringUtil.nullToEmpty(RmcCompanyLineVo.arriveProvinceName)+StringUtil.nullToEmpty(RmcCompanyLineVo.arriveCityName)+StringUtil.nullToEmpty(RmcCompanyLineVo.arriveArea)+"</td>";
                        goodsList =goodsList + "<td>"+StringUtil.nullToEmpty(RmcCompanyLineVo.frequency)+"</td>";
                        goodsList =goodsList + "<td>"+StringUtil.nullToEmpty(RmcCompanyLineVo.frequency)+"</td>";
                        goodsList =goodsList + "<td>"+StringUtil.nullToEmpty(RmcCompanyLineVo.frequency)+"</td>";
                        goodsList =goodsList + "<td>"+StringUtil.nullToEmpty(RmcCompanyLineVo.frequency)+"</td>";
                        goodsList =goodsList + "<td>"+StringUtil.nullToEmpty(RmcCompanyLineVo.contactName)+"</td>";
                        goodsList =goodsList + "<td>"+StringUtil.nullToEmpty(RmcCompanyLineVo.companyPhone)+"</td>";
                        goodsList =goodsList + "</tr>";
                        if(RmcCompanyLineVo.frequency!=null){

                        }
                    });
                    $("#goodsSelectListTbody1").html(goodsList);
                    var intx=1;
                    var var3=null;
                    $("#dataTbody").find("tr").each(function(index){
                        var tdArr = $(this).children();
                        if(tdArr.eq(1).find("input").prop("checked")){
                            if(intx==1){
                                var3 = tdArr.eq(3).text();
                            }
                            intx=intx+1;
                        }
                    });
                    layer.confirm("查询结果为多条记录，将根据预计发货时间或者订单时间进行二次筛选，是否确定?", {
                        skin : 'layui-layer-molv',
                        icon : 3,
                        title : '确认操作'
                    }, function(){
                        CommonClient.post(sys.rootPath + "/ofc/planSeleForTime", $("#goodsSelConditionForm1").serialize()+"&planCode="+var3+"&lineType=1", function(data){
                            //xescm.common.submit("/ofc/planSeleForTime",{"planCode":var3},"查询结果为多条记录，将根据预计发货时间或者订单时间进行二次筛选，是否确定?",function (data) {
                            alert(data);

                            data=eval(data);
                            if(data.length>=1){
                                var str="";
                                var var3=null;
                                $("#dataTbody").find("tr").each(function(index){
                                    var tdArr = $(this).children();
                                    var3 = tdArr.eq(3).text();
                                    if(tdArr.eq(1).find("input").prop("checked")){
                                        str=str+var3+"@";
                                    }
                                });
                                debugger;
                                if(str.length>0){
                                    xescm.common.submit("/ofc/planUpdate",{"planCode":str,"planStatus":"30","serviceProviderName":data[0].companyName,"serviceProviderContact":data[0].contactName,"serviceProviderContactPhone":data[0].companyPhone},"已找到相关服务商，自动匹配最近时间服务商，是否确定?",function () {

                                        $("#serviceProviderListDiv1").fadeOut("slow");//淡入淡出效果 隐藏div
                                        queryData(1);
                                    });
                                }
                            }
                        });
                    }, function(index){
                        layer.close(index);
                    });

                }else if(data.length==1){
                    var str="";
                    var var3=null;
                    $("#dataTbody").find("tr").each(function(index){
                        var tdArr = $(this).children();
                        var3 = tdArr.eq(3).text();
                        if(tdArr.eq(1).find("input").prop("checked")){
                            str=str+var3+"@";
                        }
                    });

                    if(str.length>0){
                        xescm.common.submit("/ofc/planUpdate",{"planCode":str,"planStatus":"30","serviceProviderName":data[0].companyName,"serviceProviderContact":data[0].contactName,"serviceProviderContactPhone":data[0].companyPhone},"查询结果只有一条记录，将自动进行分配，是否确定?",function () {

                            $("#serviceProviderListDiv1").fadeOut("slow");//淡入淡出效果 隐藏div
                            queryData(1);
                        });
                    }
                }else if(data.length==0){
                    var str="";
                    var var3=null;
                    $("#dataTbody").find("tr").each(function(index){
                        var tdArr = $(this).children();
                        var3 = tdArr.eq(3).text();
                        if(tdArr.eq(1).find("input").prop("checked")){
                            str=str+var3+"@";
                        }
                    });

                    if(str.length>0){
                        xescm.common.submit("/ofc/planUpdate",{"planCode":str,"planStatus":"20","serviceProviderName":null,"serviceProviderContact":null,"serviceProviderContactPhone":null},"查不到数据，将更新资源分配状态为未分配，是否确定?",function () {

                            $("#serviceProviderListDiv1").fadeOut("slow");//淡入淡出效果 隐藏div
                            queryData(1);
                        });
                    }
                    /*var htmlText="";
                    $("#dataTbody").find("tr").each(function(index){
                        var tdArr = $(this).children();
                        var var0 = tdArr.eq(0).text();
                        var var2 = tdArr.eq(2).text();
                        var var3 = tdArr.eq(3).text();
                        var var4 = tdArr.eq(4).text();
                        var var5 = tdArr.eq(5).text();
                        var var6 = tdArr.eq(6).text();
                        var var7 = tdArr.eq(7).text();
                        var var8 = tdArr.eq(8).text();
                        var var9 = tdArr.eq(9).text();
                        var var10 = tdArr.eq(10).text();
                        var var11 = tdArr.eq(11).text();
                        var var12 = tdArr.eq(12).text();
                        var var13 = tdArr.eq(13).text();
                        var var14 = tdArr.eq(14).text();
                        var var15 = tdArr.eq(15).text();
                        var var16 = tdArr.eq(16).text();


                        htmlText +="<tr role=\"row\" class=\"odd\">"
                        htmlText +="<td>"+var0+"</td>"
                        if(tdArr.eq(1).find("input").prop("checked")){
                            htmlText +="<td class='center'> "+"<label class='pos-rel'>"+"<input id='selGoods' type='checkbox' checked='checked' class='ace'>"+"<span class='lbl'></span>"+"</label>"+"</td>"
                                    +"<td>"+StringUtil.nullToEmpty(var2)+"</td>"
                                    +"<td class=\"hidden-480\">"+StringUtil.nullToEmpty(var3)+"</td>"
                                    +"<td class=\"hidden-480\">"+StringUtil.nullToEmpty(var4)+"</td>"
                                    +"<td class=\"hidden-480\">"+StringUtil.nullToEmpty(var5)+"</td>"
                                    +"<td class=\"hidden-480\">"+StringUtil.nullToEmpty(getSourceStatus("20"))+"</td>"

                        }else{
                            htmlText +="<td class='center'> "+"<label class='pos-rel'>"+"<input id='selGoods' type='checkbox' class='ace'>"+"<span class='lbl'></span>"+"</label>"+"</td>"
                                    +"<td>"+StringUtil.nullToEmpty(var2)+"</td>"
                                    +"<td class=\"hidden-480\">"+StringUtil.nullToEmpty(var3)+"</td>"
                                    +"<td class=\"hidden-480\">"+StringUtil.nullToEmpty(var4)+"</td>"
                                    +"<td class=\"hidden-480\">"+StringUtil.nullToEmpty(var5)+"</td>"
                                    +"<td class=\"hidden-480\">"+StringUtil.nullToEmpty(var6)+"</td>"
                        }
                        htmlText +="<td class=\"hidden-480\">"+StringUtil.nullToEmpty(var7)+"</td>"
                                +"<td class=\"hidden-480\">"+StringUtil.nullToEmpty(var8)+"</td>"
                                +"<td class=\"hidden-480\">"+StringUtil.nullToEmpty(var9)+"</td>"
                                +"<td class=\"hidden-480\">"+StringUtil.nullToEmpty(var10)+"</td>"
                                +"<td class=\"hidden-480\">"+StringUtil.nullToEmpty(var11)+"</td>"
                                +"<td class=\"hidden-480\">"+StringUtil.nullToEmpty(var12)+"</td>"
                                +"<td class=\"hidden-480\">"+StringUtil.nullToEmpty(var13)+"</td>"
                                +"<td class=\"hidden-480\">"+StringUtil.nullToEmpty(var14)+"</td>"
                                +"<td class=\"hidden-480\">"+StringUtil.nullToEmpty(var15)+"</td>"
                                +"<td class=\"hidden-480\">"+StringUtil.nullToEmpty(var16)+"</td>"
                                + "</tr>";
                    });
                    $("#dataTbody").html(htmlText);*/
                }

            },"json");
        });

        $("#goodsSelectFormBtn2").click(function () {
            CommonClient.post(sys.rootPath + "/ofc/companySelect", $("#goodsSelConditionForm2").serialize()+"&lineType=2", function(data) {

                data=eval(data);
                if(data.length>1){
                    var i=1;
                    var txt="";
                    var serviceProviderContact="";
                    var serviceProviderContactPhone="";
                    var goodsList = "";
                    $.each(data,function (index,RmcCompanyLineVo) {
                        if(i==1){
                            txt=RmcCompanyLineVo.companyName;
                            serviceProviderContact=RmcCompanyLineVo.contactName;
                            serviceProviderContactPhone=RmcCompanyLineVo.companyPhone;
                        }
                        goodsList =goodsList + "<tr role='row' class='odd'>";
                        goodsList =goodsList + "<td class='center'> "+"<label class='pos-rel'>"+"<input name='compListQuantity' type='radio' class='ace'>"+"<span class='lbl'></span>"+"</label>"+"</td>";
                        goodsList =goodsList + "<td>"+StringUtil.nullToEmpty(RmcCompanyLineVo.companyName)+"</td>";
                        goodsList =goodsList + "<td>"+StringUtil.nullToEmpty(RmcCompanyLineVo.beginProvinceName)+"</td>";
                        goodsList =goodsList + "<td>"+StringUtil.nullToEmpty(RmcCompanyLineVo.beginProvinceName)+"</td>";
                        goodsList =goodsList + "<td>"+StringUtil.nullToEmpty(RmcCompanyLineVo.beginProvinceName)+"</td>";
                        goodsList =goodsList + "<td>"+StringUtil.nullToEmpty(RmcCompanyLineVo.beginProvinceName)+"</td>";
                        goodsList =goodsList + "<td>"+StringUtil.nullToEmpty(RmcCompanyLineVo.frequency)+"</td>";
                        goodsList =goodsList + "<td>"+StringUtil.nullToEmpty(RmcCompanyLineVo.contactName)+"</td>";
                        goodsList =goodsList + "<td>"+StringUtil.nullToEmpty(RmcCompanyLineVo.companyPhone)+"</td>";
                        goodsList =goodsList + "</tr>";
                        i=i+1;
                    });
                    $("#goodsSelectListTbody2").html(goodsList);
                    var str="";
                    var var3=null;
                    $("#dataTbody").find("tr").each(function(index){
                        var tdArr = $(this).children();
                        var3 = tdArr.eq(3).text();
                        if(tdArr.eq(1).find("input").prop("checked")){
                            str=str+var3+"@";
                        }
                    });

                    if(str.length>0){
                        xescm.common.submit("/ofc/planUpdate",{"planCode":str,"planStatus":"30","serviceProviderName":txt,"serviceProviderContact":serviceProviderContact,"serviceProviderContactPhone":serviceProviderContactPhone},"城配计划单，记录有多条时将自动选择第一条记录，是否确定?",function () {

                            $("#serviceProviderListDiv2").fadeOut("slow");//淡入淡出效果 隐藏div
                            queryData(1);
                        });
                    }
                }else if(data.length==1){
                    var str="";
                    var var3=null;
                    $("#dataTbody").find("tr").each(function(index){
                        var tdArr = $(this).children();
                        var3 = tdArr.eq(3).text();
                        if(tdArr.eq(1).find("input").prop("checked")){
                            str=str+var3+"@";
                        }
                    });

                    if(str.length>0){
                        xescm.common.submit("/ofc/planUpdate",{"planCode":str,"planStatus":"30","serviceProviderName":data[0].companyName,"serviceProviderContact":data[0].contactName,"serviceProviderContactPhone":data[0].companyPhone},"查询结果只有一条记录，将自动进行分配，是否确定?",function () {
                            debugger;
                            $("#serviceProviderListDiv2").fadeOut("slow");//淡入淡出效果 隐藏div
                            queryData(1);
                        });
                    }
                }else if(data.length==0){
                    var str="";
                    var var3=null;
                    $("#dataTbody").find("tr").each(function(index){
                        var tdArr = $(this).children();
                        var3 = tdArr.eq(3).text();
                        if(tdArr.eq(1).find("input").prop("checked")){
                            str=str+var3+"@";
                        }
                    });

                    if(str.length>0){
                        xescm.common.submit("/ofc/planUpdate",{"planCode":str,"planStatus":"20","serviceProviderName":null,"serviceProviderContact":null,"serviceProviderContactPhone":null},"查不到数据，将更新资源分配状态为未分配，是否确定?",function () {

                            $("#serviceProviderListDiv2").fadeOut("slow");//淡入淡出效果 隐藏div
                            queryData(1);
                        });
                    }
                }

            },"json");
        });

        $("#goodsEnter1").click(function () {
            var str="";
            var var3=null;
            var serviceProviderName="";
            var serviceProviderContact="";
            var serviceProviderContactPhone="";
            debugger;
            $("#goodsSelectListTbody1").find("tr").each(function(index){
                var tdArr = $(this).children();
                if(tdArr.eq(0).find("input").prop("checked")){
                    serviceProviderName = tdArr.eq(1).text();
                    serviceProviderContact = tdArr.eq(9).text();
                    serviceProviderContactPhone = tdArr.eq(10).text();
                    debugger;
                }
            });
            $("#dataTbody").find("tr").each(function(index){
                var tdArr = $(this).children();
                var3 = tdArr.eq(3).text();
                if(tdArr.eq(1).find("input").prop("checked")){
                    str=str+var3+"@";
                }
            });
            CommonClient.post(sys.rootPath + "/ofc/planUpdate", "planCode="+str+"&planStatus=30"+"&serviceProviderName="+serviceProviderName+"&serviceProviderContact="+serviceProviderContact+"&serviceProviderContactPhone="+serviceProviderContactPhone, function(data){
                $("#serviceProviderListDiv1").fadeOut("slow");//淡入淡出效果 隐藏div
                queryData(1);
            });
        });

        $("#goodsEnter2").click(function () {
            var str="";
            var var3=null;
            var serviceProviderName="";
            var serviceProviderContact="";
            var serviceProviderContactPhone="";
            $("#goodsSelectListTbody2").find("tr").each(function(index){
                var tdArr = $(this).children();
                if(tdArr.eq(0).find("input").prop("checked")){
                    serviceProviderName = tdArr.eq(1).text();
                    serviceProviderContact = tdArr.eq(7).text();
                    serviceProviderContactPhone = tdArr.eq(8).text();
                }
            });
            $("#dataTbody").find("tr").each(function(index){
                var tdArr = $(this).children();
                var3 = tdArr.eq(3).text();
                if(tdArr.eq(1).find("input").prop("checked")){
                    str=str+var3+"@";
                }
            });
            CommonClient.post(sys.rootPath + "/ofc/planUpdate","planCode="+str+"&planStatus=30"+"&serviceProviderName="+serviceProviderName+"&serviceProviderContact="+serviceProviderContact+"&serviceProviderContactPhone="+serviceProviderContactPhone, function(data){
                $("#serviceProviderListDiv2").fadeOut("slow");//淡入淡出效果 隐藏div
                queryData(1);
            });
        });

        $("#doEnter").click(function(){
            var serive = "";
            var str="";
            var var3="";
            var i=0;
            $("#dataTbody").find("tr").each(function(index){
                var tdArr = $(this).children();
                if(tdArr.eq(1).find("input").prop("checked")){
                    var resourceAllocationStatus = tdArr.eq(6).text();//资源分配状态
                    var tdArr = $(this).children();
                    var3 = tdArr.eq(3).text();

                    if(resourceAllocationStatus=="已确定"){
                        alert("您选择的记录中存在【已确认】的记录，请重新选择！");
                        serive="determined"
                        return;
                    }else if(resourceAllocationStatus=="待分配" || resourceAllocationStatus=="未分配"){
                        alert("您选择的记录中存在未分配资源的记录，请重新选择！");
                        serive="determined"
                        return;
                    }else{
                        str=str+var3+"@";
                    }
                    i=i+1;
                }
            });

            if(var3==""){
                alert("请至少选择一条记录");
            }else if(serive=="determined"){
                return;
            }else {
                if(str.length>0){
                    xescm.common.submit("/ofc/planUpdate",{"planCode":str,"planStatus":"40","serviceProviderName":null},"您选择了"+i+"条记录进行资源确认，是否继续操作?",function () {
                        queryData(1);
                    });
                }

            }
        });

        $("#serviceProviderDivBlock").click(function(){
            var serive = "";
            var i=1;
            var diff=null;
            $("#dataTbody").find("tr").each(function(index){
                var tdArr = $(this).children();

                if(tdArr.eq(1).find("input").prop("checked")){
                    var businessType = tdArr.eq(5).text();//计划单类型
                    var resourceAllocationStatus = tdArr.eq(6).text();//资源分配状态
                    var departure = tdArr.eq(9).text();//出发地
                    var destination = tdArr.eq(10).text();//    目的地
                    if(resourceAllocationStatus=="已确定"){
                        alert("您选择的记录中存在【已确认】的记录，请重新选择！");
                        serive="determined"
                        return;
                    }
                    if(businessType!="干线" && businessType!="城配"){
                        alert("您选择的记录中存在非城配和干线的订单，请重新选择！");
                        serive="determined"
                        return;
                    }
                    if(i==1){
                        serive=businessType;
                        diff=businessType;
                        $("#goodsCodeCondition1").val(departure);
                        $("#goodsNameCondition1").val(destination);
                        $("#goodsCodeCondition2").val(departure);
                        $("#goodsNameCondition2").val(destination);
                    }else{
                        if(diff!=businessType){
                            alert("不允许同时选择干线与城配类型的计划单，请重新选择！");
                            serive="different"
                            return;
                        }
                    }
                    i=i+1;
                }
            });
            if(serive==""){
                alert("请至少选择一条记录");
            }else {
                if(serive=="干线"){
                    $("#serviceProviderListDiv1").fadeIn("slow");//淡入淡出效果 显示div
                }else if(serive=="城配"){
                    $("#serviceProviderListDiv2").fadeIn("slow");//淡入淡出效果 显示div
                }else if(serive=="determined") {
                    return;
                }else if(serive=="different") {
                    return;
                }

            }
        });

        $("#serviceProviderDivNoneTop1").click(function(){

            $("#serviceProviderListDiv1").fadeOut("slow");//淡入淡出效果 隐藏div

        });
        $("#serviceProviderDivNoneTop2").click(function(){

            $("#serviceProviderListDiv2").fadeOut("slow");//淡入淡出效果 隐藏div

        });
        $("#goodsListDivNoneBottom1").click(function(){

            $("#serviceProviderListDiv1").fadeOut("slow");//淡入淡出效果 隐藏div

        });
        $("#goodsListDivNoneBottom2").click(function(){

            $("#serviceProviderListDiv2").fadeOut("slow");//淡入淡出效果 隐藏div

        });
    }
</script>
<!-- inline scripts related to this page -->
<script type="text/javascript">
    function queryData(pageNum) {

        var param = {};

        param.pageNum = pageNum;
        param.pageSize = 10;
        var orderTimePre = $dp.$('orderTimePre').value;
        var orderTimeSuf = $dp.$('orderTimeSuf').value;
        param.orderTimePre = orderTimePre;
        param.orderTimeSuf = orderTimeSuf;
        param.custName = $("#custName").val();
        param.orderBatchNumber = $("#orderBatchNumber").val();
        var rsa = $("#resourceAllocationStatues").val();
        //alert($("#resourceAllocationStatus").val());


        var list = [];
        if(rsa!=null && rsa!=""){
            for(var i=0;i<rsa.length;i++){
                if(rsa[i]!=""){
                    list.push(rsa[i])
                }
            }
            param.resourceAllocationStatues=list+"*|*";
        }else {
            param.resourceAllocationStatues=rsa;
        }


        CommonClient.post(sys.rootPath + "/ofc/queryPlanPageByCondition", param, function(result) {

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

            htmlText +="<tr role=\"row\" class=\"odd\">"
                    +"<td>"+[i+1]+"</td>"
                    +"<td class='center'> "+"<label class='pos-rel'>"+"<input id='selGoods' type='checkbox' class='ace'>"+"<span class='lbl'></span>"+"</label>"+"</td>"
                    +"<td>"+StringUtil.nullToEmpty(order.orderCode)+"</td>"
                    +"<td class=\"hidden-480\">"+StringUtil.nullToEmpty(order.planCode)+"</td>"
                    +"<td class=\"hidden-480\">"+StringUtil.nullToEmpty(order.orderBatchNumber)+"</td>"
                    +"<td class=\"hidden-480\">"+StringUtil.nullToEmpty(getPlanType(order.businessType))+"</td>"
                    +"<td class=\"hidden-480\">"+StringUtil.nullToEmpty(getSourceStatus(order.resourceAllocationStatus))+"</td>"
                    +"<td class=\"hidden-480\">"+StringUtil.nullToEmpty(order.serviceProviderName)+"</td>"
                    +"<td class=\"hidden-480\">"+StringUtil.nullToEmpty(order.orderTime)+"</td>"
                    +"<td class=\"hidden-480\">"+StringUtil.nullToEmpty(order.departureProvince)+StringUtil.nullToEmpty(order.departureCity)+StringUtil.nullToEmpty(order.departureDistrict)+StringUtil.nullToEmpty(order.departureTowns)+"</td>"
                    +"<td class=\"hidden-480\">"+StringUtil.nullToEmpty(order.destinationProvince)+StringUtil.nullToEmpty(order.destinationCity)+StringUtil.nullToEmpty(order.destinationDistrict)+StringUtil.nullToEmpty(order.destinationTown)+"</td>"
                    +"<td class=\"hidden-480\">"+StringUtil.nullToEmpty(order.quantity)+"</td>"
                    +"<td class=\"hidden-480\">"+StringUtil.nullToEmpty(order.weight)+"</td>"
                    +"<td class=\"hidden-480\">"+StringUtil.nullToEmpty(order.resourceConfirmation)+"</td>"
                    +"<td class=\"hidden-480\">"+StringUtil.nullToEmpty(order.resourceConfirmationTime)+"</td>"
                    +"<td class=\"hidden-480\">"+StringUtil.nullToEmpty(order.serviceProviderContact)+"</td>"
                    +"<td class=\"hidden-480\">"+StringUtil.nullToEmpty(order.serviceProviderContactPhone)+"</td>"
                    + "</tr>";
        }
        $("#dataTbody").html(htmlText);

    }

    function getPlanType(status) {
        var value ="";
        if(status == '600'){
            value = "城配"
        }else if(status == '601'){
            value = "干线"
        }else{
            value = "类型有误"
        }
        return value;
    }

    function getSourceStatus(status) {
        var value = "";
        if(status == '10'){
            value = "待分配"
        }else if(status == "20"){
            value = "未分配";
        }else if(status == "30"){
            value = "已分配";
        }else if(status == "40"){
            value = "已确定";
        }
        return value;
    }

</script>
<link href= "../css/bootstrap.min.css" rel="stylesheet" type="text/css" />
<link href="../css/city-picker.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="../components/chosen/chosen.css" />
<script src="../components/chosen/chosen.jquery.js"></script>
<script src="../js/city-picker.data.js"></script><#--111-->
<script src="../js/city-picker.js"></script><#--111-->

</body>
