<style>
    .page-content{margin:10px 15px;}
    h1{font-family: "微软雅黑";font-weight: 700;padding: 0px;margin-top: 0px;margin-bottom: 15px;}
    .msg{font-family: "微软雅黑";font-size: 20px;color:#fff;height:35px;line-height: 35px;margin:0;}
    .bg{position:absolute;width:100%;height:100px;background:#000;top:180px;left:0px;padding-top:15px;opacity:0.7;}
</style>
<title>错误 页面</title>
<!-- ajax layout which only needs content area -->
<!-- PAGE CONTENT BEGINS -->
<!-- #section:pages/error -->
<div class="error-container">
    <div class="error-image" style="text-align: center;width:100%;">
        <img src="../img/500.gif">
        <div class="bg"></div>
        <div class="bg" style="background:transparent;opacity: 1;">
            <p class="msg">编码: ${code!}</p>
            <p class="msg">异常: ${message!}</p>
        </div>
        <h1>服务器遇到错误，无法完成请求</h1>
    </div>
    <div class="center" style="padding-top:20px;">
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
<!-- /section:pages/error -->
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
