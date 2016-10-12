<html>
<head>

</head>
<body>
<h2>订单信息</h2>

订单日期

订单编号

客户订单编号

订单状态

订单类型

业务类型

店铺

平台类型

服务产品

订单来源

备注

创建日期

创建人员

完成日期

取消日期

取消人

<hr style="height: 10px">

<h2>配送信息</h2>

货品类型

是否加急

出发地

目的地

数量


重量

体积


运输要求


取货时间


运输单号

车牌号

期望送达时间


司机姓名


联系电话
<hr style="height: 10px">
<h2>发货方信息</h2>

名称

联系人

众品有限公司

0355-56379811

Email

mayanlong@hnxianyi.com
u487_startu487_endu487_line
联系电话

15668159000

王经理

传真

地址

河南省郑州市经济开发区田江路110号

邮编

100100
<hr style="height: 10px">
<h2>收货方信息</h2>

名称

联系人

大润发超市 (望京店)

0355-56379811

Email

mayanlong@hnxianyi.com
u518_startu518_endu518_line
联系电话

15668159000

王经理

传真

地址

河南省郑州市经济开发区田江路110号

<hr style="height: 10px">

<h2>货品信息</h2>
<table>
    <tr>
        <td>序号</td>
        <td>货品编码</td>
        <td>货品名称</td>
        <td>货品规格</td>
        <td>单位</td>
        <td>数量</td>
    </tr>
    <tr>
        <td>1</td>
        <td>001</td>
        <td>肉</td>
        <td>规格</td>
        <td>g</td>
        <td>100</td>
    </tr>
    <tr>
        <td>2</td>
        <td>002</td>
        <td>奶</td>
        <td>规格</td>
        <td>g</td>
        <td>100</td>
    </tr>
</table>

<hr style="height: 10px">
订单跟踪信息
    <table class="table table-hover" width="80%" align="center">
        <!-- On rows -->
        <tr class="active" style="height: 15px">
            <td style="width: 50%;">订单状态</td>

        </tr>
    <#--
    vo  value object  拼出来的结果集
    dto  参数,给后端当参数,或者返回的dot.
    -->
    <#list orderStatusList! as orderStatus>
        <tr class="active">

            <td style="width: 10%;">${orderStatus.statusDesc!"null"}</td>
        </tr>
    </#list>
</table>
</body>
</html>