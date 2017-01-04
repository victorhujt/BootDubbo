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
import com.xescm.base.model.wrap.Wrapper;
import com.xescm.core.constant.UamConstant;
import com.xescm.core.exception.BusinessException;
import com.xescm.core.utils.PublicUtil;
import com.xescm.core.utils.ThreadLocalMap;
import com.xescm.uam.model.dto.auth.AuthTokenDto;
import com.xescm.uam.provider.UamAuthEdasService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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

	@Resource
	private UamAuthEdasService uamAuthEdasService;

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
			return handleException((HttpServletRequest) request, response, errorMsg);
		}

		final String token = authHeader.substring(7);

		AuthTokenDto authTokenDto;
		AuthResDto authResDto = null;
		try {
			ThreadLocalMap.put(UamConstant.JWT_VIEW_TOKEN, token);
			Wrapper<AuthTokenDto> authTokenDtoWrapper = uamAuthEdasService.getAuthTokenDtoByToken(token);
			if(PublicUtil.isNotEmpty(authTokenDtoWrapper) && authTokenDtoWrapper.getCode() == Wrapper.SUCCESS_CODE){
				authTokenDto = authTokenDtoWrapper.getResult();
				authResDto = authTokenDto.getAuthResDto();
				String newToken = authTokenDto.getNewToken();
				if(PublicUtil.isNotEmpty(newToken)){
					response.setHeader("newtoken", newToken);
				}
			}

			if(PublicUtil.isEmpty(authResDto)){
				throw new BusinessException("验签失败,DTO为空");
			}

			ThreadLocalMap.put(UamConstant.TOKEN_USER, authResDto.getLoginName());
			ThreadLocalMap.put(UamConstant.TOKEN_AUTH_DTO, authResDto);
		} catch (Exception e) {
			logger.error("==> JWT验签, 出现异常={}", e.getMessage(), e);
			String errorMsg = "{\"code\":-3 ,\"message\" :\"Invalid token\"}";
			return handleException(request, response, errorMsg);
		}

        return true;
		
	}

	public String getEnv() {
        return env;
    }

    public void setEnv(String env) {
        this.env = env;
    }

	private boolean handleException(HttpServletRequest req, ServletResponse res, String msg) throws IOException {
		if(req.getRequestURL().toString().contains("api")){
			//如果是API进来的处理,则直接返回JSON
			res.resetBuffer();
			res.setContentType("application/json");
			res.getWriter().write(msg);
			res.flushBuffer();
		}else{
			//TODO 其他请求默认为页面请求,跳转到error页面
			((HttpServletResponse)res).sendRedirect("/errorAuth");
		}
		return false;
	}
}
  