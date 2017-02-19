package ru.personrank;

import ru.personrank.view.Authorization;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.io.IOException;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

/**
 * Главный класс программы.
 *
 * @author Мартынов Евгений
 * @author Митков Федор
 * @author Кучеров Андрей
 */
public class Main {

    private static Logger log = Logger.getLogger(Main.class.getName());

    /**
     * Точка входа в приложение
     *
     * @param args - список аргументов
     */
    public static void main(String[] args) {
        setConfigLogging();
        setLookAndFeel("Nimbus");
        setLocaleAuthorization();
        setDefaultUIFont("Tahoma.ttf", 12);
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                Authorization.getInstance();
                log.info("Запуск приложения прошел успешно!");
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
        } catch (IllegalAccessException ex) {
            log.log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            log.log(Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            log.log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            log.log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Устанавливает стандартный шрифт для приложения.
     *
     * @param fontFileName - имя файла шрифта
     * @param fontSize - размер шрифта
     */
    private static void setDefaultUIFont(String fontFileName, int fontSize) {
        try {
            FontUIResource newFont = new FontUIResource(
                    Font.createFont(Font.TRUETYPE_FONT,
                            Main.class.getResourceAsStream("/ru/resources/fonts/"
                                    + fontFileName))
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
            log.log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            log.log(Level.SEVERE, null, ex);
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

    /**
     * Устанавливает конфигурацию логгера.
     */
    private static void setConfigLogging() {
        try {
            LogManager.getLogManager().readConfiguration(
                    Main.class.getResourceAsStream(
                            "/ru/resources/logging.properties"));
        } catch (IOException | SecurityException ex) {
            System.err.println("Не удалось найти файл настроек логгера : "
                    + ex.toString());
        }
    }
}
