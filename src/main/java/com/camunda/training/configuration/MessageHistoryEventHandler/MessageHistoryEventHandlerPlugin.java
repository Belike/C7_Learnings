package com.camunda.training.configuration.MessageHistoryEventHandler;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.camunda.bpm.engine.impl.cfg.ProcessEnginePlugin;
import org.camunda.bpm.engine.impl.history.handler.CompositeDbHistoryEventHandler;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessageHistoryEventHandlerPlugin implements ProcessEnginePlugin {
    @Override
    public void preInit(ProcessEngineConfigurationImpl processEngineConfiguration) {

    }

    @Override
    public void postInit(ProcessEngineConfigurationImpl processEngineConfiguration) {
        MessageHistoryEventHandler messageHistoryEventHandler = new MessageHistoryEventHandler();
        processEngineConfiguration.setHistoryEventHandler(new CompositeDbHistoryEventHandler(messageHistoryEventHandler));
    }

    @Override
    public void postProcessEngineBuild(ProcessEngine processEngine) {

    }
}
