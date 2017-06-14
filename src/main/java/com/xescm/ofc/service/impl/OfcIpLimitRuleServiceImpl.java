package com.xescm.ofc.service.impl;

import com.xescm.ofc.domain.OfcIplimitRule;
import com.xescm.ofc.mapper.OfcIplimitRuleMapper;
import com.xescm.ofc.service.OfcIpLimitRuleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by hujintao on 2017/6/8.
 */
@Service
public class OfcIpLimitRuleServiceImpl extends BaseService<OfcIplimitRule> implements OfcIpLimitRuleService {

    @Resource
    private OfcIplimitRuleMapper ofcIplimitRuleMapper;
    @Override
    public void updateByIp(OfcIplimitRule rule) {
        ofcIplimitRuleMapper.updateByIp(rule);
    }
}
