package com.xescm.ofc.feign.api;

import com.xescm.ofc.domain.OfcWarehouseInformation;
import com.xescm.ofc.domain.dto.CscWarehouse;
import com.xescm.ofc.domain.dto.RmcWarehouse;
import com.xescm.uam.utils.wrap.Wrapper;
import feign.Param;
import feign.RequestLine;

import java.util.List;

/**
 * Created by 韦能 on 2016/10/18.
 */
public interface FeignCscWarehouseAPI {
    @RequestLine("POST /api/csc/CscWarehouseRest/getCscWarehouseByCustomerId/{customerId}")
    Wrapper<List<CscWarehouse>> getCscWarehouseByCustomerId(@Param("customerId") String customerId);
    
    @RequestLine("POST /api/rmc/RmcWarehouseRest/getRmcWarehouseByid/{id}")
    public Wrapper<RmcWarehouse> getRmcWarehouseByid(@Param("id") String id);
}
