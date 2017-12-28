package com.epam.adok.core.dao.impl;

import com.epam.adok.core.dao.NotificationDao;
import com.epam.adok.core.entity.Notification;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;

@Repository
public class NotificationDaoImpl extends GenericDao<Notification> implements NotificationDao {

    @Override
    protected Query getReadAllNamedQuery() {
        return getEntityManager().createNamedQuery("Notification.readAll");
    }

    @Override
    public Notification read(long id) {
        return getEntityManager().find(Notification.class, id);
    }
}
