package com.xescm.ofc.feign.client;

import com.xescm.ofc.config.RestConfig;
import com.xescm.ofc.model.dto.csc.CscSupplierInfoDto;
import com.xescm.ofc.exception.BusinessException;
import com.xescm.ofc.feign.api.csc.FeignCscSupplierAPI;
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
 * Created by lyh on 2016/10/19.
 */
@Service
public class FeignCscSupplierAPIClient {
    private static final Logger logger = LoggerFactory.getLogger(FeignCscSupplierAPI.class);

    @Resource
    RestConfig restConfig;
    @Resource
    private AuthRequestInterceptor authRequestInterceptor;

    public FeignCscSupplierAPI getApi() {
        FeignCscSupplierAPI res = Feign.builder()
                .requestInterceptor(authRequestInterceptor).encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder()).target(FeignCscSupplierAPI.class, restConfig.getCscUrl());
        return res;
    }
    public Wrapper<List<CscSupplierInfoDto>> querySupplierByAttribute(CscSupplierInfoDto cscSupplierInfoDto){
        Wrapper<List<CscSupplierInfoDto>> wrapper = null;
        logger.debug("==>查询供应商 cscSupplierInfoDto={}", cscSupplierInfoDto);
        if(null == cscSupplierInfoDto){
            throw new BusinessException("参数为空");
        }
        try {
            wrapper = getApi().querySupplierByAttribute(cscSupplierInfoDto);
        } catch (RetryableException ex) {
            logger.error("==>调用接口发生异常：调用查询客户供应商接口(/api/csc/supplier/querySupplierByAttribute)无法连接或超时. {}", ex);
            return WrapMapper.wrap(Wrapper.ERROR_CODE, "调用查询客户供应商接口无法连接或超时！");
        } catch (Exception ex){
            logger.error("==>调用接口发生异常：查询客户供应商接口(/api/csc/supplier/querySupplierByAttribute). {}", ex);
            return WrapMapper.wrap(Wrapper.ERROR_CODE, "调用查询客户供应商接口异常！");
        }
        return wrapper;
    }
    public Wrapper<?> addSupplierBySupplierCode(CscSupplierInfoDto cscSupplierInfoDto){
        Wrapper<?> wrapper = null;
        logger.debug("==>添加供应商 cscSupplierInfoDto={}", cscSupplierInfoDto);
        if(null == cscSupplierInfoDto){
            throw new BusinessException("参数为空");
        }
        try {
            wrapper = getApi().addSupplierBySupplierCode(cscSupplierInfoDto);
        } catch (RetryableException ex) {
            logger.error("==>调用接口发生异常：调用添加供应商接口(/api/csc/supplier/addSupplierByCustomerId)无法连接或超时. {}", ex);
            return WrapMapper.wrap(Wrapper.ERROR_CODE, "调用添加供应商接口无法连接或超时！");
        } catch (Exception ex){
            logger.error("==>调用接口发生异常：添加供应商接口(/api/csc/supplier/addSupplierByCustomerId). {}", ex);
            return WrapMapper.wrap(Wrapper.ERROR_CODE, "调用添加供应商接口异常！");
        }
        return wrapper;
    }

}
