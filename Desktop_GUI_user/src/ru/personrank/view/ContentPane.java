package ru.personrank.view;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.PopupMenu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

/**
 * Класс реализует альтернативный вариант ContentPane для JFrame с новым
 * оформлением
 *
 * @author
 *
 */
class ContentPane extends JComponent {
    
    // Константы класса
    private static final Color HEADER_COLOR = new Color(130, 130, 130, 255);
    private static final Color CONTENT_GRADIENT_A = new Color(130, 130, 130, 128);
    private static final Color CONTENT_GRADIENT_B = new Color(220, 220, 220, 64);
    private static final int HEIGHT_HEADER = 40;
    private static final float WIDTH_BORDER = 5.0f;
    
    // Компоненты панели
    private JComponent headerPanel; 
    private JLabel title;
    private JButton bnExit;
    private JButton bnExpand;
    private JButton bnTurn;
    private JComponent contentPanel;
    
    // Конструктор класса
    public ContentPane() {
        super.setLayout(new BorderLayout());
        Border outsideBorder = BorderFactory.createEtchedBorder();
        Border insideBorder = BorderFactory.createStrokeBorder(new BasicStroke(WIDTH_BORDER), HEADER_COLOR);
        super.setBorder(BorderFactory.createCompoundBorder(outsideBorder, insideBorder));
        initHeaderPanel();
        initContentPanel();
        super.add(headerPanel, BorderLayout.NORTH);
        super.add(contentPanel, BorderLayout.CENTER);
    }

    // Метод инициализирующий панель заголовка
    private void initHeaderPanel() {
        headerPanel = new JPanel();
        
        title = new JLabel();
        title.setAlignmentY(JLabel.TOP_ALIGNMENT);
        title.setFont(new Font("TimesRoman", Font.BOLD,   30));
        title.setForeground(Color.WHITE);
        
        bnExit = new JButton();
        bnExit.setAlignmentY(JButton.TOP_ALIGNMENT);
        Dimension bnExitSize = new Dimension(32,32);
        bnExit.setPreferredSize(bnExitSize);
        bnExit.setMinimumSize(bnExitSize);
        bnExit.setMaximumSize(bnExitSize);
        bnExit.setIcon(new ImageIcon("images/button_exit_default.png"));
        bnExit.setRolloverIcon(new ImageIcon("images/button_exit_rollover.png"));
        bnExit.setPressedIcon(new ImageIcon("images/button_exit_pressed.png"));
        bnExit.addActionListener(new ActionExit());
        
        bnExpand = new JButton();
        bnExpand.setAlignmentY(JButton.TOP_ALIGNMENT);
        bnExpand.setPreferredSize(bnExitSize);
        bnExpand.setMinimumSize(bnExitSize);
        bnExpand.setMaximumSize(bnExitSize);
        bnExpand.setIcon(new ImageIcon("images/button_expand_default.png"));
        bnExpand.setRolloverIcon(new ImageIcon("images/button_expand_rollover.png"));
        bnExpand.setPressedIcon(new ImageIcon("images/button_expand_pressed.png"));
        bnExpand.addActionListener(new ActionExpand());
                
        bnTurn = new JButton();
        bnTurn.setAlignmentY(JButton.TOP_ALIGNMENT);
        bnTurn.setPreferredSize(bnExitSize);
        bnTurn.setMinimumSize(bnExitSize);
        bnTurn.setMaximumSize(bnExitSize);
        bnTurn.setIcon(new ImageIcon("images/button_turn_default.png"));
        bnTurn.setRolloverIcon(new ImageIcon("images/button_turn_rollover.png"));
        bnTurn.setPressedIcon(new ImageIcon("images/button_turn_pressed.png"));
        bnTurn.addActionListener(new ActionTurn());
        
        contentPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.X_AXIS));
        headerPanel.setOpaque(false);
        Dimension headerSize = new Dimension(getWidth(), HEIGHT_HEADER);
        headerPanel.setPreferredSize(headerSize);
        headerPanel.setMaximumSize(headerSize);
        headerPanel.setMinimumSize(headerSize);
        
       
        headerPanel.add(Box.createHorizontalStrut(2));
        headerPanel.add(title);
        headerPanel.add(Box.createHorizontalGlue());
        headerPanel.add(bnTurn);
        headerPanel.add(Box.createHorizontalStrut(2));
        headerPanel.add(bnExpand);
        headerPanel.add(Box.createHorizontalStrut(2));
        headerPanel.add(bnExit);
//        headerPanel.setBorder(new LineBorder(Color.BLACK, 1));
    }
    
    // Метод инициализирующий панель контента
    private void initContentPanel() {
        contentPanel.setLayout(new BorderLayout());
        contentPanel.setOpaque(false);
//        contentPanel.setBorder(new LineBorder(Color.BLACK,1)); 
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setPaint(HEADER_COLOR);
        g2d.fillRect(0, 0, getWidth(), HEIGHT_HEADER + (int) WIDTH_BORDER);
        GradientPaint gradient = new GradientPaint(getWidth() / 2, 0, CONTENT_GRADIENT_A, getWidth() / 2, getHeight(), CONTENT_GRADIENT_B);
        g2d.setPaint(gradient);
        g2d.fillRect(0, HEIGHT_HEADER + (int) WIDTH_BORDER, getWidth(), getHeight());
    }

    @Override
    public Component add(Component comp) {
        return contentPanel.add(comp);
    }

    @Override
    public void add(PopupMenu popup) {
        contentPanel.add(popup);
    }

    @Override
    public void add(Component comp, Object constraints) {
        contentPanel.add(comp, constraints);
    }

    @Override
    public Component add(Component comp, int index) {
        return contentPanel.add(comp, index);
    }

    @Override
    public Component add(String name, Component comp) {
        return contentPanel.add(name, comp);
    }
    
    // Метод передает натпись в заголовок
    void setTitle(String title) {
        this.title.setText(title);
    }
    
    // Класс реализующий команду завершения работы приложения
    private class ActionExit implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }
    
    // Класс реализующий команду развернуть окно на весь экран
    private class ActionExpand implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            
        }
    }
    
    // Класс реализующий каманду свернуть окно
    private class ActionTurn implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            
        }
    }

}
