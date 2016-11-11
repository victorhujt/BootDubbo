package com.xescm.ofc.web.rest;

import com.xescm.ofc.domain.dto.addr.QueryAddress;
import com.xescm.ofc.feign.client.FeignAddressInterfaceClient;
import com.xescm.ofc.web.controller.BaseController;
import com.xescm.uam.utils.wrap.Wrapper;
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
    private FeignAddressInterfaceClient feignAddressInterfaceClient;

    @RequestMapping("/api/addr/citypicker/findByCodeAndType")
    @ResponseBody
    Wrapper<?> queryAddressByCodeAndType(QueryAddress queryAddress){
        logger.info("==> queryAddress={}", queryAddress);
        Wrapper<?> wrapper = null;
        try{
            wrapper = feignAddressInterfaceClient.queryAddressByCodeAndType(queryAddress);
        }catch (Exception ex){
            logger.error("订单中心查询四级地址出现异常:{},{}", ex.getMessage(), ex);
        }
        return wrapper;
    }
}
