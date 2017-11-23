package com.xescm.ofc.service;

import com.xescm.base.model.wrap.Wrapper;
import com.xescm.ofc.domain.OfcPlanFedBackResult;
import com.xescm.ofc.domain.OfcSchedulingSingleFeedbackCondition;
import com.xescm.tfc.mq.dto.TfcTransportStateDTO;

import java.util.List;

/**
 * Created by ydx on 2016/10/12.
 */
public interface OfcPlanFedBackService {

    /**
     * 运输中心更新订单状态
     * @param tfcTransportStateDTO
     * @param userName
     * @return
     */
    Wrapper<List<OfcPlanFedBackResult>> planFedBackNew(TfcTransportStateDTO tfcTransportStateDTO, String userName);
    Wrapper<List<OfcPlanFedBackResult>> schedulingSingleFeedbackNew(OfcSchedulingSingleFeedbackCondition ofcSchedulingSingleFeedbackCondition, String userName);
}
