package com.xescm.ofc.service;

import com.xescm.ofc.domain.OfcTransplanInfo;
import com.xescm.ofc.domain.OfcTransplanNewstatus;

/**
 * Created by lyh on 2016/10/10.
 */
public interface OfcTransplanNewstatusService extends IService<OfcTransplanNewstatus> {
    int updateByPlanCode(Object key);
}
