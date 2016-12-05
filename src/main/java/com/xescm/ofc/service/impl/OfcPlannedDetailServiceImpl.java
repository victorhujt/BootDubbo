package com.xescm.ofc.service.impl;

import com.xescm.ofc.domain.OfcGoodsDetailsInfo;
import com.xescm.ofc.domain.OfcPlannedDetail;
import com.xescm.ofc.exception.BusinessException;
import com.xescm.ofc.mapper.OfcGoodsDetailsInfoMapper;
import com.xescm.ofc.mapper.OfcPlannedDetailMapper;
import com.xescm.ofc.service.OfcPlannedDetailService;
import com.xescm.ofc.utils.PubUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lyh on 2016/10/10.
 */
@Service
@Transactional
public class OfcPlannedDetailServiceImpl extends BaseService<OfcPlannedDetail> implements OfcPlannedDetailService {

    @Autowired
    private OfcPlannedDetailMapper ofcPlannedDetailMapper;

    @Override
    public List<OfcPlannedDetail> planDetailsScreenList(String code, String followTag) {
        if(!"".equals(PubUtils.trimAndNullAsEmpty(code))){
            String planCode = null;
            if(followTag.equals("planCode")){
                planCode = code;
            }
            Map<String,String> mapperMap = new HashMap<String,String>();
            mapperMap.put("planCode",planCode);
            return ofcPlannedDetailMapper.plannedDetailsScreen(mapperMap);
        }else {
            throw new BusinessException();
        }
    }
}
