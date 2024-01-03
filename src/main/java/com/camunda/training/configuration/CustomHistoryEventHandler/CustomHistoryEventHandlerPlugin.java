package com.camunda.training.configuration.CustomHistoryEventHandler;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.camunda.bpm.engine.impl.cfg.ProcessEnginePlugin;
import org.camunda.bpm.engine.impl.history.handler.CompositeDbHistoryEventHandler;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomHistoryEventHandlerPlugin implements ProcessEnginePlugin {

    private CustomHistoryEventHandler customHistoryEventHandler;
    @Override
    public void preInit(ProcessEngineConfigurationImpl processEngineConfiguration) {

    }

    @Override
    public void postInit(ProcessEngineConfigurationImpl processEngineConfiguration) {
        customHistoryEventHandler = new CustomHistoryEventHandler(processEngineConfiguration.getRepositoryService());
        processEngineConfiguration.setHistoryEventHandler(new CompositeDbHistoryEventHandler(customHistoryEventHandler));
    }

    @Override
    public void postProcessEngineBuild(ProcessEngine processEngine) {

    }
}
