package com.xescm.ofc.service;

import com.github.pagehelper.PageInfo;
import com.xescm.base.model.wrap.Wrapper;
import com.xescm.csc.model.dto.CscGoodsApiDto;
import com.xescm.csc.model.vo.CscGoodsApiVo;
import com.xescm.ofc.domain.OfcGoodsDetailsInfo;

import java.util.List;

/**
 * Created by lyh on 2016/10/10.
 */
public interface OfcGoodsDetailsInfoService extends IService<OfcGoodsDetailsInfo> {
    List<OfcGoodsDetailsInfo> goodsDetailsScreenList(String code, String followTag);

    String deleteByOrderCode(OfcGoodsDetailsInfo ofcGoodsDetailsInfo);

    void deleteAllByOrderCode(String orderCode);

    List<OfcGoodsDetailsInfo> queryByOrderCode(String orderCode);

    int updateByOrderCode(Object key);

    Wrapper<PageInfo<CscGoodsApiVo>> validateGoodsByCode(CscGoodsApiDto dto);

    // 刷[ofc_goods_details_info][pass_line_no]历史数据
    List<OfcGoodsDetailsInfo> queryIds();
    void flushPassLineNoById(OfcGoodsDetailsInfo ofcGoodsDetailsInfo);

    void fillGoodType(OfcGoodsDetailsInfo ofcGoodsDetailsInfo);
}
