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


    <h2>订单中心编辑</h2>
    <h3>基本信息</h3>
    <#--订单日期<input id="orderTimePre" name="orderTimePre" />-->
    订单类型
    <select id="orderType" name="orderType" value="${(orderInfo.orderType)!"0"}">
        <option value="60" <#if orderInfo.orderType?? ><#if ((orderInfo.orderType)== '0')>selected="selected"</#if></#if>>运输订单</option>
        <option value="61" <#if orderInfo.orderType?? ><#if ((orderInfo.orderType)== '1')>selected="selected"</#if></#if>>仓配订单</option>
    </select>
    业务类型
    <select id="businessType" name="businessType" value="${(orderInfo.businessType)!"0"}">
        <option value="600" <#if orderInfo.businessType?? ><#if ((orderInfo.businessType)== '00')>selected="selected"</#if></#if>>城配</option>
        <option value="601" <#if orderInfo.businessType?? ><#if ((orderInfo.businessType)== '01')>selected="selected"</#if></#if>>干线</option>
        <option value="----------">----------</option>
        <option value="610" <#if orderInfo.businessType?? ><#if ((orderInfo.businessType)== '10')>selected="selected"</#if></#if>>销售出库</option>
        <option value="611" <#if orderInfo.businessType?? ><#if ((orderInfo.businessType)== '11')>selected="selected"</#if></#if>>调拨出库</option>
        <option value="612" <#if orderInfo.businessType?? ><#if ((orderInfo.businessType)== '12')>selected="selected"</#if></#if>>报损出库</option>
        <option value="613" <#if orderInfo.businessType?? ><#if ((orderInfo.businessType)== '13')>selected="selected"</#if></#if>>其他出库</option>
        <option value="----------">----------</option>
        <option value="620" <#if orderInfo.businessType?? ><#if ((orderInfo.businessType)== '14')>selected="selected"</#if></#if>>采购入库</option>
        <option value="621" <#if orderInfo.businessType?? ><#if ((orderInfo.businessType)== '15')>selected="selected"</#if></#if>>调拨入库</option>
        <option value="622" <#if orderInfo.businessType?? ><#if ((orderInfo.businessType)== '16')>selected="selected"</#if></#if>>退货入库</option>
        <option value="623" <#if orderInfo.businessType?? ><#if ((orderInfo.businessType)== '17')>selected="selected"</#if></#if>>加工入库</option>
    </select>
    客户订单编号<input id="custOrderCode" name="custOrderCode" <#if orderInfo.custOrderCode?? >value="${orderInfo.custOrderCode}"</#if>/>
    店铺
    <select id="" name="storeCode" value="${(orderInfo.storeName)!"0"}">
        <option value="线下销售" <#if orderInfo.storeName?? ><#if ((orderInfo.storeName)== '线下销售')>selected="selected"</#if></#if>>线下销售</option>
        <option value="众品天猫生鲜旗舰店" <#if orderInfo.storeName?? ><#if ((orderInfo.storeName)== '众品天猫生鲜旗舰店')>selected="selected"</#if></#if>>众品天猫生鲜旗舰店</option>
        <option value="众品京东旗舰店" <#if orderInfo.storeName?? ><#if ((orderInfo.storeName)== '众品京东旗舰店')>selected="selected"</#if></#if>>众品京东旗舰店</option>
    </select>
    备注
    <input id="notes" name="notes" <#if orderInfo.notes?? >value="${orderInfo.notes}"</#if>/>
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
    <input id="" name="goodsType" <#if orderInfo.goodsType?? >value="${orderInfo.goodsType}"</#if>/>
    数量
    <input id="" name="quantity" <#if orderInfo.quantity?? >value="${orderInfo.quantity}"</#if>/>
    重量
    <input id="" name="weight" <#if orderInfo.weight?? >value="${orderInfo.weight}"</#if>/>
    体积
    <input id="" name="cubage" <#if orderInfo.cubage?? >value="${orderInfo.cubage}"</#if>/>
    出发地
    <input id="" name="departurePlace" <#if orderInfo.departurePlace?? >value="${orderInfo.departurePlace}"</#if>/>
    目的地
    <input id="" name="destination" <#if orderInfo.destination?? >value="${orderInfo.destination}"</#if>/>
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
    <input id="" name="warehouseName" <#if orderInfo.warehouseName?? >value="${orderInfo.warehouseName}"</#if>/>
    入库预计到达时间
    <#--<input id="" name="arriveTime"/>-->
    车牌号
    <input id="" name="plateNumber" <#if orderInfo.plateNumber?? >value="${orderInfo.plateNumber}"</#if>/>
    司机姓名
    <input id="" name="driverName" <#if orderInfo.driverName?? >value="${orderInfo.driverName}"</#if>/>
    联系电话
    <input id="" name="contactName" <#if orderInfo.contactName?? >value="${orderInfo.contactName}"</#if>/>
    <h3>供应商信息</h3>
    名称
    <input id="" name="supportName" <#if orderInfo.supportName?? >value="${orderInfo.supportName}"</#if>/>
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
    <button type="button" id="screenOrderBtn">确定</button>
</form>

<br/></br/>

</body>
</html>