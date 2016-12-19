package com.xescm.ofc.service.impl;

import com.xescm.ofc.domain.OfcTransplanStatus;
import com.xescm.ofc.mapper.OfcTransplanStatusMapper;
import com.xescm.ofc.service.OfcTransplanStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by lyh on 2016/10/10.
 */
@Service
@Transactional
public class OfcTransplanStatusServiceImpl extends BaseService<OfcTransplanStatus> implements OfcTransplanStatusService {
    @Autowired
    private OfcTransplanStatusMapper ofcTransplanStatusMapper;

    public int updateByPlanCode(Object key){

        return ofcTransplanStatusMapper.updateByPlanCode(key);
    }


}
