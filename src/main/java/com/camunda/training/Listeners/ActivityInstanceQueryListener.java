package com.camunda.training.Listeners;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.ActivityTypes;
import org.camunda.bpm.engine.HistoryService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.history.HistoricActivityInstance;
import org.camunda.bpm.engine.history.HistoricActivityInstanceQuery;
import org.camunda.bpm.engine.history.NativeHistoricActivityInstanceQuery;
import org.camunda.bpm.engine.impl.NativeHistoricActivityInstanceQueryImpl;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
//@Component("activityInstanceQueryDelegate")
public class ActivityInstanceQueryListener implements JavaDelegate {

    HistoryService historyService;

    public ActivityInstanceQueryListener(HistoryService historyService){
        this.historyService = historyService;
    }

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        List<HistoricActivityInstance> historicActivityInstancesCancelled =
                historyService
                        .createHistoricActivityInstanceQuery()
                        .processInstanceId(execution.getProcessInstanceId())
                        .canceled()
                        //.activityType(ActivityTypes.INTERMEDIATE_EVENT_MESSAGE)
                        .list();
        HistoricActivityInstance instance = historicActivityInstancesCancelled.get(0);
        log.info("List of cancelled activities");
        for(HistoricActivityInstance activityInstance : historicActivityInstancesCancelled){
            log.info("Activity {} has been cancelled for processInstance {}", activityInstance.getActivityName(), activityInstance.getProcessInstanceId());
        }
        List<HistoricActivityInstance> historicActivityInstancesFinished = historyService
                .createHistoricActivityInstanceQuery()
                .processInstanceId(execution.getProcessInstanceId())
                .finished()
                .list();

        log.info("List of finished activities");
        for(HistoricActivityInstance activityInstance : historicActivityInstancesFinished){
            log.info("Activity {} has been finished for processInstance {}", activityInstance.getActivityName(), activityInstance.getProcessInstanceId());
        }
    }
}
