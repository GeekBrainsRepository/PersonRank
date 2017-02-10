package PersonRank;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import static PersonRank.LeftLinksPanelClass.*;


class Window extends JFrame {
    static JPanel leftLinksPanel = new JPanel();
    static JPanel generalStatisticsPanel = new JPanel();//будет виден только внутри пакета
    static JPanel dailyStatisticsPanel = new JPanel();
    static JPanel personsPanel = new JPanel();
    static JPanel keywordsPanel = new JPanel();
    static JPanel sitesPanel = new JPanel();

    /*
    *  описание каркаса окна,
    */

    protected Window() {
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
        LeftLinksPanelClass.leftLinksPanel();
        add(leftLinksPanel, BorderLayout.LINE_START);
        action_menu();

        GeneralStatisticsPanelClass.generalStatisticsPanel();
        add(generalStatisticsPanel, BorderLayout.CENTER);
        generalStatisticsPanel.setVisible(true);

        DailyStatisticsPanelClass.dailyStatisticsPanel();

        PersonsPanelClass.personsPanel();

        KeywordsPanelClass.keywordsPanel();

        SitesPanelClass.sitesPanel();
    }
    private void action_menu(){
        generalStatistics.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                addDelete(generalStatisticsPanel, generalStatistics);
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

        dailyStatistics.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                addDelete(dailyStatisticsPanel, dailyStatistics);

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

        persons.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                addDelete(personsPanel, persons);

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

        keywords.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                addDelete(keywordsPanel, keywords);

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

        sites.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                addDelete(sitesPanel, sites);

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
        alLabel.add(generalStatistics);
        alLabel.add(dailyStatistics);
        alLabel.add(references);
        alLabel.add(persons);
        alLabel.add(keywords);
        alLabel.add(sites);

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

        generalStatistics.setFont(new Font("Arial", Font.PLAIN, 21));//костыль

            /*
            *  проверка на шрифт для выделения жирным
            */
        if (label==alLabel.get(1) | label==alLabel.get(2) | label == generalStatistics){// 3е значение костыль
            label.setFont(new Font("Arial", Font.BOLD, 21));
        }else {
            label.setFont(new Font("Arial", Font.BOLD, 16));
        }


        this.repaint();//перерисовка окна

    }

}