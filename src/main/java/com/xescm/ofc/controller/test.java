package com.xescm.ofc.controller;

import com.xescm.ofc.domain.OfcOrderStatus;
import com.xescm.ofc.service.OfcOrderStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by lyh on 2016/10/8.
 */
@RestController
public class test {

    @Autowired
    private OfcOrderStatusService ofcOrderStatusService;

    @RequestMapping("/")
    public String test(){
        OfcOrderStatus record = new OfcOrderStatus();
        record.setOrderCode("3");
        record.setOrderType("啦啦啦啦");
        ofcOrderStatusService.save(record);
        return "success";
    }

}
