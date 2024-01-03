package com.camunda.training;

import com.camunda.training.Listeners.ErrorTaskListener;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.runtime.Job;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.camunda.bpm.engine.test.mock.Mocks;
import org.camunda.community.process_test_coverage.junit4.platform7.rules.TestCoverageProcessEngineRuleBuilder;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.camunda.bpm.engine.test.assertions.bpmn.AbstractAssertions.init;
import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.*;

@RunWith(JUnit4.class)
@Deployment(resources = "UserTaskDelete.bpmn")
@Slf4j
public class UserTaskDeleteTest {

    @Rule
    @ClassRule
    public static ProcessEngineRule rule = TestCoverageProcessEngineRuleBuilder.create().build();

    @Mock
    ErrorTaskListener errorTaskListener;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        init(rule.getProcessEngine());
        Mocks.register("errorTaskListener", errorTaskListener);
    }

    @Test
    public void testHappyPath(){
        ProcessInstance processInstance = runtimeService().startProcessInstanceByKey("UserTaskDeleteExample");
        assertThat(processInstance).isStarted();

        assertThat(processInstance).isWaitingAt("StartEvent_1");
        execute(job());

        // Parallel Branch Top
        assertThat(processInstance).isWaitingAt("Activity_0rlldy4");
        Job jobUserTask = jobQuery().active().activityId("Activity_0rlldy4").singleResult();
        execute(jobUserTask);
        complete(task());

        // Parallel Branch Bottom
        assertThat(processInstance).isWaitingAt("Event_140q45g");
        Job jobTimerEvent = jobQuery().active().activityId("Event_140q45g").singleResult();
        execute(jobTimerEvent);
        assertThat(processInstance).isEnded();
    }
}
