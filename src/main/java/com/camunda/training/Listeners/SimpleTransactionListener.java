package com.camunda.training.Listeners;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

//@Component("simpleTransactionListener")
@Slf4j
public class SimpleTransactionListener implements ExecutionListener {

    private final RuntimeService runtimeService;

    @Autowired
    public SimpleTransactionListener(RuntimeService runtimeService){
        this.runtimeService = runtimeService;
    }

    @Override
    public void notify(DelegateExecution execution) {
        log.info("Starting to correlate Message in Listener with BusinessKey {}", execution.getBusinessKey());
        runtimeService.correlateMessage("ListenerExample_Message", execution.getBusinessKey());
    }
}
