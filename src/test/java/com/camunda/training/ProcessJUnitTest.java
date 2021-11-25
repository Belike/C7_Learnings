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
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import twitter4j.TwitterException;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import static org.camunda.bpm.engine.test.assertions.ProcessEngineTests.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;

@RunWith(JUnit4.class)
@Deployment(resources = "twitterProcess.bpmn")
public class ProcessJUnitTest {

  @Mock
  TwitterService twitterService;

  @Rule @ClassRule
  public static ProcessEngineRule rule = TestCoverageProcessEngineRuleBuilder.create().build();

  @Before
  public void setup(){
      MockitoAnnotations.initMocks(this);
      init(rule.getProcessEngine());
      Mocks.register("createTweetDelegate", new CreateTweetDelegate(twitterService));
  }

  @Test
  public void testHappyPath() throws TwitterException {

      Mockito.when(twitterService.tweet(anyString())).thenReturn(1L);

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

      assertThat(processInstance).isEnded()
              .hasPassed("SendTweet_ServiceTask")
              .hasPassed("ApproveTweet_UserTask")
              .variables().containsEntry("twitterStatus", 1L);
  }

  @Test
  public void testTweetRejected(){
      ProcessInstance processInstance = runtimeService()
              .createProcessInstanceByKey("TwitterQa")
              .setVariables(withVariables("approved", false, "content", "Very awful words to harm the world"))
              .startAfterActivity("ApproveTweet_UserTask")
              .execute();
      assertThat(processInstance).isStarted();

      //External Task
      assertThat(processInstance)
              .isWaitingAt("SendRejectionNotification_ExternalTask")
              .externalTask()
              .hasTopicName("notification");
      complete(externalTask());

      assertThat(processInstance).isEnded().hasPassed("TweetRejected_EndEvent");

  }

}
