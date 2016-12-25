package com.xescm.ofc.service;

import com.xescm.base.model.wrap.Wrapper;
import com.xescm.ofc.model.dto.coo.CreateOrderEntity;
import com.xescm.ofc.model.dto.epc.CancelOrderDto;
import com.xescm.ofc.model.dto.epc.QueryOrderStatusDto;
import com.xescm.ofc.model.vo.epc.CannelOrderVo;

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

    Wrapper<CannelOrderVo> cancelOrderStateByOrderCode(CancelOrderDto cancelOrderDto);

    String createOrder(String data) throws Exception;

    /**
     * 对接平台定时获取订单中心的鲜易网七天内待发货状态中的订单
     * 参数：客户编码 订单日期开始日期 订单日期结束日期
     * 仅需要订单状态为【待审核】、【已审核】、【任务中】 三种状态的订单列表
     * 返回：List
     * @param queryOrderStatusDto
     * @return
     */
    List<QueryOrderStatusDto> queryOrderStatusList(QueryOrderStatusDto queryOrderStatusDto);
}
