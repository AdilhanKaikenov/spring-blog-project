package com.epam.adok.core.dao.impl;

import com.epam.adok.core.dao.NotificationDao;
import com.epam.adok.core.entity.Notification;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import java.util.Date;

@Repository
public class NotificationDaoImpl extends GenericDao<Notification> implements NotificationDao {

    @Override
    protected Query getReadAllNamedQuery() {
        return getEntityManager().createNamedQuery("Notification.readAll");
    }

    @Override
    public Notification read(long id) {
        Query query = getEntityManager().createNamedQuery("Notification.readById");
        query.setParameter("id", id);
        return (Notification) query.getSingleResult();
    }

    @Transactional
    @Override
    public void deleteByCreatedOnBefore(Date expiryDate) {
        Query query = getEntityManager().createNamedQuery("Notification.removeByCreatedOnBefore");
        query.setParameter("expiryDate", expiryDate);
        query.executeUpdate();
    }
}
