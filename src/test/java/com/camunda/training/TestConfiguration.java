package com.camunda.training;

import org.camunda.community.process_test_coverage.spring_test.platform7.ProcessEngineCoverageProperties;
import org.springframework.context.annotation.Bean;

@org.springframework.boot.test.context.TestConfiguration
public class TestConfiguration {

    @Bean
    public ProcessEngineCoverageProperties processEngineCoverageProperties(){
        return ProcessEngineCoverageProperties
                .builder()
                .assertClassCoverageAtLeast(0.9)
                .build();
    }
}
