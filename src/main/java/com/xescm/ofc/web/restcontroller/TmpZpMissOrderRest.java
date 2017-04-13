package com.xescm.ofc.web.restcontroller;

import com.xescm.base.model.wrap.WrapMapper;
import com.xescm.base.model.wrap.Wrapper;
import com.xescm.ofc.exception.BusinessException;
import com.xescm.ofc.service.TmpZpMissOrderService;
import com.xescm.ofc.web.controller.BaseController;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * Created by lyh on 2017/4/13.
 */
@RequestMapping(value = "/ofc",produces = {"application/json;charset=UTF-8"})
@Controller
public class TmpZpMissOrderRest extends BaseController {

    @Resource
    private TmpZpMissOrderService tmpZpMissOrderService;

    /**
     * 导入众品遗漏订单
     */
    @RequestMapping(value = "zpMissOrderImport", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public Wrapper<String> zpMissOrderImport(@RequestParam(value = "file") MultipartFile file) {
        Wrapper<String> importResult;
        try {
            importResult = tmpZpMissOrderService.zpMissOrderImport(file);
        } catch (BusinessException e) {
            logger.error("导入众品遗漏订单出错:{}",e.getMessage(),e);
            importResult = WrapMapper.wrap(Wrapper.ERROR_CODE, e.getMessage());
        } catch (Exception e) {
            logger.error("导入众品遗漏订单出错:{}",e.getMessage(),e);
            importResult = WrapMapper.wrap(Wrapper.ERROR_CODE,Wrapper.ERROR_MESSAGE);
        }
        logger.info("导单结果:{}", importResult.getMessage());
        return importResult;
    }
}
