<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no"
    >
    <title>平台日报规则</title>
</head>
<body>
<div id="app" class="abnormal" v-cloak>
    <img class='bg1' src='${(OFC_WEB_URL)!}/img/background-image-1.png'>
    <img class='bg2' src='${(OFC_WEB_URL)!}/img/background-image-2.png'>
    <img class='theme' src='${(OFC_WEB_URL)!}/img/theme.png'>
    <div class='limit'>
        <img class='transitionOne' src='${(OFC_WEB_URL)!}/img/transtion-1.png'>
        <img class='transitionTwo' src='${(OFC_WEB_URL)!}/img/transtion-2.png'>
    </div>
    <div class='city'>
        <img src="${(OFC_WEB_URL)!}/img/city-1.png">
        <img src="${(OFC_WEB_URL)!}/img/city-2.png">
    </div>
    <div class="warehouseCotent">
        <div class="rules">
            <h2>{{theme}}</h2>
            <div class="paragraph" v-for="item in paragraph">
                <p>{{item.name}}</p>
                <span v-for="items in item.content">{{items}}</span>
            </div>
        </div>
    </div>
</div>
</body>
<link rel="stylesheet" type="text/css" href="${(OFC_WEB_URL)!}/css/platformDaily.css">
<script type="text/javascript" src="${(OFC_WEB_URL)!}/js/vue.min.js"></script>
<#--<script src="https://unpkg.com/vue/dist/vue.js"></script>-->
<script type="text/javascript">
    document.documentElement.style.fontSize=document.documentElement.clientWidth/6.4+'px';
    //通过resize事件 监听当前窗口大小变化
    window.addEventListener("resize",function(){document.documentElement.style.fontSize=document.documentElement.clientWidth/6.4+'px';});
    new Vue({
        el:"#app",
        data(){
            return{
                theme:'平台日报规则',
                paragraph:[
                    {name: '事后补录订单',content :[
                        '该率值用于统计事后补录的订单在所有订单中的占比，率值越高表示事后补单的情况比较严重。鉴于正常运输业务不可能在2小时完成，所以2小时订单基本可以判定是属于事后补录订单的场景;',
                        '事后补录订单 =（2小时订单/开单合计）* 100%;',
                        '2小时订单：运输类订单在2小时之内操作完成从订单中心开单到订单签收的所有流程;',
                        '开单合计：排除已取消订单之后的运输类订单合计'
                    ]},
                    {name: '应收确认日清',content :[
                        '该率值用于统计客户应收账款当日及时收入确认的订单占比，率值越高表示收入确认的比较及时;',
                        '应收确认日清 = （收入确认的订单/发车订单合计）* 100%;',
                        '收入确认的订单：当天发车且收入确认的订单合计;',
                        '发车订单合计：当天发车的所有订单合计'
                    ]},
                    {name: '应付确认日清',content :[
                        '该率值用于统计承运商应付当日及时应付确认的发车数量占比，率值越高表示应付确认的比较及时;',
                        '应付确认日清 = （应付确认车数量/外部车辆发运数量）*100%;',
                        '应付确认车数量：在结算中心应付结费单中完成应付确认的调度单数量;',
                        '外部车辆发运数量：调度单是加盟车或外部车（非自有车）承运的调度单合计'
                    ]}
                ]
            }
        }
    })
</script>
</html>