package com.xescm.ofc.mq.consumer;

import com.aliyun.openservices.ons.api.Action;
import com.aliyun.openservices.ons.api.ConsumeContext;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.MessageListener;
import com.xescm.core.utils.JacksonUtil;
import com.xescm.ofc.config.MqConfig;
import com.xescm.ofc.model.dto.ofc.OfcOrderPotDTO;
import com.xescm.ofc.service.OfcExceptOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;

/**
 * @description: 订单跟踪状态消费
 * @author: nothing
 * @date: 2017/7/21 13:17
 */
@Service
public class OfcExceptOrderConsumer implements MessageListener {

    private Logger logger = LoggerFactory.getLogger(OfcExceptOrderConsumer.class);

    @Resource
    private OfcExceptOrderService ofcExceptOrderService;
    @Resource
    private MqConfig mqConfig;

    @Override
    public Action consume(Message message, ConsumeContext consumeContext) {
        logger.info("OfcOrderTrackConsumer 开始消费MQ....");
        String topic = message.getTopic();
        String tag = message.getTag();
        String key = message.getKey();
        String messageBody = new String(message.getBody(), StandardCharsets.UTF_8);
        logger.info("OfcOrderTrackConsumer => topic = {}, tag = {}, key = {}, messageBody = {}", topic, tag, key, messageBody);
        if (topic.equals(mqConfig.getTfc2OfcOrderPoTopic()) || topic.equals(mqConfig.getWhc2OfcOrderPoTopic())) {
            try {
                OfcOrderPotDTO potDTO = JacksonUtil.parseJson(messageBody, OfcOrderPotDTO.class);
                int insertNum = ofcExceptOrderService.insertUndealOrder(potDTO);
                logger.debug("insertNum==>{}", insertNum);
            } catch (Exception e) {
                logger.error("消费订单状态发生异常：异常详情 => {}", e);
                return Action.ReconsumeLater;
            }
        }
        return Action.CommitMessage;
    }

}
