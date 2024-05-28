// package com.demo.apiBatch.jobs;
//
// import com.domain.apiBatch.BatchJobReservedEntity;
// import lombok.RequiredArgsConstructor;
// import lombok.extern.slf4j.Slf4j;
// import org.apache.ibatis.session.SqlSessionFactory;
// import org.mybatis.spring.batch.MyBatisBatchItemWriter;
// import org.mybatis.spring.batch.MyBatisPagingItemReader;
// import org.mybatis.spring.batch.builder.MyBatisBatchItemWriterBuilder;
// import org.mybatis.spring.batch.builder.MyBatisPagingItemReaderBuilder;
// import org.springframework.batch.core.Job;
// import org.springframework.batch.core.Step;
// import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
// import org.springframework.batch.core.configuration.annotation.JobScope;
// import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
// import org.springframework.batch.core.configuration.annotation.StepScope;
// import org.springframework.batch.item.ItemProcessor;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.configuration.annotation.Bean;
// import org.springframework.configuration.annotation.Configuration;
//
// import java.util.HashMap;
// import java.util.Map;
//
// @Slf4j
// @Configuration
// @RequiredArgsConstructor
// public class MyBatisTestJobConfiguration {
//
//     @Autowired
//     public JobBuilderFactory jobBuilderFactory;
//     @Autowired public StepBuilderFactory stepBuilderFactory;
//     @Autowired public SqlSessionFactory sqlSessionFactory;
//
//     @Bean
//     public Job myBatisTestJob() throws Exception {
//
//         Job exampleJob = jobBuilderFactory.get("myBatisTestJob")
//                 .start(Step())
//                 .build();
//
//         return exampleJob;
//     }
//
//     @Bean
//     @JobScope
//     public Step Step() throws Exception {
//         return stepBuilderFactory.get("Step")
//                 .<BatchJobReservedEntity, BatchJobReservedEntity>chunk(10)
//                 .reader(reader())
//                 .processor(processor())
//                 .writer(writer())
//                 .build();
//     }
//
//     @Bean
//     @StepScope
//     public MyBatisPagingItemReader<BatchJobReservedEntity> reader() throws Exception {
//
//         Map<String,Object> parameterValues = new HashMap<>();
//         parameterValues.put("repeatTime", "10000");
//
//         return new MyBatisPagingItemReaderBuilder<BatchJobReservedEntity>()
//                 .pageSize(10)
//                 .sqlSessionFactory(sqlSessionFactory)
//                 //Mapper안에서도 Paging 처리 시 OrderBy는 필수!
//                 .queryId("TestTableMapper.selectTest")
//                 .parameterValues(parameterValues)
//                 .build();
//     }
//
//     // @Bean
//     // @StepScope
//     // public ItemProcessor<BatchJobReservedEntity, BatchJobReservedEntity> processor(){
//     //
//     //     return new ItemProcessor<BatchJobReservedEntity, BatchJobReservedEntity>() {
//     //         @Override
//     //         public BatchJobReservedEntity process(BatchJobReservedEntity batchJobReservedEntity) throws Exception {
//     //             System.out.println(batchJobReservedEntity);
//     //             //1000원 추가 적립
//     //             // batchJobReservedEntity.set(batchJobReservedEntity.getRepeatTime() + 1000);
//     //
//     //             return batchJobReservedEntity;
//     //         }
//     //     };
//     // }
//
//     @Bean
//     @StepScope
//     public MyBatisBatchItemWriter<BatchJobReservedEntity> writer(){
//         return new MyBatisBatchItemWriterBuilder<BatchJobReservedEntity>()
//                 .assertUpdates(false)
//                 .sqlSessionFactory(sqlSessionFactory)
//                 .statementId("TestTableMapper.updateTest")
//                 .build();
//     }
//
//
// }
