package com.epam.adok.core.dao;

import com.epam.adok.core.entity.User;

public interface UserDao extends Dao<User> {

    User findByAuth(String login, String password);

    User findByLogin(String username);
}
