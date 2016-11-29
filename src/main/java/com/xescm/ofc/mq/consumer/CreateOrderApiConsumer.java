package com.xescm.ofc.mq.consumer;

import com.aliyun.openservices.ons.api.Action;
import com.aliyun.openservices.ons.api.ConsumeContext;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.MessageListener;
import com.xescm.ofc.config.MqConfig;
import com.xescm.ofc.domain.OfcPlanFedBackCondition;
import com.xescm.ofc.domain.OfcPlanFedBackResult;
import com.xescm.ofc.domain.OfcSchedulingSingleFeedbackCondition;
import com.xescm.ofc.mq.producer.CreateOrderApiProducer;
import com.xescm.ofc.service.CreateOrderService;
import com.xescm.ofc.service.OfcPlanFedBackService;
import com.xescm.ofc.utils.JSONUtils;
import com.xescm.uam.utils.wrap.Wrapper;
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
    private OfcPlanFedBackService ofcPlanFedBackService;

    @Autowired
    private MqConfig mqConfig;

    private List<String> keyList = new ArrayList<>();

    @Override
    public Action consume(Message message, ConsumeContext consumeContext) {
        logger.info("OFC消费MQ开始。。。");
        String topicName = message.getTopic();
        String userName ="";
        String key = message.getKey();
        String messageBody = new String(message.getBody());
        //EPCTopic
        if (StringUtils.equals(topicName, mqConfig.getEpcOrderTopic())) {
            logger.info("创单api消费MQ:Tag:{},topic:{},key{}", message.getTag(), topicName, key);
            keyList.add(key);
            String result = null;
            try {
                result = createOrderService.createOrder(messageBody);
            } catch (Exception ex) {
                logger.error("创单api消费MQ异常：{}", ex.getMessage(), ex);
            } finally {
                logger.info("创单api消费MQ获取message处理结束");
                //调用MQ生产者
                if (StringUtils.isNotBlank(result)) {
                    String code = String.valueOf(result.hashCode());
                    createOrderApiProducer.sendCreateOrderResultMQ(result, code);
                }
            }
        }else if(StringUtils.equals(topicName,mqConfig.getTfcOrderStatusTopic())){
            logger.info("运输单状态反馈消费MQ:Tag:{},topic:{},key{}",message.getTag(), topicName, key);

            try {
                if(message.getTag().equals("deliveryTag")){
                    logger.info("调度单：{}",message);
                    List<OfcSchedulingSingleFeedbackCondition> ofcSchedulingSingleFeedbackConditions = null;
                    try {
                        ofcSchedulingSingleFeedbackConditions= JSONUtils.jsonToList(messageBody , OfcSchedulingSingleFeedbackCondition.class);
                        for(int i=0;i<ofcSchedulingSingleFeedbackConditions.size();i++){
                            // 保存到数
                            Wrapper<List<OfcPlanFedBackResult>> rmcCompanyLists = ofcPlanFedBackService.schedulingSingleFeedback(ofcSchedulingSingleFeedbackConditions.get(i),userName);
                        }
                    } catch (Exception e) {
                        logger.info(e.getMessage());
                    }
                }else if(message.getTag().equals("transportTag")){
                    logger.info("运输单消费 :{}",message);
                    // 将获取的json格式字符串转换成相应对象
                    List<OfcPlanFedBackCondition> ofcPlanFedBackConditions = null;
                    try {
                        ofcPlanFedBackConditions= JSONUtils.jsonToList(messageBody , OfcPlanFedBackCondition.class);
                        for(int i=0;i<ofcPlanFedBackConditions.size();i++){
                            // 保存到数
                            Wrapper<List<OfcPlanFedBackResult>> rmcCompanyLists = ofcPlanFedBackService.planFedBack(ofcPlanFedBackConditions.get(i),userName);
                        }
                    } catch (Exception e) {
                        logger.info(e.getMessage());
                    }
                }

            } catch (Exception ex) {
                logger.error("运输单状态反馈消费MQ异常:tag:{},topic:{},key{},异常信息:{}",message.getTag(), topicName, key,ex.getMessage(),ex);
//                logger.error(ex.getMessage(), ex);
                return Action.ReconsumeLater;
            }
        }
        return Action.CommitMessage;
    }

}
