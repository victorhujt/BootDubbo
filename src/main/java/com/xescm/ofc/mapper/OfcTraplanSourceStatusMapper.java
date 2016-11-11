package com.xescm.ofc.mapper;

import com.xescm.ofc.domain.OfcTraplanSourceStatus;
import tk.mybatis.mapper.common.Mapper;

public interface OfcTraplanSourceStatusMapper extends Mapper<OfcTraplanSourceStatus> {
    int updateByPlanCode(Object key);
}