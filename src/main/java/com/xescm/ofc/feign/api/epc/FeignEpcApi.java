package com.xescm.ofc.feign.api.epc;

import com.xescm.base.model.wrap.Wrapper;
import com.xescm.ofc.model.dto.epc.QueryOrderStatusDto;
import feign.Headers;
import feign.RequestLine;

import java.util.List;

/**
 * 对接中心
 * Created by hiyond on 2016/12/19.
 */
public interface FeignEpcApi {

    //对接平台定时获取订单中心的鲜易网七天内待发货状态中的订单,订单中心提供订单列表
    @RequestLine("POST /api/epc/order/queryOrderStatus")
    @Headers("Content-Type: application/json")
    Wrapper<List<QueryOrderStatusDto>> queryOrderStatus(QueryOrderStatusDto queryOrderStatusDto);
}
