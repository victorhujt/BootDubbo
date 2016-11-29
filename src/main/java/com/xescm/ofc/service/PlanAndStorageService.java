package com.xescm.ofc.service;

import com.xescm.ofc.model.dto.vo.PlanAndStorageVo;

import java.util.List;

/**
 * Created by hiyond on 2016/11/25.
 */
public interface PlanAndStorageService {

    List<PlanAndStorageVo> queryPlanAndStorage(String orderCode, String transCode);

    List<PlanAndStorageVo> queryPlanAndStorageTrans(String orderCode, String transCode);


}
