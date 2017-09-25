package com.xescm.ofc.aspect;

import com.xescm.base.model.dto.auth.AuthResDto;
import com.xescm.base.model.wrap.Wrapper;
import com.xescm.core.constant.UamConstant;
import com.xescm.core.exception.BusinessException;
import com.xescm.core.utils.PubUtils;
import com.xescm.core.utils.PublicUtil;
import com.xescm.core.utils.ThreadLocalMap;
import com.xescm.csc.model.dto.QueryCustomerCodeDto;
import com.xescm.csc.model.vo.CscCustomerVo;
import com.xescm.csc.provider.CscCustomerEdasService;
import com.xescm.ofc.annotation.ValidParam;
import com.xescm.ofc.model.dto.ofc.OfcUserMsgDTO;
import com.xescm.ofc.service.OfcOrderManageOperService;
import com.xescm.uam.provider.UamGroupEdasService;
import com.xescm.uam.provider.UamUserEdasService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * Created by lyh on 2017/9/11.
 */
@Component
@Aspect
public class AuthAspect {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());


    @Resource
    private UamUserEdasService uamUserEdasService;
    @Resource
    private UamGroupEdasService uamGroupEdasService;
    @Resource
    private OfcOrderManageOperService ofcOrderManageOperService;
    @Resource
    private CscCustomerEdasService cscCustomerEdasService;


    @Before(value = "execution(* com.xescm.ofc.service.impl.*.*(..)) && @annotation(com.xescm.ofc.annotation.Permission)")
    public void checkAuth(JoinPoint joinPoint) throws Throwable {
        Map<Integer, ValidParam> map = new HashMap<>();
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method targetMethod = methodSignature.getMethod();
        Method realMethod = joinPoint.getTarget().getClass().getDeclaredMethod(signature.getName(), targetMethod.getParameterTypes());
        int i = 0;
        for (Annotation[] parameterAnnotation : realMethod.getParameterAnnotations()) {
            for (Annotation annotation : parameterAnnotation) {
                if (annotation instanceof ValidParam) {
                    ValidParam param = (ValidParam) annotation;
                    map.put(i, param);
                }
            }
            i ++;
        }
        if (CollectionUtils.isEmpty(map)) {
            return;
        }
        Object[] args = joinPoint.getArgs();
        AuthResDto authResDto = this.getAuthResDTO();
        this.checkUserCustomerMsg(authResDto);
        for (Map.Entry<Integer, ValidParam> entry : map.entrySet()) {
            ValidParam validParam = entry.getValue();
            int argsIndex = entry.getKey();
            if (validParam.validType() == ValidParam.ValidType.ADD_CUSTOMER_MSG) {
                Object form = args[argsIndex];
                if (form instanceof OfcUserMsgDTO) {
                    OfcUserMsgDTO customerMsgDTO = (OfcUserMsgDTO) form;
                    this.addCustomerMsg(customerMsgDTO, authResDto);
                }
            }
        }
    }

    private void checkUserCustomerMsg(AuthResDto authResDto) {
        if (PubUtils.isSEmptyOrNull(authResDto.getCustomerRefCode())) {
            logger.error("当前登录的用户没有所属客户");
            throw new BusinessException("当前登录的用户没有所属客户!");
        }
        QueryCustomerCodeDto queryDTO = new QueryCustomerCodeDto();
        queryDTO.setCustomerCode(authResDto.getCustomerRefCode());
        Wrapper<CscCustomerVo> customerVoWrapper = cscCustomerEdasService.queryCustomerByCustomerCodeOrId(queryDTO);
        CscCustomerVo result = customerVoWrapper.getResult();
        if (customerVoWrapper.getCode() == Wrapper.ERROR_CODE || null == result) {
            logger.error("查询当前登录用户客户信息出错:查询到的结果为空或有误");
            throw new BusinessException("查询当前登录用户客户信息出错");
        }
    }

    private void addCustomerMsg(OfcUserMsgDTO customerMsgDTO, AuthResDto authResDto) {
        customerMsgDTO.setCustCode(authResDto.getCustomerRefCode());
        customerMsgDTO.setCustName(authResDto.getCustomerRefName());
        customerMsgDTO.setUserId(authResDto.getUserId());
        customerMsgDTO.setUserName(authResDto.getUserName());
    }

    private AuthResDto getAuthResDTO() {
        AuthResDto authResDto = (AuthResDto) ThreadLocalMap.get(UamConstant.TOKEN_AUTH_DTO);
        if (PublicUtil.isEmpty(authResDto)) {
            throw new BusinessException("验证token失败");
        }
        return authResDto;
    }
}
