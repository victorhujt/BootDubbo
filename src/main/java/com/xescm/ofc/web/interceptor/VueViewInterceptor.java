package com.xescm.ofc.web.interceptor;

import com.google.common.collect.Maps;
import com.xescm.base.model.dto.auth.AuthResDto;
import com.xescm.base.model.wrap.Wrapper;
import com.xescm.core.constant.UamConstant;
import com.xescm.core.exception.BusinessException;
import com.xescm.core.utils.JacksonUtil;
import com.xescm.core.utils.PublicUtil;
import com.xescm.core.utils.ThreadLocalMap;
import com.xescm.ofc.config.RestConfig;
import com.xescm.uam.model.dto.auth.AuthTokenDto;
import com.xescm.uam.provider.UamAuthEdasService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * <p>Title:	  AuthInterceptor <br/> </p>
 * <p>Description 权限拦截器 <br/> </p>
 * <p>Company:    http://www.hnxianyi.com  <br/> </p>
 *
 * @Author <a href="liu_zhaoming@sina.cn"/>刘兆明</a>  <br/>
 * @CreateDate 2016年10月20日 下午2:38:35 <br/>
 */
@Service
public class VueViewInterceptor implements HandlerInterceptor {

    private static Logger logger = LoggerFactory.getLogger(VueViewInterceptor.class);

    @Resource
    private UamAuthEdasService uamAuthEdasService;

    private String env;

    @Value("${xescm.cookie.passToken}")
    private String passTokenKey;
    @Resource
    private RestConfig restConfig;

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

        logger.info("拦截到VUE 请求....");

        Map<String, Object> resultMap = Maps.newHashMap();
        String errorMsg = "{\"code\":403 ,\"message\" :\"无权访问\"}";
        resultMap.put("code", 403);
        resultMap.put("message", "无权访问");

        //登录认证跳过
        if (request.getRequestURL().toString().contains("/auth")) {
            return true;
        }

        //如果设置env=debug,则跳过Jwt认证
        if (this.getEnv() != null && this.getEnv().equals("debug")) {
            return true;
        }
        //试调用不需要走认证
        if (request.getMethod().toUpperCase().equals("OPTIONS")) {
            return true;
        }
        try {
            String authHeader = request.getHeader("Authorization");
            Cookie[] cookies = request.getCookies();
            logger.debug("开始从cookies中获取token：cookies={}, passTokenKey={}", cookies, passTokenKey);
            if ((authHeader == null || !authHeader.startsWith("Bearer ") || (PublicUtil.isNotEmpty(authHeader) && authHeader.length() < 20))) {
                // 取cookie如果取不到不允许通过
                if (PublicUtil.isNotEmpty(cookies)) {
                    for (Cookie cookie : cookies) {
                        if (passTokenKey.equals(cookie.getName())) {
                            authHeader = cookie.getValue();
                            logger.debug("cookie的值为：{}", cookie.getValue());
                            break;
                        }
                    }
                } else {
                    logger.error("==> 解析token失败,权限验证失败!");
                    throw new BusinessException("100009", "TOKEN解析失败");
                }
            }

            if (PublicUtil.isEmpty(authHeader)) {
                throw new BusinessException("request Header is null");
            }

            final String token = authHeader.substring(7);

            AuthTokenDto authTokenDto;
            AuthResDto authResDto = null;
            ThreadLocalMap.put(UamConstant.JWT_VIEW_TOKEN, token);
            Wrapper<AuthTokenDto> authTokenDtoWrapper = uamAuthEdasService.getAuthTokenDtoByToken(token);
            if (PublicUtil.isNotEmpty(authTokenDtoWrapper) && authTokenDtoWrapper.getCode() == Wrapper.SUCCESS_CODE) {
                authTokenDto = authTokenDtoWrapper.getResult();
                authResDto = authTokenDto.getAuthResDto();
                String newToken = authTokenDto.getNewToken();
                if (StringUtils.isNotEmpty(newToken)) {
                    response.setHeader("newtoken", newToken);
                }
            }

            if (PublicUtil.isEmpty(authResDto)) {
                throw new BusinessException("jwt验签失败,DTO为空");
            }

            ThreadLocalMap.put(UamConstant.TOKEN_USER, authResDto.getLoginName());
            ThreadLocalMap.put(UamConstant.TOKEN_AUTH_DTO, authResDto);
        } catch (BusinessException e) {
            try {
                resultMap.put("message", e.getMessage());
                String errorMessage = JacksonUtil.toJson(resultMap);
                handleException(request, response, errorMessage);
            } catch (Exception ex) {
                handleException(request, response, errorMsg);
            }
            return false;
        } catch (Exception e) {
            logger.error("Exception = {}", e.getMessage(), e);
            handleException(request, response, errorMsg);
            return false;
        }

        return true;

    }

    private void handleException(HttpServletRequest request, HttpServletResponse res, String msg) throws IOException {
        request.getSession().removeAttribute("token");
        res.resetBuffer();
        res.setHeader("Access-Control-Allow-Origin", restConfig.getPaas());
        res.setHeader("Access-Control-Allow-Credentials", "true");
        res.setContentType("application/json");
        res.setCharacterEncoding("UTF-8");
        res.getWriter().write(msg);
        res.flushBuffer();
    }

    public String getEnv() {
        return env;
    }

    public void setEnv(String env) {
        this.env = env;
    }
}