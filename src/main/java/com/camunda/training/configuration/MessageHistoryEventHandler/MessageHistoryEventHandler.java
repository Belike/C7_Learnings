package com.camunda.training.configuration.MessageHistoryEventHandler;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.ManagementService;
import org.camunda.bpm.engine.history.HistoricActivityInstance;
import org.camunda.bpm.engine.impl.history.event.HistoricActivityInstanceEventEntity;
import org.camunda.bpm.engine.impl.history.event.HistoryEvent;
import org.camunda.bpm.engine.impl.history.handler.HistoryEventHandler;

import java.util.List;

@Slf4j
public class MessageHistoryEventHandler implements HistoryEventHandler {

    ManagementService managementService;

    @Override
    public void handleEvent(HistoryEvent historyEvent) {
       if(historyEvent instanceof HistoricActivityInstanceEventEntity eventHistory && eventHistory.getActivityType().equals("intermediateMessageCatch")){
       }
    }

    @Override
    public void handleEvents(List<HistoryEvent> historyEvents) {
        for(HistoryEvent historyEvent : historyEvents){
            handleEvent(historyEvent);
        }
    }
}
