package com.xescm.ofc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
//@Profile({ "dev", "test" })
public class SwaggerConfiguration {
//	@Bean
//	public Docket reservationApi() {
//		return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select()
//				.paths(Predicates.not(PathSelectors.regex("/error"))) // Exclude
//				// Spring
//				// error
//				// controllers
//				//.apis(RequestHandlerSelectors.basePackage("com.xescm.ofc"))
//				.build().useDefaultResponseMessages(false);
//	}

	@Bean
	public Docket createRestApi() {
		return new Docket(DocumentationType.SWAGGER_2).
				apiInfo(apiInfo()).//用来创建该Api的基本信息
				select().//暴露哪些接口来访问
				apis(RequestHandlerSelectors.basePackage("com.xescm.ofc")).//此处暴露整个controller包下类
				paths(PathSelectors.any()).
				build();
	}

	private ApiInfo apiInfo() {
		String email = "15500005676@163.com";
		String name = "liuzhaoming";
		String url = "http://www.hnxianyi.com";
		Contact contact = new Contact(name, url, email);
		return new ApiInfoBuilder().title("接口文档").description("用户中心API").contact(contact).build();
	}
}