package com.camunda.training.delegates;

import com.camunda.training.batchJobHandler.PrintStringBatchJobHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.community.batch.CustomBatchBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class CreateBatchDelegate implements JavaDelegate {

    @Autowired
    PrintStringBatchJobHandler printStringBatchJobHandler;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        List<String> arrayList = new ArrayList<>();

        for(int i = 0; i < 100; i++){
            arrayList.add(RandomStringUtils.randomAlphabetic(5));
        }
        log.info("Starting Batch now with {} elements", arrayList.size());
        CustomBatchBuilder.of(arrayList)
                .jobHandler(printStringBatchJobHandler)
                .jobsPerSeed(10)
                .invocationsPerBatchJob(5)
                .create();
    }
}
