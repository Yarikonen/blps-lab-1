package com.itmo.simaland.config;

import com.itmo.simaland.scheduler.AutowiringSpringBeanJobFactory;
import com.itmo.simaland.scheduler.ReportJob;
import com.itmo.simaland.scheduler.ReportJobListener;
import lombok.RequiredArgsConstructor;
import org.quartz.*;
import org.quartz.spi.JobFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import java.util.Collections;

@Configuration
public class QuartzConfig {
    @Autowired
    private ReportJob reportJob;

    @Bean
    public JobFactory jobFactory() {
        return new AutowiringSpringBeanJobFactory();
    }

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean(JobFactory jobFactory, Trigger trigger, JobDetail jobDetail) {
        SchedulerFactoryBean schedulerFactory = new SchedulerFactoryBean();
        schedulerFactory.setJobFactory(jobFactory);
        schedulerFactory.setTriggers(trigger);
        schedulerFactory.setJobDetails(jobDetail);
        return schedulerFactory;
    }

    @Bean
    public JobDetail jobDetail() {
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("reportJob", reportJob);
        jobDataMap.put("retryCount", 0);

        return JobBuilder.newJob(ReportJob.class)
                .withIdentity("reportJob")
                .withDescription("Generate Daily Sales Report")
                .usingJobData(jobDataMap)
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger trigger(JobDetail jobDetail) {
        return TriggerBuilder.newTrigger()
                .forJob(jobDetail)
                .withIdentity("DailyReportTrigger", "ReportGroup")
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
//                        .withIntervalInSeconds(30)
                        .withIntervalInHours(24)
                        .repeatForever())
                .startAt(java.util.Date.from(java.time.ZonedDateTime.now().withHour(8).withMinute(0).withSecond(0).toInstant())) // Start at 8 AM
                .build();
    }

    @Bean
    public ReportJobListener jobListener() {
        return new ReportJobListener();
    }

    @Bean
    public Scheduler scheduler(SchedulerFactoryBean schedulerFactoryBean) {
        return schedulerFactoryBean.getScheduler();
    }
}