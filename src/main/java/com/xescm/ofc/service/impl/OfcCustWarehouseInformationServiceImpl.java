package com.xescm.ofc.service.impl;

import com.xescm.ofc.domain.OfcCustWarehouseInformation;
import com.xescm.ofc.mapper.OfcCustWarehouseInformationMapper;
import com.xescm.ofc.service.OfcCustWarehouseInformationService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 *
 * Created by lyh on 2017/9/14.
 */
@Service
public class OfcCustWarehouseInformationServiceImpl extends BaseService<OfcCustWarehouseInformation> implements OfcCustWarehouseInformationService{

    @Resource
    private OfcCustWarehouseInformationMapper ofcCustWarehouseInformationMapper;

    @Override
    public OfcCustWarehouseInformation queryByOrderCode(String orderCode) {
        OfcCustWarehouseInformation custWarehouseInformation = new OfcCustWarehouseInformation();
        custWarehouseInformation.setOrderCode(orderCode);
        return ofcCustWarehouseInformationMapper.queryByInfo(custWarehouseInformation);
    }
}
