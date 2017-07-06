<title>接收日志</title>
<body>
<div id="app">
    <div class="xe-pageHeader">
        接收日志筛选
    </div>
    <el-form ref="logForm" label-width="100px">
        <div class="xe-block">
            <el-form-item label="创建时间" class="xe-col-3">
                <el-date-picker v-model="logForm.dateRange" type="datetimerange" :picker-options="pickerOptions"
                                placeholder="选择时间范围" format="yyyy-MM-dd HH:mm" align="left"
                                style="font-size: 10px;">
                </el-date-picker>
            </el-form-item>
            <el-form-item label="业务单号" class="xe-col-3">
                <el-input v-model="logForm.refNo" placeholder="请输入内容"></el-input>
            </el-form-item>
            <el-form-item label="业务类型" class="xe-col-3">
                <el-select v-model="logForm.logBusinessType" clearable placeholder="请选择">
                    <el-option
                            v-for="item in logBusinessTypeOptions"
                            :key="item.value"
                            :label="item.label"
                            :value="item.value">
                    </el-option>
                </el-select>
            </el-form-item>
        </div>
        <div class="xe-block">
            <el-form-item label="发送系统" class="xe-col-3">
                <el-select v-model="logForm.logFromSys" clearable placeholder="请选择">
                    <el-option
                            v-for="item in logSourceSysOptions"
                            :key="item.value"
                            :label="item.label"
                            :value="item.value">
                    </el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="接收系统" class="xe-col-3">
                <el-select v-model="logForm.logToSys" clearable placeholder="请选择">
                    <el-option
                            v-for="item in logSourceSysOptions"
                            :key="item.value"
                            :label="item.label"
                            :value="item.value">
                    </el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="执行状态" class="xe-col-3">
                <el-select v-model="logForm.logStatus" clearable placeholder="请选择">
                    <el-option
                            v-for="item in logStatusOptions"
                            :key="item.value"
                            :label="item.label"
                            :value="item.value">
                    </el-option>
                </el-select>
            </el-form-item>
        </div>
        <div class="xe-block">
            <el-form-item label="" class="xe-col-3">
                <el-button type="primary" @click="doSearch">筛选</el-button>
                <el-button @click="resetForm">重置</el-button>
            </el-form-item>
        </div>
    </el-form>
    <div class="xe-pageHeader">
        接收日志列表
    </div>
    <el-table :data="logData" border @selection-change="handleSelectionChange" style="width: 100%">
        <el-table-column type="index" label="序号"></el-table-column>
        <el-table-column property="logBusinessType" label="业务类型"></el-table-column>
        <el-table-column property="refNo" label="业务单号"></el-table-column>
        <el-table-column property="logFromSys" label="发送系统"></el-table-column>
        <el-table-column property="logToSys" label="接收系统" width="90px"></el-table-column>
        <el-table-column property="logStatus" label="执行状态" width="90px" align="center">
            <template scope="scope">
                <el-tag v-if="scope.row.logStatus == '待处理'" type="gray">{{ scope.row.logStatus }}</el-tag>
                <el-tag v-else-if="scope.row.logStatus == '处理中'" type="primary">{{ scope.row.logStatus }}</el-tag>
                <el-tag v-else-if="scope.row.logStatus == '处理失败'" type="danger">{{ scope.row.logStatus }}</el-tag>
                <el-tag v-else-if="scope.row.logStatus == '处理成功'" type="success">{{ scope.row.logStatus }}</el-tag>
            </template>
        </el-table-column>
        <el-table-column property="processCount" label="执行次数" width="70px" align="center"></el-table-column>
        <el-table-column property="creationTime" label="创建时间" align="center"></el-table-column>
        <el-table-column property="processTime" label="执行时间" align="center"></el-table-column>
        <el-table-column fixed="right" label="操作" width="100">
            <template scope="scope">
                <el-button @click.native.prevent="showReceiveLogDetail(scope.$index, scope.row)" type="text"
                           size="small">查看
                </el-button>
            </template>
        </el-table-column>
    </el-table>
    <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentPage"
                   :current-page="pageInfo.pageNum" :page-sizes="pageInfo.xePageSizes"
                   :page-size="pageInfo.pageSize"
                   layout="total, sizes, prev, pager, next" :total="pageInfo.total">
    </el-pagination>
