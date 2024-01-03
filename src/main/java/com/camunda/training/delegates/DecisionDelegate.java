package com.camunda.training.delegates;

import org.camunda.bpm.dmn.engine.DmnDecisionRuleResult;
import org.camunda.bpm.dmn.engine.DmnDecisionTableResult;
import org.camunda.bpm.engine.DecisionService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class DecisionDelegate implements JavaDelegate {

   @Autowired
    DecisionService decisionService;

    @Override
    public void execute(DelegateExecution execution) throws Exception {

        Map<String, Object> inputParameters = new HashMap<>();
        inputParameters.put("orderTotal", (double) execution.getVariable("orderTotal"));

        DmnDecisionTableResult discountPercentage = decisionService.evaluateDecisionTableByKey("orderDiscount", inputParameters);
        Integer firstEntry = discountPercentage.getFirstResult().getFirstEntry();

        execution.setVariable("discountPercentage", firstEntry);
    }
}
