package com.camunda.training.delegates;

import org.camunda.bpm.engine.ManagementService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.runtime.Incident;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RetryIncidentDelegate implements JavaDelegate {

    private final RuntimeService runtimeService;

    private final ManagementService managementService;

    @Autowired
    public RetryIncidentDelegate(RuntimeService runtimeService, ManagementService managementService){
        this.runtimeService = runtimeService;
        this.managementService = managementService;
    }
    @Override
    public void execute(DelegateExecution execution) throws Exception {
        List<String> incidentIds = runtimeService
                .createIncidentQuery()
                .list()
                .stream()
                .map(Incident::getId)
                .collect(Collectors.toList());
        managementService.setJobRetriesAsync(incidentIds, 1);
    }
}
