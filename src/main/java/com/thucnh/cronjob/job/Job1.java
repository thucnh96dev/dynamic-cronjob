package com.thucnh.cronjob.job;

import lombok.extern.log4j.Log4j2;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.Date;

/**
 * @author : thucnh
 * @mailto : thucnh96.dev@gmail.com
 * @created :27/10/2021 - 10:45 AM
 */
@Log4j2
@DisallowConcurrentExecution
public class Job1 extends QuartzJobBean {

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        log.info("Job1 startAt: "+ new Date());
        log.info(this.getClass().getPackage().getName().concat(".").concat(this.getClass().getName()));
        log.info("Job1 success startAt: "+ new Date());
    }
}
