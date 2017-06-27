package com.xescm.ofc.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

/**
* <p>Title: RedisUtils. </p>
* <p>redis工具类 </p>
*
* @Author hiyond
* @CreateDate 2017/3/15
*/
@Component
public class RedisUtils {

    @Autowired
    private StringRedisTemplate redisTemplate;

    public boolean checkKeyExist(String key) {
        return redisTemplate.hasKey(key);
    }

}
