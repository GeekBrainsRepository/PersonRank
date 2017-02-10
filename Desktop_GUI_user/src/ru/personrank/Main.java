package ru.personrank;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Enumeration;
import javax.swing.*;
import javax.swing.plaf.FontUIResource;

import org.jdesktop.swingx.JXLoginPane;
import org.jdesktop.swingx.auth.LoginAdapter;
import org.jdesktop.swingx.auth.LoginEvent;
import org.jdesktop.swingx.auth.LoginService;
import ru.personrank.view.Window;

/**
 * Главный класс программы
 * 
 * @author Мартынов Евгений
 * @author Андрей
 * @author Федор
 */
public class Main {

    private static final String URL_GET_AUTH = "http://37.194.87.95:30000/authentication/";

    /**
     * Точка входа в приложение
     * @param args - список аргументов
     */
    public static void main(String[] args) {
        setLookAndFeel("Nimbus");
        setDefaultUIFont("Tahoma.ttf", 12);
        setLocalLoginPane();

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                showLoginWindow();
            }
        });
    }

    /**
     * Отображает окно авторизации
     */
    private static void showLoginWindow() {
        LoginService loginServise = new MyLoginService();
        loginServise.addLoginListener(new MyLoginListener());
        JXLoginPane loginPane = new JXLoginPane(loginServise);
        JFrame loginFrame = JXLoginPane.showLoginFrame(loginPane);
        loginFrame.setTitle("Авторизация");
        loginFrame.setVisible(true);
    }

    /**
     * Устанавливает менеджер отображения для приложения
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
     * Устанавливает стандартный шрифт для приложения
     * @param fontFileName - имя файла шрифта
     * @param fontSize - размер шрифта
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
     *
     */
    private static void setLocalLoginPane() {
        UIManager.put("JXLoginPane.bannerString", "Person Rank");
        UIManager.put("JXLoginPane.nameString", "Имя пользователя:");
        UIManager.put("JXLoginPane.passwordString", "Пароль:");
        UIManager.put("JXLoginPane.loginString", "Ок");
        UIManager.put("JXLoginPane.cancelString", "Отмена");
        UIManager.put("JXLoginPane.errorMessage", "Неправильный логин или пароль");
    }

    /**
     *
     */
    static class MyLoginService extends LoginService {

        @Override
        public boolean authenticate(String name, char[] password,
                String server) throws Exception {
            return chechUserLogin(name, String.valueOf(password));
        }

        private boolean chechUserLogin(String user, String login) {
            URL url = null;
            BufferedReader in = null;
            try {
                url = new URL(URL_GET_AUTH + user + "/" + login);
                URLConnection urlConnection = url.openConnection();
                in = new BufferedReader(new InputStreamReader(
                        urlConnection.getInputStream()));
                String inputLine;
                inputLine = in.readLine();
                if (inputLine.equals("true")) {
                    return true;
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return false;
        }
    }

    /**
     * Класс слушатель панели авторизации, реализует логику работы при 
     * прохождении авторизации 
     */
    static class MyLoginListener extends LoginAdapter {

        /**
         * Действия при удачном прохождении авторизациии
         * @param source 
         */
        @Override
        public void loginSucceeded(LoginEvent source) {
            Window.getInstance().setVisible(true);
        }

    }

}
