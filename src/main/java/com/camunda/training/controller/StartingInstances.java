package com.camunda.training.controller;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.spring.boot.starter.event.PostDeployEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

//@Configuration
@Slf4j
public class StartingInstances {

    RuntimeService runtimeService;

    @Autowired
    public StartingInstances(RuntimeService runtimeService){
        this.runtimeService = runtimeService;
    }

    @EventListener
    public void onPostDeployStartInstancesForSingleUserTask(PostDeployEvent event) {
        log.info("Starting new Instances for Single UserTask");
        for(int i=0; i < 5000; i++){
            runtimeService.startProcessInstanceByKey("SlowExternalTaskQuery");
        }
    }
}
