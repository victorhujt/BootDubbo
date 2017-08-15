package com.xescm.ofc.web.restcontroller.exceptOrder;

import com.xescm.base.model.wrap.WrapMapper;
import com.xescm.base.model.wrap.Wrapper;
import com.xescm.ofc.domain.OfcEnumeration;
import com.xescm.ofc.exception.BusinessException;
import com.xescm.ofc.service.OfcEnumerationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>Title:      OfcEnumerationRest. </p>
 * <p>Description 枚举类</p>
 * <p>Company:    http://www.hnxianyi.com </p>
 *
 * @Author	      nothing
 * @CreateDate    2017/7/6 13:12
 */
@RestController
@RequestMapping(value = "/ofc/enum", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(value = "OfcEnumerationController", tags = "OfcEnumerationController", description = "自定义枚举类型", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class OfcEnumerationController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private OfcEnumerationService ofcEnumerationService;

    @ResponseBody
    @RequestMapping(value = "/queryEnumsByType", method = {RequestMethod.POST})
    @ApiOperation(value = "根据平台和类型查询枚举", httpMethod = "POST")
    public Wrapper<List<OfcEnumeration>> queryOfcEnumerationList(@RequestBody OfcEnumeration ofcEnumeration) {
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
