package ru.shashy.remindertest.job;

import org.jetbrains.annotations.NotNull;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

@Component
public class NotificationJob extends QuartzJobBean {
    @Override
    protected void executeInternal(@NotNull JobExecutionContext context) throws JobExecutionException {

    }
}
