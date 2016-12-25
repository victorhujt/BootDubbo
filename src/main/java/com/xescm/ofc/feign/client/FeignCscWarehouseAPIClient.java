package com.xescm.ofc.feign.client;

import com.xescm.base.model.wrap.WrapMapper;
import com.xescm.base.model.wrap.Wrapper;
import com.xescm.ofc.config.RestConfig;
import com.xescm.ofc.model.dto.csc.CscWarehouse;
import com.xescm.ofc.exception.BusinessException;
import com.xescm.ofc.feign.api.csc.FeignCscWarehouseAPI;
import com.xescm.ofc.web.jwt.AuthRequestInterceptor;
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
public class FeignCscWarehouseAPIClient {
    private static final Logger logger = LoggerFactory.getLogger(FeignCscWarehouseAPI.class);
    @Resource
    RestConfig restConfig;
    @Resource
    private AuthRequestInterceptor authRequestInterceptor;

    public FeignCscWarehouseAPI getCscApi() {
        FeignCscWarehouseAPI res = Feign.builder()
                .requestInterceptor(authRequestInterceptor).encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder()).target(FeignCscWarehouseAPI.class, restConfig.getCscUrl());
        return res;
    }

    public Wrapper<List<CscWarehouse>> getCscWarehouseByCustomerId(CscWarehouse cscWarehouse){
        logger.debug("==>通过客户编码取仓库 cscWarehouse={}", cscWarehouse);
        if(null == cscWarehouse){
            throw new BusinessException("参数为空");
        }
        Wrapper<List<CscWarehouse>> cscWarehouseByCustomerId = null;
        try {
            cscWarehouseByCustomerId = getCscApi().getCscWarehouseByCustomerId(cscWarehouse);
        } catch (RetryableException ex) {
            logger.error("==>调用接口发生异常：调用通过客户编码获取仓库接口(/api/csc/warehouse/queryWarehouseByGroupId)无法连接或超时. {}", ex);
            return WrapMapper.wrap(Wrapper.ERROR_CODE, "调用客户编码获取仓库接口无法连接或超时！");
        } catch (Exception ex){
            logger.error("==>调用接口发生异常：通过客户编码获取仓库接口(/api/csc/warehouse/queryWarehouseByGroupId). {}", ex);
            return WrapMapper.wrap(Wrapper.ERROR_CODE, "调用根据客户编码获取仓库接口异常！");
        }
        return cscWarehouseByCustomerId;
    }
}
