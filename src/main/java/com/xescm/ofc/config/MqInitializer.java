package com.xescm.ofc.config;

import com.aliyun.openservices.ons.api.*;
import com.xescm.core.utils.PubUtils;
import com.xescm.ofc.mq.consumer.CreateOrderApiConsumer;
import com.xescm.ofc.mq.consumer.OfcExceptOrderConsumer;
import com.xescm.ofc.utils.MQUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.Properties;

/**
 * Created by wangsongtao on 2016/11/26.
 */
@Configuration
public class MqInitializer {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private Properties producerProperties;
    @Resource
    private Properties consumerProperties;
    @Resource
    private Properties consumerExceptOrderProperties;
    @Resource
    private MqConfig mqConfig;
    @Resource
    private CreateOrderApiConsumer createOrderApiConsumer;
    @Resource
    private OfcExceptOrderConsumer exceptOrderConsumer;

    /**
     * 订单通用状态消费者
     *
     * @return
     */
    @Bean(initMethod = "start", destroyMethod = "shutdown")
    public Consumer consumerOrderInfo() {
        logger.info("订单通用状态消费者消费开始---");
        Consumer consumer = ONSFactory.createConsumer(getConsumerProperties());
        this.topicConsumer(consumer, createOrderApiConsumer);
        return consumer;
    }

    /**
     * 订单状态跟踪消费者
     *
     * @return
     */
    @Bean(initMethod = "start", destroyMethod = "shutdown")
    public Consumer consumerExceptOrder() {
        logger.info("订单状态跟踪消费开始---");
        Consumer consumer = ONSFactory.createConsumer(getConsumerExceptOrderProperties());
        this.topicConsumer(consumer, exceptOrderConsumer);
        return consumer;
    }

    /**
     * 订阅topic
     *
     * @param consumer
     * @param listener
     * @return
     */
    private void topicConsumer(Consumer consumer, MessageListener listener) {
        logger.info("topicConsumer消费开始---");
        String consumerTopicNames = mqConfig.getConsumerTopicNames();
        if (PubUtils.isNull(consumerTopicNames)) {
            logger.error("没有配置需要消费的Topic列表");
            return;
        }
        String[] strArray = mqConfig.getConsumerTopicNames().split(",");
        for (int i = 0; i < strArray.length; i++) {
            String[] topicArray = strArray[i].split("@");
            String tag = null;
            if (null != topicArray && topicArray.length > 1) {
                tag = topicArray[1];
            }
            consumer.subscribe(topicArray[0], tag, listener);
        }
    }

    @Bean(initMethod = "start", destroyMethod = "shutdown")
    public Producer producer() {
        producerProperties.setProperty(PropertyKeyConst.ProducerId, mqConfig.getProducerId());
        producerProperties.setProperty(PropertyKeyConst.AccessKey, mqConfig.getAccessKey());
        producerProperties.setProperty(PropertyKeyConst.SecretKey, mqConfig.getSecretKey());
        producerProperties.setProperty(PropertyKeyConst.ONSAddr, mqConfig.getOnsAddr());
        MQUtil.propertiesUtil(producerProperties);
        Producer producer = ONSFactory.createProducer(producerProperties);
        return producer;
    }

    @Bean
    public Properties producerProperties() {
        Properties producerProperties = new Properties();
        return producerProperties;
    }

    @Bean
    public Properties consumerProperties() {
        Properties consumerProperties = new Properties();
        return consumerProperties;
    }

    @Bean
    public Properties consumerExceptOrderProperties() {
        Properties consumerExceptOrderProperties = new Properties();
        return consumerExceptOrderProperties;
    }

    private Properties getConsumerProperties() {
        consumerProperties.setProperty(PropertyKeyConst.ConsumerId, mqConfig.getConsumerId());
        consumerProperties.setProperty(PropertyKeyConst.AccessKey, mqConfig.getAccessKey());
        consumerProperties.setProperty(PropertyKeyConst.SecretKey, mqConfig.getSecretKey());
        consumerProperties.setProperty(PropertyKeyConst.ONSAddr, mqConfig.getOnsAddr());
        return consumerProperties;
    }

    private Properties getConsumerExceptOrderProperties() {
        consumerExceptOrderProperties.setProperty(PropertyKeyConst.ConsumerId, mqConfig.getConsumerExceptOrderId());
        consumerExceptOrderProperties.setProperty(PropertyKeyConst.AccessKey, mqConfig.getAccessKey());
        consumerExceptOrderProperties.setProperty(PropertyKeyConst.SecretKey, mqConfig.getSecretKey());
        consumerExceptOrderProperties.setProperty(PropertyKeyConst.ONSAddr, mqConfig.getOnsAddr());
        return consumerExceptOrderProperties;
    }

}
