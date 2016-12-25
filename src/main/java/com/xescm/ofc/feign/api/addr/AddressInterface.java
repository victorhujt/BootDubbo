package com.xescm.ofc.feign.api.addr;

import com.xescm.base.model.wrap.Wrapper;
import com.xescm.ofc.model.dto.addr.QueryAddress;
import feign.Headers;
import feign.RequestLine;

/**
 * @author Chen zhen gang
 * @date 2016/11/10
 * @time 10:48
 */

public interface AddressInterface {
    /*@Headers("Content-Type: application/json")*/
    @RequestLine("POST /api/addr/citypicker/findByCodeAndType")
    @Headers("Content-Type: application/json")
    Wrapper<?> queryAddressByCodeAndType(QueryAddress queryAddress);
}