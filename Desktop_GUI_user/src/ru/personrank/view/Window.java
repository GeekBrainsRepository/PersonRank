package ru.personrank.view;

import ru.personrank.view.admin.KeywordsPanel;
import ru.personrank.view.admin.PersonsPanel;
import ru.personrank.view.admin.SitesPanel;
import ru.personrank.view.user.DailyStatisticsPanel;
import ru.personrank.view.user.GeneralStatisticsPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Window extends JFrame {

    private LeftLinksPanel leftLinksPanel = new LeftLinksPanel();
    private JPanel contentPanel = new JPanel(new BorderLayout());
    JLabel[] labelMenu;
    private JPanel[] panels = new JPanel[]{
        new GeneralStatisticsPanel(),
        new DailyStatisticsPanel(),
        new KeywordsPanel(),
        new PersonsPanel(),
        new SitesPanel()
    };

    /*
    *  описание каркаса окна,
     */
    public Window() {
        setSize(700, 430);
        setLocationRelativeTo(null);
        setResizable(false);
        setTitle("PersonRank");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        add(leftLinksPanel, BorderLayout.WEST);
        add(contentPanel, BorderLayout.CENTER);
        contentPanel.add(panels[0]);
        labelMenu = leftLinksPanel.getLabelLeftMenu();
        for (int i = 0; i < labelMenu.length; i++) {
            labelMenu[i].addMouseListener(new MenuMouseListener(i));
        }

    }

    private class MenuMouseListener extends MouseAdapter {

        private int index;

        public MenuMouseListener(int indexPanel) {
            index = indexPanel;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.getButton() == MouseEvent.BUTTON1) {
                for(JLabel label : labelMenu) {
                    label.setFont(label.getFont().deriveFont(Font.PLAIN));
                }
                JLabel label = (JLabel)e.getSource();
                label.setFont(label.getFont().deriveFont(Font.BOLD));
                contentPanel.removeAll();
                contentPanel.add(panels[index]);
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
