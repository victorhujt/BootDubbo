package com.xescm.ofc.service;

import com.xescm.ofc.model.dto.coo.CreateOrderEntity;
import com.xescm.ofc.model.vo.epc.CannelOrderVo;
import com.xescm.uam.utils.wrap.Wrapper;

import java.util.List;

/**
 * 创单
 * Created by hiyond on 2016/11/15.
 */
public interface CreateOrderService {

    /**
     * 创建订单
     *
     * @return boolean
     */
    boolean CreateOrders(List<CreateOrderEntity> list);

    Wrapper<CannelOrderVo> cancelOrderStateByOrderCode(String custOrderCode);

    String createOrder(String data) throws Exception;


}
