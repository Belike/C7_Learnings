package com.camunda.training.delegates;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.ManagementService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.runtime.Job;
import org.camunda.bpm.engine.runtime.JobQuery;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class FailureDelegateLongRetry implements JavaDelegate {

    private ManagementService managementService;

    public FailureDelegateLongRetry(ManagementService runtimeService) {
        this.managementService = runtimeService;
    }
    @Override
    public void execute(DelegateExecution execution) throws Exception {
        throw new RuntimeException();
    }
}
