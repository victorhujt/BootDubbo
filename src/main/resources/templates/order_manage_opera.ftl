<head>
  <title>订单管理</title>
  <style type="text/css">
    /*   #goodsListDiv,#consignorListDiv,#consigneeListDiv,#custListDiv,#goodsAndConsigneeDiv {
           position:fixed;
           left:50%;
           top:85px;
           margin-left:-400px;
           width:946px;
           height:500px;
           z-index:3;
           overflow: auto;
           border:solid #7A7A7A 1px;
       }*/
    .date_a {
      line-height: 21px !important;
    }
    /*
            .initBtn{
                height:34px;
                width:34px;
                border:1px solid #cacaca;
                background:#f7f7f7!important;
                cursor:pointer;
                outline:none;
                position:absolute;
                top:0;
                right:0;
              padding-left:9px;
            }
            .initBtn:hover{
                background:#fff !important;
                border:1px solid #cacaca !important;
            }*/
    .col-label{
      margin-left:10px;

    }
    .table > thead > tr > th, .table > tbody > tr > th, .table > tfoot > tr > th, .table > thead > tr > td, .table > tbody > tr > td, .table > tfoot > tr > td{
      padding:6px 8px !important;
      line-height:19px!important;
    }
  </style>
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
    <div class="form-horizontal">
      <div class="form-group">
        <label class="control-label col-label no-padding-right" for="name">客户名称</label>
        <input type="hidden" value="${(OFC_URL)!""}">
        <div style="width:380px;margin-right:15px;" class="padding-12 y-float position-relative">
          <input readonly="readonly" id="custName" class="y-float" style="width:335px;" name="custName" type="search" placeholder=""
                 aria-controls="dynamic-table">
          <label for="custName" class="initBtn" onclick="selectCust();">
            <i class="fa fa-user bigger-130"></i>
          </label>
        <#--  <button type="button" style="height:34px;" onclick="selectCust();" class="btn btn-minier no-padding-right initBtn" id="">
              <i class="fa fa-user l-cor"></i>
          </button>-->
        </div>
        <label class="control-label col-label no-padding-right" for="name">订单编号</label>
        <div class="col-width-168" style="margin:0 12px;">
          <input id="orderCode" class="col-width-168" name="" type="search" placeholder=""
                 aria-controls="dynamic-table">
        </div>
        <label class="control-label col-label no-padding-right" for="name">订单状态</label>
        <div class="col-width-168" style="margin:0 12px;">
          <select data-placeholder="请选择订单状态" id="orderState" name="orderState" class=" chosen-select">
            <option value=""></option>
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
        <div class="padding-12 y-float" style="width:395px;">
          <div class="y-float position-relative">
            <input type="search" placeholder="" aria-controls="dynamic-table" readonly style="width: 170px;" class="laydate-icon" id="startDate" >
            <label for="startDate" class="initBtn">
              <i class="fa fa-calendar bigger-130"></i>
            </label>
          </div>
          <p class="y-float" style="margin:0 8px;line-height:34px;">至</p>
          <div class="y-float position-relative">
            <input type="search" placeholder="" aria-controls="dynamic-table" readonly style="width: 170px;" class="laydate-icon" id="endDate" >
            <label for="endDate" class="initBtn">
              <i class="fa fa-calendar bigger-130"></i>
            </label>
          </div>
        </div>
        <label class="control-label col-label no-padding-right" for="name">订单类型</label>
        <div class="col-width-168" style="margin:0 12px;">
          <div class="col-width-168">
            <select data-placeholder="请选择订单类型" style="width: 168px;" id="orderType" class="chosen-select"
                    name="orderType">
              <option value=""></option>
              <option value="60">运输订单</option>
              <option value="61">仓配订单</option>
            </select>
          </div>
        </div>
        <label class="control-label col-label no-padding-right" for="name">业务类型</label>

        <div class="col-width-168" style="margin:0 12px;">
          <select data-placeholder="请选择业务类型" style="width: 168px;" id="businessType" class="chosen-select"
                  name="businessType">
            <option value=""></option>
            <option value="600">城配</option>
            <option value="601">干线</option>
            <option value="602">卡班</option>
          </select>
        </div>
      </div>
      <div class="form-group">
        <label class="control-label col-label no-padding-right" for="name">大区名称</label>
        <div class="col-width-168" style="width:369px;margin:0 12px;">
          <select data-placeholder="请选择大区名称" id="areaName" name="areaName" class=" chosen-select">
          <#list areaList! as area >
            <option value="${(area.serialNo)!}">${(area.groupName)!}</option>
          </#list>
          </select>
        </div>
        <label class="control-label col-label no-padding-right" style="margin-left: 12px;" for="name">基地名称</label>
        <div class="col-width-168" style="margin:0 12px;">
          <select data-placeholder="请选择基地名称" id="baseName" name="baseName" class=" chosen-select">
          <#list baseList! as base >
            <option value="${(base.serialNo)!}">${(base.groupName)!}</option>
          </#list>
          </select>
        </div>
      </div>
      <div class="form-group">
        <label class="control-label col-label no-padding-right" for="name"></label>
        <button class="btn btn-white btn-info btn-bold filters" id="doSearch" style="margin-left:12px;">筛选</button>
        <button class="btn btn-white btn-info btn-bold filters rtn-btn" id="resetBtn">重置</button>
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
  <table id="dynamic-table" class="table table-striped table-bordered table-hover dataTable no-footer" role="grid"
         aria-describedby="dynamic-table_info" style="margin-bottom:20px;">
    <thead>
    <th style="width:42px;">序号</th>
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
        aria-label="Clicks: activate to sort column ascending">基地名称
    </th>

    </thead>
    <tbody id="dataTbody">
    </tbody>
  </table>
  <div class="row">
    <span id="view7"></span>
    <span id="DataPageBarDiv" style="float: right;padding-top: 0px;margin-top: 0px;">
        </span>
  </div>

  <div class="adoptModal " id="custListDiv" style="display: none;">
  <#-- <div class="modal-header"><span id="custListDivNoneTop" style="cursor:pointer">
       <button type="button" id="" style="cursor:pointer" class="bootbox-close-button close"
               data-dismiss="modal" aria-hidden="true">×</button></span>
       <h4 class="modal-title w-font">选择客户</h4></div>-->
    <div class="modal-body">
      <div class="bootbox-body">
        <form id="consignorSelConditionForm" class="form-horizontal" role="form" style="margin-bottom:15px;">
        <#--<input id="purpose2" name="cscContactDto.purpose" type="hidden" value="2">-->
          <div class="form-group" style="width:100%;">
            <label class="control-label col-label no-padding-right" for="name">名称</label>
            <div class="col-width-220 padding-15 y-float">
              <div class="clearfix">
                <input id="custNameDiv" name="cscContactCompanyDto.contactCompanyName" type="text"
                       style="color: black" class="form-control input-sm" placeholder=""
                       aria-controls="dynamic-table">
              </div>
            </div>
          <#-- <label class="control-label col-xs-1 no-padding-right" for="name"></label>-->
            <div class="col-xs-3 y-float">
              <div class="clearfix">
                <span id="custSelectFormBtn" class="btn btn-white btn-info btn-bold btn-inatervl">筛选</span>
              </div>
            </div>
          <#--    <div class="modal-footer" style="background-color:#fff;"><button style="float: left;display: none;" id="createCustBtn" data-bb-handler="confirm" type="button" class="btn btn-primary">创建新客户</button>
                <button id="custEnter" data-bb-handler="confirm" type="button" class="btn btn-primary">选中</button>
                <span id="custListDivNoneBottom" style="cursor:pointer"><button  data-bb-handler="cancel" type="button" class="btn btn-default">关闭</button></span></div>-->
          </div>
        </form>
        <form class="bootbox-form">
          <table id="dynamic-table" class="table table-striped table-bordered table-hover dataTable no-footer"
                 role="grid" aria-describedby="dynamic-table_info">
            <thead>
            <tr role="row">
              <th class="center sorting_disabled" rowspan="1" colspan="1" aria-label="">
                <label class="pos-rel">
                  选择
                  <span class="lbl"></span>
                </label>
              </th>
              <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Domain: activate to sort column ascending">序号</th>
              <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1"
                  aria-label="Price: activate to sort column ascending">类型
              </th>
              <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1"
                  aria-label="Clicks: activate to sort column ascending">公司名称
              </th>
              <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1"
                  aria-label="Clicks: activate to sort column ascending">渠道
              </th>
              <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1"
                  aria-label="Clicks: activate to sort column ascending">产品类别
              </th>
            </thead>
            <tbody id="custListDivTbody"></tbody>
          </table>
          <div class="row">
            <div id="pageBarDiv" style="float: right;padding-top: 0px;margin-top: 0px;">
            </div>
          </div>
        </form>
      </div>
    </div>
  </div>

  <link rel="stylesheet" href="../components/chosen/chosen.css"/>
  <script src="../components/chosen/chosen.jquery.js"></script>
  <script type="text/javascript">
    var scripts = [null, "../components/chosen/chosen.jquery.js", null]
    $(".page-content-area").ace_ajax("loadScripts", scripts, function () {
      $(document).ready(main);
    });

    var areaSelect = null;
    var baseSelect = null;

    function initChosen() {
      $('.chosen-select').chosen({allow_single_deselect: true});
      //resize the chosen on window resize
      $(window).off('resize.chosen').on('resize.chosen', function () {
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
    }
    function main() {
      //初始化页面数据
      initPageDataOrder();
      // 查询
//            queryOrderData(1);
      initChosen();

      $("#doSearch").click(function () {
        var startDate = $('#startDate').val();
        var endDate = $('#endDate').val();
        if(StringUtil.isEmpty(startDate) || StringUtil.isEmpty(endDate)){
          layer.msg("请补充筛选的时间范围!");
          return;
        }
        queryOrderData(1);
      });
    }


    //页面数据初始化
    function initPageDataOrder() {
      var active_class = "active";
      $("#simple-table > thead > tr > th input[type=checkbox]").eq(0).on("click", function () {
        var th_checked = this.checked;//checkbox inside "TH" table header

        $(this).closest("table").find("tbody > tr").each(function () {
          var row = this;
          if (th_checked) $(row).addClass(active_class).find("input[type=checkbox]").eq(0).prop("checked", true);
          else $(row).removeClass(active_class).find("input[type=checkbox]").eq(0).prop("checked", false);
        });
      });
      $("#simple-table").on("click", "td input[type=checkbox]", function () {
        var $row = $(this).closest("tr");
        if ($row.is(".detail-row ")) return;
        if (this.checked) $row.addClass(active_class);
        else $row.removeClass(active_class);
      });
      areaSelect = $("#areaName").html();
      baseSelect = $("#baseName").html();
    }
  </script>
  <!-- inline scripts related to this page -->
  <script type="text/javascript">

    var now = new Date();
    //当月1号
    var mon = now.getMonth() + 1;
    if(mon < 10){
      mon = "0" + mon;
    }
    var mon1st = now.getFullYear() + "-" + mon + "-01";
    //当天
    $("#startDate").val(mon1st);
    $("#endDate").val(DateUtil.formatDate(now));
    var start = {
      elem: '#startDate',
      format: 'YYYY-MM-DD',
      max: laydate.now(), //最大日期
      istime: false,
      istoday: false,
      isclear: false,
      choose: function(datas){
        var add90 = DateUtil.formatDate(DateUtil.addDays(DateUtil.parse(datas),90))
        var startDate = $("#startDate").val();
        var endDate = $("#endDate").val();
        if(endDate > add90 && endDate != datas){
          $("#endDate").val(add90);
        }
        if(startDate > endDate){
          var nowI = DateUtil.formatDate(now);
          if(endDate > add90){
            $("#endDate").val(nowI);
          }else{
            $("#endDate").val(add90 < nowI ? add90 : nowI);
          }
        }

      }
    };
    var end = {
      elem: '#endDate',
      format: 'YYYY-MM-DD',
      max: laydate.now(),
      istime: false,
      istoday: false,
      isclear: false,
      choose: function(datas){
        var sub90 = DateUtil.formatDate(subDays(DateUtil.parse(datas),90));
        var startDate = $("#startDate").val();
        var endDate = $("#endDate").val();
        if(startDate < sub90 && startDate != datas){
          $("#startDate").val(sub90);
        }
        if(startDate > endDate){
          $("#startDate").val(sub90);
        }
      }
    };

    laydate(start);
    laydate(end);

    $("#startDate").change(function () {
      laydate(start);
      laydate(end);
    });

    $("#endDate").change(function () {
      laydate(start);
      laydate(end);
    });

    function subDays(date,value) {
      date.setDate(date.getDate() - value);
      return date;
    }

    //通过选择的大区加载基地
    $("#areaName").change(function () {
      var areaCode = $("#areaName").val();
      if(StringUtil.isEmpty(areaCode)){
        $("#baseName option").remove();
        $("#baseName").html("")
        $("#baseName").html(baseSelect)
        $("#baseName").trigger("chosen:updated");
        return;
      }


      if($("#areaName option").length <= 1 || StringUtil.isEmpty(areaCode)){
        return;
      }
      $("#baseName option").remove();
      CommonClient.post(sys.rootPath + "/ofc/queryBaseListByArea",{"areaCode":areaCode},function(data) {
        if (data == undefined || data == null || null == data.result
                || undefined == data.result || data.result.size == 0) {
          layer.msg("暂时未查询到基地信息！！");
          $("#baseName").trigger("chosen:updated");
        }else if(data.code == 200){
          data=eval(data.result);
          $("#baseName").append("<option value=''></option>");
          $.each(data,function (index,baseMsg) {
            $("#baseName").append("<option value='"+baseMsg.serialNo+"'>"+baseMsg.groupName+"</option>");
            $("#baseName").trigger("chosen:updated");
          });
        }else if (data.code == 403) {
          layer.msg("没有权限!")
          $("#baseName").trigger("chosen:updated");
        } else if(data.code == 500){
          layer.msg("查询基地出错!");
          $("#baseName").trigger("chosen:updated");
        } else {
          layer.msg("查询基地出错!");
          $("#baseName").trigger("chosen:updated");
        }
      })

    })

    //通过选择的基地反查大区
    $("#baseName").change(function () {
      var baseCode = $("#baseName").val();
      if($("#areaName option").length <= 1 || StringUtil.isEmpty(baseCode)){
        return;
      }

      CommonClient.post(sys.rootPath + "/ofc/queryAreaMsgByBase",{"baseCode":baseCode},function(data) {
        if (data == undefined || data == null || null == data.result
                || undefined == data.result || data.result.size == 0) {
          layer.msg("暂时未查询到所属大区信息！！");
          $("#areaName").trigger("chosen:updated");
        }else if(data.code == 200){
          data = data.result;
          var $area = $("#areaName option[value = '" + data.serialNo + "']");
          $area.attr({selected:'selected'});
          $("#areaName").trigger("chosen:updated");
        }else if (data.code == 403) {
          layer.msg("没有权限!")
          $("#areaName").trigger("chosen:updated");
        } else if(data.code == 500){
          layer.msg("查询大区出错!");
          $("#areaName").trigger("chosen:updated");
        } else {
          layer.msg("查询大区出错!");
          $("#areaName").trigger("chosen:updated");
        }
      })

    })


    function queryOrderData(pageNum) {

      if($("#areaName option").length < 1 || $("#baseName option").length < 1){
        layer.msg("您没有大区或基地！无法进行筛选!");
        return;
      }
      var areaName = $("#areaName").val();
      var baseName = $("#baseName").val();
      if(!StringUtil.isEmpty(baseName) && StringUtil.isEmpty(areaName)){
        layer.msg("请选择基地所属大区！无法进行筛选!");
        return;
      }
      var param = {};
      param.pageNum = pageNum;
      param.pageSize = 10;
      param.custName = $("#custName").val();
      var startDate = $('#startDate').val() + " 00:00:00";
      var endDate = $('#endDate').val() + " 23:59:59";
      param.startDate = startDate;
      param.endDate = endDate;
      param.orderCode = $("#orderCode").val();
      param.orderState= $("#orderState").val();
      param.orderType = $("#orderType").val();
      param.businessType = $("#businessType").val();
      param.areaSerialNo = areaName;
      param.baseSerialNo = baseName;
      CommonClient.post(sys.rootPath + "/ofc/queryOrderDataOper", param, function (result) {

        if (result == undefined || result == null || result.result.size == 0 || result.result.list == null) {
          $("#dataTbody").html("");
          $("#DataPageBarDiv").hide();
          layer.msg("暂时未查询到相关订单信息！");
          $('#view7').html("");
        } else if (result.code == 200) {// 1:normal
          $("#DataPageBarDiv").show();
          reloadGrid(result);// 刷新页面数据
          laypage({
            cont: $("#DataPageBarDiv"), // 容器。值支持id名、原生dom对象，jquery对象,
            pages: result.result.pages, // 总页数
            skip: true, // 是否开启跳页
            skin: "molv",
            groups: 3, // 连续显示分页数
            curr: result.result.pageNum, // 当前页
            jump: function (obj, first) { // 触发分页后的回调
              if (!first) { // 点击跳页触发函数自身，并传递当前页：obj.curr
                queryOrderData(obj.curr);
              }
              $('#view7').html('共有'+ result.result.total +'条记录');
            }
          });
        } else if (result.code == 403) {
          alert("没有权限")
        } else {
          $("#dataTbody").html("");
        }
      });
    }

    function reloadGrid(data) {
      var htmlText = "";

      if (data == null || data == "" || data == undefined) {
        $("#dataTbody").html("");
        return;
      }

      var length = data.result.list.length;
      if (length < 1) {
        $("#dataTbody").html("");
        return;
      }
      $.each(data.result.list, function (index, item) {
        var order = item;
        var consigneeName = "";
        if ("2" == StringUtil.nullToEmpty(order.consigneeType)) {
          consigneeName = "个人-" + order.consigneeContactName;
        } else {
          consigneeName = order.consigneeName;
        }
        htmlText += "<tr role=\"row\" class=\"odd\">"
                + "<td>" + [index + 1] + "</td>"
                + "<td>" + getOperatorByStatusOper(order, index) + "</td>"
                + "<td>" + StringUtil.nullToEmpty(order.custName) + "</td>"
                + "<td>"
                + "<a onclick=\"orderDetailOper('" + order.orderCode + "')\">" + StringUtil.nullToEmpty(order.orderCode) + "</a>"
                + "</td>"
                + "<td>"
                + "<a onclick=\"queryOrderDetailBatchOpera('" + order.orderBatchNumber + "')\">" + StringUtil.nullToEmpty(order.orderBatchNumber) + "</a>"
                + "</td>"
                + "<td class=\"hidden-480\">" + StringUtil.nullToEmpty(order.custOrderCode) + "</td>"
                + "<td class=\"hidden-480\">" + subTimeString(StringUtil.nullToEmpty(order.orderTime)) + "</td>"
                + "<td class=\"hidden-480\">" + getOrderType(order) + "</td>"
                + "<td class=\"hidden-480\">" + getBusiType(order) + "</td>"
                + "<td class=\"hidden-480\">" + getOrderStatus(order.orderStatus) + "</td>"
                + "<td class=\"hidden-480\">" + StringUtil.nullToEmpty(consigneeName) + "</td>"
                + "<td class=\"hidden-480\">" + StringUtil.nullToEmpty(order.baseName) + "</td>"
                + "</tr>";
      })
      $("#dataTbody").html(htmlText);
    }

    function getOrderStatus(status) {
      var value = "";
      if (status == '10') {
        value = "<span class=\"label label-sm label-yellow\">待审核</span>"
      } else if (status == '20') {
        value = "<span class=\"label label-sm label-warning\">已审核</span>"
      } else if (status == '30') {
        value = "<span class=\"label label-sm label-info\">执行中</span>"
      } else if (status == '40') {
        value = "<span class=\"label label-sm label-success\">已完成</span>"
      } else if (status == '50') {
        value = "<span class=\"label label-sm label-default\">已取消</span>"
      }
      return value;
    }

    function getBusiType(order) {
      var value = "";
      if (order.businessType == '600') {
        value = "城配"
      } else if (order.businessType == "601") {
        value = "干线";
      } else if (order.businessType == "602") {
        value = "卡班";
      } else if (order.businessType == "610") {
        value = "销售出库";
      } else if (order.businessType == "611") {
        value = "调拨出库";
      } else if (order.businessType == "612") {
        value = "报损出库";
      } else if (order.businessType == "613") {
        value = "其他出库";
      } else if (order.businessType == "614") {
        value = "分拨出库";
      } else if (order.businessType == "620") {
        value = "采购入库";
      } else if (order.businessType == "621") {
        value = "调拨入库";
      } else if (order.businessType == "622") {
        value = "退货入库";
      } else if (order.businessType == "623") {
        value = "加工入库";
      } else if (order.businessType == "624") {
        value = "盘盈入库";
      } else if (order.businessType == "625") {
        value = "流通入库";
      } else if (order.businessType == "626") {
        value = "其他入库";
      } else if (order.businessType == "627") {
        value = "分拨入库";
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

    function getOrderType(order) {
      var value = "";
      if (order.orderType == "60") {
        value = "运输订单";
      } else if (order.orderType == "61") {
        value = "仓配订单";
      }
      return value;

    }

    function getOperatorByStatusOper(order, index) {
      var value = "";

      var newStatus = "<a id=\"review\" " + index + " onclick=\"reviewOrderOper('" + order.orderCode + "','" + order.orderStatus + "')\" class=\"blue\">审核</a>"
              + "<a id=\"delete\" " + index + " onclick=\"deleteOrder('" + order.orderCode + "','" + order.orderStatus + "')\"  class=\"red\">删除</a>";

      var unApproveStatus = "<a id=\"rereview\" " + index + " onclick=\"reReviewOrderOper('" + order.orderCode + "','" + order.orderStatus + "')\"  class=\"blue\">反审核</a>";
      var cancelStatus = "<a id=\"cancel\" " + index + " onclick=\"cancelOrderOper('" + order.orderCode + "','" + order.orderStatus + "')\"  class=\"blue\">取消</a>";

      if (order.orderStatus == "10") {
        value = newStatus;
      }
      if (order.orderStatus == "20" || order.orderStatus == "30") {
        value = unApproveStatus + cancelStatus;
      }
      if (order.orderStatus == "30") {
        value = cancelStatus;
      }
      return value;
    }

    //删除订单
    function deleteOrder(ordercode, orderStatus) {
      xescm.common.submit("/ofc/orderDeleteOper", {
        "orderCode": ordercode,
        "orderStatus": orderStatus
      }, "您确定要删除此订单?", function () {
        xescm.common.loadPage("/ofc/orderManageOpera");
      });
    }
    //订单详情
    function orderDetailOper(orderCode) {
      var url = "/ofc/orderDetailPageByCode/" + orderCode;
      var html = window.location.href;
      var index = html.indexOf("/index#");
      window.open(html.substring(0,index) + "/index#" + url);
//            xescm.common.loadPage("/ofc/orderDetailPageByCode/" + orderCode);
    }

    function queryOrderDetailBatchOpera(orderBatchCode) {
      var url = "/ofc/orderDetailBatchOpera/" + orderBatchCode;
      var html = window.location.href;
      var index = html.indexOf("/index#");
      window.open(html.substring(0,index) + "/index#" + url);
//            xescm.common.loadPage("/ofc/orderDetailBatchOpera/" + orderBatchCode);
    }

    //订单审核、反审核
    function reviewOrderOper(ordercode, orderStatus) {
      xescm.common.submit("/ofc/orderOrNotAuditOper", {
        "orderCode": ordercode,
        "orderStatus": orderStatus,
        "reviewTag": "review"
      }, "您确定要审核此订单?", function () {

        xescm.common.loadPage("/ofc/orderManageOpera");
      });
    }
    function reReviewOrderOper(ordercode, orderStatus) {
      xescm.common.submit("/ofc/orderOrNotAuditOper", {
        "orderCode": ordercode,
        "orderStatus": orderStatus,
        "reviewTag": "rereview"
      }, "您确定要反审核此订单?", function () {
        xescm.common.loadPage("/ofc/orderManageOpera");
      });
    }
    //订单取消
    function cancelOrderOper(ordercode, orderStatus) {
      xescm.common.submit("/ofc/orderCancelOper", {
        "orderCode": ordercode,
        "orderStatus": orderStatus
      }, "您确定要取消此订单?", function () {
        xescm.common.loadPage("/ofc/orderManageOpera");
      });
    }

    function selectCust() {
      confirmCus();
    }

    $("#custListDivNoneBottom,#custListDivNoneTop").on("click", function () {
      $("#custListDiv").hide();
    });

    $("#custSelectFormBtn").on("click", function () {
      var custName = $("#custNameDiv").val();
      queryCustomerData(custName, 1);
    });

    // 分页查询客户列表
    function queryCustomerData(custName, pageNum) {
      $("#custListDivTbody").html("");
      var custName = $("#custNameDiv").val();
      var param = {};
      param.pageNum = pageNum;
      param.pageSize = 10;
      param.custName = custName;
      CommonClient.post(sys.rootPath + "/ofc/distributing/queryCustomerByName", param, function(result) {
        if (result == undefined || result == null || result.result == null || result.result.size == 0 || result.result.list == null) {
          layer.msg("暂时未查询到客户信息！");
        } else if (result.code == 200) {
          loadCustomer(result);
          laypage({
            cont: $("#pageBarDiv"), // 容器。值支持id名、原生dom对象，jquery对象,
            pages: result.result.pages, // 总页数
            skip: true, // 是否开启跳页
            skin: "molv",
            groups: 3, // 连续显示分页数
            curr: result.result.pageNum, // 当前页
            jump: function (obj, first) { // 触发分页后的回调
              if (!first) { // 点击跳页触发函数自身，并传递当前页：obj.curr
                queryCustomerData(custName,obj.curr);
              }
            }
          });
          $(".total").html(result.result.total+'&nbsp;条&nbsp;');
        } else if (result.code == 403) {
          alert("没有权限")
        } else {
          $("#custListDivTbody").html("");
        }
      },"json");
    }

    // 加载客户列表
    function loadCustomer(data) {
      if ((data == null || data == '' || data == undefined) || (data.result.list.length < 1)) {
        $("#custListDivTbody").html("");
        return;
      }
      var custList = "";
      $.each(data.result.list,function (index,cscCustomerVo) {
        var channel = cscCustomerVo.channel;
        if(null == channel){
          channel = "";
        }
        custList =custList + "<tr role='row' class='odd' onclick='chosenTr(this)'>";
        custList =custList + "<td class='center'> " + "<label class='pos-rel'>" + "<input value='" + cscCustomerVo.customerName + "' name='cust' type='radio' class='ace'>" + "<span class='lbl'></span>" + "</label>" + "</td>";
        custList =custList + "<td>"+(index+1)+"</td>";
        var custType = StringUtil.nullToEmpty(cscCustomerVo.type);
        if(custType == '1'){
          custList =custList + "<td>公司</td>";
        }else if (custType == '2'){
          custList =custList + "<td>个人</td>";
        }else{
          custList =custList + "<td>"+custType+"</td>";
        }
        custList =custList + "<td>"+cscCustomerVo.customerName+"</td>";
        custList =custList + "<td>"+channel+"</td>";
        custList =custList + "<td>"+cscCustomerVo.productType+"</td>";
        custList =custList + "</tr>";
        $("#custListDivTbody").html(custList);
      });
    }

    /*  $("#custEnter").on("click", function () {
          var val = $('input:radio[name="cust"]:checked').val();
          if (val == "" || val == null || val == undefined) {
              alert("请选择客户");
              return;
          }
          $("#custName").val(val);
          $("#custListDiv").hide();
      })
*/
    function appendSelect(type) {
      if (type == "60") {
        return "<option value=''></option><option value='600'>城配</option><option value='601'>干线</option><option value='602'>卡班</option>";
      } else if (type == "61") {
        var html = "";
        html += "<option value=''></option>";
        html += "<option value='610'>销售出库</option>";
        html += "<option value='611'>调拨出库</option>";
        html += "<option value='612'>报损出库</option>";
        html += "<option value='613'>其他出库</option>";
        html += "<option value='614'>分拨出库</option>";
        html += "<option value='620'>采购入库</option>";
        html += "<option value='621'>调拨入库</option>";
        html += "<option value='622'>退货入库</option>";
        html += "<option value='623'>加工入库</option>";
        html += "<option value='624'>盘盈入库</option>";
        html += "<option value='625'>流通入库</option>";
        html += "<option value='626'>其他入库</option>";
        html += "<option value='627'>分拨入库</option>";
        return html
      }
      return null;

    }
    $("#orderType").on("change", function () {
      var type = $(this).val();
      if (type == "60" || type == "61") {
        console.log(type)
        $("#businessType").empty().append(appendSelect(type));
        $("#businessType").trigger("chosen:updated");
      }else if(type==''){
        $("#businessType").empty();
        $("#businessType").trigger("chosen:updated");
      }
    });

    $("#createCustBtn").click(function () {
      var csc_url = "${(CSC_URL)!}";
      var url = csc_url + "/csc/customer/toAddCustomerPage";
      xescm.common.loadPage(url);
    });
    $("#resetBtn").click(function(){
      $("#startDate").val(mon1st);
      $("#endDate").val(DateUtil.formatDate(now));
      $("#custName").val("");
      $("#orderCode").val("");
      $("#orderState").val("").trigger("chosen:updated");
      $("#orderType").val("").trigger("chosen:updated");
      $("#businessType").val("").trigger("chosen:updated");
      //重新加载大区和基地
      $("#areaName option").remove();
      $("#baseName option").remove();
      $("#areaName").html("")
      $("#baseName").html("")
      $("#areaName").html(areaSelect)
      $("#baseName").html(baseSelect)
      $("#areaName").trigger("chosen:updated");
      $("#baseName").trigger("chosen:updated");
    })
    //点击一行表示选中单选框
    function chosenTr(e){
      $(e).children().first().find("input").prop("checked","checked");
    }

    function confirmCus() {
      layer.open({
        btn:['选中','关闭'],
        yes:function (adoptModalIndex) {
          var val = $('input:radio[name="cust"]:checked').val();
          if (val == "" || val == null || val == undefined) {
            alert("请选择客户");
            return;
          }
          $("#custName").val(val);
          layer.close(adoptModalIndex);
          return false;
        },
        type: 1,
        area: ['946px', '580px'],
        content: $('#custListDiv'), //这里content是一个DOM
        title: '选择客户',
        cancel: function (adoptModalIndex) {
          layer.close(adoptModalIndex);
          return false;
        }
      });
    }
  </script>

</body>