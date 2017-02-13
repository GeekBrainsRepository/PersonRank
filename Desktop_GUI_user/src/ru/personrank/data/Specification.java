/*
 *  
 */
package ru.personrank.data;

/**
 *
 */
public interface Specification<T> {
    boolean IsSatisfiedBy(T entry);
}
