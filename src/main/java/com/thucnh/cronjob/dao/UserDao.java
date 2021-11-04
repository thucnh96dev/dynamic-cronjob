package com.thucnh.cronjob.dao;

import com.thucnh.cronjob.dao.base.BaseDao;
import com.thucnh.cronjob.domain.User;
import com.thucnh.cronjob.service.base.BaseService;
import org.springframework.stereotype.Repository;

/**
 * @author : thucnh
 * @mailto : thucnh96.dev@gmail.com
 * @created :04/11/2021 - 9:13 AM
 */
@Repository
public interface UserDao extends BaseDao<User> {

    User findByEmail(String email);
}
