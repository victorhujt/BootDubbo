package com.xescm.ofc.feign.api.epc;

import com.xescm.ofc.model.dto.epc.CancelOrderDto;
import com.xescm.uam.utils.wrap.Wrapper;
import feign.Headers;
import feign.RequestLine;

/**
 * @author Chen zhen gang
 * @date 2016/11/10
 * @time 10:48
 */

public interface OrderCancellnterface {
    /**
     * 对接中心订单取消
     * @param cancelOrderDto
     * @return
     */
    /*@Headers("Content-Type: application/json")*/
    @RequestLine("POST /api/orderCancel")
    @Headers("Content-Type: application/json")
    Wrapper<?> OrderCancel(CancelOrderDto cancelOrderDto);
}