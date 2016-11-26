package com.xescm.ofc.service;

import com.xescm.ofc.domain.OfcBatchOrderVo;

import java.util.List;

/**
 * Created by hiyond on 2016/11/25.
 */
public interface OfcBatchOrderVoService {

    OfcBatchOrderVo queryByBatchNumber(String orderBatchNumber);

}
