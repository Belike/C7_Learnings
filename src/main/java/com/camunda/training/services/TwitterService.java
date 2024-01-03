package com.camunda.training.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

@Service
@ConfigurationProperties(prefix = "environment.dev")
public class TwitterService {

    //@Value("${oauthtoken}")
    private String OAuthToken;

   // @Value("${environment.dev.oauthsecret}")
    private String OAuthSecret;

    // I forgot what these Strings exactly do... Sorry!
    //@Value("${environment.dev.oauthconsumers1}")
    private String OAuthConsumerS1;

    //@Value("${environment.dev.oauthconsumers2}")
    private String OAuthConsumerS2;

    public long tweet(String content) throws TwitterException {
        // Has to be provided before executing
        AccessToken accessToken = new AccessToken(OAuthToken, OAuthSecret);
        Twitter twitter = new TwitterFactory().getInstance();
        twitter.setOAuthConsumer(OAuthConsumerS1, OAuthConsumerS2);
        twitter.setOAuthAccessToken(accessToken);
        return  twitter.updateStatus(content).getId();
    }
}
