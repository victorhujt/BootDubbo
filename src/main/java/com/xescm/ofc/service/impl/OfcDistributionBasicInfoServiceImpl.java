package com.xescm.ofc.service.impl;


import com.xescm.ofc.domain.OfcDistributionBasicInfo;
import com.xescm.ofc.domain.OfcFundamentalInformation;
import com.xescm.ofc.mapper.OfcDistributionBasicInfoMapper;
import com.xescm.ofc.service.OfcDistributionBasicInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lyh on 2016/10/10.
 */
@Service
@Transactional
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
        ofcDistributionBasicInfoMapper.updateByOrderCode(key);
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
        String custCode = "001";
       // Map<String,String> mapperMap = new HashMap<String,String>();
        Map<String,String> mapperMap = new HashMap<>();
        mapperMap.put("custCode",custCode);
        mapperMap.put("transCode",transCode);
        String orderCode = ofcDistributionBasicInfoMapper.getOrderCodeByTransCode(mapperMap);
        return orderCode;
    }

    @Override
    public int checkTransCode(OfcDistributionBasicInfo ofcDistributionBasicInfo) {
        int result =  ofcDistributionBasicInfoMapper.checkTransCode(ofcDistributionBasicInfo);
        return result;
    }
}
