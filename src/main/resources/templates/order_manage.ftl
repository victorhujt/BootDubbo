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

        function editOrder(orderCode) {
            /*跳转到订单的可编辑页(跟下单页面一样!), 并回显该订单数据*/
            $('#myEditModal').modal();
             $.get("/getOrderDetailByCode",{"orderCode":orderCode},function (data) {
                 location.href="/placeOrEditOrder?ofcOrderDTO="+data;
             },"json");
        }

        function deleteOrder(ordercode,orderStatus) {
            $("#confirmBox").modal();
           $("#boxmsg").text("您确定要删除此订单?");
            $( "#confirmSure" ).click(function () {
               $.get("/orderDelete",{"orderCode":ordercode,"orderStatus":orderStatus},function (data) {
                   $("#confirmBox").modal('hide');
                   if(data == 200){
                       window.location.href="/orderScreenByCondition?tag=manage";
                   } else {
                       alert("删除订单失败,请联系管理员!");
                   }
               });
           });
        }

        function reviewOrder(ordercode,orderStatus) {
            $("#confirmBox").modal();
            $("#boxmsg").text("您确定要审核此订单?");
            $( "#confirmSure" ).click(function () {
                $.get("/orderOrNotAudit",{"orderCode":ordercode,"orderStatus":orderStatus,"reviewTag":"review"},function (data) {
                    $("#confirmBox").modal('hide');
                    if(data == 200){
                        window.location.href="/orderScreenByCondition?tag=manage";
                    } else {
                        alert("审核订单失败,请联系管理员!");
                    }
                });
            });
        }
        function reReviewOrder(ordercode,orderStatus) {
            $("#confirmBox").modal();
            $("#boxmsg").text("您确定要反审核此订单?");
            $("#confirmSure").click(function () {
                $.get("/orderOrNotAudit",{"orderCode":ordercode,"orderStatus":orderStatus,"reviewTag":"rereview"},function (data) {
                    $("#confirmBox").modal('hide');
                    if(data == 200){
                        window.location.href="/orderScreenByCondition?tag=manage";
                    } else {
                        alert("反审核订单失败,请联系管理员!");
                    }
                });
            });
        }
        function cancelOrder(ordercode,orderStatus) {
            $("#confirmBox").modal();
            $("#boxmsg").text("您确定要取消此订单?");
            $( "#confirmSure" ).click(function () {
                $.get("/orderCancel",{"orderCode":ordercode,"orderStatus":orderStatus},function (data) {
                    $("#confirmBox").modal('hide');
                    if(data == 200){
                        window.location.href="/orderScreenByCondition?tag=manage";
                    } else {
                        alert("取消订单失败,请联系管理员!");
                    }
                });
            });
        }
    </script>

    <title>xescm_ofc</title>
</head>

<body>

<!--确认框-->
<div class="modal fade" id="confirmBox">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title">Modal title</h4>
            </div>
            <div class="modal-body">
                <p id="boxmsg">Are you sure?&hellip;</p>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                <button type="button" class="btn btn-primary" id="confirmSure">Sure</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->





<div class="page-header">
    <h1>xescm_ofc
    </h1>
</div>

<form action="/orderScreenByCondition" method="post" id="screenOrderForm">
    <input type="hidden" name="tag" value="manage">
<#--订单日期<input id="orderTimePre" name="orderTimePre" />至<input id="orderTimeSuf" name="orderTimeSuf"/>-->
    订单编号<input id="orderCode" name="orderCode"/>
    客户订单编号<input id="custOrderCode" name="custOrderCode"/><br/>
    订单状态<input id="orderStatus" name="orderStatus"/>
    订单类型<input id="orderType" name="orderType"/>
    业务类型<input id="businessType" name="businessType"/>
    <button type="submit" id="screenOrderBtn">筛选</button>
</form>

<br/></br/>
<table class="table table-hover" width="80%" align="center">
    <!-- On rows -->
    <tr class="active" style="height: 15px">
        <td style="width: 15%;">操作</td>
        <td style="width: 10%;">订单编号</td>
        <td style="width: 10%;">客户订单编号</td>
        <td style="width: 10%;">订单日期</td>
        <td style="width: 5%;">订单类型</td>
        <td style="width: 5%;">业务类型</td>
        <td style="width: 10%;">订单状态</td>
        <td style="width: 10%;">收货方名称</td>
        <td style="width: 5%;">仓库名称</td>
        <td style="width: 5%;">店铺</td>
        <td style="width: 15%">备注</td>

    </tr>
<#--
vo  value object  拼出来的结果集
dto  参数,给后端当参数,或者返回的dot.
-->
<#list orderList! as order>
    <tr class="active">
        <td style="width: 10%;">
            <div class="btn-group" role="group">
                <button type="button" id="btn_update1" +${order_index} onclick="reviewOrder('${order.orderCode!"null"}','${order.orderStatus!"null"}')" class="btn btn-default">审核</button>
                <button type="button" id="btn_update2" +${order_index} onclick="editOrder('${order.orderCode!"null"}')" class="btn btn-default">编辑</button>
                <button type="button" id="btn_delete3" +${order_index} onclick="deleteOrder('${order.orderCode!"null"}','${order.orderStatus!"null"}')" class="btn btn-default">删除</button>
                <button type="button" id="btn_update4" +${order_index} onclick="reReviewOrder('${order.orderCode!"null"}','${order.orderStatus!"null"}')" class="btn btn-default">反审核</button>
                <button type="button" id="btn_delete5" +${order_index} onclick="cancelOrder('${order.orderCode!"null"}','${order.orderStatus!"null"}')" class="btn btn-default">取消</button>
            </div>
        </td>
        <td style="width: 10%;">${order.orderCode!"null"}</td>
        <td style="width: 10%;">${order.custOrderCode!"null"}</td>
        <td style="width: 10%;">${order.orderTime?string("MM-dd-yyyy")}</td>
        <td style="width: 5%;">${order.orderType!"null"}</td>
        <td style="width: 5%;">${order.businessType!"null"}</td>
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