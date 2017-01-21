package infologic.dao;

import java.util.List;

/**
 * Created by Антон Владимирович on 21.01.2017.
 */
public interface DAO<T> {
    T select(int id);

    List<T> select();

    boolean insert(T entity);

    boolean update(T entity);

    boolean delete(int id);
}
