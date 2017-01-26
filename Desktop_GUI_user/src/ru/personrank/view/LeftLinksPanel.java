package ru.personrank.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class LeftLinksPanel extends JPanel {

    private static final Font MENU_FONT = new Font("Arial", Font.PLAIN, 17);
    private static final Font SUBMENU_FONT = new Font("Arial", Font.PLAIN, 14);
    private static final Color MENU_COLOR = Color.BLUE;
    private static final Color PANEL_COLOR = Color.WHITE;

    private JLabel generalStatistics;
    private JLabel dailyStatistics;
    private JLabel persons;
    private JLabel keywords;
    private JLabel sites;


    public LeftLinksPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(new EmptyBorder(10, 10, 10, 10));
        setOpaque(false);

//        setBackground( new Color(83, 250, 182, 0 ) );
//        setBackground(PANEL_COLOR);

        Dimension indent = new Dimension(50, 0);

        Box generalStatisticsBox = Box.createHorizontalBox();
        generalStatisticsBox.setAlignmentX(Box.LEFT_ALIGNMENT);
        generalStatisticsBox.setBorder(new EmptyBorder(10, 0, 20, 0));
        generalStatistics = new JLabel("Общая статистика");
        generalStatistics.setFont(MENU_FONT);
        generalStatistics.setForeground(MENU_COLOR);
        generalStatisticsBox.add(generalStatistics);


        Box dailyStatisticsBox = Box.createHorizontalBox();
        dailyStatisticsBox.setAlignmentX(Box.LEFT_ALIGNMENT);
        dailyStatisticsBox.setBorder(new EmptyBorder(0, 0, 20, 0));
        dailyStatistics = new JLabel("Ежедневная статистика");
        dailyStatistics.setFont(MENU_FONT);
        dailyStatistics.setForeground(MENU_COLOR);
        dailyStatisticsBox.add(dailyStatistics);


        Box referencesBox = Box.createHorizontalBox();
        referencesBox.setAlignmentX(Box.LEFT_ALIGNMENT);
        referencesBox.setBorder(new EmptyBorder(0, 0, 10, 0));
        JLabel references = new JLabel("Справочники:");
        references.setFont(MENU_FONT);
        references.setForeground(MENU_COLOR);
        referencesBox.add(references);


        Box personsBox = Box.createHorizontalBox();
        personsBox.setAlignmentX(Box.LEFT_ALIGNMENT);
        personsBox.setBorder(new EmptyBorder(0, 0, 10, 0));
        persons = new JLabel("Личности");
        persons.setFont(SUBMENU_FONT);
        persons.setForeground(MENU_COLOR);
        personsBox.add(Box.createRigidArea(indent));
        personsBox.add(persons);


        Box keywordsBox = Box.createHorizontalBox();
        keywordsBox.setAlignmentX(Box.LEFT_ALIGNMENT);
        keywordsBox.setBorder(new EmptyBorder(0, 0, 10, 0));
        keywords = new JLabel("Ключевые слова");
        keywords.setFont(SUBMENU_FONT);
        keywords.setForeground(MENU_COLOR);
        keywordsBox.add(Box.createRigidArea(indent));
        keywordsBox.add(keywords);


        Box sitesBox = Box.createHorizontalBox();
        sitesBox.setAlignmentX(Box.LEFT_ALIGNMENT);
        sitesBox.setBorder(new EmptyBorder(0, 0, 10, 0));
        sites = new JLabel("Сайты");
        sites.setFont(SUBMENU_FONT);
        sites.setForeground(MENU_COLOR);
        sitesBox.add(Box.createRigidArea(indent));
        sitesBox.add(sites);



        add(generalStatisticsBox);
        add(dailyStatisticsBox);
        add(referencesBox);
        add(personsBox);
        add(keywordsBox);
        add(sitesBox);
    }

    public JLabel[] getLabelLeftMenu() {
        return new JLabel[]{
                generalStatistics,
                dailyStatistics,
                persons,
                keywords,
                sites
        };
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setComposite(AlphaComposite.SrcOver.derive(0.25f));// прозрачность редактировать здесь

        g2d.setColor(getBackground());
        g2d.fillRect(0, 0, getWidth(), getHeight());
        g2d.dispose();
    }
}
