package com.xescm.ofc.feign.client;

import com.xescm.ofc.config.RestConfig;
import com.xescm.ofc.exception.BusinessException;
import com.xescm.ofc.feign.api.csc.FeignCscCustomerAPI;
import com.xescm.ofc.feign.api.tfc.FeignTfcTransPlanApi;
import com.xescm.ofc.model.dto.tfc.TransportNoDTO;
import com.xescm.ofc.utils.Response;
import com.xescm.uam.domain.feign.AuthRequestInterceptor;
import feign.Feign;
import feign.RetryableException;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.net.ConnectException;

/**
 * Created by lyh on 2016/11/15.
 */
@Service
public class FeignTfcTransPlanApiClient {
    private static final Logger logger = LoggerFactory.getLogger(FeignCscCustomerAPI.class);

    @Resource
    RestConfig restConfig;
    @Resource
    private AuthRequestInterceptor authRequestInterceptor;

    public FeignTfcTransPlanApi getApi() {
        FeignTfcTransPlanApi res = Feign.builder()
                .requestInterceptor(authRequestInterceptor).encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder())
                .target(FeignTfcTransPlanApi.class, restConfig.getEpcUrl());
        return res;
    }

    public Response cancelTransport(TransportNoDTO transportNoDTO){
        logger.info("==>订单中心向TFC发送取消运输计划单请求 transportNoDTO={}", ToStringBuilder.reflectionToString(transportNoDTO));
        Response response = null;
        if(null == transportNoDTO){
            throw new BusinessException("参数为空");
        }
        try {
            response = getApi().cancelTransport(transportNoDTO);
        } catch (RetryableException ex) {
            logger.error("==>调用接口发生异常：调用TFC取消运输计划单接口(/restApi/tfc/cancelTransport)无法连接或超时. {} ", ex);
            throw new BusinessException("调用TFC取消运输计划单接口无法连接或超时！");
        } catch (Exception ex){
            logger.error("==>调用接口发生异常：调用TFC取消运输计划单接口(/restApi/tfc/cancelTransport). {}", ex);
            throw new BusinessException("调用TFC取消运输计划单发生异常");
        }

        return response;
    }

}
