package com.camunda.training.externalWorker;

import com.camunda.training.dto.Customer;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.client.ExternalTaskClient;
import org.camunda.bpm.client.topic.TopicSubscriptionBuilder;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class NotificationWorker {

    public static void main(String ... args){
        ExternalTaskClient client = ExternalTaskClient.create()
                .baseUrl("http://localhost:8080/engine-rest")
                .asyncResponseTimeout(20000)
                .lockDuration(10000)
                .maxTasks(1)
                .build();

        TopicSubscriptionBuilder subscriptionBuilder = client.subscribe("notification");
        subscriptionBuilder.handler(((externalTask, externalTaskService) -> {
            String content = (String) externalTask.getVariable("content");
            log.info("Tweet has been declined with content {}", content);
            Map<String,Object> varMap = new HashMap<>();
            varMap.put("notificationTimestamp", new Date());
            //double random = Math.random();
            double random = 0.6;
            if(random < 0.3){
                externalTaskService.handleFailure(externalTask, "Sorry, bad failure", "Randomness is an awful thing eh", 0, 0);
            }else if(random < 0.5 ){
                externalTaskService.unlock(externalTask);
            }else{
                externalTaskService.complete(externalTask, varMap);
            }
        })).open();
    }
}
