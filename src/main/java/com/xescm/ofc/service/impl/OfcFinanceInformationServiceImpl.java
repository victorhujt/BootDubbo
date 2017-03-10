package com.xescm.ofc.service.impl;

import com.xescm.ofc.domain.OfcFinanceInformation;
import com.xescm.ofc.mapper.OfcFinanceInformationMapper;
import com.xescm.ofc.service.OfcFinanceInformationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 *
 * Created by lyh on 2016/10/10.
 */
@Service
@Transactional
public class OfcFinanceInformationServiceImpl extends BaseService<OfcFinanceInformation> implements OfcFinanceInformationService {

    @Resource
    private OfcFinanceInformationMapper ofcFinanceInformationMapper;

    @Override
    public OfcFinanceInformation queryByOrderCode(String orderCode) {
        return ofcFinanceInformationMapper.queryByOrderCode(orderCode);
    }




}
