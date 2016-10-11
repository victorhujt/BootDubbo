package com.xescm.ofc.mapper;

import com.xescm.ofc.domain.OfcOrderStatus;
import com.xescm.ofc.utils.MyMapper;
import tk.mybatis.mapper.common.Mapper;

public interface OfcOrderStatusMapper extends Mapper<OfcOrderStatus> {
    int deleteByOrderCode(Object key);
public interface OfcOrderStatusMapper extends MyMapper<OfcOrderStatus> {
}