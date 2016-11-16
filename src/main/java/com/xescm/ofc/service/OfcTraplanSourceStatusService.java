package com.xescm.ofc.service;

import com.xescm.ofc.domain.OfcTraplanSourceStatus;

/**
 * Created by lyh on 2016/10/10.
 */
public interface OfcTraplanSourceStatusService extends IService<OfcTraplanSourceStatus> {
    int updateByPlanCode(Object key);
}
