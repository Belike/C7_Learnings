package com.camunda.training;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadLocalRandom;

@Component
@Slf4j
public class IncidentDelegate implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        try {
            log.warn("I am about to create the Incident!!!");
            execution.setVariable("test", "test");
            int random = 2;

            if(random >= 1) {
                throw new Exception();
            }else{
                throw new BpmnError("forseenBpmnError"); // can also be Exception
            }
        }catch (Exception e){
            //execution.createIncident("UnwantedBehavior", "Payload", "errorMessage"); NONE-BLOCKING
            throw e; //Blocking - unforseen no transaction happening
        }
    }
}
