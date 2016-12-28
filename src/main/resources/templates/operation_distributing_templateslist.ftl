<style type="text/css">
    td{
        text-align: center;
    }
</style>
<div class="col-sm-12">
    <form class="form-horizontal" role="form">
        <div class="form-group">
            <label class="control-label col-label no-padding-right" for="name">模板配置列表</label>
        </div>
        <div class="form-group" style="line-height: 34px">
            <label class="control-label col-label no-padding-right padding"  for="name">
                <input type="button" id="templatesAdd" name="templatesAdd" style="display:none;" />
                <label for="templatesAdd" class="btn btn-white btn-info btn-bold " style="padding-left: 8px">添加配置</label>
            </label>
            <div class="col-xs-9" s>
                <input type="button" id="templatesAddBack" name="templatesAddBack" style="display:none;" />
                <label for="templatesAddBack" class="btn btn-white btn-info btn-bold " >返回</label>
            </div>
        </div>
        <div class="form-group" style="margin-top:20px;">
            <div class="col-xs-12">
                <table id="dynamic-table" class="table table-striped table-bordered table-hover dataTable no-footer" role="grid" aria-describedby="dynamic-table_info">
                    <thead>
                    <tr role="row">
                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">序号</th>
                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">操作</th>
                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">类别</th>
                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">模板类型</th>
                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">配置名称</th>
                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">创建人</th>
                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">创建时间</th>
                    </tr>
                    </thead>
                    <tbody id="templatesConfigTbody">
                        <tr>
                            <td>1</td>
                            <td style=""><a onclick="editTemplates(this)" class="blue">&nbsp;编辑&nbsp;|</a><a onclick="delTemplates(this)" class="blue">&nbsp;删除&nbsp;</a></td>
                            <td>城配订单</td>
                            <td>交叉</td>
                            <td><a>呷哺呷哺导入模板</a></td>
                            <td>超级管理员</td>
                            <td>2016-12-23 23:12:22</td>
                        </tr>
                    </tbody>
                </table>
                <input id="customerCode" value="${customerCode!""}" hidden/>
                <input id="custName" value="${custName!""}" hidden/>
                <input id="historyUrl" value="${historyUrl!""}" hidden/>
            </div>
        </div>
    </form>
</div>
<script type="text/javascript">
    var customerCode = $("#customerCode").val();
    var custName = $("#custName").val();
    var historyUrl = $("#historyUrl").val();
    $("#templatesAddBack").click(function () {
        var url = "/ofc/operationDistributingExcel/" + historyUrl +"/" + customerCode;
        xescm.common.loadPage(url);
    })

    $("#templatesAdd").click(function () {
        var tag = "add";
        xescm.common.loadPage("/ofc/distributing/toTemplatesoper/" + customerCode +"/" + historyUrl + "/" + tag);
    })
    function editTemplates(obj){
        var tag = "edit";
        xescm.common.loadPage("/ofc/distributing/toTemplatesoper/" + customerCode +"/" + historyUrl + "/" + tag);
    }
    function delTemplates(obj) {
        var tag = "del";
        xescm.common.loadPage("/ofc/distributing/toTemplatesoper/" + customerCode +"/" + historyUrl + "/" + tag);
    }

</script>