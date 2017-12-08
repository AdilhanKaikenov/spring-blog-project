package com.epam.adok.core.dao.impl;

import com.epam.adok.core.dao.CategoryDao;
import com.epam.adok.core.entity.Category;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;

@Repository
public class CategoryDaoImpl extends GenericDao<Category> implements CategoryDao {

    @Override
    public Category read(int id) {
        Query query = getEntityManager().createNamedQuery("Category.readById");
        query.setParameter("id", id);
        return (Category) query.getSingleResult();
    }

    @Override
    protected Query getReadAllNamedQuery() {
        return getEntityManager().createNamedQuery("Category.readAll");
    }
}
