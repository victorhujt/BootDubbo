package com.xescm.ofc.mapper;

import com.xescm.ofc.domain.OfcTraplanSourceStatus;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface OfcTraplanSourceStatusMapper extends Mapper<OfcTraplanSourceStatus> {
    int updateByPlanCode(Object key);
}