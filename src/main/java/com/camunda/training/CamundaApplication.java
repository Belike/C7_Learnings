package com.camunda.training;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.application.PostDeploy;
import org.camunda.bpm.engine.*;
import org.camunda.bpm.engine.repository.DeploymentBuilder;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.spring.boot.starter.annotation.EnableProcessApplication;
import org.camunda.bpm.spring.boot.starter.event.PostDeployEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import java.util.Collections;
import java.util.List;
import java.util.Set;

@Slf4j
@SpringBootApplication
@EnableProcessApplication("twitter-qa")
public class CamundaApplication {

  @Autowired
  ManagementService managementService;

  @Autowired
  RepositoryService repositoryService;

  @Autowired
  ProcessEngine processEngine;

  public static void main(String... args) {
    SpringApplication.run(CamundaApplication.class, args);
  }

  @EventListener
  public void isJobExecutorDeploymentAware(ApplicationReadyEvent event){
       log.info("isJobExecutorDeploymentAware: {}", processEngine.getProcessEngineConfiguration().isJobExecutorDeploymentAware());
  }

  @EventListener
  public void onPostDeploy(PostDeployEvent event){
    Set<String> deploymentList = managementService.getRegisteredDeployments();
    log.info("There are currently {} Deployments registered with the engine" , deploymentList.size());
    deploymentList.forEach(deploymentId -> log.info(deploymentId));

    /*Probably Unregister All Deployments that are registered for this engine and then manually register which should be picked up by this engine
    for(String deploymentId : deploymentList){
      managementService.unregisterDeploymentForJobExecutor(deploymentId);
    }

    managementService.registerDeploymentForJobExecutor("b3b3f303-5124-11ec-b48f-d0abd5185985");

    log.info("Manually changed deployments for Flag JobExecutor.isDeploymentAware");
    log.info("Registered Deployments have now: {} ", managementService.getRegisteredDeployments().size());
    managementService.getRegisteredDeployments().forEach(deploymentId -> log.info(deploymentId));
     */
  }
}
