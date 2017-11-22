package com.xescm.ofc.service;

import com.xescm.ofc.domain.OfcCustDistributionBasicInfo;

/**
 *
 * Created by lyh on 2017/9/14.
 */
public interface OfcCustDistributionBasicInfoService extends IService<OfcCustDistributionBasicInfo>{
    OfcCustDistributionBasicInfo queryByOrderCode(String orderCode);
}
