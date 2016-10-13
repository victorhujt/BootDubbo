package com.xescm.ofc;

import com.xescm.ofc.filter.SimpleCORSFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class XescmOfcApplication {


	@Bean
	public FilterRegistrationBean simpleCORSFilter() {
		final FilterRegistrationBean registrationBean = new FilterRegistrationBean();
		registrationBean.setFilter(new SimpleCORSFilter());
		registrationBean.addUrlPatterns("/*");
		registrationBean.setOrder(Integer.MAX_VALUE - 2);
		return registrationBean;
	}

	public static void main(String[] args) {
		SpringApplication.run(XescmOfcApplication.class, args);
	}
}
