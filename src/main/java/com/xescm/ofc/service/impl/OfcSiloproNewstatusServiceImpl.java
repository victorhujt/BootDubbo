package com.xescm.ofc.service.impl;

import com.xescm.ofc.domain.OfcSiloproNewstatus;
import com.xescm.ofc.mapper.OfcSiloproNewstatusMapper;
import com.xescm.ofc.service.OfcSiloproNewstatusService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by lyh on 2016/10/10.
 */
@Service
@Transactional
public class OfcSiloproNewstatusServiceImpl extends BaseService<OfcSiloproNewstatus> implements OfcSiloproNewstatusService {

	
	 @Autowired
	    private OfcSiloproNewstatusMapper ofcSiloproNewStatusMapper;
	@Override
	public int updateByPlanCode(Object key) {
		ofcSiloproNewStatusMapper.updateByPlanCode(key);
		  return 0;
	}
}
