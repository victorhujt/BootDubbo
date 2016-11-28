package com.xescm.ofc.service;

import com.xescm.ofc.constant.ResultModel;
import com.xescm.ofc.domain.dto.coo.CreateOrderEntity;
import com.xescm.ofc.exception.BusinessException;

/**
 * Created by hiyond on 2016/11/18.
 */
public interface OfcCreateOrderService {

    int queryCountByOrderStatus(String orderCode, String  orderStatus);

    ResultModel ofcCreateOrder(CreateOrderEntity createOrderEntity, String orderCode) throws BusinessException;

}
