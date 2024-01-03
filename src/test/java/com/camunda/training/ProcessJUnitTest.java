package com.camunda.training;

import com.camunda.training.delegates.CreateTweetDelegate;
import com.camunda.training.services.TwitterService;
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
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import twitter4j.TwitterException;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import static org.camunda.bpm.engine.test.assertions.ProcessEngineTests.*;
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

      // User Task
      assertThat(processInstance).isWaitingAt(findId("Review Customer JSOn"));
      complete(task());

      assertThat(processInstance).isEnded().hasPassed("TweetRejected_EndEvent");

  }

  @Test
  public void testSuperUserTweet(){
      ProcessInstance processInstance = runtimeService()
              .createMessageCorrelation("superuserTweet")
              .setVariable("content", "My Exercise 11 by Norman with Random Number")
              .correlateWithResult()
              .getProcessInstance();

      assertThat(processInstance).isStarted();

      //Send Tweet
      assertThat(processInstance).isWaitingAt("SendTweet_ServiceTask");
      execute(job());
      assertThat(processInstance).isEnded().variables().containsEntry("content", "My Exercise 11 by Norman with Random Number");
  }

  @Test
  public void testTweetWithdrawn() {
      ProcessInstance processInstance = runtimeService()
              .startProcessInstanceByKey("TwitterQa", withVariables("content", "Tweet will be withdrawn"));

      assertThat(processInstance).isStarted();

      //User Task
      // Correlation can be made by business Key, Instance Variables or Process Instance ID
      assertThat(processInstance).isWaitingAt("ApproveTweet_UserTask");
      runtimeService()
              .createMessageCorrelation("tweetWithdrawn")
              //.processInstanceId(processInstance.getId())
              //.processInstanceBusinessKey("BizKey")
              .processInstanceVariableEquals("content", "Tweet will be withdrawn")
              .correlateWithResult();
      assertThat(processInstance).isEnded().hasPassed("TweetWithdrawn_EndEvent");

  }
}
