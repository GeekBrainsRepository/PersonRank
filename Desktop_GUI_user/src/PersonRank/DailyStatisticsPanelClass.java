package PersonRank;

import javax.swing.*;
import java.awt.*;

public class DailyStatisticsPanelClass extends Window {
    static void dailyStatisticsPanel(){
        panel();
    }
    private static void panel(){
        JLabel site = new JLabel();
        site.setText("Ежедневная статистика");
        Font font = new Font("Arial", Font.PLAIN, 12);
        site.setFont(font);
        Dimension size = site.getPreferredSize();
        dailyStatisticsPanel.add(site);
        site.setBounds(7,12,size.width,size.height);
    }
}
