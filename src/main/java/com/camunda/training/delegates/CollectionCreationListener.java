package com.camunda.training.delegates;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component("collectionCreationListener")
public class CollectionCreationListener implements JavaDelegate {
    @Override
    public void execute(DelegateExecution execution) throws Exception {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("item_0");
        arrayList.add("item_1");
        arrayList.add("item_2");

        execution.setVariable("items", arrayList);
    }
}
