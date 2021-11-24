package com.camunda.training;

import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.camunda.bpm.engine.test.mock.Mocks;
import org.camunda.bpm.extension.process_test_coverage.junit.rules.TestCoverageProcessEngineRuleBuilder;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import static org.camunda.bpm.engine.test.assertions.ProcessEngineTests.*;
import static org.assertj.core.api.Assertions.*;

@RunWith(JUnit4.class)
public class ProcessJUnitTest {

  @Rule @ClassRule
  public static ProcessEngineRule rule = TestCoverageProcessEngineRuleBuilder.create().build();

  @Before
  public void setup(){
      init(rule.getProcessEngine());
      Mocks.register("createTweetDelegate", new LoggerDelegate());
  }

  @Test
  @Deployment(resources = "twitterProcess.bpmn")
  public void testHappyPath() {

      Map<String, Object> variables = new HashMap<>();
      variables.put("approved", true);
      variables.put("content", "JUnit-Test from Norman with Random Number: " + ThreadLocalRandom.current().nextInt());
      ProcessInstance processInstance = runtimeService().startProcessInstanceByKey("TwitterQa", variables);

      //User Task Queries come here!

      assertThat(processInstance).isEnded();
  }

}
