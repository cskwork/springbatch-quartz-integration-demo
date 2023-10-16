package com.demo.apiBatch.jobs;

import com.demo.apiBatch.task.MyTaskOne;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Slf4j
@Configuration
@Component
@EnableBatchProcessing
@RequiredArgsConstructor
public class BatchJob {
    @Autowired
    private final JobBuilderFactory jobBuilderFactory;
    @Autowired
    private final StepBuilderFactory stepBuilderFactory;
    @Bean(name = "sampleJob")
    public Job sampleJob(JobBuilderFactory jobBuilderFactory, Step logStep) {
        return jobBuilderFactory.get("sampleJob")
                .start(logStep)
                .build();

    }

    @Bean
    public Step logStep(StepBuilderFactory stepBuilderFactory) {
        return stepBuilderFactory.get("logStep")
                .tasklet(new MyTaskOne()) // Use the custom tasklet
                .build();
    }
}