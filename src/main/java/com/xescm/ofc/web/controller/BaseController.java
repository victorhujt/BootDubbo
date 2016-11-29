/** 
 * Project Name:apollo-ui 
 * File Name:BaseController.java 
 * Package Name:com.liuzm.ui.controller.base 
 * Date:2016年4月28日上午10:39:20 
 * Copyright (c) 2016, http://www.liuzhaoming.com All Rights Reserved. 
 * 
*/  
  
package com.xescm.ofc.web.controller;

import com.xescm.ofc.config.RestConfig;
import com.xescm.ofc.constant.OrderConstConstant;
import com.xescm.uam.constant.UamConstant;
import com.xescm.uam.domain.dto.AuthResDto;
import com.xescm.uam.exception.BusinessException;
import com.xescm.uam.utils.PublicUtil;
import com.xescm.uam.utils.ThreadLocalMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;

import javax.annotation.Resource;

/**
 * <p>Title:	  BaseController <br/> </p>
 * <p>Description 基础Controller,所有控制层都要继承 <br/> </p>
 * <p>Company:    http://www.liuzhaoming.com  <br/> </p>
 * @Author        <a href="liu_zhaoming@sina.cn"/>刘兆明</a>  <br/>
 * @CreateDate    2016年4月28日 上午10:39:20 <br/>
 */

public class BaseController {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	@Resource
	RestConfig restConfig;

	public void setDefaultModel(Model model){
		model.addAttribute(OrderConstConstant.CROSS_DOMAIN_URL, restConfig.getUamUrl());
		model.addAttribute(OrderConstConstant.OFC_URL, restConfig.getOfcUrl());
		model.addAttribute(OrderConstConstant.CSC_URL, restConfig.getCscWebUrl());
	}
	protected AuthResDto getAuthResDtoByToken(){
		AuthResDto authResDto = (AuthResDto) ThreadLocalMap.get(UamConstant.TOKEN_AUTH_DTO);

		if(PublicUtil.isEmpty(authResDto)){
			throw new BusinessException("验证token失败");
		}

		return authResDto;

	}
}
  