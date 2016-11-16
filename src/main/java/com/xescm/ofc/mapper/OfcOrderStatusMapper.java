package com.xescm.ofc.mapper;

import com.xescm.ofc.domain.OfcOrderStatus;
import com.xescm.ofc.utils.MyMapper;

import java.util.List;
import java.util.Map;

public interface OfcOrderStatusMapper extends MyMapper<OfcOrderStatus> {
    int deleteByOrderCode(Object key);
    List<OfcOrderStatus> orderStatusScreen(Map<String,String> mapperMap);
    OfcOrderStatus orderStatusSelect(Map<String,String> mapperMap);
}