package com.xescm.ofc.service.impl;

import com.xescm.ofc.mapper.OfcCreateOrderMapper;
import com.xescm.ofc.service.OfcCreateOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by hiyond on 2016/11/18.
 */

@Service
public class OfcCreateOrderServiceImpl implements OfcCreateOrderService {

    @Autowired
    private OfcCreateOrderMapper createOrdersMapper;

    @Override
    public int queryCountByOrderStatus(String orderCode, String orderStatus) {
        return createOrdersMapper.queryCountByOrderStatus(orderCode, orderStatus);
    }
}
