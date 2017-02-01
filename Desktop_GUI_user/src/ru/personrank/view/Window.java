package ru.personrank.view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import org.jdesktop.swingx.JXTaskPane;
import org.jdesktop.swingx.JXTaskPaneContainer;
import ru.personrank.view.user.DailyStatisticsPanel;
import ru.personrank.view.user.GeneralStatisticsPanel;

public class Window extends JFrame {

    private JXTaskPaneContainer menu;
    private JPanel content;

    static {
        // Устанавливаем ui менеджер "Nimbus"
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
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

    public Window() {
        try {
            FontUIResource font = new FontUIResource(
                    Font.createFont(Font.TRUETYPE_FONT,
                            new File(System.getProperty("user.dir") + "/fonts/Arial.ttf"))
                            .deriveFont(Font.PLAIN, 12));
            setDefaultUIFont(font);
        } catch (FontFormatException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        setSize(750, 430);
        setUndecorated(true);
        setContentPane(new ContentPane());
        setTitle("Person Rank");
        WindowDragger winDragger = new WindowDragger(this);
        addMouseListener(winDragger);
        addMouseMotionListener(winDragger);
        setLocationRelativeTo(null);
        menu = createMainMenu();
        content = createContentContainer();
        getContentPane().add(content, BorderLayout.CENTER);
        getContentPane().add(menu, BorderLayout.WEST);
    }

    // Устанавливает тип шрифта текста поумолчанию 
    private static void setDefaultUIFont(FontUIResource font) {
        Enumeration<Object> keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if (value instanceof FontUIResource) {
                UIManager.put(key, font);
            }
        }
    }

    private JXTaskPaneContainer createMainMenu() {
        JXTaskPaneContainer container = new JXTaskPaneContainer();
        container.setPreferredSize(new Dimension(200, Window.this.getHeight()));
        JXTaskPane statistics = new JXTaskPane();
        statistics.setTitle("Статистика");
        statistics.add(new ActionGeneral());
        statistics.add(new ActionDaily());
        JXTaskPane reference = new JXTaskPane();
        reference.setTitle("Справочники");
        reference.add(new JLabel("Ключевые слова"));
        reference.add(new JLabel("Персоны"));
        reference.add(new JLabel("Сайты"));
        container.add(statistics);
        container.add(reference);

        return container;
    }

    private JPanel createContentContainer() {
        JPanel container = new JPanel();
        CardLayout layout = new CardLayout();
        container.setLayout(layout);
        container.add("GeneralStatistic", new GeneralStatisticsPanel());
        container.add("DailyStatistic", new DailyStatisticsPanel());
        return container;
    }

    @Override
    public void setTitle(String title) {
        ContentPane contentPane = (ContentPane) this.getContentPane();
        contentPane.setTitle(title);

    }

    private class ActionGeneral extends AbstractAction {

        public ActionGeneral() {
            putValue(Action.NAME, "Общая");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            CardLayout layout = (CardLayout) content.getLayout();
            layout.show(content, "GeneralStatistic");
        }
    }

    private class ActionDaily extends AbstractAction {

        public ActionDaily() {
            putValue(Action.NAME, "Ежедневная");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            CardLayout layout = (CardLayout) content.getLayout();
            layout.show(content, "DailyStatistic");
        }
    }

}
