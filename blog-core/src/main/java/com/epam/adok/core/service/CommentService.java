package com.epam.adok.core.service;

import com.epam.adok.core.commentshierarchy.CommentBranch;
import com.epam.adok.core.comparator.SorterByBlogCommentDate;
import com.epam.adok.core.dao.CommentDao;
import com.epam.adok.core.dao.NotificationDao;
import com.epam.adok.core.entity.Blog;
import com.epam.adok.core.entity.Notification;
import com.epam.adok.core.entity.User;
import com.epam.adok.core.entity.comment.AbstractComment;
import com.epam.adok.core.entity.comment.BlogComment;
import com.epam.adok.core.messagesender.EmailNotificationMessageSender;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import static com.epam.adok.core.service.CommentService.BlogCommentsStructureBuilder.buildCommentBranchTree;
import static com.epam.adok.core.service.CommentService.BlogCommentsStructureBuilder.getCommentsAsHierarchyMap;


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
        return this.commentDao.read(id);
    }

    @Transactional
    public void submitComment(T comment) {
        log.info("Entering submitComment() method.");
        this.commentDao.save(comment);
        if (comment instanceof BlogComment) {
            Notification notification = createNotification((BlogComment) comment);
            this.notificationDao.save(notification);
            long notificationId = notification.getId();
            log.info("Notification saved. ID - {} : ", notificationId);
            this.messageSender.sendNotificationEmailMessage(notificationId);
            log.info("End of submitComment() method.");
        }
    }

    public List<BlogComment> findAllBlogCommentByBlogId(long blogID) {
        return this.commentDao.readAllByBlogId(blogID);
    }

    public long removeAllBlogCommentsByBlogId(long blogId) {
        long result = this.countAllBlogCommentByBlogId(blogId);
        this.commentDao.removeAllByBlogId(blogId);
        return result;
    }

    public long countAllBlogCommentByBlogId(long blogID) {
        return commentDao.countAllByBlogId(blogID);
    }

    public List<CommentBranch> buildAllCommentBranchesByBlogId(long blogId) {

        List<BlogComment> blogComments = this.commentDao.readAllByBlogId(blogId);

        Multimap<Long, BlogComment> commentsHierarchyMap = getCommentsAsHierarchyMap(blogComments);

        return buildCommentBranchTree(commentsHierarchyMap, 0L); // zero is the mark for root comments with parentId = null
    }

    public String printCommentsTree(List<CommentBranch> branches, StringBuilder sb) {
        return this.printCommentsTree(0, branches, sb);
    }

    public String printCommentsTree(int level, List<CommentBranch> branches, StringBuilder sb) {
        for (CommentBranch branch : branches) {
                if (level > 0) {
                    String format = String.format("%" + level * 4 + "s%s%n", "", branch.getRootBlogComment().getText());
                    sb.append(format);
                } else {
                    sb.append(branch.getRootBlogComment().getText());
                    sb.append("\n");
                }
                this.printCommentsTree(level + 1, branch.getSubComments(), sb);
            }
        return sb.toString();
    }

    private Notification createNotification(BlogComment comment) {
        Notification notification = new Notification();
        User commentAuthor = comment.getUser();
        Blog commentBlog = comment.getBlog();
        Date date = new Date();
        notification.setUser(commentAuthor);
        notification.setBlog(commentBlog);
        notification.setDate(date);
        return notification;
    }

    protected static class BlogCommentsStructureBuilder {

        protected static List<CommentBranch> buildCommentBranchTree(Multimap<Long, BlogComment> map, long parentId) {
            List<CommentBranch> commentsBranches = new ArrayList<>();

            Collection<BlogComment> blogComments = map.get(parentId);

            if (!blogComments.isEmpty()) {
                for (BlogComment blogComment : blogComments) {
                    CommentBranch commentBranch = new CommentBranch();
                    commentBranch.setRootBlogComment(blogComment);
                    List<CommentBranch> commentBranches = buildCommentBranchTree(map, blogComment.getId());
                    commentBranch.setSubComments(commentBranches);

                    commentsBranches.add(commentBranch);
                }
            }
            commentsBranches.sort(new SorterByBlogCommentDate());
            return commentsBranches;
        }

        protected static Multimap<Long, BlogComment> getCommentsAsHierarchyMap(List<BlogComment> blogComments) {
            Multimap<Long, BlogComment> resultMap = HashMultimap.create();

            for (BlogComment blogComment : blogComments) {
                if (blogComment.getParentComment() == null) {
                    blogComment.setParentComment(new BlogComment());
                }
                resultMap.put(blogComment.getParentComment().getId(), blogComment);
            }
            return resultMap;
        }
    }
}
