package ru.personrank.view;

import ru.personrank.view.admin.KeywordsPanel;
import ru.personrank.view.admin.PersonsPanel;
import ru.personrank.view.admin.SitesPanel;
import ru.personrank.view.user.DailyStatisticsPanel;
import ru.personrank.view.user.GeneralStatisticsPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;


public class Window extends JFrame {
    static public JPanel leftLinksPanel = new JPanel();
    static public JPanel generalStatisticsPanel = new JPanel();//будет виден только внутри пакета
    static public JPanel dailyStatisticsPanel = new JPanel();
    static public JPanel personsPanel = new JPanel();
    static public JPanel keywordsPanel = new JPanel();
    static public JPanel sitesPanel = new JPanel();

    /*
    *  описание каркаса окна,
    */

    public Window() {
        setSize(700, 430);

        setLocationRelativeTo(null);
        setResizable(false);
        setTitle("PersonRank");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        initComponents();

    }

    /*
    * инициализация компонентов
    */

    private void initComponents() {
        LeftLinksPanel.leftLinksPanel();
        add(leftLinksPanel, BorderLayout.LINE_START);
        action_menu();

        GeneralStatisticsPanel.generalStatisticsPanel();
        add(generalStatisticsPanel, BorderLayout.CENTER);
        generalStatisticsPanel.setVisible(true);

        DailyStatisticsPanel.dailyStatisticsPanel();

        PersonsPanel.personsPanel();

        KeywordsPanel.keywordsPanel();

        SitesPanel.sitesPanel();
    }
    private void action_menu(){
        LeftLinksPanel.generalStatistics.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                addDelete(generalStatisticsPanel, LeftLinksPanel.generalStatistics);
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        LeftLinksPanel.dailyStatistics.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                addDelete(dailyStatisticsPanel, LeftLinksPanel.dailyStatistics);

            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        LeftLinksPanel.persons.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                addDelete(personsPanel, LeftLinksPanel.persons);

            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        LeftLinksPanel.keywords.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                addDelete(keywordsPanel, LeftLinksPanel.keywords);

            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        LeftLinksPanel.sites.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                addDelete(sitesPanel, LeftLinksPanel.sites);

            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
    }
    private void addDelete(JPanel panel, JLabel label){
        ArrayList<JPanel> alPanel= new ArrayList<JPanel>();
        alPanel.add(generalStatisticsPanel);
        alPanel.add(dailyStatisticsPanel);
        alPanel.add(personsPanel);
        alPanel.add(keywordsPanel);
        alPanel.add(sitesPanel);

        ArrayList<JLabel> alLabel= new ArrayList<JLabel>();
        alLabel.add(LeftLinksPanel.generalStatistics);
        alLabel.add(LeftLinksPanel.dailyStatistics);
        alLabel.add(LeftLinksPanel.references);
        alLabel.add(LeftLinksPanel.persons);
        alLabel.add(LeftLinksPanel.keywords);
        alLabel.add(LeftLinksPanel.sites);

            /*
            *  удаляем все панели с JFrame
            */
        for (int i = 0; i < alPanel.size(); i++) {
            remove(alPanel.get(i));
        }
            /*
            *  добавляем ту которая нужна
            */
        add(panel, BorderLayout.CENTER);
        panel.setVisible(true);

            /*
            *  проверка на шрифт для исправления в изначальное положение
            */
        for (int i = 0; i < alLabel.size(); i++) {
            if (alLabel.get(i)==alLabel.get(1) || alLabel.get(i)==alLabel.get(2)){
                alLabel.get(i).setFont(new Font("Arial", Font.PLAIN, 21));
            }else {
                alLabel.get(i).setFont(new Font("Arial", Font.PLAIN, 16));
            }

        }

        LeftLinksPanel.generalStatistics.setFont(new Font("Arial", Font.PLAIN, 21));//костыль

            /*
            *  проверка на шрифт для выделения жирным
            */
        if (label==alLabel.get(1) | label==alLabel.get(2) | label == LeftLinksPanel.generalStatistics){// 3е значение костыль
            label.setFont(new Font("Arial", Font.BOLD, 21));
        }else {
            label.setFont(new Font("Arial", Font.BOLD, 16));
        }


        this.repaint();//перерисовка окна

    }

}