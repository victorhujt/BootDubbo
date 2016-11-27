package com.xescm.ofc.mq.consumer;

import com.aliyun.openservices.ons.api.Action;
import com.aliyun.openservices.ons.api.ConsumeContext;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.MessageListener;
import com.xescm.ofc.config.MqConfig;
import com.xescm.ofc.mq.producer.CreateOrderApiProducer;
import com.xescm.ofc.service.CreateOrderService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 创单api消费MQ
 */
@Service
public class CreateOrderApiConsumer implements MessageListener {

    private Logger logger = LoggerFactory.getLogger(CreateOrderApiConsumer.class);

    @Autowired
    private CreateOrderService createOrderService;

    @Autowired
    private CreateOrderApiProducer createOrderApiProducer;

    @Autowired
    private MqConfig mqConfig;

    private List<String> keyList = new ArrayList<>();

    @Override
    public Action consume(Message message, ConsumeContext consumeContext) {
        logger.info("创单api消费MQ开始。。。");
        String topicName = message.getTopic();
        String key = message.getKey();
        String messageBody = new String(message.getBody());
        //TFCTopic
        if (StringUtils.equals(topicName, mqConfig.getTFCTopic()) && !keyList.contains(key)) {
            if (StringUtils.equals(message.getTag(), mqConfig.getEPCToTag())) {
                logger.info("创单api消费MQ:Tag:{},topic:{},key{}" + message.getTag(), topicName, key);
                keyList.add(key);
                String result = null;
                try {
                    result = createOrderService.createOrder(messageBody);
                } catch (Exception ex) {
                    logger.error("创单api消费MQ异常：{}{}", ex.getMessage(), ex);
                } finally {
                    logger.info("创单api消费MQ获取message处理结束");
                    //调用MQ生产者
                    if (StringUtils.isNotBlank(result)) {
                        String code = String.valueOf(result.hashCode());
                        createOrderApiProducer.sendCreateOrderResultMQ(result, code);
                    }
                }
            }
        }
        return Action.CommitMessage;
    }

}
