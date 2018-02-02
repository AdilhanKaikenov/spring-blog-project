package com.epam.adok.core.service;

import com.epam.adok.core.dao.UserDao;
import com.epam.adok.core.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    public User getUserByAuth(String login, String password) {
        return userDao.findByAuth(login, password);
    }
}
