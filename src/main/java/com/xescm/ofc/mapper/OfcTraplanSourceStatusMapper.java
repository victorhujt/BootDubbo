package com.xescm.ofc.mapper;

import com.xescm.ofc.domain.OfcTraplanSourceStatus;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.common.Mapper;

@Component
public interface OfcTraplanSourceStatusMapper extends Mapper<OfcTraplanSourceStatus> {
    int updateByPlanCode(Object key);
}