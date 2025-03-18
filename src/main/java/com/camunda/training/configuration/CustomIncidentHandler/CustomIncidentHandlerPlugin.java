package com.camunda.training.configuration.CustomIncidentHandler;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.camunda.bpm.engine.impl.cfg.ProcessEnginePlugin;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
@Slf4j
public class CustomIncidentHandlerPlugin implements ProcessEnginePlugin {
    @Override
    public void preInit(ProcessEngineConfigurationImpl processEngineConfiguration) {

    }

    @Override
    public void postInit(ProcessEngineConfigurationImpl processEngineConfiguration) {
        CustomIncidentHandler customIncidentHandler = new CustomIncidentHandler(CustomIncidentHandler.INCIDENT_HANDLER_TYPE);
        processEngineConfiguration.setIncidentHandlers(Map.of(CustomIncidentHandler.INCIDENT_HANDLER_TYPE,customIncidentHandler));
    }

    @Override
    public void postProcessEngineBuild(ProcessEngine processEngine) {

    }
}
