package com.xescm.ofc.service;

import com.xescm.ofc.domain.OfcFinanceInformation;
import com.xescm.ofc.domain.OfcWarehouseInformation;

import com.xescm.ofc.domain.OfcWarehouseInformation;

import java.util.List;

/**
 * Created by lyh on 2016/10/10.
 */
public interface OfcWarehouseInformationService extends IService<OfcWarehouseInformation>{
    int deleteByOrderCode(Object key);
    int updateByOrderCode(Object key);
    OfcWarehouseInformation warehouseInformationSelect(String code);
    /*List<String> getWarehouseNameListByCustCode(String custCode);*/
    List<OfcWarehouseInformation> getWarehouseListByCustCode(String custCode);
}
