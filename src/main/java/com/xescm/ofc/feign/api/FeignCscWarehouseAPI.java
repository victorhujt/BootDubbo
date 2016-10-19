package com.xescm.ofc.feign.api;

import com.xescm.uam.utils.wrap.Wrapper;
import feign.Param;
import feign.RequestLine;

/**
 * Created by 韦能 on 2016/10/18.
 */
public interface FeignCscWarehouseAPI {
    @RequestLine("GET /api/csc/CscWarehouseRest/getCscWarehouseByCustomerId/{customerId}")
    Wrapper<?> getCscWarehouseByCustomerId(@Param("customerId") String customerId);
    
    @RequestLine("GET /api/rmc/RmcWarehouseRest/getRmcWarehouseByid/{id}")
    public Wrapper<?> getRmcWarehouseByid(@Param("id") String id);
}
