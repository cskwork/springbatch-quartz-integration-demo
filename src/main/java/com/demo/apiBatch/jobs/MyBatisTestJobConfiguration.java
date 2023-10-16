package com.demo.apiBatch.jobs;

import com.demo.apiBatch.domain.BatchJobInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.batch.MyBatisBatchItemWriter;
import org.mybatis.spring.batch.MyBatisPagingItemReader;
import org.mybatis.spring.batch.builder.MyBatisBatchItemWriterBuilder;
import org.mybatis.spring.batch.builder.MyBatisPagingItemReaderBuilder;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class MyBatisTestJobConfiguration {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;
    @Autowired public StepBuilderFactory stepBuilderFactory;
    @Autowired public SqlSessionFactory sqlSessionFactory;

    @Bean
    public Job myBatisTestJob() throws Exception {

        Job exampleJob = jobBuilderFactory.get("myBatisTestJob")
                .start(Step())
                .build();

        return exampleJob;
    }

    @Bean
    @JobScope
    public Step Step() throws Exception {
        return stepBuilderFactory.get("Step")
                .<BatchJobInfo,BatchJobInfo>chunk(10)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .build();
    }

    @Bean
    @StepScope
    public MyBatisPagingItemReader<BatchJobInfo> reader() throws Exception {

        Map<String,Object> parameterValues = new HashMap<>();
        parameterValues.put("repeatTime", "10000");

        return new MyBatisPagingItemReaderBuilder<BatchJobInfo>()
                .pageSize(10)
                .sqlSessionFactory(sqlSessionFactory)
                //Mapper안에서도 Paging 처리 시 OrderBy는 필수!
                .queryId("TestTableMapper.selectTest")
                .parameterValues(parameterValues)
                .build();
    }

    @Bean
    @StepScope
    public ItemProcessor<BatchJobInfo, BatchJobInfo> processor(){

        return new ItemProcessor<BatchJobInfo, BatchJobInfo>() {
            @Override
            public BatchJobInfo process(BatchJobInfo batchJobInfo) throws Exception {
                System.out.println(batchJobInfo);
                //1000원 추가 적립
                batchJobInfo.setRepeatTime(batchJobInfo.getRepeatTime() + 1000);

                return batchJobInfo;
            }
        };
    }

    @Bean
    @StepScope
    public MyBatisBatchItemWriter<BatchJobInfo> writer(){
        return new MyBatisBatchItemWriterBuilder<BatchJobInfo>()
                .assertUpdates(false)
                .sqlSessionFactory(sqlSessionFactory)
                .statementId("TestTableMapper.updateTest")
                .build();
    }


}
