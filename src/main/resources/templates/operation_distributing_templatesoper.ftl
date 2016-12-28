<style type="text/css">
    td{
        text-align: center;
    }
</style>
<div class="col-sm-12">
    <form class="form-horizontal" role="form">
        <div class="form-group">
            <label class="control-label col-label no-padding-right" for="name">模板映射配置</label>
        </div>
        <div class="form-group">
            <label class="control-label col-label no-padding-right padding"  for="name">类别</label>
            <div class="col-xs-3">
                <select class="col-xs-12 chosen-select" id="templatesType"><#--class="col-xs-12 chosen-select"-->
                    <option value="">城配订单</option>
                </select>
            </div>
        </div>
        <div class="form-group">
            <label class="control-label col-label no-padding-right padding"  for="name">模板类型</label>
            <div class="col-xs-3">
                <div class="radio y-float">
                    <label style="width:56px;padding-left:10px;">
                        <input id="templatesTypeAcross" value="MODEL_TYPE_ACROSS" name="templatesType" checked="checked" type="radio" class="ace"/>
                        <span class="lbl">交叉</span>
                    </label>
                </div>
                <div class="radio y-float">
                    <label>
                        <input id="templatesTypeBoradwise" value="MODEL_TYPE_BORADWISE" name="templatesType" type="radio" class="ace"/>
                        <span class="lbl">明细列表</span>
                    </label>
                </div>
            </div>
        </div>
        <div class="form-group">
            <label class="control-label col-label no-padding-right padding"  for="name">配置名称</label>
            <div class="col-xs-3">
                <input class="col-xs-12 form-control input-sm " aria-controls="dynamic-table" style="height: 34px"/>
            </div>
        </div>
        <div class="form-group">
            <#--<label class="control-label col-label no-padding-right padding"  for="name">发货方起始位置</label>-->
                <label class="control-label col-label no-padding-right padding" for="name" style="width: 84px;margin-left: 9px;">发货方起始位置</label>
            <div class="col-xs-3">
                <input class="col-xs-3 form-control input-sm " style="height: 34px" aria-controls="dynamic-table"/><span>*确认模版中第一个发货方称的位置处于第几列</span>
            </div>
        </div>
        <div class="form-group" style="margin-top:20px;">
            <div class="col-xs-6">
                <table id="dynamic-table" class="table table-striped table-bordered table-hover dataTable no-footer" role="grid" aria-describedby="dynamic-table_info">
                    <thead>
                    <tr role="row">
                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">序号</th>
                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">标准列名</th>
                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">映射列名</th>
                    </tr>
                    </thead>
                    <tbody id="templatesConfigTbody">
                    <tr>
                        <td>1</td>
                        <td>货品编码</td>
                        <td><input /></td>
                    </tr>
                    <tr>
                        <td>2</td>
                        <td>货品名称</td>
                        <td><input/></td>
                    </tr>
                    </tbody>
                </table>
                <input id="historyUrl" value="${historyUrl!""}" hidden/>
                <input id="customerCode" value="${customerCode!""}" hidden/>
                <input id="custName" value="${custName!""}" hidden/>
            </div>
        </div>
        <div class="form-group">
            <div class="col-xs-12" style="float: right">
                <button id="templatesOperConfirm" data-bb-handler="confirm" type="button" class="btn btn-primary">保存</button>
                <span id="templatesOperNone" style="cursor:pointer"><button id=""  data-bb-handler="cancel" type="button" class="btn btn-default">不保存</button></span>
            </div>
        </div>
    </form>
</div>
<script type="text/javascript">

    $("#templatesOperNone").click(function () {
        var custCode = $("#customerCode").val();
        var custName = $("#custName").val();
        var historyUrl = $("#historyUrl").val();
        var url = '';
        if(!StringUtil.isEmpty(historyUrl) && 'operation_distributing' == historyUrl){
            url = "/ofc/distributing/toTemplatesList/" + custCode + "/" + historyUrl;
            xescm.common.loadPage(url);
        }
    })
    $("#templatesAdd").click(function () {
        xescm.common.loadPage("/ofc/distributing/toTemplatesoper");
    })
    function editTemplates(obj){
        xescm.common.loadPage("/ofc/distributing/toTemplatesoper");
    }
    function delTemplates(obj) {
        xescm.common.loadPage("/ofc/distributing/toTemplatesoper");
    }

</script>