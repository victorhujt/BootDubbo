package com.xescm.ofc.service;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @description: mq消费
 * @author: nothing
 * @date: 2017/9/26 14:58
 */
public interface MqConsumerService {

    /**
     * 订单运输状态消费
     * @param messageBody
     * @param cmap
     */
    void transportStateConsumer(String messageBody,ConcurrentHashMap cmap) throws Exception;
}
