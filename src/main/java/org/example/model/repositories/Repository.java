package org.example.model.repositories;

import java.util.List;
import java.util.UUID;

public interface Repository<T> {
    T add(T t);

    List<T> findAll();

    T findById(UUID id);

    T update(T t);
}
