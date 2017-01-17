<script>
    // 打开客户窗口
    function showCustomer() {
        layer.open({
            btn: ['选中', '关闭'],
            scrollbar: false,
            yes: function (adoptModalIndex) {
                var custEnterTag = "";
                $("#custListDivTbody").find("tr").each(function (index) {
                    var tdArr = $(this).children();
                    if (tdArr.eq(0).find("input").prop("checked")) {
                        custEnterTag = "1";
                        var customerCode = tdArr.eq(1).text();      // 客户编码
                        if ($("#customerCode").val() != customerCode) {
                            var type = tdArr.eq(2).text();          // 类型
                            var customerName = tdArr.eq(3).text();  // 客户名称
                            var channel = tdArr.eq(4).text();       // 渠道
                            var productType = tdArr.eq(5).text();   // 产品类别

                            $("#custName").val(customerName);
                            $("#customerCode").val(customerCode);

                            selectCustCallback();
                        }
                    }
                });
                if (custEnterTag == "") {
                    alert("请至少选择一行");
                } else {
                    layer.close(adoptModalIndex);
                    return false;
                }
            },
            type: 1,
            area: ['946px', '575px'],
            content: $('#custListDiv'), //这里content是一个DOM
            title: '选择客户',
            cancel: function (adoptModalIndex) {
                layer.close(adoptModalIndex);
                return false;
            }
        });
    }

    // 分页查询客户
    $("#custSelectFormBtn").click(function () {
        queryCustomerData(1);
    });

    // 分页查询客户列表
    function queryCustomerData(pageNum) {
        $("#custListDivTbody").html("");
        var custName = $("#custNameDiv").val();
        var param = {};
        param.pageNum = pageNum;
        param.pageSize = 10;
        param.custName = custName;
        CommonClient.post(sys.rootPath + "/ofc/distributing/queryCustomerByName", param, function (result) {
            if (result == undefined || result == null || result.result == null || result.result.size == 0 || result.result.list == null) {
                $("#pageBarDiv").hide();
                layer.msg("暂时未查询到客户信息！！");
            } else if (result.code == 200) {
                $("#pageBarDiv").show();
                loadCustomer(result);
                laypage({
                    cont: $("#pageBarDiv"), // 容器。值支持id名、原生dom对象，jquery对象,
                    pages: result.result.pages, // 总页数
                    skip: true, // 是否开启跳页
                    skin: "molv",
                    groups: 3, // 连续显示分页数
                    curr: result.result.pageNum, // 当前页
                    jump: function (obj, first) { // 触发分页后的回调
                        if (!first) { // 点击跳页触发函数自身，并传递当前页：obj.curr
                            queryCustomerData(obj.curr);
                        }
                    }
                });
                $(".total").html(result.result.total + '&nbsp;条&nbsp;');
            } else if (result.code == 403) {
                alert("没有权限")
            } else {
                $("#custListDivTbody").html("");
            }
        }, "json");
    }

    // 加载客户列表
    function loadCustomer(data) {
        if ((data == null || data == '' || data == undefined) || (data.result.list.length < 1)) {
            $("#custListDivTbody").html("");
            return;
        }
        var custList = "";
        $.each(data.result.list, function (index, cscCustomerVo) {
            var channel = cscCustomerVo.channel;
            if (null == channel) {
                channel = "";
            }
            custList = custList + "<tr role='row' class='odd' onclick='chosenTr(this)' >";
            custList = custList + "<td class='center'> " + "<label class='pos-rel'>" + "<input name='cust' type='radio' class='ace'>" + "<span class='lbl'></span>" + "</label>" + "</td>";
            custList = custList + "<td>" + cscCustomerVo.customerCode + "</td>";
            var custType = StringUtil.nullToEmpty(cscCustomerVo.type);
            if (custType == '1') {
                custList = custList + "<td>公司</td>";
            } else if (custType == '2') {
                custList = custList + "<td>个人</td>";
            } else {
                custList = custList + "<td>" + custType + "</td>";
            }
            custList = custList + "<td>" + cscCustomerVo.customerName + "</td>";
            custList = custList + "<td>" + channel + "</td>";
            custList = custList + "<td>" + cscCustomerVo.productType + "</td>";
            custList = custList + "</tr>";
            $("#custListDivTbody").html(custList);
        });
    }

</script>
<div class="adoptModal" id="custListDiv" style="display: none;">
    <div class="modal-body">
        <div class="bootbox-body">
            <form id="consignorSelConditionForm" class="form-horizontal" role="form">
                <div class="form-group">
                    <label class="control-label col-xs-1 no-padding-right" for="name" style="margin-top:0;">名称</label>
                    <div class="col-width-220 padding-15 y-float">
                        <div class="clearfix">
                            <input id="custNameDiv" name="cscContactCompanyDto.contactCompanyName" type="text"
                                   style="color: black" class="form-control input-sm" placeholder=""
                                   aria-controls="dynamic-table">
                        </div>
                    </div>

                    <div class="col-xs-3 y-float">
                        <div class="clearfix">
                            <span id="custSelectFormBtn" class="btn btn-white btn-info btn-bold btn-inatervl">筛选</span>
                        </div>
                    </div>
                </div>
            </form>
            <form class="bootbox-form">
                <table id="dynamic-table" class="table table-striped table-bordered table-hover dataTable no-footer"
                       role="grid" aria-describedby="dynamic-table_info">
                    <thead>
                    <tr role="row">
                        <th class="center sorting_disabled" rowspan="1" colspan="1" aria-label="">
                            <label class="pos-rel">
                                选择
                                <span class="lbl"></span>
                            </label>
                        </th>
                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1"
                            aria-label="Domain: activate to sort column ascending">客户编码
                        </th>
                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1"
                            aria-label="Price: activate to sort column ascending">类型
                        </th>
                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1"
                            aria-label="Clicks: activate to sort column ascending">公司名称
                        </th>
                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1"
                            aria-label="Clicks: activate to sort column ascending">渠道
                        </th>
                        <th class="" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1"
                            aria-label="Clicks: activate to sort column ascending">产品类别
                        </th>
                    </thead>
                    <tbody id="custListDivTbody"></tbody>
                </table>
                <div class="row">
                    <div id="pageBarDiv" style="float: right;padding-top: 0px;margin-top: 20px;">
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>