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
public class SiloprogramOrderConsumer extends BaseController implements MessageListener {

	private Logger logger = LoggerFactory.getLogger(SiloprogramOrderConsumer.class);

	@Autowired
	private OfcSiloproStatusService ofcSiloproStatusService;

	@Autowired
	private MqConfig mqConfig;


	@Override
	public Action consume(Message message, ConsumeContext context) {
		logger.info("OFC消费MQ开始。。。");
		String topicName = message.getTopic();
		String tag = message.getTag();
		String userName = "";
		String key = message.getKey();
		String messageBody = new String(message.getBody());
		logger.info("OFC消费MQ开始。。。MessageBody:" + messageBody + ",topicName:" + topicName + ",tag:" + tag);


		return null;

	}
}