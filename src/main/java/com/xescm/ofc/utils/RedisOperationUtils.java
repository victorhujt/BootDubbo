package com.xescm.ofc.utils;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * Created by hujintao on 2017/6/8.
 */
@Component
public class RedisOperationUtils {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    public RedisOperationUtils(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    public RedisOperationUtils() {
    }

    public void pushKeyToCache(String key, String value){
        ValueOperations<String, String> valueOps = stringRedisTemplate.opsForValue();
        valueOps.set(key,value);
    }
    public void pushKeyToCache(String key,String value,Long time,TimeUnit timeUnit){
        ValueOperations<String, String> valueOps = stringRedisTemplate.opsForValue();
        valueOps.set(key,value,time, timeUnit);
    }


    public void increment(String key,Long value){
        ValueOperations<String, String> valueOps = stringRedisTemplate.opsForValue();
        valueOps.increment(key,value);
    }

    public boolean hasKey(String key){
        return stringRedisTemplate.hasKey(key);
    }

    public Long getValue(String key){
        if(hasKey(key)){
            ValueOperations<String, String> valueOps = stringRedisTemplate.opsForValue();
           return  Long.parseLong(valueOps.get(key));
        }
        return null;
    }



    public void deleteKey(String key){
        stringRedisTemplate.delete(key);
    }

}
