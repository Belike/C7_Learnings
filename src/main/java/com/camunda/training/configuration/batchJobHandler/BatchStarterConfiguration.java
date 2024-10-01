package com.camunda.training.configuration.batchJobHandler;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.spring.boot.starter.event.PostDeployEvent;
import org.camunda.community.batch.CustomBatchBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

//@Configuration
@Slf4j
public class BatchStarterConfiguration {

    @Autowired
    private PrintStringBatchJobHandler printStringBatchJobHandler;

    @EventListener
    public void afterEngineStarted(PostDeployEvent event){
        log.info("Creating a new Batch");
        List<String> strings = IntStream.range(0,100).mapToObj(i -> "Batch_" + UUID.randomUUID())
                .collect(Collectors.toList());

        CustomBatchBuilder.of(strings).configuration(event.getProcessEngine().getProcessEngineConfiguration())
                .invocationsPerBatchJob(5)
                .jobsPerSeed(10)
                .jobHandler(printStringBatchJobHandler)
                .create();
    }
}
