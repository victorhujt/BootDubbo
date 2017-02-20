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
                    <#--:data="tableData"-->
                    highlight-current-row
                    <#--@current-change="handleCurrentChange"-->
                    border
                    style="width: 100%">
                <el-table-column
                        type="index"
                        label="序号">
                </el-table-column>
                <el-table-column
                        property="value"
                        label="操作列">
                </el-table-column>
                <el-table-column
                        property="address"
                        label="模板名称">
                </el-table-column>
                <el-table-column
                        property="address"
                        label="模板类型">
                </el-table-column>
                <el-table-column
                        property="address"
                        label="客户编码">
                </el-table-column>
                <el-table-column
                        property="address"
                        label="客户名称">
                </el-table-column>
            </el-table>
        </template>
        <#--<el-pagination-->
                <#--@size-change="handleSizeChange"-->
                <#--@current-change="handleCurrentChange"-->
                <#--:current-page="currentPage4"-->
                <#--:page-sizes="[100, 200, 300, 400]"-->
                <#--:page-size="100"-->
                <#--layout="total, sizes, prev, pager, next, jumper"-->
                <#--:total="400">-->
        <#--</el-pagination>-->
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
    })
    var vm = new Vue({
        el:'#vm',
        data :function() {
            return{
                formLabelWidth:'100px',
                templateForm:{
                    custName:'',
                    templateType:'',
                    templateName:''
                },
                templatesTypeList:[
                    {value:'storageIn',label:'入库单'},
                    {value:'storageOut',label:'出库单'}
                ]
            }

        },
        methods:{
            templateSearchBtn:function (val) {

            },
            templateAddBtn:function (val) {
                var url = "/ofc/storage/template_add";
                xescm.common.loadPage(url)
            }
        }
    })



</script>