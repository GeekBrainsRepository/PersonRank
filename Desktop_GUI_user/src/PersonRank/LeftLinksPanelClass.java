package PersonRank;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class LeftLinksPanelClass extends Window{

    static JLabel generalStatistics;
    static JLabel dailyStatistics;
    static JLabel references;
    static JLabel persons;
    static JLabel keywords;
    static JLabel sites;

    static void leftLinksPanel(){
        createLeftLinksPanel();
    }
    private static void createLeftLinksPanel() {
        String whiteSpace = "          ";
        generalStatistics = new JLabel("Общая статистика");
        generalStatistics.setFont(new Font("Arial", Font.BOLD, 21));

        dailyStatistics = new JLabel("Ежедневная статистика");
        dailyStatistics.setFont(new Font("Arial", Font.PLAIN, 21));

        references = new JLabel("Справочники");
        references.setFont(new Font("Arial", Font.PLAIN, 21));

        persons = new JLabel(whiteSpace + "Личности");
        persons.setFont(new Font("Arial", Font.PLAIN, 16));

        keywords = new JLabel(whiteSpace + "Ключевые слова");
        keywords.setFont(new Font("Arial", Font.PLAIN, 16));

        sites = new JLabel(whiteSpace + "Сайты");
        sites.setFont(new Font("Arial", Font.PLAIN, 16));

        leftLinksPanel.setBorder(new EmptyBorder(0, 0, 0, 10));
        leftLinksPanel.setBackground(new Color(171, 80, 101));  //заблокировал цвет левой панели, кому нужно разблокируйте в момент работы, потом опять закоментируйте строку

        leftLinksPanel.setLayout(new BoxLayout(leftLinksPanel, BoxLayout.Y_AXIS));

        leftLinksPanel.add(Box.createRigidArea(new Dimension(20, 20)));
        leftLinksPanel.add(generalStatistics);
        leftLinksPanel.add(Box.createRigidArea(new Dimension(20, 20)));
        leftLinksPanel.add(dailyStatistics);
        leftLinksPanel.add(Box.createRigidArea(new Dimension(20, 20)));
        leftLinksPanel.add(references);
        leftLinksPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        leftLinksPanel.add(persons);
        leftLinksPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        leftLinksPanel.add(keywords);
        leftLinksPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        leftLinksPanel.add(sites);
        leftLinksPanel.setVisible(true);
    }

}
