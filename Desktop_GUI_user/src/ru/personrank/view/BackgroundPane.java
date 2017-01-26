package ru.personrank.view;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class BackgroundPane extends JPanel {

    private BufferedImage background;

    public BackgroundPane() {
        try {
            background = ImageIO.read(getClass().getResource("/ru/resources/image/fon.jpeg"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(background.getWidth(), background.getHeight());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int x = (getWidth() - background.getWidth()) / 2;
        int y = (getHeight() - background.getHeight()) / 2;
        g.drawImage(background, x, y, this);
    }

}