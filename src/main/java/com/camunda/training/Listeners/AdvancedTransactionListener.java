package com.camunda.training.Listeners;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.HistoryService;
import org.camunda.bpm.engine.ManagementService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.camunda.bpm.engine.history.HistoricJobLogQuery;
import org.camunda.bpm.engine.impl.cfg.TransactionState;
import org.camunda.bpm.engine.impl.context.Context;
import org.camunda.bpm.engine.runtime.Job;
import org.camunda.bpm.engine.runtime.JobQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

//@Component("advancedTransactionListener")
@Slf4j
public class AdvancedTransactionListener implements ExecutionListener {

    private final RuntimeService runtimeService;

    private final ManagementService managementService;

    private final HistoryService historyService;

    @Autowired
    public AdvancedTransactionListener(RuntimeService runtimeService, ManagementService managementService, HistoryService historyService){
        this.runtimeService = runtimeService;
        this.managementService = managementService;
        this.historyService = historyService;
    }

    @Override
    public void notify(DelegateExecution delegateExecution) throws Exception {
        log.info("Creating TransactionListener for ExecutionListener {} and BusinessKey {}", delegateExecution.getEventName(), delegateExecution.getBusinessKey());
        Context.getCommandContext().getTransactionContext()
                .addTransactionListener(TransactionState.COMMITTING, commandContext -> {
                    List<Job> timers = managementService.createJobQuery().timers().list();
                    log.info("Size of open Timers is: {}", timers.size());
                });
    }
}