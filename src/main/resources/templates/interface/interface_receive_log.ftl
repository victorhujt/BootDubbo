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

        .el-input__icon + .el-input__inner {
            padding-right: 30px;
        }
    </style>
</head>
<body>
<div id="app">
    <div class="xe-pageHeader">
        任务日志筛选
    </div>
    <el-form label-width="100px">
        <div class="xe-block">
            <el-form-item label="创建时间" class="xe-col-3">
                <el-date-picker
                        style="width:114px;"
                        v-model="beginDate"
                        type="datetime"
                        :clearable="false"
                        :editable="false"
                        placeholder="选择起始时间">
                </el-date-picker>
                <label for="" style="width:15px;">至</label>
                <el-date-picker
                        style="width:114px;"
                        v-model="endDate"
                        type="datetime"
                        :clearable="false"
                        :editable="false"
                        placeholder="选择结束时间">
                </el-date-picker>
            </el-form-item>
            <el-form-item label="业务单号" class="xe-col-3">
                <el-input v-model="orderCode" placeholder="请输入内容"></el-input>
            </el-form-item>
            <el-form-item label="任务类型" class="xe-col-3">
                <el-input v-model="orderCode" placeholder="请输入内容"></el-input>
            </el-form-item>
        </div>
        <div class="xe-block">
            <el-form-item label="任务来源" class="xe-col-3">
                <el-input v-model="orderCode" placeholder="请输入内容"></el-input>
            </el-form-item>
            <el-form-item label="任务状态" class="xe-col-3">
                <el-select v-model="wareHouseName" placeholder="请选择">
                    <el-option
                            v-for="item in wareHouseOptions"
                            :label="item.label"
                            :value="item.value">
                    </el-option>
                </el-select>
            </el-form-item>
        </div>
    </el-form>
    <div class="xe-pageHeader">
        任务日志列表
    </div>
    <div class="ofc-block">
        <el-table :data="orderData" border @selection-change="handleSelectionChange" style="width: 100%">
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
                       :current-page="taskCurrentPage" :page-sizes="pageSizes" :page-size="pageSize"
                       layout="total, sizes, prev, pager, next" :total="totalTask">
        </el-pagination>
    </div>
</div>
</body>
<script>
    new Vue({
        el: '#app',
        data: {
            beginDate: new Date().getTime() - 3600 * 1000 * 24 * 2,
            endDate: new Date(),
        },
        beforeMount: function () {

        },
        methods: {}
    });
</script>