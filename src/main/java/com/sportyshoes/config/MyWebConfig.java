package com.sportyshoes.config;

import jakarta.servlet.Filter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyWebConfig {

  @Bean
  public Filter trailingSlashRedirectFilter() {
    return new TrailingSlashRedirectFilter();
  }

  @Bean
  public FilterRegistrationBean<Filter> trailingSlashFilter() {
    FilterRegistrationBean<Filter> registrationBean = new FilterRegistrationBean<>();
    registrationBean.setFilter(trailingSlashRedirectFilter());
    registrationBean.addUrlPatterns("/*");
    return registrationBean;
  }

}
