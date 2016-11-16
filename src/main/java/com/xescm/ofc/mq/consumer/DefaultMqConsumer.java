package com.xescm.ofc.mq.consumer;

import com.aliyun.openservices.ons.api.Consumer;
import com.aliyun.openservices.ons.api.ONSFactory;
import com.aliyun.openservices.ons.api.PropertyKeyConst;
import com.xescm.ofc.config.MqConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Properties;

/**
 * Created by MT on 2016/11/11.
 */
//@Component
public class DefaultMqConsumer  {
    /*Logger logger = LoggerFactory.getLogger(DefaultMqConsumer.class);

    @Resource
    MqConfig mqConfig;

    @Resource
    SchedulingSingleFedbackImpl schedulingSingleFedback;
    public void MqConsumer(String topic) {
        logger.info("XX消费开始---:");
        Consumer consumer = ONSFactory.createConsumer(consumerProperties());
        consumer.subscribe(topic, mqConfig.getTag(), schedulingSingleFedback);
        consumer.start();
        //等待固定时间防止进程退出
        try {
            Thread.sleep(200000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    private Properties consumerProperties(){
        Properties consumerProperties = new Properties();
        consumerProperties.setProperty(PropertyKeyConst.ConsumerId, mqConfig.getConsumerId());
        consumerProperties.setProperty(PropertyKeyConst.AccessKey, mqConfig.getAccessKey());
        consumerProperties.setProperty(PropertyKeyConst.SecretKey, mqConfig.getSecretKey());
        consumerProperties.setProperty(PropertyKeyConst.ONSAddr, "http://onsaddr-internet.aliyun.com/rocketmq/nsaddr4client-internet");
        return consumerProperties;
    }*/


}
