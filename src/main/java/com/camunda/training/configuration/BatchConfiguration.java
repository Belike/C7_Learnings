package com.camunda.training.configuration;

import com.camunda.training.batchJobHandler.PrintStringBatchJobHandler;
import org.camunda.bpm.engine.impl.cfg.ProcessEnginePlugin;
import org.camunda.community.batch.plugin.CustomBatchHandlerPlugin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

//@Configuration
public class BatchConfiguration {

    @Autowired
    PrintStringBatchJobHandler printStringBatchJobHandler;

    @Bean
    public PrintStringBatchJobHandler simpleCustomBatchJobHandler(){
        return new PrintStringBatchJobHandler();
    }

    @Bean
    public ProcessEnginePlugin customBatchHandlerPlugin(PrintStringBatchJobHandler printStringBatchJobHandler){
        return new CustomBatchHandlerPlugin(Collections.singletonList(printStringBatchJobHandler));
    }
}
