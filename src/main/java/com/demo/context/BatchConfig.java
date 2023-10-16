package com.demo.context;

import com.demo.apiBatch.task.MyTaskOne;
import com.demo.apiBatch.task.MyTaskTwo;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.configuration.annotation.*;
import org.springframework.batch.core.configuration.support.JobRegistryBeanPostProcessor;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
@EnableBatchProcessing
@RequiredArgsConstructor
public class BatchConfig {
    private final DataSource dataSource;
    private final ApplicationContext applicationContext;

    @Bean
    public BatchConfigurer batchConfigurer(DataSource dataSource, PlatformTransactionManager transactionManager) {
        return new DefaultBatchConfigurer(dataSource) {
            @Override
            protected JobRepository createJobRepository() throws Exception {
                JobRepositoryFactoryBean factoryBean = new JobRepositoryFactoryBean();
                factoryBean.setDataSource(dataSource);
                factoryBean.setTablePrefix("DP_BATCH_"); // Your custom prefix here.
                factoryBean.setTransactionManager(transactionManager);
                factoryBean.afterPropertiesSet();
                return  factoryBean.getObject();
            }
        };
    }

    /**
     * Allows you to store and retrieve job configurations by their names. This can be useful when you want to execute jobs dynamically by their names at runtime.
     * Automatically registers all Job beans with the provided JobRegistry.
     *
     * 1 JobRegistryBeanPostProcessor helps register all your jobs with a registry when your application starts up.
     * 2 The JobLocator, on the other hand, is used at runtime to retrieve these registered jobs by their names when you need them.
     * @param jobRegistry
     * @return
     */
    @Bean
    public JobRegistryBeanPostProcessor jobRegistryBeanPostProcessor(JobRegistry jobRegistry) {
        JobRegistryBeanPostProcessor postProcessor = new JobRegistryBeanPostProcessor();
        postProcessor.setJobRegistry(jobRegistry);
        return postProcessor;
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        sqlSessionFactoryBean.setMapperLocations(applicationContext.getResources("classpath:mappers/*.xml"));

        return sqlSessionFactoryBean.getObject();
    }
}