package ru.personrank.view.admin;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class KeywordsPanel extends JPanel {

    private static String[] columnNames;
    private static String[][] data;

    public KeywordsPanel() {
        filltable();
        panel();
    }

    private void panel() {
        setLayout(null);
        setOpaque(false);

        JLabel keywordsLabel = new JLabel();
        keywordsLabel.setText("Справочник: \"Ключевые слова\"");
        keywordsLabel.setFont(new Font("Arial", Font.BOLD, 12));
        Dimension size = keywordsLabel.getPreferredSize();

        JLabel personLabel = new JLabel();
        personLabel.setText("Персона: ");
        personLabel.setFont(new Font("Arial", Font.BOLD, 12));
        Dimension sizePersonLabel = personLabel.getPreferredSize();

        JButton addKey = new JButton();
        addKey.setText("Добавить");
        addKey.setFont(new Font("Tahoma", Font.PLAIN, 11));

        JButton editKey = new JButton();
        editKey.setText("Редактировать");
        editKey.setFont(new Font("Tahoma", Font.PLAIN, 11));

        JButton deleteKey = new JButton();
        deleteKey.setText("Удалить");
        deleteKey.setFont(new Font("Tahoma", Font.PLAIN, 11));

        JTable keyTable;
        DefaultTableModel myTableModel = new DefaultTableModel(data, columnNames);

        keyTable = new JTable(myTableModel) {
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;
            }
        };

        JScrollPane scrollPane = new JScrollPane(keyTable);

        JComboBox namePersonsCombo = new JComboBox();

        keywordsLabel.setBounds(27, 12, size.width, size.height);
        personLabel.setBounds(27, 40, sizePersonLabel.width, sizePersonLabel.height);
        namePersonsCombo.setBounds(90, 33, 195, 30);
        scrollPane.setBounds(25, 80, 369, 250);
        addKey.setBounds(100, 355, 90, 30);
        editKey.setBounds(200, 355, 110, 30);
        deleteKey.setBounds(320, 355, 90, 30);

        add(keywordsLabel);
        add(personLabel);
        add(namePersonsCombo);
        add(scrollPane);
        add(addKey);
        add(editKey);
        add(deleteKey);
    }

    private static void filltable() {

        columnNames = new String[]{
            "Наименование"
        };

        data = new String[][]{
            {"Медведев"},
            {"Медведевом"},
            {"Медведевым"},
            {"Медведеву"},};
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
