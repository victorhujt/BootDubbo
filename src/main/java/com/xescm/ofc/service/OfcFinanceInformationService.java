package com.xescm.ofc.service;

import com.xescm.ofc.domain.OfcFinanceInformation;

import java.util.List;

/**
 * Created by lyh on 2016/10/10.
 */
public interface OfcFinanceInformationService extends IService<OfcFinanceInformation>{

    OfcFinanceInformation queryByOrderCode(String orderCode);
}
