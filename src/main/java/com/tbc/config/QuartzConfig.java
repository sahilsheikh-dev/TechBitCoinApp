package com.tbc.config;

import java.io.IOException;
import java.util.Properties;

import javax.sql.DataSource;

import org.quartz.spi.JobFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.boot.autoconfigure.quartz.QuartzProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

@Configuration
public class QuartzConfig {

	@Autowired
	ApplicationContext applicationContext;
	@Autowired
	private DataSource dataSource;
	@Autowired
	private QuartzProperties quartzProperties;
	@Bean
	public JobFactory jobFactory() {
		AutowiringSpringBeanJobFactory jobFactory = new AutowiringSpringBeanJobFactory();
		jobFactory.setApplicationContext(applicationContext);
		return jobFactory;
	}

	@Bean
	public SchedulerFactoryBean schedulerFactoryBean() throws IOException {

		SchedulerFactoryBean schedulerFactory = new SchedulerFactoryBean();
		Properties properties = new Properties();
		properties.putAll(quartzProperties.getProperties());

		schedulerFactory.setQuartzProperties(properties);
		schedulerFactory.setWaitForJobsToCompleteOnShutdown(true);
		schedulerFactory.setOverwriteExistingJobs(true);
		schedulerFactory.setDataSource(dataSource);
		schedulerFactory.setAutoStartup(true);
		schedulerFactory.setJobFactory(jobFactory());
		return schedulerFactory;
	}
	public Properties quartzProperties() throws IOException {
        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
        propertiesFactoryBean.setLocation(new ClassPathResource("/quartz.properties"));
        propertiesFactoryBean.afterPropertiesSet();
        return propertiesFactoryBean.getObject();
    }
}