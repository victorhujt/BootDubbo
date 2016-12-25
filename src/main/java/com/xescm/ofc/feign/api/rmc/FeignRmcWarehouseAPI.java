package com.xescm.ofc.feign.api.rmc;

import com.xescm.base.model.wrap.Wrapper;
import com.xescm.ofc.model.dto.rmc.RmcWarehouse;
import feign.Headers;
import feign.RequestLine;

/**
 * Created by 韦能 on 2016/10/18.
 */
public interface FeignRmcWarehouseAPI {// /api/rmc/warehouse/queryRmcWarehouseById


    /**
     * 根据仓库编码查询仓库
     * @param warehouse
     * @return
     */
    @RequestLine("POST /api/rmc/warehouse/queryRmcWarehouseById")
    @Headers("Content-Type: application/json")
    public Wrapper<RmcWarehouse> queryRmcWarehouseById(RmcWarehouse warehouse);


}
