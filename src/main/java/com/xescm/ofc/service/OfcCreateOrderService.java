package com.xescm.ofc.service;

/**
 * Created by hiyond on 2016/11/18.
 */
public interface OfcCreateOrderService {

    int queryCountByOrderStatus(String orderCode, String  orderStatus);

}
