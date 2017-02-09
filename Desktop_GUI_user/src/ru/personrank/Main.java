package ru.personrank;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Locale;
import javax.swing.*;
import javax.swing.plaf.FontUIResource;

import org.jdesktop.swingx.JXLoginPane;
import org.jdesktop.swingx.auth.LoginService;
import org.jdesktop.swingx.plaf.LoginPaneUI;
import org.jdesktop.swingx.plaf.UIManagerExt;
import ru.personrank.view.Window;

public class Main {

    public static void main(String[] args) {

        setLookAndFeel("Nimbus");
        setDefaultUIFont("Tahoma.ttf", 12);
        setLocalLoginPane();
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                // панель авторизаций
                final JXLoginPane loginPane = new JXLoginPane(new LoginService() {
                    @Override
                    public boolean authenticate(String name, char[] password,
                                                String server) throws Exception {

                        if (name.equalsIgnoreCase("user") && String.valueOf(password).equalsIgnoreCase("user")) {
                            Window.getInstance().setVisible(true);
                            return true;
                        } else {
                            return false;
                        }
                    }
                });
                final JFrame loginFrame = JXLoginPane.showLoginFrame(loginPane);
                loginFrame.setTitle("Авторизация");
                loginFrame.setVisible(true);
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
    private static void setLocalLoginPane () {
        UIManager.put("JXLoginPane.bannerString", "Person Rank");
        UIManager.put("JXLoginPane.nameString", "Имя пользователя:");
        UIManager.put("JXLoginPane.passwordString", "Пароль:");
        UIManager.put("JXLoginPane.loginString", "Ок");
        UIManager.put("JXLoginPane.cancelString", "Отмена");
        UIManager.put("JXLoginPane.errorMessage", "Неправильный логин или пароль");
    }
}
