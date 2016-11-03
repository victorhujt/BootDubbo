package com.xescm.ofc.mapper;

import com.xescm.ofc.domain.OfcSiloproStatus;
import tk.mybatis.mapper.common.Mapper;

public interface OfcSiloproStatusMapper extends Mapper<OfcSiloproStatus> {
    int updateByPlanCode(Object key);
}