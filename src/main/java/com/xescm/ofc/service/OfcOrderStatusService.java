package com.xescm.ofc.service;

import com.xescm.ofc.domain.OfcOrderStatus;

import java.util.List;

/**
 * Created by lyh on 2016/10/10.
 */
public interface OfcOrderStatusService extends IService<OfcOrderStatus>{
    int deleteByOrderCode(Object key);
    List<OfcOrderStatus> orderStatusScreen(String code,String followTag);
    //获取订单最新状态
    OfcOrderStatus orderStatusSelect(String code,String followTag);

    OfcOrderStatus queryOrderStateByOrderCode(String orderCode);

    /**
     * 修改订单状态，并标记为作废
     * @param orderCode
     */
    void cancelOrderStateByOrderCode(String orderCode);
}
