package PersonRank;

import javax.swing.*;
import java.awt.*;

public class PersonsPanelClass extends Window{
    static void personsPanel(){
        panel();
    }
    private static void panel(){
        JLabel site = new JLabel();
        site.setText("Личности");
        Font font = new Font("Arial", Font.PLAIN, 12);
        site.setFont(font);
        Dimension size = site.getPreferredSize();
        personsPanel.add(site);
        site.setBounds(7,12,size.width,size.height);
    }
}

