package com.camunda.training.externalWorker;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.client.ExternalTaskClient;
import org.camunda.bpm.client.topic.TopicSubscriptionBuilder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class ErrorExternalTask {
    public static void main(String ... args){
        ExternalTaskClient client = ExternalTaskClient.create()
                .baseUrl("http://localhost:8080/engine-rest")
                //.asyncResponseTimeout(20000)
                .disableBackoffStrategy()
                .lockDuration(120000)
                .maxTasks(1)
                .build();

        TopicSubscriptionBuilder subscriptionBuilder = client.subscribe("demo");
        subscriptionBuilder.handler(((externalTask, externalTaskService) -> {
            log.info("{} ExternalTask Id: Doing something", externalTask.getId());
            if ((Boolean)externalTask.getVariable("shouldFail") != true) {
                log.info("{} ExternalTask Id: I am about to complete the task", externalTask.getId());
                externalTaskService.complete(externalTask);
            }else {
                throw new RuntimeException();
            }
        })).open();
    }
}
