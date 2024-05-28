package com.apiBatch.jobs;

import com.apiBatch.domain.dto.BatchJobInfoDTO;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.*;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.PagingQueryProvider;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.database.builder.JdbcPagingItemReaderBuilder;
import org.springframework.batch.item.database.support.SqlPagingQueryProviderFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * 전체 금액이 10,000원 이상인 회원들에게 1,000원 캐시백을 주는 배치
 */

//@Slf4j
//@Configuration
//@EnableBatchProcessing
public class JdbcTestJobConfiguration {
    @Autowired public JobBuilderFactory jobBuilderFactory;
    @Autowired public StepBuilderFactory stepBuilderFactory;
    @Autowired public DataSource dataSource;

    @Bean
    public Job JdbcTestJob() throws Exception {


        Job exampleJob = jobBuilderFactory.get("jdbcTestJob")
                .start(jdbcStep())
                .build();

        return exampleJob;
    }

    @Bean
    @JobScope
    public Step jdbcStep() throws Exception {
        return stepBuilderFactory.get("Step")
                .<BatchJobInfoDTO,BatchJobInfoDTO>chunk(10)
                .reader(jdbcReader())
                .processor(jdbcProcessor())
                .writer(jdbcWriter())
                .build();
    }

    @Bean
    @StepScope
    public JdbcPagingItemReader<BatchJobInfoDTO> jdbcReader() throws Exception {

        Map<String,Object> parameterValues = new HashMap<>();
        parameterValues.put("amount", "10000");

        //pageSize와 fethSize는 동일하게 설정
        return new JdbcPagingItemReaderBuilder<BatchJobInfoDTO>()
                .pageSize(10)
                .fetchSize(10)
                .dataSource(dataSource)
                .rowMapper(new BeanPropertyRowMapper<>(BatchJobInfoDTO.class))
                .queryProvider(customQueryProvider())
                .parameterValues(parameterValues)
                .name("JdbcPagingItemReader")
                .build();
    }

    @Bean
    @StepScope
    public ItemProcessor<BatchJobInfoDTO, BatchJobInfoDTO> jdbcProcessor(){

        return new ItemProcessor<BatchJobInfoDTO, BatchJobInfoDTO>() {
            @Override
            public BatchJobInfoDTO process(BatchJobInfoDTO batchJobInfoDTO) throws Exception {

                //1000원 추가 적립
                batchJobInfoDTO.setRepeatTime(batchJobInfoDTO.getRepeatTime() + 1000);

                return batchJobInfoDTO;
            }
        };
    }

    @Bean
    @StepScope
    public JdbcBatchItemWriter<BatchJobInfoDTO> jdbcWriter(){
        return new JdbcBatchItemWriterBuilder<BatchJobInfoDTO>()
                .dataSource(dataSource)
                .sql("UPDATE dp_batch_job_reserved SET repeat_time = :amount WHERE ID = :id")
                .beanMapped()
                .build();

    }

    public PagingQueryProvider customQueryProvider() throws Exception {
        SqlPagingQueryProviderFactoryBean queryProviderFactoryBean = new SqlPagingQueryProviderFactoryBean();

        queryProviderFactoryBean.setDataSource(dataSource);

        queryProviderFactoryBean.setSelectClause("SELECT id, cron_expression,is_cron_job,job_class,job_channel,job_name,repeat_time ");
        queryProviderFactoryBean.setFromClause("FROM dp_batch_job_reserved ");
        queryProviderFactoryBean.setWhereClause("WHERE repeat_time >= :amount");

        Map<String,Order> sortKey = new HashMap<>();
        sortKey.put("id", Order.ASCENDING);

        queryProviderFactoryBean.setSortKeys(sortKey);

        return queryProviderFactoryBean.getObject();

    }

}
