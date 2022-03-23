package com.camunda.training;

import com.camunda.training.dto.Customer;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.engine.variable.value.ObjectValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

import javax.inject.Inject;


@Slf4j
@Component
public class CreateTweetDelegate implements JavaDelegate {

    private TwitterService twitterService;

    @Autowired
    RepositoryService repositoryService;

    @Inject
    public CreateTweetDelegate(TwitterService twitterService){
        this.twitterService = twitterService;
    }

    @Override
    public void execute(DelegateExecution delegateExecution) throws TwitterException {
        try {
            //Good World would call Business Logic
            String content = (String) delegateExecution.getVariable("content");
            log.info("Publishing tweet: " + content);
            Long l1 = twitterService.tweet(content);
            delegateExecution.setVariable("twitterStatus", l1);

            ObjectValue typedValue = Variables.objectValue(new Customer("Norman","test")).create();
            delegateExecution.setVariable("Customer", typedValue);
        }catch (TwitterException ex){
            if(ex.getErrorCode() == 187){
                throw new TwitterException("Error");
            }else{
                throw ex;
            }
        }
    }
}