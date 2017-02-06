package com.xescm.ofc.service.impl;

import com.xescm.ofc.domain.PlanAndStorage;
import com.xescm.ofc.mapper.PlanAndStorageMapper;
import com.xescm.ofc.service.PlanAndStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 *
 * Created by hiyond on 2016/11/25.
 */
@Service
public class PlanAndStorageServiceImpl implements PlanAndStorageService {

    @Resource
    private PlanAndStorageMapper planAndStorageMapper;

    @Override
    public List<PlanAndStorage> queryPlanAndStorage(String orderCode, String transCode) {
        return planAndStorageMapper.queryPlanAndStorage(orderCode,transCode);
    }

    @Override
    public List<PlanAndStorage> queryPlanAndStorageTrans(String orderCode, String transCode) {
        return planAndStorageMapper.queryPlanAndStorageTrans(orderCode,transCode);
    }
}
