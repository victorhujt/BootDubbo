package com.xescm.ofc.web.interceptor;

import com.xescm.core.exception.BusinessException;
import com.xescm.core.utils.PubUtils;
import com.xescm.ofc.domain.OfcRuntimeProperty;
import com.xescm.ofc.enums.ResultCodeEnum;
import com.xescm.ofc.service.OfcIpLimitRuleService;
import com.xescm.ofc.service.OfcRuntimePropertyService;
import com.xescm.ofc.utils.CheckUtils;
import com.xescm.ofc.utils.RedisOperationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.xescm.ofc.constant.OrderConstConstant.INTERFACE_STATUS;

/**
 * Created by hujintao on 2017/6/22.
 */
@Service
public class LimitRuleInterceptor implements HandlerInterceptor {

    private static Logger logger = LoggerFactory.getLogger(AuthViewInterceptor.class);

    @Resource
    private RedisOperationUtils redisOperationUtils;
    @Resource
    private OfcIpLimitRuleService ofcIpLimitRuleService;

    @Resource
    private OfcRuntimePropertyService ofcRuntimePropertyService;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
            boolean isSuccess = true;
        try{
            //查单接口的开关是否开启
            CheckUtils.checkArgument(!queryOrderIsSwitch(INTERFACE_STATUS), ResultCodeEnum.INTERFACEISCLOSE);
            ofcIpLimitRuleService.checkLimit(redisOperationUtils,httpServletRequest);
        }catch (BusinessException be){
            isSuccess = false;
            logger.error("调用次数校验异常:{}", be.getMessage(), be);
        }catch (Exception e){
            isSuccess = false;
            logger.error("调用次数校验异常:{}", e.getMessage(), e);
        }
        return isSuccess;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }

    private boolean queryOrderIsSwitch(String name){
        boolean flag = false;
        OfcRuntimeProperty ofcRuntimeProperty = ofcRuntimePropertyService.findByName(name);
        if(ofcRuntimeProperty != null) {
            String value = ofcRuntimeProperty.getValue();
            if(!PubUtils.isSEmptyOrNull(value)){
                flag = "on".equals(value)?true:false;
            }
        }
        return flag;
    }
}
