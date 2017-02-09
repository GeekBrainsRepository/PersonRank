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
import org.jdesktop.swingx.auth.LoginService;
import ru.personrank.view.Window;

public class Main {

    private static final String URL_GET_AUTH = "http://37.194.87.95:30000/authentication/";

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

    // Метод отображает окно авторизации
    private static void showLoginWindow() {
        JXLoginPane loginPane = new JXLoginPane(new LoginServiceImpl());
        JFrame loginFrame = JXLoginPane.showLoginFrame(loginPane);
        loginFrame.setTitle("Авторизация");
        loginFrame.setVisible(true);
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
                            Main.class.getResourceAsStream("/ru/resources/fonts/" + fontFileName))
                            .deriveFont(Font.PLAIN, fontSize));

//                    Font.createFont(Font.TRUETYPE_FONT,
//                            new File(System.getProperty("user.dir") + "/fonts/" + fontFileName))                           
//                            .deriveFont(Font.PLAIN, fontSize));
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

    // Локализация панели авторизации
    private static void setLocalLoginPane() {
        UIManager.put("JXLoginPane.bannerString", "Person Rank");
        UIManager.put("JXLoginPane.nameString", "Имя пользователя:");
        UIManager.put("JXLoginPane.passwordString", "Пароль:");
        UIManager.put("JXLoginPane.loginString", "Ок");
        UIManager.put("JXLoginPane.cancelString", "Отмена");
        UIManager.put("JXLoginPane.errorMessage", "Неправильный логин или пароль");
    }

    //***
    static class LoginServiceImpl extends LoginService {

        @Override
        public boolean authenticate(String name, char[] password,
                                    String server) throws Exception {

            if (chechUserLogin(name, String.valueOf(password))) {
                Window.getInstance().setVisible(true);
                return true;
            } else {
                return false;
            }
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

}
