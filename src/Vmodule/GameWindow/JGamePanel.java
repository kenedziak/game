package Vmodule.GameWindow;

import Controler.Controler;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Klasa rozszerzaj&#x105;ca klase JPanel, jest elementem na kt&oacute;rym jest rysowany Obraz gry
 */
public class JGamePanel extends JPanel {
    /**
     * Obraz gry wy&#x15b;wietlany na oknie
     */
    BufferedImage mapImg;


    public JGamePanel(BufferedImage mapImage) {
        this.mapImg = mapImage;

    }

    @Override
    synchronized public void paintComponent(Graphics g) {
        super.paintComponent(g);
        BufferedImage newImage = new BufferedImage(super.getWidth(),super.getHeight(), BufferedImage.TYPE_INT_RGB);

        Graphics ag = newImage.createGraphics();
        ag.drawImage(mapImg, 0, 0, super.getWidth(),super.getHeight(), null);
        ag.dispose();
        g.drawImage(newImage,0,0,this);


    }
}

