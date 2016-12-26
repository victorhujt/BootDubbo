/** 
 * Project Name:xescm_uam 
 * File Name:AuthInterceptor.java 
 * Package Name:com.xescm.uam.config 
 * Date:2016年10月20日下午2:38:35 
 * Copyright (c) 2016, http://www.hnxianyi.com All Rights Reserved. 
 * 
*/  
  
package com.xescm.ofc.web.interceptor;

import com.xescm.base.model.dto.auth.AuthResDto;
import com.xescm.core.constant.UamConstant;
import com.xescm.core.utils.ThreadLocalMap;
import com.xescm.ofc.web.jwt.TokenUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>Title:	  AuthInterceptor <br/> </p>
 * <p>Description 权限拦截器 <br/> </p>
 * <p>Company:    http://www.hnxianyi.com  <br/> </p>
 * @Author        <a href="liu_zhaoming@sina.cn"/>刘兆明</a>  <br/>
 * @CreateDate    2016年10月20日 下午2:38:35 <br/>
 */
@Service
public class AuthViewInterceptor implements HandlerInterceptor {
	
	private static Logger logger = LoggerFactory.getLogger(AuthViewInterceptor.class);

	@Autowired
	private TokenUtils tokenUtils;

	private String env;

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object arg2, Exception ex)
			throws Exception {
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object arg2, ModelAndView mv)
			throws Exception {
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		
		//登录认证跳过
        if(request.getRequestURL().toString().contains("/auth")){
            return true;
        }

        //如果设置env=debug,则跳过Jwt认证
        if(this.getEnv()!=null && this.getEnv().equals("debug")){
            return true;
        }
        //试调用不需要走认证
        if (request.getMethod().toUpperCase().equals("OPTIONS")) {
            return true;
        }

        final String authHeader = request.getHeader("Authorization");
		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			logger.error("==> 解析token失败,权限验证失败!");
			String errorMsg = "{\"code\":-2,\"description\" :\"Missing or invalid Authorization header\"}";
			return tokenUtils.handleException((HttpServletRequest) request, response, errorMsg);
		}

		final String token = authHeader.substring(7);

//        if (PublicUtil.isNotEmpty(token)) {
//            String sessionToken = (String) request.getSession().getAttribute("token");
//            if(PublicUtil.isEmpty(sessionToken)){
//                String secToken = RandomUtil.createComplexCode(16);
//                logger.error("从Session获取token失败, 跳转到登录页面, 重新获取加密token={}", secToken);
//                request.getSession(true).setAttribute(UserConstant.SEC_TOKEN, secToken);
//                response.sendRedirect("/errorAuth");
//            }
//        }

		AuthResDto authResDtoByToken = null;
		try {
			ThreadLocalMap.put(UamConstant.JWT_VIEW_TOKEN, token);
			authResDtoByToken = tokenUtils.getAuthResDtoByToken(response, token);
			ThreadLocalMap.put(UamConstant.TOKEN_USER, authResDtoByToken.getLoginName());
			ThreadLocalMap.put(UamConstant.TOKEN_AUTH_DTO, authResDtoByToken);
		} catch (Exception e) {
			logger.error("==> JWT验签, 出现异常={}", e.getMessage(), e);
			String errorMsg = "{\"code\":-3 ,\"message\" :\"Invalid token\"}";
			return tokenUtils.handleException((HttpServletRequest) request, response, errorMsg);
		}

        return true;
		
	}

	public String getEnv() {
        return env;
    }

    public void setEnv(String env) {
        this.env = env;
    }
    
}
  