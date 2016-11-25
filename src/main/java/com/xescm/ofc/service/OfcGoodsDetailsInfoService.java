package com.xescm.ofc.service;

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
}
