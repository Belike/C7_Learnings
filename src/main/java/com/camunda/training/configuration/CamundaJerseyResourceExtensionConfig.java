package com.camunda.training.configuration;

import com.camunda.training.controller.BatchTaskRestController;
import org.camunda.bpm.spring.boot.starter.rest.CamundaJerseyResourceConfig;
import org.springframework.context.annotation.Configuration;

import javax.ws.rs.ApplicationPath;

@Configuration
public class CamundaJerseyResourceExtensionConfig extends CamundaJerseyResourceConfig {
    @Override
    protected void registerAdditionalResources() {
        register(BatchTaskRestController.class);
    }
}