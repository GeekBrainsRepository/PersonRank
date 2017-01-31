package ru.personrank;

import ru.personrank.view.Window;

public class Main {

    public static void main(String[] args) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Window().setVisible(true);
            }
        });

    }
}
