package com.xescm.ofc.service;

import com.xescm.ofc.domain.OfcFundamentalInformation;
import com.xescm.ofc.domain.OfcOrderDTO;

/**
 * Created by lyh on 2016/10/10.
 */
public interface OfcFundamentalInformationService extends IService<OfcFundamentalInformation>{

    String orderPlace(OfcOrderDTO ofcOrderDTO);

    OfcFundamentalInformation getFundamentalInformation(String orderCode);
}
