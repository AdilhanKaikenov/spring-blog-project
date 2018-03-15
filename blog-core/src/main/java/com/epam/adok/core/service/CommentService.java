package com.epam.adok.core.service;

import com.epam.adok.core.commentshierarchy.CommentBranch;
import com.epam.adok.core.dao.CommentDao;
import com.epam.adok.core.dao.NotificationDao;
import com.epam.adok.core.entity.Blog;
import com.epam.adok.core.entity.Notification;
import com.epam.adok.core.entity.User;
import com.epam.adok.core.entity.comment.AbstractComment;
import com.epam.adok.core.entity.comment.BlogComment;
import com.epam.adok.core.messagesender.EmailNotificationMessageSender;
import com.epam.adok.core.repository.BlogCommentRepository;
import com.epam.adok.core.repository.OffsetLimitPageRequest;
import com.google.common.collect.Multimap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.epam.adok.core.service.BlogCommentsStructureBuilder.*;

@Service
public class CommentService<T extends AbstractComment> {

    private static final Logger log = LoggerFactory.getLogger(CommentService.class);

    private static final int MAX_COMMENT_LINES_NUMBER = 20;

    @Autowired
    private CommentDao<T> commentDao;

    @Autowired
    private NotificationDao notificationDao;

    @Autowired
    private EmailNotificationMessageSender messageSender;

    @Autowired
    private BlogCommentRepository blogCommentRepository;

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

    public int removeAllBlogCommentsByBlogId(long blogId) {
        int result = this.getCommentsNumberByBlogId(blogId);
        this.commentDao.removeAllByBlogId(blogId);
        return result;
    }

    public List<CommentBranch> buildAllCommentBranchesByBlogId(long blogId) {
        List<BlogComment> blogComments = this.commentDao.readAllByBlogId(blogId);
        Multimap<Long, BlogComment> commentsHierarchyMap = getCommentsAsHierarchyMap(blogComments);
        return buildCommentBranchTree(commentsHierarchyMap);
    }

    public String printCommentsTree(List<CommentBranch> branches, StringBuilder sb) {
        return this.printCommentsTree(0, branches, sb); // 0 - root level
    }

    public String printCommentsTree(int level, List<CommentBranch> branches, StringBuilder sb) {
        for (CommentBranch branch : branches) {
            BlogComment rootBlogComment = branch.getRootBlogComment();
            if (level > 0) {
                String format = String.format("%" + level * 4 + "s%s%n", "",
                        rootBlogComment.getText() + " --> " + rootBlogComment.getCommentDate());
                sb.append(format);
            } else {
                sb.append(rootBlogComment.getText());
                sb.append(" --> ");
                sb.append(rootBlogComment.getCommentDate());
                sb.append("\n");
            }
            this.printCommentsTree(level + 1, branch.getSubComments(), sb);
        }
        return sb.toString();
    }

    public int getCommentsNumberByBlogId(long blogId) {
        Blog blog = new Blog();
        blog.setId(blogId);
        return this.blogCommentRepository.countByBlog(blog);
    }

    public List<CommentBranch> buildLimitedCommentsBranchesUpToTripleNesting(long blogId, int page, int linePerPageNumber) {

        Blog blog = new Blog();

        if (blogId == 0) {
            throw new IllegalArgumentException("The blog ID can not be zero!");
        }

        blog.setId(blogId);

        final Sort sortOrder = new Sort(Sort.Direction.DESC, "commentDate");

        Pageable rootCommentsPageRequest = PageRequest.of(page - 1, linePerPageNumber, sortOrder);
        Page<BlogComment> comments = this.blogCommentRepository
                .findAllRootBlogCommentsByBlog(blogId, rootCommentsPageRequest);

        List<BlogComment> rootLevel = comments.getContent();
        List<Long> rootParentIdList = this.extractIds(rootLevel);

        Pageable firstLevelPageRequest = OffsetLimitPageRequest.of(0, linePerPageNumber, sortOrder);
        List<BlogComment> firstLevel = this.blogCommentRepository
                .findAllInclusiveByBlogAndParentCommentIdIn(blog, rootParentIdList, firstLevelPageRequest);
        List<Long> parentIds = this.extractIds(firstLevel);

        Pageable secondLevelPageRequest = OffsetLimitPageRequest.of(0, linePerPageNumber, sortOrder);
        List<BlogComment> secondLevel = this.blogCommentRepository
                .findAllInclusiveByBlogAndParentCommentIdIn(blog, parentIds, secondLevelPageRequest);

        return buildLimitedTripleNestingCommentsBranchesTree(linePerPageNumber, rootLevel, firstLevel, secondLevel);
    }

    private List<Long> extractIds(List<BlogComment> blogComments) {
        List<Long> parentIdList = new ArrayList<>();
        for (BlogComment blogComment : blogComments) {
            parentIdList.add(blogComment.getId());
        }
        return parentIdList;
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
}
