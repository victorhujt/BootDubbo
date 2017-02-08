package com.xescm.ofc.mapper;

import com.xescm.ofc.domain.OfcTransplanNewstatus;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface OfcTransplanNewstatusMapper extends Mapper<OfcTransplanNewstatus> {
    int updateByPlanCode(Object key);
}