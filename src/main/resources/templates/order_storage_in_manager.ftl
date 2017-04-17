<title>入库开单</title>
<!--suppress ALL -->
<head>
    <style lang="css">
        .ofc-block {
            margin: 20px 0;
        }
        .el-dialog{
            top:50%!important;
            margin-top:-300px;
            margin-bottom:0!important;
        }
        .el-dialog__body{
            padding:10px 20px 30px;
        }
        .el-dialog__footer{
            padding:15px 20px;
        }
        .el-dialog--small .el-table{
            min-height:350px;
        }
        .el-dialog--small .el-table tr{
            cursor:pointer;
        }
    </style>
</head>
<body>
<div id="app">
    <div class="list-mian-01">
        <el-dialog title="货品实收详情" v-model="chosenRealGood" size="small">
            <el-table :data="realGoodsData" border style="width: 100%">
                <el-table-column property="goodsCode" label="货品编码">
                    <template scope="scope">
                        <el-input v-model="scope.row.goodsCode" :readOnly="true"></el-input>
                    </template>
                </el-table-column>
                <el-table-column property="goodsName" label="货品名称">
                    <template scope="scope">
                        <el-input v-model="scope.row.goodsName" :readOnly="true"></el-input>
                    </template>
                </el-table-column>
                <el-table-column property="goodsSpec" label="规格">
                    <template scope="scope">
                        <el-input v-model="scope.row.goodsSpec" :readOnly="true"></el-input>
                    </template>
                </el-table-column>
                <el-table-column property="unit" label="单位">
                    <template scope="scope">
                        <el-input v-model="scope.row.unit" :readOnly="true"></el-input>
                    </template>
                </el-table-column>
                <el-table-column property="quantity" label="实收数量">
                    <template scope="scope">
                        <el-input v-model="scope.row.realQuantity" :readOnly="true"></el-input>
                    </template>
                </el-table-column>
                <el-table-column property="unitPrice" label="单价">
                    <template scope="scope">
                        <el-input v-model="scope.row.unitPrice" :readOnly="true"></el-input>
                    </template>
                </el-table-column>
                <el-table-column property="productionBatch" label="批次号">
                    <template scope="scope">
                        <el-input v-model="scope.row.productionBatch"  :readOnly="true"></el-input>
                    </template>
                </el-table-column>
                <el-table-column property="productionTime" label="生产日期">
                    <template scope="scope">
                        <el-date-picker
                                v-model="scope.row.productionTime"
                                align="right"
                                type="date"
                                :readOnly="true"
                                :picker-options="pickerOptions1">
                        </el-date-picker>
                    </template>
                </el-table-column>
                <el-table-column property="invalidTime" label="失效日期">
                    <template scope="scope">
                        <el-date-picker
                                v-model="scope.row.invalidTime"
                                align="right"
                                type="date"
                                :readOnly="true"
                                :picker-options="pickerOptions1">
                        </el-date-picker>
                    </template>
                </el-table-column>
            </el-table>
        </el-dialog>

        <el-dialog title="选择客户" v-model="chosenCus" size="small">
            <el-form :model="chosenCusForm">
                <el-form-item label="名称" :label-width="formLabelWidth">
                    <el-input v-model="chosenCusForm.name" auto-complete="off"></el-input>
                </el-form-item>
                <el-form-item label="" :label-width="formLabelWidth20">
                    <el-button type="primary" @click="selectCustomer">查询</el-button>
                </el-form-item>
            </el-form>
            <el-table :data="customerData" border highlight-current-row @current-change="handleCustomerCurrentChange"
                      @row-dblclick="setCurrentCustInfo(currentCustomerRow)" style="width: 100%" max-height="400">
                <el-table-column type="index" label="序号"></el-table-column>
                <el-table-column property="customerCode" label="客户编码"></el-table-column>
                <el-table-column property="type" label="类型"></el-table-column>
                <el-table-column property="customerName" label="公司名称"></el-table-column>
                <el-table-column property="channel" label="渠道"></el-table-column>
                <el-table-column property="productType" label="产品类别"></el-table-column>
            </el-table>
            <el-pagination @size-change="handleCustomerSizeChange" @current-change="handleCustomerCurrentPage" :current-page="customerCurrentPage" :page-sizes="pageSizes" :page-size="customerPageSize" :total="totalCustomer" layout="total, sizes, prev, pager, next, jumper">
            </el-pagination>
            <div slot="footer" class="dialog-footer">
                <el-button @click="cancelSelectCustomer">取 消</el-button>
                <el-button type="primary" @click="setCurrentCustInfo(currentCustomerRow)">确 定</el-button>
            </div>
        </el-dialog>
        <div class="xe-pageHeader">
            入库单筛选
        </div>
        <el-form label-width="100px">
            <div class="xe-block">
                <el-form-item label="订单日期"  class="xe-col-3">
                    <el-date-picker
                            style="width:114px;"
                            v-model="beginDate"
                            type="date"
                            :clearable="false"
                            :editable="false"
                            placeholder="选择起始日期">
                    </el-date-picker>
                    <label for="" style="width:15px;">至</label>
                    <el-date-picker
                            style="width:114px;"
                            v-model="endDate"
                            type="date"
                            :clearable="false"
                            :editable="false"
                            placeholder="选择结束日期">
                    </el-date-picker>
                </el-form-item>
                <el-form-item label="订单编号" class="xe-col-3">
                    <el-input v-model="orderCode" placeholder="请输入内容"></el-input>
                </el-form-item>
                <el-form-item label="客户订单编号" class="xe-col-3">
                    <el-input v-model="customerOrderCode" placeholder="请输入内容"></el-input>
                </el-form-item>
            </div>
            <div class="xe-block">
                <el-form-item label="客户名称" class="xe-col-3">
                    <el-input
                            placeholder="请选择"
                            icon="search"
                            v-model="customerName"
                            :readOnly="true"
                            @click="openCustomer">
                    </el-input>
                </el-form-item>
                <el-form-item label="仓库名称" class="xe-col-3">
                    <el-select v-model="wareHouseName" placeholder="请选择">
                        <el-option
                                v-for="item in wareHouseOptions"
                                :label="item.label"
                                :value="item.value">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="业务类型" class="xe-col-3">
                    <el-select v-model="businessType" placeholder="请选择">
                        <el-option
                                v-for="item in businessTypeOptions"
                                :label="item.label"
                                :value="item.value">
                        </el-option>
                    </el-select>
                </el-form-item>
            </div>
            <div class="xe-block">
                <el-form-item label="大区名称" class="xe-col-3">
                    <el-select v-model="areaName" placeholder="请选择" @change="getBaseNameByArea">
                        <el-option
                                v-for="item in areaNameOptions"
                                :label="item.label"
                                :value="item.value">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="基地名称" class="xe-col-3">
                    <el-select v-model="baseName" placeholder="请选择">
                        <el-option
                                v-for="item in baseNameOptions"
                                :label="item.label"
                                :value="item.value">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="订单状态" class="xe-col-3">
                    <el-select v-model="orderStatus" placeholder="请选择">
                        <el-option
                                v-for="item in orderStatusOptions"
                                :label="item.label"
                                :value="item.value">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="是否提供运输"  class="xe-col-3">
                    <#--<el-checkbox v-model="isNeedTransport"  @click="isNeedTransport = true"></el-checkbox>-->
                        <el-select v-model="provideTransport" placeholder="请选择">
                            <el-option
                                    v-for="item in needTranSportOptions"
                                    :label="item.label"
                                    :value="item.value">
                            </el-option>
                        </el-select>
                </el-form-item>
            </div>
            <div class="xe-block">
                <el-form-item label="" class="xe-col-3">
                    <el-button type="primary" @click="selectOrder">筛选</el-button>
                    <el-button @click="resetCondition">重置</el-button>
                </el-form-item>
            </div>

        </el-form>
        <div class="xe-pageHeader">
            入库单列表
        </div>
        <div style="margin-top:20px;">
            <el-button size="small" @click="addOrder">添加</el-button>
            <el-button size="small" @click="editOrder">编辑</el-button>
            <el-button size="small" @click="deleteOrder">删除</el-button>
            <el-button size="small" @click="copyOrder" v-bind:disabled = "isDisabledCopy">复制</el-button>
            <el-button size="small" @click="auditOrder" v-bind:disabled = "isDisabledAudit">审核</el-button>
            <el-button size="small" @click="repeatAuditOrder" v-bind:disabled = "isDisabledRepeatAudit">反审核</el-button>
            <el-button size="small" @click="cancelOrder"  v-bind:disabled = "isDisabledCancel">取消</el-button>
            <el-button size="small" @click="realGood">实收详情</el-button>
            <el-button size="small" @click="batchImport">批量导入</el-button>
        </div>
        <div class="ofc-block">
            <el-table :data="orderData" border @selection-change="handleSelectionChange" style="width: 100%">
                <el-table-column type="index" label="序号"></el-table-column>
                <el-table-column type="selection">
                </el-table-column>
                <el-table-column property="customerName" label="客户名称"></el-table-column>
                <el-table-column
                        property="orderCode"
                        label="订单编号">
                    <template scope="scope">
                        <span @click="orderDetails(scope.row.orderCode)" class="xe-link">{{scope.row.orderCode}}</span>
                    </template>
                </el-table-column>
                <el-table-column property="orderBatchNumber" label="订单批次号"></el-table-column>
                <el-table-column property="customerOrderCode" label="客户订单号"></el-table-column>
                <el-table-column property="orderDate" label="订单日期"></el-table-column>
                <el-table-column property="businessName" label="业务类型"></el-table-column>
                <el-table-column property="orderStatusName" label="订单状态"></el-table-column>
                <el-table-column property="wareHouseName" label="仓库名称"></el-table-column>
                <el-table-column property="baseName" label="基地名称"></el-table-column>
            </el-table>
            <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentPage" :current-page="orderCurrentPage" :page-sizes="pageSizes" :page-size="pageSize" layout="total, sizes, prev, pager, next, jumper" :total="totalOrder">
            </el-pagination>
        </div>
    </div>
