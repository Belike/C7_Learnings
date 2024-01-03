package com.camunda.training.Listeners;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.springframework.stereotype.Component;

@Component("loggerTaskListener")
@Slf4j
public class LoggerTaskListener implements TaskListener {
    @Override
    public void notify(DelegateTask delegateTask) {
        log.info("I am just a logger to show where you at! CurrentActivityId = " + delegateTask.getTaskDefinitionKey());
    }
}
