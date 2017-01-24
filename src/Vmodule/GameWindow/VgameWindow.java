package Vmodule.GameWindow;

import Controler.Controler;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

import static javax.swing.SwingConstants.NORTH;

/**
 * Klasa, kt&oacute;ra tworzy ca&#x142;kowite okno gry. Definiuje ona rozmieszenie wszystkich element&oacute;w na ekranie
 */
public class VgameWindow {
    private JPanel panel1;
    private JPanel topPanel;
    private JLabel scoreLabel;
    private JPanel lifePanel;
    private JLabel hearthIm;
    private Controler c;
    private BufferedImage[][] tmp;
    int rowsize;
    int score;
    BufferedImage mapImage=null;


    public VgameWindow(BufferedImage mapImage, Controler c) {

            this.mapImage = mapImage;
            this.c = c;
            this.hearthIm.setIcon(new ImageIcon(Controler.class.getProtectionDomain().getCodeSource().getLocation().getPath() + "Vmodule/PNG/rsz_hearth.jpg"));
            this.hearthIm.setText("X " + Integer.toString(c.g.lifes));
            scoreLabel.setText("PUNKTY: " + Integer.toString(c.g.score) + "   ");

            panel1 = new JGamePanel(mapImage);
            panel1.setLayout(new BorderLayout());
            panel1.add(topPanel, BorderLayout.PAGE_START);
            topPanel.add(lifePanel);
            topPanel.add(hearthIm);

            c.frame.setContentPane(panel1);
            //  c.frame.setVisible(true);

        }

    public VgameWindow(BufferedImage[][] imap, int rowsize, Controler c) {
        this.rowsize = rowsize;
        this.c = c;
        this.tmp = imap;

        this.hearthIm.setIcon(new ImageIcon(Controler.class.getProtectionDomain().getCodeSource().getLocation().getPath() + "Vmodule/PNG/rsz_hearth.jpg"));
        this.hearthIm.setText("X "+ Integer.toString(c.g.lifes));
        scoreLabel.setText("PUNKTY: "+ Integer.toString(c.g.score)+"   ");
        c.frame.setContentPane(panel1);
        c.frame.setVisible(true);


    }


}

