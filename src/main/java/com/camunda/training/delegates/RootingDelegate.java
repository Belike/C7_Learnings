package com.camunda.training.delegates;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RootingDelegate implements JavaDelegate {

    @Autowired
    ApplicationContext applicationContext;

    @Override
    public void execute(DelegateExecution execution) throws BeansException {
        String beanName = (String)execution.getVariable("beanName");
        log.info("Rooting to another bean of name: {} ", beanName);

        JavaDelegate bean = (JavaDelegate) applicationContext.getBean(beanName);
    }
}
