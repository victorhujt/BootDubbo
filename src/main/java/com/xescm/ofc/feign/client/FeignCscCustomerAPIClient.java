package com.xescm.ofc.feign.client;
import com.xescm.ofc.feign.api.FeignCscCustomerAPI;
import com.xescm.uam.domain.feign.FeignUamShiroAPI;
import com.xescm.uam.utils.wrap.Wrapper;
import feign.Feign;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

/**
 * Created by lyh on 2016/10/19.
 */
public class FeignCscCustomerAPIClient {
    private static final Logger logger = LoggerFactory.getLogger(FeignCscCustomerAPI.class);
    @Value("${restful.uamUrl}")
    private String uamUrl;

    public FeignUamShiroAPI getApi() {
        FeignUamShiroAPI res = Feign.builder()
                .requestInterceptor(new AuthRequestInterceptor()).encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder()).target(FeignUamShiroAPI.class, uamUrl);
        return res;
    }

}
