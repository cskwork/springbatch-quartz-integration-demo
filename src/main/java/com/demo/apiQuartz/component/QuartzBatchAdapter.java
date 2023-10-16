package com.demo.apiQuartz.component;

import lombok.extern.slf4j.Slf4j;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.JobLocator;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.Date;

/**
 * Spring Batch Job -> QuartzJob
 * Used to execute Batch Jobs (QuartzJobBean)
 */
@Configuration
@Slf4j
public class QuartzBatchAdapter implements org.quartz.Job {
    @Autowired
    private JobLauncher jobLauncher;
    @Autowired
    private JobLocator jobLocator;
    static final String JOB_NAME = "jobName";
    static final String JOB_PARAMS = "jobParam";
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();  //전달받은 JodDataMap에서 Job이름을 꺼내오고 그 Job이름으로 context에서 bean을 가져온다
        String jobName = (String) jobDataMap.get(JOB_NAME);
        String jobParams = (String) jobDataMap.get(JOB_PARAMS);

        Job job = null;

        try {
            job = this.jobLocator.getJob(jobName);
        } catch (NoSuchJobException e) {
            throw new RuntimeException(e);
        }

        JobParameters jobParameters = new JobParametersBuilder()
                .addString("jobName", jobName)
                .addString("jobParam", jobParams)
                .addDate("curDate", new Date())
                .toJobParameters();

        try {
            this.jobLauncher.run(job, jobParameters);
        } catch (Exception e) {
            log.error(Arrays.toString(e.getStackTrace()), e);
        }
    }
}
