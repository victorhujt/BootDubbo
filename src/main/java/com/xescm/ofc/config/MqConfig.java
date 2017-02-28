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
 * @Author         <a>向铭涛</a>
 * @CreateDate     2016/11/11 15:40
 */
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
     *  /**
     * 仓储订单状态反馈
     *
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

    public String getConsumerTopicNames() {
        return consumerTopicNames;
    }

    public void setConsumerTopicNames(String consumerTopicNames) {
        this.consumerTopicNames = consumerTopicNames;
    }

    public String getWhcOrderStatusTopic() {
        return whcOrderStatusTopic;
    }

    public void setWhcOrderStatusTopic(String whcOrderStatusTopic) {
        this.whcOrderStatusTopic = whcOrderStatusTopic;
    }

    public String getEpcOrderTopic() {
        return epcOrderTopic;
    }

    public void setEpcOrderTopic(String epcOrderTopic) {
        this.epcOrderTopic = epcOrderTopic;
    }

    public String getTfcOrderStatusTopic() {
        return tfcOrderStatusTopic;
    }

    public void setTfcOrderStatusTopic(String tfcOrderStatusTopic) {
        this.tfcOrderStatusTopic = tfcOrderStatusTopic;
    }

    public String getOfcOrderStatusTopic() {
        return ofcOrderStatusTopic;
    }

    public void setOfcOrderStatusTopic(String ofcOrderStatusTopic) {
        this.ofcOrderStatusTopic = ofcOrderStatusTopic;
    }

    public String getOfc2TfcOrderTopic() {
        return ofc2TfcOrderTopic;
    }

    public void setOfc2TfcOrderTopic(String ofc2TfcOrderTopic) {
        this.ofc2TfcOrderTopic = ofc2TfcOrderTopic;
    }

    public String getOfc2WhcOrderTopic() {
        return ofc2WhcOrderTopic;
    }

    public void setOfc2WhcOrderTopic(String ofc2WhcOrderTopic) {
        this.ofc2WhcOrderTopic = ofc2WhcOrderTopic;
    }

    public String getWhc2ofcOrderStatusTopic() {
        return whc2ofcOrderStatusTopic;
    }

    public void setWhc2ofcOrderStatusTopic(String whc2ofcOrderStatusTopic) {
        this.whc2ofcOrderStatusTopic = whc2ofcOrderStatusTopic;
    }

    public String getDmsCallbackStatusTopic() {
        return dmsCallbackStatusTopic;
    }

    public void setDmsCallbackStatusTopic(String dmsCallbackStatusTopic) {
        this.dmsCallbackStatusTopic = dmsCallbackStatusTopic;
    }

    public String getWhc2OfcOrderTopic() {
        return whc2OfcOrderTopic;
    }

    public void setWhc2OfcOrderTopic(String whc2OfcOrderTopic) {
        this.whc2OfcOrderTopic = whc2OfcOrderTopic;
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

    public String getOnsAddr() {
        return onsAddr;
    }

    public void setOnsAddr(String onsAddr) {
        this.onsAddr = onsAddr;
    }
}
