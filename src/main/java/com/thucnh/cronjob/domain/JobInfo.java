package com.thucnh.cronjob.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

/**
 * @author : thucnh
 * @mailto : thucnh96.dev@gmail.com
 * @created :28/10/2021 - 9:51 AM
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JobInfo {
    private  Long job_id;
    private String job_name;
    private String job_group;
    private String job_class_name;
    private String trigger_name;
    private String trigger_group;
    private String triggerState;
    private String triggerType;
    private String priopity;
    private BigInteger repeat_interval;
    private BigInteger times_triggered;
    private String cron_expression;
    private String time_zone_id;
    private String nextFireTime;
    private String prevFireTime;
    private String desccription;
    private String startTime;
    private String endTime;
    private String cron_text;
}
