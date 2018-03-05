package com.epam.adok.core.service;

import com.epam.adok.core.auth.UserPrincipal;
import com.epam.adok.core.dao.UserDao;
import com.epam.adok.core.entity.User;
import com.epam.adok.core.exception.NotFndException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserDao userDao;

    public User getUserByAuth(String login, String password) {
        return this.userDao.findByAuth(login, password);
    }

    public User getUserByLogin(String login) {
        return this.userDao.findByLogin(login);
    }

    public User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (null == auth) {
            throw new NotFndException("");
        }

        Object obj = auth.getPrincipal();
        log.info("Principal ---------- > {}", obj);

        if (obj instanceof UserPrincipal) {
            return this.userDao.findByLogin(((UserPrincipal) obj).getUsername());
        } else {
            return null; // anonymous user
        }
    }
}
