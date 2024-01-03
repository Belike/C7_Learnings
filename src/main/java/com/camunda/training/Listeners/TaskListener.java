package com.camunda.training.Listeners;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;

@Slf4j
//@Component
public class TaskListener {

    @Autowired
    TaskService taskService;

    @EventListener()
    @Order(1)
    public void onTaskEvent(DelegateTask delegateTask){
        log.info("I am handling a Task Event");
        log.info("Task Event: {} created for process instance id: {} ", delegateTask.getTaskDefinitionKey(), delegateTask.getProcessInstanceId());
    }

    @Order(2)
    @EventListener(condition="#delegateTask.eventName=='create'")
    public void secondTaskEvent(DelegateTask delegateTask){

        log.info("#################################################################");
        log.info("I am throwing out stuff from the Delete TaskDelegate Code. Yikes");
        log.info("#################################################################");
    }

    @EventListener(condition = "#delegateTask.eventName=='assignment'")
    public void assignment(DelegateTask delegateTask){
        log.info("Task has been claimed by {}", delegateTask.getAssignee());
    }
    //@Order(3)
    @EventListener(condition = "#delegateTask.eventName=='create'")
    public void thirdTaskListener(DelegateTask delegateTask){
        log.info("I will claim the task and invoke assignment listener");
        taskService.claim(delegateTask.getId(),"bob");
    }
}
