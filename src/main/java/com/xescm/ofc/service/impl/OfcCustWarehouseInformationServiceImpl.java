package com.xescm.ofc.service.impl;

import com.xescm.ofc.domain.OfcCustWarehouseInformation;
import com.xescm.ofc.domain.OfcWarehouseInformation;
import com.xescm.ofc.mapper.OfcCustWarehouseInformationMapper;
import com.xescm.ofc.mapper.OfcWarehouseInformationMapper;
import com.xescm.ofc.service.OfcCustWarehouseInformationService;
import org.springframework.beans.BeanUtils;
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
    @Resource
    private OfcWarehouseInformationMapper ofcWarehouseInformationMapper;

    @Override
    public OfcCustWarehouseInformation queryByOrderCode(String orderCode) {
        OfcWarehouseInformation ofcWarehouseInformation = ofcWarehouseInformationMapper.ofcWarehouseInformationSelect(orderCode);
        OfcCustWarehouseInformation custWarehouseInformation = new OfcCustWarehouseInformation();
        if (null != ofcWarehouseInformation) {
            BeanUtils.copyProperties(ofcWarehouseInformation, custWarehouseInformation);
            return custWarehouseInformation;
        }
        custWarehouseInformation.setOrderCode(orderCode);
        return ofcCustWarehouseInformationMapper.queryByInfo(custWarehouseInformation);
    }
}
