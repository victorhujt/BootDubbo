package com.xescm.ofc.mapper;

import com.xescm.ofc.domain.OfcIplimitRule;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;
@Repository
public interface OfcIplimitRuleMapper extends Mapper<OfcIplimitRule> {

    public void updateByIp(OfcIplimitRule rule);
}