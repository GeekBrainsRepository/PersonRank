package ru.personrank.view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import javax.swing.*;
import org.jdesktop.swingx.JXTaskPane;
import org.jdesktop.swingx.JXTaskPaneContainer;
import ru.personrank.view.user.DailyStatisticsPanel;
import ru.personrank.view.user.GeneralStatisticsPanel;

public class Window extends JFrame {
    
    private JXTaskPaneContainer menu;
    private JPanel content;
    
    static {
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
    
    private JXTaskPaneContainer createMainMenu () {
        JXTaskPaneContainer container = new JXTaskPaneContainer();
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

    private JPanel createContentContainer () {
        JPanel container = new JPanel();
        CardLayout layout = new CardLayout();
        container.setLayout(layout);
        container.add("GeneralStatistic",new GeneralStatisticsPanel());
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
            layout.show(content,"GeneralStatistic");
        }
    }
    
    private class ActionDaily extends AbstractAction {

        public ActionDaily() {
            putValue(Action.NAME, "Ежедневная");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            CardLayout layout = (CardLayout) content.getLayout();
            layout.show(content,"DailyStatistic");
        }
    }
    
}
