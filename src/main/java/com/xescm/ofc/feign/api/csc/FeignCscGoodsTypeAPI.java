package com.xescm.ofc.feign.api.csc;

import com.xescm.ofc.model.dto.csc.CscGoodsType;
import com.xescm.ofc.model.vo.csc.CscGoodsTypeVo;
import com.xescm.uam.utils.wrap.Wrapper;
import feign.Headers;
import feign.RequestLine;

import java.util.List;

/**
 * Created by lyh on 2016/11/23.
 */
public interface FeignCscGoodsTypeAPI {

    @RequestLine("POST /api/csc/goodstype/queryCscGoodsTypeList")
    @Headers("Content-Type: application/json")
    public Wrapper<List<CscGoodsTypeVo>> queryCscGoodsTypeList(CscGoodsType cscGoodsType);

    @RequestLine("POST /api/csc/goodstype/addCscGoodsType")
    @Headers("Content-Type: application/json")
    public Wrapper<?> addCscGoodsType(CscGoodsType cscGoodsType);
}
