package com.xescm.ofc.mq.consumer;

import com.alibaba.fastjson.JSON;
import com.aliyun.openservices.ons.api.Action;
import com.aliyun.openservices.ons.api.ConsumeContext;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.MessageListener;
import com.xescm.base.model.wrap.Wrapper;
import com.xescm.core.utils.JacksonUtil;
import com.xescm.ofc.config.MqConfig;
import com.xescm.ofc.domain.OfcPlanFedBackCondition;
import com.xescm.ofc.domain.OfcPlanFedBackResult;
import com.xescm.ofc.domain.OfcSchedulingSingleFeedbackCondition;
import com.xescm.ofc.edas.model.dto.whc.FeedBackOrderDto;
import com.xescm.ofc.edas.model.dto.whc.FeedBackOrderStatusDto;
import com.xescm.ofc.model.dto.dms.DmsTransferStatusDto;
import com.xescm.ofc.mq.producer.CreateOrderApiProducer;
import com.xescm.ofc.service.*;
import com.xescm.tfc.edas.model.dto.ofc.req.GoodsAmountSyncDto;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
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
    private OfcOrderStatusService ofcOrderStatusService;

    @Autowired
    private OfcDmsCallbackStatusService ofcDmsCallbackStatusService;

    @Autowired
    private GoodsAmountSyncService goodsAmountSyncService;

    @Autowired
    private MqConfig mqConfig;

    private List<String> keyList = Collections.synchronizedList(new ArrayList<String>());

    @Override
    public Action consume(Message message, ConsumeContext consumeContext) {
        logger.info("OFC消费MQ开始。。。");
        String topicName = message.getTopic();
        String tag=message.getTag();
        String userName ="";
        String key = message.getKey();
        String messageBody = null;
        /*try {
            messageBody = new String(message.getBody(),"GBK");
        } catch (UnsupportedEncodingException e) {
            logger.error("字符編碼發生錯誤");
        }*/
        messageBody = new String(message.getBody());
        logger.info("OFC消费MQ开始。。。MessageBody:" + messageBody + ",topicName:" + topicName + ",tag:" + tag );
        //EPCTopic
        if (StringUtils.equals(topicName, mqConfig.getEpcOrderTopic())) {
            if(message.getTag().equals("xeOrderToOfc")){
                logger.info("创单api消费MQ:Tag:{},topic:{},key{}", message.getTag(), topicName, key);
                String result = null;
                try {
                    if(!keyList.contains(key)) {
                        result = createOrderService.createOrder(messageBody);
                        keyList.add(key);
                    }
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
            }else if(message.getTag().equals("DMS2OFC")){
                //接收分拣中心回传的状态
                logger.info("分拣中心状态反馈的消息体:{}",messageBody);
                logger.info("订单中心消费分拣中心状态反馈开始消费topic:{},tag:{},key{}",topicName,tag,key);
                DmsTransferStatusDto dmsTransferStatusDto = null;
                try {
                    dmsTransferStatusDto = JSON.parseObject(messageBody,DmsTransferStatusDto.class);
                    if(!keyList.contains(key)){
                        ofcDmsCallbackStatusService.receiveDmsCallbackStatus(dmsTransferStatusDto);
                    }else{
                        keyList.add(key);
                    }
                }catch (Exception ex){
                    logger.error("订单中心消费分拣中心状态反馈出错:{}",ex.getMessage(),ex);
                }
            }else if(message.getTag().equals("goodsAmountSync")){//众品订单交货量同步接口
                //接收分拣中心回传的状态
                logger.info("对接中心订单交货量调整消息体:{}",messageBody);
                logger.info("订单中心消费对接中心同步交货量开始消费topic:{},tag:{},key{}",topicName,tag,key);
                GoodsAmountSyncDto goodsAmountSyncDto;
                try {
                    goodsAmountSyncDto = JSON.parseObject(messageBody,GoodsAmountSyncDto.class);
                    if(!keyList.contains(key)){
                        goodsAmountSyncService.GoodsAmountSync(goodsAmountSyncDto);
                    }else{
                        keyList.add(key);
                    }
                } catch (Exception e) {
                    logger.error("订单中心消费对接中心同步交货量出错:{}",e.getMessage(),e);
                }

            }

        }else if(StringUtils.equals(topicName,mqConfig.getTfcOrderStatusTopic())){
            logger.info("运输单状态反馈消费MQ:Tag:{},topic:{},key{}",message.getTag(), topicName, key);

            try {
                if(message.getTag().equals("DeliveryTag")){
                    logger.info("调度单：{}",message);
                    List<OfcSchedulingSingleFeedbackCondition> ofcSchedulingSingleFeedbackConditions = null;
                    TypeReference<List<OfcSchedulingSingleFeedbackCondition>> ofcSchedulingTypeRef = new TypeReference<List<OfcSchedulingSingleFeedbackCondition>>() {
                    };
                    try {
                        ofcSchedulingSingleFeedbackConditions= JacksonUtil.parseJsonWithFormat(messageBody , ofcSchedulingTypeRef);
                        for(int i=0;i<ofcSchedulingSingleFeedbackConditions.size();i++){
                            // 保存到数
                            Wrapper<List<OfcPlanFedBackResult>> rmcCompanyLists = ofcPlanFedBackService.schedulingSingleFeedbackNew(ofcSchedulingSingleFeedbackConditions.get(i),userName);
                        }
                    } catch (Exception e) {
                        logger.error("运输单状态反馈出错:{}",e.getMessage(),e);
                    }
                }else if(message.getTag().equals("TransportTag")){
                    logger.info("运输单消费 :{}",message);
                    // 将获取的json格式字符串转换成相应对象
                    List<OfcPlanFedBackCondition> ofcPlanFedBackConditions = null;
                    TypeReference<List<OfcPlanFedBackCondition>> ofcPlanFedBackTypeRef = new TypeReference<List<OfcPlanFedBackCondition>>() {
                    };
                    try {
                        ofcPlanFedBackConditions= JacksonUtil.parseJsonWithFormat(messageBody,ofcPlanFedBackTypeRef);
                        for(int i=0;i<ofcPlanFedBackConditions.size();i++){
                            // 保存到数
                            Wrapper<List<OfcPlanFedBackResult>> rmcCompanyLists = ofcPlanFedBackService.planFedBackNew(ofcPlanFedBackConditions.get(i),userName);
                        }
                    } catch (Exception e) {
                        logger.error("运输单出错:{}",e.getMessage(),e);
                    }
                }

            } catch (Exception ex) {
                logger.error("运输单状态反馈消费MQ异常:tag:{},topic:{},key{},异常信息:{}",message.getTag(), topicName, key,ex.getMessage(),ex);
            }
         }else if(StringUtils.equals(topicName,mqConfig.getWhcOrderStatusTopic())){
        	logger.info("仓储单状态反馈的消息体为{}:",messageBody);
			logger.info("仓储单状态开始消费");
			try {
			    logger.info("仓储单状态反馈消费MQ:Tag:{},topic:{},key{}",message.getTag(), topicName, key);
                FeedBackOrderStatusDto feedBackOrderStatusDto= JacksonUtil.parseJson(messageBody,FeedBackOrderStatusDto.class);
                ofcOrderStatusService.feedBackStatusFromWhc(feedBackOrderStatusDto);
			} catch (Exception e) {
                logger.error("仓储单状态反馈出现异常{}",e.getMessage(),e);
			}
        }else if(StringUtils.equals(topicName,mqConfig.getWhc2OfcOrderTopic())){
                logger.info("仓储单出入库单实收实出反馈的消息体为{}:",messageBody);
                logger.info("仓储单出入库单实收实出反馈开始消费");
                logger.info("仓储单出入库单实收实出反馈开始消费MQ:Tag:{},topic:{},key{}",message.getTag(), topicName, key);
                try {
                    FeedBackOrderDto feedBackOrderDto= JacksonUtil.parseJson(messageBody,FeedBackOrderDto.class);
                    ofcOrderStatusService.ofcWarehouseFeedBackFromWhc(feedBackOrderDto);
                } catch (Exception e) {
                    logger.error("仓储单出入库单反馈出现异常{}",e.getMessage(),e);
                }
            }
        return Action.CommitMessage;
    }

}
