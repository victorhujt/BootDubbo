package com.xescm.ofc.service.impl;

import com.xescm.ofc.domain.OfcTransplanNewstatus;
import com.xescm.ofc.mapper.OfcTransplanNewstatusMapper;
import com.xescm.ofc.service.OfcTransplanNewstatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class OfcTransplanNewstatusServiceImpl extends BaseService<OfcTransplanNewstatus> implements OfcTransplanNewstatusService {
    @Autowired
    private OfcTransplanNewstatusMapper ofcTransplanNewstatusMapper;
    public int updateByPlanCode(Object key){
        return ofcTransplanNewstatusMapper.updateByPlanCode(key);
    }
}
