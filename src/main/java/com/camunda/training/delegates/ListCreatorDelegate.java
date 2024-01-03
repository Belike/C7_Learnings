package com.camunda.training.delegates;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class ListCreatorDelegate implements JavaDelegate {

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        ArrayList<Integer> arrayList = new ArrayList<>();

        for(int i = 0; i < 30 ; i ++){
            arrayList.add(i);
        }

        delegateExecution.setVariable("list", arrayList);
    }
}
