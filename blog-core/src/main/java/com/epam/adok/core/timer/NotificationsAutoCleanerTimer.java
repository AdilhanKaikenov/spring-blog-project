package com.epam.adok.core.timer;

import com.epam.adok.core.service.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;

@Component
@PropertySource("classpath:timer.properties")
public class NotificationsAutoCleanerTimer {

    private static final Logger log = LoggerFactory.getLogger(NotificationsAutoCleanerTimer.class);

    @Value("${number.of.weeks.timer}")
    private Integer numberOfWeeks;

    @Autowired
    private NotificationService notificationService;

    @Scheduled(fixedDelayString = "${one.day.in.milliseconds}")
    public void executeScheduledTask() {
        log.info("Executed task on :: {}", new Date());

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.WEEK_OF_MONTH, -numberOfWeeks);
        Date expiryDate = cal.getTime();

        log.info("Expiry Date : {}", expiryDate);

        notificationService.deleteAllNotificationsByCreatedOnBefore(expiryDate);
    }
}
