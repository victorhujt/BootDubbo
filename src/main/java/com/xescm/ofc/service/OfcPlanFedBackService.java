package com.xescm.ofc.service;

import com.xescm.ofc.domain.OfcPlanFedBackCondition;
import com.xescm.ofc.domain.OfcPlanFedBackResult;
import com.xescm.ofc.domain.OfcSchedulingSingleFeedbackCondition;
import com.xescm.uam.utils.wrap.Wrapper;

import java.util.List;

/**
 * Created by ydx on 2016/10/12.
 */
public interface OfcPlanFedBackService {
    Wrapper<List<OfcPlanFedBackResult>> planFedBack(OfcPlanFedBackCondition ofcPlanFedBackCondition,String userName);
    Wrapper<List<OfcPlanFedBackResult>> schedulingSingleFeedback(OfcSchedulingSingleFeedbackCondition ofcSchedulingSingleFeedbackCondition, String userName);
}
