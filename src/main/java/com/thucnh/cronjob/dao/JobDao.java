package com.thucnh.cronjob.dao;

import com.thucnh.cronjob.dao.base.BaseDao;
import com.thucnh.cronjob.domain.JobCron;
import org.springframework.stereotype.Repository;

/**
 * @author : thucnh
 * @mailto : thucnh96.dev@gmail.com
 * @created :27/10/2021 - 10:32 AM
 */
@Repository
public interface JobDao extends BaseDao<JobCron> {
}
