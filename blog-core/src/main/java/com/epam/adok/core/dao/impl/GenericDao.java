package com.epam.adok.core.dao.impl;

import com.epam.adok.core.dao.Dao;
import com.epam.adok.core.entity.AbstractBaseEntity;
import com.mysema.query.jpa.impl.JPAQuery;
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
        return new JPAQuery(this.entityManager);
    }

    public EntityManager getEntityManager() {
        return this.entityManager;
    }

    @Transactional
    @Override
    public void save(T t) {
        this.entityManager.persist(t);
    }

    @Transactional
    @Override
    public void update(T t) {
        this.entityManager.merge(t);
    }

    @Transactional
    @Override
    public void delete(T t) {
        this.entityManager.remove(
                this.entityManager.contains(t) ? t : this.entityManager.merge(t)
        );
    }

    @Override
    public List<T> readAll() {
        Query query = getReadAllNamedQuery();
        return query.getResultList();
    }

    protected abstract Query getReadAllNamedQuery();

}
