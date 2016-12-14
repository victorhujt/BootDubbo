package com.xescm.ofc;

import com.xescm.uam.domain.feign.AuthRequestInterceptor;
import com.xescm.uam.domain.feign.FeignUamAPIClient;
import com.xescm.uam.exception.MyHandlerExceptionResolver;
import com.xescm.uam.utils.jwt.AppkeyLoader;
import com.xescm.uam.utils.jwt.SimpleCORSFilter;
import com.xescm.uam.utils.jwt.TokenUtils;
import com.xescm.uam.web.interceptor.AuthApiInterceptor;
import com.xescm.uam.web.interceptor.AuthViewInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.boot.context.embedded.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.servlet.MultipartConfigElement;

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
	public MultipartConfigElement multipartConfigElement(){
		MultipartConfigFactory factory = new MultipartConfigFactory();
		//设置文件大小限制,超了页面会抛出异常信息,这个时候需要进行异常处理
		factory.setMaxFileSize(1*1024*1024);//KB,MB
		//设置总上传数据总大小
		factory.setMaxRequestSize(5*1024*1024);
		return factory.createMultipartConfig();
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
	public AuthRequestInterceptor authRequestInterceptor() { return new AuthRequestInterceptor();}

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
	@Bean
	public MyHandlerExceptionResolver myHandlerExceptionResolver() {
		return new MyHandlerExceptionResolver();
	}
	public static void main(String[] args) {
 		SpringApplication.run(XescmOfcApplication.class, args);
	}
}
