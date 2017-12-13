package com.epam.adok.core.service;

import com.epam.adok.core.dao.CommentDao;
import com.epam.adok.core.entity.comment.AbstractComment;
import com.epam.adok.core.entity.comment.BlogComment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService<T extends AbstractComment> {

    @Autowired
    private CommentDao<T> commentDao;

    public T findCommentByID(int id) {
        return commentDao.read(id);
    }

    public void submitComment(T comment) {
        commentDao.save(comment);
    }

    public List<BlogComment> findAllBlogCommentByBlogId(int blogID) {
        return commentDao.readAllByBlogId(blogID);
    }

    public void removeAllBlogCommentsByBlogId(int blogId) {
        commentDao.removeAllByBlogId(blogId);
    }
}
