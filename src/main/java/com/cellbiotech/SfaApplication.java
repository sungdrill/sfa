package com.cellbiotech;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

import com.navercorp.lucy.security.xss.servletfilter.XssEscapeServletFilter;

@SpringBootApplication
public class SfaApplication {
//	@Autowired
//	private SfaTestScheduler scheduler;

	private int maxUploadSizeInMb = 10 * 1024 * 1024; // 10 MB

	public static void main(String[] args) {
		SpringApplication.run(SfaApplication.class, args);
	}

	@Bean
	public FilterRegistrationBean xssEscapeServletFilter() {
		FilterRegistrationBean registrationBean = new FilterRegistrationBean();
		registrationBean.setFilter(new XssEscapeServletFilter());
		registrationBean.setOrder(1);  // @Order로 처리.
		registrationBean.addUrlPatterns("/*");
		return registrationBean;
	}
}
