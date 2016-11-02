package com.xescm.ofc.service.impl;

import com.xescm.ofc.domain.OfcGoodsDetailsInfo;
import com.xescm.ofc.domain.OfcSiloproStatus;
import com.xescm.ofc.domain.OfcTransplanInfo;
import com.xescm.ofc.exception.BusinessException;
import com.xescm.ofc.feign.api.FeignCscCustomerAPI;
import com.xescm.ofc.mapper.OfcTransplanInfoMapper;
import com.xescm.ofc.service.OfcOrderStatusService;
import com.xescm.ofc.service.OfcSiloproStatusService;
import com.xescm.ofc.service.OfcTransplanInfoService;
import com.xescm.ofc.utils.PubUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lyh on 2016/10/10.
 */
@Service
public class OfcTransplanInfoServiceImpl extends BaseService<OfcTransplanInfo> implements OfcTransplanInfoService {
    private static final Logger logger = LoggerFactory.getLogger(FeignCscCustomerAPI.class);
    @Autowired
    private OfcTransplanInfoMapper ofcTransplanInfoMapper;
    @Override
    public List<OfcTransplanInfo> ofcTransplanInfoScreenList(String orderCode) {
        if(!PubUtils.trimAndNullAsEmpty(orderCode).equals("")){
            //List<OfcTransplanInfo> ofcTransplanInfoList=new ArrayList<OfcTransplanInfo>();
            Map<String,String> mapperMap = new HashMap<>();
            mapperMap.put("orderCode",orderCode);
            /*OfcTransplanInfo ofcTransplanInfo=new OfcTransplanInfo();
            ofcTransplanInfo.setOrderCode(orderCode);*/
            return ofcTransplanInfoMapper.ofcTransplanInfoScreenList(mapperMap);
        }else {
            throw new BusinessException();
        }
    }
}
