package ru.shashy.remindertest.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.stereotype.Component;
import ru.shashy.remindertest.entity.ReminderTable;
import ru.shashy.remindertest.job.NotificationJob;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Slf4j
@Component
@RequiredArgsConstructor
public class ReminderNotificationScheduler {

    private final Scheduler scheduler;

    public void scheduleNotificationRemind(ReminderTable reminderTable) {
        try {
            var jobNotificationDetail = buildJobDetail(reminderTable);
            var trigger = buildTrigger(jobNotificationDetail, reminderTable.getRemind());

            scheduler.scheduleJob(jobNotificationDetail, trigger);
        } catch (SchedulerException ex) {
            log.error("scheduler crash", ex);
        }
    }
    private JobDetail buildJobDetail(ReminderTable reminderTable) {
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("reminderId", reminderTable.getId());

        return JobBuilder
                .newJob(NotificationJob.class)
                .withIdentity(reminderTable.getId().toString(), "reminders")
                .withDescription("SendReminder")
                .usingJobData(jobDataMap)
                .storeDurably()
                .build();
    }


    private Trigger buildTrigger(JobDetail jobDetail, LocalDateTime remindTime) {
        return TriggerBuilder
                .newTrigger()
                .forJob(jobDetail)
                .withIdentity(jobDetail.getKey().getName(), "reminder-triggers")
                .startAt(Date.from(remindTime.atZone(ZoneId.systemDefault()).toInstant()))
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withMisfireHandlingInstructionFireNow())
                .build();
    }
}
