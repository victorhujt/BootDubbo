package com.xescm.ofc.service;

import com.xescm.ofc.domain.PlanAndStorage;

import java.util.List;

/**
 * Created by hiyond on 2016/11/25.
 */
public interface PlanAndStorageService {

    List<PlanAndStorage> queryPlanAndStorage(String orderCode, String transCode);

    List<PlanAndStorage> queryPlanAndStorageTrans(String orderCode, String transCode);


}
