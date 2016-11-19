package com.xescm.ofc.mq.producer;

import com.aliyun.openservices.ons.api.*;
import com.aliyun.openservices.ons.api.transaction.LocalTransactionExecuter;
import com.aliyun.openservices.ons.api.transaction.TransactionProducer;
import com.aliyun.openservices.ons.api.transaction.TransactionStatus;
import com.xescm.ofc.config.MqConfig;
import com.xescm.ofc.utils.MQUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Properties;

@Component
public class CreateOrderApiProducer {

    private Logger logger = LoggerFactory.getLogger(CreateOrderApiProducer.class);

    @Resource
    MqConfig mqConfig;

    //发送MQ
    public void sendCreateOrderResultMQ(String data, String code) {
        logger.info("推送创单api返回信息：{}", data);
        if (StringUtils.isNotBlank(data)) {
            Message message = new Message(mqConfig.getOFCTopic(), mqConfig.getTag(), data.getBytes());
            message.setKey(code);
            SendResult sendResult = mqConfig.producer.send(message);
            if (sendResult != null) {
                logger.info(new Date() + " 发送 mq message success! Topic is:" + mqConfig.getTopic() + "  msgId is: " + sendResult.getMessageId());
            }
        }
    }


}
