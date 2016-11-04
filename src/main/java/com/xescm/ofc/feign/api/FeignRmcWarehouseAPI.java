package com.xescm.ofc.feign.api;

import com.xescm.ofc.domain.dto.rmc.RmcWarehouse;
import com.xescm.uam.utils.wrap.Wrapper;
import feign.Headers;
import feign.RequestLine;

/**
 * Created by 韦能 on 2016/10/18.
 */
public interface FeignRmcWarehouseAPI {// /api/rmc/warehouse/queryRmcWarehouseById


    @RequestLine("POST /api/rmc/warehouse/queryRmcWarehouseById")
    @Headers("Content-Type: application/json")
    public Wrapper<RmcWarehouse> queryRmcWarehouseById(RmcWarehouse warehouse);


}
