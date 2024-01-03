package com.camunda.training.controller;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.variable.Variables;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerCreationRestController {
    @Autowired
    ProcessEngine processEngine;

    @RequestMapping(path = "/customer/create", method = RequestMethod.PUT)
    public String createCustomer(String customerPayload){

        ProcessInstance processInstance = processEngine.getRuntimeService()
                .startProcessInstanceByKey("customerProcess",
                    Variables.putValue("Test", "Testname"));

        return processInstance.getId();

    }
}
