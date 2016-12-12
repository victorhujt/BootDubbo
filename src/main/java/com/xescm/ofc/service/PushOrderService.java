package com.xescm.ofc.service;

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
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 推送订单到结算中心
 * Created by hiyond on 2016/12/11.
 */
public interface PushOrderService {

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
                                   List<OfcGoodsDetailsInfo> ofcGoodsDetailsInfos);



}
