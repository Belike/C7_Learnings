package com.camunda.training.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.camunda.bpm.engine.RuntimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StartInstanceWithMultipleValues {

    RuntimeService runtimeService;

    @Autowired
    public StartInstanceWithMultipleValues(RuntimeService runtimeService){
        this.runtimeService = runtimeService;
    }

    @PostMapping("/{processInstanceKey}/createInstanceWithList")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseEntity<String> createInstanceWithList(@PathVariable String processInstanceKey, @RequestBody String requestBody) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();

        JsonNode jsonNode = objectMapper.readTree(requestBody);
        jsonNode.get("variables");
        return ResponseEntity.ok().body("Test");
    }
}
