/**
 * 通用数据客户端
 * @author suihonghua
 * @returns {CommonClient}
 */
function CommonClient(){}

CommonClient.prototype = new Object();
//上下文路径
CommonClient.contextPath = "";



/**
 * Ajax[POST] 同步数据请求
 * @author 王灿佩
 * @param url	[String] 请求url
 * @param param	[Object] 参数
 * @param successFunction [Function] 成功回调函数
 */
CommonClient.syncpost = function(url,param,successFunction){
	CommonClient.ajax("POST", url, param, successFunction,false);
};


/**
 * Ajax[POST] 异步数据请求
 * @author suihonghua
 * @param url	[String] 请求url
 * @param param	[Object] 参数
 * @param successFunction [Function] 成功回调函数
 */
CommonClient.post = function(url,param,successFunction){
	CommonClient.ajax("POST", url, param, successFunction,true);
};

/**
 * Ajax[GET] 异步数据请求
 * @author suihonghua
 * @param url	[String] 请求url
 * @param param	[Object] 参数
 * @param successFunction [Function] 成功回调函数
 */
CommonClient.get = function(url,param,successFunction, isUnMusk){
	CommonClient.ajax("GET", url, param, successFunction,true,isUnMusk);
};

CommonClient.getNoBeforeEnd = function(url,param,successFunction){
	CommonClient.ajaxNoBeforeEnd("GET", url, param, successFunction);
};

/**
 * Ajax 异步数据请求
 * @author suihonghua
 * @param type	[String] e.g.:"POST" or "GET"
 * @param url	[String] 请求url
 * @param param	[Object] 参数
 * @param successFunction [Function] 成功回调函数
 * @param flag 同步标识
 */
CommonClient.ajax = function(type,url,param,successFunction,flag, isUnMusk){
	if(param == null){
		param = {};
	}
	param.t_i_m_e = new Date().getTime();//防止请求使用缓存数据
	jQuery.ajax({
		type: type,
		url: CommonClient.contextPath + url,
		data: param,
		cache: false,
		async:flag,
		contentType: "application/x-www-form-urlencoded; charset=utf-8",
		beforeSend: function(jqXHR, settings){
			if (!isUnMusk) {
				$.blockUI({ message:"<span class='pl20 icon-loading'>正在处理,请稍后...</span>",
		    		css: {
			    		border: 'none',
			            padding: '15px',
			            backgroundColor: '#fff',
			            '-webkit-border-radius': '10px',
			            '-moz-border-radius': '10px',
			            opacity: .5,
			            color: '#000'
		    		}
		    	});
			}
		},
		success: function(data){
			successFunction(data);
		},
		error: function(jqXHR, textStatus, errorThrown){
//			if(jqXHR.status > 0){
//				alert("Error:status["+jqXHR.status+"],statusText["+ jqXHR.statusText +"]");
//			}
			layer.msg('服务器未响应,请稍后再试', {
                icon : 3
            });
		},
		complete: function(jqXHR, textStatus){
			$.unblockUI();
		}
	});
};

/**
 * Ajax 异步数据请求
 * @author suihonghua
 * @param type	[String] e.g.:"POST" or "GET"
 * @param url	[String] 请求url
 * @param param	[Object] 参数
 * @param successFunction [Function] 成功回调函数
 */
CommonClient.ajaxNoBeforeEnd = function(type,url,param,successFunction){
	if(param == null){
		param = {};
	}
	param.t_i_m_e = new Date().getTime();//防止请求使用缓存数据
	jQuery.ajax({
		type: type,
		url: CommonClient.contextPath + url,
		data: param,
		cache: false,
		contentType: "application/x-www-form-urlencoded; charset=utf-8",
		success: function(data){
			successFunction(data);
		},
		error: function(jqXHR, textStatus, errorThrown){
			if(jqXHR.status > 0){
				alert("Error:status["+jqXHR.status+"],statusText["+ jqXHR.statusText +"]");
			}
		},
		complete: function(jqXHR, textStatus){
			$.unblockUI();
		}
	});
};


CommonClient.postJson=function(url,json,successFunction,errorFunction){
	$.ajax({
		url:url,
		type:'POST',
		data:json,
		dataType:'json',
		contentType:'application/json',
		success:function(result){
			successFunction(result);
		},
		error:function(xhr, error, exception){
			errorFunction(error,exception);
		}
	});
};