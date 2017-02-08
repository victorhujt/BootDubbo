package com.xescm.ofc.service.impl;

import com.xescm.ofc.domain.OfcTraplanSourceStatus;
import com.xescm.ofc.mapper.OfcTraplanSourceStatusMapper;
import com.xescm.ofc.service.OfcTraplanSourceStatusService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 *
 * Created by lyh on 2016/10/10.
 */
@Service
@Transactional
public class OfcTraplanSourceStatusServiceImpl extends BaseService<OfcTraplanSourceStatus> implements OfcTraplanSourceStatusService {
    @Resource
    private OfcTraplanSourceStatusMapper ofcTraplanSourceStatusMapper;

    public int updateByPlanCode(Object key){

        return ofcTraplanSourceStatusMapper.updateByPlanCode(key);
    }
}
