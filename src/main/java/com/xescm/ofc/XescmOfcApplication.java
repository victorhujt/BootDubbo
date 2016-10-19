package com.xescm.ofc;

/*import com.xescm.uam.utils.jwt.JwtApiFilter;
import com.xescm.uam.utils.jwt.JwtViewFilter;
import com.xescm.uam.utils.jwt.SimpleCORSFilter;
import com.xescm.uam.utils.jwt.TokenUtils;*/
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

import javax.annotation.Resource;

@SpringBootApplication
public class XescmOfcApplication {

//	@Value("${env}")
//	private String env;
//
//    private TokenUtils tokenUtils;
//
//	@Bean
//	public FilterRegistrationBean simpleCORSFilter() {
//		final FilterRegistrationBean registrationBean = new FilterRegistrationBean();
//		registrationBean.setFilter(new SimpleCORSFilter());
//		registrationBean.addUrlPatterns("/*");
//		registrationBean.setOrder(Integer.MAX_VALUE-2);
//		return registrationBean;
//	}
//
//	@Bean
//	public FilterRegistrationBean jwtViewFilter() {
//		final FilterRegistrationBean registrationBean = new FilterRegistrationBean();
//		JwtViewFilter filter=new JwtViewFilter();
//		filter.setEnv(env);
//		filter.setTokenUtils(getTokenUtils());
//		registrationBean.setFilter(filter);
//		registrationBean.addUrlPatterns("/ofc/*");
//		registrationBean.setOrder(Integer.MAX_VALUE - 1);
//		return registrationBean;
//	}
//
//	@Bean
//	public FilterRegistrationBean jwtApiFilter() {
//		final FilterRegistrationBean registrationBean = new FilterRegistrationBean();
//		JwtApiFilter filter=new JwtApiFilter();
//		filter.setEnv(env);
//		filter.setTokenUtils(getTokenUtils());
//		registrationBean.setFilter(filter);
//		registrationBean.addUrlPatterns("/api/*");
//		registrationBean.setOrder(Integer.MAX_VALUE);
//		return registrationBean;
//	}
//
//	@Bean
//    public TokenUtils getTokenUtils(){
//        if(tokenUtils == null){
//            tokenUtils = new TokenUtils();
//        }
//        return tokenUtils;
//    }

	public static void main(String[] args) {
		SpringApplication.run(XescmOfcApplication.class, args);
	}
}
