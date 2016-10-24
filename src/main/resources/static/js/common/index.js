var xescm = {
    common : {
        /**
         *加载非菜单展示页面
         * @param nav 待加载的资源URL
         */
        loadPage : function(nav) {
            //加载页面
			nav = replaceSpecialChar(nav);
            $(".page-content-area").load(sys.rootPath + nav);
        },
        /**
         * 新增
         * @param {Object} nav  提交url
         */
        addModel : function(nav) {
            //加载新增页面
        	xescm.common.loadPage(nav);
        },
        /**
         * 编辑
         * @param {Object} nav  提交url
         */
        editModel : function(nav, id) {
			if (!id) {
				layer.msg('请选择一条记录', {
					skin: 'layui-layer-molv',
					icon : 0
				});
			}
			var url = nav + "/" + id;
			xescm.common.loadPage(url);
		},
        /**
		 * 删除
		 * 
		 * @param {Object}
		 *            nav 提交url
		 * @param callback
		 *            主函数执行完毕后调用的回调函数名称
		 */
        delModel : function(nav, param, successFunction) {
			layer.confirm('确认删除吗？', {
				skin : 'layui-layer-molv',
				icon : 3,
				title : '删除提示'
			}, function(index, layero) {
				CommonClient.post(sys.rootPath + nav, param, function(result) {
					if (result == undefined || result == null) {
						layer.msg("HTTP请求无数据返回", {
							icon : 1
						});
					} else if (result.code == "200") {
						layer.msg(result.message, {
							skin : 'layui-layer-molv',
							icon : 1
						});
						if(successFunction){
							successFunction();
						}
					} else {
						layer.msg(result.message, {
							skin : 'layui-layer-molv',
							icon : 5
						});
					}
				});

			});
		},
		submit : function(submitUrl,message, param, successFunction) {
			if(!message){
				message='您确认吗?'
			}
			layer.confirm(message, {
				skin : 'layui-layer-molv',
				icon : 3,
				title : '确认操作'
			}, function(){
					CommonClient.post(sys.rootPath + submitUrl, param, function(result) {
						if (result == undefined || result == null) {
							layer.msg("HTTP请求无数据返回", {
								icon : 1
							});
						} else if (result.code == "200") {
							layer.msg(result.message, {
								skin : 'layui-layer-molv',
								icon : 1
							});
							if(successFunction){
								successFunction();
							}
						} else {
							layer.msg(result.message, {
								skin : 'layui-layer-molv',
								icon : 5
							});
						}
					});
				}, function(index){
					layer.close(index);
				});
			return;
		},
        /**
		 * 返回按钮
		 * 
		 * @param {Object}
		 *            nav 提交url
		 */
        goBack : function(nav) {
        	xescm.common.loadPage(nav);
        },
        /**
		 * 提交表单 适用场景：form表单的提交，主要用在用户、角色、资源等表单的添加、修改等
		 * 
		 * @param {Object}
		 *            commitUrl 表单提交地址
		 * @param {Object}
		 *            listUrl 表单提交成功后转向的列表页地址
		 */
        commit : function(formId, commitUrl, successFunction) {
            //组装表单数据
            var index;
            var data = $("#" + formId).serialize();
            $.ajax({
                type : "post",
                url : sys.rootPath + commitUrl,
                data : data,
                dataType : "json",
                beforeSend : function() {
                    index = layer.load();
                },
                success : function(data) {
                    layer.close(index);
                    if (data.code == '200') {
                        layer.msg(data.message, {
                            icon : 1
                        });
                        
                        if(successFunction){
                        	successFunction();
                        }
                    } else {
                        layer.msg(data.message, {
                            icon : 5
                        });
                    }
                },
                error : function(data, errorMsg) {
                    layer.close(index);
                    layer.msg(data.responseText, {
                        icon : 2
                    });
                }
            });
        },
    },
    form : {
        user : {},
        userInfo : {},
        role : {},
        resource : {
            initSourceType : function() {
                $('#type').select2({
                    language : "zh-CN",
                    minimumResultsForSearch : Infinity
                });
            },
            initIcon : function() {
                $("#icon").bind('focus', function(event) {
//                	xescm.common.loadPage("/sys/tools/icon");
                    var iconLayer = layer.open({
                        type : 2,
                        scrollbar : true,
                        content : sys.rootPath + '/sys/tools/toMaintainIconPage',
                        area : 'auto',
                        maxmin : true,
                        shift : 4,
                        area : [ '800px', '600px' ],
                        title : '<i class="fa fa-cogs"></i>&nbsp;选择图标'
                    });
                });
                $("#iconShow").bind('click', function(event) {
                    $("#icon").val('');
                    $(this).removeClass();
                });
                $('[data-rel=tooltip]').tooltip();
            },
            initType : function() {
                $("#parentId").change(function() {
                    var parentId = $.trim($(this).children('option:selected').val());
                    if (parentId == null || parentId == '') {
                        //$("#iconDiv").show();
                        //$("#sourceUrlDiv").hide();
                    } else {
                        //$("#iconDiv").hide();
                        //$("#sourceUrlDiv").show();
                    }
                });
            },
            validateResourceForm : function() {},
            authorize : {}
        }
    }
};

function alert(msg) {
	layer.alert(msg, {
		skin : 'layui-layer-molv',
		icon : 5,
		time : 0
	});
}


function submitAndQuery(url){
	CommonClient.post(sys.rootPath + url, null, function(result) {
		if (result == undefined || result == null) {
			layer.msg("HTTP请求无数据返回", {
				icon : 1
			});
		} else if (result.code=="200") {
			layer.msg(result.message, {
				skin : 'layui-layer-molv',
				icon : 1
			});
			queryData();
		} else {
			layer.msg(result.message, {
				skin : 'layui-layer-molv',
				icon : 5
			});
		}
	});
}