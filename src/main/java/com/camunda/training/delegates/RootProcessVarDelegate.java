package com.camunda.training.delegates;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.impl.persistence.entity.ExecutionEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("rootDelegate")
@Slf4j
public class RootProcessVarDelegate implements JavaDelegate {

    @Autowired
    RuntimeService runtimeService;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        String base = execution.getBusinessKey();
        runtimeService.setVariable(((ExecutionEntity) execution).getRootProcessInstanceId(),
                "status", base+"_processing");
        log.info("ROOT-ProcessInstanceId: {}", ((ExecutionEntity) execution).getRootProcessInstanceId());
    }
}
