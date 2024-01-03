package com.camunda.training.delegates;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SleepingDelegate implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        int millis = 30000;
        log.info("I am starting to fall asleep now for {} millis", millis);
        Thread.sleep(millis);

        execution.setVariable("millis", millis);
    }
}
