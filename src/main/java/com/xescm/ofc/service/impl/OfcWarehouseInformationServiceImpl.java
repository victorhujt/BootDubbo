package com.xescm.ofc.service.impl;

import com.xescm.ofc.domain.OfcWarehouseInformation;
import com.xescm.ofc.mapper.OfcWarehouseInformationMapper;
import com.xescm.ofc.service.OfcWarehouseInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by lyh on 2016/10/10.
 */
@Service
public class OfcWarehouseInformationServiceImpl extends BaseService<OfcWarehouseInformation> implements OfcWarehouseInformationService{
    @Autowired
    private OfcWarehouseInformationMapper ofcWarehouseInformationMapper;

    @Override
    public int deleteByOrderCode(Object key) {
        return 0;
    }

    @Override
    public int updateByOrderCode(Object key) {
        return 0;
    }
}
