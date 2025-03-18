package com.camunda.training.delegates;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadLocalRandom;

//@Component
@Slf4j
public class IncidentDelegate implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        log.warn("I am about to create the Incident!!!");
        execution.setVariable("incidentReasoning", "Because I want");
        throw new RuntimeException("Forseen Error");
    }
}
