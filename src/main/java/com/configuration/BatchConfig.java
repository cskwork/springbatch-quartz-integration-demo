package com.configuration;

import java.util.concurrent.ThreadPoolExecutor;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.apache.commons.lang.StringUtils;
import org.apache.ddlutils.PlatformUtils;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.configuration.annotation.BatchConfigurer;
import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.support.JobRegistryBeanPostProcessor;
import org.springframework.batch.core.configuration.support.MapJobRegistry;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableBatchProcessing
@RequiredArgsConstructor
@Slf4j
public class BatchConfig {

	private static final String TABLE_PREFIX = "DP_BATCH_";
	@Autowired
	JobRegistry jobRegistry;

	@Bean
	public BatchConfigurer batchConfigurer(@Qualifier("batchTransactionManager") PlatformTransactionManager transactionManager, DataSource dataSource) {
		return new DefaultBatchConfigurer(dataSource) {
			@Override
			protected JobLauncher createJobLauncher() throws Exception {
				SimpleJobLauncher jobLauncher = new SimpleJobLauncher();
				jobLauncher.setTaskExecutor(taskExecutor());
				jobLauncher.setJobRepository(createJobRepository());
				jobLauncher.afterPropertiesSet();
				return jobLauncher;
			}

			@Override
			protected JobRepository createJobRepository() throws Exception {
				JobRepositoryFactoryBean factoryBean = new JobRepositoryFactoryBean();
				factoryBean.setDataSource(dataSource);
				factoryBean.setTablePrefix(TABLE_PREFIX);

				String driverName = new PlatformUtils().determineDatabaseType(dataSource).toLowerCase();
				if (StringUtils.equals(driverName,
					"oracle")) { // mysql -ISOLATION_SERIALIZABLE , oracle - ISOLATION_READ_COMMITTED
					// factory.setIsolationLevelForCreate("ISOLATION_READ_COMMITTED");
					factoryBean.setIsolationLevelForCreate("ISOLATION_DEFAULT");
				} else {
					factoryBean.setIsolationLevelForCreate(
						"ISOLATION_SERIALIZABLE"); // ISOLATION_SERIALIZABLE IS DEFAULT NOT SEREIALIZABLE ERROR ORACLE // ISOLATION_READ_COMMITTED
				}

				factoryBean.setTransactionManager(transactionManager);
				factoryBean.afterPropertiesSet();
				return factoryBean.getObject();
			}
		};
	}

	/**
	 * CORE POOL SIZE EXHAUST -> QUEUE CAPPING -> MAXPOOLSIZE
	 * @return
	 */
	@Bean
	public ThreadPoolTaskExecutor taskExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		// int availableProcessors= Runtime.getRuntime().availableProcessors();
		executor.setCorePoolSize(15);
		executor.setMaxPoolSize(20);
		executor.setQueueCapacity(100);
		executor.setRejectedExecutionHandler(
			new ThreadPoolExecutor.CallerRunsPolicy());  // TomcatThread 에서 직접 처리. 모든 요청에 대한 처리
		executor.setWaitForTasksToCompleteOnShutdown(false);
		executor.setAwaitTerminationSeconds(60);
		executor.setThreadNamePrefix("DP-WORKER-");
		executor.initialize();
		log.warn("batch init pool size {}", executor.getPoolSize());
		return executor;
	}

	@Bean
	@Qualifier("batchTransactionManager")
	public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(emf);
		return transactionManager;
	}

	/**
	 * Allows you to store and retrieve job configurations by their names. This can be useful when you want to execute batchJobs dynamically by
	 * their names at runtime.
	 * Automatically registers all Job beans with the provided JobRegistry.
	 * <p>
	 * 1 JobRegistryBeanPostProcessor helps register all your batchJobs with a registry when your application starts up.
	 * 2 The JobLocator, on the other hand, is used at runtime to retrieve these registered batchJobs by their names when you need them.
	 * @return
	 */
	@Bean
	public JobRegistry jobRegistry() {
		return new MapJobRegistry();
	}

	@Bean
	public JobRegistryBeanPostProcessor jobResgistryBeanPostProcessor() {
		JobRegistryBeanPostProcessor registrar = new JobRegistryBeanPostProcessor();
		registrar.setJobRegistry(jobRegistry);
		return registrar;
	}
}