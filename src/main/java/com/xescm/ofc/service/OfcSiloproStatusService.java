package com.xescm.ofc.service;

import com.xescm.ofc.domain.OfcSiloproStatus;
import com.xescm.ofc.domain.OfcSiloprogramStatusFedBackCondition;
import com.xescm.ofc.domain.ofcWarehouseFeedBackCondition;

import java.util.List;

/**
 * Created by lyh on 2016/10/10.
 */
public interface OfcSiloproStatusService extends IService<OfcSiloproStatus> {
    int updateByPlanCode(Object key);
    public void feedBackSiloproStatusFromWhc(OfcSiloprogramStatusFedBackCondition condition);
    public void ofcWarehouseFeedBackFromWhc(ofcWarehouseFeedBackCondition condition);
    List<OfcSiloproStatus> queryUncompletedPlanCodesByOrderCode(String orderCode);
}
