package com.camunda.training;

import com.camunda.training.dto.Customer;
import com.github.javafaker.Faker;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.*;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.runtime.ProcessInstanceQuery;
import org.camunda.bpm.engine.task.Task;
import org.camunda.bpm.spring.boot.starter.annotation.EnableProcessApplication;
import org.camunda.bpm.spring.boot.starter.event.PostDeployEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.event.EventListener;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Slf4j
@SpringBootApplication
@EnableProcessApplication("norman-twitter-qa")
public class CamundaApplication {

  @Autowired
  ManagementService managementService;

  @Autowired
  RepositoryService repositoryService;

  @Autowired
  RuntimeService runtimeService;

  @Autowired
  ProcessEngine processEngine;

  @Autowired
  HistoryService historyService;
  @Autowired
  TaskService taskService;

  public static void main(String... args) {
    SpringApplication.run(CamundaApplication.class, args);
  }

  @EventListener
  public void isJobExecutorDeploymentAware(ApplicationReadyEvent event){
      log.info("isJobExecutorDeploymentAware: {}", processEngine.getProcessEngineConfiguration().isJobExecutorDeploymentAware());
  }

  //@EventListener
  public void onPostDeploy(PostDeployEvent event){

    Set<String> deploymentList = managementService.getRegisteredDeployments();
    log.info("There are currently {} Deployments registered with the engine" , deploymentList.size());
    deploymentList.forEach(deploymentId -> log.info(deploymentId));

    //Probably Unregister All Deployments that are registered for this engine and then manually register which should be picked up by this engine
    for(String deploymentId : deploymentList){
      managementService.unregisterDeploymentForJobExecutor(deploymentId);
    }

    managementService.registerDeploymentForJobExecutor("b3b3f303-5124-11ec-b48f-d0abd5185985");

    log.info("Manually changed deployments for Flag JobExecutor.isDeploymentAware");
    log.info("Registered Deployments have now: {} ", managementService.getRegisteredDeployments().size());
    managementService.getRegisteredDeployments().forEach(deploymentId -> log.info(deploymentId));
  }

  //@EventListener
  public void onPostDeployStartInstancesForSingleUserTask(PostDeployEvent event) {
    log.info("Starting new Instances for Single UserTask");
    for(int i=0; i < 100; i++){
      ProcessInstance singleUserTask = runtimeService.startProcessInstanceByKey("SingleUserTask");
      String id = taskService.createTaskQuery().processInstanceId(singleUserTask.getProcessInstanceId()).singleResult().getId();
      taskService.complete(id);
    }
  }

  //@EventListener
  public void onPostDeployStartInstances(PostDeployEvent event) {

    log.info("Starting new Instances for Optimize_Test");
    for(int i = 0; i < 25; i++) {
      runtimeService.startProcessInstanceByKey("Optimize_Test", "ID_"+i, Collections.singletonMap("Variable_"+i, ThreadLocalRandom.current().nextInt()));
    }
    runtimeService.startProcessInstanceByKey("Optimize_Test", "ObjectTest", Collections.singletonMap("Customer", new Customer("Norman", "Test")));
  }

  //@EventListener
  public void onPostDeployStartUserTasks(PostDeployEvent event){
    List<Task> taskQuery = taskService.createTaskQuery().taskIdIn("A12345", "B12345").active().list();
    if(taskQuery.isEmpty()) {
      Task newTaskA = taskService.newTask("A12345");
      taskService.saveTask(newTaskA);

      Task newTaskB = taskService.newTask("B12345");
      taskService.saveTask(newTaskB);
    }
  }

  //Bofa History Clean Up Mechanism
  //@EventListener
  public void testCleanUp(PostDeployEvent event){
    ArrayList<ProcessInstance> processes = new ArrayList<>();
    for(int i = 0; i < 200; i++){
      processes.add(runtimeService.startProcessInstanceByKey("UserTaskDeleteExample", Collections.singletonMap("test", i)));
      // Will
      //historyService.cleanUpHistoryAsync(true);
    }

    for(ProcessInstance pi : processes){
      historyService.setRemovalTimeToHistoricProcessInstances()
              .absoluteRemovalTime(new Date())
              .byIds(pi.getProcessInstanceId())
              .executeAsync();
    }

    /*historyService.setRemovalTimeToHistoricProcessInstances()
            .absoluteRemovalTime(new Date())
            .byIds(processes.stream().
                    map(e -> e.getProcessInstanceId())
                    .toArray(String[]::new))
            .hierarchical()
            .executeAsync();
 */
    try {
      log.info("Starting History Clean-Up");
      historyService.setRemovalTimeToHistoricProcessInstances()
              .absoluteRemovalTime(new Date())
              .byQuery(historyService.createHistoricProcessInstanceQuery()
                      .processInstanceIds(processes.stream()
                              .map(e -> e.getProcessInstanceId())
                              .collect(Collectors.toSet()))
                      .completed())
              .hierarchical()
              .executeAsync();
    } catch (BadUserRequestException e){
      log.error("No instances for clean-up could be found");
    } catch (AuthorizationException e) {
      log.error("User {} is unauthorized or REMOVAL_TIME_ is null", e.getUserId());
    }
  }

  //BankFab Tests
  //@EventListener
  public void createUserTasksForAssignmentSql(PostDeployEvent event){
    Faker faker = new Faker();
    for(int i = 0; i < 10; i++) {
      runtimeService.startProcessInstanceByKey("ClaimTaskExample");
    }
    List<Task> userTaskList = taskService.createTaskQuery().active().list();

    for(Task task : userTaskList){
      taskService.claim(task.getId(), faker.name().fullName());
    }
  }
}
