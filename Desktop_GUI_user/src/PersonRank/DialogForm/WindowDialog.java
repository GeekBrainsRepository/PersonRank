package PersonRank.DialogForm;

import javax.swing.*;
import java.awt.*;

public class WindowDialog extends JFrame{

    static JPanel addElementPanel = new JPanel();
    static JPanel editElementPanel = new JPanel();
    static JPanel deleteElementPanel = new JPanel();
    public WindowDialog(){
        setSize(400, 150);

        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        initComponents();
    }
    void initComponents(){
        AddElementPanelClass.addElementPanel();
        addElementPanel.setVisible(false);
        add(addElementPanel, BorderLayout.CENTER);

        EditElementPanelClass.editElementPanel();
        editElementPanel.setVisible(false);
        add(editElementPanel, BorderLayout.CENTER);
        DeleteElementPanelClass.deleteElementPanel();
        deleteElementPanel.setVisible(true);
        add(deleteElementPanel);

    }

//    void setTitleDialog(String title){
//
//        setTitle(title);
//    }
}
