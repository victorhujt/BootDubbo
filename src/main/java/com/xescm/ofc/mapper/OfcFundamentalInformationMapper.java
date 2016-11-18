package com.xescm.ofc.mapper;

import com.xescm.ofc.domain.OfcFundamentalInformation;
import com.xescm.ofc.utils.MyMapper;

public interface OfcFundamentalInformationMapper extends MyMapper<OfcFundamentalInformation> {
    String getOrderCodeByCustOrderCode(Object key);

    int checkCustOrderCode(OfcFundamentalInformation ofcFundamentalInformation);
}