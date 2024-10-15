package org.example.model.repositories;

import java.util.List;

public interface RepositoryI<T> {
    T add(T t);

    List<T> findAll();

    T findById(Long id);

    T update(T t);

    T delete(Long id);

    T findByIdWithOptimisticLock(Long id);
}
