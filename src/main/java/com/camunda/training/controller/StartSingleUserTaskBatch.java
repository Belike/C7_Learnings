package com.camunda.training.controller;

import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@RestController
public class StartSingleUserTaskBatch {

    private final RuntimeService runtimeService;

    @Autowired
    public StartSingleUserTaskBatch(RuntimeService runtimeService){
        this.runtimeService = runtimeService;
    }

    @PostMapping("/start/batch/{processInstanceKey}/{amount}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseEntity<String> messageCompleteBatch(@PathVariable String processInstanceKey, @PathVariable int amount){
        if(amount <= 0) {
            return ResponseEntity.badRequest().body("Amount needs to be larger than zero");
        }else{
            try{
                for(int i = 0; i < amount; i++){
                    runtimeService.startProcessInstanceByKey(processInstanceKey);
                }
            }catch (Exception e){
                return ResponseEntity.badRequest().body("Couldn't find ProcessInstanceKey");
            }
            return ResponseEntity.ok().body("Finished");
        }
    }
}
