package com.camunda.training.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import spinjar.com.minidev.json.JSONObject;

@RestController
@Slf4j
public class RestControllerExample {

    final static String URL = "http://localhost:8080/engine-rest/signal";

    @GetMapping("/monext/{element}")
    public ResponseEntity<String> getString(@PathVariable("element") String element){
        log.info("MONEXT RESTCALL WITH ELEMENT {}", element);
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        JSONObject jsonBody = new JSONObject();
        jsonBody.put("name", element + "-signal");
        HttpEntity<String> request = new HttpEntity<String>(jsonBody.toString(), headers);

        restTemplate.postForObject(URL, request, String.class);

        return new ResponseEntity<>("{\"status\" : \"UP\"}", HttpStatus.OK);
    }

}
