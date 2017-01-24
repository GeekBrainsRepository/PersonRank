package ru.personrank.view.user;
import org.jdesktop.swingx.JXDatePicker;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.swing.table.AbstractTableModel;

import ru.personrank.data.dailystatistic.DailyStatisticOnSite;
import ru.personrank.data.dailystatistic.DailyStatisticOnSiteRepository;
import ru.personrank.data.dailystatistic.DailyStatisticSpecification;

public class DailyStatisticsPanel extends JPanel {

    private DailyStatisticOnSiteRepository dailyStatisticRepository;
    private  JLabel saitLabel;
    private  JLabel personLabel;
    private  JComboBox comboSite;
    private  ComboSiteModel comboSiteModel;
    private  JComboBox comboPerson;
    private  ComboPersonModel comboPersonModel;
    private  JLabel labelPeriod;
    private  JXDatePicker formattedTextFieldData1;
    private  JLabel labelPo;
    private  JXDatePicker formattedTextFieldData2;
    private  JButton buttonSend; 
    private  JTable dailyTable;
    private  StatisticTabelModel statisticTableModel;
    private  JScrollPane scrollPane;

    public DailyStatisticsPanel () {        
        dailyStatisticRepository = DailyStatisticOnSiteRepository.getInstance();
        saitLabel = new JLabel();
        personLabel = new JLabel();
        comboSiteModel = new ComboSiteModel();       
        comboSite = new JComboBox(comboSiteModel);
        comboSite.addItemListener(new ComboSiteItemListener());        
        comboPersonModel = new ComboPersonModel();
        comboPerson = new JComboBox(comboPersonModel);
        labelPeriod = new JLabel();
        labelPo = new JLabel();
        //DateFormat format = new SimpleDateFormat("DD.MM.YYYY");
        GregorianCalendar initDate = (GregorianCalendar) GregorianCalendar.getInstance();
        formattedTextFieldData1 = new JXDatePicker(new GregorianCalendar(
                                        initDate.get(Calendar.YEAR),
                                        initDate.get(Calendar.MONTH),
                                        initDate.getMinimum(Calendar.DAY_OF_MONTH)).getTime());
        formattedTextFieldData2 = new JXDatePicker(new GregorianCalendar(
                                        initDate.get(Calendar.YEAR),
                                        initDate.get(Calendar.MONTH),
                                        initDate.getMaximum(Calendar.DAY_OF_MONTH)).getTime());
        //formattedTextFieldData1.setFormats(formats); // При включенном форматировани некоректно отображается дата
        //formattedTextFieldData2.setFormats(format);  // Видимо setFormat работает не корректно
        buttonSend = new JButton();
        buttonSend.addActionListener(new ButtonSendListener());
        statisticTableModel = new StatisticTabelModel();
        dailyTable = new JTable(statisticTableModel);
        dailyTable.setRowHeight(30);
        scrollPane = new JScrollPane(dailyTable);
        contentPositioning();
    }      
    
    private void contentPositioning() {

        saitLabel.setText("\u0421\u0430\u0438\u0442:");
        personLabel.setText("\u041b\u0438\u0447\u043d\u043e\u0441\u0442\u044c:");
        labelPeriod.setText("\u041fe\u0440\u0438\u043e\u0434 \u0441:");
        labelPo.setText("\u043f\u043e");
        buttonSend.setText("\u041f\u0440\u0438\u043c\u0435\u043d\u0438\u0442\u044c");

        GroupLayout layout = new GroupLayout(this);
        setLayout(layout);
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
                                                                .addComponent(formattedTextFieldData1, GroupLayout.PREFERRED_SIZE, 115, GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                                                .addComponent(labelPo)
                                                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                                                .addComponent(formattedTextFieldData2, GroupLayout.PREFERRED_SIZE, 115, GroupLayout.PREFERRED_SIZE)))
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
    
    private class ComboSiteModel extends DefaultComboBoxModel {

        ComboSiteModel() {
            removeAllElements();
            List<DailyStatisticOnSite> list = dailyStatisticRepository.
                    query(DailyStatisticSpecification.getAllStatisticSite());
            for (DailyStatisticOnSite dsos : list) {
                addElement(dsos.getSiteName());
            }            
        }
    }

    private class ComboPersonModel extends DefaultComboBoxModel {

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
    
    private class StatisticTabelModel extends AbstractTableModel {

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
                    return String.class;
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
            SimpleDateFormat dateFormat = new SimpleDateFormat("DD.MM.YYYY");
            for(int i = 0; i < person.getScanDate().size(); i++) {
                Date date = person.getScanDate().get(i).getTime();
                if(date.compareTo(startDate) >= 0 && date.compareTo(stopDate) <= 0) {
                    row = new ArrayList();
                    row.add(dateFormat.format(person.getScanDate().get(i).getTime()));
                    row.add(person.getNewPages().get(i));
                    data.add(row);
                }
            }
            fireTableStructureChanged();
        }

    }
    
    private class ComboSiteItemListener implements ItemListener {

        @Override
        public void itemStateChanged(ItemEvent e) {
            if(e.getStateChange() == ItemEvent.SELECTED) {
                    comboPersonModel.setDataSource();
                }
        }
        
    }
    
    private class ButtonSendListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            statisticTableModel.setDataSource((Date)formattedTextFieldData1.getDate(),(Date)formattedTextFieldData2.getDate());
        }
        
    }
            
}
