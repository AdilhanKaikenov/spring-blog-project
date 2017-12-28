package com.epam.adok.core.service;

import com.epam.adok.core.dao.CommentDao;
import com.epam.adok.core.dao.NotificationDao;
import com.epam.adok.core.entity.Blog;
import com.epam.adok.core.entity.Notification;
import com.epam.adok.core.entity.User;
import com.epam.adok.core.entity.comment.AbstractComment;
import com.epam.adok.core.entity.comment.BlogComment;
import com.epam.adok.core.messageproducer.EmailNotificationMessageSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class CommentService<T extends AbstractComment> {

    private static final Logger log = LoggerFactory.getLogger(CommentService.class);

    @Autowired
    private CommentDao<T> commentDao;

    @Autowired
    private NotificationDao notificationDao;

    @Autowired
    private EmailNotificationMessageSender messageSender;

    public T findCommentByID(long id) {
        return commentDao.read(id);
    }

    @Transactional
    public void submitComment(T comment) {
        log.info("Entering submitComment() method.");
        commentDao.save(comment);
        if (comment instanceof BlogComment) {
            Notification notification = createNotification((BlogComment) comment);
            notificationDao.save(notification);
            long notificationId = notification.getId();
            log.info("Notification saved. ID - {} : ", notificationId);
            messageSender.sendNotificationEmailMessage(notificationId);
            log.info("End of submitComment() method.");
        }
    }

    public List<BlogComment> findAllBlogCommentByBlogId(long blogID) {
        return commentDao.readAllByBlogId(blogID);
    }

    public void removeAllBlogCommentsByBlogId(long blogId) {
        commentDao.removeAllByBlogId(blogId);
    }

    private Notification createNotification(BlogComment comment) {;
        Notification notification = new Notification();
        User commentAuthor = comment.getUser();
        Blog commentBlog = comment.getBlog();
        Date date = new Date();
        notification.setUser(commentAuthor);
        notification.setBlog(commentBlog);
        notification.setDate(date);
        return notification;
    }
}
