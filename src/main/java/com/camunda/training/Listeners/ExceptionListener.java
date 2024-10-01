package com.camunda.training.Listeners;

import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.springframework.stereotype.Component;

@Component
public class ExceptionListener implements TaskListener {

    @Override
    public void notify(DelegateTask delegateTask) {
        throw new RuntimeException();
    }
}
