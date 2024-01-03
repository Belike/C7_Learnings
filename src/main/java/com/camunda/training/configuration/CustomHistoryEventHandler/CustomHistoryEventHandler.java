package com.camunda.training.configuration.CustomHistoryEventHandler;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.impl.history.event.HistoryEvent;
import org.camunda.bpm.engine.impl.history.handler.HistoryEventHandler;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.camunda.bpm.model.bpmn.Bpmn;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.BpmnModelType;
import org.camunda.bpm.model.bpmn.instance.Process;
import org.camunda.bpm.model.xml.Model;
import org.camunda.bpm.model.xml.impl.util.ModelUtil;
import org.camunda.bpm.model.xml.instance.ModelElementInstance;
import org.camunda.bpm.model.xml.type.ModelElementType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Slf4j
public class CustomHistoryEventHandler implements HistoryEventHandler {

    RepositoryService repositoryService;

    public CustomHistoryEventHandler(RepositoryService repositoryService){
        this.repositoryService = repositoryService;
    }

    @Override
    public void handleEvent(HistoryEvent historyEvent) {
        BpmnModelInstance bpmnModelInstance = Bpmn.readModelFromStream(repositoryService.getProcessModel(historyEvent.getProcessDefinitionId()));
        Process process = bpmnModelInstance.getModelElementById(historyEvent.getProcessDefinitionKey());
        String ttl = process.getCamundaHistoryTimeToLiveString();
        log.info("TTL is {}", ttl);

        log.info("History-RemovalTime is {}", historyEvent.getRemovalTime());

        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(historyEvent.getProcessDefinitionId()).singleResult();
        String historyTimeToLive = "" + processDefinition.getHistoryTimeToLive();
        log.info("HistoryTimeToLive is {}", historyTimeToLive);
    }

    @Override
    public void handleEvents(List<HistoryEvent> historyEvents) {
        for(HistoryEvent historyEvent : historyEvents){
            handleEvent(historyEvent);
        }
    }
}
