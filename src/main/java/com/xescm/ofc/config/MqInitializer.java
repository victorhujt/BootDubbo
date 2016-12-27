package com.xescm.ofc.config;

import com.aliyun.openservices.ons.api.Consumer;
import com.aliyun.openservices.ons.api.ONSFactory;
import com.aliyun.openservices.ons.api.Producer;
import com.aliyun.openservices.ons.api.PropertyKeyConst;
import com.xescm.core.utils.PubUtils;
import com.xescm.ofc.mq.consumer.CreateOrderApiConsumer;
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
    @Resource
    private Properties producerProperties;
    @Resource
    private Properties consumerProperties;
    @Resource
    private MqConfig mqConfig;

    protected Logger logger = LoggerFactory.getLogger(this.getClass());


    @Resource
    private CreateOrderApiConsumer createOrderApiConsumer;


    @Bean(initMethod = "start", destroyMethod = "shutdown")
    public Consumer consumerCreateOrderApi(){
        logger.debug("createOrderApi消费开始---");
        Consumer consumer = ONSFactory.createConsumer(getConsumerProperties());
        if(PubUtils.isNull(mqConfig.getConsumerTopicNames())){
            logger.error("没有配置需要消费的Topic列表");
        }
        String [] strArray = mqConfig.getConsumerTopicNames().split(",");
        for(int i = 0;i<strArray.length;i++){
            String[] topicArray = strArray[i].split("@");
            String tag = null;
            if(null != topicArray && topicArray.length > 1){
                tag =  topicArray[1];
            }
            consumer.subscribe(topicArray[0], tag,createOrderApiConsumer);
        }
        return consumer;
    }

    @Bean(initMethod = "start", destroyMethod = "shutdown")
    public Producer producer(){
        producerProperties.setProperty(PropertyKeyConst.ProducerId, mqConfig.getProducerId());
        producerProperties.setProperty(PropertyKeyConst.AccessKey, mqConfig.getAccessKey());
        producerProperties.setProperty(PropertyKeyConst.SecretKey, mqConfig.getSecretKey());
        producerProperties.setProperty(PropertyKeyConst.ONSAddr, mqConfig.getOnsAddr());

        MQUtil.propertiesUtil(producerProperties);
        Producer producer = ONSFactory.createProducer(producerProperties);

        return producer;
    }
    @Bean
    public Properties producerProperties(){
        Properties producerProperties = new Properties();
        return producerProperties;
    }

    @Bean
    public Properties consumerProperties(){
        Properties consumerProperties = new Properties();
        return consumerProperties;
    }

    private Properties getConsumerProperties(){
        consumerProperties.setProperty(PropertyKeyConst.ConsumerId, mqConfig.getConsumerId());
        consumerProperties.setProperty(PropertyKeyConst.AccessKey, mqConfig.getAccessKey());
        consumerProperties.setProperty(PropertyKeyConst.SecretKey, mqConfig.getSecretKey());
        consumerProperties.setProperty(PropertyKeyConst.ONSAddr, mqConfig.getOnsAddr());
        return consumerProperties;
    }

}
