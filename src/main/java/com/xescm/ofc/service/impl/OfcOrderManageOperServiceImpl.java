package com.xescm.ofc.service.impl;

import com.xescm.ofc.domain.OrderScreenResult;
import com.xescm.ofc.model.dto.form.OrderOperForm;
import com.xescm.ofc.mapper.OfcOrderScreenMapper;
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

    @Override
    public List<OrderScreenResult> queryOrderOper(OrderOperForm form) {
        return ofcOrderScreenMapper.queryOrderOper(form);
    }
}
