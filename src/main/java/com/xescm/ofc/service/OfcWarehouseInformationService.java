package com.xescm.ofc.service;

import com.xescm.ofc.domain.OfcFinanceInformation;
import com.xescm.ofc.domain.OfcWarehouseInformation;

import com.xescm.ofc.domain.OfcWarehouseInformation;

/**
 * Created by lyh on 2016/10/10.
 */
public interface OfcWarehouseInformationService extends IService<OfcWarehouseInformation>{
    int deleteByOrderCode(Object key);
    int updateByOrderCode(Object key);
    //获取仓储订单信息
    OfcWarehouseInformation warehouseInformationSelect(String code);
}
