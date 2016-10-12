<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:th="http://www.thymeleaf.org"
      xmlns:sec="http://www.thymeleaf.org/thymeleaf-extras-springsecurity3">
<head>
    <meta charset="utf-8"/>
    <!-- 新 Bootstrap 核心 CSS 文件 -->
    <link rel="stylesheet" href="//cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css">
    <!-- 可选的Bootstrap主题文件（一般不用引入） -->
    <link rel="stylesheet" href="//cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap-theme.min.css">
    <!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
    <script src="//cdn.bootcss.com/jquery/1.11.3/jquery.min.js"></script>
    <!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
    <script src="//cdn.bootcss.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>

    <script language="javascript">

        $(function () {
            $("#btn_add").click(function () {
                $("#myModalLabel").text("新增");
                $('#myAddModal').modal();

            });

            $("#btn_edit_submit").click(function () {
                $("#updateStaffForm").submit();
            });

            $("#btn_add_submit").click(function () {
                $("#addStaffForm").submit();
            });

        });

        function editOrder() {

            /*$("#myModalLabel").text("修改");*/
            $('#myEditModal').modal();
            /*
             $.get("/getStaffById",{"staffId":id},function (data) {
                 $("#staffId").val(data.id);
                 $("#staffName").val(data.name);
                 $("#staffAge").val(data.age);
                 $("#staffTelephone").val(data.telephone);
                 $("#staffAddress").val(data.address);
             },"json");*/
        }


        function deleteOrder() {
            $("#delConfirm").modal();
            /* $( "#delStaffConfirm" ).click(function () {
                 $.get("/deleteStaffById",{"staffId":id},function (data) {
                     $("#delConfirm").modal('hide');
                     window.location.href="/";
                 });
             });*/
        }

        function reviewOrder() {
            $("#delConfirm").modal();
        }
        function reReviewOrder() {
            $("#delConfirm").modal();
        }
        function cancelOrder() {
            $("#delConfirm").modal();
        }



    </script>

    <title>xescm_ofc</title>
</head>

<body>

<!--删除弹出的确认框-->
<div class="modal fade" id="delConfirm">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title">Modal title</h4>
            </div>
            <div class="modal-body">
                <p>Are you sure?&hellip;</p>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                <button type="button" class="btn btn-primary" id="delStaffConfirm">Sure</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->


${test!"null"}
<div class="page-header">
    <h1>xescm_ofc
    </h1>
</div>

<form action="/orderScreenByCondition" method="post" id="screenOrderForm">
    <input type="hidden" name="tag" value="screen">
    <#--订单日期<input id="orderTimePre" name="orderTimePre" />至<input id="orderTimeSuf" name="orderTimeSuf"/>-->
    订单编号<input id="orderCode" name="orderCode"/>
    客户订单编号<input id="custOrderCode" name="custOrderCode"/><br/>
    订单状态<input id="orderStatus" name="orderStatus"/>
    订单类型<input id="orderType" name="orderType"/>
    业务类型<input id="businessType" name="businessType"/>
    <button type="submit" id="screenOrderBtn">筛选</button>
</form>

<br/></br/>
<button id="btn_add" class="btn btn-default" type="submit" style="float: right">Insert Staff</button>
<table class="table table-hover" width="80%" align="center">
    <!-- On rows -->
    <tr class="active" style="height: 15px">
        <td style="width: 10%;">订单编号</td>
        <td style="width: 10%;">客户订单编号</td>
        <td style="width: 10%;">订单日期</td>
        <td style="width: 10%;">订单类型</td>
        <td style="width: 10%;">业务类型</td>
        <td style="width: 10%;">订单状态</td>
        <td style="width: 10%;">收货方名称</td>
        <td style="width: 5%;">仓库名称</td>
        <td style="width: 5%;">店铺</td>
        <td style="width: 15%">备注</td>

    </tr>

<#list orderList! as order>
    <tr class="active">

        <td style="width: 10%;">${order.orderCode!"null"}</td>
        <td style="width: 10%;">${order.custOrderCode!"null"}</td>
        <td style="width: 10%;">${order.orderTime?string("MM-dd-yyyy")}</td>
        <td style="width: 10%;">${order.orderType!"null"}</td>
        <td style="width: 10%;">${order.businessType!"null"}</td>
        <td style="width: 10%;">${order.orderStatus!"null"}</td>
        <td style="width: 10%;">${order.consigneeName!"null"}</td>
        <td style="width: 5%;">${order.warehouseName!"null"}</td>
        <td style="width: 5%;">${order.notes!"null"}</td>
        <td style="width: 15%">${order.store!"null"}</td>


    </tr>
</#list>
</table>

</body>
</html>