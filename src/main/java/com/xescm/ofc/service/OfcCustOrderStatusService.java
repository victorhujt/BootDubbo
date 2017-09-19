package com.xescm.ofc.service;

import com.xescm.ofc.domain.OfcCustOrderStatus;

/**
 * Created by lyh on 2017/9/14.
 */
public interface OfcCustOrderStatusService extends IService<OfcCustOrderStatus> {
    Integer saveOrderStatus(OfcCustOrderStatus ofcOrderStatus);
}
