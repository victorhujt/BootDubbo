<title>任务日志</title>
<body>
<div id="app">
    <div class="xe-pageHeader">
        任务日志筛选
    </div>
    <el-form ref="taskForm" label-width="100px">
        <div class="xe-block">
            <el-form-item label="创建时间" class="xe-col-3">
                <el-date-picker v-model="taskForm.dateRange" type="datetimerange" :picker-options="pickerOptions"
                                placeholder="选择时间范围" format="yyyy-MM-dd HH:mm" align="left"
                                style="font-size: 10px;">
                </el-date-picker>
            </el-form-item>
            <el-form-item label="业务单号" class="xe-col-3">
                <el-input v-model="taskForm.refNo" placeholder="请输入内容"></el-input>
            </el-form-item>
            <el-form-item label="任务类型" class="xe-col-3">
                <el-select v-model="taskForm.taskType" clearable placeholder="请选择">
                    <el-option
                            v-for="item in taskTypeOptions"
                            :key="item.value"
                            :label="item.label"
                            :value="item.value">
                    </el-option>
                </el-select>
            </el-form-item>
        </div>
        <div class="xe-block">
            <el-form-item label="任务来源" class="xe-col-3">
                <el-select v-model="taskForm.taskSource" clearable placeholder="请选择">
                    <el-option
                            v-for="item in taskSourceOptions"
                            :key="item.value"
                            :label="item.label"
                            :value="item.value">
                    </el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="任务状态" class="xe-col-3">
                <el-select v-model="taskForm.taskStatus" clearable placeholder="请选择">
                    <el-option
                            v-for="item in taskStatusOptions"
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
        任务日志列表
    </div>
    <el-table :data="taskData" border @selection-change="handleSelectionChange" style="width: 100%">
        <el-table-column type="index" label="序号"></el-table-column>
        <el-table-column property="taskType" label="任务类型"></el-table-column>
        <el-table-column property="refNo" label="业务单号"></el-table-column>
        <el-table-column property="taskSource" label="任务来源"></el-table-column>
        <el-table-column property="taskExeCount" label="执行次数" width="70px" align="center"></el-table-column>
        <el-table-column property="exeInstanceIp" label="执行IP"></el-table-column>
        <el-table-column property="taskStatus" label="任务状态" width="90px" align="center">
            <template scope="scope">
                <el-tag v-if="scope.row.taskStatus == '待处理'" type="gray">{{ scope.row.taskStatus }}</el-tag>
                <el-tag v-else-if="scope.row.taskStatus == '处理中'" type="primary">{{ scope.row.taskStatus }}</el-tag>
                <el-tag v-else-if="scope.row.taskStatus == '处理失败'" type="danger">{{ scope.row.taskStatus }}</el-tag>
                <el-tag v-else-if="scope.row.taskStatus == '处理成功'" type="success">{{ scope.row.taskStatus }}</el-tag>
            </template>
        </el-table-column>
        <el-table-column property="creationTime" label="创建时间" align="center"></el-table-column>
        <el-table-column property="exeTime" label="执行时间" align="center"></el-table-column>
        <el-table-column fixed="right" label="操作" width="150">
            <template scope="scope">
                <el-button @click.native.prevent="showTaskLogDetail(scope.$index, scope.row)" type="text" size="small">查看
                </el-button>
                <el-button @click.native.prevent="delTaskLog(scope.$index, scope.row)" type="text" size="small" style="color:red;">
                    删除
                </el-button>
                <el-button @click.native.prevent="resendTaskLog0(scope.$index, scope.row)" v-if="scope.row.taskStatus=='处理失败'" type="text"
                           size="small"
                           style="color:green">重发
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
            taskTypeOptions: [],
            taskSourceOptions: [{label: '接收日志', value: 'RECEIVE_LOG'}, {label: '发送日志', value: 'SEND_LOG'}],
            taskStatusOptions: [{label: '待处理', value: '1'}, {label: '处理中', value: '2'}, {
                label: '处理成功',
                value: '3'
            }, {label: '处理失败', value: '4'}],
            taskForm: {
                dateRange: [new Date(new Date().setHours(0, 0, 0, 0)), new Date(new Date().setHours(23, 59, 59, 59))],
                taskType: '',
                refNo: '',
                taskSource: '',
                taskStatus: ''
            },
            pageInfo: {
                total: 0,
                pageNum: 1,
                pageSize: 10,
                xePageSizes: [10, 20, 50, 100]
            },
            taskData: [],
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
            var param = {};
            param.enumSys = 'OFC';
            param.enumType = 'LogBusinessTypeEnum';
            CommonClient.post(sys.rootPath + "/ofc/enum/queryEnums", param, function (result) {
                if (result == undefined || result == null) {
                    vueObj.$message.error('查询任务类型发生错误！');
                } else if (result.code == 200) {
                    if (result.result.length > 0) {
                        $.each(result.result, function (index, OfcEnumeration) {
                            var enumeration = {};
                            enumeration.label = OfcEnumeration.enumName;
                            enumeration.value = OfcEnumeration.enumValue;
                            vueObj.taskTypeOptions.push(enumeration);
                        });
                    } else {
                        console.info("暂时未查询到任务类型信息！");
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
                vueObj.taskData = [];
                var taskParam = {};
                taskParam.pageNum = this.pageInfo.pageNum;
                taskParam.pageSize = this.pageInfo.pageSize;
//                var ofcTaskInterfaceLogVo = {};
                taskParam.beginDate = DateUtil.format(this.taskForm.dateRange[0], 'yyyy-MM-dd HH:mm:ss');
                taskParam.endDate = DateUtil.format(this.taskForm.dateRange[1], 'yyyy-MM-dd HH:mm:ss');
                taskParam.taskType = this.taskForm.taskType;
                taskParam.refNo = StringUtil.trim(this.taskForm.refNo);
                taskParam.taskSource = this.taskForm.taskSource;
                taskParam.taskStatus = this.taskForm.taskStatus;
//                taskParam.param = ofcTaskInterfaceLogVo;
                CommonClient.post(sys.rootPath + "/ofc/interface/queryTaskLog", taskParam, function (result) {
                    if (result == undefined || result == null || result.code == 500) {
                        vueObj.$message.error('查询任务日志发生错误！');
                    } else if (result.code == 200) {
                        if (result.result.list.length > 0) {
                            $.each(result.result.list, function (index, OfcTaskInterfaceLog) {
                                var taskLog = {};
                                taskLog.id = OfcTaskInterfaceLog.id;
                                taskLog.taskType = OfcTaskInterfaceLog.taskType;
                                taskLog.refNo = OfcTaskInterfaceLog.refNo;
                                taskLog.taskSource = OfcTaskInterfaceLog.taskSource;
                                taskLog.taskExeCount = OfcTaskInterfaceLog.taskExeCount;
                                taskLog.exeInstanceIp = OfcTaskInterfaceLog.exeInstanceIp;
                                taskLog.taskStatus = OfcTaskInterfaceLog.taskStatus;
                                taskLog.creationTime = OfcTaskInterfaceLog.creationTime;
                                taskLog.exeTime = OfcTaskInterfaceLog.exeTime;
                                vueObj.taskData.push(taskLog);
                            });
                            vueObj.pageInfo.total = result.result.total;
                        } else {
                            console.info("暂时未查询到任务日志信息！");
                        }
                    }
                });
            },
            resetForm: function () {
                this.taskForm.dateRange = [new Date(new Date().setHours(0, 0, 0, 0)), new Date(new Date().setHours(23, 59, 59, 59))];
                this.taskForm.taskType = '';
                this.taskForm.refNo = '';
                this.taskForm.taskSource = '';
                this.taskForm.taskStatus = '';
            },
            showTaskLogDetail: function (index, row) {
                var url = "/ofc/interface/showTaskLogDetail?id=" + row.id;
                var html = window.location.href;
                var index = html.indexOf("/index#");
                window.open(html.substring(0, index) + "/index#" + url);
            },
            delTaskLog: function (index, row) {
                var vueObj = this;
                CommonClient.post(sys.rootPath + "/ofc/interface/delTaskLogById/" + row.id, null, function (result) {
                    if (result == undefined || result == null) {
                        vueObj.$message.error('删除任务日志发生错误！');
                    } else if (result.code == 200) {
                        vueObj.doSearch();
                    } else if (result.code == 500) {
                        vueObj.$message.error('删除任务日志发生失败！');
                    }
                });
            },
            resendTaskLog0: function (index, row) {
                var vueObj = this;
                CommonClient.post(sys.rootPath + "/ofc/interface/resendTaskLogById/" + row.id, null, function (result) {
                    if (result == undefined || result == null) {
                        vueObj.$message.error('重发任务日志发生错误！');
                    } else if (result.code == 200) {
                        vueObj.doSearch();
                    } else if (result.code == 500) {
                        vueObj.$message.error('重发任务日志发生失败！');
                    }
                });
            }
        }
    });
</script>