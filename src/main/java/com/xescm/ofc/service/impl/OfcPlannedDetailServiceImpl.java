package com.xescm.ofc.service.impl;

import com.xescm.core.utils.PubUtils;
import com.xescm.ofc.domain.OfcPlannedDetail;
import com.xescm.ofc.exception.BusinessException;
import com.xescm.ofc.mapper.OfcPlannedDetailMapper;
import com.xescm.ofc.service.OfcPlannedDetailService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 计划单详情
 * Created by lyh on 2016/10/10.
 */
@Service
@Transactional

public class OfcPlannedDetailServiceImpl extends BaseService<OfcPlannedDetail> implements OfcPlannedDetailService {

    @Resource
    private OfcPlannedDetailMapper ofcPlannedDetailMapper;

    @Override
    public List<OfcPlannedDetail> planDetailsScreenList(String code, String followTag) {
        if(!"".equals(PubUtils.trimAndNullAsEmpty(code))){
            String planCode = null;
            if(followTag.equals("planCode")){
                planCode = code;
            }
            Map<String,String> mapperMap = new HashMap<>();
            mapperMap.put("planCode",planCode);
            return ofcPlannedDetailMapper.plannedDetailsScreen(mapperMap);
        }else {
            throw new BusinessException("");
        }
    }

    @Override
    public void updateByPlanCode(OfcPlannedDetail detail) {
         ofcPlannedDetailMapper.updateByPlanCode(detail);
    }
}
