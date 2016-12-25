package com.xescm.ofc.web.jwt;

import feign.RequestInterceptor;
import feign.RequestTemplate;

/**
 * Created by wangsongtao on 2016/12/25.
 */
public class AuthRequestInterceptor  implements RequestInterceptor {
    public AuthRequestInterceptor() {
    }

    public void apply(RequestTemplate requestTemplate) {
        String token = AppkeyLoader.getApiToken();
        requestTemplate.header("Authorization", new String[]{"Bearer " + token});
    }
}