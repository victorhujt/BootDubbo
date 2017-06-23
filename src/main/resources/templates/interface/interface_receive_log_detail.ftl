<!DOCTYPE html>
<html>
<head>
    <title>接收日志详情</title>
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
            接收日志信息
        </div>
        <el-form label-width="100px">
            <div class="xe-block">
                <el-form-item label="业务类型" class="xe-col-3">
                    <el-input v-model="logBusinessType" :readOnly="true"></el-input>
                </el-form-item>
                <el-form-item label="业务单号" class="xe-col-3">
                    <el-input v-model="refNo" :readOnly="true"></el-input>
                </el-form-item>
                <el-form-item label="发送系统" class="xe-col-3">
                    <el-input v-model="logFromSys" :readOnly="true"></el-input>
                </el-form-item>
            </div>
            <div class="xe-block">
                <el-form-item label="接收系统" class="xe-col-3">
                    <el-input v-model="logToSys" :readOnly="true"></el-input>
                </el-form-item>
                <el-form-item label="日志类型" class="xe-col-3">
                    <el-input v-model="logType" :readOnly="true">
                    </el-input>
                </el-form-item>
                <el-form-item label="执行次数" class="xe-col-3">
                    <el-input v-model="processCount" :readOnly="true">
                    </el-input>
                </el-form-item>
            </div>
            <div class="xe-block">
                <el-form-item label="执行状态" class="xe-col-3">
                    <el-input v-model="logStatus" :readOnly="true">
                    </el-input>
                </el-form-item>
                <el-form-item label="创建时间" class="xe-col-3">
                    <el-input v-model="creationTime" :readOnly="true">
                    </el-input>
                </el-form-item>
                <el-form-item label="执行时间" class="xe-col-3">
                    <el-input v-model="processTime" :readOnly="true">
                    </el-input>
                </el-form-item>
            </div>
            <div class="xe-block">
                <el-form-item label="日志报文" style="width: 96%">
                    <el-input type="textarea" v-model="logData" :readOnly="true" :autosize="{ minRows: 5, maxRows: 15 }"
                              style="width: 100%;">
                </el-form-item>
            </div>
            <div class="xe-block">
                <el-form-item label="执行结果" style="width: 96%">
                    <el-input type="textarea" v-model="processResult" :readOnly="true" :autosize="{ minRows: 5, maxRows: 10 }"
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
                logBusinessType: '',
                refNo: '',
                logFromSys: '',
                logToSys: '',
                logType: '',
                processCount: '',
                logStatus: '',
                creationTime: '',
                processTime: '',
                logData: '',
                processResult: ''
            };
        },
        beforeMount: function () {
            var vueObj = this;
            var url = window.location.href;
            if (url.indexOf("?") != -1) {
                var param = url.split("?")[1].split("=");
                if (param[0] == "id") {
                    var id = param[1];
                    CommonClient.post(sys.rootPath + "/ofc/interface/queryReceiveLogDetailById/" + id, null, function (result) {
                        if (result == undefined || result == null) {
                            vueObj.$message.error('查询接收日志信息发生错误！');
                        } else if (result.code == 200) {
                            if (result.result != null) {
                                var taskLog = result.result;
                                vueObj.id = taskLog.id;
                                vueObj.logBusinessType = taskLog.logBusinessType;
                                vueObj.refNo = taskLog.refNo;
                                vueObj.logFromSys = taskLog.logFromSys;
                                vueObj.logToSys = taskLog.logToSys;
                                vueObj.logType = taskLog.logType;
                                vueObj.processCount = taskLog.processCount;
                                vueObj.logStatus = taskLog.logStatus;
                                vueObj.creationTime = taskLog.creationTime;
                                vueObj.processTime = taskLog.processTime;
                                vueObj.logData = vueObj.formatJson(taskLog.logData);
                                vueObj.processResult = taskLog.processResult;
                            } else {
                                console.info("暂时未查询到接收日志信息！");
                            }
                        }
                    });
                }
            }
        },
        methods: {
            goBack: function () {
                var newurl = "/ofc/interface/interfaceReceiveLog";
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