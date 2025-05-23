package com.camunda.training.configuration.Keycloak;

import org.camunda.bpm.webapp.impl.security.auth.ContainerBasedAuthenticationFilter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.filter.ForwardedHeaderFilter;

import java.util.Collections;

import static org.springframework.security.config.Customizer.withDefaults;
import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@ConditionalOnMissingClass("org.springframework.test.context.junit.jupiter.SpringExtension")
@Profile({"keycloak-dev", "keycloak-sso-dev"})
@Configuration
@EnableWebSecurity
public class WebAppSecurityConfig {

    @Bean
    @Order(2)
    public SecurityFilterChain httpSecurity(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers(
                                antMatcher("/api/**"),
                                antMatcher("/engine-rest/**")
                        )
                )
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(
                                antMatcher("/camunda/assets/**"),
                                antMatcher("/camunda/app/**"),
                                antMatcher("/camunda/api/**"),
                                antMatcher("/camunda/lib/**"))
                        .authenticated()
                        .anyRequest()
                        .permitAll())
                .oauth2Login(withDefaults())
                .logout(logout -> logout
                        .logoutRequestMatcher(antMatcher("/camunda/app/**/logout"))
                )
                .build();
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Bean
    public FilterRegistrationBean containerBasedAuthenticationFilter(){

        FilterRegistrationBean filterRegistration = new FilterRegistrationBean();
        filterRegistration.setFilter(new ContainerBasedAuthenticationFilter());
        filterRegistration.setInitParameters(
                Collections.singletonMap(
                        "authentication-provider",
                        "com.camunda.training.configuration.Keycloak.KeycloakAuthenticationProvider"
                )
        );
        filterRegistration.setOrder(201); // make sure the filter is registered after the Spring Security Filter Chain
        filterRegistration.addUrlPatterns("/camunda/app/*");
        return filterRegistration;
    }

    // The ForwardedHeaderFilter is required to correctly assemble the redirect URL for OAUth2 login.
    // Without the filter, Spring generates an HTTP URL even though the container route is accessed through HTTPS.
    @Bean
    public FilterRegistrationBean<ForwardedHeaderFilter> forwardedHeaderFilter() {
        FilterRegistrationBean<ForwardedHeaderFilter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new ForwardedHeaderFilter());
        filterRegistrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return filterRegistrationBean;
    }

    @Bean
    public HttpFirewall getHttpFirewall() {
        StrictHttpFirewall strictHttpFirewall = new StrictHttpFirewall();
        strictHttpFirewall.setAllowUrlEncodedPercent(true);
        strictHttpFirewall.setAllowUrlEncodedSlash(true);
        return strictHttpFirewall;
    }

    @Bean
    @Order(0)
    public RequestContextListener requestContextListener() {
        return new RequestContextListener();
    }
}
