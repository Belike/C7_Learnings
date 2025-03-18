package com.camunda.training.configuration.CustomIncidentHandler;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

//@Configuration
public class IncidentHandlerProcessStarter {

    public static String INCIDENT_PROCESS_KEY = "IncidentDemo";

    RuntimeService runtimeService;
    public IncidentHandlerProcessStarter(RuntimeService runtimeService){
        this.runtimeService = runtimeService;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void startProcessesWithIncidents() throws InterruptedException {
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("IncidentDemo");
        Thread.sleep(60000);
        runtimeService.createIncident("PROCESS_INSTANCE_ERROR", processInstance.getId(), "asd");
    }
}
