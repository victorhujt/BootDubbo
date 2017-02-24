package com.xescm.ofc.mq.consumer;

import com.aliyun.openservices.ons.api.Action;
import com.aliyun.openservices.ons.api.ConsumeContext;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.MessageListener;
import com.xescm.ofc.config.MqConfig;
import com.xescm.ofc.service.OfcSiloproStatusService;
import com.xescm.ofc.web.controller.BaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

//@Component
public class SiloprogramOrderStatusConsumer extends BaseController implements MessageListener{

    private Logger logger = LoggerFactory.getLogger(SiloprogramOrderStatusConsumer.class);
    
    @Autowired
    private OfcSiloproStatusService ofcSiloproStatusService;
     
    @Autowired
    private MqConfig mqConfig;
    

	@Override
	public Action consume(Message message, ConsumeContext context) {
		return null;
	}
}
