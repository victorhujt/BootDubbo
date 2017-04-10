package com.xescm.ofc.service.impl;

import com.xescm.ofc.domain.OrderScreenCondition;
import com.xescm.ofc.domain.OrderScreenResult;
import com.xescm.ofc.mapper.OfcOrderScreenMapper;
import com.xescm.ofc.service.OfcOrderScreenService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 订单查询
 * Created by lyh on 2016/10/10.
 */
@Service
@Transactional
public class OfcOrderScreenServiceImpl extends BaseService<OrderScreenResult> implements OfcOrderScreenService {
    @Resource
    private OfcOrderScreenMapper ofcOrderScreenMapper;

    @Override
    public List<OrderScreenResult> orderScreen(OrderScreenCondition orderScreenCondition) {
        return ofcOrderScreenMapper.orderScreen(orderScreenCondition);
    }

    @Override
    public List<String> searchOverallOrder(String code) {
        return ofcOrderScreenMapper.searchOverallOrder(code);
    }
}
