package com.itmo.simaland.scheduler;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.listeners.JobListenerSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ReportJobListener extends JobListenerSupport {
    private static final Logger logger = LoggerFactory.getLogger(ReportJobListener.class);

    @Override
    public String getName() {
        return "ReportJobListener";
    }

    @Override
    public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
        if (jobException != null) {
            logger.warn("ReportJob failed. Attempting retry...");
        }
    }
}
