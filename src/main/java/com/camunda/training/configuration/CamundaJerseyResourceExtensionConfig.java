package com.camunda.training.configuration;

import com.camunda.training.controller.BatchTaskRestController;
import org.camunda.bpm.spring.boot.starter.rest.CamundaJerseyResourceConfig;


//@Configuration
public class CamundaJerseyResourceExtensionConfig extends CamundaJerseyResourceConfig {
    @Override
    protected void registerAdditionalResources() {
        register(BatchTaskRestController.class);
    }
}