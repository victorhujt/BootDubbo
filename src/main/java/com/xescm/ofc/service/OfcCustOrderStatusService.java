package com.xescm.ofc.service;

import com.xescm.ofc.domain.OfcCustOrderStatus;

import java.util.List;

/**
 * Created by lyh on 2017/9/14.
 */
public interface OfcCustOrderStatusService extends IService<OfcCustOrderStatus> {
    Integer saveOrderStatus(OfcCustOrderStatus ofcOrderStatus);

    List<OfcCustOrderStatus> queryByOrderCode(String orderCode);
}
