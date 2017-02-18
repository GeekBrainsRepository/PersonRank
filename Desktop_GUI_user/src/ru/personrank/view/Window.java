package ru.personrank.view;

import org.jdesktop.swingx.JXTaskPane;
import org.jdesktop.swingx.JXTaskPaneContainer;
import ru.personrank.view.user.DailyStatisticsPanel;
import ru.personrank.view.user.GeneralStatisticsPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Главное окно программы.
 * <p>
 * Окно программы содержит левое меню для выбора отображаемых панелей и
 * панель контентента в которой отображаются панели.
 * </p>
 * 
 * @author Мартынов Евгений
 * @author Кучеров Андрей
 * @author Митков Федор
 * 
 * @version 1.0
 */
public class Window extends JFrame {

    private static final ExecutorService threadPool = Executors.newCachedThreadPool();
    private static final Window INSTANCE = new Window();
    
    private JXTaskPaneContainer menu;
    private JPanel content;

    /**
     * Создает окно.
     */
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

    /** 
    * Метод возвращающий главное окно.
    */
    public static Window getInstance() {
        return INSTANCE;
    }
    
    /**
     * Добавляет новый поток в пул потоков программы.
     * @param thread - поток исполнения
     */
    public static void addThreadInPool (Thread thread) {
        threadPool.execute(thread);
    }
    
    /**
     * Создает левое меню. 
     */
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
        reference.setVisible(false);// отключает меню справочников
        return container;
    }

    /**
     * Создает контейнер для отображения панелей.
     */ 
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

    /**
     * Устанавливает иконку для приложения.
     * @param imageIcon - изображение
     */
    private void setIconImage(ImageIcon imageIcon) {
        super.setIconImage(imageIcon.getImage());
        ContentPane pane = (ContentPane) getContentPane();
        pane.setTitleIcon(imageIcon);
    }

    /**
     * Команда для метки "Общая" в левом меню.
     */
    private class ActionGeneral extends AbstractAction {

        /**
         * Создает команду.
         */
        public ActionGeneral() {
            putValue(Action.NAME, "Общая");
        }

        /**
         * Действия при выполнении команды.
         * 
         * @param e - событие 
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            CardLayout layout = (CardLayout) content.getLayout();
            layout.show(content, "GeneralStatistic");
        }
    }

    /**
     * Команда для метки "Ежедневная" в левом меню.
     */
    private class ActionDaily extends AbstractAction {

        /**
         * Создает команду.
         */
        public ActionDaily() {
            putValue(Action.NAME, "Ежедневная");
        }

        /**
         * Действия при выполнении команды.
         * 
         * @param e - событие 
         */ 
        @Override
        public void actionPerformed(ActionEvent e) {
            CardLayout layout = (CardLayout) content.getLayout();
            layout.show(content, "DailyStatistic");
        }
    }

}