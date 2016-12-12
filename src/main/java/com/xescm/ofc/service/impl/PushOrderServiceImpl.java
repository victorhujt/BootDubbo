package com.xescm.ofc.service.impl;

import com.xescm.ofc.domain.OfcDistributionBasicInfo;
import com.xescm.ofc.domain.OfcFinanceInformation;
import com.xescm.ofc.domain.OfcFundamentalInformation;
import com.xescm.ofc.domain.OfcGoodsDetailsInfo;
import com.xescm.ofc.exception.BusinessException;
import com.xescm.ofc.feign.api.ac.PushOrderApi;
import com.xescm.ofc.model.dto.ac.AcOrderDto;
import com.xescm.uam.utils.wrap.Wrapper;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 推送订单到结算中心
 * Created by hiyond on 2016/12/11.
 */
//@Service
public class PushOrderServiceImpl {
    private static final Logger logger = LoggerFactory.getLogger(PushOrderServiceImpl.class);

    @Autowired
    private PushOrderApi pullOfcOrder;

    /**
     * 推送订单信息到结算中心
     *
     * @param ofcFundamentalInformation 订单中心基本信息
     * @param ofcFinanceInformation     财务信息
     * @param ofcDistributionBasicInfo  订单中心配送基本信息
     * @param ofcGoodsDetailsInfos      货品明细信息
     */
    public Wrapper<?> pullOfcOrder(OfcFundamentalInformation ofcFundamentalInformation,
                                   OfcFinanceInformation ofcFinanceInformation,
                                   OfcDistributionBasicInfo ofcDistributionBasicInfo,
                                   List<OfcGoodsDetailsInfo> ofcGoodsDetailsInfos) {
        logger.info("推送订单信息到结算中心");
        if (ofcFundamentalInformation == null) {
            throw new BusinessException("ofcFundamentalInformation不能为空");
        }
        if (ofcFinanceInformation == null) {
            throw new BusinessException("ofcFinanceInformation不能为空");
        }
        if (ofcDistributionBasicInfo == null) {
            throw new BusinessException("ofcDistributionBasicInfo不能为空");
        }
        if (CollectionUtils.isEmpty(ofcGoodsDetailsInfos)) {
            throw new BusinessException("ofcGoodsDetailsInfos不能为空");
        }
        AcOrderDto acOrderDto = new AcOrderDto();
        acOrderDto.setOfcFundamentalInformation(ofcFundamentalInformation);
        acOrderDto.setOfcFinanceInformation(ofcFinanceInformation);
        acOrderDto.setOfcDistributionBasicInfo(ofcDistributionBasicInfo);
        acOrderDto.setOfcGoodsDetailsInfoList(ofcGoodsDetailsInfos);
        Wrapper<?> wrapper = null;
        try {
            wrapper = pullOfcOrder.pullOfcOrder(acOrderDto);
        } catch (Exception ex) {
            logger.error("推送订单信息到结算中心失败：", ex.getMessage(), ex);
            throw new BusinessException(ex.getMessage(), ex);
        }
        return wrapper;
    }


}
