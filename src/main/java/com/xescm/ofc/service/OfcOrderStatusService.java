package com.xescm.ofc.service;

import com.xescm.ofc.domain.OfcOrderStatus;
import com.xescm.ofc.edas.model.dto.ofc.OfcTraceOrderDTO;
import com.xescm.ofc.edas.model.dto.whc.FeedBackOrderDto;
import com.xescm.ofc.edas.model.dto.whc.FeedBackOrderStatusDto;

import java.util.List;

/**
 * Created by lyh on 2016/10/10.
 */
public interface OfcOrderStatusService extends IService<OfcOrderStatus> {
    int deleteByOrderCode(Object key);

    List<OfcOrderStatus> orderStatusScreen(String code, String followTag);

    //获取订单最新状态
    OfcOrderStatus orderStatusSelect(String code, String followTag);

    OfcOrderStatus queryOrderStateByOrderCode(String orderCode);

    /**
     * 修改订单状态，并标记为作废
     *
     * @param orderCode
     */
    void cancelOrderStateByOrderCode(String orderCode);

    OfcOrderStatus queryLastUpdateOrderByOrderCode(String orderCode);

    OfcOrderStatus queryLastTimeOrderByOrderCode(String orderCode);

    @Override
    int save(OfcOrderStatus record);

    void feedBackStatusFromWhc(FeedBackOrderStatusDto feedBackOrderStatusDto);
    void ofcWarehouseFeedBackFromWhc(FeedBackOrderDto feedBackOrderDto);

    OfcTraceOrderDTO queryOrderByCode(String code) throws Exception;

    int saveOrderStatusLog(OfcOrderStatus record);
}
