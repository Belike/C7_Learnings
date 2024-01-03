package com.camunda.training.configuration.security;

import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
public class CsrfFilterConfiguration {

    private static final String CSRF_PREVENTION_FILTER = "CsrfPreventionFilter";

    @Bean
    public ServletContextInitializer csrfOverwrite() {
        return servletContext -> servletContext.addFilter(CSRF_PREVENTION_FILTER, (request, response, chain) -> chain.doFilter(request, response));
    }

}
