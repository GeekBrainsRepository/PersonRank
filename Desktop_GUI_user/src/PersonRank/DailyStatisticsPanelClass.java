package PersonRank;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DailyStatisticsPanelClass extends Window {

    private static String[] columnNames = new String[]{
            "Дата",
            "Количество новых страниц",
    };
    private static String[][] data;

    private static DateFormat format = new SimpleDateFormat("DD.MM.YYYY");

    private static JLabel saitLabel = new JLabel();
    private static JLabel personLabel = new JLabel();
    private static JComboBox comboSite = new JComboBox();
    private static JComboBox comboPerson = new JComboBox();
    private static JLabel labelPeriod = new JLabel();
    private static JFormattedTextField formattedTextFieldData1 = new JFormattedTextField(format);
    private static JLabel labelPo = new JLabel();
    private static JFormattedTextField formattedTextFieldData2 = new JFormattedTextField(format);
    private static JButton buttonSend = new JButton();
    private static JTable table;
    private static JScrollPane scrollPane;


    static void dailyStatisticsPanel() {
        Date date = new Date();
        formattedTextFieldData1.setValue(date);
        formattedTextFieldData2.setValue(date);

        // Заполнение таблицы данными
        fillTable();

        //Создание модели таблицы
        DefaultTableModel myTableModel = new DefaultTableModel(data, columnNames);
        table = new JTable(myTableModel) {
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false; //Disallow the editing of any cell
            }
        };
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
}
