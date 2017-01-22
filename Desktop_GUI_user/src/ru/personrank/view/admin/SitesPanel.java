package ru.personrank.view.admin;

import ru.personrank.view.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;


public class SitesPanel extends ru.personrank.view.Window {

    private static String[] columnNames;
    private static String[][] data;

    static public void sitesPanel(){
        filltable();
        panel();
    }

    private static void panel(){
        sitesPanel.setLayout(null);


        JLabel sitesLabel = new JLabel();
        sitesLabel.setText("Справочник: \"Сайты\"");
        sitesLabel.setFont(new Font("Arial", Font.BOLD, 12));
        Dimension size = sitesLabel.getPreferredSize();

        JButton addSite = new JButton();
        addSite.setText("Добавить");
        addSite.setFont(new Font("Tahoma", Font.PLAIN, 11));

        JButton editSite = new JButton();
        editSite.setText("Редактировать");
        editSite.setFont(new Font("Tahoma", Font.PLAIN, 11));

        JButton deleteSite = new JButton();
        deleteSite.setText("Удалить");
        deleteSite.setFont(new Font("Tahoma", Font.PLAIN, 11));

        JTable siteTable;
        DefaultTableModel myTableModel = new DefaultTableModel(data, columnNames);

        siteTable = new JTable(myTableModel) {
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;
            }
        };

        JScrollPane scrollPane = new JScrollPane(siteTable);




        sitesLabel.setBounds(27, 12, size.width, size.height);
        scrollPane.setBounds(25, 40, 369, 300);
        addSite.setBounds(100, 355, 90, 30);
        editSite.setBounds(200, 355, 110, 30);
        deleteSite.setBounds(320, 355, 90, 30);

        sitesPanel.add(sitesLabel);
        sitesPanel.add(scrollPane);
        sitesPanel.add(addSite);
        sitesPanel.add(editSite);
        sitesPanel.add(deleteSite);
    }

    private static void filltable(){

        columnNames = new String[]{
                "Наименование"
        };

        data = new String[][]{
                {"Lenta.ru"},
                {"komersant.ru"},
                {"Lenta.ru"},
                {"komersant.ru"},
                {"Lenta.ru"},
                {"komersant.ru"},
                {"Lenta.ru"},
                {"komersant.ru"},
                {"Lenta.ru"},
                {"komersant.ru"},
                {"Lenta.ru"},
                {"komersant.ru"},
                {"Lenta.ru"},
                {"komersant.ru"},
                {"Lenta.ru"},
                {"komersant.ru"},
                {"Lenta.ru"},
                {"komersant.ru"},
                {"Lenta.ru"},
                {"komersant.ru"},
        };
    }
}
