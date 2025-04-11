package com.camunda.training.configuration.NoSecurityConfiguration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.OrRequestMatcher;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@Configuration
@Profile({"db-dev", "dev"})  // 👈 Activate in non-secure profiles
public class NoSecurityConfiguration {

    @Bean
    @Order(1)
    public SecurityFilterChain noSecurityForCamunda(HttpSecurity http) throws Exception {
        return http
                .securityMatcher(
                        new OrRequestMatcher(
                                antMatcher("/camunda/**"),
                                antMatcher("/engine-rest/**")
                        )
                )  // Only applies to /camunda/**
                .authorizeHttpRequests(authz -> authz
                        .anyRequest().permitAll()
                )
                .csrf(csrf -> csrf.disable())
                .build();
    }
}
