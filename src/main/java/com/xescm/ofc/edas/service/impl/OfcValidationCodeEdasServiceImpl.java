package com.xescm.ofc.edas.service.impl;

import com.xescm.base.model.wrap.WrapMapper;
import com.xescm.base.model.wrap.Wrapper;
import com.xescm.ofc.edas.service.OfcValidationCodeEdasService;
import com.xescm.ofc.service.OfcValidationCodeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>Title:      OfcValidationCodeEdasServiceImpl. </p>
 * <p>Description 生成验证码的Edas接口 </p>
 * <p>Company:    http://www.hnxianyi.com </p>
 *
 * @Author nothing
 * @CreateDate 2017/7/3 13:31
 */
@Service
public class OfcValidationCodeEdasServiceImpl implements OfcValidationCodeEdasService {

    private Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Resource
    private OfcValidationCodeService ofcValidationCodeService;

    @Override
    public Wrapper<?> getValidationCode() {
        return WrapMapper.wrap(200,"生成验证码成功",ofcValidationCodeService.getValidationCode());
    }
}
