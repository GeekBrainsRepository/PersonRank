/*
 *
 */
package PersonRank.data;

import java.awt.List;

/**
 *
 */
public interface Repository <T> {
    void add (T entry);
    void remove(T entry);
    void update (T entry);
    List query (Specification specification);
}
