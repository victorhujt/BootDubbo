/**
 * Copyright (C) 2010-2016 Alibaba Group Holding Limited
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.xescm.ofc.config;


import com.aliyun.openservices.ons.api.Consumer;
import com.aliyun.openservices.ons.api.ONSFactory;
import com.aliyun.openservices.ons.api.PropertyKeyConst;
import com.xescm.ofc.mq.consumer.SchedulingSingleFedbackImpl;
import com.xescm.ofc.mq.consumer.SchedulingSingleFedbackImpl;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.Properties;
/**
 * <p>Title:    MqConfig. </p>
 * <p>Description TODO </p>
 * <p>Company:    MT</p>
 *
 * @Author         <a />向铭涛</a>
 * @CreateDate     2016/11/11 15:40
 */
@Configuration
@ConfigurationProperties(prefix = MqConfig.MQ_PREFIX)
public class MqConfig {
    public final static String MQ_PREFIX="mq";

    private String accessKey;  //阿里云公钥
    private String secretKey;  //阿里云密钥
    private String topic;
    private String TFCTopic;
    private String OFCTopic;
    private String tag;
    private String producerId; //XX发布者
    private String consumerId; //XX消费者
    private String onsAddr;  //阿里云地址
    private String tranTag;
    private String deliveryTag;

    @Resource
    SchedulingSingleFedbackImpl schedulingSingleFedback;

    @Bean(initMethod = "start", destroyMethod = "shutdown")
    public Consumer consumer(){
        System.out.println("yyyyyyyyy消费开始---:");
        Consumer consumer = ONSFactory.createConsumer(consumerProperties());
        consumer.subscribe(topic, null, schedulingSingleFedback);
        return consumer;
    }
    private Properties consumerProperties(){
        Properties consumerProperties = new Properties();
        consumerProperties.setProperty(PropertyKeyConst.ConsumerId, consumerId);
        consumerProperties.setProperty(PropertyKeyConst.AccessKey, accessKey);
        consumerProperties.setProperty(PropertyKeyConst.SecretKey, secretKey);
        consumerProperties.setProperty(PropertyKeyConst.ONSAddr, "http://onsaddr-internet.aliyun.com/rocketmq/nsaddr4client-internet");
        return consumerProperties;
    }

    public String getOnsAddr() {
        return onsAddr;
    }

    public void setOnsAddr(String onsAddr) {
        this.onsAddr = onsAddr;
    }

    public String getProducerId() {
        return producerId;
    }

    public void setProducerId(String producerId) {
        this.producerId = producerId;
    }

    public String getConsumerId() {
        return consumerId;
    }

    public void setConsumerId(String consumerId) {
        this.consumerId = consumerId;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getTranTag() {
        return tranTag;
    }

    public void setTranTag(String tranTag) {
        this.tranTag = tranTag;
    }

    public String getDeliveryTag() {
        return deliveryTag;
    }

    public void setDeliveryTag(String deliveryTag) {
        this.deliveryTag = deliveryTag;
    }

    //    public static final String TOPIC = "Ray_MQ_demoTest";
//    public static final String PRODUCER_ID = "PID_demoTest";
//    public static final String CONSUMER_ID = "CID_MQ_demoTest";
//    public static final String ACCESS_KEY = "LTAI3W8se7p0zD5b";
//    public static final String SECRET_KEY = "U1HuxjcUNZDxYhNwcRHbigQGzDREbd";
//    public static final String TAG = "mq_test_tag";


}
