package com.xescm.ofc.mq.consumer;

import com.alibaba.fastjson.JSON;
import com.aliyun.openservices.ons.api.Action;
import com.aliyun.openservices.ons.api.ConsumeContext;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.MessageListener;
import com.xescm.core.utils.JacksonUtil;
import com.xescm.ofc.config.MqConfig;
import com.xescm.ofc.domain.OfcInterfaceReceiveLog;
import com.xescm.ofc.edas.enums.LogBusinessTypeEnum;
import com.xescm.ofc.edas.enums.LogInterfaceTypeEnum;
import com.xescm.ofc.edas.enums.LogSourceSysEnum;
import com.xescm.ofc.edas.model.dto.ofc.OfcCreateOrderDTO;
import com.xescm.ofc.edas.model.dto.whc.FeedBackOrderDto;
import com.xescm.ofc.edas.model.dto.whc.FeedBackOrderStatusDto;
import com.xescm.ofc.service.MqConsumerService;
import com.xescm.ofc.service.OfcInterfaceReceiveLogService;
import com.xescm.ofc.service.OfcOrderStatusService;
import com.xescm.tfc.edas.model.constants.TfcMqTagConstants.Tfc2OfcStateTopicTag;
import com.xescm.tfc.edas.model.dto.ofc.req.GoodsAmountSyncDto;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import static com.xescm.ofc.constant.BaseConstant.MQ_TAG_GoodsAmountSync;
import static com.xescm.ofc.constant.BaseConstant.MQ_TAG_OrderToOfc;

/**
 * @author hujintao
 * 创单api消费MQ
 */
@Service
public class CreateOrderApiConsumer implements MessageListener {

    private Logger logger = LoggerFactory.getLogger(CreateOrderApiConsumer.class);

    @Resource
    private MqConsumerService mqConsumerService;

    @Resource
    private OfcOrderStatusService ofcOrderStatusService;

    @Resource
    private MqConfig mqConfig;
    @Resource
    private OfcInterfaceReceiveLogService receiveLogService;

    private  static ConcurrentHashMap MAP = new ConcurrentHashMap();

