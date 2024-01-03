package com.camunda.training.serialization;

import com.camunda.training.dto.Customer;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.variable.Variables;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RetrieveCustomerObjectDelegate implements JavaDelegate {
    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        Customer c1 = (Customer) delegateExecution.getVariable("Customer");
        log.info("Firstname is {} and Lastname is {}", c1.getName(), c1.getLastName());
    }
}
