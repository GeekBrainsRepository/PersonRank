package infologic.repository;

/**
 * Created by Антон Владимирович on 26.01.2017.
 */

public interface Specification <T> {
    boolean specified(T pattern);
}
