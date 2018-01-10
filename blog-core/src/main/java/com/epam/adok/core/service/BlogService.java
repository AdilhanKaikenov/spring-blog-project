package com.epam.adok.core.service;

import com.epam.adok.core.dao.BlogDao;
import com.epam.adok.core.dao.impl.blog.BlogFilter;
import com.epam.adok.core.entity.Blog;
import com.epam.adok.core.entity.comment.BlogComment;
import com.epam.adok.core.exception.BlogNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import java.text.MessageFormat;
import java.util.List;

@Service
public class BlogService {

    private static final Logger log = LoggerFactory.getLogger(BlogService.class);

    @Autowired
    private BlogDao blogDao;

    @Autowired
    private CommentService<BlogComment> blogCommentService;

    public void createBlog(Blog blog) {
        blogDao.save(blog);
    }

    public Blog findBlogByID(long id) {
        return blogDao.read(id);
    }

    public List<Blog> findAllBlogs() {
        return blogDao.readAll();
    }

    @Transactional
    public void removeBlogByID(long id) throws BlogNotFoundException {
        Blog blog;
        try {
            blog = blogDao.read(id);
        } catch (NoResultException e) {
            String message = MessageFormat.format(
                    "Blog with ID {0} was not found.", id);
            log.info("Message from BlogService class : {}", message);
            throw new BlogNotFoundException(message, e);
        }
        blogCommentService.removeAllBlogCommentsByBlogId(id);
        blogDao.delete(blog);
    }

    public void updateBlog(Blog blog) {
        blogDao.update(blog);
    }

    public List<Blog> findAllBlogsByParameters(BlogFilter filter) {
        return blogDao.readByParameters(filter);
    }

    public Blog findBlogByTitle(String title) {
        return blogDao.readByTitle(title);
    }
}
