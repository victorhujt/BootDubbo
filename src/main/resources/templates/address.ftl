<#macro greet fromUrl>
<#--111-->
<!-- Content -->
<div class="container">
    <h2 class="page-header">演示</h2>
    <div class="docs-methods">
        <form class="form-inline">
            <div id="distpicker">
                <div class="form-group">
                    <div style="position: relative;">
                        <input id="city-picker3" class="form-control" readonly type="text" value="" data-toggle="city-picker">
                    </div>
                </div>
                <div class="form-group">
                    <button class="btn btn-warning" id="reset" type="button">重置</button>
                    <button class="btn btn-danger" id="destroy" type="button">确定</button>
                </div>
            </div>
        </form>
    </div>
</div>
<script type="text/javascript">
    var sys = sys || {};
    sys.rootPath = ${fromUrl};
</script>
<script src="js/city-picker.data.js"></script><#--111-->
<script src="js/city-picker.js"></script><#--111-->
</body>
</#macro>
