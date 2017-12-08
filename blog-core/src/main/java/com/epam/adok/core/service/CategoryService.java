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

    public Category findCategoryByID(int id) {
        return categoryDao.read(id);
    }

    public List<Category> findAllCategories() {
        return categoryDao.readAll();
    }

}
