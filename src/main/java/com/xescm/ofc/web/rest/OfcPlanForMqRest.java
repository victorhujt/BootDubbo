package com.xescm.ofc.web.rest;

import com.xescm.ofc.config.MqConfig;
import com.xescm.ofc.mq.consumer.DefaultMqConsumer;
import com.xescm.ofc.mq.producer.DefaultMqProducer;
import com.xescm.ofc.web.controller.BaseController;
import com.xescm.uam.domain.dto.AuthResDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;

@RequestMapping(value = "/ofc",produces = {"application/json;charset=UTF-8"})
@Controller
public class OfcPlanForMqRest extends BaseController{
    @Autowired
    private DefaultMqProducer defaultMqProducer;
    @Autowired
    private DefaultMqConsumer defaultMqConsumer;
    @Resource
    MqConfig mqConfig;

    @RequestMapping(value = "/planForMq")
    @ResponseBody
    public void planForMq() throws InvocationTargetException {
        AuthResDto authResDto=getAuthResDtoByToken();
        String userName=authResDto.getUamUser().getUserName();
        defaultMqConsumer.MqConsumer(mqConfig.getTopic());
    }
}
