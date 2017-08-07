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


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * <p>Title:    MqConfig. </p>
 * <p>Description TODO </p>
 * <p>Company:    MT</p>
 *
 * @Author         <a>向铭涛</a>
 * @CreateDate     2016/11/11 15:40
 */
@Data
@Configuration
@ConfigurationProperties(prefix = MqConfig.MQ_PREFIX)
public class MqConfig {

    public final static String MQ_PREFIX="mq";


    private String accessKey;  //阿里云公钥
    private String secretKey;  //阿里云密钥
    private String consumerTopicNames;

    /**
     * 仓储订单状态
     */
    private String whcOrderStatusTopic;
    /**
     * 对接订单
     */
    private String epcOrderTopic;
    /**
     * 运输订单状态
     */
    private String tfcOrderStatusTopic;
    /**
     * 订单状态反馈
     */
    private String ofcOrderStatusTopic;

    /**
     * 运输计划单
     */
    private String ofc2TfcOrderTopic;
    /**
     * 仓储计划单
     */
    private String ofc2WhcOrderTopic;
    /**
     * 订单推结算
     */
    private String ofc2AcOrderTopic;
    /**
     * 订单推调度
     */
    private String ofc2DpcStatusTopic;
    /**
     * 仓储订单状态反馈
     */
    private String whc2ofcOrderStatusTopic;
    /**
     * DMS回传状态topic
     */
    public String dmsCallbackStatusTopic;
    private String whc2OfcOrderTopic;
    private String producerId; //XX发布者
    private String consumerId; //XX消费者
    private String onsAddr;  //阿里云地址


}
