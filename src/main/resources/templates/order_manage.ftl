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

        function deleteOrder(ordercode) {
            $("#confirmBox").modal();
           $("#boxmsg").text("您确定要删除此订单?");
            $( "#confirmSure" ).click(function () {
               $.get("/orderDelete",{"orderCode":orderCode},function (data) {
                   $("#confirmBox").modal('hide');
                   window.location.href="/orderScreenByCondition";
               });
           });
        }

        function reviewOrder(ordercode) {
            $("#confirmBox").modal();
            $("#boxmsg").text("您确定要审核此订单?");
            $( "#confirmSure" ).click(function () {
                $.get("/orderOrNotAudit",{"orderCode":ordercode},function (data) {
                    $("#confirmBox").modal('hide');
                    window.location.href="/orderScreenByCondition";
                });
            });
        }
        function reReviewOrder(ordercode) {
            $("#confirmBox").modal();
            $("#boxmsg").text("您确定要反审核此订单?");
            $("#confirmSure").click(function () {
                $.get("/orderOrNotAudit",{"ofcFundamentalInformation.orderCode":ordercode},function (data) {
                    $("#confirmBox").modal('hide');
                    window.location.href="/orderScreenByCondition";
                });
            });
        }
        function cancelOrder(ordercode) {
            $("#confirmBox").modal();
            $("#boxmsg").text("您确定要取消此订单?");
            $( "#confirmSure" ).click(function () {
                $.get("/orderCancel",{"orderCode":ordercode},function (data) {
                    $("#confirmBox").modal('hide');
                    window.location.href="/orderScreenByCondition";
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


<div class="modal fade" id="myAddModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">×</span></button>
                <h4 class="modal-title" id="myModalLabel">新增</h4>
            </div>


            <form action="/insertStaff" method="post" id="addStaffForm">

                <div class="modal-body">

                    <div class="form-group">
                        <label for="txt_departmentname">订单编号</label>
                        <input type="text" name="txt_departmentname" class="form-control" id="txt_departmentname"
                               placeholder="订单编号">
                    </div>
                    <div class="form-group">
                        <label for="txt_parentdepartment">客户订单编号</label>
                        <input type="text" name="txt_parentdepartment" class="form-control" id="txt_parentdepartment"
                               placeholder="客户订单编号">
                    </div>
                    <div class="form-group">
                        <label for="txt_parentdepartment">订单日期</label>
                        <input type="text" name="txt_parentdepartment" class="form-control" id="txt_parentdepartment"
                               placeholder="订单日期">
                    </div>
                    <div class="form-group">
                        <label for="txt_parentdepartment">订单类型</label>
                        <input type="text" name="txt_parentdepartment" class="form-control" id="txt_parentdepartment"
                               placeholder="订单类型">
                    </div>
                    <div class="form-group">
                        <label for="txt_parentdepartment">业务类型</label>
                        <input type="text" name="txt_parentdepartment" class="form-control" id="txt_parentdepartment"
                               placeholder="业务类型">
                    </div>
                    <div class="form-group">
                        <label for="txt_departmentlevel">订单状态</label>
                        <input type="text" name="txt_departmentlevel" class="form-control" id="txt_departmentlevel"
                               placeholder="订单状态">
                    </div>
                    <div class="form-group">
                        <label for="txt_departmentlevel">收货方名称</label>
                        <input type="text" name="txt_departmentlevel" class="form-control" id="txt_departmentlevel"
                               placeholder="收货方名称">
                    </div>
                    <div class="form-group">
                        <label for="txt_departmentlevel">仓库名称</label>
                        <input type="text" name="txt_departmentlevel" class="form-control" id="txt_departmentlevel"
                               placeholder="仓库名称">
                    </div>
                    <div class="form-group">
                        <label for="txt_departmentlevel">店铺</label>
                        <input type="text" name="txt_departmentlevel" class="form-control" id="txt_departmentlevel"
                               placeholder="店铺">
                    </div>
                    <div class="form-group">
                        <label for="txt_statu">备注</label>
                        <input type="text" name="txt_statu" class="form-control" id="txt_statu" placeholder="备注">
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal"><span
                            class="glyphicon glyphicon-remove" aria-hidden="true"></span>关闭
                    </button>
                    <button type="button" id="btn_submit" class="btn btn-primary" data-dismiss="modal"><span
                            class="glyphicon glyphicon-floppy-disk" aria-hidden="true"></span>保存
                    </button>
                </div>
        </div>
        </form>
    </div>
</div>




<div class="modal fade" id="myEditModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">×</span></button>
                <h4 class="modal-title" id="myModalLabel">修改</h4>
            </div>
            <form action="/insertStaff" method="post" id="addStaffForm">
                <div class="modal-body">
                    <div class="form-group">
                        <label for="txt_departmentname">订单编号</label>
                        <input type="text" name="txt_departmentname" class="form-control" id="txt_departmentname"
                               placeholder="订单编号">
                    </div>
                    <div class="form-group">
                        <label for="txt_parentdepartment">客户订单编号</label>
                        <input type="text" name="txt_parentdepartment" class="form-control" id="txt_parentdepartment"
                               placeholder="客户订单编号">
                    </div>
                    <div class="form-group">
                        <label for="txt_parentdepartment">订单日期</label>
                        <input type="text" name="txt_parentdepartment" class="form-control" id="txt_parentdepartment"
                               placeholder="订单日期">
                    </div>
                    <div class="form-group">
                        <label for="txt_parentdepartment">订单类型</label>
                        <input type="text" name="txt_parentdepartment" class="form-control" id="txt_parentdepartment"
                               placeholder="订单类型">
                    </div>
                    <div class="form-group">
                        <label for="txt_parentdepartment">业务类型</label>
                        <input type="text" name="txt_parentdepartment" class="form-control" id="txt_parentdepartment"
                               placeholder="业务类型">
                    </div>
                    <div class="form-group">
                        <label for="txt_departmentlevel">订单状态</label>
                        <input type="text" name="txt_departmentlevel" class="form-control" id="txt_departmentlevel"
                               placeholder="订单状态">
                    </div>
                    <div class="form-group">
                        <label for="txt_departmentlevel">收货方名称</label>
                        <input type="text" name="txt_departmentlevel" class="form-control" id="txt_departmentlevel"
                               placeholder="收货方名称">
                    </div>
                    <div class="form-group">
                        <label for="txt_departmentlevel">仓库名称</label>
                        <input type="text" name="txt_departmentlevel" class="form-control" id="txt_departmentlevel"
                               placeholder="仓库名称">
                    </div>
                    <div class="form-group">
                        <label for="txt_departmentlevel">店铺</label>
                        <input type="text" name="txt_departmentlevel" class="form-control" id="txt_departmentlevel"
                               placeholder="店铺">
                    </div>
                    <div class="form-group">
                        <label for="txt_statu">备注</label>
                        <input type="text" name="txt_statu" class="form-control" id="txt_statu" placeholder="备注">
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal"><span
                            class="glyphicon glyphicon-remove" aria-hidden="true"></span>关闭
                    </button>
                    <button type="button" id="btn_submit" class="btn btn-primary" data-dismiss="modal"><span
                            class="glyphicon glyphicon-floppy-disk" aria-hidden="true"></span>保存
                    </button>
                </div>
        </div>
        </form>
    </div>
</div>
${test!"null"}
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
<button id="btn_add" class="btn btn-default" type="submit" style="float: right">Insert Staff</button>
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
                <button type="button" id="btn_update1" +${order_index} onclick="reviewOrder('${order.orderCode!"null"}')" class="btn btn-default">审核</button>
                <button type="button" id="btn_update2" +${order_index} onclick="editOrder('${order.orderCode!"null"}')" class="btn btn-default">编辑</button>
                <button type="button" id="btn_delete3" +${order_index} onclick="deleteOrder('${order.orderCode!"null"}')" class="btn btn-default">删除</button>
                <button type="button" id="btn_update4" +${order_index} onclick="reReviewOrder('${order.orderCode!"null"}')" class="btn btn-default">反审核</button>
                <button type="button" id="btn_delete5" +${order_index} onclick="cancelOrder('${order.orderCode!"null"}')" class="btn btn-default">取消</button>
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