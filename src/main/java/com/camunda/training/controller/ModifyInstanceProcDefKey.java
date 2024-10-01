package com.camunda.training.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.management.DeploymentStatistics;
import org.camunda.bpm.engine.migration.MigrationPlan;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.camunda.bpm.engine.repository.ProcessDefinitionQuery;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.runtime.ProcessInstanceQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Configuration
public class ModifyInstanceProcDefKey {

    @Autowired
    RuntimeService runtimeService;

    @Autowired
    RepositoryService repositoryService;

    @PostMapping("/{processDefinitionKey}/modifyNewDefinitionKey")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseEntity<String> modifyNewDefinitionKey(@PathVariable String processDefinitionKey, @RequestBody String requestBody) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();

        JsonNode jsonNode = objectMapper.readTree(requestBody);
        String newProcessDefinitionKey = jsonNode.get("processDefinitionKey").textValue();

        String newProcessDefinitionId = repositoryService.createProcessDefinitionQuery().processDefinitionKey(newProcessDefinitionKey).latestVersion().singleResult().getId();
        List<ProcessDefinition> processDefinitions = repositoryService.createProcessDefinitionQuery().processDefinitionKey(processDefinitionKey).active().list();

        for(ProcessDefinition processDefinition : processDefinitions){
            MigrationPlan migrationPlan = runtimeService
                    .createMigrationPlan(processDefinition.getId(), newProcessDefinitionId)
                    .mapEqualActivities().build();

            ProcessInstanceQuery query = runtimeService
                    .createProcessInstanceQuery()
                    .processDefinitionId(migrationPlan.getSourceProcessDefinitionId());

            runtimeService
                    .newMigration(migrationPlan)
                    .processInstanceQuery(query)
                    .executeAsync();
        }

        return ResponseEntity.ok().build();
    }
}
