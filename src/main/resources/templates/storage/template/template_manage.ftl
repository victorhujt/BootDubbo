<title>导入模板配置</title>
<div id="dialog">
    <div class="list-mian-01">

    </div>
</div>
<span hidden="true" id = "ofc_web_url">${(OFC_WEB_URL)!}</span>
<div id="vm">
    <div class="list-mian-01">
        <div class="xe-pageHeader">
            模板筛选
        </div>
        <el-form :model="templateForm"  label-width="100px" class="demo-ruleForm">
            <div class="xe-block">
                <el-form-item label="客户名称"  class="xe-col-3">
                    <el-input v-model="templateForm.custName" id="custName" placeholder="请输入客户名称"></el-input>
                    <#--<el-input  id="custCode" ></el-input>-->
                </el-form-item>
                <el-form-item label="模板类型"  class="xe-col-3">
                    <el-select placeholder="请选择" v-model="templateForm.templateType">
                        <el-option  v-for="item in templatesTypeList" :label="item.label" :value="item.value"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="模板名称"  class="xe-col-3">
                    <el-input v-model="templateForm.templateName" placeholder="请输入配置名称"></el-input>
                </el-form-item >
                <el-form-item class="xe-col-3">
                    <el-button type="primary" v-on:click="templateSearchBtn" icon="search">筛选</el-button>
                </el-form-item>
            </div>
        </el-form>

        <div class="xe-pageHeader">
            模板列表
        </div>

        <template>
            <div class="xe-block">
                <el-button type="primary" v-on:click="templateAddBtn" >添加</el-button>
            </div>
            <el-table
                    :data="tableData"
                    highlight-current-row
                    <#--@current-change="handleSelectionChange"-->
                    border
                    style="width: 100%">
                <el-table-column
                        type="index"
                        label="序号">
                </el-table-column>
                <el-table-column
                        property="operat"
                        label="操作列">
                    <template scope="scope">
                        <el-button type="text" @click="templateEdit(scope.row.templateCode)"><p style="color: blue">编辑</p></el-button>
                        <el-button type="text" @click="templateDel(scope.row.templateCode)"><p style="color: blue">删除</p></el-button>
                    </template>
                </el-table-column>
                <el-table-column
                        property="templateName"
                        label="模板名称">
                    <template scope="scope">
                        <el-button type="text" @click="templateDetail(scope.row.templateCode)"><p style="color: blue">{{scope.row.templateName}}</p></el-button>
                    </template>
                </el-table-column>
                <el-table-column
                        property="templateType"
                        label="模板类型">
                </el-table-column>
                <el-table-column
                        property="custCode"
                        label="客户编码">
                </el-table-column>
                <el-table-column
                        property="custName"
                        label="客户名称">
                </el-table-column>
                <el-table-column
                        property="templateCode"
                        label="模板编码"
                        v-if="templateCodeShow">
                </el-table-column>
            </el-table>
            <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentPage" :current-page="currentPage" :page-sizes="pageSizes" :page-size="pageSize" layout="total, sizes, prev, pager, next, jumper" :total="total">
            </el-pagination>
        </template>
    </div>
</div>

<script type="text/javascript" >
    var scripts = [null,
        "/js/common/tools/trim.space.js?" + Date.parse(new Date()),
        "/components/select2.v3/select2.min.js",
        "/components/select2.v3/select2_locale_zh-CN.js",
        null];

    $(".page-content-area").ace_ajax("loadScripts", scripts, function () {
        $(document).ready(main);
    });

    function main() {
        initPageData()
    }


    function initPageData() {
        initCustomerName();
    }

    function initCustomerName() {
        var ofc_web_url = $("#ofc_web_url").html();
        var url = ofc_web_url + "/ofc/distributing/queryCustomerSelect2";
        var notice = "没有找到相关客户";
        //Select2Util.singleSelectInit("#custName",url,notice,"#custCode")
    }

    var dialog = new Vue({
        el:'#dialog',
        data:{}
    });
    var vm = new Vue({
        el:'#vm',
        data :function() {
            return{
                templateCodeShow:false,
                formLabelWidth:'100px',
                templateForm:{
                    custName:'',
                    templateType:'',
                    templateName:''
                },
                templatesTypeList:[
                    {value:'storageIn',label:'入库单'},
                    {value:'storageOut',label:'出库单'}
                ],
                currentPage:1,
                pageSizes:[10, 20, 30, 40,50],
                pageSize:10,
                total:0,
                tableData:[]
            }


        },
        methods:{
            templateSearchBtn:function (val) {
                var vm = this;
                vm.tableData = [];
                vm.total=0;
                var param = {};
                param.templateType = StringUtil.trim(this.templateForm.templateType);
                param.templateName = StringUtil.trim(this.templateForm.templateName);
                param.custName = StringUtil.trim(this.templateForm.custName);
                param.pageNum = vm.currentPage;
                param.pageSize = vm.pageSize;
                CommonClient.post("/ofc/storage_template/select", param, function (result) {
                    if (result == undefined || result == null || result.result == null ||  result.result.size == 0 || result.result.list == null) {
                        layer.msg("查询结果为空！");
                    } else if (result.code == 200) {// 1:normal
                        // 刷新页面数据
                        $.each(result.result.list, function (index, item) {
                            var rowData = {};
                            rowData.templateName = item.templateName;
                            rowData.templateType = item.templateType;
                            rowData.templateCode = item.templateCode;
                            rowData.custCode = item.custCode;
                            rowData.custName = item.custName;
                            vm.tableData.push(rowData);
                        })
                        vm.total=result.result.total;

                    } else if (result.code == 500) {
                        layer.msg(result.message);
                    } else if (result.code == 403){
                        layer.msg("没有权限")
                    } else {
                        layer.msg("操作失败")
                    }
                })
            },
            templateAddBtn:function (val) {
                var url = "/ofc/storage/template_add";
                xescm.common.loadPage(url)
            },
            handleSizeChange:function(val){
                this.pageSize=val;
//                this.selectOrder();
            },
            handleCurrentPage:function(val){
                this.currentPage=val;
//                this.selectOrder();
            },
            templateEdit:function(val){
                var vm = this;
                var orderTemplateCode = val;
                if(undefined == orderTemplateCode || StringUtil.isEmpty(orderTemplateCode)){
                    layer.msg("模板编码为空!");
                    return;
                }
                var url = "/ofc/storage_template/edit/" + orderTemplateCode;
                xescm.common.loadPage(url)
            },
            templateDel:function(val){
                var orderTemplateName = val;
                if(undefined == orderTemplateName || StringUtil.isEmpty(orderTemplateName)){
                    layer.msg("模板名称为空!");
                    return;
                }
                var param = {};
                param.templateName = orderTemplateName;
                xescm.common.submit("/ofc/storage_template/delete",param,"确认删除该模板?",function () {
                    vm.templateSearchBtn();
                })
            },
            templateDetail:function (val) {
                var vm = this;
                var orderTemplateCode = val;
                if(undefined == orderTemplateCode || StringUtil.isEmpty(orderTemplateCode)){
                    layer.msg("模板编码为空!");
                    return;
                }
                var url = "/ofc/storage_template/detail/" + orderTemplateCode;
                xescm.common.loadPage(url)

            }
        }
    })




</script>