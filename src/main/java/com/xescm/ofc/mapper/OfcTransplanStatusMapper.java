package com.xescm.ofc.mapper;

import com.xescm.ofc.domain.OfcTransplanStatus;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

public interface OfcTransplanStatusMapper extends Mapper<OfcTransplanStatus> {
    int updateByPlanCode(Object key);

}