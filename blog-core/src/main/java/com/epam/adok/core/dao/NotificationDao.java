package com.epam.adok.core.dao;

import com.epam.adok.core.entity.Notification;

import java.util.Date;

public interface NotificationDao extends Dao<Notification> {

    void deleteByCreatedOnBefore(Date expiryDate);

}
