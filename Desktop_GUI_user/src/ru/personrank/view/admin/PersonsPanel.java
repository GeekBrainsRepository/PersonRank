package ru.personrank.view.admin;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PersonsPanel extends JPanel {

    private static String[] columnNames;
    private static String[][] data;

    public PersonsPanel() {
        filltable();
        panel();
    }

    private void panel() {
        setLayout(null);
        setOpaque(false);

        JLabel personsLabel = new JLabel();
        personsLabel.setText("Справочник: \"Личности\"");
        personsLabel.setFont(new Font("Arial", Font.BOLD, 12));
        Dimension size = personsLabel.getPreferredSize();

        JButton addPerson = new JButton();
        addPerson.setText("Добавить");
        addPerson.setFont(new Font("Tahoma", Font.PLAIN, 11));

        JButton editPerson = new JButton();
        editPerson.setText("Редактировать");
        editPerson.setFont(new Font("Tahoma", Font.PLAIN, 11));

        JButton deletePerson = new JButton();
        deletePerson.setText("Удалить");
        deletePerson.setFont(new Font("Tahoma", Font.PLAIN, 11));

        JTable personTable;
        DefaultTableModel myTableModel = new DefaultTableModel(data, columnNames);

        personTable = new JTable(myTableModel) {
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;
            }
        };

        JScrollPane scrollPane = new JScrollPane(personTable);

        personsLabel.setBounds(27, 12, size.width, size.height);
        scrollPane.setBounds(25, 40, 369, 300);
        addPerson.setBounds(100, 355, 90, 30);
        editPerson.setBounds(200, 355, 110, 30);
        deletePerson.setBounds(320, 355, 90, 30);

        add(personsLabel);
        add(scrollPane);
        add(addPerson);
        add(editPerson);
        add(deletePerson);
    }

    private static void filltable() {

        columnNames = new String[]{
                "Наименование"
        };

        data = new String[][]{
                {"Медведев"},
                {"Путин"},
                {"Медведев"},
                {"Путин"},
                {"Медведев"},
                {"Путин"},
                {"Медведев"},
                {"Путин"},
                {"Медведев"},
                {"Путин"},
                {"Медведев"},
                {"Путин"},
                {"Медведев"},
                {"Путин"},
                {"Медведев"},
                {"Путин"},
                {"Медведев"},
                {"Путин"},
                {"Медведев"},
                {"Путин"},
                {"Медведев"},
                {"Путин"},
                {"Медведев"},
                {"Путин"},
                {"Медведев"},
                {"Путин"},};
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
