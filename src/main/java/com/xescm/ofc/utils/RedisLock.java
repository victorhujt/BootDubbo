package com.xescm.ofc.utils;

import com.xescm.ofc.constant.OfcKeyConstants;
import com.xescm.ofc.enums.EnableStateEnum;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * Created by hiyond on 2017/3/27.
 */
@Component
public class RedisLock {

    @Autowired
    private StringRedisTemplate redisTemplate;
    //过期时间 秒
    private static final Long EXPIRE_TIME = 60 * 10L;

    /**
     * 获取redis锁，如果没有获取到还会一直获取
     *
     * @param key
     * @return
     */
    public boolean lockWithWait(String key) {
        if (StringUtils.isBlank(key)) {
            throw new IllegalArgumentException("参数key不能为空");
        }
        if (!key.startsWith(OfcKeyConstants.OFC_KEY)) {
            key = OfcKeyConstants.OFC_KEY + key;
        }
        while (true) {
            if (this.redisLock(key)) {
                break;
            }
        }
        return true;
    }

    /**
     * redis 锁
     *
     * @param key 锁KEY
     * @return true:加锁成功，false：加锁失败
     */
    public boolean redisLock(String key) {
        if (StringUtils.isBlank(key)) {
            throw new IllegalArgumentException("参数key不能为空");
        }
        //给key加上订单中心的标识
        if (!key.startsWith(OfcKeyConstants.OFC_KEY)) {
            key = OfcKeyConstants.OFC_KEY + key;
        }
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        String redisLock = valueOperations.getAndSet(key, EnableStateEnum.DISABLE.getCode());
        //如果redisLock返回的旧值为空，证明获取到了锁，设置过期时间
        if (StringUtils.isBlank(redisLock)) {
            setExpire(redisTemplate, key);
            return true;
        }
        return false;
    }


    /**
     * @param key 锁KEY
     * @return true:当前已经存在锁， false:当前不存在锁（并且把不存在的锁添加的redis锁库中）
     * @deprecated
     */
    public boolean lock(String key) {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        String redisLock = valueOperations.getAndSet(key, EnableStateEnum.DISABLE.getCode());

        //如果当前锁已经存在，返回已经存在的锁，如果不存在返回null,并且把不存在锁，添加到redis锁库中
        if (StringUtils.isNotBlank(redisLock)) {
            setExpire(redisTemplate, key);
            return true;
        }
        return false;
    }

    public Boolean setExpire(StringRedisTemplate redisTemplate, String key) {
        return redisTemplate.expire(key, EXPIRE_TIME, TimeUnit.SECONDS);
    }

    public void deleteKey(String key) {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        valueOperations.getOperations().delete(key);
    }

}
