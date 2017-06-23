package com.xescm.ofc.service;

import com.aliyun.openservices.ons.api.Action;
import com.xescm.base.model.wrap.Wrapper;
import com.xescm.ofc.domain.OfcPlanFedBackCondition;
import com.xescm.ofc.domain.OfcPlanFedBackResult;
import com.xescm.ofc.domain.OfcSchedulingSingleFeedbackCondition;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by ydx on 2016/10/12.
 */
public interface OfcPlanFedBackService {
    Action planFedBackNew(OfcPlanFedBackCondition ofcPlanFedBackCondition, String userName, ConcurrentHashMap cmap);
    Wrapper<List<OfcPlanFedBackResult>> schedulingSingleFeedbackNew(OfcSchedulingSingleFeedbackCondition ofcSchedulingSingleFeedbackCondition, String userName);
}
