package PersonRank;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

class GeneralStatisticsPanel extends Window{
    private static String[] columnNames;
    private static String[][] data;

    static void generalStatisticsPanel(){
        setTable();
        panel();
    }
    private static void panel(){
        generalStatisticsPanel.setLayout(null);


        JLabel site = new JLabel();
        site.setText("Сайт:");
        Font font = new Font("Arial", Font.PLAIN, 12);
        site.setFont(font);

        String[] nameSites = {};
        JComboBox nameSitesCombo = new JComboBox(nameSites);
        nameSitesCombo.setEditable(false);

        JButton apply = new JButton();
        apply.setText("Применить");
        font = new Font("Tahoma", Font.PLAIN, 11);
        apply.setFont(font);


        /*
        *   здесь создается модель таблицы, где все строки и столбцы заблокированны для редактирования
        */

        JTable generalTable;
        DefaultTableModel myTableModel = new DefaultTableModel(data, columnNames);
        generalTable = new JTable(myTableModel){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false; //Disallow the editing of any cell
            }
        };

        JScrollPane scrollForTable = new JScrollPane(generalTable);

        Dimension size = site.getPreferredSize();

        generalStatisticsPanel.add(site);
        generalStatisticsPanel.add(nameSitesCombo);
        generalStatisticsPanel.add(apply);
        generalStatisticsPanel.add(scrollForTable);


        site.setBounds(7,12,size.width,size.height);    //добавил оригинальные размеры для jlabel
        nameSitesCombo.setBounds(75,5, 195,30);
        apply.setBounds(280, 5, 90, 30);
        scrollForTable.setBounds(5,40, 369,346);


        generalStatisticsPanel.setVisible(true);
    }

    private static void setTable(){
        columnNames = new String[]{
                "Имя",
                "Количество упоминаний",
        };
        data = new String[][]{
                {"Путин", "200",},
                {"Медведев", "600",},
                {"Навальный", "700",},
                {"Путин", "200",},
                {"Медведев", "600",},
                {"Навальный", "700",},
                {"Путин", "200",},
                {"Медведев", "600",},
                {"Навальный", "700",},
                {"Путин", "200",},
                {"Медведев", "600",},
                {"Навальный", "700",},
                {"Путин", "200",},
                {"Медведев", "600",},
                {"Навальный", "700",},
                {"Путин", "200",},
                {"Медведев", "600",},
                {"Навальный", "700",},
                {"Путин", "200",},
                {"Медведев", "600",},
                {"Навальный", "700",},
                {"Путин", "200",},
                {"Медведев", "600",},
                {"Навальный", "700",},
                {"Путин", "200",},
                {"Медведев", "600",},
                {"Навальный", "700",},
        };
    }
}
