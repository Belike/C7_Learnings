package com.camunda.training;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.Job;
import org.camunda.bpm.engine.runtime.JobQuery;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.community.process_test_coverage.spring_test.platform7.ProcessEngineCoverageConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import static org.camunda.bpm.engine.test.assertions.bpmn.AbstractAssertions.init;
import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.*;

@Slf4j
@SpringBootTest
@Import(ProcessEngineCoverageConfiguration.class)
public class UserTaskDeleteSpringTest {

    @Autowired
    private RuntimeService runtimeService;

    @Test
    public void testHappyPath(){
        ProcessInstance processInstance = runtimeService().startProcessInstanceByKey("UserTaskDeleteExample");
        assertThat(processInstance).isStarted();

        // Parallel Branch Top
        assertThat(processInstance).isWaitingAt("Activity_0rlldy4");
        complete(task());

        // Parallel Branch Bottom
        assertThat(processInstance).isWaitingAt("Event_140q45g");
    }
}
