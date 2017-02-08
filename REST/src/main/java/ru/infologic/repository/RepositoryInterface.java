package ru.infologic.repository;

import java.util.List;

/**
 * Created by Антон Владимирович on 25.01.2017.
 */
public interface RepositoryInterface<T> {
    void add(T pattern);

    void remove(T pattern);

    void update(T pattern);

    List<? extends T> query(Specification specification, Object... args);
}