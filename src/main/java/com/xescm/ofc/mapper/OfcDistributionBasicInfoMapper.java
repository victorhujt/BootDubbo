package com.xescm.ofc.mapper;

import com.xescm.ofc.domain.OfcDistributionBasicInfo;
import com.xescm.ofc.domain.OfcFundamentalInformation;
import com.xescm.ofc.utils.MyMapper;

import java.util.Map;

public interface OfcDistributionBasicInfoMapper extends MyMapper<OfcDistributionBasicInfo> {
    int deleteByOrderCode(Object key);
    int updateByOrderCode(Object key);
    int checkTransCode(OfcDistributionBasicInfo ofcDistributionBasicInfo);
    OfcDistributionBasicInfo ofcDistributionBasicInfoSelect(Object key);
    String getOrderCodeByTransCode(Map<String,String> mapperMap);
}