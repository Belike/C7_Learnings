package com.camunda.training;

import com.camunda.training.dto.Customer;
import lombok.extern.slf4j.Slf4j;
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
import org.mockito.MockitoAnnotations;

import static org.camunda.bpm.engine.test.assertions.ProcessEngineTests.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;

@RunWith(JUnit4.class)
@Deployment(resources = "MessageTesting.bpmn")
@Slf4j
public class SignalEventTest {

    @Rule
    @ClassRule
    public static ProcessEngineRule rule = TestCoverageProcessEngineRuleBuilder.create().build();

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        init(rule.getProcessEngine());

    }

    @Test
    public void testHappyPath(){
        Customer customer = new Customer("Norman", "Luering");
        Mocks.register("customer", customer);

        ProcessInstance processInstance = runtimeService().startProcessInstanceByKey("MessageTesting", withVariables("customer", customer));
        assertThat(processInstance).isStarted();

        assertThat(processInstance).isWaitingAt("MessageReceive_Example");
        log.info("Found Event: " + runtimeService().createEventSubscriptionQuery().singleResult().getEventName());
        runtimeService().correlateMessage("test_" + customer.getName());

        assertThat(processInstance).isEnded();
    }
}
