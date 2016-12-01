package com.xescm.ofc.service;

import com.xescm.ofc.domain.OfcSiloproStatus;
import com.xescm.ofc.domain.ofcSiloprogramStatusFedBackCondition;

/**
 * Created by lyh on 2016/10/10.
 */
public interface OfcSiloproStatusService extends IService<OfcSiloproStatus> {
    int updateByPlanCode(Object key);
    
    public void feedBackSiloproStatusFromWhc(ofcSiloprogramStatusFedBackCondition condition);
    
}
