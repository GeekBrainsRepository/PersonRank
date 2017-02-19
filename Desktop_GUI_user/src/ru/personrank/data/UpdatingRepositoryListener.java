
package ru.personrank.data;

import java.util.EventListener;

/**
 * Интерфейс слушателя для получения события "обновление репозитория". 
 * 
 * @author Мартынов Евгений
 * 
 * @see UpdatingRepositoryEvent
 */
public interface UpdatingRepositoryListener extends EventListener {
    
    /**
     * Вызывается когда происходит изменение содержимого репозитория.
     * 
     * @param event - обьект события <b>UpdatingRepositoryEvent</b>
     */
    void repositoryUpdated(UpdatingRepositoryEvent event);
}
