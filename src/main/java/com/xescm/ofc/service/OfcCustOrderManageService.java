package com.xescm.ofc.service;

import com.xescm.base.model.dto.auth.AuthResDto;
import com.xescm.base.model.wrap.Wrapper;
import com.xescm.ofc.domain.OrderSearchOperResult;
import com.xescm.ofc.model.dto.form.OfcManagementForm;
import com.xescm.ofc.model.dto.ofc.OfcOrderInfoDTO;
import com.xescm.ofc.model.dto.ofc.OfcUserMsgDTO;

import java.util.List;

/**
 * Created by lyh on 2017/9/8.
 */
public interface OfcCustOrderManageService {
    List<OrderSearchOperResult> queryOrderListByCondition(OfcUserMsgDTO customerMsgDTO, OfcManagementForm param);

    Wrapper orderCancel(OfcManagementForm managementForm, AuthResDto authResDtoByToken);

    OfcOrderInfoDTO queryOrderDetailByOrderCode(String orderCode, OfcUserMsgDTO userMsgDTO);

    void confirmOrder(String orderCode, AuthResDto authResDtoByToken);
}
