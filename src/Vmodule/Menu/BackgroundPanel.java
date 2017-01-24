package Vmodule.Menu;

import Controler.Controler;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Klasa odpowiedzialna za &#x142;adowanie obrazu w menu
 */

public class BackgroundPanel extends JPanel {

    private BufferedImage backgroundImage;

    public BackgroundPanel() {

        try {
            backgroundImage = ImageIO.read(new File(Controler.class.getProtectionDomain().getCodeSource().getLocation().getPath() + "menu.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    synchronized public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Image c;
        int w = super.getWidth();
        int h = super.getHeight();
        c = backgroundImage.getScaledInstance(w, h, Image.SCALE_DEFAULT);
        g.drawImage(c, 0, 0, this);
    }

}





