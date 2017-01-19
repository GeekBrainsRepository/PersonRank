package PersonRank;

import javax.swing.*;
import java.awt.*;


public class SitesPanelClass extends Window{
    static void sitesPanel(){
        panel();
    }
    private static void panel(){
        JLabel site = new JLabel();
        site.setText("Сайты");
        Font font = new Font("Arial", Font.PLAIN, 12);
        site.setFont(font);
        Dimension size = site.getPreferredSize();
        sitesPanel.add(site);
        site.setBounds(7,12,size.width,size.height);
    }
}
