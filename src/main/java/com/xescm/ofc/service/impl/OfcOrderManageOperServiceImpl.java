package com.xescm.ofc.service.impl;

import com.xescm.ofc.domain.OrderFollowOperResult;
import com.xescm.ofc.domain.OrderScreenResult;
import com.xescm.ofc.domain.OrderSearchOperResult;
import com.xescm.ofc.mapper.OfcOrderOperMapper;
import com.xescm.ofc.mapper.OfcOrderScreenMapper;
import com.xescm.ofc.model.dto.form.OrderOperForm;
import com.xescm.ofc.service.OfcOrderManageOperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by hiyond on 2016/11/24.
 */
@Service
public class OfcOrderManageOperServiceImpl implements OfcOrderManageOperService {

    @Autowired
    private OfcOrderScreenMapper ofcOrderScreenMapper;
    @Autowired
    private OfcOrderOperMapper ofcOrderOperMapper;

    @Override
    public List<OrderScreenResult> queryOrderOper(OrderOperForm form) {
        return ofcOrderScreenMapper.queryOrderOper(form);
    }

    @Override
    public List<OrderSearchOperResult> queryOrderList(OrderOperForm form) {
        return ofcOrderOperMapper.queryOrderList(form);
    }

    @Override
    public List<OrderSearchOperResult> queryOrderByOrderBatchNumber(String orderBatchNumber) {
        return ofcOrderOperMapper.queryOrderByOrderBatchNumber(orderBatchNumber);
    }

    @Override
    public List<OrderFollowOperResult> queryOrder(String code, String searchType) {
        return ofcOrderOperMapper.queryOrder(code, searchType);
    }
}
