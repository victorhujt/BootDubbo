package com.xescm.ofc.mq.producer;

import com.aliyun.openservices.ons.api.*;
import com.aliyun.openservices.ons.api.transaction.LocalTransactionExecuter;
import com.aliyun.openservices.ons.api.transaction.TransactionProducer;
import com.aliyun.openservices.ons.api.transaction.TransactionStatus;
import com.xescm.ofc.config.MqConfig;
import com.xescm.ofc.utils.MQUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Properties;

/**
 * Created by MT on 2016/11/11.
 */
@Component
public class DefaultMqProducer {
    private Logger logger = LoggerFactory.getLogger(DefaultMqProducer.class);

    /**
     * MQ发送定时消息示例 Demo
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
        System.out.println("Producer Started");
        Message message = new Message(mqConfig.getWHOTopic(), tag,jsonStr.getBytes());
        message.setKey(code);
        SendResult sendResult = producer.send(message);
        if (sendResult != null) {
            logger.info("{}消费成功,消费时间为{},MsgID为{}",mqConfig.getWHOTopic(),new Date(),sendResult.getMessageId());
        }
    }

}
