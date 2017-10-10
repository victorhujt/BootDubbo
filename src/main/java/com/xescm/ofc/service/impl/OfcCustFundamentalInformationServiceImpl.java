package com.xescm.ofc.service.impl;

import com.xescm.ofc.domain.OfcCustFundamentalInformation;
import com.xescm.ofc.domain.OfcFundamentalInformation;
import com.xescm.ofc.exception.BusinessException;
import com.xescm.ofc.mapper.OfcCustFundamentalInformationMapper;
import com.xescm.ofc.mapper.OfcFundamentalInformationMapper;
import com.xescm.ofc.service.OfcCustFundamentalInformationService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 *
 * Created by lyh on 2017/9/14.
 */
@Service
public class OfcCustFundamentalInformationServiceImpl extends BaseService<OfcCustFundamentalInformation> implements OfcCustFundamentalInformationService{

    @Resource
    private OfcCustFundamentalInformationMapper ofcCustFundamentalInformationMapper;
    @Resource
    private OfcFundamentalInformationMapper ofcFundamentalInformationMapper;

    @Override
    public OfcCustFundamentalInformation queryByOrderCode(String orderCode) {
        if (StringUtils.isEmpty(orderCode)) {
            logger.error("OfcCustFundamentalInformation queryByOrderCode 入参为空");
            throw new BusinessException("操作失败");
        }
        OfcFundamentalInformation fundamental = ofcFundamentalInformationMapper.selectByPrimaryKey(orderCode);
        if (fundamental != null) {
            OfcCustFundamentalInformation result = new OfcCustFundamentalInformation();
            BeanUtils.copyProperties(fundamental, result);
            return result;
        }
        OfcCustFundamentalInformation custFundamentalInformation = new OfcCustFundamentalInformation();
        custFundamentalInformation.setOrderCode(orderCode);
        return ofcCustFundamentalInformationMapper.queryOrderByInfo(custFundamentalInformation);
    }
}
