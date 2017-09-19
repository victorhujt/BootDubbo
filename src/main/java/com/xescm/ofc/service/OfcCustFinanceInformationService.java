package com.xescm.ofc.service;

import com.xescm.ofc.domain.OfcCustFinanceInformation;

/**
 * Created by lyh on 2017/9/15.
 */
public interface OfcCustFinanceInformationService extends IService<OfcCustFinanceInformation> {
    OfcCustFinanceInformation queryByOrderCode(String orderCode);
}
