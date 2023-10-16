package com.demo.context;

import com.demo.apiQuartz.component.QuartzJobFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.quartz.QuartzProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@ComponentScan(basePackages = "com.demo")
public class QuartzConfig {
    private void initializeProperties() {
        properties.setProperty("org.quartz.scheduler.instanceId", "AUTO");
        properties.setProperty("org.quartz.scheduler.instanceName", "BATCH");

        properties.setProperty("org.quartz.scheduler.makeSchedulerThreadDaemon", "true");
        properties.setProperty("org.quartz.plugin.shutdownHook.class", "org.quartz.plugins.management.ShutdownHookPlugin");  // properties.setProperty("org.quartz.scheduler.interruptJobsOnShutdownWithWait", "true");
        properties.setProperty("org.quartz.plugin.shutdownHook.cleanShutdown", "true");

        properties.setProperty("org.quartz.jobStore.tablePrefix", "BATCH_QRTZ_"); // Your custom prefix here.
        properties.setProperty("org.quartz.jobStore.class", "org.quartz.impl.jdbcjobstore.JobStoreTX");
        properties.setProperty("org.quartz.jobStore.driverDelegateClass", "org.quartz.impl.jdbcjobstore.StdJDBCDelegate");
        properties.setProperty("org.quartz.jobStore.useProperties", "false");
        properties.setProperty("spring.quartz.job-store-type", "jdbc");

        properties.setProperty("org.quartz.jobStore.isClustered", "true");
        properties.setProperty("org.quartz.jobStore.clusterCheckinInterval", "20000");
        properties.setProperty("org.quartz.jobStore.misfireThreshold", "60000");

        properties.setProperty("org.quartz.threadPool.threadCount", "5");
        properties.setProperty("org.quartz.threadPool.makeThreadsDaemons", "true");
    }

    @Autowired
    private DataSource dataSource;
    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private QuartzProperties quartzProperties;
    private final Properties properties;
    public QuartzConfig() {
        properties = new Properties();
        initializeProperties();
    }

    /**
     * create scheduler factory
     */
    @Bean
    public SchedulerFactoryBean schedulerFactoryBean() {
        QuartzJobFactory jobFactory = new QuartzJobFactory();
        SchedulerFactoryBean factory = new SchedulerFactoryBean();

        jobFactory.setApplicationContext(applicationContext);
        factory.setQuartzProperties(getProperties());
        factory.setDataSource(dataSource);
        factory.setOverwriteExistingJobs(true);
        factory.setJobFactory(jobFactory);
        //factory.setQuartzProperties(properties);         Properties properties = new Properties();
        return factory;
    }
    public Properties getProperties() {
        return properties;
    }
}
