package com.xescm.ofc.service;

import com.xescm.ofc.domain.OfcCustFundamentalInformation;

/**
 * Created by lyh on 2017/9/14.
 */
public interface OfcCustFundamentalInformationService extends IService<OfcCustFundamentalInformation>{
    OfcCustFundamentalInformation queryByOrderCode(String orderCode);
}
