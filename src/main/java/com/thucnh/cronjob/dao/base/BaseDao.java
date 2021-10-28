package com.thucnh.cronjob.dao.base;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * @author : thucnh
 * @mailto : thucnh96.dev@gmail.com
 * @created :27/10/2021 - 10:32 AM
 */
@NoRepositoryBean
public interface BaseDao<T> extends JpaRepository<T,Long>, JpaSpecificationExecutor<T> {
}
