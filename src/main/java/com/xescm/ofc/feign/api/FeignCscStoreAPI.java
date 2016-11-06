package com.xescm.ofc.feign.api;

import com.xescm.ofc.domain.dto.csc.QueryStoreDto;
import com.xescm.ofc.domain.dto.csc.vo.CscStorevo;
import com.xescm.uam.utils.wrap.Wrapper;
import feign.Headers;
import feign.RequestLine;

import java.util.List;

/**
 * Created by gsfeng on 2016/11/5.
 */
public interface FeignCscStoreAPI {
    @RequestLine("POST /api/csc/store/getStoreByCustomerId")
    @Headers("Content-Type: application/json")
    public Wrapper<List<CscStorevo>> getStoreByCustomerId(QueryStoreDto queryStoreDto);

}
