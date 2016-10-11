package com.xescm.ofc.service.impl;

import com.xescm.ofc.domain.OfcOrderStatus;
import com.xescm.ofc.mapper.OfcOrderStatusMapper;
import com.xescm.ofc.service.OfcOrderStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by lyh on 2016/10/10.
 */
@Service
public class OfcOrderStatusServiceImpl extends BaseService<OfcOrderStatus> implements OfcOrderStatusService {
    @Autowired
    private OfcOrderStatusMapper ofcOrderStatusMapper;
    @Override
    public int deleteByOrderCode(Object key) {
        ofcOrderStatusMapper.deleteByOrderCode(key);
        return 0;
    }
}
