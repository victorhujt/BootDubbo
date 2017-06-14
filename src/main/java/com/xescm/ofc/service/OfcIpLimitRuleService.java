package com.xescm.ofc.service;

import com.xescm.ofc.domain.OfcIplimitRule;

/**
 * Created by hujintao on 2017/6/8.
 */
public interface OfcIpLimitRuleService extends IService<OfcIplimitRule> {

public void updateByIp(OfcIplimitRule rule);


}
