package com.epam.adok.core.dao.impl;

import com.epam.adok.core.dao.BlogDao;
import com.epam.adok.core.entity.Blog;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;

@Repository
public class BlogDaoImpl extends GenericDao<Blog> implements BlogDao {

    @Override
    public Blog read(int id) {
        Query query = getEntityManager().createNamedQuery("Blog.readById");
        query.setParameter("id", id);
        return (Blog) query.getSingleResult();
    }

    @Override
    protected Query getReadAllNamedQuery() {
        return getEntityManager().createNamedQuery("Blog.readAll");
    }
}
