package ru.personrank;

import ru.personrank.view.Authorization;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.io.IOException;
import java.util.Enumeration;

/**
 * Главный класс программы.
 *
 * @author Мартынов Евгений
 * @author Митков Федор
 * @author Андрей Кучеров
 */
public class Main {

    /**
     * Точка входа в приложение
     *
     * @param args - список аргументов
     */
    public static void main(String[] args) {
        setLookAndFeel("Nimbus");
        setLocaleAuthorization();
        setDefaultUIFont("Tahoma.ttf", 12);
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Authorization.getInstance();
            }
        });
    }

    /**
     * Устанавливает менеджер отображения для приложения.
     *
     * @param name - название менеджера
     */
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

    /**
     * Устанавливает стандартный шрифт для приложения.
     *
     * @param fontFileName - имя файла шрифта
     * @param fontSize     - размер шрифта
     */
    private static void setDefaultUIFont(String fontFileName, int fontSize) {
        try {
            FontUIResource newFont = new FontUIResource(
                    Font.createFont(Font.TRUETYPE_FONT,
                            Main.class.getResourceAsStream("/ru/resources/fonts/" + fontFileName))
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

    /**
     * Логкализация окна авторизации.
     */
    private static void setLocaleAuthorization() {
        UIManager.put("JXLoginPane.bannerString", "Person Rank");
        UIManager.put("JXLoginPane.nameString", "Имя пользователя:");
        UIManager.put("JXLoginPane.passwordString", "Пароль:");
        UIManager.put("JXLoginPane.loginString", "Ок");
        UIManager.put("JXLoginPane.cancelString", "Отмена");
        UIManager.put("JXLoginPane.errorMessage", "Неправильный логин или пароль");
    }
}
