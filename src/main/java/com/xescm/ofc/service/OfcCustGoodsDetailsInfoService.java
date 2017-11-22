package com.xescm.ofc.service;

import com.xescm.ofc.domain.OfcCustGoodsDetailsInfo;

import java.util.List;

/**
 * Created by lyh on 2017/9/14.
 */
public interface OfcCustGoodsDetailsInfoService extends IService<OfcCustGoodsDetailsInfo>{
    List<OfcCustGoodsDetailsInfo> queryByOrderCode(String orderCode);
}
