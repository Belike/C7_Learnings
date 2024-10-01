package com.camunda.training.configuration.batchJobHandler;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.impl.interceptor.CommandContext;
import org.camunda.community.batch.CustomBatchJobHandler;
import org.camunda.community.batch.core.CustomBatchConfiguration;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

//@Component
@Slf4j
public class PrintStringBatchJobHandler extends CustomBatchJobHandler<String> {
    @Override
    public void execute(List<String> list, CommandContext commandContext) {
        list.forEach(listEntry -> log.info("Working on entry: " + listEntry));
        int random = ThreadLocalRandom.current().nextInt(0,10);
        if(random > 5) throw new RuntimeException("Randomness is your enemy");
    }
    @Override
    public String getType() {
        return "print-string-batch-handler";
    }

    @Override
    public int calculateInvocationsPerBatchJob(String s, CustomBatchConfiguration customBatchConfiguration) {
        return 0;
    }
}
