package com.thucnh.cronjob.service.quartz;

import com.cronutils.model.CronType;
import com.thucnh.cronjob.common.JobHelper;
import com.thucnh.cronjob.domain.JobCron;
import com.thucnh.cronjob.payload.job.ScheduleJob;
import com.thucnh.cronjob.service.base.BaseService;
import lombok.extern.log4j.Log4j2;
import org.quartz.*;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author : thucnh
 * @mailto : thucnh96.dev@gmail.com
 * @created :27/10/2021 - 11:39 AM
 */
@Service
@Log4j2
public class QuartzJobService extends BaseService<JobCron> {

    @Autowired
    private Scheduler scheduler;


    public static final String _jobPrefix = "JobDetail:";

    public Map<String, Object> getJob(String className) throws Exception{
        Map<String, Object> data = new HashMap<>();
        Class clazz = Class.forName(className);
        JobDetail JobDetail = JobBuilder.newJob(clazz).withIdentity(_jobPrefix + className, Scheduler.DEFAULT_GROUP).build();
        JobKey jobKey = JobDetail.getKey();
        boolean isExist = scheduler.checkExists(jobKey);
        if (isExist){
            data.put("JobDetail",JobDetail);
        }
        return  data;
    }

    public Map<String, Object> addJob(String className, String cronExpression) throws Exception{
        Map<String, Object> data = new HashMap<>();
        Class clazz = Class.forName(className);
        JobDetail JobDetail = JobBuilder.newJob(clazz).withIdentity(_jobPrefix + className, Scheduler.DEFAULT_GROUP).build();

        JobKey jobKey = JobDetail.getKey();
        boolean isExist = scheduler.checkExists(jobKey);
        if (isExist){
            data.put("isExist",isExist);
            //data.put("JobDetail",JobDetail);
            return  data;
        }
        CronTriggerImpl cronTrigger = new CronTriggerImpl();
        cronTrigger.setName(_jobPrefix + className);
        cronTrigger.setCronExpression(cronExpression);
        cronTrigger.setStartTime(new Date());
        Date startAt = scheduler.scheduleJob(JobDetail, cronTrigger);
        data.put("startAt",startAt);
        //data.put("JobDetail",JobDetail);
        data.put("jobDes", JobHelper.getTextCron(cronExpression, CronType.QUARTZ,JobHelper.VIETNAM));
        return data;
    }

    public Map<String, Object> updateJob(String className, String cronExpression) throws Exception{
        Map<String, Object> data = new HashMap<>();
        TriggerKey triggerKey = TriggerKey.triggerKey(className, Scheduler.DEFAULT_GROUP);
        // Trình tạo lập lịch biểu thức
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression);
        CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
        // Xây dựng lại trình kích hoạt theo biểu thức cronExpression mới
        trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();
        Date resAt = scheduler.rescheduleJob(triggerKey, trigger);
        data.put("resAt",resAt);
        return data;
    }

    public Map<String, Object> pauseJob(String className) throws Exception{
        Map<String, Object> data = new HashMap<>();
        JobKey jobKey = new JobKey(_jobPrefix + className, Scheduler.DEFAULT_GROUP);
        scheduler.pauseJob(jobKey);
        data.put("isPause",jobKey.getName());
        return data;
    }

    public Map<String, Object> resumeJob(String className) throws Exception{
        Map<String, Object> data = new HashMap<>();
        JobKey jobKey = new JobKey(_jobPrefix + className, Scheduler.DEFAULT_GROUP);
        scheduler.resumeJob(jobKey);
        data.put("jobKey",jobKey);
        return data;
    }

    public Map<String, Object> deleteJob(String className) throws Exception{
        Map<String, Object> data = new HashMap<>();
        JobKey jobKey = new JobKey(_jobPrefix + className, Scheduler.DEFAULT_GROUP);
        scheduler.pauseTrigger(TriggerKey.triggerKey(className, Scheduler.DEFAULT_GROUP));
        scheduler.unscheduleJob(TriggerKey.triggerKey(className, Scheduler.DEFAULT_GROUP));
        boolean isDelete  = scheduler.deleteJob(jobKey);
        data.put("isDelete",isDelete);
        return data;
    }

    public List<ScheduleJob> getRunningJobs() throws SchedulerException {

        List<ScheduleJob> data = new ArrayList<>();
        List<JobExecutionContext> executingJobs = scheduler.getCurrentlyExecutingJobs();
        for ( JobExecutionContext executingJob  : executingJobs){

            ScheduleJob job = new ScheduleJob();
            JobDetail jobDetail = executingJob.getJobDetail();
            JobKey jobKey = jobDetail.getKey();
            Trigger trigger = executingJob.getTrigger();
            job.setJobId(jobKey.getGroup() + "-" + jobKey.getName());
            job.setJobName(jobKey.getName());
            job.setJobgroup(jobKey.getGroup());
            job.setJobDescription(trigger.getDescription());
            Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
            job.setJobStatus(triggerState.name());
            if (trigger instanceof CronTrigger) {
                CronTrigger cronTrigger = (CronTrigger) trigger;
                String cronExpression = cronTrigger.getCronExpression();
                job.setJobCronExpression(cronExpression);
                job.setJobCronText(JobHelper.getTextCron(cronExpression,CronType.QUARTZ,JobHelper.VIETNAM));
            }
            data.add(job);
        }
        return data;
    }



}
