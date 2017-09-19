package com.xescm.ofc.mapper;

import com.xescm.ofc.domain.OfcCustDistributionBasicInfo;
import com.xescm.ofc.utils.MyMapper;
import org.springframework.stereotype.Repository;

@Repository
public interface OfcCustDistributionBasicInfoMapper extends MyMapper<OfcCustDistributionBasicInfo> {
    OfcCustDistributionBasicInfo queryByInfo(OfcCustDistributionBasicInfo ofcDistributionBasicInfo);
}