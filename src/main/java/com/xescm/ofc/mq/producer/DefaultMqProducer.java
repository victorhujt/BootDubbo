package com.xescm.ofc.mq.producer;

import com.aliyun.openservices.ons.api.*;
import com.aliyun.openservices.ons.api.transaction.LocalTransactionExecuter;
import com.aliyun.openservices.ons.api.transaction.TransactionProducer;
import com.aliyun.openservices.ons.api.transaction.TransactionStatus;
import com.xescm.ofc.config.MqConfig;
import com.xescm.ofc.domain.OfcPlannedDetail;
import com.xescm.ofc.domain.ofcSiloprogramStatusFedBackCondition;
import com.xescm.ofc.domain.ofcWarehouseFeedBackCondition;
import com.xescm.ofc.utils.JSONUtils;
import com.xescm.ofc.utils.MQUtil;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

/**
 * Created by MT on 2016/11/11.
 */
@Component
public class DefaultMqProducer {
    private Logger logger = LoggerFactory.getLogger(DefaultMqProducer.class);

    /**
     * MQ发送定时消息示例 Demo
     */
    @Resource
    MqConfig mqConfig;
    @Resource
    Producer producer;

    public void toSendTfcTransPlanMQ(String jsonStr,String code) {
        logger.info(mqConfig.getOfc2TfcOrderTopic()+"开始生产");
        Message message = new Message(mqConfig.getOfc2TfcOrderTopic(), null,jsonStr.getBytes());
        message.setKey(code);

        SendResult sendResult = producer.send(message);
        if (sendResult != null) {
            logger.info("{0}消费成功,消费时间为{1},MsgID为{2}",mqConfig.getOfc2TfcOrderTopic(),new Date(),sendResult.getMessageId());
        }
    }

    /**
     * 仓储计划单推送到仓储中心
     * @param jsonStr
     * @param code
     * @param tag
     */
    public boolean toSendWhc(String jsonStr,String code,String tag) {
        boolean isSend=false;
        logger.info(mqConfig.getOfc2WhcOrderTopic()+"开始生产");
        Message message = new Message(mqConfig.getOfc2WhcOrderTopic(), tag,jsonStr.getBytes());
        //入库单收货反馈测试
//        ofcSiloprogramStatusFedBackCondition c=new ofcSiloprogramStatusFedBackCondition();
//        ofcWarehouseFeedBackCondition c1=new ofcWarehouseFeedBackCondition();
//        c1.setPlanCode(code);
//        List<OfcPlannedDetail> listp=new ArrayList<>();
//        List<ofcWarehouseFeedBackCondition> list2=new ArrayList<>();
//        OfcPlannedDetail d=new OfcPlannedDetail();
//        d.setPlanCode(code);
//        d.setRealQuantity(BigDecimal.valueOf(10));
//        c.setPlanCode(code);
//        d.setGoodsCode("SC-0001");
//        d.setGoodsName("龙虾");
//        d.setGoodsSpec("10KG装");
//        listp.add(d);
//        c1.setPlannedDetails(listp);
//        list2.add(c1);
//        String shjson= JSONUtils.objectToJson(list2);
//        tag="620";
//        c.setStatus("00");
//        c.setTraceTime(new Date());
//        List<ofcSiloprogramStatusFedBackCondition> list=new ArrayList<>();
//        list.add(c);
//        String json= JSONUtils.objectToJson(list);
//        Message message1 = new Message(mqConfig.getOfcOrderStatusTopic(), tag,json.getBytes());
//        Message message2 = new Message(mqConfig.getOfc2WhcOrderTopic(), tag,shjson.getBytes());
            message.setKey(code);
         SendResult sendResult = producer.send(message);
      //  SendResult sendResult= producer.send(message1);
        //producer.send(message2);
        if (sendResult != null) {
            isSend=true;
            logger.info("{0}生产成功,tag为{1}生产时间为{2},MsgID为{3}",mqConfig.getOfc2WhcOrderTopic(),tag,new Date(),sendResult.getMessageId());
        }
        return isSend;
    }


    public void testSendWhcOrderStatus(){
        ofcSiloprogramStatusFedBackCondition c=new ofcSiloprogramStatusFedBackCondition();
        c.setPlanCode("");
        c.setStatus("开始");
        c.setTraceTime(new Date());
        String json= JSONUtils.objectToJson(c);
        Message message = new Message(mqConfig.getOfc2WhcOrderTopic(),"",json.getBytes());
        SendResult sendResult = producer.send(message);


    }



}