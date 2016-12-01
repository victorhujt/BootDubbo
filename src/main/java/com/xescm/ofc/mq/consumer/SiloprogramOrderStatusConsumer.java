package com.xescm.ofc.mq.consumer;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.aliyun.openservices.ons.api.Action;
import com.aliyun.openservices.ons.api.ConsumeContext;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.MessageListener;
import com.xescm.ofc.config.MqConfig;
import com.xescm.ofc.service.impl.OfcOrderManageServiceImpl;

public class SiloprogramOrderStatusConsumer implements MessageListener{

    private Logger logger = LoggerFactory.getLogger(SiloprogramOrderStatusConsumer.class);
    
    @Autowired
    private OfcOrderManageServiceImpl ofcOrderManageServiceImpl;
     
    @Autowired
    private MqConfig mqConfig;

	@Override
	public Action consume(Message message, ConsumeContext context) {
		logger.info("仓储计划单消费者开始消费");
		try {
			 String topicName = message.getTopic();
		     String userName ="";
		     String key = message.getKey();
		     String messageBody = new String(message.getBody());
		     if (StringUtils.equals(topicName, mqConfig.getOfc2WhcOrderTopic())) {
		    	  logger.info("运输单状态反馈消费MQ:Tag:{},topic:{},key{}",message.getTag(), topicName, key);
		    	 //业务处理
		     }
		} catch (Exception e) {
			  logger.info(e.getMessage());
		}
	     return Action.CommitMessage;
	}

}
