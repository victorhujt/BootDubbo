package com.xescm.ofc.mq.producer;

import java.util.Date;
import java.util.Properties;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.ONSFactory;
import com.aliyun.openservices.ons.api.Producer;
import com.aliyun.openservices.ons.api.PropertyKeyConst;
import com.aliyun.openservices.ons.api.SendResult;
import com.xescm.ofc.config.MqConfig;
import com.xescm.ofc.utils.MQUtil;

/**
 * Created by MT on 2016/11/11.
 */
@Component
public class DefaultMqProducer {
	 private static final Logger logger = LoggerFactory.getLogger(DefaultMqProducer.class);


    /**
     * MQ发送定时消息示例Demo
     */
    @Resource
    MqConfig mqConfig;
    @Resource
    Producer producer;

    public void toSendTfcTransPlanMQ(String jsonStr,String code) {
        logger.info("{}开始消费",mqConfig.getTfcTransPlanTag());
        Message message = new Message(mqConfig.getTfcTransPlanTopic(), mqConfig.getTfcTransPlanTag(),jsonStr.getBytes());
        message.setKey(code);

        SendResult sendResult = producer.send(message);
        if (sendResult != null) {
            logger.info("{}消费成功,消费时间为{},MsgID为{}",mqConfig.getTfcTransPlanTag(),new Date(),sendResult.getMessageId());
        }
    }

    /**
     * 仓储计划单推送到仓储中心
     * @param jsonStr
     * @param code
     * @param tag
     */
    public void toSendWhc(String jsonStr,String code,String tag) {
    	logger.info("WhcProducer Started");
        Message message = new Message(mqConfig.getWhpOrderTopic(),tag,jsonStr.getBytes());
        message.setKey(code);
        SendResult sendResult = producer().send(message);
        if (sendResult != null) {
            logger.info("{}消费成功,消费时间为{},MsgID为{}",mqConfig.getWhpOrderTopic(),new Date(),sendResult.getMessageId());

        }
    }

    //@Bean(initMethod = "start", destroyMethod = "shutdown")
    public Producer producer(){
        Properties producerProperties = new Properties();
        producerProperties.setProperty(PropertyKeyConst.ProducerId, mqConfig.getProducerId());
        producerProperties.setProperty(PropertyKeyConst.AccessKey, mqConfig.getAccessKey());
        producerProperties.setProperty(PropertyKeyConst.SecretKey, mqConfig.getSecretKey());
        producerProperties.setProperty(PropertyKeyConst.ONSAddr, mqConfig.getOnsAddr());

        MQUtil.propertiesUtil(producerProperties);
        Producer producer = ONSFactory.createProducer(producerProperties);
        producer.start();
        return producer;
    }
}
