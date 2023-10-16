package com.demo.apiQuartz.service.util;

import com.demo.apiBatch.domain.BatchJobInfo;
import com.demo.apiBatch.domain.dto.BatchJobInfoDTO;
import com.demo.apiQuartz.component.QuartzBatchAdapter;
import com.demo.apiQuartz.listener.QuartzJobListener;
import com.demo.apiWebResponse.DefaultException;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
public class QuartzServiceUtil {
    @Autowired
    private QuartzJobScheduleCreator scheduleCreator;
    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;
    static final String JOB_NAME = "jobName";
    static final String JOB_CHANNEL = "jobChannel";
    static final String JOB_PARAMS = "jobParams";
    public JobDetail createJobDetail(BatchJobInfo jobInfo){
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put(JOB_NAME, jobInfo.getJobName());
        jobDataMap.put(JOB_CHANNEL, jobInfo.getJobChannel());
        jobDataMap.put(JOB_PARAMS, jobInfo.getJobParams());

        return  JobBuilder
                .newJob(QuartzBatchAdapter.class)
                .withIdentity(jobInfo.getJobName(), jobInfo.getJobChannel())
                .usingJobData(jobDataMap)
                .storeDurably()
                .build();
    }

    public Trigger createTrigger(BatchJobInfo jobInfo){
        Trigger trigger;

        if (Boolean.TRUE.equals(jobInfo.getIsCronJob())) {
            // Check Cron Valid
            if (!CronExpression.isValidExpression(jobInfo.getCronExpression())) {
                log.error("[ERROR] isNotValidExpression - {} - {}", jobInfo.getJobName(), jobInfo.getCronExpression());
            }
            trigger = scheduleCreator.createCronTrigger(jobInfo.getJobName(), jobInfo.getJobChannel(), new Date(),
                    jobInfo.getCronExpression(), SimpleTrigger.MISFIRE_INSTRUCTION_FIRE_NOW);
        } else {
            trigger = scheduleCreator.createSimpleTrigger(jobInfo.getJobName(), jobInfo.getJobChannel(),  new Date(),
                    jobInfo.getRepeatTime(), SimpleTrigger.MISFIRE_INSTRUCTION_FIRE_NOW);
        }
        return trigger;
    }

    public void createSchedule(JobDetail jobDetail, Trigger trigger, Scheduler scheduler) throws SchedulerException {
        String jobName = (String) jobDetail.getJobDataMap().get(JOB_NAME);
        String jobChannel = (String) jobDetail.getJobDataMap().get(JOB_CHANNEL);
        // Listener Logger
        try {
            scheduler.getListenerManager().addJobListener(new QuartzJobListener()); // scheduler.getListenerManager().addTriggerListener(new QuartzTriggerListener());
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
        // //Schedule
        if (scheduler.checkExists(jobDetail.getKey())) { //JobKey : JobName + JobGroup
            scheduler.deleteJob(jobDetail.getKey());
            log.error("JOB EXISTS ... REPLACING JOB : {}, {}", jobChannel , jobName);
        }
        try{
            scheduler.scheduleJob(jobDetail, trigger);
        }catch(Exception e){
            throw new DefaultException(e.getMessage());
        }
    }

    public boolean unScheduleJob(String jobName, String jobChannel) {
        try {
            return schedulerFactoryBean.getScheduler().unscheduleJob(new TriggerKey(jobName, jobChannel ));
        } catch (SchedulerException e) {
            log.error("Failed to un-schedule job - {} - {}", jobChannel, jobName, e);
            return false;
        }
    }
}
