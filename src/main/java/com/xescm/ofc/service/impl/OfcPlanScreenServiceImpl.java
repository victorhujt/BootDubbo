package com.xescm.ofc.service.impl;

import com.xescm.ofc.domain.OfcPlanScreenCondition;
import com.xescm.ofc.domain.OfcPlanScreenResult;
import com.xescm.ofc.exception.BusinessException;
import com.xescm.ofc.mapper.OfcPlanScreenMapper;
import com.xescm.ofc.service.OfcPlanScreenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lyh on 2016/10/10.
 */
@Service
public class OfcPlanScreenServiceImpl extends BaseService<OfcPlanScreenResult> implements OfcPlanScreenService {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private OfcPlanScreenMapper ofcPlanScreenMapper;

    @Override
    public List<OfcPlanScreenResult> planScreen(OfcPlanScreenCondition ofcPlanScreenCondition) {

        if (ofcPlanScreenCondition == null){
            logger.error("planScreen 入参 ofcPlanScreenCondition 为null");
            throw new BusinessException("计划单查询出错!");
        }
        List<OfcPlanScreenResult> ofcPlanScreenResults =new ArrayList<>();
        if(ofcPlanScreenCondition.getResourceAllocationStatues().size()>0){
            String str= ofcPlanScreenCondition.getResourceAllocationStatues().get(ofcPlanScreenCondition.getResourceAllocationStatues().size()-1).replace("*|*","");
            ofcPlanScreenCondition.getResourceAllocationStatues().remove(ofcPlanScreenCondition.getResourceAllocationStatues().size()-1);
            ofcPlanScreenCondition.getResourceAllocationStatues().add(str);
            for(int i = 0; i< ofcPlanScreenCondition.getResourceAllocationStatues().size(); i++){
                ofcPlanScreenCondition.setResourceAllocationStatus(ofcPlanScreenCondition.getResourceAllocationStatues().get(i).toString());
                ofcPlanScreenResults.addAll(ofcPlanScreenMapper.planScreen(ofcPlanScreenCondition));
            }
        }else {
            ofcPlanScreenResults =ofcPlanScreenMapper.planScreen(ofcPlanScreenCondition);
        }
        if(ofcPlanScreenResults == null){
            logger.error("planScreen 查询结果 ofcPlanScreenResults 为null");
            throw new BusinessException("计划单查询出错!");
        }
        return ofcPlanScreenResults;

    }
}
