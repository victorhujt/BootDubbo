package com.xescm.ofc.config;

import com.alibaba.dts.common.exception.InitException;
import com.alibaba.edas.schedulerX.SchedulerXClient;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Date;

@Data
@Configuration
@ConfigurationProperties(prefix = SchedulerXConfig.JOB_PREFIX)
@EnableAutoConfiguration
public class SchedulerXConfig {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public static final String JOB_PREFIX = "aliyun.schedulerX";
    private String groupId;
    private String regionName;

    @Bean
    public SchedulerXClient schedulerXClient(){
        SchedulerXClient schedulerXClient = new SchedulerXClient();
        schedulerXClient.setGroupId(getGroupId());
        schedulerXClient.setRegionName(getRegionName());
        try {
            schedulerXClient.init();
            logger.info("GroupId={}, RegionName={}, 注入schedulerXClient!", getGroupId(), getRegionName());
        } catch (InitException e) {
            e.printStackTrace();
        }
        return schedulerXClient;
    }
}