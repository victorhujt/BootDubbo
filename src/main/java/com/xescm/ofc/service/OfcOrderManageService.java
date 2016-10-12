package com.xescm.ofc.service;

import com.xescm.ofc.domain.OfcOrderDTO;

/**
 * Created by ydx on 2016/10/12.
 */
public interface OfcOrderManageService extends IService<OfcOrderDTO>{
    public String orderAudit(OfcOrderDTO ofcOrderDTO);
    public String orderDelete(OfcOrderDTO ofcOrderDTO);
    public String orderCancel(OfcOrderDTO ofcOrderDTO);
}
