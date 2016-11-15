package com.xescm.ofc.feign.api.tfc;
import com.xescm.ofc.model.dto.tfc.TransportNoDTO;
import com.xescm.ofc.utils.Response;
import feign.Headers;
import feign.RequestLine;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by sql on 2016/11/15.
 */
public interface FeignTfcTransPlanApi {


    @RequestLine("POST /restApi/tfc/cancelTransport")
    @Headers("Content-Type: application/json")
    public Response cancelTransport(TransportNoDTO transportNoDTO);


}
