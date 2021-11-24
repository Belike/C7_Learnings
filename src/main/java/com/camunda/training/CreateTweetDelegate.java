package com.camunda.training;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;


@Slf4j
@Component
public class CreateTweetDelegate implements JavaDelegate {

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        //Good World would call Business Logic
        String content = (String) delegateExecution.getVariable("content");
        log.info("Publishing tweet: " + content);
        AccessToken accessToken = new AccessToken("220324559-jet1dkzhSOeDWdaclI48z5txJRFLCnLOK45qStvo", "B28Ze8VDucBdiE38aVQqTxOyPc7eHunxBVv7XgGim4say");
        Twitter twitter = new TwitterFactory().getInstance();
        twitter.setOAuthConsumer("lRhS80iIXXQtm6LM03awjvrvk", "gabtxwW8lnSL9yQUNdzAfgBOgIMSRqh7MegQs79GlKVWF36qLS");
        twitter.setOAuthAccessToken(accessToken);
        twitter.updateStatus(content);
    }
}