    @Override
    public Action consume(Message message, ConsumeContext consumeContext) {
        logger.info("OFC消费MQ开始。。。");
        String topicName = message.getTopic();
        String tag = message.getTag();
        String key = message.getKey();
        String messageId = message.getMsgID();
        String messageBody;
        messageBody = new String(message.getBody(), StandardCharsets.UTF_8);
        logger.info("OFC消费MQ开始。。。MessageBody:" + messageBody + ",topicName:" + topicName + ",tag:" + tag );
        //EPCTopic
        if (StringUtils.equals(topicName, mqConfig.getEpcOrderTopic())) {
            if (MQ_TAG_OrderToOfc.equals(message.getTag())) {
                logger.info("创单api消费MQ:Tag:{},topic:{},key{}", message.getTag(), topicName, key);
                try {
                    List<OfcCreateOrderDTO> orderEntities = JSON.parseArray(messageBody,OfcCreateOrderDTO.class);
                    for (OfcCreateOrderDTO orderEntity : orderEntities) {
                        String custOrderCode = orderEntity.getCustOrderCode();
                        OfcInterfaceReceiveLog receiveLog = new OfcInterfaceReceiveLog();
                        receiveLog.setLogBusinessType(LogBusinessTypeEnum.EDI_ORDER.getCode());
                        receiveLog.setLogFromSys(LogSourceSysEnum.EPC.getCode());
                        receiveLog.setRefNo(custOrderCode);
                        receiveLog.setLogType(LogInterfaceTypeEnum.MQ.getCode());
                        receiveLog.setLogData(JacksonUtil.toJson(orderEntity));
                        receiveLogService.insertOfcInterfaceReceiveLogWithTask(receiveLog);
                    }
                } catch (Exception ex) {
                    logger.error("创单api消费MQ异常：{}", ex.getMessage(), ex);
                    return Action.ReconsumeLater;
                } finally {
                    logger.info("创单api消费MQ获取message处理结束");
                }
                //众品订单交货量同步接口
            } else if (MQ_TAG_GoodsAmountSync.equals(message.getTag())) {
                //接收分拣中心回传的状态
                logger.info("对接中心订单交货量调整消息体:{}", messageBody);
                logger.info("订单中心消费对接中心同步交货量开始消费topic:{},tag:{},key{}", topicName, tag, key);
                try {
                    GoodsAmountSyncDto goodsAmountSyncDto = JSON.parseObject(messageBody, GoodsAmountSyncDto.class);
                    String custOrderCode = goodsAmountSyncDto.getCustOrderCode();
                    OfcInterfaceReceiveLog receiveLog = new OfcInterfaceReceiveLog();
                    receiveLog.setLogBusinessType(LogBusinessTypeEnum.EDI_GOODS_AMOUNT.getCode());
                    receiveLog.setLogFromSys(LogSourceSysEnum.EPC.getCode());
                    receiveLog.setRefNo(custOrderCode);
                    receiveLog.setLogType(LogInterfaceTypeEnum.MQ.getCode());
                    receiveLog.setLogData(JacksonUtil.toJson(goodsAmountSyncDto));
                    receiveLogService.insertOfcInterfaceReceiveLogWithTask(receiveLog);
                } catch (Exception e) {
                    logger.error("订单中心消费对接中心同步交货量出错:{}", e.getMessage(), e);
                    return Action.ReconsumeLater;
                }
            }
        } else if (StringUtils.equals(topicName,mqConfig.getTfc2OfcOrderPoTopic())) {
            try {
                if (!Tfc2OfcStateTopicTag.PUSH_NOT_SCHEDULE_TAG.getTag().equals(tag) && // 未调度
                    !Tfc2OfcStateTopicTag.PUSH_CANCEL_TAG.getTag().equals(tag) &&       // 取消
                    !Tfc2OfcStateTopicTag.PUSH_SIGN_DOC_TAG.getTag().equals(tag)) {     // 签收单
                    logger.info("订单中心消费订单状态MQ<运输>: topic => {}, Tag => {}, key: {}, messageId: {}, message: {}", topicName, message.getTag(), key, messageId, messageBody);
                    try {
                        mqConsumerService.transportStateConsumer(messageBody);
                    } catch (Exception e) {
                        logger.error("订单运输状态更新异常: 异常详情 => {}", e);
                    }
                }
            } catch (Exception ex) {
                logger.error("运输单状态反馈消费MQ异常:tag:{},topic:{},key{},异常信息:{}", message.getTag(), topicName, key, ex.getMessage(), ex);
            }
        } else if (StringUtils.equals(topicName, mqConfig.getWhcOrderStateTopic())) {
            logger.info("仓储单状态反馈的消息体为{}:", messageBody);
            logger.info("仓储单状态开始消费");
            if ("InOrderFinishedTag ".equals(tag) ||"OutOrderFinishedTag ".equals(tag)) {
                try {
                    logger.info("仓储单状态反馈消费MQ:Tag:{},topic:{},key{}", message.getTag(), topicName, key);
                    FeedBackOrderStatusDto feedBackOrderStatusDto = JacksonUtil.parseJson(messageBody, FeedBackOrderStatusDto.class);
                    ofcOrderStatusService.feedBackStatusFromWhc(feedBackOrderStatusDto);
                } catch (Exception e) {
                    logger.error("仓储单状态反馈出现异常{}", e.getMessage(), e);
                }
            }
        } else if (StringUtils.equals(topicName,mqConfig.getWhcOrderResultTopic())) {
            logger.info("仓储单出入库单实收实出反馈的消息体为{}:", messageBody);
            logger.info("仓储单出入库单实收实出反馈开始消费");
            logger.info("仓储单出入库单实收实出反馈开始消费MQ:Tag:{},topic:{},key{}", message.getTag(), topicName, key);
            try {
                FeedBackOrderDto feedBackOrderDto = JacksonUtil.parseJson(messageBody, FeedBackOrderDto.class);
                ofcOrderStatusService.ofcWarehouseFeedBackFromWhc(feedBackOrderDto);
            } catch (Exception e) {
                logger.error("仓储单出入库单反馈出现异常{}", e.getMessage(), e);
            }
        }
        return Action.CommitMessage;
    }

}
