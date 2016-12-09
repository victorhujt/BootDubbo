package com.xescm.ofc.feign.api.csc;

import com.xescm.ofc.model.dto.csc.QueryStoreDto;
import com.xescm.ofc.model.vo.csc.CscStorevo;
import com.xescm.uam.utils.wrap.Wrapper;
import feign.Headers;
import feign.RequestLine;

import java.util.List;

/**
 * Created by gsfeng on 2016/11/5.
 */
public interface FeignCscStoreAPI {
    /**
     * 查询客户下店铺
     * @param queryStoreDto
     * @return
     */
    @RequestLine("POST /api/csc/store/getStoreByCustomerId")
    @Headers("Content-Type: application/json")
    public Wrapper<List<CscStorevo>> getStoreByCustomerId(QueryStoreDto queryStoreDto);

}
