package com.camunda.training.delegates;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("messageCorrelationDelegate")
public class CorrelateMessageSameModelDelegate implements JavaDelegate {

    @Autowired
    RuntimeService runtimeService;
    @Override
    public void execute(DelegateExecution execution) throws Exception {
        runtimeService.correlateMessage("messageWithoutAsynch");
        
    }
}
