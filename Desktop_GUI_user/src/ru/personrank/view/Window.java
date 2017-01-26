package ru.personrank.view;

import ru.personrank.view.admin.KeywordsPanel;
import ru.personrank.view.admin.PersonsPanel;
import ru.personrank.view.admin.SitesPanel;
import ru.personrank.view.user.DailyStatisticsPanel;
import ru.personrank.view.user.GeneralStatisticsPanel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

public class Window extends JFrame {
    private JButton exit = new JButton();
    private JButton hide = new JButton();
    private static final Color WINDOW_BACKGROUND = Color.LIGHT_GRAY;
    private LeftLinksPanel leftLinksPanel = new LeftLinksPanel();
    private JPanel contentPanel = new JPanel(new BorderLayout());
    private JLabel[] labelMenu;
    private JPanel[] panels = new JPanel[]{
            new GeneralStatisticsPanel(),
            new DailyStatisticsPanel(),
            new KeywordsPanel(),
            new PersonsPanel(),
            new SitesPanel()
    };
    private static Font font;
    private JLabel present = new JLabel();

    private BufferedImage icon = ImageIO.read(getClass().getResource("/ru/resources/image/ico.png"));

    public Window() throws IOException, FontFormatException, URISyntaxException {
        setSize(750, 430);
        setLocationRelativeTo(null);
        setResizable(false);
        setTitle("PersonRank");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setContentPane(new BackgroundPane()); //устанавливается задний фон в виде панели
        setLayout(null);
        setIconImage(icon); //меняется иконка окна
        setUndecorated(true); //удаляем стандартную декорацию
        ownDecoration(); //добавляем свою
        present.setBounds(5, 5, 150, 20);
        contentPanel.setOpaque(false);
        //getContentPane().setBackground(WINDOW_BACKGROUND);
        labelMenu = leftLinksPanel.getLabelLeftMenu();
        for (int i = 0; i < labelMenu.length; i++) {
            labelMenu[i].addMouseListener(new MenuMouseListener(i));
        }

        leftLinksPanel.setBounds(5, 30, 230, 390);
        add(leftLinksPanel);
        contentPanel.setBounds(240, 30, 450, 390);
        add(contentPanel);
        contentPanel.add(panels[0]);
        labelMenu[0].setFont(labelMenu[0].getFont().deriveFont(Font.BOLD));
        add(exit);
        add(hide);
        add(present);
        pack();
    }

    private class MenuMouseListener extends MouseAdapter {

        private int index;

        public MenuMouseListener(int indexPanel) {
            index = indexPanel;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.getButton() == MouseEvent.BUTTON1) {
                for (JLabel label : labelMenu) {
                    label.setFont(label.getFont().deriveFont(Font.PLAIN));
                }
                JLabel label = (JLabel) e.getSource();
                label.setFont(label.getFont().deriveFont(Font.BOLD));
                contentPanel.removeAll();
                contentPanel.add(panels[index], BorderLayout.CENTER);
                contentPanel.revalidate();
                contentPanel.repaint();
            }
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            JLabel label = (JLabel) e.getSource();
            String text = label.getText();
            label.setText("<html><b><u>" + text);
        }

        @Override
        public void mouseExited(MouseEvent e) {
            JLabel label = (JLabel) e.getSource();
            String text = label.getText();
            label.setText(text.substring(12));
        }

    }

    private void ownDecoration() {
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, new File(System.getProperty("user.dir") + "/fonts/cyrillicAdaptation.ttf")).deriveFont(Font.PLAIN, 40);
        } catch (FontFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        present.setText("PersonRank");
        present.setFont(font);
        present.setBackground(new Color(120,140,110));
        present.setForeground(new Color(144, 48, 48));
        /*
        *   этот компонент скругляет углы фрейма
        */
        addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                setShape(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 20, 20));
            }
        });

        FrameDragger frameDragger = new FrameDragger(this);//   эти 3 команды реализуют класс FrameDragger
        addMouseListener(frameDragger);                    //   который отвечают за
        addMouseMotionListener(frameDragger);              //   перетаскивание фрейма по экрану


        ImageIcon exitIcon = new ImageIcon(getClass().getResource("/ru/resources/image/exit.png"));
        exit.setBounds(650, 2, 34, 24);
        exit.setOpaque(false);
        exit.setIcon(exitIcon);
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        exit.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                exit.setBounds(650, 4, 34, 24);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                exit.setBounds(650, 2, 34, 24);
            }
        });


        ImageIcon minimazeIcon = new ImageIcon(getClass().getResource("/ru/resources/image/minimize.png"));
        hide.setBounds(615, 2, 32, 32);
        hide.setOpaque(false);
        hide.setIcon(minimazeIcon);
        hide.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setState(Window.HIDE_ON_CLOSE);
            }
        });
        hide.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                hide.setBounds(615, 4, 32, 32);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                hide.setBounds(615, 2, 32, 32);
            }
        });

    }
}
