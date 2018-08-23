package com.demo.springboot;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Configuration
public class WebInterceptor extends WebMvcConfigurerAdapter {
	private static final Logger LOGGER = LoggerFactory.getLogger(WebInterceptor.class);

	@Bean
	public AuthInterceptor authInterceptor() {
		return new AuthInterceptor();
	}
	
	private class AuthInterceptor extends HandlerInterceptorAdapter{
		@Override
		public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
			LOGGER.info("==========================================");
			LOGGER.info("url:" + request.getRequestURI() + " method:" + request.getMethod());
			return true;
		}
	}
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		//权限验证拦截器
		registry.addInterceptor(authInterceptor());
		super.addInterceptors(registry);
	}

//	@Bean
//	public EmbeddedServletContainerCustomizer containerCustomizer() {
//		return (container -> {
//			container.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, "/error/404"));
//			container.addErrorPages(new ErrorPage(HttpStatus.BAD_REQUEST, "/error/400"));
//			container.addErrorPages(new ErrorPage(HttpStatus.METHOD_NOT_ALLOWED, "/error/405"));
//			container.addErrorPages(new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/error/500"));
//			container.addErrorPages(new ErrorPage(Throwable.class, "/error/500"));
//		});
//	}
//
//	@Bean
//	public static ConfigureRedisAction configureRedisAction() {
//		return ConfigureRedisAction.NO_OP;
//	}

}
