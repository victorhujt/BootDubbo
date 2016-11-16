package com.xescm.ofc.config;

import com.google.common.base.Predicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@Profile({ "dev", "test" })
public class SwaggerConfiguration {
	@Bean
	public Docket reservationApi() {
		return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select()
				.paths(Predicates.not(PathSelectors.regex("/error"))) // Exclude
				// Spring
				// error
				// controllers
				//.apis(RequestHandlerSelectors.basePackage("com.xescm.ofc"))
				.build().useDefaultResponseMessages(false);
	}

	private ApiInfo apiInfo() {
		String email = "15500005676@163.com";
		String name = "liuzhaoming";
		String url = "http://www.hnxianyi.com";
		Contact contact = new Contact(name, url, email);
		return new ApiInfoBuilder().title("接口文档").description("用户中心API").contact(contact).build();
	}
}