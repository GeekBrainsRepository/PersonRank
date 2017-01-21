package PersonRank.DialogForm;

import javax.swing.*;
import java.awt.*;

public class AddElementPanelClass extends WindowDialog{
    static void addElementPanel(){
        panel();
    }

    private static void panel(){
        addElementPanel.setLayout(null);

        JLabel name = new JLabel();
        name.setText("Наименование");
        name.setFont(new Font("Arial", Font.PLAIN, 12));
        Dimension dimension = name.getPreferredSize();

        JTextField enterText = new JTextField();
        enterText.setFont(new Font("Arial", Font.PLAIN, 14));

        JButton save = new JButton();
        save.setText("Сохранить");
        save.setFont(new Font("Tahoma", Font.PLAIN, 11));

        JButton decline = new JButton();
        decline.setText("Отмена");
        decline.setFont(new Font("Tahoma", Font.PLAIN, 11));

        name.setBounds(5, 10, dimension.width,dimension.height);
        enterText.setBounds(5, 40, 385, 30);
        save.setBounds(190, 85, 90, 30);
        decline.setBounds(290, 85, 90, 30);

        addElementPanel.add(name);
        addElementPanel.add(enterText);
        addElementPanel.add(save);
        addElementPanel.add(decline);
    }
}
