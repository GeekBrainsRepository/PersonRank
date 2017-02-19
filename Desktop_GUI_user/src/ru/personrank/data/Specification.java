
package ru.personrank.data;

/**
 * Описание обьекта <b>Specification</b> предоставляющего условие для выборки
 * определенных элементов.
 * 
 * @author Мартынов Евгений
 */
public interface Specification<T> {
    
    /**
     * Проверяет обьект на соответствие заданным условиям, если проверка
     * проходит успешнео возвращает true, если нет - false.
     * 
     * @param entry - ссылка на проверяемый обьект. 
     * @return - логическое значение.
     */
    boolean IsSatisfiedBy(T entry);
}
