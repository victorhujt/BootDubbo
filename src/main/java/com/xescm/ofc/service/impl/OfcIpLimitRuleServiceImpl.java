package com.xescm.ofc.service.impl;

import com.xescm.ofc.config.IpLimitRuleConfig;
import com.xescm.ofc.domain.OfcIplimitRule;
import com.xescm.ofc.enums.ResultCodeEnum;
import com.xescm.ofc.mapper.OfcIplimitRuleMapper;
import com.xescm.ofc.service.OfcIpLimitRuleService;
import com.xescm.ofc.utils.CheckUtils;
import com.xescm.ofc.utils.IpUtils;
import com.xescm.ofc.utils.RedisOperationUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.xescm.ofc.constant.OrderConstConstant.*;

/**
 * Created by hujintao on 2017/6/8.
 */
@Service
public class OfcIpLimitRuleServiceImpl extends BaseService<OfcIplimitRule> implements OfcIpLimitRuleService {

    @Resource
    private OfcIplimitRuleMapper ofcIplimitRuleMapper;

    @Resource
    private IpLimitRuleConfig ipLimitRuleConfig;
    @Resource
    private RedisOperationUtils redisOperationUtils;
    @Override
    public void updateByIp(OfcIplimitRule rule) {
        ofcIplimitRuleMapper.updateByIp(rule);
    }

    @Override
    public void  checkLimit(RedisOperationUtils redisOperationUtils, HttpServletRequest request) {
        boolean isSend = false;
        String ip = IpUtils.getIpAddr(request);
        if(redisOperationUtils.hasKey("SMS:"+ip)){
            isSend = true;
        }
        //验证ip是否冻结 缓存redis 10分钟
        if(redisOperationUtils.hasKey("ip:"+ip)){
            redisOperationUtils.increment("ip:"+ip+":"+QUERY_REQUEST_COUNT, 1L);
            CheckUtils.checkArgument(redisOperationUtils.hasKey("ip:"+ip), ResultCodeEnum.IPISFREEZE);
        }
        //验证ip是否已经被加入黑名单
        OfcIplimitRule ofcIpLimitRule = new OfcIplimitRule();
        ofcIpLimitRule.setIp(ip);
        List<OfcIplimitRule> rules = select(ofcIpLimitRule);
        if(!CollectionUtils.isEmpty(rules)){
            OfcIplimitRule ofcIplimitRule = rules.get(0);
            CheckUtils.checkArgument(IS_BLACK.equals(ofcIplimitRule.getBlack()), ResultCodeEnum.IPISFORBIDDEN);
        }
        Long currentTime = System.currentTimeMillis();
        if(redisOperationUtils.hasKey("ip:"+ip+":"+QUERY_REQUEST_COUNT)){
            Long reqCount =  redisOperationUtils.getValue("ip:"+ip+":"+QUERY_REQUEST_COUNT);
            Long first = redisOperationUtils.getValue("ip:"+ip+":"+FIRST_REQUEST_TIME);
            redisOperationUtils.increment("ip:"+ip+":"+QUERY_REQUEST_COUNT, 1L);

            logger.info("距离第一次请求的时间为:{}",currentTime - first+"ms");
            logger.info("请求的次数为:{}",reqCount);
            //第一阀值
            if(!isSend){
                if((currentTime - first) > ipLimitRuleConfig.getFristMinTime()*60*1000 && (currentTime - first) < ipLimitRuleConfig.getFristMaxTime()*60*1000){
                    CheckUtils.checkArgument(reqCount > ipLimitRuleConfig.getFirstThresholdMin() && reqCount < ipLimitRuleConfig.getFirstThresholdMax(), ResultCodeEnum.OPERATIONSTOOFREQUENT);
                }
            }
            //第二阀值
            if((currentTime - first) > ipLimitRuleConfig.getSecondMinTime()*60*1000 && (currentTime - first)< ipLimitRuleConfig.getSecondMaxTime()*60*1000){
                if(reqCount > ipLimitRuleConfig.getSecondThresholdMin() && reqCount < ipLimitRuleConfig.getSecondThresholdMax()){
                    redisOperationUtils.set("ip:"+ip,"freezing",5L, TimeUnit.MINUTES);//ip缓存五分钟
                    logger.info("ip缓存5分钟，{}",ip);
                }
            }
            //第三阀值
            if(currentTime - first > ipLimitRuleConfig.getThirdMinTime()*60*1000){
                if(reqCount > ipLimitRuleConfig.getThirdThresholdMin()) {
                    OfcIplimitRule rule = new OfcIplimitRule();
                    rule.setBlack(IS_BLACK);
                    rule.setIp(ip);
                    save(rule);
                    logger.info("ip加入黑名单，{}",ip);
                }
            }
        }else{
            redisOperationUtils.set("ip:"+ip+":"+QUERY_REQUEST_COUNT,String.valueOf(1L),15L,TimeUnit.MINUTES);
            redisOperationUtils.set("ip:"+ip+":"+FIRST_REQUEST_TIME,String.valueOf(System.currentTimeMillis()),15L,TimeUnit.MINUTES);
        }
    }
}
