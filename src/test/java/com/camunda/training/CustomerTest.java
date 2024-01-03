package com.camunda.training;

import com.camunda.training.serialization.RetrieveCustomerObjectDelegate;
import com.camunda.training.serialization.SetCustomerDelegate;
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

import static org.camunda.bpm.engine.test.assertions.bpmn.AbstractAssertions.init;
import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.*;

@RunWith(JUnit4.class)
@Deployment(resources = "serializationExample.bpmn")
public class CustomerTest {


    @Rule
    @ClassRule
    public static ProcessEngineRule rule = TestCoverageProcessEngineRuleBuilder.create().build();

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        init(rule.getProcessEngine());
        Mocks.register("setCustomerDelegate", new SetCustomerDelegate());
        Mocks.register("retrieveCustomerObjectDelegate", new RetrieveCustomerObjectDelegate());
    }

    @Test
    public void TestHappyPath(){
        ProcessInstance processInstance = runtimeService().startProcessInstanceByKey("SerializationExample");

        assertThat(processInstance).isWaitingAt("SetCustomerObject_ServiceTask");
        execute(job());

        assertThat(processInstance).isWaitingAt("RetrieveCustomerObject_ServiceTask");
        execute(job());

        assertThat(processInstance).isEnded();

    }
}
