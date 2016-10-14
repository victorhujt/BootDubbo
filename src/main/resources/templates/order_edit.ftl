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
            $("#screenOrderBtn").click(function () {
                $("#orderPlaceConTable").submit;
                alert(${orderPlaceResult!"null"});
            });
        });
    </script>

    <title>xescm_ofc</title>
</head>

<body>

<div class="page-header">
    <h1>xescm_ofc
    </h1>
</div>
<form action="/orderPlaceCon" method="post" id="orderPlaceConTable">
    <input type="hidden" name="tag" value="manage">


    <h2>订单中心下单</h2>
    <h3>基本信息</h3>
    <#--订单日期<input id="orderTimePre" name="orderTimePre" />-->
    订单类型
    <select id="" name="orderType">
        <option value="0">运输订单</option>
        <option value="1">仓配订单</option>
    </select>
    业务类型
    <select id="" name="businessType">
        <option value="00">城配</option>
        <option value="01">干线</option>
        <option value="----------">----------</option>
        <option value="10">销售出库</option>
        <option value="11">调拨出库</option>
        <option value="12">报损出库</option>
        <option value="13">其他出库</option>
        <option value="----------">----------</option>
        <option value="14">采购入库</option>
        <option value="15">调拨入库</option>
        <option value="16">退货入库</option>
        <option value="17">加工入库</option>
    </select>
    客户订单编号<input id="custOrderCode" name="custOrderCode"/>
    店铺
    <select id="" name="storeCode">
        <option value="线下销售">线下销售</option>
        <option value="众品天猫生鲜旗舰店">众品天猫生鲜旗舰店</option>
        <option value="众品京东旗舰店">众品京东旗舰店</option>
    </select>
    备注
    <input id="" name="notes"/>
    <h3>货品明细</h3>
<#--

    <table class="table table-hover" width="80%" align="center">
        <!-- On rows &ndash;&gt;
        <tr class="active" style="height: 15px">
            <td style="width: 10%;">货品编号</td>
            <td style="width: 10%;">货品名称</td>
            <td style="width: 10%;">货品规格</td>
            <td style="width: 5%;">单位</td>
            <td style="width: 5%;">数量</td>
            <td style="width: 10%;">生产批次</td>
            <td style="width: 10%;">生产日期</td>
            <td style="width: 5%;">失效日期</td>
            <td style="width: 5%;">操作</td>
        </tr>
    &lt;#&ndash;
    vo  value object  拼出来的结果集
    dto  参数,给后端当参数,或者返回的dot.
    &ndash;&gt;
    <#list orderList! as order>
        <tr class="active">
            <td style="width: 10%;">货品编号</td>
            <td style="width: 10%;">货品名称</td>
            <td style="width: 10%;">货品规格</td>
            <td style="width: 5%;">单位</td>
            <td style="width: 5%;">数量</td>
            <td style="width: 10%;">生产批次</td>
            <td style="width: 10%;">生产日期</td>
            <td style="width: 5%;">失效日期</td>
            <td style="width: 5%;">
                <div class="btn-group" role="group">
                    <button type="button" id="btn_delete3" +${order_index} onclick="deleteOrder('${order.orderCode!"null"}')" class="btn btn-default">删除</button>
                </div>
            </td>
        </tr>
    </#list>
    </table>
-->

    <hr/>
    <h2>运输信息</h2>
    <h3>基本信息</h3>
    货品类型
    <input id="" name="goodsType"/>
    数量
    <input id="" name="quantity"/>
    重量
    <input id="" name="weight"/>
    体积
    <input id="" name="cubage"/>
    出发地
    <input id="" name="departurePlace"/>
    目的地
    <input id="" name="destination"/>
    取货时间
    <#--<input id="" name="pickupTime"/>
    期望送达时间
    <input id="" name="expectedArrivedTime"/>-->
    运输要求
    <h3>发货方信息</h3>
    名称
    <input id="" name="consignorName"/>
    联系人
    <input id="" name=""/>
    联系电话
    <input id="" name=""/>
    传真
    <input id="" name=""/>
    Email
    <input id="" name=""/>
    邮编
    <input id="" name=""/>
    地址
    <input id="" name=""/>
    <h3>收货方信息</h3>
    名称
    <input id="" name="consigneeName"/>
    联系人
    <input id="" name=""/>
    联系电话
    <input id="" name=""/>
    传真
    <input id="" name=""/>
    Email
    <input id="" name=""/>
    邮编
    <input id="" name=""/>
    地址
    <input id="" name=""/>



    <hr/>
    <h2>仓配信息</h2>
    <h3>基本信息</h3>
    仓库名称
    <input id="" name="warehouseName"/>
    入库预计到达时间
    <#--<input id="" name="arriveTime"/>-->
    车牌号
    <input id="" name="plateNumber"/>
    司机姓名
    <input id="" name="driverName"/>
    联系电话
    <input id="" name="contactName"/>
    <h3>供应商信息</h3>
    名称
    <input id="" name="supportName"/>
    联系人
    <input id="" name=""/>
    联系电话
    <input id="" name=""/>
    传真
    <input id="" name=""/>
    Email
    <input id="" name=""/>
    邮编
    <input id="" name=""/>
    地址
    <input id="" name=""/>


<hr/>
    <button type="button" id="screenOrderBtn">下单</button>
</form>

<br/></br/>

</body>
</html>