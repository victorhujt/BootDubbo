package com.xescm.ofc;

import com.xescm.core.xss.XSSFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.servlet.MultipartConfigElement;

@ImportResource(locations={"classpath:dubbo-config.xml"})
@EnableTransactionManagement // 开启注解事务管理，等同于xml配置文件中的 <tx:annotation-driven />
@SpringBootApplication
@Configuration
@PropertySource("classpath:dubbo.properties")
public class XescmOfcApplication {

	@Value("${env}")
	private String env;

	@Bean
	public MultipartConfigElement multipartConfigElement(){
		MultipartConfigFactory factory = new MultipartConfigFactory();
		//设置文件大小限制,超了页面会抛出异常信息,这个时候需要进行异常处理
		factory.setMaxFileSize(5*1024*1024);//KB,MB
		//设置总上传数据总大小
		factory.setMaxRequestSize(20*1024*1024);
		return factory.createMultipartConfig();
	}

	@Bean
	public FilterRegistrationBean myXssFilter() {
		final FilterRegistrationBean registrationBean = new FilterRegistrationBean();
		registrationBean.setFilter(new XSSFilter());
		registrationBean.addUrlPatterns("/*");
		registrationBean.setOrder(Integer.MAX_VALUE);
		return registrationBean;
	}

	public static void main(String[] args) {
 		SpringApplication.run(XescmOfcApplication.class, args);
	}
}
