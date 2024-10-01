package com.camunda.training.delegates;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class CrateListDelegate implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        ArrayList<String> arrayList = new ArrayList<>();

        arrayList.add("txn0");
        arrayList.add("txn1");
        arrayList.add("txn2");

        execution.setVariable("idList", arrayList);
    }
}
