package com.xescm.ofc.service;

import com.xescm.ofc.domain.OfcDistributionBasicInfo;

/**
 * Created by lyh on 2016/10/10.
 */
public interface OfcDistributionBasicInfoService extends IService<OfcDistributionBasicInfo> {
    int deleteByOrderCode(Object key);
    int updateByOrderCode(Object key);

    OfcDistributionBasicInfo distributionBasicInfoSelect(String code);

    String getOrderCodeByTransCode(String transCode);
}
