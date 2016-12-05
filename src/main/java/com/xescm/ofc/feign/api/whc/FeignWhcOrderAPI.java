package com.xescm.ofc.feign.api.whc;

import com.xescm.ofc.model.dto.whc.CancelWhcOrderDTO;
import com.xescm.ofc.utils.Response;
import com.xescm.ofc.wrap.Wrapper;
import feign.Headers;
import feign.RequestLine;

/**
 * Created by victor on 2016/12/2.
 */
public interface FeignWhcOrderAPI {

    @RequestLine("POST /epc/inOrder/cancleStatus")
    @Headers("Content-Type: application/json")
    Response inOrderCancel(CancelWhcOrderDTO cancelWhcOrderDTO);

    @RequestLine("POST /epc/outOrder/cancleStatus")
    @Headers("Content-Type: application/json")
    Response outOrderCancel(CancelWhcOrderDTO cancelWhcOrderDTO);
}
