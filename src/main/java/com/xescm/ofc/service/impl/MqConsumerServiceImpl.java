package com.xescm.ofc.service.impl;

import com.xescm.core.utils.JacksonUtil;
import com.xescm.ofc.exception.BusinessException;
import com.xescm.ofc.service.MqConsumerService;
import com.xescm.ofc.service.OfcPlanFedBackService;
import com.xescm.tfc.mq.dto.TfcTransportStateDTO;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @description: mq消费
 * @author: nothing
 * @date: 2017/9/26 14:58
 */
@Service
public class MqConsumerServiceImpl implements MqConsumerService {

    Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @Resource
    private OfcPlanFedBackService ofcPlanFedBackService;

    @Override
    public void transportStateConsumer(String messageBody,ConcurrentHashMap cmap) throws Exception {
        try {
            try {
                // 将获取的json格式字符串转换成相应对象
                TfcTransportStateDTO transportStates;
                transportStates = JacksonUtil.parseJsonWithFormat(messageBody, TfcTransportStateDTO.class);
                ofcPlanFedBackService.planFedBackNew(transportStates, "",cmap);
            } catch (BusinessException ex) {
                logger.error("订单运输状态更新异常: " + ExceptionUtils.getFullStackTrace(ex));
            } catch (Exception ex) {
                logger.error("订单运输状态更新异常: " + ExceptionUtils.getFullStackTrace(ex));
            }
        } catch (BusinessException ex) {
            throw ex;
        } catch (Exception ex) {
            throw ex;
        }
    }
}
