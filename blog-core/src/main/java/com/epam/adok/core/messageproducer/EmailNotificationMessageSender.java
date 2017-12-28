package com.epam.adok.core.messageproducer;

import com.epam.adok.core.service.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class EmailNotificationMessageSender {

    private static final Logger log = LoggerFactory.getLogger(EmailNotificationMessageSender.class);

    @Autowired
    private NotificationService notificationService;

    @Async
    public void sendNotificationEmailMessage(long notificationID) {
        log.info("Entering sendNotificationEmailMessage() method --- > notificationID = {} : ", notificationID);
        try {
            Thread.sleep(10000);
            log.info("10 seconds are over");



        } catch (InterruptedException e) {
            e.printStackTrace(); // TODO
        }
    }
}
