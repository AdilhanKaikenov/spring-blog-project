package com.epam.adok.core.dao;

import com.epam.adok.core.entity.AbstractBaseEntity;

import java.util.List;

public interface Dao<T extends AbstractBaseEntity> {

    void save(T t);

    T read(int id);

    void update(T t);

    void delete(T t);

    List<T> readAll();

}
