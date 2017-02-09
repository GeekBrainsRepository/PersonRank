package ru.personrank.view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import javax.swing.*;

import org.jdesktop.swingx.JXTaskPane;
import org.jdesktop.swingx.JXTaskPaneContainer;
import ru.personrank.view.user.DailyStatisticsPanel;
import ru.personrank.view.user.GeneralStatisticsPanel;

public class Window extends JFrame {

    private static final Window INSTANCE = new Window();

    private JXTaskPaneContainer menu;
    private JPanel content;

    private Window() {
        setSize(750, 430);
        setUndecorated(true);
        setContentPane(new ContentPane());
        setTitle("Person Rank");
        setIconImage(new ImageIcon(getClass().getResource("/ru/resources/images/title_icon.png")));
        WindowDragger winDragger = new WindowDragger(this);
        addMouseListener(winDragger);
        addMouseMotionListener(winDragger);
        setLocationRelativeTo(null);
        menu = createMainMenu();
        content = createContentContainer();
        getContentPane().add(content, BorderLayout.CENTER);
        getContentPane().add(menu, BorderLayout.WEST);
    }

    // Метод возвращающий экземпляр Window
    public static Window getInstance() {
        return INSTANCE;
    }

    // Метод создающий левое меню программы
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

    //Метод создающий контейнер для отображения панелей
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
        super.setTitle(title);
        ContentPane contentPane = (ContentPane) this.getContentPane();
        contentPane.setTitle(title);

    }

    private void setIconImage(ImageIcon imageIcon) {
        super.setIconImage(imageIcon.getImage());
        ContentPane pane = (ContentPane) getContentPane();
        pane.setTitleIcon(imageIcon);
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