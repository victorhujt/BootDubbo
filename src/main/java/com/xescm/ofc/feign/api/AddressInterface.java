package com.xescm.ofc.feign.api;

import com.xescm.ofc.domain.dto.addr.Address;
import com.xescm.ofc.domain.dto.addr.QueryAddress;
import com.xescm.uam.utils.wrap.Wrapper;
import feign.Headers;
import feign.RequestLine;

/**
 * @author Chen zhen gang
 * @date 2016/11/10
 * @time 10:48
 */

public interface AddressInterface {
    @RequestLine("POST /api/addr/citypicker/findByCodeAndType")
    @Headers("Content-Type: application/json")
    Wrapper<Address> queryAddressByCodeAndType(QueryAddress queryAddress);
}