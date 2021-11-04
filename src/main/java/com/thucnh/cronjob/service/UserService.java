package com.thucnh.cronjob.service;

import com.thucnh.cronjob.dao.UserDao;
import com.thucnh.cronjob.domain.User;
import com.thucnh.cronjob.service.base.BaseService;
import com.thucnh.cronjob.service.base.TopBaseService;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author : thucnh
 * @mailto : thucnh96.dev@gmail.com
 * @created :04/11/2021 - 9:16 AM
 */
@Service
public class UserService extends TopBaseService<User,UserDao> {

    public static final int MAX_FAILED_ATTEMPTS = 5;

    private static final long LOCK_TIME_DURATION = 24 * 60 * 60 * 1000; // 24 hours

    public User findByEmail(String email ) {
        User user =  this.dao.findByEmail(email);
        return user;
    }

    public User increaseFailedAttempts(User user) {
        int newFailAttempts = user.getFailedAttempt() + 1;
        user.setFailedAttempt(newFailAttempts);
        user =  this.save(user);
        return user;
    }

    public User lockUser(User user) {
        user.setAccountNonLocked(false);
        user.setLockTime(new Date());
        user = this.save(user);
        return user;
    }

    public User resetFailedAttempts(User user) {
        user.setFailedAttempt(0);
        user.setLockTime(null);
        user = this.save(user);
        return user;
    }

    public boolean unlockWhenTimeExpired(User user) {
        long lockTimeInMillis = user.getLockTime().getTime();
        long currentTimeInMillis = System.currentTimeMillis();

        if (lockTimeInMillis + LOCK_TIME_DURATION < currentTimeInMillis) {
            user.setAccountNonLocked(true);
            user.setLockTime(null);
            user.setFailedAttempt(0);
            this.save(user);
            return true;
        }

        return false;
    }


}
