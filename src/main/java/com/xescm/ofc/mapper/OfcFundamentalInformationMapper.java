package com.xescm.ofc.mapper;

import com.xescm.ofc.domain.OfcFundamentalInformation;
import com.xescm.ofc.utils.MyMapper;
import org.apache.ibatis.annotations.Param;

public interface OfcFundamentalInformationMapper extends MyMapper<OfcFundamentalInformation> {
    String getOrderCodeByCustOrderCode(Object key);

    int checkCustOrderCode(OfcFundamentalInformation ofcFundamentalInformation);

    String getOrderCodeByCustOrderCodeAndCustCode(@Param("custOrderCode") String custOrderCode, @Param("custCode") String custCode);


}