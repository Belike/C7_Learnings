package com.camunda.training.configuration.IgnoreFailedJob;

import org.camunda.bpm.engine.impl.cmd.DefaultJobRetryCmd;
import org.camunda.bpm.engine.impl.cmd.JobRetryCmd;
import org.camunda.bpm.engine.impl.interceptor.CommandContext;
import org.camunda.bpm.engine.impl.persistence.entity.JobEntity;

public class IgnoreFailedJobCmd extends JobRetryCmd {

    public IgnoreFailedJobCmd(String jobId, Throwable exception) {
        super(jobId, exception);
    }

    @Override
    public Object execute(CommandContext commandContext) {
        JobEntity job = getJob();
        // Check if Job is of certain type and delete it with incident resolved true
        if(job.getJobDefinition().getJobType().equals("async-continuation")){
            job.delete(true);
            return null;
        }
        return new DefaultJobRetryCmd(jobId, exception).execute(commandContext);
    }

    @Override
    public boolean isRetryable() {
        return super.isRetryable();
    }
}
