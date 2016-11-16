package com.xescm.ofc.mq.producer;

import com.aliyun.openservices.ons.api.*;
import com.aliyun.openservices.ons.api.transaction.LocalTransactionExecuter;
import com.aliyun.openservices.ons.api.transaction.TransactionProducer;
import com.aliyun.openservices.ons.api.transaction.TransactionStatus;
import com.xescm.ofc.config.MqConfig;
import com.xescm.ofc.utils.MQUtil;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Properties;

/**
 * Created by MT on 2016/11/11.
 */
@Component
public class DefaultMqProducer {

    /**
     * MQ发送定时消息示例 Demo
     */
    @Resource
    MqConfig mqConfig;

    public void mQTimerProducer(){
        Properties producerProperties = new Properties();
        MQUtil.propertiesUtil(producerProperties);
        Producer producer = ONSFactory.createProducer(producerProperties);
        producer.start();
        System.out.println("Producer Started");

        for (int i = 0; i < 10; i++) {
            Message message = new Message(mqConfig.getTopic(), mqConfig.getTag(), "mq send timer message test".getBytes());
            // 延时时间单位为毫秒（ms），指定一个时刻，在这个时刻之后才能被消费，这个例子表示 3秒 后才能被消费
            long delayTime = 3000;
            message.setStartDeliverTime(System.currentTimeMillis() + delayTime);
            SendResult sendResult = producer.send(message);
            if (sendResult != null) {
                System.out.println(new Date() + " Send mq timer message success! Topic is:" + mqConfig.getTopic() + "msgId is: " + sendResult.getMessageId());
            }
        }
    }

    /**
     * MQ发送普通消息示例 Demo
     */

    public void toSendMQ(String jsonStr,String code) {
        System.out.println("Producer Started");
        Message message = new Message(mqConfig.getTopic(), mqConfig.getTag(),jsonStr.getBytes());
        message.setKey(code);
        SendResult sendResult = producer().send(message);
        if (sendResult != null) {
            System.out.println(new Date() + " Send mq message success! Topic is:" + mqConfig.getTopic() + "msgId is: " + sendResult.getMessageId());
        }
    }
    /**
     * MQ发送普通消息示例 Demo
     */

    public void toSendMQTfcCancelPlanOrder(String jsonStr) {
        System.out.println("Producer Started");
        Message message = new Message(mqConfig.getTfcCancelTopic(), mqConfig.getTfcCancelTag(),jsonStr.getBytes());

        SendResult sendResult = producer().send(message);
        if (sendResult != null) {
            System.out.println(new Date() + " Send mq message success! Topic is:" + mqConfig.getTopic() + "msgId is: " + sendResult.getMessageId());
        }
    }

    /**
     * MQ 发送事务消息示例 Demo
     */

    public void sendTransactionalMQ(String jsonStr,String code){
        Message message = new Message(mqConfig.getTopic(), mqConfig.getTag(), jsonStr.getBytes());
        message.setKey(code);
        SendResult sendResult = transactionProducer().send(message, new LocalTransactionExecuter() {
            public TransactionStatus execute(Message msg, Object arg) {
                    System.out.println("执行本地事务, 并根据本地事务的状态提交TransactionStatus.");
                    return TransactionStatus.CommitTransaction;
                }
        }, null);

        System.out.println("Send transaction message success.");
    }


    private Producer producer(){
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
    private TransactionProducer transactionProducer(){
        Properties tranProducerProperties = new Properties();
        tranProducerProperties.setProperty(PropertyKeyConst.ProducerId, mqConfig.getProducerId());
        tranProducerProperties.setProperty(PropertyKeyConst.AccessKey, mqConfig.getAccessKey());
        tranProducerProperties.setProperty(PropertyKeyConst.SecretKey, mqConfig.getSecretKey());
        tranProducerProperties.setProperty(PropertyKeyConst.ONSAddr, mqConfig.getOnsAddr());
        MQUtil.propertiesUtil(tranProducerProperties);
        //初始化事务消息Producer时,需要注册一个本地事务状态的的Checker
        LocalTransactionCheckerImpl localTransactionChecker = LocalTransactionCheckerImpl.getInstance();
        TransactionProducer transactionProducer = ONSFactory.createTransactionProducer(tranProducerProperties, localTransactionChecker);
        transactionProducer.start();
        return transactionProducer;
    }



}
