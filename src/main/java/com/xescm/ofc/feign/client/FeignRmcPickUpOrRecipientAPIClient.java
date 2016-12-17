package com.xescm.ofc.feign.client;

import com.xescm.ofc.config.RestConfig;
import com.xescm.ofc.exception.BusinessException;
import com.xescm.ofc.feign.api.rmc.FeignRmcPickUpOrRecipientAPI;
import com.xescm.ofc.feign.api.rmc.FeignRmcWarehouseAPI;
import com.xescm.ofc.model.dto.rmc.RmcDistrictQO;
import com.xescm.ofc.model.dto.rmc.RmcWarehouse;
import com.xescm.ofc.model.vo.rmc.RmcPickup;
import com.xescm.ofc.model.vo.rmc.RmcRecipient;
import com.xescm.uam.domain.feign.AuthRequestInterceptor;
import com.xescm.uam.utils.wrap.WrapMapper;
import com.xescm.uam.utils.wrap.Wrapper;
import feign.Feign;
import feign.RetryableException;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by lyh on 2016/11/1.
 */

@Service
public class FeignRmcPickUpOrRecipientAPIClient {
    private static final Logger logger = LoggerFactory.getLogger(FeignRmcPickUpOrRecipientAPI.class);
    @Resource
    private AuthRequestInterceptor authRequestInterceptor;
    @Resource
    RestConfig restConfig;
    public FeignRmcPickUpOrRecipientAPI getRmcApi() {
        FeignRmcPickUpOrRecipientAPI res = Feign.builder()
                .requestInterceptor(authRequestInterceptor).encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder()).target(FeignRmcPickUpOrRecipientAPI.class,restConfig.getRmcUrl());
        return res;
    }

    public Wrapper<List<RmcPickup>> queryPickUp(RmcDistrictQO rmcDistrictQO){
        logger.debug("==>相关地址 rmcDistrictQO={}", rmcDistrictQO);
        if(null == rmcDistrictQO){
            throw new BusinessException("参数为空");
        }
        Wrapper<List<RmcPickup>> rmcPickupList = null;
        try {
            rmcPickupList = getRmcApi().queryPickUp(rmcDistrictQO);
        } catch (RetryableException ex) {
            logger.error("==>调用接口发生异常：调用查询上门提货覆盖区域接口(/api/rmc/district/queryPickUp)无法连接或超时. {}", ex);
            return WrapMapper.wrap(Wrapper.ERROR_CODE, "调用查询上门提货覆盖区域接口无法连接或超时！");
        } catch (Exception ex){
            logger.error("==>调用接口发生异常：查询上门提货覆盖区域接口(/api/rmc/district/queryPickUp). {}", ex);
            return WrapMapper.wrap(Wrapper.ERROR_CODE, "调用查询上门提货覆盖区域接口发生异常！");
        }
        return rmcPickupList;
    }

    public Wrapper<List<RmcRecipient>> queryRecipient(RmcDistrictQO rmcDistrictQO){
        logger.debug("==>相关地址 rmcDistrictQO={}", rmcDistrictQO);
        if(null == rmcDistrictQO){
            throw new BusinessException("参数为空");
        }
        Wrapper<List<RmcRecipient>> RmcRecipientList = null;
        try {
            RmcRecipientList = getRmcApi().queryRecipient(rmcDistrictQO);
        } catch (RetryableException ex) {
            logger.error("==>调用接口发生异常：调用查询二次派送服务区域接口(/api/rmc/district/queryRecipient)无法连接或超时. {}", ex);
            return WrapMapper.wrap(Wrapper.ERROR_CODE, "调用查询二次派送服务区域接口无法连接或超时！");
        } catch (Exception ex){
            logger.error("==>调用接口发生异常：查询二次派送服务区域接口(/api/rmc/district/queryRecipient). {}", ex);
            return WrapMapper.wrap(Wrapper.ERROR_CODE, "调用查询二次派送服务区域接口发生异常！");
        }
        return RmcRecipientList;
    }
}