package com.camunda.training;

import com.camunda.training.Listeners.ErrorTaskListener;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.camunda.bpm.engine.test.mock.Mocks;
import org.camunda.bpm.scenario.ProcessScenario;
import org.camunda.bpm.scenario.Scenario;
import org.camunda.community.process_test_coverage.junit4.platform7.rules.TestCoverageProcessEngineRuleBuilder;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

@Deployment(resources = { "UserTaskDelete.bpmn" })
public class UserTaskDeleteScenarioTest {

    @Rule
    @ClassRule
    public static ProcessEngineRule rule = TestCoverageProcessEngineRuleBuilder.create().build();

    @Mock
    ErrorTaskListener errorTaskListener;

    private ProcessScenario userTaskDelete = mock(ProcessScenario.class);

    @Before
    public void defaultScenario() {
        MockitoAnnotations.initMocks(this);
        when(userTaskDelete.waitsAtUserTask("Activity_0rlldy4")).thenReturn((task) -> task.complete());
        Mocks.register("errorTaskListener", errorTaskListener);
    }

    @Test
    public void testHappyPath() {
        Scenario scenario = Scenario.run(userTaskDelete)
                .startByKey("UserTaskDeleteExample")
                .execute();
        verify(userTaskDelete).hasCompleted("Event_08u2av3");
    }
}
