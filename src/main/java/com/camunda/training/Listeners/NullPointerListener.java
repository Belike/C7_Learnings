package com.camunda.training.Listeners;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.springframework.stereotype.Component;

//@Component
public class NullPointerListener implements ExecutionListener {
    @Override
    public void notify(DelegateExecution execution) throws Exception {
        execution.getVariable("NullPointerVariable");
    }
}
