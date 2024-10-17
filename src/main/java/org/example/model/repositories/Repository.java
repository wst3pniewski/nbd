package org.example.model.repositories;

import java.util.List;

public interface Repository<T> {
    T add(T t);

    List<T> findAll();

    T findById(Long id);

    T findByIdWithOptimisticLock(Long id);
}
