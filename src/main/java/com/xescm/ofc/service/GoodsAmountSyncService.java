package com.xescm.ofc.service;

import com.xescm.base.model.wrap.Wrapper;
import com.xescm.tfc.edas.model.dto.ofc.req.GoodsAmountSyncDto;

/**
 * Created by ydx on 2016/10/12.
 */
public interface GoodsAmountSyncService {
    Wrapper<?> GoodsAmountSync(GoodsAmountSyncDto goodsAmountSyncDto);
}
