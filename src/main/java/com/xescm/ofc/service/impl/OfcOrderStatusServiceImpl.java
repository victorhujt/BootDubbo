package com.xescm.ofc.service.impl;

import com.fasterxml.jackson.core.ObjectCodec;
import com.xescm.ofc.domain.OfcOrderStatus;
import com.xescm.ofc.exception.BusinessException;
import com.xescm.ofc.feign.api.FeignCscCustomerAPI;
import com.xescm.ofc.mapper.OfcOrderStatusMapper;
import com.xescm.ofc.service.OfcOrderStatusService;
import com.xescm.ofc.utils.PubUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lyh on 2016/10/10.
 */
@Service
public class OfcOrderStatusServiceImpl extends BaseService<OfcOrderStatus> implements OfcOrderStatusService {
    private static final Logger logger = LoggerFactory.getLogger(FeignCscCustomerAPI.class);
    @Autowired
    private OfcOrderStatusMapper ofcOrderStatusMapper;
    @Override
    public int deleteByOrderCode(Object key) {
        ofcOrderStatusMapper.deleteByOrderCode(key);
        return 0;
    }

    @Override
    public List<OfcOrderStatus> orderStatusScreen(String code,String followTag) {
        if(!PubUtils.trimAndNullAsEmpty(code).equals("")){
            String orderCode = null;
            String custOrderCode =null;
            String transCode = null;
            if(followTag.equals("orderCode")){
                orderCode = code;
            }else if(followTag.equals("custOrderCode")){
                custOrderCode = code;
            }else if(followTag.equals("transCode")){
                transCode = code;
            }
            Map<String,String> mapperMap = new HashMap<>();
            mapperMap.put("orderCode",orderCode);
            mapperMap.put("custOrderCode",custOrderCode);
            mapperMap.put("transCode",transCode);
            return ofcOrderStatusMapper.orderStatusScreen(mapperMap);
        }else {
            throw new BusinessException();
        }
    }

    @Override
    public OfcOrderStatus orderStatusSelect(String code,String followTag) {
        if(!PubUtils.trimAndNullAsEmpty(code).equals("")){
            String orderCode = null;
            String custOrderCode =null;
            String transCode = null;

            if(followTag.equals("orderCode")){
                orderCode = code;
            }else if(followTag.equals("custOrderCode")){
                custOrderCode = code;
            }else if(followTag.equals("transCode")){
                transCode = code;
            }
            logger.debug("`````````````"+orderCode);
            logger.debug("`````````````"+custOrderCode);
            logger.debug("`````````````"+transCode);
            Map<String,String> mapperMap = new HashMap<>();
            mapperMap.put("orderCode",orderCode);
            mapperMap.put("custOrderCode",custOrderCode);
            mapperMap.put("transCode",transCode);
            OfcOrderStatus ofcOrderStatus = ofcOrderStatusMapper.orderStatusSelect(mapperMap);
            if(ofcOrderStatus == null){
                return new OfcOrderStatus();
            }
            return ofcOrderStatus;
        }else {
            throw new BusinessException();
        }

    }
}
