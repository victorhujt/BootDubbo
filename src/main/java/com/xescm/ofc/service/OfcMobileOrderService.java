package com.xescm.ofc.service;

import com.xescm.ofc.domain.OfcMobileOrder;

/**
 * Created by hujintao on 2016/12/12.
 */
public interface OfcMobileOrderService extends IService<OfcMobileOrder>{

     OfcMobileOrder saveOfcMobileOrder(OfcMobileOrder ofcMobileOrder);
}
