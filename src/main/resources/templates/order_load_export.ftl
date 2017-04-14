<span hidden="true" id = "REPORT">${(REPORT)!}</span>
<script type="text/javascript">
    $(document).ready(main);
    function main(){
        $("#reportFrame").attr("src",$("#REPORT").html()+"/OfcReport/ReportServer?reportlet=zpLoadOrder.cpt");
    }
    var height=$(window).height()-208;
    var imageHeight=height/2-120;
    $("#reportFrame").height(height);
    window.addEventListener("resize",function(){
        height=$(window).height()-208;
        imageHeight=height/2-120;
        $("#reportFrame").height(height);
    });
</script>
<iframe id="reportFrame" allowtransparency="true" style="background-color:transparent" width="100%" src="">
    您的浏览器不支持嵌入式框架，或者当前配置为不显示嵌入式框架。
</iframe>