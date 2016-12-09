package com.xescm.ofc.feign.api.whc;


import com.xescm.ofc.model.dto.whc.CancelOrderDTO;
import com.xescm.ofc.utils.Response;



import feign.Headers;
import feign.RequestLine;

/**
 * Created by hujintao on 2016/12/2.
 */
public interface FeignWhcOrderAPI {
    @RequestLine("POST /order/cancel/cancelOrder")
    @Headers("Content-Type: application/json")
    Response cancelOrder(CancelOrderDTO cancelOrderDTO);
}
