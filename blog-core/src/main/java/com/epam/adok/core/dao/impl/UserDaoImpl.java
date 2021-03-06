package com.epam.adok.core.dao.impl;

import com.epam.adok.core.dao.UserDao;
import com.epam.adok.core.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

@Repository
public class UserDaoImpl extends GenericDao<User> implements UserDao {

    private static final Logger log = LoggerFactory.getLogger(UserDaoImpl.class);

    @Override
    public User findByAuth(String login, String password) {
            Query query = getEntityManager().createNamedQuery("User.readAuth");
            query.setParameter("login", login);
            query.setParameter("password", password);
            List userList = query.getResultList();
            return userList.isEmpty() ? null : (User) userList.iterator().next();
    }

    @Override
    public User findByLogin(String login) {
        Query query = getEntityManager().createNamedQuery("User.readByLogin");
        query.setParameter("login", login);
        List userList = query.getResultList();
        return userList.isEmpty() ? null : (User) userList.iterator().next();
    }

    @Override
    protected Query getReadAllNamedQuery() {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public User read(long id) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
