package com.camunda.training;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.engine.variable.Variables;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Slf4j
public class MakeVariableTransientDelegate implements JavaDelegate {
    @Override
    public void execute(DelegateExecution execution) throws Exception {
        Map<String,Object> variables =execution.getVariables();
        variables.entrySet()
                .stream()
                .forEach(entry -> log.info("Variable value of " + entry.getKey() + "is " + entry.getValue()));

        execution.removeVariable("WillBeDeleted");
        execution.removeVariable("WillBeTransient");
        VariableMap variablesMap = Variables.createVariables()
                .putValue("WillBeTransient", Variables.stringValue("WillBeTransient", true));

        execution.setVariables(variablesMap);
    }
}
