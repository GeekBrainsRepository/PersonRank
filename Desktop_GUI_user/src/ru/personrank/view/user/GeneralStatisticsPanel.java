package ru.personrank.view.user;

import ru.personrank.data.generalstatistic.GeneralStatisticOnSite;
import ru.personrank.data.generalstatistic.GeneralStatisticOnSiteRepository;
import ru.personrank.data.generalstatistic.GeneralStatisticSpecification;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.border.EmptyBorder;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

public class GeneralStatisticsPanel extends JPanel {

    private GeneralStatisticOnSiteRepository statisticRepository;
    private JComboBox namesSitesComboBox;
    private GeneralStaticTabelModel generalTableModel;
    private JFreeChart barChart;
    private Font font;
    public GeneralStatisticsPanel() {       
        try{
            font = Font.createFont(Font.TRUETYPE_FONT, new File(System.getProperty("user.dir") + "/fonts/arial.ttf")).deriveFont(Font.PLAIN, 11);
        }catch (IOException ex){
            System.err.println(ex);
        }catch (FontFormatException ex){
            System.err.println(ex);
        }
        
        statisticRepository = GeneralStatisticOnSiteRepository.getInstance();
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(6, 6, 6, 6));
        setOpaque(false);
        add(createControlBox(), BorderLayout.NORTH);
        add(createContentTabbedPanel(), BorderLayout.CENTER);
    }

    private Box createControlBox() {
        Box box = Box.createHorizontalBox();
        box.setBorder(new EmptyBorder(0, 0, 10, 0));
        box.setPreferredSize(new Dimension(430, 40));

        JLabel labelSite = new JLabel();
        labelSite.setText("Сайт:");
        labelSite.setFont(new Font("Arial", Font.PLAIN, 12));
        box.add(labelSite);
        box.add(Box.createRigidArea(new Dimension(15, 0)));

        namesSitesComboBox = new JComboBox(new NamesSitesComboBoxModel());
        box.add(namesSitesComboBox);
        box.add(Box.createRigidArea(new Dimension(15, 0)));

        JButton buttonSend = new JButton("Применить");
        Dimension bnSendSize = new Dimension(90, 30);
        buttonSend.setMinimumSize(bnSendSize);
        buttonSend.setMaximumSize(bnSendSize);
        buttonSend.setFont(new Font("Tahoma", Font.PLAIN, 11));
        buttonSend.addActionListener(new ButtonSendListener());
        box.add(buttonSend);
        box.add(Box.createHorizontalGlue());

        return box;
    }

    private JTabbedPane createContentTabbedPanel() {
        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.BOTTOM,
                JTabbedPane.SCROLL_TAB_LAYOUT);
        generalTableModel = new GeneralStaticTabelModel();
        generalTableModel.setDataSource(namesSitesComboBox.getSelectedItem().toString());
        JTable generalTable = new JTable(generalTableModel);
        generalTable.setRowHeight(30);
        JScrollPane tabTable = new JScrollPane(generalTable);
        tabTable.setBorder(BorderFactory.createMatteBorder(2, 2, 0, 2, Color.BLACK));
        tabbedPane.addTab("Таблица", tabTable);
        barChart = ChartFactory.createBarChart(
                "Диаграмма популярности персоны",
                null,
                "к-во упоминаний",
                createDatashet(),
                PlotOrientation.VERTICAL, true, true, false);
        ChartPanel chartPanel = new ChartPanel(barChart);
        chartPanel.setBorder(BorderFactory.createMatteBorder(2, 2, 0, 2, Color.BLACK));
        tabbedPane.addTab("Диаграмма", chartPanel);
        return tabbedPane;
    }

    private CategoryDataset createDatashet() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        List<GeneralStatisticOnSite> list = statisticRepository.query(GeneralStatisticSpecification.findStatisticSite(
                namesSitesComboBox.getSelectedItem().toString()));
        List<String> person = list.get(0).getPersonNames();
        List<Integer> rank = list.get(0).getAllPersonRanks();
        for (int i = 0; i < person.size(); i++) {
            dataset.addValue(rank.get(i), person.get(i), "Персоны");
        }
        return dataset;
    }

    private class NamesSitesComboBoxModel extends DefaultComboBoxModel {

        NamesSitesComboBoxModel() {
            List<GeneralStatisticOnSite> list = GeneralStatisticOnSiteRepository.getInstance().
                    query(GeneralStatisticSpecification.getAllStatisticSite());
            for (GeneralStatisticOnSite gsos : list) {
                addElement(gsos.getSiteName());
            }
        }
    }

    private class GeneralStaticTabelModel extends AbstractTableModel {

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
        public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        }

        public void setDataSource(String nameSite) {
            data.clear();
            ArrayList row = null;
            ArrayList<String> personNames = null;
            ArrayList<Integer> personRank = null;
            List<GeneralStatisticOnSite> list = statisticRepository.query(GeneralStatisticSpecification.findStatisticSite(nameSite));
            personNames = (ArrayList<String>) list.get(0).getPersonNames();
            personRank = (ArrayList<Integer>) list.get(0).getAllPersonRanks();
            for (int i = 0; i < personNames.size(); i++) {
                row = new ArrayList();
                row.add(personNames.get(i));
                row.add(personRank.get(i));
                data.add(row);
            }
            if (data.size() < 14) {
                for (int i = 0; i < 14 - data.size(); i++) {
                    row = new ArrayList();
                    row.add("");
                    row.add("");
                    data.add(row);
                }
            }
            fireTableStructureChanged();
        }
    }

    private class ButtonSendListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            generalTableModel.setDataSource(namesSitesComboBox.getSelectedItem().toString());
            barChart.getCategoryPlot().setDataset(createDatashet());
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
