package com.xescm.ofc.mapper;

import com.xescm.ofc.domain.OfcTransplanNewstatus;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.common.Mapper;

@Component
public interface OfcTransplanNewstatusMapper extends Mapper<OfcTransplanNewstatus> {
    int updateByPlanCode(Object key);
}