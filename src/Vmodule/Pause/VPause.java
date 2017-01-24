package Vmodule.Pause;

import javax.swing.*;

/**
 * Klasa odpowiedzialna za wygl&#x105;d okna pauzy
 */
public class VPause {
    private JPanel panel1;
    public  VPause(JFrame frame){
        frame.setVisible(false);
        frame.setContentPane(panel1);
        frame.setVisible(true);
    }
}
