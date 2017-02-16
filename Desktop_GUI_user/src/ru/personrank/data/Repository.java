
package ru.personrank.data;

import java.util.List;

/**
 * Интрфейс для описания обьекта репозиторий. Репозиторий - это хранилище,
 * являющееся прослойкой между источником данных и потребителем.
 * 
 * @author Мартынов Евгений
 */
public interface Repository<T> {
    
    /**
     * Добавляет элемент в хранилище.
     * 
     * @param entry - ссылка на объект класса. 
     */
    void add(T entry);
    
    /**
     * Удаляет элемент из хранилища.
     * 
     * @param entry - ссылка на объект класса.
     */
    void remove(T entry);

    /**
     * Изменяет элемент в хранилище.
     * 
     * @param entry - ссылка на объект класса.
     */
    void update(T entry);

    /**
     * Возращает список элементов.
     * 
     * @param specification - обьект спецификации  
     * @return - список объектов в виде колекции List 
     */
    List<T> query(Specification specification);
}