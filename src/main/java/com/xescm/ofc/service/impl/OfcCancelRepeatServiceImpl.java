package com.xescm.ofc.service.impl;

import com.xescm.core.utils.JacksonUtil;
import com.xescm.ofc.domain.*;
import com.xescm.ofc.model.dto.tfc.TfcTransport;
import com.xescm.ofc.mq.producer.DefaultMqProducer;
import com.xescm.ofc.service.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by hujintao on 2018/1/10.
 */
@Service
public class OfcCancelRepeatServiceImpl extends BaseService<OfcCancelRepeat> implements OfcCancelRepeatService {
    @Resource
    private DefaultMqProducer defaultMqProducer;
    @Resource
    private OfcFundamentalInformationService ofcFundamentalInformationService;
    @Resource
    private OfcDistributionBasicInfoService ofcDistributionBasicInfoService;

    @Resource
    private OfcWarehouseInformationService ofcWarehouseInformationService;
    @Resource
    private OfcFinanceInformationService ofcFinanceInformationService;
    @Resource
    private OfcGoodsDetailsInfoService ofcGoodsDetailsInfoService;
    @Resource
    private OfcOrderManageService ofcOrderManageService;



    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean sendTotfc(String dataJson,String orderCode) {
        boolean isSend;
        isSend = defaultMqProducer.toSendTfcTransPlanMQ(dataJson,"cancel_repeat", orderCode);
        return isSend;
    }

    @Override
    @Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRES_NEW)
    public void saveCancelOrderRepeat(String orderCode) {
        OfcCancelRepeat r = selectByKey(orderCode);
        if (r != null) {
            logger.info("不一致订单的信息已经存在，订单号为：{}",orderCode);
            return;
        }
        OfcFundamentalInformation ofcFundamentalInformation = ofcFundamentalInformationService.selectByKey(orderCode);
        OfcDistributionBasicInfo ofcDistributionBasicInfo = ofcDistributionBasicInfoService.selectByKey(orderCode);
        OfcWarehouseInformation ofcWarehouseInformation = ofcWarehouseInformationService.queryByOrderCode(orderCode);
        OfcFinanceInformation ofcFinanceInformation = ofcFinanceInformationService.selectByKey(orderCode);
        List<OfcGoodsDetailsInfo> ofcGoodsDetailsInfos = ofcGoodsDetailsInfoService.queryByOrderCode(orderCode);
        TfcTransport tfcTransport = ofcOrderManageService.convertOrderToTfc(ofcFundamentalInformation, ofcFinanceInformation,ofcWarehouseInformation, ofcDistributionBasicInfo, ofcGoodsDetailsInfos);
        try {
            String orderData = JacksonUtil.toJson(tfcTransport);
            OfcCancelRepeat repeat = new OfcCancelRepeat();
            repeat.setOrderCode(orderCode);
            repeat.setOrderData(orderData);
            repeat.setIsSend("0");
            repeat.setCreatedTime(new Date());
            save(repeat);
        } catch (Exception e) {
            logger.error("转化为json数据发生异常：{}", e);
        }

    }
}
