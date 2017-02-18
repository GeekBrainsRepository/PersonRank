package ru.personrank.data;

import java.util.EventObject;

/**
 * Событие, которое указывает на то что произошло обновление репозитория.
 * 
 * @author Мартынов Евгений
 * @version 1.0
 */
public class UpdatingRepositoryEvent extends EventObject {

    /**
     * Создает обьект <code>UpdatingRepositoryEvent</code> с указанным 
     * источником события.
     * 
     * @param source - источник события; 
     */
    public UpdatingRepositoryEvent(Repository source) {
        super(source);
    }
    
}
