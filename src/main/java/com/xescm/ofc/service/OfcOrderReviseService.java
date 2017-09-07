package com.xescm.ofc.service;

import com.xescm.base.model.wrap.Wrapper;
import com.xescm.tfc.edas.model.dto.ofc.req.GoodsAmountSyncDto;

/**
 * Created by dragon on 2017/9/1.
 */
public interface OfcOrderReviseService {
    Wrapper<?> goodsAmountSync(GoodsAmountSyncDto goodsAmountSyncDto,String orderCode);
}
