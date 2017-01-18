package PersonRank;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class Window extends JFrame {

    private JPanel leftLinksPanel;
    /*
    *  описание каркаса окна,
    */

    protected Window() {
        setSize(650, 400);
        setLocationRelativeTo(null);
        setTitle("PersonRank");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        initComponents();
    }

    /*
    * инициализация компонентов
    */

    private void initComponents() {
        createLeftLinksPanel();
    }

    private void createLeftLinksPanel() {
        String whiteSpace = "          ";
        JLabel generalStatistics = new JLabel("Общая статистика");
        generalStatistics.setFont(new Font("Arial", Font.PLAIN, 21));

        JLabel dailyStatistics = new JLabel("Ежедневная статистика");
        dailyStatistics.setFont(new Font("Arial", Font.PLAIN, 21));

        JLabel references = new JLabel("Справочники");
        references.setFont(new Font("Arial", Font.PLAIN, 21));

        JLabel persons = new JLabel(whiteSpace + "Личности");
        persons.setFont(new Font("Arial", Font.PLAIN, 16));

        JLabel keywords = new JLabel(whiteSpace + "Ключевые слова");
        keywords.setFont(new Font("Arial", Font.PLAIN, 16));

        JLabel sites = new JLabel(whiteSpace + "Сайты");
        sites.setFont(new Font("Arial", Font.PLAIN, 16));

        leftLinksPanel = new JPanel();
        leftLinksPanel.setBorder(new EmptyBorder(0, 0, 0, 10));
        leftLinksPanel.setBackground(new Color(171, 80, 101));
        this.add(leftLinksPanel, BorderLayout.LINE_START);
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