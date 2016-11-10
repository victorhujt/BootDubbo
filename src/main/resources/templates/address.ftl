<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <meta http-equiv="x-ua-compatible" content="ie=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>：jQuery省市区三级联动插件city-picker</title>
    <base target="_blank" />
    <!--必要样式-->
    <link href="../css/city-picker.css" rel="stylesheet" type="text/css" /><#--111-->
  </head>
  <body>
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
      sys.rootPath = "http://localhost:7009";
  </script>
  <script src="../js/city-picker.data.js"></script><#--111-->
  <script src="../js/city-picker.js"></script><#--111-->
  </body>
</html>