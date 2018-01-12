package com.xescm.ofc.service.impl;

import com.xescm.core.exception.BusinessException;
import com.xescm.ofc.domain.OfcCancelRepeat;
import com.xescm.ofc.mq.producer.DefaultMqProducer;
import com.xescm.ofc.service.OfcCancelRepeatService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Created by hujintao on 2018/1/10.
 */
@Service
public class OfcCancelRepeatServiceImpl extends BaseService<OfcCancelRepeat> implements OfcCancelRepeatService {
    @Resource
    private DefaultMqProducer defaultMqProducer;
    @Override
    public boolean sendTotfc(String orderCode) {
        boolean isSend;
        OfcCancelRepeat cancelRepeat = selectByKey(orderCode);
        if (cancelRepeat == null) {
            throw new BusinessException("该订单不存在取消状态不一致的问题");
        }
        if ("1".equals(cancelRepeat.getIsSend())) {
            throw new BusinessException("该订单已经处理");
        }
        isSend = defaultMqProducer.toSendTfcTransPlanMQ(cancelRepeat.getOrderData(),"cancel_repeat", orderCode);
        if (isSend) {
            cancelRepeat.setIsSend("1");
            cancelRepeat.setUpdateTime(new Date());
            update(cancelRepeat);
        }
        return isSend;
    }
}
