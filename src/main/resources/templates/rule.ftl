<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no"
    >
    <title>平台日报规则</title>
</head>
<body>
<div id="app">
    <img class='bg1' src='${(OFC_WEB_URL)!}/img/background-image-1.png'>
    <img class='bg2' src='${(OFC_WEB_URL)!}/img/background-image-2.png'>
    <img class='theme' src='${(OFC_WEB_URL)!}/img/theme.png'>
    <div class='limit'>
        <img class='transitionOne' src='${(OFC_WEB_URL)!}/img/transtion-1.png'>
        <img class='transitionTwo' src='${(OFC_WEB_URL)!}/img/transtion-2.png'>
    <#--<div class='transitionOne' style="background:url('${(OFC_WEB_URL)!}/img/transtion-1.png')"></div>-->
    <#--<div class='transitionTwo' style="background:url('${(OFC_WEB_URL)!}/img/transtion-2.png')"></div>-->
    </div>
    <div class='city'>
        <img src="${(OFC_WEB_URL)!}/img/city-1.png">
        <img src="${(OFC_WEB_URL)!}/img/city-2.png">
    </div>
    <div class="warehouseCotent">
        <div class="warehouses rules">
            <h2>{{theme}}</h2>
            <span class="paragraph" v-for="(content,index) in paragraph">{{content.content}}</span>
        </div>
    </div>
</div>
</body>
<link rel="stylesheet" type="text/css" href="${(OFC_WEB_URL)!}/css/platformDaily.css">
<script src="https://unpkg.com/vue/dist/vue.js"></script>
<script type="text/javascript">
    document.documentElement.style.fontSize=document.documentElement.clientWidth/6.4+'px';
    //通过resize事件 监听当前窗口大小变化
    window.addEventListener("resize",function(){document.documentElement.style.fontSize=document.documentElement.clientWidth/6.4+'px';});
    var me=new Vue({
        el:"#app",
        data(){
            return{
                theme:'平台日报规则',
                paragraph:[
                    {content:'鲜易供应链（www.xianyiscm.com）是鲜易供应链有限公司旗下的提供仓运配&金融一体化服务网络平台。“鲜易供应链”是面向全国的温控供应链交易平台，为客户提供仓储、运输、城配等服务，另外平台同国内外多家金融机构合作，为客户提供保理融资、代采融资、存货质押融资、仓单质押融资等服务。'},
                    {content:'“鲜易供应链”致力于以电子商务和网络公共平台为依托，整合国内外温控供应链行业资源，推进“云仓网、运输网、共配网、信息网”的深度融合，打造统一、安全、高效、协同的国内最有影响力的一站式温控供应链服务平台。目前我国冷链信息化水平低，技术落后。由'}
                ]
            }
        },
        methods:{

        }
    })
</script>
</html>