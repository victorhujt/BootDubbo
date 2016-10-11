package com.xescm.ofc.mapper;

import com.xescm.ofc.domain.OfcWarehouseInformation;
import tk.mybatis.mapper.common.Mapper;

public interface OfcWarehouseInformationMapper extends Mapper<OfcWarehouseInformation> {
    int deleteByOrderCode(Object key);
    int updateByOrderCode(Object key);
}