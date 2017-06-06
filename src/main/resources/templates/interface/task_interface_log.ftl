<title>任务日志</title>
<!--suppress ALL -->
<head>
    <style lang="css">
        .ofc-block {
            margin: 20px 0;
        }

        .el-dialog {
            top: 50% !important;
            margin-top: -300px;
            margin-bottom: 0 !important;
        }

        .el-dialog__body {
            padding: 10px 20px 30px;
        }

        .el-dialog__footer {
            padding: 15px 20px;
        }

        .el-dialog--small .el-table {
            min-height: 350px;
        }

        .el-dialog--small .el-table tr {
            cursor: pointer;
        }
    </style>
</head>
<body>
<div id="app">
    <div>
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
                    <el-select v-model="taskForm.taskType" placeholder="请选择">
                        <el-option
                                v-for="item in taskTypeOptions"
                                :label="item.label"
                                :value="item.value">
                        </el-option>
                    </el-select>
                </el-form-item>
            </div>
            <div class="xe-block">
                <el-form-item label="任务来源" class="xe-col-3">
                    <el-select v-model="taskForm.taskSource" placeholder="请选择">
                        <el-option
                                v-for="item in taskSourceOptions"
                                :label="item.label"
                                :value="item.value">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="任务状态" class="xe-col-3">
                    <el-select v-model="taskForm.taskStatus" placeholder="请选择">
                        <el-option
                                v-for="item in taskStatusOptions"
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
        <div class="ofc-block">
            <el-table :data="taskData" border @selection-change="handleSelectionChange" style="width: 100%">
                <el-table-column type="index" label="序号"></el-table-column>
                <el-table-column property="taskType" label="任务类型"></el-table-column>
                <el-table-column property="refNo" label="业务单号"></el-table-column>
                <el-table-column property="taskSource" label="任务来源"></el-table-column>
                <el-table-column property="taskExeCount" label="执行次数"></el-table-column>
                <el-table-column property="exeInstanceIp" label="执行IP"></el-table-column>
                <el-table-column property="taskStatus" label="任务状态"></el-table-column>
                <el-table-column property="creationTime" label="创建时间"></el-table-column>
                <el-table-column property="exeTime" label="执行时间"></el-table-column>
                <el-table-column fixed="right" label="操作" width="100">
                    <template scope="scope">
                        <el-button @click="editTaskLog" type="text" size="small">查看</el-button>
                        <el-button @click="delTaskLog" type="text" size="small">删除</el-button>
                        <el-button @click="resendTaskLog" type="text" size="small">重新发送</el-button>
                    </template>
                </el-table-column>
            </el-table>
            <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentPage"
                           :current-page="pageInfo.pageNum" :page-sizes="pageInfo.xePageSizes"
                           :page-size="pageInfo.pageSize"
                           layout="total, sizes, prev, pager, next" :total="pageInfo.total">
            </el-pagination>
        </div>
    </div>
</div>
</body>
<script>
    new Vue({
        el: '#app',
        data: {
            taskTypeOptions: [],
            taskSourceOptions: [],
            taskStatusOptions: [],
            taskForm: {
                dateRange: [new Date(new Date().setHours(0, 0, 0, 0)), new Date(new Date().setHours(23, 59, 59, 59))],
                beginDate: new Date(new Date().setHours(0, 0, 0, 0)),
                endDate: new Date(new Date().setHours(23, 59, 59, 59)),
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
        created: function () {

        },
        methods: {
            handleSelectionChange: function (val) {

            },
            handleSizeChange: function (val) {
                this.pageInfo.pageSize = val;
            },
            handleCurrentPage: function (val) {
                this.pageInfo.pageNum = val;
            },
            queryTaskLog: function () {

            },
            doSearch: function () {
                var param = {};
                param.pageNum = this.pageInfo.pageNum;
                param.pageSize = this.pageInfo.pageSize;
                CommonClient.syncpost(sys.rootPath + "/ofc/interface/queryTaskLog", {}, function (result) {
                    debugger;
                    if (result == undefined || result == null || result.code == 500) {
                        //layer.msg("当前用户下没有仓库信息！");
                        console.info("查询任务日志发生错误！");
                    } else if (result.code == 200) {
                        if (result.result.length > 0) {
                            $.each(result.result, function (index, OfcTaskInterfaceLog) {
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
                        } else {
                            //layer.msg("当前用户下没有仓库信息！");
                            console.info("当前用户下没有仓库信息！");
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
            }
        }
    });
</script>