package com.epam.adok.core.dao.impl;

import com.epam.adok.core.dao.CommentDao;
import com.epam.adok.core.entity.comment.AbstractComment;
import com.epam.adok.core.entity.comment.BlogComment;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

@Repository
public class CommentDaoImpl<T extends AbstractComment> extends GenericDao<T> implements CommentDao<T> {

    @Override
    public T read(long id) {
        Query query = getEntityManager().createNamedQuery("AbstractComment.readById");
        query.setParameter("id", id);
        return (T) query.getSingleResult();
    }

    @Override
    protected Query getReadAllNamedQuery() {
        return getEntityManager().createNamedQuery("AbstractComment.readAll");
    }

    @Override
    public List<BlogComment> readAllByBlogId(long id) {
        Query query = getEntityManager().createNamedQuery("BlogComment.readAllByBlogId");
        query.setParameter("id", id);
        return query.getResultList();
    }

    @Override
    public void removeAllByBlogId(long id) {
        Query query = getEntityManager().createNamedQuery("BlogComment.deleteAllBlogId");
        query.setParameter("id", id);
        query.executeUpdate();
    }
}
