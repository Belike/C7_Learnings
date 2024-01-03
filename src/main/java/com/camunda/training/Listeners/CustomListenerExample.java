package com.camunda.training.Listeners;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

//@Component
@Slf4j
public class CustomListenerExample {

    @Autowired
    TaskService taskService;

    @EventListener(condition = "#delegateTask.eventName=='create'")
    public CustomSpringEvent createListener(DelegateTask delegateTask){
        log.info("I will claim the task and create a new event!");
        taskService.claim(delegateTask.getId(),"bob");

        return new CustomSpringEvent("CustomSpringEvent message");
    }
    @EventListener()
    public void assignment(CustomSpringEvent event){
        log.info("This is my CustomSpringEvent with message: {}", event.getMessage());
    }
}
