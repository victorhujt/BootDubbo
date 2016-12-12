package com.xescm.ofc.mq.producer;

import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.Producer;
import com.aliyun.openservices.ons.api.SendResult;
import com.xescm.ofc.config.MqConfig;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class CreateOrderApiProducer {

    private Logger logger = LoggerFactory.getLogger(CreateOrderApiProducer.class);

    @Resource
    Producer producer;

    @Resource
    MqConfig mqConfig;

    //发送MQ
    public void sendCreateOrderResultMQ(String data, String code) {
        final String tag = "xeStatusBackTag";
        logger.info("推送创单api返回信息：{},Topic:{},tag:{}", data, mqConfig.getOfcOrderStatusTopic(), tag);
        if (StringUtils.isNotBlank(data)) {
            Message message = new Message(mqConfig.getOfcOrderStatusTopic(), tag, data.getBytes());
            message.setKey(code);
            SendResult sendResult = producer.send(message);
            if (sendResult != null) {
                logger.info(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " 发送 mq message 成功! Topic：{},tag:{},message:{}", mqConfig.getOfcOrderStatusTopic(), tag, sendResult.getMessageId());
            }
        }
    }


}
