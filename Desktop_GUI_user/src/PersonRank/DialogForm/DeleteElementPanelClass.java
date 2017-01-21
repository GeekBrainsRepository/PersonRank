package PersonRank.DialogForm;

import javax.swing.*;
import java.awt.*;

public class DeleteElementPanelClass extends WindowDialog{
    static void deleteElementPanel(){
        panel();
    }
    private static void panel(){
        deleteElementPanel.setLayout(null);

        JLabel name = new JLabel();
        name.setText("Вы хотите удалить элемент ");
        name.setFont(new Font("Arial", Font.BOLD, 16));
        Dimension dimension = name.getPreferredSize();

        JButton yes = new JButton();
        yes.setText("Да");
        yes.setFont(new Font("Tahoma", Font.PLAIN, 11));
        yes.setFocusable(false);

        JButton no = new JButton();
        no.setText("Нет");
        no.setFont(new Font("Tahoma", Font.PLAIN, 11));
        no.setFocusable(true);

        name.setBounds(30, 30, dimension.width,dimension.height);
        yes.setBounds(90, 85, 90, 30);
        no.setBounds(210, 85, 90, 30);

        deleteElementPanel.add(name);
        deleteElementPanel.add(yes);
        deleteElementPanel.add(no);
    }
}
