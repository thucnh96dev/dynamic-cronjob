package com.thucnh.cronjob.job;

import lombok.extern.log4j.Log4j2;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.Date;

/**
 * @author : thucnh
 * @mailto : thucnh96.dev@gmail.com
 * @created :28/10/2021 - 9:20 AM
 */
@Log4j2
public class LogManager extends QuartzJobBean {
    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        log.info("LogManager startAt: "+ new Date());
        log.info(this.getClass().getPackage().getName().concat(".").concat(this.getClass().getName()));
        log.info("LogManager success startAt: "+ new Date());

    }
}
