package com.xescm.ofc.service;

import com.xescm.ofc.constant.ResultModel;
import com.xescm.ofc.domain.*;
import com.xescm.ofc.domain.dto.coo.CreateOrderEntity;

import java.util.List;

/**
 * 创单
 * Created by hiyond on 2016/11/15.
 */
public interface CreateOrderService {

    /**
     * 创建订单
     *
     * @return boolean
     */
    boolean CreateOrders(List<CreateOrderEntity> list);

    ResultModel cancelOrderStateByOrderCode(String custOrderCode, String custCode);

    String createOrder(String data) throws Exception;


}
