package com.xescm.ofc.feign.client;

import com.xescm.ofc.config.RestConfig;
import com.xescm.ofc.domain.dto.CscSupplierInfoDto;
import com.xescm.ofc.feign.api.FeignCscGoodsAPI;
import com.xescm.ofc.feign.api.FeignCscSupplierAPI;
import com.xescm.uam.domain.feign.AuthRequestInterceptor;
import com.xescm.uam.utils.wrap.Wrapper;
import feign.Feign;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

/**
 * Created by lyh on 2016/10/19.
 */
public class FeignCscSupplierAPIClient {
    private static final Logger logger = LoggerFactory.getLogger(FeignCscSupplierAPI.class);

    @Resource
    RestConfig restConfig;

    public FeignCscSupplierAPI getApi() {
        FeignCscSupplierAPI res = Feign.builder()
                .requestInterceptor(new AuthRequestInterceptor()).encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder()).target(FeignCscSupplierAPI.class, restConfig.getCscUrl());
        return res;
    }
    public Wrapper<?> querySupplierByAttribute(CscSupplierInfoDto cscSupplierInfoDto){
        logger.debug("==>查询货品 cscSupplierInfoDto={}", cscSupplierInfoDto);
        Wrapper<?> wrapper = getApi().querySupplierByAttribute(cscSupplierInfoDto);
        return wrapper;
    }
    public Wrapper<?> addSupplierBySupplierCode(CscSupplierInfoDto cscSupplierInfoDto){
        logger.debug("==>查询货品 cscSupplierInfoDto={}", cscSupplierInfoDto);
        Wrapper<?> wrapper = getApi().addSupplierBySupplierCode(cscSupplierInfoDto);
        return wrapper;

    }
    public Wrapper<?> modifySupplierBySupplierCode(CscSupplierInfoDto cscSupplierInfoDto){
        logger.debug("==>查询货品 cscSupplierInfoDto={}", cscSupplierInfoDto);
        Wrapper<?> wrapper = getApi().modifySupplierBySupplierCode(cscSupplierInfoDto);
        return wrapper;
    }

}
