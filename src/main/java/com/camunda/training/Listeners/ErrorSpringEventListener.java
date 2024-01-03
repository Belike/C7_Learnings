package com.camunda.training.Listeners;

import org.camunda.bpm.engine.delegate.DelegateTask;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

//@Component
public class ErrorSpringEventListener {

    @EventListener(condition = "#delegateTask.eventName=='create'")
    public void onTaskEvent(DelegateTask delegateTask){
        throw new RuntimeException();
    }
}
