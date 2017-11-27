package com.xescm.ofc.service;

import com.xescm.ofc.domain.OfcCustWarehouseInformation;

/**
 *
 * Created by lyh on 2017/9/14.
 */
public interface OfcCustWarehouseInformationService extends IService<OfcCustWarehouseInformation>{
    OfcCustWarehouseInformation queryByOrderCode(String orderCode);
}
