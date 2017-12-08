package com.epam.adok.core.dao;

import com.epam.adok.core.dao.impl.blog.BlogFilter;
import com.epam.adok.core.entity.Blog;

import java.util.List;

public interface BlogDao extends Dao<Blog> {

    List<Blog> readByParameters(BlogFilter filter);

}
