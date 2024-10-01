package com.camunda.training;

import lombok.extern.slf4j.Slf4j;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Slf4j
public class HashFunctionTest {

    public static void main(String ... args){
        String assignee = "test";

        String algorithm = "MD5";
        try{
            MessageDigest digest = MessageDigest.getInstance(algorithm);
            digest.update(assignee.getBytes(StandardCharsets.UTF_8));
            log.info(""+ByteBuffer.wrap(digest.digest(),0,8).getLong());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
