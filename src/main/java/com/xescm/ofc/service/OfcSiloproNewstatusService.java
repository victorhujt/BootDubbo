package com.xescm.ofc.service;

import com.xescm.ofc.domain.OfcSiloproNewstatus;

/**
 * Created by lyh on 2016/10/10.
 */
public interface OfcSiloproNewstatusService extends IService<OfcSiloproNewstatus> {
	
    int updateByPlanCode(Object key);
}
