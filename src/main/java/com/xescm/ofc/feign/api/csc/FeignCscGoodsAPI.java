package com.xescm.ofc.feign.api.csc;

import com.xescm.ofc.model.dto.csc.CscGoodsApiDto;
import com.xescm.ofc.model.dto.csc.CscGoodsType;
import com.xescm.ofc.model.vo.csc.CscGoodsApiVo;
import com.xescm.ofc.model.vo.csc.CscGoodsTypeVo;
import com.xescm.uam.utils.wrap.Wrapper;
import feign.Headers;
import feign.Param;
import feign.RequestLine;

import java.util.List;

/**
 * Created by wang on 2016/10/18.
 */
public interface FeignCscGoodsAPI {
    /*@RequestLine("POST /api/csc/goods/queryCscGoodsList")
    @Headers("Content-Type: application/json")
    public Wrapper<List<CscGoodsVo>> queryCscGoodsList(CscGoods cscGoods);*/

    /**
     * 查询客户货品列表
     * @param cscGoods
     * @return
     */
    @RequestLine("POST /api/csc/goods/queryCscGoodsList")
    @Headers("Content-Type: application/json")
    public Wrapper<List<CscGoodsApiVo>> queryCscGoodsList(CscGoodsApiDto cscGoods);

    /**
     * 查询货品类别
     * @param cscGoodsType
     * @return
     */
    @RequestLine("POST api/csc/goodstype/getCscGoodsTypeList")
    @Headers("Content-Type: application/json")
    public Wrapper<List<CscGoodsTypeVo>> getCscGoodsTypeList(CscGoodsType cscGoodsType);

}
