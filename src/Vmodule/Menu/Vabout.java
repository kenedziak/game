package Vmodule.Menu;

import Controler.Controler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

/**
 * Klasa reprezentuj&#x105;ca i definiuj&#x105;ca wygl&#x105;d panelu o tw&oacute;rcach
 */
public class Vabout {
    private JPanel aboutMainPanel;
    private JButton wróćDoMenuButton;
    private JLabel aboutLabel;
    private Controler c;

    public Vabout(JFrame frame, Controler c) {
        this.c = c;
        wróćDoMenuButton.addActionListener(new ButtonAction());
        frame.setContentPane(aboutMainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    class ButtonAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println(e.getActionCommand());
            c.buttonClick(e);
        }
    }
}

