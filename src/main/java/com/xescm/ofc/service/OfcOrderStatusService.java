package com.xescm.ofc.service;

import com.xescm.ofc.domain.OfcOrderStatus;

import java.util.List;

/**
 * Created by lyh on 2016/10/10.
 */
public interface OfcOrderStatusService extends IService<OfcOrderStatus>{
    int deleteByOrderCode(Object key);
    List<OfcOrderStatus> orderStatusScreen(Object key);
    //获取订单最新状态
    OfcOrderStatus getOrderStatus(String orderCode);
}
