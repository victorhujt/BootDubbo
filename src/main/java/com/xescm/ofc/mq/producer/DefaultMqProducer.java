package com.xescm.ofc.mq.producer;

import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.Producer;
import com.aliyun.openservices.ons.api.SendResult;
import com.xescm.ofc.config.MqConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.Date;

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

    public boolean toSendTfcTransPlanMQ(String jsonStr,String tag,String code) {
        boolean isSend = false;
        logger.info(mqConfig.getOfc2TfcOrderTopic()+"开始生产");
        Message message = new Message(mqConfig.getOfc2TfcOrderTopic(), tag,jsonStr.getBytes());
        message.setKey(code);

        SendResult sendResult = producer.send(message);
        if (sendResult != null) {
            isSend = true;
            logger.info("{0}消费成功,消费时间为{1},MsgID为{2}",mqConfig.getOfc2TfcOrderTopic(),new Date(),sendResult.getMessageId());
        }
        return isSend;
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


    /**
     * 发生MQ消息
     * @param msgStr    消息内容
     * @param topic     TOPIC
     * @param key       KEY
     * @param tag       TAG
     * @return          发送结果
     */
    public boolean sendMsg(String msgStr, String topic, String key, String tag) {
        boolean isSend = false;
        try {
            logger.info("MQ: {} 开始生产", topic);
            Message message = new Message(topic, tag, msgStr.getBytes(StandardCharsets.UTF_8));
            message.setKey(key);
            SendResult result = producer.send(message);
            if (result != null) {
                isSend = true;
                logger.info("TOPIC: {}, 生产成功,tag: {}, 生产时间: {}, MsgID: {}, 消息: {}", topic, tag, new Date(), result.getMessageId(), msgStr);
            } else {
                logger.error("TOPIC: {} 生产失败", topic);
            }
        } catch (Exception e) {
            logger.error("MQ消息发送发生异常：{}", e);
            throw e;
        }
        return isSend;
    }
}