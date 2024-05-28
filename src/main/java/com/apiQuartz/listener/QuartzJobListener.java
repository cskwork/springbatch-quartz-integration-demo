package com.apiQuartz.listener;

import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
public class QuartzJobListener implements JobListener{
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    String now = LocalDateTime.now().format(formatter);
    @Override
    public String getName() {
        return QuartzJobListener.class.getName();
    }

    /**
     * Job이 수행되기 전 상태
     *   - TriggerListener.vetoJobExecution == false
     */
    @Override
    public void jobToBeExecuted(JobExecutionContext context) {
        log.info(String.format("[%s][%s][%s]", now, "작업시작", context.getJobDetail().getKey().toString()));
    }

    /**
     * Job이 중단된 상태
     *   - TriggerListener.vetoJobExecution == true
     */
    @Override
    public void jobExecutionVetoed(JobExecutionContext context) {
         log.info(String.format("[%s][%s][%s]", now, "작업중단", context.getJobDetail().getKey().toString()));
    }

    /**
     * Job 수행이 완료된 상태
     */
    @Override
    public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
         log.info(String.format("[%s][%s][%s]", now, "작업완료", context.getJobDetail().getKey().toString()));
    }
}