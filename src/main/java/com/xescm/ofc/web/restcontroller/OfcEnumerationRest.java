package com.xescm.ofc.web.restcontroller;

import com.xescm.base.model.wrap.WrapMapper;
import com.xescm.base.model.wrap.Wrapper;
import com.xescm.ofc.domain.OfcEnumeration;
import com.xescm.ofc.exception.BusinessException;
import com.xescm.ofc.service.OfcEnumerationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * @description: 枚举
 * @author: nothing
 * @date: 2017/6/7 18:52
 */
@Controller
@RequestMapping(value = "/ofc/enum", produces = {"application/json;charset=UTF-8"})
public class OfcEnumerationRest {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private OfcEnumerationService ofcEnumerationService;

    @ResponseBody
    @RequestMapping(value = "/queryEnums", method = {RequestMethod.POST})
    public Wrapper<List<OfcEnumeration>> queryOfcEnumerationList(OfcEnumeration ofcEnumeration) {
        Wrapper<List<OfcEnumeration>> result;
        try {
            List<OfcEnumeration> enumList = ofcEnumerationService.queryOfcEnumerationList(ofcEnumeration);
            result = WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, enumList);
        } catch (BusinessException e) {
            logger.error("查询枚举发生异常：异常信息=>{}", e);
            result = WrapMapper.wrap(Wrapper.ERROR_CODE, e.getMessage());
        } catch (Exception e) {
            logger.error("查询枚举发生未知异常：异常信息=>{}", e);
            result = WrapMapper.wrap(Wrapper.ERROR_CODE, Wrapper.ERROR_MESSAGE);
        }
        return result;
    }
}
