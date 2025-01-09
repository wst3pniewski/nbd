package org.example.model.repositories;

import java.util.List;
import java.util.UUID;

public interface Repository<T> {
    void add(T t);

    List<T> findAll();

    T findById(UUID id);

    void update(T t);
}
