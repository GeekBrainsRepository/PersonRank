package ru.personrank.view;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.PopupMenu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.plaf.FontUIResource;

/**
 * Класс реализует альтернативный вариант ContentPane для JFrame с новым
 * оформлением
 *
 * @author
 *
 */
class ContentPane extends JComponent {

    // Константы класса
    private static final Color TITLE_COLOR = new Color(20, 116, 138, 248);
    private static final Color HEADER_COLOR = new Color(170, 170, 170, 255);
    private static final Color CONTENT_GRADIENT_A = new Color(130, 130, 130, 128);
    private static final Color CONTENT_GRADIENT_B = new Color(220, 220, 220, 64);
    private static final int HEIGHT_HEADER = 40;
    private static final float WIDTH_BORDER = 5.0f;

    // Компоненты панели
    private JComponent headerPanel;
    private JLabel title;
    private JButton bnExit;
    private JButton bnExtended;
    private JButton bnIconified;
    private JComponent contentPanel;

    // Конструктор класса
    public ContentPane() {
        super.setLayout(new BorderLayout());
        Border outsideBorder = BorderFactory.createBevelBorder(BevelBorder.RAISED, TITLE_COLOR, new Color(20, 116, 138, 128));
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

        try {
            Font fontTitle = Font.createFont(Font.TRUETYPE_FONT, 
                    getClass().getResourceAsStream("/ru/resources/fonts/Renfrew.ttf"))
                    .deriveFont(Font.PLAIN, 26);
//            Font fontTitle = Font.createFont(Font.TRUETYPE_FONT,
//                    new File(System.getProperty("user.dir") + "/fonts/Renfrew.ttf"))
//                    .deriveFont(Font.PLAIN, 26);
            title = new JLabel();
            title.setAlignmentY(JLabel.TOP_ALIGNMENT);
            title.setVerticalTextPosition(JLabel.BOTTOM);
            title.setFont(fontTitle);
            title.setForeground(TITLE_COLOR);
        } catch (FontFormatException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        bnExit = new JButton();
        bnExit.setAlignmentY(JButton.TOP_ALIGNMENT);
        Dimension bnExitSize = new Dimension(32, 32);
        bnExit.setPreferredSize(bnExitSize);
        bnExit.setMinimumSize(bnExitSize);
        bnExit.setMaximumSize(bnExitSize);
        bnExit.setIcon(new ImageIcon(getClass().getResource("/ru/resources/images/button_exit_default.png")));
        bnExit.setRolloverIcon(new ImageIcon(getClass().getResource("/ru/resources/images/button_exit_rollover.png")));
        bnExit.setPressedIcon(new ImageIcon(getClass().getResource("/ru/resources/images/button_exit_pressed.png")));
        bnExit.addActionListener(new ActionExit());

        bnExtended = new JButton();
        bnExtended.setAlignmentY(JButton.TOP_ALIGNMENT);
        bnExtended.setPreferredSize(bnExitSize);
        bnExtended.setMinimumSize(bnExitSize);
        bnExtended.setMaximumSize(bnExitSize);
        bnExtended.setIcon(new ImageIcon(getClass().getResource("/ru/resources/images/button_expand_default.png")));
        bnExtended.setRolloverIcon(new ImageIcon(getClass().getResource("/ru/resources/images/button_expand_rollover.png")));
        bnExtended.setPressedIcon(new ImageIcon(getClass().getResource("/ru/resources/images/button_expand_pressed.png")));
        bnExtended.addActionListener(new ActionExtended());
        bnExtended.setEnabled(false);

        bnIconified = new JButton();
        bnIconified.setAlignmentY(JButton.TOP_ALIGNMENT);
        bnIconified.setPreferredSize(bnExitSize);
        bnIconified.setMinimumSize(bnExitSize);
        bnIconified.setMaximumSize(bnExitSize);
        bnIconified.setIcon(new ImageIcon(getClass().getResource("/ru/resources/images/button_turn_default.png")));
        bnIconified.setRolloverIcon(new ImageIcon(getClass().getResource("/ru/resources/images/button_turn_rollover.png")));
        bnIconified.setPressedIcon(new ImageIcon(getClass().getResource("/ru/resources/images/button_turn_pressed.png")));
        bnIconified.addActionListener(new ActionIconified());

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
        headerPanel.add(bnIconified);
        headerPanel.add(Box.createHorizontalStrut(2));
        headerPanel.add(bnExtended);
        headerPanel.add(Box.createHorizontalStrut(2));
        headerPanel.add(bnExit);
    }

    // Метод инициализирующий панель контента
    private void initContentPanel() {
        contentPanel.setLayout(new BorderLayout());
        contentPanel.setOpaque(false);
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

    // Метод устанавливает надпись в заголовок окна
    void setTitle(String title) {
        this.title.setText(title);
    }

    // Метод устанавливает иконку в заголовок окна
    void setTitleIcon(ImageIcon imageIcon) {
        title.setIcon(imageIcon);
    }

    // Класс реализующий команду завершения работы приложения
    private class ActionExit implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }

    // Класс реализующий команду развернуть окно на весь экран
    private class ActionExtended implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (Window.getInstance().getExtendedState() == Window.MAXIMIZED_BOTH) {
                 Window.getInstance().setExtendedState(Window.NORMAL);
            } else {
                Window.getInstance().setExtendedState(Window.MAXIMIZED_BOTH);
            }
        }
    }

    // Класс реализующий каманду свернуть окно
    private class ActionIconified implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Window.getInstance().setState(Window.ICONIFIED);
        }
    }

}
