package com.epam.adok.core.service;

import com.epam.adok.core.dao.UserDao;
import com.epam.adok.core.entity.User;
import com.epam.adok.core.exception.NotFndException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    public User getUserByAuth(String login, String password) {
        return userDao.findByAuth(login, password);
    }

    public User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (null == auth) {
            throw new NotFndException("");
        }

        Object obj = auth.getPrincipal();
        String login = "";

        if (obj instanceof UserDetails) {
            login = ((UserDetails) obj).getUsername();
        } else {
            login = obj.toString();
        }

        User u = userDao.findByLogin(login);
        return u;
    }
}
