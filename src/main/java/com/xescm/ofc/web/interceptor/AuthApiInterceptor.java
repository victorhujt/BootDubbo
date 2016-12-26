package com.xescm.ofc.web.interceptor;

import com.xescm.ofc.service.TokenService;
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
public class AuthApiInterceptor implements HandlerInterceptor {

	private static Logger logger = LoggerFactory.getLogger(AuthApiInterceptor.class);

    @Autowired
    private TokenService tokenService;

	private String env;

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object arg2, Exception arg3)
			throws Exception {
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object arg2, ModelAndView mv)
			throws Exception {
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws Exception {

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
            String errorMsg = "{\"code\":-2, \"message\" :\"Missing or invalid Authorization header\"}";
            tokenService.handleException((HttpServletRequest) request, response, errorMsg);
            return false;
        }

        final String token = authHeader.substring(7);
        try {
            //校验API对应的Token是否正常
            tokenService.checkApiToken(token);
        } catch (Exception e) {
            String errorMsg = "{\"code\":-3 ,\"message\" :\"Invalid API token\"}";
            tokenService.handleException((HttpServletRequest) request, response, errorMsg);
            logger.error("==> API验签出现异常={}", e.getMessage(), e);
            return false;
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
