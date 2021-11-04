package com.thucnh.cronjob;

import com.thucnh.cronjob.dao.UserDao;
import com.thucnh.cronjob.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * @author : thucnh
 * @mailto : thucnh96.dev@gmail.com
 * @created :04/11/2021 - 10:38 AM
 */
@Component
public class DataInitToApplication implements CommandLineRunner {

    @Autowired
    private UserDao userDao;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void run(String... args) throws Exception {
        final String adminEmail = "admin@admin.com";
        final String adminPassword = "admin";
        try {
            User user = userDao.findByEmail(adminEmail);
            if (null == user){
                user =  User.builder()
                        .email(adminEmail)
                        .password(bCryptPasswordEncoder.encode(adminPassword))
                        .accountNonLocked(true)
                        .failedAttempt(0)
                        .enabled(true)
                        .lockTime(null)
                        .firstName("admin")
                        .lastName("system")
                        .build();
                userDao.save(user);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
