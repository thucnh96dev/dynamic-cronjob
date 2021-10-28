package com.thucnh.cronjob.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigInteger;

/**
 * @author : thucnh
 * @mailto : thucnh96.dev@gmail.com
 * @created :27/10/2021 - 10:31 AM
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "sys_jobcron")
public class JobCron {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long job_id;
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

}
