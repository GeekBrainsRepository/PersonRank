package ru.personrank.view;

import org.jdesktop.swingx.JXLoginPane;
import org.jdesktop.swingx.auth.LoginAdapter;
import org.jdesktop.swingx.auth.LoginEvent;
import org.jdesktop.swingx.auth.LoginService;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;


/**
 * Authorization является специальным окном, которое реализует авторизацию
 * пользователя в системе.
 * 
 * @author Митков Федор
 */
public class Authorization {

    private static final String URL_GET_AUTH = "http://37.194.87.95:30000/authentication/";

    private static final Authorization INSTANCE = new Authorization();
    
    private Authorization() {
        LoginService loginServise = new Authorization.AuthorizationService();
        loginServise.addLoginListener(new Authorization.AuthorizationListener());
        JXLoginPane loginPane = new JXLoginPane(loginServise);
        JFrame loginFrame = JXLoginPane.showLoginFrame(loginPane);
        loginFrame.setTitle("Авторизация");
        loginFrame.setVisible(true);
    }

    /** 
     * Метод возвращающий экземпляр Authorization.
     * 
     * @return - обьект Authorization
     */
    public static Authorization getInstance() {
        return INSTANCE;
    }

    /**
     * Реализует проверку логина и пароля пользователя.
     */
    static class AuthorizationService extends LoginService {

        @Override
        public boolean authenticate(String name, char[] password,
                                    String server) throws Exception {
            return chechUserLogin(name, String.valueOf(password));
        }
        
        /**
         * Делает запрос на сервер для проверки логина и пароля, если аторизация
         * на сервере проходит успешно, метод возвращает true, если екудачно
         * false.
         * 
         * @param user - имя пользователя в виде String;
         * @param passw - пароль пользователя в виде String;
         * @return - логическое значение.
         */
        private boolean chechUserLogin(String user, String passw) {

            //Вход в систему минуя авторизацию на сервере, для тестирования.
            if (user.equals("test") && passw.equals("")) {
                return true;
            }

            URL url = null;
            BufferedReader in = null;
            try {
                url = new URL(URL_GET_AUTH + user + "/" + passw);
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
     * Cлушатель панели авторизации, реализует логику работы при
     * прохождении авторизации
     */
    static class AuthorizationListener extends LoginAdapter {

        /**
         * Действия при удачном прохождении авторизациии
         *
         * @param source
         */
        @Override
        public void loginSucceeded(LoginEvent source) {
            Window.getInstance().setVisible(true);
        }

    }
}
