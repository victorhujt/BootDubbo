package com.xescm.ofc.service;

import com.xescm.ofc.domain.OrderScreenResult;
import com.xescm.ofc.domain.OrderSearchOperResult;
import com.xescm.ofc.model.dto.form.OrderOperForm;
import org.springframework.stereotype.Service;
import com.xescm.ofc.model.dto.form.OrderOperForm;

import java.util.List;

/**
 * 运营-订单管理
 * Created by hiyond on 2016/11/24.
 */

public interface OfcOrderManageOperService {

    List<OrderScreenResult> queryOrderOper(OrderOperForm form);

    /**
     * 查询订单列表
     *
     * @param form
     * @return list
     */
    List<OrderSearchOperResult> queryOrderList(OrderOperForm form);

    /**
     * 根据订单批次号查询订单列表
     *
     * @param orderBatchNumber
     * @return list
     */
    List<OrderSearchOperResult> queryOrderByOrderBatchNumber(String orderBatchNumber);


}
