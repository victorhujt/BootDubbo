package com.xescm.ofc.mapper;

import com.xescm.ofc.domain.OfcTransplanStatus;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface OfcTransplanStatusMapper extends Mapper<OfcTransplanStatus> {
    int updateByPlanCode(Object key);

}