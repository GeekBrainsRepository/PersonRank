
package ru.personrank.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

/**
 * Реализует перемещение окна приложения по экрану.
 * 
 * @author Мартынов Евгений
 */
public class WindowDragger extends MouseAdapter implements MouseMotionListener {

    private JFrame frameToDrag;
    private Point lastDragPosition;

    /**
     * Создает обьект класса.
     * @param frameToDrag - окно к которому применяются действия 
     */
    public WindowDragger(JFrame frameToDrag) {
        this.frameToDrag = frameToDrag;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        lastDragPosition = e.getLocationOnScreen();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        Point currentDragPosition = e.getLocationOnScreen();
        int deltaX = currentDragPosition.x - lastDragPosition.x;
        int deltaY = currentDragPosition.y - lastDragPosition.y;
        if (deltaX != 0 || deltaY != 0) {
            int x = frameToDrag.getLocation().x + deltaX;
            int y = frameToDrag.getLocation().y + deltaY;
            frameToDrag.setLocation(x, y);
            lastDragPosition = currentDragPosition;
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        // Не используется
    }
}
