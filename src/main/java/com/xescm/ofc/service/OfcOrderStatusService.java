package com.xescm.ofc.service;

import com.xescm.ofc.domain.OfcOrderStatus;

/**
 * Created by lyh on 2016/10/10.
 */
public interface OfcOrderStatusService extends IService<OfcOrderStatus>{
    int deleteByOrderCode(Object key);
}
