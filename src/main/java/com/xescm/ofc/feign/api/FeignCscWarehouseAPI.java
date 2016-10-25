package com.xescm.ofc.feign.api;

import com.xescm.ofc.domain.OfcWarehouseInformation;
import com.xescm.uam.utils.wrap.Wrapper;
import feign.Param;
import feign.RequestLine;

import java.util.List;

/**
 * Created by 韦能 on 2016/10/18.
 */
public interface FeignCscWarehouseAPI {
    @RequestLine("GET /api/csc/CscWarehouseRest/getCscWarehouseByCustomerId/{customerId}")
    Wrapper<List<String>> getCscWarehouseByCustomerId(@Param("customerId") String customerId);
    
    @RequestLine("GET /api/rmc/RmcWarehouseRest/getRmcWarehouseByid/{id}")
    public Wrapper<OfcWarehouseInformation> getRmcWarehouseByid(@Param("id") String id);
}
