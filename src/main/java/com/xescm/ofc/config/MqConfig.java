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
 * <p>Description 订单中心MQ推送配置</p>
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

    /** 阿里云地址 */
    private String onsAddr;
    /** 阿里云公钥 */
    private String accessKey;
    /** 阿里云密钥 */
    private String secretKey;
    /** topic名称 */
    private String consumerTopicNames;
    /** 生产者ID */
    private String producerId;
    /** 消费者ID */
    private String consumerId;
    /** 异常订单消费者ID */
    private String consumerExceptOrderId;

    /** 对接订单 */
    private String epcOrderTopic;

    /** 订单状态反馈 */
    private String ofcOrderStatusTopic;

    /** 仓储订单状态 */
    private String whcOrderStatusTopic;

    /** 运输订单状态 */
    private String tfcOrderStatusTopic;

    /** 订单推送tfc */
    private String ofc2TfcOrderTopic;

    /** 订单推送whc */
    private String ofc2WhcOrderTopic;

    /** 订单推送ac */
    private String ofc2AcOrderTopic;

    /** 取消订单推送dpc */
    private String ofc2DpcStatusTopic;

    /** DMS回传状态Topic */
    public String dmsCallbackStatusTopic;

    /** 仓储订单Topic */
    private String whc2OfcOrderTopic;
    
    /** 仓储订单状态反馈 */
    private String whc2ofcOrderStatusTopic;

    /** 订单待创建货品Topic */
    private String ofc2CscGoodsInfoTopic;

    /**----------------异常订单topic----------------**/

    /** 运输中心推送订单状态 */
    private String tfc2OfcOrderPoTopic;
    /** 仓储中心推送订单状态 */
    private String whc2OfcOrderPoTopic;
    /**订单中心推送货品到客户中心*/
    private String ofc2CscGoodsTopic;

    /**----------------异常订单topic----------------**/

}
