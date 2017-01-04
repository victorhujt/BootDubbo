package com.xescm.ofc.mq.consumer;

import com.aliyun.openservices.ons.api.Action;
import com.aliyun.openservices.ons.api.ConsumeContext;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.MessageListener;
import com.xescm.core.utils.JacksonUtil;
import com.xescm.ofc.config.MqConfig;
import com.xescm.ofc.domain.OfcSiloprogramStatusFedBackCondition;
import com.xescm.ofc.service.OfcSiloproStatusService;
import com.xescm.ofc.web.controller.BaseController;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

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
			 List<OfcSiloprogramStatusFedBackCondition> ofcSiloprogramStatusFedBackConditions = null;
			 try {
				 TypeReference<List<OfcSiloprogramStatusFedBackCondition>> typeReference = new TypeReference<List<OfcSiloprogramStatusFedBackCondition>>() {
				 };
				 ofcSiloprogramStatusFedBackConditions= JacksonUtil.parseJsonWithFormat(messageBody,typeReference);
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
