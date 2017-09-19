package com.xescm.ofc.aspect;

import com.xescm.base.model.dto.auth.AuthResDto;
import com.xescm.base.model.wrap.Wrapper;
import com.xescm.core.constant.UamConstant;
import com.xescm.core.exception.BusinessException;
import com.xescm.core.utils.PubUtils;
import com.xescm.core.utils.PublicUtil;
import com.xescm.core.utils.ThreadLocalMap;
import com.xescm.ofc.annotation.ValidParam;
import com.xescm.ofc.model.dto.ofc.OfcUserMsgDTO;
import com.xescm.ofc.service.OfcOrderManageOperService;
import com.xescm.uam.model.dto.group.UamGroupDto;
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
import java.util.List;
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
        UamGroupDto uamGroupDto = this.checkUserGroupMsg(authResDto);
        for (Map.Entry<Integer, ValidParam> entry : map.entrySet()) {
            ValidParam validParam = entry.getValue();
            int argsIndex = entry.getKey();
            if (validParam.validType() == ValidParam.ValidType.ADD_CUSTOMER_MSG) {
                Object form = args[argsIndex];
                if (form instanceof OfcUserMsgDTO) {
                    OfcUserMsgDTO customerMsgDTO = (OfcUserMsgDTO) form;
                    this.addCustomerMsg(customerMsgDTO, uamGroupDto, authResDto);
                }
            }
        }
    }

    private UamGroupDto checkUserGroupMsg(AuthResDto authResDto) {
        UamGroupDto uamGroupDto = new UamGroupDto();
        uamGroupDto.setSerialNo(authResDto.getGroupRefCode());
        Wrapper<List<UamGroupDto>> allGroupByType = uamGroupEdasService.getAllGroupByType(uamGroupDto);
        ofcOrderManageOperService.checkUamGroupEdasResultNullOrError(allGroupByType);
        if (CollectionUtils.isEmpty(allGroupByType.getResult()) || allGroupByType.getResult().size() > 1) {
            throw new BusinessException("查询当前登录用户组织信息出错:查询到的结果为空或有误");
        }
        UamGroupDto uamGroupDtoResult = allGroupByType.getResult().get(0);
        if (null == uamGroupDtoResult || PubUtils.isSEmptyOrNull(uamGroupDtoResult.getType())) {
            throw new BusinessException("查询当前登录用户组织信息出错:查询到的结果有误");
        }
        String userGroupCode = authResDto.getGroupRefCode();
        if (PubUtils.isSEmptyOrNull(userGroupCode)) {
            throw new BusinessException("当前登录的用户没有所属组织!");
        }
        return uamGroupDto;
    }

    private void addCustomerMsg(OfcUserMsgDTO customerMsgDTO, UamGroupDto uamGroupDto, AuthResDto authResDto) {
        // 通过UAM接口查询用户绑定的客户信息
        // fixme
        // uamUserEdasService.xxxxx();
        customerMsgDTO.setCustCode("customerCode");
        customerMsgDTO.setCustName("customerName");
        customerMsgDTO.setUserId(authResDto.getUserId());
        customerMsgDTO.setUserName(authResDto.getUserName());
        customerMsgDTO.setUserGroupCode(uamGroupDto.getGroupCode());
        customerMsgDTO.setUserGroupName(uamGroupDto.getGroupName());
        customerMsgDTO.setGroupType(uamGroupDto.getType());
    }

    private AuthResDto getAuthResDTO() {
        AuthResDto authResDto = (AuthResDto) ThreadLocalMap.get(UamConstant.TOKEN_AUTH_DTO);
        if(PublicUtil.isEmpty(authResDto)){
            throw new BusinessException("验证token失败");
        }
        return authResDto;
    }
}
