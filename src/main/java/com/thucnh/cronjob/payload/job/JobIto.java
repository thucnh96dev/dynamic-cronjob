package com.thucnh.cronjob.payload.job;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : thucnh
 * @mailto : thucnh96.dev@gmail.com
 * @created :27/10/2021 - 3:21 PM
 */
@Data
@NoArgsConstructor
public class JobIto {
    private String className;
    private String cronExpression;
    private String description;
    private String timeZone;
    private boolean isRunNow = false;
}
