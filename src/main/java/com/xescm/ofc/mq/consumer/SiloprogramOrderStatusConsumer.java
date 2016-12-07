package com.xescm.ofc.mq.consumer;

import java.util.List;

import com.xescm.ofc.service.OfcSiloproStatusService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.aliyun.openservices.ons.api.Action;
import com.aliyun.openservices.ons.api.ConsumeContext;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.MessageListener;
import com.xescm.ofc.config.MqConfig;
import com.xescm.ofc.domain.ofcSiloprogramStatusFedBackCondition;
import com.xescm.ofc.service.impl.OfcSiloproStatusServiceImpl;
import com.xescm.ofc.utils.JSONUtils;
import com.xescm.ofc.web.controller.BaseController;

//@Component
public class SiloprogramOrderStatusConsumer extends BaseController implements MessageListener{

    private Logger logger = LoggerFactory.getLogger(SiloprogramOrderStatusConsumer.class);
    
    @Autowired
    private OfcSiloproStatusService ofcSiloproStatusService;
     
    @Autowired
    private MqConfig mqConfig;
    

	@Override
	public Action consume(Message message, ConsumeContext context) {
		   logger.info("OFC消费MQ开始。。。");
	        String topicName = message.getTopic();
	        String userName ="";
	        String key = message.getKey();
	        String messageBody = new String(message.getBody());
		if(StringUtils.equals(topicName,mqConfig.getWhcOrderStatusTopic())){
    	logger.info("仓储计划单状态反馈的消息体为:"+messageBody);
		logger.info("仓储计划单开始消费");
		try {
    	  logger.info("仓储计划单状态反馈消费MQ:Tag:{},topic:{},key{}",message.getTag(), topicName, key);
			 List<ofcSiloprogramStatusFedBackCondition> ofcSiloprogramStatusFedBackConditions = null;
			 try {
				 ofcSiloprogramStatusFedBackConditions= JSONUtils.jsonToList(messageBody , ofcSiloprogramStatusFedBackCondition.class);
				 for(int i=0;i<ofcSiloprogramStatusFedBackConditions.size();i++){
					 ofcSiloproStatusService.feedBackSiloproStatusFromWhc(ofcSiloprogramStatusFedBackConditions.get(i));
				 }
			 } catch (Exception e) {
				 logger.info(e.getMessage());
				// return Action.ReconsumeLater;
			 }
		} catch (Exception e) {
			  logger.info(e.getMessage());
			 // return Action.ReconsumeLater;
		}
    }
		return null;}
}
