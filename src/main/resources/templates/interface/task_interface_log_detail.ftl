<!DOCTYPE html>
<html>
<head>
    <title>出库单详情</title>
    <style lang="css">
        .borderNone .el-input__inner {
            border: none;
        }

        .el-table__body-wrapper {
            overflow-y: auto;
            overflow-x: hidden;
        }

        .weight {
            position: absolute;
            osition: relative;
            left: 260px;
            display: block;
            top: 3px;
        }
    </style>
</head>
<body>
<div id="app">
    <div class="list-mian-01">
        <div class="xe-pageHeader">
            任务日志信息
        </div>
        <el-form label-width="100px">
            <div class="xe-block">
                <el-form-item label="任务类型" class="xe-col-3">
                    <el-input v-model="taskType" :readOnly="true"></el-input>
                </el-form-item>
                <el-form-item label="业务单号" class="xe-col-3">
                    <el-input v-model="refNo" :readOnly="true"></el-input>
                </el-form-item>
                <el-form-item label="任务来源" class="xe-col-3">
                    <el-input v-model="taskSource" :readOnly="true"></el-input>
                </el-form-item>
            </div>
            <div class="xe-block">
                <el-form-item label="执行次数" class="xe-col-3">
                    <el-input v-model="taskExeCount" :readOnly="true"></el-input>
                </el-form-item>
                <el-form-item label="执行IP" class="xe-col-3">
                    <el-input v-model="exeInstanceIp" :readOnly="true">
                    </el-input>
                </el-form-item>
                <el-form-item label="任务状态" class="xe-col-3">
                    <el-input v-model="taskStatus" :readOnly="true">
                    </el-input>
                </el-form-item>
            </div>
            <div class="xe-block">
                <el-form-item label="创建时间" class="xe-col-3">
                    <el-input v-model="creationTime" :readOnly="true">
                    </el-input>
                </el-form-item>
                <el-form-item label="执行时间" class="xe-col-3">
                    <el-input v-model="exeTime" :readOnly="true">
                    </el-input>
                </el-form-item>
            </div>
            <div class="xe-block">
                <el-form-item label="任务日志" style="width: 96%">
                    <el-input type="textarea" v-model="taskData" :readOnly="true" :autosize="{ maxRows: 25 }"
                              style="width: 100%;">
                </el-form-item>
            </div>
            <div class="block">
                <el-button type="primary" @click="goBack">返回</el-button>
            </div>
        </el-form>
    </div>
</div>
</body>
<script>
    new Vue({
        el: '#app',
        data: function () {
            return {
                taskType: '',
                refNo: '',
                taskSource: '',
                taskExeCount: '',
                exeInstanceIp: '',
                taskStatus: '',
                creationTime: '',
                exeTime: '',
                taskData: ''
            };
        },
        beforeMount: function () {
            var vueObj = this;
            var url = window.location.href;
            if (url.indexOf("?") != -1) {
                var param = url.split("?")[1].split("=");
                if (param[0] == "id") {
                    var id = param[1];
                    CommonClient.post(sys.rootPath + "/ofc/interface/queryTaskLogDetailById/" + id, null, function (result) {
                        if (result == undefined || result == null) {
                            vueObj.$message.error('查询任务日志信息发生错误！');
                        } else if (result.code == 200) {
                            if (result.result != null) {
                                var taskLog = result.result;
                                vueObj.id = taskLog.id;
                                vueObj.taskType = taskLog.taskType;
                                vueObj.refNo = taskLog.refNo;
                                vueObj.taskSource = taskLog.taskSource;
                                vueObj.taskExeCount = taskLog.taskExeCount;
                                vueObj.exeInstanceIp = taskLog.exeInstanceIp;
                                vueObj.taskStatus = taskLog.taskStatus;
                                vueObj.creationTime = taskLog.creationTime;
                                vueObj.exeTime = taskLog.exeTime;
                                vueObj.taskData = vueObj.formatJson(taskLog.taskData);
                            } else {
                                console.info("暂时未查询到任务日志信息！");
                            }
                        }
                    });
                }
            }
        },
        methods: {
            goBack: function () {
                var newurl = "/ofc/interface/taskInterfaceLog";
                var html = window.location.href;
                var index = html.indexOf("/index#");
                window.open(html.substring(0, index) + "/index#" + newurl);
            },
            formatJson: function (json, options) {
                var reg = null, formatted = '', pad = 0, PADDING = '    '; // one can also use '\t' or a different number of spaces

                // optional settings
                options = options || {};
                // remove newline where '{' or '[' follows ':'
                options.newlineAfterColonIfBeforeBraceOrBracket = (options.newlineAfterColonIfBeforeBraceOrBracket === true) ? true : false;
                // use a space after a colon
                options.spaceAfterColon = (options.spaceAfterColon === false) ? false : true;

                // begin formatting...
                if (typeof json !== 'string') {
                    // make sure we start with the JSON as a string
                    json = JSON.stringify(json);
                } else {
                    // is already a string, so parse and re-stringify in order to remove extra whitespace
                    json = JSON.parse(json);
                    json = JSON.stringify(json);
                }

                // add newline before and after curly braces
                reg = /([\{\}])/g;
                json = json.replace(reg, '\r\n$1\r\n');

                // add newline before and after square brackets
                reg = /([\[\]])/g;
                json = json.replace(reg, '\r\n$1\r\n');

                // add newline after comma
                reg = /(\,)/g;
                json = json.replace(reg, '$1\r\n');

                // remove multiple newlines
                reg = /(\r\n\r\n)/g;
                json = json.replace(reg, '\r\n');

                // remove newlines before commas
                reg = /\r\n\,/g;
                json = json.replace(reg, ',');

                // optional formatting...
                if (!options.newlineAfterColonIfBeforeBraceOrBracket) {
                    reg = /\:\r\n\{/g;
                    json = json.replace(reg, ':{');
                    reg = /\:\r\n\[/g;
                    json = json.replace(reg, ':[');
                }
                if (options.spaceAfterColon) {
                    reg = /\:/g;
                    json = json.replace(reg, ':');
                }

                $.each(json.split('\r\n'), function (index, node) {
                    var i = 0, indent = 0, padding = '';

                    if (node.match(/\{$/) || node.match(/\[$/)) {
                        indent = 1;
                    } else if (node.match(/\}/) || node.match(/\]/)) {
                        if (pad !== 0) {
                            pad -= 1;
                        }
                    } else {
                        indent = 0;
                    }

                    for (i = 0; i < pad; i++) {
                        padding += PADDING;
                    }
                    formatted += padding + node + '\r\n';
                    pad += indent;
                });

                return formatted;
            }
        }
    });
</script>
</html>