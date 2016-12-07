package com.xescm.ofc.feign.api.whc;


import com.xescm.ofc.model.dto.whc.CancelOrderDTO;
import com.xescm.ofc.utils.Response;



import feign.Headers;
import feign.RequestLine;

/**
 * Created by victor on 2016/12/2.
 */
public interface FeignWhcOrderAPI {

    @RequestLine("POST /epc/inOrder/cancleStatus")
    @Headers("Content-Type: application/json")
    Response inOrderCancel(CancelOrderDTO cancelOrderDTO);

    @RequestLine("POST /epc/outOrder/cancleStatus")
    @Headers("Content-Type: application/json")
    Response outOrderCancel(CancelOrderDTO cancelOrderDTO
    );
}
