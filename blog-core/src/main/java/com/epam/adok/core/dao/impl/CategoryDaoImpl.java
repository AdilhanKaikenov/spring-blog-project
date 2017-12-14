package com.epam.adok.core.dao.impl;

import com.epam.adok.core.dao.CategoryDao;
import com.epam.adok.core.entity.Category;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

@Repository
public class CategoryDaoImpl extends GenericDao<Category> implements CategoryDao {

    @Override
    public Category read(long id) {
        Query query = getEntityManager().createNamedQuery("Category.readById");
        query.setParameter("id", id);
        return (Category) query.getSingleResult();
    }

    @Override
    protected Query getReadAllNamedQuery() {
        return getEntityManager().createNamedQuery("Category.readAll");
    }

    @Override
    public List<Category> readByIdList(List<Long> ids) {
        Query query = getEntityManager().createNamedQuery("Category.readByIdList");
        query.setParameter("idList", ids);
        return  query.getResultList();
    }
}