</div>
</body>
<script>
    new Vue({
        el: '#app',
        data: {
            logBusinessTypeOptions: [],
            logSourceSysOptions: [],
            logStatusOptions: [
                {label: '待处理', value: '1'},
                {label: '处理中', value: '2'},
                {label: '处理成功', value: '3'},
                {label: '处理失败', value: '4'}
            ],
            logForm: {
                dateRange: [new Date(new Date().setHours(0, 0, 0, 0)), new Date(new Date().setHours(23, 59, 59, 59))],
                logBusinessType: '',
                refNo: '',
                logFromSys: '',
                logToSys: '',
                logStatus: ''
            },
            pageInfo: {
                total: 0,
                pageNum: 1,
                pageSize: 10,
                xePageSizes: [10, 20, 50, 100]
            },
            logData: [],
            pickerOptions: {
                shortcuts: [{
                    text: '最近一周',
                    onClick(picker) {
                        const end = new Date(new Date().setHours(23, 59, 59, 59));
                        const start = new Date(new Date().setHours(0, 0, 0, 0));
                        start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
                        picker.$emit('pick', [start, end]);
                    }
                }, {
                    text: '最近一个月',
                    onClick(picker) {
                        const end = new Date(new Date().setHours(23, 59, 59, 59));
                        const start = new Date(new Date().setHours(0, 0, 0, 0));
                        start.setTime(start.getTime() - 3600 * 1000 * 24 * 30);
                        picker.$emit('pick', [start, end]);
                    }
                }, {
                    text: '最近三个月',
                    onClick(picker) {
                        const end = new Date(new Date().setHours(23, 59, 59, 59));
                        const start = new Date(new Date().setHours(0, 0, 0, 0));
                        start.setTime(start.getTime() - 3600 * 1000 * 24 * 90);
                        picker.$emit('pick', [start, end]);
                    }
                }]
            }
        },
        beforeMount: function () {
            var vueObj = this;
            // 业务类型
            var param = {};
            param.enumSys = 'OFC';
            param.enumType = 'LogBusinessTypeEnum';
            CommonClient.post(sys.rootPath + "/ofc/enum/queryEnums", param, function (result) {
                if (result == undefined || result == null) {
                    vueObj.$message.error('查询业务类型发生错误！');
                } else if (result.code == 200) {
                    if (result.result.length > 0) {
                        $.each(result.result, function (index, OfcEnumeration) {
                            var enumeration = {};
                            enumeration.label = OfcEnumeration.enumName;
                            enumeration.value = OfcEnumeration.enumValue;
                            vueObj.logBusinessTypeOptions.push(enumeration);
                        });
                    } else {
                        console.info("暂时未查询到业务类型信息！");
                    }
                }
            });
            // 系统类型
            var param = {};
            param.enumSys = 'OFC';
            param.enumType = 'LogSourceSysEnum';
            CommonClient.post(sys.rootPath + "/ofc/enum/queryEnums", param, function (result) {
                if (result == undefined || result == null) {
                    vueObj.$message.error('查询业务类型发生错误！');
                } else if (result.code == 200) {
                    if (result.result.length > 0) {
                        $.each(result.result, function (index, OfcEnumeration) {
                            var enumeration = {};
                            enumeration.label = OfcEnumeration.enumName;
                            enumeration.value = OfcEnumeration.enumValue;
                            vueObj.logSourceSysOptions.push(enumeration);
                        });
                    } else {
                        console.info("暂时未查询到业务类型信息！");
                    }
                }
            });
        },
        created: function () {
            this.doSearch();
        },
        methods: {
            handleSelectionChange: function (val) {

            },
            handleSizeChange: function (val) {
                this.pageInfo.pageSize = val;
                this.doSearch();
            },
            handleCurrentPage: function (val) {
                this.pageInfo.pageNum = val;
                this.doSearch();
            },
            doSearch: function () {
                var vueObj = this;
                vueObj.logData = [];
                vueObj.pageInfo.pageNum = 1;
                vueObj.pageInfo.pageSize = 10;
                vueObj.pageInfo.total = 0;
                var taskParam = {};
                taskParam.pageNum = this.pageInfo.pageNum;
                taskParam.pageSize = this.pageInfo.pageSize;
//                var ofcTaskInterfaceLogVo = {};
                taskParam.beginDate = DateUtil.format(this.logForm.dateRange[0], 'yyyy-MM-dd HH:mm:ss');
                taskParam.endDate = DateUtil.format(this.logForm.dateRange[1], 'yyyy-MM-dd HH:mm:ss');
                taskParam.logBusinessType = this.logForm.logBusinessType;
                taskParam.refNo = StringUtil.trim(this.logForm.refNo);
                taskParam.logFromSys = this.logForm.logFromSys;
                taskParam.logToSys = this.logForm.logToSys;
                taskParam.logStatus = this.logForm.logStatus;
//                taskParam.param = ofcTaskInterfaceLogVo;
                CommonClient.post(sys.rootPath + "/ofc/interface/queryReceiveLog", taskParam, function (result) {
                    if (result == undefined || result == null || result.code == 500) {
                        vueObj.$message.error('查询接收日志发生错误！');
                    } else if (result.code == 200) {
                        if (result.result.list.length > 0) {
                            $.each(result.result.list, function (index, OfcInterfaceReceiveLog) {
                                var receiveLog = {};
                                receiveLog.id = OfcInterfaceReceiveLog.id;
                                receiveLog.logBusinessType = OfcInterfaceReceiveLog.logBusinessType;
                                receiveLog.refNo = OfcInterfaceReceiveLog.refNo;
                                receiveLog.logFromSys = OfcInterfaceReceiveLog.logFromSys;
                                receiveLog.logToSys = OfcInterfaceReceiveLog.logToSys;
                                receiveLog.logStatus = OfcInterfaceReceiveLog.logStatus;
                                receiveLog.processCount = OfcInterfaceReceiveLog.processCount;
                                receiveLog.creationTime = OfcInterfaceReceiveLog.creationTime;
                                receiveLog.processTime = OfcInterfaceReceiveLog.processTime;
                                vueObj.logData.push(receiveLog);
                            });
                            vueObj.pageInfo.total = result.result.total;
                        } else {
                            console.info("暂时未查询到接收日志信息！");
                        }
                    }
                });
            },
            resetForm: function () {
                this.taskForm.dateRange = [new Date(new Date().setHours(0, 0, 0, 0)), new Date(new Date().setHours(23, 59, 59, 59))];
                this.logForm.logBusinessType = '';
                this.logForm.refNo = '';
                this.logForm.logFromSys = '';
                this.logForm.logToSys = '';
                this.logForm.logStatus = '';
            },
            showReceiveLogDetail: function (index, row) {
                var url = "/ofc/interface/showReceiveLogDetail?id=" + row.id;
                var html = window.location.href;
                var index = html.indexOf("/index#");
                window.open(html.substring(0, index) + "/index#" + url);
            }
        }
    });
</script>