package com.xescm.ofc.feign.client;

import com.xescm.base.model.wrap.WrapMapper;
import com.xescm.base.model.wrap.Wrapper;
import com.xescm.ofc.config.RestConfig;
import com.xescm.ofc.model.dto.rmc.RmcCompanyLineQO;
import com.xescm.ofc.model.vo.rmc.RmcCompanyLineVo;
import com.xescm.ofc.exception.BusinessException;
import com.xescm.ofc.feign.api.rmc.FeignRmcCompanyAPI;
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
public class FeignRmcCompanyAPIClient {
    private static final Logger logger = LoggerFactory.getLogger(FeignRmcCompanyAPI.class);
    /*@Value("${restful.uamUrl}")
    private String uamUrl;*/
    @Resource
    RestConfig restConfig;
    @Resource
    private AuthRequestInterceptor authRequestInterceptor;

    public FeignRmcCompanyAPI getApi() {
        FeignRmcCompanyAPI res = Feign.builder()
                .requestInterceptor(authRequestInterceptor).encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder())
                .target(FeignRmcCompanyAPI.class, restConfig.getRmcUrl());
        return res;
    }

    public Wrapper<List<RmcCompanyLineVo>> queryCompanyLine(RmcCompanyLineQO rmcCompanyLineQO){
        Wrapper<List<RmcCompanyLineVo>> wrapper = null;
        logger.debug("==>查询供应商 cscSupplierInfoDto={}", rmcCompanyLineQO);
        if(null == rmcCompanyLineQO){
            throw new BusinessException("参数为空");
        }
        try {
            wrapper = getApi().queryCompanyLine(rmcCompanyLineQO);
        } catch (RetryableException ex) {
            logger.error("==>调用接口发生异常：调用线路资源查询接口(/api/rmc/company/queryCompanyLine)无法连接或超时. {}", ex);
            return WrapMapper.wrap(Wrapper.ERROR_CODE, "调用线路资源查询接口无法连接或超时！");
        } catch (Exception ex){
            logger.error("==>调用接口发生异常：线路资源查询接口(/api/rmc/company/queryCompanyLine). {}", ex);
            return WrapMapper.wrap(Wrapper.ERROR_CODE, "调用线路资源查询接口发生异常！");
        }
        return wrapper;
    }
}
