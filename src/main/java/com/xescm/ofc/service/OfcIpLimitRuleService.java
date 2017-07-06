package com.xescm.ofc.service;

import com.xescm.ofc.domain.OfcIplimitRule;
import com.xescm.ofc.utils.RedisOperationUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by hujintao on 2017/6/8.
 */
public interface OfcIpLimitRuleService extends IService<OfcIplimitRule> {

public void updateByIp(OfcIplimitRule rule);


public void  checkLimit(RedisOperationUtils redisOperationUtils, HttpServletRequest request);


}
