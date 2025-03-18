package com.camunda.training.configuration.CustomIncidentHandler;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.impl.context.Context;
import org.camunda.bpm.engine.impl.incident.DefaultIncidentHandler;
import org.camunda.bpm.engine.impl.incident.IncidentContext;
import org.camunda.bpm.engine.impl.persistence.entity.ExecutionEntity;
import org.camunda.bpm.engine.runtime.Execution;
import org.camunda.bpm.engine.runtime.Incident;
import org.springframework.stereotype.Component;

@Slf4j
public class CustomIncidentHandler extends DefaultIncidentHandler {

    public final static String INCIDENT_HANDLER_TYPE = "failedJob";

    public CustomIncidentHandler(String type) {
        super(type);
    }

    @Override
    public String getIncidentHandlerType() {
        return INCIDENT_HANDLER_TYPE;
    }

    @Override
    public Incident handleIncident(IncidentContext context, String message) {
        log.info("Execution Context by context: {}", context.getExecutionId());
        log.info("Execution Context by getCommandContext: {}", Context.getCommandContext().getExecutionManager().findExecutionById(context.getExecutionId()));

        ExecutionEntity execution = Context.getCommandContext().getExecutionManager().findExecutionById(context.getExecutionId());
        execution.getVariable("test");

        return super.handleIncident(context, message);
    }
}
