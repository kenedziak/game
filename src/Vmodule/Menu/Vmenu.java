package Vmodule.Menu;

import Controler.Controler;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Klasa reprezentuj&#x105;ca i definiuj&#x105;ca wygl&#x105;d Menu
 */
public class Vmenu {
    private JPanel main;
    private JButton nowaGraButton;
    private JButton oTwórcachButton;
    private JButton opcjeButton;
    private JButton zamknijButton;
    private JPanel menuPanel;
    private Controler c;

    public Vmenu(JFrame frame, Controler c) {
        this.c = c;
        frame.setContentPane(menuPanel);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        ButtonAction actionlistener = new ButtonAction();
        nowaGraButton.addActionListener(actionlistener);
        oTwórcachButton.addActionListener(actionlistener);
        opcjeButton.addActionListener(actionlistener);
        zamknijButton.addActionListener(actionlistener);


    }

    private void createUIComponents() {
        this.menuPanel = new BackgroundPanel();
    }


    class ButtonAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            c.buttonClick(e);
        }
    }
}
