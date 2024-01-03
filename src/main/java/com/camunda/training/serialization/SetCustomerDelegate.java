package com.camunda.training.serialization;

import com.camunda.training.dto.Customer;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.variable.value.ObjectValue;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
@Slf4j
public class SetCustomerDelegate implements JavaDelegate {
    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        Customer c1 = new Customer("Norman", "Lüring");

        HashMap<String, Object> vars = new HashMap<>();
        vars.put("Customer", c1);

        delegateExecution.setVariables(vars);
    }
}
