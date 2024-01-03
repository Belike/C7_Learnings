package com.camunda.training.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

public class CustomerCreateService {

    @Autowired
    ObjectMapper objectMapper;

    public void createNewCustomer(DelegateExecution execution) throws IOException {
        String customerData = (String) execution.getVariable("customer");
        Customer customer = objectMapper.readValue(customerData, Customer.class);
    }
}
