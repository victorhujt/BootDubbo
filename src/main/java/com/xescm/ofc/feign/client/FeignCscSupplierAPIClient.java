package com.xescm.ofc.feign.client;

import com.xescm.ofc.config.RestConfig;
import com.xescm.ofc.model.dto.csc.CscSupplierInfoDto;
import com.xescm.ofc.exception.BusinessException;
import com.xescm.ofc.feign.api.csc.FeignCscSupplierAPI;
import com.xescm.uam.domain.feign.AuthRequestInterceptor;
import com.xescm.uam.utils.wrap.Wrapper;
import feign.Feign;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by lyh on 2016/10/19.
 */
@Service
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
    public Wrapper<List<CscSupplierInfoDto>> querySupplierByAttribute(CscSupplierInfoDto cscSupplierInfoDto){
        logger.debug("==>查询供应商 cscSupplierInfoDto={}", cscSupplierInfoDto);
        if(null == cscSupplierInfoDto){
            throw new BusinessException("参数为空");
        }
        Wrapper<List<CscSupplierInfoDto>> wrapper = getApi().querySupplierByAttribute(cscSupplierInfoDto);
        return wrapper;
    }
    public Wrapper<?> addSupplierBySupplierCode(CscSupplierInfoDto cscSupplierInfoDto){
        logger.debug("==>添加供应商 cscSupplierInfoDto={}", cscSupplierInfoDto);
        if(null == cscSupplierInfoDto){
            throw new BusinessException("参数为空");
        }
        Wrapper<?> wrapper = getApi().addSupplierBySupplierCode(cscSupplierInfoDto);
        return wrapper;
    }

}
