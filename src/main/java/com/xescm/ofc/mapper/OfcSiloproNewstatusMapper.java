package com.xescm.ofc.mapper;

import com.xescm.ofc.domain.OfcSiloproNewstatus;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface OfcSiloproNewstatusMapper extends Mapper<OfcSiloproNewstatus> {

	int updateByPlanCode(Object key);
}