package com.camunda.training.delegates;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component("mailService")
public class MailServiceDelegate implements JavaDelegate {

    @Autowired
    TaskService taskService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        log.info("This will be sent via mail");
        log.info("Done!");

    }
}
