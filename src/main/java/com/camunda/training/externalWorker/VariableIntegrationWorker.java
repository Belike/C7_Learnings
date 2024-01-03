package com.camunda.training.externalWorker;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.client.ExternalTaskClient;
import org.camunda.bpm.client.topic.TopicSubscriptionBuilder;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class VariableIntegrationWorker {

    public static void main(String ... args) {
        ExternalTaskClient client = ExternalTaskClient.create()
                .baseUrl("http://localhost:8080/engine-rest")
                .asyncResponseTimeout(20000)
                .disableBackoffStrategy()
                .lockDuration(10000)
                .maxTasks(1)
                .build();

        TopicSubscriptionBuilder subscriptionBuilder = client.subscribe("variableset");
        subscriptionBuilder.handler(((externalTask, externalTaskService) -> {
            HashMap<String, Object> variables = new HashMap<>();
            variables.put("variableEmpty", "notEmptyString");
            variables.put("variableNull", "notNull");

            externalTaskService.complete(externalTask, variables);
        })).open();

        TopicSubscriptionBuilder subscriptionBuilder1 = client.subscribe("variableremote");
        subscriptionBuilder1.handler(((externalTask, externalTaskService) -> {
            log.info("variableEmpty is {} and will be {}", externalTask.getVariable("variableEmpty"), "");
            log.info("variableNull is {} and will be {}", externalTask.getVariable("variableNull"), null);

            HashMap<String, Object> variables = (HashMap<String, Object>) externalTask.getAllVariables();
            variables.put("variableEmpty", "");
            variables.put("variableNull", null);

            externalTaskService.complete(externalTask,variables);
        })).open();
    }

}
