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
        logger.info(mqConfig.getOfc2TfcOrderTopic()+"开始生产");
        Message message = new Message(mqConfig.getOfc2TfcOrderTopic(), null,jsonStr.getBytes());
        message.setKey(code);

        SendResult sendResult = producer.send(message);
        if (sendResult != null) {
            logger.info("{0}消费成功,消费时间为{1},MsgID为{2}",mqConfig.getOfc2TfcOrderTopic(),new Date(),sendResult.getMessageId());
        }
    }

    /**
     * 仓储计划单推送到仓储中心
     * @param jsonStr
     * @param code
     * @param tag
     */
    public boolean toSendWhc(String jsonStr,String code,String tag) {
        boolean isSend=false;
        logger.info(mqConfig.getOfc2WhcOrderTopic()+"开始生产");
        Message message = new Message(mqConfig.getOfc2WhcOrderTopic(), tag,jsonStr.getBytes());
        message.setKey(code);
        SendResult sendResult = producer.send(message);
        if (sendResult != null) {
            isSend=true;
            logger.info("{0}生产成功,tag为{1}生产时间为{2},MsgID为{3}",mqConfig.getOfc2WhcOrderTopic(),tag,new Date(),sendResult.getMessageId());
        }
        return isSend;
    }

}