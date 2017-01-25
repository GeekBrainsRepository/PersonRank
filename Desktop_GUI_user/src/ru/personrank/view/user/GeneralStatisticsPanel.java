package ru.personrank.view.user;

import ru.personrank.data.generalstatistic.GeneralStatisticOnSite;
import ru.personrank.data.generalstatistic.GeneralStatisticOnSiteRepository;
import ru.personrank.data.generalstatistic.GeneralStatisticSpecification;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.border.EmptyBorder;

public class GeneralStatisticsPanel extends JPanel {

    private static JComboBox namesSitesComboBox;
    private static JButton buttonSend;
    JLabel labelSite;
    private static JTable generalTable;
    private static GeneralStaticTabelModel generalTableModel;
    
    public GeneralStatisticsPanel () {
        setOpaque(false);
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(6,6,6,6));
        labelSite = new JLabel();
        labelSite.setText("Сайт:");
        Font font = new Font("Arial", Font.PLAIN, 12);
        labelSite.setFont(font);
        namesSitesComboBox = new JComboBox(new NamesSitesComboBoxModel());
        buttonSend = new JButton();
        buttonSend.setText("Применить");
        font = new Font("Tahoma", Font.PLAIN, 11);
        buttonSend.setFont(font);
        Dimension bnSendSize = new Dimension(90,30);
        buttonSend.setMinimumSize(bnSendSize);
        buttonSend.setMaximumSize(bnSendSize);
        buttonSend.addActionListener(new ButtonSendListener());
        generalTableModel = new GeneralStaticTabelModel();
        generalTableModel.setDataSource(namesSitesComboBox.getSelectedItem().toString());
        generalTable = new JTable(generalTableModel);
        generalTable.setRowHeight(30);


        Box controlsBox = Box.createHorizontalBox();
        controlsBox.setBorder(new EmptyBorder(0,0,10,0));
        controlsBox.setPreferredSize(new Dimension(430,40));
        controlsBox.add(labelSite);
        controlsBox.add(Box.createRigidArea(new Dimension(15,0)));
        controlsBox.add(namesSitesComboBox);
        controlsBox.add(Box.createRigidArea(new Dimension(15,0)));
        controlsBox.add(buttonSend);
        controlsBox.add(Box.createHorizontalGlue());
        add(controlsBox, BorderLayout.NORTH);
        add(new JScrollPane(generalTable), BorderLayout.CENTER);     
    }

    class NamesSitesComboBoxModel extends DefaultComboBoxModel {

        NamesSitesComboBoxModel() {
            List<GeneralStatisticOnSite> list = GeneralStatisticOnSiteRepository.
                    getInstance().
                    query(GeneralStatisticSpecification.getAllStatisticSite());
            for (GeneralStatisticOnSite gsos : list) {
                addElement(gsos.getSiteName());
            }
        }
    }

    class GeneralStaticTabelModel extends AbstractTableModel {

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
    
    class ButtonSendListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            generalTableModel.setDataSource(namesSitesComboBox.getSelectedItem().toString());
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
