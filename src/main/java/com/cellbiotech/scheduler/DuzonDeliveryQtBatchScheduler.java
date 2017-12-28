package com.cellbiotech.scheduler;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class DuzonDeliveryQtBatchScheduler {
    private SchedulerFactory schedulerFactory;
    private Scheduler scheduler;

    @PostConstruct
    public void start() throws SchedulerException{

        schedulerFactory = new StdSchedulerFactory();
        scheduler = schedulerFactory.getScheduler();
        scheduler.start();

        //job 지정
        JobDetail job = JobBuilder.newJob(DuzonDeliveryQtBatchJob.class).withIdentity("duzonDeliveryQtBatchJob").build();

        //trigger 생성
        Trigger trigger = TriggerBuilder.newTrigger().
                withSchedule(CronScheduleBuilder.cronSchedule("0 20 8,13,15,17 * * ?")).build();
//        startAt과 endAt을 사용해 job 스케쥴의 시작, 종료 시간도 지정할 수 있다.
//        Trigger trigger = TriggerBuilder.newTrigger().startAt(startDateTime).endAt(EndDateTime)
//                .withSchedule(CronScheduleBuilder.cronSchedule("*/1 * * * *")).build();

        scheduler.scheduleJob(job, trigger);

    }
}

