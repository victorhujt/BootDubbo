package com.xescm.ofc.mapper;

import com.xescm.ofc.domain.OfcWarehouseInformation;
import com.xescm.ofc.utils.MyMapper;
import org.springframework.stereotype.Component;

@Component
public interface OfcWarehouseInformationMapper extends MyMapper<OfcWarehouseInformation> {
    int deleteByOrderCode(Object key);
    int updateByOrderCode(Object key);
    OfcWarehouseInformation ofcWarehouseInformationSelect(Object key);
}