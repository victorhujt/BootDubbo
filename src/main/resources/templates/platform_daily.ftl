<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <title>平台使用情况日报</title>
</head>
<body>
<div id="app" :class="normal">
    <img class='bg1' src='${(OFC_WEB_URL)!}/img/background-image-1.png'>
    <img class='bg2' src='${(OFC_WEB_URL)!}/img/background-image-2.png'>
    <img class='theme' src='${(OFC_WEB_URL)!}/img/theme.png'>
    <div class='limit'></div>
    <div class='city'>
        <img src="${(OFC_WEB_URL)!}/img/city-1.png">
        <img src="${(OFC_WEB_URL)!}/img/city-2.png">
    </div>
    <div class="warehouseCotent">
        <div v-for="(index,content) in jsonData" class="warehouses height">
            <p>
                <span class="warehouseName">{{content.baseName==null?"未命名":content.baseName}}</span>
                <span class="custom">
							<span class="one borderColor"></span>
							<span class="three borderColor"></span>
						</span>
                <span class="ranking">目前排名为第 <i>{{index+1}}</i> 位</span>
            </p>
            <div class="externals">
                <span class="external"><p>{{content.additionalOrderPercent==null?"0%":content.additionalOrderPercent}}</p><p>事后补录订单</p></span>
                <span class="external"><p>{{content.receivablePercent==null?"0%":content.receivablePercent}}</p><p>应收确认日清</p></span>
                <span class="external"><p>{{content.payablePercent==null?"0%":content.payablePercent}}</p><p>应付确认日清</p></span>
            </div>
        </div>
        <div class="warehouses littleHeight" v-if="jsonMessage != null">
            {{jsonMessage}}
        </div>
    </div>
</div>
</body>
<link rel="stylesheet" type="text/css" href="${(OFC_WEB_URL)!}/css/platformDaily.css">
<script type="text/javascript" src="${(OFC_WEB_URL)!}/js/vue.js"></script>
<script type="text/javascript" src="${(OFC_WEB_URL)!}/js/common/common-client.js"></script>
<script type="text/javascript" src="${(OFC_WEB_URL)!}/js/jquery.js"></script>
<script type="text/javascript" src="${(OFC_WEB_URL)!}/js/common/jquery.blockUI.js"></script>
<script type="text/javascript" src="${(OFC_WEB_URL)!}/plugins/layui/layer/layer.js"></script>


<script type="text/javascript">
    document.documentElement.style.fontSize=document.documentElement.clientWidth/6.4+'px';
    //通过resize事件 监听当前窗口大小变化
    window.addEventListener("resize",function(){document.documentElement.style.fontSize=document.documentElement.clientWidth/6.4+'px';});
    new Vue({
        el:'#app',
        data:function() {
            return {
                jsonData:[],
                ofcUrl:'${ofcUrl!}',
                jsonMessage:null,
                normal:""
            }
        },
        created:function(){
            var _this = this;
            CommonClient.post(_this.ofcUrl+"/ofc/queryDailyAccount",{},function(data){
                var number = 0;
                if(data.result.length>0){
                    for(var i=0;i<data.result.length;i++){
                        if(data.result[i].baseCode!=""){
                            _this.jsonData.push(data.result[i]);
                            number++;
                        }
                    }
                    if(number == 0){
                        _this.jsonMessage = "今日的数据源还未生成";
                        _this.normal="abnormal";
                    }
                }else{
                    _this.jsonMessage = "今日的数据源还未生成";
                    _this.normal="abnormal";
                }
            },"json");
        }
    })
</script>
