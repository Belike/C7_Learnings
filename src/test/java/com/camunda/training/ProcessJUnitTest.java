package com.camunda.training;

import org.camunda.bpm.engine.runtime.Job;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
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

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
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
      variables.put("content", "JUnit-Test from Norman with Random Number: " + ThreadLocalRandom.current().nextInt());
      ProcessInstance processInstance = runtimeService().startProcessInstanceByKey("TwitterQa", variables);
      assertThat(processInstance).isStarted();

      /*User Task Queries old way without Help from Camunda BPM Assert
      assertThat(processInstance).isWaitingAt("ApproveTweet_UserTask");
      List<Task> taskList = taskService().createTaskQuery()
              .taskCandidateGroup("management")
              .processInstanceId(processInstance.getId())
              .list();

      assertThat(taskList).isNotNull();
      assertThat(taskList).hasSize(1);

      Task task = taskList.get(0);
      Map<String, Object> approvedMap = new HashMap<>();
      approvedMap.put("approved", true);
      taskService().complete(task.getId(), approvedMap);
       */

      //Camunda BPM Assert
      assertThat(processInstance).isWaitingAt("ApproveTweet_UserTask");
      assertThat(processInstance).task().hasCandidateGroup("management");
      complete(task(), withVariables("approved", true));

      /*Executing Job without Helpers
      List<Job> jobList = jobQuery().processInstanceId(processInstance.getId()).list();
      assertThat(jobList).hasSize(1);
      Job job = jobList.get(0);
      execute(job);
       */

      //Camunda BPM Assert
      assertThat(processInstance).isWaitingAt("SendTweet_ServiceTask");
      execute(job());

      assertThat(processInstance).isEnded().hasPassed("SendTweet_ServiceTask").hasPassed("ApproveTweet_UserTask");
  }

}
