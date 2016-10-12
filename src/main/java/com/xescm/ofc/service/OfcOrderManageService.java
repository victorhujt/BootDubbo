package com.xescm.ofc.service;

import com.xescm.ofc.domain.OfcOrderDTO;

/**
 * Created by ydx on 2016/10/12.
 */
public interface OfcOrderManageService {
    String orderAudit(String orderCode,String orderStatus);
    String orderDelete(String orderCode,String orderStatus);
    String orderCancel(String orderCode,String orderStatus);
    OfcOrderDTO getOrderDetailByCode(String orderCode);
}
