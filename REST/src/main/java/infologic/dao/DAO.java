package infologic.dao;

import java.util.List;

public interface DAO<T> {
    T select(int id);

    List<T> select();

    boolean insert(T entity);

    boolean update(T entity);

    boolean delete(int id);
}
