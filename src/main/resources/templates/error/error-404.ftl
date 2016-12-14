<title>404 Error Page</title>
<!-- ajax layout which only needs content area -->
<!-- PAGE CONTENT BEGINS -->
<!-- #section:pages/error -->
<style>
    h1{
        font-family: "微软雅黑";
        font-weight: 700;
        padding: 10px;
    }
    p{
        font-size:14px;
        margin:0;
    }
</style>
<div class="error-container">
    <div class="error-image" style="text-align: center;width:100%;">
        <img src="../img/404.gif">
        <div>
            <h1>抱歉，服务器找不到请求的网页</h1>
            <p>可能原因：网址有错误 > 请检查地址是否完整或存在多余字符</p>
            <p style="padding-left:55px">网址已失效 > 可能页面已删除，物流页面已下线</p>
        </div>
    </div>
    <div class="center" style="margin-top:20px">
        <a href="javascript:history.back()" class="btn btn-white btn-info btn-bold y-rt-btn">
            <i class="ace-icon fa fa-arrow-left"></i>
            返回
        </a>
        <a href="#" id="indexPage" class="btn btn-white btn-info btn-bold">
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
      $("#indexPage").click(function() {
        xescm.common.loadPage("/toIndexContent");
      });
    });
</script>
