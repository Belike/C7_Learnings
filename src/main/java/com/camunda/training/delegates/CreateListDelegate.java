package com.camunda.training.delegates;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class CreateListDelegate implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        List<String> vulnerabilities = new ArrayList<String>();
        vulnerabilities.add("Eins");
        vulnerabilities.add("Zwei");

        execution.setVariable("vulnerabilities", vulnerabilities);
    }
}
