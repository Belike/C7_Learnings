package com.camunda.training.delegates;

import com.camunda.training.dto.Customer;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

@Component
public class CreateObjectVariablesDelegate implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        Customer customer = new Customer("Norman", "Luering");
        execution.setVariable("customer", customer);
    }
}