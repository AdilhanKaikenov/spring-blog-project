package com.epam.adok.core.service;

import com.epam.adok.core.dao.BlogDao;
import com.epam.adok.core.dao.impl.blog.BlogFilter;
import com.epam.adok.core.entity.Blog;
import com.epam.adok.core.entity.comment.BlogComment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BlogService {

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
    public void removeBlogByID(long id) {

        blogCommentService.removeAllBlogCommentsByBlogId(id);

        Blog targetBlog = new Blog();
        targetBlog.setId(id);
        blogDao.delete(targetBlog);
    }

//    @Transactional
//    public boolean removeBlogByID(long id) {
//
//        Blog blog = this.blogDao.read(id);
//        if (blog == null) {
//            throw new
//        }
//
//        try {
//            blogCommentService.removeAllBlogCommentsByBlogId(id);
//
//            Blog targetBlog = new Blog();
//            targetBlog.setId(id);
//            blogDao.delete(targetBlog);
//
//            return true;
//        } catch (final DataAccessException e) {
//            // logging
//            throw new
//        }
//    }

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
