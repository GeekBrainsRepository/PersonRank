package ru.personrank.view.user;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
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
import java.io.File;
import java.io.IOException;
import javax.swing.table.AbstractTableModel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;

import ru.personrank.data.dailystatistic.DailyStatisticOnSite;
import ru.personrank.data.dailystatistic.DailyStatisticOnSiteRepository;
import ru.personrank.data.dailystatistic.DailyStatisticSpecification;

public class DailyStatisticsPanel extends JPanel {

    private DailyStatisticOnSiteRepository dailyStatisticRepository;
    private JLabel saitLabel;
    private JLabel personLabel;
    private JComboBox comboSite;
    private ComboSiteModel comboSiteModel;
    private JComboBox comboPerson;
    private ComboPersonModel comboPersonModel;
    private JLabel labelPeriod;
    private JXDatePicker formattedTextFieldData1;
    private JLabel labelPo;
    private JXDatePicker formattedTextFieldData2;
    private JButton buttonSend; 
    private JTable dailyTable;
    private StatisticTabelModel statisticTableModel;
    private JScrollPane scrollPane;
    private JTabbedPane contentTabbedPane;
    private JFreeChart lineChart;
    private Font font;
    
    public DailyStatisticsPanel () {        
        try{
            font = Font.createFont(Font.TRUETYPE_FONT, new File(System.getProperty("user.dir") + "/fonts/arial.ttf")).deriveFont(Font.PLAIN, 11);
        }catch (IOException ex){
            System.err.println(ex);
        }catch (FontFormatException ex){
            System.err.println(ex);
        }


        setOpaque(false);
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
        contentTabbedPane = new JTabbedPane(JTabbedPane.BOTTOM,
                JTabbedPane.SCROLL_TAB_LAYOUT);
        contentTabbedPane.setBorder(null);
        statisticTableModel = new StatisticTabelModel();
        statisticTableModel.setDataSource(formattedTextFieldData1.getDate(), formattedTextFieldData2.getDate());
        dailyTable = new JTable(statisticTableModel); 
        dailyTable.setRowHeight(30);
        scrollPane = new JScrollPane(dailyTable);
        scrollPane.setBorder(BorderFactory.createMatteBorder(2, 2, 0, 2, Color.BLACK));
        contentTabbedPane.addTab("Таблица", scrollPane);
        lineChart = ChartFactory.createTimeSeriesChart(
                "График изменения популярности", 
                "Дата", 
                "к-во страниц", 
                createDataset(), false, true, true);
        ChartPanel chartPanel = new ChartPanel(lineChart);
        chartPanel.setBorder(BorderFactory.createMatteBorder(2, 2, 0, 2, Color.BLACK));
        contentTabbedPane.addTab("Граффик", chartPanel);
        setOpaque(false);
        contentPositioning();
        
    }      
    
    private XYDataset createDataset() {
        TimeSeries series = new TimeSeries("");
        List<DailyStatisticOnSite> site = dailyStatisticRepository.query(
                    DailyStatisticSpecification.findStatisticSite(
                            comboSite.getSelectedItem().toString()));
            DailyStatisticOnSite.Person person = null;
            for (DailyStatisticOnSite.Person persons : site.get(0).getPersons()) {
                if(persons.getName().equals(comboPerson.getSelectedItem().toString())) {
                    person =  persons;
                }
            }        
            for(int i = 0; i < person.getScanDate().size(); i++) {
                Date date = person.getScanDate().get(i).getTime();
                if(date.compareTo(formattedTextFieldData1.getDate()) >= 0 && date.compareTo(formattedTextFieldData2.getDate()) <= 0) {
                    series.add(new Day(person.getScanDate().get(i).getTime()), 
                            person.getNewPages().get(i));
                }
            }

        return new TimeSeriesCollection(series);
    }
    
    private void contentPositioning() {

        saitLabel.setText("Сайт:");
        saitLabel.setFont(font);
        personLabel.setText("Личность:");
        personLabel.setFont(font);
        labelPeriod.setText("Пeриод с:");
        labelPeriod.setFont(font);
        labelPo.setText("по:");
        labelPo.setFont(font);
        buttonSend.setText("Применить");
        buttonSend.setFont(font);

        GroupLayout layout = new GroupLayout(this);
        setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup()
                        .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                        .addComponent(contentTabbedPane, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 378, Short.MAX_VALUE)
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
                                .addComponent(contentTabbedPane, GroupLayout.DEFAULT_SIZE, 190, Short.MAX_VALUE)
                                .addGap(6, 6, 6))
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
            if(data.size() < 14) {
                for(int i = 0; i < 14 - data.size(); i++) {
                    row = new ArrayList();
                    row.add("");
                    row.add("");
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
            lineChart.getXYPlot().setDataset(createDataset());
        }
        
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
