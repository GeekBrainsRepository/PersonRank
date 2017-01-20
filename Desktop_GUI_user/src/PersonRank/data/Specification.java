/*
 *  
 */
package PersonRank.data;

/**
 * 
 */
public interface Specification <T> {
    boolean IsSatisfiedBy(T entry);
}
