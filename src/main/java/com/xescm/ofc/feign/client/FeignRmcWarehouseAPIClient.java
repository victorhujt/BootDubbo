package com.xescm.ofc.feign.client;

import com.xescm.ofc.config.RestConfig;
import com.xescm.ofc.model.dto.rmc.RmcWarehouse;
import com.xescm.ofc.exception.BusinessException;
import com.xescm.ofc.feign.api.rmc.FeignRmcWarehouseAPI;
import com.xescm.uam.domain.feign.AuthRequestInterceptor;
import com.xescm.uam.utils.wrap.Wrapper;
import feign.Feign;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by lyh on 2016/11/1.
 */

@Service
public class FeignRmcWarehouseAPIClient {
    private static final Logger logger = LoggerFactory.getLogger(FeignRmcWarehouseAPI.class);
    @Resource
    RestConfig restConfig;
    @Resource
    private AuthRequestInterceptor authRequestInterceptor;
    /*public FeignCscWarehouseAPI getRmcApi() {
        FeignCscWarehouseAPI res = Feign.builder()
                .requestInterceptor(new AuthRequestInterceptor()).encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder()).target(FeignCscWarehouseAPI.class, restConfig.getRmcUrl());
        return res;
    }*/
    public FeignRmcWarehouseAPI getRmcApi() {
        FeignRmcWarehouseAPI res = Feign.builder()
                .requestInterceptor(authRequestInterceptor).encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder()).target(FeignRmcWarehouseAPI.class,restConfig.getRmcUrl());
        return res;
    }
    /*  public Wrapper<RmcWarehouse> getRmcWarehouseByid(@Param("id") String id){
        logger.debug("==>根据仓库ID获取仓库信息 id={}", id);
        if(null == id){
            throw new BusinessException("参数为空");
        }
        Wrapper<RmcWarehouse> rmcWarehouseByid = null;
        try {
            rmcWarehouseByid = getRmcApi().getRmcWarehouseByid(id);
        }catch (Exception ex){
            throw new BusinessException(ex.getMessage());
        }
        return rmcWarehouseByid;
    }*/
    public Wrapper<RmcWarehouse> queryByWarehouseCode(RmcWarehouse warehouse){
        logger.debug("==>根据仓库ID获取仓库信息 warehouse={}", warehouse);
        if(null == warehouse){
            throw new BusinessException("参数为空");
        }
        Wrapper<RmcWarehouse> rmcWarehouseByid = null;
        try {
            rmcWarehouseByid = getRmcApi().queryRmcWarehouseById(warehouse);
        }catch (Exception ex){
            throw new BusinessException(ex.getMessage(), ex);
        }
        return rmcWarehouseByid;
    }
}