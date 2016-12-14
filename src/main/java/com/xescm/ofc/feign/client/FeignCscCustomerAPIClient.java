package com.xescm.ofc.feign.client;
import com.xescm.ofc.config.RestConfig;
import com.xescm.ofc.model.dto.csc.*;
import com.xescm.ofc.model.vo.csc.CscCustomerVo;
import com.xescm.ofc.exception.BusinessException;
import com.xescm.ofc.feign.api.csc.FeignCscCustomerAPI;
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
 * Created by lyh on 2016/10/19.
 */
@Service
public class FeignCscCustomerAPIClient {
    private static final Logger logger = LoggerFactory.getLogger(FeignCscCustomerAPI.class);
    /*@Value("${restful.uamUrl}")
    private String uamUrl;*/
    @Resource
    RestConfig restConfig;
    @Resource
    private AuthRequestInterceptor authRequestInterceptor;

    public FeignCscCustomerAPI getApi() {
        FeignCscCustomerAPI res = Feign.builder()
                .requestInterceptor(authRequestInterceptor).encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder())
                .target(FeignCscCustomerAPI.class, restConfig.getCscUrl());
        return res;
    }

    public Wrapper<?> queryCustomerByName(QueryCustomerNameDto queryCustomerNameDto){
        logger.debug("==>通过客户名称获取客户列表 queryCustomerNameDto={}", queryCustomerNameDto);
        if(null == queryCustomerNameDto){
            throw new BusinessException("参数为空");
        }
        Wrapper<?> wrapper = getApi().queryCustomerByName(queryCustomerNameDto);
        return wrapper;
    }

    /**
     * 根据 CustomerCode
     * @param
     * @return
     */
    public Wrapper<CscCustomerVo> queryCustomerByCustomerCodeOrId(QueryCustomerCodeDto queryCustomerCodeDto) {
        logger.debug("通过QueryCustomerCodeDto查询客户信息:{}",queryCustomerCodeDto);
        if(queryCustomerCodeDto == null){
            throw new BusinessException("参数为空");
        }
        Wrapper<CscCustomerVo> wrapper = getApi().queryCustomerByCustomerCodeOrId(queryCustomerCodeDto);
        return wrapper;
    }

    /**
     * 模糊匹配，查询客户
     * @param queryCustomerNameDto
     * @return
     */
    public Wrapper<?> QueryCustomerByNameAvgue(QueryCustomerNameAvgueDto queryCustomerNameDto) {
        logger.debug("通过QueryCustomerCodeDto查询客户信息:{}",queryCustomerNameDto);
        if(null == queryCustomerNameDto){
            throw new BusinessException("参数为空");
        }
        Wrapper<?> wrapper = getApi().QueryCustomerByNameAvgue(queryCustomerNameDto);
        return wrapper;
    }
}
