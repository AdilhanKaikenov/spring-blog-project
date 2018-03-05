package com.epam.adok.core.messagesender;

import com.epam.adok.core.entity.Blog;
import com.epam.adok.core.entity.Notification;
import com.epam.adok.core.entity.User;
import com.epam.adok.core.service.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;

@Component
public class EmailNotificationMessageSender {

    private static final Logger log = LoggerFactory.getLogger(EmailNotificationMessageSender.class);

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private JavaMailSender mailSender;

    @Async
    public void sendNotificationEmailMessage(long notificationID) {
        log.info("Entering sendNotificationEmailMessage() method --- > notificationID = {} : ", notificationID);
        try {

            Thread.sleep(10000);
            log.info("10 seconds are over");

            Notification notification = this.notificationService.readNotificationByID(notificationID);

            User commentAuthor = notification.getUser();
            Blog blog = notification.getBlog();
            User blogAuthor = blog.getAuthor();
            String blogAuthorEmail = blogAuthor.getEmail();

            String message = MessageFormat.format("User {0} left a comment on your blog {1} at {2}",
                    commentAuthor.getLogin(),
                    blog.getTitle(),
                    notification.getDate());

            SimpleMailMessage mailMessage = buildMailMessage(
                    "uvedomitel.blogov@bk.ru", blogAuthorEmail, "Notification", message);

            this.mailSender.send(mailMessage);

        } catch (InterruptedException e) {
            e.printStackTrace(); // TODO
        }
    }

    private SimpleMailMessage buildMailMessage(String from, String to, String Subject, String message) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(from);
        mailMessage.setTo(to);
        mailMessage.setSubject(Subject);
        mailMessage.setText(message);
        return mailMessage;
    }
}
