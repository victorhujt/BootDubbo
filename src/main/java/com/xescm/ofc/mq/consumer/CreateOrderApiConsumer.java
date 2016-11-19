/*
package com.xescm.ofc.mq.consumer;

import com.aliyun.openservices.ons.api.Action;
import com.aliyun.openservices.ons.api.ConsumeContext;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.MessageListener;
import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.collect.Lists;
import com.xescm.ofc.config.MqConfig;
import com.xescm.ofc.domain.dto.coo.CreateOrderEntity;
import com.xescm.ofc.domain.dto.coo.CreateOrderResultDto;
import com.xescm.ofc.mq.producer.CreateOrderApiProducer;
import com.xescm.ofc.service.CreateOrderService;
import com.xescm.ofc.utils.JsonUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

*/
/**
 * 创单api消费MQ
 * Created by hiyond on 2016/11/17.
 *//*

@Service
public class CreateOrderApiConsumer implements MessageListener {

    private Logger logger = LoggerFactory.getLogger(SchedulingSingleFedbackImpl.class);

    @Autowired
    private CreateOrderService createOrderService;

    @Autowired
    private CreateOrderApiProducer createOrderApiProducer;

    @Autowired
    private MqConfig mqConfig;

    private List<String> keyList = new ArrayList<>();

    @Override
    public Action consume(Message message, ConsumeContext consumeContext) {
        String topicName = message.getTopic();
        String key = message.getKey();
        String messageBody = new String(message.getBody());
        //TFCTopic
        if (StringUtils.equals(topicName, mqConfig.getTFCTopic()) && !keyList.contains(key)) {
            if(StringUtils.equals(message.getTag(),mqConfig.getEPCToTag())){
                keyList.add(key);
                String result = null;
                try {
                    logger.debug("创单api消费MQ获取message:{},key:{}", messageBody, key);
                    result = createOrderService.createOrder(messageBody);
                } catch (Exception ex) {
                    logger.error("创单api消费MQ异常：{}{}", ex.getMessage(), ex);
                } finally {
                    logger.debug("创单api消费MQ获取message处理结束");
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

    public void sendCreateOrderResultMQDemo() {
        String result = null;
        String code = String.valueOf(result.hashCode());
        createOrderApiProducer.sendCreateOrderResultMQ(result, code);
    }

    public String test() throws Exception {
        CreateOrderResultDto createOrderResultDto = new CreateOrderResultDto();
        createOrderResultDto.setCode("500");
        String r = "typeId:"+"123456"+"||"+"reason:"+"null"+","+"typeId:"+"123456"+"||"+"reason:"+"参数错误";
        createOrderResultDto.setReason(r);
        List<String> list = Lists.newArrayList("typeId:123456","typeId:654321");
        createOrderResultDto.setMessage(list);

        //要反回的json格式的字符串
        String createOrderResultDtoJson = JsonUtil.object2Json(createOrderResultDto);
        return createOrderResultDtoJson;
    }

}
*/
