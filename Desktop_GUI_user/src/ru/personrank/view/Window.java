package ru.personrank.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import ru.personrank.view.admin.KeywordsPanel;
import ru.personrank.view.admin.PersonsPanel;
import ru.personrank.view.admin.SitesPanel;
import ru.personrank.view.user.DailyStatisticsPanel;
import ru.personrank.view.user.GeneralStatisticsPanel;

public class Window extends JFrame {
    
    private LeftLinksPanel leftLinksPanel = new LeftLinksPanel();
    private JPanel contentPanel = new JPanel(new BorderLayout());
    private JLabel[] labelMenu;
    private JPanel[] panels = new JPanel[]{
            new GeneralStatisticsPanel(),
            new DailyStatisticsPanel(),
            new KeywordsPanel(),
            new PersonsPanel(),
            new SitesPanel()
    };

    
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
        labelMenu = leftLinksPanel.getLabelLeftMenu();
        leftLinksPanel.setPreferredSize(new Dimension(230,430));
        for (int i = 0; i < labelMenu.length; i++) {
            labelMenu[i].addMouseListener(new MenuMouseListener(i));
        }
        contentPanel.add(panels[0], BorderLayout.CENTER);
        getContentPane().add(leftLinksPanel, BorderLayout.WEST);
        getContentPane().add(contentPanel,  BorderLayout.CENTER);
    }

    @Override
    public void setTitle(String title) {
       ContentPane contentPane = (ContentPane) this.getContentPane();
       contentPane.setTitle(title);
    }
    
    private class MenuMouseListener extends MouseAdapter {

        private int index;

        public MenuMouseListener(int indexPanel) {
            index = indexPanel;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.getButton() == MouseEvent.BUTTON1) {
                for (JLabel label : labelMenu) {
                    label.setFont(label.getFont().deriveFont(Font.PLAIN));
                }
                JLabel label = (JLabel) e.getSource();
                label.setFont(label.getFont().deriveFont(Font.BOLD));
                contentPanel.removeAll();
                contentPanel.add(panels[index], BorderLayout.CENTER);
                contentPanel.revalidate();
                contentPanel.repaint();
            }
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            JLabel label = (JLabel) e.getSource();
            String text = label.getText();
            label.setText("<html><b><u>" + text);
        }

        @Override
        public void mouseExited(MouseEvent e) {
            JLabel label = (JLabel) e.getSource();
            String text = label.getText();
            label.setText(text.substring(12));
        }

    }

}
