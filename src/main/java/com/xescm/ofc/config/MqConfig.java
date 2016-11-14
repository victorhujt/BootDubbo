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


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

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
    private String tag;
    private String producerId; //XX发布者
    private String consumerId; //XX消费者
    private String onsAddr;  //阿里云地址

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

//    public static final String TOPIC = "Ray_MQ_demoTest";
//    public static final String PRODUCER_ID = "PID_demoTest";
//    public static final String CONSUMER_ID = "CID_MQ_demoTest";
//    public static final String ACCESS_KEY = "LTAI3W8se7p0zD5b";
//    public static final String SECRET_KEY = "U1HuxjcUNZDxYhNwcRHbigQGzDREbd";
//    public static final String TAG = "mq_test_tag";


}
