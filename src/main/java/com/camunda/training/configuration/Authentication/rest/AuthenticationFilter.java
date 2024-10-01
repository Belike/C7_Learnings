package com.camunda.training.configuration.Authentication.rest;

import jakarta.servlet.*;
import org.camunda.bpm.engine.IdentityService;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//@Configuration
public class AuthenticationFilter implements Filter {

    private IdentityService identityService;

    public AuthenticationFilter(IdentityService identityService){
        this.identityService = identityService;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        identityService.setAuthentication("demo", getUserGroups("demo"));
        filterChain.doFilter(servletRequest, servletResponse);

    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }

    private List<String> getUserGroups(String userId) {
        List<String> groupIds = new ArrayList<>();
        // query groups using KeycloakIdentityProvider plugin
        this.identityService.createGroupQuery().groupMember(userId).list().forEach(g -> groupIds.add(g.getId()));
        return groupIds;
    }
}
