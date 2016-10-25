package com.xescm.ofc;

import com.xescm.uam.domain.feign.FeignUamAPIClient;
import com.xescm.uam.utils.jwt.*;
import com.xescm.uam.web.interceptor.AuthApiInterceptor;
import com.xescm.uam.web.interceptor.AuthViewInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement // 开启注解事务管理，等同于xml配置文件中的 <tx:annotation-driven />
@EnableAutoConfiguration
@ComponentScan
@SpringBootApplication
public class XescmOfcApplication {



	private TokenUtils tokenUtils;

	@Value("${env}")
	private String env;

	@Bean
	public FilterRegistrationBean simpleCORSFilter() {
		final FilterRegistrationBean registrationBean = new FilterRegistrationBean();
		registrationBean.setFilter(new SimpleCORSFilter());
		registrationBean.addUrlPatterns("/*");
		registrationBean.setOrder(Integer.MAX_VALUE-2);
		return registrationBean;
	}

	@Bean
	public AuthApiInterceptor authApiInterceptor(){
		return new AuthApiInterceptor();
	}

	@Bean
	public AuthViewInterceptor authViewInterceptor(){
		return new AuthViewInterceptor();
	}

	@Bean
	public AppkeyLoader appkeyLoader(){
		return new AppkeyLoader();
	}

	@Bean
	public FeignUamAPIClient feignUamAPIClient(){
		return new FeignUamAPIClient();
	}

	@Bean(name = "tokenUtils")
	public TokenUtils getTokenUtils(){
		if(tokenUtils == null){
			tokenUtils = new TokenUtils();
		}
		return tokenUtils;
	}

	public static void main(String[] args) {
		SpringApplication.run(XescmOfcApplication.class, args);
	}
}
