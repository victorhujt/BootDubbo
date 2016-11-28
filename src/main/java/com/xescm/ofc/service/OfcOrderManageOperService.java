package com.xescm.ofc.service;

import com.xescm.ofc.domain.OrderScreenResult;
import com.xescm.ofc.domain.form.OrderOperForm;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by hiyond on 2016/11/24.
 */

public interface OfcOrderManageOperService {

    List<OrderScreenResult> queryOrderOper(OrderOperForm form);

}
