package com.configuration;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.sql.DataSource;

import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import com.apiQuartz.component.QuartzJobFactory;

import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableAsync
@Slf4j
public class QuartzConfig {
	@Autowired
	private DataSource dataSource;
	@Autowired
	private ApplicationContext applicationContext;

	/**
	 * create scheduler factory
	 */
	@Bean
	public SchedulerFactoryBean schedulerFactory() throws IOException, SchedulerException {
		String instanceName = "WORKER";
		boolean enabled = true;

		ClassPathResource quartzProperties = new ClassPathResource("quartz.properties");
		Properties properties = new Properties();
		try (InputStream inputStream = quartzProperties.getInputStream()) {
			properties.load(inputStream);
			instanceName = properties.getProperty("org.quartz.scheduler.instanceName");
			enabled = properties.getProperty("quartz.enabled").equals("true");
			log.warn("instanceName : " + instanceName);
			log.warn("quartz isEnabled : " + enabled);
		} catch (IOException e) {
			log.error(e.getMessage());
		}

		QuartzJobFactory jobFactory = new QuartzJobFactory();
		jobFactory.setApplicationContext(applicationContext);
		SchedulerFactoryBean factory = new SchedulerFactoryBean();
		factory.setConfigLocation(quartzProperties);
		factory.setSchedulerName(instanceName);
		factory.setJobFactory(jobFactory);
		factory.setDataSource(dataSource);
		factory.setOverwriteExistingJobs(true);
		factory.setApplicationContext(applicationContext);

		if (!enabled) { // 스케줄러 방지
			log.warn("############## SCHEUDLER IS DISABLED ##############");
			factory.setAutoStartup(false);
		}

		return factory;
	}

}