package com.xescm.ofc.service;

import com.xescm.ofc.domain.OfcOrderNewstatus;
import com.xescm.ofc.edas.model.dto.whc.FeedBackInventoryDto;

/**
 * Created by lyh on 2016/10/10.
 */
public interface OfcOrderNewstatusService extends IService<OfcOrderNewstatus> {
    public void FeedBackInventory(FeedBackInventoryDto feedBackInventoryDto);
}
