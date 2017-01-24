package Controler;

import Lmodule.Game;
import Vmodule.GameWindow.JGamePanel;
import Vmodule.Menu.Vabout;
import Vmodule.GameWindow.VgameWindow;
import Vmodule.Menu.Vmenu;
import Vmodule.Pause.VPause;
import com.sun.istack.internal.Nullable;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.sql.Time;

/**
 * Klasa odpowiedzialna, za komunikacje pomi&#x119;dzy modu&#x142;em logicznym(Lmodule), a  modu&#x142;em pokazuj&#x105;cym(Vmodule)
 *
 */

public class Controler {
    /**
     * Zmienna reprezentuj&#x105;ca g&#x142;&oacute;wne okno aplikacji
     */

    public JFrame frame;
    /**
     * Zmienna reprezentuj&#x105;ca aktualn&#x105; gre;
     */

    public Game g= null;

    /**
     * Tworzy nowy kontorler, kt&oacute;ry ustawia wszystkie domy&#x15b;lne parametry pola klasy frame oraz &#x142;aduje ekran menu.
     */
    public Controler() {

        this.frame = new JFrame();
        this.frame.setSize(600, 400);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width / 2 - frame.getSize().width / 2, dim.height / 2 - frame.getSize().height / 2);
        frame.setFocusable(true);
        frame.setTitle("Best Game Ever");
        this.showMenu();
        this.init();
    }

    /**
     * Metoda odpowiedzialna za od&#x15b;wie&#x17c;anie okna gry .
     * @param x grafika, kt&oacute;ra zostanie za&#x142;adowana do okna gry (pole frame)
     */
    synchronized public void reload(BufferedImage x) {

       VgameWindow window = new VgameWindow(x,this);
 }



    private void showMenu() {
        Vmenu menu = new Vmenu(this.frame, this);

    }
    /**
     * Metoda inicjuj&#x105;ca actionListener dla pola frame.
     */
    public void init() {
        frame.addKeyListener(new KeyAction(this));
    }
    synchronized public void LoadLvl(BufferedImage[][] imap,int rowsize, int score) {

        VgameWindow gameWindow = new VgameWindow(imap,rowsize, this);

    }
    public void pauseWindow() {
        Vmenu menu = new Vmenu(this.frame, this);


        //     VPause pause = new VPause(this.frame);
    }
    public int buttonClick(ActionEvent e) {
        String buttonClickedName = e.getActionCommand();
        if (buttonClickedName.equals("Nowa Gra")) {
            this.g = new Game();
            this.g.gameInit(this);
        }
        if (buttonClickedName.equals("Opcje")) {


        }
        if (buttonClickedName.equals("O twórcach")) {
            Vabout vAbout = new Vabout(this.frame, this);

        }
        if (buttonClickedName.equals("O twórcach")) {
            Vabout vAbout = new Vabout(this.frame, this);

        }

        if (buttonClickedName.equals("Wróć do menu")) {
            Vmenu vMenu = new Vmenu(this.frame, this);

        }


        if (buttonClickedName.equals("Zamknij")) {
            System.exit(0);
        }


        return 0;

    }

    /**
     * Metoda, kt&oacute;ra jest wykonywana podczas ko&#x144;czenia gry. Wy&#x142;&#x105;cza gre oraz wy&#x15b;wietla menu.
     */
    public void gameOver() {
        this.g = null;
        this.showMenu();
    }

    private void checkScore(int score) {


    }

    /**
     * Metoda definiuj&#x105;ca zachowanie podczas naci&#x15b;ni&#x119;cia danego przycisku
     * @param e KeyEvent
     */
    public void keyPress(KeyEvent e) {
        if(g !=null ) {
            if ('w' == e.getKeyChar()) {
                this.g.moveUp();
            }
            if ('s' == e.getKeyChar()) {
                this.g.moveDown();
            }

            if ('a' == e.getKeyChar()) {
                this.g.moveLeft();
            }

            if ('d' == e.getKeyChar()) {
                this.g.moveRight();
            }
            if ('p' == e.getKeyChar()) {
                this.g.pause();
            }


        }

    }

    class KeyAction implements KeyListener {
        Controler c;

        KeyAction(Controler c) {
            this.c = c;
        }

        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public synchronized void keyPressed(KeyEvent e) {
            this.c.keyPress(e);

        }

        @Override
        public void keyReleased(KeyEvent e) {


        }
    }



}


