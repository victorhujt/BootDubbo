package com.xescm.ofc.service;

import com.xescm.ofc.domain.*;

import java.util.List;

/**
 *
 * @author  lyh on 2017/11/14.
 */
public interface OfcPotNormalRuleService extends IService<OfcPotNormalRule>{


    void insertOrderNormalRule(OfcFundamentalInformation ofcFundamentalInformation
            , OfcDistributionBasicInfo ofcDistributionBasicInfo, List<OfcGoodsDetailsInfo> ofcGoodsDetailsInfos, OfcWarehouseInformation ofcWarehouseInformation);
}
