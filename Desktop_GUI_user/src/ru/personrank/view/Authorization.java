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
 * Класс реализует окно авторизации приложении
 * 
 * @author Митков Федор
 */
public class Authorization {

    private static final String URL_GET_AUTH = "http://37.194.87.95:30000/authentication/";

    private static final Authorization INSTANCE = new Authorization();


    private Authorization() {
        LoginService loginServise = new Authorization.MyLoginService();
        loginServise.addLoginListener(new Authorization.MyLoginListener());
        JXLoginPane loginPane = new JXLoginPane(loginServise);
        JFrame loginFrame = JXLoginPane.showLoginFrame(loginPane);
        loginFrame.setTitle("Авторизация");
        loginFrame.setVisible(true);
    }

    // Метод возвращающий экземпляр Authorization
    public static Authorization getInstance() {
        return INSTANCE;
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

            //Вход в систему минуя авторизацию на сервере, для тестирования.
            if (user.equals("test") && login.equals("")) {
                return true;
            }

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
         *
         * @param source
         */
        @Override
        public void loginSucceeded(LoginEvent source) {
            Window.getInstance().setVisible(true);
        }

    }
}
