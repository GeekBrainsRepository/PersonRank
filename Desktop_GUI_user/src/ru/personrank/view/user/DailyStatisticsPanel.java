package ru.personrank.view.user;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import ru.personrank.view.Window;

import javax.swing.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import ru.personrank.data.*;

public class DailyStatisticsPanel extends Window {

    private static DailyStatisticOnSiteRepository dailyStatisticRepository = DailyStatisticOnSiteRepository.getInstance();
    private static String[] columnNames = new String[]{
            "Дата",
            "Количество новых страниц",
    };
    private static String[][] data;

    private static DateFormat format = new SimpleDateFormat("DD.MM.YYYY");

    private static JLabel saitLabel = new JLabel();
    private static JLabel personLabel = new JLabel();
    private static JComboBox comboSite;
    private static ComboSiteModel comboSiteModel;
    private static JComboBox comboPerson;
    private static ComboPersonModel comboPersonModel;
    private static JLabel labelPeriod = new JLabel();
    private static JFormattedTextField formattedTextFieldData1 = new JFormattedTextField(format);
    private static JLabel labelPo = new JLabel();
    private static JFormattedTextField formattedTextFieldData2 = new JFormattedTextField(format);
    private static JButton buttonSend; 
    private static JTable table;
    private static StatisticTabelModel statisticTableModel;
    private static JScrollPane scrollPane;


