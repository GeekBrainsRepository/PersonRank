package PersonRank;

import javax.swing.*;
import java.awt.*;


public class KeywordsPanelClass extends Window{
    static void keywordsPanel(){
        panel();
    }
    private static void panel(){
        JLabel site = new JLabel();
        site.setText("Ключевые слова");
        Font font = new Font("Arial", Font.PLAIN, 12);
        site.setFont(font);
        Dimension size = site.getPreferredSize();
        keywordsPanel.add(site);
        site.setBounds(7,12,size.width,size.height);
    }
}
