package com.xescm.ofc.service;

import com.xescm.ofc.domain.OfcOrderDTO;

/**
 * Created by ydx on 2016/10/12.
 */
public interface OfcOrderManageService{
    String orderAudit(OfcOrderDTO ofcOrderDTO);
    String orderDelete(OfcOrderDTO ofcOrderDTO);
    String orderCancel(OfcOrderDTO ofcOrderDTO);
}
