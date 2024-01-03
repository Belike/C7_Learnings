package com.camunda.training.delegates;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class RequestDelegate implements JavaDelegate {

    static final String URL = "http://localhost:8080/monext";


    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        int element = (int) delegateExecution.getVariable("element");

        if(element != 29){
            restTemplate.getForEntity(URL + "/" + element, String.class);
        }
    }
}
