package com.xescm.ofc.mapper;

import com.xescm.ofc.domain.OfcCustFundamentalInformation;
import com.xescm.ofc.domain.OfcFundamentalInformation;
import com.xescm.ofc.utils.MyMapper;
import org.springframework.stereotype.Repository;
@Repository
public interface OfcCustFundamentalInformationMapper extends MyMapper<OfcCustFundamentalInformation> {
    OfcCustFundamentalInformation queryOrderByInfo(OfcFundamentalInformation information);
}