package com.camunda.training.configuration.IgnoreFailedJob;

import org.camunda.bpm.engine.impl.interceptor.Command;
import org.camunda.bpm.engine.impl.jobexecutor.FailedJobCommandFactory;

public class IgnoreFailedJobCommandFactory implements FailedJobCommandFactory {
    @Override
    public Command<Object> getCommand(String jobId, Throwable exception) {
        return new IgnoreFailedJobCmd(jobId, exception);
    }
}
