package PersonRank;

import PersonRank.data.GeneralStatisticOnSite;
import PersonRank.data.GeneralStatisticOnSiteRepository;
import PersonRank.data.GeneralStatisticSpecification;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

class GeneralStatisticsPanelClass extends Window {

    private static String[] columnNames;
    private static String[][] data;

    static void generalStatisticsPanel() {
        setTable();
        panel();
    }

    private static void panel() {
        generalStatisticsPanel.setLayout(null);

        JLabel site = new JLabel();
        site.setText("Сайт:");
        Font font = new Font("Arial", Font.PLAIN, 12);
        site.setFont(font);

        final JComboBox namesSitesComboBox = new JComboBox(new NamesSitesComboBoxModel());
        namesSitesComboBox.setEditable(false);

        JButton apply = new JButton();
        apply.setText("Применить");
        font = new Font("Tahoma", Font.PLAIN, 11);
        apply.setFont(font);


        /*
        *   здесь создается модель таблицы, где все строки и столбцы заблокированны для редактирования
         */
        JTable generalTable;
        final GeneralStaticTabelModel tableModel = new GeneralStaticTabelModel();     
        generalTable = new JTable(tableModel);

         apply.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tableModel.setDataSource(namesSitesComboBox.getSelectedItem().toString());
            }
        });
        
        JScrollPane scrollForTable = new JScrollPane(generalTable);

        Dimension size = site.getPreferredSize();

        generalStatisticsPanel.add(site);
        generalStatisticsPanel.add(namesSitesComboBox);
        generalStatisticsPanel.add(apply);
        generalStatisticsPanel.add(scrollForTable);

        site.setBounds(27, 12, size.width, size.height);    //добавил оригинальные размеры для jlabel
        namesSitesComboBox.setBounds(95, 5, 195, 30);
        apply.setBounds(300, 5, 90, 30);
        scrollForTable.setBounds(25, 40, 369, 346);

    }

    private static void setTable() {
        columnNames = new String[]{
            "Имя",
            "Количество упоминаний",};
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
            {"Навальный", "700",},};
    }

    static class NamesSitesComboBoxModel extends DefaultComboBoxModel {

        NamesSitesComboBoxModel() {
            List<GeneralStatisticOnSite> list = GeneralStatisticOnSiteRepository.
                    getInstance().
                    query(GeneralStatisticSpecification.getAllStatisticSite());
            for (GeneralStatisticOnSite gsos : list) {
                addElement(gsos.getSiteName());
            }
        }
    }

    static class GeneralStaticTabelModel extends AbstractTableModel {

        private ArrayList columnNames; 
        private ArrayList data; 

        public GeneralStaticTabelModel() {
            columnNames = new ArrayList();
            columnNames.add("Имя");
            columnNames.add("Колличество упоминаний");
            data = new ArrayList();
        }

        @Override
        public int getRowCount() {
            return data.size();
        }

        @Override
        public int getColumnCount() {
            return columnNames.size();
        }

        @Override
        public Class getColumnClass(int column) {
            return String.class;
        }

        @Override
        public String getColumnName(int column) {
            return (String) columnNames.get(column);
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            synchronized (data) {
                return ((ArrayList) data.get(rowIndex)).get(columnIndex);
            }
        }

        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }

        @Override
        public void setValueAt(Object aValue, int rowIndex, int columnIndex) {}
        
        public void setDataSource(String nameSite) {
            data.clear();
            ArrayList row = null; 
            ArrayList<String> personNames = null;
            ArrayList<Integer> personRank = null;
            List<GeneralStatisticOnSite> list = GeneralStatisticOnSiteRepository.
                    getInstance().
                    query(GeneralStatisticSpecification.findStatisticSite(nameSite));
            personNames = (ArrayList<String>) list.get(0).getPersonNames();
            personRank = (ArrayList<Integer>) list.get(0).getAllPersonRanks();            
            for (int i = 0; i < personNames.size(); i++) {
                row = new ArrayList();
                row.add(personNames.get(i));
                row.add(personRank.get(i));
                data.add(row);
            }
            fireTableStructureChanged();
        }

    }
}
