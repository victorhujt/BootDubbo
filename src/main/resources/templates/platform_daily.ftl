<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <title>平台使用情况日报</title>
</head>
<body>
<div id="app">
    <nav>
        <span><</span>
        <span>平台使用情况日报<span>
    </nav>
    <img class='bg1' src='img/background-image-1.png'>
    <img class='bg2' src='img/background-image-2.png'>
    <img class='theme' src='img/theme.png'>
    <img class='transitionOne' src='img/transtion-1.png'>
    <img class='transitionTwo' src='img/transtion-2.png'>
    <div class='city'>
        <img src="img/city-1.png">
        <img src="img/city-2.png">
    </div>
    <div class="warehouseCotent">
        <div v-for="(content,index) in even(jsonData)" class="warehouses height">
            <p>
                <span class="warehouseName">{{content.baseName==null?"":content.baseName}}</span>
                <span class="custom">
							<span class="one borderColor"></span>
							<span class="two">{{achievement(content)}}</span>
							<span class="three borderColor"></span>
						</span>
                <span class="ranking">目前排名为第 <i>{{index+1}}</i> 位</span>
            </p>
            <div class="externals">
                <span class="external"><p>{{content.additionalOrderPercent==null?"0%":content.additionalOrderPercent}}</p>事后补录订单</span>
                <span class="external"><p>{{content.receivablePercent==null?"0%":content.receivablePercent}}</p>应收确认日清</span>
                <span class="external"><p>{{content.payablePercent==null?"0%":content.payablePercent}}</p>应付确认日清</span>
            </div>
        </div>
    </div>
</div>
</body>
<link rel="stylesheet" type="text/css" href="css/platformDaily.css">
<script type="text/javascript">
    document.documentElement.style.fontSize=document.documentElement.clientWidth/6.4+'px';
    //通过resize事件 监听当前窗口大小变化
    window.addEventListener("resize",function(){document.documentElement.style.fontSize=document.documentElement.clientWidth/6.4+'px';});
    var me=new Vue({
        el:'#app',
        data() {
            return {
                jsonData:[]
            }
        },
        mounted(){
            let that = this;
            debugger;
            CommonClient.post(sys.rootPath+"/ofc/queryDailyAccount",{},function(data){
                that.jsonData =data.result;
            },"json");
        },
        methods:{
            even(jsonData){
                return jsonData.filter(function (base) {
                   // console.log(base.baseCode);
                    return base.baseCode != ""
                })
            },
            achievement(){

            }
        }
    })
</script>
