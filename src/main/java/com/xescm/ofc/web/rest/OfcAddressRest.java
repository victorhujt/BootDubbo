package com.xescm.ofc.web.rest;

import com.xescm.base.model.wrap.Wrapper;
import com.xescm.ofc.web.controller.BaseController;
import com.xescm.rmc.edas.domain.qo.RmcAddressQO;
import com.xescm.rmc.edas.service.RmcAddressEdasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by lyh on 2016/11/10.
 */
@Controller
public class OfcAddressRest extends BaseController{
    @Autowired
    private RmcAddressEdasService rmcAddressEdasService;

    @RequestMapping("/api/addr/citypicker/findByCodeAndType")
    @ResponseBody
    Wrapper<?> queryAddressByCodeAndType(RmcAddressQO queryAddress){
        logger.info("==> queryAddress={}", queryAddress);
        Wrapper<?> wrapper = null;
        try{
            wrapper = rmcAddressEdasService.findByCodeAndType(queryAddress);
        }catch (Exception ex){
            logger.error("订单中心查询四级地址出现异常:{}", ex.getMessage(), ex);
        }
        return wrapper;
    }
}
