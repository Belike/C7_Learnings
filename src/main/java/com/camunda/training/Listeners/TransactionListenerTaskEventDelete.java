package com.camunda.training.Listeners;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.ActivityTypes;
import org.camunda.bpm.engine.HistoryService;
import org.camunda.bpm.engine.ManagementService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.camunda.bpm.engine.history.*;
import org.camunda.bpm.engine.impl.cfg.TransactionState;
import org.camunda.bpm.engine.impl.context.Context;
import org.camunda.bpm.engine.runtime.Job;
import org.camunda.bpm.model.bpmn.instance.BoundaryEvent;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class TransactionListenerTaskEventDelete implements TaskListener {

    private final RuntimeService runtimeService;

    private final ManagementService managementService;

    private final HistoryService historyService;

    public TransactionListenerTaskEventDelete(RuntimeService runtimeService, ManagementService managementService, HistoryService historyService) {
        this.runtimeService = runtimeService;
        this.managementService = managementService;
        this.historyService = historyService;

    }
    @Override
    public void notify(DelegateTask delegateTask) {
        log.info("Creating TransactionListener for TaskListener with TaskId {} and ProcessInstance {}", delegateTask.getId(), delegateTask.getProcessInstanceId());
        Context.getCommandContext().getTransactionContext()
                .addTransactionListener(TransactionState.COMMITTED, commandContext -> {
                    HistoricTaskInstance finishedTask = historyService.createHistoricTaskInstanceQuery().taskId(delegateTask.getId()).finished().singleResult();
                    List<HistoricActivityInstance> list = historyService.createHistoricActivityInstanceQuery().finished().processInstanceId(delegateTask.getProcessInstanceId()).startedAfter(finishedTask.getEndTime()).list();

                    for(HistoricActivityInstance activityInstance: list){
                        if(activityInstance.getActivityType() == ActivityTypes.BOUNDARY_TIMER){
                            log.info("I am the timer event: {} with id: {}", activityInstance.getActivityName(), activityInstance.getActivityId());
                        } else if(activityInstance.getActivityType() == ActivityTypes.BOUNDARY_MESSAGE){
                            log.info("I am the boundary message event: {} with id: {}", activityInstance.getActivityName(), activityInstance.getActivityId());
                        }
                    }
                });
    }
}
