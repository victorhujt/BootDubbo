package com.xescm.ofc.controller;

import com.xescm.ofc.domain.OfcOrderStatus;
import com.xescm.ofc.mapper.OfcOrderStatusMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by lyh on 2016/10/8.
 */
@RestController
public class test {
    @Autowired
    private OfcOrderStatusMapper ofcOrderStatusMapper;

    @RequestMapping("/")
    public String test(){
        OfcOrderStatus oos = new OfcOrderStatus();
        oos.setCustCode(1+"");
        oos.setCustName("测试");
        ofcOrderStatusMapper.insert(oos);
        return "success";
    }

}
