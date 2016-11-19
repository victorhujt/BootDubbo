package com.xescm.ofc.web.controller;

import com.xescm.ofc.exception.BusinessException;
import com.xescm.ofc.service.CreateOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by hiyond on 2016/11/19.
 */

@RestController
@RequestMapping("ofcOrder")
public class TestOrder extends BaseController {

    @Autowired
    private CreateOrderService createOrderService;

    @RequestMapping(value = "/order",method = {RequestMethod.POST,RequestMethod.GET})
    @ResponseBody
    public String test() throws Exception {
        createOrderService.createOrder(null);
        return "";
    }

}