    static public void dailyStatisticsPanel() {
        
        comboSiteModel = new ComboSiteModel();       
        comboSite = new JComboBox(comboSiteModel);
        comboSite.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == ItemEvent.SELECTED) {
                    comboPersonModel.setDataSource();
                }
            }
        });        
        comboPersonModel = new ComboPersonModel();
        comboPerson = new JComboBox(comboPersonModel);
        
        formattedTextFieldData1.setValue(new GregorianCalendar(2017,Calendar.JANUARY,1).getTime());
        formattedTextFieldData2.setValue(new GregorianCalendar(2017,Calendar.JANUARY,30).getTime());
        
        buttonSend = new JButton();
        buttonSend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                statisticTableModel.setDataSource((Date)formattedTextFieldData1.getValue(),(Date)formattedTextFieldData2.getValue());
            }
        });
        
        // Заполнение таблицы данными
        fillTable();

        //Создание модели таблицы
        statisticTableModel = new StatisticTabelModel();
        table = new JTable(statisticTableModel);
        scrollPane = new JScrollPane(table);

        panel();
    }

    private static void panel() {

        saitLabel.setText("\u0421\u0430\u0438\u0442:");
        personLabel.setText("\u041b\u0438\u0447\u043d\u043e\u0441\u0442\u044c:");
        labelPeriod.setText("\u041fe\u0440\u0438\u043e\u0434 \u0441:");
        labelPo.setText("\u043f\u043e");
        buttonSend.setText("\u041f\u0440\u0438\u043c\u0435\u043d\u0438\u0442\u044c");

        GroupLayout layout = new GroupLayout(dailyStatisticsPanel);
        dailyStatisticsPanel.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup()
                        .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                        .addComponent(scrollPane, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 378, Short.MAX_VALUE)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup()
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addComponent(saitLabel, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
                                                                .addGap(18, 18, 18)
                                                                .addComponent(comboSite, 0, 203, Short.MAX_VALUE))
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addComponent(personLabel, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
                                                                .addGap(18, 18, 18)
                                                                .addComponent(comboPerson, 0, 203, Short.MAX_VALUE))
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addComponent(labelPeriod, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
                                                                .addGap(18, 18, 18)
                                                                .addComponent(formattedTextFieldData1, GroupLayout.PREFERRED_SIZE, 75, GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                                                .addComponent(labelPo)
                                                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                                                .addComponent(formattedTextFieldData2, GroupLayout.PREFERRED_SIZE, 75, GroupLayout.PREFERRED_SIZE)))
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(buttonSend)))
                                .addGap(27, 27, 27))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup()
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup()
                                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                .addComponent(comboSite, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                                                .addComponent(saitLabel))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(32, 32, 32)
                                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                        .addComponent(personLabel)
                                                        .addComponent(comboPerson, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(labelPeriod)
                                        .addComponent(formattedTextFieldData1, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(buttonSend, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(labelPo)
                                        .addComponent(formattedTextFieldData2, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 190, Short.MAX_VALUE)
                                .addGap(13, 13, 13))
        );
    }

    private static void fillTable() {

        data = new String[][]{
                {"10.01.2017", "12",},
                {"15.01.2017", "25",},
                {"11.01.2017", "30",},
                {"12.01.2017", "40",},
                {"13.01.2017", "1",},
                {"10.01.2017", "12",},
                {"15.01.2017", "25",},
                {"11.01.2017", "30",},
                {"12.01.2017", "40",},
                {"13.01.2017", "1",},
                {"10.01.2017", "12",},
                {"15.01.2017", "25",},
                {"11.01.2017", "30",},
                {"12.01.2017", "40",},
                {"13.01.2017", "1",},
                {"10.01.2017", "12",},
                {"15.01.2017", "25",},
                {"11.01.2017", "30",},
                {"12.01.2017", "40",},
                {"13.01.2017", "1",},
                {"10.01.2017", "12",},
                {"15.01.2017", "25",},
                {"11.01.2017", "30",},
                {"12.01.2017", "40",},
                {"13.01.2017", "1",},
        };
    }
    
    private static class ComboSiteModel extends DefaultComboBoxModel {

        ComboSiteModel() {
            removeAllElements();
            List<DailyStatisticOnSite> list = dailyStatisticRepository.
                    query(DailyStatisticSpecification.getAllStatisticSite());
            for (DailyStatisticOnSite dsos : list) {
                addElement(dsos.getSiteName());
            }            
        }
    }

    private static class ComboPersonModel extends DefaultComboBoxModel {

        public ComboPersonModel() {
            setDataSource();
        }

        public void setDataSource() {
            removeAllElements();
            List<DailyStatisticOnSite> list = dailyStatisticRepository.
                    query(DailyStatisticSpecification.findStatisticSite(
                            comboSite.getSelectedItem().toString()));
            for (DailyStatisticOnSite.Person person : list.get(0).getPersons()) {
                addElement(person.getName());
            }            
        }
    }
    
    static class StatisticTabelModel extends AbstractTableModel {

        private ArrayList columnNames;
        private ArrayList data;

        public StatisticTabelModel() {
            columnNames = new ArrayList();
            columnNames.add("Дата");
            columnNames.add("Колличество страниц");
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
            switch (column) {
                case (1) :
                    return Calendar.class;
                case (2) :
                    return Integer.class;
                default : return String.class;
            }
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
        public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        }

        public void setDataSource(Date startDate, Date stopDate) {
            data.clear();
            List<DailyStatisticOnSite> site = dailyStatisticRepository.query(
                    DailyStatisticSpecification.findStatisticSite(
                            comboSite.getSelectedItem().toString()));
            DailyStatisticOnSite.Person person = null;
            for (DailyStatisticOnSite.Person persons : site.get(0).getPersons()) {
                if(persons.getName().equals(comboPerson.getSelectedItem().toString())) {
                    person =  persons;
                }
            }
            ArrayList row = null;
            for(int i = 0; i < person.getScanDate().size(); i++) {
                Date date = person.getScanDate().get(i).getTime();
                if(date.compareTo(startDate) >= 0 && date.compareTo(stopDate) <= 0) {
                    row = new ArrayList();
                    row.add(person.getScanDate().get(i).getTime());
                    row.add(person.getNewPages().get(i));
                    data.add(row);
                }
            }
            fireTableStructureChanged();
        }

    }
}
