package com.camunda.training.externalWorker;

import com.camunda.training.dto.Customer;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.client.ExternalTaskClient;
import org.camunda.bpm.client.topic.TopicSubscriptionBuilder;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class ComplexObjectWorker {
    public static void main(String ... args){
        ExternalTaskClient client = ExternalTaskClient.create()
                .baseUrl("http://localhost:8080/engine-rest")
                //.asyncResponseTimeout(20000)
                //.disableBackoffStrategy()
                .lockDuration(10000)
                .maxTasks(1)
                .build();

        TopicSubscriptionBuilder subscriptionBuilder = client.subscribe("complexObject");
        subscriptionBuilder.handler(((externalTask, externalTaskService) -> {
            Customer content = (Customer) externalTask.getVariable("customer");
            externalTaskService.complete(externalTask, Collections.singletonMap("finished", true));
        })).open();
    }
}
