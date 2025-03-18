package com.camunda.training.delegates;

import com.camunda.training.dto.Vulnerability;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CreateComplexListDelegate implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        List<Vulnerability> vulnerabilityList = new ArrayList<Vulnerability>();

        vulnerabilityList.add(new Vulnerability("severe", "RuntimeError", 1234));
        vulnerabilityList.add(new Vulnerability("high", "NullPointerException", 9999));

        execution.setVariable("vulnerabilityList", vulnerabilityList);
        execution.setVariable("assessmentTrackKey", "testKey");
    }
}
