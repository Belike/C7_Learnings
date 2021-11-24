package com.camunda.training;

import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

import static org.camunda.bpm.engine.test.assertions.ProcessEngineTests.*;
import static org.assertj.core.api.Assertions.*;

@RunWith(JUnit4.class)
public class ProcessJUnitTest {

  @Rule
  public ProcessEngineRule rule = new ProcessEngineRule();

  @Before
  public void setup(){
      init(rule.getProcessEngine());
  }

  @Test
  @Deployment(resources = "twitterProcess.bpmn")
  public void testHappyPath() {

      Map<String, Object> variables = new HashMap<>();
      variables.put("approved", true);
      ProcessInstance processInstance = runtimeService().startProcessInstanceByKey("TwitterQa", variables);
      assertThat(processInstance).isEnded();
  }

}
