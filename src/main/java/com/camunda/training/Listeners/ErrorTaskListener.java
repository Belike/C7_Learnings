package com.camunda.training.Listeners;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadLocalRandom;

@Slf4j
//@Component
public class ErrorTaskListener implements TaskListener {

    @Override
    public void notify(DelegateTask delegateTask) {
        throw new RuntimeException();
    }
}
