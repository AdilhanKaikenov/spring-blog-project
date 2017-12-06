package com.epam.adok.core.service;

import com.epam.adok.core.dao.BlogDao;
import com.epam.adok.core.entity.Blog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlogService {

    @Autowired
    private BlogDao blogDao;

    public Blog findBlogByID(int id) {
        return blogDao.read(id);
    }

    public List<Blog> findAllBlogs() {
        return blogDao.readAll();
    }
}
