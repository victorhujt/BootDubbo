package com.xescm.ofc.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Created by hujintao on 2017/3/29.
 */
@Configuration
@ConfigurationProperties(prefix = DingDingRobotConfig.DINGDING_PREFIX)
public class DingDingRobotConfig {
    public final static String DINGDING_PREFIX="DingDing";
    /**
     * 钉钉机器人的accessToken
     */
    private String accessToken;
    /**
     * 被@人的手机号
     */
    private String atMobiles;
    /**
     * @所有人时:true,否则为:false
     */
    private boolean  atAll;

    public String getAtMobiles() {
        return atMobiles;
    }

    public void setAtMobiles(String atMobiles) {
        this.atMobiles = atMobiles;
    }

    public boolean isAtAll() {
        return atAll;
    }

    public void setAtAll(boolean atAll) {
        this.atAll = atAll;
    }



    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
