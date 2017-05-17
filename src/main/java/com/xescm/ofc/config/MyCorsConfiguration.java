package com.xescm.ofc.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * <p>Title:	  MyCorsConfiguration <br/> </p>
 * <p>Description 跨域配置 <br/> </p>
 * <p>Company:    http://www.hnxianyi.com  <br/> </p>
 *
 * @Author <a href="liu_zhaoming@sina.cn"/>刘兆明</a>  <br/>
 * @Date 2016/12/26 20:49
 */
@Configuration
public class MyCorsConfiguration {

    @Autowired
    private RestConfig restConfig;

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/ofc/**").allowedOrigins(restConfig.getUamUrl(),restConfig.getPaas(), restConfig.getPaasDev());// 用户中心
//                registry.addMapping("/ofc/searchOverallOrder").allowedOrigins();
            }
        };
    }
}
