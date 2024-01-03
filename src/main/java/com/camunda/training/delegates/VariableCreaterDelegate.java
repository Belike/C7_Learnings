package com.camunda.training.delegates;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.engine.variable.Variables;
import org.springframework.stereotype.Component;

@Component
public class VariableCreaterDelegate implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        execution.setVariable("WillBeDeleted", "WillBeDeleted");
        execution.setVariable("WillBeTransient", "WillBeTransient");

        VariableMap variables = Variables.createVariables()
                .putValue("IsTransient", Variables.stringValue("IsTransient", true));
    }
}
