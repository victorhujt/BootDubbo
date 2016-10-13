package com.xescm.ofc.service.impl;


import com.xescm.ofc.domain.OfcDistributionBasicInfo;
import com.xescm.ofc.mapper.OfcDistributionBasicInfoMapper;
import com.xescm.ofc.domain.OfcDistributionBasicInfo;
import com.xescm.ofc.service.OfcDistributionBasicInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by lyh on 2016/10/10.
 */
@Service
public class OfcDistributionBasicInfoServiceImpl extends BaseService<OfcDistributionBasicInfo> implements OfcDistributionBasicInfoService{
    @Autowired
    private OfcDistributionBasicInfoMapper ofcDistributionBasicInfoMapper;

    @Override
    public int deleteByOrderCode(Object key) {
        ofcDistributionBasicInfoMapper.deleteByOrderCode(key);
        return 0;
    }

    @Override
    public int updateByOrderCode(Object key) {
        return 0;
    }

    @Override
    public OfcDistributionBasicInfo distributionBasicInfoSelect(String orderCode) {
        OfcDistributionBasicInfo ofcDistributionBasicInfo = ofcDistributionBasicInfoMapper.ofcDistributionBasicInfoSelect(orderCode);
        if(ofcDistributionBasicInfo == null){
            return new OfcDistributionBasicInfo();
        }
        return ofcDistributionBasicInfo;
    }

    @Override
    public String getOrderCodeByTransCode(String transCode) {
        String orderCode = ofcDistributionBasicInfoMapper.getOrderCodeByTransCode(transCode);
        return orderCode;
    }
}
