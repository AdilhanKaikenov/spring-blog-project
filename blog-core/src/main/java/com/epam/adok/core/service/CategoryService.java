package com.epam.adok.core.service;

import com.epam.adok.core.dao.CategoryDao;
import com.epam.adok.core.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryDao categoryDao;

    public Category findCategoryByID(long id) {
        return this.categoryDao.read(id);
    }

    public List<Category> findAllCategories() {
        return this.categoryDao.readAll();
    }

    public List<Category> findAllCategoriesByIdList(List<Long> ids) {
        return this.categoryDao.readByIdList(ids);
    }
}
