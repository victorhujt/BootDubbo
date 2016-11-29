package com.xescm.ofc.service.impl;

import com.xescm.ofc.model.vo.ofc.OfcBatchOrderVo;
import com.xescm.ofc.mapper.OfcBatchOrderVoMapper;
import com.xescm.ofc.service.OfcBatchOrderVoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by hiyond on 2016/11/25.
 */
@Service
public class OfcBatchOrderVoServiceImpl implements OfcBatchOrderVoService {

    @Autowired
    private OfcBatchOrderVoMapper ofcBatchOrderVoMapper;

    @Override
    public OfcBatchOrderVo queryByBatchNumber(String orderBatchNumber) {
        return ofcBatchOrderVoMapper.queryByBatchNumber(orderBatchNumber);
    }

}
