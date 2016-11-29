package com.xescm.ofc.feign.client;

import com.xescm.ofc.config.RestConfig;
import com.xescm.ofc.model.dto.rmc.RmcCompanyLineQO;
import com.xescm.ofc.model.vo.rmc.RmcCompanyLineVo;
import com.xescm.ofc.exception.BusinessException;
import com.xescm.ofc.feign.api.rmc.FeignRmcCompanyAPI;
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
public class FeignRmcCompanyAPIClient {
    private static final Logger logger = LoggerFactory.getLogger(FeignRmcCompanyAPI.class);
    /*@Value("${restful.uamUrl}")
    private String uamUrl;*/
    @Resource
    RestConfig restConfig;


    public FeignRmcCompanyAPI getApi() {
        FeignRmcCompanyAPI res = Feign.builder()
                .requestInterceptor(new AuthRequestInterceptor()).encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder())
                .target(FeignRmcCompanyAPI.class, restConfig.getRmcUrl());
        return res;
    }

    public Wrapper<List<RmcCompanyLineVo>> queryCompanyLine(RmcCompanyLineQO rmcCompanyLineQO){
        logger.debug("==>查询供应商 cscSupplierInfoDto={}", rmcCompanyLineQO);
        if(null == rmcCompanyLineQO){
            throw new BusinessException("参数为空");
        }
        Wrapper<List<RmcCompanyLineVo>> wrapper = getApi().queryCompanyLine(rmcCompanyLineQO);
        return wrapper;
    }
}
