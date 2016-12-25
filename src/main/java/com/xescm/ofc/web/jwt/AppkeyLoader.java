package com.xescm.ofc.web.jwt;

import com.xescm.core.constant.UamConstant;
import com.xescm.ofc.service.TokenService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by wangsongtao on 16/4/11.
 */
@Component
public class AppkeyLoader implements InitializingBean {

    @Value("${auth.appKey}")
    public String appKey;

    private static final Map<String,String> GLOBAL_MP = new HashMap<String,String>();

    @Resource
    private TokenService tokenService;


    public static String getApiToken(){
        String token = GLOBAL_MP.get(UamConstant.JWT_API_TOKEN);
        return token;
    }


    public static String getApiPrivateKey(){
        String token = GLOBAL_MP.get(UamConstant.JWT_API_PRIVATE_KEY);
        return token;
    }

    public static String getViewPrivateKey(){
        String token = GLOBAL_MP.get(UamConstant.JWT_VIEW_PRIVATE_KEY);
        return token;
    }



    private void init(){
        // 从服务端获取Token
        String privateApiKey = tokenService.getApiPrivateKey();
        String token = tokenService.encodeApiToken(privateApiKey,appKey);
        GLOBAL_MP.put(UamConstant.JWT_API_TOKEN,token);
        GLOBAL_MP.put(UamConstant.JWT_API_PRIVATE_KEY,privateApiKey);
        GLOBAL_MP.put(UamConstant.JWT_VIEW_PRIVATE_KEY,tokenService.getViewPrivateKey());
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        init();
    }
}

