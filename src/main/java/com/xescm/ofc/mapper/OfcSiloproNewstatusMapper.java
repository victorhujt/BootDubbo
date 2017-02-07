package com.xescm.ofc.mapper;

import com.xescm.ofc.domain.OfcSiloproNewstatus;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.common.Mapper;

@Component
public interface OfcSiloproNewstatusMapper extends Mapper<OfcSiloproNewstatus> {

	int updateByPlanCode(Object key);
}