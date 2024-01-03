package com.camunda.training.Listeners;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

//@Component("claimTask")
@Slf4j
public class ClaimTaskListener implements TaskListener {

    @Autowired
    TaskService taskService;

    @SneakyThrows
    @Override
    public void notify(DelegateTask delegateTask) {
        log.info("I am create TaskListener");
        HashMap<String, Object> variables = new HashMap<>();
        variables.put("test", "Test for Claim");
        delegateTask.setVariables(variables);
        if(delegateTask.getAssignee() == null) taskService.claim(delegateTask.getId(), "Norman");
        Thread.sleep(3000);
    }
}
