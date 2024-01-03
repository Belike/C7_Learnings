package com.camunda.training.delegates;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.HistoryService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

@Component("feelDelegate")
@Slf4j
public class FeelDelegate implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        String feelVar = (String) execution.getVariable("feelVar");
        log.info("FeelVar is {}", feelVar);

    }
}
