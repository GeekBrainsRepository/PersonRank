/*
 *
 */
package PersonRank.data;

import java.util.List;

/**
 *
 */
public interface Repository <T> {
    void add (T entry);
    void remove(T entry);
    void update (T entry);
    List <T> query (Specification specification);
}
