package com.xescm.ofc.service.impl;

import com.xescm.ofc.domain.OfcTransplanInfo;
import com.xescm.ofc.domain.OfcTransplanNewstatus;
import com.xescm.ofc.mapper.OfcTransplanNewstatusMapper;
import com.xescm.ofc.service.OfcTransplanInfoService;
import com.xescm.ofc.service.OfcTransplanNewstatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by lyh on 2016/10/10.
 */
@Service
@Transactional
public class OfcTransplanNewstatusServiceImpl extends BaseService<OfcTransplanNewstatus> implements OfcTransplanNewstatusService {
    @Autowired
    private OfcTransplanNewstatusMapper ofcTransplanNewstatusMapper;
    public int updateByPlanCode(Object key){
        ofcTransplanNewstatusMapper.updateByPlanCode(key);
        return 0;
    }
}
