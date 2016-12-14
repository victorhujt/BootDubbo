package com.xescm.ofc.service;

import com.xescm.ofc.domain.OfcMobileOrder;

import java.util.List;

/**
 * Created by hujintao on 2016/12/12.
 */
public interface OfcMobileOrderService {

     OfcMobileOrder saveOfcMobileOrder(OfcMobileOrder ofcMobileOrder);

     public List<OfcMobileOrder> queryOrderNotes(String mobileOrderStatus);
}
