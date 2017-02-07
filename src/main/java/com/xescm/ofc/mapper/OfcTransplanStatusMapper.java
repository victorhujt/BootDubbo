package com.xescm.ofc.mapper;

import com.xescm.ofc.domain.OfcTransplanStatus;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.common.Mapper;

@Component
public interface OfcTransplanStatusMapper extends Mapper<OfcTransplanStatus> {
    int updateByPlanCode(Object key);

}