package com.xescm.ofc.service.impl;

import com.xescm.ofc.domain.OfcPlanScreenCondition;
import com.xescm.ofc.domain.OfcPlanScreenResult;
import com.xescm.ofc.mapper.OfcPlanScreenMapper;
import com.xescm.ofc.service.OfcPlanScreenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lyh on 2016/10/10.
 */
@Service
public class OfcPlanScreenServiceImpl extends BaseService<OfcPlanScreenResult> implements OfcPlanScreenService {
    @Autowired
    private OfcPlanScreenMapper ofcPlanScreenMapper;

    @Override
    public List<OfcPlanScreenResult> planScreen(OfcPlanScreenCondition ofcPlanScreenCondition) {

        if (ofcPlanScreenCondition == null){

        }
        List<OfcPlanScreenResult> ofcPlanScreenResults =new ArrayList<OfcPlanScreenResult>();
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

        }
        return ofcPlanScreenResults;

    }
}
