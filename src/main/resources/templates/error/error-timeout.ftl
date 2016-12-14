<title>timeout Error Page</title>
<!-- ajax layout which only needs content area -->
<!-- PAGE CONTENT BEGINS -->
<!-- #section:pages/error -->
<style>
    .page-content{
        background:#ca9ba7;
        margin:10px 15px;
    }
    h1{
        font-family: "微软雅黑";
        font-weight: 700;
        padding: 10px;
    }
    p{
        font-size:14px;
        background:#ca9ba7;
        margin:0;
    }
    .btn{
        height:34px;
        padding:0px 12px;
        line-height: 25px;
    }
</style>
<div class="error-container">
    <div class="error-image" style="text-align: center;width:100%;background:#ca9ba7;">
        <img src="../img/timeout.gif">
        <div>
            <h1>请求超时...</h1>
            <p>重新 <a href="">请求</a> 一下吧！</p>
        </div>
    </div>
    <div class="center" style="padding-top:20px;background:#ca9ba7;">
        <a href="javascript:history.back()" class="btn btn-inverse y-rt-btn">
            <i class="ace-icon fa fa-arrow-left"></i>
            返回
        </a>
        <a href="#" class="btn btn-inverse">
            <i class="ace-icon fa fa-tachometer"></i>
            控制台
        </a>
    </div>
</div>
<!-- page specific plugin scripts -->
<script type="text/javascript">
    var scripts = [null, null]
    $('.page-content-area').ace_ajax('loadScripts', scripts, function () {
        //inline scripts related to this page
    });
    var height=$(window).height()-208;
    $(".page-content").height(height);
    window.addEventListener("resize",function(){
        var height=$(window).height()-208;
        $(".page-content").height(height);
    });
</script>
