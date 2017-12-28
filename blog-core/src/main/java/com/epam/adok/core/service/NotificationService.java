package com.epam.adok.core.service;

import com.epam.adok.core.dao.NotificationDao;
import com.epam.adok.core.entity.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    @Autowired
    private NotificationDao notificationDao;

    public Notification readNotificationByID(long id) {
        return notificationDao.read(id);
    }
}
