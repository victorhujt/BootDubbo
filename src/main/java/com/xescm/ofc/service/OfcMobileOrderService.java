package com.xescm.ofc.service;

import com.xescm.ofc.domain.OfcMobileOrder;
import com.xescm.ofc.model.dto.form.MobileOrderOperForm;

import java.util.List;

/**
 * Created by hujintao on 2016/12/12.
 */

public interface OfcMobileOrderService extends IService<OfcMobileOrder>{

     OfcMobileOrder saveOfcMobileOrder(OfcMobileOrder ofcMobileOrder);

      List<OfcMobileOrder> queryOrderNotes(String mobileOrderStatus);

    List<OfcMobileOrder> queryOrderList(MobileOrderOperForm form);

}
