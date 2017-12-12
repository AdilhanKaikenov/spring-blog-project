package com.epam.adok.core.dao.impl;

import com.epam.adok.core.dao.Dao;
import com.epam.adok.core.entity.AbstractBaseEntity;
import com.mysema.query.jpa.impl.JPAQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public abstract class GenericDao<T extends AbstractBaseEntity> implements Dao<T> {

    @PersistenceContext(unitName = "mySqlPU")
    private EntityManager entityManager;

    public JPAQuery getJpaQuery() {
        return new JPAQuery(entityManager);
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    @Transactional
    @Override
    public void save(T t) {
        entityManager.persist(t);
    }

    @Override
    public void update(T t) {
        entityManager.merge(t);
    }

    @Transactional
    @Override
    public void delete(T t) {
        entityManager.remove(entityManager.contains(t) ? t : entityManager.merge(t));
    }

    @Override
    public List<T> readAll() {
        Query query = getReadAllNamedQuery();
        return query.getResultList();
    }

    protected abstract Query getReadAllNamedQuery();

}
