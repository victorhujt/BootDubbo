package com.xescm.ofc.service.impl;

import com.xescm.ofc.domain.OfcCustFinanceInformation;
import com.xescm.ofc.domain.OfcFinanceInformation;
import com.xescm.ofc.mapper.OfcCustFinanceInformationMapper;
import com.xescm.ofc.mapper.OfcFinanceInformationMapper;
import com.xescm.ofc.service.OfcCustFinanceInformationService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 *
 * Created by lyh on 2017/9/15.
 */
@Service
public class OfcCustFinanceInformationServiceImpl extends BaseService<OfcCustFinanceInformation> implements OfcCustFinanceInformationService {

    @Resource
    private OfcCustFinanceInformationMapper ofcCustFinanceInformationMapper;
    @Resource
    private OfcFinanceInformationMapper ofcFinanceInformationMapper;

    @Override
    public OfcCustFinanceInformation queryByOrderCode(String orderCode) {
        OfcFinanceInformation ofcFinanceInformation = ofcFinanceInformationMapper.selectByPrimaryKey(orderCode);
        if (null != ofcFinanceInformation) {
            OfcCustFinanceInformation result = new OfcCustFinanceInformation();
            BeanUtils.copyProperties(ofcFinanceInformation, result);
            return result;
        }
        return ofcCustFinanceInformationMapper.selectByPrimaryKey(orderCode);
    }
}
