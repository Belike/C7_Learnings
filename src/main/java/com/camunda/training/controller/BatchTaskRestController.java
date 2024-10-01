package com.camunda.training.controller;

import com.camunda.training.dto.TaskId;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
public class BatchTaskRestController {
    public final TaskService taskService;
    public BatchTaskRestController(TaskService taskService){
        this.taskService = taskService;
    }

    @PostMapping("/task/batch/complete")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseEntity<String> messageCompleteBatch(@Valid @RequestBody List<TaskId> taskIdList, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            log.error("Could not bind Tasks");
            return ResponseEntity.badRequest().body("Invalid request body");
        }else {
            taskIdList.stream().forEach(taskId -> {
                taskService.complete(taskId.getId());
            });
            return ResponseEntity.ok("Batch has been successfully executed");
        }
    }
}
