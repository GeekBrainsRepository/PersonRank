package ru.personrank.view.user;

import ru.personrank.data.generalstatistic.GeneralStatisticOnSite;
import ru.personrank.data.generalstatistic.GeneralStatisticOnSiteRepository;
import ru.personrank.data.generalstatistic.GeneralStatisticSpecification;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import ru.personrank.data.UpdatingRepositoryEvent;
import ru.personrank.data.UpdatingRepositoryListener;

public class GeneralStatisticsPanel extends JPanel {

    private static final Color TABLE_GRID_COLOR = Color.LIGHT_GRAY;
    private static final Color TABLE_SELECTION_BACKGROUND = new Color(20, 116, 138, 248);

    private GeneralStatisticOnSiteRepository statisticRepository;
    private JComboBox namesSitesComboBox;
    private NamesSitesComboBoxModel namesSitesComboBoxModel;
    private JLabel dateScanSite;
    private GeneralStaticTabelModel generalTableModel;
    private JFreeChart barChart;

    public GeneralStatisticsPanel() {
        statisticRepository = GeneralStatisticOnSiteRepository.getInstance();
        statisticRepository.addUpdatingRepositoryListener(new UpdatingRepositoryListener() {
            @Override
            public void repositoryUpdated(UpdatingRepositoryEvent event) {
                namesSitesComboBoxModel.setDataSource();
                generalTableModel.setDataSource(namesSitesComboBox.getSelectedItem().toString());
            }
        });
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(6, 6, 6, 6));
        setOpaque(false);
        add(createControlBox(), BorderLayout.NORTH);
        add(createContentPanel(), BorderLayout.CENTER);
    }

    private Box createControlBox() {
        Box box = Box.createHorizontalBox();
        box.setBorder(new EmptyBorder(0, 0, 10, 0));
        box.setPreferredSize(new Dimension(430, 40));

        JLabel labelSite = new JLabel();
        labelSite.setText("Сайт:");
        box.add(labelSite);
        box.add(Box.createRigidArea(new Dimension(15, 0)));

        namesSitesComboBoxModel = new NamesSitesComboBoxModel();
        namesSitesComboBox = new JComboBox(namesSitesComboBoxModel);
        box.add(namesSitesComboBox);
        box.add(Box.createRigidArea(new Dimension(15, 0)));

        JButton buttonSend = new JButton("Применить");
        Dimension bnSendSize = new Dimension(90, 30);
        buttonSend.setMinimumSize(bnSendSize);
        buttonSend.setMaximumSize(bnSendSize);
        buttonSend.addActionListener(new ButtonSendListener());
        box.add(buttonSend);
        box.add(Box.createHorizontalGlue());

        return box;
    }

    private JPanel createContentPanel() {
        dateScanSite = new JLabel("");
        dateScanSite.setHorizontalTextPosition(JLabel.RIGHT);
        dateScanSite.setHorizontalAlignment(JLabel.RIGHT);
        dateScanSite.setFont(dateScanSite.getFont().deriveFont(Font.ITALIC));
        if (namesSitesComboBox.getSelectedItem() != null) {
            List<GeneralStatisticOnSite> list = statisticRepository.
                    query(GeneralStatisticSpecification.findStatisticSite(
                            namesSitesComboBox.getSelectedItem().toString()));
            if (!list.isEmpty()) {
                Date date = list.get(0).getReviewDate().getTime();
                SimpleDateFormat formater = new SimpleDateFormat("Актуально на: (dd.MM.yyyy)");
                dateScanSite.setText(formater.format(date));
            }
        }
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.add(dateScanSite, BorderLayout.NORTH);
        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.BOTTOM,
                JTabbedPane.SCROLL_TAB_LAYOUT);
        generalTableModel = new GeneralStaticTabelModel();
        if (namesSitesComboBox.getSelectedItem() != null) {
            generalTableModel.setDataSource(namesSitesComboBox.getSelectedItem().toString());
        }
        JTable generalTable = new JTable(generalTableModel);
        generalTable.setShowGrid(true);
        generalTable.setShowHorizontalLines(true);
        generalTable.setShowVerticalLines(true);
        generalTable.setGridColor(TABLE_GRID_COLOR);
        generalTable.setRowHeight(25);
        generalTable.setSelectionBackground(TABLE_SELECTION_BACKGROUND);
        JScrollPane tabTable = new JScrollPane(generalTable);
        tabbedPane.addTab("Таблица", tabTable);
        barChart = ChartFactory.createBarChart(
                null,
                null,
                "к-во упоминаний",
                createDatashet(),
                PlotOrientation.VERTICAL, true, true, false);
        ChartPanel chartPanel = new ChartPanel(barChart);
        chartPanel.setBorder(tabTable.getBorder());
        tabbedPane.addTab("Диаграмма", chartPanel);
        contentPanel.add(tabbedPane, BorderLayout.CENTER);
        return contentPanel;
    }

    private CategoryDataset createDatashet() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        if (namesSitesComboBox.getSelectedItem() != null) {
            List<GeneralStatisticOnSite> list = statisticRepository.query(GeneralStatisticSpecification.findStatisticSite(
                    namesSitesComboBox.getSelectedItem().toString()));
            List<String> person = list.get(0).getPersonNames();
            List<Integer> rank = list.get(0).getAllPersonRanks();
            for (int i = 0; i < person.size(); i++) {
                dataset.addValue(rank.get(i), person.get(i), "Персоны");
            }
        }
        return dataset;
    }

    private class NamesSitesComboBoxModel extends DefaultComboBoxModel {

        NamesSitesComboBoxModel() {
            setDataSource();
        }

        private void setDataSource() {
            List<GeneralStatisticOnSite> list = GeneralStatisticOnSiteRepository.getInstance().
                    query(GeneralStatisticSpecification.getAllStatisticSite());
            removeAllElements();
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
            List<GeneralStatisticOnSite> list = statisticRepository.query(
                    GeneralStatisticSpecification.findStatisticSite(nameSite));
            personNames = (ArrayList<String>) list.get(0).getPersonNames();
            personRank = (ArrayList<Integer>) list.get(0).getAllPersonRanks();
            for (int i = 0; i < personNames.size(); i++) {
                row = new ArrayList();
                row.add(personNames.get(i));
                row.add(personRank.get(i));
                data.add(row);
            }
            if (data.size() < 10) {
                for (int i = 10 - data.size(); i != 0 ; i--) {
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
            if (namesSitesComboBox.getSelectedItem() != null) {
                generalTableModel.setDataSource(namesSitesComboBox.getSelectedItem().toString());
                barChart.getCategoryPlot().setDataset(createDatashet());
                List<GeneralStatisticOnSite> list = GeneralStatisticOnSiteRepository.getInstance().
                        query(GeneralStatisticSpecification.findStatisticSite(namesSitesComboBox.getSelectedItem().toString()));
                if (!list.isEmpty()) {
                    Date date = list.get(0).getReviewDate().getTime();
                    SimpleDateFormat formater = new SimpleDateFormat("Актуально на: (dd.MM.yyyy)");
                    dateScanSite.setText(formater.format(date));
                }
            }
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
