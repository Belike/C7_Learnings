package com.camunda.training.delegates;

import com.camunda.training.dto.Customer;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.engine.variable.Variables;
import org.springframework.stereotype.Component;

@Component
public class VariableCreaterDelegate implements JavaDelegate {

    RuntimeService runtimeService;

    public VariableCreaterDelegate(RuntimeService runtimeService){
        this.runtimeService = runtimeService;
    }

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        execution.setVariable("WillBeDeleted", "WillBeDeleted");
        execution.setVariable("WillBeTransient", "WillBeTransient");
        execution.setVariableLocal("localVariable", "asdfg");

        execution.setVariable("Customer", new Customer("Norman", "Luering"));

        VariableMap variables = Variables.createVariables()
                .putValue("IsTransient", Variables.stringValue("IsTransient", true));
    }
}
