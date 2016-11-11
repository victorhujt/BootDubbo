package com.xescm.ofc.service;

import com.xescm.ofc.domain.OrderScreenCondition;
import com.xescm.ofc.domain.OrderScreenResult;

import java.util.List;

/**
 * Created by lyh on 2016/10/10.
 */
public interface OfcOrderScreenService extends IService<OrderScreenResult>{
    /*订单查询条件筛选结果*/
    List<OrderScreenResult> orderScreen(OrderScreenCondition orderScreenCondition);
}
