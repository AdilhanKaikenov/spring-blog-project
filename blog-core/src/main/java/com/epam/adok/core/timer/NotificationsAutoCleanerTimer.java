package com.epam.adok.core.timer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class NotificationsAutoCleanerTimer {

    private static final Logger log = LoggerFactory.getLogger(NotificationsAutoCleanerTimer.class);

    @Scheduled(fixedRate = 5000)
    public void executeScheduledTask() {
        log.info("Executed task on :: {}", new Date());
    }
}
