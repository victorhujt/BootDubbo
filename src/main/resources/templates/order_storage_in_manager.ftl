<!--suppress ALL -->
<head>
    <style lang="css">
        .block {
            margin: 20px 0;
        }
    </style>
</head>
<body>
    <div id="app">
    <div class="list-mian-01">
        <el-dialog title="选择客户" v-model="chosenCus" size="small">
            <el-form :model="chosenCusForm">
                <el-form-item label="名称" :label-width="formLabelWidth">
                    <el-input v-model="chosenCusForm.name" auto-complete="off"></el-input>
                </el-form-item>
                <el-form-item label="" :label-width="formLabelWidth">
                  <el-button type="primary" @click="selectCustomer">查询</el-button>
                </el-form-item>
            </el-form>
            <el-table :data="customerData" highlight-current-row @current-change="handleCustomerCurrentChange" style="width: 100%">
                <el-table-column type="index"></el-table-column>
                <el-table-column property="customerCode" label="客户编码"></el-table-column>
                <el-table-column property="type" label="类型"></el-table-column>
                <el-table-column property="customerName" label="公司名称"></el-table-column>
                <el-table-column property="channel" label="渠道"></el-table-column>
                <el-table-column property="productType" label="产品类别"></el-table-column>
            </el-table>
            <el-pagination @size-change="handleCustomerSizeChange" @current-change="handleCustomerCurrentPage" :current-page="currentPage" :page-sizes="pageSizes" :page-size="customerPageSize" :total="total" layout="total, sizes, prev, pager, next, jumper">
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
                      placeholder="选择起始日期">
              </el-date-picker>
              <label for="" style="width:15px;">至</label>
              <el-date-picker
                      style="width:114px;"
                      v-model="endDate"
                      type="date"
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
                      @click="chosenCus = true">
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
            <el-form-item label="业务名称" class="xe-col-3">
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
              <el-select v-model="areaName" placeholder="请选择">
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
        <div>
            <el-button type="primary" size="small" @click="addOrder">添加</el-button>
            <el-button type="primary" size="small" @click="editOrder">编辑</el-button>
            <el-button type="primary" size="small" @click="deleteOrder">删除</el-button>
            <el-button type="primary" size="small" @click="copyOrder">复制</el-button>
            <el-button type="primary" size="small" @click="auditOrder">审核</el-button>
            <el-button type="primary" size="small" @click="repeatAuditOrder">反审核</el-button>
            <el-button type="primary" size="small" @click="cancelOrder">取消</el-button>
            <el-button type="primary" size="small" @click="batchImport">批量导入</el-button>
        </div>
        <div class="block">
            <el-table :data="orderData"  @selection-change="handleSelectionChange" style="width: 100%">
                <el-table-column type="index"></el-table-column>
                <el-table-column type="selection">
                </el-table-column>
                <el-table-column property="customerName" label="客户名称"></el-table-column>
                <el-table-column
                        property="orderCode"
                        label="订单编号">
                    <template scope="scope">
                        <el-button type="text" @click="orderDetails(scope.row.orderCode)"><p style="color: blue">{{scope.row.orderCode}}</p></el-button>
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
            <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentPage" :current-page="currentPage" :page-sizes="pageSizes" :page-size="pageSize" layout="total, sizes, prev, pager, next, jumper" :total="total">
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
            currentRow:'',
            currentCustomerRow:'',
            currentPage:1,
            formLabelWidth: '100px',
            pageSize:10,
            total:0,
            wareHouseOptions:[],
            pageSizes:[10, 20, 30, 40,50],
            customerData:[],
            customerPageSize:10,
            currentCustomerPage:1,
            wareHouseName:'',
            businessType:'',
            areaName:'',
            baseName:'',
            orderStatus:'',
            orderCode:'',
            customerName:'',
            customerCode:'',
            customerOrderCode:'',
            total:0,
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
            this.wareHouseOptions=[];
            this.areaNameOptions=[];
            this.baseNameOptions=[];
            var vueObj=this;
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
                    }else{
                        layer.msg("当前用户下没有仓库信息！");
                    }
                }
            });

            //大区和基地信息
            CommonClient.post(sys.rootPath + "/ofc/loadAreaAndBaseByUser",{},function (result) {
                if (result == undefined || result == null) {
                    layer.msg("当前用户下没有大区和基地信息！");
                } else if (result.code == 200) {
                    var areaArray=result.result.area;
                    var baseArray=result.result.base;
                    if(areaArray.length>0){
                        $.each(areaArray,function (index,OfcGroupVo) {
                            var area={};
                            if(OfcGroupVo.groupName&&OfcGroupVo.serialNo){
                                area.label=OfcGroupVo.groupName;
                                area.value= OfcGroupVo.serialNo;
                                vueObj.areaNameOptions.push(area);
                            }
                        });
                    }else{
                        layer.msg("当前用户下没有大区信息！");
                    }

                    if(baseArray.length>0){
                        $.each(baseArray,function (index,OfcGroupVo) {
                            if(OfcGroupVo.groupName!=""&&OfcGroupVo.serialNo!=""){
                                var base={};
                                base.label=OfcGroupVo.groupName;
                                base.value= OfcGroupVo.serialNo;
                                vueObj.baseNameOptions.push(base);
                            }
                        });
                    }else{
                        layer.msg("当前用户下没有基地信息！");
                    }
                }
            });
            vueObj.selectOrder();
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
                this.currentCustomerPage = val;
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
                this.total=0;
                this.chosenCus=false;
            },
            handleCustomerCurrentPage:function(val) {
                this.currentCustomerPage = val;
                this.selectCustomer();
            },
            selectCustomer:function(){
                var param = {};
                param.pageNum = this.currentCustomerPage;
                param.pageSize=this.customerPageSize;
                param.custName = this.chosenCusForm.name;
                this.customerData=[];
                var vueObj=this;
                CommonClient.post(sys.rootPath + "/ofc/distributing/queryCustomerByName", param,
                        function(result) {
                            if (result == undefined || result == null || result.result == null ||  result.result.size == 0 || result.result.list == null) {
                                layer.msg("暂时未查询到客户信息！！");
                            } else if (result.code == 200) {
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
                                vueObj.total=result.result.total;
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
                window.open(html.substring(0,index) + "/index#" + url);
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
                    window.open(html.substring(0,index) + "/index#" + url);
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
                    CommonClient.post(sys.rootPath + "/ofc/copyOrderOper", {"orderCode":order.orderCode}, function(result) {
                        if (result == undefined || result == null||result.result==null ) {
                            vueObj.promptInfo(" 复制订单出现异常","error");
                            return;
                        }else if(result.code==200&&result.result!=null){
                            vueObj.promptInfo("订单复制成功！订单编号:"+result.result,"success");
                            vueObj.selectOrder();
                        }else{
                            if(result.message==null||result.message==""){
                                vueObj.promptInfo("复制订单出现异常","error");
                            }else{
                                vueObj.promptInfo(result.message,"error");
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
                    this.areaName="";
                    this.baseName="";
                    this.wareHouseName="";

            },
            auditOrder:function(){
                if(this.valiateSelectOrder()){
                    var order=this.multipleSelection[0];
                    if(order.orderStatusName!="待审核"){
                        this.promptInfo("只有待审核的可以审核","warning");
                        return;
                    }
                    this.auditOrderOrNotAuditOper(order.orderCode,"review");
                }
            },
            repeatAuditOrder:function(){
                if(this.valiateSelectOrder()){
                    var order=this.multipleSelection[0];
                    this.auditOrderOrNotAuditOper(order.orderCode,"rereview");
                }
            },
            cancelOrder:function(){
                if(this.valiateSelectOrder()){
                    var order=this.multipleSelection[0];
                    var vueObj=this;
                    if(order.orderStatusName=="执行中"||order.orderStatusName=="已审核"){
                        CommonClient.syncpost(sys.rootPath + "/ofc/orderCancelOper", {"orderCode":order.orderCode}, function(result) {
                            if (result == undefined || result == null ) {
                              vueObj.promptInfo("取消订单出现异常","error");
                              return;
                            }else if(result.code==200){
                                vueObj.promptInfo(result.message,"success");
                                vueObj.selectOrder();
                            }else{
                                if(result.message==null||result.message==""){
                                    vueObj.promptInfo("订单取消失败","error");
                                }else{
                                    vueObj.promptInfo(result.message,"error");
                                }
                            }
                        });
                    }else{
                        vueObj.promptInfo("订单编号"+order.orderCode+"不能执行取消，仅能对订单状态为【已审核】或【执行中】的订单执行取消操作！","error");
                        return;
                    }
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
                CommonClient.syncpost(sys.rootPath + "/ofc/auditOrderOrNotAuditOper", {"orderCode":orderCode,"reviewTag":tag}, function(result) {
                    if (result == undefined || result == null ) {
                         vueObj.promptInfo("审核或者反审核出现异常","error");
                        return;
                    }else if(result.code==200){
                        vueObj.promptInfo(result.message,"success");
                        vueObj.selectOrder();
                    }else{
                        if(result.message==null||result.message==""){
                            vueObj.promptInfo("审核或者反审核出现异常","error");
                        }else{
                            vueObj.promptInfo(result.message,"error");
                        }
                    }
                });
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
                this.currentPage=val;
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
                    if(this.baseName!=""){
                        if(this.areaName==""){
                            vueObj.promptInfo("选择基地时，必须选择大区","warning");
                            return;
                        }
                    }
                }
                if(this.beginDate){
                    param.startDate=DateUtil.format(this.beginDate, "yyyy-MM-dd HH:mm:ss");
                }
                if(this.endDate){
                    param.endDate = this.endDate.getFullYear()+"-"+(this.endDate.getMonth()+1)+"-"+this.endDate.getDate()+" 23:59:59";
                }
                param.pageNum = this.currentPage;
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
                param.tag="in";
                CommonClient.post(sys.rootPath + "/ofc/queryOrderStorageDataOper", param,function (result) {
                    if (result == undefined || result == null || result.result.size == 0 || result.result.list == null) {
                        layer.msg("暂时未查询到相关订单信息！");
                    } else if (result.code == 200) {
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
                        vueObj.total=result.result.total;
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
            }
        }
    });
</script>