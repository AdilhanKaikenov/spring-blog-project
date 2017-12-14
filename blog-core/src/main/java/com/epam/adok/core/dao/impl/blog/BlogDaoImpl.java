package com.epam.adok.core.dao.impl.blog;

import com.epam.adok.core.dao.BlogDao;
import com.epam.adok.core.dao.impl.GenericDao;
import com.epam.adok.core.dao.impl.querybuilder.BlogQueryBuilder;
import com.epam.adok.core.entity.Blog;
import com.mysema.query.jpa.impl.JPAQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

import static com.epam.adok.core.entity.QBlog.blog;

@Repository
public class BlogDaoImpl extends GenericDao<Blog> implements BlogDao {

    private static final Logger log = LoggerFactory.getLogger(BlogDaoImpl.class);

    @Override
    public Blog read(long id) {
        Query query = getEntityManager().createNamedQuery("Blog.readById");
        query.setParameter("id", id);
        return (Blog) query.getSingleResult();
    }

    @Override
    protected Query getReadAllNamedQuery() {
        return getEntityManager().createNamedQuery("Blog.readAll");
    }

    @Override
    public List<Blog> readByParameters(BlogFilter filter) {
        BlogQueryBuilder blogQueryBuilder = new BlogQueryBuilder(getEntityManager(), filter);
        JPAQuery jpaQuery = blogQueryBuilder.createJPAQuery();
        return jpaQuery.list(blog);
    }
}