</div>
</body>
<script>
    new Vue({
        el: '#app',
        data:{
            beginDate:new Date().getTime() - 3600 * 1000 * 24 * 2,
            endDate:new Date(),
            chosenCus:false,
            isDisabledCancel:false,
            isDisabledCopy:false,
            isDisabledAudit:false,
            isDisabledRepeatAudit:false,
            provideTransport:'',
            chosenRealGood:false,
            realGoodsData:[],
            needTranSportOptions:[
                {label:"全部",value:''} ,
                {label:"是",value:'1'},
                {label:"否",value:'0'}
            ],
            currentRow:'',
            currentCustomerRow:'',
            orderCurrentPage:1,
            customerCurrentPage:1,
            formLabelWidth: '100px',
            formLabelWidth20: '20px',
            pageSize:10,
            totalCustomer:0,
            totalOrder:0,
            wareHouseOptions:[],
            pageSizes:[10, 20, 30, 40,50],
            customerData:[],
            customerPageSize:10,
            wareHouseName:'',
            businessType:'',
            areaName:'',
            baseName:'',
            orderStatus:'',
            orderCode:'',
            customerName:'',
            customerCode:'',
            customerOrderCode:'',
            chosenCusForm: {
                name: ''
            },
            businessTypeOptions: [
                {
            value: '620',
            label: '采购入库'
            }, {
            value: '621',
            label: '调拨入库'
            }, {
            value: '622',
            label: '退货入库'
            }, {
            value: '623',
            label: '加工入库'
         }, {
            value: '624',
            label: '盘盈入库'
            }, {
            value: '625',
            label: '流通入库'
            }, {
            value: '626',
            label: '其他入库'
            }],
            areaNameOptions:[],
            multipleSelection: [],
            baseNameOptions:[],
            orderStatusOptions:[{
                value: '10',
                label: '待审核'
            },{
                value: '20',
                label: '已审核'
            },{
                value: '30',
                label: '执行中'
            },{
                value: '40',
                label: '已完成'
            },{
                value: '50',
                label: '已取消'
            }],
            orderData:[]
        },
        beforeMount:function(){
            var vueObj=this;
            vueObj.wareHouseOptions=[];
            vueObj.areaNameOptions=[];
            vueObj.baseNameOptions=[];
            CommonClient.post(sys.rootPath + "/ofc/loadWarehouseByUser",{},function (result) {
                if (result == undefined || result == null) {
                    layer.msg("当前用户下没有仓库信息！");
                } else if (result.code == 200) {
                    if(result.result.length>0){
                        $.each(result.result,function (index,RmcWarehouseRespDto) {
                            var warehouse={};
                            warehouse.label=RmcWarehouseRespDto.warehouseName;
                            warehouse.value= RmcWarehouseRespDto.warehouseCode;
                            vueObj.wareHouseOptions.push(warehouse);
                        });
                        if(vueObj.wareHouseOptions.length==1){
                            vueObj.wareHouseName = vueObj.wareHouseOptions[0].value;
                        }
                    }else{
                        layer.msg("当前用户下没有仓库信息！");
                    }
                }
            });
            //大区和基地信息
            CommonClient.syncpost(sys.rootPath + "/ofc/loadAreaAndBaseByUser",{},function (result) {
                if (result == undefined || result == null) {
                    layer.msg("当前用户下没有大区和基地信息！");
                } else if (result.code == 200) {
                    var areaArray=result.result.area;
                    var baseArray=result.result.base;
                    if(areaArray.length>0){
                        $.each(areaArray,function (index,OfcGroupVo) {
                            var area={};
                            area.label=OfcGroupVo.groupName;
                            area.value= OfcGroupVo.serialNo;
                            vueObj.areaNameOptions.push(area);
                        });
                        if(vueObj.areaNameOptions.length==1){
                            vueObj.areaName=vueObj.areaNameOptions[0].value;
                        }
                    }else{
                        layer.msg("当前用户下没有大区信息！");
                    }

                    if(baseArray.length>0){
                        $.each(baseArray,function (index,OfcGroupVo) {
                                var base={};
                                base.label=OfcGroupVo.groupName;
                                base.value= OfcGroupVo.serialNo;
                                vueObj.baseNameOptions.push(base);
                        });
                        if(vueObj.baseNameOptions.length==1){
                            vueObj.baseName=vueObj.baseNameOptions[0].value;
                        }
                    }else{
                        layer.msg("当前用户下没有基地信息！");
                    }
                }
            });
        },
        methods: {
            handleCustomerCurrentChange:function(val) {
                this.currentCustomerRow = val;
            },
            handleCustomerSizeChange:function(val) {
                this.customerPageSize=val;
                this.selectCustomer();
            },
            orderDetails:function (val) {
                    var url = "/ofc/orderStorageInDetails/"+"?orderCode="+val;
                    var html = window.location.href;
                    var index = html.indexOf("/index#");
                    window.open(html.substring(0,index) + "/index#" + url);
            },
            handleCustomerCurrentPage:function(val) {
                this.customerCurrentPage = val;
                this.selectCustomer();
            },
            setCurrentCustInfo:function(val) {
                this.customerName = val.customerName;
                this.customerCode=val.customerCode;
                this.chosenCus = false;
            },
            cancelSelectCustomer:function(){
                this.customerData=[];
                this.customerPageSize=10;
                this.totalCustomer=0;
                this.chosenCus=false;
            },
            handleCustomerCurrentPage:function(val) {
                this.customerCurrentPage = val;
                this.selectCustomer();
            },
            selectCustomer:function(){
                var param = {};
                param.pageNum = this.customerCurrentPage;
                param.pageSize=this.customerPageSize;
                param.custName = this.chosenCusForm.name;
                this.customerData=[];
                var vueObj=this;
                CommonClient.syncpost(sys.rootPath + "/ofc/distributing/queryCustomerByName", param,
                        function(result) {
                            if (result == undefined || result == null || result.result == null ||  result.result.length == 0 || result.result.list == null) {
                                vueObj.totalCustomer=0;
                                vueObj.customerCurrentPage=0;
                                layer.msg("暂时未查询到客户信息！！");

                            } else if (result.code == 200) {
                                if(result.result.length == 0){
                                    vueObj.totalCustomer=0;
                                    vueObj.customerCurrentPage=0;
                                    layer.msg("暂时未查询到客户信息！！");
                                }
                                $.each(result.result.list,function (index,cscCustomerVo) {
                                    var channel = cscCustomerVo.channel;
                                    if(null == channel){
                                        channel = "";
                                    }
                                    var customer={};
                                    customer.customerCode=cscCustomerVo.customerCode;
                                    var custType = StringUtil.nullToEmpty(cscCustomerVo.type);
                                    if(custType == '1'){
                                        customer.type="公司";
                                    }else if (custType == '2'){
                                        customer.type="个人";
                                    }else{
                                        customer.type=custType;
                                    }
                                    customer.customerName=cscCustomerVo.customerName;
                                    customer.channel=channel;
                                    customer.productType=cscCustomerVo.productType;
                                    vueObj.customerData.push(customer);
                                });
                                vueObj.totalCustomer=result.result.total;
                            } else if (result.code == 403) {
                                vueObj.promptInfo("没有权限","error");
                            }
                        },"json");
            },
            handleSizeChange:function(val){
                this.pageSize=val;
                this.selectOrder();
            },
            handleSelectionChange:function(val){
                this.multipleSelection = val;

            },
            addOrder:function(){
                var url = "/ofc/orderStorageIn/"+"?tag=manager";
                var html = window.location.href;
                var index = html.indexOf("/index#");
                window.open(html.substring(0,index) + "/index#" + url,"_self");
            },
            editOrder:function(){
                if(this.valiateSelectOrder()){
                    var order=this.multipleSelection[0];
                    if(order.orderStatusName!="待审核"){
                        this.promptInfo("只有处于待审核状态才可以进行编辑","warning");
                        return;
                    }
                    var url = "/ofc/orderStorageInEdit/"+"?orderCode="+order.orderCode;
                    var html = window.location.href;
                    var index = html.indexOf("/index#");
                    window.open(html.substring(0,index) + "/index#" + url,"_self");
                }
            },
            deleteOrder:function(){
                    if(this.multipleSelection.length<1){
                        this.promptInfo("请至少选中一行","warning");
                        return false;
                    }
                    var flag=true;
                    for(var i=0;i<this.multipleSelection.length;i++){
                        var order=this.multipleSelection[i];
                        var vueObj=this;
                        if(order.orderStatusName!="待审核"){
                            vueObj.promptInfo("只有处于待审核状态才可以删除","warning");
                            return;
                        }
                        CommonClient.syncpost(sys.rootPath + "/ofc/orderDeleteOper", {"orderCode":order.orderCode,"orderStatus":this.getOrderStatusName(order.orderStatusName)}, function(result) {
                            if(result==undefined||result==null){
                                vueObj.promptInfo("订单删除失败","error");
                                flag=false;
                                return;
                            }else if(result.code==200){
                                vueObj.selectOrder();
                            }else{
                                flag=false;
                                if(result.message==null||result.message==""){
                                    vueObj.promptInfo("订单删除失败","error");
                                }else{
                                    vueObj.promptInfo(result.message,"error");
                                }
                                return;
                            }
                        });
                    }
                    if(flag){
                        vueObj.promptInfo("订单删除成功！","success");
                    }
            },
            copyOrder:function(){
                if(this.valiateSelectOrder()){
                    var order=this.multipleSelection[0];
                    var vueObj=this;
                    vueObj.isDisabledCopy=true;
                    CommonClient.post(sys.rootPath + "/ofc/copyOrderOper", {"orderCode":order.orderCode}, function(result) {
                        if (result == undefined || result == null||result.result==null ) {
                            vueObj.promptInfo(" 复制订单出现异常","error");
                            vueObj.isDisabledCopy=false;
                            return;
                        }else if(result.code==200&&result.result!=null){
                            vueObj.promptInfo("订单复制成功！订单编号:"+result.result,"success");
                            vueObj.isDisabledCopy=false;
                            vueObj.selectOrder();
                        }else{
                            if(result.message==null||result.message==""){
                                vueObj.promptInfo("复制订单出现异常","error");
                                vueObj.isDisabledCopy=false;
                            }else{
                                vueObj.promptInfo(result.message,"error");
                                vueObj.isDisabledCopy=false;
                            }
                        }
                    });
                }
            },
            resetCondition:function(){
                this.beginDate=new Date()- 3600 * 1000 * 24 * 2;
                this.endDate=new Date();
                this.orderCode="";
                this.customerOrderCode="";
                this.customerName="";
                this.customerCode="";
                this.orderStatus="";
                this.businessType="";
                this.baseName="";
                this.areaName="";
                this.wareHouseName="";
                this.provideTransport="";
                if(this.baseNameOptions.length==1&&this.areaNameOptions.length==1){
                    this.areaName=this.areaNameOptions[0].value;
                    this.baseName=this.baseNameOptions[0].value;
                }
                if(this.baseNameOptions.length>1&&this.areaNameOptions.length==1){
                    this.areaName=this.areaNameOptions[0].value;
                }
            },
            auditOrder:function(){
                if(this.multipleSelection.length<1){
                    this.promptInfo("请至少选中一行","warning");
                    return false;
                }
                var vueObj=this;
                var orders=vueObj.multipleSelection;
                vueObj.isDisabledAudit=true;
                for(var i=0;i<orders.length;i++){
                    var order=orders[i];
                    if(order.orderStatusName!="待审核"){
                        vueObj.promptInfo("订单编号"+order.orderCode+"不能执行审核，仅能对订单状态为【待审核】的订单执行审核操作！","warning");
                        vueObj.isDisabledAudit=false;
                        return;
                    }
                }
                this.auditOrderOrNotAuditOper(order.orderCode,"review");
            },
            repeatAuditOrder:function(){
                if(this.multipleSelection.length<1){
                    this.promptInfo("请至少选中一行","warning");
                    return false;
                }
                var vueObj=this;
                vueObj.isDisabledRepeatAudit=true;
                var orders=vueObj.multipleSelection;
                for(var i=0;i<orders.length;i++){
                    var order=orders[i];
                    if(order.orderStatusName!="已审核"){
                        vueObj.promptInfo("订单编号"+order.orderCode+"不能执行反审核，仅能对订单状态为【已审核】的订单执行反审核操作！","warning");
                        vueObj.isDisabledRepeatAudit=false;
                        return;
                    }
                }
                this.auditOrderOrNotAuditOper(order.orderCode,"rereview");
            },
            cancelOrder:function(){
                var vueObj=this;
                if(this.multipleSelection.length<1){
                    this.promptInfo("请至少选中一行","warning");
                    return false;
                }
                var orders=this.multipleSelection;
                var flag=false;
                vueObj.isDisabledCancel=true;
                for(var i=0;i<orders.length;i++){
                    var order=orders[i];
                    if(order.orderStatusName=="执行中"||order.orderStatusName=="已审核"){
                        CommonClient.syncpost(sys.rootPath + "/ofc/orderCancelOper", {"orderCode":order.orderCode}, function(result) {
                            if (result == undefined || result == null ) {
                            }else if(result.code==200){
                                flag=true;
                            }else{
                                if(result.message==null||result.message==""){
                                }else{
                                }
                            }
                        });
                    }else{
                        vueObj.promptInfo("订单编号"+order.orderCode+"不能执行取消，仅能对订单状态为【已审核】或【执行中】的订单执行取消操作！","warning");
                        vueObj.isDisabledCancel=false;
                        return;
                    }
                }
                if(flag){
                    vueObj.promptInfo("订单取消成功","success");
                    vueObj.isDisabledCancel=false;
                    vueObj.selectOrder();
                }else{
                    vueObj.isDisabledCancel=false;
                    vueObj.promptInfo("订单取消失败","error");
                }
            },
            batchImport:function(){
                var templateType = "storageIn";
                var url = "/ofc/storage_template/batch_import/" + templateType;
                var html = window.location.href;
                var index = html.indexOf("/index#");
                window.open(html.substring(0,index) + "/index#" + url);
            },
            auditOrderOrNotAuditOper:function (orderCode,tag) {
                var vueObj=this;
                var flag=false;
                CommonClient.syncpost(sys.rootPath + "/ofc/auditOrderOrNotAuditOper", {"orderCode":orderCode,"reviewTag":tag}, function(result) {
                    if (result == undefined || result == null ) {
                    }else if(result.code==200){
                        flag=true;
                    }else{
                        if(result.message==null||result.message==""){
                        }else{
                        }
                    }
                });
                if(flag){
                    if(tag=="rereview"){
                        vueObj.promptInfo("订单反审核成功","success");
                        vueObj.isDisabledRepeatAudit=false;
                        vueObj.selectOrder();
                    }else if(tag=="review"){
                        vueObj.promptInfo("订单审核成功","success");
                        vueObj.isDisabledAudit=false;
                        vueObj.selectOrder();
                    }
                }
            },
            valiateSelectOrder:function(){
                if(this.multipleSelection.length<1){
                    this.promptInfo("请至少选中一行","warning");
                    return false;
                }
                if(this.multipleSelection.length>1){
                    this.promptInfo("只能选择一行","warning");
                    return false;
                }
                return true;
            },
            handleCurrentPage:function(val){
                this.orderCurrentPage=val;
                this.selectOrder();
            },
            getBusinessName:function(businessType){
                for(var i=0;i<this.businessTypeOptions.length;i++){
                    var option=this.businessTypeOptions[i];
                    if(businessType==option.value){
                        return option.label;
                        break;
                    }else if(businessType==option.label){
                        return option.value;
                        break;
                    }
                }
            },
            getOrderStatusName:function(orderStatus){
                for(var i=0;i<this.orderStatusOptions.length;i++){
                    var option=this.orderStatusOptions[i];
                    if(orderStatus==option.value){
                        return option.label;
                        break;
                    }else if(orderStatus==option.label){
                        return option.value;
                        break;
                    }
                }
            },
            selectOrder:function(){
                var param={};
                var vueObj=this;
                vueObj.orderData=[];
                if(this.beginDate&& this.endDate){
                    this.beginDate=new Date(this.beginDate);
                    if( this.beginDate.getTime()> this.endDate.getTime()){
                        vueObj.promptInfo("订单的起始日期不能大于结束日期","error");
                        return;
                    }
                    if( this.endDate.getTime()-this.beginDate.getTime()> 3600 * 1000 * 24 * 90){
                        vueObj.promptInfo("订单的起始日期和结束日期不能相差90天","warning");
                        return;
                    }
                }
                if(this.beginDate){
                    param.startDate=DateUtil.format(this.beginDate, "yyyy-MM-dd HH:mm:ss");
                }
                if(this.endDate){
                    param.endDate = this.endDate.getFullYear()+"-"+(this.endDate.getMonth()+1)+"-"+this.endDate.getDate()+" 23:59:59";
                }
                if(!StringUtil.isEmpty(this.baseName)){
                    if(StringUtil.isEmpty(this.areaName)){
                        vueObj.promptInfo("选择基地时，必须选择大区","warning");
                        return;
                    }
                }
                param.pageNum = this.orderCurrentPage;
                param.pageSize=this.pageSize;
                param.custName =this.customerName;
                param.orderCode =StringUtil.trim(this.orderCode);
                param.orderState= StringUtil.trim(this.orderStatus);
                param.orderType ="61";//仓储订单
                param.businessType =StringUtil.trim(this.businessType);
                param.areaSerialNo = StringUtil.trim(this.areaName);
                param.baseSerialNo = StringUtil.trim(this.baseName);
                param.custOrderCode=StringUtil.trim(this.customerOrderCode);
                param.warehouseCode=StringUtil.trim(this.wareHouseName);
                param.provideTransport = StringUtil.trim(this.provideTransport);
                param.tag="in";
                CommonClient.syncpost(sys.rootPath + "/ofc/queryOrderStorageDataOper", param,function (result) {
                    if (result == undefined || result == null || result.result.list.length == 0 || result.result.list == null) {
                        vueObj.totalOrder=0;
                        vueObj.orderCurrentPage=0;
                        layer.msg("暂时未查询到相关订单信息！");
                    }
                    if (result.code == 200) {
                        if(result.result.list.length == 0){
                            vueObj.totalOrder=0;
                            vueObj.orderCurrentPage=0;
                            layer.msg("暂时未查询到相关订单信息！");
                        }
                        $.each(result.result.list, function (index, item) {
                            var order={};
                            order.customerName=item.custName;
                            order.orderCode=item.orderCode;
                            order.orderBatchNumber=item.orderBatchNumber;
                            order.customerOrderCode=item.custOrderCode;
                            order.orderDate=item.orderTime;
                            order.businessName=vueObj.getBusinessName(item.businessType);
                            order.orderStatusName=vueObj.getOrderStatusName(item.orderStatus);
                            order.wareHouseName=item.warehouseName;
                            order.baseName=item.baseName;
                            vueObj.orderData.push(order);
                        })
                        vueObj.totalOrder=result.result.total;
                    } else if (result.code == 403) {
                        vueObj.promptInfo("没有权限","error");
                    }
                });
            },
            promptInfo:function(message,type){
                this.$message({
                  message: message,
                  type: type
                });
            },
            getBaseNameByArea:function(){
                var  _this=this;
                _this.baseNameOptions=[];
                _this.baseName="";
                CommonClient.post(sys.rootPath + "/ofc/queryBaseListByArea",{"areaCode":_this.areaName},function(data) {
                    if (data == undefined || data == null || null == data.result
                            || undefined == data.result || data.result.size == 0) {
                        layer.msg("暂时未查询到基地信息！！");
                    }else if(data.code == 200){
                        var res=eval(data.result);
                        $.each(res,function (index,baseMsg) {
                            var option={};
                            option.label=baseMsg.groupName;
                            option.value=baseMsg.serialNo;
                            _this.baseNameOptions.push(option);
                        });
                    }else if (res.code == 403) {
                        vueObj.promptInfo("没有权限","error");
                    } else if(res.code == 500){
                        vueObj.promptInfo("查询基地出错!","error");
                    } else {
                        vueObj.promptInfo("查询基地出错!","error");
                    }
                })
            },
            openCustomer:function(){
                this.customerData=[];
                this.customerPageSize=10;
                this.totalCustomer=0;
                this.chosenCus=true;
            },
            realGood:function(){
                if(this.valiateSelectOrder()){
                    this.chosenRealGood=true;
                    var _this=this;
                    _this.realGoodsData=[];
                    var orderCode=this.multipleSelection[0].orderCode;
                    CommonClient.syncpost(sys.rootPath + "/ofc/queryRealGood",{"orderCode":orderCode,"businessType":"RK"},function(result) {
                        if(result==undefined||result==null||result.result==null){
                            return;
                        }else if(result.code == 200) {
                            var data = eval(result.result);
                            _this.realGoodsData = [];
                            $.each(data, function (index, wmsDetailsValueDTO) {
                                var good = {};
                                good.goodsCode = wmsDetailsValueDTO.itemCode;
                                good.goodsName = wmsDetailsValueDTO.itemName;
                                good.goodsSpec = wmsDetailsValueDTO.standard;
                                good.unit = wmsDetailsValueDTO.uom;
                                good.realQuantity = wmsDetailsValueDTO.sendQty;//
                                good.unitPrice = wmsDetailsValueDTO.price;
                                good.productionBatch = wmsDetailsValueDTO.lotatt05;//批次号
                                good.productionTime = DateUtil.parse(wmsDetailsValueDTO.lotatt01);//生产日期
                                good.invalidTime = DateUtil.parse(wmsDetailsValueDTO.lotatt02);//失效日期
                                _this.realGoodsData.push(good);
                            });
                        }
                    });

                }
            }
        }
    });
</script>