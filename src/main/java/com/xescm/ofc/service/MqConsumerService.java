package com.xescm.ofc.service;

/**
 * @description: mq消费
 * @author: nothing
 * @date: 2017/9/26 14:58
 */
public interface MqConsumerService {

    /**
     * 订单运输状态消费
     * @param messageBody
     */
    void transportStateConsumer(String messageBody) throws Exception;
}
