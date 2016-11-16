package com.xescm.ofc.service.impl;

import com.xescm.ofc.domain.OfcSiloproStatus;
import com.xescm.ofc.mapper.OfcSiloproStatusMapper;
import com.xescm.ofc.service.OfcSiloproStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by lyh on 2016/10/10.
 */
@Service
@Transactional
public class OfcSiloproStatusServiceImpl extends BaseService<OfcSiloproStatus> implements OfcSiloproStatusService {
    @Autowired
    private OfcSiloproStatusMapper ofcSiloproStatusMapper;

    public int updateByPlanCode(Object key){
        ofcSiloproStatusMapper.updateByPlanCode(key);
        return 0;
    }
}
