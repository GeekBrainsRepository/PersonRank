package ru.personrank;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.FontUIResource;
import ru.personrank.view.Window;

public class Main {

    public static void main(String[] args) {

        setLookAndFeel("Nimbus");
        setDefaultUIFont("Tahoma.ttf", 12);

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Window.getInstance().setVisible(true);
            }
        });

    }

    // Метод устанавливает для приложения менеджер отображения 
    private static void setLookAndFeel(String name) {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if (name.equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // Метод устанавливает шрифт для приложения
    private static void setDefaultUIFont(String fontFileName, int fontSize) {
        try {
            FontUIResource newFont = new FontUIResource(
                    Font.createFont(Font.TRUETYPE_FONT,
                            new File(System.getProperty("user.dir") + "/fonts/" + fontFileName))
                            .deriveFont(Font.PLAIN, fontSize));
            Enumeration<Object> keys = UIManager.getDefaults().keys();
            while (keys.hasMoreElements()) {
                Object key = keys.nextElement();
                Object value = UIManager.get(key);
                if (value instanceof FontUIResource) {
                    UIManager.put(key, newFont);
                }
            }
        } catch (FontFormatException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
