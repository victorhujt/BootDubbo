package com.xescm.ofc.feign.api.csc;

import com.xescm.ofc.model.dto.csc.CscWarehouse;
import com.xescm.uam.utils.wrap.Wrapper;
import feign.Headers;
import feign.RequestLine;

import java.util.List;

/**
 * Created by 韦能 on 2016/10/18.
 */
public interface FeignCscWarehouseAPI {
    /**
     * 查询客户所属仓库
     * @param cscWarehouse
     * @return
     */
    @RequestLine("POST /api/csc/warehouse/queryWarehouseByGroupId")
    @Headers("Content-Type: application/json")
    public Wrapper<List<CscWarehouse>> getCscWarehouseByCustomerId(CscWarehouse cscWarehouse);
}
