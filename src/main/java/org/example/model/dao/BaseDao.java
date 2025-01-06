package org.example.model.dao;

import com.datastax.oss.driver.api.mapper.annotations.Delete;
import com.datastax.oss.driver.api.mapper.annotations.Insert;
import com.datastax.oss.driver.api.mapper.annotations.Select;

import java.util.UUID;

public interface BaseDao<T> {
    @Insert
    void create(T t);

    @Select
    T findById(UUID id);

    @Delete
    void delete(T t);
}
