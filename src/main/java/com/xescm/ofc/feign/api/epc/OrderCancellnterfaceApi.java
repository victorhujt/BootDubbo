package com.xescm.ofc.feign.api.epc;

import com.xescm.ofc.model.vo.epc.CannelOrderVo;
import com.xescm.uam.utils.wrap.Wrapper;
import feign.Headers;
import feign.RequestLine;

/**
 * 取消订单接口
 */
public interface OrderCancellnterfaceApi {

    @RequestLine("POST /api/epc/order/orderCancel")
    @Headers("Content-Type: application/json")
    Wrapper<CannelOrderVo> orderCancel(String custOrderCode);
}