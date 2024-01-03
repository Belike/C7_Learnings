package com.camunda.training.batchJobHandler;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.impl.interceptor.CommandContext;
import org.camunda.community.batch.CustomBatchJobHandler;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class PrintStringBatchJobHandler extends CustomBatchJobHandler<String> {
    @Override
    public void execute(List<String> list, CommandContext commandContext) {
        list.forEach(listEntry -> log.info("Working on entry: " + listEntry));
    }
    @Override
    public String getType() {
        return "print-string-batch-handler";
    }
}
