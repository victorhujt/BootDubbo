package com.xescm.ofc.service.impl;

import com.xescm.ofc.domain.OrderScreenCondition;
import com.xescm.ofc.domain.OrderScreenResult;
import com.xescm.ofc.exception.BusinessException;
import com.xescm.ofc.mapper.OfcOrderScreenMapper;
import com.xescm.ofc.service.OfcOrderScreenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by lyh on 2016/10/10.
 */
@Service
public class OfcOrderScreenServiceImpl extends BaseService<OrderScreenResult> implements OfcOrderScreenService {
    @Autowired
    private OfcOrderScreenMapper ofcOrderScreenMapper;

    @Override
    public List<OrderScreenResult> orderScreen(OrderScreenCondition orderScreenCondition) {

        if (orderScreenCondition == null){

        }
        List<OrderScreenResult> orderSearchResults = ofcOrderScreenMapper.orderScreen(orderScreenCondition);
        if(orderSearchResults == null){

        }
        return orderSearchResults;

    }
}
