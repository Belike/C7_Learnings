package com.camunda.training.configuration.Authentication.rest;

import jakarta.inject.Inject;
import org.camunda.bpm.engine.IdentityService;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
public class RestSecurityConfig {

    @Inject
    IdentityService identityService;

    @Bean
    public FilterRegistrationBean<AuthenticationFilter> keycloakAuthenticationFilter() {
        FilterRegistrationBean<AuthenticationFilter> filterRegistration = new FilterRegistrationBean<>();
        filterRegistration.setFilter(new AuthenticationFilter(this.identityService));
        filterRegistration.setOrder(102); // make sure the filter is registered
        // after the Spring Security Filter Chain
        filterRegistration.addUrlPatterns("/engine-rest/*");
        return filterRegistration;
    }
}
