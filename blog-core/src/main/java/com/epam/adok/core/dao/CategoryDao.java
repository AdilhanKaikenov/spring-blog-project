package com.epam.adok.core.dao;

import com.epam.adok.core.entity.Category;

import java.util.List;

public interface CategoryDao extends Dao<Category> {

    List<Category> readByIdList(List<Long> ids);

}
