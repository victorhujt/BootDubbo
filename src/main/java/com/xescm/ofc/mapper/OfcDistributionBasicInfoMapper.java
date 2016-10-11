package com.xescm.ofc.mapper;

import com.xescm.ofc.domain.OfcDistributionBasicInfo;
import tk.mybatis.mapper.common.Mapper;

public interface OfcDistributionBasicInfoMapper extends Mapper<OfcDistributionBasicInfo> {
    int deleteByOrderCode(Object key);
    int updateByOrderCode(Object key);
}