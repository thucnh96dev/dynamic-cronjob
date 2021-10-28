package com.thucnh.cronjob.payload.job;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : thucnh
 * @mailto : thucnh96.dev@gmail.com
 * @created :27/10/2021 - 2:27 PM
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ScheduleJob {
    private String jobId;
    private String jobName;
    private String jobgroup;
    private String jobDescription;
    private String jobStatus;
    private String jobCronExpression;
    private String jobCronText;
}
