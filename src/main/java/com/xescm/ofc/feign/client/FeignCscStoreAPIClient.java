package com.xescm.ofc.feign.client;

import com.xescm.base.model.wrap.Wrapper;
import com.xescm.ofc.config.RestConfig;
import com.xescm.ofc.exception.BusinessException;
import com.xescm.ofc.model.dto.csc.QueryStoreDto;
import com.xescm.ofc.model.vo.csc.CscStorevo;
import com.xescm.ofc.feign.api.csc.FeignCscStoreAPI;
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
 * Created by lyh on 2016/11/6.
 */
@Service
public class FeignCscStoreAPIClient {
    private static final Logger logger = LoggerFactory.getLogger(FeignCscStoreAPI.class);
    @Resource
    RestConfig restConfig;
    @Resource
    private AuthRequestInterceptor authRequestInterceptor;

    public FeignCscStoreAPI getApi() {
        FeignCscStoreAPI res = Feign.builder()
                .requestInterceptor(authRequestInterceptor).encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder()).target(FeignCscStoreAPI.class, restConfig.getCscUrl());
        return res;
    }
    public Wrapper<List<CscStorevo>> getStoreByCustomerId(QueryStoreDto queryStoreDto){
        Wrapper<List<CscStorevo>> storeByCustomerId = null;
        logger.info("==>queryStoreDto={}",queryStoreDto);
        if(null == queryStoreDto){
            throw new BusinessException("参数为空");
        }
        try {
            storeByCustomerId = getApi().getStoreByCustomerId(queryStoreDto);
        } catch (RetryableException ex) {
            logger.error("==>调用接口发生异常：调用查询客户下店铺接口(/api/csc/store/getStoreByCustomerId)无法连接或超时. {}", ex);
            throw new BusinessException("调用查询客户下店铺接口无法连接或超时！");
        } catch (Exception ex){
            logger.error("==>调用接口发生异常：通过查询客户下店铺接口(/api/csc/store/getStoreByCustomerId). {}", ex);
            throw new BusinessException("调用查询客户下店铺接口异常！");
        }
        return  storeByCustomerId;
    }

}
