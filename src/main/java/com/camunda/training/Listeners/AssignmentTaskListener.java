package com.camunda.training.Listeners;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

//@Component("assignListener")
@Slf4j
public class AssignmentTaskListener implements TaskListener {
    @Override
    public void notify(DelegateTask delegateTask) {
        log.info("Assignment invoked. Assignee is now: {} ", delegateTask.getAssignee());
        Map<String, Object> variables = new HashMap<>();
        variables = delegateTask.getVariables();
        log.info("Variables found: {}", variables.size());
        log.info("Plus value of: {}", variables.get("test"));
    }
}
