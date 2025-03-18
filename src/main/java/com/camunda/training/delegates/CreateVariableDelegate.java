package com.camunda.training.delegates;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@Slf4j
public class CreateVariableDelegate implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        if(Objects.equals((String) execution.getVariable("vulnerability"), "Eins")){
            execution.setVariable("einsVariable", "einsVariable");
        }else if (Objects.equals((String) execution.getVariable("vulnerability"), "Zwei")){
            execution.setVariable("zweiVariable", "zweiVariable");
        } else {
            throw new RuntimeException();
        }
    }
}
