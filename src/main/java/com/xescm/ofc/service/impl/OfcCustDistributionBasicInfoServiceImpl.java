package com.xescm.ofc.service.impl;

import com.xescm.ofc.domain.OfcCustDistributionBasicInfo;
import com.xescm.ofc.domain.OfcDistributionBasicInfo;
import com.xescm.ofc.exception.BusinessException;
import com.xescm.ofc.mapper.OfcCustDistributionBasicInfoMapper;
import com.xescm.ofc.mapper.OfcDistributionBasicInfoMapper;
import com.xescm.ofc.service.OfcCustDistributionBasicInfoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by lyh on 2017/9/14.
 */
@Service
public class OfcCustDistributionBasicInfoServiceImpl extends BaseService<OfcCustDistributionBasicInfo> implements OfcCustDistributionBasicInfoService {


    @Resource
    private OfcCustDistributionBasicInfoMapper ofcCustDistributionBasicInfoMapper;
    @Resource
    private OfcDistributionBasicInfoMapper ofcDistributionBasicInfoMapper;

    @Override
    public OfcCustDistributionBasicInfo queryByOrderCode(String orderCode) {
        if (StringUtils.isEmpty(orderCode)) {
            throw new BusinessException("查询运输信息出错");
        }
        OfcDistributionBasicInfo ofcDistributionBasicInfo = ofcDistributionBasicInfoMapper.queryByOrderCode(orderCode);
        if (null != ofcDistributionBasicInfo) {
            OfcCustDistributionBasicInfo result = new OfcCustDistributionBasicInfo();
            BeanUtils.copyProperties(ofcDistributionBasicInfo, result);
            return result;
        }
        OfcCustDistributionBasicInfo custDistributionBasicInfo = new OfcCustDistributionBasicInfo();
        custDistributionBasicInfo.setOrderCode(orderCode);
        return ofcCustDistributionBasicInfoMapper.queryByInfo(custDistributionBasicInfo);
    }
}
