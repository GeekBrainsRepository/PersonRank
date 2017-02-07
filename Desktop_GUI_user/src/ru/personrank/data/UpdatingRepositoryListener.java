/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.personrank.data;

import java.util.EventListener;

/**
 *
 * @author KIP&A
 */
public interface UpdatingRepositoryListener extends EventListener {
    
    void repositoryUpdated(UpdatingRepositoryEvent event);
}
